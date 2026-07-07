package food_ordering_system.repository;

import food_ordering_system.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * MenuRepository handles all database operations for the Menu entity.
 * By extending JpaRepository, it automatically provides methods like:
 * - findAll() - retrieve all menus
 * - findById() - retrieve a menu by ID
 * - save() - save or update a menu
 * - deleteById() - delete a menu by ID
 *
 * By also extending JpaSpecificationExecutor, we get support for
 * dynamic queries (Specifications). This lets us filter menus by
 * categoryId and/or search term in any combination, without writing
 * a separate repository method for every possible combination of
 * filters.
 */
public interface MenuRepository extends JpaRepository<Menu, Long>, JpaSpecificationExecutor<Menu> {
}