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
/**
 * Financial Statement Level Controller
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   13-08-2021      FXL-356	 FXL-591	Ishan        Created
 *    
 ********************************************************************************************************
 */
import com.fusionx.lending.origination.base.MessagePropertyBase;
import com.fusionx.lending.origination.core.LogginAuthentcation;
import com.fusionx.lending.origination.domain.FinancialStatementLevel;
import com.fusionx.lending.origination.exception.UserNotFound;
import com.fusionx.lending.origination.resource.FinancialStatementLevelAddResourcess;
import com.fusionx.lending.origination.resource.FinancialStatementLevelUpdateResourcess;
import com.fusionx.lending.origination.resource.SuccessAndErrorDetailsResource;
import com.fusionx.lending.origination.service.FinancialStatementLevelService;

@RestController
@RequestMapping(value = "/financial-statement-level")
@CrossOrigin(origins = "*")
public class FinancialStatementLevelController extends MessagePropertyBase {

	private FinancialStatementLevelService financialStatementLevelService;

	@Autowired
	public void setFinancialStatementLevelService(FinancialStatementLevelService financialStatementLevelService) {
		this.financialStatementLevelService = financialStatementLevelService;
	}

	/**
	 * Returns the all records
	 *
	 * @return the all records
	 */
	@GetMapping(value = "/{tenantId}/all")
	public ResponseEntity<Object> getAllFinancialStatementLevel(@PathVariable(value = "tenantId") String tenantId) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<FinancialStatementLevel> financialStatementLevel = financialStatementLevelService.getAll();
		if (!financialStatementLevel.isEmpty()) {
			return new ResponseEntity<>(financialStatementLevel, HttpStatus.OK);
		} else {
			responseMessage.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * Returns the record by id
	 *
	 * @param tenantId the tenant id
	 * @param id       the id
	 * @return the FinancialStatementLevel Record.
	 */
	@GetMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> getFinancialStatementLevelById(@PathVariable(value = "tenantId") String tenantId,
			@PathVariable(value = "id") Long id) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<FinancialStatementLevel> financialStatementLevel = financialStatementLevelService.getById(id);
		if (financialStatementLevel.isPresent()) {
			return new ResponseEntity<>(financialStatementLevel.get(), HttpStatus.OK);
		} else {
			responseMessage.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * Returns the record by code
	 *
	 * @param tenantId the tenant id
	 * @param code     the code
	 * @return the FinancialStatementLevel Record.
	 */
	@GetMapping(value = "/{tenantId}/code/{code}")
	public ResponseEntity<Object> getFinancialStatementLevelByCode(@PathVariable(value = "tenantId") String tenantId,
			@PathVariable(value = "code") String code) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<FinancialStatementLevel> financialStatementLevel = financialStatementLevelService.getByCode(code);
		if (financialStatementLevel.isPresent()) {
			return new ResponseEntity<>(financialStatementLevel.get(), HttpStatus.OK);
		} else {
			responseMessage.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * Returns the all records by name
	 *
	 * @param tenantId the tenant id
	 * @param name     the name
	 * @return the FinancialStatementLevels Records By Name.
	 */
	@GetMapping(value = "/{tenantId}/name/{name}")
	public ResponseEntity<Object> getAllFinancialStatementLevelByName(@PathVariable(value = "tenantId") String tenantId,
			@PathVariable(value = "name") String name) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<FinancialStatementLevel> financialStatementLevel = financialStatementLevelService.getByName(name);
		if (!financialStatementLevel.isEmpty()) {
			return new ResponseEntity<>(financialStatementLevel, HttpStatus.OK);
		} else {
			responseMessage.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * Returns the all records by status
	 *
	 * @param tenantId the tenant id
	 * @param status   the status
	 * @return the FinancialStatementLevels Records By Status.
	 */
	@GetMapping(value = "/{tenantId}/status/{status}")
	public ResponseEntity<Object> getAllFinancialStatementLevelByStatus(
			@PathVariable(value = "tenantId") String tenantId, @PathVariable(value = "status") String status) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();

		if (status.equals("ACTIVE") || status.equals("INACTIVE")) {
			List<FinancialStatementLevel> financialStatementLevel = financialStatementLevelService.getByStatus(status);
			if (!financialStatementLevel.isEmpty()) {
				return new ResponseEntity<>(financialStatementLevel, HttpStatus.OK);
			} else {
				responseMessage.setMessages(RECORD_NOT_FOUND);
				return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
			}
		} else {
			responseMessage.setMessages(environment.getProperty(COMMON_INVALID_VALUE));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * add the FinancialStatementLevel details
	 * 
	 * @param tenantId the tenant id
	 * @param request  the the object to include data
	 * @return the saved FinancialStatementLevel
	 */
	@PostMapping(value = "/{tenantId}")
	public ResponseEntity<Object> addFinancialStatementLevel(@PathVariable String tenantId,
			@Valid @RequestBody FinancialStatementLevelAddResourcess request) {

		if (LogginAuthentcation.getInstance().getUserName() == null
				|| LogginAuthentcation.getInstance().getUserName().isEmpty()) {
			throw new UserNotFound(environment.getProperty(USER_NOT_FOUND));
		}
		FinancialStatementLevel financialStatementLevel = financialStatementLevelService
				.addFinancialStatementLevel(tenantId, request);
		SuccessAndErrorDetailsResource successAndErrorDetails = new SuccessAndErrorDetailsResource(
				environment.getProperty(RECORD_CREATED), Long.toString(financialStatementLevel.getId()));
		return new ResponseEntity<>(successAndErrorDetails, HttpStatus.CREATED);
	}

	/**
	 * update the given FinancialStatementLevel details
	 * 
	 * @param tenantId the tenant id
	 * @param id       the FinancialStatementLevel id
	 * @param request  the the object to include data
	 * @return the after updating FinancialStatementLevel
	 */
	@PutMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> updateFinancialStatementLevel(@PathVariable String tenantId,
			@PathVariable(value = "id") Long id, @Valid @RequestBody FinancialStatementLevelUpdateResourcess request) {

		if (LogginAuthentcation.getInstance().getUserName() == null
				|| LogginAuthentcation.getInstance().getUserName().isEmpty()) {
			throw new UserNotFound(environment.getProperty(USER_NOT_FOUND));
		}

		SuccessAndErrorDetailsResource successAndErrorDetails = new SuccessAndErrorDetailsResource();
		Optional<FinancialStatementLevel> optFinancialStatementLevel = financialStatementLevelService.getById(id);
		if (optFinancialStatementLevel.isPresent()) {
			FinancialStatementLevel financialStatementLevel = financialStatementLevelService
					.updateFinancialStatementLevel(tenantId, id, request);
			successAndErrorDetails = new SuccessAndErrorDetailsResource(environment.getProperty(RECORD_UPDATED),
					financialStatementLevel.getId().toString());
			return new ResponseEntity<>(successAndErrorDetails, HttpStatus.OK);
		} else {
			successAndErrorDetails.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(successAndErrorDetails, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

}
