package com.fusionx.lending.origination.resource;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class UpdatePendingExternalCribDetailsRequestResource {

	
	@Pattern(regexp = "^$|(\\d{4})-(\\d{2})-(\\d{2})$", message = "{common.invalid-date-pattern}")
	private String requestedDate;
	
	private String requestedUser;
	
	@JsonProperty("cribRequestDetails")
	@Valid
	private List<ExternalCribRequestUpdateResource> cribRequestDetails;

	public String getRequestedDate() {
		return requestedDate;
	}

	public void setRequestedDate(String requestedDate) {
		this.requestedDate = requestedDate;
	}

	public String getRequestedUser() {
		return requestedUser;
	}

	public void setRequestedUser(String requestedUser) {
		this.requestedUser = requestedUser;
	}

	public List<ExternalCribRequestUpdateResource> getCribRequestDetails() {
		return cribRequestDetails;
	}

	public void setCribRequestDetails(List<ExternalCribRequestUpdateResource> cribRequestDetails) {
		this.cribRequestDetails = cribRequestDetails;
	}
	
	
}
