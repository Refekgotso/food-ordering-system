package food_ordering_system.config;

import food_ordering_system.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * SecurityConfig defines the real access rules for the API:
 *
 *   Public (no token needed):
 *     - POST /api/auth/register, POST /api/auth/login
 *     - GET  /api/categories/**, GET /api/menu/**, GET /api/reviews/**
 *
 *   ADMIN only (token required + ADMIN authority):
 *     - POST/PUT/DELETE on /api/categories/** and /api/menu/**
 *
 *   Any authenticated user:
 *     - everything else not listed above
 *
 * Sessions are stateless (JWT carries all auth state on every
 * request), CSRF is disabled (this is a token-based REST API, not a
 * browser form-based app), and CORS is restricted to the frontend's
 * dev origin. The JWT filter runs before Spring's default
 * username/password filter so authentication is resolved from the
 * token before any default filter logic runs.
 */
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // Public endpoints - no token needed
                        .requestMatchers("/api/auth/register", "/api/auth/login").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/categories/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/menu/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/reviews/**").permitAll()

                        // ADMIN only - write operations on categories and menu
                        .requestMatchers(HttpMethod.POST, "/api/categories/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/categories/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/categories/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/menu/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/menu/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/menu/**").hasAuthority("ADMIN")

                        // Everything else requires a logged-in user
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * Restricts CORS to the frontend's local development origin,
     * allowing the standard HTTP methods and any headers (needed
     * for the Authorization header carrying the JWT).
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:5173"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}