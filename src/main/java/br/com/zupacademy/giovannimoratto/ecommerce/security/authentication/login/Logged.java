package br.com.zupacademy.giovannimoratto.ecommerce.security.authentication.login;

import br.com.zupacademy.giovannimoratto.ecommerce.users.UserModel;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * @Author giovanni.moratto
 */

public class Logged implements UserDetails {

    /* Attributes */
    private final UserModel user;
    private final User userDetails;

    /* Constructors */
    public Logged(UserModel userModel) {
        this.user = userModel;
        this.userDetails = new User(userModel.getLogin(), userModel.getPassword(), List.of());
    }

    /* Methods */
    @Override
    public Collection <GrantedAuthority> getAuthorities() {
        return userDetails.getAuthorities();
    }

    @Override
    public String getPassword() {
        return userDetails.getPassword();
    }

    @Override
    public String getUsername() {
        return userDetails.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return userDetails.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return userDetails.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return userDetails.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return userDetails.isEnabled();
    }

    /* Getters */
    public UserModel getUser() {
        return user;
    }

}