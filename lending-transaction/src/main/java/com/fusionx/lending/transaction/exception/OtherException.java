package com.fusionx.lending.transaction.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;

public class OtherException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    @JsonIgnore
    private HttpStatus status;

    @JsonProperty(value = "message")
    private String message;

    private boolean isJsonObject;

    public OtherException(String exception) {
        super(exception);
    }

    public OtherException(String exception, HttpStatus status) {
        super(exception);
        this.message = exception;
        this.status = status;
    }

    public OtherException(String exception, HttpStatus status, boolean isJsonObject) {
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
