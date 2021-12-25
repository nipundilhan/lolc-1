package com.fusionx.lending.origination.controller;

/**
 * Exception Type Controller
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   17-08-2021   FXL-627  	 FXL-563       Piyumi     Created
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
import com.fusionx.lending.origination.domain.ExceptionType;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.exception.PageableLengthException;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.resource.CommonAddResource;
import com.fusionx.lending.origination.resource.CommonUpdateResource;
import com.fusionx.lending.origination.resource.SuccessAndErrorDetailsResource;
import com.fusionx.lending.origination.service.ExceptionTypeService;


@RestController
@RequestMapping(value = "/exception-type")
@CrossOrigin(origins = "*")
public class ExceptionTypeController extends MessagePropertyBase{
	
	
	private ExceptionTypeService exceptionTypeService;
	
	@Autowired
	public void setExceptionTypeService(ExceptionTypeService exceptionTypeService) {
		this.exceptionTypeService = exceptionTypeService;
	}
	
	@GetMapping("/{tenantId}/all")
	public ResponseEntity<Object> getAllExceptionType(@PathVariable(value = "tenantId") String tenantId){
		List<ExceptionType> exceptionTypes = exceptionTypeService.findAll();
		if(!exceptionTypes.isEmpty()) {
			return new ResponseEntity<>(exceptionTypes, HttpStatus.OK);
		}
		else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Get the ExceptionType by id.
	 *
	 * @param tenantId - the tenant id
	 * @param id - the id
	 * @return the ExceptionType by id
	 */
	@GetMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> getExceptionTypeById(@PathVariable(value = "tenantId") String tenantId,
													         	  @PathVariable(value = "id") Long id){
			
		Optional<ExceptionType> isPresentExceptionType = exceptionTypeService.findById(id);
		if(isPresentExceptionType.isPresent()) {
			return new ResponseEntity<>(isPresentExceptionType.get(), HttpStatus.OK);
		}
		else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Get the ExceptionType by code.
	 *
	 * @param tenantId - the tenant id
	 * @param code - the code
	 * @return the ExceptionType by code
	 */
	
	@GetMapping(value = "/{tenantId}/code/{code}")
	public ResponseEntity<Object> getExceptionTypeByCode(@PathVariable(value = "tenantId") String tenantId,
													   	            @PathVariable(value = "code") String code){		
		
		Optional<ExceptionType> isPresentExceptionType = exceptionTypeService.findByCode(code);
		if(isPresentExceptionType.isPresent()) {
			return new ResponseEntity<>(isPresentExceptionType.get(), HttpStatus.OK);
		}
		else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Get the ExceptionTypes by name.
	 *
	 * @param tenantId - the tenant id
	 * @param name - the name
	 * @return the ExceptionTypes by name
	 */
	
	@GetMapping(value = "/{tenantId}/name/{name}")
	public ResponseEntity<Object> getExceptionTypeByName(@PathVariable(value = "tenantId") String tenantId,
															        @PathVariable(value = "name") String name){
			
		List<ExceptionType> exceptionTypes = exceptionTypeService.findByName(name);
		if(!exceptionTypes.isEmpty()) {
			return new ResponseEntity<>(exceptionTypes, HttpStatus.OK);
		}
		else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Get the ExceptionTypes by status.
	 *
	 * @param tenantId - the tenant id
	 * @param status - the status
	 * @return the ExceptionTypes by status
	 */
	
	@GetMapping(value = "/{tenantId}/status/{status}")
	public ResponseEntity<Object> getExceptionTypeByStatus(@PathVariable(value = "tenantId") String tenantId,
			@PathVariable(value = "status") String status) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		if(status.equals(CommonStatus.ACTIVE.toString()) || status.equals(CommonStatus.INACTIVE.toString())) {
			List<ExceptionType> exceptionTypes = exceptionTypeService.findByStatus(status);
			if(!exceptionTypes.isEmpty()) {
				return new ResponseEntity<>(exceptionTypes, HttpStatus.OK);
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
	 * Add the ExceptionType.
	 *
	 * @param tenantId - the tenant id
	 * @param CommonAddResource - the common add resource
	 * @return the response entity
	 */
	
	@PostMapping("/{tenantId}")
	public ResponseEntity<Object> addExceptionType(@PathVariable(value = "tenantId") String tenantId,
			   									       		  @Valid @RequestBody CommonAddResource commonAddResource){
		ExceptionType exceptionType = exceptionTypeService.save(tenantId, commonAddResource);
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource(getEnvironment().getProperty(RECORD_CREATED), Long.toString(exceptionType.getId()));
		return new ResponseEntity<>(responseMessage,HttpStatus.CREATED);
	}
	
	/**
	 * Update ExceptionType.
	 *
	 * @param tenantId - the tenant id
	 * @param id - the id
	 * @param CommonUpdateResource - the common update resource
	 * @return the response entity
	 */
	
	@PutMapping(value = "{tenantId}/{id}")
	public ResponseEntity<Object> updateExceptionType(@PathVariable(value = "tenantId") String tenantId, 
												                 @PathVariable(value = "id") Long id, 
												                 @Valid @RequestBody CommonUpdateResource commonUpdateResource){
		SuccessAndErrorDetailsResource successAndErrorDetailsResource=new SuccessAndErrorDetailsResource();
		Optional<ExceptionType> isPresentExceptionType = exceptionTypeService.findById(id);		
		if(isPresentExceptionType.isPresent()) {
			ExceptionType exceptionType = exceptionTypeService.update(tenantId, id, commonUpdateResource);
			successAndErrorDetailsResource = new SuccessAndErrorDetailsResource(getEnvironment().getProperty(RECORD_UPDATED), exceptionType.getId().toString());
			return new ResponseEntity<>(successAndErrorDetailsResource,HttpStatus.OK);
		}
		else {
			successAndErrorDetailsResource.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
	
	/**
	 * Search ExceptionType.
	 *
	 * @param tenantId - the tenant id
	 * @param searchq - the searchq
	 * @param name - the name
	 * @param code - the code
	 * @param pageable - the pageable
	 * @return the page
	 */
	/*@GetMapping(value = "/{tenantId}/search")
	public Page<ExceptionType> searchExceptionType(@PathVariable(value = "tenantId") String tenantId,
			@RequestParam(value = "searchq", required = false) String searchq,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "code", required = false) String code,
			@PageableDefault Pageable pageable) {
		if(pageable.getPageSize()>Constants.MAX_PAGEABLE_LENGTH) 
			throw new PageableLengthException(environment.getProperty(PAGEABLE_LENGTH));
		return exceptionTypeService.searchExceptionType(searchq, name, code, pageable);
	}*/
	
	@GetMapping(value = "/{tenantId}/search")
	public Page<ExceptionType> searchExceptionType(@PathVariable(value = "tenantId") String tenantId,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "code", required = false) String code,
			@PageableDefault Pageable pageable) {
		if(pageable.getPageSize()>Constants.MAX_PAGEABLE_LENGTH) 
			throw new PageableLengthException(environment.getProperty(PAGEABLE_LENGTH));
		return exceptionTypeService.searchExceptionType(name, code, pageable);
	}

}
