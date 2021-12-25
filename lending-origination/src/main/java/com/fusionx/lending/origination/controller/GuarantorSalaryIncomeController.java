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
import com.fusionx.lending.origination.domain.SalaryIncome;
import com.fusionx.lending.origination.resource.GuarantorSalaryIncomeAddResource;
import com.fusionx.lending.origination.resource.GuarantorSalaryIncomeUpdateResource;
import com.fusionx.lending.origination.resource.SuccessAndErrorDetailsResource;
import com.fusionx.lending.origination.service.GuarantorSalaryIncomeService;

/**
 * The Class GuarantorSalaryIncomeController.
 */
@RestController
@RequestMapping(value = "/guarantor-salary-income")
@CrossOrigin(origins = "*")
public class GuarantorSalaryIncomeController extends MessagePropertyBase{

	private GuarantorSalaryIncomeService guarantorSalaryIncomeService;

	@Autowired
	public void setGuarantorSalaryIncomeService(GuarantorSalaryIncomeService guarantorSalaryIncomeService) {
		this.guarantorSalaryIncomeService = guarantorSalaryIncomeService;
	}

	/**
	 * Gets the all salary income details.
	 *
	 * @param tenantId the tenant id
	 * @return the all salary income details
	 */
	@GetMapping("/{tenantId}/all")
	public ResponseEntity<Object> getAllSalaryIncomeDetails(@PathVariable(value = "tenantId") String tenantId) {
		List<SalaryIncome> salaryIncomes = guarantorSalaryIncomeService.findAll(tenantId);
		if (!salaryIncomes.isEmpty()) {
			return new ResponseEntity<>(salaryIncomes, HttpStatus.OK);
		} else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}


	/**
	 * Gets the salary income details by id.
	 *
	 * @param tenantId the tenant id
	 * @param id the id
	 * @return the salary income details by id
	 */
	@GetMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> getSalaryIncomeDetailsById(@PathVariable(value = "tenantId") String tenantId,
			@PathVariable(value = "id") Long id) {

		Optional<SalaryIncome> isPresentSalaryIncome = guarantorSalaryIncomeService
				.findById(tenantId, id);
		if (isPresentSalaryIncome.isPresent()) {
			return new ResponseEntity<>(isPresentSalaryIncome.get(), HttpStatus.OK);
		} else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	/**
	 * Adds the salary income details.
	 *
	 * @param tenantId the tenant id
	 * @param guarantorSalaryIncomeAddResource the guarantor salary income add resource
	 * @return the response entity
	 */
	@PostMapping("/{tenantId}")
	public ResponseEntity<Object> addSalaryIncomeDetails(@PathVariable(value = "tenantId") String tenantId,
			@Valid @RequestBody GuarantorSalaryIncomeAddResource guarantorSalaryIncomeAddResource) {
		guarantorSalaryIncomeService.saveSalaryIncomes(tenantId, guarantorSalaryIncomeAddResource);
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource(
				getEnvironment().getProperty(RECORD_CREATED));
		return new ResponseEntity<>(responseMessage, HttpStatus.CREATED);
	}

	/**
	 * Update salary income details.
	 *
	 * @param tenantId the tenant id
	 * @param id the id
	 * @param guarantorSalaryIncomeUpdateResource the guarantor salary income update resource
	 * @return the response entity
	 */
	@PutMapping("/{tenantId}/{id}")
	public ResponseEntity<Object> updateSalaryIncomeDetails(
			@PathVariable(value = "tenantId") String tenantId,
			@PathVariable(value = "id") Long id, 
			@Valid @RequestBody GuarantorSalaryIncomeUpdateResource guarantorSalaryIncomeUpdateResource) {
		SuccessAndErrorDetailsResource successAndErrorDetailsResource = new SuccessAndErrorDetailsResource();
		Optional<SalaryIncome> isPresentSalaryIncome = guarantorSalaryIncomeService.findById(tenantId, id);	
		if(isPresentSalaryIncome.isPresent()) {
			SalaryIncome salaryIncome = guarantorSalaryIncomeService.updateSalaryIncome(tenantId, id, guarantorSalaryIncomeUpdateResource);
			successAndErrorDetailsResource = new SuccessAndErrorDetailsResource(getEnvironment().getProperty(RECORD_UPDATED), salaryIncome.getId().toString());
			return new ResponseEntity<>(successAndErrorDetailsResource,HttpStatus.OK);
		}
		else {
			successAndErrorDetailsResource.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
	
	/**
	 * Gets the country mapping details by master def pending id.
	 *
	 * @param tenantId the tenant id
	 * @param guarantorId the guarantor id
	 * @return the country mapping details by master def pending id
	 */
	@GetMapping(value = "/{tenantId}/guarantor/{guarantorId}")
	public ResponseEntity<Object> getSalaryIncomeDetailsByGuarantorId(
			@PathVariable(value = "tenantId") String tenantId,
			@PathVariable(value = "guarantorId") Long guarantorId) {
		
		List<SalaryIncome> salaryIncomes = guarantorSalaryIncomeService.findByIdGuarantorId(tenantId,guarantorId);
		
		if (salaryIncomes != null && !salaryIncomes.isEmpty()) {
			return new ResponseEntity<>(salaryIncomes, HttpStatus.OK);
		} else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
}
