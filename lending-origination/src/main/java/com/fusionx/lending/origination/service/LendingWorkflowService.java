package com.fusionx.lending.origination.service;

import org.springframework.stereotype.Service;

import com.fusionx.lending.origination.domain.MicroBprWorkflow;
import com.fusionx.lending.origination.enums.WorkflowStatus;
import com.fusionx.lending.origination.enums.WorkflowType;

@Service
public interface LendingWorkflowService {

	/**
	 * 
	 * Insert workflow table
	 * 
	 * @param - tenantId
	 * @param - processId
	 * @param - workflowType
	 * @param - user
	 * @return - Successfully saved
	 * 
	 */
	public MicroBprWorkflow save(String tenantId, Long processId, WorkflowType workflowType, String user);

	/**
	 * 
	 * Insert workflow table
	 * 
	 * @param - LendingWorkflow
	 * @param - workflowStatus
	 * @param - user
	 * @return - Successfully saved
	 * 
	 */
	public MicroBprWorkflow update(MicroBprWorkflow lendingWF, WorkflowStatus workflowStatus, String user);

}
