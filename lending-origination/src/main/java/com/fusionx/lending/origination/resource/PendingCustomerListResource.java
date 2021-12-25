package com.fusionx.lending.origination.resource;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class PendingCustomerListResource {
	
	@Pattern(regexp = "^$|[0-9]+", message = "{common.invalid-number-format}")
	@NotBlank(message = "{common.not-null}")
	private String penCusId;

	public String getPenCusId() {
		return penCusId;
	}

	public void setPenCusId(String penCusId) {
		this.penCusId = penCusId;
	}

}
