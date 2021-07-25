package br.com.zupacademy.giovannimoratto.ecommerce.purchase_gateway.pagseguro;

import br.com.zupacademy.giovannimoratto.ecommerce.purchase_costumer.PurchaseModel;
import br.com.zupacademy.giovannimoratto.ecommerce.purchase_gateway.TransactionModel;
import br.com.zupacademy.giovannimoratto.ecommerce.purchase_gateway.GatewayResponse;
import br.com.zupacademy.giovannimoratto.ecommerce.purchase_gateway.TransactionStatus;

import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static br.com.zupacademy.giovannimoratto.ecommerce.purchase_gateway.TransactionStatus.failed;
import static br.com.zupacademy.giovannimoratto.ecommerce.purchase_gateway.TransactionStatus.successful;
import static br.com.zupacademy.giovannimoratto.ecommerce.purchase_gateway.pagseguro.PagseguroStatusResponse.SUCESSO;

/**
 * @Author giovanni.moratto
 */

public class PagseguroRequest implements GatewayResponse {

    /* Attributes */
    private final String idTransaction;
    private final PagseguroStatusResponse pagseguroStatus;

    /* Constructors */
    public PagseguroRequest(String idTransaction, PagseguroStatusResponse pagseguroStatus) {
        this.idTransaction = idTransaction;
        this.pagseguroStatus = pagseguroStatus;
    }

    /* Methods */
    @Override
    public TransactionModel create(PurchaseModel purchase) {
        return new TransactionModel(pagseguroStatus.convert(), idTransaction, purchase);
    }
    /*
    public TransactionStatus convert(PagseguroStatusResponse pagseguroStatus) {
        return this.pagseguroStatus.equals(SUCESSO) ? successful : failed;
    }

     */

}