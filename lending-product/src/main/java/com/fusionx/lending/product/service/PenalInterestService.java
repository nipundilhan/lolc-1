package com.fusionx.lending.product.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fusionx.lending.product.domain.PenalInterest;
import com.fusionx.lending.product.domain.PenalInterestPending;
import com.fusionx.lending.product.enums.WorkflowStatus;
import com.fusionx.lending.product.resources.PenalInterestAddResource;
import com.fusionx.lending.product.resources.PenalInterestUpdateResource;

/**
 * Penal Interest Service - Service
 * @author 	VenukiR
 * @Date    02-06-2020
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   02-06-2020   FX-1517       FX-3902    VenukiR       Created
 *    2	  05-08-2021	  						 IsuruH	      Updated JavaDocs
 ********************************************************************************************************
 */

@Service
public interface PenalInterestService {

	/**
     * Find all Interest Template
	 * @author Dilhan
	 * 
	 * @return   		- JSON Array of all Interest Template objects
	 * 
	 */
	List<PenalInterest> findAll();
	
	/**
     * Find Interest Template by Id
	 * @author Dilhan
	 * 
	 * @return   		- JSON Array of Interest Template objects
	 * 
	 */
	Optional<PenalInterest> findById(Long interestTemplateId);
	
	/**
     * Find Interest Template by Status
	 * @author Dilhan
	 * 
	 * @return   		- JSON Array of Interest Template objects
	 * 
	 */
	List<PenalInterest> findByStatus(String status);
	
	/**
     * Gets the penal interest by name
     *
     * @param name the name of the record
     * @return the penal interest
     */    
	List<PenalInterest> findByName(String name);
	
	/**
     * Insert the penal interest 
	 * @author Dilhan
	 * 
	 * @param	- PenalInterestAddResource
	 * @return  - Successfully saved message
	 * 
	 */
	PenalInterest addPenalInterestTemplate(String tenantId, PenalInterestAddResource penalInterestAddResource);
	
	/**
     * Update the penal interest 
	 * @author Dilhan
	 * 
	 * @param	- PenalInterestUpdateResource
	 * @return  - Successfully saved message
	 * 
	 */
	public PenalInterest updatePenalInterestTemplate(String tenantId, Long id, PenalInterestUpdateResource penalInterestUpdateResource);
	
	/**
     * Gets the penal interest by code
     *
     * @param code the code of the record
     * @return the penal interest
     */
	public Optional<PenalInterest> getPenalTemplateByCode(String interestTemplateCode);
	
	/**
	 * 
	 * Approve Reject
	 * @author Dilhan
	 * @param  - tenantId
	 * @param - workflowStatus
	 * @return - Successfully saved
	 * 
	 * */
	public boolean approveReject(String tenantId, Long id, WorkflowStatus workflowStatus);
	
	
	/**
	 * 
	 * Find Pending Penal Interes by ID
	 * @author Dilhan
	 * @return -JSON array of Penal Interest Pending
	 * 
	 * */
	public Optional<PenalInterestPending> getByPendingId(Long pendingId);

//	/**
//     * Search Interest Templates
//	 * @param searchq - the searchq
//	 * @param name - the name
//	 * @param code - the code
//	 * @param status - the status
//	 * @param pageable - the pageable
//	 * @return the page
//	 */
//	public Page<PenalInterest> searchPenalInterestTemplateList(String searchq, String name, String code, String status,
//			Pageable pageable);
}