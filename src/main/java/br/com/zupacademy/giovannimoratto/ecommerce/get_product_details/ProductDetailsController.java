package br.com.zupacademy.giovannimoratto.ecommerce.get_product_details;

import br.com.zupacademy.giovannimoratto.ecommerce.add_product.ProductModel;
import br.com.zupacademy.giovannimoratto.ecommerce.add_product.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;

/**
 * @Author giovanni.moratto
 */

@RestController
@RequestMapping("/api/product")
public class ProductDetailsController {

    private final ProductRepository productRepository;

    public ProductDetailsController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/{id}")
    @Transactional
    public ResponseEntity <ProductDetailsResponse> getProductDetails(@PathVariable Long id) {

        ProductModel product = productRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "This Product does not exist"));

        return ResponseEntity.ok(new ProductDetailsResponse(product));
    }

}