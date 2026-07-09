package food_ordering_system.exception;

/**
 * EmailAlreadyExistsException is thrown when a registration attempt
 * uses an email address that is already associated with an existing
 * account.
 *
 * By extending RuntimeException, this exception does not need to be
 * declared or caught explicitly.
 */
public class EmailAlreadyExistsException extends RuntimeException {

    public EmailAlreadyExistsException(String message) {
        super(message);
    }
}