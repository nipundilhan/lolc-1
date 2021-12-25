package com.fusionx.lending.product.exception;

import com.fusionx.lending.product.enums.WorkflowType;

public class WorkFlowException extends RuntimeException {

	private final WorkflowType workflowType;
	public WorkFlowException(String exception, WorkflowType workflowType) {
		super(exception);
		this.workflowType=workflowType;
	}
	public WorkflowType getWorkflowType() {
		return workflowType;
	}
	
}
