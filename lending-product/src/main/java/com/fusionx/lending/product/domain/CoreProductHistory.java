package com.fusionx.lending.product.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.fusionx.lending.product.core.BaseEntity;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.enums.YesNo;

import lombok.Data;

/**
 * Core Product History
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   23-09-2020      		     FXL-795	Dilhan      Created
 *    
 ********************************************************************************************************
 */

@Entity
@Table(name = "core_product_history")
@Data
public class CoreProductHistory extends BaseEntity implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = -3678563395537366754L;

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
	
	@Column(name = "core_product_id", nullable=true)
	private Long coreProduct;
	
	@Column(name = "sales_access_channel_id", nullable=true)
	private Long salesAccessChannel;

	@Column(name = "service_access_channel_id", nullable=true)
	private Long serviceAccessChannel;
	
	@Column(name = "other_fee_type_id", nullable=true)
	private Long otherFeeType;
	
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
	
	@Column(name = "[comment]", length=300, nullable=true)
	private String comment;
	
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
	
	@Column(name = "created_user", length=255, nullable=false)
	private String createdUser;
	
	@Column(name = "created_date", nullable=false)
	private Timestamp createdDate;
	
	@Column(name = "modified_user", nullable=true, length=255)
	private String modifiedUser;
	
	@Column(name = "modified_date", nullable=true)
	private Timestamp modifiedDate;
	
	@Column(name = "approved_user", nullable=true, length=255)
	private String approvedUser;
	
	@Column(name = "approved_date", nullable=true)
	private Timestamp approvedDate;
	
	@Column(name="history_created_user")
	private String historyCreatedUser;
	
	@Column(name="history_created_date")
	private Timestamp historyCreatedDate;

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

	public Long getCoreProduct() {
		return coreProduct;
	}

	public void setCoreProduct(Long coreProduct) {
		this.coreProduct = coreProduct;
	}

	public Long getSalesAccessChannel() {
		return salesAccessChannel;
	}

	public void setSalesAccessChannel(Long salesAccessChannel) {
		this.salesAccessChannel = salesAccessChannel;
	}

	public Long getServiceAccessChannel() {
		return serviceAccessChannel;
	}

	public void setServiceAccessChannel(Long serviceAccessChannel) {
		this.serviceAccessChannel = serviceAccessChannel;
	}

	public Long getOtherFeeType() {
		return otherFeeType;
	}

	public void setOtherFeeType(Long otherFeeType) {
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
}
