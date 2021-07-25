package br.com.zupacademy.giovannimoratto.ecommerce.products.product_features;

import br.com.zupacademy.giovannimoratto.ecommerce.products.ProductModel;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
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
    @Column(name = "id")
    private Long id;
    @Column(name = "nome", nullable = false)
    private String name;
    @Column(name = "descricao", nullable = false)
    private String description;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "produto", nullable = false)
    @JsonBackReference
    private ProductModel product;

    /* Constructors */
    // Default - JPA
    @Deprecated
    public FeatureModel() {
    }

    // Set FeatureRequest.class values in FeatureModel.class
    public FeatureModel(@NotBlank String name, @NotBlank String description, @NotNull ProductModel product) {
        this.name = name;
        this.description = description;
        this.product = product;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description);
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

    /* Getters */
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

}
