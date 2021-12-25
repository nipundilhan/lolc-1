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
import com.fusionx.lending.product.domain.FeatureBenefitItemType;
import com.fusionx.lending.product.exception.UserNotFound;
import com.fusionx.lending.product.resources.AddBaseRequest;
import com.fusionx.lending.product.resources.SuccessAndErrorDetails;
import com.fusionx.lending.product.resources.UpdateBaseRequest;
import com.fusionx.lending.product.resources.ValidationCommon;
import com.fusionx.lending.product.service.FeatureBenefitItemTypeService;

/**
 * Feature Benefit Item Type Service - Feature Benefit Item Type Domain entity
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
@RequestMapping(value = "/feature-benefit-item-type")
@CrossOrigin(origins = "*")
public class FeatureBenefitItemTypeController {
	
	@Autowired
	private FeatureBenefitItemTypeService  service;
	
	@Autowired
	private Environment   environment;
	
	private String userNotFound="common.user-not-found";
	
	/**
	 * get all Feature Benefit Item Type details
	 * @param @PathVariable{tenantId}
	 * @param @PathVariable{all}
	 * @return List<FeatureBenefitItemType>
	 */
    @GetMapping(value = "/{tenantId}/all")
    public ResponseEntity<Object> getAllFeatureBenefitItemType(@PathVariable(value = "tenantId", required = true) String tenantId) {
    	SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
	
			Optional<Collection<FeatureBenefitItemType>> featureBenefitItemTypes = service.findAll();
			if(featureBenefitItemTypes.isPresent()) {
				int featureBenefitItemTypeSize = featureBenefitItemTypes.get().size();
				if (featureBenefitItemTypeSize > 0) {
					return new ResponseEntity<>((Collection<FeatureBenefitItemType>) featureBenefitItemTypes.get(),HttpStatus.OK);
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
     * get Feature Benefit Item Type Info by id
     * @param @PathVariable{tenantId}
     * @param @PathVariable{id}
     * @return Option
     */
 	@GetMapping(value = "/{tenantId}/{featureBenefitItemTypeId}")
 	public ResponseEntity<Object> getFeatureBenefitItemTypeById(@PathVariable(value = "tenantId", required = true) String tenantId, 
 			                                    @PathVariable(value = "featureBenefitItemTypeId", required = true) Long featureBenefitItemTypeId) {
 		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();

	 		Optional<FeatureBenefitItemType> featureBenefitItemTypeTypes = service.findById(featureBenefitItemTypeId);
	 		if (featureBenefitItemTypeTypes.isPresent()) {
	 			return new ResponseEntity<>(featureBenefitItemTypeTypes.get(), HttpStatus.OK);
	 		} 
	 		else {
	 			responseMessage.setMessages(environment.getProperty("record-not-found"));
				return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
	 		}
 	}
 	
 	
 	/**
     * get Feature Benefit Item Type Info by code
     * @param @PathVariable{tenantId}
     * @param @PathVariable{code}
     * @return Option
     */
 	@GetMapping(value = "/{tenantId}/code/{code}")
 	public ResponseEntity<Object> getFeatureBenefitItemTypeByCode(@PathVariable(value = "tenantId", required = true) String tenantId, 
 			                                    @PathVariable(value = "code", required = true) String code) {
 		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
 		
	 		Optional<FeatureBenefitItemType> featureBenefitItemTypes = service.findByCode(code);
	 		if (featureBenefitItemTypes.isPresent()) {
	 			return new ResponseEntity<>(featureBenefitItemTypes.get(), HttpStatus.OK);
	 		} 
	 		else {
	 			responseMessage.setMessages(environment.getProperty("record-not-found"));
				return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
	 		}
 	
 	}
 	
	/**
     * save Feature Benefit Item Type Info
     * @param @PathVariable{tenantId}
     * @param @RequestBody{AddBaseRequest}
     * @return SuccessAndErrorDetailsDto
     */
 	@PostMapping(value = "/{tenantId}")
 	public ResponseEntity<Object> addFeatureBenefitItemType(@PathVariable(value = "tenantId", required = true) String tenantId,
 			                                @Valid @RequestBody AddBaseRequest addBaseRequest) {
	 		if(LogginAuthentcation.getInstance().getUserName()==null || LogginAuthentcation.getInstance().getUserName().isEmpty()) { 
	 			throw new UserNotFound(environment.getProperty(userNotFound));
	 		}

 		    FeatureBenefitItemType newFeatureBenefitItemType = service.addFeatureBenefitItemType(tenantId, addBaseRequest);
	 		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails(environment.getProperty("rec.saved"),Long.toString(newFeatureBenefitItemType.getId()));
	     	return new ResponseEntity<>(responseMessage,HttpStatus.CREATED);
 	}
 	
 	
	/**
     * update Feature Benefit Item Type Info
     * @param @PathVariable{tenantId}
     * @param @RequestBody{UpdateBaseRequest}
     * @return SuccessAndErrorDetailsDto
     */
	@PutMapping(value = "/{tenantId}/{featureBenefitItemTypeId}")
	public ResponseEntity<Object> updateFeatureBenefitItemType(@PathVariable(value = "tenantId", required = true) String tenantId, 
			                                   @PathVariable(value = "featureBenefitItemTypeId", required = true) Long featureBenefitItemTypeId, 
			                                   @Valid @RequestBody UpdateBaseRequest updateBaseRequest) {
		if(LogginAuthentcation.getInstance().getUserName()==null || LogginAuthentcation.getInstance().getUserName().isEmpty()) { 
 			throw new UserNotFound(environment.getProperty(userNotFound));
 		}
		
		if(service.exists(featureBenefitItemTypeId)) 
	      {
			updateBaseRequest.setId(featureBenefitItemTypeId.toString());
			

			FeatureBenefitItemType updatedFeatureBenefitItemType = service.updateFeatureBenefitItemType(tenantId, updateBaseRequest);
			SuccessAndErrorDetails successDetailsDto = new SuccessAndErrorDetails(environment.getProperty("rec.updated"), updatedFeatureBenefitItemType.getId().toString());
        	return new ResponseEntity<>(successDetailsDto,HttpStatus.OK);
		}
		else {
			
			ValidationCommon validationCommon = new ValidationCommon();
			
			validationCommon.setCode(environment.getProperty("record-not-found"));
			return new ResponseEntity<>(validationCommon, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
	
	
	/**
     * get Feature Benefit Item Type Info by  status
     * @param @PathVariable{tenantId}
     * @param @PathVariable{status}
     * @return Option
     */
 	@GetMapping(value = "/{tenantId}/status/{status}")
 	public ResponseEntity<Object> getFeatureBenefitItemTypeByStatus(@PathVariable(value = "tenantId", required = true) String tenantId, 
 			                                    @PathVariable(value = "status", required = true) String status) {


 			Optional<Collection<FeatureBenefitItemType>>  featureBenefitItemTypes = service.findByStatus(status);
	 		
 			if (featureBenefitItemTypes.isPresent() && !featureBenefitItemTypes.get().isEmpty()) {
	 			return new ResponseEntity<>(featureBenefitItemTypes.get(), HttpStatus.OK);
	 		} 
	 		else {SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
	 			responseMessage.setMessages(environment.getProperty("record-not-found"));
				return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
	 		}
 		
 	}
 	
 	
 	/**
     * get Feature Benefit Item Type Info by name
     * @param @PathVariable{tenantId}
     * @param @PathVariable{name}
     * @return Option
     */
 	@GetMapping(value = "/{tenantId}/name/{name}")
 	public ResponseEntity<Object> getFeatureBenefitItemTypeByName(@PathVariable(value = "tenantId", required = true) String tenantId, 
 			                                    @PathVariable(value = "name", required = true) String name) {
 		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
 		
	 		Optional<Collection<FeatureBenefitItemType>> featureBenefitItemTypes = service.findByName(name);
	 		if (featureBenefitItemTypes.isPresent()  && !featureBenefitItemTypes.get().isEmpty() ) {
	 			return new ResponseEntity<>(featureBenefitItemTypes.get(), HttpStatus.OK);
	 		} 
	 		else {
	 			responseMessage.setMessages(environment.getProperty("record-not-found"));
				return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
	 		}
 	
 	}
 	
 	
}
