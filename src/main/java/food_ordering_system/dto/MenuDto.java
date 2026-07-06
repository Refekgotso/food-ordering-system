package food_ordering_system.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

/**
 * MenuDto (Data Transfer Object) is used to transfer menu data
 * between the service layer and the controller.
 * It prevents exposing the full entity/database structure directly.
 * Validation annotations ensure incoming data meets required rules
 * before reaching the business logic.
 */
public class MenuDto {

    /**
     * The unique identifier of the menu item.
     * Only populated in responses, not required in requests.
     */
    private Long id;

    /**
     * The name of the menu item.
     * Must not be blank.
     */
    @NotBlank(message = "Menu name is required")
    private String name;

    /**
     * A description of the menu item.
     * Optional field.
     */
    private String description;

    /**
     * The price of the menu item.
     * Must not be null and must be at least 0.0.
     */
    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", message = "Price must be a positive value")
    private BigDecimal price;

    /**
     * URL pointing to an image of the menu item.
     * Optional field.
     */
    private String imageUrl;

    /**
     * The id of the category this menu item belongs to.
     * Used as input when creating or updating a menu item.
     * Must not be null.
     */
    @NotNull(message = "Category ID is required")
    private Long categoryId;

    /**
     * The name of the category this menu item belongs to.
     * Output only - populated from the related Category entity.
     * Not required in incoming requests.
     */
    private String categoryName;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }
    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }
}