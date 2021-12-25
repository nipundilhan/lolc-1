package com.fusionx.lending.product.controller;

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

import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.FeeCharge;
import com.fusionx.lending.product.domain.FeeChargeDetailAdhoc;
import com.fusionx.lending.product.domain.FeeChargeDetailAdhocPending;
import com.fusionx.lending.product.domain.FeeChargeDetailOptional;
import com.fusionx.lending.product.domain.FeeChargePending;
import com.fusionx.lending.product.exception.UserNotFound;
import com.fusionx.lending.product.resources.FeeChargeDetailAdhocAddResource;
import com.fusionx.lending.product.resources.FeeChargeDetailAdhocUpdateResource;
import com.fusionx.lending.product.resources.FeeChargeDetailOptionalAddResource;
import com.fusionx.lending.product.resources.FeeChargeDetailOptionalUpdateResource;
import com.fusionx.lending.product.resources.SuccessAndErrorDetails;
import com.fusionx.lending.product.service.FeeChargeDetailAdhocService;

/**
 * API Controller related to Fee Charge Details Optional options.
 *
 * @author Nipun Dilhan
 * @version 1.0.0
 * @since 1.0.0
 * <p>
 * <br/><br/>
 * <b>Change History : </b>
 * <br/><br/>
 * #        Date            Story Point     Task No     Author                  Description
 * <br/>
 * .....................................................................................................................<br/>
 * 1        18-08-2021      -               -           Nipun Dilhan      Created
 * <p>
 *
 */

@RestController
@RequestMapping(value = "/fee-charge-detail-adhoc")
@CrossOrigin(origins = "*")
public class FeeChargeDetailAdhocController    extends MessagePropertyBase {
	

	public FeeChargeDetailAdhocService feeChargeDetailAdhocService;
	
    @Autowired
    public void setFeeChargeDetailOptionalService(FeeChargeDetailAdhocService feeChargeDetailAdhocService) {
        this.feeChargeDetailAdhocService = feeChargeDetailAdhocService;
    }
    
    /**
     * Get  all fee charge optional
     *
     * @param tenantId the id of tenant
     * @return list of FeeChargeDetailAdhoc if record exists, otherwise <code>204</code>
     */
	@GetMapping(value = "/{tenantId}/all")
	public ResponseEntity<Object> findAll(
			@PathVariable(value = "tenantId", required = true) String tenantId) {
		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();

		List<FeeChargeDetailAdhoc> allList = feeChargeDetailAdhocService.findAll();
		
		if (!allList.isEmpty()) {
			return new ResponseEntity<>(allList, HttpStatus.OK);
		} else {
			responseMessage.setMessages("record not found");
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

    
    /**
     * Saves the Fee Charge Details Optional Options
     *
     * @param tenantId                  the id of the tenant
     * @param feeChargeDetailOptionalAddResource the object to save
     * @return message if record successfully saved.
     * @see FeeChargeDetailOptionalAddResource
     */
 	@PostMapping(value = "/{tenantId}")
 	public ResponseEntity<Object> addFeeChargeDetailsAdhoc(@PathVariable(value = "tenantId", required = true) String tenantId,
 			                                          @Valid @RequestBody FeeChargeDetailAdhocAddResource feeChargeDetailAdhocAddResource) {
 		if(LogginAuthentcation.getInstance().getUserName()==null || LogginAuthentcation.getInstance().getUserName().isEmpty()) { 
 			throw new UserNotFound(environment.getProperty("common.user-not-found"));
 		}	
 		 
 		FeeChargeDetailAdhoc feeChargeDetailAdhoc = feeChargeDetailAdhocService.create(tenantId,feeChargeDetailAdhocAddResource);
 		SuccessAndErrorDetails successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty("rec.saved"),Long.toString(feeChargeDetailAdhoc.getId()));
		return new ResponseEntity<>(successAndErrorDetails,HttpStatus.CREATED);
 	}
 	
 	
 	
    /**
     * Updates the given Fee Charge Details Optiona options.
     *
     * @param tenantId                     the id of the tenant
     * @param id                           the id of the fee charge
     * @param feeChargeDetailOptionalUpdateResource the object which contains data
     * @return the message
     * @see FeeChargeDetailOptionalUpdateResource
     */
	@PutMapping(value = "/{tenantId}/fee-charge/{id}")
	public ResponseEntity<Object> updateFeeChargeDetailsOptional(@PathVariable(value = "tenantId", required = true) String tenantId, 
			                                             @PathVariable(value = "id", required = true) Long id, 
			                                             @Valid @RequestBody FeeChargeDetailAdhocUpdateResource feeChargeDetailAdhocUpdateResource) {
		if(LogginAuthentcation.getInstance().getUserName()==null || LogginAuthentcation.getInstance().getUserName().isEmpty()) { 
 			throw new UserNotFound(environment.getProperty("common.user-not-found"));
 		}
		
		FeeChargePending feeChargePending = feeChargeDetailAdhocService.updateFeeChargeDetailAdhoc(tenantId, id, feeChargeDetailAdhocUpdateResource);
		
		SuccessAndErrorDetails successAndErrorDetails=new SuccessAndErrorDetails();
		if(feeChargePending!= null) {
			successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty("rec.updated"), feeChargePending.getId().toString());
	        return new ResponseEntity<>(successAndErrorDetails,HttpStatus.OK);
		}
		else {
			successAndErrorDetails.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(successAndErrorDetails, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
	
    /**
     * get FeeCharge  by id
     * @param @PathVariable{tenantId}
     * @param @PathVariable{id}
     * @return Option
     */
 	@GetMapping(value = "/{tenantId}/{id}")
 	public ResponseEntity<Object> getById(@PathVariable(value = "tenantId", required = true) String tenantId, 
 			                                              @PathVariable(value = "id", required = true) Long id) {

 		FeeChargeDetailAdhoc feeChargeDetailAdhoc = feeChargeDetailAdhocService.getFeeChargeDetailAdhocDetailsById(id);
	 	if (feeChargeDetailAdhoc !=null) {
	 		return new ResponseEntity<>(feeChargeDetailAdhoc, HttpStatus.OK);
	 	} 
	 	else {
	 		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
	 		responseMessage.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
	 	}
 	}
 	
 	@GetMapping(value = "/{tenantId}/fee-charge/{id}")
 	public ResponseEntity<Object> getAdhocListByFeeChargeId(@PathVariable(value = "tenantId", required = true) String tenantId, 
 			                                              @PathVariable(value = "id", required = true) Long id) {

 		List<FeeChargeDetailAdhoc> feeChargeDetailAdhocList = feeChargeDetailAdhocService.getFeeChargeDetailAdhocByFeeChargeId(id);
	 	if ((feeChargeDetailAdhocList !=null) && (!feeChargeDetailAdhocList.isEmpty())) {
	 		return new ResponseEntity<>(feeChargeDetailAdhocList, HttpStatus.OK);
	 	} 
	 	else {
	 		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
	 		responseMessage.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
	 	}
 	}
 	
 	@GetMapping(value = "/{tenantId}/fee-charge-pending/{id}")
 	public ResponseEntity<Object> getPendingAdhocListByFeeChargePendingId(@PathVariable(value = "tenantId", required = true) String tenantId, 
 			                                              @PathVariable(value = "id", required = true) Long id) {

 		List<FeeChargeDetailAdhocPending> pendingFeeChargeAdhocList = feeChargeDetailAdhocService.getPendingAdhocListByFeeChargePendingId(id);
	 	
 		if ((pendingFeeChargeAdhocList !=null) && (!pendingFeeChargeAdhocList.isEmpty())) {
	 		return new ResponseEntity<>(pendingFeeChargeAdhocList, HttpStatus.OK);
	 	} 
	 	else {
	 		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
	 		responseMessage.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
	 	}
 	}
 	
    /**
     * Get FeeChargeDetailOptional by status
     *
     * @param tenantId the id of tenant
     * @param status   the status
     * @return the list of all FeeChargeDetailOptional if record exists, otherwise <code>204 - No Content</code>
     */
	@GetMapping(value = "/{tenantId}/status/{status}")
	public ResponseEntity<Object> findByStatus(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "status", required = true) String status) {

		List<FeeChargeDetailAdhoc> findByStatusList = feeChargeDetailAdhocService.findByStatus(status);
		
		if (!findByStatusList.isEmpty()) {
			return new ResponseEntity<>(findByStatusList, HttpStatus.OK);
		} else {
			SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
			responseMessage.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
    /**
     * Get FeeChargeDetailOptional by  category id
     *
     * @param tenantId the id of tenant
     * @param id       the id of the fee charge category
     * @return list of FeeChargeDetailOptional if record exists, otherwise <code>204</code>
     */
	@GetMapping(value = "/{tenantId}/category/{code}")
	public ResponseEntity<Object> findByCategory(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "code", required = true) String code) {


		List<FeeChargeDetailAdhoc> feeChargeDetailAdhoclList = feeChargeDetailAdhocService.findByCategory(code);
		
		if ((feeChargeDetailAdhoclList!=null) && (!feeChargeDetailAdhoclList.isEmpty())) {
			return new ResponseEntity<>(feeChargeDetailAdhoclList, HttpStatus.OK);
		} else {
			SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
			responseMessage.setMessages("record not found");
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
    /**
     * Get FeeChargeDetailOptional by  category id
     *
     * @param tenantId the id of tenant
     * @param id       the id of the fee charge category
     * @return list of FeeChargeDetailOptional if record exists, otherwise <code>204</code>
     */
	@GetMapping(value = "/{tenantId}/fee-type/{code}")
	public ResponseEntity<Object> findByFeeType(
			@PathVariable(value = "tenantId") String tenantId,
			@PathVariable(value = "code") String code) {
		

		List<FeeChargeDetailAdhoc> feeChargeDetailAdhoclList = feeChargeDetailAdhocService.findByFeeTypeCode(code);
		
		if ((feeChargeDetailAdhoclList!=null) && (!feeChargeDetailAdhoclList.isEmpty())) {
			return new ResponseEntity<>(feeChargeDetailAdhoclList, HttpStatus.OK);
		} else {
			SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
			responseMessage.setMessages("record not found");
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	 	
	
	
	
	
	@PutMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> updateDirectFeeChargeDetailAdhoc(@PathVariable(value = "tenantId", required = true) String tenantId, 
			                                             @PathVariable(value = "id", required = true) Long id, 
			                                             @Valid @RequestBody FeeChargeDetailAdhocUpdateResource feeChargeDetailAdhocUpdateResource) {
		if(LogginAuthentcation.getInstance().getUserName()==null || LogginAuthentcation.getInstance().getUserName().isEmpty()) { 
 			throw new UserNotFound(environment.getProperty("common.user-not-found"));
 		}
		
		FeeChargeDetailAdhoc feeChargeDetailAdhoc = feeChargeDetailAdhocService.directUpdateFeeChargeDetailAdhoc(id,feeChargeDetailAdhocUpdateResource);
		
		SuccessAndErrorDetails successAndErrorDetails=new SuccessAndErrorDetails();
		if(feeChargeDetailAdhoc!= null) {
			successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty("rec.updated"), feeChargeDetailAdhoc.getId().toString());
	        return new ResponseEntity<>(successAndErrorDetails,HttpStatus.OK);
		}
		else {
			successAndErrorDetails.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(successAndErrorDetails, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
	
	
	@PutMapping(value = "/{tenantId}/temp-approve/fee-charge-pending/{id}")
	public ResponseEntity<Object> tempApprove(@PathVariable(value = "tenantId", required = true) String tenantId, 
			                                             @PathVariable(value = "id", required = true) Long id) {

		feeChargeDetailAdhocService.approvePendingAdhoc(id);

		SuccessAndErrorDetails successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty("rec.updated"), null);
	    return new ResponseEntity<>(successAndErrorDetails,HttpStatus.OK);
		
	}
	
	

}
