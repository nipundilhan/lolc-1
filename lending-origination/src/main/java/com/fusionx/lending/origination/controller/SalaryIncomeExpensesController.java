package com.fusionx.lending.origination.controller;

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
import com.fusionx.lending.origination.core.LogginAuthentcation;
import com.fusionx.lending.origination.domain.SalaryIncomeExpenses;
import com.fusionx.lending.origination.exception.UserNotFound;
import com.fusionx.lending.origination.resource.SalaryIncomeExpenseDetailResponse;
import com.fusionx.lending.origination.resource.SalaryIncomeExpensesAddMainResource;
import com.fusionx.lending.origination.resource.SalaryIncomeExpensesAddResource;
import com.fusionx.lending.origination.resource.SalaryIncomeExpensesUpdateMainResource;
import com.fusionx.lending.origination.resource.SalaryIncomeExpensesUpdateResource;
import com.fusionx.lending.origination.resource.SuccessAndErrorDetailsResource;
import com.fusionx.lending.origination.service.SalaryIncomeExpensesService;

/**
 * API Service related to salary income expense
 *
 * @author Nipun Dilhan
 * @version 1.0.0
 * @since 1.0.0
 * <p>
 * <br/><br/>
 * <b>Change History : </b>
 * <br/><br/>
 * #        Date            Story Point     Task No     Author                  Description
 * <br/>
 * .....................................................................................................................<br/>
 * 1        12-09-2021      -               FXL-784     Nipun Dilhan      Created
 * <p>
 *
 */

@RestController
@RequestMapping(value = "/salary-income-expenses")
@CrossOrigin(origins = "*")
public class SalaryIncomeExpensesController   extends MessagePropertyBase  {
	

	SalaryIncomeExpensesService salaryIncomeExpensesService;
	
	
	@Autowired
	public void setSalaryIncomeExpensesService(SalaryIncomeExpensesService salaryIncomeExpensesService) {
		this.salaryIncomeExpensesService = salaryIncomeExpensesService;
	}
	
	
	/**
	 * Add the SalaryIncomeDetails.
	 *
	 * @param tenantId - the tenant id
	 * @param SalaryIncomeDetailsAddResource - the income source details add resource
	 * @return the response entity
	 */
	
	@PostMapping("/{tenantId}/salary-income-detail/{id}")
	public ResponseEntity<Object> addSalaryIncomeExpensesDetails(@PathVariable(value = "tenantId") String tenantId,
																	@PathVariable(value = "id") Long id,
			   									       		  @Valid @RequestBody SalaryIncomeExpensesAddResource salaryIncomeExpensesAddResource){
		
		if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new UserNotFound(environment.getProperty(USER_NOT_FOUND));
		
		SalaryIncomeExpenses salaryIncomeExpense = salaryIncomeExpensesService.saveIncomeExpense(tenantId, LogginAuthentcation.getInstance().getUserName(), id, salaryIncomeExpensesAddResource);
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource(getEnvironment().getProperty(RECORD_CREATED),salaryIncomeExpense.getId().toString());
		return new ResponseEntity<>(responseMessage,HttpStatus.CREATED);
	}
	
	
	@PutMapping("/{tenantId}/salary-income-detail/{id}")
	public ResponseEntity<Object> updateSalaryIncomeExpensesDetails(@PathVariable(value = "tenantId") String tenantId,
																@PathVariable(value = "id") Long id,
			   									       		  @Valid @RequestBody SalaryIncomeExpensesUpdateResource salaryIncomeExpensesUpdateResource){
		
		if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new UserNotFound(environment.getProperty(USER_NOT_FOUND));

		SalaryIncomeExpenses salaryIncomeExpense = salaryIncomeExpensesService.updateIncomeExpense(tenantId, LogginAuthentcation.getInstance().getUserName(), id, salaryIncomeExpensesUpdateResource);
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource(getEnvironment().getProperty(RECORD_UPDATED),salaryIncomeExpense.getId().toString());
		return new ResponseEntity<>(responseMessage,HttpStatus.CREATED);
	}
	
    /**
     * Get Country mapping pending details by master defintion pending id
     *
     * @param tenantId the id of tenant
     * @param id       the id of the master definition pending
     * @return list of countries if record exists, otherwise <code>204</code>
     */
	@GetMapping(value = "/{tenantId}/salary-income-detail/{id}")
	public ResponseEntity<Object> getCountryMappingDetailsByMasterDefPendingId(
			@PathVariable(value = "tenantId") String tenantId,
			@PathVariable(value = "id") Long id) {
		
		SalaryIncomeExpenseDetailResponse response = salaryIncomeExpensesService.getSalaryIncomeExpenseDetails(id);
		if (response != null) {
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
    /**
     * Get Country mapping pending details by master defintion pending id
     *
     * @param tenantId the id of tenant
     * @param id       the id of the master definition pending
     * @return list of countries if record exists, otherwise <code>204</code>
     */
	@GetMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> getById(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id) {
		
		SalaryIncomeExpenses response = salaryIncomeExpensesService.findById(id);
		if (response != null) {
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

}
