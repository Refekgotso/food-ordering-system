package food_ordering_system.repository;

import food_ordering_system.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * CategoryRepository is responsible for all database operations
 * related to the Category entity.
 * By extending JpaRepository, it automatically provides methods like:
 * - findAll() - retrieve all categories
 * - findById() - retrieve a category by ID
 * - save() - save or update a category
 * - delete() - delete a category
 * No additional code is needed for basic database operations.
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {

}