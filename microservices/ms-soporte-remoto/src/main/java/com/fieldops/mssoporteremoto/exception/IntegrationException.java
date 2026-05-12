package com.fieldops.mssoporteremoto.exception;

public class IntegrationException extends RuntimeException {
    public IntegrationException(String message, Throwable cause) {
        super(message, cause);
    }
}