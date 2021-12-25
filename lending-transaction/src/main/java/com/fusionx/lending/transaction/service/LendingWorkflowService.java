package com.fusionx.lending.transaction.service;

import org.springframework.stereotype.Service;

import com.fusionx.lending.transaction.domain.LendingWorkflow;
import com.fusionx.lending.transaction.enums.WorkflowStatus;
import com.fusionx.lending.transaction.enums.WorkflowType;

/**
 * API Service related to Lending Workflow.
 *
 * @author Dilhan
 * @version 1.0.0
 * @since 1.0.0
 * <p>
 * <br/><br/>
 * <b>Change History : </b>
 * <br/><br/>
 * #        Date            Story Point     Task No             Author                      Description         Verified Date   Verified By
 * <br/>
 * .............................................................................................................................................<br/>
 * 1        21-10-2021     	-               FXL-1151            Dilhan                       Created          
 * <p>
 */
@Service
public interface LendingWorkflowService {

    /**
     * Saves the given workflow
     *
     * @param tenantId     the tenant id
     * @param processId    the process id
     * @param workflowType the workflow type
     * @param user         the user name
     * @return the saved workflow
     */
    LendingWorkflow save(String tenantId, Long processId, WorkflowType workflowType, String user);

    /**
     * Updates the given workflow
     *
     * @param lendingWF      the workflow to update
     * @param workflowStatus the status of the workflow
     * @param user           the user name
     * @return the updated workflow
     */
    LendingWorkflow update(LendingWorkflow lendingWF, WorkflowStatus workflowStatus, String user);

}
