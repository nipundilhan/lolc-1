package com.fusionx.lending.product.resources;


import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 	Structured Loan Calculation Resource
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   04-10-2021   			 	 FXL-993      Piyumi     Created
 *    
 ********************************************************************************************************
*/


@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class StructuredLoanCalculationResource {
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|\\d{1,20}\\.\\d{1,2}$",message="{common-amount.pattern}")
	private String loanAmount; 
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|\\d{1,20}\\.\\d{1,2}$",message="{common-amount.pattern}")
	private String paidAmountInAdvance;  
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|\\d{1,20}\\.\\d{1,2}$",message="{common-amount.pattern}")
	private String  taxesInAdvance; 
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|\\d{1,20}\\.\\d{1,2}$",message="{common-amount.pattern}")
	private String totalTaxes;  
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|\\d{1,20}\\.\\d{1,2}$",message="{common-amount.pattern}")
	private String  totalCharges; 
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|\\d{1,20}\\.\\d{1,2}$",message="{common-amount.pattern}")
	private String downPaymentCharges;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|\\d{1,20}\\.\\d{1,2}$",message="{common-amount.pattern}")
	private String scheduleCharges;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|\\d{1,20}\\.\\d{1,2}$",message="{common-amount.pattern}")
	private String taxesForSchedule;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String period;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String paymentFreq;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|^-?[0-9]{0,2}(\\.[0-9]{1,2})?$|^-?(100)(\\.[0]{1,2})?$",message="{rate.pattern}")
	private String annualInterestRate;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|\\d{1,20}\\.\\d{1,2}$",message="{common-amount.pattern}")
	private String residualValue;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "IN_ADVANCE|IN_ARREARS", message = "{repayment-criteria.pattern}")
	private String repaymentCriteria;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String numberOfPrepayments;
	
	private List<RepaymentStructureResource> rentalStructure;

	public String getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(String loanAmount) {
		this.loanAmount = loanAmount;
	}

	public String getTaxesInAdvance() {
		return taxesInAdvance;
	}

	public void setTaxesInAdvance(String taxesInAdvance) {
		this.taxesInAdvance = taxesInAdvance;
	}

	public String getTotalTaxes() {
		return totalTaxes;
	}

	public void setTotalTaxes(String totalTaxes) {
		this.totalTaxes = totalTaxes;
	}

	public String getTotalCharges() {
		return totalCharges;
	}

	public void setTotalCharges(String totalCharges) {
		this.totalCharges = totalCharges;
	}

	public String getTaxesForSchedule() {
		return taxesForSchedule;
	}

	public void setTaxesForSchedule(String taxesForSchedule) {
		this.taxesForSchedule = taxesForSchedule;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public String getPaymentFreq() {
		return paymentFreq;
	}

	public void setPaymentFreq(String paymentFreq) {
		this.paymentFreq = paymentFreq;
	}

	public String getAnnualInterestRate() {
		return annualInterestRate;
	}

	public void setAnnualInterestRate(String annualInterestRate) {
		this.annualInterestRate = annualInterestRate;
	}

	public String getResidualValue() {
		return residualValue;
	}

	public void setResidualValue(String residualValue) {
		this.residualValue = residualValue;
	}

	public String getRepaymentCriteria() {
		return repaymentCriteria;
	}

	public void setRepaymentCriteria(String repaymentCriteria) {
		this.repaymentCriteria = repaymentCriteria;
	}

	public String getNumberOfPrepayments() {
		return numberOfPrepayments;
	}

	public void setNumberOfPrepayments(String numberOfPrepayments) {
		this.numberOfPrepayments = numberOfPrepayments;
	}

	public String getPaidAmountInAdvance() {
		return paidAmountInAdvance;
	}

	public void setPaidAmountInAdvance(String paidAmountInAdvance) {
		this.paidAmountInAdvance = paidAmountInAdvance;
	}

	public List<RepaymentStructureResource> getRentalStructure() {
		return rentalStructure;
	}

	public void setRentalStructure(List<RepaymentStructureResource> rentalStructure) {
		this.rentalStructure = rentalStructure;
	}

	public String getScheduleCharges() {
		return scheduleCharges;
	}

	public void setScheduleCharges(String scheduleCharges) {
		this.scheduleCharges = scheduleCharges;
	}

	public String getDownPaymentCharges() {
		return downPaymentCharges;
	}

	public void setDownPaymentCharges(String downPaymentCharges) {
		this.downPaymentCharges = downPaymentCharges;
	}

}
