package br.com.zupacademy.giovannimoratto.ecommerce.security.Authentication;

import br.com.zupacademy.giovannimoratto.ecommerce.add_user.UserModel;
import br.com.zupacademy.giovannimoratto.ecommerce.add_user.UserRepository;
import br.com.zupacademy.giovannimoratto.ecommerce.security.Authentication.login.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @Author giovanni.moratto
 */

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional <UserModel> optionalLogin = repository.findByLogin(username);
        System.out.println(optionalLogin);
        if (optionalLogin.isPresent()) {
            return objectMapper.map(optionalLogin.get());
        }
        throw new UsernameNotFoundException("Dados inválidos!");
    }

}