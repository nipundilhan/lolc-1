package com.fusionx.lending.origination.exception;

public class TenantNotFoundException extends RuntimeException{
	public TenantNotFoundException(String exception) {
		super(exception);
	}
}
