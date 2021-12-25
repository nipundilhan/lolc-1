package com.fusionx.lending.origination.resource;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;

@Data
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class ResponseCustomerContactResource {

	
	private String pconContactTypeId;
		
	private String pconContactTypeDesc;
	
	private String pconValue;
	
	private String messages;	
	
	private ResponseGuarantorContactValueResource value;

	

	public String getPconContactTypeId() {
		return pconContactTypeId;
	}

	public void setPconContactTypeId(String pconContactTypeId) {
		this.pconContactTypeId = pconContactTypeId;
	}

	public String getPconContactTypeDesc() {
		return pconContactTypeDesc;
	}

	public void setPconContactTypeDesc(String pconContactTypeDesc) {
		this.pconContactTypeDesc = pconContactTypeDesc;
	}

	public String getPconValue() {
		return pconValue;
	}

	public void setPconValue(String pconValue) {
		this.pconValue = pconValue;
	}

	public String getMessages() {
		return messages;
	}

	public void setMessages(String messages) {
		this.messages = messages;
	}

	public ResponseGuarantorContactValueResource getValue() {
		return value;
	}

	public void setValue(ResponseGuarantorContactValueResource value) {
		this.value = value;
	}

}
