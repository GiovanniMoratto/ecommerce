package br.com.zupacademy.giovannimoratto.ecommerce.add_product_question;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * @Author giovanni.moratto
 */

public interface Mailer {

    void send(@NotBlank String body, @NotBlank String subject,
              @NotBlank String nameFrom, @NotBlank @Email String from, @NotBlank @Email String to);

}