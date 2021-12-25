package com.fusionx.lending.product.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fusionx.lending.product.core.BaseEntity;


/**
 * Sub Product Service 
 * @author 	Sanatha
 * @Date    19-JUL-2021
 * 
 ********************************************************************************************************
 *  ###   	Date         	Story Point   	Task No    		Author      	 Description
 *-------------------------------------------------------------------------------------------------------
 *    1   	19-JUL-2021  	FXL-25      	FXL-25   		Sanatha     	 Initial Development.
 *    
 ********************************************************************************************************
 */
@Entity
@Table(name = "sub_product_pending")
public class SubProductPending extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = 1988L;
	
	@Column(name = "tenant_id", length=10, nullable=false)
	private String tenantId;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "sub_product_id", nullable=true)
	private SubProduct subProduct;
	
	@Transient
    private Long subProdId;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "lending_workflow_id", nullable=true)
	private LendingWorkflow lendingWorkflow;
	
	@Transient
    private Long lendingWorkflowId;
	
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "product_id", nullable=false)
	private Product product;
	
	@Transient
    private Long productId;
	
	@Transient
    private String productCode;
	
	@Transient
    private String productName;
	
	@Column(name = "code", length=4, nullable=false)
	private String code;
	
	@Column(name = "name", length=350,  nullable=true)
	private String name;
	
	@Column(name = "predecessor_id",length=35, nullable=true)
	private String predecessorId;
	
	@Transient
    private String predecessorCode;
	
	@Transient
    private String predecessorName;
	
	@Column(name = "marketing_state_id",  nullable=false)
	private Long marketingStateId;
	
	@Transient
    private String marketingStateReferenceCode;
	
	@Transient
    private String marketingStateCode;
	
	@Transient
    private String marketingStateName;
	
	@Column(name = "first_marketed_date",  nullable=true)
	private Date firstMarketedDate;
	
	@Column(name = "last_marketed_date",  nullable=true)
	private Date lastMarketedDate;
	
	@Column(name = "state_tenure_length", nullable=true)
	private Long stateTenureLength;
	
	@Column(name = "state_tenure_period_id", nullable=true)
	private Long stateTenurePeriodId;
	
	@Transient
    private String stateTenurePeriodCode;
	
	@Transient
    private String stateTenurePeriodName;
	
	@Transient
    private String stateTenurePeriodDescription;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinColumn(name = "feature_benefit_template_id", nullable=true)
	private FeatureBenifitTemplate featureBenifitTemplate;
	
	@Transient
    private Long featureBenifitTemplateId;
	
	@Transient
    private String featureBenifitTemplateCode;
	
	@Transient
    private String featureBenifitTemplateName;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinColumn(name = "eligibility_template_id", nullable=true)
	private Eligibility eligibility;
	
	@Transient
    private Long eligibilityId;
	
	@Transient
    private String eligibilityCode;
	
	@Transient
    private String eligibilityName;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinColumn(name = "interest_template_id", nullable=true)
	private InterestTemplate interestTemplate;
	
	@Transient
    private Long interestTemplateId;
	
	@Transient
    private String interestTemplateCode;
	
	@Transient
    private String interestTemplateName;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinColumn(name = "repayment_template_id", nullable=true)
	private Repayment repayment;
	
	@Transient
    private Long repaymentId;
	
	@Transient
    private String repaymentCode;
	
	@Transient
    private String repaymentName;
	
	@Column(name="max_penal_interest_rate", columnDefinition="Decimal(25,5)", nullable=true)
	private BigDecimal maxPenalInterestRate;
	
	@Column(name="mini_penal_interest_rate", columnDefinition="Decimal(25,5)", nullable=true)
	private BigDecimal miniPenalInterestRate;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "penal_interest_id", nullable = true)
	private PenalInterest penalInterest;
	
	@Column(name = "core_product_id", nullable=true)
	private Long coreProductId;
	
	@Column(name = "status", length=20, nullable=false)
	private String status;
	
	@Column(name = "approve_status", length=30, nullable=true)
	private String approveStatus;
	
	@Column(name = "created_user", length=255, nullable=false)
	private String createdUser;
	
	@Column(name = "created_date", nullable=false)
	private Timestamp createdDate;
	
	@Column(name = "modified_user", length=255, nullable=true)
	private String modifiedUser;
	
	@Column(name = "modified_date", nullable=true)
	private Timestamp modifiedDate;
	
	@Column(name = "pen_approved_user", length=255, nullable=true)
	private String pendingApprovedUser;
	
	@Column(name = "pen_approved_date", nullable=true)
	private Timestamp pendingApprovedDate;
	
	@Column(name = "pen_rejected_user", length=255, nullable=true)
	private String pendingRejectedUser;
	
	@Column(name = "pen_rejected_date", nullable=true)
	private Timestamp penndingRejectedDate;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "loan_applicable_range_id", nullable = true)
	private LoanApplicableRange loanApplicableRange;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinColumn(name = "due_date_template_id")
	private DueDateTemplates dueDateTemplate;

	@Column(name = "due_date_template_remark", length = 1000)
	private String dueDateTemplateRemark;
	
	@Column(name = "risk_template_id",length=35, nullable=true) 
	private String riskTemplateId;

	@Column(name = "risk_template_remark", length = 1000, nullable=true)
	private String riskTemplateRemark;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "fee_charge_id", nullable=true)
	private FeeCharge feeCharge;
	
	
	
	public LoanApplicableRange getLoanApplicableRange() {
		return loanApplicableRange;
	}

	public void setLoanApplicableRange(LoanApplicableRange loanApplicableRange) {
		this.loanApplicableRange = loanApplicableRange;
	}

	public FeeCharge getFeeCharge() {
		return feeCharge;
	}

	public void setFeeCharge(FeeCharge feeCharge) {
		this.feeCharge = feeCharge;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public SubProduct getSubProduct() {
		return subProduct;
	}

	public void setSubProduct(SubProduct subProduct) {
		this.subProduct = subProduct;
	}
	
	public Long getSubProdId() {
		if(product != null) {
			return subProduct.getId();
		} else {
			return null;
		}
	}

	public LendingWorkflow getLendingWorkflow() {
		return lendingWorkflow;
	}

	public void setLendingWorkflow(LendingWorkflow lendingWorkflow) {
		this.lendingWorkflow = lendingWorkflow;
	}
	
	public Long getLendingWorkflowId() {
		if(product != null) {
			return lendingWorkflow.getId();
		} else {
			return null;
		}
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
	public Long getProductId() {
		if(product != null) {
			return product.getId();
		} else {
			return null;
		}
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPredecessorId() {
		return predecessorId;
	}

	public void setPredecessorId(String predecessorId) {
		this.predecessorId = predecessorId;
	}
	
	public String getPredecessorCode() {
		return predecessorCode;
	}

	public void setPredecessorCode(String predecessorCode) {
		this.predecessorCode = predecessorCode;
	}

	public String getPredecessorName() {
		return predecessorName;
	}

	public void setPredecessorName(String predecessorName) {
		this.predecessorName = predecessorName;
	}

	public Long getMarketingStateId() {
		return marketingStateId;
	}
	
	public void setMarketingStateId(Long marketingStateId) {
		this.marketingStateId = marketingStateId;
	}
	
	public String getMarketingStateReferenceCode() {
		return marketingStateReferenceCode;
	}

	public void setMarketingStateReferenceCode(String marketingStateReferenceCode) {
		this.marketingStateReferenceCode = marketingStateReferenceCode;
	}

	public String getMarketingStateCode() {
		return marketingStateCode;
	}

	public void setMarketingStateCode(String marketingStateCode) {
		this.marketingStateCode = marketingStateCode;
	}

	public String getMarketingStateName() {
		return marketingStateName;
	}

	public void setMarketingStateName(String marketingStateName) {
		this.marketingStateName = marketingStateName;
	}

	public Date getFirstMarketedDate() {
		return firstMarketedDate;
	}

	public void setFirstMarketedDate(Date firstMarketedDate) {
		this.firstMarketedDate = firstMarketedDate;
	}

	public Date getLastMarketedDate() {
		return lastMarketedDate;
	}

	public void setLastMarketedDate(Date lastMarketedDate) {
		this.lastMarketedDate = lastMarketedDate;
	}

	public Long getStateTenureLength() {
		return stateTenureLength;
	}

	public void setStateTenureLength(Long stateTenureLength) {
		this.stateTenureLength = stateTenureLength;
	}

	public Long getStateTenurePeriodId() {
		return stateTenurePeriodId;
	}

	public void setStateTenurePeriodId(Long stateTenurePeriodId) {
		this.stateTenurePeriodId = stateTenurePeriodId;
	}
	
	public String getStateTenurePeriodCode() {
		return stateTenurePeriodCode;
	}

	public void setStateTenurePeriodCode(String stateTenurePeriodCode) {
		this.stateTenurePeriodCode = stateTenurePeriodCode;
	}

	public String getStateTenurePeriodName() {
		return stateTenurePeriodName;
	}

	public void setStateTenurePeriodName(String stateTenurePeriodName) {
		this.stateTenurePeriodName = stateTenurePeriodName;
	}

	public String getStateTenurePeriodDescription() {
		return stateTenurePeriodDescription;
	}

	public void setStateTenurePeriodDescription(String stateTenurePeriodDescription) {
		this.stateTenurePeriodDescription = stateTenurePeriodDescription;
	}

	public FeatureBenifitTemplate getFeatureBenifitTemplate() {
		return featureBenifitTemplate;
	}

	public void setFeatureBenifitTemplate(FeatureBenifitTemplate featureBenifitTemplate) {
		this.featureBenifitTemplate = featureBenifitTemplate;
	}

	public Long getFeatureBenifitTemplateId() {
		if(featureBenifitTemplate != null) {
			return featureBenifitTemplate.getId();
		} else {
			return null;
		}
	}
	
	public String getFeatureBenifitTemplateCode() {
		return featureBenifitTemplateCode;
	}

	public void setFeatureBenifitTemplateCode(String featureBenifitTemplateCode) {
		this.featureBenifitTemplateCode = featureBenifitTemplateCode;
	}

	public String getFeatureBenifitTemplateName() {
		return featureBenifitTemplateName;
	}

	public void setFeatureBenifitTemplateName(String featureBenifitTemplateName) {
		this.featureBenifitTemplateName = featureBenifitTemplateName;
	}

	public Eligibility getEligibility() {
		return eligibility;
	}

	public void setEligibility(Eligibility eligibility) {
		this.eligibility = eligibility;
	}
	
	public Long getEligibilityId() {
		if(eligibility != null) {
			return eligibility.getId();
		} else {
			return null;
		}
	}
	
	public String getEligibilityCode() {
		return eligibilityCode;
	}

	public void setEligibilityCode(String eligibilityCode) {
		this.eligibilityCode = eligibilityCode;
	}

	public String getEligibilityName() {
		return eligibilityName;
	}

	public void setEligibilityName(String eligibilityName) {
		this.eligibilityName = eligibilityName;
	}

	public InterestTemplate getInterestTemplate() {
		return interestTemplate;
	}

	public void setInterestTemplate(InterestTemplate interestTemplate) {
		this.interestTemplate = interestTemplate;
	}
	
	public Long getInterestTemplateId() {
		if(interestTemplate != null) {
			return interestTemplate.getId();
		} else {
			return null;
		}
	}
	
	public String getInterestTemplateCode() {
		return interestTemplateCode;
	}

	public void setInterestTemplateCode(String interestTemplateCode) {
		this.interestTemplateCode = interestTemplateCode;
	}

	public String getInterestTemplateName() {
		return interestTemplateName;
	}

	public void setInterestTemplateName(String interestTemplateName) {
		this.interestTemplateName = interestTemplateName;
	}

	public Repayment getRepayment() {
		return repayment;
	}

	public void setRepayment(Repayment repayment) {
		this.repayment = repayment;
	}
	
	public Long getRepaymentId() {
		if(repayment != null) {
			return repayment.getId();
		} else {
			return null;
		}
	}
	
	public String getRepaymentCode() {
		return repaymentCode;
	}

	public void setRepaymentCode(String repaymentCode) {
		this.repaymentCode = repaymentCode;
	}

	public String getRepaymentName() {
		return repaymentName;
	}

	public void setRepaymentName(String repaymentName) {
		this.repaymentName = repaymentName;
	}

	public Long getCoreProductId() {
		return coreProductId;
	}

	public void setCoreProductId(Long coreProductId) {
		this.coreProductId = coreProductId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getApproveStatus() {
		return approveStatus;
	}

	public void setApproveStatus(String approveStatus) {
		this.approveStatus = approveStatus;
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

	public String getPendingApprovedUser() {
		return pendingApprovedUser;
	}

	public void setPendingApprovedUser(String pendingApprovedUser) {
		this.pendingApprovedUser = pendingApprovedUser;
	}

	public Timestamp getPendingApprovedDate() {
		return pendingApprovedDate;
	}

	public void setPendingApprovedDate(Timestamp pendingApprovedDate) {
		this.pendingApprovedDate = pendingApprovedDate;
	}

	public String getPendingRejectedUser() {
		return pendingRejectedUser;
	}

	public void setPendingRejectedUser(String pendingRejectedUser) {
		this.pendingRejectedUser = pendingRejectedUser;
	}

	public Timestamp getPenndingRejectedDate() {
		return penndingRejectedDate;
	}

	public void setPenndingRejectedDate(Timestamp penndingRejectedDate) {
		this.penndingRejectedDate = penndingRejectedDate;
	}
	
	public BigDecimal getMaxPenalInterestRate() {
		return maxPenalInterestRate;
	}

	public void setMaxPenalInterestRate(BigDecimal maxPenalInterestRate) {
		this.maxPenalInterestRate = maxPenalInterestRate;
	}

	public BigDecimal getMiniPenalInterestRate() {
		return miniPenalInterestRate;
	}

	public void setMiniPenalInterestRate(BigDecimal miniPenalInterestRate) {
		this.miniPenalInterestRate = miniPenalInterestRate;
	}

	public PenalInterest getPenalInterest() {
		return penalInterest;
	}

	public void setPenalInterest(PenalInterest penalInterest) {
		this.penalInterest = penalInterest;
	}

	public String getDueDateTemplateRemark() {
		return dueDateTemplateRemark;
	}

	public void setDueDateTemplateRemark(String dueDateTemplateRemark) {
		this.dueDateTemplateRemark = dueDateTemplateRemark;
	}

	public DueDateTemplates getDueDateTemplate() {
		return dueDateTemplate;
	}

	public void setDueDateTemplate(DueDateTemplates dueDateTemplate) {
		this.dueDateTemplate = dueDateTemplate;
	}

	public String getRiskTemplateId() {
		return riskTemplateId;
	}

	public void setRiskTemplateId(String riskTemplateId) {
		this.riskTemplateId = riskTemplateId;
	}

	public String getRiskTemplateRemark() {
		return riskTemplateRemark;
	}

	public void setRiskTemplateRemark(String riskTemplateRemark) {
		this.riskTemplateRemark = riskTemplateRemark;
	}
	
	
}
