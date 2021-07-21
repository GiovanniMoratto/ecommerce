package br.com.zupacademy.giovannimoratto.ecommerce.add_review;

import br.com.zupacademy.giovannimoratto.ecommerce.add_product.ProductModel;
import br.com.zupacademy.giovannimoratto.ecommerce.add_user.UserModel;

import javax.persistence.*;

/**
 * @Author giovanni.moratto
 */

@Entity
@Table(name = "tb_opinioes")
public class ReviewModel {

    /* Attributes */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "NOTA", nullable = false)
    private Integer rating;
    @Column(name = "TITULO", nullable = false)
    private String title;
    @Column(name = "DESCRICAO", nullable = false)
    private String description;
    @ManyToOne
    @JoinColumn(name = "ID_PRODUCT")
    private ProductModel product;
    @ManyToOne
    @JoinColumn(name = "ID_USUARIO")
    private UserModel user;

    /* Constructors */
    // Default - JPA
    @Deprecated
    public ReviewModel() {
    }

    // Set ReviewRequest.class values in ReviewModel.class
    public ReviewModel(Integer rating, String title, String description, ProductModel product, UserModel user) {
        this.rating = rating;
        this.title = title;
        this.description = description;
        this.product = product;
        this.user = user;
    }

    public Integer getRating() {
        return rating;
    }

}