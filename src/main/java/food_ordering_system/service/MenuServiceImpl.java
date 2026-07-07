package food_ordering_system.service;

import food_ordering_system.dto.MenuDto;
import food_ordering_system.entity.Category;
import food_ordering_system.entity.Menu;
import food_ordering_system.exception.CategoryNotFoundException;
import food_ordering_system.exception.MenuNotFoundException;
import food_ordering_system.repository.CategoryRepository;
import food_ordering_system.repository.MenuRepository;
import food_ordering_system.response.Response;
import food_ordering_system.specification.MenuSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 * MenuServiceImpl contains the actual business logic for the Menu
 * feature: validating that a category exists before saving, mapping
 * between Menu entities and MenuDto objects, and applying dynamic
 * filters + pagination when listing menus.
 */
@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Response<MenuDto> createMenu(MenuDto dto) {
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException(
                        "Category not found with id: " + dto.getCategoryId()));

        Menu menu = mapToEntity(dto, category);
        Menu saved = menuRepository.save(menu);

        return Response.success("Menu created", mapToDto(saved));
    }

    @Override
    public Response<Page<MenuDto>> getAllMenus(Long categoryId, String search, Pageable pageable) {
        Specification<Menu> spec = Specification
                .where(MenuSpecification.hasCategoryId(categoryId))
                .and(MenuSpecification.nameContains(search));

        Page<MenuDto> page = menuRepository.findAll(spec, pageable)
                .map(this::mapToDto);

        return Response.success("Menus retrieved", page);
    }

    @Override
    public Response<MenuDto> getMenuById(Long id) {
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new MenuNotFoundException("Menu not found with id: " + id));

        return Response.success("Menu retrieved", mapToDto(menu));
    }

    @Override
    public Response<MenuDto> updateMenu(Long id, MenuDto dto) {
        Menu existing = menuRepository.findById(id)
                .orElseThrow(() -> new MenuNotFoundException("Menu not found with id: " + id));

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException(
                        "Category not found with id: " + dto.getCategoryId()));

        existing.setName(dto.getName());
        existing.setDescription(dto.getDescription());
        existing.setPrice(dto.getPrice());
        existing.setImageUrl(dto.getImageUrl());
        existing.setCategory(category);

        Menu updated = menuRepository.save(existing);

        return Response.success("Menu updated", mapToDto(updated));
    }

    @Override
    public Response<Void> deleteMenu(Long id) {
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new MenuNotFoundException("Menu not found with id: " + id));

        menuRepository.delete(menu);

        return Response.success("Menu deleted", null);
    }

    /**
     * Maps a Menu entity to a MenuDto for use in API responses.
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
     * Maps an incoming MenuDto (plus the already-validated Category)
     * into a new Menu entity ready to be saved.
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