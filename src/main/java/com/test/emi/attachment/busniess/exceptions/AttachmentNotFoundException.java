package com.test.emi.attachment.busniess.exceptions;

import com.dgcash.common.core.exception.DigitalCashException;

public class AttachmentNotFoundException extends DigitalCashException {

    private static final String ERROR_CODE = "ATTACHMENT_NOT_FOUND";

    public AttachmentNotFoundException() {
        super(ERROR_CODE);
    }
}
