package com.fusionx.lending.transaction.resource;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class WaiveOffApprovalTypesAddResource {

	@NotBlank(message = "{common.not-null}")
    @Pattern(regexp = "^$|ACTIVE|INACTIVE", message = "{status.pattern}")
    private String status;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String waiveOffTypeId;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String waiveOffApprovalGroupId;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getWaiveOffTypeId() {
		return waiveOffTypeId;
	}

	public void setWaiveOffTypeId(String waiveOffTypeId) {
		this.waiveOffTypeId = waiveOffTypeId;
	}

	public String getWaiveOffApprovalGroupId() {
		return waiveOffApprovalGroupId;
	}

	public void setWaiveOffApprovalGroupId(String waiveOffApprovalGroupId) {
		this.waiveOffApprovalGroupId = waiveOffApprovalGroupId;
	}
	
	
	
}
