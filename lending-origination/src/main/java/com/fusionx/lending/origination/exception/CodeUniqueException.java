package com.fusionx.lending.origination.exception;

public class CodeUniqueException extends RuntimeException{
	
	private static final long serialVersionUID = 1213132313L;

	public CodeUniqueException (String exception) {
		super(exception);
	}

}
