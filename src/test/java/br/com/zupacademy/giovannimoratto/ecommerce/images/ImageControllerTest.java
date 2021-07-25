package br.com.zupacademy.giovannimoratto.ecommerce.images;

import br.com.zupacademy.giovannimoratto.ecommerce.categories.CategoryRepository;
import br.com.zupacademy.giovannimoratto.ecommerce.categories.CategoryRequest;
import br.com.zupacademy.giovannimoratto.ecommerce.products.ProductRepository;
import br.com.zupacademy.giovannimoratto.ecommerce.products.ProductRequest;
import br.com.zupacademy.giovannimoratto.ecommerce.products.product_features.FeatureRequest;
import br.com.zupacademy.giovannimoratto.ecommerce.security.authentication.login.LoginRequest;
import br.com.zupacademy.giovannimoratto.ecommerce.security.authentication.token.TokenService;
import br.com.zupacademy.giovannimoratto.ecommerce.users.UserRepository;
import br.com.zupacademy.giovannimoratto.ecommerce.users.UserRequest;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Optional;

import static java.math.BigDecimal.valueOf;

/**
 * @Author giovanni.moratto
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class ImageControllerTest {

    private final MockMvc mockMvc;
    private final Gson gson;
    private final TokenService tokenService;
    private final AuthenticationManager authManager;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final ImageRepository imageRepository;
    private String token1;
    private String token2;

    @Autowired
    ImageControllerTest(MockMvc mockMvc, Gson gson, TokenService tokenService,
                        AuthenticationManager authManager,
                        UserRepository userRepository, CategoryRepository categoryRepository,
                        ProductRepository productRepository, ImageRepository imageRepository) {
        this.mockMvc = mockMvc;
        this.gson = gson;
        this.tokenService = tokenService;
        this.authManager = authManager;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.imageRepository = imageRepository;
    }

    @BeforeEach
    void setUpInitial() throws Exception {
        String login = "test@email.com";
        String password = "123456";
        // Create User
        String userRequest1 = gson.toJson(new UserRequest(login, password));
        mockMvc.perform(MockMvcRequestBuilders.post("/api/new-user")
                .content(userRequest1)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON));

        String userRequest2 = gson.toJson(new UserRequest("test2@email.com", password));
        mockMvc.perform(MockMvcRequestBuilders.post("/api/new-user")
                .content(userRequest2)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON));

        // Create Category
        String jsonRequest2 = gson.toJson(new CategoryRequest("test", null));
        mockMvc.perform(MockMvcRequestBuilders.post("/api/new-category")
                .content(jsonRequest2)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON));
        // Log User
        LoginRequest loginRequest1 = new LoginRequest(login, password);
        token1 = tokenService.buildToken(authManager.authenticate(loginRequest1.convert()));

        LoginRequest loginRequest2 = new LoginRequest("test2@email.com", password);
        token2 = tokenService.buildToken(authManager.authenticate(loginRequest2.convert()));
        // Create a Product
        ProductRequest productRequestUser1 = new ProductRequest(
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
        String jsonRequestUser1 = gson.toJson(productRequestUser1);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/new-product")
                .header("Authorization", "Bearer " + token1)
                .content(jsonRequestUser1)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON));

        ProductRequest productRequestUser2 = new ProductRequest(
                "XBOX",
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
        String jsonRequestUser2 = gson.toJson(productRequestUser2);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/new-product")
                .header("Authorization", "Bearer " + token2)
                .content(jsonRequestUser2)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON));
    }

    @Before
    public void setUp() {
        userRepository.deleteAll();
        categoryRepository.deleteAll();
        productRepository.deleteAll();
        imageRepository.deleteAll();
    }

    // POST Request
    @Test
    @DisplayName("400 Bad Request - When trying to POST with empty BODY")
    void emptyBodyStatus400() throws Exception {
        // Values to Fail Test
        long id = 1L;
        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/product/" + id + "/add-images")
                .header("Authorization", "Bearer " + token1))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    // POST Request
    @Test
    @DisplayName("400 Bad Request - When trying to POST with empty Object")
    void emptyObjectStatus400() throws Exception {
        // Values to Fail Test
        long id = 1L;
        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/product/" + id + "/add-images")
                .file(new MockMultipartFile("{ }", getClass().getResourceAsStream("{ }")))
                .header("Authorization", "Bearer " + token1))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    // POST Request
    @Test
    @DisplayName("403 FORBIDDEN - User not log in the app")
    void userNotLoggedStatus403() throws Exception {
        // Values to Success Test
        long id = 1L;
        String urlTemplate = "/api/product/" + id + "/add-images";
        String imageRequestAttribute = "images";
        String fileName = "casper.jpg";

        // Generate Image files
        MockMultipartFile file = new MockMultipartFile(
                imageRequestAttribute, fileName, "multipart/form-data",
                getClass().getResourceAsStream(fileName));

        mockMvc.perform(MockMvcRequestBuilders.multipart(urlTemplate)
                .file(file))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
        Assertions.assertEquals(0, imageRepository.count());
    }

    // POST Request
    @Test
    @DisplayName("403 FORBIDDEN - User with invalid token")
    void invalidTokenStatus403() throws Exception {
        // Values to Success Test
        long id = 1L;
        String urlTemplate = "/api/product/" + id + "/add-images";
        String imageRequestAttribute = "images";
        String fileName = "casper.jpg";

        // Generate Image files
        MockMultipartFile file = new MockMultipartFile(
                imageRequestAttribute, fileName, "multipart/form-data",
                getClass().getResourceAsStream(fileName));

        mockMvc.perform(MockMvcRequestBuilders.multipart(urlTemplate)
                .file(file)
                .header("Authorization", "Invalid"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
        Assertions.assertEquals(0, imageRepository.count());
    }

    // POST Request
    @Test
    @DisplayName("403 FORBIDDEN - User does not have permission to add an image to another user's product")
    void userNotAllowedStatus403() throws Exception {
        // Values to Success Test
        long id = 1L;
        String urlTemplate = "/api/product/" + id + "/add-images";
        String imageRequestAttribute = "images";
        String fileName = "casper.jpg";

        // Generate Image files
        MockMultipartFile file = new MockMultipartFile(
                imageRequestAttribute, fileName, "multipart/form-data",
                getClass().getResourceAsStream(fileName));

        mockMvc.perform(MockMvcRequestBuilders.multipart(urlTemplate)
                .file(file)
                .header("Authorization", "Bearer " + token2))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
        Assertions.assertEquals(0, imageRepository.count());
    }

    // POST Request
    @Test
    @DisplayName("200 OK - Succeed and update the Product with images")
    void updateProductWithImagesStatus200() throws Exception {
        // Values to Success Test
        long id = 1L;
        String urlTemplate = "/api/product/" + id + "/add-images";
        String imageRequestAttribute = "images";
        String url = "http://ecommerce/files.com/";
        String fileName1 = "casper.jpg";
        String fileName2 = "cat.jpg";
        String link1 = url + fileName1;
        String link2 = url + fileName2;

        // Generate Image files
        MockMultipartFile file1 = new MockMultipartFile(
                imageRequestAttribute, fileName1, "multipart/form-data",
                getClass().getResourceAsStream(fileName1));

        MockMultipartFile file2 = new MockMultipartFile(
                imageRequestAttribute, fileName2, "multipart/form-data",
                getClass().getResourceAsStream(fileName2));

        mockMvc.perform(MockMvcRequestBuilders.multipart(urlTemplate)
                .file(file1)
                .file(file2)
                .header("Authorization", "Bearer " + token1))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Assertions.assertEquals(2L, imageRepository.count());
        Optional <ImageModel> optionalLink1 = imageRepository.findByLink(link1);
        Assertions.assertTrue(optionalLink1.isPresent());
        Optional <ImageModel> optionalLink2 = imageRepository.findByLink(link2);
        Assertions.assertTrue(optionalLink2.isPresent());
    }

}