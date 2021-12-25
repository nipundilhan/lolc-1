package com.fusionx.lending.product.controller;

/**
 * Repayment Amount Type Controller
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   	Task No    	Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   22-06-2021     FX-6619 		FX-6669     RavishikaS      Created
 *    
 ********************************************************************************************************
 */


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
import com.fusionx.lending.product.domain.RepaymentAmountType;
import com.fusionx.lending.product.domain.FeatureBenefitItem;
import com.fusionx.lending.product.exception.UserNotFound;
import com.fusionx.lending.product.resources.CommonAddResource;
import com.fusionx.lending.product.resources.CommonUpdateResource;
import com.fusionx.lending.product.resources.SuccessAndErrorDetails;
import com.fusionx.lending.product.resources.SuccessAndErrorDetailsResource;
import com.fusionx.lending.product.service.RepaymentAmountTypeService;

@RestController
@RequestMapping(value = "/repayment-amount-type")
@CrossOrigin(origins = "*")
public class RepaymentAmountTypeController extends MessagePropertyBase{
	
	@Autowired
	Environment environment;
	
	@Autowired
	public RepaymentAmountTypeService repaymentAmountTypeService;
	
	/**
	 * get all RepaymentAmountType
	 * @param @PathVariable{tenantId}
	 * @param @PathVariable{all}
	 * @return List<Type>
	 */
    @GetMapping(value = "/{tenantId}/all")
    public ResponseEntity<Object> getAllRepaymentAmountType(@PathVariable(value = "tenantId", required = true) String tenantId) {
    	SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<RepaymentAmountType>isPresentRepaymentAmountType = repaymentAmountTypeService.getAll();
		if(!isPresentRepaymentAmountType.isEmpty()) {
			return new ResponseEntity<>((Collection<RepaymentAmountType>)isPresentRepaymentAmountType,HttpStatus.OK);
		}
		else {
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
    
    /**
     * get RepaymentAmountType by id
     * @param @PathVariable{tenantId}
     * @param @PathVariable{id}
     * @return Option
     */
 	@GetMapping(value = "/{tenantId}/{id}")
 	public ResponseEntity<Object> getRepaymentAmountTypeById(@PathVariable(value = "tenantId", required = true) String tenantId, 
 			                                              @PathVariable(value = "id", required = true) Long id) {
 		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
	 	Optional<RepaymentAmountType> isPresentRepaymentAmountType = repaymentAmountTypeService.getById(id);
	 	if (isPresentRepaymentAmountType.isPresent()) {
	 		return new ResponseEntity<>(isPresentRepaymentAmountType.get(), HttpStatus.OK);
	 	} 
	 	else {
	 		responseMessage.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
	 	}
 	}
 	
	/**
	 * get RepaymentAmountType by code
	 * @param @pathVariable{tenantId}
	 * @param @pathVariable {code}
	 * @return Optional
	 **/
	@GetMapping(value = "/{tenantId}/code/{code}")
	public ResponseEntity<Object> getRepaymentAmountTypeByCode(@PathVariable(value = "tenantId", required = true) String tenantId,
													   	            @PathVariable(value = "code", required = true) String code){
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<RepaymentAmountType> isPresentRepaymentAmountType = repaymentAmountTypeService.getByCode(code);
		if(isPresentRepaymentAmountType.isPresent()) {
			return new ResponseEntity<>(isPresentRepaymentAmountType.get(), HttpStatus.OK);
		}
		else {
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

 	
 	/**
     * get RepaymentAmountType by status
     * @param @PathVariable{tenantId}
     * @param @PathVariable{status}
     * @return Option
     */
 	@GetMapping(value = "/{tenantId}/status/{status}")
 	public ResponseEntity<Object> getRepaymentAmountTypeByStatus(@PathVariable(value = "tenantId", required = true) String tenantId, 
 			                                                  @PathVariable(value = "status", required = true) String status) {
 		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
 		
 		if(status.equals("ACTIVE") || status.equals("INACTIVE")){
		 	List<RepaymentAmountType> isPresentRepaymentAmountType = repaymentAmountTypeService.getByStatus(status);
		 	int size = isPresentRepaymentAmountType.size();
		 	if (size>0) {
		 		return new ResponseEntity<>(isPresentRepaymentAmountType, HttpStatus.OK);
		 	} 
		 	else {
		 		responseMessage.setMessages(RECORD_NOT_FOUND);
				return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		 	}
 		}
 		else{
	 		responseMessage.setMessages(environment.getProperty(INVALID_STATUS));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
	 	}
 	}
 	
 	/**
     * save RepaymentAmountType
     * @param @PathVariable{tenantId}
     * @param @RequestBody{CommonAddResource}
     * @return SuccessAndErrorDetailsDto
     */
 	@PostMapping(value = "/{tenantId}")
 	public ResponseEntity<Object> addRepaymentAmountType(@PathVariable(value = "tenantId", required = true) String tenantId,
 			                                          @Valid @RequestBody CommonAddResource commonAddResource) {
 		if(LogginAuthentcation.getInstance().getUserName()==null || LogginAuthentcation.getInstance().getUserName().isEmpty()) { 
 			throw new UserNotFound(environment.getProperty(COMMON_USER_NOT_FOUND));
 		}
		
 		SuccessAndErrorDetails successAndErrorDetails = new SuccessAndErrorDetails();
 		RepaymentAmountType repaymentAmountType = repaymentAmountTypeService.addRepaymentAmountType(tenantId, commonAddResource);
	 	successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty(RECORD_CREATED),Long.toString(repaymentAmountType.getId()));
		return new ResponseEntity<>(successAndErrorDetails,HttpStatus.CREATED);
 	}
 	
	/**
     * update RepaymentAmountType 
     * @param @PathVariable{tenantId}
     * @param @RequestBody{CommonUpdateResource}
     * @return SuccessAndErrorDetailsDto
     */
	@PutMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> updateRepaymentAmountType(@PathVariable(value = "tenantId", required = true) String tenantId, 
			                                             @PathVariable(value = "id", required = true) Long id, 
			                                             @Valid @RequestBody CommonUpdateResource commonUpdateResource) {
		if(LogginAuthentcation.getInstance().getUserName()==null || LogginAuthentcation.getInstance().getUserName().isEmpty()) { 
 			throw new UserNotFound(environment.getProperty(COMMON_USER_NOT_FOUND));
 		}
		
		SuccessAndErrorDetails successAndErrorDetails=new SuccessAndErrorDetails();
		Optional<RepaymentAmountType> isPresentRepaymentAmountType = repaymentAmountTypeService.getById(id);
		if(isPresentRepaymentAmountType.isPresent()) {
			RepaymentAmountType repaymentAmountType = repaymentAmountTypeService.updateRepaymentAmountType(tenantId, id, commonUpdateResource);
			successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty(RECORD_UPDATED), repaymentAmountType.getId().toString());
        	return new ResponseEntity<>(successAndErrorDetails,HttpStatus.OK);
		}
		else {
			successAndErrorDetails.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(successAndErrorDetails, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
}
