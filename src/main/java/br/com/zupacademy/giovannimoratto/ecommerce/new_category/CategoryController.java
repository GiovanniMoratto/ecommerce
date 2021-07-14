package br.com.zupacademy.giovannimoratto.ecommerce.new_category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import javax.validation.Valid;

/**
 * @Author giovanni.moratto
 */

@RestController
public class CategoryController {

    @Autowired
    private CategoryRepository repository;

    /* Methods */
    // POST Request - Register a new Category
    @PostMapping("/new_category") // Endpoint
    @Transactional
    public void addNewCategory(@RequestBody @Valid CategoryRequest request) {
        CategoryModel newCategory = request.toModel(repository);
        repository.save(newCategory);
    }

}