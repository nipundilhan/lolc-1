package com.fusionx.lending.product.controller;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fusionx.lending.product.Constants;
import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.EligibilityCurrency;
import com.fusionx.lending.product.domain.EligibilityCurrencyPending;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.enums.WorkflowStatus;
import com.fusionx.lending.product.exception.PageableLengthException;
import com.fusionx.lending.product.exception.UserNotFound;
import com.fusionx.lending.product.resources.EligibilityCurrencyAddResource;
import com.fusionx.lending.product.resources.EligibilityCurrencyUpdateResource;
import com.fusionx.lending.product.resources.SuccessAndErrorDetailsResource;
import com.fusionx.lending.product.service.EligibilityCurrencyService;

/**
 * Eligibility Currency Controller
 * 
 *******************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *------------------------------------------------------------------------------------------------------
 *    1   01-07-2021      		     FX-6891	MiyuruW      Created
 *    
 *******************************************************************************************************
 */

@RestController
@RequestMapping(value = "/eligibility-currency")
@CrossOrigin(origins = "*")
public class EligibilityCurrencyController {

	@Autowired
	private Environment environment;
	
	@Autowired
	private EligibilityCurrencyService eligibilityCurrencyService;
	
	private String userNotFound = "common.user-not-found";
	private String commonSaved = "common.saved";
	private String commonUpdated = "common.updated";
	private String pageableLength = "common.pageable-length";
	private String recordNotFound = "common.record-not-found";
	private String invalidStatus = "invalid-status";
	private String commonApproved = "common.approved";
	private String cantApprove = "common.can-not-approved";
	private String commonRejected = "common.rejected";
	private String cantReject = "common.can-not-rejected";
	
	
	/**
	 * Gets the all eligibility currencys.
	 *
	 * @param tenantId - the tenant id
	 * @return the all eligibility currencys
	 */
	@GetMapping(value = "/{tenantId}/all")
	public ResponseEntity<Object> getAllEligibilityCurrencys(@PathVariable(value = "tenantId", required = true) String tenantId) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<EligibilityCurrency> eligibilityCurrency = eligibilityCurrencyService.findAll(tenantId);
		if (!eligibilityCurrency.isEmpty()) {
			return new ResponseEntity<>((Collection<EligibilityCurrency>) eligibilityCurrency, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	/**
	 * Gets the eligibility currency by id.
	 *
	 * @param tenantId - the tenant id
	 * @param id - the id
	 * @return the eligibility currency by id
	 */
	@GetMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> getEligibilityCurrencyById(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<EligibilityCurrency> isPresentEligibilityCurrency = eligibilityCurrencyService.findById(tenantId, id);
		if (isPresentEligibilityCurrency.isPresent()) {
			return new ResponseEntity<>(isPresentEligibilityCurrency, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	/**
	 * Gets the eligibility currencys by status.
	 *
	 * @param tenantId - the tenant id
	 * @param status - the status
	 * @return the eligibility currencys by status
	 */
	@GetMapping(value = "/{tenantId}/status/{status}")
	public ResponseEntity<Object> getEligibilityCurrencysByStatus(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "status", required = true) String status) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		if(status.equals(CommonStatus.ACTIVE.toString()) || status.equals(CommonStatus.INACTIVE.toString())) {
			List<EligibilityCurrency> eligibilityCurrency = eligibilityCurrencyService.findByStatus(tenantId, status);
			if (!eligibilityCurrency.isEmpty()) {
				return new ResponseEntity<>((Collection<EligibilityCurrency>) eligibilityCurrency, HttpStatus.OK);
			} else {
				responseMessage.setMessages(environment.getProperty(recordNotFound));
				return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
			}
		} else {
			responseMessage.setMessages(environment.getProperty(invalidStatus));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	/**
	 * Gets the eligibility currencys by eligibility id.
	 *
	 * @param tenantId - the tenant id
	 * @param eligibilityId - the eligibility id
	 * @return the eligibility currencys by eligibility id
	 */
	@GetMapping(value = "/{tenantId}/eligibility/{eligibilityId}")
	public ResponseEntity<Object> getEligibilityCurrencysByEligibilityId(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "eligibilityId", required = true) Long eligibilityId) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<EligibilityCurrency> eligibilityCurrency = eligibilityCurrencyService.findByEligibilityId(tenantId, eligibilityId);
		if (!eligibilityCurrency.isEmpty()) {
			return new ResponseEntity<>((Collection<EligibilityCurrency>) eligibilityCurrency, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	/**
	 * Gets the eligibility currencys by currency id.
	 *
	 * @param tenantId - the tenant id
	 * @param currencyId - the currency id
	 * @return the eligibility currencys by currency id
	 */
	@GetMapping(value = "/{tenantId}/currency/{currencyId}")
	public ResponseEntity<Object> getEligibilityCurrencysByCurrencyId(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "currencyId", required = true) Long currencyId) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<EligibilityCurrency> eligibilityCurrency = eligibilityCurrencyService.findByCurrencyId(tenantId, currencyId);
		if (!eligibilityCurrency.isEmpty()) {
			return new ResponseEntity<>((Collection<EligibilityCurrency>) eligibilityCurrency, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	/**
	 * Adds the eligibility currency.
	 *
	 * @param tenantId - the tenant id
	 * @param eligibilityCurrencyAddResource - the eligibility currency add resource
	 * @return the response entity
	 */
	@PostMapping(value = "/{tenantId}")
	public ResponseEntity<Object> addEligibilityCurrency(@PathVariable(value = "tenantId", required = true) String tenantId,
			@Valid @RequestBody EligibilityCurrencyAddResource eligibilityCurrencyAddResource) {
		if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new UserNotFound(environment.getProperty(userNotFound));
		Long id = eligibilityCurrencyService.saveAndValidateEligibilityCurrency(tenantId, LogginAuthentcation.getInstance().getUserName(), eligibilityCurrencyAddResource);
		SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty(commonSaved), id);
		return new ResponseEntity<>(successDetailsDto,HttpStatus.CREATED);
	}
	
	
	/**
	 * Update eligibility currency.
	 *
	 * @param tenantId - the tenant id
	 * @param id - the id
	 * @param eligibilityCurrencyUpdateResource - the eligibility currency update resource
	 * @return the response entity
	 */
	@PutMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> updateEligibilityCurrency(@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id,
			@Valid @RequestBody EligibilityCurrencyUpdateResource eligibilityCurrencyUpdateResource) {
		if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new UserNotFound(environment.getProperty(userNotFound));
		eligibilityCurrencyService.updateAndValidateEligibilityCurrency(tenantId, LogginAuthentcation.getInstance().getUserName(), id, eligibilityCurrencyUpdateResource);
		SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty(commonUpdated));
		return new ResponseEntity<>(successDetailsDto, HttpStatus.OK);
	}
	
	
	/**
	 * Approve eligibility currency.
	 *
	 * @param tenantId - the tenant id
	 * @param pendingId - the pending id
	 * @return the response entity
	 */
	@PutMapping(value = "{tenantId}/pending/{pendingId}/approve")
	public ResponseEntity<Object> approveEligibilityCurrency(@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "pendingId", required = true) Long pendingId) {
		SuccessAndErrorDetailsResource successAndErrorDetailsResource = new SuccessAndErrorDetailsResource();
		Optional<EligibilityCurrencyPending> isPresentEligibilityCurrencyPending = eligibilityCurrencyService.getByPendingId(pendingId);
		if (isPresentEligibilityCurrencyPending.isPresent()) {
			if (eligibilityCurrencyService.approveReject(tenantId, pendingId, WorkflowStatus.COMPLETE)) {
				successAndErrorDetailsResource = new SuccessAndErrorDetailsResource(environment.getProperty(commonApproved), pendingId.toString());
				return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.OK);
			} else {
				successAndErrorDetailsResource.setMessages(environment.getProperty(cantApprove));
				return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.UNPROCESSABLE_ENTITY);
			}
		} else {
			successAndErrorDetailsResource.setMessages(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
	
	
	/**
	 * Reject eligibility currency.
	 *
	 * @param tenantId - the tenant id
	 * @param pendingId - the pending id
	 * @return the response entity
	 */
	@PutMapping(value = "{tenantId}/pending/{pendingId}/reject")
	public ResponseEntity<Object> rejectEligibilityCurrency(@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "pendingId", required = true) Long pendingId) {
		SuccessAndErrorDetailsResource successAndErrorDetailsResource = new SuccessAndErrorDetailsResource();
		Optional<EligibilityCurrencyPending> isPresentEligibilityCurrencyPending = eligibilityCurrencyService.getByPendingId(pendingId);
		if (isPresentEligibilityCurrencyPending.isPresent()) {
			if (eligibilityCurrencyService.approveReject(tenantId, pendingId, WorkflowStatus.REJECT)) {
				successAndErrorDetailsResource = new SuccessAndErrorDetailsResource(environment.getProperty(commonRejected), pendingId.toString());
				return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.OK);
			} else {
				successAndErrorDetailsResource.setMessages(environment.getProperty(cantReject));
				return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.UNPROCESSABLE_ENTITY);
			}
		} else {
			successAndErrorDetailsResource.setMessages(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
	
	
	/**
	 * Gets the eligibility currency by pending id.
	 *
	 * @param tenantId - the tenant id
	 * @param pendingId - the pending id
	 * @return the eligibility currency by pending id
	 */
	@GetMapping(value = "/{tenantId}/pending/{pendingId}")
	public ResponseEntity<Object> getEligibilityCurrencyByPendingId(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "pendingId", required = true) Long pendingId) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<EligibilityCurrencyPending> isPresentEligibilityCurrencyPending = eligibilityCurrencyService.getByPendingId(pendingId);
		if (isPresentEligibilityCurrencyPending.isPresent()) {
			return new ResponseEntity<>(isPresentEligibilityCurrencyPending, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	/**
	 * Search eligibility currency.
	 *
	 * @param tenantId - the tenant id
	 * @param searchq - the searchq
	 * @param status - the status
	 * @param approveStatus - the approve status
	 * @param pageable - the pageable
	 * @return the page
	 */
	@GetMapping(value = "/{tenantId}/workflow-items/search")
	public Page<EligibilityCurrencyPending> searchEligibilityCurrency(@PathVariable(value = "tenantId", required = true) String tenantId,
			@RequestParam(value = "searchq", required = false) String searchq,
			@RequestParam(value = "status", required = false) String status,
			@RequestParam(value = "approveStatus", required = false) String approveStatus,
			@PageableDefault(size = 10) Pageable pageable) {
		if(pageable.getPageSize()>Constants.MAX_PAGEABLE_LENGTH) 
			throw new PageableLengthException(environment.getProperty(pageableLength));
		return eligibilityCurrencyService.searchEligibilityCurrency(searchq, status, approveStatus, pageable);
	}
}
