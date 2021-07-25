package br.com.zupacademy.giovannimoratto.ecommerce.purchases.gateway.request;

import br.com.zupacademy.giovannimoratto.ecommerce.purchases.costumer.PurchaseModel;
import br.com.zupacademy.giovannimoratto.ecommerce.purchases.gateway.TransactionModel;

/**
 * @Author giovanni.moratto
 */

public interface GatewayResponse {

    TransactionModel create(PurchaseModel purchase);

}
