package com.dgcash.emi.attachment.busniess.exceptions;

import com.dgcash.common.core.exception.DigitalCashException;

public class FileSizeExceedsMaxLimitException extends DigitalCashException {

    private static final String ERROR_CODE = "FILE_SIZE_EXCEEDS_MAX_LIMIT";

    public FileSizeExceedsMaxLimitException() {
        super(ERROR_CODE);
    }
}
