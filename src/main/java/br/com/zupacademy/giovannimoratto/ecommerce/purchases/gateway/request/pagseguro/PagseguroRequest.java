package br.com.zupacademy.giovannimoratto.ecommerce.purchases.gateway.request.pagseguro;

import br.com.zupacademy.giovannimoratto.ecommerce.purchases.costumer.PurchaseModel;
import br.com.zupacademy.giovannimoratto.ecommerce.purchases.gateway.TransactionModel;
import br.com.zupacademy.giovannimoratto.ecommerce.purchases.gateway.request.GatewayResponse;
import br.com.zupacademy.giovannimoratto.ecommerce.purchases.gateway.request.TransactionStatus;

import javax.validation.constraints.NotBlank;

import static br.com.zupacademy.giovannimoratto.ecommerce.purchases.gateway.request.TransactionStatus.failed;
import static br.com.zupacademy.giovannimoratto.ecommerce.purchases.gateway.request.TransactionStatus.successful;
import static br.com.zupacademy.giovannimoratto.ecommerce.purchases.gateway.request.pagseguro.PagseguroStatus.SUCESSO;

/**
 * @Author giovanni.moratto
 */

public class PagseguroRequest implements GatewayResponse {

    /* Attributes */
    @NotBlank
    private final String idTransaction;
    @NotBlank
    private final PagseguroStatus statusTransaction;

    /* Constructors */
    public PagseguroRequest(@NotBlank String idTransaction, @NotBlank PagseguroStatus statusTransaction) {
        this.idTransaction = idTransaction;
        this.statusTransaction = statusTransaction;
    }

    /* Methods */
    // Convert Request in TransactionModel
    @Override
    public TransactionModel create(PurchaseModel purchase) {
        return new TransactionModel(convert(statusTransaction), idTransaction, purchase);
    }

    // Convert PagSeguro response status to TransactionModel valid value
    public TransactionStatus convert(PagseguroStatus statusTransaction) {
        return this.statusTransaction.equals(SUCESSO) ? successful : failed;
    }

}