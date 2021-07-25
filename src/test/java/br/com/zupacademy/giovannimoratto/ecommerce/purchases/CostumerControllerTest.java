package br.com.zupacademy.giovannimoratto.ecommerce.purchases;

import br.com.zupacademy.giovannimoratto.ecommerce.categories.CategoryRequest;
import br.com.zupacademy.giovannimoratto.ecommerce.products.ProductRequest;
import br.com.zupacademy.giovannimoratto.ecommerce.products.product_features.FeatureRequest;
import br.com.zupacademy.giovannimoratto.ecommerce.purchases.costumer.CostumerRequest;
import br.com.zupacademy.giovannimoratto.ecommerce.purchases.costumer.Gateway;
import br.com.zupacademy.giovannimoratto.ecommerce.purchases.costumer.PurchaseRepository;
import br.com.zupacademy.giovannimoratto.ecommerce.security.authentication.login.LoginRequest;
import br.com.zupacademy.giovannimoratto.ecommerce.security.authentication.token.TokenService;
import br.com.zupacademy.giovannimoratto.ecommerce.users.UserRequest;
import com.google.gson.Gson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static br.com.zupacademy.giovannimoratto.ecommerce.purchases.costumer.Gateway.paypal;
import static java.math.BigDecimal.valueOf;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @Author giovanni.moratto
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class CostumerControllerTest {

    private final MockMvc mockMvc;
    private final Gson gson;
    private final PurchaseRepository purchaseRepository;
    private final AuthenticationManager authManager;
    private final TokenService tokenService;
    private String token;

    @Autowired
    CostumerControllerTest(MockMvc mockMvc, Gson gson, PurchaseRepository purchaseRepository,
                           AuthenticationManager authManager, TokenService tokenService) {
        this.mockMvc = mockMvc;
        this.gson = gson;
        this.purchaseRepository = purchaseRepository;
        this.authManager = authManager;
        this.tokenService = tokenService;
    }

    @BeforeEach
    void setUpInitial() throws Exception {
        purchaseRepository.deleteAll();
        String login = "test@email.com";
        String password = "123456";
        // Create User
        String userRequest = gson.toJson(new UserRequest(login, password));
        mockMvc.perform(post("/api/new-user")
                .content(userRequest)
                .characterEncoding("UTF-8")
                .contentType(APPLICATION_JSON));
        // Create Category
        mockMvc.perform(post("/api/new-category")
                .content(gson.toJson(new CategoryRequest("Games", null)))
                .characterEncoding("UTF-8")
                .contentType(APPLICATION_JSON));
        // Log User
        LoginRequest loginRequest = new LoginRequest(login, password);
        token = tokenService.buildToken(authManager.authenticate(loginRequest.convert()));
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
                                new FeatureRequest("Test", "Test")
                               ))))
                .characterEncoding("UTF-8")
                .contentType(APPLICATION_JSON));
    }

    /* Methods */
    // POST Request
    @Test
    @DisplayName("400 Bad Request - When trying to POST with empty BODY")
    void emptyBodyStatus400() throws Exception {
        // Values to FAIL Test
        mockMvc.perform(post("/api/buy")
                .header("Authorization", "Bearer " + token)
                .characterEncoding("UTF-8")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }


    // POST Request
    @Test
    @DisplayName("400 Bad Request - When trying to POST with empty Object")
    void emptyObjectStatus400() throws Exception {
        // Values to FAIL Test
        mockMvc.perform(post("/api/buy")
                .header("Authorization", "Bearer " + token)
                .content("{ }")
                .characterEncoding("UTF-8")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    // POST Request
    @ParameterizedTest
    @NullSource
    @DisplayName("400 Bad Request - When trying to POST with an invalid QUANTITY")
    void quantityInvalidStatus400(Integer quantity) throws Exception {
        // Values to FAIL Test
        Long idProduct = 1L;
        Gateway gateway = paypal;

        mockMvc.perform(post("/api/buy")
                .header("Authorization", "Bearer " + token)
                .content(gson.toJson(new CostumerRequest(quantity, idProduct, gateway)))
                .characterEncoding("UTF-8")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();
        Assertions.assertEquals(0, purchaseRepository.count());
    }

    // POST Request
    @ParameterizedTest
    @NullSource
    @DisplayName("400 Bad Request - When trying to POST with an invalid PRODUCT ID")
    void idProductInvalidStatus400(Long idProduct) throws Exception {
        // Values to FAIL Test
        Integer quantity = 5;
        Gateway gateway = paypal;

        mockMvc.perform(post("/api/buy")
                .header("Authorization", "Bearer " + token)
                .content(gson.toJson(new CostumerRequest(quantity, idProduct, gateway)))
                .characterEncoding("UTF-8")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();
        Assertions.assertEquals(0, purchaseRepository.count());
    }

    // POST Request
    @ParameterizedTest
    @NullSource
    @DisplayName("400 Bad Request - When trying to POST with an invalid GATEWAY")
    void gatewayInvalidStatus400(Gateway gateway) throws Exception {
        // Values to FAIL Test
        Integer quantity = 5;
        Long idProduct = 1L;

        mockMvc.perform(post("/api/buy")
                .header("Authorization", "Bearer " + token)
                .content(gson.toJson(new CostumerRequest(quantity, idProduct, gateway)))
                .characterEncoding("UTF-8")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();
        Assertions.assertEquals(0, purchaseRepository.count());
    }

    @Test
    @DisplayName("200 OK - Succeed and persist the Purchase about the product")
    void addPurchaseStatus200() throws Exception {
        // Values to Success Test
        Integer quantity = 5;
        Long idProduct = 1L;
        Gateway gateway = paypal;

        mockMvc.perform(post("/api/buy")
                .header("Authorization", "Bearer " + token)
                .content(gson.toJson(new CostumerRequest(quantity, idProduct, gateway)))
                .characterEncoding("UTF-8")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isFound()).andReturn();
        Assertions.assertEquals(1, purchaseRepository.count());
    }


}
