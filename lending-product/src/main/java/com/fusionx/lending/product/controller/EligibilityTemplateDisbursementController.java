package com.fusionx.lending.product.controller;

/**
Eligibility Template Disbursement
* 
********************************************************************************************************
*  ###   Date         Story Point   Task No    Author       Description
*-------------------------------------------------------------------------------------------------------
*    1   21-07-2019   FXL-1         FXL-42     Dilki        Created
*    
********************************************************************************************************
*/

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
import com.fusionx.lending.product.domain.EligibilityTemplateDisbursement;
import com.fusionx.lending.product.domain.EligibilityTemplateDisbursementPending;
import com.fusionx.lending.product.enums.CommonApproveStatus;
import com.fusionx.lending.product.enums.WorkflowStatus;
import com.fusionx.lending.product.exception.PageableLengthException;
import com.fusionx.lending.product.exception.UserNotFound;
import com.fusionx.lending.product.resources.EligibilityTemplateDisbursementAddResource;
import com.fusionx.lending.product.resources.EligibilityTemplateDisbursementUpdateResource;
import com.fusionx.lending.product.resources.SuccessAndErrorDetails;
import com.fusionx.lending.product.resources.SuccessAndErrorDetailsResource;
import com.fusionx.lending.product.service.EligibilityTemplateDisbursementService;

@RestController
@RequestMapping(value = "/eligibility-disbursement")
@CrossOrigin(origins = "*")
public class EligibilityTemplateDisbursementController extends MessagePropertyBase {

	@Autowired
	public EligibilityTemplateDisbursementService eligibilityTemplateDisbursementService;

	/**
	 * Returns the all records
	 *
	 * @param tenantId the tenant id
	 * @return the all records
	 */
	@GetMapping(value = "/{tenantId}/all")
	public ResponseEntity<Object> getAllEligibilityTemplateDisbursement(
			@PathVariable(value = "tenantId", required = true) String tenantId) {
		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
		List<EligibilityTemplateDisbursement> eligibilityTemplateDisbursement = eligibilityTemplateDisbursementService
				.getAll();
		int size = eligibilityTemplateDisbursement.size();
		if (size > 0) {
			return new ResponseEntity<>(eligibilityTemplateDisbursement, HttpStatus.OK);
		} else {
			responseMessage.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * Returns the record by id
	 *
	 * @param tenantId the tenant id
	 * @param id       the id
	 * @return the EligibilityTemplateDisbursement Record.
	 */
	@GetMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> getEligibilityTemplateDisbursementById(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id) {
		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
		Optional<EligibilityTemplateDisbursement> isPresentEligibilityTemplateDisbursement = eligibilityTemplateDisbursementService
				.getById(id);
		if (isPresentEligibilityTemplateDisbursement.isPresent()) {
			return new ResponseEntity<>(isPresentEligibilityTemplateDisbursement.get(), HttpStatus.OK);
		} else {
			responseMessage.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	} 

	/**
	 * Returns the EligibilityTemplateLegalStructures by status
	 *
	 * @param tenantId the tenant id
	 * @param status   the status
	 * @return the EligibilityTemplateLegalStructures
	 */
	@GetMapping(value = "/{tenantId}/status/{status}")
	public ResponseEntity<Object> getEligibilityTemplateDisbursementByStatus(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "status", required = true) String status) {
		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();

		if (status.equals("ACTIVE") || status.equals("INACTIVE")) {
			List<EligibilityTemplateDisbursement> isPresentEligibilityTemplateDisbursement = eligibilityTemplateDisbursementService
					.getByStatus(status);
			int size = isPresentEligibilityTemplateDisbursement.size();
			if (size > 0) {
				return new ResponseEntity<>(isPresentEligibilityTemplateDisbursement, HttpStatus.OK);
			} else {
				responseMessage.setMessages(RECORD_NOT_FOUND);
				return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
			}
		} else {
			responseMessage.setMessages(environment.getProperty("invalid-status"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * Saves the given EligibilityTemplateLegalStructure object
	 *
	 * @param tenantId                             the tenant id
	 * @param EligibilityTemplateBranchAddResource the object to save
	 * @return the message
	 */
	@PostMapping(value = "/{tenantId}")
	public ResponseEntity<Object> addEligibilityTemplateDisbursement(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@Valid @RequestBody EligibilityTemplateDisbursementAddResource eligibilityTemplateAddResource) {

		if (LogginAuthentcation.getInstance().getUserName() == null
				|| LogginAuthentcation.getInstance().getUserName().isEmpty()) {
			throw new UserNotFound(environment.getProperty(COMMON_USER_NOT_FOUND));
		}

		EligibilityTemplateDisbursement eligibilityTemplateDisbursementType = eligibilityTemplateDisbursementService
				.addEligibilityTemplateDisbursment(tenantId, eligibilityTemplateAddResource);
		SuccessAndErrorDetails successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty("rec.saved"),
				Long.toString(eligibilityTemplateDisbursementType.getId()));
		return new ResponseEntity<>(successAndErrorDetails, HttpStatus.CREATED);
	}

	/**
	 * Updates the given EligibilityTemplateLegalStructure record
	 * 
	 * @param tenantId                                the tenant id
	 * @param id                                      the id
	 * @param EligibilityTemplateBranchUpdateResource object to update
	 * @return the
	 */
	@PutMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> updateEligibilityTemplateDisbursement(
			@PathVariable(value = "tenantId") String tenantId, @PathVariable(value = "id") Long id,
			@Valid @RequestBody EligibilityTemplateDisbursementUpdateResource eligibilityTemplateUpdateResource) {
		if (LogginAuthentcation.getInstance().getUserName() == null
				|| LogginAuthentcation.getInstance().getUserName().isEmpty()) {
			throw new UserNotFound(environment.getProperty(COMMON_USER_NOT_FOUND));
		}

		SuccessAndErrorDetails successAndErrorDetails = new SuccessAndErrorDetails();
		Optional<EligibilityTemplateDisbursement> isPresentEligibilityTemplateDisbursement = eligibilityTemplateDisbursementService
				.getById(id);
		if (isPresentEligibilityTemplateDisbursement.isPresent()) {
			if (isPresentEligibilityTemplateDisbursement.get().getApproveStatus() == null
					|| !isPresentEligibilityTemplateDisbursement.get().getApproveStatus()
							.equals(CommonApproveStatus.PENDING)) {
				EligibilityTemplateDisbursement eligibilityTemplateDisbursement = eligibilityTemplateDisbursementService
						.updateEligibilityTemplateDisbursment(tenantId, id, eligibilityTemplateUpdateResource);
				successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty("rec.updated"),
						eligibilityTemplateDisbursement.getId().toString());
				return new ResponseEntity<>(successAndErrorDetails, HttpStatus.OK);
			} else {
				successAndErrorDetails.setMessages(environment.getProperty("common.can-not-update"));
				return new ResponseEntity<>(successAndErrorDetails, HttpStatus.UNPROCESSABLE_ENTITY);
			}

		} else {
			successAndErrorDetails.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(successAndErrorDetails, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

	/**
	 * approve EligibilityTemplateDisbursement Pending
	 * 
	 * @param @PathVariable{tenantId}
	 * @param @PathVariable{pendingId}
	 * @return SuccessAndErrorDetailsDto
	 */
	@PutMapping(value = "{tenantId}/approve/{pendingId}")
	public ResponseEntity<Object> approve(@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "pendingId", required = true) Long pendingId) {

		if (LogginAuthentcation.getInstance().getUserName() == null
				|| LogginAuthentcation.getInstance().getUserName().isEmpty()) {
			throw new UserNotFound(environment.getProperty(COMMON_USER_NOT_FOUND));
		}
		SuccessAndErrorDetailsResource successAndErrorDetailsResource = new SuccessAndErrorDetailsResource();
		Optional<EligibilityTemplateDisbursementPending> isPresentEligiTempPending = eligibilityTemplateDisbursementService
				.getByPendingId(pendingId);
		if (isPresentEligiTempPending.isPresent()) {

			if (eligibilityTemplateDisbursementService.approveReject(tenantId, pendingId, WorkflowStatus.COMPLETE)) {
				successAndErrorDetailsResource = new SuccessAndErrorDetailsResource(
						environment.getProperty("common.approved"), pendingId.toString());
				return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.OK);
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
	 * Reject EligibilityTemplateDisbursement Pending
	 * 
	 * @param @PathVariable{tenantId}
	 * @param @PathVariable{pendingId}
	 * @return SuccessAndErrorDetailsDto
	 */
	@PutMapping(value = "{tenantId}/reject/{pendingId}")
	public ResponseEntity<Object> reject(@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "pendingId", required = true) Long pendingId) {

		if (LogginAuthentcation.getInstance().getUserName() == null
				|| LogginAuthentcation.getInstance().getUserName().isEmpty()) {
			throw new UserNotFound(environment.getProperty(COMMON_USER_NOT_FOUND));
		}

		SuccessAndErrorDetailsResource successAndErrorDetailsResource = new SuccessAndErrorDetailsResource();
		Optional<EligibilityTemplateDisbursementPending> isPresentEligiPending = eligibilityTemplateDisbursementService
				.getByPendingId(pendingId);
		if (isPresentEligiPending.isPresent()) {

			if (eligibilityTemplateDisbursementService.approveReject(tenantId, pendingId, WorkflowStatus.REJECT)) {
				successAndErrorDetailsResource = new SuccessAndErrorDetailsResource(
						environment.getProperty("common.rejected"), pendingId.toString());
				return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.OK);
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
	 * Gets the EligibilityTemplateDisbursement by pending id.
	 *
	 * @param tenantId  - the tenant id
	 * @param pendingId - the pending id
	 * @return EligibilityTemplateDisbursementPending
	 */
	@GetMapping(value = "/{tenantId}/pending/{pendingId}")
	public ResponseEntity<Object> getEligibilityTemplateDisbursementPendingByPendingId(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "pendingId", required = true) Long pendingId) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<EligibilityTemplateDisbursementPending> isPresentEligibilityTemplateDisbursementPending = eligibilityTemplateDisbursementService
				.getByPendingId(pendingId);
		if (isPresentEligibilityTemplateDisbursementPending.isPresent()) {
			return new ResponseEntity<>(isPresentEligibilityTemplateDisbursementPending, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * Search EligibilityTemplateDisbursement pending.
	 *
	 * @param tenantId      - the tenant id
	 * @param searchq       - the searchq
	 * @param status        - the status
	 * @param approveStatus - the approve status
	 * @param pageable      - the pageable
	 * @return the page
	 */
	@GetMapping(value = "/{tenantId}/workflow-items/search")
	public Page<EligibilityTemplateDisbursementPending> searchEligibilityTemplateDisbursementPending(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@RequestParam(value = "searchq", required = false) String searchq,
			@RequestParam(value = "status", required = false) String status,
			@RequestParam(value = "approveStatus", required = false) String approveStatus,
			@PageableDefault(size = 10) Pageable pageable) {
		if (pageable.getPageSize() > Constants.MAX_PAGEABLE_LENGTH)
			throw new PageableLengthException(environment.getProperty(PAGEABLE_LENGTH));
		return eligibilityTemplateDisbursementService.searchEligibilityTemplateDisbursementPending(searchq, status,
				approveStatus, pageable);
	}
}
