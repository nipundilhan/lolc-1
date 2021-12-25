package com.fusionx.lending.origination.resource;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;

@Data
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class ResponseGuarantorIdentificationValueResource {

	private String penSuppliesId;

	private String ppidtId;

	public String getPenSuppliesId() {
		return penSuppliesId;
	}

	public void setPenSuppliesId(String penSuppliesId) {
		this.penSuppliesId = penSuppliesId;
	}

	public String getPpidtId() {
		return ppidtId;
	}

	public void setPpidtId(String ppidtId) {
		this.ppidtId = ppidtId;
	}

    
}
