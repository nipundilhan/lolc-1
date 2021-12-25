package com.fusionx.lending.origination.resource;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ExternalCribRequestUpdateResource {
	
	@NotBlank(message = "{common.not-null}")
	//@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String requestId;

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	};
	
}
