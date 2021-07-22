package br.com.zupacademy.giovannimoratto.ecommerce.add_product_question;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author giovanni.moratto
 */

@Repository
public interface QuestionRepository extends JpaRepository <QuestionModel, Long> {
}
