package food_ordering_system.repository;

import food_ordering_system.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * MenuRepository handles all database operations for the Menu entity.
 * By extending JpaRepository, it automatically provides methods like:
 * - findAll() - retrieve all menus
 * - findById() - retrieve a menu by ID
 * - save() - save or update a menu
 * - deleteById() - delete a menu by ID
 * No additional code is needed for basic database operations.
 */
public interface MenuRepository extends JpaRepository<Menu, Long> {
}