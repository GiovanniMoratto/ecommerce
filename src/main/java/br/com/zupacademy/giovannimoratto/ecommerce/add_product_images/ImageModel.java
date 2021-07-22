package br.com.zupacademy.giovannimoratto.ecommerce.add_product_images;

import br.com.zupacademy.giovannimoratto.ecommerce.add_product.ProductModel;
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
    @Column(name = "ID")
    private Long id;
    @NotBlank
    @URL
    @Column(name = "LINK", nullable = false)
    private String link;
    @NotNull
    @Valid
    @ManyToOne(optional = false)
    @JoinColumn(name = "ID_PRODUTO", nullable = false)
    private ProductModel product;

    /* Constructors */
    // Default - JPA
    @Deprecated
    public ImageModel() {
    }

    public ImageModel(String link, ProductModel product) {
        this.link = link;
        this.product = product;
    }

}