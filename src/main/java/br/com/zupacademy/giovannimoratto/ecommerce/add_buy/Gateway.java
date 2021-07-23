package br.com.zupacademy.giovannimoratto.ecommerce.add_buy;

import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @Author giovanni.moratto
 */

public enum Gateway {

    pagseguro {
        @Override
        String urlResponse(BuyModel buy, UriComponentsBuilder uriBuilder) {
            UriComponents pagseguroResponse = uriBuilder.path("/pagseguro/{id}/")
                    .buildAndExpand(buy.getId().toString());
            return "pagseguro.com" + buy.getId() + "?redirectUrl=" + pagseguroResponse;
        }
    },

    paypal {
        @Override
        String urlResponse(BuyModel buy, UriComponentsBuilder uriBuilder) {
            UriComponents paypalResponse = uriBuilder.path("/paypal/{id}/")
                    .buildAndExpand(buy.getId().toString());
            return "paypal.com" + buy.getId() + "?redirectUrl=" + paypalResponse;
        }
    };

    abstract String urlResponse(BuyModel buy, UriComponentsBuilder uriBuilder);
}
