package br.com.zupacademy.giovannimoratto.ecommerce.add_product_review;

import br.com.zupacademy.giovannimoratto.ecommerce.add_category.CategoryRequest;
import br.com.zupacademy.giovannimoratto.ecommerce.add_product.ProductModel;
import br.com.zupacademy.giovannimoratto.ecommerce.add_product.ProductRepository;
import br.com.zupacademy.giovannimoratto.ecommerce.add_product.ProductRequest;
import br.com.zupacademy.giovannimoratto.ecommerce.add_product.product_features.FeatureRequest;
import br.com.zupacademy.giovannimoratto.ecommerce.add_user.UserModel;
import br.com.zupacademy.giovannimoratto.ecommerce.add_user.UserRepository;
import br.com.zupacademy.giovannimoratto.ecommerce.add_user.UserRequest;
import br.com.zupacademy.giovannimoratto.ecommerce.security.authentication.login.LoginRequest;
import br.com.zupacademy.giovannimoratto.ecommerce.security.authentication.token.TokenService;
import com.google.gson.Gson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
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

import java.util.ArrayList;
import java.util.List;

import static java.math.BigDecimal.valueOf;

/**
 * @Author giovanni.moratto
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class ReviewControllerTest {

    private final MockMvc mockMvc;
    private final Gson gson;
    private final TokenService tokenService;
    private final AuthenticationManager authManager;
    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private String token;

    @Autowired
    ReviewControllerTest(MockMvc mockMvc, Gson gson, TokenService tokenService, AuthenticationManager authManager,
                         ReviewRepository reviewRepository, ProductRepository productRepository,
                         UserRepository userRepository) {
        this.mockMvc = mockMvc;
        this.gson = gson;
        this.tokenService = tokenService;
        this.authManager = authManager;
        this.reviewRepository = reviewRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @BeforeEach
    void setUpInitial() throws Exception {
        String login = "test@email.com";
        String password = "123456";
        // Create User
        String userRequest = gson.toJson(new UserRequest(login, password));
        mockMvc.perform(MockMvcRequestBuilders.post("/api/new-user")
                .content(userRequest)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON));
        // Create Category
        String categoryRequest = gson.toJson(new CategoryRequest("test", null));
        mockMvc.perform(MockMvcRequestBuilders.post("/api/new-category")
                .content(categoryRequest)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON));
        // Log User
        LoginRequest loginRequest = new LoginRequest(login, password);
        token = tokenService.buildToken(authManager.authenticate(loginRequest.convert()));
        // Create a Product
        FeatureRequest feature1 = new FeatureRequest("Test", "Test");
        FeatureRequest feature2 = new FeatureRequest("Test", "Test");
        FeatureRequest feature3 = new FeatureRequest("Test", "Test");
        List <FeatureRequest> features = new ArrayList <>();
        features.add(feature1);
        features.add(feature2);
        features.add(feature3);
        ProductRequest request = new ProductRequest(
                "PS5",
                valueOf(499.99),
                150,
                "The PlayStation 5 (PS5) is a home video game console developed by Sony " +
                "Interactive Entertainment",
                1L,
                features
        );
        String productRequest = gson.toJson(request);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/new-product")
                .header("Authorization", "Bearer " + token)
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
        mockMvc.perform(MockMvcRequestBuilders.post("/api/product/" + idProduct + "/add-review")
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
        long idProduct = 1L;
        mockMvc.perform(MockMvcRequestBuilders.post("/api/product/" + idProduct + "/add-review")
                .header("Authorization", "Bearer " + token)
                .content("{ }")
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    // POST Request
    @ParameterizedTest
    @NullSource
    @ValueSource(ints = {-1, 0, 6})
    @DisplayName("400 Bad Request - When trying to POST with an invalid LIKE")
    void likeInvalidStatus400(Integer likes) throws Exception {
        // Values to Success Test
        long idProduct = 1L;
        String title = "Good";
        String comment = "Nice";
        ProductModel product = productRepository.getById(1L);
        UserModel user = userRepository.getById(1L);

        String jsonRequest = gson.toJson(new ReviewRequest(likes, title, comment));
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/product/" + idProduct + "/add-review")
                .header("Authorization", "Bearer " + token)
                .content(jsonRequest)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();
        Assertions.assertEquals(0, reviewRepository.count());
        Assertions.assertTrue(reviewRepository.findByTitle(title).isEmpty());
        Assertions.assertTrue(reviewRepository.findByProduct(product).isEmpty());
        Assertions.assertTrue(reviewRepository.findByUser(user).isEmpty());
    }

    // POST Request
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "\n", "\t"})
    @DisplayName("400 Bad Request - When trying to POST with an invalid TITLE")
    void titleInvalidStatus400(String title) throws Exception {
        // Values to Success Test
        long idProduct = 1L;
        Integer likes = 5;
        String comment = "Nice";
        ProductModel product = productRepository.getById(1L);
        UserModel user = userRepository.getById(1L);

        String jsonRequest = gson.toJson(new ReviewRequest(likes, title, comment));
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/product/" + idProduct + "/add-review")
                .header("Authorization", "Bearer " + token)
                .content(jsonRequest)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();
        Assertions.assertEquals(0, reviewRepository.count());
        Assertions.assertTrue(reviewRepository.findByTitle(title).isEmpty());
        Assertions.assertTrue(reviewRepository.findByProduct(product).isEmpty());
        Assertions.assertTrue(reviewRepository.findByUser(user).isEmpty());
    }

    // POST Request
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "\n", "\t"})
    @DisplayName("400 Bad Request - When trying to POST with an empty COMMENT")
    void commentEmptyStatus400(String comment) throws Exception {
        // Values to Success Test
        long idProduct = 1L;
        Integer likes = 5;
        String title = "Good";
        ProductModel product = productRepository.getById(1L);
        UserModel user = userRepository.getById(1L);

        String jsonRequest = gson.toJson(new ReviewRequest(likes, title, comment));
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/product/" + idProduct + "/add-review")
                .header("Authorization", "Bearer " + token)
                .content(jsonRequest)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();
        Assertions.assertEquals(0, reviewRepository.count());
        Assertions.assertTrue(reviewRepository.findByTitle(title).isEmpty());
        Assertions.assertTrue(reviewRepository.findByProduct(product).isEmpty());
        Assertions.assertTrue(reviewRepository.findByUser(user).isEmpty());
    }

    // POST Request
    @Test
    @DisplayName("400 Bad Request - When trying to POST with a COMMENT bigger than 500 characters")
    void commentBiggerThan500Status400() throws Exception {
        // Values to Success Test
        long idProduct = 1L;
        Integer likes = 5;
        String title = "Good";
        String comment = "a".repeat(501);
        ProductModel product = productRepository.getById(1L);
        UserModel user = userRepository.getById(1L);

        String jsonRequest = gson.toJson(new ReviewRequest(likes, title, comment));
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/product/" + idProduct + "/add-review")
                .header("Authorization", "Bearer " + token)
                .content(jsonRequest)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();
        Assertions.assertEquals(0, reviewRepository.count());
        Assertions.assertTrue(reviewRepository.findByTitle(title).isEmpty());
        Assertions.assertTrue(reviewRepository.findByProduct(product).isEmpty());
        Assertions.assertTrue(reviewRepository.findByUser(user).isEmpty());
    }

    // POST Request
    @Test
    @DisplayName("200 OK - Succeed and persist the review about the product")
    void addReviewStatus400() throws Exception {
        // Values to Success Test
        long idProduct = 1L;
        Integer likes = 5;
        String title = "Good";
        String comment = "Nice";
        ProductModel product = productRepository.getById(1L);
        UserModel user = userRepository.getById(1L);

        String jsonRequest = gson.toJson(new ReviewRequest(likes, title, comment));
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/product/" + idProduct + "/add-review")
                .header("Authorization", "Bearer " + token)
                .content(jsonRequest)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        Assertions.assertEquals(1L, reviewRepository.count());
        Assertions.assertTrue(reviewRepository.findByTitle(title).isPresent());
        Assertions.assertTrue(reviewRepository.findByProduct(product).isPresent());
        Assertions.assertTrue(reviewRepository.findByUser(user).isPresent());
    }

}
