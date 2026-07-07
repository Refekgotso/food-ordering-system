package food_ordering_system.repository;

import food_ordering_system.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * RoleRepository handles all database operations for the Role entity.
 * Provides a lookup by name so roles can always be found by their
 * string value (e.g. "ADMIN", "CUSTOMER") rather than a hardcoded id.
 */
public interface RoleRepository extends JpaRepository<Role, Long> {

    /**
     * Finds a role by its unique name.
     */
    Optional<Role> findByName(String name);
}