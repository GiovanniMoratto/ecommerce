package br.com.zupacademy.giovannimoratto.ecommerce.add_product.product_features;

import br.com.zupacademy.giovannimoratto.ecommerce.add_product.ProductModel;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

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
    @Column(name = "NOME", nullable = false)
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

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof FeatureModel))
            return false;
        FeatureModel that = (FeatureModel) o;
        return name.equals(that.name) && description.equals(that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description);
    }

    /* Getters and Setters */
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}