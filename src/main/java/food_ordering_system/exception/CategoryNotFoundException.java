package food_ordering_system.exception;

/**
 * CategoryNotFoundException is a custom exception class.
 * It is thrown when a requested category is not found
 * in the database.
 *
 * Custom exceptions are used to:
 * - Provide meaningful error messages to the client
 * - Handle specific error scenarios gracefully
 * - Avoid exposing raw system errors to the outside world
 *
 * By extending RuntimeException, this exception does not
 * need to be declared or caught explicitly.
 */
public class CategoryNotFoundException extends RuntimeException {

    /**
     * Constructor that accepts a custom error message.
     * @param message the error message to display
     */
    public CategoryNotFoundException(String message) {
        super(message);
    }
}