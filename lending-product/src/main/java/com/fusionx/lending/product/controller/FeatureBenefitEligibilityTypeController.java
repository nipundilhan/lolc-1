package com.fusionx.lending.product.controller;

/**
 * Feature Benefit Eligibility Type Controller
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   10-06-2021             				 MenukaJ        Created
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

import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.FeatureBenefitEligibilityType;
import com.fusionx.lending.product.exception.UserNotFound;
import com.fusionx.lending.product.resources.CommonAddResource;
import com.fusionx.lending.product.resources.CommonUpdateResource;
import com.fusionx.lending.product.resources.SuccessAndErrorDetails;
import com.fusionx.lending.product.resources.SuccessAndErrorDetailsResource;
import com.fusionx.lending.product.service.FeatureBenefitEligibilityTypeService;

@RestController
@RequestMapping(value = "/feature-benefit-eligibility-type")
@CrossOrigin(origins = "*")
public class FeatureBenefitEligibilityTypeController {
	
	@Autowired
	Environment environment;
	
	@Autowired
	public FeatureBenefitEligibilityTypeService featureBenefitEligibilityTypeService;
	
	private static final String RECORD_NOT_FOUND = "Records not available";
	private String userNotFound="common.user-not-found";
	
	/**
	 * get all feature Benefit Eligibility Type
	 * @param @PathVariable{tenantId}
	 * @param @PathVariable{all}
	 * @return List<Type>
	 */
    @GetMapping(value = "/{tenantId}/all")
    public ResponseEntity<Object> getAllFeatureBenefitEligibilityType(@PathVariable(value = "tenantId", required = true) String tenantId) {
    	SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
		List<FeatureBenefitEligibilityType> featureBenefitEligibilityType = featureBenefitEligibilityTypeService.getAll();
		int size = featureBenefitEligibilityType.size();
		if (size > 0) {
			return new ResponseEntity<>((Collection<FeatureBenefitEligibilityType>) featureBenefitEligibilityType,HttpStatus.OK);
		} 
		else {
			responseMessage.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
    
    /**
     * get Feature Benefit Eligibility Type by id
     * @param @PathVariable{tenantId}
     * @param @PathVariable{id}
     * @return Option
     */
 	@GetMapping(value = "/{tenantId}/{id}")
 	public ResponseEntity<Object> getFeatureBenefitEligibilityTypeById(@PathVariable(value = "tenantId", required = true) String tenantId, 
 			                                              @PathVariable(value = "id", required = true) Long id) {
 		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
	 	Optional<FeatureBenefitEligibilityType> isPresentFeatureBenefitEligibilityType = featureBenefitEligibilityTypeService.getById(id);
	 	if (isPresentFeatureBenefitEligibilityType.isPresent()) {
	 		return new ResponseEntity<>(isPresentFeatureBenefitEligibilityType.get(), HttpStatus.OK);
	 	} 
	 	else {
	 		responseMessage.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
	 	}
 	}
 	
	/**
	 * get feature Benefit Eligibility Type by code
	 * @param @pathVariable{tenantId}
	 * @param @pathVariable {code}
	 * @return Optional
	 **/
	@GetMapping(value = "/{tenantId}/code/{code}")
	public ResponseEntity<Object> getFeatureBenefitEligibilityTypeByCode(@PathVariable(value = "tenantId", required = true) String tenantId,
													   	            @PathVariable(value = "code", required = true) String code){
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<FeatureBenefitEligibilityType> isPresentFeatureBenefitEligibilityType = featureBenefitEligibilityTypeService.getByCode(code);
		if(isPresentFeatureBenefitEligibilityType.isPresent()) {
			return new ResponseEntity<>(isPresentFeatureBenefitEligibilityType.get(), HttpStatus.OK);
		}
		else {
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * get feature Benefit Eligibility Type by name
	 * @param @pathVariable{tenantId}
	 * @param @pathVariable {name}
	 * @return List
	 **/
	@GetMapping(value = "/{tenantId}/name/{name}")
	public ResponseEntity<Object> getFeatureBenefitEligibilityTypeByName(@PathVariable(value = "tenantId", required = true) String tenantId,
															        @PathVariable(value = "name", required = true) String name){
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<FeatureBenefitEligibilityType> isPresentFeatureBenefitEligibilityType = featureBenefitEligibilityTypeService.getByName(name);
		if(!isPresentFeatureBenefitEligibilityType.isEmpty()) {
			return new ResponseEntity<>(isPresentFeatureBenefitEligibilityType, HttpStatus.OK);
		}
		else {
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
 	
 	/**
     * get feature Benefit Eligibility Type by status
     * @param @PathVariable{tenantId}
     * @param @PathVariable{status}
     * @return Option
     */
 	@GetMapping(value = "/{tenantId}/status/{status}")
 	public ResponseEntity<Object> getFeatureBenefitEligibilityTypeByStatus(@PathVariable(value = "tenantId", required = true) String tenantId, 
 			                                                  @PathVariable(value = "status", required = true) String status) {
 		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
 		
 		if(status.equals("ACTIVE") || status.equals("INACTIVE")){
		 	List<FeatureBenefitEligibilityType> isPresentFeatureBenefitEligibilityType = featureBenefitEligibilityTypeService.getByStatus(status);
		 	int size = isPresentFeatureBenefitEligibilityType.size();
		 	if (size>0) {
		 		return new ResponseEntity<>(isPresentFeatureBenefitEligibilityType, HttpStatus.OK);
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
     * save Feature Benefit Eligibility Type
     * @param @PathVariable{tenantId}
     * @param @RequestBody{CommonAddResource}
     * @return SuccessAndErrorDetailsDto
     */
 	@PostMapping(value = "/{tenantId}")
 	public ResponseEntity<Object> addFeatureBenefitEligibilityType(@PathVariable(value = "tenantId", required = true) String tenantId,
 			                                          @Valid @RequestBody CommonAddResource commonAddResource) {
 		if(LogginAuthentcation.getInstance().getUserName()==null || LogginAuthentcation.getInstance().getUserName().isEmpty()) { 
 			throw new UserNotFound(environment.getProperty(userNotFound));
 		}
		
 		SuccessAndErrorDetails successAndErrorDetails = new SuccessAndErrorDetails();
 		FeatureBenefitEligibilityType eligibilityType = featureBenefitEligibilityTypeService.addFeatureBenefitEligibilityType(tenantId, commonAddResource);
	 	successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty("rec.saved"),Long.toString(eligibilityType.getId()));
		return new ResponseEntity<>(successAndErrorDetails,HttpStatus.CREATED);
 	}
 	
	/**
     * update Feature Benefit Eligibility 
     * @param @PathVariable{tenantId}
     * @param @RequestBody{CommonUpdateResource}
     * @return SuccessAndErrorDetailsDto
     */
	@PutMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> updateFeatureBenefitEligibilityType(@PathVariable(value = "tenantId", required = true) String tenantId, 
			                                             @PathVariable(value = "id", required = true) Long id, 
			                                             @Valid @RequestBody CommonUpdateResource commonUpdateResource) {
		if(LogginAuthentcation.getInstance().getUserName()==null || LogginAuthentcation.getInstance().getUserName().isEmpty()) { 
 			throw new UserNotFound(environment.getProperty(userNotFound));
 		}
		
		SuccessAndErrorDetails successAndErrorDetails=new SuccessAndErrorDetails();
		Optional<FeatureBenefitEligibilityType> isPresentFeatureBenefitEligibilityType = featureBenefitEligibilityTypeService.getById(id);
		if(isPresentFeatureBenefitEligibilityType.isPresent()) {
			FeatureBenefitEligibilityType featureBenefitEligibilityType = featureBenefitEligibilityTypeService.updateFeatureBenefitEligibilityType(tenantId, id, commonUpdateResource);
			successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty("rec.updated"), featureBenefitEligibilityType.getId().toString());
        	return new ResponseEntity<>(successAndErrorDetails,HttpStatus.OK);
		}
		else {
			successAndErrorDetails.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(successAndErrorDetails, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
}
