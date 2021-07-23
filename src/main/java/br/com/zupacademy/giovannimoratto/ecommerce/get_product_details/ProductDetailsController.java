package br.com.zupacademy.giovannimoratto.ecommerce.get_product_details;

import br.com.zupacademy.giovannimoratto.ecommerce.add_product.ProductModel;
import br.com.zupacademy.giovannimoratto.ecommerce.add_product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.Optional;

/**
 * @Author giovanni.moratto
 */

@RestController
@RequestMapping("/api/product")
public class ProductDetailsController {

    private final ProductRepository productRepository;

    @Autowired
    public ProductDetailsController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/{id}")
    @Transactional
    public ResponseEntity <ProductDetailsResponse> getProductDetails(@PathVariable Long id) {

        Optional <ProductModel> product = productRepository.findById(id);
        return product.map(
                productModel -> ResponseEntity.ok(new ProductDetailsResponse(productModel)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}