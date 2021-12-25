package com.fusionx.lending.product.controller;

import java.util.Collection;
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
import com.fusionx.lending.product.domain.OtherEligibilityType;
import com.fusionx.lending.product.exception.UserNotFound;
import com.fusionx.lending.product.resources.AddBaseRequest;
import com.fusionx.lending.product.resources.SuccessAndErrorDetails;
import com.fusionx.lending.product.resources.UpdateBaseRequest;
import com.fusionx.lending.product.service.OtherEligibilityTypeService;

/**
 * Other Eligibility Type Controller
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   09-06-2020   			   	        	Thamokshi    Created
 *    
 ********************************************************************************************************
 */

@RestController
@RequestMapping(value = "/other-eligibility-type")
@CrossOrigin(origins = "*")
public class OtherEligibilityTypeController extends MessagePropertyBase{

	@Autowired
	private OtherEligibilityTypeService otherEligibilityTypeService;
	
	private final static String RECORD_NOT_FOUND = "Record not found.";
	private String userNotFound="common.user-not-found";
	
	/**
	 * get all Other Eligibility service
	 * @param @PathVariable{tenantId}
	 * @param @PathVariable{all}
	 * @return List<Type>
	 */
    @GetMapping(value = "/{tenantId}/all")
    public ResponseEntity<Object> getOtherEligibilityType(@PathVariable(value = "tenantId", required = true) String tenantId) {
    	SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
		List<OtherEligibilityType> otherEligibilityType = otherEligibilityTypeService.findAll();
		int size = otherEligibilityType.size();
		if (size > 0) {
			return new ResponseEntity<>((Collection<OtherEligibilityType>) otherEligibilityType,HttpStatus.OK);
		} 
		else {
			responseMessage.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
    
    /**
     * get Other Eligibility service by id
     * @param @PathVariable{tenantId}
     * @param @PathVariable{id}
     * @return Option
     */
 	@GetMapping(value = "/{tenantId}/{otherEligibilityTypeId}")
 	public ResponseEntity<Object> getOtherEligibilityTypeById(@PathVariable(value = "tenantId", required = true) String tenantId, 
 			                                                  @PathVariable(value = "otherEligibilityTypeId", required = true) Long otherEligibilityTypeId) {
 		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
	 	Optional<OtherEligibilityType> isPresentOtherEligibilityType = otherEligibilityTypeService.findById(otherEligibilityTypeId);
	 	if (isPresentOtherEligibilityType.isPresent()) {
	 		return new ResponseEntity<>(isPresentOtherEligibilityType.get(), HttpStatus.OK);
	 	} 
	 	else {
	 		responseMessage.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
	 	}
 	}
 	
    /**
     * get Other Eligibility service by Code
     * @param @PathVariable{tenantId}
     * @param @PathVariable{id}
     * @return Option
     */
 	@GetMapping(value = "/{tenantId}/code/{code}")
 	public ResponseEntity<Object> getOtherEligibilityTypeByCode(@PathVariable(value = "tenantId", required = true) String tenantId, 
 			                                                    @PathVariable(value = "code", required = true) String code) {
 		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
	 	Optional<OtherEligibilityType> isPresentOtherEligibilityType = otherEligibilityTypeService.findByCode(code);
	 	if (isPresentOtherEligibilityType.isPresent()) {
	 		return new ResponseEntity<>(isPresentOtherEligibilityType.get(), HttpStatus.OK);
	 	} 
	 	else {
	 		responseMessage.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
	 	}
 	}
 	
    /**
     * get Other Eligibility service by Name
     * @param @PathVariable{tenantId}
     * @param @PathVariable{id}
     * @return Option
     */
 	@GetMapping(value = "/{tenantId}/name/{name}")
 	public ResponseEntity<Object> getOtherEligibilityTypeByName(@PathVariable(value = "tenantId", required = true) String tenantId, 
 			                                                    @PathVariable(value = "name", required = true) String name) {
 		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
	 	List<OtherEligibilityType> isPresentOtherEligibilityType = otherEligibilityTypeService.findByName(name);
	 	if (!isPresentOtherEligibilityType.isEmpty()) {
	 		return new ResponseEntity<>(isPresentOtherEligibilityType, HttpStatus.OK);
	 	} 
	 	else {
	 		responseMessage.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
	 	}
 	}
 	
 	/**
     * get Other Eligibility service by status
     * @param @PathVariable{tenantId}
     * @param @PathVariable{status}
     * @return Option
     */
 	@GetMapping(value = "/{tenantId}/status/{status}")
 	public ResponseEntity<Object> getOtherEligibilityTypeByStatus(@PathVariable(value = "tenantId", required = true) String tenantId, 
 			                                                      @PathVariable(value = "status", required = true) String status) {
 		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
 		
 		if(status.equals("ACTIVE") || status.equals("INACTIVE")){
		 	List<OtherEligibilityType> isPresentOtherEligibilityType = otherEligibilityTypeService.findByStatus(status);
		 	int size = isPresentOtherEligibilityType.size();
		 	if (size>0) {
		 		return new ResponseEntity<>(isPresentOtherEligibilityType, HttpStatus.OK);
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
     * save Other Eligibility service
     * @param @PathVariable{tenantId}
     * @param @RequestBody{AddBaseRequest}
     * @return SuccessAndErrorDetails
     */
 	@PostMapping(value = "/{tenantId}")
 	public ResponseEntity<Object> addOtherEligibilityType(@PathVariable(value = "tenantId", required = true) String tenantId,
 			                                              @Valid @RequestBody AddBaseRequest addBaseRequest) {
 		if(LogginAuthentcation.getInstance().getUserName()==null || LogginAuthentcation.getInstance().getUserName().isEmpty()) { 
 			throw new UserNotFound(environment.getProperty(userNotFound));
 		}
		
 		OtherEligibilityType otherEligibilityType = otherEligibilityTypeService.addOtherEligibilityType(tenantId, addBaseRequest);
 		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails(environment.getProperty("rec.saved"),Long.toString(otherEligibilityType.getId()));
	     	return new ResponseEntity<>(responseMessage,HttpStatus.CREATED);
 	}
 	
 	
	/**
     * update Eligibility codes service
     * @param @PathVariable{tenantId}
     * @param @RequestBody{UpdateBaseRequest}
     * @return SuccessAndErrorDetailsDto
     */
	@PutMapping(value = "/{tenantId}/{otherEligibilityTypeId}")
	public ResponseEntity<Object> updateOtherEligibilityType(@PathVariable(value = "tenantId", required = true) String tenantId, 
			                                                 @PathVariable(value = "otherEligibilityTypeId", required = true) Long otherEligibilityTypeId, 
			                                                 @Valid @RequestBody UpdateBaseRequest updateBaseRequest) {
		if(LogginAuthentcation.getInstance().getUserName()==null || LogginAuthentcation.getInstance().getUserName().isEmpty()) { 
 			throw new UserNotFound(environment.getProperty(userNotFound));
 		}
		
		SuccessAndErrorDetails successAndErrorDetails = new SuccessAndErrorDetails();
		Optional<OtherEligibilityType> isPresentOtherEligibilityType = otherEligibilityTypeService.findById(otherEligibilityTypeId);
		if(isPresentOtherEligibilityType.isPresent()) {
			updateBaseRequest.setId(otherEligibilityTypeId.toString());
			OtherEligibilityType otherEligibilityType = otherEligibilityTypeService.updateOtherEligibilityType(tenantId, updateBaseRequest);
			successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty("rec.updated"), otherEligibilityType.getId().toString());
        	return new ResponseEntity<>(successAndErrorDetails,HttpStatus.OK);
		}
		else {
			successAndErrorDetails.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(successAndErrorDetails, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
}
