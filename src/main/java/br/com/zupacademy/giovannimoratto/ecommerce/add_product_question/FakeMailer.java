package br.com.zupacademy.giovannimoratto.ecommerce.add_product_question;

/**
 * @Author giovanni.moratto
 */

public class FakeMailer implements Mailer {

    @Override
    public void send(String body, String subject, String nameFrom, String from, String to) {
        System.out.println(body);
        System.out.println(subject);
        System.out.println(nameFrom);
        System.out.println(from);
        System.out.println(to);
    }

}