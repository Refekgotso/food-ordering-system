package food_ordering_system.config;

import food_ordering_system.entity.Role;
import food_ordering_system.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * RoleSeeder ensures the ADMIN and CUSTOMER roles always exist in the
 * database, regardless of environment. It runs automatically once on
 * every application startup via CommandLineRunner.
 *
 * Each role is only inserted if it doesn't already exist, so this is
 * safe to run repeatedly without creating duplicates. Role IDs should
 * never be hardcoded anywhere else in the app - always look roles up
 * by name via RoleRepository.findByName(...).
 */
@Component
@RequiredArgsConstructor
public class RoleSeeder implements CommandLineRunner {

    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) {
        seedRoleIfMissing("ADMIN");
        seedRoleIfMissing("CUSTOMER");
    }

    private void seedRoleIfMissing(String roleName) {
        roleRepository.findByName(roleName)
                .orElseGet(() -> roleRepository.save(
                        Role.builder().name(roleName).build()
                ));
    }
}