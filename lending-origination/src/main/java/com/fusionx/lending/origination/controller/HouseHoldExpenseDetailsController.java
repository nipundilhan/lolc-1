package com.fusionx.lending.origination.controller;

import java.util.List;

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
import com.fusionx.lending.origination.domain.HouseHoldExpenseDetails;
import com.fusionx.lending.origination.resource.HouseHoldExpenseDetailsAddResource;
import com.fusionx.lending.origination.resource.HouseHoldExpenseDetailsUpdateResource;
import com.fusionx.lending.origination.resource.SuccessAndErrorDetailsResource;
import com.fusionx.lending.origination.service.HouseHoldExpenseDetailsService;
/**
 * API Service related to House Hold Expense Details.
 *
 * @author Dilhan
 * @version 1.0.0
 * @since 1.0.0
 * <p>
 * <br/><br/>
 * <b>Change History : </b>
 * <br/><br/>
 * #        Date            Story Point     Task No     Author                  Description
 * <br/>
 * .....................................................................................................................<br/>
 * 1        02-09-2021      	             FXL-662    Dilhan                    Created
 * <p>
 *
 */

@RestController
@RequestMapping(value = "/house-hold-expense-details")
@CrossOrigin(origins = "*")
public class HouseHoldExpenseDetailsController extends MessagePropertyBase {

	@Autowired
	private HouseHoldExpenseDetailsService houseHoldExpenseDetailsService;

	/**
	 * Add the HouseHoldExpenseDetails.
	 *
	 * @param tenantId - the tenant id
	 * @param HouseHoldExpenseDetailsAddResource - the house hold expense details add resource
	 * @return the response entity
	 */
	@PostMapping("/{tenantId}")
	public ResponseEntity<Object> addIncomeSourceDetails(@PathVariable(value = "tenantId") String tenantId,
			@Valid @RequestBody HouseHoldExpenseDetailsAddResource houseHoldExpenseDetailsAddResource) {
		houseHoldExpenseDetailsService.saveHouseHoldExpenseDetails(tenantId, houseHoldExpenseDetailsAddResource);
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource(
				getEnvironment().getProperty(RECORD_CREATED));
		return new ResponseEntity<>(responseMessage, HttpStatus.CREATED);
	}

	@PutMapping("/{tenantId}")
	public ResponseEntity<Object> updateExpenseTypeHouseholdExpenseCategories(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@Valid @RequestBody HouseHoldExpenseDetailsUpdateResource houseHoldExpenseDetailsUpdateResource) {
		SuccessAndErrorDetailsResource successAndErrorDetailsResource = new SuccessAndErrorDetailsResource();

		houseHoldExpenseDetailsService.updateHouseHoldExpenseDetails(tenantId, houseHoldExpenseDetailsUpdateResource);
		successAndErrorDetailsResource = new SuccessAndErrorDetailsResource(
				getEnvironment().getProperty(RECORD_UPDATED));
		return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.OK);
	}


	@GetMapping(value = "/{tenantId}/income-source-details/{incomeSourceDetailsId}")
	public ResponseEntity<Object> getIncomeSourceDetailsByLinkedPersonId(
			@PathVariable(value = "tenantId") String tenantId,
			@PathVariable(value = "incomeSourceDetailsId") Long incomeSourceDetailsId) {

		List<HouseHoldExpenseDetails> houseHoldExpenseDetails = houseHoldExpenseDetailsService
				.findByIncomeSourceDetailsId(incomeSourceDetailsId);
		if (!houseHoldExpenseDetails.isEmpty()) {
			return new ResponseEntity<>(houseHoldExpenseDetails, HttpStatus.OK);
		} else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

}
