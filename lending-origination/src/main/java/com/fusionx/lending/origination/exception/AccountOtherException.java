package com.fusionx.lending.origination.exception;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AccountOtherException extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@JsonIgnore
	private HttpStatus status;
	
	@JsonProperty(value="message")
	private String message;
	
	public AccountOtherException (String exception) {
		super(exception);
	}
	
	public AccountOtherException (String exception, HttpStatus status) {
		super(exception);
		this.message = exception;
		this.status = status;
	}
	
	public HttpStatus getStatus() {
		return status;
	}
	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
