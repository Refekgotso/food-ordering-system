package food_ordering_system.controller;

import food_ordering_system.dto.MenuDto;
import food_ordering_system.response.Response;
import food_ordering_system.service.MenuService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * MenuController handles all incoming HTTP requests
 * related to the Menu resource.
 * It acts as the entry point for menu-related API calls.
 * The controller receives requests, delegates to the service layer,
 * and returns responses wrapped in the standard Response<T> format.
 *
 * @RequiredArgsConstructor generates a constructor for MenuService,
 * enabling constructor injection instead of field injection.
 */
@RestController
@RequestMapping("/api/menu")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    /**
     * POST /api/menu
     * Creates a new menu item.
     * @Valid triggers validation on the incoming MenuDto.
     */
    @PostMapping
    public ResponseEntity<Response<MenuDto>> create(
            @RequestBody @Valid MenuDto dto) {
        return ResponseEntity.ok(menuService.createMenu(dto));
    }

    /**
     * GET /api/menu
     * Retrieves menu items with optional filtering, pagination, and sorting.
     *
     * All query parameters are optional:
     *   categoryId - filter to a specific category
     *   search     - case-insensitive partial match on name
     *   page       - zero-based page number (default 0)
     *   size       - items per page (default 10)
     *   sort       - field and direction, e.g. sort=price,asc
     *
     * Spring automatically builds the Pageable object from the
     * page, size, and sort query params - no manual parsing needed.
     */
    @GetMapping
    public ResponseEntity<Response<Page<MenuDto>>> all(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String search,
            Pageable pageable) {
        return ResponseEntity.ok(menuService.getAllMenus(categoryId, search, pageable));
    }

    /**
     * GET /api/menu/{id}
     * Retrieves a single menu item by its id.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Response<MenuDto>> byId(
            @PathVariable Long id) {
        return ResponseEntity.ok(menuService.getMenuById(id));
    }

    /**
     * PUT /api/menu/{id}
     * Updates an existing menu item.
     * @Valid triggers validation on the incoming MenuDto.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Response<MenuDto>> update(
            @PathVariable Long id,
            @RequestBody @Valid MenuDto dto) {
        return ResponseEntity.ok(menuService.updateMenu(id, dto));
    }

    /**
     * DELETE /api/menu/{id}
     * Deletes a menu item by its id.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Void>> delete(
            @PathVariable Long id) {
        return ResponseEntity.ok(menuService.deleteMenu(id));
    }
}