package com.fusionx.lending.product.resources;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class OfficerEligibilityResource {

	@NotBlank(message = "{common.not-null}")
	@Size(max = 4, min = 4, message = "{code.size}")
	@Pattern(regexp = "^$|^[a-zA-Z0-9]+$", message = "{common.code-pattern}") 
	private String code;
	
	@NotBlank(message="{common.not-null}")
	@Pattern(regexp = "|[0-9]+",message="{common.invalid-value}")
	private String officerTypeId;
	
	@NotBlank(message="{common.not-null}")
	private String officerTypeName;

	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|\\d{1,20}\\.\\d{1,5}$", message = "{common.invalid-amount-pattern}")
	private String minAmount;

	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|\\d{1,20}\\.\\d{1,5}$", message = "{common.invalid-amount-pattern}")
	private String maxAmount;
	
	@NotBlank(message="{status.not-null}")
	@Pattern(regexp = "ACTIVE|INACTIVE", message = "{status.pattern}")
	private String status;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getOfficerTypeId() {
		return officerTypeId;
	}

	public void setOfficerTypeId(String officerTypeId) {
		this.officerTypeId = officerTypeId;
	}

	public String getOfficerTypeName() {
		return officerTypeName;
	}

	public void setOfficerTypeName(String officerTypeName) {
		this.officerTypeName = officerTypeName;
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
}
