package com.fusionx.lending.product.controller;

/**
 * Repayment Controller
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   	Task No    	Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   05-07-2021     FX-6620 		FX-6803     RavishikaS      Created
 *    
 ********************************************************************************************************
 */


import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fusionx.lending.product.Constants;
import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.FeatureBenefitItem;
import com.fusionx.lending.product.domain.FeeChargePending;
import com.fusionx.lending.product.domain.Repayment;
import com.fusionx.lending.product.domain.RepaymentPending;
import com.fusionx.lending.product.enums.CommonApproveStatus;
import com.fusionx.lending.product.enums.WorkflowStatus;
import com.fusionx.lending.product.exception.PageableLengthException;
import com.fusionx.lending.product.exception.UserNotFound;
import com.fusionx.lending.product.resources.RepaymentAddResource;
import com.fusionx.lending.product.resources.RepaymentUpdateResource;
import com.fusionx.lending.product.resources.SuccessAndErrorDetails;
import com.fusionx.lending.product.resources.SuccessAndErrorDetailsResource;
import com.fusionx.lending.product.service.RepaymentService;

@RestController
@RequestMapping(value = "/repayment")
@CrossOrigin(origins = "*")
public class RepaymentController extends MessagePropertyBase {
	
	@Autowired
	Environment environment;
	
	@Autowired
	public RepaymentService repaymentService;
	
	private String pageableLength = "common.pageable-length";
	
	
	/**
	 * get all Repayment 
	 * @param @PathVariable{tenantId}
	 * @param @PathVariable{all}
	 * @return List<Type>
	 */
    @GetMapping(value = "/{tenantId}/all")
    public ResponseEntity<Object> getAllRepayment(@PathVariable(value = "tenantId", required = true) String tenantId) {
    	SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
		List<Repayment> repayment = repaymentService.getAll();
		int size = repayment.size();
		if (size > 0) {
			return new ResponseEntity<>((Collection<Repayment>) repayment,HttpStatus.OK);
		} 
		else {
			responseMessage.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
    
    /**
     * get Repayment  by id
     * @param @PathVariable{tenantId}
     * @param @PathVariable{id}
     * @return Option
     */
 	@GetMapping(value = "/{tenantId}/{id}")
 	public ResponseEntity<Object> getRepaymentById(@PathVariable(value = "tenantId", required = true) String tenantId, 
 			                                              @PathVariable(value = "id", required = true) Long id) {
 		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
	 	Optional<Repayment> isPresentRepayment = repaymentService.getById(id);
	 	if (isPresentRepayment.isPresent()) {
	 		return new ResponseEntity<>(isPresentRepayment.get(), HttpStatus.OK);
	 	} 
	 	else {
	 		responseMessage.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
	 	}
 	}
 	
	/**
	 * get Repayment  by code
	 * @param @pathVariable{tenantId}
	 * @param @pathVariable {code}
	 * @return Optional
	 **/
	@GetMapping(value = "/{tenantId}/code/{code}")
	public ResponseEntity<Object> getRepaymentByCode(@PathVariable(value = "tenantId", required = true) String tenantId,
													   	            @PathVariable(value = "code", required = true) String code){
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<Repayment> isPresentRepayment = repaymentService.getByCode(code);
		if(isPresentRepayment.isPresent()) {
			return new ResponseEntity<>(isPresentRepayment.get(), HttpStatus.OK);
		}
		else {
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
 	
 	/**
     * get Repayment  by status
     * @param @PathVariable{tenantId}
     * @param @PathVariable{status}
     * @return Option
     */
 	@GetMapping(value = "/{tenantId}/status/{status}")
 	public ResponseEntity<Object> getRepaymentByStatus(@PathVariable(value = "tenantId", required = true) String tenantId, 
 			                                                  @PathVariable(value = "status", required = true) String status) {
 		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
 		
 		if(status.equals("ACTIVE") || status.equals("INACTIVE")){
		 	List<Repayment> isPresentRepayment = repaymentService.getByStatus(status);
		 	int size = isPresentRepayment.size();
		 	if (size>0) {
		 		return new ResponseEntity<>(isPresentRepayment, HttpStatus.OK);
		 	} 
		 	else {
		 		responseMessage.setMessages(RECORD_NOT_FOUND);
				return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		 	}
 		}
 		else{
	 		responseMessage.setMessages(environment.getProperty(INVALID_STATUS));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
	 	}
 	}
 	
 	/**
	 * get Repayment by name
	 * @param @pathVariable{tenantId}
	 * @param @pathVariable {name}
	 * @return Optional
	 **/
	@GetMapping(value = "/{tenantId}/name/{name}")
	public ResponseEntity<Object> getRepaymentByName(@PathVariable(value = "tenantId", required = true) String tenantId,
													   @PathVariable(value = "name", required = true) String name){
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<Repayment> isPresentRepayment = repaymentService.getByName(name);
		if(!isPresentRepayment.isEmpty()) {
			return new ResponseEntity<>(isPresentRepayment, HttpStatus.OK);
		}
		else {
			responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
 	
 	/**
     * save Repayment 
     * @param @PathVariable{tenantId}
     * @param @RequestBody{repaymentAddResource}
     * @return SuccessAndErrorDetailsDto
     */
 	@PostMapping(value = "/{tenantId}")
 	public ResponseEntity<Object> addRepayment(@PathVariable(value = "tenantId", required = true) String tenantId,
 			                                          @Valid @RequestBody RepaymentAddResource repaymentAddResource) {
 		if(LogginAuthentcation.getInstance().getUserName()==null || LogginAuthentcation.getInstance().getUserName().isEmpty()) { 
 			throw new UserNotFound(environment.getProperty(COMMON_USER_NOT_FOUND));
 		}
		
 		SuccessAndErrorDetails successAndErrorDetails = new SuccessAndErrorDetails();
 		Repayment repaymentType = repaymentService.addRepayment(tenantId, repaymentAddResource);
	 	successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty(RECORD_CREATED),Long.toString(repaymentType.getId()));
		return new ResponseEntity<>(successAndErrorDetails,HttpStatus.CREATED);
 	}
 	
	/**
     * update Repayment  
     * @param @PathVariable{tenantId}
     * @param @RequestBody{RepaymentUpdateResource}
     * @return SuccessAndErrorDetailsDto
     */
	@PutMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> updateRepayment(@PathVariable(value = "tenantId", required = true) String tenantId, 
			                                             @PathVariable(value = "id", required = true) Long id, 
			                                             @Valid @RequestBody RepaymentUpdateResource repaymentUpdateResource) {
		if(LogginAuthentcation.getInstance().getUserName()==null || LogginAuthentcation.getInstance().getUserName().isEmpty()) { 
 			throw new UserNotFound(environment.getProperty(COMMON_USER_NOT_FOUND));
 		}
		
		SuccessAndErrorDetails successAndErrorDetails=new SuccessAndErrorDetails();
		Optional<Repayment> isPresentRepayment = repaymentService.getById(id);
		if(isPresentRepayment.isPresent()) {
			if(isPresentRepayment.get().getApproveStatus() == null || !isPresentRepayment.get().getApproveStatus().equals(CommonApproveStatus.PENDING))
			{
				Repayment repayment = repaymentService.updateRepayment(tenantId, id, repaymentUpdateResource);
				successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty(RECORD_UPDATED), repayment.getId().toString());
	        	return new ResponseEntity<>(successAndErrorDetails,HttpStatus.OK);
			} else {
				successAndErrorDetails.setMessages(environment.getProperty("common.can-not-update"));
				return new ResponseEntity<>(successAndErrorDetails, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			
		}
		else {
			successAndErrorDetails.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(successAndErrorDetails, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
	
	/**
	* approve Repayment  Pending
	* @param @PathVariable{tenantId}
	* @param @PathVariable{pendingId}
	* @return SuccessAndErrorDetailsDto
	*/
	@PutMapping(value = "{tenantId}/approve/{pendingId}")
	public ResponseEntity<Object> approve(@PathVariable(value = "tenantId", required = true) String tenantId, @PathVariable(value = "pendingId", required = true) Long pendingId){
		SuccessAndErrorDetailsResource successAndErrorDetailsResource=new SuccessAndErrorDetailsResource();
		Optional<RepaymentPending> isPresentEligiTempPending = repaymentService.getByPendingId(pendingId);
		if(isPresentEligiTempPending.isPresent()) {
	
			if(repaymentService.approveReject(tenantId, pendingId, WorkflowStatus.COMPLETE)) {
			successAndErrorDetailsResource = new SuccessAndErrorDetailsResource(environment.getProperty("common.approved"), pendingId.toString());
			return new ResponseEntity<>(successAndErrorDetailsResource,HttpStatus.OK);
			} else {
				successAndErrorDetailsResource.setMessages(environment.getProperty("common.can-not-approved"));
				return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.UNPROCESSABLE_ENTITY);
			}

		} else {
			successAndErrorDetailsResource.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
	
	/**
	* Reject Repayment  Pending
	* @param @PathVariable{tenantId}
	* @param @PathVariable{pendingId}
	* @return SuccessAndErrorDetailsDto
	*/
	@PutMapping(value = "{tenantId}/reject/{pendingId}")
	public ResponseEntity<Object> reject(@PathVariable(value = "tenantId", required = true) String tenantId, @PathVariable(value = "pendingId", required = true) Long pendingId){
		SuccessAndErrorDetailsResource successAndErrorDetailsResource=new SuccessAndErrorDetailsResource();
		Optional<RepaymentPending> isPresentEligiPending = repaymentService.getByPendingId(pendingId);
		if(isPresentEligiPending.isPresent()) {
	
			if(repaymentService.approveReject(tenantId, pendingId, WorkflowStatus.REJECT)) {
			successAndErrorDetailsResource = new SuccessAndErrorDetailsResource(environment.getProperty("common.rejected"), pendingId.toString());
			return new ResponseEntity<>(successAndErrorDetailsResource,HttpStatus.OK);
			} else {
				successAndErrorDetailsResource.setMessages(environment.getProperty("common.can-not-rejected"));
				return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.UNPROCESSABLE_ENTITY);
			}

		} else {
			successAndErrorDetailsResource.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
	
	/**
	 * Gets the Repayment by pending id.
	 *
	 * @param tenantId - the tenant id
	 * @param pendingId - the pending id
	 * @return  RepaymentPending
	 */
	@GetMapping(value = "/{tenantId}/pending/{pendingId}")
	public ResponseEntity<Object> getFeeChargePendingByPendingId(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "pendingId", required = true) Long pendingId) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<RepaymentPending> isPresentRepaymentPending = repaymentService.getByPendingId(pendingId);
		if (isPresentRepaymentPending.isPresent()) {
			return new ResponseEntity<>(isPresentRepaymentPending, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	/**
	 * Search Re payment pending.
	 *
	 * @param tenantId - the tenant id
	 * @param searchq - the searchq
	 * @param status - the status
	 * @param approveStatus - the approve status
	 * @param pageable - the pageable
	 * @return the page
	 */
	@GetMapping(value = "/{tenantId}/workflow-items/search")
	public Page<RepaymentPending> searchRepaymentPending(@PathVariable(value = "tenantId", required = true) String tenantId,
			@RequestParam(value = "searchq", required = false) String searchq,
			@RequestParam(value = "status", required = false) String status,
			@RequestParam(value = "approveStatus", required = false) String approveStatus,
			@PageableDefault(size = 10) Pageable pageable) {
		if(pageable.getPageSize()>Constants.MAX_PAGEABLE_LENGTH) 
			throw new PageableLengthException(environment.getProperty(pageableLength));
		return repaymentService.searchRepaymentPending(searchq, status, approveStatus, pageable);
	}

}
