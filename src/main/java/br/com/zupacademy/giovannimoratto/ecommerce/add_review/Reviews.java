package br.com.zupacademy.giovannimoratto.ecommerce.add_review;

import java.util.OptionalDouble;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Author giovanni.moratto
 */

public class Reviews {

    /* Attributes */
    private Set <ReviewModel> reviews;

    /* Setter */
    public void setReviews(Set <ReviewModel> reviews) {
        this.reviews = reviews;
    }

    public double averageRating() {
        Set <Integer> ratings = mapReviews(review -> review.getRating());
        OptionalDouble average = ratings.stream().mapToInt(rating -> rating).average();
        return average.orElse(0.0);
    }

    public <T> Set <T> mapReviews(Function <ReviewModel, T> mapFunction) {
        return this.reviews.stream().map(mapFunction).collect(Collectors.toSet());
    }

    public int numberOfRatings() {
        return reviews.size();
    }

}
