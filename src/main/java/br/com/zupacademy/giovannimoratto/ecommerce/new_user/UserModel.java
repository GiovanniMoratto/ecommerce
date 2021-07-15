package br.com.zupacademy.giovannimoratto.ecommerce.new_user;

import br.com.zupacademy.giovannimoratto.ecommerce.security.Authentication.AccountProfile;
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
    @ManyToMany(fetch = FetchType.EAGER)
    private final List<AccountProfile> profiles = new ArrayList <AccountProfile>();

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

    /* Methods */
    // implements UserDetails
    @Override
    public Collection <? extends GrantedAuthority> getAuthorities() {
        return profiles;
    }

    @Override
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

    /* Getters and Setters */
    // Check for date Before or equal to Present in UserControllerTest
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

}