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
import com.fusionx.lending.origination.resource.GuarantorIncomeRequestResource;
import com.fusionx.lending.origination.resource.IncomeExpenseSummaryResponseResource;
import com.fusionx.lending.origination.resource.SuccessAndErrorDetailsResource;
import com.fusionx.lending.origination.service.GuarantorIncomeService;

/**
 * Guarantor Income Detail Service.
 * 
 ********************************************************************************************************
 *  ###   Date         	Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   04-MAY-2021   		      FX-6301    Sanatha      Initial Development.
 *    
 ********************************************************************************************************
 */
@RestController
@RequestMapping(value = "/guarantor-income")
@CrossOrigin(origins = "*")
public class GuarantorIncomeController {
	
	@Autowired
	private GuarantorIncomeService guarantorIncomeService;
	
	@Autowired
	private Environment environment;
	
	private String userNotFound = "common.user-not-found";
	private String commonSaved = "common.saved";
	private String recordNotFound = "common.record-not-found";
	
	/**
	 * Save Guarantor Income 
	 *
	 * @author Sanatha
	 * @param tenantId
	 * @param guarantorId
	 * @param GuarantorIncomeRequestResource
	 * @return SuccessAndErrorDetailsResource
	 */
	@PostMapping(value = "/{tenantId}/{guarantorId}")
	public ResponseEntity<Object> saveGuarantorIncome(@PathVariable(value = "tenantId", required = true) String tenantId, 
			@PathVariable(value = "guarantorId", required = true) Long guarantorId,
			@Valid @RequestBody GuarantorIncomeRequestResource guarantorIncomeRequestResource) {
		if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new UserNotFound(environment.getProperty(userNotFound));
		guarantorIncomeService.saveAndUpdateGuarantorIncomeExpense(tenantId, guarantorId, guarantorIncomeRequestResource);
		SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty(commonSaved));
		return new ResponseEntity<>(successDetailsDto,HttpStatus.CREATED);
	}
	
	/**
	 * Get Customer Income Expense all details.
	 *
	 * @author Sanatha
	 * @param tenantId
	 * @param id (guarantor ID)
	 * @return SuccessAndErrorDetailsResource
	 */	
	@GetMapping(value = "/{tenantId}/{id}/summary")
	public ResponseEntity<Object> getSummary(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		LoggerRequest.getInstance().logInfo1("******<<<<<<#############>>>>>>******");
 		LoggerRequest.getInstance().logInfo1("******<<<<<<IN getSummary>>>>>>******");
 		IncomeExpenseSummaryResponseResource incomeExpenseSummaryResponseResource =guarantorIncomeService.incomeExpenseSummary(id);
		if (incomeExpenseSummaryResponseResource != null) {
			return new ResponseEntity<>(incomeExpenseSummaryResponseResource, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}


}
