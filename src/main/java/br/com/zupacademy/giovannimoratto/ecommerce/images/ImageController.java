package br.com.zupacademy.giovannimoratto.ecommerce.images;

import br.com.zupacademy.giovannimoratto.ecommerce.features.uploader.Uploader;
import br.com.zupacademy.giovannimoratto.ecommerce.products.ProductModel;
import br.com.zupacademy.giovannimoratto.ecommerce.products.ProductRepository;
import br.com.zupacademy.giovannimoratto.ecommerce.users.UserModel;
import br.com.zupacademy.giovannimoratto.ecommerce.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Set;

/**
 * @Author giovanni.moratto
 */

@RestController
@RequestMapping("/api")
public class ImageController {

    private final Uploader uploader;
    /* Dependencies Injection */
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;

    public ImageController(Uploader uploader) {
        this.uploader = uploader;
    }

    /* Methods */
    // POST Request - Register a Image in a Product
    @PostMapping("/product/{id}/add-images") // Endpoint
    @Transactional
    public ResponseEntity <?> addNewImage(@PathVariable Long id, @Valid ImageRequest request,
                                          @AuthenticationPrincipal UserDetails logged) {

        ProductModel product = productRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "This Product does not exist"));

        UserModel user = userRepository.findByLogin(logged.getUsername()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.FORBIDDEN, "User not allowed!"));

        if (!product.getSeller().getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User not allowed!");
        }

        Set <String> imageLinks = uploader.upload(request.getImages());
        product.addImages(imageLinks);
        productRepository.save(product);

        return ResponseEntity.ok().build();
    }

}