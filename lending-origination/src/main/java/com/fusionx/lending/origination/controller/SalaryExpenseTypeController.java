package com.fusionx.lending.origination.controller;
/**
 * Salary Expense Type Controller
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   30-08-2021   	FXL-521   	 FX-685		Piyumi      Created
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
import com.fusionx.lending.origination.domain.SalaryExpenseType;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.exception.PageableLengthException;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.resource.SalaryExpenseTypeAddResource;
import com.fusionx.lending.origination.resource.SalaryExpenseTypeUpdateResource;
import com.fusionx.lending.origination.resource.SuccessAndErrorDetailsResource;
import com.fusionx.lending.origination.service.SalaryExpenseTypeService;

@RestController
@RequestMapping(value = "/salary-expense-type")
@CrossOrigin(origins = "*")
public class SalaryExpenseTypeController extends MessagePropertyBase {
	
	private SalaryExpenseTypeService salaryExpenseTypeService;
	
	@Autowired
	public void setSalaryExpenseTypeService(SalaryExpenseTypeService salaryExpenseTypeService) {
		this.salaryExpenseTypeService = salaryExpenseTypeService;
	}
	
	/**
	 * Get the all salary expense types.
	 *
	 * @param tenantId - the tenant id
	 * @return the all salary expense types
	 */	
	@GetMapping("/{tenantId}/all")
	public ResponseEntity<Object> getAll(@PathVariable(value = "tenantId") String tenantId){		
		List<SalaryExpenseType> isPresentSalaryExpenseTypeList = salaryExpenseTypeService.findAll();
		
		if(!isPresentSalaryExpenseTypeList.isEmpty()) {
			return new ResponseEntity<>(isPresentSalaryExpenseTypeList,HttpStatus.OK);
		}
		else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Get the salary expense types by id.
	 *
	 * @param tenantId - the tenant id
	 * @param id - the id
	 * @return the salary expense types by id
	 */
	@GetMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> getSalaryExpenseTypeById(@PathVariable(value = "tenantId") String tenantId,
													         	  @PathVariable(value = "id") Long id){
		
		Optional<SalaryExpenseType> isPresentSalaryExpenseType = salaryExpenseTypeService.findById(id);
		
		if(isPresentSalaryExpenseType.isPresent()) {
			return new ResponseEntity<>(isPresentSalaryExpenseType.get(), HttpStatus.OK);
		}
		else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Get the salary expense types by status.
	 *
	 * @param tenantId - the tenant id
	 * @param status - the status
	 * @return the salary expense types by status
	 */
	@GetMapping(value = "/{tenantId}/status/{status}")
	public ResponseEntity<Object> getSalaryExpenseTypeByStatus(@PathVariable(value = "tenantId") String tenantId,
													   			      @PathVariable(value = "status") String status){
		
		
		if(status.equals(CommonStatus.ACTIVE.toString()) || status.equals(CommonStatus.INACTIVE.toString())) {
			List<SalaryExpenseType> isPresentSalaryExpenseTypeList = salaryExpenseTypeService.findByStatus(status);
			if(!isPresentSalaryExpenseTypeList.isEmpty()) {
				return new ResponseEntity<>(isPresentSalaryExpenseTypeList, HttpStatus.OK);
			}
			else {
				SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
				responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
				return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
			}
		}
		else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(RECORD_NOT_FOUND);
	        throw new ValidateRecordException(environment.getProperty(COMMON_STATUS_PATTERN), "message");
		}
	}
	
	/**
	 * Add the salary expense type.
	 *
	 * @param tenantId - the tenant id
	 * @param SalaryExpenseTypeAddResource - the salary expense type add resource
	 * @return the response entity
	 */
	@PostMapping("/{tenantId}")
	public ResponseEntity<Object> addSalaryExpenseType(@PathVariable(value = "tenantId") String tenantId,
			   									       		  @Valid @RequestBody SalaryExpenseTypeAddResource salaryExpenseTypeAddResource){
		salaryExpenseTypeService.addSalaryExpenseType(tenantId, salaryExpenseTypeAddResource);
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource(getEnvironment().getProperty(RECORD_CREATED));
		return new ResponseEntity<>(responseMessage,HttpStatus.CREATED);
	}
	
	/**
	 * Update salary expense type.
	 *
	 * @param tenantId - the tenant id
	 * @param id - the id
	 * @param SalaryExpenseTypeUpdateResource - the salary expense type update resource
	 * @return the response entity
	 */
	@PutMapping(value = "{tenantId}/{id}")
	public ResponseEntity<Object> updateSalaryExpenseType(@PathVariable(value = "tenantId") String tenantId, 
												                 @PathVariable(value = "id") Long id, 
												                 @Valid @RequestBody SalaryExpenseTypeUpdateResource salaryExpenseTypeUpdateResource){
		SuccessAndErrorDetailsResource successAndErrorDetailsResource=new SuccessAndErrorDetailsResource();
		Optional<SalaryExpenseType> isPresentSalaryExpenseType = salaryExpenseTypeService.findById(id);		
		
		if(isPresentSalaryExpenseType.isPresent()) {
			SalaryExpenseType salaryExpenseType = salaryExpenseTypeService.updateSalaryExpenseType(tenantId, id, salaryExpenseTypeUpdateResource);
			successAndErrorDetailsResource = new SuccessAndErrorDetailsResource(getEnvironment().getProperty(RECORD_UPDATED), salaryExpenseType.getId().toString());
			return new ResponseEntity<>(successAndErrorDetailsResource,HttpStatus.OK);
		}
		else {
			successAndErrorDetailsResource.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
	
	/**
	 * Get the salary expense types by code.
	 *
	 * @param tenantId - the tenant id
	 * @param code - the code
	 * @return the salary expense types by code
	 */
	@GetMapping(value = "/{tenantId}/code/{code}")
	public ResponseEntity<Object> getSalaryExpenseTypeByCode(@PathVariable(value = "tenantId") String tenantId,
													   			      @PathVariable(value = "code") String code){
		
		
		List<SalaryExpenseType> isPresentSalaryExpenseTypeList = salaryExpenseTypeService.findByCode(code);
			if(!isPresentSalaryExpenseTypeList.isEmpty()) {
				return new ResponseEntity<>(isPresentSalaryExpenseTypeList, HttpStatus.OK);
			}
			else {
				SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
				responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
				return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
			}
		
	}
	
	/**
	 * Search salary expense type
	 *
	 * @param tenantId - the tenant id
	 * @param searchQ - the searchQ
	 * @param name - the name
	 * @param code - the code
	 * @param pageable - the pageable
	 * @return the page
	 */
	@GetMapping(value = "/{tenantId}/search")
	public Page<SalaryExpenseType> searchSalaryExpenseType(@PathVariable(value = "tenantId", required = true) String tenantId,
			@RequestParam(value = "searchQ", required = false) String searchQ,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "code", required = false) String code,
			@PageableDefault Pageable pageable) {
		if(pageable.getPageSize()>Constants.MAX_PAGEABLE_LENGTH) 
			throw new PageableLengthException(environment.getProperty(PAGEABLE_LENGTH));
		return salaryExpenseTypeService.search(searchQ, name, code , pageable);
	}
	

}
