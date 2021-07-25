package br.com.zupacademy.giovannimoratto.ecommerce.reviews;

import br.com.zupacademy.giovannimoratto.ecommerce.products.ProductModel;
import br.com.zupacademy.giovannimoratto.ecommerce.users.UserModel;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * @Author giovanni.moratto
 */

@Entity
@Table(name = "tb_opinioes")
public class ReviewModel {

    /* Attributes */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "nota", nullable = false)
    private Integer likes;
    @Column(name = "titulo", nullable = false)
    private String title;
    @Column(name = "descricao", nullable = false)
    private String comment;
    @ManyToOne
    @JoinColumn(name = "id_produto")
    @JsonBackReference
    private ProductModel product;
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private UserModel user;

    /* Constructors */
    // Default - JPA
    @Deprecated
    public ReviewModel() {
    }

    // Set ReviewRequest.class values in ReviewModel.class
    public ReviewModel(@NotNull @Min(1) @Max(5) Integer likes, @NotBlank String title,
                       @NotBlank @Size(max = 500) String comment, @Valid ProductModel product, @Valid UserModel user) {
        this.likes = likes;
        this.title = title;
        this.comment = comment;
        this.product = product;
        this.user = user;
    }

    /* Getters */
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