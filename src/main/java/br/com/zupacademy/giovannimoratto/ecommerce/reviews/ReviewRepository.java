package br.com.zupacademy.giovannimoratto.ecommerce.reviews;

import br.com.zupacademy.giovannimoratto.ecommerce.products.ProductModel;
import br.com.zupacademy.giovannimoratto.ecommerce.users.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @Author giovanni.moratto
 */

@Repository
public interface ReviewRepository extends JpaRepository <ReviewModel, Long> {

    Optional <ReviewModel> findByTitle(String title);

    Optional <ReviewModel> findByProduct(ProductModel product);

    Optional <ReviewModel> findByUser(UserModel user);

}
