package com.fusionx.lending.origination.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fusionx.lending.origination.core.BaseEntity;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.enums.InterestRepaymentMethod;
import com.fusionx.lending.origination.enums.RepaymentCriteria;
import com.fusionx.lending.origination.enums.Rewards;

import lombok.Data;

@Entity
@Table(name = "facility_parameter")
@Data
public class FacilityParameter extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 53254346334L;

	@Column(name = "tenant_id", length=10, nullable=false)
	private String tenantId;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "tlead_info_id", nullable=false)
	private LeadInfo leadInfo;
	
	@Column(name = "product_id" , nullable=true)
	private Long productId;
	
	@Column(name = "product_code", length=20, nullable=false)
	private String productCode;
	
	@Column(name = "product_name", length=255, nullable=true)
	private String productName;
	
	@Column(name = "sub_product_id" , nullable=true)
	private Long subProductId;
	
	@Column(name = "sub_product_code", length=20, nullable=false)
	private String subProductCode;
	
	@Column(name = "sub_product_name", length=255, nullable=true)
	private String subProductName;
	
	@Column(name = "calculation_method_id" , nullable=true)
	private Long calculationMethodId;
	
	@Column(name = "calculation_method_code", length=20, nullable=false)
	private String calculationMethodCode;
	
	@Column(name = "calculation_method_name", length=255, nullable=true)
	private String calculationMethodName;
	
	@Column(name = "payment_frequency_id" , nullable=true)
	private Long paymentFrequencyId;
	
	@Column(name = "payment_frequency_code", length=20, nullable=false)
	private String paymentFrequencyCode;
	
	@Column(name = "payment_frequency_name", length=255, nullable=true)
	private String paymentFrequencyName;
	
	@Column(name = "currency_code", length=20, nullable=false)
	private String currencyCode;
	
	@Column(name="loan_amount", columnDefinition="Decimal(25,5)", nullable=false)
    private BigDecimal loanAmount;
	
	@Column(name = "term", nullable=false)
	private Long term;
	
	@Column(name="rate", nullable=false)
    private Float rate;
	
	@Column(name="approved_limit", columnDefinition="Decimal(25,5)", nullable=true)
    private BigDecimal approvedLimit;
	
	@Column(name="initial_disbursement", columnDefinition="Decimal(25,5)", nullable=true)
    private BigDecimal initialDisbursement;
	
	@Column(name = "due_date", nullable=true)
	private Date dueDate;
	
	@Column(name = "expiry_date", nullable=true)
	private Date expiryDate;
	
	@Column(name="penal_rate", nullable=true)
    private Float penalRate;
	
	@Column(name = "grace_period", nullable=true)
	private Long gracePeriod;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "int_repayment_method", length=20, nullable=true)
	private InterestRepaymentMethod interestRepaymentMethod;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "repayment_criteria", length=20, nullable=true)
	private RepaymentCriteria repaymentCriteria;
	
	@Column(name = "no_of_pre_payment", nullable=true)
	private Long noOfPrePayment;
	
	@Column(name="amount_paid_in_advance", columnDefinition="Decimal(25,5)", nullable=true)
    private BigDecimal amountPaidInAdvance;
	
	@Column(name="residual_value", columnDefinition="Decimal(25,5)", nullable=true)
    private BigDecimal residualValue;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "rewards", length=20, nullable=true)
	private Rewards rewards;
	
	@Column(name = "remaks", length=255, nullable=true)
	private String remaks;
	
	@Column(name="loan_amount_with_tax", columnDefinition="Decimal(25,5)", nullable=true)
    private BigDecimal loanAmountWithTax;
	
	@Column(name="amount_finance", columnDefinition="Decimal(25,5)", nullable=true)
    private BigDecimal amountFinance;
	
	@Column(name="total_receivable", columnDefinition="Decimal(25,5)", nullable=true)
    private BigDecimal totalReceivable;
	
	@Column(name="pre_paid_installment", columnDefinition="Decimal(25,5)", nullable=true)
    private BigDecimal prePaidInstallment;
	
	@Column(name="down_payment_amount", columnDefinition="Decimal(25,5)", nullable=true)
    private BigDecimal downPaymentAmount;
	
	@Column(name="total_taxes", columnDefinition="Decimal(25,5)", nullable=true)
    private BigDecimal totalTaxes;
	
	@Column(name="total_charges", columnDefinition="Decimal(25,5)", nullable=true)
    private BigDecimal totalCharges;
	
	@Column(name="lease_factor", nullable=true)
    private Float leaseFactor;
	
	@Column(name="charges_factor", nullable=true)
    private Float chargesFactor;
	
	@Column(name="total_factor", nullable=true)
    private Float totalFactor;
	
	@Column(name = "branch_id", nullable=true)
	private Long branchId;
	
	@Column(name = "branch_code", length=20, nullable=true)
	private String branchCode;
	
	@Column(name = "tc_no", length=20, nullable=true)
	private String tcNo;
	
	@Column(name = "quotation_no", length=20, nullable=true)
	private String quotationNo;
	
	@Column(name = "attribute1", nullable=true)
	private Long attribute1;
	
	@Column(name = "attribute2", nullable=true)
	private Long attribute2;
	
	@Column(name = "attribute3", nullable=true)
	private Long attribute3;
	
	@Column(name = "attribute4", nullable=true)
	private Long attribute4;
	
	@Column(name = "attribute5", nullable=true)
	private Long attribute5;
	
	@Column(name = "attribute6",length=255, nullable=true)
	private String attribute6;
	
	@Column(name = "attribute7",length=255, nullable=true)
	private String attribute7;
	
	@Column(name = "attribute8",length=255, nullable=true)
	private String attribute8;
	
	@Column(name = "attribute9",length=255, nullable=true)
	private String attribute9;
	
	@Column(name = "attribute10",length=255, nullable=true)
	private String attribute10;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "status", length=20, nullable=false)
	private CommonStatus status;
	
	@Column(name = "created_user")
	private String createdUser;
	
	@Column(name = "created_date")
	private Timestamp createdDate;
	
	@Column(name = "modified_user")
	private String modifiedUser;
	
	@Column(name = "modified_date")
	private Timestamp modifiedDate;
	
	@Transient
	private List<FacilityDetail> facilityDetails;
	
	@Transient
	private List<FacilityStructure> facilityStructure;
	
	@Transient
	private List<FacilityCharges> facilityCharges;
	
	@Transient
	private List<FacilityTax> facilityTaxes;

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public LeadInfo getLeadInfo() {
		return leadInfo;
	}

	public void setLeadInfo(LeadInfo leadInfo) {
		this.leadInfo = leadInfo;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

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

	public Long getSubProductId() {
		return subProductId;
	}

	public void setSubProductId(Long subProductId) {
		this.subProductId = subProductId;
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

	public Long getCalculationMethodId() {
		return calculationMethodId;
	}

	public void setCalculationMethodId(Long calculationMethodId) {
		this.calculationMethodId = calculationMethodId;
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

	public Long getPaymentFrequencyId() {
		return paymentFrequencyId;
	}

	public void setPaymentFrequencyId(Long paymentFrequencyId) {
		this.paymentFrequencyId = paymentFrequencyId;
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

	public BigDecimal getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(BigDecimal loanAmount) {
		this.loanAmount = loanAmount;
	}

	public Long getTerm() {
		return term;
	}

	public void setTerm(Long term) {
		this.term = term;
	}

	public Float getRate() {
		return rate;
	}

	public void setRate(Float rate) {
		this.rate = rate;
	}

	public BigDecimal getApprovedLimit() {
		return approvedLimit;
	}

	public void setApprovedLimit(BigDecimal approvedLimit) {
		this.approvedLimit = approvedLimit;
	}

	public BigDecimal getInitialDisbursement() {
		return initialDisbursement;
	}

	public void setInitialDisbursement(BigDecimal initialDisbursement) {
		this.initialDisbursement = initialDisbursement;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public Float getPenalRate() {
		return penalRate;
	}

	public void setPenalRate(Float penalRate) {
		this.penalRate = penalRate;
	}

	public Long getGracePeriod() {
		return gracePeriod;
	}

	public void setGracePeriod(Long gracePeriod) {
		this.gracePeriod = gracePeriod;
	}

	public InterestRepaymentMethod getInterestRepaymentMethod() {
		return interestRepaymentMethod;
	}

	public void setInterestRepaymentMethod(InterestRepaymentMethod interestRepaymentMethod) {
		this.interestRepaymentMethod = interestRepaymentMethod;
	}
	
	public void setInterestRepaymentMethod(String interestRepaymentMethod) {
		this.interestRepaymentMethod = InterestRepaymentMethod.valueOf(interestRepaymentMethod);
	}

	public RepaymentCriteria getRepaymentCriteria() {
		return repaymentCriteria;
	}

	public void setRepaymentCriteria(RepaymentCriteria repaymentCriteria) {
		this.repaymentCriteria = repaymentCriteria;
	}
	
	public void setRepaymentCriteria(String repaymentCriteria) {
		this.repaymentCriteria = RepaymentCriteria.valueOf(repaymentCriteria);
	}

	public Long getNoOfPrePayment() {
		return noOfPrePayment;
	}

	public void setNoOfPrePayment(Long noOfPrePayment) {
		this.noOfPrePayment = noOfPrePayment;
	}

	public BigDecimal getAmountPaidInAdvance() {
		return amountPaidInAdvance;
	}

	public void setAmountPaidInAdvance(BigDecimal amountPaidInAdvance) {
		this.amountPaidInAdvance = amountPaidInAdvance;
	}

	public BigDecimal getResidualValue() {
		return residualValue;
	}

	public void setResidualValue(BigDecimal residualValue) {
		this.residualValue = residualValue;
	}

	public Rewards getRewards() {
		return rewards;
	}

	public void setRewards(Rewards rewards) {
		this.rewards = rewards;
	}
	
	public void setRewards(String rewards) {
		this.rewards = Rewards.valueOf(rewards);
	}

	public String getRemaks() {
		return remaks;
	}

	public void setRemaks(String remaks) {
		this.remaks = remaks;
	}

	public BigDecimal getLoanAmountWithTax() {
		return loanAmountWithTax;
	}

	public void setLoanAmountWithTax(BigDecimal loanAmountWithTax) {
		this.loanAmountWithTax = loanAmountWithTax;
	}

	public BigDecimal getAmountFinance() {
		return amountFinance;
	}

	public void setAmountFinance(BigDecimal amountFinance) {
		this.amountFinance = amountFinance;
	}

	public BigDecimal getTotalReceivable() {
		return totalReceivable;
	}

	public void setTotalReceivable(BigDecimal totalReceivable) {
		this.totalReceivable = totalReceivable;
	}

	public BigDecimal getPrePaidInstallment() {
		return prePaidInstallment;
	}

	public void setPrePaidInstallment(BigDecimal prePaidInstallment) {
		this.prePaidInstallment = prePaidInstallment;
	}

	public BigDecimal getDownPaymentAmount() {
		return downPaymentAmount;
	}

	public void setDownPaymentAmount(BigDecimal downPaymentAmount) {
		this.downPaymentAmount = downPaymentAmount;
	}

	public BigDecimal getTotalTaxes() {
		return totalTaxes;
	}

	public void setTotalTaxes(BigDecimal totalTaxes) {
		this.totalTaxes = totalTaxes;
	}

	public BigDecimal getTotalCharges() {
		return totalCharges;
	}

	public void setTotalCharges(BigDecimal totalCharges) {
		this.totalCharges = totalCharges;
	}

	public Float getLeaseFactor() {
		return leaseFactor;
	}

	public void setLeaseFactor(Float leaseFactor) {
		this.leaseFactor = leaseFactor;
	}

	public Float getChargesFactor() {
		return chargesFactor;
	}

	public void setChargesFactor(Float chargesFactor) {
		this.chargesFactor = chargesFactor;
	}

	public Float getTotalFactor() {
		return totalFactor;
	}

	public void setTotalFactor(Float totalFactor) {
		this.totalFactor = totalFactor;
	}

	public Long getBranchId() {
		return branchId;
	}

	public void setBranchId(Long branchId) {
		this.branchId = branchId;
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

	public Long getAttribute1() {
		return attribute1;
	}

	public void setAttribute1(Long attribute1) {
		this.attribute1 = attribute1;
	}

	public Long getAttribute2() {
		return attribute2;
	}

	public void setAttribute2(Long attribute2) {
		this.attribute2 = attribute2;
	}

	public Long getAttribute3() {
		return attribute3;
	}

	public void setAttribute3(Long attribute3) {
		this.attribute3 = attribute3;
	}

	public Long getAttribute4() {
		return attribute4;
	}

	public void setAttribute4(Long attribute4) {
		this.attribute4 = attribute4;
	}

	public Long getAttribute5() {
		return attribute5;
	}

	public void setAttribute5(Long attribute5) {
		this.attribute5 = attribute5;
	}

	public String getAttribute6() {
		return attribute6;
	}

	public void setAttribute6(String attribute6) {
		this.attribute6 = attribute6;
	}

	public String getAttribute7() {
		return attribute7;
	}

	public void setAttribute7(String attribute7) {
		this.attribute7 = attribute7;
	}

	public String getAttribute8() {
		return attribute8;
	}

	public void setAttribute8(String attribute8) {
		this.attribute8 = attribute8;
	}

	public String getAttribute9() {
		return attribute9;
	}

	public void setAttribute9(String attribute9) {
		this.attribute9 = attribute9;
	}

	public String getAttribute10() {
		return attribute10;
	}

	public void setAttribute10(String attribute10) {
		this.attribute10 = attribute10;
	}

	public CommonStatus getStatus() {
		return status;
	}

	public void setStatus(CommonStatus status) {
		this.status = status;
	}

	public String getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public String getModifiedUser() {
		return modifiedUser;
	}

	public void setModifiedUser(String modifiedUser) {
		this.modifiedUser = modifiedUser;
	}

	public Timestamp getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public List<FacilityDetail> getFacilityDetails() {
		return facilityDetails;
	}

	public void setFacilityDetails(List<FacilityDetail> facilityDetails) {
		this.facilityDetails = facilityDetails;
	}

	public List<FacilityStructure> getFacilityStructure() {
		return facilityStructure;
	}

	public void setFacilityStructure(List<FacilityStructure> facilityStructure) {
		this.facilityStructure = facilityStructure;
	}

	public List<FacilityCharges> getFacilityCharges() {
		return facilityCharges;
	}

	public void setFacilityCharges(List<FacilityCharges> facilityCharges) {
		this.facilityCharges = facilityCharges;
	}

	public List<FacilityTax> getFacilityTaxes() {
		return facilityTaxes;
	}

	public void setFacilityTaxes(List<FacilityTax> facilityTaxes) {
		this.facilityTaxes = facilityTaxes;
	}
}
