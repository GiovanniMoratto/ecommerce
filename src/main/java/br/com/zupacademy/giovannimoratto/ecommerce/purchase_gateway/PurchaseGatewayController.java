package br.com.zupacademy.giovannimoratto.ecommerce.purchase_gateway;

import br.com.zupacademy.giovannimoratto.ecommerce.purchase_costumer.PurchaseModel;
import br.com.zupacademy.giovannimoratto.ecommerce.purchase_costumer.PurchaseRepository;
import br.com.zupacademy.giovannimoratto.ecommerce.purchase_gateway.event.PurchaseEventService;
import br.com.zupacademy.giovannimoratto.ecommerce.purchase_gateway.pagseguro.PagseguroRequest;
import br.com.zupacademy.giovannimoratto.ecommerce.purchase_gateway.paypal.PaypalRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import javax.validation.Valid;

/**
 * @Author giovanni.moratto
 */

@RestController
@RequestMapping("api/")
public class PurchaseGatewayController {

    private final PurchaseRepository repository;
    private final PurchaseEventService event;

    public PurchaseGatewayController(PurchaseRepository repository, PurchaseEventService event) {
        this.repository = repository;
        this.event = event;
    }

    /* Methods */
    // POST Request - Purchase Response for PagSeguro
    @PostMapping("/pagseguro.com/{id}") // Endpoint
    @Transactional
    public void pagseguroResponseProcess(@AuthenticationPrincipal UserDetails logged, @PathVariable("id") Long id,
                                           @Valid PagseguroRequest request) {
        process(id, request);
    }

    // POST Request - Purchase Response for PayPal
    @PostMapping("/paypal.com/{id}") // Endpoint
    @Transactional
    public void paypalResponseProcess(@AuthenticationPrincipal UserDetails logged, @PathVariable("id") Long id,
                                        @Valid PaypalRequest request) {
        process(id, request);
    }

    private void process(Long id, GatewayResponse gateway) {

        PurchaseModel purchase = repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND));

        purchase.addTransaction(gateway);
        repository.save(purchase);
        event.process(purchase);
    }

}
