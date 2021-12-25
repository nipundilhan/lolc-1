package com.fusionx.lending.product.resources;

import com.fusionx.lending.product.enums.WorkflowType;

public class WorkflowStatusResource {

	private Boolean status;
	
	private WorkflowType approvWorkflowType;

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public WorkflowType getApprovWorkflowType() {
		return approvWorkflowType;
	}

	public void setApprovWorkflowType(WorkflowType approvWorkflowType) {
		this.approvWorkflowType = approvWorkflowType;
	}
}
