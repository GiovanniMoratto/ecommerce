package br.com.zupacademy.giovannimoratto.ecommerce.security;

import br.com.zupacademy.giovannimoratto.ecommerce.security.Authentication.AuthenticationService;
import br.com.zupacademy.giovannimoratto.ecommerce.security.Authentication.TokenAuthentication;
import br.com.zupacademy.giovannimoratto.ecommerce.security.Authentication.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @Author giovanni.moratto
 */

@EnableWebSecurity
@Configuration
public class SecurityConfigurations extends WebSecurityConfigurerAdapter {
    // Herança para sobreescrever métodos

    private final AuthenticationService authService;
    private final TokenService tokenService;

    @Autowired
    public SecurityConfigurations(AuthenticationService authService, TokenService tokenService) {
        this.authService = authService;
        this.tokenService = tokenService;
    }

    // Configurações de Autenticação - Login
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(authService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    // Configuração de recursos estáticos (JS - CSS - Imagens - Videos - etc)
    @Override
    public void configure(WebSecurity web) throws Exception {

    }

    // Configurações de Autorização - Acessos a recursos e perfis de acesso
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/new_user").permitAll()
                //.antMatchers(HttpMethod.POST, "/new_category").permitAll()
                .antMatchers(HttpMethod.POST, "/auth").permitAll()
                .anyRequest().authenticated()
                .and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().addFilterBefore(new TokenAuthentication(tokenService, authService),
                UsernamePasswordAuthenticationFilter.class);
    }

}