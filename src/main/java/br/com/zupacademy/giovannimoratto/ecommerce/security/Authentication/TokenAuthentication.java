package br.com.zupacademy.giovannimoratto.ecommerce.security.Authentication;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * @Author giovanni.moratto
 */

public class TokenAuthentication extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final AuthenticationService authService;

    public TokenAuthentication(TokenService tokenService, AuthenticationService authService) {
        this.tokenService = tokenService;
        this.authService = authService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String token = getTokenRequest(request).get();
        if (tokenService.isValid(token)) {
            userAuthenticate(token);
        }
        filterChain.doFilter(request, response);
    }

    private Optional <String> getTokenRequest(HttpServletRequest request) {
        String authToken = request.getHeader("Authorization");
        if (authToken == null || authToken.isEmpty() || !authToken.startsWith("Bearer ")) {
            return Optional.empty();
        }
        return Optional.of(authToken.substring(7, authToken.length()));
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