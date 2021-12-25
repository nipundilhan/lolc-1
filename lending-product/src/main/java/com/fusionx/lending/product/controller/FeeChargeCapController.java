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
import com.fusionx.lending.product.domain.FeeChargeCap;
import com.fusionx.lending.product.domain.FeeChargeCapPending;
import com.fusionx.lending.product.enums.WorkflowStatus;
import com.fusionx.lending.product.exception.PageableLengthException;
import com.fusionx.lending.product.exception.UserNotFound;
import com.fusionx.lending.product.resources.FeeChargeCapAddResource;
import com.fusionx.lending.product.resources.FeeChargeCapUpdateResource;
import com.fusionx.lending.product.resources.SuccessAndErrorDetails;
import com.fusionx.lending.product.resources.SuccessAndErrorDetailsResource;
import com.fusionx.lending.product.service.FeeChargeCapService;

/**
 * The Class FeeChargeCapController.
 */
@RestController
@RequestMapping(value = "/fee-charge-cap")
@CrossOrigin(origins = "*")
public class FeeChargeCapController {
	
	/** The environment. */
	@Autowired
	Environment environment;
	
	/** The fee charge cap service. */
	@Autowired
	public FeeChargeCapService feeChargeCapService;
	
	
	/** The Constant RECORD_NOT_FOUND. */
	private static final String RECORD_NOT_FOUND = "Records not available";
	
	/** The user not found. */
	private String userNotFound="common.user-not-found";
	
	/** The pageable length. */
	private String pageableLength = "common.pageable-length";
	
	/**
	 * get all Fee Charge Cap .
	 *
	 * @param tenantId the tenant id
	 * @return List<Type>
	 */
    @GetMapping(value = "/{tenantId}/all")
    public ResponseEntity<Object> getAllFeeCharge(@PathVariable(value = "tenantId", required = true) String tenantId) {
    	SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
		List<FeeChargeCap> feeChargeCap = feeChargeCapService.getAll();
		int size = feeChargeCap.size();
		if (size > 0) {
			return new ResponseEntity<>((Collection<FeeChargeCap>) feeChargeCap,HttpStatus.OK);
		} 
		else {
			responseMessage.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
    
    /**
     * get Fee Charge Cap by id.
     *
     * @param tenantId the tenant id
     * @param id the id
     * @return Option
     */
 	@GetMapping(value = "/{tenantId}/{id}")
 	public ResponseEntity<Object> getFeeChargeById(@PathVariable(value = "tenantId", required = true) String tenantId, 
 			                                              @PathVariable(value = "id", required = true) Long id) {
 		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
	 	Optional<FeeChargeCap> isPresentFeeChargeCap = feeChargeCapService.getById(id);
	 	if (isPresentFeeChargeCap.isPresent()) {
	 		return new ResponseEntity<>(isPresentFeeChargeCap.get(), HttpStatus.OK);
	 	} 
	 	else {
	 		responseMessage.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
	 	}
 	}
 	
	/**
	 * get Fee Charge Cap by code.
	 *
	 * @param tenantId the tenant id
	 * @param code the code
	 * @return Optional
	 */
	@GetMapping(value = "/{tenantId}/code/{code}")
	public ResponseEntity<Object> getFeeChargeByCode(@PathVariable(value = "tenantId", required = true) String tenantId,
													   	            @PathVariable(value = "code", required = true) String code){
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<FeeChargeCap> isPresentFeeChargeCap = feeChargeCapService.getByCode(code);
		if(isPresentFeeChargeCap.isPresent()) {
			return new ResponseEntity<>(isPresentFeeChargeCap.get(), HttpStatus.OK);
		}
		else {
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
 	/**
	  * get Fee Charge Cap  by status.
	  *
	  * @param tenantId the tenant id
	  * @param status the status
	  * @return Option
	  */
 	@GetMapping(value = "/{tenantId}/status/{status}")
 	public ResponseEntity<Object> getFeeChargeByStatus(@PathVariable(value = "tenantId", required = true) String tenantId, 
 			                                                  @PathVariable(value = "status", required = true) String status) {
 		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
 		
 		if(status.equals("ACTIVE") || status.equals("INACTIVE")){
		 	List<FeeChargeCap> isPresentFeeChargeCap = feeChargeCapService.getByStatus(status);
		 	int size = isPresentFeeChargeCap.size();
		 	if (size>0) {
		 		return new ResponseEntity<>(isPresentFeeChargeCap, HttpStatus.OK);
		 	} 
		 	else {
		 		responseMessage.setMessages(RECORD_NOT_FOUND);
				return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		 	}
 		}
 		else{
	 		responseMessage.setMessages(environment.getProperty("invalid-status"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
	 	}
 	}
 	
	
	/**
	 * save Fee Charge Cap .
	 *
	 * @param tenantId the tenant id
	 * @param feeChargeCapAddResource the fee charge cap add resource
	 * @return SuccessAndErrorDetailsDto
	 */
 	@PostMapping(value = "/{tenantId}")
 	public ResponseEntity<Object> addFeeCharge(@PathVariable(value = "tenantId", required = true) String tenantId,
 			                                          @Valid @RequestBody FeeChargeCapAddResource feeChargeCapAddResource) {
 		if(LogginAuthentcation.getInstance().getUserName()==null || LogginAuthentcation.getInstance().getUserName().isEmpty()) { 
 			throw new UserNotFound(environment.getProperty(userNotFound));
 		}
		
 		SuccessAndErrorDetails successAndErrorDetails = new SuccessAndErrorDetails();
 		FeeChargeCap addFeeChargeCap = feeChargeCapService.addFeeChargeCap(tenantId, feeChargeCapAddResource);
	 	successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty("rec.saved"),Long.toString(addFeeChargeCap.getId()));
		return new ResponseEntity<>(successAndErrorDetails,HttpStatus.CREATED);
 	}
 	
 	/**
	  * update Fee Charge Cap .
	  *
	  * @param tenantId the tenant id
	  * @param id the id
	  * @param feeChargeCapAddResource the fee charge cap add resource
	  * @return SuccessAndErrorDetailsDto
	  */
	@PutMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> updateFeeCharge(@PathVariable(value = "tenantId", required = true) String tenantId, 
			                                             @PathVariable(value = "id", required = true) Long id, 
			                                             @Valid @RequestBody FeeChargeCapUpdateResource feeChargeCapAddResource) {
		if(LogginAuthentcation.getInstance().getUserName()==null || LogginAuthentcation.getInstance().getUserName().isEmpty()) { 
 			throw new UserNotFound(environment.getProperty(userNotFound));
 		}
		
		SuccessAndErrorDetails successAndErrorDetails=new SuccessAndErrorDetails();
		Optional<FeeChargeCap> isPresentFeeChargeCap = feeChargeCapService.getById(id);
		if(isPresentFeeChargeCap.isPresent()) {
		
			FeeChargeCap feeChargeCap = feeChargeCapService.updateFeeChargeCap(tenantId, id, feeChargeCapAddResource);
			successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty("rec.updated"), feeChargeCap.getId().toString());
	        return new ResponseEntity<>(successAndErrorDetails,HttpStatus.OK);
		}
		else {
			successAndErrorDetails.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(successAndErrorDetails, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
	
	/**
	 * approve FeeCharge  Pending.
	 *
	 * @param tenantId the tenant id
	 * @param pendingId the pending id
	 * @return SuccessAndErrorDetailsDto
	 */
	@PutMapping(value = "{tenantId}/approve/{pendingId}")
	public ResponseEntity<Object> approve(@PathVariable(value = "tenantId", required = true) String tenantId, @PathVariable(value = "pendingId", required = true) Long pendingId){
		SuccessAndErrorDetailsResource successAndErrorDetailsResource=new SuccessAndErrorDetailsResource();
		Optional<FeeChargeCapPending> isPresentFeeChargeCapPending = feeChargeCapService.getByPendingId(pendingId);
		if(isPresentFeeChargeCapPending.isPresent()) {
	
			if(feeChargeCapService.approveReject(tenantId, pendingId, WorkflowStatus.COMPLETE)) {
			successAndErrorDetailsResource = new SuccessAndErrorDetailsResource(environment.getProperty("common.approved"), pendingId.toString());
			return new ResponseEntity<>(successAndErrorDetailsResource,HttpStatus.OK);
			} else {
				successAndErrorDetailsResource.setMessages(environment.getProperty("common.can-not-approved"));
				return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.UNPROCESSABLE_ENTITY);
			}

		} else {
			successAndErrorDetailsResource.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
	
	/**
	 * Reject FeeCharge  Pending.
	 *
	 * @param tenantId the tenant id
	 * @param pendingId the pending id
	 * @return SuccessAndErrorDetailsDto
	 */
	@PutMapping(value = "{tenantId}/reject/{pendingId}")
	public ResponseEntity<Object> reject(@PathVariable(value = "tenantId", required = true) String tenantId, @PathVariable(value = "pendingId", required = true) Long pendingId){
		SuccessAndErrorDetailsResource successAndErrorDetailsResource=new SuccessAndErrorDetailsResource();
		Optional<FeeChargeCapPending> isPresentFeeChargeCapPending = feeChargeCapService.getByPendingId(pendingId);
		if(isPresentFeeChargeCapPending.isPresent()) {
	
			if(feeChargeCapService.approveReject(tenantId, pendingId, WorkflowStatus.REJECT)) {
			successAndErrorDetailsResource = new SuccessAndErrorDetailsResource(environment.getProperty("common.rejected"), pendingId.toString());
			return new ResponseEntity<>(successAndErrorDetailsResource,HttpStatus.OK);
			} else {
				successAndErrorDetailsResource.setMessages(environment.getProperty("common.can-not-rejected"));
				return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.UNPROCESSABLE_ENTITY);
			}

		} else {
			successAndErrorDetailsResource.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

	
	/**
	 * Search FeeCharge  Pending.
	 *
	 * @param tenantId - the tenant id
	 * @param searchq - the searchq
	 * @param status - the status
	 * @param approveStatus - the approve status
	 * @param pageable - the pageable
	 * @return the page
	 */
	@GetMapping(value = "/{tenantId}/workflow-items/search")
	public Page<FeeChargeCapPending> searchFeeChargeCapPending(@PathVariable(value = "tenantId", required = true) String tenantId,
			@RequestParam(value = "searchq", required = false) String searchq,
			@RequestParam(value = "status", required = false) String status,
			@RequestParam(value = "approveStatus", required = false) String approveStatus,
			@PageableDefault(size = 10) Pageable pageable) {
		if(pageable.getPageSize()>Constants.MAX_PAGEABLE_LENGTH) 
			throw new PageableLengthException(environment.getProperty(pageableLength));
		return feeChargeCapService.searchFeeChargeCap(searchq, status, approveStatus, pageable);
	}
	
 	/**
	  * Gets the fee charge by pending id.
	  *
	  * @param tenantId the tenant id
	  * @param id the id
	  * @return the fee charge by pending id
	  */
	@GetMapping(value = "/{tenantId}/pending-id/{id}")
 	public ResponseEntity<Object> getFeeChargeByPendingId(@PathVariable(value = "tenantId", required = true) String tenantId, 
 			                                              @PathVariable(value = "id", required = true) Long id) {
 		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
	 	Optional<FeeChargeCapPending> feeChargeCapPending = feeChargeCapService.getByPendingId(id);
	 	if (feeChargeCapPending.isPresent()) {
	 		return new ResponseEntity<>(feeChargeCapPending.get(), HttpStatus.OK);
	 	} 
	 	else {
	 		responseMessage.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
	 	}
 	}
	
	
	 /**
 	 * Gets the fee charge caps by other fee type.
 	 *
 	 * @param tenantId the tenant id
 	 * @param name the name
 	 * @return the by other fee type
 	 */
 	@GetMapping(value = "/{tenantId}/fee-type/{name}")
	 public ResponseEntity<Object> getByOtherFeeType(@PathVariable(value = "tenantId", required = true) String tenantId, 
			 @PathVariable(value = "name", required = true) String name) {
		 SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
		 List<FeeChargeCap> feeChargeCap = feeChargeCapService.getByOtherFeeType(name);
		 int size = feeChargeCap.size();
			if (size > 0) {
				return new ResponseEntity<>((Collection<FeeChargeCap>) feeChargeCap,HttpStatus.OK);
			} 
			else {
				responseMessage.setMessages(RECORD_NOT_FOUND);
				return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
			}
	 }
 	
 	@GetMapping(value = "/{tenantId}/fee-charge/{name}")
 	public ResponseEntity<Object> getByFeeCharge(@PathVariable(value = "tenantId", required = true) String tenantId, 
 			@PathVariable(value = "name", required = true) String name) {
 		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
 		List<FeeChargeCap> feeChargeCap = feeChargeCapService.getByFeeCharge(name);
 		int size = feeChargeCap.size();
 		if (size > 0) {
 			return new ResponseEntity<>((Collection<FeeChargeCap>) feeChargeCap,HttpStatus.OK);
 		} 
 		else {
 			responseMessage.setMessages(RECORD_NOT_FOUND);
 			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
 		}
 	}
	
}
