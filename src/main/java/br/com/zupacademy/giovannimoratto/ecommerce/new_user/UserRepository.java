package br.com.zupacademy.giovannimoratto.ecommerce.new_user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @Author giovanni.moratto
 */

@Repository
public interface UserRepository extends JpaRepository <UserModel, Long> {

    /* Methods */
    // Encode the password from UserModel.class
    static String userPasswordEncoder(String requestPassword) {
        return BCrypt.hashpw(requestPassword, BCrypt.gensalt());
    }

    Optional <UserModel> findByLogin(String login);

    int countByLogin(String login);
}
