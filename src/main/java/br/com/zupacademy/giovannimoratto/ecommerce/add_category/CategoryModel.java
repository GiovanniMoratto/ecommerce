package br.com.zupacademy.giovannimoratto.ecommerce.add_category;

import javax.persistence.*;

/**
 * @Author giovanni.moratto
 */

@Entity
@Table(name = "tb_categorias")
public class CategoryModel {

    /* Attributes */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "NAME", nullable = false)
    private String name;
    @ManyToOne
    @JoinColumn(name = "ID_PARENT_CATEGORY")
    private CategoryModel parentCategory;

    /* Constructors */
    // Default - JPA
    @Deprecated
    public CategoryModel() {
    }

    // Case 1 - Set CategoryRequest.class values in CategoryModel.class
    public CategoryModel(String requestName, CategoryModel requestParentCategory) {
        this.name = requestName;
        this.parentCategory = requestParentCategory;
    }

    // Case 2 - Set CategoryRequest.class values in CategoryModel.class
    public CategoryModel(String name) {
        this.name = name;
    }

    /* Getters and Setters */
    // Check if the category exists in CategoryControllerTest
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}