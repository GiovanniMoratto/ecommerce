package br.com.zupacademy.giovannimoratto.ecommerce.purchase_response.paypal;

import br.com.zupacademy.giovannimoratto.ecommerce.purchase_response.TransactionStatus;

/**
 * @Author giovanni.moratto
 */

public enum PaypalStatusResponse {
    1, 0;

    public TransactionStatus filter() {
        if (this.equals(1)) {
            return TransactionStatus.successful;
        }
        else {
            return TransactionStatus.failed;
        }
    }
}
