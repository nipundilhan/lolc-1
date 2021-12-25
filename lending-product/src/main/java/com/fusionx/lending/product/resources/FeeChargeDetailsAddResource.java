package com.fusionx.lending.product.resources;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Fee Charge Cap Add Resource
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   28-07-2021      		     		    Dilhan     Created
 *    
 ********************************************************************************************************
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class FeeChargeDetailsAddResource {

	@NotBlank(message = "{common.not-null}")
	@Size(max = 4, min = 4, message = "{common-code.size}")
	@Pattern(regexp = "^$|^[a-zA-Z0-9]+$", message = "{common.code-pattern}")
	private String code;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String feeChargeId;
	
	@NotBlank(message = "{common.not-null}")
	@Size(max = 70, message = "{common-name.size}")
	private String feeChargeName;
	
	@Pattern(regexp = "^$|\\d{1,20}\\.\\d{1,2}$",message="{common-amount.pattern}")
	private String amount;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|\\d{1,20}\\.\\d{1,2}$",message="{common-amount.pattern}")
	private String minAmount;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|\\d{1,20}\\.\\d{1,2}$",message="{common-amount.pattern}")
	private String maxAmount;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "ACTIVE|INACTIVE", message = "{common-status.pattern}")
	private String status;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String applicationFrequencyId;
	
	@NotBlank(message = "{common.not-null}")
	@Size(max = 70, message = "{common-name.size}")
	private String applicationFrequencyName;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String calculationFrequencyId;
	
	@NotBlank(message = "{common.not-null}")
	@Size(max = 70, message = "{common-name.size}")
	private String calculationFrequencyName;
	
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String rateTypeId;
	
	@Size(max = 70, message = "{common-name.size}")
	private String rateTypeName;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String feeTypeId;
	
	@NotBlank(message = "{common.not-null}")
	@Size(max = 70, message = "{common-name.size}")
	private String feeTypeName;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String feeCategoryId;
	
	@NotBlank(message = "{common.not-null}")
	@Size(max = 70, message = "{common-name.size}")
	private String feeCategoryName;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "Amount|Rate", message = "{common-status.pattern}")
	private String type;
	
	//@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|^-?[0-9]{0,2}(\\.[0-9]{1,2})?$|^-?(100)(\\.[0]{1,2})?$",message="{rate.pattern}")
	private String rate;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "YES|NO", message = "{common-status.pattern}")
	private String isNegotiable;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|\\d{4}(\\-)(((0)[0-9])|((1)[0-2]))(\\-)([0-2][0-9]|(3)[0-1])$", message = "{common-date.pattern}")
	private String effectiveDate;

	public String getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getFeeChargeId() {
		return feeChargeId;
	}

	public void setFeeChargeId(String feeChargeId) {
		this.feeChargeId = feeChargeId;
	}

	public String getFeeChargeName() {
		return feeChargeName;
	}

	public void setFeeChargeName(String feeChargeName) {
		this.feeChargeName = feeChargeName;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getMinAmount() {
		return minAmount;
	}

	public void setMinAmount(String minAmount) {
		this.minAmount = minAmount;
	}

	public String getMaxAmount() {
		return maxAmount;
	}

	public void setMaxAmount(String maxAmount) {
		this.maxAmount = maxAmount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getApplicationFrequencyId() {
		return applicationFrequencyId;
	}

	public void setApplicationFrequencyId(String applicationFrequencyId) {
		this.applicationFrequencyId = applicationFrequencyId;
	}

	public String getApplicationFrequencyName() {
		return applicationFrequencyName;
	}

	public void setApplicationFrequencyName(String applicationFrequencyName) {
		this.applicationFrequencyName = applicationFrequencyName;
	}

	public String getCalculationFrequencyId() {
		return calculationFrequencyId;
	}

	public void setCalculationFrequencyId(String calculationFrequencyId) {
		this.calculationFrequencyId = calculationFrequencyId;
	}

	public String getCalculationFrequencyName() {
		return calculationFrequencyName;
	}

	public void setCalculationFrequencyName(String calculationFrequencyName) {
		this.calculationFrequencyName = calculationFrequencyName;
	}

	public String getRateTypeId() {
		return rateTypeId;
	}

	public void setRateTypeId(String rateTypeId) {
		this.rateTypeId = rateTypeId;
	}

	public String getRateTypeName() {
		return rateTypeName;
	}

	public void setRateTypeName(String rateTypeName) {
		this.rateTypeName = rateTypeName;
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

	public String getFeeCategoryId() {
		return feeCategoryId;
	}

	public void setFeeCategoryId(String feeCategoryId) {
		this.feeCategoryId = feeCategoryId;
	}

	public String getFeeCategoryName() {
		return feeCategoryName;
	}

	public void setFeeCategoryName(String feeCategoryName) {
		this.feeCategoryName = feeCategoryName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getIsNegotiable() {
		return isNegotiable;
	}

	public void setIsNegotiable(String isNegotiable) {
		this.isNegotiable = isNegotiable;
	}
}
