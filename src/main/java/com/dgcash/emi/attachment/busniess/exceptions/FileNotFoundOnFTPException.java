package com.dgcash.emi.attachment.busniess.exceptions;

import com.dgcash.common.core.exception.DigitalCashException;

public class FileNotFoundOnFTPException extends DigitalCashException {

    private static final String ERROR_CODE = "FILE_NOT_FOUND_ON_FTP";

    public FileNotFoundOnFTPException() {
        super(ERROR_CODE);
    }
}
