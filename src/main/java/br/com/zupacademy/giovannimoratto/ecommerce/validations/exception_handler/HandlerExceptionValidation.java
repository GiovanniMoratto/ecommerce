package br.com.zupacademy.giovannimoratto.ecommerce.validations.exception_handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author giovanni.moratto
 */

@RestControllerAdvice
public class HandlerExceptionValidation {

    @Autowired
    private MessageSource messageSource;

    /* Methods */
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List <FilterExceptionDTO> handlerError(MethodArgumentNotValidException exception) {

        List <FilterExceptionDTO> filterException = new ArrayList <>();
        List <ObjectError> objectErrors = exception.getBindingResult().getGlobalErrors();
        objectErrors.forEach(e -> {
            String message = messageSource.getMessage(e, LocaleContextHolder.getLocale());
            FilterExceptionDTO error = new FilterExceptionDTO(e.getObjectName(), message);
            filterException.add(error);
        });
        List <FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        fieldErrors.forEach(e -> {
            String message = messageSource.getMessage(e, LocaleContextHolder.getLocale());
            FilterExceptionDTO error = new FilterExceptionDTO(e.getField(), message);
            filterException.add(error);
        });
        return filterException;
    }

}