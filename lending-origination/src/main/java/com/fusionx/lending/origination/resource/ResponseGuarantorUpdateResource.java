package com.fusionx.lending.origination.resource;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;

@Data
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class ResponseGuarantorUpdateResource {

	private String messages;	
	
	private ResponseGuarantorUpdateValueResource value;

	public String getMessages() {
		return messages;
	}

	public void setMessages(String messages) {
		this.messages = messages;
	}

	public ResponseGuarantorUpdateValueResource getValue() {
		return value;
	}

	public void setValue(ResponseGuarantorUpdateValueResource value) {
		this.value = value;
	}

	

}
