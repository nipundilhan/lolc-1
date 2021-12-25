package com.fusionx.lending.transaction.resource;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class WaiveOffApprovalUsersAddResource {

	@NotBlank(message = "{common.not-null}")
    @Pattern(regexp = "^$|ACTIVE|INACTIVE", message = "{status.pattern}")
    private String status;
	
	@NotBlank(message = "{common.not-null}")
	private String userName;
	
	@NotBlank(message = "{common.not-null}")
	private String userId;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String waiveOffApprovalGroupId;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getWaiveOffApprovalGroupId() {
		return waiveOffApprovalGroupId;
	}

	public void setWaiveOffApprovalGroupId(String waiveOffApprovalGroupId) {
		this.waiveOffApprovalGroupId = waiveOffApprovalGroupId;
	}
	
}
