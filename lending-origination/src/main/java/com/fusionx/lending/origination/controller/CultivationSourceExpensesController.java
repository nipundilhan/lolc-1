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
import com.fusionx.lending.origination.domain.CultivationIncomeExpenses;
import com.fusionx.lending.origination.resource.CultivationIncomeExpensesAddResource;
import com.fusionx.lending.origination.resource.CultivationIncomeExpensesUpdateResource;
import com.fusionx.lending.origination.resource.SuccessAndErrorDetailsResource;
import com.fusionx.lending.origination.service.CultivationIncomeExpensesService;

/**
 * API Service related to Cultivation Income Expenses.
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
 * 1        14-09-2021      	             FXL-661    Dilhan                    Created
 * <p>
 *
 */
@RestController
@RequestMapping(value = "/cultivation-source-expenses")
@CrossOrigin(origins = "*")
public class CultivationSourceExpensesController extends MessagePropertyBase{

	private CultivationIncomeExpensesService cultivationIncomeExpensesService;

	@Autowired
	public void setCultivationIncomeExpensesService(CultivationIncomeExpensesService cultivationIncomeExpensesService) {
		this.cultivationIncomeExpensesService = cultivationIncomeExpensesService;
	}

	/**
	 * Gets the all cultivation income details.
	 *
	 * @param tenantId the tenant id
	 * @return the all cultivation income details
	 */
	@GetMapping("/{tenantId}/all")
	public ResponseEntity<Object> getAllCultivationIncomeDetails(@PathVariable(value = "tenantId") String tenantId) {
		List<CultivationIncomeExpenses> cultivationIncomeExpenses = cultivationIncomeExpensesService.findAll(tenantId);
		if (!cultivationIncomeExpenses.isEmpty()) {
			return new ResponseEntity<>(cultivationIncomeExpenses, HttpStatus.OK);
		} else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * Get the CultivationSourceExpenseDetails by id.
	 *
	 * @param tenantId - the tenant id
	 * @param id - the id
	 * @return the CultivationSourceExpenseDetails by id
	 */
	@GetMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> getCultivationIncomeDetailsById(@PathVariable(value = "tenantId") String tenantId,
			@PathVariable(value = "id") Long id) {

		Optional<CultivationIncomeExpenses> isPresentCultivationIncomeExpenses = cultivationIncomeExpensesService
				.findById(tenantId, id);
		if (isPresentCultivationIncomeExpenses.isPresent()) {
			return new ResponseEntity<>(isPresentCultivationIncomeExpenses.get(), HttpStatus.OK);
		} else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * Get the CultivationSourceExpenseDetails by cultivation income details id.
	 *
	 * @param tenantId - the tenant id
	 * @param cultivationIncomeDetailsId - the cultivationIncomeDetailsId
	 * @return the CultivationSourceExpenseDetails by cultivationIncomeDetailsId
	 */
	@GetMapping(value = "/{tenantId}/cultivation-income-details/{id}")
	public ResponseEntity<Object> getCultivationSourceExpenseDetailsByCultIncomeId(
			@PathVariable(value = "tenantId") String tenantId,
			@PathVariable(value = "id") Long id) {
		
		List<CultivationIncomeExpenses> cultivationExpenseDetails = cultivationIncomeExpensesService
				.getCultivationExpenseDetails(id);
		if (cultivationExpenseDetails != null && !cultivationExpenseDetails.isEmpty()) {
			return new ResponseEntity<>(cultivationExpenseDetails, HttpStatus.OK);
		} else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Add the CultivationSourceExpenseDetails.
	 *
	 * @param tenantId - the tenant id
	 * @param CultivationIncomeExpensesAddResource - the cultivation income expenses add resource
	 * @return 
	 */
	@PostMapping("/{tenantId}")
	public ResponseEntity<Object> addCultivationExpense(@PathVariable(value = "tenantId") String tenantId,
			@Valid @RequestBody CultivationIncomeExpensesAddResource cultivationIncomeExpensesAddResource) {
		cultivationIncomeExpensesService.saveCultivationIncomeExpenses(tenantId, cultivationIncomeExpensesAddResource);
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource(
				getEnvironment().getProperty(RECORD_CREATED));
		return new ResponseEntity<>(responseMessage, HttpStatus.CREATED);
	}

	/**
	 * Update CultivationSourceExpenseDetails.
	 *
	 * @param tenantId - the tenant id
	 * @param id - the id
	 * @param CultivationIncomeExpensesUpdateResource - the cultivation income expenses update resource
	 * @return the response entity
	 */
	@PutMapping("/{tenantId}/{id}")
	public ResponseEntity<Object> updateCultivationExpense(
			@PathVariable(value = "tenantId") String tenantId,
			@PathVariable(value = "id") Long id, 
			@Valid @RequestBody CultivationIncomeExpensesUpdateResource cultivationIncomeExpensesUpdateResource) {
		SuccessAndErrorDetailsResource successAndErrorDetailsResource = new SuccessAndErrorDetailsResource();
		Optional<CultivationIncomeExpenses> isPresentCultivationIncomeExpenses = cultivationIncomeExpensesService.findById(tenantId, id);	
		if(isPresentCultivationIncomeExpenses.isPresent()) {
			CultivationIncomeExpenses cultivationIncomeExpenses = cultivationIncomeExpensesService.updateCultivationIncomeExpenses(tenantId, id, cultivationIncomeExpensesUpdateResource);
			successAndErrorDetailsResource = new SuccessAndErrorDetailsResource(getEnvironment().getProperty(RECORD_UPDATED), cultivationIncomeExpenses.getId().toString());
			return new ResponseEntity<>(successAndErrorDetailsResource,HttpStatus.OK);
		}
		else {
			successAndErrorDetailsResource.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
}
