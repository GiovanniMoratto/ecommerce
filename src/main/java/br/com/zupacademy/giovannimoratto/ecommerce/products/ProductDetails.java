package br.com.zupacademy.giovannimoratto.ecommerce.products;

import br.com.zupacademy.giovannimoratto.ecommerce.images.ImageModel;
import br.com.zupacademy.giovannimoratto.ecommerce.products.product_features.FeaturesDetails;
import br.com.zupacademy.giovannimoratto.ecommerce.questions.QuestionModel;
import br.com.zupacademy.giovannimoratto.ecommerce.reviews.ReviewModel;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

/**
 * @Author giovanni.moratto
 */

public class ProductDetails {

    /* Attributes */
    private final Set <String> images;
    private final String name;
    private final BigDecimal price;
    private final Set <FeaturesDetails> features;
    private final String description;
    private final double averageLikes;
    private final int numberOfLikes;
    private final Set <Map <String, String>> reviews;
    private final SortedSet <String> questions;
    private final Integer stockInformation;
    private final int numberOfReviews;
    private final String category;
    private final String seller;

    /* Constructors */
    public ProductDetails(ProductModel product) {
        this.images = product.mapImages(ImageModel::getLink);
        this.name = product.getName();
        this.price = product.getPrice();
        this.features = product.mapFeatures(FeaturesDetails::new);
        this.description = product.getDescription();
        this.reviews = product.mapReviews(review ->
                Map.of("title", review.getTitle(), "comment", review.getComment()));
        this.averageLikes = product.averageLikes();
        this.numberOfLikes = product.numberOfLikes();
        this.questions = product.mapQuestions(QuestionModel::getTitle);
        this.stockInformation = product.getStockInformation();
        this.numberOfReviews = product.numberOfReviews();
        this.category = product.getCategory().getName();
        this.seller = product.getSeller().getLogin();
    }

    /* Getters */
    public Set <String> getImages() {
        return images;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Set <FeaturesDetails> getFeatures() {
        return features;
    }

    public String getDescription() {
        return description;
    }

    public double getAverageLikes() {
        return averageLikes;
    }

    public int getNumberOfLikes() {
        return numberOfLikes;
    }

    public Set <Map <String, String>> getReviews() {
        return reviews;
    }

    public SortedSet <String> getQuestions() {
        return questions;
    }

    public Integer getStockInformation() {
        return stockInformation;
    }

    public int getNumberOfReviews() {
        return numberOfReviews;
    }

    public String getCategory() {
        return category;
    }

    public String getSeller() {
        return seller;
    }

}