package com.fusionx.lending.origination.controller;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
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
import com.fusionx.lending.origination.domain.DisbursementDetails;
import com.fusionx.lending.origination.exception.UserNotFound;
import com.fusionx.lending.origination.resource.DisbursementAddRequestResource;
import com.fusionx.lending.origination.resource.SuccessAndErrorDetailsResource;
import com.fusionx.lending.origination.service.DisbursementDetailsService;

/**
 * Disbursement Detail Service.
 * 
 ********************************************************************************************************
 *  ###   Date         	Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   04-MAY-2021   		      FX-6484    Sanatha      Initial Development.
 *    2   28-OCT-2021   		      FX-6484    Dilhan      
 *    
 ********************************************************************************************************
 */
@RestController
@RequestMapping(value = "/disbursement-details")
@CrossOrigin(origins = "*")
public class DisbursementDetailsController extends MessagePropertyBase{
	
	@Autowired
	private DisbursementDetailsService disbursementDetailsService;
	
	@Autowired
	private Environment environment;
	
	/**
	 * Gets the DisbursementDetails  by status.
	 *
	 * @param tenantId - the tenant id
	 * @param status - the status
	 * @return the DisbursementDetails by status
	 */
	@GetMapping(value = "/{tenantId}/status/{status}")
	public ResponseEntity<Object> getOtherDetailsByStatus(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "status", required = true) String status) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<DisbursementDetails> disbursementDetails = disbursementDetailsService.findByStatus(tenantId, status);
		if (!disbursementDetails.isEmpty()) {
			return new ResponseEntity<>((Collection<DisbursementDetails>) disbursementDetails, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Gets the DisbursementDetails   by Customer  id.
	 *
	 * @param tenantId - the tenant id
	 * @param id - the Customer id
	 * @return the DisbursementDetails  e by Customer  id
	 */
	@GetMapping(value = "/{tenantId}/customer-id/{id}")
	public ResponseEntity<Object> getOtherDetailsByCustomerId(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<DisbursementDetails> disbursementDetails = disbursementDetailsService.findByCustomerId(tenantId, id);
		if (!disbursementDetails.isEmpty()) {
			return new ResponseEntity<>((Collection<DisbursementDetails>) disbursementDetails, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Gets the DisbursementDetails   by Lead Info id.
	 *
	 * @param tenantId - the tenant id
	 * @param id - the Lead info id
	 * @return the DisbursementDetails  e by Lead Info  id
	 */
	@GetMapping(value = "/{tenantId}/lead-info-id/{id}")
	public ResponseEntity<Object> getOtherDetailsByLeadInfoId(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<DisbursementDetails> disbursementDetails = disbursementDetailsService.findByLeadInfoId(tenantId, id);
		if (!disbursementDetails.isEmpty()) {
			return new ResponseEntity<>((Collection<DisbursementDetails>) disbursementDetails, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Save and Update Disbursement Details
	 *
	 * @param tenantId
	 * @param customerId
	 * @param DisbursementAddRequestResource
	 * @return SuccessAndErrorDetailsResource
	 */
	@PostMapping(value = "/{tenantId}")
	public ResponseEntity<Object> saveDisbursementDetails(@PathVariable(value = "tenantId", required = true) String tenantId, 
			@Valid @RequestBody DisbursementAddRequestResource disbursementAddRequestResource) {
		if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new UserNotFound(environment.getProperty(USER_NOT_FOUND));
		disbursementDetailsService.saveAndValidateDisbursementDetails(tenantId, LogginAuthentcation.getInstance().getUserName(), disbursementAddRequestResource);
		SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty(RECORD_CREATED));
		return new ResponseEntity<>(successDetailsDto,HttpStatus.CREATED);
	}
	
    /**
     * Get Disbursement Details by id
     *
     * @param tenantId the id of tenant
     * @param id       the id of the record
     * @return disbursement details if record exists, otherwise <code>204</code>
     */
    @GetMapping(value = "/{tenantId}/{id}")
    public ResponseEntity<Object> getAccountStatusById(@PathVariable(value = "tenantId") String tenantId,
                                                        @PathVariable(value = "id") Long id) {
        Optional<DisbursementDetails> isPresentAccountStatus = disbursementDetailsService.findById(id);
        if (isPresentAccountStatus.isPresent()) {
            return new ResponseEntity<>(isPresentAccountStatus.get(), HttpStatus.OK);
        } else {
        	SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
            responseMessage.setMessages(RECORD_NOT_FOUND);
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
	 * Update Disbursement Details
	 *
	 * @param tenantId
	 * @param customerId
	 * @param DisbursementAddRequestResource
	 * @return SuccessAndErrorDetailsResource
	 */
	@PutMapping(value = "/{tenantId}")
	public ResponseEntity<Object> updateDisbursementDetails(@PathVariable(value = "tenantId", required = true) String tenantId, 
			@Valid @RequestBody DisbursementAddRequestResource disbursementAddRequestResource) {
		if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new UserNotFound(environment.getProperty(USER_NOT_FOUND));
		disbursementDetailsService.saveAndValidateDisbursementDetails(tenantId, LogginAuthentcation.getInstance().getUserName(), disbursementAddRequestResource);
		SuccessAndErrorDetailsResource successAndErrorDetailsResource = new SuccessAndErrorDetailsResource(getEnvironment().getProperty(RECORD_UPDATED));
		return new ResponseEntity<>(successAndErrorDetailsResource,HttpStatus.OK);
	}

}
