package com.fusionx.lending.origination.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fusionx.lending.origination.core.LogginAuthentcation;
import com.fusionx.lending.origination.exception.UserNotFound;
import com.fusionx.lending.origination.resource.AdditionalDocumentAddRequestResource;
import com.fusionx.lending.origination.resource.SuccessAndErrorDetailsResource;
import com.fusionx.lending.origination.service.AdditionalDocumentService;


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
@RestController
@RequestMapping(value = "/additional-documents")
@CrossOrigin(origins = "*")
public class AdditionalDocumentController {
	
	@Autowired
	private AdditionalDocumentService additionalDocumentService;
	
	@Autowired
	private Environment environment;
	
	private String userNotFound = "common.user-not-found";
	private String commonSaved = "common.saved";
	
	/**
	 * Save and Update Additional Document Details
	 *
	 * @author Sanatha
	 * @param tenantId
	 * @param AdditionalDocumentAddRequestResource
	 * @return SuccessAndErrorDetailsResource
	 */
	@PostMapping(value = "/{tenantId}")
	public ResponseEntity<Object> saveAndUpdateAdditionalDocuments(@PathVariable(value = "tenantId", required = true) String tenantId, 
			@Valid @RequestBody AdditionalDocumentAddRequestResource additionalDocumentAddRequestResource) {
		if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new UserNotFound(environment.getProperty(userNotFound));
		additionalDocumentService.saveAndValidateAdditionalDocuments(tenantId, LogginAuthentcation.getInstance().getUserName(), additionalDocumentAddRequestResource);
		SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty(commonSaved));
		return new ResponseEntity<>(successDetailsDto,HttpStatus.CREATED);
	}

}
