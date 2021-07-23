package br.com.zupacademy.giovannimoratto.ecommerce.add_product_question;

import br.com.zupacademy.giovannimoratto.ecommerce.add_product.ProductModel;
import br.com.zupacademy.giovannimoratto.ecommerce.add_user.UserModel;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
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
    @Column(name = "ID")
    private Long id;
    @Column(name = "TITULO", nullable = false)
    private String title;
    @CreationTimestamp
    @PastOrPresent
    @Column(name = "DATA_CRIACAO", nullable = false)
    private LocalDateTime createdAt;
    @ManyToOne
    @JoinColumn(name = "ID_PRODUTO")
    private ProductModel product;
    @ManyToOne
    @JoinColumn(name = "ID_USUARIO")
    private UserModel user;

    /* Constructors */
    // Default - JPA
    @Deprecated
    public QuestionModel() {
    }

    // Set QuestionRequest.class values in QuestionModel.class
    public QuestionModel(String title, ProductModel product, UserModel user) {
        this.title = title;
        this.product = product;
        this.user = user;
    }

    @Override
    public int compareTo(QuestionModel o) {
        return this.title.compareTo(o.title);
    }

    /* Getters and Setters */
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