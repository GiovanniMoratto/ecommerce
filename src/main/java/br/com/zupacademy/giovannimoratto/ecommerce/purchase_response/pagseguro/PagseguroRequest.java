package br.com.zupacademy.giovannimoratto.ecommerce.purchase_response.pagseguro;

import br.com.zupacademy.giovannimoratto.ecommerce.purchase_request.PurchaseModel;
import br.com.zupacademy.giovannimoratto.ecommerce.purchase.BankTransactionModel;
import br.com.zupacademy.giovannimoratto.ecommerce.purchase_response.GatewayResponse;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author giovanni.moratto
 */

public class PagseguroRequest implements GatewayResponse {

    /* Attributes */
    @NotBlank
    private String id;
    @NotNull
    private PagseguroStatusResponse status;

    /* Constructors */
    public PagseguroRequest(String id, PagseguroStatusResponse status) {
        this.id = id;
        this.status = status;
    }

    @Override
    public BankTransactionModel create(PurchaseModel purchase) {
        return new BankTransactionModel(status.filter(), id, purchase);
    }

}