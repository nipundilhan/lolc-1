package com.fusionx.lending.product.resources;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Fixed Loan Calculator Add Resource
 * @author 	NipunDilhan
 * @Date    05-OCT-2021
 * 
 ********************************************************************************************************
 *  ###   	Date         	Story Point   	Task No    		Author      	 Description
 *-------------------------------------------------------------------------------------------------------
 *    1   	05-OCT-2021  		            FXL-994   		NipunDilhan     	     Initial Development.
 *    
 ********************************************************************************************************
 */


@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class FixedLoanCalculatorAddResource {
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String leadId;

	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String productId;
	
	@NotBlank(message = "{common.not-null}")
	private String productName;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String subProductId;
	
	@NotBlank(message = "{common.not-null}")
	private String subProductName;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String currencyId;
	
	@NotBlank(message = "{common.not-null}")
	@Size(max = 70, message = "{common-name.size}")
	private String currencyName;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "ACTIVE|INACTIVE", message = "{common-status.pattern}")
	private String status;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|^-?[0-9]{0,2}(\\.[0-9]{1,2})?$|^-?(100)(\\.[0]{1,2})?$",message="{rate.pattern}")
	private String annualInterestRate;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String term;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|\\d{1,20}\\.\\d{1,2}$",message="{common-amount.pattern}")
	private String loanAmount;
	
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String repaymentFrequencyId;
	
	@NotBlank(message = "{common.not-null}")
	private String repaymentFrequencyName;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "FIXD", message = "{tc-fixed-calculation.pattern}")
	private String calculationMethodName;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|\\d{1,20}\\.\\d{1,2}$",message="{common-amount.pattern}")
	private String residualValue;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|\\d{1,20}\\.\\d{1,2}$",message="{common-amount.pattern}")
	private String amountPaidInAdvance;
	
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String noOfPrePayments;
	

	@Valid
	private List<FixedChargesResource> fixedChargesDetails;
	
	@Valid
	private List<OptionalChargesResource> optionalChargesDetails;
	
	
	@Valid
	private List<AdhocChargesResource> adhocChargesDetails;
	
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "IN_ADVANCE|IN_ARRERS", message = "{repayment-criteria.pattern}")
	private String repaymentCriteria;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "YES|NO", message = "{common-enable.pattern}")
	private String rewards;
		
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String dueDate;	
	
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String gracePeriod;

	public String getGracePeriod() {
		return gracePeriod;
	}

	public void setGracePeriod(String gracePeriod) {
		this.gracePeriod = gracePeriod;
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	public String getRewards() {
		return rewards;
	}

	public void setRewards(String rewards) {
		this.rewards = rewards;
	}

	public String getRepaymentCriteria() {
		return repaymentCriteria;
	}

	public void setRepaymentCriteria(String repaymentCriteria) {
		this.repaymentCriteria = repaymentCriteria;
	}

	
	

	public List<AdhocChargesResource> getAdhocChargesDetails() {
		return adhocChargesDetails;
	}

	public void setAdhocChargesDetails(List<AdhocChargesResource> adhocChargesDetails) {
		this.adhocChargesDetails = adhocChargesDetails;
	}

	public List<FixedChargesResource> getFixedChargesDetails() {
		return fixedChargesDetails;
	}

	public void setFixedChargesDetails(List<FixedChargesResource> fixedChargesDetails) {
		this.fixedChargesDetails = fixedChargesDetails;
	}

	public List<OptionalChargesResource> getOptionalChargesDetails() {
		return optionalChargesDetails;
	}

	public void setOptionalChargesDetails(List<OptionalChargesResource> optionalChargesDetails) {
		this.optionalChargesDetails = optionalChargesDetails;
	}
	
	

	public String getLeadId() {
		return leadId;
	}

	public void setLeadId(String leadId) {
		this.leadId = leadId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getSubProductId() {
		return subProductId;
	}

	public void setSubProductId(String subProductId) {
		this.subProductId = subProductId;
	}

	public String getSubProductName() {
		return subProductName;
	}

	public void setSubProductName(String subProductName) {
		this.subProductName = subProductName;
	}

	public String getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}

	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}



	public String getAnnualInterestRate() {
		return annualInterestRate;
	}

	public void setAnnualInterestRate(String annualInterestRate) {
		this.annualInterestRate = annualInterestRate;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public String getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(String loanAmount) {
		this.loanAmount = loanAmount;
	}

	public String getRepaymentFrequencyId() {
		return repaymentFrequencyId;
	}

	public void setRepaymentFrequencyId(String repaymentFrequencyId) {
		this.repaymentFrequencyId = repaymentFrequencyId;
	}

	public String getRepaymentFrequencyName() {
		return repaymentFrequencyName;
	}

	public void setRepaymentFrequencyName(String repaymentFrequencyName) {
		this.repaymentFrequencyName = repaymentFrequencyName;
	}

	public String getCalculationMethodName() {
		return calculationMethodName;
	}

	public void setCalculationMethodName(String calculationMethodName) {
		this.calculationMethodName = calculationMethodName;
	}

	public String getResidualValue() {
		return residualValue;
	}

	public void setResidualValue(String residualValue) {
		this.residualValue = residualValue;
	}

	public String getAmountPaidInAdvance() {
		return amountPaidInAdvance;
	}

	public void setAmountPaidInAdvance(String amountPaidInAdvance) {
		this.amountPaidInAdvance = amountPaidInAdvance;
	}

	public String getNoOfPrePayments() {
		return noOfPrePayments;
	}

	public void setNoOfPrePayments(String noOfPrePayments) {
		this.noOfPrePayments = noOfPrePayments;
	}


	
	
	
}
