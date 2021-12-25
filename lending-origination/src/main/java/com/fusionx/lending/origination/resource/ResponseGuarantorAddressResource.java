package com.fusionx.lending.origination.resource;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;

@Data
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class ResponseGuarantorAddressResource {

	
	private String paddAddressTypeCommonListId;
		
	private String paddAddressGeoLevelId;
	
	private String paddAddressCountryId;
	
	private String messages;	
	
	private ResponseGuarantorAddressValueResource value;

	public String getPaddAddressTypeCommonListId() {
		return paddAddressTypeCommonListId;
	}

	public void setPaddAddressTypeCommonListId(String paddAddressTypeCommonListId) {
		this.paddAddressTypeCommonListId = paddAddressTypeCommonListId;
	}

	public String getPaddAddressGeoLevelId() {
		return paddAddressGeoLevelId;
	}

	public void setPaddAddressGeoLevelId(String paddAddressGeoLevelId) {
		this.paddAddressGeoLevelId = paddAddressGeoLevelId;
	}

	public String getPaddAddressCountryId() {
		return paddAddressCountryId;
	}

	public void setPaddAddressCountryId(String paddAddressCountryId) {
		this.paddAddressCountryId = paddAddressCountryId;
	}

	public String getMessages() {
		return messages;
	}

	public void setMessages(String messages) {
		this.messages = messages;
	}

	public ResponseGuarantorAddressValueResource getValue() {
		return value;
	}

	public void setValue(ResponseGuarantorAddressValueResource value) {
		this.value = value;
	}

	

}
