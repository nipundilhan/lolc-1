package com.fusionx.lending.origination.resource;

import java.util.List;

import javax.validation.Valid;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class PendingCutomerApproveRequestResource {

	@Valid
	private List<PendingCustomerListResource> penCusIds;

	public List<PendingCustomerListResource> getPenCusIds() {
		return penCusIds;
	}

	public void setPenCusIds(List<PendingCustomerListResource> penCusIds) {
		this.penCusIds = penCusIds;
	}
	
}
