package br.com.zupacademy.giovannimoratto.ecommerce.add_buy;

import br.com.zupacademy.giovannimoratto.ecommerce.add_product.ProductModel;
import br.com.zupacademy.giovannimoratto.ecommerce.add_product.ProductRepository;
import br.com.zupacademy.giovannimoratto.ecommerce.add_product_question.email.Email;
import br.com.zupacademy.giovannimoratto.ecommerce.add_user.UserModel;
import br.com.zupacademy.giovannimoratto.ecommerce.add_user.UserRepository;
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
public class BuyController {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final BuyRepository buyRepository;
    private final Email email;

    public BuyController(UserRepository userRepository, ProductRepository productRepository,
                         BuyRepository buyRepository, Email email) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.buyRepository = buyRepository;
        this.email = email;
    }

    /* Methods */
    // POST Request - Register a Buy
    @PostMapping("/buy") // Endpoint
    @Transactional
    public String addBuy(@AuthenticationPrincipal UserDetails logged,
                         @RequestBody @Valid BuyRequest request,
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
            BuyModel buy = new BuyModel(quantity, product, gateway, costumer);
            buyRepository.save(buy);
            email.sendBuyEmail(buy);
            return buy.urlRedirect(uriBuilder);
        }
        BindException stockErrors = new BindException(request, "Buy Request");
        stockErrors.reject(
                String.valueOf(507),
                "It is not possible to create a purchase order");
        throw stockErrors;
    }

}
