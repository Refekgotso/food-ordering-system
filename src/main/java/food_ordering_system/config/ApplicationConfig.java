package food_ordering_system.config;

import org.springframework.context.annotation.Configuration;

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
     * Additional configuration beans can be defined here.
     * For example: password encoders, CORS settings,
     * or custom bean definitions.
     */
}