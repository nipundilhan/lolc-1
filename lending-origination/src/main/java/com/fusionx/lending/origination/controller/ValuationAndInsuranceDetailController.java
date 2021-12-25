package com.fusionx.lending.origination.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
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

import com.fusionx.lending.origination.core.LogginAuthentcation;
import com.fusionx.lending.origination.domain.BankStatementDetails;
import com.fusionx.lending.origination.exception.UserNotFound;
import com.fusionx.lending.origination.resource.BankStatementDetailsAddResource;
import com.fusionx.lending.origination.resource.SuccessAndErrorDetailsResource;
import com.fusionx.lending.origination.resource.ValuationAndInsuranceDetListResponseResource;
import com.fusionx.lending.origination.resource.ValuationAndInsuranceDetailsAddResource;
import com.fusionx.lending.origination.resource.ValuationAndInsuranceDetailsUpdateResource;
import com.fusionx.lending.origination.service.ValuationAndInsuranceDetailsService;


/**
 * Valuation And InsuranceDetail Controller
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   20-04-2021      		     			VenukiR      Created
 *    
 ********************************************************************************************************
 */

@RestController
@RequestMapping(value = "/valuation-and-insurance")
@CrossOrigin(origins = "*")
public class ValuationAndInsuranceDetailController {

	@Autowired
	private Environment environment;
	
	@Autowired
	private ValuationAndInsuranceDetailsService valuationAndInsuranceDetailsService;
	
	private String userNotFound = "common.user-not-found";
	private String commonSaved = "common.saved";
	private String commonUpdated = "common.updated";
	private String pageableLength = "common.pageable-length";
	private String recordNotFound = "common.record-not-found";
	
	/**
	 * Gets the ValuationAndInsuranceDetails by id.
	 *
	 * @param tenantId - the tenant id
	 * @param id - the id
	 * @return the ValuationAndInsuranceDetails by id
	 */
	@GetMapping(value = "/{tenantId}/customerId/{customerid}/assetEntity/{assetEntityId}")
	public ResponseEntity<Object> getCustValuationDetailsByEntityId(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "assetEntityId", required = true) Long assetEntityId,
			@PathVariable(value = "customerid", required = true) Long customerid) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		ValuationAndInsuranceDetListResponseResource isPresentValuationInsuDetails = valuationAndInsuranceDetailsService.findValuationAndInsuranceDetails(tenantId,customerid, assetEntityId);
		if(isPresentValuationInsuDetails != null) {
			return new ResponseEntity<>(isPresentValuationInsuDetails,HttpStatus.OK);
		} 	else {
			responseMessage.setMessages(recordNotFound);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
		
	}
	
	
	/**
	 * Adds the ValuationAndInsuranceDetails.
	 *
	 * @param tenantId - the tenant id
	 * @param ValuationAndInsuranceDetailsAddResource - the ValuationAndInsuranceDetails add resource
	 * @return the response entity
	 */
	@PostMapping(value = "/{tenantId}")
	public ResponseEntity<Object> addValuationAndInsuranceDetails(@PathVariable(value = "tenantId", required = true) String tenantId,
			@Valid @RequestBody ValuationAndInsuranceDetailsAddResource valuationAndInsuranceDetailsAddResource) {
		if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new UserNotFound(environment.getProperty(userNotFound));
		valuationAndInsuranceDetailsService.saveValuationAndInsuranceDetails(tenantId, LogginAuthentcation.getInstance().getUserName(), valuationAndInsuranceDetailsAddResource);
		SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty(commonSaved));
		return new ResponseEntity<>(successDetailsDto,HttpStatus.CREATED);
	}
	
//	@PostMapping(value = "/{tenantId}")
//	public ResponseEntity<Object> addBankStatementDetails(
//			@PathVariable(value = "tenantId", required = true) String tenantId,
//			@Valid @RequestBody ValuationAndInsuranceDetailsAddResource valuationAndInsuranceDetailsAddResource) {
//		if (LogginAuthentcation.getInstance().getUserName() == null
//				|| LogginAuthentcation.getInstance().getUserName().isEmpty()) {
//			throw new UserNotFound(environment.getProperty(userNotFound));
//		}
//
//		SuccessAndErrorDetailsResource successAndErrorDetails = new SuccessAndErrorDetailsResource();
//		valuationAndInsuranceDetailsService.saveValuationAndInsuranceDetails(tenantId, LogginAuthentcation.getInstance().getUserName(), valuationAndInsuranceDetailsAddResource);
//		successAndErrorDetails = new SuccessAndErrorDetailsResource(environment.getProperty(commonSaved));
//		return new ResponseEntity<>(successAndErrorDetails, HttpStatus.CREATED);
//	}
	
	/**
	 * Update ValuationAndInsuranceDetails.
	 *
	 * @param tenantId - the tenant id
	 * @param id - the id
	 * @param ApprovalCategoryUpdateResource - the ApprovalCategory update resource
	 * @return the response entity
	 */
	@PutMapping(value = "/{tenantId}/valuation/{id}")
	public ResponseEntity<Object> updateValuationDetails(@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id,
			@Valid @RequestBody ValuationAndInsuranceDetailsUpdateResource valuationAndInsuranceDetailsUpdateResource) {
		if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new UserNotFound(environment.getProperty(userNotFound));
		if(valuationAndInsuranceDetailsUpdateResource.getValuationDetails()!=null) {
			valuationAndInsuranceDetailsService.updateValuationAndInsuranceDetails(tenantId, LogginAuthentcation.getInstance().getUserName(),id, valuationAndInsuranceDetailsUpdateResource);
			SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty(commonUpdated));
			return new ResponseEntity<>(successDetailsDto, HttpStatus.OK);
			
		} else {
			SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(successDetailsDto, HttpStatus.UNPROCESSABLE_ENTITY);
		}

	}
	
//	/**
//	 * Update ValuationAndInsuranceDetails.
//	 *
//	 * @param tenantId - the tenant id
//	 * @param id - the id
//	 * @param ApprovalCategoryUpdateResource - the ApprovalCategory update resource
//	 * @return the response entity
//	 */
//	@PutMapping(value = "/{tenantId}/insurance/{id}")
//	public ResponseEntity<Object> updateInsuranceDetails(@PathVariable(value = "tenantId", required = true) String tenantId,
//			@PathVariable(value = "id", required = true) Long id,
//			@Valid @RequestBody ValuationAndInsuranceDetailsUpdateResource valuationAndInsuranceDetailsUpdateResource) {
//		if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
//			throw new UserNotFound(environment.getProperty(userNotFound));
//		if(valuationAndInsuranceDetailsUpdateResource.getInsuranceDetails()!=null) {
//			
//			valuationAndInsuranceDetailsService.updateValuationAndInsuranceDetails(tenantId, LogginAuthentcation.getInstance().getUserName(),id, valuationAndInsuranceDetailsUpdateResource);
//			SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty(commonUpdated));
//			return new ResponseEntity<>(successDetailsDto, HttpStatus.OK);
//		} else {
//			SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty(recordNotFound));
//			return new ResponseEntity<>(successDetailsDto, HttpStatus.UNPROCESSABLE_ENTITY);
//		}
//	}
}