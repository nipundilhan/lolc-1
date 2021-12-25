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
import com.fusionx.lending.product.domain.FeatureBenefitTemplateItem;
import com.fusionx.lending.product.domain.FeeChargeDetailOptional;
import com.fusionx.lending.product.domain.FeeChargeDetailOptionalPending;
import com.fusionx.lending.product.domain.FeeChargeDetails;
import com.fusionx.lending.product.domain.FeeChargePending;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.exception.UserNotFound;
import com.fusionx.lending.product.repository.FeeChargeDetailOptionalRepository;
import com.fusionx.lending.product.resources.AgeEligibilityAddResource;
import com.fusionx.lending.product.resources.AgeEligibilityUpdateResource;
import com.fusionx.lending.product.resources.FeeChargeDetailOptionalAddResource;
import com.fusionx.lending.product.resources.FeeChargeDetailOptionalUpdateResource;
import com.fusionx.lending.product.resources.FeeChargeDetailsAddResource;
import com.fusionx.lending.product.resources.FeeChargeDetailsUpdateResource;
import com.fusionx.lending.product.resources.SuccessAndErrorDetails;
import com.fusionx.lending.product.service.AgeEligibilityService;
import com.fusionx.lending.product.service.FeeChargeDetailOptionalService;


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
@RequestMapping(value = "/fee-charge-detail-optional")
@CrossOrigin(origins = "*")
public class FeeChargeDetailOptionalController   extends MessagePropertyBase {
	

	public FeeChargeDetailOptionalService feeChargeDetailOptionalService;
	
    @Autowired
    public void setFeeChargeDetailOptionalService(FeeChargeDetailOptionalService feeChargeDetailOptionalService) {
        this.feeChargeDetailOptionalService = feeChargeDetailOptionalService;
    }
	
	@Autowired
	private FeeChargeDetailOptionalRepository feeChargeDetailOptionalRepository;

	
	
    /**
     * Saves the Fee Charge Details Optional Options
     *
     * @param tenantId                  the id of the tenant
     * @param feeChargeDetailOptionalAddResource the object to save
     * @return message if record successfully saved.
     * @see FeeChargeDetailOptionalAddResource
     */
 	@PostMapping(value = "/{tenantId}")
 	public ResponseEntity<Object> addFeeChargeDetailsOptional(@PathVariable(value = "tenantId", required = true) String tenantId,
 			                                          @Valid @RequestBody FeeChargeDetailOptionalAddResource feeChargeDetailOptionalAddResource) {
 		if(LogginAuthentcation.getInstance().getUserName()==null || LogginAuthentcation.getInstance().getUserName().isEmpty()) { 
 			throw new UserNotFound(environment.getProperty("common.user-not-found"));
 		}
		
 		SuccessAndErrorDetails successAndErrorDetails = new SuccessAndErrorDetails();
 		FeeChargeDetailOptional addFeeChargeDetailOptional = feeChargeDetailOptionalService.addFeeChargeDetailOptional(tenantId, feeChargeDetailOptionalAddResource);
	 	successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty("rec.saved"),Long.toString(addFeeChargeDetailOptional.getId()));
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
			                                             @Valid @RequestBody FeeChargeDetailOptionalUpdateResource feeChargeDetailOptionalUpdateResource) {
		if(LogginAuthentcation.getInstance().getUserName()==null || LogginAuthentcation.getInstance().getUserName().isEmpty()) { 
 			throw new UserNotFound(environment.getProperty("common.user-not-found"));
 		}
		
		FeeChargePending feeChargePending = feeChargeDetailOptionalService.updateFeeChargeDetailsOptional(tenantId, id, feeChargeDetailOptionalUpdateResource);
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
     * Get  by fee charge id
     *
     * @param tenantId the id of tenant
     * @param id       the fee charge id
     * @return list of FeeChargeDetailOptional if record exists, otherwise <code>204</code>
     */
	@GetMapping(value = "/{tenantId}/fee-charge/{id}")
	public ResponseEntity<Object> findByFeeChargeId(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id) {
		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();

		List<FeeChargeDetailOptional> allFeeChargeOptionalList = feeChargeDetailOptionalService.getAllFeeChargeOptionalByFeeChargeId(id);
		
		if (!allFeeChargeOptionalList.isEmpty()) {
			return new ResponseEntity<>(allFeeChargeOptionalList, HttpStatus.OK);
		} else {
			responseMessage.setMessages("record not found");
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
    /**
     * Get  all fee charge optional
     *
     * @param tenantId the id of tenant
     * @return list of FeeChargeDetailOptional if record exists, otherwise <code>204</code>
     */
	@GetMapping(value = "/{tenantId}/all")
	public ResponseEntity<Object> findAll(
			@PathVariable(value = "tenantId", required = true) String tenantId) {
		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();

		List<FeeChargeDetailOptional> allList = feeChargeDetailOptionalService.findAll();
		
		if (!allList.isEmpty()) {
			return new ResponseEntity<>(allList, HttpStatus.OK);
		} else {
			responseMessage.setMessages("record not found");
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
    /**
     * Get  all fee charge optional by other fee type id
     *
     * @param tenantId the id of tenant
     * @param id       the fee type id
     * @return list of FeeChargeDetailOptional if record exists, otherwise <code>204</code>
     */
	@GetMapping(value = "/{tenantId}/fee-type/{id}")
	public ResponseEntity<Object> findByFeeTypeId(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id) {
		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();

		List<FeeChargeDetailOptional> allFeeChargeOptionalList = feeChargeDetailOptionalService.findByOtherFeeTypeId(id);
		
		if (!allFeeChargeOptionalList.isEmpty()) {
			return new ResponseEntity<>(allFeeChargeOptionalList, HttpStatus.OK);
		} else {
			responseMessage.setMessages("record not found");
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
    /**
     * Get FeeChargeDetailOptional by id
     *
     * @param tenantId the id of tenant
     * @param id       the id of the record
     * @return FeeChargeDetailOptional if record exists, otherwise <code>204</code>
     */
	@GetMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> getDetailsId(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id) {
		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();

		FeeChargeDetailOptional feeChargeDetailOptional = feeChargeDetailOptionalService.getDetailsById(id);
		
		if (feeChargeDetailOptional != null) {
			return new ResponseEntity<>(feeChargeDetailOptional, HttpStatus.OK);
		} else {
			responseMessage.setMessages("record not found");
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

    /**
     * Get FeeChargeDetailOptionalPending by  fee charge pending id
     *
     * @param tenantId the id of tenant
     * @param id       the id of the record
     * @return list of FeeChargeDetailOptionalPending if record exists, otherwise <code>204</code>
     */
	@GetMapping(value = "/{tenantId}/fee-charge-pending/{id}")
	public ResponseEntity<Object> getDetailsByFeeChargePendingId(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id) {
		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();

		List<FeeChargeDetailOptionalPending> feeChargeDetailOptionalPendingList = feeChargeDetailOptionalService.getAllDetailsByFeeChargePendingId(id);
		
		if (!feeChargeDetailOptionalPendingList.isEmpty()) {
			return new ResponseEntity<>(feeChargeDetailOptionalPendingList, HttpStatus.OK);
		} else {
			responseMessage.setMessages("record not found");
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
		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();

		
		if (status.equals(CommonStatus.ACTIVE.toString()) || status.equals(CommonStatus.INACTIVE.toString())) {
			List<FeeChargeDetailOptional> findByStatusList = feeChargeDetailOptionalService.findByStatus(status);
			
			if (!findByStatusList.isEmpty()) {
				return new ResponseEntity<>(findByStatusList, HttpStatus.OK);
			} else {
				responseMessage.setMessages(RECORD_NOT_FOUND);
				return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
			} 
		}else {
            responseMessage.setMessages(environment.getProperty("common-status.pattern"));
            return new ResponseEntity<>(responseMessage, HttpStatus.UNPROCESSABLE_ENTITY);
        }
	}
	
    /**
     * Get FeeChargeDetailOptional by  category id
     *
     * @param tenantId the id of tenant
     * @param id       the id of the fee charge category
     * @return list of FeeChargeDetailOptional if record exists, otherwise <code>204</code>
     */
	@GetMapping(value = "/{tenantId}/category/{id}")
	public ResponseEntity<Object> findByCategory(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id) {
		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();

		List<FeeChargeDetailOptional> feeChargeDetailOptionalList = feeChargeDetailOptionalService.findByCategory(id);
		
		if (!feeChargeDetailOptionalList.isEmpty()) {
			return new ResponseEntity<>(feeChargeDetailOptionalList, HttpStatus.OK);
		} else {
			responseMessage.setMessages("record not found");
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	@GetMapping(value = "/{tenantId}/temp-approve/fee-charge-pending/{id}")
	public ResponseEntity<Object> temporyApprove(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id) {
		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();

		feeChargeDetailOptionalService.approvePendingFeeChargeDetailsOptional(id);
		
		responseMessage.setMessages("updated succesfully");
		return new ResponseEntity<>(responseMessage, HttpStatus.OK);
		
	}
	
	
	@PutMapping(value = "/{tenantId}/detail-optional/{id}")
	public ResponseEntity<Object> directUpdate(@PathVariable(value = "tenantId", required = true) String tenantId, 
			                                             @PathVariable(value = "id", required = true) Long id, 
			                                             @Valid @RequestBody FeeChargeDetailOptionalUpdateResource feeChargeDetailOptionalUpdateResource) {
		if(LogginAuthentcation.getInstance().getUserName()==null || LogginAuthentcation.getInstance().getUserName().isEmpty()) { 
 			throw new UserNotFound(environment.getProperty("common.user-not-found"));
 		}
		
		Optional<FeeChargeDetailOptional> feeChargeDetailOptional = feeChargeDetailOptionalRepository.findById(id);
		
		FeeChargeDetailOptional directUpdate = feeChargeDetailOptionalService.directUpdate(tenantId, LogginAuthentcation.getInstance().getUserName(), feeChargeDetailOptionalUpdateResource , feeChargeDetailOptional.get().getFeeCharge());
		SuccessAndErrorDetails successAndErrorDetails=new SuccessAndErrorDetails();
		if(directUpdate!= null) {
			successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty("rec.updated"), directUpdate.getId().toString());
	        return new ResponseEntity<>(successAndErrorDetails,HttpStatus.OK);
		}
		else {
			successAndErrorDetails.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(successAndErrorDetails, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
	

	

}
