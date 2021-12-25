package com.fusionx.lending.product.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fusionx.lending.product.resources.BasicTcCalculatorResource;
import com.fusionx.lending.product.service.BasicTcCalculatorService;
/**
 * Basic Tc Calculator
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   01-10-2021   FXL-78        FXL-983    Dilki        Created
 *    2	  05-10-2021   FXL-78        FXL-985    Dilki        Created
 *    3   07-10-2021   FXL-78        FXL-1008   Dilki        Created
 *    4	  08-10-2021   FXL-78        FXL-984    Dilki        Created
 *    
 ********************************************************************************************************
 */

@RestController
@RequestMapping(value = "/basic-tc-calculator")
@CrossOrigin(origins = "*")
public class BasicTcCalculatorController {

	private BasicTcCalculatorService basicTcCalculatorService;

	@Autowired
	public void setNoOfDownPaymentCalculateService(BasicTcCalculatorService basicTcCalculatorService) {
		this.basicTcCalculatorService = basicTcCalculatorService;
	}

	/**
	 * Calculates the IRR
	 *
	 * @param tenantId                  the tenant id
	 * @param basicTcCalculatorResource the resource file
	 * @return the calculated IRR
	 */
	@PostMapping(value = "/{tenantId}/get-irr")
	public ResponseEntity<Object> calculateIrr(@PathVariable(value = "tenantId") String tenantId,
			@Valid @RequestBody BasicTcCalculatorResource basicTcCalculatorResource) {

		BasicTcCalculatorResource response = basicTcCalculatorService.calculateIrr(basicTcCalculatorResource);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	/**
	 * Calculates the no of Down Payments
	 *
	 * @param tenantId                  the tenant id
	 * @param basicTcCalculatorResource the resource file
	 * @return the calculated no of Down Payments
	 */
	@PostMapping(value = "/{tenantId}/get-no-of-pre-payments")
	public ResponseEntity<Object> calculateNoOfPrePayments(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@Valid @RequestBody BasicTcCalculatorResource basicTcCalculatorResource) {

		BasicTcCalculatorResource response = basicTcCalculatorService
				.calculateNoOfPrePayments(basicTcCalculatorResource);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	/**
	 * Calculates the Loan Amount
	 *
	 * @param tenantId                  the tenant id
	 * @param basicTcCalculatorResource the resource file
	 * @return the calculated Loan Amount
	 */
	@PostMapping(value = "/{tenantId}/get-loan-amount")
	public ResponseEntity<Object> calculateLoanAmount(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@Valid @RequestBody BasicTcCalculatorResource basicTcCalculatorResource) {

		BasicTcCalculatorResource response = basicTcCalculatorService.calculateLoanAmount(basicTcCalculatorResource);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	/**
	 * Calculates the Period of Loan
	 *
	 * @param tenantId                  the tenant id
	 * @param basicTcCalculatorResource the resource file
	 * @return the calculated Period of Loan
	 */
	@PostMapping(value = "/{tenantId}/get-period-of-loan")
	public ResponseEntity<Object> calculatePeriodOfLoan(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@Valid @RequestBody BasicTcCalculatorResource basicTcCalculatorResource) {

		BasicTcCalculatorResource response = basicTcCalculatorService.calculatePeriodOfLoan(basicTcCalculatorResource);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

}
