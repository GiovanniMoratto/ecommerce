package br.com.zupacademy.giovannimoratto.ecommerce.add_buy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author giovanni.moratto
 */

@Repository
public interface BuyRepository extends JpaRepository <BuyModel, Long> {

}
