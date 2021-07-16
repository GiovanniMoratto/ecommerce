package br.com.zupacademy.giovannimoratto.ecommerce.security.Authentication.login;

import br.com.zupacademy.giovannimoratto.ecommerce.add_user.UserModel;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @Author giovanni.moratto
 */

@Configuration
public class LoginObjectMapper implements ObjectMapper {

    @Override
    public UserDetails map(Object user) {
        return new Logged((UserModel) user);
    }

}