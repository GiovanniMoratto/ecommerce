package br.com.zupacademy.giovannimoratto.ecommerce.add_product_review;

import java.util.OptionalDouble;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Author giovanni.moratto
 */

public class Reviews {

    /* Attributes */
    private final Set <ReviewModel> reviews;

    /* Setter */
    public Reviews(Set <ReviewModel> reviews) {
        this.reviews = reviews;
    }

    public double averageLikes() {
        Set <Integer> likes = mapReviews(ReviewModel::getLikes);
        OptionalDouble average = likes.stream().mapToInt(like -> like).average();
        return average.orElse(0.0);
    }

    public <T> Set <T> mapReviews(Function <ReviewModel, T> mapFunction) {
        return this.reviews.stream().map(mapFunction).collect(Collectors.toSet());
    }

    public int numberOfReviews() {
        return this.reviews.size();
    }

    public int numberOfLikes() {
        Set <Integer> likes = mapReviews(ReviewModel::getLikes);
        return likes.size();
    }

}