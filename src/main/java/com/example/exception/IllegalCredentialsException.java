package com.example.exception;

/**
 * An exception to be thrown if the user submits an invalid username or password during registration.
 */
public class IllegalCredentialsException extends Exception {
    /**
     * Creates a new IllegalCredentialsException with the given message.
     * 
     * @param   message   the detail message. The detail message is saved for
     *          later retrieval by the {@link #getMessage()} method.
     */
    public IllegalCredentialsException(String message) {
        super(message);
    }
}
