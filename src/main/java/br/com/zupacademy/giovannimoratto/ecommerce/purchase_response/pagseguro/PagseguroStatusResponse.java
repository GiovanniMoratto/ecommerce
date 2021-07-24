package br.com.zupacademy.giovannimoratto.ecommerce.purchase_response.pagseguro;

import br.com.zupacademy.giovannimoratto.ecommerce.purchase_response.TransactionStatus;

/**
 * @Author giovanni.moratto
 */

public enum PagseguroStatusResponse {
    SUCESSO, ERRO;

    public TransactionStatus filter() {
        if (this.equals(SUCESSO)) {
            return TransactionStatus.successful;
        }
        else {
            return TransactionStatus.failed;
        }
    }
}
