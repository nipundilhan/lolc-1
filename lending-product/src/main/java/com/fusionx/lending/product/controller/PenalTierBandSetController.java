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
import com.fusionx.lending.product.domain.PenalTierBandSet;
import com.fusionx.lending.product.domain.PenalTierBandSetPending;
import com.fusionx.lending.product.exception.UserNotFound;
import com.fusionx.lending.product.resources.PenalTierBandSetAddRequest;
import com.fusionx.lending.product.resources.PenalTierBandSetUpdateRequest;
import com.fusionx.lending.product.resources.SuccessAndErrorDetails;
import com.fusionx.lending.product.resources.SuccessAndErrorDetailsResource;
import com.fusionx.lending.product.service.PenalTierBandSetService;

@RestController
@RequestMapping(value = "/penal-tier-band-set")
@CrossOrigin(origins = "*")
public class PenalTierBandSetController {
	
	@Autowired
	Environment environment;

	@Autowired
	public PenalTierBandSetService penalTierBandSetService;
	
	private String userNotFound = "common.user-not-found";
	
	/**
	 * Get the PenalTierBandSet by id.
	 *
	 * @param @PathVariable{tenantId}
	 * @param @PathVariable{id}
	 * @return InterestTierBandSet
	 */
	@GetMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> getPenalTierBandSetById(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<PenalTierBandSet> isPresentPenalTierBandSet = penalTierBandSetService.getById(id);
		if (isPresentPenalTierBandSet.isPresent()) {
			return new ResponseEntity<>(isPresentPenalTierBandSet, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty("record-not-found"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * get PenalTierBandSet by PenalInterestTemplateId
	 * 
	 * @param @PathVariable{tenantId}
	 * @param @PathVariable{penalInterestTemplateId}
	 * @return List<Type>
	 */
	@GetMapping(value = "/{tenantId}/penal-interest-template/{penalInterestTemplateId}")
	public ResponseEntity<Object> getByInterestTemplateId(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "penalInterestTemplateId", required = true) Long penalInterestTemplateId) {

		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
		List<PenalTierBandSet> penalTierBandSets = penalTierBandSetService.getByPenalInterestTemplateId(penalInterestTemplateId);
		int size = penalTierBandSets.size();
		if (size > 0) {
			return new ResponseEntity<>((Collection<PenalTierBandSet>) penalTierBandSets, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty("record-not-found"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Add PenalTierBandSet
	 *
	 * @param @PathVariable{tenantId}
	 * @param @PathVariable{penalInterestTemplateId}
	 * @return SuccessAndErrorDetailsDto
	 */
	@PostMapping(value = "/{tenantId}/penal-interest-template/{penalInterestTemplateId}")
	public ResponseEntity<Object> addInterestTierBandSet(@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "penalInterestTemplateId", required = true) Long penalInterestTemplateId,
			@Valid @RequestBody PenalTierBandSetAddRequest penalTierBandSetAddRequest) {
		
		if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new UserNotFound(environment.getProperty(userNotFound));
		
		SuccessAndErrorDetails successAndErrorDetails = new SuccessAndErrorDetails();
		Long id = penalTierBandSetService.addPenalTierBandSet(tenantId, penalInterestTemplateId, 
				penalTierBandSetAddRequest);
		successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty("rec.saved"),
				Long.toString(id));
		return new ResponseEntity<>(successAndErrorDetails, HttpStatus.CREATED);
	}
	
	/**
	 * Update PenalTierBandSet
	 *
	 * @param @PathVariable{tenantId}
	 * @param @PathVariable{penalInterestTemplateId}
	 * @return SuccessAndErrorDetailsDto
	 */
	@PutMapping(value = "/{tenantId}/penal-interest-template/{penalInterestTemplateId}")
	public ResponseEntity<Object> updateInterestTierBandSet(@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "penalInterestTemplateId", required = true) Long penalInterestTemplateId,
			@Valid @RequestBody PenalTierBandSetUpdateRequest penalTierBandSetUpdateRequest) {
		
		if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new UserNotFound(environment.getProperty(userNotFound));
		
		SuccessAndErrorDetails successAndErrorDetails = new SuccessAndErrorDetails();
		Long id = penalTierBandSetService.updatePenalTierBandSet(tenantId, penalInterestTemplateId, 
				penalTierBandSetUpdateRequest);
		successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty("rec.updated"),
				Long.toString(id));
		return new ResponseEntity<>(successAndErrorDetails, HttpStatus.CREATED);
	}
	
	/**
	 * Gets PenalTierBandSet by pending id.
	 *
	 * @param tenantId - the tenant id
	 * @param pendingId - the pending id
	 * @return PenalTierBandSet by pending id
	 */
	@GetMapping(value = "/{tenantId}/pending/{pendingId}")
	public ResponseEntity<Object> getInterestTierBandSetByPendingId(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "pendingId", required = true) Long pendingId) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<PenalTierBandSetPending> isPresentPenalTierBandSetPending = penalTierBandSetService.getByPendingId(pendingId);
		if (isPresentPenalTierBandSetPending.isPresent()) {
			return new ResponseEntity<>(isPresentPenalTierBandSetPending, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty("record-not-found"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
//	/**
//	 * Search InterestTierBandSetPending
//	 *
//	 * @param tenantId - the tenant id
//	 * @param searchq - the searchq
//	 * @param status - the status
//	 * @param approveStatus - the approve status
//	 * @param pageable - the pageable
//	 * @return the page
//	 */
//	@GetMapping(value = "/{tenantId}/workflow-items/search")
//	public Page<InterestTierBandSetPending> searchInterestTierBandSetPending(@PathVariable(value = "tenantId", required = true) String tenantId,
//			@RequestParam(value = "searchq", required = false) String searchq,
//			@RequestParam(value = "status", required = false) String status,
//			@RequestParam(value = "approveStatus", required = false) String approveStatus,
//			@PageableDefault(size = 10) Pageable pageable) {
//		if(pageable.getPageSize()>Constants.MAX_PAGEABLE_LENGTH) 
//			throw new PageableLengthException(environment.getProperty("common.pageable-length"));
//		return interestTierBandSetService.searchInterestTierBandSetPending(searchq, status, approveStatus, pageable);
//	}

}
