package br.com.zupacademy.giovannimoratto.ecommerce.add_product;

import br.com.zupacademy.giovannimoratto.ecommerce.add_category.CategoryRequest;
import br.com.zupacademy.giovannimoratto.ecommerce.add_product.product_features.FeatureRequest;
import br.com.zupacademy.giovannimoratto.ecommerce.add_user.UserRequest;
import br.com.zupacademy.giovannimoratto.ecommerce.security.Authentication.login.LoginRequest;
import br.com.zupacademy.giovannimoratto.ecommerce.security.Authentication.token.TokenService;
import com.google.gson.Gson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static java.math.BigDecimal.valueOf;

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
    private final ProductRepository repository;
    private String token;

    @Autowired
    ProductControllerTest(MockMvc mockMvc, Gson gson, AuthenticationManager authManager, TokenService tokenService,
                          ProductRepository repository) {
        this.mockMvc = mockMvc;
        this.gson = gson;
        this.authManager = authManager;
        this.tokenService = tokenService;
        this.repository = repository;
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

    // POST Request
    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("400 Bad Request - When trying to POST with empty NAME")
    void nameEmptyStatus400(String name) throws Exception {
        // Values to Fail Test
        FeatureRequest feature1 = new FeatureRequest("Test", "Test");
        FeatureRequest feature2 = new FeatureRequest("Test", "Test");
        FeatureRequest feature3 = new FeatureRequest("Test", "Test");
        List <FeatureRequest> features = new ArrayList <>();
        features.add(feature1);
        features.add(feature2);
        features.add(feature3);
        ProductRequest request = new ProductRequest(
                name,
                valueOf(499.99),
                150,
                "The PlayStation 5 (PS5) is a home video game console developed by Sony " +
                "Interactive Entertainment",
                1L,
                features
        );

        String jsonRequest = gson.toJson(request);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(urlTemplate)
                .header("Authorization", "Bearer " + token)
                .content(jsonRequest)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();
        Assertions.assertTrue(repository.findByName(name).isEmpty());
    }

    // POST Request
    @ParameterizedTest
    @NullSource
    @DisplayName("400 Bad Request - When trying to POST with null PRICE")
    void priceNullStatus400(BigDecimal price) throws Exception {
        // Values to Fail Test
        FeatureRequest feature1 = new FeatureRequest("Test", "Test");
        FeatureRequest feature2 = new FeatureRequest("Test", "Test");
        FeatureRequest feature3 = new FeatureRequest("Test", "Test");
        List <FeatureRequest> features = new ArrayList <>();
        features.add(feature1);
        features.add(feature2);
        features.add(feature3);
        ProductRequest request = new ProductRequest(
                "PS5 FAIL",
                price,
                150,
                "The PlayStation 5 (PS5) is a home video game console developed by Sony " +
                "Interactive Entertainment",
                1L,
                features
        );

        String jsonRequest = gson.toJson(request);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(urlTemplate)
                .header("Authorization", "Bearer " + token)
                .content(jsonRequest)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();
        Assertions.assertTrue(repository.findByName("PS5 FAIL").isEmpty());
    }

    // POST Request
    @Test
    @DisplayName("400 Bad Request - When trying to POST with PRICE less than 0")
    void priceLowStatus400() throws Exception {
        // Values to Success Test
        FeatureRequest feature1 = new FeatureRequest("Test", "Test");
        FeatureRequest feature2 = new FeatureRequest("Test", "Test");
        FeatureRequest feature3 = new FeatureRequest("Test", "Test");
        List <FeatureRequest> features = new ArrayList <>();
        features.add(feature1);
        features.add(feature2);
        features.add(feature3);
        ProductRequest request = new ProductRequest(
                "PS5 FAIL",
                valueOf(-499.99),
                150,
                "The PlayStation 5 (PS5) is a home video game console developed by Sony " +
                "Interactive Entertainment",
                1L,
                features
        );

        String jsonRequest = gson.toJson(request);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(urlTemplate)
                .header("Authorization", "Bearer " + token)
                .content(jsonRequest)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();
        Assertions.assertTrue(repository.findByName("PS5 FAIL").isEmpty());
    }

    // POST Request
    @ParameterizedTest
    @NullSource
    @ValueSource(ints = {-1})
    @DisplayName("400 Bad Request - When trying to POST with null Available Quantity")
    void availableQuantityNullStatus400(Integer availableQuantity) throws Exception {
        // Values to Fail Test
        FeatureRequest feature1 = new FeatureRequest("Test", "Test");
        FeatureRequest feature2 = new FeatureRequest("Test", "Test");
        FeatureRequest feature3 = new FeatureRequest("Test", "Test");
        List <FeatureRequest> features = new ArrayList <>();
        features.add(feature1);
        features.add(feature2);
        features.add(feature3);
        ProductRequest request = new ProductRequest(
                "PS5 FAIL",
                valueOf(499.99),
                availableQuantity,
                "The PlayStation 5 (PS5) is a home video game console developed by Sony " +
                "Interactive Entertainment",
                1L,
                features
        );

        String jsonRequest = gson.toJson(request);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(urlTemplate)
                .header("Authorization", "Bearer " + token)
                .content(jsonRequest)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();
        Assertions.assertTrue(repository.findByName("PS5 FAIL").isEmpty());
    }

    // POST Request
    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("400 Bad Request - When trying to POST with null Available Quantity")
    void availableQuantityNullStatus400(String description) throws Exception {
        // Values to Fail Test
        FeatureRequest feature1 = new FeatureRequest("Test", "Test");
        FeatureRequest feature2 = new FeatureRequest("Test", "Test");
        FeatureRequest feature3 = new FeatureRequest("Test", "Test");
        List <FeatureRequest> features = new ArrayList <>();
        features.add(feature1);
        features.add(feature2);
        features.add(feature3);
        ProductRequest request = new ProductRequest(
                "PS5 FAIL",
                valueOf(499.99),
                150,
                description,
                1L,
                features
        );

        String jsonRequest = gson.toJson(request);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(urlTemplate)
                .header("Authorization", "Bearer " + token)
                .content(jsonRequest)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();
        Assertions.assertTrue(repository.findByName("PS5 FAIL").isEmpty());
    }

    // POST Request
    @Test
    @DisplayName("400 Bad Request - When trying to POST with Available Quantity bigger than 1000 characters")
    void availableQuantityBiggerStatus400() throws Exception {
        // Values to Fail Test
        FeatureRequest feature1 = new FeatureRequest("Test", "Test");
        FeatureRequest feature2 = new FeatureRequest("Test", "Test");
        FeatureRequest feature3 = new FeatureRequest("Test", "Test");
        List <FeatureRequest> features = new ArrayList <>();
        features.add(feature1);
        features.add(feature2);
        features.add(feature3);
        ProductRequest request = new ProductRequest(
                "PS5 FAIL",
                valueOf(499.99),
                150,
                "a".repeat(1001),
                1L,
                features
        );

        String jsonRequest = gson.toJson(request);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(urlTemplate)
                .header("Authorization", "Bearer " + token)
                .content(jsonRequest)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();
        Assertions.assertTrue(repository.findByName("PS5 FAIL").isEmpty());
    }

    // POST Request
    @ParameterizedTest
    @NullSource
    @ValueSource(longs = {2L, 3L, 99L})
    @DisplayName("400 Bad Request - When trying to POST with invalid CATEGORY")
    void categoryInvalidStatus400(Long idCategory) throws Exception {
        // Values to Fail Test
        FeatureRequest feature1 = new FeatureRequest("Test", "Test");
        FeatureRequest feature2 = new FeatureRequest("Test", "Test");
        FeatureRequest feature3 = new FeatureRequest("Test", "Test");
        List <FeatureRequest> features = new ArrayList <>();
        features.add(feature1);
        features.add(feature2);
        features.add(feature3);
        ProductRequest request = new ProductRequest(
                "PS5 FAIL",
                valueOf(499.99),
                150,
                "The PlayStation 5 (PS5) is a home video game console developed by Sony " +
                "Interactive Entertainment",
                idCategory,
                features
        );

        String jsonRequest = gson.toJson(request);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(urlTemplate)
                .header("Authorization", "Bearer " + token)
                .content(jsonRequest)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();
        Assertions.assertTrue(repository.findByName("PS5 FAIL").isEmpty());
    }

    @ParameterizedTest
    @EmptySource
    @DisplayName("400 Bad Request - When trying to POST with empty Features")
    void featuresNullStatus400(List <FeatureRequest> features) throws Exception {
        // Values to Fail Test
        ProductRequest request = new ProductRequest(
                "PS5 FAIL",
                valueOf(499.99),
                150,
                "The PlayStation 5 (PS5) is a home video game console developed by Sony " +
                "Interactive Entertainment",
                1L,
                features
        );

        String jsonRequest = gson.toJson(request);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(urlTemplate)
                .header("Authorization", "Bearer " + token)
                .content(jsonRequest)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();
        Assertions.assertTrue(repository.findByName("PS5 FAIL").isEmpty());
    }

    @Test
    @DisplayName("400 Bad Request - When trying to POST with less than 3 Features")
    void featuresLessThan3Status400() throws Exception {
        // Values to Fail Test
        FeatureRequest feature1 = new FeatureRequest("Test", "Test");
        FeatureRequest feature2 = new FeatureRequest("Test", "Test");
        List <FeatureRequest> features = new ArrayList <>();
        features.add(feature1);
        features.add(feature2);
        ProductRequest request = new ProductRequest(
                "PS5 FAIL",
                valueOf(499.99),
                150,
                "The PlayStation 5 (PS5) is a home video game console developed by Sony " +
                "Interactive Entertainment",
                1L,
                features
        );

        String jsonRequest = gson.toJson(request);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(urlTemplate)
                .header("Authorization", "Bearer " + token)
                .content(jsonRequest)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();
        Assertions.assertTrue(repository.findByName("PS5 FAIL").isEmpty());
    }

    // POST Request
    @Test
    @DisplayName("200 OK - Succeed and persist the Product")
    void createNewProductStatus200() throws Exception {
        // Values to Success Test
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

        String jsonRequest = gson.toJson(request);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(urlTemplate)
                .header("Authorization", "Bearer " + token)
                .content(jsonRequest)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        Assertions.assertTrue(repository.findByName("PS5").isPresent());
    }

}