package br.com.zupacademy.giovannimoratto.ecommerce.security.authentication.token;

/**
 * @Author giovanni.moratto
 */

public class TokenResponse {

    /* Attributes */
    private final String token;
    private final String type;

    /* Constructors */
    public TokenResponse(String token, String type) {
        this.token = token;
        this.type = type;
    }

    /* Getters */
    public String getToken() {
        return token;
    }

    public String getType() {
        return type;
    }

}