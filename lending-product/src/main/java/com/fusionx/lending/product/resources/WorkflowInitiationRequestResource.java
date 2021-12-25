package com.fusionx.lending.product.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fusionx.lending.product.enums.WorkflowType;

public class WorkflowInitiationRequestResource {

	@JsonProperty(value = "approval_user")
	private String approvalUser;
	
	private WorkflowType approvWorkflowType;

	public String getApprovalUser() {
		return approvalUser;
	}

	public void setApprovalUser(String approvalUser) {
		this.approvalUser = approvalUser;
	}

	public WorkflowType getApprovWorkflowType() {
		return approvWorkflowType;
	}

	public void setApprovWorkflowType(WorkflowType approvWorkflowType) {
		this.approvWorkflowType = approvWorkflowType;
	}
}
