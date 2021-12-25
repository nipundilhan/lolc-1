package com.fusionx.lending.origination.resource;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class RentalCalculationTaxDetailsResponseResource {
	
	
	private String taxType;
	private String taxApplicableOn;
	private String taxRate;
	private String taxAmount;
	
	public String getTaxAmount() {
		return taxAmount;
	}
	public void setTaxAmount(String taxAmount) {
		this.taxAmount = taxAmount;
	}
	public String getTaxType() {
		return taxType;
	}
	public void setTaxType(String taxType) {
		this.taxType = taxType;
	}
	public String getTaxApplicableOn() {
		return taxApplicableOn;
	}
	public void setTaxApplicableOn(String taxApplicableOn) {
		this.taxApplicableOn = taxApplicableOn;
	}
	public String getTaxRate() {
		return taxRate;
	}
	public void setTaxRate(String taxRate) {
		this.taxRate = taxRate;
	}
	
	

}
