package food_ordering_system.service;

import food_ordering_system.dto.MenuDto;
import food_ordering_system.entity.Category;
import food_ordering_system.entity.Menu;
import food_ordering_system.exception.CategoryNotFoundException;
import food_ordering_system.repository.CategoryRepository;
import food_ordering_system.repository.MenuRepository;
import food_ordering_system.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * MenuServiceImpl implements the MenuService interface.
 * This is where the actual business logic for menu operations lives.
 * It retrieves data from the repository, validates it, maps between
 * entities and DTOs, and returns wrapped Response objects.
 *
 * @RequiredArgsConstructor generates a constructor for all final fields,
 * enabling constructor injection of dependencies.
 */
@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;
    private final CategoryRepository categoryRepository;

    /**
     * Creates a new menu item.
     * Validates that the category exists before saving.
     * Maps the DTO to an entity, saves it, then maps back to DTO.
     */
    @Override
    public Response<MenuDto> createMenu(MenuDto dto) {
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException(
                        "Category not found with id: " + dto.getCategoryId()));

        Menu menu = mapToEntity(dto, category);
        Menu saved = menuRepository.save(menu);
        return Response.success("Menu created successfully", mapToDto(saved));
    }

    /**
     * Retrieves all menu items from the database.
     * Maps each Menu entity to a MenuDto before returning.
     */
    @Override
    public Response<List<MenuDto>> getAllMenus() {
        List<MenuDto> menus = menuRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .toList();
        return Response.success("Menus retrieved successfully", menus);
    }

    /**
     * Retrieves a single menu item by its id.
     * Throws CategoryNotFoundException if no menu exists with that id.
     */
    @Override
    public Response<MenuDto> getMenuById(Long id) {
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(
                        "Menu not found with id: " + id));
        return Response.success("Menu retrieved successfully", mapToDto(menu));
    }

    /**
     * Private helper method to map a Menu entity to a MenuDto.
     * Centralizes mapping logic so it's not repeated across methods.
     */
    private MenuDto mapToDto(Menu menu) {
        MenuDto dto = new MenuDto();
        dto.setId(menu.getId());
        dto.setName(menu.getName());
        dto.setDescription(menu.getDescription());
        dto.setPrice(menu.getPrice());
        dto.setImageUrl(menu.getImageUrl());
        dto.setCategoryId(menu.getCategory().getId());
        dto.setCategoryName(menu.getCategory().getName());
        return dto;
    }

    /**
     * Private helper method to map a MenuDto to a Menu entity.
     * Takes the already-fetched Category object as a parameter
     * since it was validated and loaded before this method is called.
     */
    private Menu mapToEntity(MenuDto dto, Category category) {
        return Menu.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .imageUrl(dto.getImageUrl())
                .category(category)
                .build();
    }
}