package br.com.zupacademy.giovannimoratto.ecommerce.add_product;

import br.com.zupacademy.giovannimoratto.ecommerce.add_category.CategoryRequest;
import br.com.zupacademy.giovannimoratto.ecommerce.add_user.UserRequest;
import br.com.zupacademy.giovannimoratto.ecommerce.security.Authentication.login.LoginRequest;
import br.com.zupacademy.giovannimoratto.ecommerce.security.Authentication.token.TokenService;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * @Author giovanni.moratto
 */


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

    private final String urlTemplate = "/api/new-product";
    private final MockMvc mockMvc;
    private final Gson gson;
    private final AuthenticationManager authManager;
    private final TokenService tokenService;
    private String token;

    @Autowired
    ProductControllerTest(MockMvc mockMvc, Gson gson, AuthenticationManager authManager, TokenService tokenService) {
        this.mockMvc = mockMvc;
        this.gson = gson;
        this.authManager = authManager;
        this.tokenService = tokenService;
    }

    @BeforeEach
    void setUpInitial() throws Exception {
        String login = "test@email.com";
        String password = "123456";
        // Create User
        String jsonRequest1 = gson.toJson(new UserRequest(login, password));
        mockMvc.perform(MockMvcRequestBuilders.post("/api/new-user")
                .content(jsonRequest1)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON));
        // Create Category
        String jsonRequest2 = gson.toJson(new CategoryRequest("test", null));
        mockMvc.perform(MockMvcRequestBuilders.post("/api/new-category")
                .content(jsonRequest2)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON));
        // Log User
        LoginRequest request = new LoginRequest(login, password);
        token = tokenService.buildToken(authManager.authenticate(request.convert()));
    }

    /* Methods */
    // POST Request
    @Test
    @DisplayName("400 Bad Request - When trying to POST with empty BODY")
    void emptyBodyStatus400() throws Exception {
        // Values to Fail Test
        mockMvc.perform(MockMvcRequestBuilders.post(urlTemplate)
                .header("Authorization", "Bearer " + token)
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
                .header("Authorization", "Bearer " + token)
                .content("{ }")
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

}
