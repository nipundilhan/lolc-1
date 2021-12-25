package com.fusionx.lending.origination.controller;

/**
 * Other Income Category Controller
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   16-08-2021   FXL-639  	 FXL-535       Piyumi     Created
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
import com.fusionx.lending.origination.domain.OtherIncomeCategory;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.resource.OtherIncomeCategoryAddResource;
import com.fusionx.lending.origination.resource.OtherIncomeCategoryUpdateResource;
import com.fusionx.lending.origination.resource.SuccessAndErrorDetailsResource;
import com.fusionx.lending.origination.service.OtherIncomeCategoryService;


@RestController
@RequestMapping(value = "/other-income-category")
@CrossOrigin(origins = "*")
public class OtherIncomeCategoryController extends MessagePropertyBase{
	
	
	private OtherIncomeCategoryService otherIncomeCategoryService;
	
	@Autowired
	public void setOtherIncomeCategoryService(OtherIncomeCategoryService otherIncomeCategoryService) {
		this.otherIncomeCategoryService = otherIncomeCategoryService;
	}
	
	
	@GetMapping("/{tenantId}/all")
	public ResponseEntity<Object> getAllOtherIncomeCategory(@PathVariable(value = "tenantId", required = true) String tenantId){
		List<OtherIncomeCategory> isPresentOtherIncomeCategories = otherIncomeCategoryService.findAll();
		if(!isPresentOtherIncomeCategories.isEmpty()) {
			return new ResponseEntity<>(isPresentOtherIncomeCategories, HttpStatus.OK);
		}
		else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	
	@GetMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> getOtherIncomeCategoryById(@PathVariable(value = "tenantId", required = true) String tenantId,
													         	  @PathVariable(value = "id", required = true) Long id){
	
		Optional<OtherIncomeCategory> isPresentOtherIncomeCategory = otherIncomeCategoryService.findById(id);
		if(isPresentOtherIncomeCategory.isPresent()) {
			return new ResponseEntity<>(isPresentOtherIncomeCategory.get(), HttpStatus.OK);
		}
		else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	@GetMapping(value = "/{tenantId}/code/{code}")
	public ResponseEntity<Object> getOtherIncomeCategoryByCode(@PathVariable(value = "tenantId", required = true) String tenantId,
													   	            @PathVariable(value = "code", required = true) String code){
		
		Optional<OtherIncomeCategory> isPresentOtherIncomeCategory = otherIncomeCategoryService.findByCode(code);
		if(isPresentOtherIncomeCategory.isPresent()) {
			return new ResponseEntity<>(isPresentOtherIncomeCategory.get(), HttpStatus.OK);
		}
		else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	@GetMapping(value = "/{tenantId}/name/{name}")
	public ResponseEntity<Object> getOtherIncomeCategoryByName(@PathVariable(value = "tenantId", required = true) String tenantId,
															        @PathVariable(value = "name", required = true) String name){
	
		List<OtherIncomeCategory> isPresentOtherIncomeCategories = otherIncomeCategoryService.findByName(name);
		if(!isPresentOtherIncomeCategories.isEmpty()) {
			return new ResponseEntity<>(isPresentOtherIncomeCategories, HttpStatus.OK);
		}
		else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	@GetMapping(value = "/{tenantId}/status/{status}")
	public ResponseEntity<Object> getOtherIncomeCategoryByStatus(@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "status", required = true) String status) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		if(status.equals(CommonStatus.ACTIVE.toString()) || status.equals(CommonStatus.INACTIVE.toString())) {
			
			List<OtherIncomeCategory> isPresentOtherIncomeCategories = otherIncomeCategoryService.findByStatus(status);
			if(!isPresentOtherIncomeCategories.isEmpty()) {
				return new ResponseEntity<>(isPresentOtherIncomeCategories, HttpStatus.OK);
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
	
	
	@PostMapping("/{tenantId}")
	public ResponseEntity<Object> addOtherIncomeCategory(@PathVariable(value = "tenantId", required = true) String tenantId,
			   									       		  @Valid @RequestBody OtherIncomeCategoryAddResource otherIncomeCategoryAddResource){
		OtherIncomeCategory otherIncomeCategory = otherIncomeCategoryService.save(tenantId, otherIncomeCategoryAddResource);
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource(getEnvironment().getProperty(RECORD_CREATED), Long.toString(otherIncomeCategory.getId()));
		return new ResponseEntity<>(responseMessage,HttpStatus.CREATED);
	}
	
	
	@PutMapping(value = "{tenantId}/{id}")
	public ResponseEntity<Object> updateOtherIncomeCategory(@PathVariable(value = "tenantId", required = true) String tenantId, 
												                 @PathVariable(value = "id", required = true) Long id, 
												                 @Valid @RequestBody OtherIncomeCategoryUpdateResource otherIncomeCategoryUpdateResource){
		SuccessAndErrorDetailsResource successAndErrorDetailsResource=new SuccessAndErrorDetailsResource();
		Optional<OtherIncomeCategory>isPresentOtherIncomeCategory = otherIncomeCategoryService.findById(id);		
		if(isPresentOtherIncomeCategory.isPresent()) {
			OtherIncomeCategory otherIncomeCategory = otherIncomeCategoryService.update(tenantId, id, otherIncomeCategoryUpdateResource);
			successAndErrorDetailsResource = new SuccessAndErrorDetailsResource(getEnvironment().getProperty(RECORD_UPDATED), otherIncomeCategory.getId().toString());
			return new ResponseEntity<>(successAndErrorDetailsResource,HttpStatus.OK);
		}
		else {
			successAndErrorDetailsResource.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

}
