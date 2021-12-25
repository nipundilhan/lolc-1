package com.fusionx.lending.product.controller;

/**
 * Interest Template Controller 
 * 
 *******************************************************************************************************
 *  ###   Date         Story Point   		Task No    Author       Description
 *------------------------------------------------------------------------------------------------------
 *    1   28-09-2021    FXL_156  			FXL-931		Piyumi      Created
 *    
 *******************************************************************************************************
 */

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.InterestTemplate;
import com.fusionx.lending.product.domain.InterestTemplatePending;
import com.fusionx.lending.product.domain.RepaymentPending;
import com.fusionx.lending.product.enums.WorkflowStatus;
import com.fusionx.lending.product.exception.PageableLengthException;
import com.fusionx.lending.product.exception.UserNotFound;
import com.fusionx.lending.product.resources.InterestTemplateAddResource;
import com.fusionx.lending.product.resources.InterestTemplateUpdateResource;
import com.fusionx.lending.product.resources.SuccessAndErrorDetails;
import com.fusionx.lending.product.resources.SuccessAndErrorDetailsResource;
import com.fusionx.lending.product.service.InterestTemplateService;

@RestController
@RequestMapping(value = "/interest-template")
@CrossOrigin(origins = "*")
public class InterestTemplateController extends MessagePropertyBase{


	@Autowired
	public InterestTemplateService interestTemplateService;
	
	/**
	 * Get all
	 *
	 *  @param tenantId - tenantId
	 * @return InterestTemplate list
	 */
	@GetMapping(value = "/{tenantId}/all")
	public ResponseEntity<Object> getAllInterestTemplate(
			@PathVariable(value = "tenantId") String tenantId) {
		
		List<InterestTemplate> interestTemplateList = interestTemplateService.findAll();
		if (!interestTemplateList.isEmpty()) {
			return new ResponseEntity<>(interestTemplateList, HttpStatus.OK);
		} else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Get the InterestTemplate by id.
	 *
	 * @param tenantId - tenantId
	 * @param id -id
	 * @return InterestTemplate
	 */
	@GetMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> getInterestTemplateById(
			@PathVariable(value = "tenantId") String tenantId,
			@PathVariable(value = "id") Long id) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<InterestTemplate> isPresentInterestTemplate = interestTemplateService.findById(id);
		if (isPresentInterestTemplate.isPresent()) {
			return new ResponseEntity<>(isPresentInterestTemplate, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Get the InterestTemplate by code.
	 *
	 * @param tenantId - tenantId
	 * @param code -code
	 * @return InterestTemplate
	 */
	@GetMapping(value = "/{tenantId}/code/{code}")
	public ResponseEntity<Object> getInterestTemplateByCode(
			@PathVariable(value = "tenantId") String tenantId,
			@PathVariable(value = "code") String code) {
		
		Optional<InterestTemplate> isPresentInterestTemplate = interestTemplateService.getInterestTemplateByCode(code);
		if (isPresentInterestTemplate.isPresent()) {
			return new ResponseEntity<>(isPresentInterestTemplate, HttpStatus.OK);
		} else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Get the InterestTemplate by status
	 *
	 *  @param tenantId - tenantId
	 *  @param status -status
	 * @return InterestTemplate list
	 */
	@GetMapping(value = "/{tenantId}/status/{status}")
	public ResponseEntity<Object> getInterestTemplateByStatus(
			@PathVariable(value = "tenantId") String tenantId,
			@PathVariable(value = "status") String status) {
		
		List<InterestTemplate> interestTemplateList = interestTemplateService.findByStatus(status);
		if (!interestTemplateList.isEmpty()) {
			return new ResponseEntity<>(interestTemplateList, HttpStatus.OK);
		} else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Get the InterestTemplate by name
	 *
	 *  @param tenantId - tenantId
	 *  @param name -name
	 * @return InterestTemplate list
	 */
	@GetMapping(value = "/{tenantId}/name/{name}")
	public ResponseEntity<Object> getInterestTemplateByName(
			@PathVariable(value = "tenantId") String tenantId,
			@PathVariable(value = "name") String name) {
		
		List<InterestTemplate> interestTemplateList = interestTemplateService.findByName(name);
		if (!interestTemplateList.isEmpty()) {
			return new ResponseEntity<>(interestTemplateList, HttpStatus.OK);
		} else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

	
	/**
	 * Add InterestTemplate
	 *
	 *  @param tenantId - tenantId
	 *  @param InterestTemplateAddResource -InterestTemplateAddResource
	 * @return SuccessAndErrorDetailsDto
	 */
	@PostMapping(value = "/{tenantId}")
	public ResponseEntity<Object> addInterestTemplate(@PathVariable(value = "tenantId") String tenantId,
			@Valid @RequestBody InterestTemplateAddResource interestTemplateAddResource) {
		
		if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new UserNotFound(environment.getProperty(COMMON_USER_NOT_FOUND));
		
		InterestTemplate interestTemplate = interestTemplateService.addInterestTemplate(tenantId, interestTemplateAddResource, 
				LogginAuthentcation.getInstance().getUserName());
		SuccessAndErrorDetails successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty(RECORD_CREATED),
				Long.toString(interestTemplate.getId()));
		return new ResponseEntity<>(successAndErrorDetails, HttpStatus.CREATED);
	}
	
	/**
	 * Update InterestTemplate
	 *
	 * @param tenantId - tenantId
	 * @param id -id
	 * @param InterestTemplateUpdateResource -InterestTemplateUpdateResource
	 * @return SuccessAndErrorDetailsDto
	 */
	@PutMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> updateInterestTemplate(@PathVariable(value = "tenantId") String tenantId,
			@PathVariable(value = "id") Long id,
			@Valid @RequestBody InterestTemplateUpdateResource interestTemplateUpdateResource) {
		
		if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new UserNotFound(environment.getProperty(COMMON_USER_NOT_FOUND));
		
		InterestTemplate interestTemplate = interestTemplateService.updateInterestTemplate(tenantId, id, 
				interestTemplateUpdateResource,LogginAuthentcation.getInstance().getUserName());
		SuccessAndErrorDetails successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty(RECORD_UPDATED),
				Long.toString(interestTemplate.getId()));
		return new ResponseEntity<>(successAndErrorDetails, HttpStatus.CREATED);
	}
	
	/**
	 * Search InterestTemplate
	 *
	 * @param tenantId - the tenant id
	 * @param searchq - the searchq
	 * @param name - the name
	 * @param code - the code
	 * @param status - the status
	 * @param pageable - the pageable
	 * @return the page
	 */
	@GetMapping(value = "/{tenantId}/workflow-items/search")
	public Page<InterestTemplate> searchInterestTemplate(@PathVariable(value = "tenantId") String tenantId,
			@RequestParam(value = "searchq", required = false) String searchq,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "code", required = false) String code,
			@RequestParam(value = "status", required = false) String status,
			@PageableDefault Pageable pageable) {
		if(pageable.getPageSize()>Constants.MAX_PAGEABLE_LENGTH) 
			throw new PageableLengthException(environment.getProperty(PAGEABLE_LENGTH));
		return interestTemplateService.searchInterestTemplateList(searchq, name , code , status, pageable);
	}
	
	/**
	 * Gets the Interest template by pending id.
	 *
	 * @param tenantId - the tenant id
	 * @param pendingId - the pending id
	 * @return  IntersetTemplatePending
	 */
	@GetMapping(value = "/{tenantId}/pending/{pendingId}")
	public ResponseEntity<Object> getInterestTemplatePendingByPendingId(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "pendingId", required = true) Long pendingId) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<InterestTemplatePending> isPresentInterestTemplatePending = interestTemplateService.getByPendingId(pendingId);
		if (isPresentInterestTemplatePending.isPresent()) {
			return new ResponseEntity<>(isPresentInterestTemplatePending, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	* approve Interest template  Pending
	* @param @PathVariable{tenantId}
	* @param @PathVariable{pendingId}
	* @return SuccessAndErrorDetailsDto
	*/
	@PutMapping(value = "{tenantId}/approve/{pendingId}")
	public ResponseEntity<Object> approve(@PathVariable(value = "tenantId", required = true) String tenantId, @PathVariable(value = "pendingId", required = true) Long pendingId){
		SuccessAndErrorDetailsResource successAndErrorDetailsResource=new SuccessAndErrorDetailsResource();
		Optional<InterestTemplatePending> isPresentIntTempPending = interestTemplateService.getByPendingId(pendingId);
		if(isPresentIntTempPending.isPresent()) {
	
			if(interestTemplateService.approveReject(tenantId, pendingId, WorkflowStatus.COMPLETE)) {
			successAndErrorDetailsResource = new SuccessAndErrorDetailsResource(environment.getProperty("common.approved"), pendingId.toString());
			return new ResponseEntity<>(successAndErrorDetailsResource,HttpStatus.OK);
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
	* Reject Interest Template  Pending
	* @param @PathVariable{tenantId}
	* @param @PathVariable{pendingId}
	* @return SuccessAndErrorDetailsDto
	*/
	@PutMapping(value = "{tenantId}/reject/{pendingId}")
	public ResponseEntity<Object> reject(@PathVariable(value = "tenantId", required = true) String tenantId, @PathVariable(value = "pendingId", required = true) Long pendingId){
		SuccessAndErrorDetailsResource successAndErrorDetailsResource=new SuccessAndErrorDetailsResource();
		Optional<InterestTemplatePending> isPresentIntTempPending = interestTemplateService.getByPendingId(pendingId);
		if(isPresentIntTempPending.isPresent()) {
	
			if(interestTemplateService.approveReject(tenantId, pendingId, WorkflowStatus.REJECT)) {
			successAndErrorDetailsResource = new SuccessAndErrorDetailsResource(environment.getProperty("common.rejected"), pendingId.toString());
			return new ResponseEntity<>(successAndErrorDetailsResource,HttpStatus.OK);
			} else {
				successAndErrorDetailsResource.setMessages(environment.getProperty("common.can-not-rejected"));
				return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.UNPROCESSABLE_ENTITY);
			}

		} else {
			successAndErrorDetailsResource.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
	

}
