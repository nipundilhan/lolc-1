package com.fusionx.lending.origination.resource;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;

@Data
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class ResponseCustomerValueResource {
	
	private String messages;	

	private ResponseCustomerResource value;

	
	
	public String getMessages() {
		return messages;
	}

	public void setMessages(String messages) {
		this.messages = messages;
	}

	public ResponseCustomerResource getValue() {
		return value;
	}

	public void setValue(ResponseCustomerResource value) {
		this.value = value;
	}

	

    
}
