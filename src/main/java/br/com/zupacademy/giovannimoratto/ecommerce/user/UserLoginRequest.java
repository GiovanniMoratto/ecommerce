package br.com.zupacademy.giovannimoratto.ecommerce.user;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @Author giovanni.moratto
 */

public class UserLoginRequest {

    /* Attributes */
    @NotBlank
    @Email
    private final String login;
    @NotBlank
    @Size(min = 6)
    private final String password;

    public UserLoginRequest(String login, String password) {
        this.login = login;
        this.password = password;
    }

    // Convert UserRequest.class in UsernamePasswordAuthenticationToken
    public UsernamePasswordAuthenticationToken convert() {
        return new UsernamePasswordAuthenticationToken(login, password);
    }

}
