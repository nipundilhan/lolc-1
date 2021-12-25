package com.fusionx.lending.origination.controller;

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

import com.fusionx.lending.origination.Constants;
import com.fusionx.lending.origination.core.LogginAuthentcation;
import com.fusionx.lending.origination.domain.ApprovalCategory;
import com.fusionx.lending.origination.exception.PageableLengthException;
import com.fusionx.lending.origination.exception.UserNotFound;
import com.fusionx.lending.origination.resource.ApprovalCategoryAddResource;
import com.fusionx.lending.origination.resource.ApprovalCategoryUpdateResource;
import com.fusionx.lending.origination.resource.SuccessAndErrorDetailsResource;
import com.fusionx.lending.origination.service.ApprovalCategoryService;


/**
 * Approval Category Controller
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   20-04-2021      		     			VenukiR      Created
 *    
 ********************************************************************************************************
 */

@RestController
@RequestMapping(value = "/approval-category")
@CrossOrigin(origins = "*")
public class ApprovalCategoryController {

	@Autowired
	private Environment environment;
	
	@Autowired
	private ApprovalCategoryService approvalCategoryService;
	
	private String userNotFound = "common.user-not-found";
	private String commonSaved = "common.saved";
	private String commonUpdated = "common.updated";
	private String pageableLength = "common.pageable-length";
	private String recordNotFound = "common.record-not-found";
	
	
	/**
	 * Gets the all ApprovalCategory.
	 *
	 * @param tenantId - the tenant id
	 * @param pageable - the pageable
	 * @return the all ApprovalCategory
	 */
//	@GetMapping(value = "/{tenantId}/all")
//	public Page<ApprovalCategory> getAllApprovalCategory(@PathVariable(value = "tenantId", required = true) String tenantId,
//			@PageableDefault(size = 10) Pageable pageable) {
//		if (pageable.getPageSize() > Constants.MAX_PAGEABLE_LENGTH)
//			throw new PageableLengthException(environment.getProperty(pageableLength));
//		return approvalCategoryService.findAll(pageable);
//	}
	
	@GetMapping(value = "/{tenantId}/all")
	public ResponseEntity<Object> getAllApprovalCategory(
			@PathVariable(value = "tenantId", required = true) String tenantId) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<ApprovalCategory> approvalCategory = approvalCategoryService.findAll();
		if (!approvalCategory.isEmpty()) {
			return new ResponseEntity<>((Collection<ApprovalCategory>) approvalCategory, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Gets the ApprovalCategory by id.
	 *
	 * @param tenantId - the tenant id
	 * @param id - the id
	 * @return the ApprovalCategory by id
	 */
	@GetMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> getApprovalCategoryById(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<ApprovalCategory> isPresentApprovalCategory = approvalCategoryService.findById(id);
		if (isPresentApprovalCategory.isPresent()) {
			return new ResponseEntity<>(isPresentApprovalCategory, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Gets the ApprovalCategory by status.
	 *
	 * @param tenantId - the tenant id
	 * @param status - the status
	 * @return the ApprovalCategory by status
	 */
	@GetMapping(value = "/{tenantId}/status/{status}")
	public ResponseEntity<Object> getApprovalCategoryByStatus(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "status", required = true) String status) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<ApprovalCategory> approvalCategory = approvalCategoryService.findByStatus(status);
		if (!approvalCategory.isEmpty()) {
			return new ResponseEntity<>((Collection<ApprovalCategory>) approvalCategory, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	

	/**
	 * Gets the ApprovalCategory by name.
	 *
	 * @param tenantId - the tenant id
	 * @param name - the name
	 * @return the ApprovalCategory by name
	 */
	@GetMapping(value = "/{tenantId}/name/{name}")
	public ResponseEntity<Object> getApprovalCategoryByName(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "name", required = true) String name) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<ApprovalCategory> approvalCategory = approvalCategoryService.findByName(name);
		if (!approvalCategory.isEmpty()) {
			return new ResponseEntity<>((Collection<ApprovalCategory>) approvalCategory, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Gets the ApprovalCategory by code.
	 *
	 * @param tenantId - the tenant id
	 * @param code - the code
	 * @return the ApprovalCategory by code
	 */
	@GetMapping(value = "/{tenantId}/code/{code}")
	public ResponseEntity<Object> getApprovalCategorysByCode(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "code", required = true) String code) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<ApprovalCategory> isPresentApprovalCategory = approvalCategoryService.findByCode(code);
		if (isPresentApprovalCategory.isPresent()) {
			return new ResponseEntity<>(isPresentApprovalCategory, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Adds the ApprovalCategory.
	 *
	 * @param tenantId - the tenant id
	 * @param ApprovalCategoryAddResource - the ApprovalCategory add resource
	 * @return the response entity
	 */
	@PostMapping(value = "/{tenantId}")
	public ResponseEntity<Object> addApprovalCategory(@PathVariable(value = "tenantId", required = true) String tenantId,
			@Valid @RequestBody ApprovalCategoryAddResource daGroupAddResource) {
		if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new UserNotFound(environment.getProperty(userNotFound));
		Long id = approvalCategoryService.saveAndValidateApprovalCategory(tenantId, LogginAuthentcation.getInstance().getUserName(), daGroupAddResource);
		SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty(commonSaved), id.toString());
		return new ResponseEntity<>(successDetailsDto,HttpStatus.CREATED);
	}
	
	/**
	 * Update ApprovalCategory.
	 *
	 * @param tenantId - the tenant id
	 * @param id - the id
	 * @param ApprovalCategoryUpdateResource - the ApprovalCategory update resource
	 * @return the response entity
	 */
	@PutMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> updateApprovalCategory(@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id,
			@Valid @RequestBody ApprovalCategoryUpdateResource ApprovalCategoryUpdateResource) {
		if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new UserNotFound(environment.getProperty(userNotFound));
		if (approvalCategoryService.existsById(id)) {
			approvalCategoryService.updateAndValidateApprovalCategory(tenantId, LogginAuthentcation.getInstance().getUserName(), id, ApprovalCategoryUpdateResource);
			SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty(commonUpdated));
			return new ResponseEntity<>(successDetailsDto, HttpStatus.OK);
		} else {
			SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(successDetailsDto, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
	
	/**
	 * Search ApprovalCategory.
	 *
	 * @param tenantId - the tenant id
	 * @param searchq - the searchq
	 * @param name - the name
	 * @param code - the code
	 * @param pageable - the pageable
	 * @return the page
	 */
	@GetMapping(value = "/{tenantId}/search")
	public Page<ApprovalCategory> searchApprovalCategory(@PathVariable(value = "tenantId", required = true) String tenantId,
			@RequestParam(value = "searchq", required = false) String searchq,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "code", required = false) String code,
			@PageableDefault(size = 10) Pageable pageable) {
		if(pageable.getPageSize()>Constants.MAX_PAGEABLE_LENGTH) 
			throw new PageableLengthException(environment.getProperty(pageableLength));
		return approvalCategoryService.searchApprovalCategory(searchq, name, code, pageable);
	}
}