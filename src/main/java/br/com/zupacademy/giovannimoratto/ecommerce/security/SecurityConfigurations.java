package br.com.zupacademy.giovannimoratto.ecommerce.security;

import br.com.zupacademy.giovannimoratto.ecommerce.security.Authentication.AuthService;
import br.com.zupacademy.giovannimoratto.ecommerce.security.Authentication.token.TokenAuth;
import br.com.zupacademy.giovannimoratto.ecommerce.security.Authentication.token.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author giovanni.moratto
 */

@EnableWebSecurity
@Configuration
public class SecurityConfigurations extends WebSecurityConfigurerAdapter {

    private static final Logger Log = LoggerFactory.getLogger(SecurityConfigurations.class);
    private final AuthService authService;
    private final TokenService tokenService;

    @Autowired
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
    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    // Settings of static resources (JS - CSS - Images - Videos - etc)
    @Override
    public void configure(WebSecurity web) throws Exception {

    }

    // Authorization Settings - Resource Accesses and Access Profiles
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/new_user").permitAll()
                //.antMatchers(HttpMethod.POST, "/new_category").permitAll()
                .antMatchers(HttpMethod.POST, "/auth").permitAll()
                .anyRequest().authenticated()
                .and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().addFilterBefore(
                new TokenAuth(tokenService, authService),
                UsernamePasswordAuthenticationFilter.class
                                      )
                .exceptionHandling()
                .authenticationEntryPoint(new AuthEntryPoint());
    }

    private static class AuthEntryPoint implements AuthenticationEntryPoint {

        private static final Logger logger = LoggerFactory.getLogger(AuthEntryPoint.class);

        @Override
        public void commence(HttpServletRequest request, HttpServletResponse response,
                             AuthenticationException authException) throws IOException, ServletException {

            logger.error("Unauthorized access. Message: {}", authException.getMessage());
            response.sendError(
                    HttpServletResponse.SC_UNAUTHORIZED,
                    "You are not authorized to access this feature."
                              );
        }
    }

}