package com.sirma.pms.exception;

/**
 * this is a custom exception to handle project not found
 */
public class ProjectNotFoundException extends Exception{

    public ProjectNotFoundException() {
        super();
    }

    public ProjectNotFoundException(final String message) {
        super(message);
    }
}
