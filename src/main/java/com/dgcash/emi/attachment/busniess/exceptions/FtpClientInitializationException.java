package com.dgcash.emi.attachment.busniess.exceptions;

import com.dgcash.common.core.exception.DigitalCashException;

public class FtpClientInitializationException  extends DigitalCashException {

    private static final String ERROR_CODE = "ERROR_INITIALIZING_FTP_CLIENT";

    public FtpClientInitializationException() {
        super(ERROR_CODE);
    }
}
