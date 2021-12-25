package com.fusionx.lending.origination.controller;

/**
 * 	Salary Income Details Controller
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   02-09-2021   FXL-115  	 FXL-658       Piyumi       Created
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
import com.fusionx.lending.origination.domain.SalaryIncomeDetails;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.resource.SalaryIncomeDetailsAddResource;
import com.fusionx.lending.origination.resource.SalaryIncomeDetailsUpdateResource;
import com.fusionx.lending.origination.resource.SuccessAndErrorDetailsResource;
import com.fusionx.lending.origination.service.SalaryIncomeDetailsService;


@RestController
@RequestMapping(value = "/salary-income-details")
@CrossOrigin(origins = "*")
public class SalaryIncomeDetailsController extends MessagePropertyBase{
	
	
	private SalaryIncomeDetailsService salaryIncomeDetailsService;
	
	@Autowired
	public void setSalaryIncomeDetailsService(SalaryIncomeDetailsService salaryIncomeDetailsService) {
		this.salaryIncomeDetailsService = salaryIncomeDetailsService;
	}
	
	@GetMapping("/{tenantId}/all")
	public ResponseEntity<Object> getAllSalaryIncomeDetails(@PathVariable(value = "tenantId") String tenantId){
		List<SalaryIncomeDetails> salaryIncomeDetails = salaryIncomeDetailsService.findAll(tenantId);
		if(!salaryIncomeDetails.isEmpty()) {
			return new ResponseEntity<>(salaryIncomeDetails, HttpStatus.OK);
		}
		else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Get the SalaryIncomeDetails by id.
	 *
	 * @param tenantId - the tenant id
	 * @param id - the id
	 * @return the SalaryIncomeDetails by id
	 */
	@GetMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> getSalaryIncomeDetailsById(@PathVariable(value = "tenantId" ) String tenantId,
													         	  @PathVariable(value = "id") Long id){
			
		Optional<SalaryIncomeDetails> isPresentSalaryIncomeDetails = salaryIncomeDetailsService.findById(tenantId,id);
		if(isPresentSalaryIncomeDetails.isPresent()) {
			return new ResponseEntity<>(isPresentSalaryIncomeDetails.get(), HttpStatus.OK);
		}
		else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Get the SalaryIncomeDetailss by status.
	 *
	 * @param tenantId - the tenant id
	 * @param status - the status
	 * @return the SalaryIncomeDetails by status
	 */
	
	@GetMapping(value = "/{tenantId}/status/{status}")
	public ResponseEntity<Object> getSalaryIncomeDetailsByStatus(@PathVariable(value = "tenantId") String tenantId,
			@PathVariable(value = "status") String status) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		if(status.equals(CommonStatus.ACTIVE.toString()) || status.equals(CommonStatus.INACTIVE.toString())) {
	
			List<SalaryIncomeDetails> salaryIncomeDetails = salaryIncomeDetailsService.findByStatus(tenantId,status);
			if(!salaryIncomeDetails.isEmpty()) {
				return new ResponseEntity<>(salaryIncomeDetails, HttpStatus.OK);
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
	 * Add the SalaryIncomeDetails.
	 *
	 * @param tenantId - the tenant id
	 * @param SalaryIncomeDetailsAddResource - the income source details add resource
	 * @return the response entity
	 */
	
	@PostMapping("/{tenantId}")
	public ResponseEntity<Object> addSalaryIncomeDetails(@PathVariable(value = "tenantId") String tenantId,
			   									       		  @Valid @RequestBody SalaryIncomeDetailsAddResource salaryIncomeDetailsAddResource){
		Long incomeDetailsId = salaryIncomeDetailsService.save(tenantId, salaryIncomeDetailsAddResource);
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource(getEnvironment().getProperty(RECORD_CREATED), Long.toString(incomeDetailsId));
		return new ResponseEntity<>(responseMessage,HttpStatus.CREATED);
	}
	
	/**
	 * Update SalaryIncomeDetails.
	 *
	 * @param tenantId - the tenant id
	 * @param id - the id
	 * @param SalaryIncomeDetailsUpdateResource - the income source details update resource
	 * @return the response entity
	 */
	
	@PutMapping(value = "{tenantId}/{id}")
	public ResponseEntity<Object> updateSalaryIncomeDetails(@PathVariable(value = "tenantId") String tenantId, 
												                 @PathVariable(value = "id") Long id, 
												                 @Valid @RequestBody SalaryIncomeDetailsUpdateResource salaryIncomeDetailsUpdateResource){
		SuccessAndErrorDetailsResource successAndErrorDetailsResource=new SuccessAndErrorDetailsResource();
		Optional<SalaryIncomeDetails> isPresentSalaryIncomeDetails = salaryIncomeDetailsService.findById(tenantId, id);		
		if(isPresentSalaryIncomeDetails.isPresent()) {
			SalaryIncomeDetails salaryIncomeDetails = salaryIncomeDetailsService.update(tenantId, id, salaryIncomeDetailsUpdateResource);
			successAndErrorDetailsResource = new SuccessAndErrorDetailsResource(getEnvironment().getProperty(RECORD_UPDATED), salaryIncomeDetails.getId().toString());
			return new ResponseEntity<>(successAndErrorDetailsResource,HttpStatus.OK);
		}
		else {
			successAndErrorDetailsResource.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
	
	/**
	 * Get the SalaryIncomeDetails by income source details id.
	 *
	 * @param tenantId - the tenant id
	 * @param incomeSourceDetailsId - the incomeSourceDetailsId
	 * @return the SalaryIncomeDetails by incomeSourceDetailsId
	 */
	
	@GetMapping(value = "/{tenantId}/income-source-details/{incomeSourceDetailsId}")
	public ResponseEntity<Object> getSalaryIncomeDetailsByIncomeSourceDetailsId(@PathVariable(value = "tenantId") String tenantId,
			@PathVariable(value = "incomeSourceDetailsId") Long incomeSourceDetailsId) {

		List<SalaryIncomeDetails> salaryIncomeDetails = salaryIncomeDetailsService.findByIncomeSourceDetailsId(tenantId,incomeSourceDetailsId);
		if(!salaryIncomeDetails.isEmpty()) {
			return new ResponseEntity<>(salaryIncomeDetails, HttpStatus.OK);
		}
		else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

}
