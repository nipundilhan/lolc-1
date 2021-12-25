package com.fusionx.lending.origination.resource;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class DaLimitAddResource {

	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{id.pattern}")
	private String authorityGroupId;
	
	@NotBlank(message = "{common.not-null}")
	private String authorityGroupName;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{daLevel.pattern}")
	private String daLevel;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{id.pattern}")
	private String approvalCategoryId;
	
	@NotBlank(message = "{common.not-null}")
	private String approvalCategoryName;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|\\d{1,20}\\.\\d{1,2}$",message="{common-amount.pattern}")
	private String daLimitValue;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "ACTIVE|INACTIVE", message = "{common-status.pattern}")
	private String status;

	public String getAuthorityGroupId() {
		return authorityGroupId;
	}

	public void setAuthorityGroupId(String authorityGroupId) {
		this.authorityGroupId = authorityGroupId;
	}

	public String getAuthorityGroupName() {
		return authorityGroupName;
	}

	public void setAuthorityGroupName(String authorityGroupName) {
		this.authorityGroupName = authorityGroupName;
	}

	public String getDaLevel() {
		return daLevel;
	}

	public void setDaLevel(String daLevel) {
		this.daLevel = daLevel;
	}

	public String getApprovalCategoryId() {
		return approvalCategoryId;
	}

	public void setApprovalCategoryId(String approvalCategoryId) {
		this.approvalCategoryId = approvalCategoryId;
	}

	public String getApprovalCategoryName() {
		return approvalCategoryName;
	}

	public void setApprovalCategoryName(String approvalCategoryName) {
		this.approvalCategoryName = approvalCategoryName;
	}

	public String getDaLimitValue() {
		return daLimitValue;
	}

	public void setDaLimitValue(String daLimitValue) {
		this.daLimitValue = daLimitValue;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
