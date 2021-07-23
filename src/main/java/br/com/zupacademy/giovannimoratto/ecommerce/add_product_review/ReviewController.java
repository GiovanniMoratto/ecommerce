package br.com.zupacademy.giovannimoratto.ecommerce.add_product_review;

import br.com.zupacademy.giovannimoratto.ecommerce.add_product.ProductModel;
import br.com.zupacademy.giovannimoratto.ecommerce.add_product.ProductRepository;
import br.com.zupacademy.giovannimoratto.ecommerce.add_user.UserModel;
import br.com.zupacademy.giovannimoratto.ecommerce.add_user.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import javax.validation.Valid;

/**
 * @Author giovanni.moratto
 */

@RestController
@RequestMapping("/api/product")
public class ReviewController {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;

    public ReviewController(ProductRepository productRepository, UserRepository userRepository,
                            ReviewRepository reviewRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.reviewRepository = reviewRepository;
    }

    /* Methods */
    // POST Request - Register a Review in a Product
    @PostMapping("/{id}/add-review") // Endpoint
    @Transactional
    public ResponseEntity <?> addNewReview(@PathVariable Long id, @AuthenticationPrincipal UserDetails logged,
                                           @RequestBody @Valid ReviewRequest request) {

        ProductModel product = productRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "This Product does not exist"));

        UserModel user = userRepository.findByLogin(logged.getUsername()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.FORBIDDEN, "User not allowed!"));

        ReviewModel newReview = request.toModel(product, user);
        reviewRepository.save(newReview);
        return ResponseEntity.ok().build();
    }

}