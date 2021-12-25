package com.fusionx.lending.transaction.controller;

/**
 * Tax Event
 * <p>
 * *******************************************************************************************************
 * ###   Date         Story Point   Task No    Author       Description
 * -------------------------------------------------------------------------------------------------------
 * 1   12-10-2021   FXL-1234      FXL-1131   Dilki      Created
 * <p>
 * *******************************************************************************************************
 */
import com.fusionx.lending.transaction.base.MessagePropertyBase;
import com.fusionx.lending.transaction.core.LogginAuthentcation;
import com.fusionx.lending.transaction.domain.TaxEvent;
import com.fusionx.lending.transaction.exception.UserNotFound;
import com.fusionx.lending.transaction.resource.AddTaxEventRequestResource;
import com.fusionx.lending.transaction.resource.SuccessAndErrorDetails;
import com.fusionx.lending.transaction.resource.SuccessAndErrorDetailsResource;
import com.fusionx.lending.transaction.resource.TaxEventAddResource;
import com.fusionx.lending.transaction.resource.TaxEventUpdateResource;
import com.fusionx.lending.transaction.resource.UpdateTaxEventRequestResource;
import com.fusionx.lending.transaction.service.TaxCalculationService;
import com.fusionx.lending.transaction.service.TaxEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/tax-event")
@CrossOrigin(origins = "*")
public class TaxEventController extends MessagePropertyBase {

	private TaxEventService taxEventService;

	@Autowired
	public void setTaxEventService(TaxEventService taxEventService) {
		this.taxEventService = taxEventService;
	}

	private TaxCalculationService taxCalculationService;

	@Autowired
	public void setTaxCalculationService(TaxCalculationService taxCalculationService) {
		this.taxCalculationService = taxCalculationService;
	}

	/**
	 * Gets list of all items
	 *
	 * @param tenantId the tenant id
	 * @return the list of all items.
	 */
	@GetMapping(value = "/{tenantId}/all")
	public ResponseEntity<Object> getAllTaxEvent(@PathVariable(value = "tenantId") String tenantId) {

		List<TaxEvent> taxEvent = taxEventService.findAll();
		int size = taxEvent.size();

		if (size > 0) {
			return new ResponseEntity<>(taxEvent, HttpStatus.OK);
		} else {
			SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
			responseMessage.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * Get Tax Event by id
	 *
	 * @param tenantId the tenant id
	 * @param id       the id of the record
	 * @return the Tax Event record.
	 */
	@GetMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> getTaxEventById(@PathVariable(value = "tenantId") String tenantId,
			@PathVariable(value = "id") Long id) {

		Optional<TaxEvent> isPresentTaxEvent = taxEventService.findById(id);

		if (isPresentTaxEvent.isPresent()) {
			return new ResponseEntity<>(isPresentTaxEvent.get(), HttpStatus.OK);
		} else {
			SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
			responseMessage.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * Gets Tax Event by code
	 *
	 * @param tenantId the tenant id
	 * @param code     the code
	 * @return the Tax Event record.
	 */
	@GetMapping(value = "/{tenantId}/code/{code}")
	public ResponseEntity<Object> getTaxEventByTaxEvent(@PathVariable(value = "tenantId") String tenantId,
			@PathVariable(value = "code") String code) {

		Optional<TaxEvent> isPresentTaxEvent = taxEventService.findByCode(code);

		if (isPresentTaxEvent.isPresent()) {
			return new ResponseEntity<>(isPresentTaxEvent.get(), HttpStatus.OK);
		} else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * Gets Tax Events by status
	 *
	 * @param tenantId the tenant id
	 * @param status   the status should be either <code>ACTIVE</code> or
	 *                 <code>INACTIVE</code>
	 * @return the list of Tax Events.
	 */
	@GetMapping(value = "/{tenantId}/status/{status}")
	public ResponseEntity<Object> getTaxEventByStatus(@PathVariable(value = "tenantId") String tenantId,
			@PathVariable(value = "status") String status) {

		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();

		if (status.equals("ACTIVE") || status.equals("INACTIVE")) {
			List<TaxEvent> isPresentTaxEvent = taxEventService.findByStatus(status);
			int size = isPresentTaxEvent.size();

			if (size > 0) {
				return new ResponseEntity<>(isPresentTaxEvent, HttpStatus.OK);
			} else {
				responseMessage.setMessages(RECORD_NOT_FOUND);
				return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
			}
		} else {
			responseMessage.setMessages(environment.getProperty(INVALID_STATUS));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * Saves the given Tax Event record
	 *
	 * @param tenantId            the tenant id
	 * @param taxEventAddResource the resource file
	 * @return the saved record id.
	 */
	@PostMapping(value = "/{tenantId}")
	public ResponseEntity<Object> addTaxEvent(@PathVariable(value = "tenantId") String tenantId,
			@Valid @RequestBody AddTaxEventRequestResource taxEventAddResource) {

		if (LogginAuthentcation.getInstance().getUserName() == null
				|| LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new UserNotFound(environment.getProperty(USER_NOT_FOUND));
		taxEventAddResource.setTaxEventCreatedUser(LogginAuthentcation.getInstance().getUserName());

		TaxEvent taxEvent = taxEventService.addTaxEvent(tenantId, taxEventAddResource);
		SuccessAndErrorDetailsResource successAndErrorDetailsResource = new SuccessAndErrorDetailsResource();
		successAndErrorDetailsResource.setMessages(environment.getProperty(RECORD_CREATED));
		successAndErrorDetailsResource.setValue(taxEvent.getId().toString());
		return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.CREATED);
	}

	/**
	 * Updates the given Tax Event record
	 *
	 * @param tenantId               the tenant id
	 * @param id                     the id of record
	 * @param taxEventUpdateResource the resource file
	 * @return the updated record id with message
	 */
	@PutMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> updateTaxEvent(@PathVariable(value = "tenantId") String tenantId,
			@PathVariable(value = "id") Long id,
			@Valid @RequestBody UpdateTaxEventRequestResource taxEventUpdateResource) {

		if (LogginAuthentcation.getInstance().getUserName() == null
				|| LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new UserNotFound(environment.getProperty(USER_NOT_FOUND));
		taxEventUpdateResource.setModifiedUser(LogginAuthentcation.getInstance().getUserName());

		SuccessAndErrorDetailsResource successAndErrorDetailsResource = new SuccessAndErrorDetailsResource();

		TaxEvent taxEvent = taxEventService.updateTaxEvent(tenantId, taxEventUpdateResource, id);
		successAndErrorDetailsResource.setMessages(environment.getProperty(RECORD_UPDATED));
		successAndErrorDetailsResource.setValue(taxEvent.getId().toString());
		return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.OK);

	}

	/**
	 * Gets Tax Event by code
	 *
	 * @param tenantId the tenant id
	 * @param code     the code
	 * @return the Tax Event record.
	 */
	@GetMapping(value = "/{tenantId}/tx-evnt/code/{code}")
	public ResponseEntity<Object> getTaxEventByCode(@PathVariable(value = "tenantId") String tenantId,
			@PathVariable(value = "code") String code) {

		Optional<TaxEvent> isPresentTaxEvent = taxCalculationService.calculateTax(code);

		if (isPresentTaxEvent.isPresent()) {
			return new ResponseEntity<>(isPresentTaxEvent.get(), HttpStatus.OK);
		} else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

}