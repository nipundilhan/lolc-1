package com.fusionx.lending.product.controller;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
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

import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.PenalTierBand;
import com.fusionx.lending.product.domain.PenalTierBandPending;
import com.fusionx.lending.product.exception.UserNotFound;
import com.fusionx.lending.product.resources.PenalTierBandAddRequest;
import com.fusionx.lending.product.resources.PenalTierBandUpdateRequest;
import com.fusionx.lending.product.resources.SuccessAndErrorDetails;
import com.fusionx.lending.product.resources.SuccessAndErrorDetailsResource;
import com.fusionx.lending.product.service.PenalTierBandService;

/**
 * PenalTierBandController
 * 
 *******************************************************************************************************
 * ### 	Date 			Story Point 		Task No 	Author 		Description
 * ------------------------------------------------------------------------------------------------------
 * 1 	22-08-2021 		                               Dilhan 		Created
 * 
 *******************************************************************************************************
 */

@RestController
@RequestMapping(value = "/penal-tier-band")
@CrossOrigin(origins = "*")
public class PenalTierBandController {
	
	@Autowired
	Environment environment;

	@Autowired
	public PenalTierBandService penalTierBandService;
	
	private String userNotFound = "common.user-not-found";
	
	/**
	 * Get the PenalTierBand by id.
	 *
	 * @param @PathVariable{tenantId}
	 * @param @PathVariable{id}
	 * @return PenalTierBand
	 */
	@GetMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> getPenalTierBandById(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<PenalTierBand> isPresentPenalTierBand = penalTierBandService.getById(id);
		if (isPresentPenalTierBand.isPresent()) {
			return new ResponseEntity<>(isPresentPenalTierBand, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty("record-not-found"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * get PenalTierBand by PenalTierBandSetId
	 * 
	 * @param @PathVariable{tenantId}
	 * @param @PathVariable{PenalTierBandSetId}
	 * @return List<Type>
	 */
	@GetMapping(value = "/{tenantId}/penal-tier-band-set/{penalTierBandSetId}")
	public ResponseEntity<Object> getByPenalTierBandSetId(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "penalTierBandSetId", required = true) Long penalTierBandSetId) {

		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
		List<PenalTierBand> penalTierBands = penalTierBandService.getByPenalTierBandSetId(penalTierBandSetId);
		int size = penalTierBands.size();
		if (size > 0) {
			return new ResponseEntity<>((Collection<PenalTierBand>) penalTierBands, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty("record-not-found"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Add PenalTierBand
	 *
	 * @param @PathVariable{tenantId}
	 * @param @PathVariable{interestTierBandSetId}
	 * @return SuccessAndErrorDetailsDto
	 */
	@PostMapping(value = "/{tenantId}/penal-tier-band-set/{penalTierBandSetId}")
	public ResponseEntity<Object> addInterestTierBand(@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "penalTierBandSetId", required = true) Long penalTierBandSetId,
			@Valid @RequestBody PenalTierBandAddRequest penalTierBandAddRequest) {
		
		if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new UserNotFound(environment.getProperty(userNotFound));

		Long id = penalTierBandService.addPenalTierBand(tenantId, penalTierBandSetId, 
				penalTierBandAddRequest);
		SuccessAndErrorDetails successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty("rec.saved"),
				Long.toString(id));
		return new ResponseEntity<>(successAndErrorDetails, HttpStatus.CREATED);
	}
	
	/**
	 * Update PenalTierBand
	 *
	 * @param @PathVariable{tenantId}
	 * @param @PathVariable{penalTierBandSetId}
	 * @return SuccessAndErrorDetailsDto
	 */
	@PutMapping(value = "/{tenantId}/penal-interest-template/{penalInterestTemplateId}/penal-tier-band-set/{penalTierBandSetId}")
	public ResponseEntity<Object> updateInterestTierBand(@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "penalInterestTemplateId", required = true) Long penalInterestTemplateId,
			@PathVariable(value = "penalTierBandSetId", required = true) Long penalTierBandSetId,
			@Valid @RequestBody PenalTierBandUpdateRequest penalTierBandUpdateRequest) {
		
		if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new UserNotFound(environment.getProperty(userNotFound));
		
		SuccessAndErrorDetails successAndErrorDetails = new SuccessAndErrorDetails();
		Long id = penalTierBandService.updatePenalTierBand(tenantId,penalInterestTemplateId, penalTierBandSetId, 
				penalTierBandUpdateRequest);
		successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty("rec.updated"),
				Long.toString(id));
		return new ResponseEntity<>(successAndErrorDetails, HttpStatus.CREATED);
	}
	
	/**
	 * Gets PenalTierBand by pending id.
	 *
	 * @param tenantId - the tenant id
	 * @param pendingId - the pending id
	 * @return PenalTierBand by pending id
	 */
	@GetMapping(value = "/{tenantId}/pending/{pendingId}")
	public ResponseEntity<Object> getInterestTierBandByPendingId(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "pendingId", required = true) Long pendingId) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<PenalTierBandPending> isPresentPenalTierBandPending = penalTierBandService.getByPendingId(pendingId);
		if (isPresentPenalTierBandPending.isPresent()) {
			return new ResponseEntity<>(isPresentPenalTierBandPending, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty("record-not-found"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
//	/**
//	 * Search PenalTierBandPending
//	 *
//	 * @param tenantId - the tenant id
//	 * @param searchq - the searchq
//	 * @param status - the status
//	 * @param approveStatus - the approve status
//	 * @param pageable - the pageable
//	 * @return the page
//	 */
//	@GetMapping(value = "/{tenantId}/workflow-items/search")
//	public Page<InterestTierBandPending> searchInterestTierBandPending(@PathVariable(value = "tenantId", required = true) String tenantId,
//			@RequestParam(value = "searchq", required = false) String searchq,
//			@RequestParam(value = "status", required = false) String status,
//			@RequestParam(value = "approveStatus", required = false) String approveStatus,
//			@PageableDefault(size = 10) Pageable pageable) {
//		if(pageable.getPageSize()>Constants.MAX_PAGEABLE_LENGTH) 
//			throw new PageableLengthException(environment.getProperty("common.pageable-length"));
//		return penalTierBandService.searchPenalTierBandPending(searchq, status, approveStatus, pageable);
//	}

}

