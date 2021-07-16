package br.com.zupacademy.giovannimoratto.ecommerce.security.Authentication.token;

import br.com.zupacademy.giovannimoratto.ecommerce.security.Authentication.AuthService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author giovanni.moratto
 */

public class TokenAuth extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final AuthService authService;

    public TokenAuth(TokenService tokenService, AuthService authService) {
        this.tokenService = tokenService;
        this.authService = authService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String token = getTokenRequest(request);
        if (tokenService.isValid(token)) {
            userAuthenticate(token);
        }
        filterChain.doFilter(request, response);
    }

    private String getTokenRequest(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    private void userAuthenticate(String token) {
        String username = tokenService.getUserName(token);
        UserDetails user = authService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user,
                null, user.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

}