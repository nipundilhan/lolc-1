package com.fusionx.lending.origination.resource;

import java.util.List;
/**
 * Approval Category Details
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   13-09-2021   FXL-338 		 FXL-803 	Dilki        Created
 *    
 ********************************************************************************************************
 */
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;

@Data
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ApprovalCategoryDetailsUpdateResource {

	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "MANUAL|AUTO", message = "{approvalType.pattern}")
	private String approvalType;

	@NotBlank(message = "{common.not-null}")
	@Size(max = 4, min = 4, message = "{common-code.size}")
	@Pattern(regexp = "^$|^[a-zA-Z0-9]+$", message = "{common.code-pattern}")
	private String code;

	@NotBlank(message = "{common.not-null}")
	@Size(max = 150, message = "{common-name.size}")
	private String name;

	@Size(max = 150, message = "{description.size}")
	private String description;

	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "ACTIVE|INACTIVE", message = "{common-status.pattern}")
	private String status;

	@Pattern(regexp = "YES|NO", message = "{common-yesno.pattern}")
	private String modifyWorkFlow;

	private List<ApprovalCategoryApplicableVariablesUpdateResource> applicableVariables;

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

	public String getApprovalType() {
		return approvalType;
	}

	public void setApprovalType(String approvalType) {
		this.approvalType = approvalType;
	}

	public String getModifyWorkFlow() {
		return modifyWorkFlow;
	}

	public void setModifyWorkFlow(String modifyWorkFlow) {
		this.modifyWorkFlow = modifyWorkFlow;
	}

	public List<ApprovalCategoryApplicableVariablesUpdateResource> getApplicableVariables() {
		return applicableVariables;
	}

	public void setApplicableVariables(List<ApprovalCategoryApplicableVariablesUpdateResource> applicableVariables) {
		this.applicableVariables = applicableVariables;
	}

	@NotBlank(message = "{version.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{version.pattern}")
	private String version;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

}
