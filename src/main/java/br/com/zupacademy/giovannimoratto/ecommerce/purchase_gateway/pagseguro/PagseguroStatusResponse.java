package br.com.zupacademy.giovannimoratto.ecommerce.purchase_gateway.pagseguro;

import br.com.zupacademy.giovannimoratto.ecommerce.purchase_gateway.TransactionStatus;

/**
 * @Author giovanni.moratto
 */

public enum PagseguroStatusResponse {
    SUCESSO, ERRO;

    public TransactionStatus convert() {
        if (this.equals(SUCESSO)) {
            return TransactionStatus.successful;
        }
        return TransactionStatus.failed;
    }
}