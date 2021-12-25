package com.fusionx.lending.product.resources;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 	Tc Structured Detail Add Resource
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   12-10-2021   			 	 FXL-1138      Piyumi     Created
 *    
 ********************************************************************************************************
*/


@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class TcStructuredDetailAddResource {
	
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
	@Pattern(regexp = "^$|^-?[0-9]{0,2}(\\.[0-9]{1,2})?$|^-?(100)(\\.[0]{1,2})?$",message="{rate.pattern}")
	private String interestRate;
	
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
	@Pattern(regexp = "STRU", message = "{tc-stru-calculation.pattern}")
	private String calculationMethodName;

	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "IN_ADVANCE|IN_ARREARS", message = "{repayment-criteria.pattern}")
	private String repaymentCriteria;
	
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String numberOfPrepayments;

	@Pattern(regexp = "^$|\\d{1,20}\\.\\d{1,2}$",message="{common-amount.pattern}")
	private String amountPaidInAdvance;
	
	@Pattern(regexp = "^$|\\d{1,20}\\.\\d{1,2}$",message="{common-amount.pattern}")
	private String residualValue;
	
	@Pattern(regexp = "YES|NO", message = "{reward-flag.pattern}")
	private String rewardFlag;
	
	private String remarks;
	
	@Valid
	private List<RepaymentStructureResource> repaymentStructure;
	
	@Valid
	private List<FixedChargesResource> fixedChargesDetails;
	
	@Valid
	private List<OptionalChargesResource> optionalChargesDetails;
	
	@Valid
	private List<AdhocChargesResource> adhocChargesDetails;

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

	public String getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(String interestRate) {
		this.interestRate = interestRate;
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

	public String getResidualValue() {
		return residualValue;
	}

	public void setResidualValue(String residualValue) {
		this.residualValue = residualValue;
	}

	public String getRewardFlag() {
		return rewardFlag;
	}

	public void setRewardFlag(String rewardFlag) {
		this.rewardFlag = rewardFlag;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public List<RepaymentStructureResource> getRepaymentStructure() {
		return repaymentStructure;
	}

	public void setRepaymentStructure(List<RepaymentStructureResource> repaymentStructure) {
		this.repaymentStructure = repaymentStructure;
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

	public List<AdhocChargesResource> getAdhocChargesDetails() {
		return adhocChargesDetails;
	}

	public void setAdhocChargesDetails(List<AdhocChargesResource> adhocChargesDetails) {
		this.adhocChargesDetails = adhocChargesDetails;
	}

	public String getAmountPaidInAdvance() {
		return amountPaidInAdvance;
	}

	public void setAmountPaidInAdvance(String amountPaidInAdvance) {
		this.amountPaidInAdvance = amountPaidInAdvance;
	}

}
