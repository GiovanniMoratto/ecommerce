package br.com.zupacademy.giovannimoratto.ecommerce.questions;

import br.com.zupacademy.giovannimoratto.ecommerce.categories.CategoryRequest;
import br.com.zupacademy.giovannimoratto.ecommerce.products.ProductModel;
import br.com.zupacademy.giovannimoratto.ecommerce.products.ProductRepository;
import br.com.zupacademy.giovannimoratto.ecommerce.products.ProductRequest;
import br.com.zupacademy.giovannimoratto.ecommerce.products.product_features.FeatureRequest;
import br.com.zupacademy.giovannimoratto.ecommerce.security.authentication.login.LoginRequest;
import br.com.zupacademy.giovannimoratto.ecommerce.security.authentication.token.TokenService;
import br.com.zupacademy.giovannimoratto.ecommerce.users.UserModel;
import br.com.zupacademy.giovannimoratto.ecommerce.users.UserRepository;
import br.com.zupacademy.giovannimoratto.ecommerce.users.UserRequest;
import com.google.gson.Gson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.math.BigDecimal.valueOf;

/**
 * @Author giovanni.moratto
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class QuestionControllerTest {

    private final MockMvc mockMvc;
    private final Gson gson;
    private final TokenService tokenService;
    private final AuthenticationManager authManager;
    private final QuestionRepository questionRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private String customerToken;

    @Autowired
    QuestionControllerTest(MockMvc mockMvc, Gson gson, TokenService tokenService, AuthenticationManager authManager,
                           QuestionRepository questionRepository, ProductRepository productRepository,
                           UserRepository userRepository) {
        this.mockMvc = mockMvc;
        this.gson = gson;
        this.tokenService = tokenService;
        this.authManager = authManager;
        this.questionRepository = questionRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @BeforeEach
    void setUpInitial() throws Exception {
        /* Create Users */
        // Salesperson
        String salespersonLogin = "salesperson@email.com";
        String salespersonPassword = "123456";
        String salesRequest = gson.toJson(new UserRequest(salespersonLogin, salespersonPassword));
        mockMvc.perform(MockMvcRequestBuilders.post("/api/new-user")
                .content(salesRequest)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON));
        // Customer
        String customerLogin = "customer@email.com";
        String customerPassword = "123456";
        String userRequest = gson.toJson(new UserRequest(customerLogin, customerPassword));
        mockMvc.perform(MockMvcRequestBuilders.post("/api/new-user")
                .content(userRequest)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON));
        /* Create Category */
        String categoryRequest = gson.toJson(new CategoryRequest("Games", null));
        mockMvc.perform(MockMvcRequestBuilders.post("/api/new-category")
                .content(categoryRequest)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON));
        /* Log Users */
        // Salesperson
        LoginRequest loginSalesperson = new LoginRequest(salespersonLogin, salespersonPassword);
        String salespersonToken = tokenService.buildToken(authManager.authenticate(loginSalesperson.convert()));
        // Customer
        LoginRequest loginCustomer = new LoginRequest(customerLogin, customerPassword);
        customerToken = tokenService.buildToken(authManager.authenticate(loginCustomer.convert()));
        // Create a Product
        ProductRequest request = new ProductRequest(
                "PS5",
                valueOf(499.99),
                150,
                "The PlayStation 5 (PS5) is a home video game console developed by Sony " +
                "Interactive Entertainment",
                1L,
                List.of(
                        new FeatureRequest("Test", "Test"),
                        new FeatureRequest("Test", "Test"),
                        new FeatureRequest("Test", "Test")
                       )
        );
        String productRequest = gson.toJson(request);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/new-product")
                .header("Authorization", "Bearer " + salespersonToken)
                .content(productRequest)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON));
    }

    /* Methods */
    // POST Request
    @Test
    @DisplayName("400 Bad Request - When trying to POST with empty BODY")
    void emptyBodyStatus400() throws Exception {
        // Values to Fail Test
        long idProduct = 1L;
        mockMvc.perform(MockMvcRequestBuilders.post("/api/product/" + idProduct + "/add-question")
                .header("Authorization", "Bearer " + customerToken)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    // POST Request
    @Test
    @DisplayName("400 Bad Request - When trying to POST with empty Object")
    void emptyObjectStatus400() throws Exception {
        // Values to Fail Test
        long idProduct = 1L;
        mockMvc.perform(MockMvcRequestBuilders.post("/api/product/" + idProduct + "/add-question")
                .header("Authorization", "Bearer " + customerToken)
                .content("{ }")
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    // POST Request
    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("400 Bad Request - When trying to POST with an invalid TITLE")
    void titleInvalidStatus400(String title) throws Exception {
        // Values to Fail Test
        long idProduct = 1L;
        QuestionRequest request = new QuestionRequest();
        request.setTitle(title);

        String jsonRequest = gson.toJson(request);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/product/" + idProduct + "/add-question")
                .header("Authorization", "Bearer " + customerToken)
                .content(jsonRequest)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();

        Assertions.assertEquals(0, questionRepository.count());
        Assertions.assertFalse(questionRepository.findByTitle(title).isPresent());

        ProductModel product = productRepository.getById(idProduct);
        Assertions.assertFalse(questionRepository.findByProduct(product).isPresent());

        UserModel customer = userRepository.getById(2L);
        Assertions.assertFalse(questionRepository.findByUser(customer).isPresent());

        QuestionModel question = new QuestionModel(title, product, customer);
        Assertions.assertFalse(questionRepository.findByCreatedAt(question.getCreatedAt()).isPresent());
    }

    // POST Request
    @Test
    @DisplayName("200 OK - Succeed and persist the QUESTION about the product")
    void addQuestionStatus400() throws Exception {
        // Values to Success Test
        long idProduct = 1L;
        String title = "Best video game ever!";
        QuestionRequest request = new QuestionRequest();
        request.setTitle(title);

        String jsonRequest = gson.toJson(request);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/product/" + idProduct + "/add-question")
                .header("Authorization", "Bearer " + customerToken)
                .content(jsonRequest)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        Assertions.assertEquals(1, questionRepository.count());
    }

}