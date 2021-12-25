package com.fusionx.lending.origination.resource;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;

@Data
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class ResponseCustomerRelationshipResource {

	private String messages;	
	
	private ResponseCustomerRelationshipValueResource value;

	public String getMessages() {
		return messages;
	}

	public void setMessages(String messages) {
		this.messages = messages;
	}

	public ResponseCustomerRelationshipValueResource getValue() {
		return value;
	}

	public void setValue(ResponseCustomerRelationshipValueResource value) {
		this.value = value;
	}

}
