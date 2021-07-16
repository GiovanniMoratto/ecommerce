package br.com.zupacademy.giovannimoratto.ecommerce.user;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @Author giovanni.moratto
 */

@Entity
@Table(name = "tb_usuarios")
public class UserModel implements UserDetails {

    private static final Long serialVersionUID = 1L;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "tb_usuario-perfil")
    private final List <UserProfile> profiles = new ArrayList <>();
    /* Attributes */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "LOGIN", nullable = false)
    private String login;
    @Column(name = "SENHA", nullable = false)
    private String password;
    @CreationTimestamp
    @PastOrPresent
    @Column(name = "DATA_CRIACAO", nullable = false)
    private LocalDateTime createdAt;

    /* Constructors */
    // Default - JPA
    @Deprecated
    public UserModel() {
    }

    // Set UserRequest.class values in UserModel.class
    public UserModel(String requestLogin, String requestPassword) {
        this.login = requestLogin;
        this.password = UserRepository.userPasswordEncoder(requestPassword);
    }

    // implements UserDetails
    @Override
    public Collection <? extends GrantedAuthority> getAuthorities() {
        return profiles;
    }

    // Check for Hash in UserControllerTest
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    // Check for date Before or equal to Present in UserControllerTest
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Long getId() {
        return id;
    }
}