package food_ordering_system.controller;

import food_ordering_system.dto.MenuDto;
import food_ordering_system.response.Response;
import food_ordering_system.service.MenuService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
     * Retrieves all menu items from the database.
     */
    @GetMapping
    public ResponseEntity<Response<List<MenuDto>>> all() {
        return ResponseEntity.ok(menuService.getAllMenus());
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
}