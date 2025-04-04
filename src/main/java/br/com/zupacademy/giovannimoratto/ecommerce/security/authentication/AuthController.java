package br.com.zupacademy.giovannimoratto.ecommerce.security.authentication;

import br.com.zupacademy.giovannimoratto.ecommerce.security.authentication.login.LoginRequest;
import br.com.zupacademy.giovannimoratto.ecommerce.security.authentication.token.TokenResponse;
import br.com.zupacademy.giovannimoratto.ecommerce.security.authentication.token.TokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import javax.validation.Valid;

/**
 * @Author giovanni.moratto
 */

@RestController
@RequestMapping("/api")
public class AuthController {

    private final AuthenticationManager authManager;
    private final TokenService tokenService;

    public AuthController(AuthenticationManager authManager, TokenService tokenService) {
        this.authManager = authManager;
        this.tokenService = tokenService;
    }

    /* Methods */
    // POST Request - Authenticate User
    @PostMapping("/auth")
    @Transactional
    public ResponseEntity <TokenResponse> userAuthenticate(@RequestBody @Valid LoginRequest request) {
        UsernamePasswordAuthenticationToken userData = request.convert();
        try {
            Authentication authenticated = authManager.authenticate(userData);
            String token = tokenService.buildToken(authenticated);
            return ResponseEntity.ok(new TokenResponse(token, "Bearer"));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body(
                    new TokenResponse("Invalid login or password!", "ERROR"));
        }
    }

}