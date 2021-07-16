package br.com.zupacademy.giovannimoratto.ecommerce.security.Authentication;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Author giovanni.moratto
 */

@Service
public class TokenService {

    @Value("${ecommerce.jwt.expiration}")
    private Long expirationInMillis;

    @Value("${ecommerce.jwt.secret}")
    private String secret;

    public String build(Authentication authenticate) {
        UserDetails user = (UserDetails) authenticate.getPrincipal();
        final Date now = new Date();
        final Date expiration = new Date(now.getTime() + expirationInMillis);
        return Jwts.builder()
                .setIssuer("API Desafio do Mercado Livre")
                .setSubject(user.getUsername())
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public boolean isValid(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public String getUserName(String token) {
        Claims claims = Jwts.parser().setSigningKey(this.secret)
                .parseClaimsJws(token).getBody();

        return claims.getSubject();
    }

}