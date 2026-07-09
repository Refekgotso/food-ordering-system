package food_ordering_system.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * ApplicationConfig is a configuration class for the application.
 * Configuration classes are used to:
 * - Define application-wide settings and beans
 * - Replace XML-based Spring configuration
 * - Set up security, CORS, database connections,
 *   and other application configurations
 *
 * The @Configuration annotation tells Spring that this class
 * contains configuration settings that should be loaded
 * when the application starts.
 */
@Configuration
public class ApplicationConfig {

    /**
     * Exposes a BCrypt PasswordEncoder bean that can be injected
     * anywhere passwords need to be hashed (on registration) or
     * verified (on login). BCrypt automatically handles salting,
     * so we never need to manage salts ourselves.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}