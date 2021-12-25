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
import com.fusionx.lending.origination.domain.OtherIncome;
import com.fusionx.lending.origination.resource.GuarantorOtherIncomeAddResource;
import com.fusionx.lending.origination.resource.GuarantorOtherIncomeUpdateResource;
import com.fusionx.lending.origination.resource.SuccessAndErrorDetailsResource;
import com.fusionx.lending.origination.service.GuarantorOtherIncomeService;

/**
 * The Class GuarantorOtherIncomeController.
 */
@RestController
@RequestMapping(value = "/guarantor-other-income")
@CrossOrigin(origins = "*")
public class GuarantorOtherIncomeController extends MessagePropertyBase{

	private GuarantorOtherIncomeService guarantorOtherIncomeService;

	@Autowired
	public void setGuarantorOtherIncomeService(GuarantorOtherIncomeService guarantorOtherIncomeService) {
		this.guarantorOtherIncomeService = guarantorOtherIncomeService;
	}

	/**
	 * Gets the all other income details.
	 *
	 * @param tenantId the tenant id
	 * @return the all other income details
	 */
	@GetMapping("/{tenantId}/all")
	public ResponseEntity<Object> getAllOtherIncomeDetails(@PathVariable(value = "tenantId") String tenantId) {
		List<OtherIncome> otherIncomes = guarantorOtherIncomeService.findAll(tenantId);
		if (!otherIncomes.isEmpty()) {
			return new ResponseEntity<>(otherIncomes, HttpStatus.OK);
		} else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}


	/**
	 * Gets the other income details by id.
	 *
	 * @param tenantId the tenant id
	 * @param id the id
	 * @return the other income details by id
	 */
	@GetMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> getOtherIncomeDetailsById(@PathVariable(value = "tenantId") String tenantId,
			@PathVariable(value = "id") Long id) {

		Optional<OtherIncome> isPresentOtherIncome = guarantorOtherIncomeService
				.findById(tenantId, id);
		if (isPresentOtherIncome.isPresent()) {
			return new ResponseEntity<>(isPresentOtherIncome.get(), HttpStatus.OK);
		} else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	/**
	 * Adds the other income details.
	 *
	 * @param tenantId the tenant id
	 * @param guarantorOtherIncomeAddResource the guarantor other income add resource
	 * @return the response entity
	 */
	@PostMapping("/{tenantId}")
	public ResponseEntity<Object> addOtherIncomeDetails(@PathVariable(value = "tenantId") String tenantId,
			@Valid @RequestBody GuarantorOtherIncomeAddResource guarantorOtherIncomeAddResource) {
		guarantorOtherIncomeService.saveOtherIncomes(tenantId, guarantorOtherIncomeAddResource);
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource(
				getEnvironment().getProperty(RECORD_CREATED));
		return new ResponseEntity<>(responseMessage, HttpStatus.CREATED);
	}

	/**
	 * Update other income details.
	 *
	 * @param tenantId the tenant id
	 * @param id the id
	 * @param guarantorOtherIncomeUpdateResource the guarantor other income update resource
	 * @return the response entity
	 */
	@PutMapping("/{tenantId}/{id}")
	public ResponseEntity<Object> updateOtherIncomeDetails(
			@PathVariable(value = "tenantId") String tenantId,
			@PathVariable(value = "id") Long id, 
			@Valid @RequestBody GuarantorOtherIncomeUpdateResource guarantorOtherIncomeUpdateResource) {
		SuccessAndErrorDetailsResource successAndErrorDetailsResource = new SuccessAndErrorDetailsResource();
		Optional<OtherIncome> isPresentOtherIncome = guarantorOtherIncomeService.findById(tenantId, id);	
		if(isPresentOtherIncome.isPresent()) {
			OtherIncome otherIncome = guarantorOtherIncomeService.updateOtherIncome(tenantId, id, guarantorOtherIncomeUpdateResource);
			successAndErrorDetailsResource = new SuccessAndErrorDetailsResource(getEnvironment().getProperty(RECORD_UPDATED), otherIncome.getId().toString());
			return new ResponseEntity<>(successAndErrorDetailsResource,HttpStatus.OK);
		}
		else {
			successAndErrorDetailsResource.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
	
	/**
	 * Gets the other income details by guarantor id.
	 *
	 * @param tenantId the tenant id
	 * @param guarantorId the guarantor id
	 * @return the other income details by guarantor id
	 */
	@GetMapping(value = "/{tenantId}/guarantor/{guarantorId}")
	public ResponseEntity<Object> getOtherIncomeDetailsByGuarantorId(
			@PathVariable(value = "tenantId") String tenantId,
			@PathVariable(value = "guarantorId") Long guarantorId) {
		
		List<OtherIncome> otherIncomes = guarantorOtherIncomeService.findByIdGuarantorId(tenantId,guarantorId);
		
		if (otherIncomes != null && !otherIncomes.isEmpty()) {
			return new ResponseEntity<>(otherIncomes, HttpStatus.OK);
		} else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
}
