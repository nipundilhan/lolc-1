package com.fusionx.lending.origination.resource;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;

@Data
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class ResponseCustomerRelationshipValueResource {

	private String penCustomerId;

	private String pculpId;

	public String getPenCustomerId() {
		return penCustomerId;
	}

	public void setPenCustomerId(String penCustomerId) {
		this.penCustomerId = penCustomerId;
	}

	public String getPculpId() {
		return pculpId;
	}

	public void setPculpId(String pculpId) {
		this.pculpId = pculpId;
	}
    
}
