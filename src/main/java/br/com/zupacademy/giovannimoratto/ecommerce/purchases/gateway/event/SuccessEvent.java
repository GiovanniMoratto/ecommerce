package br.com.zupacademy.giovannimoratto.ecommerce.purchases.gateway.event;

import br.com.zupacademy.giovannimoratto.ecommerce.purchases.costumer.PurchaseModel;

/**
 * @Author giovanni.moratto
 */

public interface SuccessEvent {

    void processedOk(PurchaseModel purchase);
}
