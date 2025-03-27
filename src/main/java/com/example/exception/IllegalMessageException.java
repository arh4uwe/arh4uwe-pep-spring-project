package com.example.exception;

/**
 * An exception to be thrown if the user tries to create an invalid message.
 */
public class IllegalMessageException extends RuntimeException {
    /**
     * Creates a new IllegalMessageException with the given message.
     * 
     * @param   message   the detail message. The detail message is saved for
     *          later retrieval by the {@link #getMessage()} method.
     */
    public IllegalMessageException(String message) {
        super(message);
    }
}
