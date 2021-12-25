package com.fusionx.lending.product.controller;


import java.util.Collection;
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
import com.fusionx.lending.product.domain.FeatureBenefitGroupType;
import com.fusionx.lending.product.exception.UserNotFound;
import com.fusionx.lending.product.resources.AddBaseRequest;
import com.fusionx.lending.product.resources.SuccessAndErrorDetails;
import com.fusionx.lending.product.resources.UpdateBaseRequest;
import com.fusionx.lending.product.resources.ValidationCommon;
import com.fusionx.lending.product.service.FeatureBenefitGroupTypeService;

/**
 * Feature Benefit Group Type Service - Feature Benefit Group Type Domain entity
 * @author 	Sanatha
 * @Date    15-JUN-2021
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   15-JUN-2021  						     Sanatha      Created
 *    
 ********************************************************************************************************
 */
@RestController
@RequestMapping(value = "/feature-benefit-group-type")
@CrossOrigin(origins = "*")
public class FeatureBenefitGroupTypeController {
	
	@Autowired
	private FeatureBenefitGroupTypeService  service;
	
	@Autowired
	private Environment   environment;
	
	private String userNotFound="common.user-not-found";
	
	/**
	 * get all Feature Benefit Group Type details
	 * @param @PathVariable{tenantId}
	 * @param @PathVariable{all}
	 * @return List<FeatureBenefitGroupType>
	 */
    @GetMapping(value = "/{tenantId}/all")
    public ResponseEntity<Object> getAllFeatureBenefitGroupType(@PathVariable(value = "tenantId", required = true) String tenantId) {
    	SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
	
			Optional<Collection<FeatureBenefitGroupType>> featureBenefitGroupTypes = service.findAll();
			if(featureBenefitGroupTypes.isPresent()) {
				int currencieSsize = featureBenefitGroupTypes.get().size();
				if (currencieSsize > 0) {
					return new ResponseEntity<>((Collection<FeatureBenefitGroupType>) featureBenefitGroupTypes.get(),HttpStatus.OK);
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
     * get Feature Benefit Group Type Info by id
     * @param @PathVariable{tenantId}
     * @param @PathVariable{id}
     * @return Option
     */
 	@GetMapping(value = "/{tenantId}/{featureBenefitGroupTypeId}")
 	public ResponseEntity<Object> getFeatureBenefitGroupTypeById(@PathVariable(value = "tenantId", required = true) String tenantId, 
 			                                    @PathVariable(value = "featureBenefitGroupTypeId", required = true) Long featureBenefitGroupTypeId) {
 		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();

	 		Optional<FeatureBenefitGroupType> featureBenefitGroupTypes = service.findById(featureBenefitGroupTypeId);
	 		if (featureBenefitGroupTypes.isPresent()) {
	 			return new ResponseEntity<>(featureBenefitGroupTypes.get(), HttpStatus.OK);
	 		} 
	 		else {
	 			responseMessage.setMessages(environment.getProperty("record-not-found"));
				return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
	 		}
 	}
 	
 	
 	/**
     * get Feature Benefit Group Type Info by code
     * @param @PathVariable{tenantId}
     * @param @PathVariable{code}
     * @return Option
     */
 	@GetMapping(value = "/{tenantId}/code/{code}")
 	public ResponseEntity<Object> getFeatureBenefitGroupTypeByCode(@PathVariable(value = "tenantId", required = true) String tenantId, 
 			                                    @PathVariable(value = "code", required = true) String code) {
 		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
 		
	 		Optional<FeatureBenefitGroupType> featureBenefitGroupTypes = service.findByCode(code);
	 		if (featureBenefitGroupTypes.isPresent()) {
	 			return new ResponseEntity<>(featureBenefitGroupTypes.get(), HttpStatus.OK);
	 		} 
	 		else {
	 			responseMessage.setMessages(environment.getProperty("record-not-found"));
				return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
	 		}
 	
 	}
 	
	/**
     * save Feature Benefit Group Type Info
     * @param @PathVariable{tenantId}
     * @param @RequestBody{AddBaseRequest}
     * @return SuccessAndErrorDetailsDto
     */
 	@PostMapping(value = "/{tenantId}")
 	public ResponseEntity<Object> addFeatureBenefitGroupType(@PathVariable(value = "tenantId", required = true) String tenantId,
 			                                @Valid @RequestBody AddBaseRequest addBaseRequest) {
 		if(LogginAuthentcation.getInstance().getUserName()==null || LogginAuthentcation.getInstance().getUserName().isEmpty()) { 
 			throw new UserNotFound(environment.getProperty(userNotFound));
 		}

 		FeatureBenefitGroupType newFeatureBenefitGroupType = service.addFeatureBenefitGroupType(tenantId, addBaseRequest);
	 		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails(environment.getProperty("rec.saved"),Long.toString(newFeatureBenefitGroupType.getId()));
	     	return new ResponseEntity<>(responseMessage,HttpStatus.CREATED);
 	}
 	
 	
	/**
     * update Feature Benefit Group Type Info
     * @param @PathVariable{tenantId}
     * @param @RequestBody{UpdateBaseRequest}
     * @return SuccessAndErrorDetailsDto
     */
	@PutMapping(value = "/{tenantId}/{featureBenefitGroupTypeId}")
	public ResponseEntity<Object> updateFeatureBenefitGroupType(@PathVariable(value = "tenantId", required = true) String tenantId, 
			                                   @PathVariable(value = "featureBenefitGroupTypeId", required = true) Long featureBenefitGroupTypeId, 
			                                   @Valid @RequestBody UpdateBaseRequest updateBaseRequest) {
		if(LogginAuthentcation.getInstance().getUserName()==null || LogginAuthentcation.getInstance().getUserName().isEmpty()) { 
 			throw new UserNotFound(environment.getProperty(userNotFound));
 		}
		
		if(service.exists(featureBenefitGroupTypeId)) 
	      {
			updateBaseRequest.setId(featureBenefitGroupTypeId.toString());
			//updateBaseRequest.setTenantId(tenantId);

			FeatureBenefitGroupType updatedFeatureBenefitGroupType = service.updateFeatureBenefitGroupType(tenantId, updateBaseRequest);
			SuccessAndErrorDetails successDetailsDto = new SuccessAndErrorDetails(environment.getProperty("rec.updated"), updatedFeatureBenefitGroupType.getId().toString());
        	return new ResponseEntity<>(successDetailsDto,HttpStatus.OK);
		}
		else {
			
			ValidationCommon validationCommon = new ValidationCommon();
			
			validationCommon.setCode(environment.getProperty("record-not-found"));
			return new ResponseEntity<>(validationCommon, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
	
	
	/**
     * get Feature Benefit Group TypeI nfo by  status
     * @param @PathVariable{tenantId}
     * @param @PathVariable{status}
     * @return Option
     */
 	@GetMapping(value = "/{tenantId}/status/{status}")
 	public ResponseEntity<Object> getFeatureBenefitGroupTypeByStatus(@PathVariable(value = "tenantId", required = true) String tenantId, 
 			                                    @PathVariable(value = "status", required = true) String status) {
 		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();

 			Optional<Collection<FeatureBenefitGroupType>>  featureBenefitGroupTypes = service.findByStatus(status);
	 		
 			if (featureBenefitGroupTypes.isPresent() && !featureBenefitGroupTypes.get().isEmpty()) {
	 			return new ResponseEntity<>(featureBenefitGroupTypes.get(), HttpStatus.OK);
	 		} 
	 		else {
	 			responseMessage.setMessages(environment.getProperty("record-not-found"));
				return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
	 		}
 		
 	}
 	
 	/**
     * get Feature Benefit Group Type Info by name
     * @param @PathVariable{tenantId}
     * @param @PathVariable{name}
     * @return Option
     */
 	@GetMapping(value = "/{tenantId}/name/{name}")
 	public ResponseEntity<Object> getFeatureBenefitGroupTypeByName(@PathVariable(value = "tenantId", required = true) String tenantId, 
 			                                    @PathVariable(value = "name", required = true) String name) {
 		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
 		
	 		Optional<Collection<FeatureBenefitGroupType>> featureBenefitGroupTypes = service.findByName(name);
	 		if (featureBenefitGroupTypes.isPresent() && !featureBenefitGroupTypes.get().isEmpty() ) {
	 			return new ResponseEntity<>(featureBenefitGroupTypes.get(), HttpStatus.OK);
	 		} 
	 		else {
	 			responseMessage.setMessages(environment.getProperty("record-not-found"));
				return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
	 		}
 	
 	}
}
