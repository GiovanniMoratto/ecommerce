package br.com.zupacademy.giovannimoratto.ecommerce.add_user;

import org.springframework.http.ResponseEntity;
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
public class UserController {

    private final UserRepository repository;

    public UserController(UserRepository repository) {
        this.repository = repository;
    }

    /* Methods */
    // POST Request - Register a new User
    @PostMapping("/new-user") // Endpoint
    @Transactional
    public ResponseEntity <?> addNewUser(@RequestBody @Valid UserRequest request) {
        UserModel newUser = request.toModel();
        repository.save(newUser);
        return ResponseEntity.ok().build();
    }

}