package com.fusionx.lending.origination.service;

import org.springframework.stereotype.Service;

import com.fusionx.lending.origination.resource.AdditionalDocumentAddRequestResource;

/**
 * Additional Document Detail Service.
 * 
 ********************************************************************************************************
 *  ###   Date         	Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   04-MAY-2021   		      FX-6484    Sanatha      Initial Development.
 *    
 ********************************************************************************************************
 */
@Service
public interface AdditionalDocumentService {
	
	/**
	 * Save and validate Other details.
	 *
	 * @param tenantId - the tenant id
	 * @param createdUser - the created user
	 * @param createdUser - the lead info id
	 * @param AdditionalDocumentAddRequestResource - the additional document details add resource
	 * @return the id
	 */
	public void saveAndValidateAdditionalDocuments(String tenantId, String createdUser, AdditionalDocumentAddRequestResource additionalDocumentAddRequestResource);

}
