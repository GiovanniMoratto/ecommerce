package br.com.zupacademy.giovannimoratto.ecommerce.get_product_details;

import br.com.zupacademy.giovannimoratto.ecommerce.add_product.ProductModel;
import br.com.zupacademy.giovannimoratto.ecommerce.add_product_images.ImageModel;
import br.com.zupacademy.giovannimoratto.ecommerce.add_product_question.QuestionModel;
import br.com.zupacademy.giovannimoratto.ecommerce.add_product_review.Reviews;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

/**
 * @Author giovanni.moratto
 */

public class ProductDetailsResponse {

    /* Attributes */
    private final Set <String> images;
    private final String name;
    private final BigDecimal price;
    private final Set <ProductFeaturesDetails> features;
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
    public ProductDetailsResponse(ProductModel product) {
        this.images = product.mapImages(ImageModel::getLink);
        this.name = product.getName();
        this.price = product.getPrice();
        this.features = product.mapFeatures(ProductFeaturesDetails::new);
        this.description = product.getDescription();
        Reviews reviews = product.getReviews();
        this.averageLikes = reviews.averageLikes();
        this.numberOfLikes = reviews.numberOfLikes();
        this.reviews = reviews.mapReviews(review ->
                Map.of("title", review.getTitle(), "comment", review.getComment()));
        this.questions = product.mapQuestions(QuestionModel::getTitle);
        this.stockInformation = product.getStockInformation();
        this.numberOfReviews = reviews.numberOfReviews();
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

    public Set <ProductFeaturesDetails> getFeatures() {
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