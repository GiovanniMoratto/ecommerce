package br.com.zupacademy.giovannimoratto.ecommerce.reviews;

import br.com.zupacademy.giovannimoratto.ecommerce.products.ProductModel;
import br.com.zupacademy.giovannimoratto.ecommerce.users.UserModel;

import javax.validation.constraints.*;

/**
 * @Author giovanni.moratto
 */

public class ReviewRequest {

    /* Attributes */
    @NotNull
    @Min(1)
    @Max(5)
    private final Integer likes;
    @NotBlank
    private final String title;
    @NotBlank
    @Size(max = 500)
    private final String comment;

    /* Constructors */
    public ReviewRequest(Integer likes, String title, String comment) {
        this.likes = likes;
        this.title = title;
        this.comment = comment;
    }

    /* Methods */
    // Convert ReviewRequest.class in ReviewModel.class
    public ReviewModel toModel(ProductModel product, UserModel user) {
        return new ReviewModel(likes, title, comment, product, user);
    }

}