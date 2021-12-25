package com.fusionx.lending.origination.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fusionx.lending.origination.core.LogginAuthentcation;
import com.fusionx.lending.origination.domain.ExternalLiability;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.exception.UserNotFound;
import com.fusionx.lending.origination.repository.ExternalLiabilityRepository;
import com.fusionx.lending.origination.resource.ExternalLiabilityAddResource;
import com.fusionx.lending.origination.resource.ExternalLiabilityUpdateResource;
import com.fusionx.lending.origination.resource.SuccessAndErrorDetailsResource;
import com.fusionx.lending.origination.service.ExternalLiabilityService;

/**
 * External Liability  Controller
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   04-08-2021     		     FXL-414    NipunDilhan      Created
 *    
 ********************************************************************************************************
 */

@RestController
@RequestMapping(value = "/external-liability")
@CrossOrigin(origins = "*")
public class ExternalLiabilityController {

	
	
	@Autowired
	private Environment environment;
	
	
	@Autowired
	private ExternalLiabilityService externalLiabilityService;
	@Autowired
	private ExternalLiabilityRepository externalLiabilityRepository;

	
	private String userNotFound = "common.user-not-found";
	private String commonSaved = "common.saved";
	private String notFound = "common.not-found";
	
	

	
	
	/**
	 * Gets the  External Liabilites by id.
	 *
	 * @param tenantId - the tenant id
	 * @param id - the id
	 * @return the  External Liability
	 */
	@GetMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> getExternalLiabilitesById(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id) {
		
		Optional<ExternalLiability> externalLiabilityOptional = externalLiabilityRepository.findById(id);
		SuccessAndErrorDetailsResource responseMessage=new SuccessAndErrorDetailsResource();
		if (externalLiabilityOptional.isPresent()) {
			return new ResponseEntity<>(externalLiabilityOptional.get(), HttpStatus.OK);
		} else {
			responseMessage.setMessages(notFound);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	/**
	 * Gets the all External Liabilites.
	 *
	 * @param tenantId - the tenant id
	 * @return the all External Liabilites
	 */
	@GetMapping(value = "/{tenantId}/all")
	public ResponseEntity<Object> getAllExternalLiabilites(
			@PathVariable(value = "tenantId", required = true) String tenantId) {
		
		List<ExternalLiability> externalLiabilityList = externalLiabilityRepository.findAll();
		SuccessAndErrorDetailsResource responseMessage=new SuccessAndErrorDetailsResource();
		if (!externalLiabilityList.isEmpty()) {
			return new ResponseEntity<>(externalLiabilityList, HttpStatus.OK);
		} else {
			responseMessage.setMessages(notFound);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	
	/**
	 * get  External Liabilites  by customer id
	 * 
	 * @param @PathVariable{tenantId}
	 * @param @PathVariable{id}
	 * @return ExternalLiability list
	 */
	@GetMapping(value = "/{tenantId}/customer/{id}")
	public ResponseEntity<Object> getExternalLiabilitesByCustomerId(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id) {
		
		List<ExternalLiability> externalLiabilityList = externalLiabilityService.findByCustomerId(id);
		SuccessAndErrorDetailsResource responseMessage=new SuccessAndErrorDetailsResource();
		if (!externalLiabilityList.isEmpty()) {
			return new ResponseEntity<>(externalLiabilityList, HttpStatus.OK);
		} else {
			responseMessage.setMessages(notFound);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Gets the  External Liabilites by status.
	 *
	 * @param tenantId - the tenant id
	 * @param status - the status
	 * @return the  External Liabilites by status
	 */
	@GetMapping(value = "/{tenantId}/status/{status}")
	public ResponseEntity<Object> getExternalLiabilitesByStatus(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "status", required = true) String status) {
		SuccessAndErrorDetailsResource responseMessage=new SuccessAndErrorDetailsResource();
 		if(status.equals("ACTIVE") || status.equals("INACTIVE")){
 			List<ExternalLiability> externalLiabilityList = externalLiabilityRepository.findAllByStatus(CommonStatus.valueOf(status));
 			
 			if (!externalLiabilityList.isEmpty()) {
 				return new ResponseEntity<>(externalLiabilityList, HttpStatus.OK);
 			} else {
 				responseMessage.setMessages(notFound);
 				return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
 			}
 		} else{
	 		responseMessage.setMessages(environment.getProperty("invalid-status"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
	 	}
	}	
	
	
	
	/**
	 * Adds the  External Liability
	 *
	 * @param tenantId - the tenant id
	 * @param id - the customer id
	 * @param ExternalLiabilityAddResource - the external liability add resource
	 * @return the response entity
	 */
	@PostMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> saveExternalLiability(@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id, 
			@Valid @RequestBody ExternalLiabilityAddResource externalLiabilitiesAddResource) {
		if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new UserNotFound(environment.getProperty(userNotFound));
	
		ExternalLiability externalLiability = externalLiabilityService.addExternalLiability(tenantId,id, externalLiabilitiesAddResource);
		
		if(externalLiability != null) {
			SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty(commonSaved),externalLiability.getId());
			return new ResponseEntity<>(successDetailsDto,HttpStatus.CREATED);
		}else {
			SuccessAndErrorDetailsResource successDetailsDto=new SuccessAndErrorDetailsResource(environment.getProperty("common.internal-server-error"));
	    	return new ResponseEntity<>(successDetailsDto,HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
	
	/**
	 * Update   External Liability
	 *
	 * @param tenantId - the tenant id
	 * @param id - the External Liability id
	 * @param ExternalLiabilityAddResource - the external liability add resource
	 * @return the response entity
	 */	
	@PutMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> updateExternalLiability(@PathVariable(value = "tenantId", required = true) String tenantId, 
			@PathVariable(value = "id", required = true) Long id,
			@Valid @RequestBody ExternalLiabilityUpdateResource externalLiabilityUpdateResource) {
		if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new UserNotFound(environment.getProperty(userNotFound));
	
		ExternalLiability externalLiability = externalLiabilityService.updateExternalLiability(tenantId,id, externalLiabilityUpdateResource);
		
		if(externalLiability != null) {
			SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty("common.updated"),externalLiability.getId());
			return new ResponseEntity<>(successDetailsDto,HttpStatus.CREATED);
		}else {
			SuccessAndErrorDetailsResource successDetailsDto=new SuccessAndErrorDetailsResource(environment.getProperty("common.internal-server-error"));
	    	return new ResponseEntity<>(successDetailsDto,HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
	
}
