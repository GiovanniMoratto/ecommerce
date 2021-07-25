package br.com.zupacademy.giovannimoratto.ecommerce.users;

import com.google.gson.Gson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.ZonedDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.Optional;

/**
 * @Author giovanni.moratto
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    private final String urlTemplate = "/api/new-user";
    private final MockMvc mockMvc;
    private final Gson gson;
    private final UserRepository repository;

    @Autowired
    UserControllerTest(MockMvc mockMvc, Gson gson, UserRepository repository) {
        this.mockMvc = mockMvc;
        this.gson = gson;
        this.repository = repository;
    }

    @BeforeEach
    void setUp() {
        this.repository.deleteAll();
    }

    /* Methods */
    // POST Request
    @Test
    @DisplayName("400 Bad Request - When trying to POST with empty BODY")
    void emptyBodyStatus400() throws Exception {
        // Values to Fail Test
        mockMvc.perform(MockMvcRequestBuilders.post(urlTemplate)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    // POST Request
    @Test
    @DisplayName("400 Bad Request - When trying to POST with empty Object")
    void emptyObjectStatus400() throws Exception {
        // Values to Fail Test
        mockMvc.perform(MockMvcRequestBuilders.post(urlTemplate)
                .content("{ }")
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    // POST Request
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"invalidEmail.com", "@invalid.com", "@.com", "@invalid"})
    @DisplayName("400 Bad Request - When trying to POST with invalid LOGIN")
    void loginInvalidStatus400(String login) throws Exception {
        // Values to Fail Test
        String password = "123456";
        String jsonRequest = gson.toJson(new UserRequest(login, password));
        mockMvc.perform(MockMvcRequestBuilders.post(urlTemplate)
                .content(jsonRequest)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
        Assertions.assertTrue(repository.findByLogin(login).isEmpty());
    }

    // POST Request
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"1", "12", "123", "1234", "12345"})
    @DisplayName("400 Bad Request - When trying to POST with invalid PASSWORD")
    void passwordInvalidStatus400(String password) throws Exception {
        // Values to Fail Test
        String login = "test@email.com";
        String jsonRequest = gson.toJson(new UserRequest(login, password));
        mockMvc.perform(MockMvcRequestBuilders.post(urlTemplate)
                .content(jsonRequest)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
        Assertions.assertTrue(repository.findByLogin("test@email.com").isEmpty());
    }

    // POST Request
    @Test
    @DisplayName("200 OK - Succeed and persist the New User in the Database")
    void createNewUserStatus200() throws Exception {
        // Values to Success Test
        String login = "test@email.com";
        String password = "123456";
        String jsonRequest = gson.toJson(new UserRequest(login, password));
        mockMvc.perform(MockMvcRequestBuilders.post(urlTemplate)
                .content(jsonRequest)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Optional <UserModel> optionalUser = repository.findByLogin(login);
        Assertions.assertTrue(optionalUser.isPresent());
        UserModel user = optionalUser.get();
        Assertions.assertTrue(BCrypt.checkpw(password, user.getPassword()));
        Assertions.assertTrue(user.getCreatedAt().isBefore(ChronoLocalDateTime.from(ZonedDateTime.now())));
    }

    // POST Request
    @Test
    @DisplayName("400 Bad Request - When trying to POST with duplicate LOGIN")
    void duplicateLoginStatus400() throws Exception {
        // Values to Fail Test
        String login = "duplicate@email.com";
        String password = "123456";

        UserRequest request1 = new UserRequest(login, password);
        String jsonRequest1 = gson.toJson(request1);

        UserRequest request2 = new UserRequest(login, password);
        String jsonRequest2 = gson.toJson(request2);

        mockMvc.perform(MockMvcRequestBuilders.post(urlTemplate)
                .content(jsonRequest1)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON));

        mockMvc.perform(MockMvcRequestBuilders.post(urlTemplate)
                .content(jsonRequest2)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        Assertions.assertEquals(1, repository.countByLogin(login));
    }

}