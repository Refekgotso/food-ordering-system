package food_ordering_system.service;

import food_ordering_system.dto.MenuDto;
import food_ordering_system.response.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * MenuService defines the business operations available for the
 * Menu feature. The controller depends only on this interface,
 * not the implementation, which keeps the API layer decoupled
 * from the actual business logic.
 */
public interface MenuService {

    /**
     * Creates a new menu item after validating that the referenced
     * category exists.
     */
    Response<MenuDto> createMenu(MenuDto dto);

    /**
     * Retrieves menus with optional filtering by categoryId and/or
     * search term, plus pagination and sorting support via Pageable.
     */
    Response<Page<MenuDto>> getAllMenus(Long categoryId, String search, Pageable pageable);

    /**
     * Retrieves a single menu item by its ID.
     */
    Response<MenuDto> getMenuById(Long id);

    /**
     * Updates an existing menu item, validating that both the menu
     * and the referenced category exist.
     */
    Response<MenuDto> updateMenu(Long id, MenuDto dto);

    /**
     * Deletes a menu item by its ID.
     */
    Response<Void> deleteMenu(Long id);
}