package br.com.zupacademy.giovannimoratto.ecommerce.security.Authentication;

import br.com.zupacademy.giovannimoratto.ecommerce.user.UserModel;
import br.com.zupacademy.giovannimoratto.ecommerce.user.UserRepository;
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
public class AuthenticationService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional <UserModel> optionalLogin = repository.findByLogin(username);
        if (optionalLogin.isPresent()){
            return optionalLogin.get();
        }
        throw new UsernameNotFoundException("Dados inválidos!");
    }

}