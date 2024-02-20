package com.dgcash.emi.attachment.busniess.exceptions;

import com.dgcash.common.core.exception.DigitalCashException;

public class UploadFileException extends DigitalCashException {

    private static final String ERROR_CODE = "UPLOAD_FILE_EXCEPTION";

    public UploadFileException() {
        super(ERROR_CODE);
    }
}
