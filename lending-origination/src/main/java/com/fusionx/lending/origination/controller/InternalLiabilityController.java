package com.fusionx.lending.origination.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fusionx.lending.origination.core.LogginAuthentcation;
import com.fusionx.lending.origination.domain.Customer;
import com.fusionx.lending.origination.domain.ExternalLiability;
import com.fusionx.lending.origination.domain.InternalLiability;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.exception.UserNotFound;
import com.fusionx.lending.origination.repository.ExternalLiabilityRepository;
import com.fusionx.lending.origination.repository.InternalLiabilityRepository;
import com.fusionx.lending.origination.resource.ExternalLiabilityUpdateResource;
import com.fusionx.lending.origination.resource.InternalLiabilityAddResource;
import com.fusionx.lending.origination.resource.InternalLiabilityUpdateResource;
import com.fusionx.lending.origination.resource.SuccessAndErrorDetailsResource;
import com.fusionx.lending.origination.service.InternalLiabilityService;


/**
 * Internal Liability Controller
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   04-08-2021     		     FXL-413      	NipunDilhan      Created
 *    
 ********************************************************************************************************
 */

@RestController
@RequestMapping(value = "/internal-liability")
@CrossOrigin(origins = "*")
@Validated
public class InternalLiabilityController {
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private InternalLiabilityService internalLiabilityService;

	
	@Autowired
	private InternalLiabilityRepository internalLiabilityRepository;

	
	private String userNotFound = "common.user-not-found";
	private String commonSaved = "common.saved";
	private String commonUpdated = "common.updated";
	private String pageableLength = "common.pageable-length";
	private String recordNotFound = "common.record-not-found";
	private String notFound = "common.not-found";

	/**
	 * Gets the  Internal Liabilites by id.
	 *
	 * @param tenantId - the tenant id
	 * @param id - the id
	 * @return the  Internal Liability
	 */
	@GetMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> getInternalLiabilitesById(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id) {
		
		Optional<InternalLiability> internalLiabilityOptional = internalLiabilityRepository.findById(id);
		SuccessAndErrorDetailsResource responseMessage=new SuccessAndErrorDetailsResource();
		if (internalLiabilityOptional.isPresent()) {
			return new ResponseEntity<>(internalLiabilityOptional.get(), HttpStatus.OK);
		} else {
			responseMessage.setMessages(notFound);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * get  Internal Liabilites  by customer id
	 * 
	 * @param @PathVariable{tenantId}
	 * @param @PathVariable{id} - customer id 
	 * @return InternalLiability list
	 */
	@GetMapping(value = "/{tenantId}/customer/{id}")
	public ResponseEntity<Object> getInternalLiabilitesByCustomerId(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id) {
		
		List<InternalLiability> internalLiabilityList = internalLiabilityService.findByCustomerId(id);
		SuccessAndErrorDetailsResource responseMessage=new SuccessAndErrorDetailsResource();
		if (!internalLiabilityList.isEmpty()) {
			return new ResponseEntity<>(internalLiabilityList, HttpStatus.OK);
		} else {
			responseMessage.setMessages(notFound);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	
	/**
	 * Gets the all Internal Liabilites.
	 *
	 * @param tenantId - the tenant id
	 * @return the all Internal Liabilites
	 */
	@GetMapping(value = "/{tenantId}/all")
	public ResponseEntity<Object> getAllInternalLiabilites(
			@PathVariable(value = "tenantId", required = true) String tenantId) {
		
		List<InternalLiability> internalLiabilityList = internalLiabilityRepository.findAll();
		SuccessAndErrorDetailsResource responseMessage=new SuccessAndErrorDetailsResource();
		if (!internalLiabilityList.isEmpty()) {
			return new ResponseEntity<>(internalLiabilityList, HttpStatus.OK);
		} else {
			responseMessage.setMessages(notFound);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Gets the  Internal Liabilites by status.
	 *
	 * @param tenantId - the tenant id
	 * @param status - the status
	 * @return the  Internal Liabilites by status
	 */
	@GetMapping(value = "/{tenantId}/status/{status}")
	public ResponseEntity<Object> getInternalLiabilitesByStatus(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "status", required = true) String status) {
		SuccessAndErrorDetailsResource responseMessage=new SuccessAndErrorDetailsResource();
 		if(status.equals("ACTIVE") || status.equals("INACTIVE")){
 			List<InternalLiability> internalLiabilityList = internalLiabilityRepository.findAllByStatus(status);
 			
 			if (!internalLiabilityList.isEmpty()) {
 				return new ResponseEntity<>(internalLiabilityList, HttpStatus.OK);
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
	 * Adds the  Internal Liability
	 *
	 * @param tenantId - the tenant id
	 * @param id - the customer id
	 * @param List<InternalLiabilityAddResource>  - the internal liability add resource
	 * @return the response entity
	 */
	@PostMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> save(@PathVariable(value = "tenantId", required = true) String tenantId, 

			@PathVariable(value = "id", required = true) Long id,
			@RequestBody List<@Valid InternalLiabilityAddResource> internalLiabilityAddResourceList) {
		if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new UserNotFound(environment.getProperty(userNotFound));
	
		Customer customer = internalLiabilityService.saveInternalLiabilityList(tenantId,id, internalLiabilityAddResourceList);
		
		if(customer != null) {
			SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty(commonSaved));
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
	public ResponseEntity<Object> update(@PathVariable(value = "tenantId", required = true) String tenantId, 
			@PathVariable(value = "id", required = true) Long id,
			@RequestBody List<@Valid InternalLiabilityUpdateResource> internalLiabilityUpdateResourceList) {
		if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new UserNotFound(environment.getProperty(userNotFound));
	
		Customer customer = internalLiabilityService.updateInternalLiabilityList(id, internalLiabilityUpdateResourceList);
		
		if(customer != null) {
			SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty("common.updated"));
			return new ResponseEntity<>(successDetailsDto,HttpStatus.OK);
		}else {
			SuccessAndErrorDetailsResource successDetailsDto=new SuccessAndErrorDetailsResource(environment.getProperty("common.internal-server-error"));
	    	return new ResponseEntity<>(successDetailsDto,HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
	
}
