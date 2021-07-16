package br.com.zupacademy.giovannimoratto.ecommerce.security.Authentication.login;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * @Author giovanni.moratto
 */

public interface ObjectMapper {

    /* Methods */
    UserDetails map(Object user);

}