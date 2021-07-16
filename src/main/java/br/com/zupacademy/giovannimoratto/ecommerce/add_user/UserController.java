package br.com.zupacademy.giovannimoratto.ecommerce.add_user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import javax.validation.Valid;

/**
 * @Author giovanni.moratto
 */

@RestController
public class UserController {

    @Autowired
    private UserRepository repository;

    /* Methods */
    // POST Request - Register a new User
    @PostMapping("/new_user") // Endpoint
    @Transactional
    public ResponseEntity <?> addNewUser(@RequestBody @Valid UserRequest request) {
        UserModel newUser = request.toModel();
        repository.save(newUser);
        return ResponseEntity.ok().build();
    }

}