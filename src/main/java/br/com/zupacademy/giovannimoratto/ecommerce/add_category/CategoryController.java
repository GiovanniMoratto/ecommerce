package br.com.zupacademy.giovannimoratto.ecommerce.add_category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import javax.validation.Valid;

/**
 * @Author giovanni.moratto
 */

@RestController
@RequestMapping("/api")
public class CategoryController {

    /* Dependencies Injections */
    @Autowired
    private CategoryRepository repository;

    /* Methods */
    // POST Request - Register a new Category
    @PostMapping("/new-category") // Endpoint
    @Transactional
    public ResponseEntity <?> addNewCategory(@RequestBody @Valid CategoryRequest request) {
        CategoryModel newCategory = request.toModel(repository);
        repository.save(newCategory);
        return ResponseEntity.ok().build();
    }

}