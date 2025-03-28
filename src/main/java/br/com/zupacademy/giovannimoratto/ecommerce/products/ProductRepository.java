package br.com.zupacademy.giovannimoratto.ecommerce.products;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @Author giovanni.moratto
 */

@Repository
public interface ProductRepository extends JpaRepository <ProductModel, Long> {

    /* Methods */
    Optional <ProductModel> findByName(String name);

}