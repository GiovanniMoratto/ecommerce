package br.com.zupacademy.giovannimoratto.ecommerce.new_user;

import org.springframework.beans.factory.annotation.Autowired;
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
    // POST Request - Register an author
    @PostMapping("/new_user") // Endpoint
    @Transactional
    public void addNewUser(@RequestBody @Valid UserRequest request) {
        UserModel newUser = request.toModel();
        repository.save(newUser);
    }
}
