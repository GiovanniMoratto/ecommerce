package br.com.zupacademy.giovannimoratto.ecommerce.purchases.costumer;

import br.com.zupacademy.giovannimoratto.ecommerce.products.ProductModel;
import br.com.zupacademy.giovannimoratto.ecommerce.validations.annotations.ExistsId;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * @Author giovanni.moratto
 */

public class CostumerRequest {

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
    public CostumerRequest(Integer quantity, Long idProduct, Gateway gateway) {
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