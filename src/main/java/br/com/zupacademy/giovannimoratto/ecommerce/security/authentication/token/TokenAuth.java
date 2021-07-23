package br.com.zupacademy.giovannimoratto.ecommerce.security.authentication.token;

import br.com.zupacademy.giovannimoratto.ecommerce.security.authentication.AuthService;
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

    /* Attributes */
    private final TokenService tokenService;
    private final AuthService authService;

    /* Constructors */
    public TokenAuth(TokenService tokenService, AuthService authService) {
        this.tokenService = tokenService;
        this.authService = authService;
    }

    /* Methods */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String token = getTokenRequest(request);
        if (tokenService.isValid(token)) {
            userAuthenticate(token);
        }
        filterChain.doFilter(request, response);
    }

    // Get token from the logged in user header.
    private String getTokenRequest(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
            return null;
        }
        return token.substring(7);
    }

    // Authenticate token by getting username and checking the database.
    private void userAuthenticate(String token) {
        String username = tokenService.getUserName(token);
        UserDetails user = authService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user,
                null, user.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

}