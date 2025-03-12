package com.test.emi.attachment.busniess.exceptions;

import com.dgcash.common.core.exception.DigitalCashException;

public class InvalidFieldsNumberException extends DigitalCashException {

    private static final String ERROR_CODE = "INVALID_FIELDS_NUMBER";

    public InvalidFieldsNumberException() {
        super(ERROR_CODE);
    }
}
