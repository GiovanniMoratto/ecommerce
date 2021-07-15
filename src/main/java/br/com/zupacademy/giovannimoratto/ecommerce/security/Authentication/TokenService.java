package br.com.zupacademy.giovannimoratto.ecommerce.security.Authentication;

import br.com.zupacademy.giovannimoratto.ecommerce.new_user.UserModel;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 * @Author giovanni.moratto
 */

@Service
public class TokenService {
    public String build(Authentication authenticate) {
        UserDetails user = (UserModel) authenticate.getPrincipal();
        return user.getUsername();
    }
}
