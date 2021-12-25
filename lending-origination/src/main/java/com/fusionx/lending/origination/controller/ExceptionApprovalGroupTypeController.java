package com.fusionx.lending.origination.controller;
/**
 * Exception Approval Group Type Controller
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1  23-08-2021    FXL-632   	 FX-586		Piyumi      Created
 *    
 ********************************************************************************************************
 */

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fusionx.lending.origination.base.MessagePropertyBase;
import com.fusionx.lending.origination.domain.ExceptionApprovalGroupType;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.resource.ExceptionApprovalGroupTypeAddResource;
import com.fusionx.lending.origination.resource.ExceptionApprovalGroupTypeUpdateResource;
import com.fusionx.lending.origination.resource.SuccessAndErrorDetailsResource;
import com.fusionx.lending.origination.service.ExceptionApprovalGroupTypeService;

@RestController
@RequestMapping(value = "/exception-approval-group-type")
@CrossOrigin(origins = "*")
public class ExceptionApprovalGroupTypeController extends MessagePropertyBase {
	
	private ExceptionApprovalGroupTypeService exceptionApprovalGroupTypeService;
	
	@Autowired
	public void setExceptionApprovalGroupTypeService(ExceptionApprovalGroupTypeService exceptionApprovalGroupTypeService) {
		this.exceptionApprovalGroupTypeService = exceptionApprovalGroupTypeService;
	}
	
	/**
	 * Get the all exception approval group type.
	 *
	 * @param tenantId - the tenant id
	 * @return the all exception approval group type
	 */	
	@GetMapping("/{tenantId}/all")
	public ResponseEntity<Object> getAll(@PathVariable(value = "tenantId") String tenantId){		
		List<ExceptionApprovalGroupType> isPresentExceptionApprovalGroupTypeList = exceptionApprovalGroupTypeService.findAll();
		
		if(!isPresentExceptionApprovalGroupTypeList.isEmpty()) {
			return new ResponseEntity<>(isPresentExceptionApprovalGroupTypeList,HttpStatus.OK);
		}
		else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Get the  exception approval group type by id.
	 *
	 * @param tenantId - the tenant id
	 * @param id - the id
	 * @return the  exception approval group type by id
	 */
	@GetMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> getExceptionApprovalGroupTypeById(@PathVariable(value = "tenantId") String tenantId,
													         	  @PathVariable(value = "id") Long id){
		
		Optional<ExceptionApprovalGroupType> isPresentExceptionApprovalGroupType = exceptionApprovalGroupTypeService.findById(id);
		
		if(isPresentExceptionApprovalGroupType.isPresent()) {
			return new ResponseEntity<>(isPresentExceptionApprovalGroupType.get(), HttpStatus.OK);
		}
		else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Get the  exception approval group type by status.
	 *
	 * @param tenantId - the tenant id
	 * @param status - the status
	 * @return the  exception approval group type by status
	 */
	@GetMapping(value = "/{tenantId}/status/{status}")
	public ResponseEntity<Object> getExceptionApprovalGroupTypeByStatus(@PathVariable(value = "tenantId") String tenantId,
													   			      @PathVariable(value = "status") String status){
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		if(status.equals(CommonStatus.ACTIVE.toString()) || status.equals(CommonStatus.INACTIVE.toString())) {
			List<ExceptionApprovalGroupType> isPresentExceptionApprovalGroupTypeList = exceptionApprovalGroupTypeService.findByStatus(status);
			if(!isPresentExceptionApprovalGroupTypeList.isEmpty()) {
				return new ResponseEntity<>(isPresentExceptionApprovalGroupTypeList, HttpStatus.OK);
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
	 * Add the exception approval group type.
	 *
	 * @param tenantId - the tenant id
	 * @param exceptionApprovalGroupTypeAddResource - the exception approval group type add resource
	 * @return the response entity
	 */
	@PostMapping("/{tenantId}")
	public ResponseEntity<Object> addExceptionApprovalGroupType(@PathVariable(value = "tenantId") String tenantId,
			   									       		  @Valid @RequestBody ExceptionApprovalGroupTypeAddResource exceptionApprovalGroupTypeAddResource){
		ExceptionApprovalGroupType exceptionApprovalGroupType = exceptionApprovalGroupTypeService.addExceptionApprovalGroupType(tenantId, exceptionApprovalGroupTypeAddResource);
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource(getEnvironment().getProperty(RECORD_CREATED), Long.toString(exceptionApprovalGroupType.getId()));
		return new ResponseEntity<>(responseMessage,HttpStatus.CREATED);
	}
	
	/**
	 * Update exception approval group type.
	 *
	 * @param tenantId - the tenant id
	 * @param id - the id
	 * @param exceptionApprovalGroupTypeUpdateResource - the exception approval group type update resource
	 * @return the response entity
	 */
	@PutMapping(value = "{tenantId}/{id}")
	public ResponseEntity<Object> updateExceptionApprovalGroupType(@PathVariable(value = "tenantId") String tenantId, 
												                 @PathVariable(value = "id") Long id, 
												                 @Valid @RequestBody ExceptionApprovalGroupTypeUpdateResource exceptionApprovalGroupTypeUpdateResource){
		SuccessAndErrorDetailsResource successAndErrorDetailsResource=new SuccessAndErrorDetailsResource();
		Optional<ExceptionApprovalGroupType> isPresentExceptionApprovalGroupType = exceptionApprovalGroupTypeService.findById(id);		
		
		if(isPresentExceptionApprovalGroupType.isPresent()) {
			ExceptionApprovalGroupType exceptionApprovalGroupType = exceptionApprovalGroupTypeService.updateExceptionApprovalGroupType(tenantId, id, exceptionApprovalGroupTypeUpdateResource);
			successAndErrorDetailsResource = new SuccessAndErrorDetailsResource(getEnvironment().getProperty(RECORD_UPDATED), exceptionApprovalGroupType.getId().toString());
			return new ResponseEntity<>(successAndErrorDetailsResource,HttpStatus.OK);
		}
		else {
			successAndErrorDetailsResource.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
	
	/**
	 * Get all by exception approval group id.
	 *
	 * @param tenantId - the tenant id
	 * @param exceptionApprovalGroupId - the  exception approval group id
	 * @return the all exception approval group type
	 */
	@GetMapping(value = "/{tenantId}/exception-approval-group/{exceptionApprovalGroupId}")
	public ResponseEntity<Object> getAllByExceptionApprovalGroupId(@PathVariable(value = "tenantId") String tenantId,
													   			      @PathVariable(value = "exceptionApprovalGroupId") Long exceptionApprovalGroupId){
		
			List<ExceptionApprovalGroupType> isPresentExceptionApprovalGroupTypeList = exceptionApprovalGroupTypeService.findByExceptionApprovalGroupId(exceptionApprovalGroupId);
			if(!isPresentExceptionApprovalGroupTypeList.isEmpty()) {
				return new ResponseEntity<>(isPresentExceptionApprovalGroupTypeList, HttpStatus.OK);
			}
			else {
				SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
				responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
				return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
			}
	}

}
