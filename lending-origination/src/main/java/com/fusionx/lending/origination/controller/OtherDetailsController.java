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
import com.fusionx.lending.origination.domain.OtherDetails;
import com.fusionx.lending.origination.exception.UserNotFound;
import com.fusionx.lending.origination.resource.OtherDetailsAddRequestResource;
import com.fusionx.lending.origination.resource.OtherDetailsUpdateRequestResource;
import com.fusionx.lending.origination.resource.SuccessAndErrorDetailsResource;
import com.fusionx.lending.origination.service.OtherDetailsService;

/**
 * Other Detail Service.
 * 
 ********************************************************************************************************
 *  ###   Date         	Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   04-MAY-2021   		      FX-6484    Sanatha      Initial Development.
 *    
 ********************************************************************************************************
 */
@RestController
@RequestMapping(value = "/other-details")
@CrossOrigin(origins = "*")
public class OtherDetailsController {
	
	@Autowired
	private OtherDetailsService otherDetailsService;
	
	@Autowired
	private Environment environment;
	
	private String userNotFound = "common.user-not-found";
	private String commonSaved = "common.saved";
	private String commonUpdated = "common.updated";
	private String recordNotFound = "common.record-not-found";
	
	/**
	 * Gets the all Other Details  
	 *
	 * @param tenantId - the tenant id
	 * @return the all Other Details  
	 */
	@GetMapping(value = "/{tenantId}/all")
	public ResponseEntity<Object> getAllOtherDetails  (@PathVariable(value = "tenantId", required = true) String tenantId) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<OtherDetails> otherDetails = otherDetailsService.findAll(tenantId);
		if (!otherDetails.isEmpty()) {
			return new ResponseEntity<>((Collection<OtherDetails>) otherDetails, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Gets the Other Details   by id.
	 *
	 * @param tenantId - the tenant id
	 * @param id - the id
	 * @return the Other Details  e by id
	 */
	@GetMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> getOtherDetailsById(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<OtherDetails> isOtherDetailsType = otherDetailsService.findById(tenantId, id);
		if (isOtherDetailsType.isPresent()) {
			return new ResponseEntity<>(isOtherDetailsType, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	/**
	 * Gets the Other Details   by status.
	 *
	 * @param tenantId - the tenant id
	 * @param status - the status
	 * @return the Other Details by status
	 */
	@GetMapping(value = "/{tenantId}/status/{status}")
	public ResponseEntity<Object> getOtherDetailsByStatus(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "status", required = true) String status) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<OtherDetails> otherDetails = otherDetailsService.findByStatus(tenantId, status);
		if (!otherDetails.isEmpty()) {
			return new ResponseEntity<>((Collection<OtherDetails>) otherDetails, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Gets the Other Details   by Lead info id.
	 *
	 * @param tenantId - the tenant id
	 * @param id - the Lead info id
	 * @return the Other Details  e by Lead Info id
	 */
	@GetMapping(value = "/{tenantId}/lead-info-id/{id}")
	public ResponseEntity<Object> getOtherDetailsByLeadInfoId(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<OtherDetails> otherDetails = otherDetailsService.findByLeadId(tenantId, id);
		if (otherDetails.isPresent()) {
			return new ResponseEntity<>( otherDetails, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Add Other Details  
	 *
	 * @param tenantId
	 * @param OtherDetailsAddRequestResource
	 * @return SuccessAndErrorDetailsResource
	 */
	@PostMapping(value = "/{tenantId}")
	public ResponseEntity<Object> saveOtherDetails(@PathVariable(value = "tenantId", required = true) String tenantId, 
			@Valid @RequestBody OtherDetailsAddRequestResource otherDetailsAddRequestResource) {
		if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new UserNotFound(environment.getProperty(userNotFound));
		otherDetailsService.saveAndValidateOtherDetails(tenantId, LogginAuthentcation.getInstance().getUserName(), otherDetailsAddRequestResource);
		SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty(commonSaved));
		return new ResponseEntity<>(successDetailsDto,HttpStatus.CREATED);
	}
	
	/**
	 * Update Other Details  
	 *
	 * @param tenantId - the tenant id
	 * @param id - other detail id
	 * @param OtherDetailsAddRequestResource 
	 * @return the response entity
	 */
	@PutMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> updateOtherDetails(@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id,
			@Valid @RequestBody OtherDetailsUpdateRequestResource otherDetailsUpdateRequestResource) {
		if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new UserNotFound(environment.getProperty(userNotFound));
	
			otherDetailsService.updateAndValidateOtherDetails(tenantId, LogginAuthentcation.getInstance().getUserName(), otherDetailsUpdateRequestResource,id);
			SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty(commonUpdated));
			return new ResponseEntity<>(successDetailsDto, HttpStatus.OK);
		
	}

}
