package com.fusionx.lending.product.resources;

import java.math.BigDecimal;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class FixedInstallmentPaymentScheduleResponse {
	
	
	
	private Long sequence;
	
	private BigDecimal capitalPortion;
	
	private BigDecimal interestPortion;
	
	private BigDecimal capitalBalance;
	
	private BigDecimal chargesCollectThroughOutPeriod;
	
	private BigDecimal taxesCollectThroughOutPeriod;	
	
	private BigDecimal totalGrossInstallment;
	
	private BigDecimal netFixedInstallment;

	public Long getSequence() {
		return sequence;
	}

	public void setSequence(Long sequence) {
		this.sequence = sequence;
	}

	public BigDecimal getCapitalPortion() {
		return capitalPortion;
	}

	public void setCapitalPortion(BigDecimal capitalPortion) {
		this.capitalPortion = capitalPortion;
	}

	public BigDecimal getInterestPortion() {
		return interestPortion;
	}

	public void setInterestPortion(BigDecimal interestPortion) {
		this.interestPortion = interestPortion;
	}

	public BigDecimal getCapitalBalance() {
		return capitalBalance;
	}

	public void setCapitalBalance(BigDecimal capitalBalance) {
		this.capitalBalance = capitalBalance;
	}

	public BigDecimal getChargesCollectThroughOutPeriod() {
		return chargesCollectThroughOutPeriod;
	}

	public void setChargesCollectThroughOutPeriod(BigDecimal chargesCollectThroughOutPeriod) {
		this.chargesCollectThroughOutPeriod = chargesCollectThroughOutPeriod;
	}

	public BigDecimal getTaxesCollectThroughOutPeriod() {
		return taxesCollectThroughOutPeriod;
	}

	public void setTaxesCollectThroughOutPeriod(BigDecimal taxesCollectThroughOutPeriod) {
		this.taxesCollectThroughOutPeriod = taxesCollectThroughOutPeriod;
	}

	public BigDecimal getTotalGrossInstallment() {
		return totalGrossInstallment;
	}

	public void setTotalGrossInstallment(BigDecimal totalGrossInstallment) {
		this.totalGrossInstallment = totalGrossInstallment;
	}

	public BigDecimal getNetFixedInstallment() {
		return netFixedInstallment;
	}

	public void setNetFixedInstallment(BigDecimal netFixedInstallment) {
		this.netFixedInstallment = netFixedInstallment;
	}
	
	
	
	
	


}
