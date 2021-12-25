package com.fusionx.lending.product.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.enums.WorkflowStatus;
import com.fusionx.lending.product.exception.UserNotFound;
import com.fusionx.lending.product.resources.SubProductPenalInterestResource;
import com.fusionx.lending.product.resources.SuccessAndErrorDetails;
import com.fusionx.lending.product.resources.SuccessAndErrorDetailsResource;
import com.fusionx.lending.product.service.SubProductPenalInterestService;

/**
 * API Service related to SubProduct Penal Interest.
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
 * 1        30-08-2021      -               -          Dilhan Jayasinghe      Created
 * <p>
 *
 */
@RestController
@RequestMapping(value = "/subproduct-penal-interest")
@CrossOrigin(origins = "*")
public class SubProductPenalInterestController extends MessagePropertyBase{

	@Autowired
	public SubProductPenalInterestService subProductPenalInterestService;

	/**
	 * Update SubProduct by adding Penal Interest Template
	 *
	 * @param @PathVariable{tenantId}
	 * @param @PathVariable{subProductId}
	 * @return SuccessAndErrorDetailsDto
	 */
	@PutMapping(value = "/{tenantId}/sub-product/{subProductId}")
	public ResponseEntity<Object> updateMasterDefinition(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "subProductId", required = true) Long subProductId,
			@Valid @RequestBody SubProductPenalInterestResource subProductPenalInterestResource) {

		if (LogginAuthentcation.getInstance().getUserName() == null
				|| LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new UserNotFound(environment.getProperty(COMMON_USER_NOT_FOUND));

		SuccessAndErrorDetails successAndErrorDetails = new SuccessAndErrorDetails();
		Long id = subProductPenalInterestService.updateSubProductWithInterestTemplate(tenantId, subProductId,
				subProductPenalInterestResource);
		successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty("rec.updated"),
				Long.toString(id));
		return new ResponseEntity<>(successAndErrorDetails, HttpStatus.CREATED);
	}
		
	
	/**
	 * Approve SubProduct.
	 * 
	 * @param tenantId - the tenant id
	 * @param pendingSubProductId
	 * @return the response entity
	 */
	@PutMapping(value = "{tenantId}/pending/{pendingSubProductId}/approve")
	public ResponseEntity<Object> approveSubProduct(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "pendingSubProductId", required = true) Long pendingSubProductId) {

		if (LogginAuthentcation.getInstance().getUserName() == null
				|| LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new UserNotFound(environment.getProperty(COMMON_USER_NOT_FOUND));

		SuccessAndErrorDetailsResource successAndErrorDetailsResource = new SuccessAndErrorDetailsResource();
		if (subProductPenalInterestService.approveReject(tenantId, pendingSubProductId, WorkflowStatus.COMPLETE)) {
			successAndErrorDetailsResource = new SuccessAndErrorDetailsResource(
					environment.getProperty("common.approved"), pendingSubProductId.toString());
			return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.OK);
		} else {
			successAndErrorDetailsResource.setMessages(environment.getProperty("common.can-not-approved"));
			return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.UNPROCESSABLE_ENTITY);
		}

	}		

	/**
	 * Reject SubProduct.
	 * 
	 * @param tenantId  - the tenant id
	 * @param pendingId - the pending id
	 * @return the response entity
	 */
	@PutMapping(value = "{tenantId}/pending/{pendingSubProductId}/reject")
	public ResponseEntity<Object> rejectSubProduct(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "pendingSubProductId", required = true) Long pendingSubProductId) {

		if (LogginAuthentcation.getInstance().getUserName() == null
				|| LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new UserNotFound(environment.getProperty(COMMON_USER_NOT_FOUND));

		SuccessAndErrorDetailsResource successAndErrorDetailsResource = new SuccessAndErrorDetailsResource();

		if (subProductPenalInterestService.approveReject(tenantId, pendingSubProductId, WorkflowStatus.REJECT)) {
			successAndErrorDetailsResource = new SuccessAndErrorDetailsResource(
					environment.getProperty("common.rejected"), pendingSubProductId.toString());
			return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.OK);
		} else {
			successAndErrorDetailsResource.setMessages(environment.getProperty("common.can-not-rejected"));
			return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.UNPROCESSABLE_ENTITY);
		}

	}

 }		
