package com.fusionx.lending.product.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.fusionx.lending.product.domain.EligibilityResidencyEligibility;
import com.fusionx.lending.product.domain.EligibilityResidencyEligibilityPending;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.exception.PageableLengthException;
import com.fusionx.lending.product.exception.UserNotFound;
import com.fusionx.lending.product.exception.ValidateRecordException;
import com.fusionx.lending.product.resources.EligibilityResidencyEligibilityAddResource;
import com.fusionx.lending.product.resources.EligibilityResidencyEligibilityUpdateResource;
import com.fusionx.lending.product.resources.SuccessAndErrorDetails;
import com.fusionx.lending.product.resources.SuccessAndErrorDetailsResource;
import com.fusionx.lending.product.service.EligibilityResidencyEligibilityService;

/**
 * EligibilityResidencyEligibilityController
 * 
 *******************************************************************************************************
 *  ###   Date         Story Point   		Task No    Author       Description
 *------------------------------------------------------------------------------------------------------
 *    1   28-07-2021    FXL_July_2021_3  	FXL-55		Piyumi      Created
 *    
 *******************************************************************************************************
 */

@RestController
@RequestMapping(value = "/eligibility-residency-eligibility")
@CrossOrigin(origins = "*")
public class EligibilityResidencyEligibilityController extends MessagePropertyBase {

	@Autowired
	public EligibilityResidencyEligibilityService eligibilityResidencyEligibilityService;
	
	private String userNotFound = "common.user-not-found";
	
	/**
	 * Get the EligibilityResidencyEligibility by id.
	 *
	 * @param @PathVariable{tenantId}
	 * @param @PathVariable{id}
	 * @return EligibilityResidencyEligibility
	 */
	@GetMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> getById(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<EligibilityResidencyEligibility> isPresentEligibilityResidencyEligibility = eligibilityResidencyEligibilityService.getById(tenantId,id);
		if (isPresentEligibilityResidencyEligibility.isPresent()) {
			return new ResponseEntity<>(isPresentEligibilityResidencyEligibility, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * get EligibilityResidencyEligibility by eligibilityId
	 * 
	 * @param @PathVariable{tenantId}
	 * @param @PathVariable{eligibilityId}
	 * @return List<Type>
	 */
	@GetMapping(value = "/{tenantId}/eligibility/{eligibilityId}")
	public ResponseEntity<Object> getByEligibilityId(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "eligibilityId", required = true) Long eligibilityId) {

		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
		List<EligibilityResidencyEligibility> eligibilityResidencyEligibilityList = eligibilityResidencyEligibilityService.getByEligibilityId(tenantId,eligibilityId);
		int size = eligibilityResidencyEligibilityList.size();
		if (size > 0) {
			return new ResponseEntity<>(eligibilityResidencyEligibilityList, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * get EligibilityResidencyEligibility by residencyEligibilityId
	 * 
	 * @param @PathVariable{tenantId}
	 * @param @PathVariable{residencyEligibilityId}
	 * @return List<Type>
	 */
	@GetMapping(value = "/{tenantId}/residency-eligibility/{residencyEligibilityId}")
	public ResponseEntity<Object> getByResidencyEligibilityId(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "residencyEligibilityId", required = true) Long residencyEligibilityId) {

		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
		List<EligibilityResidencyEligibility> eligibilityResidencyEligibilityList = eligibilityResidencyEligibilityService.getByResidencyEligibilityId(tenantId,residencyEligibilityId);
		int size = eligibilityResidencyEligibilityList.size();
		if (size > 0) {
			return new ResponseEntity<>(eligibilityResidencyEligibilityList, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * get EligibilityResidencyEligibility by status
	 * 
	 * @param @PathVariable{tenantId}
	 * @param @PathVariable{status}
	 * @return List<Type>
	 */
	@GetMapping(value = "/{tenantId}/status/{status}")
	public ResponseEntity<Object> getByStatus(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "status", required = true) String status) {

		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
		if(status.equals(CommonStatus.ACTIVE.toString()) || status.equals(CommonStatus.INACTIVE.toString())) {
			List<EligibilityResidencyEligibility> eligibilityResidencyEligibilityList = eligibilityResidencyEligibilityService.getByStatus(tenantId,status);
			int size = eligibilityResidencyEligibilityList.size();
			if (size > 0) {
				return new ResponseEntity<>(eligibilityResidencyEligibilityList, HttpStatus.OK);
			} else {
				responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
				return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
			}
		}else {
			responseMessage.setMessages(RECORD_NOT_FOUND);
	        throw new ValidateRecordException(environment.getProperty(COMMON_STATUS_PATTERN), "message");
		}
	}
	
	/**
	 * Add EligibilityResidencyEligibility
	 *
	 * @param @PathVariable{tenantId}
	 * @return SuccessAndErrorDetailsDto
	 */
	@PostMapping(value = "/{tenantId}")
	public ResponseEntity<Object> addEligibilityResidencyEligibility(@PathVariable(value = "tenantId", required = true) String tenantId,
			@Valid @RequestBody EligibilityResidencyEligibilityAddResource eligibilityResidencyEligibilityAddResource) {
		
		if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new UserNotFound(environment.getProperty(userNotFound));
		
		Long id = eligibilityResidencyEligibilityService.addEligibilityResidencyEligibility(tenantId,
				eligibilityResidencyEligibilityAddResource);
		SuccessAndErrorDetails successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty("rec.saved"),
				Long.toString(id));
		return new ResponseEntity<>(successAndErrorDetails, HttpStatus.CREATED);
	}
	
	/**
	 * Update EligibilityResidencyEligibility
	 *
	 * @param @PathVariable{tenantId}
	 * @return SuccessAndErrorDetailsDto
	 */
	@PutMapping(value = "/{tenantId}")
	public ResponseEntity<Object> updateEligibilityResidencyEligibility(@PathVariable(value = "tenantId", required = true) String tenantId,
			@Valid @RequestBody EligibilityResidencyEligibilityUpdateResource interestTierBandSetUpdateResource) {
		
		if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new UserNotFound(environment.getProperty(userNotFound));
		
		Long id = eligibilityResidencyEligibilityService.updateEligibilityResidencyEligibility(tenantId, 
				interestTierBandSetUpdateResource);
		SuccessAndErrorDetails successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty("rec.updated"),
				Long.toString(id));
		return new ResponseEntity<>(successAndErrorDetails, HttpStatus.CREATED);
	}
	
	/**
	 * Gets EligibilityResidencyEligibility by pending id.
	 *
	 * @param tenantId - the tenant id
	 * @param pendingId - the pending id
	 * @return EligibilityResidencyEligibility by pending id
	 */
	@GetMapping(value = "/{tenantId}/pending/{pendingId}")
	public ResponseEntity<Object> getByPendingId(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "pendingId", required = true) Long pendingId) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<EligibilityResidencyEligibilityPending> isPresentEligibilityResidencyEligibilityPending = eligibilityResidencyEligibilityService.getByPendingId(tenantId,pendingId);
		if (isPresentEligibilityResidencyEligibilityPending.isPresent()) {
			return new ResponseEntity<>(isPresentEligibilityResidencyEligibilityPending, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Search EligibilityResidencyEligibilityPending
	 *
	 * @param tenantId - the tenant id
	 * @param searchq - the searchq
	 * @param status - the status
	 * @param approveStatus - the approve status
	 * @param pageable - the pageable
	 * @return the page
	 */
	@GetMapping(value = "/{tenantId}/workflow-items/search")
	public Page<EligibilityResidencyEligibilityPending> search(@PathVariable(value = "tenantId", required = true) String tenantId,
			@RequestParam(value = "searchq", required = false) String searchq,
			@RequestParam(value = "status", required = false) String status,
			@RequestParam(value = "approveStatus", required = false) String approveStatus,
			@PageableDefault(size = 10) Pageable pageable) {
		if(pageable.getPageSize()>Constants.MAX_PAGEABLE_LENGTH) 
			throw new PageableLengthException(environment.getProperty("common.pageable-length"));
		return eligibilityResidencyEligibilityService.searchEligibilityResidencyEligibilityPending(tenantId,searchq, status, approveStatus, pageable);
	}



}
