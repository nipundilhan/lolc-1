package com.fusionx.lending.product.resources;

import java.util.List;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
/**
 * Master Currency Main Add Resource
 * 
 *******************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *------------------------------------------------------------------------------------------------------
 *    1   14-07-2021      		     FX-	Piyumi      Created
 *    
 *******************************************************************************************************
 */

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class MasterCurrencyMainAddResource {
	
	private List<MasterCurrencyAddResource> currencyList;
	
	public List<MasterCurrencyAddResource> getCurrencyList() {
		return currencyList;
	}

	public void setCurrencyList(List<MasterCurrencyAddResource> currencyList) {
		this.currencyList = currencyList;
	}

}
