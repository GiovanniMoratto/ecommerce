package br.com.zupacademy.giovannimoratto.ecommerce.add_image;

import br.com.zupacademy.giovannimoratto.ecommerce.add_product.ProductModel;

import javax.persistence.*;

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
    @Column(name = "LINK", nullable = false)
    private String link;
    @ManyToOne
    @JoinColumn(name = "ID_PRODUTO")
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

    public String getLink() {
        return link;
    }

}
