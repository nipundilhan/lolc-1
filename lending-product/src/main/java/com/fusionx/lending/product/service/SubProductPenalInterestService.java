package com.fusionx.lending.product.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fusionx.lending.product.domain.MasterDefinitionPending;
import com.fusionx.lending.product.enums.WorkflowStatus;
import com.fusionx.lending.product.resources.SubProductPenalInterestResource;

/**
 * API Service related to Sub Product Penal Interest.
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
 * 1        27-08-2021      -               -           Dilhan Jayasinghe      Created
 * <p>
 */
@Service
public interface SubProductPenalInterestService {

	/**
	 * 
	 * Update SubProduct
	 * 
	 * @author Dilhan
	 * @param - MasterCurrencyMainUpdateResource
	 * @return - Successfully saved
	 * 
	 */
	public Long updateSubProductWithInterestTemplate(String tenantId, Long subProductId,
			SubProductPenalInterestResource subProductPenalInterestResource);
	
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

}
