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
import com.fusionx.lending.origination.domain.LeadInfoStaging;
import com.fusionx.lending.origination.exception.UserNotFound;
import com.fusionx.lending.origination.resource.LeadInfoStagingAddRequestResource;
import com.fusionx.lending.origination.resource.LeadInfoStagingUpdateRequestResource;
import com.fusionx.lending.origination.resource.LeadInfoStagingWithAdditionalDetailsResource;
import com.fusionx.lending.origination.resource.SuccessAndErrorDetailsResource;
import com.fusionx.lending.origination.service.LeadInfoStagingService;

/**
 * Lead Info Staging Service.
 * 
 ********************************************************************************************************
 *  ###   Date         	Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   24-JUN-2021   		      FX-6676    Sanatha      Initial Development.
 *    
 ********************************************************************************************************
 */
@RestController
@RequestMapping(value = "/lead-info-staging")
@CrossOrigin(origins = "*")
public class LeadInfoStagingController {
	
	@Autowired
	private LeadInfoStagingService leadInfoStagingService;
	
	@Autowired
	private Environment environment;
	
	private String userNotFound = "common.user-not-found";
	private String commonSaved = "common.saved";
	private String commonUpdated = "common.updated";
	private String recordNotFound = "common.record-not-found";
	
	/**
	 * Gets the all LeadInfoStaging Details  
	 *
	 * @param tenantId - the tenant id
	 * @return the all LeadInfoStaging Details  
	 */
	@GetMapping(value = "/{tenantId}/all")
	public ResponseEntity<Object> getAllLeadInfoStaging  (@PathVariable(value = "tenantId", required = true) String tenantId) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<LeadInfoStaging> leadInfoStaging = leadInfoStagingService.findAll();
		if (!leadInfoStaging.isEmpty()) {
			return new ResponseEntity<>((Collection<LeadInfoStaging>) leadInfoStaging, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Gets the LeadInfoStaging Details   by id.
	 *
	 * @param tenantId - the tenant id
	 * @param id - the id
	 * @return the LeadInfoStaging Details   by id
	 */
	@GetMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> getLeadInfoStagingById(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<LeadInfoStaging> isLeadInfoStaging = leadInfoStagingService.findById(id);
		if (isLeadInfoStaging.isPresent()) {
			return new ResponseEntity<>(isLeadInfoStaging, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Gets the LeadInfoStaging Details   by status.
	 *
	 * @param tenantId - the tenant id
	 * @param status - the status
	 * @return the LeadInfoStaging Details   by status
	 */
	@GetMapping(value = "/{tenantId}/status/{status}")
	public ResponseEntity<Object> getLeadInfoStagingByStatus(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "status", required = true) String status) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<LeadInfoStaging> leadInfoStaging = leadInfoStagingService.findByStatus(status);
		if (!leadInfoStaging.isEmpty()) {
			return new ResponseEntity<>((Collection<LeadInfoStaging>) leadInfoStaging, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Gets the LeadInfoStaging Details   by staging status.
	 *
	 * @param tenantId - the tenant id
	 * @param stagingStatus - the staging status
	 * @return the LeadInfoStaging Details   by staging status
	 */
	@GetMapping(value = "/{tenantId}/staging-status/{stagingStatus}")
	public ResponseEntity<Object> getLeadInfoStagingByStagingStatus(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "stagingStatus", required = true) String stagingStatus) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<LeadInfoStaging> leadInfoStaging = leadInfoStagingService.findByStagingStatus(stagingStatus);
		if (!leadInfoStaging.isEmpty()) {
			return new ResponseEntity<>((Collection<LeadInfoStaging>) leadInfoStaging, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Gets the LeadInfoStaging Details   by lead info id.
	 *
	 * @param tenantId - the tenant id
	 * @param id - the lead info id
	 * @return the LeadInfoStaging Details   by lead info id
	 */
	@GetMapping(value = "/{tenantId}/lead-info-id/{id}")
	public ResponseEntity<Object> getLeadInfoStagingByLeadInfoId(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<LeadInfoStaging> leadInfoStaging = leadInfoStagingService.findByLeadId(id);
		if (!leadInfoStaging.isEmpty()) {
			return new ResponseEntity<>((Collection<LeadInfoStaging>) leadInfoStaging, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Add LeadInfoStaging Details  
	 *
	 * @param tenantId
	 * @param LeadInfoStagingAddRequestResource
	 * @return SuccessAndErrorDetailsResource
	 */
	@PostMapping(value = "/{tenantId}")
	public ResponseEntity<Object> saveLeadInfoStaging(@PathVariable(value = "tenantId", required = true) String tenantId, 
			@Valid @RequestBody LeadInfoStagingAddRequestResource leadInfoStagingAddRequestResource) {
		if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new UserNotFound(environment.getProperty(userNotFound));
		leadInfoStagingService.saveAndValidateLeadInfoStagingDetails(tenantId, LogginAuthentcation.getInstance().getUserName(), leadInfoStagingAddRequestResource);
		SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty(commonSaved));
		return new ResponseEntity<>(successDetailsDto,HttpStatus.CREATED);
	}
	
	/**
	 * Update LeadInfoStaging Details  
	 *
	 * @param tenantId - the tenant id
	 * @param id - LeadInfoStaging detail id
	 * @param LeadInfoStagingUpdateRequestResource 
	 * @return the response entity
	 */
	@PutMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> updateLeadInfoStaging(@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id,
			@Valid @RequestBody LeadInfoStagingUpdateRequestResource leadInfoStagingUpdateRequestResource) {
		if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new UserNotFound(environment.getProperty(userNotFound));
	
			leadInfoStagingService.updateAndValidateLeadInfoStagingDetails(tenantId, LogginAuthentcation.getInstance().getUserName(), leadInfoStagingUpdateRequestResource,id);
			SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty(commonUpdated));
			return new ResponseEntity<>(successDetailsDto, HttpStatus.OK);
		
	}
	
	/**
	 * Get Lead Info Staging with additional details.
	 * @param @pathVariable{tenantId}
	 * @param @pathVariable{stagingStatus}
	 * @param @pathVariable{user}
	 * @return List
	 **/
	@GetMapping(value = "/{tenantId}/additional-details/{stagingStatus}/user/{user}")
	public ResponseEntity<Object>  getStagingStausWithAdditionalDetails(@PathVariable(value = "tenantId", required = true) String tenantId,
                                                              @PathVariable(value = "stagingStatus", required = true) String stagingStatus,
                                                              @PathVariable(value = "user", required = true) String user){
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<LeadInfoStagingWithAdditionalDetailsResource> leadInfoStaging =leadInfoStagingService.findByStagingStausWithAdditionalDetails(tenantId,stagingStatus,user);
		if (!leadInfoStaging.isEmpty()) {
			return new ResponseEntity<>((Collection<LeadInfoStagingWithAdditionalDetailsResource>) leadInfoStaging, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
		
		
		
	}

}
