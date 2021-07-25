package br.com.zupacademy.giovannimoratto.ecommerce.purchases.gateway.actions;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author giovanni.moratto
 */

public class SellersRakingRequest {

    /* Attributes */
    @NotNull
    private Long id;
    @NotBlank
    private String seller;

    /* Getters */
    public Long getId() {
        return id;
    }

    public String getSeller() {
        return seller;
    }
}
