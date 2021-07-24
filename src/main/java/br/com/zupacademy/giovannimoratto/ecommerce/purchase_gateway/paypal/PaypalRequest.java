package br.com.zupacademy.giovannimoratto.ecommerce.purchase_gateway.paypal;

import br.com.zupacademy.giovannimoratto.ecommerce.purchase_costumer.PurchaseModel;
import br.com.zupacademy.giovannimoratto.ecommerce.purchase_gateway.TransactionModel;
import br.com.zupacademy.giovannimoratto.ecommerce.purchase_gateway.GatewayResponse;
import br.com.zupacademy.giovannimoratto.ecommerce.purchase_gateway.TransactionStatus;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static br.com.zupacademy.giovannimoratto.ecommerce.purchase_gateway.TransactionStatus.failed;
import static br.com.zupacademy.giovannimoratto.ecommerce.purchase_gateway.TransactionStatus.successful;

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
    private final Integer paypalStatus;

    /* Constructors */
    public PaypalRequest(String idTransaction, Integer paypalStatus) {
        this.idTransaction = idTransaction;
        this.paypalStatus = paypalStatus;
    }

    /* Methods */
    @Override
    public TransactionModel create(PurchaseModel purchase) {
        return new TransactionModel(convert(paypalStatus), idTransaction, purchase);
    }

    public TransactionStatus convert(Integer paypalStatus) {
        return this.paypalStatus == 0 ? failed : successful;
    }

}