package br.com.zupacademy.giovannimoratto.ecommerce.categories;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * @Author giovanni.moratto
 */

@Entity
@Table(name = "tb_categorias")
public class CategoryModel {

    /* Attributes */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "nome", nullable = false, unique = true)
    private String name;
    @ManyToOne
    @JoinColumn(name = "id_categoria_mae")
    private CategoryModel parentCategory;

    /* Constructors */
    // Default - JPA
    @Deprecated
    public CategoryModel() {
    }

    // Case 1 - Set CategoryRequest.class values in CategoryModel.class
    public CategoryModel(@NotBlank String name, @Valid CategoryModel parentCategory) {
        this.name = name;
        this.parentCategory = parentCategory;
    }

    // Case 2 - Set CategoryRequest.class values in CategoryModel.class
    public CategoryModel(@NotBlank String name) {
        this.name = name;
    }

    /* Getters */
    // Check if the category exists in CategoryControllerTest
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}