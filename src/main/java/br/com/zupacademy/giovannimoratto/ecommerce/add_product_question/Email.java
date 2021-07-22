package br.com.zupacademy.giovannimoratto.ecommerce.add_product_question;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author giovanni.moratto
 */

@Service
public class Email {

    @Autowired
    private Mailer mailer;

    public void newQuestion(QuestionModel question) {
        mailer.send("<html>...</html>", "Nova pergunta...",
                question.getUser().getUsername(), "novapergunta@nossomercadolivre.com",
                question.getProduct().getUserCreator().getUsername());
    }

}