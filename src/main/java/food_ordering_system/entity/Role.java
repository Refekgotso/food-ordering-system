package food_ordering_system.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Role represents a named permission group (e.g. "ADMIN", "CUSTOMER").
 * Users are linked to Roles via a many-to-many relationship, so a
 * single user could hold multiple roles if needed in the future.
 */
@Entity
@Table(name = "roles")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Role {

    /**
     * Auto-generated unique identifier for each role.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The role name. Must be unique (e.g. "ADMIN", "CUSTOMER").
     * Role IDs should never be hardcoded elsewhere in the app -
     * always look roles up by this name instead.
     */
    @Column(unique = true, nullable = false)
    private String name;
}