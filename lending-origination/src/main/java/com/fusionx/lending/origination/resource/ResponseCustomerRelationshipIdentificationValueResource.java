package com.fusionx.lending.origination.resource;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;

@Data
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class ResponseCustomerRelationshipIdentificationValueResource {

	private String penCustomerId;

	private String pculpId;
	
	private String ppidtId;

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

	public String getPpidtId() {
		return ppidtId;
	}

	public void setPpidtId(String ppidtId) {
		this.ppidtId = ppidtId;
	}
	
	
    
}
