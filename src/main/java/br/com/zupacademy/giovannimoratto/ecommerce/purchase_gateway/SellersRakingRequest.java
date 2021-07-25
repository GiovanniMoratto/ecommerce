package br.com.zupacademy.giovannimoratto.ecommerce.purchase_gateway;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author giovanni.moratto
 */

public class SellersRakingRequest {

    @NotNull
    private Long id;
    @NotBlank
    private String seller;

    public Long getId() {
        return id;
    }

    public String getSeller() {
        return seller;
    }
}
