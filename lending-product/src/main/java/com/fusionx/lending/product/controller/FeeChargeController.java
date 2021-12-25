package com.fusionx.lending.product.controller;

/**
 * FeeCharge  Controller
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   10-06-2021             				 MenukaJ        Created
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
import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.EligibilityPending;
import com.fusionx.lending.product.domain.FeeCharge;
import com.fusionx.lending.product.domain.FeeChargePending;
import com.fusionx.lending.product.enums.CommonApproveStatus;
import com.fusionx.lending.product.enums.WorkflowStatus;
import com.fusionx.lending.product.exception.PageableLengthException;
import com.fusionx.lending.product.exception.UserNotFound;
import com.fusionx.lending.product.resources.FeeChargeAddResource;
import com.fusionx.lending.product.resources.FeeChargeUpdateResource;
import com.fusionx.lending.product.resources.SuccessAndErrorDetails;
import com.fusionx.lending.product.resources.SuccessAndErrorDetailsResource;
import com.fusionx.lending.product.service.FeeChargeService;


@RestController
@RequestMapping(value = "/fee-charge")
@CrossOrigin(origins = "*")
public class FeeChargeController {
	
	@Autowired
	Environment environment;
	
	@Autowired
	public FeeChargeService feeChargeService;
	
	
	private static final String RECORD_NOT_FOUND = "Records not available";
	private String userNotFound="common.user-not-found";
	private String pageableLength = "common.pageable-length";
	
	/**
	 * get all FeeCharge 
	 * @param @PathVariable{tenantId}
	 * @param @PathVariable{all}
	 * @return List<Type>
	 */
    @GetMapping(value = "/{tenantId}/all")
    public ResponseEntity<Object> getAllFeeCharge(@PathVariable(value = "tenantId", required = true) String tenantId) {
    	SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
		List<FeeCharge> feeCharge = feeChargeService.getAll();
		int size = feeCharge.size();
		if (size > 0) {
			return new ResponseEntity<>((Collection<FeeCharge>) feeCharge,HttpStatus.OK);
		} 
		else {
			responseMessage.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
    
    /**
     * get FeeCharge  by id
     * @param @PathVariable{tenantId}
     * @param @PathVariable{id}
     * @return Option
     */
 	@GetMapping(value = "/{tenantId}/{id}")
 	public ResponseEntity<Object> getFeeChargeById(@PathVariable(value = "tenantId", required = true) String tenantId, 
 			                                              @PathVariable(value = "id", required = true) Long id) {
 		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
	 	Optional<FeeCharge> isPresentFeeCharge = feeChargeService.getById(id);
	 	if (isPresentFeeCharge.isPresent()) {
	 		return new ResponseEntity<>(isPresentFeeCharge.get(), HttpStatus.OK);
	 	} 
	 	else {
	 		responseMessage.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
	 	}
 	}
 	
	/**
	 * get FeeCharge  by code
	 * @param @pathVariable{tenantId}
	 * @param @pathVariable {code}
	 * @return Optional
	 **/
	@GetMapping(value = "/{tenantId}/code/{code}")
	public ResponseEntity<Object> getFeeChargeByCode(@PathVariable(value = "tenantId", required = true) String tenantId,
													   	            @PathVariable(value = "code", required = true) String code){
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<FeeCharge> isPresentFeeCharge = feeChargeService.getByCode(code);
		if(isPresentFeeCharge.isPresent()) {
			return new ResponseEntity<>(isPresentFeeCharge.get(), HttpStatus.OK);
		}
		else {
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * get FeeCharge  by name
	 * @param @pathVariable{tenantId}
	 * @param @pathVariable {name}
	 * @return List
	 **/
	@GetMapping(value = "/{tenantId}/name/{name}")
	public ResponseEntity<Object> getFeeChargeByName(@PathVariable(value = "tenantId", required = true) String tenantId,
															        @PathVariable(value = "name", required = true) String name){
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<FeeCharge> isPresentFeeCharge = feeChargeService.getByName(name);
		if(!isPresentFeeCharge.isEmpty()) {
			return new ResponseEntity<>(isPresentFeeCharge, HttpStatus.OK);
		}
		else {
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
 	
 	/**
     * get FeeCharge  by status
     * @param @PathVariable{tenantId}
     * @param @PathVariable{status}
     * @return Option
     */
 	@GetMapping(value = "/{tenantId}/status/{status}")
 	public ResponseEntity<Object> getFeeChargeByStatus(@PathVariable(value = "tenantId", required = true) String tenantId, 
 			                                                  @PathVariable(value = "status", required = true) String status) {
 		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
 		
 		if(status.equals("ACTIVE") || status.equals("INACTIVE")){
		 	List<FeeCharge> isPresentFeeCharge = feeChargeService.getByStatus(status);
		 	int size = isPresentFeeCharge.size();
		 	if (size>0) {
		 		return new ResponseEntity<>(isPresentFeeCharge, HttpStatus.OK);
		 	} 
		 	else {
		 		responseMessage.setMessages(RECORD_NOT_FOUND);
				return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		 	}
 		}
 		else{
	 		responseMessage.setMessages(environment.getProperty("invalid-status"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
	 	}
 	}
 	
 	/**
     * save FeeCharge 
     * @param @PathVariable{tenantId}
     * @param @RequestBody{feeChargeAddResource}
     * @return SuccessAndErrorDetailsDto
     */
 	@PostMapping(value = "/{tenantId}")
 	public ResponseEntity<Object> addFeeCharge(@PathVariable(value = "tenantId", required = true) String tenantId,
 			                                          @Valid @RequestBody FeeChargeAddResource feeChargeAddResource) {
 		if(LogginAuthentcation.getInstance().getUserName()==null || LogginAuthentcation.getInstance().getUserName().isEmpty()) { 
 			throw new UserNotFound(environment.getProperty(userNotFound));
 		}
		
 		SuccessAndErrorDetails successAndErrorDetails = new SuccessAndErrorDetails();
 		FeeCharge feeChargeType = feeChargeService.addFeeCharge(tenantId, feeChargeAddResource);
	 	successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty("rec.saved"),Long.toString(feeChargeType.getId()));
		return new ResponseEntity<>(successAndErrorDetails,HttpStatus.CREATED);
 	}
 	
	/**
     * update FeeCharge  
     * @param @PathVariable{tenantId}
     * @param @RequestBody{feeChargeUpdateResource}
     * @return SuccessAndErrorDetailsDto
     */
	@PutMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> updateFeeCharge(@PathVariable(value = "tenantId", required = true) String tenantId, 
			                                             @PathVariable(value = "id", required = true) Long id, 
			                                             @Valid @RequestBody FeeChargeUpdateResource feeChargeUpdateResource) {
		if(LogginAuthentcation.getInstance().getUserName()==null || LogginAuthentcation.getInstance().getUserName().isEmpty()) { 
 			throw new UserNotFound(environment.getProperty(userNotFound));
 		}
		
		SuccessAndErrorDetails successAndErrorDetails=new SuccessAndErrorDetails();
		Optional<FeeCharge> isPresentFeeCharge = feeChargeService.getById(id);
		if(isPresentFeeCharge.isPresent()) {

			FeeCharge feeCharge = feeChargeService.updateFeeCharge(tenantId, id, feeChargeUpdateResource);
			successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty("rec.updated"), feeCharge.getId().toString());
	        return new ResponseEntity<>(successAndErrorDetails,HttpStatus.OK);

			
		}
		else {
			successAndErrorDetails.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(successAndErrorDetails, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
	
	/**
	* approve FeeCharge  Pending
	* @param @PathVariable{tenantId}
	* @param @PathVariable{pendingId}
	* @return SuccessAndErrorDetailsDto
	*/
	@PutMapping(value = "{tenantId}/approve/{pendingId}")
	public ResponseEntity<Object> approve(@PathVariable(value = "tenantId", required = true) String tenantId, @PathVariable(value = "pendingId", required = true) Long pendingId){
		SuccessAndErrorDetailsResource successAndErrorDetailsResource=new SuccessAndErrorDetailsResource();
		Optional<FeeChargePending> isPresentEligiTempPending = feeChargeService.getByPendingId(pendingId);
		if(isPresentEligiTempPending.isPresent()) {
	
			if(feeChargeService.approveReject(tenantId, pendingId, WorkflowStatus.COMPLETE)) {
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
	* Reject FeeCharge  Pending
	* @param @PathVariable{tenantId}
	* @param @PathVariable{pendingId}
	* @return SuccessAndErrorDetailsDto
	*/
	@PutMapping(value = "{tenantId}/reject/{pendingId}")
	public ResponseEntity<Object> reject(@PathVariable(value = "tenantId", required = true) String tenantId, @PathVariable(value = "pendingId", required = true) Long pendingId){
		SuccessAndErrorDetailsResource successAndErrorDetailsResource=new SuccessAndErrorDetailsResource();
		Optional<FeeChargePending> isPresentEligiPending = feeChargeService.getByPendingId(pendingId);
		if(isPresentEligiPending.isPresent()) {
	
			if(feeChargeService.approveReject(tenantId, pendingId, WorkflowStatus.REJECT)) {
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
	 * Gets the Fee Charge by pending id.
	 *
	 * @param tenantId - the tenant id
	 * @param pendingId - the pending id
	 * @return  FeeChargePending
	 */
	@GetMapping(value = "/{tenantId}/pending/{pendingId}")
	public ResponseEntity<Object> getFeeChargePendingByPendingId(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "pendingId", required = true) Long pendingId) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<FeeChargePending> isPresentFeeChargePending = feeChargeService.getByPendingId(pendingId);
		if (isPresentFeeChargePending.isPresent()) {
			return new ResponseEntity<>(isPresentFeeChargePending, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	/**
	 * Search fee charge pending.
	 *
	 * @param tenantId - the tenant id
	 * @param searchq - the searchq
	 * @param status - the status
	 * @param approveStatus - the approve status
	 * @param pageable - the pageable
	 * @return the page
	 */
	@GetMapping(value = "/{tenantId}/workflow-items/search")
	public Page<FeeChargePending> searchFeeChargePending(@PathVariable(value = "tenantId", required = true) String tenantId,
			@RequestParam(value = "searchq", required = false) String searchq,
			@RequestParam(value = "status", required = false) String status,
			@RequestParam(value = "approveStatus", required = false) String approveStatus,
			@PageableDefault(size = 10) Pageable pageable) {
		if(pageable.getPageSize()>Constants.MAX_PAGEABLE_LENGTH) 
			throw new PageableLengthException(environment.getProperty(pageableLength));
		return feeChargeService.searchFeeChargePending(searchq, status, approveStatus, pageable);
	}
}
