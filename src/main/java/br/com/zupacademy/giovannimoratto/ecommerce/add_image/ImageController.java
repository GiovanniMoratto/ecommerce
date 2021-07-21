package br.com.zupacademy.giovannimoratto.ecommerce.add_image;

import br.com.zupacademy.giovannimoratto.ecommerce.add_product.ProductModel;
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
public class ImageController {

    private final ProductRepository repository;
    private final UserRepository userRepository;
    private final FakeUploader uploader;

    public ImageController(ProductRepository repository, UserRepository userRepository, FakeUploader uploader) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.uploader = uploader;
    }

    /* Methods */
    // POST Request - Register a Image in a Product
    @PostMapping("/{id}/new-image") // Endpoint
    @Transactional
    public ResponseEntity <?> addNewImage(@PathVariable Long id, @Valid ImageRequest request,
                                          @AuthenticationPrincipal UserDetails logged) {
        ProductModel productWithImages = ImageRepository.uploadImageLinks(
                userRepository, logged, id, repository, request, uploader);
        repository.save(productWithImages);
        return ResponseEntity.ok().build();
    }

}