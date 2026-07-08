package food_ordering_system.exception;

/**
 * InactiveAccountException is thrown when a login attempt uses
 * correct credentials, but the account has been deactivated.
 */
public class InactiveAccountException extends RuntimeException {

    public InactiveAccountException(String message) {
        super(message);
    }
}