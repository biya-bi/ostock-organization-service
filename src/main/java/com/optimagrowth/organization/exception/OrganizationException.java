package com.optimagrowth.organization.exception;

public class OrganizationException extends RuntimeException {

    public OrganizationException(String message) {
        super(message);
    }

    public OrganizationException(Throwable cause) {
        super(cause);
    }

    public OrganizationException(String message, Throwable cause) {
        super(message, cause);
    }

}
