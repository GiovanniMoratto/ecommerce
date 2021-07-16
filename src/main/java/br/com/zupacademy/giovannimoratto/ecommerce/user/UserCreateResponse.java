package br.com.zupacademy.giovannimoratto.ecommerce.user;

import java.time.LocalDateTime;

/**
 * @Author giovanni.moratto
 */
public class UserCreateResponse {

    private final String username;
    private final LocalDateTime createdAt;

    public UserCreateResponse(UserModel newUser) {
        this.username = newUser.getUsername();
        this.createdAt = newUser.getCreatedAt();
    }

    public String getUsername() {
        return username;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

}