package br.com.zupacademy.giovannimoratto.ecommerce.images;

import br.com.zupacademy.giovannimoratto.ecommerce.products.ProductModel;
import br.com.zupacademy.giovannimoratto.ecommerce.validations.annotations.ExistsId;
import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author giovanni.moratto
 */

@Entity
@Table(name = "tb_imagens")
public class ImageModel {

    /* Attributes */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @URL
    @NotBlank
    @Column(name = "link", nullable = false)
    private String link;
    @Valid
    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_produto", nullable = false)
    @JsonBackReference
    private ProductModel product;

    /* Constructors */
    // Default - JPA
    @Deprecated
    public ImageModel() {
    }

    public ImageModel(@NotBlank @URL String link, @NotNull @Valid ProductModel product) {
        this.link = link;
        this.product = product;
    }

    /* Getters and Setters */
    public String getLink() {
        return link;
    }

}