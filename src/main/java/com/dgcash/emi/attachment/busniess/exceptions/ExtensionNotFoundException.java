package com.dgcash.emi.attachment.busniess.exceptions;

import com.dgcash.common.core.exception.DigitalCashException;

public class ExtensionNotFoundException extends DigitalCashException {

    private static final String ERROR_CODE = "EXTENSION_NOT_FOUND";

    public ExtensionNotFoundException() {
        super(ERROR_CODE);
    }
}
