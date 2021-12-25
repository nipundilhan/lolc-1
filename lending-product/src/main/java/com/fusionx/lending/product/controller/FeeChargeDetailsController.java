package com.fusionx.lending.product.controller;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

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

import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.FeeChargeCap;
import com.fusionx.lending.product.domain.FeeChargeCapPending;
import com.fusionx.lending.product.domain.FeeChargeDetails;
import com.fusionx.lending.product.domain.FeeChargeDetailsPending;
import com.fusionx.lending.product.enums.WorkflowStatus;
import com.fusionx.lending.product.exception.UserNotFound;
import com.fusionx.lending.product.resources.FeeChargeDetailsAddResource;
import com.fusionx.lending.product.resources.FeeChargeDetailsUpdateResource;
import com.fusionx.lending.product.resources.SuccessAndErrorDetails;
import com.fusionx.lending.product.resources.SuccessAndErrorDetailsResource;
import com.fusionx.lending.product.service.FeeChargeDetailsService;

// TODO: Auto-generated Javadoc
/**
 * The Class FeeChargeDetailsController.
 */
@RestController
@RequestMapping(value = "/fee-charge-details")
@CrossOrigin(origins = "*")
public class FeeChargeDetailsController extends MessagePropertyBase{
	
	/** The environment. */
	@Autowired
	Environment environment;
	
	/** The fee charge details service. */
	@Autowired
	public FeeChargeDetailsService feeChargeDetailsService;
	
	
	/** The Constant RECORD_NOT_FOUND. */
	private static final String RECORD_NOT_FOUND = "Records not available";
	
	/** The user not found. */
	private String userNotFound="common.user-not-found";
	
	/** The pageable length. */
	private String pageableLength = "common.pageable-length";
	
	/**
	 * get all Fee Charge Details.
	 *
	 * @param tenantId the tenant id
	 * @return List<Type>
	 */
	  @GetMapping(value = "/{tenantId}/all")
	    public ResponseEntity<Object> getAllFeeChargeDetails(@PathVariable(value = "tenantId", required = true) String tenantId) {
	    	SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
			List<FeeChargeDetails> feeChargeDetails = feeChargeDetailsService.getAll();
			int size = feeChargeDetails.size();
			if (size > 0) {
				return new ResponseEntity<>((Collection<FeeChargeDetails>) feeChargeDetails,HttpStatus.OK);
			} 
			else {
				responseMessage.setMessages(RECORD_NOT_FOUND);
				return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
			}
		}
	  
	  /**
  	 * get Fee Charge Details by id.
  	 *
  	 * @param tenantId the tenant id
  	 * @param id the id
  	 * @return Option
  	 */
	 	@GetMapping(value = "/{tenantId}/{id}")
	 	public ResponseEntity<Object> getFeeChargeById(@PathVariable(value = "tenantId", required = true) String tenantId, 
	 			                                              @PathVariable(value = "id", required = true) Long id) {
	 		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
		 	Optional<FeeChargeDetails> isPresentFeeChargeDetails = feeChargeDetailsService.getById(id);
		 	if (isPresentFeeChargeDetails.isPresent()) {
		 		return new ResponseEntity<>(isPresentFeeChargeDetails.get(), HttpStatus.OK);
		 	} 
		 	else {
		 		responseMessage.setMessages(RECORD_NOT_FOUND);
				return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		 	}
	 	}
	  
	  /**
  	 * get Fee Charge Details  by code.
  	 *
  	 * @param tenantId the tenant id
  	 * @param code the code
  	 * @return Optional
  	 */
		@GetMapping(value = "/{tenantId}/code/{code}")
		public ResponseEntity<Object> getFeeChargeByCode(@PathVariable(value = "tenantId", required = true) String tenantId,
														   	            @PathVariable(value = "code", required = true) String code){
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			Optional<FeeChargeDetails> isPresentFeeChargeDetails = feeChargeDetailsService.getByCode(code);
			if(isPresentFeeChargeDetails.isPresent()) {
				return new ResponseEntity<>(isPresentFeeChargeDetails.get(), HttpStatus.OK);
			}
			else {
				responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
				return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
			}
		}
	  
		/**
		 * get Fee Charge Details by status.
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
			 	List<FeeChargeDetails> isPresentFeeChargeDetails = feeChargeDetailsService.getByStatus(status);
			 	int size = isPresentFeeChargeDetails.size();
			 	if (size>0) {
			 		return new ResponseEntity<>(isPresentFeeChargeDetails, HttpStatus.OK);
			 	} 
			 	else {
			 		responseMessage.setMessages(RECORD_NOT_FOUND);
					return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
			 	}
	 		}
	 		else{
	 			responseMessage.setMessages(environment.getProperty(COMMON_STATUS_PATTERN));
				return new ResponseEntity<>(responseMessage, HttpStatus.UNPROCESSABLE_ENTITY);
		 	}
	 	}
	  
		/**
		 * save Fee Charge Details.
		 *
		 * @param tenantId the tenant id
		 * @param feeChargeDetailsAddResource the fee charge details add resource
		 * @return SuccessAndErrorDetailsDto
		 */
	 	@PostMapping(value = "/{tenantId}")
	 	public ResponseEntity<Object> addFeeCharge(@PathVariable(value = "tenantId", required = true) String tenantId,
	 			                                          @Valid @RequestBody FeeChargeDetailsAddResource feeChargeDetailsAddResource) {
	 		if(LogginAuthentcation.getInstance().getUserName()==null || LogginAuthentcation.getInstance().getUserName().isEmpty()) { 
	 			throw new UserNotFound(environment.getProperty(userNotFound));
	 		}
			
	 		SuccessAndErrorDetails successAndErrorDetails = new SuccessAndErrorDetails();
	 		FeeChargeDetails addFeeChargeDetails = feeChargeDetailsService.addFeeChargeDetails(tenantId, feeChargeDetailsAddResource);
		 	successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty("rec.saved"),Long.toString(addFeeChargeDetails.getId()));
			return new ResponseEntity<>(successAndErrorDetails,HttpStatus.CREATED);
	 	}
	 	
	 	/**
	 	 * update Fee Charge Details .
	 	 *
	 	 * @param tenantId the tenant id
	 	 * @param id the id
	 	 * @param feeChargeDetailsUpdateResource the fee charge details update resource
	 	 * @return SuccessAndErrorDetailsDto
	 	 */
		@PutMapping(value = "/{tenantId}/{id}")
		public ResponseEntity<Object> updateFeeCharge(@PathVariable(value = "tenantId", required = true) String tenantId, 
				                                             @PathVariable(value = "id", required = true) Long id, 
				                                             @Valid @RequestBody FeeChargeDetailsUpdateResource feeChargeDetailsUpdateResource) {
			if(LogginAuthentcation.getInstance().getUserName()==null || LogginAuthentcation.getInstance().getUserName().isEmpty()) { 
	 			throw new UserNotFound(environment.getProperty(userNotFound));
	 		}
			
			SuccessAndErrorDetails successAndErrorDetails=new SuccessAndErrorDetails();
			Optional<FeeChargeDetails> isPresentFeeChargeDetails = feeChargeDetailsService.getById(id);
			if(isPresentFeeChargeDetails.isPresent()) {
			
				FeeChargeDetails feeChargeDetails = feeChargeDetailsService.updateFeeChargeDetails(tenantId, id, feeChargeDetailsUpdateResource);
				successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty("rec.updated"), feeChargeDetails.getId().toString());
		        return new ResponseEntity<>(successAndErrorDetails,HttpStatus.OK);
			}
			else {
				successAndErrorDetails.setMessages(RECORD_NOT_FOUND);
				return new ResponseEntity<>(successAndErrorDetails, HttpStatus.UNPROCESSABLE_ENTITY);
			}
		}
		
		/**
		 * approve Fee Charge Details Pending.
		 *
		 * @param tenantId the tenant id
		 * @param pendingId the pending id
		 * @return SuccessAndErrorDetailsDto
		 */
		@PutMapping(value = "{tenantId}/approve/{pendingId}")
		public ResponseEntity<Object> approve(@PathVariable(value = "tenantId", required = true) String tenantId, @PathVariable(value = "pendingId", required = true) Long pendingId){
			SuccessAndErrorDetailsResource successAndErrorDetailsResource=new SuccessAndErrorDetailsResource();
			Optional<FeeChargeDetailsPending> isPresentFeeChargeDetailsPending = feeChargeDetailsService.getByPendingId(pendingId);
			if(isPresentFeeChargeDetailsPending.isPresent()) {
		
				if(feeChargeDetailsService.approveReject(tenantId, pendingId, WorkflowStatus.COMPLETE)) {
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
		 * Reject Fee Charge Details Pending.
		 *
		 * @param tenantId the tenant id
		 * @param pendingId the pending id
		 * @return SuccessAndErrorDetailsDto
		 */
		@PutMapping(value = "{tenantId}/reject/{pendingId}")
		public ResponseEntity<Object> reject(@PathVariable(value = "tenantId", required = true) String tenantId, @PathVariable(value = "pendingId", required = true) Long pendingId){
			SuccessAndErrorDetailsResource successAndErrorDetailsResource=new SuccessAndErrorDetailsResource();
			Optional<FeeChargeDetailsPending> isPresentFeeChargeDetailsPending = feeChargeDetailsService.getByPendingId(pendingId);
			if(isPresentFeeChargeDetailsPending.isPresent()) {
		
				if(feeChargeDetailsService.approveReject(tenantId, pendingId, WorkflowStatus.REJECT)) {
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
		 	Optional<FeeChargeDetailsPending> feeChargeDetailsPending = feeChargeDetailsService.getByPendingId(id);
		 	if (feeChargeDetailsPending.isPresent()) {
		 		return new ResponseEntity<>(feeChargeDetailsPending.get(), HttpStatus.OK);
		 	} 
		 	else {
		 		responseMessage.setMessages(RECORD_NOT_FOUND);
				return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		 	}
	 	}
		
		
	 	/**
	 	 * Gets the by other fee type.
	 	 *
	 	 * @param tenantId the tenant id
	 	 * @param name the name
	 	 * @return the by other fee type
	 	 */
	 	@GetMapping(value = "/{tenantId}/fee-type/{name}")
		 public ResponseEntity<Object> getByOtherFeeType(@PathVariable(value = "tenantId", required = true) String tenantId, 
				 @PathVariable(value = "name", required = true) String name) {
			 SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
			 List<FeeChargeDetails> feeChargeDetails = feeChargeDetailsService.getByFeeType(name);
			 int size = feeChargeDetails.size();
				if (size > 0) {
					return new ResponseEntity<>((Collection<FeeChargeDetails>) feeChargeDetails,HttpStatus.OK);
				} 
				else {
					responseMessage.setMessages(RECORD_NOT_FOUND);
					return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
				}
		 }
	 	
	 	/**
	 	 * Gets the by fee charge.
	 	 *
	 	 * @param tenantId the tenant id
	 	 * @param name the name
	 	 * @return the by fee charge
	 	 */
	 	@GetMapping(value = "/{tenantId}/fee-charge/{name}")
	 	public ResponseEntity<Object> getByFeeCharge(@PathVariable(value = "tenantId", required = true) String tenantId, 
	 			@PathVariable(value = "name", required = true) String name) {
	 		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
	 		List<FeeChargeDetails> feeChargeDetails = feeChargeDetailsService.getByFeeCharge(name);
	 		int size = feeChargeDetails.size();
	 		if (size > 0) {
	 			return new ResponseEntity<>((Collection<FeeChargeDetails>) feeChargeDetails,HttpStatus.OK);
	 		} 
	 		else {
	 			responseMessage.setMessages(RECORD_NOT_FOUND);
	 			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
	 		}
	 	}
	 	
	 	/**
	 	 * Gets the by fee category.
	 	 *
	 	 * @param tenantId the tenant id
	 	 * @param name the name
	 	 * @return the by fee category
	 	 */
	 	@GetMapping(value = "/{tenantId}/fee-category/{name}")
	 	public ResponseEntity<Object> getByFeeCategory(@PathVariable(value = "tenantId", required = true) String tenantId, 
	 			@PathVariable(value = "name", required = true) String name) {
	 		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
	 		List<FeeChargeDetails> feeChargeDetails = feeChargeDetailsService.getByFeeCategory(name);
	 		int size = feeChargeDetails.size();
	 		if (size > 0) {
	 			return new ResponseEntity<>((Collection<FeeChargeDetails>) feeChargeDetails,HttpStatus.OK);
	 		} 
	 		else {
	 			responseMessage.setMessages(RECORD_NOT_FOUND);
	 			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
	 		}
	 	}

}
