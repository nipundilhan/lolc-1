package com.fusionx.lending.product.domain;

import com.fusionx.lending.product.core.BaseEntity;
import com.fusionx.lending.product.enums.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Lending Account Detail
 *
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   07-10-2021      		                Rohan      Created
 *
 ********************************************************************************************************
 */
@Entity
@Table(name = "lending_account_detail")
public class LendingAccountDetail extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 65364566L;

    @Column(name = "account_number", length = 50, nullable = false)
    private String accountNumber;

    @Column(name = "loan_amount", columnDefinition = "Decimal(38,2)")
    private BigDecimal loanAmount;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "collection_method")
    private CollectionMethodEnum collectionMethod;

    @Column(name = "interest_rate", columnDefinition = "Decimal(8,2)", nullable = false)
    private BigDecimal interestRate;

    @Column(name = "term_number", nullable = false)
    private Long termNumber;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "calculation_method", nullable = false)
    private CalculateMethodEnum calculationMethod;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "repayment_criteria", length = 50)
    private RepaymentCriteriaEnum repaymentCriteria;

    @Column(name = "no_of_pre_payment")
    private Long noOfPrePayment;

    @Column(name = "amount_paid_in_advanced", columnDefinition = "Decimal(38,2)")
    private BigDecimal amountPaidInAdvanced;

    @Column(name = "residual_value", columnDefinition = "Decimal(38,2)")
    private BigDecimal residualValue;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "rewards")
    private YesNo rewards;

    @Column(name = "remarks", length = 500)
    private String remarks;

    @Column(name = "loan_amount_with_tax", columnDefinition = "Decimal(38,2)")
    private BigDecimal loanAmountWithTax;

    @Column(name = "total_receivable", columnDefinition = "Decimal(38,2)")
    private BigDecimal totalReceivable;

    @Column(name = "down_payment_amount", columnDefinition = "Decimal(38,2)")
    private BigDecimal downPaymentAmount;

    @Column(name = "lease_factor", columnDefinition = "Decimal(8,2)")
    private BigDecimal leaseFactor;

    @Column(name = "charge_factor", columnDefinition = "Decimal(8,2)")
    private BigDecimal chargeFactor;

    @Column(name = "total_factor", columnDefinition = "Decimal(8,2)")
    private BigDecimal totalFactor;

    @Column(name = "irr", columnDefinition = "Decimal(8,2)")
    private BigDecimal irr;

    @Column(name = "due_date")
    private Timestamp dueDate;

    @Column(name = "penal_interest_rate", columnDefinition = "Decimal(8,2)")
    private BigDecimal penalInterestRate;

    @Column(name = "grace_period")
    private Long gracePeriod;

    @Column(name = "expiry_date")
    private Timestamp expiryDate;

    @Column(name = "currency_code", length = 20)
    private String currencyCode;

    @Column(name = "currency_code_numeric", length = 3)
    private String currencyCodeNumeric;

    @Column(name = "tenant_id", length = 10, nullable = false)
    private String tenantId;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status")
    private CommonStatus status;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "account_status")
    private AccountStatusEnum accountStatus;

    @Embedded
    @AttributeOverrides(value = {
            @AttributeOverride(name = "createdUser", column = @Column(name = "created_user")),
            @AttributeOverride(name = "createdDate", column = @Column(name = "created_date")),
            @AttributeOverride(name = "modifiedUser", column = @Column(name = "modified_user")),
            @AttributeOverride(name = "modifiedDate", column = @Column(name = "modified_date"))
    })
    private AuditData auditData;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "sub_product_id", nullable = false)
    private Long subProductId;

    @Column(name = "branch_id", nullable = false)
    private Long branchId;

    @Column(name = "me_id", nullable = false)
    private Long meId;

    @Column(name = "currency_id", nullable = false)
    private Long currencyId;

    @Column(name = "tc_id", nullable = false)
    private Long tcId;

    @Column(name = "approvedLead_id", nullable = false)
    private Long approvedLeadId;

    @Column(name = "recovery_account_id")
    private Long recoveryAccountId;

    @Column(name = "document_template_id")
    private Long documentTemplateId;

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(BigDecimal loanAmount) {
        this.loanAmount = loanAmount;
    }

    public CollectionMethodEnum getCollectionMethod() {
        return collectionMethod;
    }

    public void setCollectionMethod(CollectionMethodEnum collectionMethod) {
        this.collectionMethod = collectionMethod;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    public Long getTermNumber() {
        return termNumber;
    }

    public void setTermNumber(Long termNumber) {
        this.termNumber = termNumber;
    }

    public CalculateMethodEnum getCalculationMethod() {
        return calculationMethod;
    }

    public void setCalculationMethod(CalculateMethodEnum calculationMethod) {
        this.calculationMethod = calculationMethod;
    }

    public RepaymentCriteriaEnum getRepaymentCriteria() {
        return repaymentCriteria;
    }

    public void setRepaymentCriteria(RepaymentCriteriaEnum repaymentCriteria) {
        this.repaymentCriteria = repaymentCriteria;
    }

    public Long getNoOfPrePayment() {
        return noOfPrePayment;
    }

    public void setNoOfPrePayment(Long noOfPrePayment) {
        this.noOfPrePayment = noOfPrePayment;
    }

    public BigDecimal getAmountPaidInAdvanced() {
        return amountPaidInAdvanced;
    }

    public void setAmountPaidInAdvanced(BigDecimal amountPaidInAdvanced) {
        this.amountPaidInAdvanced = amountPaidInAdvanced;
    }

    public BigDecimal getResidualValue() {
        return residualValue;
    }

    public void setResidualValue(BigDecimal residualValue) {
        this.residualValue = residualValue;
    }

    public YesNo getRewards() {
        return rewards;
    }

    public void setRewards(YesNo rewards) {
        this.rewards = rewards;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public BigDecimal getLoanAmountWithTax() {
        return loanAmountWithTax;
    }

    public void setLoanAmountWithTax(BigDecimal loanAmountWithTax) {
        this.loanAmountWithTax = loanAmountWithTax;
    }

    public BigDecimal getTotalReceivable() {
        return totalReceivable;
    }

    public void setTotalReceivable(BigDecimal totalReceivable) {
        this.totalReceivable = totalReceivable;
    }

    public BigDecimal getDownPaymentAmount() {
        return downPaymentAmount;
    }

    public void setDownPaymentAmount(BigDecimal downPaymentAmount) {
        this.downPaymentAmount = downPaymentAmount;
    }

    public BigDecimal getLeaseFactor() {
        return leaseFactor;
    }

    public void setLeaseFactor(BigDecimal leaseFactor) {
        this.leaseFactor = leaseFactor;
    }

    public BigDecimal getChargeFactor() {
        return chargeFactor;
    }

    public void setChargeFactor(BigDecimal chargeFactor) {
        this.chargeFactor = chargeFactor;
    }

    public BigDecimal getTotalFactor() {
        return totalFactor;
    }

    public void setTotalFactor(BigDecimal totalFactor) {
        this.totalFactor = totalFactor;
    }

    public BigDecimal getIrr() {
        return irr;
    }

    public void setIrr(BigDecimal irr) {
        this.irr = irr;
    }

    public Timestamp getDueDate() {
        return dueDate;
    }

    public void setDueDate(Timestamp dueDate) {
        this.dueDate = dueDate;
    }

    public BigDecimal getPenalInterestRate() {
        return penalInterestRate;
    }

    public void setPenalInterestRate(BigDecimal penalInterestRate) {
        this.penalInterestRate = penalInterestRate;
    }

    public Long getGracePeriod() {
        return gracePeriod;
    }

    public void setGracePeriod(Long gracePeriod) {
        this.gracePeriod = gracePeriod;
    }

    public Timestamp getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Timestamp expiryDate) {
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

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public CommonStatus getStatus() {
        return status;
    }

    public void setStatus(CommonStatus status) {
        this.status = status;
    }

    public AccountStatusEnum getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(AccountStatusEnum accountStatus) {
        this.accountStatus = accountStatus;
    }

    public AuditData getAuditData() {
        return auditData;
    }

    public void setAuditData(AuditData auditData) {
        this.auditData = auditData;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }


    public Long getSubProductId() {
        return subProductId;
    }

    public void setSubProductId(Long subProductId) {
        this.subProductId = subProductId;
    }

    public Long getBranchId() {
        return branchId;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    public Long getMeId() {
        return meId;
    }

    public void setMeId(Long meId) {
        this.meId = meId;
    }

    public Long getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Long currencyId) {
        this.currencyId = currencyId;
    }

    public Long getTcId() {
        return tcId;
    }

    public void setTcId(Long tcId) {
        this.tcId = tcId;
    }

    public Long getApprovedLeadId() {
        return approvedLeadId;
    }

    public void setApprovedLeadId(Long approvedLeadId) {
        this.approvedLeadId = approvedLeadId;
    }

    public Long getRecoveryAccountId() {
        return recoveryAccountId;
    }

    public void setRecoveryAccountId(Long recoveryAccountId) {
        this.recoveryAccountId = recoveryAccountId;
    }

    public Long getDocumentTemplateId() {
        return documentTemplateId;
    }

    public void setDocumentTemplateId(Long documentTemplateId) {
        this.documentTemplateId = documentTemplateId;
    }
}
