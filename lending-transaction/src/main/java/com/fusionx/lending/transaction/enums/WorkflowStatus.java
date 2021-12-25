package com.fusionx.lending.transaction.enums;

import java.util.ArrayList;
import java.util.List;

public enum WorkflowStatus {
	ACTIVE,
	COMPLETE,
	REJECT;
	
	
	public List<String> getAllWorkflowStatus() {
		List<String> statusList = new ArrayList<>();
		WorkflowStatus workflowStatus = null;
		Object[] obj = WorkflowStatus.class.getEnumConstants();
		
		for (int i = 0; i < obj.length; i++) {
			workflowStatus = (WorkflowStatus) obj[i];
			statusList.add(workflowStatus.name());
		}
		
		return statusList;
	}
}
