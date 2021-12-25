package com.fusionx.lending.origination.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
/**
 * Approval Category Details
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   13-09-2021   FXL-338 		 FXL-803 	Dilki        Created
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
import com.fusionx.lending.origination.domain.ApprovalCategoryDetails;
import com.fusionx.lending.origination.resource.ApprovalCategoryDetailsAddResource;
import com.fusionx.lending.origination.resource.ApprovalCategoryDetailsUpdateResource;
import com.fusionx.lending.origination.resource.SuccessAndErrorDetailsResource;
import com.fusionx.lending.origination.service.ApprovalCategoryDetailsService;

@RestController
@RequestMapping(value = "/approval-category-details")
@CrossOrigin(origins = "*")
public class ApprovalCategoryDetailsController extends MessagePropertyBase {

	@Autowired
	public ApprovalCategoryDetailsService approvalCategoryDetailsService;

	/**
	 * get all ApprovalCategoryDetails
	 * 
	 * @param @PathVariable{tenantId}
	 * @param @PathVariable{all}
	 * @return List<Type>
	 */
	@GetMapping(value = "/{tenantId}/all")
	public ResponseEntity<Object> getAllApprovalCategoryDetails(
			@PathVariable(value = "tenantId", required = true) String tenantId) {

		List<ApprovalCategoryDetails> approvalCategoryDetails = approvalCategoryDetailsService.getAll();
		int size = approvalCategoryDetails.size();
		if (size > 0) {
			return new ResponseEntity<>(approvalCategoryDetails, HttpStatus.OK);
		} else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * get ApprovalCategoryDetails by id
	 * 
	 * @param @PathVariable{tenantId}
	 * @param @PathVariable{id}
	 * @return Option
	 */
	@GetMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> getApprovalCategoryDetailsById(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id) {

		Optional<ApprovalCategoryDetails> isPresentApprovalCategoryDetails = approvalCategoryDetailsService.getById(id);
		if (isPresentApprovalCategoryDetails.isPresent()) {
			return new ResponseEntity<>(isPresentApprovalCategoryDetails.get(), HttpStatus.OK);
		} else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * get ApprovalCategoryDetails by code
	 * 
	 * @param @pathVariable{tenantId}
	 * @param @pathVariable           {code}
	 * @return Optional
	 **/
	@GetMapping(value = "/{tenantId}/code/{code}")
	public ResponseEntity<Object> getApprovalCategoryDetailsByCode(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "code", required = true) String code) {

		Optional<ApprovalCategoryDetails> isPresentApprovalCategoryDetails = approvalCategoryDetailsService
				.getByCode(code);
		if (isPresentApprovalCategoryDetails.isPresent()) {
			return new ResponseEntity<>(isPresentApprovalCategoryDetails.get(), HttpStatus.OK);
		} else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * get ApprovalCategoryDetails by name
	 * 
	 * @param @pathVariable{tenantId}
	 * @param @pathVariable           {name}
	 * @return List
	 **/
	@GetMapping(value = "/{tenantId}/name/{name}")
	public ResponseEntity<Object> getApprovalCategoryDetailsByName(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "name", required = true) String name) {

		List<ApprovalCategoryDetails> isPresentApprovalCategoryDetails = approvalCategoryDetailsService.getByName(name);
		if (!isPresentApprovalCategoryDetails.isEmpty()) {
			return new ResponseEntity<>(isPresentApprovalCategoryDetails, HttpStatus.OK);
		} else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * get ApprovalCategoryDetails by status
	 * 
	 * @param @PathVariable{tenantId}
	 * @param @PathVariable{status}
	 * @return Option
	 */
	@GetMapping(value = "/{tenantId}/status/{status}")
	public ResponseEntity<Object> getApprovalCategoryDetailsByStatus(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "status", required = true) String status) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();

		if (status.equals("ACTIVE") || status.equals("INACTIVE")) {
			List<ApprovalCategoryDetails> isPresentApprovalCategoryDetails = approvalCategoryDetailsService
					.getByStatus(status);
			int size = isPresentApprovalCategoryDetails.size();
			if (size > 0) {
				return new ResponseEntity<>(isPresentApprovalCategoryDetails, HttpStatus.OK);
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
	 * save ApprovalCategoryDetails
	 * 
	 * @param @PathVariable{tenantId}
	 * @param @RequestBody{ApprovalCategoryDetailsAddResource}
	 * @return SuccessAndErrorDetailsDto
	 */
	@PostMapping(value = "/{tenantId}")
	public ResponseEntity<Object> addApprovalCategoryDetails(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@Valid @RequestBody ApprovalCategoryDetailsAddResource approvalCategoryDetailsAddResource) {
		if (LogginAuthentcation.getInstance().getUserName() == null
				|| LogginAuthentcation.getInstance().getUserName().isEmpty()) {
			throw new UsernameNotFoundException(environment.getProperty(NOT_FOUND));
		}

		ApprovalCategoryDetails approvalCategoryDetails = approvalCategoryDetailsService
				.addApprovalCategoryDetails(tenantId, approvalCategoryDetailsAddResource);
		SuccessAndErrorDetailsResource successAndErrorDetails = new SuccessAndErrorDetailsResource(
				environment.getProperty(RECORD_CREATED), Long.toString(approvalCategoryDetails.getId()));
		return new ResponseEntity<>(successAndErrorDetails, HttpStatus.CREATED);
	}

	/**
	 * update ApprovalCategoryDetails
	 * 
	 * @param @PathVariable{tenantId}
	 * @param @RequestBody{ApprovalCategoryDetailsUpdateResource}
	 * @return SuccessAndErrorDetailsDto
	 */
	@PutMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> updateApprovalCategoryDetails(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id,
			@Valid @RequestBody ApprovalCategoryDetailsUpdateResource approvalCategoryDetailsUpdateResource) {
		if (LogginAuthentcation.getInstance().getUserName() == null
				|| LogginAuthentcation.getInstance().getUserName().isEmpty()) {
			throw new UsernameNotFoundException(environment.getProperty(NOT_FOUND));
		}
		ApprovalCategoryDetails approvalCategoryDetails = approvalCategoryDetailsService
				.updateApprovalCategoryDetails(tenantId, id, approvalCategoryDetailsUpdateResource);
		SuccessAndErrorDetailsResource successAndErrorDetails = new SuccessAndErrorDetailsResource(
				environment.getProperty(RECORD_UPDATED), approvalCategoryDetails.getId().toString());
		return new ResponseEntity<>(successAndErrorDetails, HttpStatus.OK);

	}
}
