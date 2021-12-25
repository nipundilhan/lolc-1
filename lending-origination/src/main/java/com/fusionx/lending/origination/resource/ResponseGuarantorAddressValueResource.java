package com.fusionx.lending.origination.resource;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;

@Data
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class ResponseGuarantorAddressValueResource {

	private String penSuppliesId;

	private String ppaddId;

	public String getPenSuppliesId() {
		return penSuppliesId;
	}

	public void setPenSuppliesId(String penSuppliesId) {
		this.penSuppliesId = penSuppliesId;
	}

	public String getPpaddId() {
		return ppaddId;
	}

	public void setPpaddId(String ppaddId) {
		this.ppaddId = ppaddId;
	}

	

    
}
