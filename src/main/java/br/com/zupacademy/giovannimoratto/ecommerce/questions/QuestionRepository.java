package br.com.zupacademy.giovannimoratto.ecommerce.questions;

import br.com.zupacademy.giovannimoratto.ecommerce.products.ProductModel;
import br.com.zupacademy.giovannimoratto.ecommerce.users.UserModel;
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
