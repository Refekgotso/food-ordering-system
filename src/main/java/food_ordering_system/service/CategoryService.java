package food_ordering_system.service;

import food_ordering_system.dto.CategoryDto;
import java.util.List;

/**
 * CategoryService is the service interface that defines
 * the business logic operations for the Category entity.
 * It acts as a contract between the controller and
 * the service implementation layer.
 */
public interface CategoryService {

    /**
     * Retrieves all categories from the database.
     * @return a list of CategoryDto objects
     */
    List<CategoryDto> getAllCategories();
    /**
     * Retrieves a single category by its id.
     * @param id the category id
     * @return the matching CategoryDto
     */
    CategoryDto getCategoryById(Long id);
}