package br.com.zupacademy.giovannimoratto.ecommerce.purchases.gateway.actions;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author giovanni.moratto
 */

public class InvoiceRequest {

    /* Attributes */
    @NotNull
    private Long idPurchase;
    @NotBlank
    private String product;

    /* Getters */
    public Long getIdPurchase() {
        return idPurchase;
    }

    public String getProduct() {
        return product;
    }
}
