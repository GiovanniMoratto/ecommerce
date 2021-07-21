package br.com.zupacademy.giovannimoratto.ecommerce.add_image;

import br.com.zupacademy.giovannimoratto.ecommerce.add_product.ProductModel;
import br.com.zupacademy.giovannimoratto.ecommerce.add_product.ProductRepository;
import br.com.zupacademy.giovannimoratto.ecommerce.add_user.UserRepository;
import br.com.zupacademy.giovannimoratto.ecommerce.validations.exception_handler.SearchException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * @Author giovanni.moratto
 */

@Repository
public interface ImageRepository extends JpaRepository <ImageModel, Long> {

    static ProductModel uploadImageLinks(UserRepository userRepository, UserDetails logged, Long id,
                                         ProductRepository productRepository, ImageRequest request,
                                         FakeUploader fakeUploader) {

        Long userLoggedId = getUserId(userRepository, logged);
        Long userCreatorId = getProductCreatorId(id, productRepository);
        checkIfIsTheSame(userCreatorId, userLoggedId);
        Set <String> imageLinks = uploadImages(request, fakeUploader);
        ProductModel product = getProduct(id, productRepository);
        product.addImages(imageLinks);
        return product;
    }

    static Long getUserId(UserRepository userRepository, UserDetails logged) {
        return userRepository.getByLogin(logged.getUsername()).getId();
    }

    static Long getProductCreatorId(Long id, ProductRepository productRepository) {
        return productRepository.findById(id).get().getUserCreator().getId();
    }

    static void checkIfIsTheSame(Long userCreator, Long userLogged) throws SearchException {
        if (userCreator.equals(userLogged))
            return;
        throw new SearchException("User not allowed!");
    }

    static Set <String> uploadImages(ImageRequest request, FakeUploader fakeUploader) {
        return fakeUploader.sendToCloudAndReturnLinks(request.getImages());
    }

    static ProductModel getProduct(Long id, ProductRepository productRepository) {
        return productRepository.findById(id).get();
    }

}
