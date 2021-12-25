package com.fusionx.lending.origination.exception;

public class RentalCalculationException extends RuntimeException{

	private final String errorDesc;
	private final String status;
	public RentalCalculationException(String exception, String errorDesc, String status) {
		super(exception);
		this.errorDesc=errorDesc;
		this.status=status;
	}
	
	public String getErrorDesc() {
		return errorDesc;
	}
	
	public String getStatus() {
		return status;
	}
}
