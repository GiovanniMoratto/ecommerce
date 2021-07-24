package br.com.zupacademy.giovannimoratto.ecommerce.purchase_response;

import br.com.zupacademy.giovannimoratto.ecommerce.purchase_request.PurchaseModel;
import br.com.zupacademy.giovannimoratto.ecommerce.purchase_request.PurchaseRepository;
import br.com.zupacademy.giovannimoratto.ecommerce.purchase_response.pagseguro.PagseguroRequest;
import br.com.zupacademy.giovannimoratto.ecommerce.purchase_response.paypal.PaypalRequest;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/api")
public class PurchaseGatewayController {

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private PurchaseEventService purchaseEventService;

    /* Methods */
    // POST Request - Purchase Response for PagSeguro
    @PostMapping("pagseguro.com/{id}") // Endpoint
    @Transactional
    public String pagseguroResponseProcess(@AuthenticationPrincipal UserDetails logged,
                                           @PathVariable("id") Long purchaseId,
                                           @Valid PagseguroRequest request) {
        return process(purchaseId, request);
    }

    // POST Request - Purchase Response for PayPal
    @PostMapping("paypal.com/{id}") // Endpoint
    @Transactional
    public String paypalResponseProcess(@AuthenticationPrincipal UserDetails logged,
                                        @PathVariable("id") Long purchaseId,
                                        @Valid PaypalRequest request) {
        return process(purchaseId, request);
    }

    private String process(Long purchaseId, GatewayResponse gateway) {

        PurchaseModel purchase = purchaseRepository.findById(purchaseId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND));

        purchase.addTransaction(gateway);
        purchaseRepository.save(purchase);
        purchaseEventService.process(purchase);
        return purchase.toString();
    }

}
