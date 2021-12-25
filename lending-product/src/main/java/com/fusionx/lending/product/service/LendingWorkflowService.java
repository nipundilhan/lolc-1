package com.fusionx.lending.product.service;

import com.fusionx.lending.product.domain.LendingWorkflow;
import com.fusionx.lending.product.enums.WorkflowStatus;
import com.fusionx.lending.product.enums.WorkflowType;
import org.springframework.stereotype.Service;

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
