package com.fusionx.lending.product.controller;

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

import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.RepaymentFrequency;
import com.fusionx.lending.product.exception.UserNotFound;
import com.fusionx.lending.product.resources.CommonAddResource;
import com.fusionx.lending.product.resources.CommonUpdateResource;
import com.fusionx.lending.product.resources.RepaymentFrequencyAddResource;
import com.fusionx.lending.product.resources.RepaymentFrequencyUpdateResource;
import com.fusionx.lending.product.resources.SuccessAndErrorDetails;
import com.fusionx.lending.product.resources.SuccessAndErrorDetailsResource;
import com.fusionx.lending.product.service.RepaymentFrequencyService;

/**
 * Repayment Frequency Controller
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   07-07-2021             				Dilki        Created
 *    2   13-10-2021    FXL-174       FXL-1170  Pasindu      Modified
 ********************************************************************************************************
 */

@RestController
@RequestMapping(value = "/repayment-frequency")
@CrossOrigin(origins = "*")
public class RepaymentFrequencyController {

	@Autowired
	Environment environment;

	@Autowired
	public RepaymentFrequencyService repaymentFrequencyService;

	private static final String RECORD_NOT_FOUND = "Records not available";
	private String userNotFound = "common.user-not-found";

	/**
	 * get all Repayment Frequency
	 * 
	 * @param @PathVariable{tenantId}
	 * @param @PathVariable{all}
	 * @return List<Type>
	 */
	@GetMapping(value = "/{tenantId}/all")
	public ResponseEntity<Object> getAllRepaymentFrequency(
			@PathVariable(value = "tenantId", required = true) String tenantId) {
		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
		List<RepaymentFrequency> repaymentFrequency = repaymentFrequencyService.getAll();
		int size = repaymentFrequency.size();
		if (size > 0) {
			return new ResponseEntity<>((Collection<RepaymentFrequency>) repaymentFrequency, HttpStatus.OK);
		} else {
			responseMessage.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * get Repayment Frequency by id
	 * 
	 * @param @PathVariable{tenantId}
	 * @param @PathVariable{id}
	 * @return Option
	 */
	@GetMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> getRepaymentFrequencyById(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id) {
		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
		Optional<RepaymentFrequency> isPresentRepaymentFrequency = repaymentFrequencyService.getById(id);
		if (isPresentRepaymentFrequency.isPresent()) {
			return new ResponseEntity<>(isPresentRepaymentFrequency.get(), HttpStatus.OK);
		} else {
			responseMessage.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * get Repayment Frequency by code
	 * 
	 * @param @pathVariable{tenantId}
	 * @param @pathVariable           {code}
	 * @return Optional
	 **/
	@GetMapping(value = "/{tenantId}/code/{code}")
	public ResponseEntity<Object> getRepaymentFrequencyByCode(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "code", required = true) String code) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<RepaymentFrequency> isPresentRepaymentFrequency = repaymentFrequencyService.getByCode(code);
		if (isPresentRepaymentFrequency.isPresent()) {
			return new ResponseEntity<>(isPresentRepaymentFrequency.get(), HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * get Repayment Frequency by name
	 * 
	 * @param @pathVariable{tenantId}
	 * @param @pathVariable           {name}
	 * @return List
	 **/
	@GetMapping(value = "/{tenantId}/name/{name}")
	public ResponseEntity<Object> getRepaymentFrequencyByName(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "name", required = true) String name) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<RepaymentFrequency> isPresentRepaymentFrequency = repaymentFrequencyService.getByName(name);
		if (!isPresentRepaymentFrequency.isEmpty()) {
			return new ResponseEntity<>(isPresentRepaymentFrequency, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * get Repayment Frequency by status
	 * 
	 * @param @PathVariable{tenantId}
	 * @param @PathVariable{status}
	 * @return Option
	 */
	@GetMapping(value = "/{tenantId}/status/{status}")
	public ResponseEntity<Object> getRepaymentFrequencyByStatus(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "status", required = true) String status) {
		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();

		if (status.equals("ACTIVE") || status.equals("INACTIVE")) {
			List<RepaymentFrequency> isPresentRepaymentFrequency = repaymentFrequencyService.getByStatus(status);
			int size = isPresentRepaymentFrequency.size();
			if (size > 0) {
				return new ResponseEntity<>(isPresentRepaymentFrequency, HttpStatus.OK);
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
	 * save Repayment Frequency
	 * 
	 * @param @PathVariable{tenantId}
	 * @param @RequestBody{CommonAddResource}
	 * @return SuccessAndErrorDetailsDto
	 */
	@PostMapping(value = "/{tenantId}")
	public ResponseEntity<Object> addRepaymentFrequency(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@Valid @RequestBody RepaymentFrequencyAddResource repaymentFrequencyAddResource) {
		if (LogginAuthentcation.getInstance().getUserName() == null
				|| LogginAuthentcation.getInstance().getUserName().isEmpty()) {
			throw new UserNotFound(environment.getProperty(userNotFound));
		}

		SuccessAndErrorDetails successAndErrorDetails = new SuccessAndErrorDetails();
		RepaymentFrequency repaymentFrequency = repaymentFrequencyService.addRepaymentFrequency(tenantId,
				repaymentFrequencyAddResource);
		successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty("rec.saved"),
				Long.toString(repaymentFrequency.getId()));
		return new ResponseEntity<>(successAndErrorDetails, HttpStatus.CREATED);
	}

	/**
	 * update Repayment Frequency
	 * 
	 * @param @PathVariable{tenantId}
	 * @param @RequestBody{CommonUpdateResource}
	 * @return SuccessAndErrorDetailsDto
	 */
	@PutMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> updateRepaymentFrequency(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id,
			@Valid @RequestBody RepaymentFrequencyUpdateResource repaymentFrequencyUpdateResource) {
		if (LogginAuthentcation.getInstance().getUserName() == null
				|| LogginAuthentcation.getInstance().getUserName().isEmpty()) {
			throw new UserNotFound(environment.getProperty(userNotFound));
		}

		SuccessAndErrorDetails successAndErrorDetails = new SuccessAndErrorDetails();
		Optional<RepaymentFrequency> isPresentRepaymentFrequency = repaymentFrequencyService.getById(id);
		if (isPresentRepaymentFrequency.isPresent()) {
			RepaymentFrequency repaymentFrequency = repaymentFrequencyService.updateRepaymentFrequency(tenantId, id,
					repaymentFrequencyUpdateResource);
			successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty("rec.updated"),
					repaymentFrequency.getId().toString());
			return new ResponseEntity<>(successAndErrorDetails, HttpStatus.OK);
		} else {
			successAndErrorDetails.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(successAndErrorDetails, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
}
