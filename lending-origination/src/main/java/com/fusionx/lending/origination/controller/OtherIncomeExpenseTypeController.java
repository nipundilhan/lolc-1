package com.fusionx.lending.origination.controller;
/**
 * Other Income Expense Type Controller
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   19-08-2021   	FXL-524   	 FX-542		Piyumi      Created
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
import com.fusionx.lending.origination.domain.OtherIncomeExpenseType;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.resource.OtherIncomeExpenseTypeAddResource;
import com.fusionx.lending.origination.resource.OtherIncomeExpenseTypeUpdateResource;
import com.fusionx.lending.origination.resource.SuccessAndErrorDetailsResource;
import com.fusionx.lending.origination.service.OtherIncomeExpenseTypeService;

@RestController
@RequestMapping(value = "/expense-type-other-income")
@CrossOrigin(origins = "*")
public class OtherIncomeExpenseTypeController extends MessagePropertyBase {
	
	private OtherIncomeExpenseTypeService otherIncomeExpenseTypeService;
	
	@Autowired
	public void setOtherIncomeExpenseTypeService(OtherIncomeExpenseTypeService otherIncomeExpenseTypeService) {
		this.otherIncomeExpenseTypeService = otherIncomeExpenseTypeService;
	}
	
	/**
	 * Get the all other income expense types.
	 *
	 * @param tenantId - the tenant id
	 * @return the all other income expense types
	 */	
	@GetMapping("/{tenantId}/all")
	public ResponseEntity<Object> getAll(@PathVariable(value = "tenantId") String tenantId){		
		List<OtherIncomeExpenseType> isPresentOtherIncomeExpenseTypeList = otherIncomeExpenseTypeService.findAll();
		
		if(!isPresentOtherIncomeExpenseTypeList.isEmpty()) {
			return new ResponseEntity<>(isPresentOtherIncomeExpenseTypeList,HttpStatus.OK);
		}
		else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Get the other income expense types by id.
	 *
	 * @param tenantId - the tenant id
	 * @param id - the id
	 * @return the other income expense types by id
	 */
	@GetMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> getOtherIncomeExpenseTypeById(@PathVariable(value = "tenantId") String tenantId,
													         	  @PathVariable(value = "id") Long id){
		
		Optional<OtherIncomeExpenseType> isPresentOtherIncomeExpenseType = otherIncomeExpenseTypeService.findById(id);
		
		if(isPresentOtherIncomeExpenseType.isPresent()) {
			return new ResponseEntity<>(isPresentOtherIncomeExpenseType.get(), HttpStatus.OK);
		}
		else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Get the other income expense types by status.
	 *
	 * @param tenantId - the tenant id
	 * @param status - the status
	 * @return the other income expense types by status
	 */
	@GetMapping(value = "/{tenantId}/status/{status}")
	public ResponseEntity<Object> getOtherIncomeExpenseTypeByStatus(@PathVariable(value = "tenantId") String tenantId,
													   			      @PathVariable(value = "status") String status){
		
		
		if(status.equals(CommonStatus.ACTIVE.toString()) || status.equals(CommonStatus.INACTIVE.toString())) {
			List<OtherIncomeExpenseType> isPresentOtherIncomeExpenseTypeList = otherIncomeExpenseTypeService.findByStatus(status);
			if(!isPresentOtherIncomeExpenseTypeList.isEmpty()) {
				return new ResponseEntity<>(isPresentOtherIncomeExpenseTypeList, HttpStatus.OK);
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
	 * Add the other income expense type.
	 *
	 * @param tenantId - the tenant id
	 * @param otherIncomeTypeAddResource - the other income expense type add resource
	 * @return the response entity
	 */
	@PostMapping("/{tenantId}")
	public ResponseEntity<Object> addOtherIncomeExpenseType(@PathVariable(value = "tenantId") String tenantId,
			   									       		  @Valid @RequestBody OtherIncomeExpenseTypeAddResource otherIncomeExpenseTypeAddResource){
		otherIncomeExpenseTypeService.addOtherIncomeExpenseType(tenantId, otherIncomeExpenseTypeAddResource);
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource(getEnvironment().getProperty(RECORD_CREATED));
		return new ResponseEntity<>(responseMessage,HttpStatus.CREATED);
	}
	
	/**
	 * Update other income expense type.
	 *
	 * @param tenantId - the tenant id
	 * @param id - the id
	 * @param otherIncomeTypeUpdateResource - the other income expense type update resource
	 * @return the response entity
	 */
	@PutMapping(value = "{tenantId}")
	public ResponseEntity<Object> updateOtherIncomeExpenseType(@PathVariable(value = "tenantId") String tenantId, 
												                 @Valid @RequestBody OtherIncomeExpenseTypeUpdateResource otherIncomeExpenseTypeUpdateResource){
		SuccessAndErrorDetailsResource successAndErrorDetailsResource=new SuccessAndErrorDetailsResource();
		//Optional<OtherIncomeExpenseType> isPresentOtherIncomeExpenseType = otherIncomeExpenseTypeService.findById(id);		
		
			otherIncomeExpenseTypeService.updateOtherIncomeExpenseType(tenantId, otherIncomeExpenseTypeUpdateResource);
			successAndErrorDetailsResource = new SuccessAndErrorDetailsResource(getEnvironment().getProperty(RECORD_UPDATED));
			return new ResponseEntity<>(successAndErrorDetailsResource,HttpStatus.OK);
		
	}

}
