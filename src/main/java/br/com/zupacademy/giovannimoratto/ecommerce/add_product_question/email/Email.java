package br.com.zupacademy.giovannimoratto.ecommerce.add_product_question.email;

import br.com.zupacademy.giovannimoratto.ecommerce.purchase_request.PurchaseModel;
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

    public void sendQuestionEmail(QuestionModel question) {
        String body = "<html>...</html>";
        String subject = question.getTitle();
        String nameFrom = "novapergunta@nossomercadolivre.com";
        String from = question.getUser().getLogin();
        String to = question.getProduct().getSeller().getLogin();

        mailer.send(body, subject, nameFrom, from, to);
    }

    public void sendBuyEmail(PurchaseModel buy) {
        String body = "<html>...</html>";
        String subject = "Purchase of " + buy.getProduct().getName() + " was successful. ";
        String nameFrom = "compras@nossomercadolivre.com";
        String from = buy.getCostumer().getLogin();
        String to = buy.getProduct().getSeller().getLogin();

        mailer.send(body, subject, nameFrom, from, to);
    }

}