package br.com.zupacademy.giovannimoratto.ecommerce.security.Authentication;

/**
 * @Author giovanni.moratto
 */
public class TokenResponse {

    private final String token;
    private final String type;

    public TokenResponse(String token, String type) {
        this.token = token;
        this.type = type;
    }

    public String getToken() {
        return token;
    }

    public String getType() {
        return type;
    }

}