package com.fusionx.lending.origination.controller;

/**
 * Credit App Collateral Details
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   20-04-2021      		     			VenukiR      Created
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
import com.fusionx.lending.origination.core.LogginAuthentcation;
import com.fusionx.lending.origination.domain.CreditAppCollateralDetail;
import com.fusionx.lending.origination.exception.UserNotFound;
import com.fusionx.lending.origination.resource.CreditAppraiselCollateralDetailsAddResource;
import com.fusionx.lending.origination.resource.CreditAppraiselCollateralDetailsUpdateResource;
import com.fusionx.lending.origination.resource.SuccessAndErrorDetailsResource;
import com.fusionx.lending.origination.service.CreditAppCollateralDetailsService;

@RestController
@RequestMapping(value = "/credit-app-collateral-details")
@CrossOrigin(origins = "*")
public class CreditAppCollateralDetailsController extends MessagePropertyBase {

	@Autowired
	private CreditAppCollateralDetailsService creditAppCollateralDetailsService;

	/**
	 * Gets the all CreditAppCollateralDetail.
	 *
	 * @param tenantId - the tenant id
	 * @param pageable - the pageable
	 * @return the all CreditAppCollateralDetail
	 */
//	@GetMapping(value = "/{tenantId}/all")
//	public Page<CreditAppCollateralDetail> getAllCreditAppColDetails(@PathVariable(value = "tenantId", required = true) String tenantId,
//			@PageableDefault(size = 10) Pageable pageable) {
//		if (pageable.getPageSize() > Constants.MAX_PAGEABLE_LENGTH)
//			throw new PageableLengthException(environment.getProperty(pageableLength));
//		return creditAppCollateralDetailsService.findAll(pageable);
//	}

	@GetMapping(value = "/{tenantId}/all")
	public ResponseEntity<Object> getAllCreditAppColDetails(
			@PathVariable(value = "tenantId", required = true) String tenantId) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<CreditAppCollateralDetail> expenseType = creditAppCollateralDetailsService.findAll(tenantId);
		if (!expenseType.isEmpty()) {
			return new ResponseEntity<>(expenseType, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * Gets the CreditAppCollateralDetail by id.
	 *
	 * @param tenantId - the tenant id
	 * @param id       - the id
	 * @return the CreditAppCollateralDetail by id
	 */
	@GetMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> getCreditAppColDetailsById(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<CreditAppCollateralDetail> isPresentExpenseType = creditAppCollateralDetailsService.findById(id,
				tenantId);
		if (isPresentExpenseType.isPresent()) {
			return new ResponseEntity<>(isPresentExpenseType, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * Gets the CreditAppCollateralDetail by status.
	 *
	 * @param tenantId - the tenant id
	 * @param status   - the status
	 * @return the CreditAppCollateralDetail by status
	 */
	@GetMapping(value = "/{tenantId}/status/{status}")
	public ResponseEntity<Object> getCreditAppColDetailsByStatus(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "status", required = true) String status) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<CreditAppCollateralDetail> expenseType = creditAppCollateralDetailsService.findByStatus(status, tenantId);
		if (!expenseType.isEmpty()) {
			return new ResponseEntity<>(expenseType, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * Adds the CreditAppCollateralDetail.
	 *
	 * @param tenantId                             - the tenant id
	 * @param CreditAppraiselCollateralAddResource - the
	 *                                             CreditAppraiselCollateralAddResource
	 *                                             add resource
	 * @return the response entity
	 */
	@PostMapping(value = "/{tenantId}")
	public ResponseEntity<Object> addCreditAppCollateralDetail(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@Valid @RequestBody CreditAppraiselCollateralDetailsAddResource creditAppraiselCollateralDetailsAddResource) {
		if (LogginAuthentcation.getInstance().getUserName() == null
				|| LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new UserNotFound(environment.getProperty(USER_NOT_FOUND));
		if (creditAppCollateralDetailsService.saveAndValidateCreditAppCollateralDetails(tenantId,
				LogginAuthentcation.getInstance().getUserName(), creditAppraiselCollateralDetailsAddResource)) {
			SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(
					environment.getProperty(RECORD_CREATED));
			return new ResponseEntity<>(successDetailsDto, HttpStatus.CREATED);
		} else {
			SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(
					environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(successDetailsDto, HttpStatus.UNPROCESSABLE_ENTITY);
		}

//		CreditAppCollateralDetail creditAppCollateralDetail = creditAppCollateralDetailsService
//				.addCreditAppCollateralDetail(tenantId, creditAppraiselCollateralDetailsAddResource);
//		SuccessAndErrorDetailsResource successAndErrorDetails = new SuccessAndErrorDetailsResource(
//				environment.getProperty(RECORD_CREATED), Long.toString(creditAppCollateralDetail.getId()));
//		return new ResponseEntity<>(successAndErrorDetails, HttpStatus.CREATED);

	}

	/**
	 * Update CreditAppCollateralDetail.
	 *
	 * @param tenantId                  - the tenant id
	 * @param id                        - the id
	 * @param expenseTypeUpdateResource - the ApprovalCategoryProductMapping update
	 *                                  resource
	 * @return the response entity
	 */
	@PutMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> updateCreditAppCollateralDetail(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id,
			@Valid @RequestBody CreditAppraiselCollateralDetailsUpdateResource creditAppraiselCollateralDetailsUpdateResource) {
		if (LogginAuthentcation.getInstance().getUserName() == null
				|| LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new UserNotFound(environment.getProperty(USER_NOT_FOUND));
		if (creditAppCollateralDetailsService.existsById(id)) {
			Long getId = creditAppCollateralDetailsService.updateAndValidateCreditAppCollateralDetails(tenantId,
					LogginAuthentcation.getInstance().getUserName(), id,
					creditAppraiselCollateralDetailsUpdateResource);
			SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(
					environment.getProperty(RECORD_UPDATED), getId.toString());
			return new ResponseEntity<>(successDetailsDto, HttpStatus.OK);
		} else {
			SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(
					environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(successDetailsDto, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

	/**
	 * Gets the CreditAppCollateralDetail by customerId.
	 *
	 * @param tenantId - the tenant id
	 * @param status   - the status
	 * @return the CreditAppCollateralDetail by customerId
	 */
	@GetMapping(value = "/{tenantId}/customerId/{customerId}")
	public ResponseEntity<Object> getCreditAppColDetailsByCustId(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "customerId", required = true) Long customerId) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<CreditAppCollateralDetail> expenseType = creditAppCollateralDetailsService
				.getCreditAppCollateralDetails(customerId, tenantId);
		if (!expenseType.isEmpty()) {
			return new ResponseEntity<>(expenseType, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

}