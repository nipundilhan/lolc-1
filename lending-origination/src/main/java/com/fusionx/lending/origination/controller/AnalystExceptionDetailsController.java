package com.fusionx.lending.origination.controller;

/**
 * 	Analyst Exception Details Controller
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   26-08-2021   FXL-117  	 FXL-543       Piyumi     Created
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
import com.fusionx.lending.origination.domain.AnalystDetails;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.resource.AnalystAddResource;
import com.fusionx.lending.origination.resource.AnalystUpdateResource;
import com.fusionx.lending.origination.resource.SuccessAndErrorDetailsResource;
import com.fusionx.lending.origination.service.AnalystExceptionDetailsService;


@RestController
@RequestMapping(value = "/analyst-exception")
@CrossOrigin(origins = "*")
public class AnalystExceptionDetailsController extends MessagePropertyBase{
	
	
	private AnalystExceptionDetailsService analystExceptionDetailsService;
	
	@Autowired
	public void setAnalystExceptionDetailsService(AnalystExceptionDetailsService analystExceptionDetailsService) {
		this.analystExceptionDetailsService = analystExceptionDetailsService;
	}
	
	@GetMapping("/{tenantId}/all")
	public ResponseEntity<Object> getAllAnalystExceptionDetails(@PathVariable(value = "tenantId") String tenantId){
		List<AnalystDetails> analystDetails = analystExceptionDetailsService.findAll(tenantId);
		
		if(!analystDetails.isEmpty()) {
			return new ResponseEntity<>(analystDetails, HttpStatus.OK);
		}
		else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Get the Analyst Details by id.
	 *
	 * @param tenantId - the tenant id
	 * @param id - the id
	 * @return the AnalystDetails by id
	 */
	@GetMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> getAnalystDetailsById(@PathVariable(value = "tenantId") String tenantId,
													         	  @PathVariable(value = "id") Long id){
			
		Optional<AnalystDetails> isPresentAnalystDetail = analystExceptionDetailsService.findById(tenantId,id);
		if(isPresentAnalystDetail.isPresent()) {
			return new ResponseEntity<>(isPresentAnalystDetail.get(), HttpStatus.OK);
		}
		else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Get the Analyst Details by status.
	 *
	 * @param tenantId - the tenant id
	 * @param status - the status
	 * @return the Analyst Details by status
	 */
	
	@GetMapping(value = "/{tenantId}/status/{status}")
	public ResponseEntity<Object> getAnalystDetailsByStatus(@PathVariable(value = "tenantId") String tenantId,
			@PathVariable(value = "status") String status) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		if(status.equals(CommonStatus.ACTIVE.toString()) || status.equals(CommonStatus.INACTIVE.toString())) {
			
			List<AnalystDetails> analystDetails = analystExceptionDetailsService.findByStatus(tenantId,status);
			if(!analystDetails.isEmpty()) {
				return new ResponseEntity<>(analystDetails, HttpStatus.OK);
			}else {
				responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
				return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
			}
		}else {
				responseMessage.setMessages(RECORD_NOT_FOUND);
		        throw new ValidateRecordException(environment.getProperty(COMMON_STATUS_PATTERN), "message");
		}
	}
	
	/**
	 * Add the AnalystExceptionDetails.
	 *
	 * @param tenantId - the tenant id
	 * @param AnalystAddResource - the analyst add resource
	 * @return the response entity
	 */
	
	@PostMapping("/{tenantId}")
	public ResponseEntity<Object> addAnalystExceptionDetails(@PathVariable(value = "tenantId") String tenantId,
			   									       		  @Valid @RequestBody AnalystAddResource analystAddResource){
		AnalystDetails analystDetail = analystExceptionDetailsService.add(tenantId, analystAddResource);
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource(getEnvironment().getProperty(RECORD_CREATED), analystDetail.getId().toString());
		return new ResponseEntity<>(responseMessage,HttpStatus.CREATED);
	}
	
	/**
	 * Update AnalystExceptionDetails.
	 *
	 * @param tenantId - the tenant id
	 * @param id - the id
	 * @param AnalystUpdateResource - the analyst update resource
	 * @return the response entity
	 */
	
	@PutMapping(value = "{tenantId}/{id}")
	public ResponseEntity<Object> updateAnalystExceptionDetails(@PathVariable(value = "tenantId") String tenantId, 
												                 @PathVariable(value = "id") Long id, 
												                 @Valid @RequestBody AnalystUpdateResource analystUpdateResource){
		SuccessAndErrorDetailsResource successAndErrorDetailsResource=new SuccessAndErrorDetailsResource();
		Optional<AnalystDetails> isPresentAnalystDetails = analystExceptionDetailsService.findById(tenantId,id);		
		if(isPresentAnalystDetails.isPresent()) {
			AnalystDetails analystDetail = analystExceptionDetailsService.update(tenantId, id, analystUpdateResource);
			successAndErrorDetailsResource = new SuccessAndErrorDetailsResource(getEnvironment().getProperty(RECORD_UPDATED), analystDetail.getId().toString());
			return new ResponseEntity<>(successAndErrorDetailsResource,HttpStatus.OK);
		}
		else {
			successAndErrorDetailsResource.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
	
	
	/**
	 * send To Approval AnalystExceptionDetails.
	 *
	 * @param tenantId - the tenant id
	 * @param id - the id
	 * @return the response entity
	 */
	
	@PutMapping(value = "{tenantId}/send-to-approval/{id}")
	public ResponseEntity<Object> sendToApproval(@PathVariable(value = "tenantId") String tenantId, 
												                 @PathVariable(value = "id") Long id){
		SuccessAndErrorDetailsResource successAndErrorDetailsResource=new SuccessAndErrorDetailsResource();
		Optional<AnalystDetails> isPresentAnalystDetails = analystExceptionDetailsService.findById(tenantId,id);		
		if(isPresentAnalystDetails.isPresent()) {
			Long analystDetailId = analystExceptionDetailsService.sendToApproval(tenantId, id);
			successAndErrorDetailsResource = new SuccessAndErrorDetailsResource(getEnvironment().getProperty(RECORD_UPDATED), analystDetailId.toString());
			return new ResponseEntity<>(successAndErrorDetailsResource,HttpStatus.OK);
		}
		else {
			successAndErrorDetailsResource.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
	
	/**
	 * Update AnalystExceptionDetails.
	 *
	 * @param tenantId - the tenant id
	 * @param id - the id
	 * @param processId - the processId
	 * @return the response entity
	 */
	
	@PutMapping(value = "{tenantId}/approve/{id}/workflow/{processId}")
	public ResponseEntity<Object> approveAnalystExceptionWorkFlow(@PathVariable(value = "tenantId") String tenantId, 
												                 @PathVariable(value = "id") Long id, 
												                 @PathVariable(value = "processId") String processId){
		
			Long analystDetailId = analystExceptionDetailsService.approveAnalystExceptionWorkFlow(tenantId, id, processId);
			SuccessAndErrorDetailsResource successAndErrorDetailsResource = new SuccessAndErrorDetailsResource(getEnvironment().getProperty(RECORD_UPDATED), analystDetailId.toString());
			return new ResponseEntity<>(successAndErrorDetailsResource,HttpStatus.OK);
	}
	
	/**
	 *  reject AnalystExceptionDetails.
	 *
	 * @param tenantId - the tenant id
	 * @param id - the id
	 * @param processId - the processId
	 * @return the response entity
	 */
	
	@PutMapping(value = "{tenantId}/reject/{id}/workflow/{processId}")
	public ResponseEntity<Object> rejectAnalystExceptionWorkFlow(@PathVariable(value = "tenantId") String tenantId, 
												                 @PathVariable(value = "id") Long id, 
												                 @PathVariable(value = "processId") String processId){
		
			Long analystDetailId = analystExceptionDetailsService.rejectedAnalystExceptionWorkFlow(tenantId, id, processId);
			SuccessAndErrorDetailsResource successAndErrorDetailsResource = new SuccessAndErrorDetailsResource(getEnvironment().getProperty(RECORD_UPDATED), analystDetailId.toString());
			return new ResponseEntity<>(successAndErrorDetailsResource,HttpStatus.OK);
	}
	
	

}
