package com.fusionx.lending.transaction.exception;

public class TenantNotFoundException extends RuntimeException {
    public TenantNotFoundException(String exception) {
        super(exception);
    }
}
