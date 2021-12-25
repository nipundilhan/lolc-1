package com.fusionx.lending.product.controller;

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
import com.fusionx.lending.product.domain.InterestTierBand;
import com.fusionx.lending.product.domain.InterestTierBandPending;
import com.fusionx.lending.product.exception.PageableLengthException;
import com.fusionx.lending.product.exception.UserNotFound;
import com.fusionx.lending.product.resources.InterestTierBandAddResource;
import com.fusionx.lending.product.resources.InterestTierBandUpdateResource;
import com.fusionx.lending.product.resources.SuccessAndErrorDetails;
import com.fusionx.lending.product.resources.SuccessAndErrorDetailsResource;
import com.fusionx.lending.product.service.InterestTierBandService;

/**
 * InterestTierBandController
 * 
 *******************************************************************************************************
 * ### 	Date 			Story Point 		Task No 	Author 		Description
 * ------------------------------------------------------------------------------------------------------
 * 1 	26-07-2021 		FXL_July_2021_2 	FXL-53 		Piyumi 		Created
 * 
 *******************************************************************************************************
 */

@RestController
@RequestMapping(value = "/interest-tier-band")
@CrossOrigin(origins = "*")
public class InterestTierBandController {
	
	@Autowired
	Environment environment;

	@Autowired
	public InterestTierBandService interestTierBandService;
	
	private String userNotFound = "common.user-not-found";
	
	/**
	 * Get the InterestTierBand by id.
	 *
	 * @param @PathVariable{tenantId}
	 * @param @PathVariable{id}
	 * @return InterestTierBand
	 */
	@GetMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> getInterestTierBandById(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<InterestTierBand> isPresentInterestTierBand = interestTierBandService.getById(id);
		if (isPresentInterestTierBand.isPresent()) {
			return new ResponseEntity<>(isPresentInterestTierBand, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty("record-not-found"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * get InterestTierBand by InterestTierBandSetId
	 * 
	 * @param @PathVariable{tenantId}
	 * @param @PathVariable{InterestTierBandSetId}
	 * @return List<Type>
	 */
	@GetMapping(value = "/{tenantId}/interest-tier-band-set/{interestTierBandSetId}")
	public ResponseEntity<Object> getByInterestTierBandSetId(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "interestTierBandSetId", required = true) Long interestTierBandSetId) {

		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
		List<InterestTierBand> interestTierBands = interestTierBandService.getByInterestTierBandSetId(interestTierBandSetId);
		int size = interestTierBands.size();
		if (size > 0) {
			return new ResponseEntity<>((Collection<InterestTierBand>) interestTierBands, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty("record-not-found"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Add InterestTierBand
	 *
	 * @param @PathVariable{tenantId}
	 * @param @PathVariable{interestTierBandSetId}
	 * @return SuccessAndErrorDetailsDto
	 */
	@PostMapping(value = "/{tenantId}/interest-tier-band-set/{interestTierBandSetId}")
	public ResponseEntity<Object> addInterestTierBand(@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "interestTierBandSetId", required = true) Long interestTierBandSetId,
			@Valid @RequestBody InterestTierBandAddResource interestTierBandAddResource) {
		
		if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new UserNotFound(environment.getProperty(userNotFound));

		Long id = interestTierBandService.addInterestTierBand(tenantId, interestTierBandSetId, 
				interestTierBandAddResource);
		SuccessAndErrorDetails successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty("rec.saved"),
				Long.toString(id));
		return new ResponseEntity<>(successAndErrorDetails, HttpStatus.CREATED);
	}
	
	/**
	 * Update InterestTierBand
	 *
	 * @param @PathVariable{tenantId}
	 * @param @PathVariable{interestTierBandSetId}
	 * @return SuccessAndErrorDetailsDto
	 */
	@PutMapping(value = "/{tenantId}/interest-template/{interestTemplateId}/interest-tier-band-set/{interestTierBandSetId}")
	public ResponseEntity<Object> updateInterestTierBand(@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "interestTemplateId", required = true) Long interestTemplateId,
			@PathVariable(value = "interestTierBandSetId", required = true) Long interestTierBandSetId,
			@Valid @RequestBody InterestTierBandUpdateResource interestTierBandUpdateResource) {
		
		if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new UserNotFound(environment.getProperty(userNotFound));
		
		SuccessAndErrorDetails successAndErrorDetails = new SuccessAndErrorDetails();
		Long id = interestTierBandService.updateInterestTierBand(tenantId,interestTemplateId, interestTierBandSetId, 
				interestTierBandUpdateResource);
		successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty("rec.updated"),
				Long.toString(id));
		return new ResponseEntity<>(successAndErrorDetails, HttpStatus.CREATED);
	}
	
	/**
	 * Gets InterestTierBand by pending id.
	 *
	 * @param tenantId - the tenant id
	 * @param pendingId - the pending id
	 * @return InterestTierBand by pending id
	 */
	@GetMapping(value = "/{tenantId}/pending/{pendingId}")
	public ResponseEntity<Object> getInterestTierBandByPendingId(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "pendingId", required = true) Long pendingId) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<InterestTierBandPending> isPresentInterestTierBandPending = interestTierBandService.getByPendingId(pendingId);
		if (isPresentInterestTierBandPending.isPresent()) {
			return new ResponseEntity<>(isPresentInterestTierBandPending, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty("record-not-found"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Search InterestTierBandPending
	 *
	 * @param tenantId - the tenant id
	 * @param searchq - the searchq
	 * @param status - the status
	 * @param approveStatus - the approve status
	 * @param pageable - the pageable
	 * @return the page
	 */
	@GetMapping(value = "/{tenantId}/workflow-items/search")
	public Page<InterestTierBandPending> searchInterestTierBandPending(@PathVariable(value = "tenantId", required = true) String tenantId,
			@RequestParam(value = "searchq", required = false) String searchq,
			@RequestParam(value = "status", required = false) String status,
			@RequestParam(value = "approveStatus", required = false) String approveStatus,
			@PageableDefault(size = 10) Pageable pageable) {
		if(pageable.getPageSize()>Constants.MAX_PAGEABLE_LENGTH) 
			throw new PageableLengthException(environment.getProperty("common.pageable-length"));
		return interestTierBandService.searchInterestTierBandPending(searchq, status, approveStatus, pageable);
	}

}



