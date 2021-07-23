package br.com.zupacademy.giovannimoratto.ecommerce.add_product_question.email;

import br.com.zupacademy.giovannimoratto.ecommerce.add_product_question.QuestionModel;
import org.springframework.stereotype.Service;

/**
 * @Author giovanni.moratto
 */

@Service
public class Email {

    private final Mailer mailer;

    public Email(Mailer mailer) {
        this.mailer = mailer;
    }

    public void sendEmail(QuestionModel question) {
        String body = "<html>...</html>";
        String subject = question.getTitle();
        String nameFrom = "Costumer name";
        String from = question.getUser().getLogin();
        String to = question.getProduct().getSeller().getLogin();

        mailer.send(body, subject, nameFrom, from, to);
    }

}