package com.fusionx.lending.product.resources;

import java.util.List;

import javax.validation.Valid;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
/**
 * Master Definition Country Main Add Resource
 * 
 *******************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *------------------------------------------------------------------------------------------------------
 *    1   14-07-2021      		     FX-	Dilki      Created
 *    
 *******************************************************************************************************
 */

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class MasterDefCountryMainAddResource {
	
	@Valid
	private List<MasterDefCountryAddResource> country;

	public List<MasterDefCountryAddResource> getCountry() {
		return country;
	}

	public void setCountry(List<MasterDefCountryAddResource> country) {
		this.country = country;
	}
	


}
