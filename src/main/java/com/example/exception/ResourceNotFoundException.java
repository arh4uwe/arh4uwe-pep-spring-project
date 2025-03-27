package com.example.exception;

/**
 * An exception to be thrown if a service tries to find a resource, but cannot find it.
 */
public class ResourceNotFoundException extends RuntimeException {
    /**
     * Creates a new ResourceNotFoundException with the given message.
     * 
     * @param   message   the detail message. The detail message is saved for
     *          later retrieval by the {@link #getMessage()} method.
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
