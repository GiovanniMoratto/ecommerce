package br.com.zupacademy.giovannimoratto.ecommerce.validations.annotations;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @Author giovanni.moratto
 */

public class ExistsIdValidator implements ConstraintValidator <ExistsId, Long> {

    @PersistenceContext
    private EntityManager em;

    /* Attributes */
    private String object;

    @Override
    public void initialize(ExistsId constraintAnnotation) {
        object = constraintAnnotation.domainClass().getSimpleName();
    }

    @Override
    public boolean isValid(Long id, ConstraintValidatorContext context) {
        if (id == null) {
            return true;
        }
        Query query = em.createQuery("SELECT 1 FROM " + object + " o WHERE o.id = :VALUE");
        query.setParameter("VALUE", id);

        return !query.getResultList().isEmpty();
    }

}