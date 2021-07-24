package br.com.zupacademy.giovannimoratto.ecommerce.purchase_response;

import br.com.zupacademy.giovannimoratto.ecommerce.purchase_request.PurchaseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @Author giovanni.moratto
 */

@Service
public class PurchaseEventService {

    @Autowired
    private Set <SuccessEventRequest> successEvent;

    public void process(PurchaseModel purchase) {
        if (purchase.successfullyProcessed()) {
            successEvent.forEach(event -> event.process(purchase));
        }
        else {
            //Fail event
        }
    }
}
