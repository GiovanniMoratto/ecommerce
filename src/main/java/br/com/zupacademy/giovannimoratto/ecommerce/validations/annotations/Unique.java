package br.com.zupacademy.giovannimoratto.ecommerce.validations.annotations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @Author giovanni.moratto
 */

@SuppressWarnings("unused")
@Documented
@Constraint(validatedBy = {UniqueValidator.class})
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR,
         ElementType.PARAMETER, ElementType.TYPE_USE
})
@Retention(RetentionPolicy.RUNTIME)
public @interface Unique {

    Class <?> domainClass();

    String fieldName();

    String message() default "This value already exists!";

    Class <?>[] groups() default {};

    Class <? extends Payload>[] payload() default {};

}