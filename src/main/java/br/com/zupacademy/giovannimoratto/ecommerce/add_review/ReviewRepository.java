package br.com.zupacademy.giovannimoratto.ecommerce.add_review;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author giovanni.moratto
 */

@Repository
public interface ReviewRepository extends JpaRepository <ReviewModel, Long> {

}
