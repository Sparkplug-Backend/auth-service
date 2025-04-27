package com.sparkplug.auth.application.exception;

import com.sparkplug.common.exception.ApplicationException;

public class AlreadyTakenException extends ApplicationException {

    public AlreadyTakenException(String fieldName, String value) {
        super(fieldName + " is already taken: " + value);
    }

    @Override
    public int getStatusCode() {
        return 409;
    }
}
