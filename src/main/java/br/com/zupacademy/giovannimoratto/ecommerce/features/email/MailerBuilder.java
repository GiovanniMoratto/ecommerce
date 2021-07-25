package br.com.zupacademy.giovannimoratto.ecommerce.features.email;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * @Author giovanni.moratto
 */

@Primary
@Component
public class MailerBuilder implements Mailer {

    @Override
    public void send(String body, String subject, String nameFrom, String from, String to) {
        System.out.println("body: " + body);
        System.out.println("subject: " + subject);
        System.out.println("nameFrom: " + nameFrom);
        System.out.println("from: " + from);
        System.out.println("to: " + to);
    }

}