package com.dgcash.emi.attachment.busniess.exceptions;

import com.dgcash.common.core.exception.DigitalCashException;

public class FileExtensionNotAcceptedException extends DigitalCashException {

    private static final String ERROR_CODE = "FILE_EXTENSION_NOT_ACCEPTED";

    public FileExtensionNotAcceptedException() {
        super(ERROR_CODE);
    }
}
