package br.com.zupacademy.giovannimoratto.ecommerce.security.Authentication;

import org.springframework.security.core.AuthenticationException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author giovanni.moratto
 */

public class AuthEntryPoint implements org.springframework.security.web.AuthenticationEntryPoint {

    /* Methods */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

    }

}