package com.fusionx.lending.origination.service;
/**
 * 	Analyst Exception Details Service
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   24-08-2021   FXL-117  	 FXL-543       Piyumi     Created
 *    
 ********************************************************************************************************
*/

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fusionx.lending.origination.domain.AnalystDetails;
import com.fusionx.lending.origination.resource.AnalystAddResource;
import com.fusionx.lending.origination.resource.AnalystUpdateResource;



@Service
public interface AnalystExceptionDetailsService {
	
	/**
	 * FindAll
	 * 
	 * @param tenantId - the tenant id
	 * @return the Analyst Details List
	 */
	List<AnalystDetails> findAll(String tenantId);
	
	/**
	 * Find by id.
	 * 
	 * @param tenantId - the tenant id
	 * @param id - the id
	 * @return the Analyst Details Object
	 */
	Optional<AnalystDetails> findById(String tenantId, Long id);
	
	/**
	 * Find by status.
	 *
	 * @param tenantId - the tenant id
	 * @param status - the status
	 * @return the Analyst Details Object
	 */
	List<AnalystDetails> findByStatus(String tenantId, String status);

	/**
	 * add  Analyst Exception Details
	 *
	 * @param tenantId - the tenant id
	 * @param AnalystAddResource - the analyst add resource
	 * @return the Analyst Details Object
	 */
	AnalystDetails add(String tenantId, AnalystAddResource analystAddResource);

	/**
	 * update Analyst Exception Details
	 *
	 * @param tenantId - the tenant id
	 * @param id - the analyst details id
	 * @param AnalystUpdateResource - analyst  update resource
	 * @return the Analyst Details Object
	 */
	AnalystDetails update(String tenantId, Long id,  AnalystUpdateResource analystUpdateResource);
	
	/**
	 * send To Approval - Analyst Exception Details
	 *
	 * @param tenantId - the tenant id
	 * @param id - the id
	 * @return Long
	 */
	Long sendToApproval(String tenantId , Long id);
	
	/**
	 * Approve - Analyst Exception Details
	 *
	 * @param tenantId - the tenant id
	 * @param analystExceptionDetailId - the analyst exception details id
	 * @param workflowProcessId - the workflow process id
	 * @return Long
	 */
	Long approveAnalystExceptionWorkFlow(String tenantId, Long analystExceptionDetailId, String workflowProcessId);
	
	/**
	 * Reject - Analyst Exception Details
	 *
	 * @param tenantId - the tenant id
	 * @param analystExceptionDetailId - the analyst exception details id
	 * @param workflowProcessId - the workflow process id
	 * @return Long
	 */
	Long rejectedAnalystExceptionWorkFlow(String tenantId, Long analystExceptionDetailId, String workflowProcessId);

}
