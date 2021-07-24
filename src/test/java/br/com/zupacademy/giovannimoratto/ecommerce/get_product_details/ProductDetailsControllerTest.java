package br.com.zupacademy.giovannimoratto.ecommerce.get_product_details;

import br.com.zupacademy.giovannimoratto.ecommerce.add_category.CategoryModel;
import br.com.zupacademy.giovannimoratto.ecommerce.add_category.CategoryRepository;
import br.com.zupacademy.giovannimoratto.ecommerce.add_product.ProductModel;
import br.com.zupacademy.giovannimoratto.ecommerce.add_product.ProductRepository;
import br.com.zupacademy.giovannimoratto.ecommerce.add_product.product_features.FeatureRequest;
import br.com.zupacademy.giovannimoratto.ecommerce.add_product_images.ImageModel;
import br.com.zupacademy.giovannimoratto.ecommerce.add_product_images.ImageRepository;
import br.com.zupacademy.giovannimoratto.ecommerce.add_product_question.QuestionModel;
import br.com.zupacademy.giovannimoratto.ecommerce.add_product_question.QuestionRepository;
import br.com.zupacademy.giovannimoratto.ecommerce.add_product_review.ReviewModel;
import br.com.zupacademy.giovannimoratto.ecommerce.add_product_review.ReviewRepository;
import br.com.zupacademy.giovannimoratto.ecommerce.add_user.UserModel;
import br.com.zupacademy.giovannimoratto.ecommerce.add_user.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static java.math.BigDecimal.valueOf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @Author giovanni.moratto
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class ProductDetailsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    /* Methods */
    // GET Request
    @Test
    @DisplayName("404 Not Found - When trying to GET with invalid endpoint")
    void invalidEndpointStatus404() throws Exception {
        mockMvc.perform(get("/api/product/{id}", "1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(""));
    }

    // GET Request
    @Test
    @DisplayName("200 OK - Succeed and return a list with product details")
    void returnProductDetailsStatus200(
            @Autowired UserRepository userRepository,
            @Autowired CategoryRepository categoryRepository,
            @Autowired ProductRepository productRepository,
            @Autowired ImageRepository imageRepository,
            @Autowired ReviewRepository reviewRepository,
            @Autowired QuestionRepository questionRepository
                                      ) throws Exception {

        UserModel user = new UserModel("costumer@email.com", "123456");
        userRepository.save(user);

        CategoryModel category = new CategoryModel("Games");
        categoryRepository.save(category);

        ProductModel product = new ProductModel(
                "PS5",
                valueOf(499.99),
                150,
                "The PlayStation 5 (PS5) is a home video game console developed by Sony " +
                "Interactive Entertainment",
                category,
                user,
                List.of(
                        new FeatureRequest("Test", "Test"),
                        new FeatureRequest("Test", "Test"),
                        new FeatureRequest("Test", "Test")
                       )
        );
        productRepository.save(product);

        ImageModel image = new ImageModel("http://bucket.io/casper.jpg", product);
        imageRepository.save(image);

        ReviewModel review = new ReviewModel(5, "Amazing", "Best Video Game ever!", product, user);
        reviewRepository.save(review);

        QuestionModel question = new QuestionModel("Is it good?", product, user);
        questionRepository.save(question);

        mockMvc.perform(get("/api/product/{id}", product.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("images").isArray())
                .andExpect(jsonPath("name").isString())
                .andExpect(jsonPath("price").isNumber())
                .andExpect(jsonPath("features").isArray())
                .andExpect(jsonPath("description").isString())
                .andExpect(jsonPath("questions").isArray());
    }

}