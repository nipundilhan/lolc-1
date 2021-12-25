package com.fusionx.lending.origination.resource;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
/**
 * Approval Group
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   27-09-2021   FXL-78 		 FXL-977 	Dilki        Created
 *    
 ********************************************************************************************************
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ApprovalGroupAddResource {

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
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String minimumApprovalNo;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String approvalCategoryId;

	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "ACTIVE|INACTIVE", message = "{common-status.pattern}")
	private String status;
	
	private List<ApprovalGroupDALimitsAddResource> daLimits;
	
	private List<ApprovalGroupPreConditionsAddResource> preConditions;

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

	public String getMinimumApprovalNo() {
		return minimumApprovalNo;
	}

	public void setMinimumApprovalNo(String minimumApprovalNo) {
		this.minimumApprovalNo = minimumApprovalNo;
	}

	public String getApprovalCategoryId() {
		return approvalCategoryId;
	}

	public void setApprovalCategoryId(String approvalCategoryId) {
		this.approvalCategoryId = approvalCategoryId;
	}

	public List<ApprovalGroupDALimitsAddResource> getDaLimits() {
		return daLimits;
	}

	public void setDaLimits(List<ApprovalGroupDALimitsAddResource> daLimits) {
		this.daLimits = daLimits;
	}

	public List<ApprovalGroupPreConditionsAddResource> getPreConditions() {
		return preConditions;
	}

	public void setPreConditions(List<ApprovalGroupPreConditionsAddResource> preConditions) {
		this.preConditions = preConditions;
	}

}
