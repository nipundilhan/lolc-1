package com.fusionx.lending.origination.controller;

/**
 * 	Business Income Expenses Controller
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   15-09-2021   FXL-115  	 FXL-785       Piyumi       Created
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
import com.fusionx.lending.origination.domain.BusinessIncomeExpenses;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.resource.BusinessIncomeExpenseTypeResource;
import com.fusionx.lending.origination.resource.BusinessIncomeExpensesAddResource;
import com.fusionx.lending.origination.service.BusinessIncomeExpensesService;
import com.fusionx.lending.origination.resource.SuccessAndErrorDetailsResource;


@RestController
@RequestMapping(value = "/business-income-expenses")
@CrossOrigin(origins = "*")
public class BusinessIncomeExpensesController extends MessagePropertyBase{
	
	
	private BusinessIncomeExpensesService businessIncomeExpensesService;
	
	@Autowired
	public void setBusinessIncomeExpensesService(BusinessIncomeExpensesService businessIncomeExpensesService) {
		this.businessIncomeExpensesService = businessIncomeExpensesService;
	}
	
	@GetMapping("/{tenantId}/all")
	public ResponseEntity<Object> getAllBusinessIncomeExpenses(@PathVariable(value = "tenantId") String tenantId){
		List<BusinessIncomeExpenses> businessIncomeExpenses = businessIncomeExpensesService.findAll();
		if(!businessIncomeExpenses.isEmpty()) {
			return new ResponseEntity<>(businessIncomeExpenses, HttpStatus.OK);
		}
		else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Get the BusinessIncomeExpenses by id.
	 *
	 * @param tenantId - the tenant id
	 * @param id - the id
	 * @return the BusinessIncomeExpenses by id
	 */
	@GetMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> getBusinessIncomeExpensesById(@PathVariable(value = "tenantId" ) String tenantId,
													         	  @PathVariable(value = "id") Long id){
			
		Optional<BusinessIncomeExpenses> isPresentBusinessIncomeExpenses = businessIncomeExpensesService.findById(id);
		if(isPresentBusinessIncomeExpenses.isPresent()) {
			return new ResponseEntity<>(isPresentBusinessIncomeExpenses.get(), HttpStatus.OK);
		}
		else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Get the BusinessIncomeExpensess by status.
	 *
	 * @param tenantId - the tenant id
	 * @param status - the status
	 * @return the BusinessIncomeExpenses by status
	 */
	
	@GetMapping(value = "/{tenantId}/status/{status}")
	public ResponseEntity<Object> getBusinessIncomeExpensesByStatus(@PathVariable(value = "tenantId") String tenantId,
			@PathVariable(value = "status") String status) {

		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		if(status.equals(CommonStatus.ACTIVE.toString()) || status.equals(CommonStatus.INACTIVE.toString())) {
			List<BusinessIncomeExpenses> businessIncomeExpenses = businessIncomeExpensesService.findByStatus(status);
			if(!businessIncomeExpenses.isEmpty()) {
				return new ResponseEntity<>(businessIncomeExpenses, HttpStatus.OK);
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
	 * Add the BusinessIncomeExpenses.
	 *
	 * @param tenantId - the tenant id
	 * @param BusinessIncomeExpensesAddResource - the business income expenses add resource
	 * @return the response entity
	 */
	
	@PostMapping("/{tenantId}")
	public ResponseEntity<Object> addBusinessIncomeExpenses(@PathVariable(value = "tenantId") String tenantId,
			   									       		  @Valid @RequestBody BusinessIncomeExpensesAddResource businessIncomeExpensesAddResource){
		
		businessIncomeExpensesService.save(tenantId, businessIncomeExpensesAddResource);
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource(getEnvironment().getProperty(RECORD_CREATED));
		return new ResponseEntity<>(responseMessage,HttpStatus.CREATED);
	}
	
	/**
	 * Update BusinessIncomeExpenses.
	 *
	 * @param tenantId - the tenant id
	 * @param id - the id
	 * @param BusinessIncomeExpenseTypeResource - the  business income expense type resource
	 * @return the response entity
	 */
	
	@PutMapping(value = "{tenantId}/{id}")
	public ResponseEntity<Object> updateBusinessIncomeExpenses(@PathVariable(value = "tenantId") String tenantId, 
												                 @PathVariable(value = "id") Long id, 
												                 @Valid @RequestBody BusinessIncomeExpenseTypeResource businessIncomeExpenseTypeResource){
		
		SuccessAndErrorDetailsResource successAndErrorExpensesResource=new SuccessAndErrorDetailsResource();
		Optional<BusinessIncomeExpenses> isPresentBusinessIncomeExpenses = businessIncomeExpensesService.findById(id);	
		
		if(isPresentBusinessIncomeExpenses.isPresent()) {
			BusinessIncomeExpenses businessIncomeExpenses = businessIncomeExpensesService.update(tenantId, id, businessIncomeExpenseTypeResource);
			
			successAndErrorExpensesResource = new SuccessAndErrorDetailsResource(getEnvironment().getProperty(RECORD_UPDATED), businessIncomeExpenses.getId().toString());
			return new ResponseEntity<>(successAndErrorExpensesResource,HttpStatus.OK);
		}
		else {
			successAndErrorExpensesResource.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(successAndErrorExpensesResource, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
	
	/**
	 * Get the BusinessIncomeExpenses by businessIncomeDetailsId.
	 *
	 * @param tenantId - the tenant id
	 * @param businessIncomeDetailsId - the business income details id
	 * @return the BusinessIncomeExpenses by businessIncomeDetailsId
	 */
	@GetMapping(value = "/{tenantId}/business-income-details/{businessIncomeDetailsId}")
	public ResponseEntity<Object> getByBusinessIncomeDetailId(@PathVariable(value = "tenantId" ) String tenantId,
													         	  @PathVariable(value = "businessIncomeDetailsId") Long businessIncomeDetailsId){
			
		List<BusinessIncomeExpenses> businessIncomeExpenses = businessIncomeExpensesService.findByBusinessIncomeDetailsId(businessIncomeDetailsId);
		if(!businessIncomeExpenses.isEmpty()) {
			return new ResponseEntity<>(businessIncomeExpenses, HttpStatus.OK);
		}
		else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
}
