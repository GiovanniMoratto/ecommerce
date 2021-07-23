package br.com.zupacademy.giovannimoratto.ecommerce.security.authentication.login;

import br.com.zupacademy.giovannimoratto.ecommerce.add_user.UserModel;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * @Author giovanni.moratto
 */

@Component
public class LoginObjectMapper implements ObjectMapper {

    /* Methods */
    @Override
    public UserDetails map(Object user) {
        return new Logged((UserModel) user);
    }

}