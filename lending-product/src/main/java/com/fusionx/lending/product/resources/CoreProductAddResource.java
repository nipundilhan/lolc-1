package com.fusionx.lending.product.resources;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Core Product Add Resource
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   23-09-2021      		     FXL-795	Dilhan      Created
 *    
 ********************************************************************************************************
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class CoreProductAddResource {

	@NotBlank(message = "{common.not-null}")
	@Size(max = 4, min = 4, message = "{common-code.size}")
	@Pattern(regexp = "^$|^[a-zA-Z0-9]+$", message = "{common.code-pattern}")
	private String code;
	
	@Pattern(regexp = "YES|NO", message = "{common-enable.pattern}")
	private String earlyPaymentApplicable;
	
	@Pattern(regexp = "YES|NO", message = "{common-enable.pattern}")
	private String overPaymentApplicable;
	
	@Pattern(regexp = "YES|NO", message = "{common-enable.pattern}")
	private String overPaymentAllow;
	
	@Pattern(regexp = "YES|NO", message = "{common-enable.pattern}")
	private String fullPartialRepaymentAllow;
	
	@Size(max = 500, message = "{common-length01.size}")
	private String tcsCsUrl;
	
	@Size(max = 300, message = "{common-length01.size}")
	private String comment;
	
	@Size(max = 350, message = "{common-length01.size}")
	private String description;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "ACTIVE|INACTIVE", message = "{common-status.pattern}")
	private String status;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String currencyId;
	
	@NotBlank(message = "{common.not-null}")
	@Size(max = 70, message = "{common-name.size}")
	private String currencyName;
	
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String salesAccessChannelId;
	
	@Size(max = 70, message = "{common-name.size}")
	private String salesAccessChannelName;
	
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String serviceAccessChannelId;
	
	@Size(max = 70, message = "{common-name.size}")
	private String serviceAccessChannelName;
	
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String feeTypeId;
	
	@Size(max = 70, message = "{common-name.size}")
	private String feeTypeName;

	public String getTcsCsUrl() {
		return tcsCsUrl;
	}

	public void setTcsCsUrl(String tcsCsUrl) {
		this.tcsCsUrl = tcsCsUrl;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getEarlyPaymentApplicable() {
		return earlyPaymentApplicable;
	}

	public void setEarlyPaymentApplicable(String earlyPaymentApplicable) {
		this.earlyPaymentApplicable = earlyPaymentApplicable;
	}

	public String getOverPaymentApplicable() {
		return overPaymentApplicable;
	}

	public void setOverPaymentApplicable(String overPaymentApplicable) {
		this.overPaymentApplicable = overPaymentApplicable;
	}

	public String getOverPaymentAllow() {
		return overPaymentAllow;
	}

	public void setOverPaymentAllow(String overPaymentAllow) {
		this.overPaymentAllow = overPaymentAllow;
	}

	public String getFullPartialRepaymentAllow() {
		return fullPartialRepaymentAllow;
	}

	public void setFullPartialRepaymentAllow(String fullPartialRepaymentAllow) {
		this.fullPartialRepaymentAllow = fullPartialRepaymentAllow;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}

	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	public String getSalesAccessChannelId() {
		return salesAccessChannelId;
	}

	public void setSalesAccessChannelId(String salesAccessChannelId) {
		this.salesAccessChannelId = salesAccessChannelId;
	}

	public String getSalesAccessChannelName() {
		return salesAccessChannelName;
	}

	public void setSalesAccessChannelName(String salesAccessChannelName) {
		this.salesAccessChannelName = salesAccessChannelName;
	}

	public String getServiceAccessChannelId() {
		return serviceAccessChannelId;
	}

	public void setServiceAccessChannelId(String serviceAccessChannelId) {
		this.serviceAccessChannelId = serviceAccessChannelId;
	}

	public String getServiceAccessChannelName() {
		return serviceAccessChannelName;
	}

	public void setServiceAccessChannelName(String serviceAccessChannelName) {
		this.serviceAccessChannelName = serviceAccessChannelName;
	}

	public String getFeeTypeId() {
		return feeTypeId;
	}

	public void setFeeTypeId(String feeTypeId) {
		this.feeTypeId = feeTypeId;
	}

	public String getFeeTypeName() {
		return feeTypeName;
	}

	public void setFeeTypeName(String feeTypeName) {
		this.feeTypeName = feeTypeName;
	}
	
}
