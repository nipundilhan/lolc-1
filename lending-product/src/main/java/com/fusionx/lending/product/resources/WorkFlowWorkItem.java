package com.fusionx.lending.product.resources;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WorkFlowWorkItem {

	@JsonProperty("work-item-instance")
	private List<WorkFlowWorkItemInstance> workItemInstance;

	public List<WorkFlowWorkItemInstance> getWorkItemInstance() {
		return workItemInstance;
	}

	public void setWorkItemInstance(List<WorkFlowWorkItemInstance> workItemInstance) {
		this.workItemInstance = workItemInstance;
	}
	
}
