package com.fusionx.lending.origination.controller;

/**
 * 	Exception Approval Group Controller
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   20-08-2021   FXL-632  	 FXL-564       Piyumi     Created
 *    
 ********************************************************************************************************
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

import com.fusionx.lending.origination.Constants;
import com.fusionx.lending.origination.base.MessagePropertyBase;
import com.fusionx.lending.origination.domain.ExceptionApprovalGroup;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.exception.PageableLengthException;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.resource.ExceptionApprovalGroupAddResource;
import com.fusionx.lending.origination.resource.ExceptionApprovalGroupUpdateResource;
import com.fusionx.lending.origination.resource.SuccessAndErrorDetailsResource;
import com.fusionx.lending.origination.service.ExceptionApprovalGroupService;


@RestController
@RequestMapping(value = "/exception-approval-group")
@CrossOrigin(origins = "*")
public class ExceptionApprovalGroupController extends MessagePropertyBase{
	
	
	private ExceptionApprovalGroupService exceptionApprovalGroupService;
	
	@Autowired
	public void setExceptionApprovalGroupService(ExceptionApprovalGroupService exceptionApprovalGroupService) {
		this.exceptionApprovalGroupService = exceptionApprovalGroupService;
	}
	
	@GetMapping("/{tenantId}/all")
	public ResponseEntity<Object> getAllExceptionApprovalGroup(@PathVariable(value = "tenantId", required = true) String tenantId){
		List<ExceptionApprovalGroup> exceptionApprovalGroups = exceptionApprovalGroupService.findAll();
		
		if(!exceptionApprovalGroups.isEmpty()) {
			return new ResponseEntity<>(exceptionApprovalGroups, HttpStatus.OK);
		}
		else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Get the ExceptionApprovalGroup by id.
	 *
	 * @param tenantId - the tenant id
	 * @param id - the id
	 * @return the ExceptionApprovalGroup by id
	 */
	@GetMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> getExceptionApprovalGroupById(@PathVariable(value = "tenantId", required = true) String tenantId,
													         	  @PathVariable(value = "id", required = true) Long id){
			
		Optional<ExceptionApprovalGroup> isPresentExceptionApprovalGroup = exceptionApprovalGroupService.findById(id);
		if(isPresentExceptionApprovalGroup.isPresent()) {
			return new ResponseEntity<>(isPresentExceptionApprovalGroup.get(), HttpStatus.OK);
		}
		else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Get the ExceptionApprovalGroup by code.
	 *
	 * @param tenantId - the tenant id
	 * @param code - the code
	 * @return the ExceptionApprovalGroup by code
	 */
	
	@GetMapping(value = "/{tenantId}/code/{code}")
	public ResponseEntity<Object> getExceptionApprovalGroupByCode(@PathVariable(value = "tenantId", required = true) String tenantId,
													   	            @PathVariable(value = "code", required = true) String code){	
		Optional<ExceptionApprovalGroup> isPresentExceptionApprovalGroup = exceptionApprovalGroupService.findByCode(code);
		if(isPresentExceptionApprovalGroup.isPresent()) {
			return new ResponseEntity<>(isPresentExceptionApprovalGroup.get(), HttpStatus.OK);
		}
		else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Get the ExceptionApprovalGroups by name.
	 *
	 * @param tenantId - the tenant id
	 * @param name - the name
	 * @return the ExceptionApprovalGroups by name
	 */
	
	@GetMapping(value = "/{tenantId}/name/{name}")
	public ResponseEntity<Object> getExceptionApprovalGroupByName(@PathVariable(value = "tenantId", required = true) String tenantId,
															        @PathVariable(value = "name", required = true) String name){
		
		List<ExceptionApprovalGroup> exceptionApprovalGroups = exceptionApprovalGroupService.findByName(name);
		if(!exceptionApprovalGroups.isEmpty()) {
			return new ResponseEntity<>(exceptionApprovalGroups, HttpStatus.OK);
		}
		else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Get the ExceptionApprovalGroups by status.
	 *
	 * @param tenantId - the tenant id
	 * @param status - the status
	 * @return the ExceptionApprovalGroups by status
	 */
	
	@GetMapping(value = "/{tenantId}/status/{status}")
	public ResponseEntity<Object> getExceptionApprovalGroupByStatus(@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "status", required = true) String status) {

		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		if(status.equals(CommonStatus.ACTIVE.toString()) || status.equals(CommonStatus.INACTIVE.toString())) {

			List<ExceptionApprovalGroup> exceptionApprovalGroups = exceptionApprovalGroupService.findByStatus(status);
			if(!exceptionApprovalGroups.isEmpty()) {
				return new ResponseEntity<>(exceptionApprovalGroups, HttpStatus.OK);
			}
			else {
				responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
				return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
			}
		}else {
			responseMessage.setMessages(RECORD_NOT_FOUND);
	        throw new ValidateRecordException(environment.getProperty(COMMON_STATUS_PATTERN), "message");
		}
	}
	
	/**
	 * Add the ExceptionApprovalGroup.
	 *
	 * @param tenantId - the tenant id
	 * @param CommonAddResource - the common add resource
	 * @return the response entity
	 */
	
	@PostMapping("/{tenantId}")
	public ResponseEntity<Object> addExceptionApprovalGroup(@PathVariable(value = "tenantId", required = true) String tenantId,
			   									       		  @Valid @RequestBody ExceptionApprovalGroupAddResource exceptionApprovalGroupAddResource){
		ExceptionApprovalGroup otherIncomeCategory = exceptionApprovalGroupService.save(tenantId, exceptionApprovalGroupAddResource);
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource(getEnvironment().getProperty(RECORD_CREATED), Long.toString(otherIncomeCategory.getId()));
		return new ResponseEntity<>(responseMessage,HttpStatus.CREATED);
	}
	
	/**
	 * Update ExceptionApprovalGroup.
	 *
	 * @param tenantId - the tenant id
	 * @param id - the id
	 * @param CommonUpdateResource - the common update resource
	 * @return the response entity
	 */
	
	@PutMapping(value = "{tenantId}/{id}")
	public ResponseEntity<Object> updateExceptionApprovalGroup(@PathVariable(value = "tenantId", required = true) String tenantId, 
												                 @PathVariable(value = "id", required = true) Long id, 
												                 @Valid @RequestBody ExceptionApprovalGroupUpdateResource exceptionApprovalGroupUpdateResource){
		SuccessAndErrorDetailsResource successAndErrorDetailsResource=new SuccessAndErrorDetailsResource();
		Optional<ExceptionApprovalGroup>isPresentExceptionApprovalGroup = exceptionApprovalGroupService.findById(id);		
		if(isPresentExceptionApprovalGroup.isPresent()) {
			ExceptionApprovalGroup otherIncomeCategory = exceptionApprovalGroupService.update(tenantId, id, exceptionApprovalGroupUpdateResource);
			successAndErrorDetailsResource = new SuccessAndErrorDetailsResource(getEnvironment().getProperty(RECORD_UPDATED), otherIncomeCategory.getId().toString());
			return new ResponseEntity<>(successAndErrorDetailsResource,HttpStatus.OK);
		}
		else {
			successAndErrorDetailsResource.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
	
	/**
	 * Search ExceptionApprovalGroup.
	 *
	 * @param tenantId - the tenant id
	 * @param searchq - the searchq
	 * @param name - the name
	 * @param code - the code
	 * @param status - the status
	 * @param pageable - the pageable
	 * @return the page
	 */
	@GetMapping(value = "/{tenantId}/search")
	public Page<ExceptionApprovalGroup> searchExceptionApprovalGroup(@PathVariable(value = "tenantId", required = true) String tenantId,
			//@RequestParam(value = "searchq", required = false) String searchq,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "code", required = false) String code,
			@RequestParam(value = "status", required = false) String status,
			@PageableDefault Pageable pageable) {
		if(pageable.getPageSize()>Constants.MAX_PAGEABLE_LENGTH) 
			throw new PageableLengthException(environment.getProperty(PAGEABLE_LENGTH));
		return exceptionApprovalGroupService.searchExceptionApprovalGroup(name, code, status , pageable);
	}

}
