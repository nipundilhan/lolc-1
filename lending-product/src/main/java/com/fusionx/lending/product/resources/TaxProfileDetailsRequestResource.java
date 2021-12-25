package com.fusionx.lending.product.resources;

import javax.validation.constraints.NotBlank;

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

import javax.validation.constraints.Pattern;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class TaxProfileDetailsRequestResource {

	@JsonProperty("id")
	private String id;

	@Pattern(regexp = "^$|[0-9]+", message = "{taxAmount.pattern}")
	private String taxAmount;

	@Pattern(regexp = "^$|\\d{1,20}\\.\\d{1,5}$", message = "{taxRate.pattern}")
	private String taxRate;

	@Pattern(regexp = "^(([0-9]*)|(([0-9]*)\\.([0-9]*)))$", message = "{tierMinValue.pattern}")
	private String tierMinValue;

	@Pattern(regexp = "^(([0-9]*)|(([0-9]*)\\.([0-9]*)))$", message = "{tierMaxValue.pattern}")
	private String tierMaxValue;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(String taxAmount) {
		this.taxAmount = taxAmount;
	}

	public String getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(String taxRate) {
		this.taxRate = taxRate;
	}

	public String getTierMinValue() {
		return tierMinValue;
	}

	public void setTierMinValue(String tierMinValue) {
		this.tierMinValue = tierMinValue;
	}

	public String getTierMaxValue() {
		return tierMaxValue;
	}

	public void setTierMaxValue(String tierMaxValue) {
		this.tierMaxValue = tierMaxValue;
	}

}
