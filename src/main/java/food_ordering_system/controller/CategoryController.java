package food_ordering_system.controller;

import food_ordering_system.dto.CategoryDto;
import food_ordering_system.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * CategoryController handles all incoming HTTP requests
 * related to the Category resource.
 * It acts as the entry point of the application from outside.
 * The controller receives requests, calls the service layer,
 * and returns the response to the client.
 *
 * @RestController combines @Controller and @ResponseBody
 * @RequestMapping defines the base URL for all endpoints
 */
@RestController
@RequestMapping("/api/category")
public class CategoryController {

    /**
     * Injects the CategoryService to handle business logic.
     * Spring automatically provides the implementation.
     */
    @Autowired
    private CategoryService categoryService;

    /**
     * GET /api/category
     * Retrieves all categories from the database.
     * @return a list of CategoryDto objects in JSON format
     */
    @GetMapping
    public List<CategoryDto> getAllCategories() {
        return categoryService.getAllCategories();
    }
    /**
     * GET /api/category/{id}
     * Retrieves a single category by its id.
     */
    @GetMapping("/{id}")
    public CategoryDto getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryById(id);
    }

    /**
     * POST /api/category
     * Creates a new category.
     */
    @PostMapping
    public ResponseEntity<CategoryDto> addCategory(@RequestBody CategoryDto dto) {
        CategoryDto created = categoryService.addCategory(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}