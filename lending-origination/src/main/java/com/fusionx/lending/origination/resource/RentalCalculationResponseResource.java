package com.fusionx.lending.origination.resource;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fusionx.lending.origination.enums.ServiceStatus;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class RentalCalculationResponseResource {
	
	private String amountFinanced;
	private String assignedMeCode;
	private String calculationMethod;
	private List<RentalCalculationChargeDetailsResponseResource> chargeDetails;
	private String chargesOnPrePayment;
	private String companyCode;
	private String createdMeCode;
	private String currencyCode;
	private String customerCode;
	private String customerName;
	private String customerType;
	private String downPaymentAmount;
	private String employeeNo;
	private String leaseFactor;
	private String loanAmount;
	private String noOfDownPayments;
	private List<RentalCalculationOtherFactoredChargesResponseResource> otherFactoredCharges;
	private String branch;
	private String createdDate;
	private String createdUser;
	private String creditAmount;
	private String effectiveRate;
	private String flexiRateEnabled;
	private String installment;
	private String insuranceAvailable;
	private String invoiceValue;
	private String prePayments;
	private String product;
	private String pvOfResidualValue;
	private String quotationBranch;
	private String quotationDate;
	private String quotationNumber;
	private String quotationOnly;
	private String quotationValue;
	private List<RentalCalculationRentStructuresResponseResource> rentStructures;
	private String rentalsPaidInAdvance;
	private String repaymentFrequency;
	private String residualValue;
	private RentalCalculationRevolvingLoanDetailsResponseResource revolvingLoanDetails;
	private String stampDutyAppOnRent;
	private String stampDutyApplicable;
	private String subProduct;
	private String supplierCode;
	private List<RentalCalculationTaxDetailsResponseResource> taxDetails;
	private String taxOnPrePayment;
	private String taxStartMonth;
	private String term;
	private String totalFactor;
	private String totalReceivable;
	private List<RentalCalculationTransactionDetailsResponseResource> transactionDetails;
	
	private String errorDesc;
	private String errorMessage;
	private String contactPerson;
	
	private ServiceStatus serviceStatus;
	
	private String msg;
	
	public String getAmountFinanced() {
		return amountFinanced;
	}
	public void setAmountFinanced(String amountFinanced) {
		this.amountFinanced = amountFinanced;
	}
	public String getAssignedMeCode() {
		return assignedMeCode;
	}
	public void setAssignedMeCode(String assignedMeCode) {
		this.assignedMeCode = assignedMeCode;
	}
	public String getCalculationMethod() {
		return calculationMethod;
	}
	public void setCalculationMethod(String calculationMethod) {
		this.calculationMethod = calculationMethod;
	}
	public List<RentalCalculationChargeDetailsResponseResource> getChargeDetails() {
		return chargeDetails;
	}
	public void setChargeDetails(List<RentalCalculationChargeDetailsResponseResource> chargeDetails) {
		this.chargeDetails = chargeDetails;
	}
	public String getChargesOnPrePayment() {
		return chargesOnPrePayment;
	}
	public void setChargesOnPrePayment(String chargesOnPrePayment) {
		this.chargesOnPrePayment = chargesOnPrePayment;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getCreatedMeCode() {
		return createdMeCode;
	}
	public void setCreatedMeCode(String createdMeCode) {
		this.createdMeCode = createdMeCode;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	public String getCustomerCode() {
		return customerCode;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerType() {
		return customerType;
	}
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	public String getDownPaymentAmount() {
		return downPaymentAmount;
	}
	public void setDownPaymentAmount(String downPaymentAmount) {
		this.downPaymentAmount = downPaymentAmount;
	}
	public String getEmployeeNo() {
		return employeeNo;
	}
	public void setEmployeeNo(String employeeNo) {
		this.employeeNo = employeeNo;
	}
	public String getLeaseFactor() {
		return leaseFactor;
	}
	public void setLeaseFactor(String leaseFactor) {
		this.leaseFactor = leaseFactor;
	}
	public String getLoanAmount() {
		return loanAmount;
	}
	public void setLoanAmount(String loanAmount) {
		this.loanAmount = loanAmount;
	}
	public String getNoOfDownPayments() {
		return noOfDownPayments;
	}
	public void setNoOfDownPayments(String noOfDownPayments) {
		this.noOfDownPayments = noOfDownPayments;
	}
	public List<RentalCalculationOtherFactoredChargesResponseResource> getOtherFactoredCharges() {
		return otherFactoredCharges;
	}
	public void setOtherFactoredCharges(List<RentalCalculationOtherFactoredChargesResponseResource> otherFactoredCharges) {
		this.otherFactoredCharges = otherFactoredCharges;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getCreatedUser() {
		return createdUser;
	}
	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}
	public String getCreditAmount() {
		return creditAmount;
	}
	public void setCreditAmount(String creditAmount) {
		this.creditAmount = creditAmount;
	}
	public String getEffectiveRate() {
		return effectiveRate;
	}
	public void setEffectiveRate(String effectiveRate) {
		this.effectiveRate = effectiveRate;
	}
	public String getFlexiRateEnabled() {
		return flexiRateEnabled;
	}
	public void setFlexiRateEnabled(String flexiRateEnabled) {
		this.flexiRateEnabled = flexiRateEnabled;
	}
	public String getInstallment() {
		return installment;
	}
	public void setInstallment(String installment) {
		this.installment = installment;
	}
	public String getInsuranceAvailable() {
		return insuranceAvailable;
	}
	public void setInsuranceAvailable(String insuranceAvailable) {
		this.insuranceAvailable = insuranceAvailable;
	}
	public String getInvoiceValue() {
		return invoiceValue;
	}
	public void setInvoiceValue(String invoiceValue) {
		this.invoiceValue = invoiceValue;
	}
	public String getPrePayments() {
		return prePayments;
	}
	public void setPrePayments(String prePayments) {
		this.prePayments = prePayments;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public String getPvOfResidualValue() {
		return pvOfResidualValue;
	}
	public void setPvOfResidualValue(String pvOfResidualValue) {
		this.pvOfResidualValue = pvOfResidualValue;
	}
	public String getQuotationBranch() {
		return quotationBranch;
	}
	public void setQuotationBranch(String quotationBranch) {
		this.quotationBranch = quotationBranch;
	}
	public String getQuotationDate() {
		return quotationDate;
	}
	public void setQuotationDate(String quotationDate) {
		this.quotationDate = quotationDate;
	}
	public String getQuotationNumber() {
		return quotationNumber;
	}
	public void setQuotationNumber(String quotationNumber) {
		this.quotationNumber = quotationNumber;
	}
	public String getQuotationOnly() {
		return quotationOnly;
	}
	public void setQuotationOnly(String quotationOnly) {
		this.quotationOnly = quotationOnly;
	}
	public String getQuotationValue() {
		return quotationValue;
	}
	public void setQuotationValue(String quotationValue) {
		this.quotationValue = quotationValue;
	}
	public List<RentalCalculationRentStructuresResponseResource> getRentStructures() {
		return rentStructures;
	}
	public void setRentStructures(List<RentalCalculationRentStructuresResponseResource> rentStructures) {
		this.rentStructures = rentStructures;
	}
	public String getRentalsPaidInAdvance() {
		return rentalsPaidInAdvance;
	}
	public void setRentalsPaidInAdvance(String rentalsPaidInAdvance) {
		this.rentalsPaidInAdvance = rentalsPaidInAdvance;
	}
	public String getRepaymentFrequency() {
		return repaymentFrequency;
	}
	public void setRepaymentFrequency(String repaymentFrequency) {
		this.repaymentFrequency = repaymentFrequency;
	}
	public String getResidualValue() {
		return residualValue;
	}
	public void setResidualValue(String residualValue) {
		this.residualValue = residualValue;
	}
	public RentalCalculationRevolvingLoanDetailsResponseResource getRevolvingLoanDetails() {
		return revolvingLoanDetails;
	}
	public void setRevolvingLoanDetails(RentalCalculationRevolvingLoanDetailsResponseResource revolvingLoanDetails) {
		this.revolvingLoanDetails = revolvingLoanDetails;
	}
	public String getStampDutyAppOnRent() {
		return stampDutyAppOnRent;
	}
	public void setStampDutyAppOnRent(String stampDutyAppOnRent) {
		this.stampDutyAppOnRent = stampDutyAppOnRent;
	}
	public String getStampDutyApplicable() {
		return stampDutyApplicable;
	}
	public void setStampDutyApplicable(String stampDutyApplicable) {
		this.stampDutyApplicable = stampDutyApplicable;
	}
	public String getSubProduct() {
		return subProduct;
	}
	public void setSubProduct(String subProduct) {
		this.subProduct = subProduct;
	}
	public String getSupplierCode() {
		return supplierCode;
	}
	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}
	public List<RentalCalculationTaxDetailsResponseResource> getTaxDetails() {
		return taxDetails;
	}
	public void setTaxDetails(List<RentalCalculationTaxDetailsResponseResource> taxDetails) {
		this.taxDetails = taxDetails;
	}
	public String getTaxOnPrePayment() {
		return taxOnPrePayment;
	}
	public void setTaxOnPrePayment(String taxOnPrePayment) {
		this.taxOnPrePayment = taxOnPrePayment;
	}
	public String getTaxStartMonth() {
		return taxStartMonth;
	}
	public void setTaxStartMonth(String taxStartMonth) {
		this.taxStartMonth = taxStartMonth;
	}
	public String getTerm() {
		return term;
	}
	public void setTerm(String term) {
		this.term = term;
	}
	public String getTotalFactor() {
		return totalFactor;
	}
	public void setTotalFactor(String totalFactor) {
		this.totalFactor = totalFactor;
	}
	public String getTotalReceivable() {
		return totalReceivable;
	}
	public void setTotalReceivable(String totalReceivable) {
		this.totalReceivable = totalReceivable;
	}
	public List<RentalCalculationTransactionDetailsResponseResource> getTransactionDetails() {
		return transactionDetails;
	}
	public void setTransactionDetails(List<RentalCalculationTransactionDetailsResponseResource> transactionDetails) {
		this.transactionDetails = transactionDetails;
	}
	public String getErrorDesc() {
		return errorDesc;
	}
	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public String getContactPerson() {
		return contactPerson;
	}
	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}
	public ServiceStatus getServiceStatus() {
		return serviceStatus;
	}
	public void setServiceStatus(ServiceStatus serviceStatus) {
		this.serviceStatus = serviceStatus;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
	
	

}
