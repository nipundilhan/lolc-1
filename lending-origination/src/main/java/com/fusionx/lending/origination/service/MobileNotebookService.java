package com.fusionx.lending.origination.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fusionx.lending.origination.domain.MobileNotebook;
import com.fusionx.lending.origination.resource.AddToLeadResource;
import com.fusionx.lending.origination.resource.MobileNotebookAddResource;
import com.fusionx.lending.origination.resource.MobileNotebookDeleteResource;
import com.fusionx.lending.origination.resource.MobileNotebookReminderResponseResource;
import com.fusionx.lending.origination.resource.MobileNotebookUpdateResource;

/**
 * Mobile Notebook Service
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   01-06-2021   		         FX-6506    SenithaP     Created
 *    
 ********************************************************************************************************
 */

@Service
public interface MobileNotebookService {

	/**
	 * @author Senitha
	 * 
	 * Find all Mobile Notebook
	 * @return -JSON array of all Mobile Notebook
	 * 
	 * */
	public Page<MobileNotebook> getAll(String tenantId, Pageable pageable);
	
	/**
	 * @author Senitha
	 * 
	 * Find Mobile Notebook by ID
	 * @return -JSON array of Mobile Notebook
	 * 
	 * */
	public Optional<MobileNotebook> getById(String tenantId, Long id);
	
	/**
	 * @author Senitha
	 * 
	 * Find Mobile Notebook by Customer Name
	 * @return -JSON array of Mobile Notebook
	 * 
	 * */
	public List<MobileNotebook>searchByCustomerName(String tenantId, String customerName);
	
	/**
	 * @author Senitha
	 * 
	 * Find Mobile Notebook by NIC Number
	 * @return -JSON array of Mobile Notebook
	 * 
	 * */
	public List<MobileNotebook>searchByNicNo(String tenantId, String nicNo);
	
	/**
	 * @author Senitha
	 * 
	 * Find Mobile Notebook by status
	 * @return -JSON array of Mobile Notebook
	 * 
	 * */
	public List<MobileNotebook> getByStatus(String tenantId, String status);
	
	/**
	 * @author Senitha
	 * 
	 * Find Mobile Notebook by on boarding status
	 * @return -JSON array of Mobile Notebook
	 * 
	 * */
	public List<MobileNotebook> getByOnboardingStatus(String tenantId, String onboardingStatus);
	
	/**
	 * @author Senitha
	 * 
	 * Insert Mobile Notebook
	 * @param  - MobileNotebookAddResource
	 * @return - Successfully saved
	 * 
	 * */
	public MobileNotebook addMobileNotebook(String tenantId, MobileNotebookAddResource mobileNotebookAddResource);

	/**
	 * @author Senitha
	 * 
	 * Update Mobile Notebook
	 * @param  - MobileNotebookUpdateResource
	 * @return - Successfully saved
	 * 
	 * */
	public MobileNotebook updateMobileNotebook(String tenantId, MobileNotebookUpdateResource mobileNotebookUpdateResource);
	
	/**
	 * @author Senitha
	 * 
	 * Delete Mobile Notebook
	 * @param  - MobileNotebookDeleteResource
	 * @return - Successfully saved
	 * 
	 * */
	public MobileNotebook deleteMobileNotebook(String tenantId, MobileNotebookDeleteResource mobileNotebookDeleteResource);
	
	/**
	 * @author Senitha
	 * 
	 * Add To Lead Mobile Notebook
	 * @param  - AddToLeadResource
	 * @return - Successfully saved
	 * 
	 * */
	public MobileNotebook addToLead(String tenantId, AddToLeadResource addToLeadResource);
	
	/**
	 * @author Senitha
	 * 
	 * Generate Notebook Reminder
	 * @return -JSON array of Mobile Notebook Reminder Response Resource
	 * 
	 * */
	public List<MobileNotebookReminderResponseResource>generateReminder();

}
