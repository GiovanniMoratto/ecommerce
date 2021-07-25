package br.com.zupacademy.giovannimoratto.ecommerce.purchase_gateway;

import br.com.zupacademy.giovannimoratto.ecommerce.purchase_costumer.PurchaseModel;
import br.com.zupacademy.giovannimoratto.ecommerce.purchase_costumer.PurchaseRepository;
import br.com.zupacademy.giovannimoratto.ecommerce.purchase_gateway.event.PurchaseEventService;
import br.com.zupacademy.giovannimoratto.ecommerce.purchase_gateway.pagseguro.PagseguroRequest;
import br.com.zupacademy.giovannimoratto.ecommerce.purchase_gateway.paypal.PaypalRequest;
import org.springframework.http.HttpStatus;
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
                                         @RequestBody @Valid PagseguroRequest request, GatewayResponse gateway) {

        process(id, request);
    }

    public void process(Long id, GatewayResponse gateway) {

        PurchaseModel purchase = repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND));

        purchase.addTransaction(gateway);
        repository.save(purchase);
        event.process(purchase);
    }

    // POST Request - Purchase Response for PayPal
    @PostMapping("/paypal.com/{id}") // Endpoint
    @Transactional
    public void paypalResponseProcess(@AuthenticationPrincipal UserDetails logged, @PathVariable("id") Long id,
                                      @RequestBody @Valid PaypalRequest request, GatewayResponse gateway) {

        process(id, request);
    }

    @PostMapping("/get-invoice")
    public void getInvoice(@RequestBody InvoiceRequest invoiceRequest) {
        System.out.println("Invoice");
        System.out.println("Purchase : " + invoiceRequest.getIdPurchase());
        System.out.println("Product: " + invoiceRequest.getProduct());
    }

    @PostMapping("/get-ranking")
    public void getRanking(@RequestBody SellersRakingRequest ranking) {

        System.out.println("Ranking");
        System.out.println("Purchase : " + ranking.getId());
        System.out.println("Seller: " + ranking.getSeller());
    }

}