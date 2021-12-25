package com.fusionx.lending.product.controller;


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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.domain.TcHeader;
import com.fusionx.lending.product.resources.StructuredLoanCalculationResponseResource;
import com.fusionx.lending.product.resources.SuccessAndErrorDetails;
import com.fusionx.lending.product.resources.TcStructuredDetailAddResource;
import com.fusionx.lending.product.service.StructuredLoanCalculationService;
/**
 * 	Structured Loan Calculation Controller
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   06-10-2021   			  	 FXL-993       Piyumi     Created
 *    
 ********************************************************************************************************
*/

@RestController
@RequestMapping(value = "/structured-loan-calculator")
@CrossOrigin(origins = "*")
public class StructuredLoanCalculationController extends MessagePropertyBase{


	@Autowired
	private StructuredLoanCalculationService structuredLoanCalculationService;
	
	/**
	 * Structured Loan Calculator
	 * 
	 * @param tenantId - the tenant id
	 * @param tcStructuredDetailAddResource - the tcStructuredDetailAddResource
	 * @return the response entity
	 * @throws Exception 
	 * @throws NumberFormatException 
	 */
	@PostMapping(value = "/{tenantId}/calculator")
	public ResponseEntity<Object> structuredLoanCalculator(
			@PathVariable(value = "tenantId") String tenantId,
			@Valid @RequestBody TcStructuredDetailAddResource tcStructuredDetailAddResource) throws NumberFormatException, Exception {
			 
		StructuredLoanCalculationResponseResource response = structuredLoanCalculationService.structuredLoanCalculatorCaller(tenantId,
				tcStructuredDetailAddResource);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	/**
	 * save Tc Structured Detail
	 * 
	 * @param tenantId - the tenant id
	 * @param tcStructuredDetailAddResource - the tcStructuredDetailAddResource
	 * @return the response entity
	 * @throws Exception 
	 * @throws NumberFormatException 
	 */
	@PostMapping(value = "/{tenantId}")
	public ResponseEntity<Object> saveTcStructuredDetail(
			@PathVariable(value = "tenantId") String tenantId,
			@Valid @RequestBody TcStructuredDetailAddResource tcStructuredDetailAddResource) throws NumberFormatException, Exception {
			 
		StructuredLoanCalculationResponseResource response = structuredLoanCalculationService.addTcStructuredDetail(tenantId,
				tcStructuredDetailAddResource);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	/**
     * Gets the structured details by id.
     *
     * @param tenantId the tenant id
     * @param id the id
     * @return the structured details by id
     */
    @GetMapping(value = "/{tenantId}/{id}")
    public ResponseEntity<Object> getById(
            @PathVariable(value = "tenantId") String tenantId,
            @PathVariable(value = "id") Long id) {
       
        Optional<TcHeader> tcHeader = structuredLoanCalculationService.findById(id);
        if (tcHeader.isPresent()) {
            return new ResponseEntity<>(tcHeader.get(), HttpStatus.OK);
        } else {
        	SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
            responseMessage.setMessages(RECORD_NOT_FOUND);
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Gets the structured details by lead id.
     *
     * @param tenantId the tenant id
     * @param leadId the leadId
     * @return the structured details by lead id
     */
    @GetMapping(value = "/{tenantId}/lead/{leadId}")
    public ResponseEntity<Object> getRevolvingDetailsByLeadId(
            @PathVariable(value = "tenantId") String tenantId,
            @PathVariable(value = "leadId") Long leadId) {
       
    	 List<TcHeader> tcHeaderList = structuredLoanCalculationService.findByLeadId(leadId);
        if (!tcHeaderList.isEmpty()) {
            return new ResponseEntity<>(tcHeaderList, HttpStatus.OK);
        } else {
        	SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
            responseMessage.setMessages(RECORD_NOT_FOUND);
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

}
