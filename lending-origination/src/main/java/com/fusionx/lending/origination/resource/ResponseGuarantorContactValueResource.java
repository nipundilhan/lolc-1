package com.fusionx.lending.origination.resource;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;

@Data
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class ResponseGuarantorContactValueResource {

	private String penSuppliesId;

	private String ppconId;

	public String getPenSuppliesId() {
		return penSuppliesId;
	}

	public void setPenSuppliesId(String penSuppliesId) {
		this.penSuppliesId = penSuppliesId;
	}

	public String getPpconId() {
		return ppconId;
	}

	public void setPpconId(String ppconId) {
		this.ppconId = ppconId;
	}

	
    
}
