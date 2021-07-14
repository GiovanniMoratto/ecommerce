package br.com.zupacademy.giovannimoratto.ecommerce.validations.annotations;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @Author giovanni.moratto
 */

public class UniqueValidator implements ConstraintValidator <Unique, String> {

    @PersistenceContext
    private EntityManager em;

    /* Attributes */
    private String object;
    private String field;

    @Override
    public void initialize(Unique constraintAnnotation) {
        object = constraintAnnotation.domainClass().getSimpleName();
        field = constraintAnnotation.fieldName();
    }

    @Override
    @Transactional
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (value == null) {
            return false;
        }

        Query query = em.createQuery("SELECT 1 FROM " + object + " WHERE " + field + " = :VALUE");
        query.setParameter("VALUE", value);

        return query.getResultList().isEmpty();
    }

}