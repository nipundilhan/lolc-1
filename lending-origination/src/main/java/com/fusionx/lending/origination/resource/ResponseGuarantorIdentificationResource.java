package com.fusionx.lending.origination.resource;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;

@Data
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class ResponseGuarantorIdentificationResource {

	
	private String pidtIdentificationNo;
		
	private String pidtIdentificationTypeId;
	
	private String pidtIdentificationTypeDesc;
	
    private String pidtExpiryDate;
	
	private String pidtIssueDate;
	
	private String messages;
		
	private ResponseGuarantorIdentificationValueResource value;

	public String getPidtIdentificationNo() {
		return pidtIdentificationNo;
	}

	public void setPidtIdentificationNo(String pidtIdentificationNo) {
		this.pidtIdentificationNo = pidtIdentificationNo;
	}

	public String getPidtIdentificationTypeId() {
		return pidtIdentificationTypeId;
	}

	public void setPidtIdentificationTypeId(String pidtIdentificationTypeId) {
		this.pidtIdentificationTypeId = pidtIdentificationTypeId;
	}

	public String getPidtIdentificationTypeDesc() {
		return pidtIdentificationTypeDesc;
	}

	public void setPidtIdentificationTypeDesc(String pidtIdentificationTypeDesc) {
		this.pidtIdentificationTypeDesc = pidtIdentificationTypeDesc;
	}
	
	public String getPidtExpiryDate() {
		return pidtExpiryDate;
	}

	public void setPidtExpiryDate(String pidtExpiryDate) {
		this.pidtExpiryDate = pidtExpiryDate;
	}

	public String getPidtIssueDate() {
		return pidtIssueDate;
	}

	public void setPidtIssueDate(String pidtIssueDate) {
		this.pidtIssueDate = pidtIssueDate;
	}

	public String getMessages() {
		return messages;
	}

	public void setMessages(String messages) {
		this.messages = messages;
	}

	public ResponseGuarantorIdentificationValueResource getValue() {
		return value;
	}

	public void setValue(ResponseGuarantorIdentificationValueResource value) {
		this.value = value;
	}

	


}
