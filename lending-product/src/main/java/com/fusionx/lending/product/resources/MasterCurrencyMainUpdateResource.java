package com.fusionx.lending.product.resources;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Master Currency Main Update Resource
 * 
 *******************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *------------------------------------------------------------------------------------------------------
 *    1   14-07-2021      		     FX-	Piyumi      Created
 *    
 *******************************************************************************************************
 */

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class MasterCurrencyMainUpdateResource {
	
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String masterDefinitionPendingId;
	
	private List<MasterCurrencyUpdateResource> currencyList;
	
	public List<MasterCurrencyUpdateResource> getCurrencyList() {
		return currencyList;
	}

	public void setCurrencyList(List<MasterCurrencyUpdateResource> currencyList) {
		this.currencyList = currencyList;
	}
	
	public String getMasterDefinitionPendingId() {
		return masterDefinitionPendingId;
	}

	public void setMasterDefinitionPendingId(String masterDefinitionPendingId) {
		this.masterDefinitionPendingId = masterDefinitionPendingId;
	}
	

}
