package com.fusionx.lending.origination.resource;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class RentalCalculationOtherFactoredChargesResponseResource {
	
	private String addCriteria;
	private String frequency;
	private String quotationNumber;
	private String transactionType;
	
	private List<RentalCalculationPeriodListResponseResource> periodList;
	
	public String getAddCriteria() {
		return addCriteria;
	}
	public void setAddCriteria(String addCriteria) {
		this.addCriteria = addCriteria;
	}
	public String getFrequency() {
		return frequency;
	}
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	public String getQuotationNumber() {
		return quotationNumber;
	}
	public void setQuotationNumber(String quotationNumber) {
		this.quotationNumber = quotationNumber;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public List<RentalCalculationPeriodListResponseResource> getPeriodList() {
		return periodList;
	}
	public void setPeriodList(List<RentalCalculationPeriodListResponseResource> periodList) {
		this.periodList = periodList;
	}
	
	

}
