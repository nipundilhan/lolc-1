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
import com.fusionx.lending.origination.domain.UserMappingDetails;
import com.fusionx.lending.origination.resource.UserMappingDetailsAddResource;
import com.fusionx.lending.origination.resource.UserMappingDetailsUpdateResource;
import com.fusionx.lending.origination.resource.SuccessAndErrorDetailsResource;
import com.fusionx.lending.origination.service.ExceptionApprovalGroupUserMappingDetailsService;

@RestController
@RequestMapping(value = "/exception-approval-group-user-mapping-details")
@CrossOrigin(origins = "*")
public class ExceptionApprovalGroupUserMappingDetailsController extends MessagePropertyBase {

	@Autowired
	public ExceptionApprovalGroupUserMappingDetailsService userMappingDetailsService;

	/**
	 * get all UserMappingDetails
	 * 
	 * @param @PathVariable{tenantId}
	 * @param @PathVariable{all}
	 * @return List<Type>
	 */
	@GetMapping(value = "/{tenantId}/all")
	public ResponseEntity<Object> getAllUserMappingDetails(@PathVariable(value = "tenantId") String tenantId) {

		List<UserMappingDetails> userMappingDetails = userMappingDetailsService.getAll();
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

		Optional<UserMappingDetails> isPresentUserMappingDetails = userMappingDetailsService.getById(id);
		if (isPresentUserMappingDetails.isPresent()) {
			return new ResponseEntity<>(isPresentUserMappingDetails.get(), HttpStatus.OK);
		} else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * Gets the UserMappingDetails by approvalCategoryId.
	 *
	 * @param tenantId
	 * @param status
	 * @return the UserMappingDetails by approvalCategoryId
	 */
	@GetMapping(value = "/{tenantId}/approval-group/{approvalGroupId}")
	public ResponseEntity<Object> getUserMappingDetailsByCustId(@PathVariable(value = "tenantId") String tenantId,
			@PathVariable(value = "approvalGroupId") Long approvalGroupId) {

		List<UserMappingDetails> userMappingDetails = userMappingDetailsService
				.getByApprovalCategoryId(approvalGroupId);
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
			List<UserMappingDetails> isPresentUserMappingDetails = userMappingDetailsService.getByStatus(status);
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
	 * @param @RequestBody{UserMappingDetailsAddResource}
	 * @return SuccessAndErrorDetailsDto
	 */
	@PostMapping(value = "/{tenantId}")
	public ResponseEntity<Object> addUserMappingDetails(@PathVariable(value = "tenantId") String tenantId,
			@Valid @RequestBody UserMappingDetailsAddResource commonAddResource) {
		if (LogginAuthentcation.getInstance().getUserName() == null
				|| LogginAuthentcation.getInstance().getUserName().isEmpty()) {
			throw new UsernameNotFoundException(environment.getProperty(NOT_FOUND));
		}
		UserMappingDetails userMappingDetails = userMappingDetailsService.addUserMappingDetails(tenantId,
				commonAddResource);
		SuccessAndErrorDetailsResource successAndErrorDetails = new SuccessAndErrorDetailsResource(
				environment.getProperty(RECORD_CREATED), Long.toString(userMappingDetails.getId()));
		return new ResponseEntity<>(successAndErrorDetails, HttpStatus.CREATED);
	}

	/**
	 * update UserMappingDetails
	 * 
	 * @param @PathVariable{tenantId}
	 * @param @RequestBody{UserMappingDetailsUpdateResource}
	 * @return SuccessAndErrorDetailsDto
	 */
	@PutMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> updateUserMappingDetails(@PathVariable(value = "tenantId") String tenantId,
			@PathVariable(value = "id") Long id,
			@Valid @RequestBody UserMappingDetailsUpdateResource commonUpdateResource) {
		if (LogginAuthentcation.getInstance().getUserName() == null
				|| LogginAuthentcation.getInstance().getUserName().isEmpty()) {
			throw new UsernameNotFoundException(environment.getProperty(NOT_FOUND));
		}

		SuccessAndErrorDetailsResource successAndErrorDetails = new SuccessAndErrorDetailsResource();
		Optional<UserMappingDetails> isPresentUserMappingDetails = userMappingDetailsService.getById(id);
		if (isPresentUserMappingDetails.isPresent()) {
			UserMappingDetails userMappingDetails = userMappingDetailsService.updateUserMappingDetails(tenantId, id,
					commonUpdateResource);
			successAndErrorDetails = new SuccessAndErrorDetailsResource(environment.getProperty(RECORD_UPDATED),
					userMappingDetails.getId().toString());
			return new ResponseEntity<>(successAndErrorDetails, HttpStatus.OK);
		} else {
			successAndErrorDetails.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(successAndErrorDetails, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
}
