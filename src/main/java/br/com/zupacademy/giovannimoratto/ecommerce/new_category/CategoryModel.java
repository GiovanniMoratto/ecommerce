package br.com.zupacademy.giovannimoratto.ecommerce.new_category;

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

    public CategoryModel(String name, CategoryModel parentCategory) {
        this.name = name;
        this.parentCategory = parentCategory;
    }

    public CategoryModel(String name) {
        this.name = name;
    }

    // Check if the category exists in CategoryControllerTest
    public Long getId() {
        return id;
    }

}