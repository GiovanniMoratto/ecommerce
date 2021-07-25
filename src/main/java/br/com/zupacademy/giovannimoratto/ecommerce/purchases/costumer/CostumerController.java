package br.com.zupacademy.giovannimoratto.ecommerce.purchases.costumer;

import br.com.zupacademy.giovannimoratto.ecommerce.features.email.Email;
import br.com.zupacademy.giovannimoratto.ecommerce.products.ProductModel;
import br.com.zupacademy.giovannimoratto.ecommerce.products.ProductRepository;
import br.com.zupacademy.giovannimoratto.ecommerce.users.UserModel;
import br.com.zupacademy.giovannimoratto.ecommerce.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;

/**
 * @Author giovanni.moratto
 */

@RestController
@RequestMapping("/api")
public class CostumerController {

    /* Dependencies Injection */
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private PurchaseRepository purchaseRepository;
    @Autowired
    private Email email;

    /* Methods */
    // POST Request - Register a Buy
    @PostMapping("/buy") // Endpoint
    @Transactional
    public ResponseEntity <?> addBuy(@AuthenticationPrincipal UserDetails logged,
                                     @RequestBody @Valid CostumerRequest request,
                                     UriComponentsBuilder uriBuilder) throws BindException {
        // Get user from Token
        UserModel costumer = userRepository.getByLogin(logged.getUsername());

        // Get Product from request
        ProductModel product = productRepository.getById(request.getIdProduct());

        // Destock amount product in stock
        Integer quantity = request.getQuantity();
        Boolean subtracted = product.destock(quantity);

        if (subtracted) {
            Gateway gateway = request.getGateway();
            PurchaseModel purchase = new PurchaseModel(quantity, product, gateway, costumer);
            purchaseRepository.save(purchase);
            email.purchaseRequest(purchase);
            return ResponseEntity.status(HttpStatus.FOUND).body(purchase.urlRedirect(uriBuilder));
        }
        return ResponseEntity.badRequest().body("It is not possible to create a purchase order");
    }

}
