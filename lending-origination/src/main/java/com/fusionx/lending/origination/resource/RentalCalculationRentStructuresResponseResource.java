package com.fusionx.lending.origination.resource;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class RentalCalculationRentStructuresResponseResource {
	
	private String installement;
	private String period;
	private String quotationNumber;
	private String rate;
	private String sequence;
	private String totalChargesAmount;
	private String totalInstallement;
	private String totalTaxAmount;
	
	private List<RentalCalculationChargeDetailsResponseResource> chargeDetails;
	
	private List<RentalCalculationTaxDetailsResponseResource> taxDetails;

	public String getInstallement() {
		return installement;
	}

	public void setInstallement(String installement) {
		this.installement = installement;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public String getQuotationNumber() {
		return quotationNumber;
	}

	public void setQuotationNumber(String quotationNumber) {
		this.quotationNumber = quotationNumber;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	public String getTotalChargesAmount() {
		return totalChargesAmount;
	}

	public void setTotalChargesAmount(String totalChargesAmount) {
		this.totalChargesAmount = totalChargesAmount;
	}

	public String getTotalInstallement() {
		return totalInstallement;
	}

	public void setTotalInstallement(String totalInstallement) {
		this.totalInstallement = totalInstallement;
	}

	public String getTotalTaxAmount() {
		return totalTaxAmount;
	}

	public void setTotalTaxAmount(String totalTaxAmount) {
		this.totalTaxAmount = totalTaxAmount;
	}

	public List<RentalCalculationChargeDetailsResponseResource> getChargeDetails() {
		return chargeDetails;
	}

	public void setChargeDetails(List<RentalCalculationChargeDetailsResponseResource> chargeDetails) {
		this.chargeDetails = chargeDetails;
	}

	public List<RentalCalculationTaxDetailsResponseResource> getTaxDetails() {
		return taxDetails;
	}

	public void setTaxDetails(List<RentalCalculationTaxDetailsResponseResource> taxDetails) {
		this.taxDetails = taxDetails;
	}
	
	
	
	

}
