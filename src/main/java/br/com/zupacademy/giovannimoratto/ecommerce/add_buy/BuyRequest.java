package br.com.zupacademy.giovannimoratto.ecommerce.add_buy;

import br.com.zupacademy.giovannimoratto.ecommerce.add_product.ProductModel;
import br.com.zupacademy.giovannimoratto.ecommerce.validations.annotations.ExistsId;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * @Author giovanni.moratto
 */

public class BuyRequest {

    /* Attributes */
    @Positive
    @NotNull
    private final Integer quantity;
    @NotNull
    @ExistsId(domainClass = ProductModel.class)
    private final Long idProduct;
    @NotNull
    private final Gateway gateway;

    /* Constructor */
    public BuyRequest(Integer quantity, Long idProduct, Gateway gateway) {
        this.quantity = quantity;
        this.idProduct = idProduct;
        this.gateway = gateway;
    }

    /* Getters */
    public Integer getQuantity() {
        return quantity;
    }

    public Long getIdProduct() {
        return idProduct;
    }

    public Gateway getGateway() {
        return gateway;
    }

}