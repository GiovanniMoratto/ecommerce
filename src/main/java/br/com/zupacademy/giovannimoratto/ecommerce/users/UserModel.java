package br.com.zupacademy.giovannimoratto.ecommerce.users;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * @Author giovanni.moratto
 */

@Entity
@Table(name = "tb_usuarios")
public class UserModel {

    /* Attributes */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "login", nullable = false, unique = true)
    private String login;
    @Column(name = "senha", nullable = false)
    private String password;
    @PastOrPresent
    @CreationTimestamp
    @Column(name = "data_criacao", nullable = false)
    private LocalDateTime createdAt;

    /* Constructors */
    // Default - JPA
    @Deprecated
    public UserModel() {
    }

    // Set UserRequest.class values in UserModel.class
    public UserModel(@NotBlank @Email String login, @NotBlank @Size(min = 6) String password) {
        this.login = login;
        this.password = UserRepository.userPasswordEncoder(password);
    }

    /* Getters */
    // ImageRepository
    public Long getId() {
        return id;
    }

    // Get username to Logged.class
    public String getLogin() {
        return login;
    }

    // Check for Hash in UserControllerTest
    public String getPassword() {
        return password;
    }

    // Check for date Before or equal to Present in UserControllerTest
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

}