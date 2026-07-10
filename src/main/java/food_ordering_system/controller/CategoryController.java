package food_ordering_system.controller;

import food_ordering_system.dto.CategoryDto;
import food_ordering_system.response.Response;
import food_ordering_system.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import jakarta.validation.Valid;

import java.util.List;

/**
 * CategoryController handles all incoming HTTP requests
 * related to the Category resource.
 * It acts as the entry point of the application from outside.
 * The controller receives requests, calls the service layer,
 * and returns the response to the client wrapped in a standard
 * Response<T> object for a consistent API shape.
 *
 * @RestController combines @Controller and @ResponseBody
 * @RequestMapping defines the base URL for all endpoints
 */
@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    /**
     * Injects the CategoryService to handle business logic.
     * Spring automatically provides the implementation.
     */
    @Autowired
    private CategoryService categoryService;

    /**
     * GET /api/categories
     * Retrieves all categories from the database, wrapped in a
     * standard Response<T> object.
     */
    @GetMapping
    public ResponseEntity<Response<List<CategoryDto>>> getAllCategories() {
        List<CategoryDto> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(Response.success("Categories retrieved", categories));
    }

    /**
     * GET /api/categories/{id}
     * Retrieves a single category by its id, wrapped in a
     * standard Response<T> object.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Response<CategoryDto>> getCategoryById(@PathVariable Long id) {
        CategoryDto dto = categoryService.getCategoryById(id);
        return ResponseEntity.ok(Response.success("Category retrieved", dto));
    }

    /**
     * POST /api/categories
     * Creates a new category.
     * @Valid triggers Jakarta Bean Validation on the incoming dto
     * (checks @NotBlank and @Size rules defined in CategoryDto).
     * If validation fails, the GlobalExceptionHandler returns a 400.
     */
    @PostMapping
    public ResponseEntity<Response<CategoryDto>> addCategory(@RequestBody @Valid CategoryDto dto) {
        CategoryDto created = categoryService.addCategory(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Response.success("Category created", created));
    }

    /**
     * PUT /api/categories/{id}
     * Updates an existing category's name.
     * @Valid ensures the new name passes validation rules.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Response<CategoryDto>> updateCategory(@PathVariable Long id, @RequestBody @Valid CategoryDto dto) {
        CategoryDto updated = categoryService.updateCategory(id, dto);
        return ResponseEntity.ok(Response.success("Category updated", updated));
    }

    /**
     * DELETE /api/categories/{id}
     * Deletes an existing category.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Void>> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok(Response.success("Category deleted", null));
    }
}