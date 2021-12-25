package com.fusionx.lending.product.controller;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
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
import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.EligibilityOfficerType;
import com.fusionx.lending.product.domain.EligibilityOfficerTypePending;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.enums.WorkflowStatus;
import com.fusionx.lending.product.exception.PageableLengthException;
import com.fusionx.lending.product.exception.UserNotFound;
import com.fusionx.lending.product.resources.EligibilityOfficerTypeAddResource;
import com.fusionx.lending.product.resources.EligibilityOfficerTypeUpdateResource;
import com.fusionx.lending.product.resources.SuccessAndErrorDetailsResource;
import com.fusionx.lending.product.service.EligibilityOfficerTypeService;

/**
 * Eligibility Officer Type Controller
 * 
 *******************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *------------------------------------------------------------------------------------------------------
 *    1   01-07-2021      		     FX-6888	MiyuruW      Created
 *    
 *******************************************************************************************************
 */

@RestController
@RequestMapping(value = "/eligibility-officer")
@CrossOrigin(origins = "*")
public class EligibilityOfficerTypeController {

	@Autowired
	private Environment environment;
	
	@Autowired
	private EligibilityOfficerTypeService eligibilityOfficerTypeService;
	
	private String userNotFound = "common.user-not-found";
	private String commonSaved = "common.saved";
	private String commonUpdated = "common.updated";
	private String pageableLength = "common.pageable-length";
	private String recordNotFound = "common.record-not-found";
	private String invalidStatus = "invalid-status";
	private String commonApproved = "common.approved";
	private String cantApprove = "common.can-not-approved";
	private String commonRejected = "common.rejected";
	private String cantReject = "common.can-not-rejected";
	
	
	/**
	 * Gets the all eligibility officer types.
	 *
	 * @param tenantId - the tenant id
	 * @return the all eligibility officer types
	 */
	@GetMapping(value = "/{tenantId}/all")
	public ResponseEntity<Object> getAllEligibilityOfficerTypes(@PathVariable(value = "tenantId", required = true) String tenantId) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<EligibilityOfficerType> eligibilityOfficerType = eligibilityOfficerTypeService.findAll();
		if (!eligibilityOfficerType.isEmpty()) {
			return new ResponseEntity<>((Collection<EligibilityOfficerType>) eligibilityOfficerType, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	/**
	 * Gets the eligibility officer type by id.
	 *
	 * @param tenantId - the tenant id
	 * @param id - the id
	 * @return the eligibility officer type by id
	 */
	@GetMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> getEligibilityOfficerTypeById(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<EligibilityOfficerType> isPresentEligibilityOfficerType = eligibilityOfficerTypeService.findById(id);
		if (isPresentEligibilityOfficerType.isPresent()) {
			return new ResponseEntity<>(isPresentEligibilityOfficerType, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	/**
	 * Gets the eligibility officer types by status.
	 *
	 * @param tenantId - the tenant id
	 * @param - status the status
	 * @return the eligibility officer types by status
	 */
	@GetMapping(value = "/{tenantId}/status/{status}")
	public ResponseEntity<Object> getEligibilityOfficerTypesByStatus(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "status", required = true) String status) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		if(status.equals(CommonStatus.ACTIVE.toString()) || status.equals(CommonStatus.INACTIVE.toString())) {
			List<EligibilityOfficerType> eligibilityOfficerType = eligibilityOfficerTypeService.findByStatus(status);
			if (!eligibilityOfficerType.isEmpty()) {
				return new ResponseEntity<>((Collection<EligibilityOfficerType>) eligibilityOfficerType, HttpStatus.OK);
			} else {
				responseMessage.setMessages(environment.getProperty(recordNotFound));
				return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
			}
		} else {
			responseMessage.setMessages(environment.getProperty(invalidStatus));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}	
	}
	
	
	/**
	 * Gets the eligibility officer types by eligibility id.
	 *
	 * @param tenantId - the tenant id
	 * @param eligibilityId - the eligibility id
	 * @return the eligibility officer types by eligibility id
	 */
	@GetMapping(value = "/{tenantId}/eligibility/{eligibilityId}")
	public ResponseEntity<Object> getEligibilityOfficerTypesByEligibilityId(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "eligibilityId", required = true) Long eligibilityId) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<EligibilityOfficerType> eligibilityOfficerType = eligibilityOfficerTypeService.findByEligibilityId(eligibilityId);
		if (!eligibilityOfficerType.isEmpty()) {
			return new ResponseEntity<>((Collection<EligibilityOfficerType>) eligibilityOfficerType, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	/**
	 * Gets the eligibility officer types by officer type id.
	 *
	 * @param tenantId - the tenant id
	 * @param officerTypeId - the officer type id
	 * @return the eligibility officer types by officer type id
	 */
	@GetMapping(value = "/{tenantId}/officer-type/{officerTypeId}")
	public ResponseEntity<Object> getEligibilityOfficerTypesByOfficerTypeId(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "officerTypeId", required = true) Long officerTypeId) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<EligibilityOfficerType> eligibilityOfficerType = eligibilityOfficerTypeService.findByOfficerTypeId(officerTypeId);
		if (!eligibilityOfficerType.isEmpty()) {
			return new ResponseEntity<>((Collection<EligibilityOfficerType>) eligibilityOfficerType, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	/**
	 * Adds the eligibility officer type.
	 *
	 * @param tenantId - the tenant id
	 * @param eligibilityOfficerTypeAddResource - the eligibility officer type add resource
	 * @return the response entity
	 */
	@PostMapping(value = "/{tenantId}")
	public ResponseEntity<Object> addEligibilityOfficerType(@PathVariable(value = "tenantId", required = true) String tenantId,
			@Valid @RequestBody EligibilityOfficerTypeAddResource eligibilityOfficerTypeAddResource) {
		if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new UserNotFound(environment.getProperty(userNotFound));
		Long id = eligibilityOfficerTypeService.saveAndValidateEligibilityOfficerType(tenantId, LogginAuthentcation.getInstance().getUserName(), eligibilityOfficerTypeAddResource);
		SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty(commonSaved), id);
		return new ResponseEntity<>(successDetailsDto,HttpStatus.CREATED);
	}
	
	
	
	/**
	 * Update eligibility officer type.
	 *
	 * @param tenantId - the tenant id
	 * @param id - the id
	 * @param eligibilityOfficerTypeUpdateResource - the eligibility officer type update resource
	 * @return the response entity
	 */
	@PutMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> updateEligibilityOfficerType(@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id,
			@Valid @RequestBody EligibilityOfficerTypeUpdateResource eligibilityOfficerTypeUpdateResource) {
		if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new UserNotFound(environment.getProperty(userNotFound));
		eligibilityOfficerTypeService.updateAndValidateEligibilityOfficerType(tenantId, LogginAuthentcation.getInstance().getUserName(), id, eligibilityOfficerTypeUpdateResource);
		SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty(commonUpdated));
		return new ResponseEntity<>(successDetailsDto, HttpStatus.OK);
	}
	
	
	/**
	 * Approve eligibility officer type.
	 *
	 * @param tenantId - the tenant id
	 * @param pendingId - the pending id
	 * @return the response entity
	 */
	@PutMapping(value = "{tenantId}/pending/{pendingId}/approve")
	public ResponseEntity<Object> approveEligibilityOfficerType(@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "pendingId", required = true) Long pendingId) {
		SuccessAndErrorDetailsResource successAndErrorDetailsResource = new SuccessAndErrorDetailsResource();
		Optional<EligibilityOfficerTypePending> isPresentEligibilityOfficerTypePending = eligibilityOfficerTypeService.getByPendingId(pendingId);
		if (isPresentEligibilityOfficerTypePending.isPresent()) {
			if (eligibilityOfficerTypeService.approveReject(tenantId, pendingId, WorkflowStatus.COMPLETE)) {
				successAndErrorDetailsResource = new SuccessAndErrorDetailsResource(environment.getProperty(commonApproved), pendingId.toString());
				return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.OK);
			} else {
				successAndErrorDetailsResource.setMessages(environment.getProperty(cantApprove));
				return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.UNPROCESSABLE_ENTITY);
			}
		} else {
			successAndErrorDetailsResource.setMessages(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
	
	
	/**
	 * Reject eligibility officer type.
	 *
	 * @param tenantId - the tenant id
	 * @param pendingId - the pending id
	 * @return the response entity
	 */
	@PutMapping(value = "{tenantId}/pending/{pendingId}/reject")
	public ResponseEntity<Object> rejectEligibilityOfficerType(@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "pendingId", required = true) Long pendingId) {
		SuccessAndErrorDetailsResource successAndErrorDetailsResource = new SuccessAndErrorDetailsResource();
		Optional<EligibilityOfficerTypePending> isPresentEligibilityOfficerTypePending = eligibilityOfficerTypeService.getByPendingId(pendingId);
		if (isPresentEligibilityOfficerTypePending.isPresent()) {
			if (eligibilityOfficerTypeService.approveReject(tenantId, pendingId, WorkflowStatus.REJECT)) {
				successAndErrorDetailsResource = new SuccessAndErrorDetailsResource(environment.getProperty(commonRejected), pendingId.toString());
				return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.OK);
			} else {
				successAndErrorDetailsResource.setMessages(environment.getProperty(cantReject));
				return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.UNPROCESSABLE_ENTITY);
			}
		} else {
			successAndErrorDetailsResource.setMessages(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
	
	
	/**
	 * Gets the eligibility officer type by pending id.
	 *
	 * @param tenantId - the tenant id
	 * @param pendingId - the pending id
	 * @return the eligibility officer type by pending id
	 */
	@GetMapping(value = "/{tenantId}/pending/{pendingId}")
	public ResponseEntity<Object> getEligibilityOfficerTypeByPendingId(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "pendingId", required = true) Long pendingId) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<EligibilityOfficerTypePending> isPresentEligibilityOfficerTypePending = eligibilityOfficerTypeService.getByPendingId(pendingId);
		if (isPresentEligibilityOfficerTypePending.isPresent()) {
			return new ResponseEntity<>(isPresentEligibilityOfficerTypePending, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	/**
	 * Search eligibility officer type.
	 *
	 * @param tenantId - the tenant id
	 * @param searchq - the searchq
	 * @param status - the status
	 * @param approveStatus - the approve status
	 * @param pageable - the pageable
	 * @return the page
	 */
	@GetMapping(value = "/{tenantId}/workflow-items/search")
	public Page<EligibilityOfficerTypePending> searchEligibilityOfficerType(@PathVariable(value = "tenantId", required = true) String tenantId,
			@RequestParam(value = "searchq", required = false) String searchq,
			@RequestParam(value = "status", required = false) String status,
			@RequestParam(value = "approveStatus", required = false) String approveStatus,
			@PageableDefault(size = 10) Pageable pageable) {
		if(pageable.getPageSize()>Constants.MAX_PAGEABLE_LENGTH) 
			throw new PageableLengthException(environment.getProperty(pageableLength));
		return eligibilityOfficerTypeService.searchEligibilityOfficerType(searchq, status, approveStatus, pageable);
	}
}
