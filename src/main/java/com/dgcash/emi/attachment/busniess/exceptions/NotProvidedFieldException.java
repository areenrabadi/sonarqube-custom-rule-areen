package com.dgcash.emi.attachment.busniess.exceptions;

public class NotProvidedFieldException extends RuntimeException {

    private static final String ERROR_CODE = "NOT_PROVIDED_FIELD";

    public NotProvidedFieldException() {
        super(ERROR_CODE);
    }
}
