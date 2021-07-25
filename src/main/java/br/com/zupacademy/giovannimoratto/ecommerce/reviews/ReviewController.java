package br.com.zupacademy.giovannimoratto.ecommerce.reviews;

import br.com.zupacademy.giovannimoratto.ecommerce.products.ProductModel;
import br.com.zupacademy.giovannimoratto.ecommerce.products.ProductRepository;
import br.com.zupacademy.giovannimoratto.ecommerce.users.UserModel;
import br.com.zupacademy.giovannimoratto.ecommerce.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/api")
public class ReviewController {

    /* Dependencies Injection */
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ReviewRepository reviewRepository;

    /* Methods */
    // POST Request - Register a Review in a Product
    @PostMapping("/product/{id}/add-review") // Endpoint
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