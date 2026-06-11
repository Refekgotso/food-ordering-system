package food_ordering_system.dto;

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
     */
    private String name;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}