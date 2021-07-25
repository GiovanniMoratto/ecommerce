package br.com.zupacademy.giovannimoratto.ecommerce.questions;

import br.com.zupacademy.giovannimoratto.ecommerce.products.ProductModel;
import br.com.zupacademy.giovannimoratto.ecommerce.users.UserModel;
import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;

/**
 * @Author giovanni.moratto
 */

@Entity
@Table(name = "tb_perguntas")
public class QuestionModel implements Comparable <QuestionModel> {

    /* Attributes */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "titulo", nullable = false)
    private String title;
    @CreationTimestamp
    @PastOrPresent
    @Column(name = "data_criacao", nullable = false)
    private LocalDateTime createdAt;
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
    public QuestionModel() {
    }

    // Set QuestionRequest.class values in QuestionModel.class
    public QuestionModel(@NotBlank String title, @Valid ProductModel product, @Valid UserModel user) {
        this.title = title;
        this.product = product;
        this.user = user;
    }

    /* Methods */
    @Override
    public int compareTo(QuestionModel o) {
        return this.title.compareTo(o.title);
    }

    /* Getters */
    public UserModel getUser() {
        return user;
    }

    public ProductModel getProduct() {
        return product;
    }

    public String getTitle() {
        return title;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

}