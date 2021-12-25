package com.fusionx.lending.origination.resource;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class PendingSuppliesListResource {
	
	@Pattern(regexp = "^$|[0-9]+", message = "{common.invalid-number-format}")
	@NotBlank(message = "{common.not-null}")
	private String penSupId;

	public String getPenSupId() {
		return penSupId;
	}

	public void setPenSupId(String penSupId) {
		this.penSupId = penSupId;
	}

}
