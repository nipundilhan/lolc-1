package com.fusionx.lending.origination.resource;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;

@Data
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class ResponseCustomerOnboardingResource {

	
	private String customerOnBoardRequestExpireDate;
		
	private String customerOnBoardPendingCustomerId;
		
	private String messages;
		
	private ResponseCustomerOnboardingValueResource value;

	public String getCustomerOnBoardRequestExpireDate() {
		return customerOnBoardRequestExpireDate;
	}

	public void setCustomerOnBoardRequestExpireDate(String customerOnBoardRequestExpireDate) {
		this.customerOnBoardRequestExpireDate = customerOnBoardRequestExpireDate;
	}

	public String getCustomerOnBoardPendingCustomerId() {
		return customerOnBoardPendingCustomerId;
	}

	public void setCustomerOnBoardPendingCustomerId(String customerOnBoardPendingCustomerId) {
		this.customerOnBoardPendingCustomerId = customerOnBoardPendingCustomerId;
	}

	public String getMessages() {
		return messages;
	}

	public void setMessages(String messages) {
		this.messages = messages;
	}

	public ResponseCustomerOnboardingValueResource getValue() {
		return value;
	}

	public void setValue(ResponseCustomerOnboardingValueResource value) {
		this.value = value;
	}

	
	
	

}
