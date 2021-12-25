package com.fusionx.lending.transaction.exception;

import javax.persistence.PersistenceException;

public class OptimisticLockException extends PersistenceException {

    private static final long serialVersionUID = 1L;

    private final String field;

    public OptimisticLockException(String exception, String field) {
        super(exception);
        this.field = field;
    }

    public String getField() {
        return this.field;
    }
}