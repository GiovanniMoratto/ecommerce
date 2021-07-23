package br.com.zupacademy.giovannimoratto.ecommerce.get_product_details;

import br.com.zupacademy.giovannimoratto.ecommerce.add_category.CategoryRequest;
import br.com.zupacademy.giovannimoratto.ecommerce.add_product.ProductRequest;
import br.com.zupacademy.giovannimoratto.ecommerce.add_product.product_features.FeatureRequest;
import br.com.zupacademy.giovannimoratto.ecommerce.add_product_question.QuestionRequest;
import br.com.zupacademy.giovannimoratto.ecommerce.add_product_review.ReviewRequest;
import br.com.zupacademy.giovannimoratto.ecommerce.add_user.UserRequest;
import br.com.zupacademy.giovannimoratto.ecommerce.security.authentication.login.LoginRequest;
import br.com.zupacademy.giovannimoratto.ecommerce.security.authentication.token.TokenService;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static java.math.BigDecimal.valueOf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @Author giovanni.moratto
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class ProductDetailsController {

    private final MockMvc mockMvc;
    private final Gson gson;
    private final TokenService tokenService;
    private final AuthenticationManager authManager;

    @Autowired
    ProductDetailsController(MockMvc mockMvc, Gson gson, TokenService tokenService, AuthenticationManager authManager) {
        this.mockMvc = mockMvc;
        this.gson = gson;
        this.tokenService = tokenService;
        this.authManager = authManager;
    }

    @BeforeEach
    public void seUp() throws Exception {
        // Create user
        String login = "seller@email.com";
        String password = "123456";
        mockMvc.perform(post("/api/new-user")
                .content(gson.toJson(new UserRequest(login, password)))
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON));
        // Create Category
        mockMvc.perform(post("/api/new-category")
                .content(gson.toJson(new CategoryRequest("Games", null)))
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON));
        // Log user
        String token = tokenService.buildToken(authManager.authenticate(new LoginRequest(login, password).convert()));
        // Create a Product
        mockMvc.perform(post("/api/new-product")
                .header("Authorization", "Bearer " + token)
                .content(gson.toJson(new ProductRequest(
                        "PS5",
                        valueOf(499.99),
                        150,
                        "The PlayStation 5 (PS5) is a home video game console developed by Sony " +
                        "Interactive Entertainment",
                        1L,
                        List.of(
                                new FeatureRequest("Test", "Test"),
                                new FeatureRequest("Test", "Test"),
                                new FeatureRequest("Test", "Test")))))
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON));
        // Add Images
        mockMvc.perform(multipart("/api/product/1/add-images")
                .file(new MockMultipartFile("images", "casper.jpg",
                        "multipart/form-data", getClass().getResourceAsStream("casper.jpg")))
                .header("Authorization", "Bearer " + token));
        // Add review
        mockMvc.perform(post("/api/product/1/add-review")
                .header("Authorization", "Bearer " + token)
                .content(gson.toJson(new ReviewRequest(5, "Amazing", "Best Video Game ever!")))
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON));
        // Create a Question
        QuestionRequest question = new QuestionRequest();
        question.setTitle("Is it good?");
        mockMvc.perform(post("/api/product/1/add-question")
                .header("Authorization", "Bearer " + token)
                .content(gson.toJson(question))
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON));
    }

    /* Methods */
    // GET Request
    @Test
    @DisplayName("404 Not Found - When trying to GET with invalid endpoint")
    void invalidEndpointStatus404() throws Exception {
        mockMvc.perform(get("/api/product/{id}", "x"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(""));
    }

    // GET Request
    @Test
    @DisplayName("200 OK - Succeed and return a list with product details")
    void returnProductDetailsStatus200() throws Exception {
        mockMvc.perform(get("/api/product/1"))
                .andDo(print()).andExpect(status().isOk());
    }

}
