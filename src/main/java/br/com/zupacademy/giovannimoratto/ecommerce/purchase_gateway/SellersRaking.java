package br.com.zupacademy.giovannimoratto.ecommerce.purchase_gateway;

import br.com.zupacademy.giovannimoratto.ecommerce.purchase_costumer.PurchaseModel;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * @Author giovanni.moratto
 */

@Component
public class SellersRaking {

    public void getRaking(PurchaseModel purchase) {
        RestTemplate restTemplate = new RestTemplate();
        Map <String, Object> request = Map.of("idPurchase", purchase.getId(), "seller",
                purchase.getProduct().getSeller().getLogin());
        restTemplate.postForEntity("http://localhost:8080/api/get-ranking", request, String.class);
    }
}
