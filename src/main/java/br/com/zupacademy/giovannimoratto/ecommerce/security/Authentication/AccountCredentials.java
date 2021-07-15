package br.com.zupacademy.giovannimoratto.ecommerce.security.Authentication;

/**
 * @Author giovanni.moratto
 */

public class AccountCredentials {

    /* Attributes */
    private String username;
    private String password;

    public AccountCredentials(String username, String password) {
        this.username = username;
        this.password = password;
    }
    @Deprecated
    private AccountCredentials(){
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}