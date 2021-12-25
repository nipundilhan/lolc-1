package com.fusionx.lending.origination.resource;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class FacilityCalculationRequestResource {

	@NotBlank(message = "{productCode.not-null}")
	@Size(max = 20, message = "{common.size20}")
	private String productCode;
	
	@NotBlank(message = "{productName.not-null}")
	@Size(max = 255, message = "{common.size255}")
	private String productName;
	
	@NotBlank(message = "{subProductCode.not-null}")
	@Size(max = 20, message = "{common.size20}")
	private String subProductCode;
	
	@NotBlank(message = "{subProductName.not-null}")
	@Size(max = 255, message = "{common.size255}")
	private String subProductName;
	
	@NotBlank(message = "{calculationMethodCode.not-null}")
	@Size(max = 20, message = "{common.size20}")
	private String calculationMethodCode;
	
	@NotBlank(message = "{calculationMethodName.not-null}")
	@Size(max = 255, message = "{common.size255}")
	private String calculationMethodName;
	
	@NotBlank(message = "{paymentFrequencyCode.not-null}")
	@Size(max = 20, message = "{common.size20}")
	private String paymentFrequencyCode;
	
	@NotBlank(message = "{paymentFrequencyName.not-null}")
	@Size(max = 255, message = "{common.size255}")
	private String paymentFrequencyName;
	
	@NotBlank(message = "{currencyCode.not-null}")
	@Size(max = 20, message = "{common.size20}")
	private String currencyCode;
	
	@NotBlank(message = "{loanAmount.not-null}")
	@Pattern(regexp = "^$|\\d{1,20}\\.\\d{1,5}$",message="{common.invalid-amount-pattern}")
	private String loanAmount;
	
	@NotBlank(message = "{term.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String term;
	
	@NotBlank(message = "{rate.not-null}")
	@Pattern(regexp = "^$|^-?[0-9]{0,2}(\\.[0-9]{1,2})?$|^-?(100)(\\.[0]{1,2})?$",message="{rate.pattern}")
	private String rate;
	
	@Pattern(regexp = "^$|\\d{1,20}\\.\\d{1,5}$",message="{common.invalid-amount-pattern}")
    private String approvedLimit;
	
	@Pattern(regexp = "^$|\\d{1,20}\\.\\d{1,5}$",message="{common.invalid-amount-pattern}")
    private String initialDisbursement;
	
	@Pattern(regexp = "^$|\\d{4}(\\-)(((0)[0-9])|((1)[0-2]))(\\-)([0-2][0-9]|(3)[0-1])$", message = "{date.pattern}")
	private String dueDate;
	
	@Pattern(regexp = "^$|\\d{4}(\\-)(((0)[0-9])|((1)[0-2]))(\\-)([0-2][0-9]|(3)[0-1])$", message = "{date.pattern}")
	private String expiryDate;
	
	@Pattern(regexp = "^$|^-?[0-9]{0,2}(\\.[0-9]{1,2})?$|^-?(100)(\\.[0]{1,2})?$",message="{rate.pattern}")
    private String penalRate;
	
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String gracePeriod;
	
	@Pattern(regexp = "^$|END_OF_MONTH|END_OF_TERM", message = "{interestRepaymentMethod.pattern}")
	private String interestRepaymentMethod;
	
	@Pattern(regexp = "^$|IN_ADVANCE|IN_ARREARS", message = "{repaymentCriteria.pattern}")
	private String repaymentCriteria;
	
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String noOfPrePayment;
	
	@Pattern(regexp = "^$|\\d{1,20}\\.\\d{1,5}$",message="{common.invalid-amount-pattern}")
    private String amountPaidInAdvance;
	
	@Pattern(regexp = "^$|\\d{1,20}\\.\\d{1,5}$",message="{common.invalid-amount-pattern}")
    private String residualValue;
	
	@Pattern(regexp = "^$|YES|NO", message = "{rewards.pattern}")
	private String rewards;
	
	@Size(max = 255, message = "{common.size255}")
	private String remaks;
	
	@NotBlank(message = "{loanAmountWithTax.not-null}")
	@Pattern(regexp = "^$|\\d{1,20}\\.\\d{1,5}$",message="{common.invalid-amount-pattern}")
	private String loanAmountWithTax;
	
	@NotBlank(message = "{amountFinance.not-null}")
	@Pattern(regexp = "^$|\\d{1,20}\\.\\d{1,5}$",message="{common.invalid-amount-pattern}")
	private String amountFinance;
	
	@NotBlank(message = "{totalReceivable.not-null}")
	@Pattern(regexp = "^$|\\d{1,20}\\.\\d{1,5}$",message="{common.invalid-amount-pattern}")
	private String totalReceivable;
	
	@Pattern(regexp = "^$|\\d{1,20}\\.\\d{1,5}$",message="{common.invalid-amount-pattern}")
	private String prePaidInstallment;
	
	@Pattern(regexp = "^$|\\d{1,20}\\.\\d{1,5}$",message="{common.invalid-amount-pattern}")
	private String downPaymentAmount;
	
	@Pattern(regexp = "^$|\\d{1,20}\\.\\d{1,5}$",message="{common.invalid-amount-pattern}")
	private String totalTaxes;
	
	@Pattern(regexp = "^$|\\d{1,20}\\.\\d{1,5}$",message="{common.invalid-amount-pattern}")
	private String totalCharges;
	
	@Pattern(regexp = "^$|^-?[0-9]{0,2}(\\.[0-9]{1,2})?$|^-?(100)(\\.[0]{1,2})?$",message="{rate.pattern}")
	private String leaseFactor;
	
	@Pattern(regexp = "^$|^-?[0-9]{0,2}(\\.[0-9]{1,2})?$|^-?(100)(\\.[0]{1,2})?$",message="{rate.pattern}")
	private String chargesFactor;
	
	@Pattern(regexp = "^$|^-?[0-9]{0,2}(\\.[0-9]{1,2})?$|^-?(100)(\\.[0]{1,2})?$",message="{rate.pattern}")
	private String totalFactor;
	
	//@NotBlank(message = "{branchCode.not-null}")
	@Size(max = 20, message = "{common.size20}")
	private String branchCode;
	
	//@NotBlank(message = "{tcNo.not-null}")
	@Size(max = 20, message = "{common.size20}")
	private String tcNo;
	
	//@NotBlank(message = "{quotationNo.not-null}")
	@Size(max = 20, message = "{common.size20}")
	private String quotationNo;
	
	@Valid
	private List<FacilityDetailRequestResource> paymentStructures;
	
	@Valid
	private List<FacilityStructureRequestResource> facilityStructures;
	
	@Valid
	private List<FacilityCalculationTaxRequestResource> taxes;
	
	@Valid
	private List<FacilityCalculationFixedChargesRequestResource> fixedCharges;
	
	@Valid
	private List<FacilityCalculationOptionalChargesRequestResource> optionalCharges;
	
	@Valid
	private List<FacilityCalculationPeriodicChargesRequestResource> periodicCharges;

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getSubProductCode() {
		return subProductCode;
	}

	public void setSubProductCode(String subProductCode) {
		this.subProductCode = subProductCode;
	}

	public String getSubProductName() {
		return subProductName;
	}

	public void setSubProductName(String subProductName) {
		this.subProductName = subProductName;
	}

	public String getCalculationMethodCode() {
		return calculationMethodCode;
	}

	public void setCalculationMethodCode(String calculationMethodCode) {
		this.calculationMethodCode = calculationMethodCode;
	}

	public String getCalculationMethodName() {
		return calculationMethodName;
	}

	public void setCalculationMethodName(String calculationMethodName) {
		this.calculationMethodName = calculationMethodName;
	}

	public String getPaymentFrequencyCode() {
		return paymentFrequencyCode;
	}

	public void setPaymentFrequencyCode(String paymentFrequencyCode) {
		this.paymentFrequencyCode = paymentFrequencyCode;
	}

	public String getPaymentFrequencyName() {
		return paymentFrequencyName;
	}

	public void setPaymentFrequencyName(String paymentFrequencyName) {
		this.paymentFrequencyName = paymentFrequencyName;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(String loanAmount) {
		this.loanAmount = loanAmount;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getApprovedLimit() {
		return approvedLimit;
	}

	public void setApprovedLimit(String approvedLimit) {
		this.approvedLimit = approvedLimit;
	}

	public String getInitialDisbursement() {
		return initialDisbursement;
	}

	public void setInitialDisbursement(String initialDisbursement) {
		this.initialDisbursement = initialDisbursement;
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getPenalRate() {
		return penalRate;
	}

	public void setPenalRate(String penalRate) {
		this.penalRate = penalRate;
	}

	public String getGracePeriod() {
		return gracePeriod;
	}

	public void setGracePeriod(String gracePeriod) {
		this.gracePeriod = gracePeriod;
	}

	public String getInterestRepaymentMethod() {
		return interestRepaymentMethod;
	}

	public void setInterestRepaymentMethod(String interestRepaymentMethod) {
		this.interestRepaymentMethod = interestRepaymentMethod;
	}

	public String getRepaymentCriteria() {
		return repaymentCriteria;
	}

	public void setRepaymentCriteria(String repaymentCriteria) {
		this.repaymentCriteria = repaymentCriteria;
	}

	public String getNoOfPrePayment() {
		return noOfPrePayment;
	}

	public void setNoOfPrePayment(String noOfPrePayment) {
		this.noOfPrePayment = noOfPrePayment;
	}

	public String getAmountPaidInAdvance() {
		return amountPaidInAdvance;
	}

	public void setAmountPaidInAdvance(String amountPaidInAdvance) {
		this.amountPaidInAdvance = amountPaidInAdvance;
	}

	public String getResidualValue() {
		return residualValue;
	}

	public void setResidualValue(String residualValue) {
		this.residualValue = residualValue;
	}

	public String getRewards() {
		return rewards;
	}

	public void setRewards(String rewards) {
		this.rewards = rewards;
	}

	public String getRemaks() {
		return remaks;
	}

	public void setRemaks(String remaks) {
		this.remaks = remaks;
	}

	public String getLoanAmountWithTax() {
		return loanAmountWithTax;
	}

	public void setLoanAmountWithTax(String loanAmountWithTax) {
		this.loanAmountWithTax = loanAmountWithTax;
	}

	public String getAmountFinance() {
		return amountFinance;
	}

	public void setAmountFinance(String amountFinance) {
		this.amountFinance = amountFinance;
	}

	public String getTotalReceivable() {
		return totalReceivable;
	}

	public void setTotalReceivable(String totalReceivable) {
		this.totalReceivable = totalReceivable;
	}

	public String getPrePaidInstallment() {
		return prePaidInstallment;
	}

	public void setPrePaidInstallment(String prePaidInstallment) {
		this.prePaidInstallment = prePaidInstallment;
	}

	public String getDownPaymentAmount() {
		return downPaymentAmount;
	}

	public void setDownPaymentAmount(String downPaymentAmount) {
		this.downPaymentAmount = downPaymentAmount;
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

	public String getLeaseFactor() {
		return leaseFactor;
	}

	public void setLeaseFactor(String leaseFactor) {
		this.leaseFactor = leaseFactor;
	}

	public String getChargesFactor() {
		return chargesFactor;
	}

	public void setChargesFactor(String chargesFactor) {
		this.chargesFactor = chargesFactor;
	}

	public String getTotalFactor() {
		return totalFactor;
	}

	public void setTotalFactor(String totalFactor) {
		this.totalFactor = totalFactor;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getTcNo() {
		return tcNo;
	}

	public void setTcNo(String tcNo) {
		this.tcNo = tcNo;
	}

	public String getQuotationNo() {
		return quotationNo;
	}

	public void setQuotationNo(String quotationNo) {
		this.quotationNo = quotationNo;
	}

	public List<FacilityDetailRequestResource> getPaymentStructures() {
		return paymentStructures;
	}

	public void setPaymentStructures(List<FacilityDetailRequestResource> paymentStructures) {
		this.paymentStructures = paymentStructures;
	}
	
	public List<FacilityStructureRequestResource> getFacilityStructures() {
		return facilityStructures;
	}

	public void setFacilityStructures(List<FacilityStructureRequestResource> facilityStructures) {
		this.facilityStructures = facilityStructures;
	}

	public List<FacilityCalculationTaxRequestResource> getTaxes() {
		return taxes;
	}

	public void setTaxes(List<FacilityCalculationTaxRequestResource> taxes) {
		this.taxes = taxes;
	}

	public List<FacilityCalculationFixedChargesRequestResource> getFixedCharges() {
		return fixedCharges;
	}

	public void setFixedCharges(List<FacilityCalculationFixedChargesRequestResource> fixedCharges) {
		this.fixedCharges = fixedCharges;
	}

	public List<FacilityCalculationOptionalChargesRequestResource> getOptionalCharges() {
		return optionalCharges;
	}

	public void setOptionalCharges(List<FacilityCalculationOptionalChargesRequestResource> optionalCharges) {
		this.optionalCharges = optionalCharges;
	}

	public List<FacilityCalculationPeriodicChargesRequestResource> getPeriodicCharges() {
		return periodicCharges;
	}

	public void setPeriodicCharges(List<FacilityCalculationPeriodicChargesRequestResource> periodicCharges) {
		this.periodicCharges = periodicCharges;
	}
}
