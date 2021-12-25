package com.fusionx.lending.origination.controller;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.fusionx.lending.origination.base.MessagePropertyBase;
import com.fusionx.lending.origination.domain.ExpenceTypeCultivationCategory;
import com.fusionx.lending.origination.domain.LeadInfo;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.resource.NewFacilityAddResource;
import com.fusionx.lending.origination.resource.NewFacilityUpdateResource;
import com.fusionx.lending.origination.resource.SuccessAndErrorDetailsResource;
import com.fusionx.lending.origination.service.NewFacilityService;

/**
 * New Facility Controller
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   26-04-2021   							 MenukaJ        Created
 *    
 ********************************************************************************************************
 */

@RestController
@RequestMapping(value = "/new-facility")
@CrossOrigin(origins = "*")
public class NewFacilityController extends MessagePropertyBase {
	
	@Autowired
	private NewFacilityService newFacilityService;
	
	/**
	 * get all Facility
	 * @param @PathVariable{tenantId}
	 * @param @PathVariable{all}
	 * @return List
	 **/
	@GetMapping("/{tenantId}/all")
	public ResponseEntity<Object> getAllFacility(@PathVariable(value = "tenantId", required = true) String tenantId){
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<LeadInfo> isPresentLeadInfo = newFacilityService.getAll();
		if(!isPresentLeadInfo.isEmpty()) {
			return new ResponseEntity<>((Collection<LeadInfo>)isPresentLeadInfo,HttpStatus.OK);
		}
		else {
			responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * get Facility by ID
	 * @param @pathVariable{tenantId}
	 * @param @pathVariable {id}
	 * @return Optional
	 **/
	@GetMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> getFacilityById(@PathVariable(value = "tenantId", required = true) String tenantId,
													         	  @PathVariable(value = "id", required = true) Long id){
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<LeadInfo> isPresentLeadInfo = newFacilityService.getById(id);
		if(isPresentLeadInfo.isPresent()) {
			return new ResponseEntity<>(isPresentLeadInfo.get(), HttpStatus.OK);
		}
		else {
			responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	

	
	/**
	 * get Facility by status
	 * @param @pathVariable{tenantId}
	 * @param @pathVariable {status}
	 * @return List
	 **/
	@GetMapping(value = "/{tenantId}/status/{status}")
	public ResponseEntity<Object> getFacilityByStatus(@PathVariable(value = "tenantId", required = true) String tenantId,
													   			      @PathVariable(value = "status", required = true) String status){
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		if(status.equals(CommonStatus.ACTIVE.toString()) || status.equals(CommonStatus.INACTIVE.toString())) {
			List<LeadInfo> isPresentLeadInfo = newFacilityService.getByStatus(status);
			if(!isPresentLeadInfo.isEmpty()) {
				return new ResponseEntity<>(isPresentLeadInfo, HttpStatus.OK);
			}
			else {
				responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
				return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
			}
		}
		else {
			responseMessage.setMessages(RECORD_NOT_FOUND);
	        throw new ValidateRecordException(environment.getProperty("common-status.pattern"), "message");
		}
	}
	
	/**
     * save  Facility
     * @param @PathVariable{tenantId}
     * @param @RequestBody{NewFacilityAddResource}
     * @return SuccessAndErrorDetailsDto
     */
	@PostMapping("/{tenantId}/{leadId}")
	public ResponseEntity<Object> addFacility(@PathVariable(value = "tenantId", required = true) String tenantId,
			 												@PathVariable(value = "leadId", required = true) Long leadId,
			   									       		  @Valid @RequestBody NewFacilityAddResource newFacilityAddResource){
		Long id = newFacilityService.saveFacility(tenantId, leadId, newFacilityAddResource);
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource(getEnvironment().getProperty(RECORD_CREATED), Long.toString(id));
		return new ResponseEntity<>(responseMessage,HttpStatus.CREATED);
	}
	
	/**
     * update facility
     * @param @PathVariable{tenantId}
     * @param @RequestBody{NewFacilityUpdateResource}
     * @return SuccessAndErrorDetailsDto
     */
	@PutMapping(value = "{tenantId}/{leadId}")
	public ResponseEntity<Object> updateFacility(@PathVariable(value = "tenantId", required = true) String tenantId, 
												                 @PathVariable(value = "leadId", required = true) Long leadId, 
												                 @Valid @RequestBody NewFacilityUpdateResource newFacilityUpdateResource){
		SuccessAndErrorDetailsResource successAndErrorDetailsResource=new SuccessAndErrorDetailsResource();
		Optional<LeadInfo>isPresentLeadInfo= newFacilityService.getById(leadId);		
		if(isPresentLeadInfo.isPresent()) {
			Long value = newFacilityService.updateFacility(tenantId, newFacilityUpdateResource, leadId);
			successAndErrorDetailsResource = new SuccessAndErrorDetailsResource(getEnvironment().getProperty(RECORD_UPDATED), value.toString());
			return new ResponseEntity<>(successAndErrorDetailsResource,HttpStatus.OK);
		}
		else {
			successAndErrorDetailsResource.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}	
	
}
