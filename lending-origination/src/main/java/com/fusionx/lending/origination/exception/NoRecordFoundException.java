package com.fusionx.lending.origination.exception;

public class NoRecordFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 4545342352L;

	public NoRecordFoundException (String exception) {
		super(exception);
	}

}
