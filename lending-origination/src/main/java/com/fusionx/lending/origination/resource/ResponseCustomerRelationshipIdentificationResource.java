package com.fusionx.lending.origination.resource;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;

@Data
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class ResponseCustomerRelationshipIdentificationResource {

	private String messages;	
	
	private ResponseCustomerRelationshipIdentificationValueResource value;

	public String getMessages() {
		return messages;
	}

	public void setMessages(String messages) {
		this.messages = messages;
	}

	public ResponseCustomerRelationshipIdentificationValueResource getValue() {
		return value;
	}

	public void setValue(ResponseCustomerRelationshipIdentificationValueResource value) {
		this.value = value;
	}

	

}
