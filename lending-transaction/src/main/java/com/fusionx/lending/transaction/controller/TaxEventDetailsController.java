package com.fusionx.lending.transaction.controller;

import com.fusionx.lending.transaction.base.MessagePropertyBase;
import com.fusionx.lending.transaction.core.LogginAuthentcation;
import com.fusionx.lending.transaction.domain.TaxEventDetails;
import com.fusionx.lending.transaction.exception.UserNotFound;
import com.fusionx.lending.transaction.resource.AddTaxEventDetailsRequestResource;
import com.fusionx.lending.transaction.resource.SuccessAndErrorDetails;
import com.fusionx.lending.transaction.resource.SuccessAndErrorDetailsResource;
import com.fusionx.lending.transaction.resource.UpdateTaxEventDetailsRequestResource;
import com.fusionx.lending.transaction.service.TaxEventDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/tax-event-details")
@CrossOrigin(origins = "*")
public class TaxEventDetailsController extends MessagePropertyBase {

	private TaxEventDetailsService taxEventDetailsService;

	@Autowired
	public void setTaxEventDetailsService(TaxEventDetailsService taxEventDetailsService) {
		this.taxEventDetailsService = taxEventDetailsService;
	}

	/**
	 * Gets list of all items
	 *
	 * @param tenantId the tenant id
	 * @return the list of all items.
	 */
	@GetMapping(value = "/{tenantId}/all")
	public ResponseEntity<Object> getAllTaxEventDetails(@PathVariable(value = "tenantId") String tenantId) {

		List<TaxEventDetails> taxEventDetails = taxEventDetailsService.findAll();
		int size = taxEventDetails.size();

		if (size > 0) {
			return new ResponseEntity<>(taxEventDetails, HttpStatus.OK);
		} else {
			SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
			responseMessage.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * Get Tax Event Details by id
	 *
	 * @param tenantId the tenant id
	 * @param id       the id of the record
	 * @return the Tax Event Details record.
	 */
	@GetMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> getTaxEventDetailsById(@PathVariable(value = "tenantId") String tenantId,
			@PathVariable(value = "id") Long id) {

		Optional<TaxEventDetails> isPresentTaxEventDetails = taxEventDetailsService.findById(id);

		if (isPresentTaxEventDetails.isPresent()) {
			return new ResponseEntity<>(isPresentTaxEventDetails.get(), HttpStatus.OK);
		} else {
			SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
			responseMessage.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * Gets Tax Event Details by code
	 *
	 * @param tenantId the tenant id
	 * @param code     the code
	 * @return the Tax Event Details record.
	 */
	@GetMapping(value = "/{tenantId}/code/{code}")
	public ResponseEntity<Object> getTaxEventDetailsByTaxEventDetails(@PathVariable(value = "tenantId") String tenantId,
			@PathVariable(value = "code") String code) {

		List<TaxEventDetails> isPresentTaxEventDetails = taxEventDetailsService.findByCode(code);
		int size = isPresentTaxEventDetails.size();

		if (size > 0) {
			return new ResponseEntity<>(isPresentTaxEventDetails, HttpStatus.OK);
		} else {
			SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
			responseMessage.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * Gets Tax Event Detailss by status
	 *
	 * @param tenantId the tenant id
	 * @param status   the status should be either <code>ACTIVE</code> or
	 *                 <code>INACTIVE</code>
	 * @return the list of Tax Event Detailss.
	 */
	@GetMapping(value = "/{tenantId}/status/{status}")
	public ResponseEntity<Object> getTaxEventDetailsByStatus(@PathVariable(value = "tenantId") String tenantId,
			@PathVariable(value = "status") String status) {

		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();

		if (status.equals("ACTIVE") || status.equals("INACTIVE")) {
			List<TaxEventDetails> isPresentTaxEventDetails = taxEventDetailsService.findByStatus(status);
			int size = isPresentTaxEventDetails.size();

			if (size > 0) {
				return new ResponseEntity<>(isPresentTaxEventDetails, HttpStatus.OK);
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
	 * Saves the given Tax Event Details record
	 *
	 * @param tenantId                   the tenant id
	 * @param taxEventDetailsAddResource the resource file
	 * @return the saved record id.
	 */
	@PostMapping(value = "/{tenantId}")
	public ResponseEntity<Object> addTaxEventDetails(@PathVariable(value = "tenantId") String tenantId,
			@Valid @RequestBody AddTaxEventDetailsRequestResource taxEventDetailsAddResource) {

		if (LogginAuthentcation.getInstance().getUserName() == null
				|| LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new UserNotFound(environment.getProperty(USER_NOT_FOUND));
		taxEventDetailsAddResource.setTaxEventCreatedUser(LogginAuthentcation.getInstance().getUserName());

		TaxEventDetails taxEventDetails = taxEventDetailsService.addTaxEventDetails(tenantId,
				taxEventDetailsAddResource);
		SuccessAndErrorDetailsResource successAndErrorDetailsResource = new SuccessAndErrorDetailsResource();
		successAndErrorDetailsResource.setMessages(environment.getProperty(RECORD_CREATED));
		successAndErrorDetailsResource.setValue(taxEventDetails.getId().toString());
		return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.CREATED);
	}

	/**
	 * Updates the given Tax Event Details record
	 *
	 * @param tenantId                      the tenant id
	 * @param id                            the id of record
	 * @param taxEventDetailsUpdateResource the resource file
	 * @return the updated record id with message
	 */
	@PutMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> updateTaxEventDetails(@PathVariable(value = "tenantId") String tenantId,
			@PathVariable(value = "id") Long id,
			@Valid @RequestBody UpdateTaxEventDetailsRequestResource taxEventDetailsUpdateResource) {

		if (LogginAuthentcation.getInstance().getUserName() == null
				|| LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new UserNotFound(environment.getProperty(USER_NOT_FOUND));
		taxEventDetailsUpdateResource.setModifiedUser(LogginAuthentcation.getInstance().getUserName());

		SuccessAndErrorDetailsResource successAndErrorDetailsResource = new SuccessAndErrorDetailsResource();

		TaxEventDetails taxEventDetails = taxEventDetailsService.updateTaxEventDetails(tenantId,
				taxEventDetailsUpdateResource, id);
		successAndErrorDetailsResource.setMessages(environment.getProperty(RECORD_UPDATED));
		successAndErrorDetailsResource.setValue(taxEventDetails.getId().toString());
		return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.OK);

	}

	/**
	 * Get All Tax Event Details By Tax Event Id
	 *
	 * @param @PathVariable{tenantId}
	 * @param @PathVariable{taxEventId}
	 * @return List<TaxEventDetails>
	 */
	@GetMapping(value = "/{tenantId}/tax-event-id/{taxEventId}")
	public ResponseEntity<Object> getByBankSubCode(@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "taxEventId", required = true) String taxEventId) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<TaxEventDetails> taxEventDetails = taxEventDetailsService.findByTaxEventId(taxEventId);
		if (!taxEventDetails.isEmpty()) {
			return new ResponseEntity<>(taxEventDetails, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

}