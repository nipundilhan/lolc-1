package com.fusionx.lending.origination.resource;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;

@Data
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class ResponseCustomerContactListResource {


	private String pconContactTypeId;
	
	private String pconValue;
	
	private String ppconId;

	public String getPconContactTypeId() {
		return pconContactTypeId;
	}

	public void setPconContactTypeId(String pconContactTypeId) {
		this.pconContactTypeId = pconContactTypeId;
	}

	public String getPconValue() {
		return pconValue;
	}

	public void setPconValue(String pconValue) {
		this.pconValue = pconValue;
	}

	public String getPpconId() {
		return ppconId;
	}

	public void setPpconId(String ppconId) {
		this.ppconId = ppconId;
	}

	

}
