package br.com.zupacademy.giovannimoratto.ecommerce.purchase_response.paypal;

import br.com.zupacademy.giovannimoratto.ecommerce.purchase_request.PurchaseModel;
import br.com.zupacademy.giovannimoratto.ecommerce.purchase.BankTransactionModel;
import br.com.zupacademy.giovannimoratto.ecommerce.purchase_response.GatewayResponse;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author giovanni.moratto
 */

public class PaypalRequest implements GatewayResponse {

    /* Attributes */
    @NotBlank
    private String id;
    @NotNull
    private PaypalStatusResponse status;

    /* Constructors */
    public PaypalRequest(String id, PaypalStatusResponse status) {
        this.id = id;
        this.status = status;
    }

    @Override
    public BankTransactionModel create(PurchaseModel purchase) {
        return new BankTransactionModel(status.filter(), id, purchase);
    }

}
