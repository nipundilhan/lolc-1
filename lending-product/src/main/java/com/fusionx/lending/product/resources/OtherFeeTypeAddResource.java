package com.fusionx.lending.product.resources;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Other Fee Type Add Resource
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   04-06-2020   FX-6604   	 FX-6509	Senitha      Created
 *    
 ********************************************************************************************************
 */

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class OtherFeeTypeAddResource {

	@NotBlank(message = "{common.not-null}")
	@Size(max = 4, min = 4, message = "{common-code.size}")
	@Pattern(regexp = "^$|^[a-zA-Z0-9]+$", message = "{common.code-pattern}")
	private String code;
	
	@NotBlank(message = "{common.not-null}")
	@Size(max = 70, message = "{common-name.size}")
	private String name;
	
	@Size(max = 350, message = "{common-description.size}")
	private String description;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "ACTIVE|INACTIVE", message = "{common-status.pattern}")
	private String status;
	
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	@NotBlank(message = "{common.not-null}")
	private String feeCategoryId;
	private String feeCategoryName;
	
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	@NotBlank(message = "{common.not-null}")
	private String transactionCodeId;
	private String transactionCode;
	
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	@NotBlank(message = "{common.not-null}")
	private String transactionSubCodeId;
	private String transactionSubCode;
	
	@Pattern(regexp = "YES|NO", message = "{common-enable.pattern}")
	private String refundable;
	
	@Pattern(regexp = "^$|^-?[0-9]{0,2}(\\.[0-9]{1,2})?$|^-?(100)(\\.[0]{1,2})?$",message="{common.rate-pattern.one}")
	private String refundablePercentage;
	
	@Pattern(regexp = "INCOME|THIRD_PARTY", message = " value should be INCOME or THIRD_PARTY")
	private String collectionType;
	

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
	public String getTransactionCodeId() {
		return transactionCodeId;
	}
	public void setTransactionCodeId(String transactionCodeId) {
		this.transactionCodeId = transactionCodeId;
	}
	public String getTransactionCode() {
		return transactionCode;
	}
	public void setTransactionCode(String transactionCode) {
		this.transactionCode = transactionCode;
	}
	public String getTransactionSubCodeId() {
		return transactionSubCodeId;
	}
	public void setTransactionSubCodeId(String transactionSubCodeId) {
		this.transactionSubCodeId = transactionSubCodeId;
	}
	public String getTransactionSubCode() {
		return transactionSubCode;
	}
	public void setTransactionSubCode(String transactionSubCode) {
		this.transactionSubCode = transactionSubCode;
	}
	
	public String getRefundable() {
		return refundable;
	}
	public void setRefundable(String refundable) {
		this.refundable = refundable;
	}
	public String getRefundablePercentage() {
		return refundablePercentage;
	}
	public void setRefundablePercentage(String refundablePercentage) {
		this.refundablePercentage = refundablePercentage;
	}
	

	public String getCollectionType() {
		return collectionType;
	}
	public void setCollectionType(String collectionType) {
		this.collectionType = collectionType;
	}
}
