package com.fusionx.lending.product.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

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
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fusionx.lending.product.core.BaseEntity;
import com.fusionx.lending.product.core.MasterPropertyBase;
import com.fusionx.lending.product.enums.CalculateMethodEnum;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.enums.RepaymentCriteriaEnum;
import com.fusionx.lending.product.enums.YesNo;

/**
 * Tc Header Domain
 * @author 	Nipun Dilhan
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   10-10-2021  						    NipunDilhan      Created
 *    
 ********************************************************************************************************
 */

@Entity
@Table(name = "tc_header")
@JsonIgnoreProperties({MasterPropertyBase.HIBERNATE_LAZY_INITIALIZER, MasterPropertyBase.JSON_INITIALIZER_HANDLER})
public class TcHeader  extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = 1988L;
	
	@Column(name = "loan_amount",  nullable=false)
	private BigDecimal loanAmount;
	
	@Column(name = "interest_rate",  nullable=false)
	private BigDecimal interestRate;
	
	@Column(name = "term", nullable=true)
	private Long term;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "calculation_method", length=20, nullable=false)
	private CalculateMethodEnum calculationMethod;
	
	@Column(name = "tc_expiry_date", nullable=false)
	private Timestamp tcExpiryDate;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "repayment_criteria", length=20, nullable=false)
	private RepaymentCriteriaEnum repaymentCriteria;
	
	@Column(name = "no_of_pre_payments", nullable=true)
	private Long noOfPrePayments;
	
	@Column(name = "amount_paid_in_advance",  nullable=true)
	private BigDecimal amountPaidInAdvance;
	
	@Column(name = "residual_value",  nullable=true)
	private BigDecimal residualValue;	
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "reward", length=20, nullable=true)
	private YesNo rewards;
	
	@Column(name = "remarks", nullable=true, length=500)
	private String remarks;
	
	@Column(name = "tenant_id", length=10, nullable=false)
	private String tenantId;
	
	@Column(name = "loan_amount_with_tax",  nullable=true)
	private BigDecimal loanAmountWithTax;
	
	@Column(name = "total_receivable",  nullable=true)
	private BigDecimal totalReceivable;
	
	@Column(name = "down_payment_amount",  nullable=true)
	private BigDecimal downPaymentAmount;
	
	@Column(name = "lease_factor",  nullable=true)
	private BigDecimal leaseFactor;
	
	@Column(name = "charges_factor",  nullable=true)
	private BigDecimal chargesFactor;
	
	@Column(name = "total_factor",  nullable=true)
	private BigDecimal totalFactor;

	@Column(name = "irr",  nullable=true)
	private BigDecimal irr;	
	
	@Column(name = "due_date", nullable=true)
	private Long dueDate;
	
	@Column(name = "penal_interest_rate",  nullable=true)
	private BigDecimal penalInterestRate;

	@Column(name = "grace_period", nullable=true)
	private Long gracePeriod;
	
	
	@Column(name = "currency_code", nullable=true, length=20)
	private String currencyCode;
	
	
	@Column(name = "currency_code_numeric", nullable=true, length=20)
	private String currencyCodeNumeric;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "repayment_frequency_id", nullable=false)  
	private RepaymentFrequency repaymentFrequency;
	

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "product_id", nullable=false)  
	private Product product;
	

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "sub_product_id", nullable=false)  
	private SubProduct subProduct;
	
	@Column(name = "currency_id", nullable=true)
	private Long currencyId;
	
	@Column(name = "lead_id", nullable=true)
	private Long leadId;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinColumn(name = "tc_revolving_detail_id", nullable=true)  
	private TcRevolvingDetail tcRevolvingDetail;
	
	@Column(name = "created_user", length=255, nullable=false)
	private String createdUser;
	
	@Column(name = "created_date", nullable=false)
	private Timestamp createdDate;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinColumn(name = "tc_structured_detail_id", nullable=true)  
	private TcStructuredDetail tcStructuredDetail;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinColumn(name = "tc_amortization_detail_id", nullable=true)  
	private TcAmortizationDetail tcAmortizationDetail;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "status", length=20, nullable=true)
	private CommonStatus status;
	
	
	@Transient
	@JsonInclude(Include.NON_NULL)
	private Optional<TcRevolvingDetail> tcRevolvingDetails;
	
	@Transient
	@JsonInclude(Include.NON_NULL)
	private List<TcFixedCharges> fixedChargesDetails;
	
	@Transient
	@JsonInclude(Include.NON_NULL)
	private List<TcOptionsCharges> optionsChargesDetails;
	
	@Transient
	@JsonInclude(Include.NON_NULL)
	private List<TcAdhocCharges> adhocChargesDetails;
	
	@Transient
	@JsonInclude(Include.NON_NULL)
	private List<TcStructuredPayment> tcStructuredPayment;
	
	@Transient
	@JsonInclude(Include.NON_NULL)
	private List<TcStructuredPaymentSchedule> tcStructuredPaymentSchedule;
	
	@Transient
	@JsonInclude(Include.NON_NULL)
	private TcAmortizationDetail tcAmortizeDetail;
	
	

	public TcAmortizationDetail getTcAmortizeDetail() {
		return tcAmortizeDetail;
	}

	public void setTcAmortizeDetail(TcAmortizationDetail tcAmortizeDetail) {
		this.tcAmortizeDetail = tcAmortizeDetail;
	}

	public TcAmortizationDetail getTcAmortizationDetail() {
		return tcAmortizationDetail;
	}

	public void setTcAmortizationDetail(TcAmortizationDetail tcAmortizationDetail) {
		this.tcAmortizationDetail = tcAmortizationDetail;
	}

	public Optional<TcRevolvingDetail> getTcRevolvingDetails() {
		return tcRevolvingDetails;
	}

	public void setTcRevolvingDetails(Optional<TcRevolvingDetail> tcRevolvingDetails) {
		this.tcRevolvingDetails = tcRevolvingDetails;
	}

	public List<TcFixedCharges> getFixedChargesDetails() {
		return fixedChargesDetails;
	}

	public void setFixedChargesDetails(List<TcFixedCharges> fixedChargesDetails) {
		this.fixedChargesDetails = fixedChargesDetails;
	}

	public List<TcOptionsCharges> getOptionsChargesDetails() {
		return optionsChargesDetails;
	}

	public void setOptionsChargesDetails(List<TcOptionsCharges> optionsChargesDetails) {
		this.optionsChargesDetails = optionsChargesDetails;
	}

	public List<TcAdhocCharges> getAdhocChargesDetails() {
		return adhocChargesDetails;
	}

	public void setAdhocChargesDetails(List<TcAdhocCharges> adhocChargesDetails) {
		this.adhocChargesDetails = adhocChargesDetails;
	}

	public BigDecimal getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(BigDecimal loanAmount) {
		this.loanAmount = loanAmount;
	}

	public BigDecimal getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(BigDecimal interestRate) {
		this.interestRate = interestRate;
	}

	public Long getTerm() {
		return term;
	}

	public void setTerm(Long term) {
		this.term = term;
	}

	public CalculateMethodEnum getCalculationMethod() {
		return calculationMethod;
	}

	public void setCalculationMethod(CalculateMethodEnum calculationMethod) {
		this.calculationMethod = calculationMethod;
	}

	public Timestamp getTcExpiryDate() {
		return tcExpiryDate;
	}

	public void setTcExpiryDate(Timestamp tcExpiryDate) {
		this.tcExpiryDate = tcExpiryDate;
	}

	public RepaymentCriteriaEnum getRepaymentCriteria() {
		return repaymentCriteria;
	}

	public void setRepaymentCriteria(RepaymentCriteriaEnum repaymentCriteria) {
		this.repaymentCriteria = repaymentCriteria;
	}

	public Long getNoOfPrePayments() {
		return noOfPrePayments;
	}

	public void setNoOfPrePayments(Long noOfPrePayments) {
		this.noOfPrePayments = noOfPrePayments;
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

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
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

	public BigDecimal getChargesFactor() {
		return chargesFactor;
	}

	public void setChargesFactor(BigDecimal chargesFactor) {
		this.chargesFactor = chargesFactor;
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

	public Long getDueDate() {
		return dueDate;
	}

	public void setDueDate(Long dueDate) {
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

	public RepaymentFrequency getRepaymentFrequency() {
		return repaymentFrequency;
	}

	public void setRepaymentFrequency(RepaymentFrequency repaymentFrequency) {
		this.repaymentFrequency = repaymentFrequency;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public SubProduct getSubProduct() {
		return subProduct;
	}

	public void setSubProduct(SubProduct subProduct) {
		this.subProduct = subProduct;
	}

	public Long getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(Long currencyId) {
		this.currencyId = currencyId;
	}

	public Long getLeadId() {
		return leadId;
	}

	public void setLeadId(Long leadId) {
		this.leadId = leadId;
	}

	public TcRevolvingDetail getTcRevolvingDetail() {
		return tcRevolvingDetail;
	}

	public void setTcRevolvingDetail(TcRevolvingDetail tcRevolvingDetail) {
		this.tcRevolvingDetail = tcRevolvingDetail;
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

	public TcStructuredDetail getTcStructuredDetail() {
		return tcStructuredDetail;
	}

	public void setTcStructuredDetail(TcStructuredDetail tcStructuredDetail) {
		this.tcStructuredDetail = tcStructuredDetail;
	}	

	public CommonStatus getStatus() {
		return status;
	}

	public void setStatus(CommonStatus status) {
		this.status = status;
	}

	public List<TcStructuredPayment> getTcStructuredPayment() {
		return tcStructuredPayment;
	}

	public void setTcStructuredPayment(List<TcStructuredPayment> tcStructuredPayment) {
		this.tcStructuredPayment = tcStructuredPayment;
	}

	public List<TcStructuredPaymentSchedule> getTcStructuredPaymentSchedule() {
		return tcStructuredPaymentSchedule;
	}

	public void setTcStructuredPaymentSchedule(List<TcStructuredPaymentSchedule> tcStructuredPaymentSchedule) {
		this.tcStructuredPaymentSchedule = tcStructuredPaymentSchedule;
	}

		
}
