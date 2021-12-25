package com.fusionx.lending.origination.controller;

/**
 * 	Business Tax Details Controller 
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   15-09-2021   FXL-115  	 FXL-785       Piyumi       Created
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
import com.fusionx.lending.origination.domain.BusinessTaxDetails;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.resource.BusinessTaxDetailsAddResource;
import com.fusionx.lending.origination.resource.BusinessTaxTypeResource;
import com.fusionx.lending.origination.resource.SuccessAndErrorDetailsResource;
import com.fusionx.lending.origination.service.BusinessTaxDetailsService;


@RestController
@RequestMapping(value = "/business-tax-details")
@CrossOrigin(origins = "*")
public class BusinessTaxDetailsController extends MessagePropertyBase{
	
	private BusinessTaxDetailsService businessTaxDetailsService;
	
	@Autowired
	public void setBusinessTaxDetailsService(BusinessTaxDetailsService businessTaxDetailsService) {
		this.businessTaxDetailsService = businessTaxDetailsService;
	}
	
	@GetMapping("/{tenantId}/all")
	public ResponseEntity<Object> getAllBusinessTaxDetails(@PathVariable(value = "tenantId") String tenantId){
		List<BusinessTaxDetails> businessTaxDetails = businessTaxDetailsService.findAll();
		if(!businessTaxDetails.isEmpty()) {
			return new ResponseEntity<>(businessTaxDetails, HttpStatus.OK);
		}
		else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Get the BusinessTaxDetails by id.
	 *
	 * @param tenantId - the tenant id
	 * @param id - the id
	 * @return the BusinessTaxDetails by id
	 */
	@GetMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> getBusinessTaxDetailsById(@PathVariable(value = "tenantId" ) String tenantId,
													         	  @PathVariable(value = "id") Long id){
			
		Optional<BusinessTaxDetails> isPresentBusinessTaxDetails = businessTaxDetailsService.findById(id);
		if(isPresentBusinessTaxDetails.isPresent()) {
			return new ResponseEntity<>(isPresentBusinessTaxDetails.get(), HttpStatus.OK);
		}
		else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Get the BusinessTaxDetailss by status.
	 *
	 * @param tenantId - the tenant id
	 * @param status - the status
	 * @return the BusinessTaxDetails by status
	 */
	
	@GetMapping(value = "/{tenantId}/status/{status}")
	public ResponseEntity<Object> getBusinessTaxDetailsByStatus(@PathVariable(value = "tenantId") String tenantId,
			@PathVariable(value = "status") String status) {

		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		if(status.equals(CommonStatus.ACTIVE.toString()) || status.equals(CommonStatus.INACTIVE.toString())) {
			List<BusinessTaxDetails> businessTaxDetails = businessTaxDetailsService.findByStatus(status);
			if(!businessTaxDetails.isEmpty()) {
				return new ResponseEntity<>(businessTaxDetails, HttpStatus.OK);
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
	 * Add the BusinessTaxDetails.
	 *
	 * @param tenantId - the tenant id
	 * @param BusinessTaxDetailsAddResource - the business tax details add resource
	 * @return the response entity
	 */
	
	@PostMapping("/{tenantId}")
	public ResponseEntity<Object> addBusinessTaxDetails(@PathVariable(value = "tenantId") String tenantId,
			   									       		  @Valid @RequestBody BusinessTaxDetailsAddResource businessTaxDetailsAddResource){
		
		businessTaxDetailsService.save(tenantId, businessTaxDetailsAddResource);
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource(getEnvironment().getProperty(RECORD_CREATED));
		return new ResponseEntity<>(responseMessage,HttpStatus.CREATED);
	}
	
	/**
	 * Update BusinessTaxDetails.
	 *
	 * @param tenantId - the tenant id
	 * @param id - the id
	 * @param BusinessTaxTypeResource - the  business tax type resource
	 * @return the response entity
	 */
	
	@PutMapping(value = "{tenantId}/{id}")
	public ResponseEntity<Object> updateBusinessTaxDetails(@PathVariable(value = "tenantId") String tenantId, 
												                 @PathVariable(value = "id") Long id, 
												                 @Valid @RequestBody BusinessTaxTypeResource businessTaxTypeResource){
		
		SuccessAndErrorDetailsResource successAndErrorExpensesResource=new SuccessAndErrorDetailsResource();
		Optional<BusinessTaxDetails> isPresentBusinessTaxDetails = businessTaxDetailsService.findById(id);	
		
		if(isPresentBusinessTaxDetails.isPresent()) {
			BusinessTaxDetails businessTaxDetails = businessTaxDetailsService.update(tenantId, id, businessTaxTypeResource);
			
			successAndErrorExpensesResource = new SuccessAndErrorDetailsResource(getEnvironment().getProperty(RECORD_UPDATED), businessTaxDetails.getId().toString());
			return new ResponseEntity<>(successAndErrorExpensesResource,HttpStatus.OK);
		}
		else {
			successAndErrorExpensesResource.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(successAndErrorExpensesResource, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
	
	/**
	 * Get the BusinessTaxDetails by businessIncomeDetailsId.
	 *
	 * @param tenantId - the tenant id
	 * @param businessIncomeDetailsId - the business income details id
	 * @return the BusinessTaxDetails by businessIncomeDetailsId
	 */
	@GetMapping(value = "/{tenantId}/business-income-details/{businessIncomeDetailsId}")
	public ResponseEntity<Object> getByBusinessIncomeDetailId(@PathVariable(value = "tenantId" ) String tenantId,
													         	  @PathVariable(value = "businessIncomeDetailsId") Long businessIncomeDetailsId){
			
		List<BusinessTaxDetails> businessTaxDetails = businessTaxDetailsService.findByBusinessIncomeDetailsId(businessIncomeDetailsId);
		if(!businessTaxDetails.isEmpty()) {
			return new ResponseEntity<>(businessTaxDetails, HttpStatus.OK);
		}
		else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
}
