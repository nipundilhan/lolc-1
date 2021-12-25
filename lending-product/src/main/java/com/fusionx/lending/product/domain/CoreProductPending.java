package com.fusionx.lending.product.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fusionx.lending.product.core.BaseEntity;
import com.fusionx.lending.product.enums.CommonApproveStatus;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.enums.YesNo;

import lombok.Data;

/**
 * Core Product Pending
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   23-09-2020      		     FXL-795	Dilhan      Created
 *    
 ********************************************************************************************************
 */

@Entity
@Table(name = "core_product_pending")
@Data
public class CoreProductPending extends BaseEntity implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = -6196131468698647123L;

	@Column(name = "tenant_id", length=10, nullable=false)
	private String tenantId;
	
	@Column(name = "code", length=4, nullable=false)
	private String code;
	
	@Column(name = "tcs_cs_url", length=500, nullable=true)
	private String tcsCsUrl;
	
	@Column(name = "description", length=350, nullable=true)
	private String description;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "status", length=20, nullable=false)
	private CommonStatus status;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "core_product_id", nullable=true)
	private CoreProduct coreProduct;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "sales_access_channel_id", nullable=true)
	private SalesAccessChannel salesAccessChannel;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "service_access_channel_id", nullable=true)
	private ServiceAccessChannel serviceAccessChannel;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "other_fee_type_id", nullable=true)
	private OtherFeeType otherFeeType;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "early_payment_applicable", length=20, nullable=true)
	private YesNo earlyPaymentApplicable;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "over_payment_applicable", length=20, nullable=true)
	private YesNo overPaymentApplicable;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "over_payment_allow", length=20, nullable=true)
	private YesNo overPayment;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "full_partial_repayment_allow", length=20, nullable=true)
	private YesNo fullPartialRepayment;
	
	@Column(name = "currencyId", nullable=false)
	private Long currencyId;
	
	@Column(name = "currency_code", length=3, nullable=false)
	private String currencyCode;
	
	@Column(name = "currency_code_numeric", length=3, nullable=false)
	private String currencyCodeNumeric;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "lending_workflow_id", nullable=true)
	private LendingWorkflow lendingWorkflow;
	
	@Column(name = "attribute1", length=100, nullable=true)
	private String attribute1;
	
	@Column(name = "attribute2", length=100, nullable=true)
	private String attribute2;
	
	@Column(name = "attribute3", length=100, nullable=true)
	private String attribute3;
	
	@Column(name = "attribute4", nullable=true)
	private Long attribute4;
	
	@Column(name = "attribute5", nullable=true)
	private Long attribute5;
	
	@Column(name = "[comment]", length=300, nullable=true)
	private String comment;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "approve_status",  nullable=true, length=30)
	private CommonApproveStatus approveStatus;
	
	@Column(name = "approved_user", nullable=true, length=255)
	private String approvedUser;
	
	@Column(name = "approved_date", nullable=true)
	private Timestamp approvedDate;
	
	@Column(name = "created_user", length=255, nullable=false)
	private String createdUser;
	
	@Column(name = "created_date", nullable=false)
	private Timestamp createdDate;

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getAttribute1() {
		return attribute1;
	}

	public void setAttribute1(String attribute1) {
		this.attribute1 = attribute1;
	}

	public String getAttribute2() {
		return attribute2;
	}

	public void setAttribute2(String attribute2) {
		this.attribute2 = attribute2;
	}

	public String getAttribute3() {
		return attribute3;
	}

	public void setAttribute3(String attribute3) {
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

	public CoreProduct getCoreProduct() {
		return coreProduct;
	}

	public void setCoreProduct(CoreProduct coreProduct) {
		this.coreProduct = coreProduct;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getTcsCsUrl() {
		return tcsCsUrl;
	}

	public void setTcsCsUrl(String tcsCsUrl) {
		this.tcsCsUrl = tcsCsUrl;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public CommonStatus getStatus() {
		return status;
	}

	public void setStatus(CommonStatus status) {
		this.status = status;
	}

	public SalesAccessChannel getSalesAccessChannel() {
		return salesAccessChannel;
	}

	public void setSalesAccessChannel(SalesAccessChannel salesAccessChannel) {
		this.salesAccessChannel = salesAccessChannel;
	}

	public ServiceAccessChannel getServiceAccessChannel() {
		return serviceAccessChannel;
	}

	public void setServiceAccessChannel(ServiceAccessChannel serviceAccessChannel) {
		this.serviceAccessChannel = serviceAccessChannel;
	}

	public OtherFeeType getOtherFeeType() {
		return otherFeeType;
	}

	public void setOtherFeeType(OtherFeeType otherFeeType) {
		this.otherFeeType = otherFeeType;
	}

	public YesNo getEarlyPaymentApplicable() {
		return earlyPaymentApplicable;
	}

	public void setEarlyPaymentApplicable(YesNo earlyPaymentApplicable) {
		this.earlyPaymentApplicable = earlyPaymentApplicable;
	}

	public YesNo getOverPaymentApplicable() {
		return overPaymentApplicable;
	}

	public void setOverPaymentApplicable(YesNo overPaymentApplicable) {
		this.overPaymentApplicable = overPaymentApplicable;
	}

	public YesNo getOverPayment() {
		return overPayment;
	}

	public void setOverPayment(YesNo overPayment) {
		this.overPayment = overPayment;
	}

	public YesNo getFullPartialRepayment() {
		return fullPartialRepayment;
	}

	public void setFullPartialRepayment(YesNo fullPartialRepayment) {
		this.fullPartialRepayment = fullPartialRepayment;
	}

	public Long getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(Long currencyId) {
		this.currencyId = currencyId;
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

	public LendingWorkflow getLendingWorkflow() {
		return lendingWorkflow;
	}

	public void setLendingWorkflow(LendingWorkflow lendingWorkflow) {
		this.lendingWorkflow = lendingWorkflow;
	}

	public CommonApproveStatus getApproveStatus() {
		return approveStatus;
	}

	public void setApproveStatus(CommonApproveStatus approveStatus) {
		this.approveStatus = approveStatus;
	}

	public String getApprovedUser() {
		return approvedUser;
	}

	public void setApprovedUser(String approvedUser) {
		this.approvedUser = approvedUser;
	}

	public Timestamp getApprovedDate() {
		return approvedDate;
	}

	public void setApprovedDate(Timestamp approvedDate) {
		this.approvedDate = approvedDate;
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
	
	public Long getSalesAccessChannelId() {
		return salesAccessChannel!= null ? salesAccessChannel.getId():null;
	}
	
	public Long getServiceAccessChannelId() {
		return serviceAccessChannel!= null ? serviceAccessChannel.getId():null;
	}
	
	public Long getOtherFeeTypeId() {
		return otherFeeType!= null ? otherFeeType.getId():null;
	}
	
	public String getSalesAccessChannelName() {
		return salesAccessChannel!= null ? salesAccessChannel.getName():null;
	}
	
	public String getServiceAccessChannelName() {
		return serviceAccessChannel!= null ? serviceAccessChannel.getName():null;
	}
	
	public String getOtherFeeTypeName() {
		return otherFeeType!= null ? otherFeeType.getName():null;
	}
	
}
