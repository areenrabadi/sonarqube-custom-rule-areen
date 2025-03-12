package com.test.emi.attachment.busniess.exceptions;

import com.dgcash.common.core.exception.DigitalCashException;

public class SettingsNotFoundException extends DigitalCashException {

    private static final String ERROR_CODE = "SETTINGS_NOT_FOUND_EXCEPTION";

    public SettingsNotFoundException() {
        super(ERROR_CODE);
    }
}
