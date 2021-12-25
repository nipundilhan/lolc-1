package com.fusionx.lending.origination.controller;

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
import com.fusionx.lending.origination.domain.BusinessIncomeExpense;
import com.fusionx.lending.origination.resource.GuarantorBusinessIncomeAddResource;
import com.fusionx.lending.origination.resource.GuarantorBusinessIncomeUpdateResource;
import com.fusionx.lending.origination.resource.SuccessAndErrorDetailsResource;
import com.fusionx.lending.origination.service.GuarantorBusinessIncomeService;

/**
 * The Class GuarantorBusinessIncomeController.
 */
@RestController
@RequestMapping(value = "/guarantor-business-income")
@CrossOrigin(origins = "*")
public class GuarantorBusinessIncomeController extends MessagePropertyBase{

	/** The guarantor business income service. */
	private GuarantorBusinessIncomeService guarantorBusinessIncomeService;

	/**
	 * Sets the guarantor business income service.
	 *
	 * @param guarantorBusinessIncomeService the new guarantor business income service
	 */
	@Autowired
	public void setGuarantorBusinessIncomeService(GuarantorBusinessIncomeService guarantorBusinessIncomeService) {
		this.guarantorBusinessIncomeService = guarantorBusinessIncomeService;
	}
	
	/**
	 * Gets the all business income details.
	 *
	 * @param tenantId the tenant id
	 * @return the all business income details
	 */
	@GetMapping("/{tenantId}/all")
	public ResponseEntity<Object> getAllBusinessIncomeDetails(@PathVariable(value = "tenantId") String tenantId) {
		List<BusinessIncomeExpense> businessIncomeExpenses = guarantorBusinessIncomeService.findAll(tenantId);
		if (!businessIncomeExpenses.isEmpty()) {
			return new ResponseEntity<>(businessIncomeExpenses, HttpStatus.OK);
		} else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}


	/**
	 * Gets the business income details by id.
	 *
	 * @param tenantId the tenant id
	 * @param id the id
	 * @return the business income details by id
	 */
	@GetMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> getBusinessIncomeDetailsById(@PathVariable(value = "tenantId") String tenantId,
			@PathVariable(value = "id") Long id) {

		Optional<BusinessIncomeExpense> isPresentBusinessIncomeExpense = guarantorBusinessIncomeService
				.findById(tenantId, id);
		if (isPresentBusinessIncomeExpense.isPresent()) {
			return new ResponseEntity<>(isPresentBusinessIncomeExpense.get(), HttpStatus.OK);
		} else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	/**
	 * Adds the business income details.
	 *
	 * @param tenantId the tenant id
	 * @param guarantorBusinessIncomeAddResource the guarantor business income add resource
	 * @return the response entity
	 */
	@PostMapping("/{tenantId}")
	public ResponseEntity<Object> addBusinessIncomeDetails(@PathVariable(value = "tenantId") String tenantId,
			@Valid @RequestBody GuarantorBusinessIncomeAddResource guarantorBusinessIncomeAddResource) {
		guarantorBusinessIncomeService.saveBusinessIncomes(tenantId, guarantorBusinessIncomeAddResource);
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource(
				getEnvironment().getProperty(RECORD_CREATED));
		return new ResponseEntity<>(responseMessage, HttpStatus.CREATED);
	}

	/**
	 * Update business income details.
	 *
	 * @param tenantId the tenant id
	 * @param id the id
	 * @param guarantorBusinessIncomeUpdateResource the guarantor business income update resource
	 * @return the response entity
	 */
	@PutMapping("/{tenantId}/{id}")
	public ResponseEntity<Object> updateBusinessIncomeDetails(
			@PathVariable(value = "tenantId") String tenantId,
			@PathVariable(value = "id") Long id, 
			@Valid @RequestBody GuarantorBusinessIncomeUpdateResource guarantorBusinessIncomeUpdateResource) {
		SuccessAndErrorDetailsResource successAndErrorDetailsResource = new SuccessAndErrorDetailsResource();
		Optional<BusinessIncomeExpense> isPresentBusinessIncomeExpense = guarantorBusinessIncomeService.findById(tenantId, id);	
		if(isPresentBusinessIncomeExpense.isPresent()) {
			BusinessIncomeExpense businessIncomeExpense = guarantorBusinessIncomeService.updateBusinessIncome(tenantId, id, guarantorBusinessIncomeUpdateResource);
			successAndErrorDetailsResource = new SuccessAndErrorDetailsResource(getEnvironment().getProperty(RECORD_UPDATED), businessIncomeExpense.getId().toString());
			return new ResponseEntity<>(successAndErrorDetailsResource,HttpStatus.OK);
		}
		else {
			successAndErrorDetailsResource.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
	
	/**
	 * Gets the business income details by guarantor id.
	 *
	 * @param tenantId the tenant id
	 * @param guarantorId the guarantor id
	 * @return the business income details by guarantor id
	 */
	@GetMapping(value = "/{tenantId}/guarantor/{guarantorId}")
	public ResponseEntity<Object> getBusinessIncomeDetailsByGuarantorId(
			@PathVariable(value = "tenantId") String tenantId,
			@PathVariable(value = "guarantorId") Long guarantorId) {
		
		List<BusinessIncomeExpense> businessIncomeExpenses = guarantorBusinessIncomeService.findByIdGuarantorId(tenantId,guarantorId);
		
		if (businessIncomeExpenses != null && !businessIncomeExpenses.isEmpty()) {
			return new ResponseEntity<>(businessIncomeExpenses, HttpStatus.OK);
		} else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

}
