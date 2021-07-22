package br.com.zupacademy.giovannimoratto.ecommerce.add_product_question.email;

import br.com.zupacademy.giovannimoratto.ecommerce.add_product_question.QuestionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author giovanni.moratto
 */

@Service
public class Email {

    @Autowired
    private Mailer mailer;

    public void sendEmail(QuestionModel question) {
        String body = "<html>...</html>";
        String subject = question.getTitle();
        String nameFrom = "Costumer name";
        String from = question.getUser().getUsername();
        String to = question.getProduct().getUserCreator().getUsername();

        mailer.send(body, subject, nameFrom, from, to);
    }

}