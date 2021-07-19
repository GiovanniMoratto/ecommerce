package br.com.zupacademy.giovannimoratto.ecommerce.add_product.product_features;

import br.com.zupacademy.giovannimoratto.ecommerce.add_product.ProductModel;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @Author giovanni.moratto
 */

@Entity
@Table(name = "tb_caracteristicas")
public class FeatureModel {

    /* Attributes */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "NAME", nullable = false)
    private String name;
    @Column(name = "DESCRICAO", nullable = false)
    private String description;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "PRODUTO", nullable = false)
    private ProductModel product;

    /* Constructors */
    // Default - JPA
    @Deprecated
    public FeatureModel() {
    }

    // Set FeatureRequest.class values in FeatureModel.class
    public FeatureModel(String name, String description, ProductModel product) {
        this.name = name;
        this.description = description;
        this.product = product;
    }

}