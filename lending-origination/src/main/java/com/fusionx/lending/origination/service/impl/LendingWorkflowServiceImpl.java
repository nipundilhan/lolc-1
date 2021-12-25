package com.fusionx.lending.origination.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.origination.domain.MicroBprWorkflow;
import com.fusionx.lending.origination.enums.WorkflowStatus;
import com.fusionx.lending.origination.enums.WorkflowType;
import com.fusionx.lending.origination.repository.MicroBprWorkflowRepository;
import com.fusionx.lending.origination.service.LendingWorkflowService;
import com.fusionx.lending.origination.service.ValidateService;

@Component
@Transactional(rollbackFor = Exception.class)
public class LendingWorkflowServiceImpl implements LendingWorkflowService {

	@Autowired
	private MicroBprWorkflowRepository microBprWorkflowRepository;

	@Autowired
	private ValidateService validationService;

	@Override
	public MicroBprWorkflow save(String tenantId, Long processId, WorkflowType workflowType, String user) {

		MicroBprWorkflow microBprWorkflow = new MicroBprWorkflow();
		microBprWorkflow.setTenantId(tenantId);
		microBprWorkflow.setWorkflowProcessId(processId);
		microBprWorkflow.setWorkflowType(workflowType);
		microBprWorkflow.setWorkflowStatus(WorkflowStatus.ACTIVE);
		microBprWorkflow.setCreatedUser(user);
		microBprWorkflow.setCreatedDate(validationService.getCreateOrModifyDate());
		microBprWorkflow.setSyncTs(validationService.getCreateOrModifyDate());

		microBprWorkflowRepository.save(microBprWorkflow);

		return microBprWorkflow;
	}

	@Override
	public MicroBprWorkflow update(MicroBprWorkflow microBprWF, WorkflowStatus workflowStatus, String user) {

		MicroBprWorkflow microBprWorkflow = microBprWF;
		microBprWorkflow.setWorkflowStatus(workflowStatus);
		microBprWorkflow.setSyncTs(validationService.getCreateOrModifyDate());
		microBprWorkflow.setModifiedUser(user);
		microBprWorkflow.setModifiedDate(validationService.getCreateOrModifyDate());
		microBprWorkflow.setSyncTs(validationService.getCreateOrModifyDate());

		microBprWorkflowRepository.save(microBprWorkflow);

		return microBprWorkflow;
	}
}
