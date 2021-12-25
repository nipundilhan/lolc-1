package com.fusionx.lending.origination.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
/**
 * Approval Category Levels
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
import com.fusionx.lending.origination.domain.ApprovalCategoryLevels;
import com.fusionx.lending.origination.resource.ApprovalCategoryLevelsAddResource;
import com.fusionx.lending.origination.resource.ApprovalCategoryLevelsUpdateResource;
import com.fusionx.lending.origination.resource.SuccessAndErrorDetailsResource;
import com.fusionx.lending.origination.service.ApprovalCategoryLevelsService;

@RestController
@RequestMapping(value = "/approval-category-levels")
@CrossOrigin(origins = "*")
public class ApprovalCategoryLevelsController extends MessagePropertyBase {

	@Autowired
	public ApprovalCategoryLevelsService approvalCategoryLevelsService;

	/**
	 * get all ApprovalCategoryLevels
	 * 
	 * @param @PathVariable{tenantId}
	 * @param @PathVariable{all}
	 * @return List<Type>
	 */
	@GetMapping(value = "/{tenantId}/all")
	public ResponseEntity<Object> getAllApprovalCategoryLevels(
			@PathVariable(value = "tenantId", required = true) String tenantId) {

		List<ApprovalCategoryLevels> approvalCategoryLevels = approvalCategoryLevelsService.getAll();
		int size = approvalCategoryLevels.size();
		if (size > 0) {
			return new ResponseEntity<>(approvalCategoryLevels, HttpStatus.OK);
		} else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * get ApprovalCategoryLevels by id
	 * 
	 * @param @PathVariable{tenantId}
	 * @param @PathVariable{id}
	 * @return Option
	 */
	@GetMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> getApprovalCategoryLevelsById(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id) {

		Optional<ApprovalCategoryLevels> isPresentApprovalCategoryLevels = approvalCategoryLevelsService.getById(id);
		if (isPresentApprovalCategoryLevels.isPresent()) {
			return new ResponseEntity<>(isPresentApprovalCategoryLevels.get(), HttpStatus.OK);
		} else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * get ApprovalCategoryLevels by code
	 * 
	 * @param @pathVariable{tenantId}
	 * @param @pathVariable           {code}
	 * @return Optional
	 **/
	@GetMapping(value = "/{tenantId}/code/{code}")
	public ResponseEntity<Object> getApprovalCategoryLevelsByCode(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "code", required = true) String code) {

		Optional<ApprovalCategoryLevels> isPresentApprovalCategoryLevels = approvalCategoryLevelsService
				.getByCode(code);
		if (isPresentApprovalCategoryLevels.isPresent()) {
			return new ResponseEntity<>(isPresentApprovalCategoryLevels.get(), HttpStatus.OK);
		} else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * get ApprovalCategoryLevels by name
	 * 
	 * @param @pathVariable{tenantId}
	 * @param @pathVariable           {name}
	 * @return List
	 **/
	@GetMapping(value = "/{tenantId}/name/{name}")
	public ResponseEntity<Object> getApprovalCategoryLevelsByName(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "name", required = true) String name) {

		List<ApprovalCategoryLevels> isPresentApprovalCategoryLevels = approvalCategoryLevelsService.getByName(name);
		if (!isPresentApprovalCategoryLevels.isEmpty()) {
			return new ResponseEntity<>(isPresentApprovalCategoryLevels, HttpStatus.OK);
		} else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * get ApprovalCategoryLevels by status
	 * 
	 * @param @PathVariable{tenantId}
	 * @param @PathVariable{status}
	 * @return Option
	 */
	@GetMapping(value = "/{tenantId}/status/{status}")
	public ResponseEntity<Object> getApprovalCategoryLevelsByStatus(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "status", required = true) String status) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();

		if (status.equals("ACTIVE") || status.equals("INACTIVE")) {
			List<ApprovalCategoryLevels> isPresentApprovalCategoryLevels = approvalCategoryLevelsService
					.getByStatus(status);
			int size = isPresentApprovalCategoryLevels.size();
			if (size > 0) {
				return new ResponseEntity<>(isPresentApprovalCategoryLevels, HttpStatus.OK);
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
	 * save ApprovalCategoryLevels
	 * 
	 * @param @PathVariable{tenantId}
	 * @param @RequestBody{CommonAddResource}
	 * @return SuccessAndErrorDetailsDto
	 */
	@PostMapping(value = "/{tenantId}")
	public ResponseEntity<Object> addApprovalCategoryLevels(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@Valid @RequestBody ApprovalCategoryLevelsAddResource approvalCategoryLevelsAddResource) {
		if (LogginAuthentcation.getInstance().getUserName() == null
				|| LogginAuthentcation.getInstance().getUserName().isEmpty()) {
			throw new UsernameNotFoundException(environment.getProperty(NOT_FOUND));
		}

		ApprovalCategoryLevels approvalCategoryLevels = approvalCategoryLevelsService
				.addApprovalCategoryLevels(tenantId, approvalCategoryLevelsAddResource);
		SuccessAndErrorDetailsResource successAndErrorDetails = new SuccessAndErrorDetailsResource(
				environment.getProperty(RECORD_CREATED), Long.toString(approvalCategoryLevels.getId()));
		return new ResponseEntity<>(successAndErrorDetails, HttpStatus.CREATED);
	}

	/**
	 * update ApprovalCategoryLevels
	 * 
	 * @param @PathVariable{tenantId}
	 * @param @RequestBody{CommonUpdateResource}
	 * @return SuccessAndErrorDetailsDto
	 */
	@PutMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> updateApprovalCategoryLevels(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id,
			@Valid @RequestBody ApprovalCategoryLevelsUpdateResource approvalCategoryLevelsUpdateResource) {
		if (LogginAuthentcation.getInstance().getUserName() == null
				|| LogginAuthentcation.getInstance().getUserName().isEmpty()) {
			throw new UsernameNotFoundException(environment.getProperty(NOT_FOUND));
		}
		ApprovalCategoryLevels approvalCategoryLevels = approvalCategoryLevelsService
				.updateApprovalCategoryLevels(tenantId, id, approvalCategoryLevelsUpdateResource);
		SuccessAndErrorDetailsResource successAndErrorDetails = new SuccessAndErrorDetailsResource(
				environment.getProperty(RECORD_UPDATED), approvalCategoryLevels.getId().toString());
		return new ResponseEntity<>(successAndErrorDetails, HttpStatus.OK);
	}
}
