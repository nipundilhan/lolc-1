package com.fusionx.lending.product.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fusionx.lending.product.domain.MasterDefinition;
import com.fusionx.lending.product.domain.MasterDefinitionPending;
import com.fusionx.lending.product.enums.WorkflowStatus;
import com.fusionx.lending.product.resources.MasterPenalInterestResource;

/**
 * API Service related to Master Interest.
 *
 * @author Dilhan Jayasinghe
 * @version 1.0.0
 * @since 1.0.0
 * <p>
 * <br/><br/>
 * <b>Change History : </b>
 * <br/><br/>
 * #        Date            Story Point     Task No     Author                  Description
 * <br/>
 * .....................................................................................................................<br/>
 * 1        26-08-2021      -               -           Dilhan Jayasinghe      Created
 * <p>
 *
 */
@Service
public interface MasterInterestService {

	/**
	 * 
	 * Find MasterDefinition by ID
	 * 
	 * @author Dilhan
	 * @return -JSON Object of MasterDefinition
	 * 
	 */
	public Optional<MasterDefinition> getById(Long id);


	/**
	 * 
	 * Update MasterDefinition
	 * 
	 * @author Dilhan
	 * @param - MasterCurrencyMainUpdateResource
	 * @return - Successfully saved
	 * 
	 */
	public Long updateMasterDefinitionWithInterestTemplate(String tenantId, Long masterDefId,
			MasterPenalInterestResource masterPenalInterestResource);
	
	/**
	 * 
	 * Approve Reject
	 * 
	 * @author Dilhan
	 * @param - tenantId
	 * @param - workflowStatus
	 * @return - Successfully saved
	 * 
	 */
	public boolean approveReject(String tenantId, Long id, WorkflowStatus workflowStatus);

	/**
	 * 
	 * Find Pending MasterDefinition by ID
	 * 
	 * @author Dilhan
	 * @return -JSON object of MasterDefinition Pending
	 * 
	 */
	public Optional<MasterDefinitionPending> getByPendingId(Long pendingId);
	
	
}
