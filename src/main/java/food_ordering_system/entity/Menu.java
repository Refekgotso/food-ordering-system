package food_ordering_system.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Menu entity represents a menu item in the food ordering system.
 * It maps to the "menus" table in the database.
 * Each menu item belongs to one Category via a foreign key (category_id).
 */
@Entity
@Table(name = "menus")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Menu {

    /**
     * Auto-generated unique identifier for each menu item.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The name of the menu item (e.g. "Cheese Burger").
     */
    private String name;

    /**
     * A longer description of the menu item.
     * Length increased to 1000 characters to allow detailed descriptions.
     */
    @Column(length = 1000)
    private String description;

    /**
     * The price of the menu item.
     * BigDecimal is used instead of double to avoid floating point
     * rounding errors when dealing with monetary values.
     */
    private BigDecimal price;

    /**
     * URL pointing to an image of the menu item.
     */
    private String imageUrl;

    /**
     * The category this menu item belongs to.
     * @ManyToOne means many menus can belong to one category.
     * @JoinColumn specifies the foreign key column name in the menus table.
     */
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}