package com.fusionx.lending.product.resources;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class MasterDefCountryMainUpdateResource {
	
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String masterDefinitionPendingId;

	@Valid
	private List<MasterDefCountryUpdateResource> country;
	
	

	public String getMasterDefinitionPendingId() {
		return masterDefinitionPendingId;
	}

	public void setMasterDefinitionPendingId(String masterDefinitionPendingId) {
		this.masterDefinitionPendingId = masterDefinitionPendingId;
	}

	public List<MasterDefCountryUpdateResource> getCountry() {
		return country;
	}

	public void setCountry(List<MasterDefCountryUpdateResource> country) {
		this.country = country;
	}
	
	
}
