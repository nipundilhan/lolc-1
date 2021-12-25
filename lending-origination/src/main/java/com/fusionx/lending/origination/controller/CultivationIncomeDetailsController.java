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
import com.fusionx.lending.origination.domain.CultivationIncomeDetails;
import com.fusionx.lending.origination.resource.CultivationIncomeDetailsAddResource;
import com.fusionx.lending.origination.resource.CultivationIncomeDetailsUpdateResource;
import com.fusionx.lending.origination.resource.SuccessAndErrorDetailsResource;
import com.fusionx.lending.origination.service.CultivationIncomeDetailsService;

@RestController
@RequestMapping(value = "/cultivation-income-details")
@CrossOrigin(origins = "*")
public class CultivationIncomeDetailsController extends MessagePropertyBase {

	private CultivationIncomeDetailsService cultivationIncomeDetailsService;

	@Autowired
	public void setCultivationIncomeDetailsService(CultivationIncomeDetailsService cultivationIncomeDetailsService) {
		this.cultivationIncomeDetailsService = cultivationIncomeDetailsService;
	}

	@GetMapping("/{tenantId}/all")
	public ResponseEntity<Object> getAllCultivationIncomeDetails(@PathVariable(value = "tenantId") String tenantId) {
		List<CultivationIncomeDetails> cultivationIncomeDetails = cultivationIncomeDetailsService.findAll(tenantId);
		if (!cultivationIncomeDetails.isEmpty()) {
			return new ResponseEntity<>(cultivationIncomeDetails, HttpStatus.OK);
		} else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

	@GetMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> getCultivationIncomeDetailsById(@PathVariable(value = "tenantId") String tenantId,
			@PathVariable(value = "id") Long id) {

		Optional<CultivationIncomeDetails> isPresentCultivationIncomeDetails = cultivationIncomeDetailsService
				.findById(tenantId, id);
		if (isPresentCultivationIncomeDetails.isPresent()) {
			return new ResponseEntity<>(isPresentCultivationIncomeDetails.get(), HttpStatus.OK);
		} else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

	@GetMapping(value = "/{tenantId}/status/{status}")
	public ResponseEntity<Object> getCultivationIncomeDetailsByStatus(@PathVariable(value = "tenantId") String tenantId,
			@PathVariable(value = "status") String status) {

		List<CultivationIncomeDetails> cultivationIncomeDetails = cultivationIncomeDetailsService.findByStatus(tenantId,
				status);
		if (!cultivationIncomeDetails.isEmpty()) {
			return new ResponseEntity<>(cultivationIncomeDetails, HttpStatus.OK);
		} else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

	@GetMapping(value = "/{tenantId}/income-source-details/{incomeSourceDetailsId}")
	public ResponseEntity<Object> getCultivationIncomeDetailsByIncomeSourceDetailsId(
			@PathVariable(value = "tenantId") String tenantId,
			@PathVariable(value = "incomeSourceDetailsId") Long incomeSourceDetailsId) {

		List<CultivationIncomeDetails> cultivationIncomeDetails = cultivationIncomeDetailsService
				.findByIncomeSourceDetailsId(tenantId, incomeSourceDetailsId);
		if (!cultivationIncomeDetails.isEmpty()) {
			return new ResponseEntity<>(cultivationIncomeDetails, HttpStatus.OK);
		} else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * Add the CultivationIncomeDetails.
	 *
	 * @param tenantId                            - the tenant id
	 * @param CultivationIncomeDetailsAddResource - the cultivation income details
	 *                                            add resource
	 * @return the response entity
	 */
	@PostMapping("/{tenantId}")
	public ResponseEntity<Object> addCultivationIncomeDetails(@PathVariable(value = "tenantId") String tenantId,
			@Valid @RequestBody CultivationIncomeDetailsAddResource cultivationIncomeDetailsAddResource) {
		Long incomeDetailsId = cultivationIncomeDetailsService.saveCultivationIncomeDetails(tenantId,
				cultivationIncomeDetailsAddResource);
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource(
				getEnvironment().getProperty(RECORD_CREATED), Long.toString(incomeDetailsId));
		return new ResponseEntity<>(responseMessage, HttpStatus.CREATED);
	}

	/**
	 * Update the CultivationIncomeDetails.
	 *
	 * @param tenantId                               - the tenant id
	 * @param id                                     - the id
	 * @param CultivationIncomeDetailsUpdateResource - the cultivation income
	 *                                               details update resource
	 * @return the response entity
	 */
	@PutMapping(value = "{tenantId}/{id}")
	public ResponseEntity<Object> updateSalaryIncomeDetails(@PathVariable(value = "tenantId") String tenantId,
			@PathVariable(value = "id") Long id,
			@Valid @RequestBody CultivationIncomeDetailsUpdateResource cultivationIncomeDetailsUpdateResource) {
		SuccessAndErrorDetailsResource successAndErrorDetailsResource = new SuccessAndErrorDetailsResource();
		Optional<CultivationIncomeDetails> isPresentCultivationIncomeDetails = cultivationIncomeDetailsService
				.findById(tenantId, id);
		if (isPresentCultivationIncomeDetails.isPresent()) {
			CultivationIncomeDetails cultivationIncomeDetails = cultivationIncomeDetailsService
					.updateCultivationIncomeDetails(tenantId, id, cultivationIncomeDetailsUpdateResource);
			successAndErrorDetailsResource = new SuccessAndErrorDetailsResource(
					getEnvironment().getProperty(RECORD_UPDATED), cultivationIncomeDetails.getId().toString());
			return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.OK);
		} else {
			successAndErrorDetailsResource.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
}
