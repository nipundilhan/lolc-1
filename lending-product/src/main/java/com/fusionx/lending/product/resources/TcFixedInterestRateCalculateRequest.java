package com.fusionx.lending.product.resources;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class TcFixedInterestRateCalculateRequest {
	

	private BigDecimal loanAmount;
	
	private int loadPeriod;
	
	private int repaymentFrequency;
	
	private BigDecimal annualInterestRatePercentage ;
	
	private BigDecimal residualValue ;
	
	private int noOfPrePayments;
	
	private BigDecimal totalCharges;
	
	private BigDecimal totalTaxes;
	
	private BigDecimal chargesAtDownPayment;
	
	private List<TcStructurePaymentScheduleResource> structuredPaymentScheduleList;
	
	
	public BigDecimal getChargesAtDownPayment() {
		return chargesAtDownPayment;
	}
	public void setChargesAtDownPayment(BigDecimal chargesAtDownPayment) {
		this.chargesAtDownPayment = chargesAtDownPayment;
	}
	public BigDecimal getTotalCharges() {
		return totalCharges;
	}
	public void setTotalCharges(BigDecimal totalCharges) {
		this.totalCharges = totalCharges;
	}
	public BigDecimal getTotalTaxes() {
		return totalTaxes;
	}
	public void setTotalTaxes(BigDecimal totalTaxes) {
		this.totalTaxes = totalTaxes;
	}
	public BigDecimal getLoanAmount() {
		return loanAmount;
	}
	public void setLoanAmount(BigDecimal loanAmount) {
		this.loanAmount = loanAmount;
	}
	public int getLoadPeriod() {
		return loadPeriod;
	}
	public void setLoadPeriod(int loadPeriod) {
		this.loadPeriod = loadPeriod;
	}
	public int getRepaymentFrequency() {
		return repaymentFrequency;
	}
	public void setRepaymentFrequency(int repaymentFrequency) {
		this.repaymentFrequency = repaymentFrequency;
	}
	public BigDecimal getAnnualInterestRatePercentage() {
		return annualInterestRatePercentage;
	}
	public void setAnnualInterestRatePercentage(BigDecimal annualInterestRatePercentage) {
		this.annualInterestRatePercentage = annualInterestRatePercentage;
	}
	public BigDecimal getResidualValue() {
		return residualValue;
	}
	public void setResidualValue(BigDecimal residualValue) {
		this.residualValue = residualValue;
	}
	public int getNoOfPrePayments() {
		return noOfPrePayments;
	}
	public void setNoOfPrePayments(int noOfPrePayments) {
		this.noOfPrePayments = noOfPrePayments;
	}
	
	
	private BigDecimal irr ;
	
	private BigDecimal installmentAmount ;





	public BigDecimal getIrr() {
		return irr;
	}
	public void setIrr(BigDecimal irr) {
		this.irr = irr;
	}
	public BigDecimal getInstallmentAmount() {
		return installmentAmount;
	}
	public void setInstallmentAmount(BigDecimal installmentAmount) {
		this.installmentAmount = installmentAmount;
	}
	public List<TcStructurePaymentScheduleResource> getStructuredPaymentScheduleList() {
		return structuredPaymentScheduleList;
	}
	public void setStructuredPaymentScheduleList(List<TcStructurePaymentScheduleResource> structuredPaymentScheduleList) {
		this.structuredPaymentScheduleList = structuredPaymentScheduleList;
	}
	

	
	
	
	
	

}
