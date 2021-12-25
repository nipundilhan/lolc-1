package com.fusionx.lending.origination.resource;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;

@Data
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class ResponseGuarantorResource {

	private Long penSuppliesId;

	public Long getPenSuppliesId() {
		return penSuppliesId;
	}

	public void setPenSuppliesId(Long penSuppliesId) {
		this.penSuppliesId = penSuppliesId;
	}
	
	
}
