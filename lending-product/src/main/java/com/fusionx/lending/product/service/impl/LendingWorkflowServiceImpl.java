package com.fusionx.lending.product.service.impl;

import com.fusionx.lending.product.domain.LendingWorkflow;
import com.fusionx.lending.product.enums.WorkflowStatus;
import com.fusionx.lending.product.enums.WorkflowType;
import com.fusionx.lending.product.repository.LendingWorkflowRepository;
import com.fusionx.lending.product.service.LendingWorkflowService;
import com.fusionx.lending.product.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * API Service related to Lending Workflow.
 *
 * @author Miyuru Wijesinghe
 * @version 1.0.0
 * @since 1.0.0
 * <p>
 * <br/><br/>
 * <b>Change History : </b>
 * <br/><br/>
 * #        Date            Story Point     Task No             Author                      Description         Verified Date   Verified By
 * <br/>
 * .............................................................................................................................................<br/>
 * 1        01-07-2021    	-               FX-6889             Miyuru Wijesinghe           Created             16-Sep-2021     ChinthakaMa
 * <p>
 */
@Component
@Transactional(rollbackFor = Exception.class)
public class LendingWorkflowServiceImpl implements LendingWorkflowService {

    private LendingWorkflowRepository lendingWorkflowRepository;
    private ValidationService validationService;

    @Autowired
    public void setLendingWorkflowRepository(LendingWorkflowRepository lendingWorkflowRepository) {
        this.lendingWorkflowRepository = lendingWorkflowRepository;
    }

    @Autowired
    public void setValidationService(ValidationService validationService) {
        this.validationService = validationService;
    }

    @Override
    public LendingWorkflow save(String tenantId, Long processId, WorkflowType workflowType, String user) {

        LendingWorkflow lendingWorkflow = new LendingWorkflow();
        lendingWorkflow.setTenantId(tenantId);
        lendingWorkflow.setWorkflowProcessId(processId);
        lendingWorkflow.setWorkflowType(workflowType);
        lendingWorkflow.setWorkflowStatus(WorkflowStatus.ACTIVE);
        lendingWorkflow.setCreatedUser(user);
        lendingWorkflow.setCreatedDate(validationService.getCreateOrModifyDate());
        lendingWorkflow.setSyncTs(validationService.getCreateOrModifyDate());

        lendingWorkflowRepository.save(lendingWorkflow);

        return lendingWorkflow;
    }

    @Override
    public LendingWorkflow update(LendingWorkflow lendingWF, WorkflowStatus workflowStatus, String user) {

        LendingWorkflow lendingWorkflow = lendingWF;
        lendingWorkflow.setWorkflowStatus(workflowStatus);
        lendingWorkflow.setSyncTs(validationService.getCreateOrModifyDate());
        lendingWorkflow.setModifiedUser(user);
        lendingWorkflow.setModifiedDate(validationService.getCreateOrModifyDate());
        lendingWorkflow.setSyncTs(validationService.getCreateOrModifyDate());

        lendingWorkflowRepository.save(lendingWorkflow);

        return lendingWorkflow;
    }
}
