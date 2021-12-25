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
 *    1   17-07-2021      		     		    Dilhan     Created
 *    
 ********************************************************************************************************
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class FeeChargeCapAddResource {

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
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|\\d{1,20}\\.\\d{1,2}$",message="{common-amount.pattern}")
	private String minimumAmount;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "ACTIVE|INACTIVE", message = "{common-status.pattern}")
	private String status;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String feeCapPeriodId;
	
	@NotBlank(message = "{common.not-null}")
	@Size(max = 70, message = "{common-name.size}")
	private String feeCapPeriodName;
	
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String feeOccurence;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "MIN|MAX", message = "{common-min-max.pattern}")
	private String minMaxType;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String otherFeeTypeId;
	
	@NotBlank(message = "{common.not-null}")
	@Size(max = 70, message = "{common-name.size}")
	private String otherFeeTypeName;
	
	public String getOtherFeeTypeId() {
		return otherFeeTypeId;
	}

	public void setOtherFeeTypeId(String otherFeeTypeId) {
		this.otherFeeTypeId = otherFeeTypeId;
	}

	public String getOtherFeeTypeName() {
		return otherFeeTypeName;
	}

	public void setOtherFeeTypeName(String otherFeeTypeName) {
		this.otherFeeTypeName = otherFeeTypeName;
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
	public String getFeeCapPeriodName() {
		return feeCapPeriodName;
	}
	
	public void setFeeCapPeriodName(String feeCapPeriodName) {
		this.feeCapPeriodName = feeCapPeriodName;
	}

	public String getMinimumAmount() {
		return minimumAmount;
	}

	public void setMinimumAmount(String minimumAmount) {
		this.minimumAmount = minimumAmount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFeeCapPeriodId() {
		return feeCapPeriodId;
	}

	public void setFeeCapPeriodId(String feeCapPeriodId) {
		this.feeCapPeriodId = feeCapPeriodId;
	}

	public String getFeeOccurence() {
		return feeOccurence;
	}

	public void setFeeOccurence(String feeOccurence) {
		this.feeOccurence = feeOccurence;
	}

	public String getMinMaxType() {
		return minMaxType;
	}

	public void setMinMaxType(String minMaxType) {
		this.minMaxType = minMaxType;
	}

}
