package br.com.zupacademy.giovannimoratto.ecommerce.user;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

/**
 * @Author giovanni.moratto
 */

@Entity
@Table(name = "tb_perfis_acesso")
public class UserProfile implements GrantedAuthority {

    private static final Long serialVersionUID = 1L;

    /* Attributes */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @NotBlank
    @Column(name = "NAME", nullable = false)
    private String name;

    /* Constructors */
    // Default - JPA
    @Deprecated
    public UserProfile() {
    }

    public UserProfile(String name) {
        this.name = name;
    }

    /* Methods */
    @Override
    public String getAuthority() {
        return name;
    }

}