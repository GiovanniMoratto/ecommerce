package br.com.zupacademy.giovannimoratto.ecommerce.purchase_gateway;

import br.com.zupacademy.giovannimoratto.ecommerce.purchase_costumer.PurchaseModel;

/**
 * @Author giovanni.moratto
 */

public interface GatewayResponse {

    TransactionModel create(PurchaseModel purchase);

}
