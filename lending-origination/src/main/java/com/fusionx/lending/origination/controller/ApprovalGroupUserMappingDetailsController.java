package com.fusionx.lending.origination.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

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
import com.fusionx.lending.origination.domain.ApprovalGroupUserMappingDetails;
import com.fusionx.lending.origination.resource.ApprovalGroupUserMappingDetailsAddResource;
import com.fusionx.lending.origination.resource.ApprovalGroupUserMappingDetailsUpdateResource;
import com.fusionx.lending.origination.resource.SuccessAndErrorDetailsResource;
import com.fusionx.lending.origination.service.ApprovalGroupUserMappingDetailsService;

@RestController
@RequestMapping(value = "/approval-group-user-mapping-details")
@CrossOrigin(origins = "*")
public class ApprovalGroupUserMappingDetailsController extends MessagePropertyBase {

	@Autowired
	public ApprovalGroupUserMappingDetailsService userMappingDetailsService;

	/**
	 * get all UserMappingDetails
	 * 
	 * @param @PathVariable{tenantId}
	 * @param @PathVariable{all}
	 * @return List<Type>
	 */
	@GetMapping(value = "/{tenantId}/all")
	public ResponseEntity<Object> getAllUserMappingDetails(@PathVariable(value = "tenantId") String tenantId) {

		List<ApprovalGroupUserMappingDetails> userMappingDetails = userMappingDetailsService.getAll();
		int size = userMappingDetails.size();
		if (size > 0) {
			return new ResponseEntity<>(userMappingDetails, HttpStatus.OK);
		} else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * get UserMappingDetails by id
	 * 
	 * @param @PathVariable{tenantId}
	 * @param @PathVariable{id}
	 * @return Option
	 */
	@GetMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> getUserMappingDetailsById(@PathVariable(value = "tenantId") String tenantId,
			@PathVariable(value = "id") Long id) {

		Optional<ApprovalGroupUserMappingDetails> isPresentUserMappingDetails = userMappingDetailsService.getById(id);
		if (isPresentUserMappingDetails.isPresent()) {
			return new ResponseEntity<>(isPresentUserMappingDetails.get(), HttpStatus.OK);
		} else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * Gets the UserMappingDetails by approvalGroupId.
	 *
	 * @param tenantId
	 * @param status
	 * @return the UserMappingDetails by approvalCategoryId
	 */
	@GetMapping(value = "/{tenantId}/approvalCategoryId/{approvalCategoryId}")
	public ResponseEntity<Object> getUserMappingDetailsByCustId(@PathVariable(value = "tenantId") String tenantId,
			@PathVariable(value = "approvalCategoryId") Long approvalCategoryId) {

		List<ApprovalGroupUserMappingDetails> userMappingDetails = userMappingDetailsService
				.getByApprovalCategoryId(approvalCategoryId);
		if (!userMappingDetails.isEmpty()) {
			return new ResponseEntity<>(userMappingDetails, HttpStatus.OK);
		} else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(environment.getProperty(NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * get UserMappingDetails by status
	 * 
	 * @param @PathVariable{tenantId}
	 * @param @PathVariable{status}
	 * @return Option
	 */
	@GetMapping(value = "/{tenantId}/status/{status}")
	public ResponseEntity<Object> getUserMappingDetailsByStatus(@PathVariable(value = "tenantId") String tenantId,
			@PathVariable(value = "status") String status) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();

		if (status.equals("ACTIVE") || status.equals("INACTIVE")) {
			List<ApprovalGroupUserMappingDetails> isPresentUserMappingDetails = userMappingDetailsService
					.getByStatus(status);
			int size = isPresentUserMappingDetails.size();
			if (size > 0) {
				return new ResponseEntity<>(isPresentUserMappingDetails, HttpStatus.OK);
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
	 * save UserMappingDetails
	 * 
	 * @param @PathVariable{tenantId}
	 * @param @RequestBody{approvalGroupUserMappingDetailsAddResource}
	 * @return SuccessAndErrorDetailsDto
	 */
	@PostMapping(value = "/{tenantId}")
	public ResponseEntity<Object> addUserMappingDetails(@PathVariable(value = "tenantId") String tenantId,
			@Valid @RequestBody ApprovalGroupUserMappingDetailsAddResource approvalGroupUserMappingDetailsAddResource) {
		if (LogginAuthentcation.getInstance().getUserName() == null
				|| LogginAuthentcation.getInstance().getUserName().isEmpty()) {
			throw new UsernameNotFoundException(environment.getProperty(NOT_FOUND));
		}
		ApprovalGroupUserMappingDetails userMappingDetails = userMappingDetailsService.addUserMappingDetails(tenantId,
				approvalGroupUserMappingDetailsAddResource);
		SuccessAndErrorDetailsResource successAndErrorDetails = new SuccessAndErrorDetailsResource(
				environment.getProperty(RECORD_CREATED), Long.toString(userMappingDetails.getId()));
		return new ResponseEntity<>(successAndErrorDetails, HttpStatus.CREATED);
	}

	/**
	 * update UserMappingDetails
	 * 
	 * @param @PathVariable{tenantId}
	 * @param @RequestBody{approvalGroupUserMappingDetailsUpdateResource}
	 * @return SuccessAndErrorDetailsDto
	 */
	@PutMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> updateUserMappingDetails(@PathVariable(value = "tenantId") String tenantId,
			@PathVariable(value = "id") Long id,
			@Valid @RequestBody ApprovalGroupUserMappingDetailsUpdateResource approvalGroupUserMappingDetailsUpdateResource) {
		if (LogginAuthentcation.getInstance().getUserName() == null
				|| LogginAuthentcation.getInstance().getUserName().isEmpty()) {
			throw new UsernameNotFoundException(environment.getProperty(NOT_FOUND));
		}

		SuccessAndErrorDetailsResource successAndErrorDetails = new SuccessAndErrorDetailsResource();
		Optional<ApprovalGroupUserMappingDetails> isPresentUserMappingDetails = userMappingDetailsService.getById(id);
		if (isPresentUserMappingDetails.isPresent()) {
			ApprovalGroupUserMappingDetails userMappingDetails = userMappingDetailsService
					.updateUserMappingDetails(tenantId, id, approvalGroupUserMappingDetailsUpdateResource);
			successAndErrorDetails = new SuccessAndErrorDetailsResource(environment.getProperty(RECORD_UPDATED),
					userMappingDetails.getId().toString());
			return new ResponseEntity<>(successAndErrorDetails, HttpStatus.OK);
		} else {
			successAndErrorDetails.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(successAndErrorDetails, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
}
