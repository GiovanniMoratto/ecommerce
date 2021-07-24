package br.com.zupacademy.giovannimoratto.ecommerce.purchase_gateway;

import br.com.zupacademy.giovannimoratto.ecommerce.purchase_costumer.PurchaseModel;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * @Author giovanni.moratto
 */

@Component
public class Invoice {

    public void getInvoice(PurchaseModel purchase) {

        RestTemplate restTemplate = new RestTemplate();
        Map <String, Object> request = Map.of("idPurchase", purchase.getId(), "product",
                purchase.getProduct().getDescription());
        restTemplate.postForEntity("http://localhost:8080/get-invoice", request, String.class);
    }

}
