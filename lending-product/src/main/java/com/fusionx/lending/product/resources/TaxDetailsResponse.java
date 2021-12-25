package com.fusionx.lending.product.resources;

import java.math.BigDecimal;

public class TaxDetailsResponse {
	
	private String taxType;
	private String taxApplicableOn;
	private BigDecimal taxAmountRate;
	private BigDecimal totalTaxAmount;
	private Long applicationFrequencyId;
	private String taxAmountType;
	private Long taxCodeId;
	
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
	public BigDecimal getTaxAmountRate() {
		return taxAmountRate;
	}
	public void setTaxAmountRate(BigDecimal taxAmountRate) {
		this.taxAmountRate = taxAmountRate;
	}
	public BigDecimal getTotalTaxAmount() {
		return totalTaxAmount;
	}
	public void setTotalTaxAmount(BigDecimal totalTaxAmount) {
		this.totalTaxAmount = totalTaxAmount;
	}
	public Long getApplicationFrequencyId() {
		return applicationFrequencyId;
	}
	public void setApplicationFrequencyId(Long applicationFrequencyId) {
		this.applicationFrequencyId = applicationFrequencyId;
	}
	public String getTaxAmountType() {
		return taxAmountType;
	}
	public void setTaxAmountType(String taxAmountType) {
		this.taxAmountType = taxAmountType;
	}
	public Long getTaxCodeId() {
		return taxCodeId;
	}
	public void setTaxCodeId(Long taxCodeId) {
		this.taxCodeId = taxCodeId;
	}
	
	

	

	
	
	

}
