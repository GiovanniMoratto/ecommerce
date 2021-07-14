package br.com.zupacademy.giovannimoratto.ecommerce.new_user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @Author giovanni.moratto
 */

public class UserRequest {

    /* Attributes */
    @NotBlank
    @Email
    private final String login;
    @NotBlank
    @Size(min = 6)
    private final String password;

    public UserRequest(@NotBlank @Email String login, @NotBlank @Size(min = 6) String password) {
        this.login = login;
        this.password = password;
    }

    /* Methods */
    // Convert UserRequest.class in UserModel.class
    public UserModel toModel() {
        return new UserModel(login, password);
    }

}