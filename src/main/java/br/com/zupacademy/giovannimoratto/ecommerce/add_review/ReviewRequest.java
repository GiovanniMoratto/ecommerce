package br.com.zupacademy.giovannimoratto.ecommerce.add_review;

import br.com.zupacademy.giovannimoratto.ecommerce.add_product.ProductModel;
import br.com.zupacademy.giovannimoratto.ecommerce.add_product.ProductRepository;
import br.com.zupacademy.giovannimoratto.ecommerce.add_user.UserModel;
import br.com.zupacademy.giovannimoratto.ecommerce.add_user.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.constraints.*;

/**
 * @Author giovanni.moratto
 */
public class ReviewRequest {

    /* Attributes */
    @NotNull
    @Min(1)
    @Max(5)
    private final Integer rating;
    @NotBlank
    private final String title;
    @NotBlank
    @Size(max = 500)
    private final String description;

    /* Constructors */
    public ReviewRequest(Integer rating, String title, String description) {
        this.rating = rating;
        this.title = title;
        this.description = description;
    }

    /* Methods */
    // Convert ReviewRequest.class in ReviewModel.class
    public ReviewModel toModel(Long id, ProductRepository productRepository, UserDetails logged,
                               UserRepository userRepository) {

        ProductModel product = productRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "This ID Product does not exist"));

        UserModel user = userRepository.findByLogin(logged.getUsername()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.FORBIDDEN, "User not allowed!"));

        return new ReviewModel(rating, title, description, product, user);
    }

}