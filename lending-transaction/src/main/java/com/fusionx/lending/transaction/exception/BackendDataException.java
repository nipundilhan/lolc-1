package com.fusionx.lending.transaction.exception;

public class BackendDataException extends RuntimeException {

    private final String code;

    public BackendDataException(String exception, String code) {
        super(exception);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
