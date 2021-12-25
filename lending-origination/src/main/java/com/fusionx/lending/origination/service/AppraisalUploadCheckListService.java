package com.fusionx.lending.origination.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fusionx.lending.origination.domain.AppraisalUploadCheckList;
import com.fusionx.lending.origination.enums.ServiceEntity;
import com.fusionx.lending.origination.enums.ServicePoint;
import com.fusionx.lending.origination.resource.AppraisalUploadCheckListAddResource;
import com.fusionx.lending.origination.resource.AppraisalUploadCheckListUpdateResource;


/**
 * Appraisal Upload Check List Service
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   26-09-2021      		     			SewwandiH      Created
 *    
 ********************************************************************************************************
 */

@Service
public interface AppraisalUploadCheckListService {
	
	/**
	 * Find all.
	 *
	 * @return the list
	 */
	public List<AppraisalUploadCheckList> findAll();
	
	/**
	 * Find by id.
	 *
	 * @param id - the id
	 * @return appraisal upload check list
	 */
	public Optional<AppraisalUploadCheckList> findById(Long id);
	
	/**
	 * Find by status.
	 *
	 * @param status - the status
	 * @return appraisal upload check list
	 */	
	public List<AppraisalUploadCheckList> findByStatus(String status);
	
	/** find by check list templates id
	 * '
	 * @param id - check list id
	 * @return the appraisal upload check list
	 */
	public List<AppraisalUploadCheckList> findByChecklistTemplateId(Long checklistTemplateId);
	
	/**
	 * Save and validate appraisal upload check list
	 *
	 * @param id - the id
	 * @return the boolean
	 */
	public Long saveAndValidateappraisalUploadCheckList(String tenantId, String createdUser, AppraisalUploadCheckListAddResource appraisalUploadCheckListAddResource);
	
	/**
	 * Update and validate appraisal upload check list
	 *
	 * @param id - the id
	 * @return the boolean
	 */
	public Long updateAndValidateappraisalUploadCheckList(String tenantId, String createdUser, Long id,AppraisalUploadCheckListUpdateResource appraisalUploadCheckListUpdateResource);

	/**
	 * exists by id
	 */
	public Boolean existsById(Long id); 
}
