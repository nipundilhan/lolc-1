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
import com.fusionx.lending.origination.core.LogginAuthentcation;
import com.fusionx.lending.origination.domain.BankStatementDetails;
import com.fusionx.lending.origination.exception.UserNotFound;
import com.fusionx.lending.origination.resource.BankStatementDetailsAddResource;
import com.fusionx.lending.origination.resource.BankStatementDetailsUpdateResource;
import com.fusionx.lending.origination.resource.SuccessAndErrorDetailsResource;
import com.fusionx.lending.origination.service.BankStatementDetailsService;

@RestController
@RequestMapping(value = "/bank-statement-details")
@CrossOrigin(origins = "*")
public class BankStatementDetailsController extends MessagePropertyBase {

	@Autowired
	public BankStatementDetailsService bankStatementDetailsService;

	/**
	 * get all BankStatementDetails
	 * 
	 * @param @PathVariable{tenantId}
	 * @param @PathVariable{all}
	 * @return List<Type>
	 */
	@GetMapping(value = "/{tenantId}/all")
	public ResponseEntity<Object> getAllBankStatementDetails(
			@PathVariable(value = "tenantId", required = true) String tenantId) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<BankStatementDetails> bankStatementDetails = bankStatementDetailsService.findAll();
		int size = bankStatementDetails.size();
		if (size > 0) {
			return new ResponseEntity<>(bankStatementDetails, HttpStatus.OK);
		} else {
			responseMessage.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * get BankStatementDetails by id
	 * 
	 * @param @PathVariable{tenantId}
	 * @param @PathVariable{id}
	 * @return Option
	 */
	@GetMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> getBankStatementDetailsById(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<BankStatementDetails> isPresentBankStatementDetails = bankStatementDetailsService.findById(id,
				tenantId);
		if (isPresentBankStatementDetails.isPresent()) {
			return new ResponseEntity<>(isPresentBankStatementDetails.get(), HttpStatus.OK);
		} else {
			responseMessage.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * get BankStatementDetails by status
	 * 
	 * @param @PathVariable{tenantId}
	 * @param @PathVariable{status}
	 * @return Option
	 */
	@GetMapping(value = "/{tenantId}/status/{status}")
	public ResponseEntity<Object> getBankStatementDetailsByStatus(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "status", required = true) String status) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();

		if (status.equals("ACTIVE") || status.equals("INACTIVE")) {
			List<BankStatementDetails> isPresentBankStatementDetails = bankStatementDetailsService.findByStatus(status,
					tenantId);
			int size = isPresentBankStatementDetails.size();
			if (size > 0) {
				return new ResponseEntity<>(isPresentBankStatementDetails, HttpStatus.OK);
			} else {
				responseMessage.setMessages(RECORD_NOT_FOUND);
				return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
			}
		} else {
			responseMessage.setMessages(environment.getProperty(COMMON_NOT_MATCH));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * save BankStatementDetails
	 * 
	 * @param @PathVariable{tenantId}
	 * @param @RequestBody{CommonAddResource}
	 * @return SuccessAndErrorDetailsDto
	 */
	@PostMapping(value = "/{tenantId}")
	public ResponseEntity<Object> addBankStatementDetails(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@Valid @RequestBody BankStatementDetailsAddResource bankStatementDetailsAddResource) {
		if (LogginAuthentcation.getInstance().getUserName() == null
				|| LogginAuthentcation.getInstance().getUserName().isEmpty()) {
			throw new UserNotFound(environment.getProperty(NOT_FOUND));
		}

		BankStatementDetails bankStatementDetails = bankStatementDetailsService.addBankStatementDetails(tenantId,
				LogginAuthentcation.getInstance().getUserName(), bankStatementDetailsAddResource);

		SuccessAndErrorDetailsResource successAndErrorDetails = new SuccessAndErrorDetailsResource(
				environment.getProperty(RECORD_CREATED), Long.toString(bankStatementDetails.getId()));
		return new ResponseEntity<>(successAndErrorDetails, HttpStatus.CREATED);
	}

	/**
	 * update BankStatementDetails
	 * 
	 * @param @PathVariable{tenantId}
	 * @param @RequestBody{CommonUpdateResource}
	 * @return SuccessAndErrorDetailsDto
	 */
	@PutMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> updateBankStatementDetails(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id,
			@Valid @RequestBody BankStatementDetailsUpdateResource bankStatementDetailsUpdateResource) {
		if (LogginAuthentcation.getInstance().getUserName() == null
				|| LogginAuthentcation.getInstance().getUserName().isEmpty()) {
			throw new UserNotFound(environment.getProperty(NOT_FOUND));
		}

		SuccessAndErrorDetailsResource successAndErrorDetails = new SuccessAndErrorDetailsResource();
		Optional<BankStatementDetails> isPresentBankStatementDetails = bankStatementDetailsService.findById(id,
				tenantId);
		if (isPresentBankStatementDetails.isPresent()) {
			BankStatementDetails bankStatementDetails = bankStatementDetailsService.updateBankStatementDetails(tenantId,
					id, LogginAuthentcation.getInstance().getUserName(), bankStatementDetailsUpdateResource);
			successAndErrorDetails = new SuccessAndErrorDetailsResource(environment.getProperty(RECORD_UPDATED),
					bankStatementDetails.getId().toString());
			return new ResponseEntity<>(successAndErrorDetails, HttpStatus.OK);
		} else {
			successAndErrorDetails.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(successAndErrorDetails, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

	/**
	 * Gets the BankStatementDetails by customerId.
	 *
	 * @param tenantId - the tenant id
	 * @param status   - the status
	 * @return the BankStatementDetails by customerId
	 */
	@GetMapping(value = "/{tenantId}/customerId/{customerId}")
	public ResponseEntity<Object> getBankStatementDetailsByCustId(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "customerId", required = true) Long customerId) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<BankStatementDetails> expenseType = bankStatementDetailsService.getByCustometId(customerId);
		if (!expenseType.isEmpty()) {
			return new ResponseEntity<>(expenseType, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
}
