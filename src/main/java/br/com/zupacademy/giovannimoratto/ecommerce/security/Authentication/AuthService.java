package br.com.zupacademy.giovannimoratto.ecommerce.security.Authentication;

import br.com.zupacademy.giovannimoratto.ecommerce.security.Authentication.login.ObjectMapper;
import io.jsonwebtoken.lang.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @Author giovanni.moratto
 */

@Service
public class AuthService implements UserDetailsService {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${security.username-query}")
    private String query;

    /* Methods */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List <?> users = em.createQuery(query).setParameter("username", username).getResultList();
        Assert.isTrue(users.size() <= 1, "[Warning]: Another user with the same " +
                                         "username[" + username + "] is already logged in.");
        if (userNotFound(users)) {
            throw new UsernameNotFoundException("Invalid login or password!");
        }
        return objectMapper.map(users.get(0));
    }

    private boolean userNotFound(List <?> users) {
        return users.isEmpty();
    }

}