package com.fusionx.lending.origination.controller;

/**
 * Check List Template
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   17-08-2021   FXL-75  		 FXL-551 	Dilki        Created
 *    
 ********************************************************************************************************
 */
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
import com.fusionx.lending.origination.domain.CheckListTemplate;
import com.fusionx.lending.origination.domain.CheckListTemplatePending;
import com.fusionx.lending.origination.enums.WorkflowStatus;
import com.fusionx.lending.origination.resource.CheckListTemplateAddResource;
import com.fusionx.lending.origination.resource.CheckListTemplateUpdateResource;
import com.fusionx.lending.origination.resource.SuccessAndErrorDetailsResource;
import com.fusionx.lending.origination.service.CheckListTemplateService;  

@RestController
@RequestMapping(value = "/check-list-template")
@CrossOrigin(origins = "*")
public class CheckListTemplateController extends MessagePropertyBase {

	@Autowired
	public CheckListTemplateService checkListTemplateService;

	/**
	 * get all CheckListTemplate
	 * 
	 * @param @PathVariable{tenantId}
	 * @param @PathVariable{all}
	 * @return List<Type>
	 */
	@GetMapping(value = "/{tenantId}/all")
	public ResponseEntity<Object> getAllCheckListTemplate(
			@PathVariable(value = "tenantId", required = true) String tenantId) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<CheckListTemplate> checkListTemplate = checkListTemplateService.getAll();
		int size = checkListTemplate.size();
		if (size > 0) {
			return new ResponseEntity<>(checkListTemplate, HttpStatus.OK);
		} else {
			responseMessage.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * get CheckListTemplate by id
	 * 
	 * @param @PathVariable{tenantId}
	 * @param @PathVariable{id}
	 * @return Option
	 */
	@GetMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> getCheckListTemplateById(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<CheckListTemplate> isPresentCheckListTemplate = checkListTemplateService.getById(id);
		if (isPresentCheckListTemplate.isPresent()) {
			return new ResponseEntity<>(isPresentCheckListTemplate.get(), HttpStatus.OK);
		} else {
			responseMessage.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * get CheckListTemplate by code
	 * 
	 * @param @pathVariable{tenantId}
	 * @param @pathVariable           {code}
	 * @return Optional
	 **/
	@GetMapping(value = "/{tenantId}/code/{code}")
	public ResponseEntity<Object> getCheckListTemplateByCode(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "code", required = true) String code) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<CheckListTemplate> isPresentCheckListTemplate = checkListTemplateService.getByCode(code);
		if (isPresentCheckListTemplate.isPresent()) {
			return new ResponseEntity<>(isPresentCheckListTemplate.get(), HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * get CheckListTemplate by name
	 * 
	 * @param @pathVariable{tenantId}
	 * @param @pathVariable           {name}
	 * @return List
	 **/
	@GetMapping(value = "/{tenantId}/name/{name}")
	public ResponseEntity<Object> getCheckListTemplateByName(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "name", required = true) String name) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<CheckListTemplate> isPresentCheckListTemplate = checkListTemplateService.getByName(name);
		if (!isPresentCheckListTemplate.isEmpty()) {
			return new ResponseEntity<>(isPresentCheckListTemplate, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * get CheckListTemplate by status
	 * 
	 * @param @PathVariable{tenantId}
	 * @param @PathVariable{status}
	 * @return Option
	 */
	@GetMapping(value = "/{tenantId}/status/{status}")
	public ResponseEntity<Object> getCheckListTemplateByStatus(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "status", required = true) String status) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();

		if (status.equals("ACTIVE") || status.equals("INACTIVE")) {
			List<CheckListTemplate> isPresentCheckListTemplate = checkListTemplateService.getByStatus(status);
			int size = isPresentCheckListTemplate.size();
			if (size > 0) {
				return new ResponseEntity<>(isPresentCheckListTemplate, HttpStatus.OK);
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
	 * save CheckListTemplate
	 * 
	 * @param @PathVariable{tenantId}
	 * @param @RequestBody{CheckListTemplateAddResource}
	 * @return SuccessAndErrorDetailsDto
	 */
	@PostMapping(value = "/{tenantId}")
	public ResponseEntity<Object> addCheckListTemplate(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@Valid @RequestBody CheckListTemplateAddResource checkListTemplateAddResource) {
		if (LogginAuthentcation.getInstance().getUserName() == null
				|| LogginAuthentcation.getInstance().getUserName().isEmpty()) {
			throw new UsernameNotFoundException(environment.getProperty(NOT_FOUND));
		}
		CheckListTemplate checkListTemplate = checkListTemplateService.addCheckListTemplate(tenantId,
				checkListTemplateAddResource);
		SuccessAndErrorDetailsResource successAndErrorDetails = new SuccessAndErrorDetailsResource(
				environment.getProperty(RECORD_CREATED), Long.toString(checkListTemplate.getId()));
		return new ResponseEntity<>(successAndErrorDetails, HttpStatus.CREATED);
	}

	/**
	 * update CheckListTemplate
	 * 
	 * @param @PathVariable{tenantId}
	 * @param @RequestBody{CheckListTemplateUpdateResource}
	 * @return SuccessAndErrorDetailsDto
	 */
	@PutMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> updateCheckListTemplate(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id,
			@Valid @RequestBody CheckListTemplateUpdateResource checkListTemplateUpdateResource) {
		if (LogginAuthentcation.getInstance().getUserName() == null
				|| LogginAuthentcation.getInstance().getUserName().isEmpty()) {
			throw new UsernameNotFoundException(environment.getProperty(NOT_FOUND));
		}

		SuccessAndErrorDetailsResource successAndErrorDetails = new SuccessAndErrorDetailsResource();
		Optional<CheckListTemplate> isPresentCheckListTemplate = checkListTemplateService.getById(id);
		if (isPresentCheckListTemplate.isPresent()) {
			CheckListTemplate checkListTemplate = checkListTemplateService.updateCheckListTemplate(tenantId, id,
					checkListTemplateUpdateResource);
			successAndErrorDetails = new SuccessAndErrorDetailsResource(environment.getProperty(RECORD_UPDATED),
					checkListTemplate.getId().toString());
			return new ResponseEntity<>(successAndErrorDetails, HttpStatus.OK);
		} else {
			successAndErrorDetails.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(successAndErrorDetails, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

	/**
	 * approve CheckListTemplate Pending
	 * 
	 * @param @PathVariable{tenantId}
	 * @param @PathVariable{pendingId}
	 * @return SuccessAndErrorDetailsDto
	 */
	@PutMapping(value = "{tenantId}/approve/{pendingId}")
	public ResponseEntity<Object> approve(@PathVariable(value = "tenantId") String tenantId,
			@PathVariable(value = "pendingId") Long pendingId) {

		SuccessAndErrorDetailsResource successAndErrorDetailsResource = new SuccessAndErrorDetailsResource();
		Optional<CheckListTemplatePending> isPresentEligiTempPending = checkListTemplateService
				.getByPendingId(pendingId);

		if (isPresentEligiTempPending.isPresent()) {

			if (checkListTemplateService.approveReject(tenantId, pendingId, WorkflowStatus.COMPLETE)) {
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
	 * Reject CheckListTemplate Pending
	 * 
	 * @param @PathVariable{tenantId}
	 * @param @PathVariable{pendingId}
	 * @return SuccessAndErrorDetailsDto
	 */
	@PutMapping(value = "{tenantId}/reject/{pendingId}")
	public ResponseEntity<Object> reject(@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "pendingId", required = true) Long pendingId) {
		SuccessAndErrorDetailsResource successAndErrorDetailsResource = new SuccessAndErrorDetailsResource();
		Optional<CheckListTemplatePending> isPresentEligiPending = checkListTemplateService.getByPendingId(pendingId);
		if (isPresentEligiPending.isPresent()) {

			if (checkListTemplateService.approveReject(tenantId, pendingId, WorkflowStatus.REJECT)) {
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
	 * Get CheckList Template by pending id.
	 *
	 * @param tenantId  - the tenant id
	 * @param pendingId - the pending id
	 * @return CheckListTemplate by pending id
	 */
	@GetMapping(value = "/{tenantId}/pending/{pendingId}")
	public ResponseEntity<Object> getCheckListTemplateByPendingId(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "pendingId", required = true) Long pendingId) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<CheckListTemplatePending> isPresentCheckListTemplatePending = checkListTemplateService
				.getByPendingId(pendingId);
		if (isPresentCheckListTemplatePending.isPresent()) {
			return new ResponseEntity<>(isPresentCheckListTemplatePending, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty("record-not-found"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

}
