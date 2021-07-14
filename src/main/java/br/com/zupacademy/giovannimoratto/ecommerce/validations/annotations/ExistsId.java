package br.com.zupacademy.giovannimoratto.ecommerce.validations.annotations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @Author giovanni.moratto
 */

@Documented
@Constraint(validatedBy = {ExistsIdValidator.class})
@Target({
        ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR,
        ElementType.PARAMETER, ElementType.TYPE_USE
})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExistsId {

    Class <?> domainClass();

    String message() default "This ID does not exists!";

    Class <?>[] groups() default {};

    Class <? extends Payload>[] payload() default {};

}