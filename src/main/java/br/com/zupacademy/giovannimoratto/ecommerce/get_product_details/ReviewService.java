package br.com.zupacademy.giovannimoratto.ecommerce.get_product_details;

import br.com.zupacademy.giovannimoratto.ecommerce.add_product_review.ReviewModel;

import java.util.OptionalDouble;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Author giovanni.moratto
 */

public class ReviewService {

    /* Attributes */
    private final Set <ReviewModel> reviews;

    /* Methods */
    // Get average likes in product reviews
    public double averageLikes() {
        Set <Integer> likes = mapReviews(ReviewModel::getLikes);
        OptionalDouble average = likes.stream().mapToInt(like -> like).average();
        return average.orElse(0.0);
    }

    // Link Reviews value Collection by ReviewModel primary key
    public <T> Set <T> mapReviews(Function <ReviewModel, T> mapFunction) {
        return this.reviews.stream().map(mapFunction).collect(Collectors.toSet());
    }

    // Get total product reviews
    public int numberOfReviews() {
        return this.reviews.size();
    }

    // Get Total likes of a product
    public int numberOfLikes() {
        Set <Integer> likes = mapReviews(ReviewModel::getLikes);
        return likes.size();
    }

    /* Setter */
    public ReviewService(Set <ReviewModel> reviews) {
        this.reviews = reviews;
    }

}