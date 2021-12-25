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
import com.fusionx.lending.origination.domain.CreditAppraisalDisbursmentDetails;
import com.fusionx.lending.origination.exception.UserNotFound;
import com.fusionx.lending.origination.resource.CreditAppraisalDisbursmentDetailsAddResource;
import com.fusionx.lending.origination.resource.CreditAppraisalDisbursmentDetailsUpdateResource;
import com.fusionx.lending.origination.resource.SuccessAndErrorDetailsResource;
import com.fusionx.lending.origination.service.CreditAppraisalDisbursmentDetailsService;

/**
 * CreditAppraisalDisbursmentDetails Controller
 * 
 * @author Pasindu
 * @Dat 06-10-2021
 * 
 ********************************************************************************************************
 *      ### Date Story Point Task No Author Description
 *      -------------------------------------------------------------------------------------------------------
 *      1 06-10-2021 FXL-121 FXL-1005 Pasindu Created
 * 
 ********************************************************************************************************
 */

@RestController
@RequestMapping(value = "/credit-appraisal-disbursement-details")
@CrossOrigin(origins = "*")
public class CreditAppraisalDisbursmentDetailsController extends MessagePropertyBase{

	@Autowired
	Environment environment;

	@Autowired
	public CreditAppraisalDisbursmentDetailsService creditAppraisalDisbursmentDetailsService;
	
	/**
	 * get all CreditAppraisalDisbursmentDetails
	 * 
	 * @param @PathVariable{tenantId}
	 * @param @PathVariable{all}
	 * @return List<Type>
	 */
	@GetMapping(value = "/{tenantId}/all")
	public ResponseEntity<Object> getAllCreditAppraisalDisbursmentDetails(
			@PathVariable(value = "tenantId", required = true) String tenantId) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<CreditAppraisalDisbursmentDetails> creditAppraisalDisbursementDetails = creditAppraisalDisbursmentDetailsService
				.getAll();
		int size = creditAppraisalDisbursementDetails.size();
		if (size > 0) {
			return new ResponseEntity<>(
					(Collection<CreditAppraisalDisbursmentDetails>) creditAppraisalDisbursementDetails, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * get CreditAppraisalDisbursmentDetails by id
	 * 
	 * @param @PathVariable{tenantId}
	 * @param @PathVariable{id}
	 * @return Option
	 */
	@GetMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> getCoreTransactionById(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id) {

		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<CreditAppraisalDisbursmentDetails> isPresentcreditAppraisalDisbursementDetails = creditAppraisalDisbursmentDetailsService
				.findById(id);
		if (isPresentcreditAppraisalDisbursementDetails.isPresent()) {
			return new ResponseEntity<>(isPresentcreditAppraisalDisbursementDetails.get(), HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * get CreditAppraisalDisbursmentDetails by status
	 * 
	 * @param @PathVariable{tenantId}
	 * @param @PathVariable{status}
	 * @return List
	 */
	@GetMapping(value = "/{tenantId}/status/{status}")
	public ResponseEntity<Object> getCoreTransactionByStatus(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "status", required = true) String status) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();

		if (status.equals("ACTIVE") || status.equals("INACTIVE")) {
			List<CreditAppraisalDisbursmentDetails> creditAppraisalDisbursmentDetails = creditAppraisalDisbursmentDetailsService
					.findByStatus(status);
			int size = creditAppraisalDisbursmentDetails.size();
			if (size > 0) {
				return new ResponseEntity<>(creditAppraisalDisbursmentDetails, HttpStatus.OK);
			} else {
				responseMessage.setMessages(RECORD_NOT_FOUND);
				return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
			}
		} else {
			responseMessage.setMessages(environment.getProperty("invalid-status"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * save CreditAppraisalDisbursmentDetails
	 * 
	 * @param @PathVariable{tenantId}
	 * @param @RequestBody{CreditAppraisalDisbursmentDetailsAddResource}
	 * @return SuccessAndErrorDetailsDto
	 */
	@PostMapping(value = "/{tenantId}")
	public ResponseEntity<Object> addCreditAppraisalDisbursmentDetails(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@Valid @RequestBody CreditAppraisalDisbursmentDetailsAddResource creditAppraisalDisbursmentDetailsAddResource) {
		if (LogginAuthentcation.getInstance().getUserName() == null
				|| LogginAuthentcation.getInstance().getUserName().isEmpty()) {
			throw new UserNotFound(environment.getProperty("user-not-found"));
		}

		SuccessAndErrorDetailsResource successAndErrorDetails = new SuccessAndErrorDetailsResource();
		CreditAppraisalDisbursmentDetails creditAppraisalDisbursmentDetails = creditAppraisalDisbursmentDetailsService
				.addCreditAppraisalDisbursmentDetails(tenantId, creditAppraisalDisbursmentDetailsAddResource);
		successAndErrorDetails = new SuccessAndErrorDetailsResource(environment.getProperty("rec.saved"),
				Long.toString(creditAppraisalDisbursmentDetails.getId()));
		return new ResponseEntity<>(successAndErrorDetails, HttpStatus.CREATED);
	}

	/**
	 * update CreditAppraisalDisbursmentDetails
	 * 
	 * @param @PathVariable{tenantId}
	 * @param @RequestBody{CreditAppraisalDisbursmentDetailsUpdateResource}
	 * @return SuccessAndErrorDetailsDto
	 */
	@PutMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> updateCoreTransaction(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id,
			@Valid @RequestBody CreditAppraisalDisbursmentDetailsUpdateResource creditAppraisalDisbursmentDetailsUpdateResource) {
		if (LogginAuthentcation.getInstance().getUserName() == null
				|| LogginAuthentcation.getInstance().getUserName().isEmpty()) {
			throw new UserNotFound(environment.getProperty("user-not-found"));
		}

		SuccessAndErrorDetailsResource successAndErrorDetails = new SuccessAndErrorDetailsResource();
		Optional<CreditAppraisalDisbursmentDetails> isPresentCreditAppraisalDisbursmentDetails = creditAppraisalDisbursmentDetailsService
				.findById(id);
		if (isPresentCreditAppraisalDisbursmentDetails.isPresent()) {
			CreditAppraisalDisbursmentDetails creditAppraisalDisbursmentDetails = creditAppraisalDisbursmentDetailsService
					.updateCreditAppraisalDisbursmentDetails(tenantId, id,
							creditAppraisalDisbursmentDetailsUpdateResource);
			successAndErrorDetails = new SuccessAndErrorDetailsResource(environment.getProperty("rec.updated"),
					creditAppraisalDisbursmentDetails.getId().toString());
			return new ResponseEntity<>(successAndErrorDetails, HttpStatus.OK);
		} else {
			successAndErrorDetails.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(successAndErrorDetails, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

}
