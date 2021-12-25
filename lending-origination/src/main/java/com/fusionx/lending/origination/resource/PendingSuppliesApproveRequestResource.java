package com.fusionx.lending.origination.resource;

import java.util.List;

import javax.validation.Valid;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class PendingSuppliesApproveRequestResource {

	@Valid
	private List<PendingSuppliesListResource> penSupIds;

	public List<PendingSuppliesListResource> getPenSupIds() {
		return penSupIds;
	}

	public void setPenSupIds(List<PendingSuppliesListResource> penSupIds) {
		this.penSupIds = penSupIds;
	}
	
}
