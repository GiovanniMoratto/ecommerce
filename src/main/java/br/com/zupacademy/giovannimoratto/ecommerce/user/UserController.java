package br.com.zupacademy.giovannimoratto.ecommerce.user;

import br.com.zupacademy.giovannimoratto.ecommerce.security.Authentication.TokenResponse;
import br.com.zupacademy.giovannimoratto.ecommerce.security.Authentication.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;

/**
 * @Author giovanni.moratto
 */

@RestController
public class UserController {

    private final UserRepository repository;
    private final AuthenticationManager authManager;
    private final TokenService tokenService;

    @Autowired
    public UserController(UserRepository repository, AuthenticationManager authManager, TokenService tokenService) {
        this.repository = repository;
        this.authManager = authManager;
        this.tokenService = tokenService;
    }

    /* Methods */
    // POST Request - Register a new User
    @PostMapping("/new_user") // Endpoint
    @Transactional
    public ResponseEntity<?> addNewUser(@RequestBody @Valid UserCreateRequest request, UriComponentsBuilder uriBuilder) {
        UserModel newUser = request.toModel();
        repository.save(newUser);

        URI uri = uriBuilder.path("/user/{id}").buildAndExpand(newUser.getId()).toUri();
        return ResponseEntity.created(uri).body(new UserCreateResponse(newUser));
    }

    // POST Request - Authenticate User
    @PostMapping("/auth")
    @Transactional
    public ResponseEntity <TokenResponse> authenticate(@RequestBody @Valid UserLoginRequest request) {
        UsernamePasswordAuthenticationToken userData = request.convert();
        try {
            Authentication authenticate = authManager.authenticate(userData);
            String token = tokenService.build(authenticate);
            return ResponseEntity.ok(new TokenResponse(token, "Bearer"));
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().build();
        }
    }

}