package com.fusionx.lending.product.resources;

import java.util.List;

import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Master DefinitionCountry Main Update Resource
 * 
 *******************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *------------------------------------------------------------------------------------------------------
 *    1   14-07-2021      		     FX-	Dilki      Created
 *    
 *******************************************************************************************************
 */

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class MasterCountryMainUpdateResource {
	
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String masterDefinitionPendingId;
	
	private List<MasterDefCountryUpdateResource> countryList;
	
	public List<MasterDefCountryUpdateResource> getDefinitionCountryList() {
		return countryList;
	}

	public void setDefinitionCountryList(List<MasterDefCountryUpdateResource> countryList) {
		this.countryList = countryList;
	}
	
	public String getMasterDefinitionPendingId() {
		return masterDefinitionPendingId;
	}

	public void setMasterDefinitionPendingId(String masterDefinitionPendingId) {
		this.masterDefinitionPendingId = masterDefinitionPendingId;
	}
	

}
