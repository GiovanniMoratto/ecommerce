package br.com.zupacademy.giovannimoratto.ecommerce.security;

import br.com.zupacademy.giovannimoratto.ecommerce.security.authentication.AuthService;
import br.com.zupacademy.giovannimoratto.ecommerce.security.authentication.token.TokenAuth;
import br.com.zupacademy.giovannimoratto.ecommerce.security.authentication.token.TokenService;
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

@Configuration
@EnableWebSecurity
public class SecurityConfigurations extends WebSecurityConfigurerAdapter {

    private final AuthService authService;
    private final TokenService tokenService;

    public SecurityConfigurations(AuthService authService, TokenService tokenService) {
        this.authService = authService;
        this.tokenService = tokenService;
    }

    /* Methods */
    // Authentication Settings - Login
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(authService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    // Settings of static resources (JS - CSS - Images - Videos - etc)
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(
                "/**.html", "/v2/api-docs", "/webjars/**", "/configuration/**", "/swagger-resources/**");}

    // Authorization Settings - Resource Accesses and Access Profiles
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/new-user").permitAll()
                .antMatchers(HttpMethod.POST, "/api/new-category").permitAll()
                .antMatchers(HttpMethod.POST, "/api/auth").permitAll()
                .antMatchers(HttpMethod.GET, "/api/product/*").permitAll()
                .anyRequest().authenticated()
                .and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().addFilterBefore(
                new TokenAuth(tokenService, authService),
                UsernamePasswordAuthenticationFilter.class);
    }

}