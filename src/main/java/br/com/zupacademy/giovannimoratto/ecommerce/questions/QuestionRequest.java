package br.com.zupacademy.giovannimoratto.ecommerce.questions;

import br.com.zupacademy.giovannimoratto.ecommerce.products.ProductModel;
import br.com.zupacademy.giovannimoratto.ecommerce.users.UserModel;

import javax.validation.constraints.NotBlank;

/**
 * @Author giovanni.moratto
 */

public class QuestionRequest {

    /* Attributes */
    @NotBlank
    private String title;

    /* Constructors */
    @Deprecated
    public QuestionRequest() {
    }

    public QuestionRequest(@NotBlank String title) {
        this.title = title;
    }

    /* Methods */
    // Convert ReviewRequest.class in ReviewModel.class
    public QuestionModel toModel(ProductModel product, UserModel user) {
        return new QuestionModel(title, product, user);
    }

}