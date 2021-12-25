package com.fusionx.lending.origination.resource;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;

@Data
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class ResponseCustomerResource {

	private Long penCustomerId;

	public Long getPenCustomerId() {
		return penCustomerId;
	}

	public void setPenCustomerId(Long penCustomerId) {
		this.penCustomerId = penCustomerId;
	}

	
	
	
}
