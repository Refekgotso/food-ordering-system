package food_ordering_system.security;

import food_ordering_system.entity.Role;
import food_ordering_system.entity.User;
import food_ordering_system.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * JwtAuthenticationFilter runs exactly once per request (via
 * OncePerRequestFilter). It reads the Authorization header, and if
 * a valid "Bearer <token>" is present, loads the corresponding user
 * and populates the SecurityContext with their roles mapped to
 * GrantedAuthority objects - so the rest of Spring Security knows
 * who is calling and what they're allowed to do.
 *
 * If no header is present, or the token is invalid/expired, the
 * request simply continues unauthenticated. This filter must never
 * throw an exception that would turn into a 500 - an invalid token
 * should just mean "not logged in", handled later by the security
 * filter chain's access rules (401/403).
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        System.out.println(">>> JwtAuthenticationFilter - Authorization header: " + authHeader);

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            System.out.println(">>> Extracted token: " + token);

            boolean valid = jwtUtils.isTokenValid(token);
            System.out.println(">>> Token valid? " + valid);

            if (valid) {
                String email = jwtUtils.extractEmail(token);
                System.out.println(">>> Extracted email: " + email);

                Optional<User> userOptional = userRepository.findByEmail(email);
                System.out.println(">>> User found? " + userOptional.isPresent());

                if (userOptional.isPresent()) {
                    User user = userOptional.get();

                    List<GrantedAuthority> authorities = user.getRoles().stream()
                            .map(Role::getName)
                            .map(SimpleGrantedAuthority::new)
                            .map(GrantedAuthority.class::cast)
                            .toList();

                    System.out.println(">>> Authorities granted: " + authorities);

                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(user, null, authorities);

                    authToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        }

        // Always continue the chain - whether authenticated or not.
        // If no valid token was found, the request proceeds
        // unauthenticated, and the security filter chain's access
        // rules decide whether that's allowed (401/403 as needed).
        filterChain.doFilter(request, response);
    }
}