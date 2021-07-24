package br.com.zupacademy.giovannimoratto.ecommerce.purchase;

import br.com.zupacademy.giovannimoratto.ecommerce.purchase_request.PurchaseModel;
import br.com.zupacademy.giovannimoratto.ecommerce.purchase_request.PurchaseRepository;
import br.com.zupacademy.giovannimoratto.ecommerce.purchase_response.GatewayResponse;
import br.com.zupacademy.giovannimoratto.ecommerce.purchase_response.pagseguro.PagseguroRequest;
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

    /* Methods */
    // POST Request - Purchase Response for PagSeguro
    @PostMapping("pagseguro.com/{id}") // Endpoint
    @Transactional
    public void pagseguroResponseProcess(@AuthenticationPrincipal UserDetails logged, @PathVariable("id") Long id,
                                         @Valid PagseguroRequest request) {
        process(id, request);
    }


    private void process(Long id, GatewayResponse gateway) {

        PurchaseModel purchase = purchaseRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND));

        purchase.addTransaction(gateway);
        purchaseRepository.save(purchase);
        purchaseEventService.process(purchase);
    }
}
