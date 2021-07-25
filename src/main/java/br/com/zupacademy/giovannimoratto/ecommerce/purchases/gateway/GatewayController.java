package br.com.zupacademy.giovannimoratto.ecommerce.purchases.gateway;

import br.com.zupacademy.giovannimoratto.ecommerce.purchases.costumer.PurchaseModel;
import br.com.zupacademy.giovannimoratto.ecommerce.purchases.costumer.PurchaseRepository;
import br.com.zupacademy.giovannimoratto.ecommerce.purchases.gateway.actions.InvoiceRequest;
import br.com.zupacademy.giovannimoratto.ecommerce.purchases.gateway.actions.SellersRakingRequest;
import br.com.zupacademy.giovannimoratto.ecommerce.purchases.gateway.event.PurchaseEventService;
import br.com.zupacademy.giovannimoratto.ecommerce.purchases.gateway.request.GatewayResponse;
import br.com.zupacademy.giovannimoratto.ecommerce.purchases.gateway.request.pagseguro.PagseguroRequest;
import br.com.zupacademy.giovannimoratto.ecommerce.purchases.gateway.request.paypal.PaypalRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import javax.validation.Valid;

/**
 * @Author giovanni.moratto
 */

@RestController
@RequestMapping("api/")
public class GatewayController {

    /* Dependencies Injection */
    @Autowired
    private PurchaseRepository repository;
    @Autowired
    private PurchaseEventService event;

    /* Methods */
    // POST Request - Purchase Response for PagSeguro
    @PostMapping("/pagseguro.com/{id}") // Endpoint
    @Transactional
    public ResponseEntity <?> pagseguroResponseProcess(@AuthenticationPrincipal UserDetails logged,
                                                       @PathVariable("id") Long id,
                                                       @RequestBody @Valid PagseguroRequest request) {
        process(id, request);
        return ResponseEntity.ok().build();
    }

    // Process the Gateways requests adding transactions, persisting in the database and calling event actions
    private void process(Long id, GatewayResponse gateway) {

        PurchaseModel purchase = repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND));

        purchase.addTransaction(gateway);
        repository.save(purchase);
        event.process(purchase);
    }

    // POST Request - Purchase Response for PayPal
    @PostMapping("/paypal.com/{id}") // Endpoint
    @Transactional
    public ResponseEntity <?> paypalResponseProcess(@AuthenticationPrincipal UserDetails logged,
                                                    @PathVariable("id") Long id,
                                                    @RequestBody @Valid PaypalRequest request) {
        process(id, request);
        return ResponseEntity.ok().build();
    }

    // POST Request - Generate Invoice for a new Purchase
    @PostMapping("/get-invoice")
    public ResponseEntity <?> getInvoice(@RequestBody InvoiceRequest invoiceRequest) {
        System.out.println("Invoice");
        System.out.println("Purchase : " + invoiceRequest.getIdPurchase());
        System.out.println("Product: " + invoiceRequest.getProduct());
        return ResponseEntity.ok().build();
    }

    // POST Request - Generate a new Sellers Raking by the new Purchase
    @PostMapping("/get-ranking")
    public ResponseEntity <?> getRanking(@RequestBody SellersRakingRequest ranking) {
        System.out.println("Ranking");
        System.out.println("Purchase : " + ranking.getId());
        System.out.println("Seller: " + ranking.getSeller());
        return ResponseEntity.ok().build();
    }

}