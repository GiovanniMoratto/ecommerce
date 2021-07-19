package br.com.zupacademy.giovannimoratto.ecommerce.validations.exception_handler;

/**
 * @Author giovanni.moratto
 */

public class SearchException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public SearchException(String message) {
        super(message);
    }

}
