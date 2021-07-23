package br.com.zupacademy.giovannimoratto.ecommerce.add_product_review;

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
    private Integer likes;
    @Column(name = "TITULO", nullable = false)
    private String title;
    @Column(name = "DESCRICAO", nullable = false)
    private String comment;
    @ManyToOne
    @JoinColumn(name = "ID_PRODUTO")
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
    public ReviewModel(Integer likes, String title, String comment, ProductModel product, UserModel user) {
        this.likes = likes;
        this.title = title;
        this.comment = comment;
        this.product = product;
        this.user = user;
    }

    /* Getters and Setters */
    public Integer getLikes() {
        return likes;
    }

    public ProductModel getProduct() {
        return product;
    }

    public UserModel getUser() {
        return user;
    }

    public String getTitle() {
        return title;
    }

    public String getComment() {
        return comment;
    }

}