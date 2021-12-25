package com.fusionx.lending.origination.controller;

/**
 * 	Business Additional Details Controller 
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   09-09-2021   FXL-115  	 FXL-657       Piyumi       Created
 *    
 ********************************************************************************************************
*/

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
import com.fusionx.lending.origination.domain.BusinessAdditionalDetails;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.resource.BusinessAdditionalDetailsAddResource;
import com.fusionx.lending.origination.resource.BusinessAdditionalDetailsUpdateResource;
import com.fusionx.lending.origination.resource.SuccessAndErrorDetailsResource;
import com.fusionx.lending.origination.service.BusinessAdditionalDetailsService;


@RestController
@RequestMapping(value = "/business-additional-details")
@CrossOrigin(origins = "*")
public class BusinessAdditionalDetailsController extends MessagePropertyBase{
	
	
	private BusinessAdditionalDetailsService businessAdditionalDetailsService;
	
	@Autowired
	public void setBusinessAdditionalDetailsService(BusinessAdditionalDetailsService businessAdditionalDetailsService) {
		this.businessAdditionalDetailsService = businessAdditionalDetailsService;
	}
	
	@GetMapping("/{tenantId}/all")
	public ResponseEntity<Object> getAllBusinessAdditionalDetails(@PathVariable(value = "tenantId") String tenantId){
		List<BusinessAdditionalDetails> businessAdditionalDetails = businessAdditionalDetailsService.findAll(tenantId);
		if(!businessAdditionalDetails.isEmpty()) {
			return new ResponseEntity<>(businessAdditionalDetails, HttpStatus.OK);
		}
		else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Get the BusinessAdditionalDetails by id.
	 *
	 * @param tenantId - the tenant id
	 * @param id - the id
	 * @return the BusinessAdditionalDetails by id
	 */
	@GetMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> getBusinessAdditionalDetailsById(@PathVariable(value = "tenantId" ) String tenantId,
													         	  @PathVariable(value = "id") Long id){
			
		Optional<BusinessAdditionalDetails> isPresentBusinessAdditionalDetails = businessAdditionalDetailsService.findById(tenantId,id);
		if(isPresentBusinessAdditionalDetails.isPresent()) {
			return new ResponseEntity<>(isPresentBusinessAdditionalDetails.get(), HttpStatus.OK);
		}
		else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Get the BusinessAdditionalDetailss by status.
	 *
	 * @param tenantId - the tenant id
	 * @param status - the status
	 * @return the BusinessAdditionalDetails by status
	 */
	
	@GetMapping(value = "/{tenantId}/status/{status}")
	public ResponseEntity<Object> getBusinessAdditionalDetailsByStatus(@PathVariable(value = "tenantId") String tenantId,
			@PathVariable(value = "status") String status) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		if(status.equals(CommonStatus.ACTIVE.toString()) || status.equals(CommonStatus.INACTIVE.toString())) {
			
			List<BusinessAdditionalDetails> businessAdditionalDetails = businessAdditionalDetailsService.findByStatus(tenantId,status);
			if(!businessAdditionalDetails.isEmpty()) {
				return new ResponseEntity<>(businessAdditionalDetails, HttpStatus.OK);
			}
			else {
				responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
				return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
			}
		}else {
			responseMessage.setMessages(RECORD_NOT_FOUND);
	        throw new ValidateRecordException(environment.getProperty(COMMON_STATUS_PATTERN), "message");
		}	
	}
	
	/**
	 * Add the BusinessAdditionalDetails.
	 *
	 * @param tenantId - the tenant id
	 * @param BusinessAdditionalDetailsAddResource - the business additional details add resource
	 * @return the response entity
	 */
	
	@PostMapping("/{tenantId}")
	public ResponseEntity<Object> addBusinessAdditionalDetails(@PathVariable(value = "tenantId") String tenantId,
			   									       		  @Valid @RequestBody BusinessAdditionalDetailsAddResource businessAdditionalDetailsAddResource){
		BusinessAdditionalDetails businessAdditionalDetails = businessAdditionalDetailsService.save(tenantId, businessAdditionalDetailsAddResource);
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource(getEnvironment().getProperty(RECORD_CREATED), Long.toString(businessAdditionalDetails.getId()));
		return new ResponseEntity<>(responseMessage,HttpStatus.CREATED);
	}
	
	/**
	 * Update BusinessAdditionalDetails.
	 *
	 * @param tenantId - the tenant id
	 * @param id - the id
	 * @param BusinessAdditionalDetailsUpdateResource - the  business additional details update resource
	 * @return the response entity
	 */
	
	@PutMapping(value = "{tenantId}/{id}")
	public ResponseEntity<Object> updateBusinessAdditionalDetails(@PathVariable(value = "tenantId") String tenantId, 
												                 @PathVariable(value = "id") Long id, 
												                 @Valid @RequestBody BusinessAdditionalDetailsUpdateResource businessAdditionalDetailsUpdateResource){
		SuccessAndErrorDetailsResource successAndErrorDetailsResource=new SuccessAndErrorDetailsResource();
		Optional<BusinessAdditionalDetails> isPresentBusinessAdditionalDetails = businessAdditionalDetailsService.findById(tenantId, id);		
		if(isPresentBusinessAdditionalDetails.isPresent()) {
			BusinessAdditionalDetails businessAdditionalDetails = businessAdditionalDetailsService.update(tenantId, id, businessAdditionalDetailsUpdateResource);
			successAndErrorDetailsResource = new SuccessAndErrorDetailsResource(getEnvironment().getProperty(RECORD_UPDATED), businessAdditionalDetails.getId().toString());
			return new ResponseEntity<>(successAndErrorDetailsResource,HttpStatus.OK);
		}
		else {
			successAndErrorDetailsResource.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
	
}
