package com.fusionx.lending.product.exception;

public class TenantNotFoundException extends RuntimeException{
	public TenantNotFoundException(String exception) {
		super(exception);
	}
}
