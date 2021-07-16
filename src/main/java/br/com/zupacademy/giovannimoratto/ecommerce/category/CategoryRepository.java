package br.com.zupacademy.giovannimoratto.ecommerce.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @Author giovanni.moratto
 */

@Repository
public interface CategoryRepository extends JpaRepository <CategoryModel, Long> {

    Optional <CategoryModel> findByName(String name);

    int countByName(String name);
}
