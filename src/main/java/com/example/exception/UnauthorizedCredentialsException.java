package com.example.exception;

/**
 * An exception to be thrown if the user tries to login with an unauthorized username/password combination.
 */
public class UnauthorizedCredentialsException extends RuntimeException {
    /**
     * Creates a new UnauthorizedCredentialsException with the given message.
     * 
     * @param   message   the detail message. The detail message is saved for
     *          later retrieval by the {@link #getMessage()} method.
     */
    public UnauthorizedCredentialsException(String message) {
        super(message);
    }
}
