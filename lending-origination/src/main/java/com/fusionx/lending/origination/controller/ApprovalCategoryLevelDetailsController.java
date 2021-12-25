package com.fusionx.lending.origination.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
/**
 * Approval Category LevelDetails
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   14-09-2021    		 	 FXL-823 	Dilki        Created
 *    
 ********************************************************************************************************
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fusionx.lending.origination.base.MessagePropertyBase;
import com.fusionx.lending.origination.core.LogginAuthentcation;
import com.fusionx.lending.origination.domain.ApprovalCategoryLevelDetails;
import com.fusionx.lending.origination.resource.ApprovalCategoryLevelDetailsAddResource;
import com.fusionx.lending.origination.resource.ApprovalCategoryLevelDetailsUpdateResource;
import com.fusionx.lending.origination.resource.SuccessAndErrorDetailsResource;
import com.fusionx.lending.origination.service.ApprovalCategoryLevelDetailsService;

@RestController
@RequestMapping(value = "/approval-category-level-details")
@CrossOrigin(origins = "*")
public class ApprovalCategoryLevelDetailsController extends MessagePropertyBase {

	@Autowired
	public ApprovalCategoryLevelDetailsService approvalCategoryLevelDetailsService;

	/**
	 * get all ApprovalCategoryLevelDetails
	 * 
	 * @param @PathVariable{tenantId}
	 * @param @PathVariable{all}
	 * @return List<Type>
	 */
	@GetMapping(value = "/{tenantId}/all")
	public ResponseEntity<Object> getAllApprovalCategoryLevelDetails(
			@PathVariable(value = "tenantId", required = true) String tenantId) {

		List<ApprovalCategoryLevelDetails> approvalCategoryLevelDetails = approvalCategoryLevelDetailsService.getAll();
		int size = approvalCategoryLevelDetails.size();
		if (size > 0) {
			return new ResponseEntity<>(approvalCategoryLevelDetails, HttpStatus.OK);
		} else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * get ApprovalCategoryLevelDetails by ApprovalCategoryDetailsId
	 * 
	 * @param @PathVariable{tenantId}
	 * @param @PathVariable{approvalCategoryDetailsId}
	 * @return List<Type>
	 */
	@GetMapping(value = "/{tenantId}/approvalCategoryDetailsId/{approvalCategoryDetailsId}")
	public ResponseEntity<Object> getByApprovalCategoryDetailsId(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "approvalCategoryDetailsId", required = true) Long approvalCategoryDetailsId) {

		List<ApprovalCategoryLevelDetails> approvalCategoryLevelDetails = approvalCategoryLevelDetailsService
				.getByApprovalCategoryDetailsId(approvalCategoryDetailsId);
		int size = approvalCategoryLevelDetails.size();
		if (size > 0) {
			return new ResponseEntity<>(approvalCategoryLevelDetails, HttpStatus.OK);
		} else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * get ApprovalCategoryLevelDetails by id
	 * 
	 * @param @PathVariable{tenantId}
	 * @param @PathVariable{id}
	 * @return Option
	 */
	@GetMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> getApprovalCategoryLevelDetailsById(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id) {

		Optional<ApprovalCategoryLevelDetails> isPresentApprovalCategoryLevelDetails = approvalCategoryLevelDetailsService
				.getById(id);
		if (isPresentApprovalCategoryLevelDetails.isPresent()) {
			return new ResponseEntity<>(isPresentApprovalCategoryLevelDetails.get(), HttpStatus.OK);
		} else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * get ApprovalCategoryLevelDetails by code
	 * 
	 * @param @pathVariable{tenantId}
	 * @param @pathVariable           {code}
	 * @return Optional
	 **/
	@GetMapping(value = "/{tenantId}/code/{code}")
	public ResponseEntity<Object> getApprovalCategoryLevelDetailsByCode(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "code", required = true) String code) {

		Optional<ApprovalCategoryLevelDetails> isPresentApprovalCategoryLevelDetails = approvalCategoryLevelDetailsService
				.getByCode(code);
		if (isPresentApprovalCategoryLevelDetails.isPresent()) {
			return new ResponseEntity<>(isPresentApprovalCategoryLevelDetails.get(), HttpStatus.OK);
		} else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * get ApprovalCategoryLevelDetails by name
	 * 
	 * @param @pathVariable{tenantId}
	 * @param @pathVariable           {name}
	 * @return List
	 **/
	@GetMapping(value = "/{tenantId}/name/{name}")
	public ResponseEntity<Object> getApprovalCategoryLevelDetailsByName(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "name", required = true) String name) {

		List<ApprovalCategoryLevelDetails> isPresentApprovalCategoryLevelDetails = approvalCategoryLevelDetailsService
				.getByName(name);
		if (!isPresentApprovalCategoryLevelDetails.isEmpty()) {
			return new ResponseEntity<>(isPresentApprovalCategoryLevelDetails, HttpStatus.OK);
		} else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * get ApprovalCategoryLevelDetails by status
	 * 
	 * @param @PathVariable{tenantId}
	 * @param @PathVariable{status}
	 * @return Option
	 */
	@GetMapping(value = "/{tenantId}/status/{status}")
	public ResponseEntity<Object> getApprovalCategoryLevelDetailsByStatus(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "status", required = true) String status) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();

		if (status.equals("ACTIVE") || status.equals("INACTIVE")) {
			List<ApprovalCategoryLevelDetails> isPresentApprovalCategoryLevelDetails = approvalCategoryLevelDetailsService
					.getByStatus(status);
			int size = isPresentApprovalCategoryLevelDetails.size();
			if (size > 0) {
				return new ResponseEntity<>(isPresentApprovalCategoryLevelDetails, HttpStatus.OK);
			} else {
				responseMessage.setMessages(RECORD_NOT_FOUND);
				return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
			}
		} else {
			responseMessage.setMessages(environment.getProperty(COMMON_INVALID_VALUE));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * save ApprovalCategoryLevelDetails
	 * 
	 * @param @PathVariable{tenantId}
	 * @param @RequestBody{CommonAddResource}
	 * @return SuccessAndErrorDetailsDto
	 */
	@PostMapping(value = "/{tenantId}")
	public ResponseEntity<Object> addApprovalCategoryLevelDetails(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@Valid @RequestBody ApprovalCategoryLevelDetailsAddResource approvalCategoryLevelDetailsAddResource) {
		if (LogginAuthentcation.getInstance().getUserName() == null
				|| LogginAuthentcation.getInstance().getUserName().isEmpty()) {
			throw new UsernameNotFoundException(environment.getProperty(NOT_FOUND));
		}

		ApprovalCategoryLevelDetails approvalCategoryLevelDetails = approvalCategoryLevelDetailsService
				.addApprovalCategoryLevelDetails(tenantId, approvalCategoryLevelDetailsAddResource);
		SuccessAndErrorDetailsResource successAndErrorDetails = new SuccessAndErrorDetailsResource(
				environment.getProperty(RECORD_CREATED), Long.toString(approvalCategoryLevelDetails.getId()));
		return new ResponseEntity<>(successAndErrorDetails, HttpStatus.CREATED);
	}

	/**
	 * update ApprovalCategoryLevelDetails
	 * 
	 * @param @PathVariable{tenantId}
	 * @param @RequestBody{CommonUpdateResource}
	 * @return SuccessAndErrorDetailsDto
	 */
	@PutMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> updateApprovalCategoryLevelDetails(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id,
			@Valid @RequestBody ApprovalCategoryLevelDetailsUpdateResource approvalCategoryLevelDetailsUpdateResource) {
		if (LogginAuthentcation.getInstance().getUserName() == null
				|| LogginAuthentcation.getInstance().getUserName().isEmpty()) {
			throw new UsernameNotFoundException(environment.getProperty(NOT_FOUND));
		}
		ApprovalCategoryLevelDetails approvalCategoryLevelDetails = approvalCategoryLevelDetailsService
				.updateApprovalCategoryLevelDetails(tenantId, id, approvalCategoryLevelDetailsUpdateResource);
		SuccessAndErrorDetailsResource successAndErrorDetails = new SuccessAndErrorDetailsResource(
				environment.getProperty(RECORD_UPDATED), approvalCategoryLevelDetails.getId().toString());
		return new ResponseEntity<>(successAndErrorDetails, HttpStatus.OK);
	}
}
