package food_ordering_system.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * User represents an account in the food ordering system.
 * Passwords are always stored as a BCrypt hash - never in plain text.
 * Each user holds one or more Roles via a many-to-many relationship,
 * mapped through a join table named "users_roles".
 */
@Entity
@Table(name = "users")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    /**
     * Auto-generated unique identifier for each user.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The user's full name.
     */
    @NotBlank
    private String name;

    /**
     * The user's email address. Must be unique across all users
     * and used as the login identifier.
     */
    @Email
    @Column(unique = true, nullable = false)
    private String email;

    /**
     * The user's password, stored as a BCrypt hash.
     * NEVER store or log the raw plain-text password.
     */
    @Column(nullable = false)
    private String password;

    /**
     * The user's contact phone number.
     */
    private String phoneNumber;

    /**
     * The user's delivery/home address. Optional.
     */
    private String address;

    /**
     * URL to the user's profile picture. Nullable for now -
     * not required at registration time.
     */
    private String profileUrl;

    /**
     * Whether the account is active. Inactive accounts are blocked
     * from logging in, even with correct credentials.
     */
    @Builder.Default
    private boolean active = true;

    /**
     * Timestamp of when the account was created.
     * Set automatically the first time the entity is saved.
     */
    private LocalDateTime createdAt;

    /**
     * The roles this user holds (e.g. CUSTOMER, ADMIN).
     * Many-to-many because a user could hold more than one role,
     * and a role can belong to many users. Mapped through a join
     * table called "users_roles".
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles;

    /**
     * Sets createdAt automatically right before the entity is first
     * persisted to the database, so we never rely on the client or
     * service layer to remember to set it.
     */
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}