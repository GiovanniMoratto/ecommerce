package br.com.zupacademy.giovannimoratto.ecommerce.products.product_features;

/**
 * @Author giovanni.moratto
 */
public class FeaturesDetails {

    /* Attributes */
    private final String name;
    private final String description;

    /* Constructors */
    public FeaturesDetails(FeatureModel feature) {
        this.name = feature.getName();
        this.description = feature.getDescription();
    }

    /* Getters */
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

}
