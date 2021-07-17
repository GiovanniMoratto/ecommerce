package br.com.zupacademy.giovannimoratto.ecommerce.authentication;

import br.com.zupacademy.giovannimoratto.ecommerce.add_user.UserRepository;
import br.com.zupacademy.giovannimoratto.ecommerce.add_user.UserRequest;
import br.com.zupacademy.giovannimoratto.ecommerce.security.Authentication.login.LoginRequest;
import com.google.gson.Gson;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * @Author giovanni.moratto
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    private final String urlLogin = "/api/auth";
    private final MockMvc mockMvc;
    private final Gson gson;
    private final UserRepository repository;

    @Autowired
    AuthControllerTest(MockMvc mockMvc, Gson gson, UserRepository repository) {
        this.mockMvc = mockMvc;
        this.gson = gson;
        this.repository = repository;
    }

    @BeforeEach
    void setUpInitial() throws Exception {
        // Values to Success Test
        String urlCreate = "/api/new-user";
        String login = "test@email.com";
        String password = "123456";

        // Create User
        String jsonRequest = gson.toJson(new UserRequest(login, password));
        mockMvc.perform(MockMvcRequestBuilders.post(urlCreate)
                .content(jsonRequest)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON));
    }

    @AfterEach
    void setUpFinal() {
        this.repository.deleteAll();
    }

    /* Methods */
    // POST Request
    @Test
    @DisplayName("400 Bad Request - When trying to POST with empty BODY")
    void emptyBodyStatus400() throws Exception {
        // Values to Fail Test
        mockMvc.perform(MockMvcRequestBuilders.post(urlLogin)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    // POST Request
    @Test
    @DisplayName("400 Bad Request - When trying to POST with empty Object")
    void emptyObjectStatus400() throws Exception {
        // Values to Fail Test
        mockMvc.perform(MockMvcRequestBuilders.post(urlLogin)
                .content("{ }")
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    // POST Request
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"invalidemail.com", "@invalid.com", "@.com", "@invalid"})
    @DisplayName("400 Bad Request - When trying to POST with invalid type of USERNAME")
    void usernameInvalidStatus400(String username) throws Exception {
        // Values to Fail Test
        String password = "123456";
        String jsonRequest = gson.toJson(new UserRequest(username, password));
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(urlLogin)
                .content(jsonRequest)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();
        Assertions.assertFalse(result.getResponse().getContentAsString().contains("Bearer"));
    }

    // POST Request
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"1", "12", "123", "1234", "12345"})
    @DisplayName("400 Bad Request - When trying to POST with invalid type of PASSWORD")
    void passwordInvalidStatus400(String password) throws Exception {
        // Values to Fail Test
        String username = "test@email.com";
        String jsonRequest = gson.toJson(new UserRequest(username, password));
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(urlLogin)
                .content(jsonRequest)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();
        Assertions.assertFalse(result.getResponse().getContentAsString().contains("Bearer"));
    }

    // POST Request
    @ParameterizedTest
    @ValueSource(strings = {"test01@email.com", "test02@email.com", "test03@email.com"})
    @DisplayName("401 Unauthorized - When trying to POST with invalid LOGIN - USERNAME")
    void loginUsernameInvalidStatus401(String username) throws Exception {
        // Values to Fail Test
        String password = "123456";

        String jsonRequest = gson.toJson(new UserRequest(username, password));
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(urlLogin)
                .content(jsonRequest)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized()).andReturn();
        Assertions.assertTrue(result.getResponse().getContentAsString().contains("ERROR"));
    }

    // POST Request
    @ParameterizedTest
    @ValueSource(strings = {"000000", "111111", "222222"})
    @DisplayName("401 Unauthorized - When trying to POST with invalid LOGIN - PASSWORD")
    void loginPasswordInvalidStatus401(String password) throws Exception {
        // Values to Fail Test
        String username = "test@email.com";

        String jsonRequest = gson.toJson(new UserRequest(username, password));
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(urlLogin)
                .content(jsonRequest)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized()).andReturn();
        Assertions.assertTrue(result.getResponse().getContentAsString().contains("ERROR"));
    }

    // POST Request
    @Test
    @DisplayName("401 Unauthorized - When trying to POST with invalid LOGIN for both")
    void loginInvalidStatus401() throws Exception {
        // Values to Fail Test
        String username = "invalid@email.com";
        String password = "invalid";

        String jsonRequest = gson.toJson(new UserRequest(username, password));
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(urlLogin)
                .content(jsonRequest)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized()).andReturn();
        Assertions.assertTrue(result.getResponse().getContentAsString().contains("ERROR"));
    }

    // POST Request
    @Test
    @DisplayName("200 OK - Succeed and log the user into the app")
    void logUserStatus200() throws Exception {
        // Values to Success Test
        String username = "test@email.com";
        String password = "123456";

        // Log User
        String jsonRequest = gson.toJson(new LoginRequest(username, password));
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(urlLogin)
                .content(jsonRequest)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        Assertions.assertTrue(result.getResponse().getContentAsString().contains("Bearer"));
    }

}