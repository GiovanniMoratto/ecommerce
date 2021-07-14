package br.com.zupacademy.giovannimoratto.ecommerce.validations.exception_handler;

/**
 * @Author giovanni.moratto
 */

public class FilterExceptionDTO {

    /* Attributes */
    private final String field;
    private final String error;

    /* Constructor */
    public FilterExceptionDTO(String field, String error) {
        super();
        this.field = field;
        this.error = error;
    }

    /* Getters and Setters */
    public String getField() {
        return field;
    }

    public String getError() {
        return error;
    }

}