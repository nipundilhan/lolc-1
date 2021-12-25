package com.fusionx.lending.origination.resource;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class AssertApproveRequestResource {

	@Valid
	private List<AssertListResource> assertList;

	public List<AssertListResource> getAssertList() {
		return assertList;
	}

	public void setAssertList(List<AssertListResource> assertList) {
		this.assertList = assertList;
	}

}
