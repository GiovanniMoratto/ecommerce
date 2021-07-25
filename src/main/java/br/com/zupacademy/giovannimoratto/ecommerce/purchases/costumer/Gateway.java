package br.com.zupacademy.giovannimoratto.ecommerce.purchases.costumer;

import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @Author giovanni.moratto
 */

public enum Gateway {

    pagseguro {
        @Override
        String urlResponse(PurchaseModel purchase, UriComponentsBuilder uriBuilder) {
            UriComponents pagseguroResponse = uriBuilder.path("/api/pagseguro.com/{id}/")
                    .buildAndExpand(purchase.getId().toString());
            return "pagseguro.com" + purchase.getId() + "?redirectUrl=" + pagseguroResponse;
        }
    },

    paypal {
        @Override
        String urlResponse(PurchaseModel purchase, UriComponentsBuilder uriBuilder) {
            UriComponents paypalResponse = uriBuilder.path("/api/paypal.com/{id}/")
                    .buildAndExpand(purchase.getId().toString());
            return "paypal.com" + purchase.getId() + "?redirectUrl=" + paypalResponse;
        }
    };

    abstract String urlResponse(PurchaseModel purchase, UriComponentsBuilder uriBuilder);
}