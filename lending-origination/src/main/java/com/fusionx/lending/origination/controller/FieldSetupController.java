package com.fusionx.lending.origination.controller;

import java.util.Collection;
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
import com.fusionx.lending.origination.domain.FieldSetup;
import com.fusionx.lending.origination.domain.RiskGrading;
import com.fusionx.lending.origination.exception.UserNotFound;
import com.fusionx.lending.origination.resource.FieldSetupAddResource;
import com.fusionx.lending.origination.resource.FieldSetupUpdateResource;
import com.fusionx.lending.origination.resource.RiskGradingAddResource;
import com.fusionx.lending.origination.resource.RiskGradingUpdateResource;
import com.fusionx.lending.origination.resource.SuccessAndErrorDetailsResource;
import com.fusionx.lending.origination.service.FieldSetupService;
import com.fusionx.lending.origination.service.RiskGradingService;

/**
 * Credit risk parameter template field setup.
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   16-08-2021      		     			SewwandiH      Created
 *    
 ********************************************************************************************************
 */

@RestController
@RequestMapping(value = "/field-setup")
@CrossOrigin(origins = "*")
public class FieldSetupController {
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private FieldSetupService fieldSetupService;
	
	private String userNotFound = "common.user-not-found";
	private String commonSaved = "common.saved";
	private String commonUpdated = "common.updated";
	private String pageableLength = "common.pageable-length";
	private String recordNotFound = "common.record-not-found";

	
	/**
	 * Get the all field setups.
	 *
	 * @param tenantId - the tenant id
	 * @return the all field setups.
	 */
	@GetMapping(value = "/{tenantId}/all")
	public ResponseEntity<Object> getAllFieldSetup(@PathVariable(value = "tenantId", required = true) String tenantId) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<FieldSetup> fieldSetup = fieldSetupService.findAll();
		if (!fieldSetup.isEmpty()) {
			return new ResponseEntity<>((Collection<FieldSetup>) fieldSetup, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Get the field setup by id.
	 *
	 * @param tenantId - the tenant id
	 * @param id - the id
	 * @return the field setup by id
	 */
	@GetMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> getFieldSetupById(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<FieldSetup> isPresentFieldSetup = fieldSetupService.findById(id);
		if (isPresentFieldSetup.isPresent()) {
			return new ResponseEntity<>(isPresentFieldSetup, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Get the Field setup by status.
	 *
	 * @param tenantId - the tenant id
	 * @param status - the status
	 * @return the field setup by status
	 */
	@GetMapping(value = "/{tenantId}/status/{status}")
	public ResponseEntity<Object> getFieldSetupByStatus(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "status", required = true) String status) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<FieldSetup> fieldSetup = fieldSetupService.findByStatus(status);
		if (!fieldSetup.isEmpty()) {
			return new ResponseEntity<>((Collection<FieldSetup>) fieldSetup, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Get the Field setup by Field name.
	 *
	 * @param tenantId - the tenant id
	 * @param name - the name
	 * @return the field setup by name
	 */
	@GetMapping(value = "/{tenantId}/fieldName/{fieldName}")
	public ResponseEntity<Object> getFieldSetupByName(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "fieldName", required = true) String fieldName) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<FieldSetup> fieldSetup = fieldSetupService.findByName(fieldName);
		if (!fieldSetup.isEmpty()) {
			return new ResponseEntity<>((Collection<FieldSetup>) fieldSetup, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Get the field setup by code.
	 *
	 * @param tenantId - the tenant id
	 * @param code - the code
	 * @return the field setup by code
	 */
	@GetMapping(value = "/{tenantId}/fieldId/{fieldId}")
	public ResponseEntity<Object> getFieldSetupByCode(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "fieldId", required = true) String fieldId) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<FieldSetup> fieldSetup = fieldSetupService.findByCode(fieldId);
		if (fieldSetup.isPresent()) {
			return new ResponseEntity<>(fieldSetup, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Add the Field setup.
	 *
	 * @param tenantId - the tenant id
	 * @param fieldSetupAddResource - the field setup request
	 * @return the response entity
	 */
	@PostMapping(value = "/{tenantId}")
	public ResponseEntity<Object> addFieldSetup(@PathVariable(value = "tenantId", required = true) String tenantId,
			@Valid @RequestBody FieldSetupAddResource fieldSetupAddResource) {
		if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new UserNotFound(environment.getProperty(userNotFound));
		Long id = fieldSetupService.save(tenantId, LogginAuthentcation.getInstance().getUserName(), fieldSetupAddResource);
		SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty(commonSaved));
		return new ResponseEntity<>(successDetailsDto,HttpStatus.CREATED);
	}
	
	/**
	 * update the Field setup.
	 *
	 * @param tenantId - the tenant id
	 * @param riskGradingAddResource - the risk grading update resource
	 * @return the response entity
	 */
	@PutMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> updateFieldSetup(@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id,
			@Valid @RequestBody FieldSetupUpdateResource fieldSetupUpdateResource) {
		if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new UserNotFound(environment.getProperty(userNotFound));
		if (fieldSetupService.existsById(id)) {
			fieldSetupService.update(tenantId, LogginAuthentcation.getInstance().getUserName(), id, fieldSetupUpdateResource);
			SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty(commonUpdated));
			return new ResponseEntity<>(successDetailsDto, HttpStatus.OK);
		} else {
			SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(successDetailsDto, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
}
