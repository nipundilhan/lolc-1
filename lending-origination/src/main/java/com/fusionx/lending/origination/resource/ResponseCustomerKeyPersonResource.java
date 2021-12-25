package com.fusionx.lending.origination.resource;
/**
 *ResponseCustomerKeyPersonResource
 * 
 ********************************************************************************************************
 * ### 		Date 			Story Point 	Task No 	Author 		Description
 * -------------------------------------------------------------------------------------------------------
 * 1 		04-08-2021 		FXL-381			FXL-424		Piyumi	 	Created
 * 
 ********************************************************************************************************
 */
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;

@Data
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class ResponseCustomerKeyPersonResource {

	private String messages;	
	
	private ResponseCustomerKeyPersonValueResource value;

	public String getMessages() {
		return messages;
	}

	public void setMessages(String messages) {
		this.messages = messages;
	}

	public ResponseCustomerKeyPersonValueResource getValue() {
		return value;
	}

	public void setValue(ResponseCustomerKeyPersonValueResource value) {
		this.value = value;
	}

}
