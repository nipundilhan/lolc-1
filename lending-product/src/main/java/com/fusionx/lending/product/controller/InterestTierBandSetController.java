package com.fusionx.lending.product.controller;

/**
 * InterestTierBandSetController 
 * 
 *******************************************************************************************************
 *  ###   Date         Story Point   		Task No    Author       Description
 *------------------------------------------------------------------------------------------------------
 *    1   20-07-2021    FXL_July_2021_2  	FXL-52		Piyumi      Created
 *    
 *******************************************************************************************************
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
import com.fusionx.lending.product.domain.InterestTierBandSet;
import com.fusionx.lending.product.domain.InterestTierBandSetPending;
import com.fusionx.lending.product.exception.PageableLengthException;
import com.fusionx.lending.product.exception.UserNotFound;
import com.fusionx.lending.product.resources.InterestTierBandSetAddResource;
import com.fusionx.lending.product.resources.InterestTierBandSetUpdateResource;
import com.fusionx.lending.product.resources.SuccessAndErrorDetails;
import com.fusionx.lending.product.resources.SuccessAndErrorDetailsResource;
import com.fusionx.lending.product.service.InterestTierBandSetService;

@RestController
@RequestMapping(value = "/interest-tier-band-set")
@CrossOrigin(origins = "*")
public class InterestTierBandSetController {
	
	@Autowired
	Environment environment;

	@Autowired
	public InterestTierBandSetService interestTierBandSetService;
	
	private String userNotFound = "common.user-not-found";
	
	/**
	 * Get the InterestTierBandSet by id.
	 *
	 * @param @PathVariable{tenantId}
	 * @param @PathVariable{id}
	 * @return InterestTierBandSet
	 */
	@GetMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> getInterestTierBandSetById(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<InterestTierBandSet> isPresentInterestTierBandSet = interestTierBandSetService.getById(id);
		if (isPresentInterestTierBandSet.isPresent()) {
			return new ResponseEntity<>(isPresentInterestTierBandSet, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty("record-not-found"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * get InterestTierBandSet by InterestTemplateId
	 * 
	 * @param @PathVariable{tenantId}
	 * @param @PathVariable{interestTemplateId}
	 * @return List<Type>
	 */
	@GetMapping(value = "/{tenantId}/interest-template/{interestTemplateId}")
	public ResponseEntity<Object> getByInterestTemplateId(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "interestTemplateId", required = true) Long interestTemplateId) {

		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
		List<InterestTierBandSet> interestTierBandSets = interestTierBandSetService.getByInterestTemplateId(interestTemplateId);
		int size = interestTierBandSets.size();
		if (size > 0) {
			return new ResponseEntity<>((Collection<InterestTierBandSet>) interestTierBandSets, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty("record-not-found"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * get InterestTierBandSet by InterestTemplateId and code
	 * 
	 * @param @PathVariable{tenantId}
	 * @param @PathVariable{interestTemplateId}
	 * @param @PathVariable{code}
	 * @return List<Type>
	 */
	@GetMapping(value = "/{tenantId}/code/{code}/interest-template/{interestTemplateId}")
	public ResponseEntity<Object> getByCodeAndInterestTemplateId(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "code", required = true) String code,
			@PathVariable(value = "interestTemplateId", required = true) Long interestTemplateId) {

		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
		Optional<InterestTierBandSet> interestTierBandSets = interestTierBandSetService.getByCodeAndInterestTemplateId(code,interestTemplateId);
		if (interestTierBandSets.isPresent()) {
			return new ResponseEntity<>(interestTierBandSets, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty("record-not-found"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Add InterestTierBandSet
	 *
	 * @param @PathVariable{tenantId}
	 * @param @PathVariable{interestTemplateId}
	 * @return SuccessAndErrorDetailsDto
	 */
	@PostMapping(value = "/{tenantId}/interest-template/{interestTemplateId}")
	public ResponseEntity<Object> addInterestTierBandSet(@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "interestTemplateId", required = true) Long interestTemplateId,
			@Valid @RequestBody InterestTierBandSetAddResource interestTierBandSetAddResource) {
		
		if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new UserNotFound(environment.getProperty(userNotFound));
		
		SuccessAndErrorDetails successAndErrorDetails = new SuccessAndErrorDetails();
		Long id = interestTierBandSetService.addInterestTierBandSet(tenantId, interestTemplateId, 
				interestTierBandSetAddResource);
		successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty("rec.saved"),
				Long.toString(id));
		return new ResponseEntity<>(successAndErrorDetails, HttpStatus.CREATED);
	}
	
	/**
	 * Update InterestTierBandSet
	 *
	 * @param @PathVariable{tenantId}
	 * @param @PathVariable{interestTemplateId}
	 * @return SuccessAndErrorDetailsDto
	 */
	@PutMapping(value = "/{tenantId}/interest-template/{interestTemplateId}")
	public ResponseEntity<Object> updateInterestTierBandSet(@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "interestTemplateId", required = true) Long interestTemplateId,
			@Valid @RequestBody InterestTierBandSetUpdateResource interestTierBandSetUpdateResource) {
		
		if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new UserNotFound(environment.getProperty(userNotFound));
		
		SuccessAndErrorDetails successAndErrorDetails = new SuccessAndErrorDetails();
		Long id = interestTierBandSetService.updateInterestTierBandSet(tenantId, interestTemplateId, 
				interestTierBandSetUpdateResource);
		successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty("rec.updated"),
				Long.toString(id));
		return new ResponseEntity<>(successAndErrorDetails, HttpStatus.CREATED);
	}
	
	/**
	 * Gets InterestTierBandSet by pending id.
	 *
	 * @param tenantId - the tenant id
	 * @param pendingId - the pending id
	 * @return InterestTierBandSet by pending id
	 */
	@GetMapping(value = "/{tenantId}/pending/{pendingId}")
	public ResponseEntity<Object> getInterestTierBandSetByPendingId(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "pendingId", required = true) Long pendingId) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<InterestTierBandSetPending> isPresentInterestTierBandSetPending = interestTierBandSetService.getByPendingId(pendingId);
		if (isPresentInterestTierBandSetPending.isPresent()) {
			return new ResponseEntity<>(isPresentInterestTierBandSetPending, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty("record-not-found"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Search InterestTierBandSetPending
	 *
	 * @param tenantId - the tenant id
	 * @param searchq - the searchq
	 * @param status - the status
	 * @param approveStatus - the approve status
	 * @param pageable - the pageable
	 * @return the page
	 */
	@GetMapping(value = "/{tenantId}/workflow-items/search")
	public Page<InterestTierBandSetPending> searchInterestTierBandSetPending(@PathVariable(value = "tenantId", required = true) String tenantId,
			@RequestParam(value = "searchq", required = false) String searchq,
			@RequestParam(value = "status", required = false) String status,
			@RequestParam(value = "approveStatus", required = false) String approveStatus,
			@PageableDefault(size = 10) Pageable pageable) {
		if(pageable.getPageSize()>Constants.MAX_PAGEABLE_LENGTH) 
			throw new PageableLengthException(environment.getProperty("common.pageable-length"));
		return interestTierBandSetService.searchInterestTierBandSetPending(searchq, status, approveStatus, pageable);
	}

}
