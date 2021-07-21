package br.com.zupacademy.giovannimoratto.ecommerce.add_review;

import br.com.zupacademy.giovannimoratto.ecommerce.add_product.ProductRepository;
import br.com.zupacademy.giovannimoratto.ecommerce.add_user.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    private final ReviewRepository repository;

    public ReviewController(ProductRepository productRepository, UserRepository userRepository,
                            ReviewRepository repository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.repository = repository;
    }

    /* Methods */
    // POST Request - Register a Review in a Product
    @PostMapping("/{id}/add-review") // Endpoint
    @Transactional
    public ResponseEntity <?> addNewReview(@PathVariable Long id, @Valid ReviewRequest request,
                                           @AuthenticationPrincipal UserDetails logged) {
        ReviewModel newReview = request.toModel(id, productRepository, logged, userRepository);
        repository.save(newReview);
        return ResponseEntity.ok().build();
    }

}