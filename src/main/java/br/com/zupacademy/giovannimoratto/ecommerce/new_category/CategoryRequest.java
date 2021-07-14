package br.com.zupacademy.giovannimoratto.ecommerce.new_category;

import br.com.zupacademy.giovannimoratto.ecommerce.validations.annotations.ExistsId;
import br.com.zupacademy.giovannimoratto.ecommerce.validations.annotations.Unique;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

/**
 * @Author giovanni.moratto
 */

public class CategoryRequest {

    /* Attributes */
    @NotBlank
    @Unique(domainClass = CategoryModel.class, fieldName = "name")
    private final String name;
    @ExistsId(domainClass = CategoryModel.class)
    private final Long idParentCategory;

    public CategoryRequest(String name, Long idParentCategory) {
        this.name = name;
        this.idParentCategory = idParentCategory;
    }

    /* Methods */
    public CategoryModel toModel(CategoryRepository repository) {
        if (idParentCategory != null) {
            CategoryModel optionalCategory = repository.findById(idParentCategory).orElseThrow();
            return new CategoryModel(name, optionalCategory);
        }
        return new CategoryModel(name);
    }

}