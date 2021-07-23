package br.com.zupacademy.giovannimoratto.ecommerce.get_product_details;

import br.com.zupacademy.giovannimoratto.ecommerce.add_product.product_features.FeatureModel;

/**
 * @Author giovanni.moratto
 */

public class ProductFeaturesDetails {

    private final String name;
    private final String description;

    public ProductFeaturesDetails(FeatureModel feature) {
        this.name = feature.getName();
        this.description = feature.getDescription();
    }

    /* Getters and Setters */
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

}