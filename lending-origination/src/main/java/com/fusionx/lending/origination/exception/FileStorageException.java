package com.fusionx.lending.origination.exception;


public class FileStorageException extends RuntimeException {
	private final String details;
    public FileStorageException(String message, String details) {
        super(message);
        this.details=details;
    }
    public String getDetails() {
		return details;
	}
}
