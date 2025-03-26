package com.example.exception;

/**
 * An exception to be thrown if the user tries to register a username already in use.
 */
public class DuplicateUsernameException extends Exception {
    /**
     * Creates a new DuplicateUsernameException with the given message.
     * 
     * @param   message   the detail message. The detail message is saved for
     *          later retrieval by the {@link #getMessage()} method.
     */
    public DuplicateUsernameException(String message) {
        super(message);
    }
}
