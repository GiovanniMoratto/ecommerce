package br.com.zupacademy.giovannimoratto.ecommerce.purchase_gateway.event;

import br.com.zupacademy.giovannimoratto.ecommerce.email.Email;
import br.com.zupacademy.giovannimoratto.ecommerce.purchase_costumer.PurchaseModel;
import br.com.zupacademy.giovannimoratto.ecommerce.purchase_gateway.Invoice;
import br.com.zupacademy.giovannimoratto.ecommerce.purchase_gateway.SellersRaking;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @Author giovanni.moratto
 */

@Service
public class PurchaseEventService {

    private final Set <SuccessEvent> successEvents;
    private final Set <FailEvent> failEvents;
    private final Invoice invoice;
    private final Email email;
    private final SellersRaking raking;

    public PurchaseEventService(Set <SuccessEvent> successEvents, Set <FailEvent> failEvents,
                                Invoice invoice, Email email, SellersRaking raking) {
        this.successEvents = successEvents;
        this.failEvents = failEvents;
        this.invoice = invoice;
        this.email = email;
        this.raking = raking;
    }

    public void process(PurchaseModel purchase) {
        if (purchase.successfullyProcessed()) {
            successEvents.forEach(event -> event.process(purchase));
            invoice.getInvoice(purchase);
            raking.getRaking(purchase);
            email.sendPurchaseCompleteEmail(purchase);
        }
        else {
            failEvents.forEach(event -> event.process(purchase));
            email.sendPurchaseFailEmail(purchase);
        }
    }
}
