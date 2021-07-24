package br.com.zupacademy.giovannimoratto.ecommerce.purchase_request;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author giovanni.moratto
 */

@Repository
public interface PurchaseRepository extends JpaRepository <PurchaseModel, Long> {

}
