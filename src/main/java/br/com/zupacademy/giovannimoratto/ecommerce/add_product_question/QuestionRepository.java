package br.com.zupacademy.giovannimoratto.ecommerce.add_product_question;

import br.com.zupacademy.giovannimoratto.ecommerce.add_product.ProductModel;
import br.com.zupacademy.giovannimoratto.ecommerce.add_user.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @Author giovanni.moratto
 */

@Repository
public interface QuestionRepository extends JpaRepository <QuestionModel, Long> {

    Optional <QuestionModel> findByTitle(String title);

    Optional <QuestionModel> findByUser(UserModel customer);

    Optional <QuestionModel> findByProduct(ProductModel product);

    Optional <QuestionModel> findByCreatedAt(LocalDateTime createdAt);
}
