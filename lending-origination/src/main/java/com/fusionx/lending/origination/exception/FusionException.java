package com.fusionx.lending.origination.exception;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FusionException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	
	@JsonIgnore
	private HttpStatus status;
	
	@JsonProperty(value="message")
	private String message;
	
	private boolean isJsonObject;
	
	public FusionException (String exception) {
		super(exception);
	}
	
	public FusionException (String exception, HttpStatus status) {
		super(exception);
		this.message = exception;
		this.status = status;
	}
	
	public FusionException (String exception, HttpStatus status, boolean isJsonObject) {
		super(exception);
		this.message = exception;
		this.status = status;
		this.isJsonObject = isJsonObject;
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

	public boolean isJsonObject() {
		return isJsonObject;
	}
	public void setJsonObject(boolean isJsonObject) {
		this.isJsonObject = isJsonObject;
	}
	
	
}
