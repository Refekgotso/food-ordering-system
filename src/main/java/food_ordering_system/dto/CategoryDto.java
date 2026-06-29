package food_ordering_system.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * CategoryDto (Data Transfer Object) is used to transfer
 * category data between the service layer and the controller.
 * It prevents exposing the full entity/database structure
 * directly to the outside world (API consumers).
 * Only the fields needed by the client are included here.
 */
public class CategoryDto {

    /**
     * The unique identifier of the category.
     */
    private Long id;

    /**
     * The name of the category.
     * Must not be blank, and must be between 2 and 50 characters.
     */
    @NotBlank(message = "Category name is required")
    @Size(min = 2, max = 50, message = "Name must be 2-50 characters")
    private String name;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}