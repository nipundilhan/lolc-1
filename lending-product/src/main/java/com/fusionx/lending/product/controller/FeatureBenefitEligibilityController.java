package com.fusionx.lending.product.controller;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.fusionx.lending.product.domain.FeatureBenefitEligibility;
import com.fusionx.lending.product.exception.UserNotFound;
import com.fusionx.lending.product.resources.AddBaseEligibilityRequest;
import com.fusionx.lending.product.resources.SuccessAndErrorDetails;
import com.fusionx.lending.product.resources.UpdateBaseEligibilityRequest;
import com.fusionx.lending.product.resources.ValidationCommon;
import com.fusionx.lending.product.service.FeatureBenefitEligibilityService;

/**
 * feature benefit eligibility Service - Domain entity
 * @author 	Sanatha
 * @Date    24-JUN-2021
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   24-JUN-2021   					     Sanatha      Initial development.
 *    
 ********************************************************************************************************
 */
@RestController
@RequestMapping(value = "/feature-benefit-eligibility")
@CrossOrigin(origins = "*")
public class FeatureBenefitEligibilityController {
	
	private static final Logger logger = LoggerFactory.getLogger(FeatureBenefitEligibilityController.class);
	
	@Autowired
	private FeatureBenefitEligibilityService  service;
	
	@Autowired
	private Environment   environment;
	
	private String userNotFound="common.user-not-found";
		
	/**
	 * get all Feature Benefit Eligibility details
	 * @param @PathVariable{tenantId}
	 * @param @PathVariable{all}
	 * @return List<FeatureBenefitEligibility>
	 */
    @GetMapping(value = "/{tenantId}/all")
    public ResponseEntity<Object> getAllFeatureBenefitEligibility(@PathVariable(value = "tenantId", required = true) String tenantId) {
    	SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
	
			List<FeatureBenefitEligibility> featureBenefitEligibilities = service.findAll();
			if(featureBenefitEligibilities !=null && !featureBenefitEligibilities.isEmpty()) {
				int currencieSsize = featureBenefitEligibilities.size();
				if (currencieSsize > 0) {
					return new ResponseEntity<>((Collection<FeatureBenefitEligibility>) featureBenefitEligibilities,HttpStatus.OK);
				} 
				else {
					responseMessage.setMessages(environment.getProperty("record-not-found"));
					return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
				}
			}
			else {
				responseMessage.setMessages(environment.getProperty("record-not-found"));
				return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
			}		
	}  
    
	/**
     * get Feature Benefit Eligibility Info by id
     * @param @PathVariable{tenantId} 
     * @param @PathVariable{id}
     * @return Option
     */
 	@GetMapping(value = "/{tenantId}/{id}")
 	public ResponseEntity<Object> getFeatureBenefitEligibilityById(@PathVariable(value = "tenantId", required = true) String tenantId, 
 			                                    @PathVariable(value = "id", required = true) Long id) {
 		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();

	 		Optional<FeatureBenefitEligibility> featureBenefitEligibilities = service.findById(id);
	 		if (featureBenefitEligibilities.isPresent()) {
	 			return new ResponseEntity<>(featureBenefitEligibilities.get(), HttpStatus.OK);
	 		} 
	 		else {
	 			responseMessage.setMessages(environment.getProperty("record-not-found"));
				return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
	 		}
 	}
 	
	/**
     * save Feature Benefit Eligibility  Info
     * @param @PathVariable{tenantId}
     * @param @RequestBody{AddBaseEligibilityRequest}
     * @return SuccessAndErrorDetailsDto
     */
 	@PostMapping(value = "/{tenantId}")
 	public ResponseEntity<Object> addFeatureBenefitEligibility(@PathVariable(value = "tenantId", required = true) String tenantId,
 			                                @Valid @RequestBody AddBaseEligibilityRequest addBaseEligibilityRequest) {
	 		if(LogginAuthentcation.getInstance().getUserName()==null || LogginAuthentcation.getInstance().getUserName().isEmpty()) { 
	 			throw new UserNotFound(environment.getProperty(userNotFound));
	 		}
			addBaseEligibilityRequest.setCreatedUser(LogginAuthentcation.getInstance().getUserName());

 		    FeatureBenefitEligibility newFeatureBenefitEligibility = service.addFeatureBenefitEligibility(tenantId, addBaseEligibilityRequest);
	 		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails(environment.getProperty("rec.saved"),Long.toString(newFeatureBenefitEligibility.getId()));
	     	return new ResponseEntity<>(responseMessage,HttpStatus.CREATED);
 	}
 	
 	
	/**
     * update Feature Benefit Eligibility  Info
     * @param @PathVariable{tenantId}
     * @param @RequestBody{updateBaseEligibilityRequest}
     * @return SuccessAndErrorDetailsDto
     */
	@PutMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> updateFeatureBenefitEligibility(@PathVariable(value = "tenantId", required = true) String tenantId, 
			                                   @PathVariable(value = "id", required = true) Long id, 
			                                   @Valid @RequestBody UpdateBaseEligibilityRequest updateBaseEligibilityRequest) {
		if(LogginAuthentcation.getInstance().getUserName()==null || LogginAuthentcation.getInstance().getUserName().isEmpty()) { 
 			throw new UserNotFound(environment.getProperty(userNotFound));
 		}
		updateBaseEligibilityRequest.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
		
		if(service.exists(id)) 
	      {
			updateBaseEligibilityRequest.setId(id.toString());
			updateBaseEligibilityRequest.setTenantId(tenantId);

			FeatureBenefitEligibility updatedFeatureBenefitEligibility = service.updateFeatureBenefitEligibility(tenantId, updateBaseEligibilityRequest);
			SuccessAndErrorDetails successDetailsDto = new SuccessAndErrorDetails(environment.getProperty("rec.updated"), updatedFeatureBenefitEligibility.getId().toString());
        	return new ResponseEntity<>(successDetailsDto,HttpStatus.OK);
		}
		else {
			
			ValidationCommon validationCommon = new ValidationCommon();
			
			validationCommon.setCode(environment.getProperty("record-not-found"));
			return new ResponseEntity<>(validationCommon, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
	
	/**
     * get Feature Benefit Eligibility Info by  status
     * @param @PathVariable{tenantId}
     * @param @PathVariable{status}
     * @return Option
     */
 	@GetMapping(value = "/{tenantId}/status/{status}")
 	public ResponseEntity<Object> getFeatureBenefitEligibilityByStatus(@PathVariable(value = "tenantId", required = true) String tenantId, 
 			                                    @PathVariable(value = "status", required = true) String status) {
 		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();

 			List<FeatureBenefitEligibility>  featureBenefitEligibilities = service.findByStatus(status);
	 		
 			if(featureBenefitEligibilities !=null && !featureBenefitEligibilities.isEmpty()) {
	 			return new ResponseEntity<>(featureBenefitEligibilities, HttpStatus.OK);
	 		} 
	 		else {
	 			responseMessage.setMessages(environment.getProperty("record-not-found"));
				return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
	 		}
 		
 	}
 	
 	/**
     * get Feature Benefit Eligibility Info by  Name
     * @param @PathVariable{tenantId}
     * @param @PathVariable{Name}
     * @return Option
     */
 	@GetMapping(value = "/{tenantId}/name/{name}")
 	public ResponseEntity<Object> getFeatureBenefitEligibilityByName(@PathVariable(value = "tenantId", required = true) String tenantId, 
 			                                    @PathVariable(value = "name", required = true) String name) {
 		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();

 			List<FeatureBenefitEligibility>  featureBenefitEligibilities = service.findByName(name);
	 		
 			if(featureBenefitEligibilities !=null && !featureBenefitEligibilities.isEmpty()) {
	 			return new ResponseEntity<>(featureBenefitEligibilities, HttpStatus.OK);
	 		} 
	 		else {
	 			responseMessage.setMessages(environment.getProperty("record-not-found"));
				return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
	 		}
 		
 	}
 	
 	/**
     * get Credit Interest Eligibility Info by  CreditIntEligibilityTypeCode
     * @param @PathVariable{tenantId}
     * @param @PathVariable{Name}
     * @return Option
     */
 	@GetMapping(value = "/{tenantId}/eligibility-type-code/{code}")
 	public ResponseEntity<Object> getFeatureBenefitEligibilityByBenefitEligibilityTypeCode(@PathVariable(value = "tenantId", required = true) String tenantId, 
 			                                    @PathVariable(value = "code", required = true) String code) {
 		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();

 			List<FeatureBenefitEligibility>  featureBenefitEligibilities = service.findByFeatureBenefitEligiTypeCode(code);
	 		
 			if(featureBenefitEligibilities !=null && !featureBenefitEligibilities.isEmpty()) {
	 			return new ResponseEntity<>(featureBenefitEligibilities, HttpStatus.OK);
	 		} 
	 		else {
	 			responseMessage.setMessages(environment.getProperty("record-not-found"));
				return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
	 		}
 		
 	}
}
