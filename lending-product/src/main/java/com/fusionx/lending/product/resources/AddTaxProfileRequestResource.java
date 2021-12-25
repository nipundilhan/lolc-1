package com.fusionx.lending.product.resources;
/**
 * Tax Profile Service
 * @author 	KilasiG
 * @Date     05-11-2019
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   05-11-2019   FX-1545       FX-2175    KilasiG      Created
 *    
 ********************************************************************************************************
 */

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;

@Data
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class AddTaxProfileRequestResource extends TaxProfileRequestResource{
	
	@JsonIgnore
	private String taxProfileCreatedUser;

	public String getTaxProfileCreatedUser() {
		return taxProfileCreatedUser;
	}

	public void setTaxProfileCreatedUser(String taxProfileCreatedUser) {
		this.taxProfileCreatedUser = taxProfileCreatedUser;
	}
		
}
