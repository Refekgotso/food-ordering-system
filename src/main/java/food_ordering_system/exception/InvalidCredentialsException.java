package food_ordering_system.exception;

/**
 * InvalidCredentialsException is thrown when a login attempt fails
 * due to either a non-existent email OR an incorrect password.
 *
 * IMPORTANT: both cases must use this SAME exception with the SAME
 * generic message. Never reveal to the client which one was wrong -
 * doing so would let an attacker discover which emails are
 * registered by observing different error messages.
 */
public class InvalidCredentialsException extends RuntimeException {

    public InvalidCredentialsException(String message) {
        super(message);
    }
}