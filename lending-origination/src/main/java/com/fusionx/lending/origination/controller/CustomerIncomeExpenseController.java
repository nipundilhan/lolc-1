package com.fusionx.lending.origination.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fusionx.lending.origination.core.LoggerRequest;
import com.fusionx.lending.origination.core.LogginAuthentcation;
import com.fusionx.lending.origination.exception.UserNotFound;
import com.fusionx.lending.origination.resource.CustomerIncomeExpenseRequestResource;
import com.fusionx.lending.origination.resource.IncomeExpenseSummaryResponseResource;
import com.fusionx.lending.origination.resource.SuccessAndErrorDetailsResource;
import com.fusionx.lending.origination.service.CustomerIncomeExpenseService;

/**
 * Customer Income Expense Controller
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *   1   13-05-2021      		     			MenukaJ       Created
 *   2	 13-MAY-2021							Sanatha		  Added Customer Other/Salary/HouseHold and Financial commitment income expense details. Added Customer income expense summary.(Parallel  dev with Menuka) 
 ********************************************************************************************************
 */

@RestController
@RequestMapping(value = "/customer-income-expence")
@CrossOrigin(origins = "*")
public class CustomerIncomeExpenseController {
	
	@Autowired
	private CustomerIncomeExpenseService customerIncomeExpenseService;
	
	@Autowired
	private Environment environment;
	
	private String userNotFound = "common.user-not-found";
	private String commonSaved = "common.saved";
	private String recordNotFound = "common.record-not-found";
	
	/**
	 * Save Customer Income Expense
	 *
	 * @param tenantId
	 * @param customerId
	 * @param CustomerIncomeExpenseRequestResource
	 * @return SuccessAndErrorDetailsResource
	 */
	@PostMapping(value = "/{tenantId}/{customerId}")
	public ResponseEntity<Object> saveCustomerIncomeExpense(@PathVariable(value = "tenantId", required = true) String tenantId, 
			@PathVariable(value = "customerId", required = true) Long customerId,
			@Valid @RequestBody CustomerIncomeExpenseRequestResource customerIncomeExpenseRequestResource) {
		if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new UserNotFound(environment.getProperty(userNotFound));
		customerIncomeExpenseService.saveCustomerIncomeExpense(tenantId, customerId, customerIncomeExpenseRequestResource);
		SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty(commonSaved));
		return new ResponseEntity<>(successDetailsDto,HttpStatus.CREATED);
	}
	
	/**
	 * Get Customer Income Expense all details.
	 *
	 * @author Sanatha
	 * @param tenantId
	 * @param id (customer ID)
	 * @return SuccessAndErrorDetailsResource
	 */
	@GetMapping(value = "/{tenantId}/{id}/summary")
	public ResponseEntity<Object> getSummary(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		LoggerRequest.getInstance().logInfo1("******<<<<<<#############>>>>>>******");
 		LoggerRequest.getInstance().logInfo1("******<<<<<<IN getSummary>>>>>>******");
 		IncomeExpenseSummaryResponseResource incomeExpenseSummaryResponseResource =customerIncomeExpenseService.incomeExpenseSummary(id);
		if (incomeExpenseSummaryResponseResource != null) {
			return new ResponseEntity<>(incomeExpenseSummaryResponseResource, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

}
