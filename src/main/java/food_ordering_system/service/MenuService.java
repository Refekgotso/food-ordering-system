package food_ordering_system.service;

import food_ordering_system.dto.MenuDto;
import food_ordering_system.response.Response;

import java.util.List;

/**
 * MenuService defines the business logic operations for the Menu entity.
 * It acts as a contract between the controller and the service implementation.
 */
public interface MenuService {

    Response<MenuDto> createMenu(MenuDto dto);

    Response<List<MenuDto>> getAllMenus();

    Response<MenuDto> getMenuById(Long id);
}