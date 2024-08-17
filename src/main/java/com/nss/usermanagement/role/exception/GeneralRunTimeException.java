package com.nss.usermanagement.role.exception;

public class GeneralRunTimeException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public GeneralRunTimeException(String message) {
        super(message);
    }

    public GeneralRunTimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
