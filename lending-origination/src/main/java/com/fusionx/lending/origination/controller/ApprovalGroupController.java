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
import com.fusionx.lending.origination.domain.ApprovalGroup;
import com.fusionx.lending.origination.resource.ApprovalGroupAddResource;
import com.fusionx.lending.origination.resource.ApprovalGroupUpdateResource;
import com.fusionx.lending.origination.resource.SuccessAndErrorDetailsResource;
import com.fusionx.lending.origination.service.ApprovalGroupService;
/**
 * Approval Group
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   27-09-2021   FXL-78 		 FXL-977 	Dilki        Created
 *    
 ********************************************************************************************************
 */
@RestController
@RequestMapping(value = "/approval-group")
@CrossOrigin(origins = "*")
public class ApprovalGroupController extends MessagePropertyBase {

	@Autowired
	public ApprovalGroupService approvalGroupService;

	/**
	 * get all ApprovalGroup
	 * 
	 * @param @PathVariable{tenantId}
	 * @param @PathVariable{all}
	 * @return List<Type>
	 */
	@GetMapping(value = "/{tenantId}/all")
	public ResponseEntity<Object> getAllApprovalGroup(
			@PathVariable(value = "tenantId", required = true) String tenantId) {

		List<ApprovalGroup> approvalGroup = approvalGroupService.getAll();
		int size = approvalGroup.size();
		if (size > 0) {
			return new ResponseEntity<>(approvalGroup, HttpStatus.OK);
		} else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * get ApprovalGroup by id
	 * 
	 * @param @PathVariable{tenantId}
	 * @param @PathVariable{id}
	 * @return Option
	 */
	@GetMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> getApprovalGroupById(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id) {

		Optional<ApprovalGroup> isPresentApprovalGroup = approvalGroupService.getById(id);
		if (isPresentApprovalGroup.isPresent()) {
			return new ResponseEntity<>(isPresentApprovalGroup.get(), HttpStatus.OK);
		} else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * get ApprovalGroup by code
	 * 
	 * @param @pathVariable{tenantId}
	 * @param @pathVariable           {code}
	 * @return Optional
	 **/
	@GetMapping(value = "/{tenantId}/code/{code}")
	public ResponseEntity<Object> getApprovalGroupByCode(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "code", required = true) String code) {

		Optional<ApprovalGroup> isPresentApprovalGroup = approvalGroupService
				.getByCode(code);
		if (isPresentApprovalGroup.isPresent()) {
			return new ResponseEntity<>(isPresentApprovalGroup.get(), HttpStatus.OK);
		} else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * get ApprovalGroup by name
	 * 
	 * @param @pathVariable{tenantId}
	 * @param @pathVariable           {name}
	 * @return List
	 **/
	@GetMapping(value = "/{tenantId}/name/{name}")
	public ResponseEntity<Object> getApprovalGroupByName(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "name", required = true) String name) {

		List<ApprovalGroup> isPresentApprovalGroup = approvalGroupService.getByName(name);
		if (!isPresentApprovalGroup.isEmpty()) {
			return new ResponseEntity<>(isPresentApprovalGroup, HttpStatus.OK);
		} else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * get ApprovalGroup by status
	 * 
	 * @param @PathVariable{tenantId}
	 * @param @PathVariable{status}
	 * @return Option
	 */
	@GetMapping(value = "/{tenantId}/status/{status}")
	public ResponseEntity<Object> getApprovalGroupByStatus(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "status", required = true) String status) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();

		if (status.equals("ACTIVE") || status.equals("INACTIVE")) {
			List<ApprovalGroup> isPresentApprovalGroup = approvalGroupService
					.getByStatus(status);
			int size = isPresentApprovalGroup.size();
			if (size > 0) {
				return new ResponseEntity<>(isPresentApprovalGroup, HttpStatus.OK);
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
	 * save ApprovalGroup
	 * 
	 * @param @PathVariable{tenantId}
	 * @param @RequestBody{CommonAddResource}
	 * @return SuccessAndErrorDetailsDto
	 */
	@PostMapping(value = "/{tenantId}")
	public ResponseEntity<Object> addApprovalGroup(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@Valid @RequestBody ApprovalGroupAddResource approvalGroupAddResource) {
		if (LogginAuthentcation.getInstance().getUserName() == null
				|| LogginAuthentcation.getInstance().getUserName().isEmpty()) {
			throw new UsernameNotFoundException(environment.getProperty(NOT_FOUND));
		}

		ApprovalGroup approvalGroup = approvalGroupService
				.addApprovalGroup(tenantId, approvalGroupAddResource);
		SuccessAndErrorDetailsResource successAndErrorDetails = new SuccessAndErrorDetailsResource(
				environment.getProperty(RECORD_CREATED), Long.toString(approvalGroup.getId()));
		return new ResponseEntity<>(successAndErrorDetails, HttpStatus.CREATED);
	}

	/**
	 * update ApprovalGroup
	 * 
	 * @param @PathVariable{tenantId}
	 * @param @RequestBody{CommonUpdateResource}
	 * @return SuccessAndErrorDetailsDto
	 */
	@PutMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> updateApprovalGroup(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id,
			@Valid @RequestBody ApprovalGroupUpdateResource approvalGroupUpdateResource) {
		if (LogginAuthentcation.getInstance().getUserName() == null
				|| LogginAuthentcation.getInstance().getUserName().isEmpty()) {
			throw new UsernameNotFoundException(environment.getProperty(NOT_FOUND));
		}
		ApprovalGroup approvalGroup = approvalGroupService
				.updateApprovalGroup(tenantId, id, approvalGroupUpdateResource);
		SuccessAndErrorDetailsResource successAndErrorDetails = new SuccessAndErrorDetailsResource(
				environment.getProperty(RECORD_UPDATED), approvalGroup.getId().toString());
		return new ResponseEntity<>(successAndErrorDetails, HttpStatus.OK);
	}
}
