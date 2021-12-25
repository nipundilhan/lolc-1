package com.fusionx.lending.product.resources;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Lending account detail Add Resource
 *
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   07-10-2021      	-	     	-		Thushan      Created
 *
 ********************************************************************************************************
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class LendingAccountDetailAddResource {

    private String tenantId;

    @NotBlank(message = "{common.not-null}")
    @Size(max = 50, message = "{common.account-number.size}")
    private String accountNumber;

    @NotBlank(message = "{common.not-null}")
    @Pattern(regexp = "^$|\\d{1,20}\\.\\d{1,2}$", message = "{common.invalid-amount-pattern-38-2}" + " into loan amount")
    private String loanAmount;

    @NotBlank(message = "{common.not-null}")
    @Pattern(regexp = "DIRECT_CREDIT|TELLER_RECEIPT|SELLER_FROM_LOAN", message = "{collection-method.pattern}")
    private String collectionMethod;

    @NotBlank(message = "{common.not-null}")
    @Pattern(regexp = "^$|\\d{1,20}\\.\\d{1,2}$", message = "{common.invalid-amount-pattern-8-2}" + " into interest rate")
    private String interestRate;

    @NotBlank(message = "{common.not-null}")
    private String termNumber;

    @NotBlank(message = "{common.not-null}")
    @Pattern(regexp = "FIXD|STRU|REVO", message = "{calculation-method.pattern}")
    private String calculationMethod;

    @NotBlank(message = "{common.not-null}")
    @Pattern(regexp = "IN_ADVANCE|IN_ARRERS", message = "{calculation-method.pattern}")
    private String repaymentCriteria;

    @NotBlank(message = "{common.not-null}")
    private String noOfPrePayment;

    @NotBlank(message = "{common.not-null}")
    private String amountPaidInAdvanced;

    @NotBlank(message = "{common.not-null}")
    @Pattern(regexp = "^$|\\d{1,20}\\.\\d{1,2}$", message = "{common.invalid-amount-pattern-38-2}"+" into residual value")
    private String residualValue;

    @NotBlank(message = "{common.not-null}")
    @Pattern(regexp = "YES|NO", message = "{common-enable.pattern}")
    private String rewards;

    @NotBlank(message = "{common.not-null}")
    @Size(max = 500, message = "{remark-500.size}")
    private String remarks;

    @NotBlank(message = "{common.not-null}")
    @Pattern(regexp = "^$|\\d{1,20}\\.\\d{1,2}$", message = "{common.invalid-amount-pattern-38-2}" + " into loan amount with tax")
    private String loanAmountWithTax;

    @NotBlank(message = "{common.not-null}")
    @Pattern(regexp = "^$|\\d{1,20}\\.\\d{1,2}$", message = "{common.invalid-amount-pattern-38-2}" + " into total receivable")
    private String totalReceivable;

    @NotBlank(message = "{common.not-null}")
    @Pattern(regexp = "^$|\\d{1,20}\\.\\d{1,2}$", message = "{common.invalid-amount-pattern-38-2}" + " into down payment amount")
    private String downPaymentAmount;

    @NotBlank(message = "{common.not-null}")
    @Pattern(regexp = "^$|\\d{1,20}\\.\\d{1,2}$", message = "{common.invalid-amount-pattern-8-2}" + " into lease factory")
    private String leaseFactor;

    @NotBlank(message = "{common.not-null}")
    @Pattern(regexp = "^$|\\d{1,20}\\.\\d{1,2}$", message = "{common.invalid-amount-pattern-8-2}" + " into change factory")
    private String chargeFactor;

    @NotBlank(message = "{common.not-null}")
    @Pattern(regexp = "^$|\\d{1,20}\\.\\d{1,2}$", message = "{common.invalid-amount-pattern-8-2}" + " into total factory")
    private String totalFactor;

    @NotBlank(message = "{common.not-null}")
    @Pattern(regexp = "^$|\\d{1,20}\\.\\d{1,2}$", message = "{common.invalid-amount-pattern-8-2}" + " into IRR")
    private String irr;

    @NotBlank(message = "{common.not-null}")
    @Pattern(regexp = "^$|\\d{4}(\\-)(((0)[0-9])|((1)[0-2]))(\\-)([0-2][0-9]|(3)[0-1])$", message = "{common.invalid-date-pattern}" + " into due date")
    private String dueDate;

    @NotBlank(message = "{common.not-null}")
    @Pattern(regexp = "^$|\\d{1,20}\\.\\d{1,2}$", message = "{common.invalid-amount-pattern-8-2}" + " into penal interest rate")
    private String penalInterestRate;

    @NotBlank(message = "{common.not-null}")
    private String gracePeriod;

    @NotBlank(message = "{common.not-null}")
    @Pattern(regexp = "^$|\\d{4}(\\-)(((0)[0-9])|((1)[0-2]))(\\-)([0-2][0-9]|(3)[0-1])$", message = "{common.invalid-date-pattern}" + " into expire date")
    private String expiryDate;

    @NotBlank(message = "{common.not-null}")
    @Size(max = 20, message = "Currency code" + "{common.size.grater.than}" + "20 characters")
    private String currencyCode;

    @NotBlank(message = "{common.not-null}")
    private String currencyCodeNumeric;

    @NotBlank(message = "{common.not-null}")
    @Pattern(regexp = "ACTIVE|INACTIVE", message = "{common-status.pattern}")
    private String status;

    @NotBlank(message = "{common.not-null}")
    @Pattern(regexp = "INITIATE|CREATED", message = "{common-enable.pattern}")
    private String accountStatus;


    @NotBlank(message = "{common.not-null}")
    private String productId;

    @NotBlank(message = "{common.not-null}")
    private String subProductId;

    @NotBlank(message = "{common.not-null}")
    private String branchId;

    @NotBlank(message = "{common.not-null}")
    private String meId;

    @NotBlank(message = "{common.not-null}")
    private String currencyId;

    @NotBlank(message = "{common.not-null}")
    private String tcId;

    @NotBlank(message = "{common.not-null}")
    private String approvedLeadId;

    private String recoveryAccountId;

    private String documentTemplateId;

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(String loanAmount) {
        this.loanAmount = loanAmount;
    }

    public String getCollectionMethod() {
        return collectionMethod;
    }

    public void setCollectionMethod(String collectionMethod) {
        this.collectionMethod = collectionMethod;
    }

    public String getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(String interestRate) {
        this.interestRate = interestRate;
    }

    public String getTermNumber() {
        return termNumber;
    }

    public void setTermNumber(String termNumber) {
        this.termNumber = termNumber;
    }

    public String getCalculationMethod() {
        return calculationMethod;
    }

    public void setCalculationMethod(String calculationMethod) {
        this.calculationMethod = calculationMethod;
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

    public String getAmountPaidInAdvanced() {
        return amountPaidInAdvanced;
    }

    public void setAmountPaidInAdvanced(String amountPaidInAdvanced) {
        this.amountPaidInAdvanced = amountPaidInAdvanced;
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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getLoanAmountWithTax() {
        return loanAmountWithTax;
    }

    public void setLoanAmountWithTax(String loanAmountWithTax) {
        this.loanAmountWithTax = loanAmountWithTax;
    }

    public String getTotalReceivable() {
        return totalReceivable;
    }

    public void setTotalReceivable(String totalReceivable) {
        this.totalReceivable = totalReceivable;
    }

    public String getDownPaymentAmount() {
        return downPaymentAmount;
    }

    public void setDownPaymentAmount(String downPaymentAmount) {
        this.downPaymentAmount = downPaymentAmount;
    }

    public String getLeaseFactor() {
        return leaseFactor;
    }

    public void setLeaseFactor(String leaseFactor) {
        this.leaseFactor = leaseFactor;
    }

    public String getChargeFactor() {
        return chargeFactor;
    }

    public void setChargeFactor(String chargeFactor) {
        this.chargeFactor = chargeFactor;
    }

    public String getTotalFactor() {
        return totalFactor;
    }

    public void setTotalFactor(String totalFactor) {
        this.totalFactor = totalFactor;
    }

    public String getIrr() {
        return irr;
    }

    public void setIrr(String irr) {
        this.irr = irr;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getPenalInterestRate() {
        return penalInterestRate;
    }

    public void setPenalInterestRate(String penalInterestRate) {
        this.penalInterestRate = penalInterestRate;
    }

    public String getGracePeriod() {
        return gracePeriod;
    }

    public void setGracePeriod(String gracePeriod) {
        this.gracePeriod = gracePeriod;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencyCodeNumeric() {
        return currencyCodeNumeric;
    }

    public void setCurrencyCodeNumeric(String currencyCodeNumeric) {
        this.currencyCodeNumeric = currencyCodeNumeric;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getSubProductId() {
        return subProductId;
    }

    public void setSubProductId(String subProductId) {
        this.subProductId = subProductId;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getMeId() {
        return meId;
    }

    public void setMeId(String meId) {
        this.meId = meId;
    }

    public String getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }

    public String getTcId() {
        return tcId;
    }

    public void setTcId(String tcId) {
        this.tcId = tcId;
    }

    public String getApprovedLeadId() {
        return approvedLeadId;
    }

    public void setApprovedLeadId(String approvedLeadId) {
        this.approvedLeadId = approvedLeadId;
    }

    public String getRecoveryAccountId() {
        return recoveryAccountId;
    }

    public void setRecoveryAccountId(String recoveryAccountId) {
        this.recoveryAccountId = recoveryAccountId;
    }

    public String getDocumentTemplateId() {
        return documentTemplateId;
    }

    public void setDocumentTemplateId(String documentTemplateId) {
        this.documentTemplateId = documentTemplateId;
    }
}
