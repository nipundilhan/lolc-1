package com.fusionx.lending.product.controller;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

/**
 * EligibilityOtherController
 * 
 *******************************************************************************************************
 *  ###   Date         Story Point   		Task No    Author       Description
 *------------------------------------------------------------------------------------------------------
 *    1   27-07-2021    FXL_July_2021_2  	FXL-54		Piyumi      Created
 *    
 *******************************************************************************************************
 */

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
import com.fusionx.lending.product.domain.EligibilityOther;
import com.fusionx.lending.product.domain.EligibilityOtherPending;
import com.fusionx.lending.product.exception.PageableLengthException;
import com.fusionx.lending.product.exception.UserNotFound;
import com.fusionx.lending.product.resources.EligibilityOtherAddResource;
import com.fusionx.lending.product.resources.EligibilityOtherUpdateResource;
import com.fusionx.lending.product.resources.SuccessAndErrorDetails;
import com.fusionx.lending.product.resources.SuccessAndErrorDetailsResource;
import com.fusionx.lending.product.service.EligibilityOtherService;

@RestController
@RequestMapping(value = "/eligibility-other-eligibility")
@CrossOrigin(origins = "*")
public class EligibilityOtherController extends MessagePropertyBase{
	
	@Autowired
	private EligibilityOtherService eligibilityOtherService;
	
	
	/**
	 * Get the EligibilityOther by id.
	 *
	 * @param @PathVariable{tenantId}
	 * @param @PathVariable{id}
	 * @return EligibilityOther
	 */
	@GetMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> getEligibilityOtherById(
			@PathVariable(value = "tenantId") String tenantId,
			@PathVariable(value = "id") Long id) {
		
		Optional<EligibilityOther> isPresentEligibilityOther = eligibilityOtherService.getById(id);
		if (isPresentEligibilityOther.isPresent()) {
			return new ResponseEntity<>(isPresentEligibilityOther, HttpStatus.OK);
		} else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Get the all EligibilityOther.
	 *
	 * @param tenantId - the tenant id
	 * @return the all EligibilityOther
	 */
	@GetMapping(value = "/{tenantId}/all")
	public ResponseEntity<Object> getAllEligibilityOther(@PathVariable(value = "tenantId", required = true) String tenantId) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<EligibilityOther> eligibilityOtherList = eligibilityOtherService.findAll();
		if (!eligibilityOtherList.isEmpty()) {
			return new ResponseEntity<>((Collection<EligibilityOther>) eligibilityOtherList, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * get EligibilityOther by eligibilityId
	 * 
	 * @param @PathVariable{tenantId}
	 * @param @PathVariable{eligibilityId}
	 * @return List<Type>
	 */
	@GetMapping(value = "/{tenantId}/eligibility/{eligibilityId}")
	public ResponseEntity<Object> getByEligibilityTemplateId(
			@PathVariable(value = "tenantId") String tenantId,
			@PathVariable(value = "eligibilityId") Long eligibilityId) {
		
		List<EligibilityOther> eligibilityOtherList = eligibilityOtherService.getByEligibilityId(eligibilityId);
		if (!eligibilityOtherList.isEmpty()) {
			return new ResponseEntity<>(eligibilityOtherList, HttpStatus.OK);
		} else {
			SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Add EligibilityOther
	 *
	 * @param @PathVariable{tenantId}
	 * @param @PathVariable{eligibilityTemplateId}
	 * @return SuccessAndErrorDetailsDto
	 */
	@PostMapping(value = "/{tenantId}/eligibility/{eligibilityId}")
	public ResponseEntity<Object> addEligibilityOther(@PathVariable(value = "tenantId") String tenantId,
			@PathVariable(value = "eligibilityId") Long eligibilityId,
			@Valid @RequestBody EligibilityOtherAddResource eligibilityOtherAddResource) {
		
		if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new UserNotFound(environment.getProperty(COMMON_USER_NOT_FOUND));
		
		Long id = eligibilityOtherService.addEligibilityOther(tenantId, eligibilityId, 
				eligibilityOtherAddResource);
		SuccessAndErrorDetails successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty(RECORD_CREATED),
				Long.toString(id));
		return new ResponseEntity<>(successAndErrorDetails, HttpStatus.CREATED);
	}
	
	/**
	 * Update EligibilityOther
	 *
	 * @param @PathVariable{tenantId}
	 * @param @PathVariable{eligibilityTemplateId}
	 * @return SuccessAndErrorDetailsDto
	 */
	@PutMapping(value = "/{tenantId}/eligibility/{eligibilityId}")
	public ResponseEntity<Object> updateEligibilityOther(@PathVariable(value = "tenantId") String tenantId,
			@PathVariable(value = "eligibilityId") Long eligibilityId,
			@Valid @RequestBody EligibilityOtherUpdateResource eligibilityOtherUpdateResource) {
		
		if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new UserNotFound(environment.getProperty(COMMON_USER_NOT_FOUND));
		
		Long id = eligibilityOtherService.updateEligibilityOther(tenantId, eligibilityId, 
				eligibilityOtherUpdateResource);
		SuccessAndErrorDetails successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty(RECORD_UPDATED),
				Long.toString(id));
		return new ResponseEntity<>(successAndErrorDetails, HttpStatus.CREATED);
	}
	
	/**
	 * Gets EligibilityOther by pending id.
	 *
	 * @param tenantId - the tenant id
	 * @param pendingId - the pending id
	 * @return ResponseEntity<Object> by pending id
	 */
	@GetMapping(value = "/{tenantId}/pending/{pendingId}")
	public ResponseEntity<Object> getEligibilityOtherByPendingId(
			@PathVariable(value = "tenantId") String tenantId,
			@PathVariable(value = "pendingId") Long pendingId) {
		
		Optional<EligibilityOtherPending> isPresentEligibilityOtherPending = eligibilityOtherService.getByPendingId(pendingId);
		if (isPresentEligibilityOtherPending.isPresent()) {
			return new ResponseEntity<>(isPresentEligibilityOtherPending, HttpStatus.OK);
		} else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Search EligibilityOtherPending
	 *
	 * @param tenantId - the tenant id
	 * @param searchq - the searchq
	 * @param status - the status
	 * @param approveStatus - the approve status
	 * @param pageable - the pageable
	 * @return the page
	 */
	@GetMapping(value = "/{tenantId}/workflow-items/search")
	public Page<EligibilityOtherPending> searchEligibilityOtherPending(@PathVariable(value = "tenantId") String tenantId,
			@RequestParam(value = "searchq", required = false) String searchq,
			@RequestParam(value = "status", required = false) String status,
			@RequestParam(value = "approveStatus", required = false) String approveStatus,
			@PageableDefault Pageable pageable) {
		if(pageable.getPageSize()>Constants.MAX_PAGEABLE_LENGTH) 
			throw new PageableLengthException(environment.getProperty("common.pageable-length"));
		return eligibilityOtherService.searchEligibilityOtherPending(searchq, status, approveStatus, pageable);
	}


}
