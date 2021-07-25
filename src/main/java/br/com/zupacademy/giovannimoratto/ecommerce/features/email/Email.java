package br.com.zupacademy.giovannimoratto.ecommerce.features.email;

import br.com.zupacademy.giovannimoratto.ecommerce.purchases.costumer.PurchaseModel;
import br.com.zupacademy.giovannimoratto.ecommerce.questions.QuestionModel;
import org.springframework.stereotype.Service;

/**
 * @Author giovanni.moratto
 */

@Service
public class Email {

    /* Attributes */
    private final Mailer mailer;

    /* Constructor */
    public Email(Mailer mailer) {
        this.mailer = mailer;
    }

    /* Methods */
    // Send email for new Question
    public void question(QuestionModel question) {
        String body = "<html>...</html>";
        String subject = question.getTitle();
        String nameFrom = "novapergunta@nossomercadolivre.com";
        String from = question.getUser().getLogin();
        String to = question.getProduct().getSeller().getLogin();
        mailer.send(body, subject, nameFrom, from, to);
    }

    // Send email for new Purchase Request
    public void purchaseRequest(PurchaseModel buy) {
        String body = "<html>...</html>";
        String subject = "Purchase of " + buy.getProduct().getName() + " was request. ";
        String nameFrom = "compras@nossomercadolivre.com";
        String from = buy.getCostumer().getLogin();
        String to = buy.getProduct().getSeller().getLogin();
        mailer.send(body, subject, nameFrom, from, to);
    }

    // Send an email for successful purchase
    public void purchaseSuccessful(PurchaseModel buy) {
        String body = "<html>...</html>";
        String subject = "Purchase of " + buy.getProduct().getName() + " was successful complete. Thank you for your " +
                         "business";
        String nameFrom = "compras@nossomercadolivre.com";
        String to = buy.getCostumer().getLogin();
        String from = buy.getProduct().getSeller().getLogin();
        mailer.send(body, subject, nameFrom, from, to);
    }

    // Send an email for failed purchase
    public void purchaseFail(PurchaseModel buy) {
        String body = "<html>...</html>";
        String subject = "Purchase of " + buy.getProduct().getName() + " was fail. Please check your information";
        String nameFrom = "compras@nossomercadolivre.com";
        String to = buy.getCostumer().getLogin();
        String from = buy.getProduct().getSeller().getLogin();
        mailer.send(body, subject, nameFrom, from, to);
    }

}