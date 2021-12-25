package com.fusionx.lending.origination.resource;

/**
 * Branch Service - Successc And Error  Detail class
 * @author 	Nisalak
 * @Dat     08-07-2019
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   08-07-2019   FX-172        FX-1258    Nisalak      Created
 *    
 ********************************************************************************************************
 */

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class SuccessAndErrorDetailsResource {
	@JsonProperty("messages")
	private String messages;
	
	@JsonProperty("details")
	private String details;
	
	@JsonProperty("code")
	private String code;
	
	@JsonProperty("value")
	private Object value;
	
	

	public String getMessages() {
		return messages;
	}

	public void setMessages(String messages) {
		this.messages = messages;
	}
	
	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public SuccessAndErrorDetailsResource() {
		super();
	}
	
	public SuccessAndErrorDetailsResource(String messages) {
		super();
		this.messages = messages;
	}

	public SuccessAndErrorDetailsResource(String messages, Object value) {
		super();
		this.messages = messages;
		this.value = value;
	}
}
