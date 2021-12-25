package com.fusionx.lending.product.service;

/**
 * Interest Template Service
 * @author 	Venuki
 * @Dat     07-06-2021
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   07-06-2019   FX-2879        FX-6532    Venuki      Created
 *    
 ********************************************************************************************************
 */

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fusionx.lending.product.domain.InterestTemplate;
import com.fusionx.lending.product.domain.InterestTemplatePending;
import com.fusionx.lending.product.enums.WorkflowStatus;
import com.fusionx.lending.product.resources.InterestTemplateAddResource;
import com.fusionx.lending.product.resources.InterestTemplateUpdateResource;

@Service
public interface InterestTemplateService {
	
	/**
     * Find all Interest Template
	 * @author Venuki
	 * 
	 * @return   		- JSON Array of all Interest Template objects
	 * 
	 */
	List<InterestTemplate> findAll();
	
	
	/**
     * Find Interest Template by Id
	 * @author Venuki
	 * 
	 * @return   		- JSON Array of Interest Template objects
	 * 
	 */
	 Optional<InterestTemplate> findById(Long interestTemplateId);
	
	/**
     * Find Interest Template by Status
	 * @author Venuki
	 * 
	 * @return   		- JSON Array of Interest Template objects
	 * 
	 */
	 List<InterestTemplate> findByStatus(String status);
	
	/**
     * Insert Interest Template
	 * @author Venuki
	 * 
	 * @param	- InterestTemplateAddResource
	 * @return  - Successfully saved message
	 * 
	 */
	 InterestTemplate addInterestTemplate(String tenantId, InterestTemplateAddResource interestTemplateAddResource, String userName);
	
	/**
     * Update Interest Template
	 * @author Venuki
	 * 
	 * @param	- OtherEligibilityUpdateResource
	 * @return  - Successfully saved message
	 * 
	 */
	 InterestTemplate updateInterestTemplate(String tenantId, Long id, InterestTemplateUpdateResource interestTemplateUpdateResource, String userName);
	
	/**
     * Find Interest Template by code
	 * @author Venuki
	 * 
	 * @return   		- JSON Array of Interest Template objects
	 * 
	 */
	 Optional<InterestTemplate> getInterestTemplateByCode(String interestTemplateCode);

	/**
     * Search Interest Templates
	 * @param searchq - the searchq
	 * @param name - the name
	 * @param code - the code
	 * @param status - the status
	 * @param pageable - the pageable
	 * @return the page
	 */
	 Page<InterestTemplate> searchInterestTemplateList(String searchq, String name, String code, String status,
			Pageable pageable);

	/**
	* Find Interest Template by name
	* @param name -  name
	* @return - JSON Array of Interest Template objects
	*/	
	List<InterestTemplate> findByName(String name);
	
	/**
	 * 
	 * Find Pending interest template  by ID
	 * @author SewwandiH
	 * @param pendingId
	 * @return -JSON object of Interest Pending
	 * 
	 * */
	public Optional<InterestTemplatePending> getByPendingId(Long pendingId);
	
	/**
	 * 
	 * Approve Reject
	 * @author SewwandiH
	 * @param  - tenantId
	 * @param - workflowStatus
	 * @return - Successfully saved
	 * 
	 *  */
	public boolean approveReject(String tenantId, Long id, WorkflowStatus workflowStatus); 
}
