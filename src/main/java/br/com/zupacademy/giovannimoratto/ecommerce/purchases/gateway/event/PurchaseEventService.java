package br.com.zupacademy.giovannimoratto.ecommerce.purchases.gateway.event;

import br.com.zupacademy.giovannimoratto.ecommerce.features.email.Email;
import br.com.zupacademy.giovannimoratto.ecommerce.purchases.costumer.PurchaseModel;
import br.com.zupacademy.giovannimoratto.ecommerce.purchases.gateway.actions.Invoice;
import br.com.zupacademy.giovannimoratto.ecommerce.purchases.gateway.actions.SellersRaking;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @Author giovanni.moratto
 */

@Service
public class PurchaseEventService {

    /* Attributes */
    private final Set <SuccessEvent> successEvents;
    private final Set <FailEvent> failEvents;
    private final Invoice invoice;
    private final Email email;
    private final SellersRaking raking;

    /* Constructors */
    public PurchaseEventService(Set <SuccessEvent> successEvents, Set <FailEvent> failEvents,
                                Invoice invoice, Email email, SellersRaking raking) {
        this.successEvents = successEvents;
        this.failEvents = failEvents;
        this.invoice = invoice;
        this.email = email;
        this.raking = raking;
    }

    /* Methods */
    public void process(PurchaseModel purchase) {
        // Use the check methods in PurchaseModel to proceed if the actions
        if (purchase.successfullyProcessed()) {
            successEvents.forEach(event -> event.process(purchase));
            invoice.getInvoice(purchase);
            raking.getRaking(purchase);
            email.purchaseSuccessful(purchase);
        }
        else {
            failEvents.forEach(event -> event.process(purchase));
            email.purchaseFail(purchase);
        }
    }

}