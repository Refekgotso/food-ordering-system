package food_ordering_system.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

/**
 * JwtUtils handles all JWT token operations: generating a signed
 * token for a user's email, extracting the email from a token, and
 * validating that a token is well-formed, correctly signed, and
 * not expired.
 *
 * The secret key and expiration are driven from application.properties
 * rather than hardcoded, so they can differ between environments.
 */
@Component
public class JwtUtils {

    private final SecretKey signingKey;
    private final long expirationMs;

    public JwtUtils(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration}") long expirationMs) {
        this.signingKey = Keys.hmacShaKeyFor(secret.getBytes());
        this.expirationMs = expirationMs;
    }

    /**
     * Generates a signed JWT for the given email, valid for the
     * configured expiration duration.
     */
    public String generateToken(String email) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + expirationMs);

        return Jwts.builder()
                .subject(email)
                .issuedAt(now)
                .expiration(expiry)
                .signWith(signingKey)
                .compact();
    }

    /**
     * Extracts the email (stored as the token's subject) from a
     * valid token.
     */
    public String extractEmail(String token) {
        return parseClaims(token).getSubject();
    }

    /**
     * Validates that a token is correctly signed and not expired.
     * Returns false (rather than throwing) for any malformed,
     * tampered, or expired token, so callers can treat validation
     * as a simple true/false check.
     */
    public boolean isTokenValid(String token) {
        try {
            Claims claims = parseClaims(token);
            return claims.getExpiration().after(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    private Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(signingKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}