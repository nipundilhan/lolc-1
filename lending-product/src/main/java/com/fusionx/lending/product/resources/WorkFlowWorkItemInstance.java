package com.fusionx.lending.product.resources;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WorkFlowWorkItemInstance {

	@JsonProperty("work-item-id")
	private Long workItemId;
	
	@JsonProperty("work-item-name")
	private String workItemName;
	
	@JsonProperty("work-item-state")
	private Long workItemState;

	public Long getWorkItemId() {
		return workItemId;
	}

	public void setWorkItemId(Long workItemId) {
		this.workItemId = workItemId;
	}

	public String getWorkItemName() {
		return workItemName;
	}

	public void setWorkItemName(String workItemName) {
		this.workItemName = workItemName;
	}

	public Long getWorkItemState() {
		return workItemState;
	}

	public void setWorkItemState(Long workItemState) {
		this.workItemState = workItemState;
	}
}
