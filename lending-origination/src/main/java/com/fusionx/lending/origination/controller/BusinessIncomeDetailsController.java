package com.fusionx.lending.origination.controller;

/**
 * 	Business Income Details Controller 
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   14-09-2021   FXL-115  	 FXL-816       Piyumi       Created
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
import com.fusionx.lending.origination.domain.BusinessIncomeDetails;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.resource.BusinessIncomeDetailsAddResource;
import com.fusionx.lending.origination.resource.BusinessIncomeDetailsUpdateResource;
import com.fusionx.lending.origination.resource.SuccessAndErrorDetailsResource;
import com.fusionx.lending.origination.service.BusinessIncomeDetailsService;


@RestController
@RequestMapping(value = "/business-income-details")
@CrossOrigin(origins = "*")
public class BusinessIncomeDetailsController extends MessagePropertyBase{
	
	
	private BusinessIncomeDetailsService businessIncomeDetailsService;
	
	@Autowired
	public void setBusinessIncomeDetailsService(BusinessIncomeDetailsService businessIncomeDetailsService) {
		this.businessIncomeDetailsService = businessIncomeDetailsService;
	}
	
	@GetMapping("/{tenantId}/all")
	public ResponseEntity<Object> getAllBusinessIncomeDetails(@PathVariable(value = "tenantId") String tenantId){
		List<BusinessIncomeDetails> businessIncomeDetails = businessIncomeDetailsService.findAll(tenantId);
		if(!businessIncomeDetails.isEmpty()) {
			return new ResponseEntity<>(businessIncomeDetails, HttpStatus.OK);
		}
		else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Get the BusinessIncomeDetails by id.
	 *
	 * @param tenantId - the tenant id
	 * @param id - the id
	 * @return the BusinessIncomeDetails by id
	 */
	@GetMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> getBusinessIncomeDetailsById(@PathVariable(value = "tenantId" ) String tenantId,
													         	  @PathVariable(value = "id") Long id){
			
		Optional<BusinessIncomeDetails> isPresentBusinessIncomeDetails = businessIncomeDetailsService.findById(tenantId,id);
		if(isPresentBusinessIncomeDetails.isPresent()) {
			return new ResponseEntity<>(isPresentBusinessIncomeDetails.get(), HttpStatus.OK);
		}
		else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Get the BusinessIncomeDetailss by status.
	 *
	 * @param tenantId - the tenant id
	 * @param status - the status
	 * @return the BusinessIncomeDetails by status
	 */
	
	@GetMapping(value = "/{tenantId}/status/{status}")
	public ResponseEntity<Object> getBusinessIncomeDetailsByStatus(@PathVariable(value = "tenantId") String tenantId,
			@PathVariable(value = "status") String status) {

		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		if(status.equals(CommonStatus.ACTIVE.toString()) || status.equals(CommonStatus.INACTIVE.toString())) {
			List<BusinessIncomeDetails> businessIncomeDetails = businessIncomeDetailsService.findByStatus(tenantId,status);
			if(!businessIncomeDetails.isEmpty()) {
				return new ResponseEntity<>(businessIncomeDetails, HttpStatus.OK);
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
	 * Add the BusinessIncomeDetails.
	 *
	 * @param tenantId - the tenant id
	 * @param BusinessIncomeDetailsAddResource - the business income details add resource
	 * @return the response entity
	 */
	
	@PostMapping("/{tenantId}")
	public ResponseEntity<Object> addBusinessIncomeDetails(@PathVariable(value = "tenantId") String tenantId,
			   									       		  @Valid @RequestBody BusinessIncomeDetailsAddResource businessIncomeDetailsAddResource){
		BusinessIncomeDetails businessIncomeDetails = businessIncomeDetailsService.save(tenantId, businessIncomeDetailsAddResource);
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource(getEnvironment().getProperty(RECORD_CREATED), Long.toString(businessIncomeDetails.getId()));
		return new ResponseEntity<>(responseMessage,HttpStatus.CREATED);
	}
	
	/**
	 * Update BusinessIncomeDetails.
	 *
	 * @param tenantId - the tenant id
	 * @param id - the id
	 * @param BusinessIncomeDetailsUpdateResource - the  business income details update resource
	 * @return the response entity
	 */
	
	@PutMapping(value = "{tenantId}/{id}")
	public ResponseEntity<Object> updateBusinessIncomeDetails(@PathVariable(value = "tenantId") String tenantId, 
												                 @PathVariable(value = "id") Long id, 
												                 @Valid @RequestBody BusinessIncomeDetailsUpdateResource businessIncomeDetailsUpdateResource){
		SuccessAndErrorDetailsResource successAndErrorDetailsResource=new SuccessAndErrorDetailsResource();
		Optional<BusinessIncomeDetails> isPresentBusinessIncomeDetails = businessIncomeDetailsService.findById(tenantId, id);		
		if(isPresentBusinessIncomeDetails.isPresent()) {
			BusinessIncomeDetails businessIncomeDetails = businessIncomeDetailsService.update(tenantId, id, businessIncomeDetailsUpdateResource);
			successAndErrorDetailsResource = new SuccessAndErrorDetailsResource(getEnvironment().getProperty(RECORD_UPDATED), businessIncomeDetails.getId().toString());
			return new ResponseEntity<>(successAndErrorDetailsResource,HttpStatus.OK);
		}
		else {
			successAndErrorDetailsResource.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
	
	/**
	 * Get the BusinessIncomeDetails by IncomeSourceDetailsId.
	 *
	 * @param tenantId - the tenant id
	 * @param incomeSourceDetailsId - the incomeSourceDetailsId
	 * @return the BusinessIncomeDetails by incomeSourceDetailsId
	 */
	@GetMapping(value = "/{tenantId}/income-source-details/{incomeSourceDetailsId}")
	public ResponseEntity<Object> getByIncomeSourceDetailsId(@PathVariable(value = "tenantId" ) String tenantId,
													         	  @PathVariable(value = "incomeSourceDetailsId") Long incomeSourceDetailsId){
			
		List<BusinessIncomeDetails> businessIncomeDetails = businessIncomeDetailsService.findByIncomeSourceDetailsId(tenantId,incomeSourceDetailsId);
		if(!businessIncomeDetails.isEmpty()) {
			return new ResponseEntity<>(businessIncomeDetails, HttpStatus.OK);
		}
		else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
}
