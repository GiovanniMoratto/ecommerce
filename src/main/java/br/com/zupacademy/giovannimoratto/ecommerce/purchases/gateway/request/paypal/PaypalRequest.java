package br.com.zupacademy.giovannimoratto.ecommerce.purchases.gateway.request.paypal;

import br.com.zupacademy.giovannimoratto.ecommerce.purchases.costumer.PurchaseModel;
import br.com.zupacademy.giovannimoratto.ecommerce.purchases.gateway.TransactionModel;
import br.com.zupacademy.giovannimoratto.ecommerce.purchases.gateway.request.GatewayResponse;
import br.com.zupacademy.giovannimoratto.ecommerce.purchases.gateway.request.TransactionStatus;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static br.com.zupacademy.giovannimoratto.ecommerce.purchases.gateway.request.TransactionStatus.failed;
import static br.com.zupacademy.giovannimoratto.ecommerce.purchases.gateway.request.TransactionStatus.successful;

/**
 * @Author giovanni.moratto
 */

public class PaypalRequest implements GatewayResponse {

    /* Attributes */
    @NotBlank
    private final String idTransaction;
    @NotNull
    @Min(0)
    @Max(1)
    private final Integer statusTransaction;

    /* Constructors */
    public PaypalRequest(@NotBlank String idTransaction, @NotNull @Min(0) @Max(1) Integer statusTransaction) {
        this.idTransaction = idTransaction;
        this.statusTransaction = statusTransaction;
    }

    /* Methods */
    // Convert Request in TransactionModel
    @Override
    public TransactionModel create(PurchaseModel purchase) {
        return new TransactionModel(convert(statusTransaction), idTransaction, purchase);
    }

    // Convert PayPal response status to TransactionModel valid value
    public TransactionStatus convert(Integer statusTransaction) {
        return this.statusTransaction == 0 ? failed : successful;
    }

}