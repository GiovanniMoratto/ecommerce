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
    //@NotBlank
    private final String gateway;
    //@NotNull
    //@Min(0)
   // @Max(1)
    private final int status;

    /* Constructors */
    public PaypalRequest(String gateway, int status) {
        this.gateway = gateway;
        this.status = status;
    }

    /* Methods */
    @Override
    public TransactionModel create(PurchaseModel purchase) {
        return new TransactionModel(this.status == 0 ? failed : successful, gateway, purchase);
    }

    public TransactionStatus convert(int paypalStatus) {
        return this.status == 0 ? failed : successful;
    }

}