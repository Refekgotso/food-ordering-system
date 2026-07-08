package food_ordering_system.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * SecurityConfig defines the security filter chain for the application.
 *
 * TEMPORARY STATE: for now, all endpoints are permitted without
 * authentication so registration and login can be built and tested
 * without Spring Security blocking requests. This will be replaced
 * with real public/authenticated/admin-only rules once JWT auth is
 * fully wired up (see Section 7 of the Week 3 spec).
 */
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()
                );

        return http.build();
    }
}