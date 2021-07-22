package br.com.zupacademy.giovannimoratto.ecommerce.add_product_images;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @Author giovanni.moratto
 */

@Repository
public interface ImageRepository extends JpaRepository <ImageModel, Long> {

    /* Methods */
    Optional <ImageModel> findByLink(String link1);

}