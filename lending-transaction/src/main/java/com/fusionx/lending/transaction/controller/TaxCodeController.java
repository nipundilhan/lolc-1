package com.fusionx.lending.transaction.controller;

/**
 * Tax Code Mapping With Transaction Sub Code
 * <p>
 * *******************************************************************************************************
 * ###   Date         Story Point   Task No    Author       Description
 * -------------------------------------------------------------------------------------------------------
 * 1   11-10-2021     FXL-74        FXL-1130   Dilki      Created
 * <p>
 * *******************************************************************************************************
 */
import com.fusionx.lending.transaction.base.MessagePropertyBase;
import com.fusionx.lending.transaction.core.LogginAuthentcation;
import com.fusionx.lending.transaction.domain.TaxCode;
import com.fusionx.lending.transaction.exception.UserNotFound;
import com.fusionx.lending.transaction.resource.AddTaxCodeRequestResource;
import com.fusionx.lending.transaction.resource.SuccessAndErrorDetails;
import com.fusionx.lending.transaction.resource.SuccessAndErrorDetailsResource;
import com.fusionx.lending.transaction.resource.UpdateTaxCodeRequestResource;
import com.fusionx.lending.transaction.service.TaxCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/tax-transaction-code-mapping")
@CrossOrigin(origins = "*")
public class TaxCodeController extends MessagePropertyBase {

	private TaxCodeService taxCodeService;

	@Autowired
	public void setTaxCodeService(TaxCodeService taxCodeService) {
		this.taxCodeService = taxCodeService;
	}

	/**
	 * Gets list of all items
	 *
	 * @param tenantId the tenant id
	 * @return the list of all items.
	 */
	@GetMapping(value = "/{tenantId}/all")
	public ResponseEntity<Object> getAllTaxCode(@PathVariable(value = "tenantId") String tenantId) {

		List<TaxCode> taxCode = taxCodeService.findAll();
		int size = taxCode.size();

		if (size > 0) {
			return new ResponseEntity<>(taxCode, HttpStatus.OK);
		} else {
			SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
			responseMessage.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * Get Tax Code by id
	 *
	 * @param tenantId the tenant id
	 * @param id       the id of the record
	 * @return the Tax Code record.
	 */
	@GetMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> getTaxCodeById(@PathVariable(value = "tenantId") String tenantId,
			@PathVariable(value = "id") Long id) {

		Optional<TaxCode> isPresentTaxCode = taxCodeService.findById(id);

		if (isPresentTaxCode.isPresent()) {
			return new ResponseEntity<>(isPresentTaxCode.get(), HttpStatus.OK);
		} else {
			SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
			responseMessage.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * Gets Tax Code by code
	 *
	 * @param tenantId the tenant id
	 * @param code     the code
	 * @return the Tax Code record.
	 */
	@GetMapping(value = "/{tenantId}/code/{code}")
	public ResponseEntity<Object> getTaxCodeByTaxCode(@PathVariable(value = "tenantId") String tenantId,
			@PathVariable(value = "code") String code) {

		Optional<TaxCode> isPresentTaxCode = taxCodeService.findByCode(code);

		if (isPresentTaxCode.isPresent()) {
			return new ResponseEntity<>(isPresentTaxCode.get(), HttpStatus.OK);
		} else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * Gets Tax Codes by status
	 *
	 * @param tenantId the tenant id
	 * @param status   the status should be either <code>ACTIVE</code> or
	 *                 <code>INACTIVE</code>
	 * @return the list of Tax Codes.
	 */
	@GetMapping(value = "/{tenantId}/status/{status}")
	public ResponseEntity<Object> getTaxCodeByStatus(@PathVariable(value = "tenantId") String tenantId,
			@PathVariable(value = "status") String status) {

		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();

		if (status.equals("ACTIVE") || status.equals("INACTIVE")) {
			List<TaxCode> isPresentTaxCode = taxCodeService.findByStatus(status);
			int size = isPresentTaxCode.size();

			if (size > 0) {
				return new ResponseEntity<>(isPresentTaxCode, HttpStatus.OK);
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
	 * Saves the given Tax Code record
	 *
	 * @param tenantId           the tenant id
	 * @param taxCodeAddResource the resource file
	 * @return the saved record id.
	 */
	@PostMapping(value = "/{tenantId}")
	public ResponseEntity<Object> addTaxCode(@PathVariable(value = "tenantId") String tenantId,
			@Valid @RequestBody AddTaxCodeRequestResource taxCodeAddResource) {

		if (LogginAuthentcation.getInstance().getUserName() == null
				|| LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new UserNotFound(environment.getProperty(USER_NOT_FOUND));
		taxCodeAddResource.setTaxCodeCreatedUser(LogginAuthentcation.getInstance().getUserName());

		TaxCode taxCode = taxCodeService.addTaxCode(tenantId, taxCodeAddResource);
		SuccessAndErrorDetailsResource successAndErrorDetailsResource = new SuccessAndErrorDetailsResource();
		successAndErrorDetailsResource.setMessages(environment.getProperty(RECORD_CREATED));
		successAndErrorDetailsResource.setValue(taxCode.getId().toString());
		return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.CREATED);
	}

	/**
	 * Updates the given Tax Code record
	 *
	 * @param tenantId              the tenant id
	 * @param id                    the id of record
	 * @param taxCodeUpdateResource the resource file
	 * @return the updated record id with message
	 */
	@PutMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> updateTaxCode(@PathVariable(value = "tenantId") String tenantId,
			@PathVariable(value = "id") Long id,
			@Valid @RequestBody UpdateTaxCodeRequestResource taxCodeUpdateResource) {

		if (LogginAuthentcation.getInstance().getUserName() == null
				|| LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new UserNotFound(environment.getProperty(USER_NOT_FOUND));
		taxCodeUpdateResource.setModifiedUser(LogginAuthentcation.getInstance().getUserName());

		SuccessAndErrorDetailsResource successAndErrorDetailsResource = new SuccessAndErrorDetailsResource();

		TaxCode taxCode = taxCodeService.updateTaxCode(tenantId, taxCodeUpdateResource, id);
		successAndErrorDetailsResource.setMessages(environment.getProperty(RECORD_UPDATED));
		successAndErrorDetailsResource.setValue(taxCode.getId().toString());
		return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.OK);

	}

	/**
	 * Get All Tax Code By Bank Transaction Sub Code
	 *
	 * @param @PathVariable{tenantId}
	 * @param @PathVariable{subCode}
	 * @return List<TaxCode>
	 */
	@GetMapping(value = "/{tenantId}/bank-sub-code/{subCode}")
	public ResponseEntity<Object> getByBankSubCode(@PathVariable(value = "tenantId") String tenantId,
			@PathVariable(value = "subCode") String subCode) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<TaxCode> taxCode = taxCodeService.findByBankTransactionSubCode(subCode);
		if (!taxCode.isEmpty()) {
			return new ResponseEntity<>(taxCode, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * Get All Tax Code By Bank Transaction Code
	 *
	 * @param @PathVariable{tenantId}
	 * @param @PathVariable{bankCode}
	 * @return List<TaxCode>
	 */
	@GetMapping(value = "/{tenantId}/bank-code/{bankCode}")
	public ResponseEntity<Object> getByBankCode(@PathVariable(value = "tenantId") String tenantId,
			@PathVariable(value = "bankCode") String bankCode) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<TaxCode> taxCode = taxCodeService.findByBankTransactionCodeId(bankCode);
		if (!taxCode.isEmpty()) {
			return new ResponseEntity<>(taxCode, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

}