package com.fusionx.lending.product.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.MasterCurrency;
import com.fusionx.lending.product.domain.MasterCurrencyPending;
import com.fusionx.lending.product.enums.WorkflowStatus;
import com.fusionx.lending.product.exception.PageableLengthException;
import com.fusionx.lending.product.exception.UserNotFound;
import com.fusionx.lending.product.resources.MasterCurrencyMainAddResource;
import com.fusionx.lending.product.resources.MasterCurrencyMainUpdateResource;
import com.fusionx.lending.product.resources.SuccessAndErrorDetails;
import com.fusionx.lending.product.resources.SuccessAndErrorDetailsResource;
import com.fusionx.lending.product.service.MasterCurrencyService;

/**
 * Master Def Currency Eligibility Controller
 * 
 *******************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *------------------------------------------------------------------------------------------------------
 *    1   13-07-2021      		     FXL-6	Piyumi      Created
 *    
 *******************************************************************************************************
 */

@RestController
@RequestMapping(value = "/master-currency")
@CrossOrigin(origins = "*")
public class MasterCurrencyController extends MessagePropertyBase{


	@Autowired
	private MasterCurrencyService masterCurrencyService;
	
	/**
	 * Get the Master Currency by id.
	 *
	 * @param tenantId - the tenant id
	 * @param id - the id
	 * @return Master Currency
	 */
	@GetMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> getMasterCurrencyById(
			@PathVariable(value = "tenantId") String tenantId,
			@PathVariable(value = "id") Long id) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<MasterCurrency> isPresentMasterCurrency = masterCurrencyService.getById(id);
		if (isPresentMasterCurrency.isPresent()) {
			return new ResponseEntity<>(isPresentMasterCurrency, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * get Master Currency by MasterDefinitionId
	 * 
	 * @param tenantId - the tenant id
	 * @param masterDefinitionId - the masterDefinitionId
	 * @return List<Type>
	 */
	@GetMapping(value = "/{tenantId}/master-definition/{masterDefinitionId}")
	public ResponseEntity<Object> getByMasterDefinitionId(
			@PathVariable(value = "tenantId") String tenantId,
			@PathVariable(value = "masterDefinitionId") Long masterDefinitionId) {

		
		List<MasterCurrency> masterCurrency = masterCurrencyService.getByMasterDefinitionId(masterDefinitionId);
		if (!masterCurrency.isEmpty()) {
			return new ResponseEntity<>(masterCurrency, HttpStatus.OK);
		} else {
			SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Add Master Currency
	 *
	 * @param tenantId - the tenant id
	 * @param masterDefinitionId - the masterDefinitionId
	 * @param masterCurrencyMainAddResource - the masterCurrencyMainAddResource
	 * @return SuccessAndErrorDetailsDto
	 */
	@PostMapping(value = "/{tenantId}/master-definition/{masterDefinitionId}")
	public ResponseEntity<Object> addMasterCurrency(@PathVariable(value = "tenantId") String tenantId,
			@PathVariable(value = "masterDefinitionId") Long masterDefinitionId,
			@Valid @RequestBody MasterCurrencyMainAddResource masterCurrencyMainAddResource) {
		
		if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new UserNotFound(environment.getProperty(COMMON_USER_NOT_FOUND));
		
		List<Long> idList = masterCurrencyService.addMasterCurrency(tenantId, masterDefinitionId, 
				masterCurrencyMainAddResource);
		SuccessAndErrorDetails successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty(RECORD_CREATED),
				idList != null ? idList.toString():null);
		return new ResponseEntity<>(successAndErrorDetails, HttpStatus.CREATED);
	}
	
	/**
	 * Update Master Currency
	 *
	 * @param tenantId - the tenant id
	 * @param masterDefinitionId - the masterDefinitionId
	 * @param masterCurrencyMainUpdateResource - the masterCurrencyMainUpdateResource
	 * @return SuccessAndErrorDetailsDto
	 */
	@PutMapping(value = "/{tenantId}/master-definition/{masterDefinitionId}")
	public ResponseEntity<Object> updateMasterCurrency(@PathVariable(value = "tenantId") String tenantId,
			@PathVariable(value = "masterDefinitionId") Long masterDefinitionId,
			@Valid @RequestBody MasterCurrencyMainUpdateResource masterCurrencyMainUpdateResource) {
		
		if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new UserNotFound(environment.getProperty(COMMON_USER_NOT_FOUND));
		
		Long id = masterCurrencyService.updateMasterCurrency(tenantId, masterDefinitionId, 
				masterCurrencyMainUpdateResource);
		SuccessAndErrorDetails  successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty(RECORD_UPDATED),
				Long.toString(id));
		return new ResponseEntity<>(successAndErrorDetails, HttpStatus.CREATED);
	}
	

	/**
	 * Search Master Currency
	 *
	 * @param tenantId - the tenant id
	 * @param searchq - the searchq
	 * @param status - the status
	 * @param approveStatus - the approve status
	 * @param pageable - the pageable
	 * @return the page
	 */
	@GetMapping(value = "/{tenantId}/workflow-items/search")
	public Page<MasterCurrencyPending> searchMasterCurrency(@PathVariable(value = "tenantId") String tenantId,
			@RequestParam(value = "searchq", required = false) String searchq,
			@RequestParam(value = "status", required = false) String status,
			@RequestParam(value = "approveStatus", required = false) String approveStatus,
			@PageableDefault Pageable pageable) {
		if(pageable.getPageSize()>Constants.MAX_PAGEABLE_LENGTH) 
			throw new PageableLengthException(environment.getProperty("common.pageable-length"));
		return masterCurrencyService.searchMasterCurrency(searchq, status, approveStatus, pageable);
	}
	
	/**
	 * Gets Master Currency by pending id.
	 *
	 * @param tenantId - the tenant id
	 * @param pendingId - the pending id
	 * @return Master Currency by pending id
	 */
	@GetMapping(value = "/{tenantId}/pending/{pendingId}")
	public ResponseEntity<Object> getMasterCurrencyByPendingId(
			@PathVariable(value = "tenantId") String tenantId,
			@PathVariable(value = "pendingId") Long pendingId) {
		
		Optional<MasterCurrencyPending> isPresentMasterCurrencyPending = masterCurrencyService.getByPendingId(pendingId);
		if (isPresentMasterCurrencyPending.isPresent()) {
			return new ResponseEntity<>(isPresentMasterCurrencyPending, HttpStatus.OK);
		} else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Approve Master Currency.
	 * @param tenantId - the tenant id
	 * @param pendingMasterDefinitionId 
	 * @return the response entity
	 */
	@PutMapping(value = "{tenantId}/pending/{pendingMasterDefinitionId}/approve")
	public ResponseEntity<Object> approveMasterCurrency(@PathVariable(value = "tenantId") String tenantId,
			@PathVariable(value = "pendingMasterDefinitionId") Long pendingMasterDefinitionId) {
		
		if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new UserNotFound(environment.getProperty(COMMON_USER_NOT_FOUND));
		
		SuccessAndErrorDetailsResource successAndErrorDetailsResource = new SuccessAndErrorDetailsResource();
			if (masterCurrencyService.approveReject(tenantId, pendingMasterDefinitionId, WorkflowStatus.COMPLETE)) {
				successAndErrorDetailsResource = new SuccessAndErrorDetailsResource(environment.getProperty(COMMON_APPROVED), pendingMasterDefinitionId.toString());
				return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.OK);
			} else {
				successAndErrorDetailsResource.setMessages(environment.getProperty(CANT_APPROVED));
				return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.UNPROCESSABLE_ENTITY);
			}
		
	}
	
	/**
	 * Reject Master Currency.
	 * @param tenantId - the tenant id
	 * @param pendingId - the pending id
	 * @return the response entity
	 */
	@PutMapping(value = "{tenantId}/pending/{pendingId}/reject")
	public ResponseEntity<Object> rejectMasterCurrency(@PathVariable(value = "tenantId") String tenantId,
			@PathVariable(value = "pendingId") Long pendingId) {
		
		if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new UserNotFound(environment.getProperty(COMMON_USER_NOT_FOUND));
		
		SuccessAndErrorDetailsResource successAndErrorDetailsResource = new SuccessAndErrorDetailsResource();

			if (masterCurrencyService.approveReject(tenantId, pendingId, WorkflowStatus.REJECT)) {
				successAndErrorDetailsResource = new SuccessAndErrorDetailsResource(environment.getProperty(COMMON_REJECTED), pendingId.toString());
				return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.OK);
			} else {
				successAndErrorDetailsResource.setMessages(environment.getProperty(CANT_REJECTED));
				return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.UNPROCESSABLE_ENTITY);
			}
		
	}

}
