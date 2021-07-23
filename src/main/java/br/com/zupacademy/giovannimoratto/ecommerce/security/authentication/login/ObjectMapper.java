package br.com.zupacademy.giovannimoratto.ecommerce.security.authentication.login;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * @Author giovanni.moratto
 */

public interface ObjectMapper {

    /* Methods */
    UserDetails map(Object user);

}