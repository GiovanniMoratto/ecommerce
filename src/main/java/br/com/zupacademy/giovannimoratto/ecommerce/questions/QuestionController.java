package br.com.zupacademy.giovannimoratto.ecommerce.questions;

import br.com.zupacademy.giovannimoratto.ecommerce.features.email.Email;
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
@RequestMapping("/api/product")
public class QuestionController {

    /* Dependencies Injection */
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private Email email;

    /* Methods */
    // POST Request - Register a Question in a Product
    @PostMapping("/{id}/add-question") // Endpoint
    @Transactional
    public ResponseEntity <?> addNewQuestion(@PathVariable Long id, @AuthenticationPrincipal UserDetails logged,
                                             @RequestBody @Valid QuestionRequest request) {

        ProductModel product = productRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "This Product does not exist"));

        UserModel user = userRepository.findByLogin(logged.getUsername()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.FORBIDDEN, "User not allowed!"));

        QuestionModel newQuestion = request.toModel(product, user);
        questionRepository.save(newQuestion);
        email.question(newQuestion);
        return ResponseEntity.ok().build();
    }

}
