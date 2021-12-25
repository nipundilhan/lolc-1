package com.fusionx.lending.origination.controller;

/**
 * 	Other Income Expenses
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   22-09-2021   FXL-641  	 FXL-792       Dilki        Created
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
import com.fusionx.lending.origination.domain.OtherIncomeExpenses;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.resource.OtherIncomeExpensesAddResource;
import com.fusionx.lending.origination.resource.OtherIncomeExpensesResource;
import com.fusionx.lending.origination.service.OtherIncomeExpensesService;
import com.fusionx.lending.origination.resource.SuccessAndErrorDetailsResource;

@RestController
@RequestMapping(value = "/other-income-expenses")
@CrossOrigin(origins = "*")
public class OtherIncomeExpensesController extends MessagePropertyBase {

	private OtherIncomeExpensesService otherIncomeExpensesService;

	@Autowired
	public void setOtherIncomeExpensesService(OtherIncomeExpensesService otherIncomeExpensesService) {
		this.otherIncomeExpensesService = otherIncomeExpensesService;
	}

	@GetMapping("/{tenantId}/all")
	public ResponseEntity<Object> getAllOtherIncomeExpenses(@PathVariable(value = "tenantId") String tenantId) {
		List<OtherIncomeExpenses> otherIncomeExpenses = otherIncomeExpensesService.findAll();
		if (!otherIncomeExpenses.isEmpty()) {
			return new ResponseEntity<>(otherIncomeExpenses, HttpStatus.OK);
		} else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * Get the OtherIncomeExpenses by id.
	 *
	 * @param tenantId - the tenant id
	 * @param id       - the id
	 * @return the OtherIncomeExpenses by id
	 */
	@GetMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> getOtherIncomeExpensesById(@PathVariable(value = "tenantId") String tenantId,
			@PathVariable(value = "id") Long id) {

		Optional<OtherIncomeExpenses> isPresentOtherIncomeExpenses = otherIncomeExpensesService.findById(id);
		if (isPresentOtherIncomeExpenses.isPresent()) {
			return new ResponseEntity<>(isPresentOtherIncomeExpenses.get(), HttpStatus.OK);
		} else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * Get the OtherIncomeExpensess by status.
	 *
	 * @param tenantId - the tenant id
	 * @param status   - the status
	 * @return the OtherIncomeExpenses by status
	 */

	@GetMapping(value = "/{tenantId}/status/{status}")
	public ResponseEntity<Object> getOtherIncomeExpensesByStatus(@PathVariable(value = "tenantId") String tenantId,
			@PathVariable(value = "status") String status) {

		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		if (status.equals(CommonStatus.ACTIVE.toString()) || status.equals(CommonStatus.INACTIVE.toString())) {
			List<OtherIncomeExpenses> otherIncomeExpenses = otherIncomeExpensesService.findByStatus(status);
			if (!otherIncomeExpenses.isEmpty()) {
				return new ResponseEntity<>(otherIncomeExpenses, HttpStatus.OK);
			} else {

				responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
				return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
			}
		} else {
			responseMessage.setMessages(RECORD_NOT_FOUND);
			throw new ValidateRecordException(environment.getProperty(COMMON_STATUS_PATTERN), "message");
		}
	}

	/**
	 * Add the OtherIncomeExpenses.
	 *
	 * @param tenantId
	 * @param OtherIncomeExpensesAddResource
	 * @return the response entity
	 */

	@PostMapping("/{tenantId}")
	public ResponseEntity<Object> addOtherIncomeExpenses(@PathVariable(value = "tenantId") String tenantId,
			@Valid @RequestBody OtherIncomeExpensesAddResource otherIncomeExpensesAddResource) {

		otherIncomeExpensesService.saveOtherIncome(tenantId, otherIncomeExpensesAddResource);
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource(
				getEnvironment().getProperty(RECORD_CREATED));
		return new ResponseEntity<>(responseMessage, HttpStatus.CREATED);
	}

	/**
	 * Update OtherIncomeExpenses.
	 *
	 * @param tenantId
	 * @param id
	 * @param OtherIncomeExpensesResource
	 * @return the response entity
	 */

	@PutMapping(value = "{tenantId}/{id}")
	public ResponseEntity<Object> updateOtherIncomeExpenses(@PathVariable(value = "tenantId") String tenantId,
			@PathVariable(value = "id") Long id,
			@Valid @RequestBody OtherIncomeExpensesResource otherIncomeExpenseTypeResource) {

		SuccessAndErrorDetailsResource successAndErrorExpensesResource = new SuccessAndErrorDetailsResource();
		Optional<OtherIncomeExpenses> isPresentOtherIncomeExpenses = otherIncomeExpensesService.findById(id);

		if (isPresentOtherIncomeExpenses.isPresent()) {
			OtherIncomeExpenses otherIncomeExpenses = otherIncomeExpensesService.update(tenantId, id,
					otherIncomeExpenseTypeResource);

			successAndErrorExpensesResource = new SuccessAndErrorDetailsResource(
					getEnvironment().getProperty(RECORD_UPDATED), otherIncomeExpenses.getId().toString());
			return new ResponseEntity<>(successAndErrorExpensesResource, HttpStatus.OK);
		} else {
			successAndErrorExpensesResource.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(successAndErrorExpensesResource, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

	/**
	 * Get the OtherIncomeExpenses by otherIncomeDetailsId.
	 *
	 * @param tenantId
	 * @param otherIncomeDetailsId
	 * @return the OtherIncomeExpenses by otherIncomeDetailsId
	 */
	@GetMapping(value = "/{tenantId}/other-income-details/{otherIncomeDetailsId}")
	public ResponseEntity<Object> getByOtherIncomeDetailsId(@PathVariable(value = "tenantId") String tenantId,
			@PathVariable(value = "otherIncomeDetailsId") Long otherIncomeDetailsId) {

		List<OtherIncomeExpenses> otherIncomeExpenses = otherIncomeExpensesService
				.findByOtherIncomeDetailsId(otherIncomeDetailsId);
		if (!otherIncomeExpenses.isEmpty()) {
			return new ResponseEntity<>(otherIncomeExpenses, HttpStatus.OK);
		} else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

}
