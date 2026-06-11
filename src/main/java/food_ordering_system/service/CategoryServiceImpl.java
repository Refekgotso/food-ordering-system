package food_ordering_system.service;

import food_ordering_system.dto.CategoryDto;
import food_ordering_system.entity.Category;
import food_ordering_system.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * CategoryServiceImpl is the implementation of CategoryService.
 * This is where the actual business logic lives.
 * It retrieves data from the repository and converts
 * Category entities into CategoryDto objects before
 * returning them to the controller.
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    /**
     * Injects the CategoryRepository to access the database.
     * Spring automatically provides the implementation.
     */
    @Autowired
    private CategoryRepository categoryRepository;

    /**
     * Retrieves all categories from the database.
     * Converts each Category entity to a CategoryDto object.
     * @return a list of CategoryDto objects
     */
    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(cat -> {
            CategoryDto dto = new CategoryDto();
            dto.setId(cat.getId());
            dto.setName(cat.getName());
            return dto;
        }).collect(Collectors.toList());
    }
}