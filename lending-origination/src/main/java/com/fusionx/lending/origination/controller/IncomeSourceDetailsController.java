package com.fusionx.lending.origination.controller;

/**
 * 	Income Source Details Controller
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   31-08-2021   FXL-115  	 FXL-656       Piyumi       Created
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
import com.fusionx.lending.origination.domain.IncomeSourceDetails;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.resource.IncomeSourceDetailsAddResource;
import com.fusionx.lending.origination.resource.IncomeSourceDetailsUpdateResource;
import com.fusionx.lending.origination.resource.SuccessAndErrorDetailsResource;
import com.fusionx.lending.origination.service.IncomeSourceDetailsService;


@RestController
@RequestMapping(value = "/income-source-details")
@CrossOrigin(origins = "*")
public class IncomeSourceDetailsController extends MessagePropertyBase{
	
	
	private IncomeSourceDetailsService incomeSourceDetailsService;
	
	@Autowired
	public void setIncomeSourceDetailsService(IncomeSourceDetailsService incomeSourceDetailsService) {
		this.incomeSourceDetailsService = incomeSourceDetailsService;
	}
	
	@GetMapping("/{tenantId}/all")
	public ResponseEntity<Object> getAllIncomeSourceDetails(@PathVariable(value = "tenantId") String tenantId){
		List<IncomeSourceDetails> incomeSourceDetails = incomeSourceDetailsService.findAll();
		if(!incomeSourceDetails.isEmpty()) {
			return new ResponseEntity<>(incomeSourceDetails, HttpStatus.OK);
		}
		else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Get the IncomeSourceDetails by id.
	 *
	 * @param tenantId - the tenant id
	 * @param id - the id
	 * @return the IncomeSourceDetails by id
	 */
	@GetMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> getIncomeSourceDetailsById(@PathVariable(value = "tenantId" ) String tenantId,
													         	  @PathVariable(value = "id") Long id){
			
		Optional<IncomeSourceDetails> isPresentIncomeSourceDetails = incomeSourceDetailsService.findById(id);
		if(isPresentIncomeSourceDetails.isPresent()) {
			return new ResponseEntity<>(isPresentIncomeSourceDetails.get(), HttpStatus.OK);
		}
		else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Get the IncomeSourceDetailss by status.
	 *
	 * @param tenantId - the tenant id
	 * @param status - the status
	 * @return the IncomeSourceDetails by status
	 */
	
	@GetMapping(value = "/{tenantId}/status/{status}")
	public ResponseEntity<Object> getIncomeSourceDetailsByStatus(@PathVariable(value = "tenantId") String tenantId,
			@PathVariable(value = "status") String status) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		if(status.equals(CommonStatus.ACTIVE.toString()) || status.equals(CommonStatus.INACTIVE.toString())) {
		
			List<IncomeSourceDetails> incomeSourceDetails = incomeSourceDetailsService.findByStatus(status);
			if(!incomeSourceDetails.isEmpty()) {
				return new ResponseEntity<>(incomeSourceDetails, HttpStatus.OK);
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
	 * Add the IncomeSourceDetails.
	 *
	 * @param tenantId - the tenant id
	 * @param IncomeSourceDetailsAddResource - the income source details add resource
	 * @return the response entity
	 */
	
	@PostMapping("/{tenantId}")
	public ResponseEntity<Object> addIncomeSourceDetails(@PathVariable(value = "tenantId") String tenantId,
			   									       		  @Valid @RequestBody IncomeSourceDetailsAddResource incomeSourceDetailsAddResource){
		IncomeSourceDetails incomeSourceDetails = incomeSourceDetailsService.save(tenantId, incomeSourceDetailsAddResource);
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource(getEnvironment().getProperty(RECORD_CREATED), Long.toString(incomeSourceDetails.getId()));
		return new ResponseEntity<>(responseMessage,HttpStatus.CREATED);
	}
	
	/**
	 * Update IncomeSourceDetails.
	 *
	 * @param tenantId - the tenant id
	 * @param id - the id
	 * @param IncomeSourceDetailsUpdateResource - the income source details update resource
	 * @return the response entity
	 */
	
	@PutMapping(value = "{tenantId}/{id}")
	public ResponseEntity<Object> updateIncomeSourceDetails(@PathVariable(value = "tenantId") String tenantId, 
												                 @PathVariable(value = "id") Long id, 
												                 @Valid @RequestBody IncomeSourceDetailsUpdateResource incomeSourceDetailsUpdateResource){
		SuccessAndErrorDetailsResource successAndErrorDetailsResource=new SuccessAndErrorDetailsResource();
		Optional<IncomeSourceDetails> isPresentIncomeSourceDetails = incomeSourceDetailsService.findById(id);		
		if(isPresentIncomeSourceDetails.isPresent()) {
			IncomeSourceDetails incomeSourceDetails = incomeSourceDetailsService.update(tenantId, id, incomeSourceDetailsUpdateResource);
			successAndErrorDetailsResource = new SuccessAndErrorDetailsResource(getEnvironment().getProperty(RECORD_UPDATED), incomeSourceDetails.getId().toString());
			return new ResponseEntity<>(successAndErrorDetailsResource,HttpStatus.OK);
		}
		else {
			successAndErrorDetailsResource.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
	
	/**
	 * Get the IncomeSourceDetails by leadId.
	 *
	 * @param tenantId - the tenant id
	 * @param leadId - the leadId
	 * @return the IncomeSourceDetails by leadId
	 */
	
	@GetMapping(value = "/{tenantId}/lead/{leadId}")
	public ResponseEntity<Object> getIncomeSourceDetailsByLeadId(@PathVariable(value = "tenantId") String tenantId,
			@PathVariable(value = "leadId") Long leadId) {

		List<IncomeSourceDetails> incomeSourceDetails = incomeSourceDetailsService.findByLeadId(leadId);
		if(!incomeSourceDetails.isEmpty()) {
			return new ResponseEntity<>(incomeSourceDetails, HttpStatus.OK);
		}
		else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Get the IncomeSourceDetails by customerId.
	 *
	 * @param tenantId - the tenant id
	 * @param customerId - the customerId
	 * @return the IncomeSourceDetails by customerId
	 */
	
	@GetMapping(value = "/{tenantId}/customer/{customerId}")
	public ResponseEntity<Object> getIncomeSourceDetailsByCustomerId(@PathVariable(value = "tenantId") String tenantId,
			@PathVariable(value = "customerId") Long customerId) {

		List<IncomeSourceDetails> incomeSourceDetails = incomeSourceDetailsService.findByCustomerId(customerId);
		if(!incomeSourceDetails.isEmpty()) {
			return new ResponseEntity<>(incomeSourceDetails, HttpStatus.OK);
		}
		else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Get the IncomeSourceDetails by linkedPersonId.
	 *
	 * @param tenantId - the tenant id
	 * @param linkedPersonId - the linkedPersonId
	 * @return the IncomeSourceDetails by linkedPersonId
	 */
	
	@GetMapping(value = "/{tenantId}/linkedPerson/{linkedPersonId}")
	public ResponseEntity<Object> getIncomeSourceDetailsByLinkedPersonId(@PathVariable(value = "tenantId") String tenantId,
			@PathVariable(value = "linkedPersonId") Long linkedPersonId) {

		List<IncomeSourceDetails> incomeSourceDetails = incomeSourceDetailsService.findByLinkedPersonId(linkedPersonId);
		if(!incomeSourceDetails.isEmpty()) {
			return new ResponseEntity<>(incomeSourceDetails, HttpStatus.OK);
		}
		else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	

}
