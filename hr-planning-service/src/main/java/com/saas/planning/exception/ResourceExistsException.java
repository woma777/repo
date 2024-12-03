package com.saas.planning.exception;

public class ResourceExistsException extends RuntimeException {
    public ResourceExistsException( String message) {
        super(message);
    }
}

