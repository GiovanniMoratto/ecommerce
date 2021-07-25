package br.com.zupacademy.giovannimoratto.ecommerce.products.product_features;

import br.com.zupacademy.giovannimoratto.ecommerce.products.ProductModel;

import javax.validation.constraints.NotBlank;

/**
 * @Author giovanni.moratto
 */

public class FeatureRequest {

    /* Attributes */
    @NotBlank
    private final String name;
    @NotBlank
    private final String description;

    /* Constructors */
    public FeatureRequest(@NotBlank String name, @NotBlank String description) {
        this.name = name;
        this.description = description;
    }

    /* Methods */
    // Convert FeatureRequest.class in FeatureModel.class
    public FeatureModel toModel(ProductModel product) {
        return new FeatureModel(name, description, product);
    }

}