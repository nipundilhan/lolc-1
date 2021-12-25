package com.fusionx.lending.product.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.MasterDefinition;
import com.fusionx.lending.product.domain.MasterDefinitionPending;
import com.fusionx.lending.product.enums.WorkflowStatus;
import com.fusionx.lending.product.exception.UserNotFound;
import com.fusionx.lending.product.resources.MasterPenalInterestResource;
import com.fusionx.lending.product.resources.SuccessAndErrorDetails;
import com.fusionx.lending.product.resources.SuccessAndErrorDetailsResource;
import com.fusionx.lending.product.service.MasterInterestService;

/**
 * API Service related to Master Interest.
 *
 * @authorDilhan Jayasinghe
 * @version 1.0.0
 * @since 1.0.0
 * <p>
 * <br/><br/>
 * <b>Change History : </b>
 * <br/><br/>
 * #        Date            Story Point     Task No     Author                  Description
 * <br/>
 * .....................................................................................................................<br/>
 * 1        25-08-2021      -               -          Dilhan Jayasinghe      Created
 * <p>
 *
 */
@RestController
@RequestMapping(value = "/master-interest")
@CrossOrigin(origins = "*")
public class MasterInterestController extends MessagePropertyBase{

	@Autowired
	public MasterInterestService masterInterestService;

	/**
	 * Get the Master Currency by id.
	 *
	 * @param @PathVariable{tenantId}
	 * @param @PathVariable{id}
	 * @return Master Currency
	 */
	@GetMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> getMasterDefinitionById(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id) {
		Optional<MasterDefinition> isPresentMasterDefinition = masterInterestService.getById(id);
		if (isPresentMasterDefinition.isPresent()) {
			return new ResponseEntity<>(isPresentMasterDefinition, HttpStatus.OK);
		} else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(environment.getProperty("record-not-found"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}		

	/**
	 * Update Master Definition by adding Interest Template
	 *
	 * @param @PathVariable{tenantId}
	 * @param @PathVariable{masterDefinitionId}
	 * @return SuccessAndErrorDetailsDto
	 */
	@PutMapping(value = "/{tenantId}/master-definition/{masterDefinitionId}")
	public ResponseEntity<Object> updateMasterDefinition(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "masterDefinitionId", required = true) Long masterDefinitionId,
			@Valid @RequestBody MasterPenalInterestResource masterPenalInterestResource) {

		if (LogginAuthentcation.getInstance().getUserName() == null
				|| LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new UserNotFound(environment.getProperty(COMMON_USER_NOT_FOUND));

		SuccessAndErrorDetails successAndErrorDetails = new SuccessAndErrorDetails();
		Long id = masterInterestService.updateMasterDefinitionWithInterestTemplate(tenantId, masterDefinitionId,
				masterPenalInterestResource);
		successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty("rec.updated"),
				Long.toString(id));
		return new ResponseEntity<>(successAndErrorDetails, HttpStatus.CREATED);
	}
		
	/**
	 * Gets Master Definition by pending id.
	 *
	 * @param tenantId  - the tenant id
	 * @param pendingId - the pending id
	 * @return Master Currency by pending id
	 */
	@GetMapping(value = "/{tenantId}/pending/{pendingId}")
	public ResponseEntity<Object> getMasterDefinitionByPendingId(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "pendingId", required = true) Long pendingId) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<MasterDefinitionPending> isPresentMasterDefinitionPending = masterInterestService
				.getByPendingId(pendingId);
		if (isPresentMasterDefinitionPending.isPresent()) {
			return new ResponseEntity<>(isPresentMasterDefinitionPending, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty("record-not-found"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Approve Master Definition.
	 * 
	 * @param tenantId - the tenant id
	 * @param pendingMasterDefinitionId
	 * @return the response entity
	 */
	@PutMapping(value = "{tenantId}/pending/{pendingMasterDefinitionId}/approve")
	public ResponseEntity<Object> approveMasterDefinition(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "pendingMasterDefinitionId", required = true) Long pendingMasterDefinitionId) {

		if (LogginAuthentcation.getInstance().getUserName() == null
				|| LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new UserNotFound(environment.getProperty(COMMON_USER_NOT_FOUND));

		SuccessAndErrorDetailsResource successAndErrorDetailsResource = new SuccessAndErrorDetailsResource();
		if (masterInterestService.approveReject(tenantId, pendingMasterDefinitionId, WorkflowStatus.COMPLETE)) {
			successAndErrorDetailsResource = new SuccessAndErrorDetailsResource(
					environment.getProperty("common.approved"), pendingMasterDefinitionId.toString());
			return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.OK);
		} else {
			successAndErrorDetailsResource.setMessages(environment.getProperty("common.can-not-approved"));
			return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.UNPROCESSABLE_ENTITY);
		}

	}		

	/**
	 * Reject Master Definition.
	 * 
	 * @param tenantId  - the tenant id
	 * @param pendingId - the pending id
	 * @return the response entity
	 */
	@PutMapping(value = "{tenantId}/pending/{pendingMasterDefinitionId}/reject")
	public ResponseEntity<Object> rejectMasterDefinition(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "pendingMasterDefinitionId", required = true) Long pendingMasterDefinitionId) {

		if (LogginAuthentcation.getInstance().getUserName() == null
				|| LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new UserNotFound(environment.getProperty(COMMON_USER_NOT_FOUND));

		SuccessAndErrorDetailsResource successAndErrorDetailsResource = new SuccessAndErrorDetailsResource();

		if (masterInterestService.approveReject(tenantId, pendingMasterDefinitionId, WorkflowStatus.REJECT)) {
			successAndErrorDetailsResource = new SuccessAndErrorDetailsResource(
					environment.getProperty("common.rejected"), pendingMasterDefinitionId.toString());
			return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.OK);
		} else {
			successAndErrorDetailsResource.setMessages(environment.getProperty("common.can-not-rejected"));
			return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.UNPROCESSABLE_ENTITY);
		}

	}

 }		
