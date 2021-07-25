package br.com.zupacademy.giovannimoratto.ecommerce.purchases;

import br.com.zupacademy.giovannimoratto.ecommerce.categories.CategoryRequest;
import br.com.zupacademy.giovannimoratto.ecommerce.products.ProductRequest;
import br.com.zupacademy.giovannimoratto.ecommerce.products.product_features.FeatureRequest;
import br.com.zupacademy.giovannimoratto.ecommerce.purchases.costumer.CostumerRequest;
import br.com.zupacademy.giovannimoratto.ecommerce.purchases.costumer.Gateway;
import br.com.zupacademy.giovannimoratto.ecommerce.purchases.costumer.PurchaseRepository;
import br.com.zupacademy.giovannimoratto.ecommerce.purchases.gateway.request.pagseguro.PagseguroRequest;
import br.com.zupacademy.giovannimoratto.ecommerce.purchases.gateway.request.pagseguro.PagseguroStatus;
import br.com.zupacademy.giovannimoratto.ecommerce.purchases.gateway.request.paypal.PaypalRequest;
import br.com.zupacademy.giovannimoratto.ecommerce.security.authentication.login.LoginRequest;
import br.com.zupacademy.giovannimoratto.ecommerce.security.authentication.token.TokenService;
import br.com.zupacademy.giovannimoratto.ecommerce.users.UserRequest;
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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static br.com.zupacademy.giovannimoratto.ecommerce.purchases.gateway.request.pagseguro.PagseguroStatus.SUCESSO;
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
class GatewayControllerTest {

    private final MockMvc mockMvc;
    private final Gson gson;
    private final PurchaseRepository purchaseRepository;
    private final AuthenticationManager authManager;
    private final TokenService tokenService;
    private String token;
    private String pagseguro = "/api/pagseguro.com/1";
    private String paypal = "/api/paypal.com/1";

    @Autowired
    GatewayControllerTest(MockMvc mockMvc, Gson gson, PurchaseRepository purchaseRepository,
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
        // Buy Request for Pagseguro
        mockMvc.perform(post("/api/buy")
                .header("Authorization", "Bearer " + token)
                .content(gson.toJson(new CostumerRequest(1, 1L, Gateway.pagseguro)))
                .characterEncoding("UTF-8")
                .contentType(APPLICATION_JSON));
        // Buy Request for PayPal
        mockMvc.perform(post("/api/buy")
                .header("Authorization", "Bearer " + token)
                .content(gson.toJson(new CostumerRequest(1, 1L, Gateway.paypal)))
                .characterEncoding("UTF-8")
                .contentType(APPLICATION_JSON));
    }

    /* Methods */
    // POST Request
    @Test
    @DisplayName("400 Bad Request - When trying to POST with empty BODY in PagSeguro")
    void pagseguroEmptyBodyStatus400() throws Exception {
        // Values to FAIL Test
        mockMvc.perform(post(pagseguro)
                .header("Authorization", "Bearer " + token)
                .characterEncoding("UTF-8")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    // POST Request
    @Test
    @DisplayName("400 Bad Request - When trying to POST with empty BODY in PayPal")
    void paypalEmptyBodyStatus400() throws Exception {
        // Values to FAIL Test
        mockMvc.perform(post(paypal)
                .header("Authorization", "Bearer " + token)
                .characterEncoding("UTF-8")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    // POST Request
    @Test
    @DisplayName("400 Bad Request - When trying to POST with empty Object in PayPal")
    void paypalEmptyObjectStatus400() throws Exception {
        // Values to FAIL Test
        mockMvc.perform(post(paypal)
                .header("Authorization", "Bearer " + token)
                .content("{ }")
                .characterEncoding("UTF-8")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    // POST Request
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"5", "x"})
    @DisplayName("400 Bad Request - When trying to POST with an invalid PagSeguro ID Transaction")
    void pagseguroIdTransactionInvalidStatus400(String idTransaction) throws Exception {
        // Values to FAIL Test
        PagseguroStatus statusTransaction = SUCESSO;
        mockMvc.perform(post(pagseguro)
                .header("Authorization", "Bearer " + token)
                .content(gson.toJson(new PagseguroRequest(idTransaction, statusTransaction)))
                .characterEncoding("UTF-8")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();
    }

    // POST Request
    @ParameterizedTest
    @NullSource
    @DisplayName("400 Bad Request - When trying to POST with an invalid PagSeguro status Transaction")
    void pagseguroStatusTransactionInvalidStatus400(PagseguroStatus statusTransaction) throws Exception {
        // Values to FAIL Test
        String idTransaction = "1";
        mockMvc.perform(post(pagseguro)
                .header("Authorization", "Bearer " + token)
                .content(gson.toJson(new PagseguroRequest(idTransaction, statusTransaction)))
                .characterEncoding("UTF-8")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();
    }

    // POST Request
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"5", "x"})
    @DisplayName("400 Bad Request - When trying to POST with an invalid PayPal ID Transaction")
    void paypalIdTransactionInvalidStatus400(String idTransaction) throws Exception {
        // Values to FAIL Test
        Integer statusTransaction = 1;
        mockMvc.perform(post(pagseguro)
                .header("Authorization", "Bearer " + token)
                .content(gson.toJson(new PaypalRequest(idTransaction, statusTransaction)))
                .characterEncoding("UTF-8")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();
    }

    // POST Request
    @ParameterizedTest
    @NullSource
    @ValueSource(ints = {-1, 2})
    @DisplayName("400 Bad Request - When trying to POST with an invalid PayPal status Transaction")
    void paypalStatusTransactionInvalidStatus400(Integer statusTransaction) throws Exception {
        // Values to FAIL Test
        String idTransaction = "1";
        mockMvc.perform(post(pagseguro)
                .header("Authorization", "Bearer " + token)
                .content(gson.toJson(new PaypalRequest(idTransaction, statusTransaction)))
                .characterEncoding("UTF-8")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();
    }


}
