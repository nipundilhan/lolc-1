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
@Table(name = "sub_product_history")
public class SubProductHistory extends BaseEntity implements Serializable {
	
private static final long serialVersionUID = 1988L;
	
	@Column(name = "tenant_id", length=10, nullable=false)
	private String tenantId;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "sub_product_id", nullable=false)
	private SubProduct subProduct;
	
	@Column(name = "product_id", nullable=true)
    private Long productId;
	
	
	
	@Column(name = "code", length=4, nullable=false)
	private String code;
	
	@Column(name = "name", length=350,  nullable=true)
	private String name;
	
	@Column(name = "predecessor_id",length=35, nullable=true)
	private String predecessorId;
	
	
	
	@Column(name = "marketing_state_id",  nullable=false)
	private Long marketingStateId;
	
	
	
	@Column(name = "first_marketed_date",  nullable=true)
	private Date firstMarketedDate;
	
	@Column(name = "last_marketed_date",  nullable=true)
	private Date lastMarketedDate;
	
	@Column(name = "state_tenure_length", nullable=true)
	private Long stateTenureLength;
	
	@Column(name = "state_tenure_period_id", nullable=true)
	private Long stateTenurePeriodId;
	
	
	
	@Column(name = "feature_benefit_template_id", nullable=true)
    private Long featureBenifitTemplateId;
	
	
	
	@Column(name = "eligibility_template_id", nullable=true)
    private Long eligibilityId;
	
	
	
	@Column(name = "interest_template_id", nullable=true)
    private Long interestTemplateId;
	
	
	
	@Column(name = "repayment_template_id", nullable=true)
    private Long repaymentId;
	
	
	
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
	private Timestamp pendingRejectedDate;
	
	@Column(name="history_created_user", length=255, nullable=false)
	private String historyCreatedUser;
	
	@Column(name="history_created_date", nullable=false)
	private Timestamp historyCreatedDate;
	
	@Column(name="max_penal_interest_rate", columnDefinition="Decimal(25,5)", nullable=true)
	private BigDecimal maxPenalInterestRate;
	
	@Column(name="mini_penal_interest_rate", columnDefinition="Decimal(25,5)", nullable=true)
	private BigDecimal miniPenalInterestRate;
	
	
	@Column(name = "penal_interest_id", nullable = true)
	private Long penalInterest;
	
	@Column(name = "sub_product_version", nullable = true)
	private Long subProductVersion;

	@Column(name = "due_date_template_id")
	private Long dueDateTemplateId;

	@Column(name = "due_date_template_remark", length = 1000)
	private String dueDateTemplateRemark;

	@Column(name = "risk_template_id",length=35, nullable=true) 
	private String riskTemplateId;

	@Column(name = "risk_template_remark", length = 1000, nullable=true)
	private String riskTemplateRemark;
	
	@Column(name = "fee_charge_id", nullable=true)
    private Long feeChargeId;
	
	
	
	public Long getFeeChargeId() {
		return feeChargeId;
	}


	public void setFeeChargeId(Long feeChargeId) {
		this.feeChargeId = feeChargeId;
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


	public Long getProductId() {
		return productId;
	}


	public void setProductId(Long productId) {
		this.productId = productId;
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


	public Long getMarketingStateId() {
		return marketingStateId;
	}


	public void setMarketingStateId(Long marketingStateId) {
		this.marketingStateId = marketingStateId;
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


	public Long getFeatureBenifitTemplateId() {
		return featureBenifitTemplateId;
	}


	public void setFeatureBenifitTemplateId(Long featureBenifitTemplateId) {
		this.featureBenifitTemplateId = featureBenifitTemplateId;
	}


	public Long getEligibilityId() {
		return eligibilityId;
	}


	public void setEligibilityId(Long eligibilityId) {
		this.eligibilityId = eligibilityId;
	}


	public Long getInterestTemplateId() {
		return interestTemplateId;
	}


	public void setInterestTemplateId(Long interestTemplateId) {
		this.interestTemplateId = interestTemplateId;
	}


	public Long getRepaymentId() {
		return repaymentId;
	}


	public void setRepaymentId(Long repaymentId) {
		this.repaymentId = repaymentId;
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


	public Timestamp getPendingRejectedDate() {
		return pendingRejectedDate;
	}


	public void setPendingRejectedDate(Timestamp pendingRejectedDate) {
		this.pendingRejectedDate = pendingRejectedDate;
	}


	public String getHistoryCreatedUser() {
		return historyCreatedUser;
	}


	public void setHistoryCreatedUser(String historyCreatedUser) {
		this.historyCreatedUser = historyCreatedUser;
	}


	public Timestamp getHistoryCreatedDate() {
		return historyCreatedDate;
	}


	public void setHistoryCreatedDate(Timestamp historyCreatedDate) {
		this.historyCreatedDate = historyCreatedDate;
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


	public Long getPenalInterest() {
		return penalInterest;
	}


	public void setPenalInterest(Long penalInterest) {
		this.penalInterest = penalInterest;
	}


	public Long getSubProductVersion() {
		return subProductVersion;
	}


	public void setSubProductVersion(Long subProductVersion) {
		this.subProductVersion = subProductVersion;
	}


	public Long getDueDateTemplateId() {
		return dueDateTemplateId;
	}


	public void setDueDateTemplateId(Long dueDateTemplateId) {
		this.dueDateTemplateId = dueDateTemplateId;
	}


	public String getDueDateTemplateRemark() {
		return dueDateTemplateRemark;
	}


	public void setDueDateTemplateRemark(String dueDateTemplateRemark) {
		this.dueDateTemplateRemark = dueDateTemplateRemark;
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
