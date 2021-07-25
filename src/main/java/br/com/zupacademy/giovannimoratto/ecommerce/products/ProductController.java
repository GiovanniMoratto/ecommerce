package br.com.zupacademy.giovannimoratto.ecommerce.products;

import br.com.zupacademy.giovannimoratto.ecommerce.categories.CategoryRepository;
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
public class ProductController {

    /* Dependencies Injection */
    @Autowired
    private ProductRepository repository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private UserRepository userRepository;

    /* Methods */
    // POST Request - Register a new Product
    @PostMapping("/new-product") // Endpoint
    @Transactional
    public ResponseEntity <?> addNewProduct(@RequestBody @Valid ProductRequest request,
                                            @AuthenticationPrincipal UserDetails userLogged) {
        ProductModel newProduct = request.toModel(categoryRepository, userRepository, userLogged);
        repository.save(newProduct);
        return ResponseEntity.ok().build();
    }

    // GET Request - Get details for a Product
    @GetMapping("/product/{id}")
    public ResponseEntity <ProductDetails> getProductDetails(@PathVariable Long id) {

        ProductModel product = repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "This Product does not exist"));

        return ResponseEntity.ok(new ProductDetails(product));
    }

}