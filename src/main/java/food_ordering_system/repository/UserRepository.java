package food_ordering_system.repository;

import food_ordering_system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * UserRepository handles all database operations for the User entity.
 * Provides a lookup by email (used as the login identifier) and an
 * existence check so the registration endpoint can reject duplicate
 * emails before attempting to save.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a user by their email address.
     * Used during login and by the JWT filter to load the
     * authenticated user from the token's email claim.
     */
    Optional<User> findByEmail(String email);

    /**
     * Checks whether a user with the given email already exists.
     * Used during registration to reject duplicate sign-ups.
     */
    boolean existsByEmail(String email);
}