package com.fusionx.lending.product.controller;

/**
 * Residency eligibility service
 * @author 	Rangana
 * @Dat     08-06-2021
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   08-06-2021     FX-6        FX-6524    Rangana      Created
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
import com.fusionx.lending.product.domain.ResidencyEligibility;
import com.fusionx.lending.product.domain.ResidencyInclude;
import com.fusionx.lending.product.exception.UserNotFound;
import com.fusionx.lending.product.resources.ResidencyIncludeResource;
import com.fusionx.lending.product.resources.ResidencyIncludeUpdateResource;
import com.fusionx.lending.product.resources.SuccessAndErrorDetails;
import com.fusionx.lending.product.service.ResidencyEligibilityService;
import com.fusionx.lending.product.service.ResidencyIncludeService;

@RestController
@RequestMapping(value = "/residency-include")
@CrossOrigin(origins = "*")
public class ResidencyIncludeController {
	
	@Autowired
	Environment environment;
	
	@Autowired
	public ResidencyIncludeService residencyIncludeService;
	
	@Autowired
	public ResidencyEligibilityService residencyEligibilityService;
	
	private static final String RECORD_NOT_FOUND = "Records not available";
	private String userNotFound="common.user-not-found";
	
	/**
	 * get all Residency Include details
	 * @param @PathVariable{tenantId}
	 * @param @PathVariable{all}
	 * @return List<Type>
	 */
    @GetMapping(value = "/{tenantId}/all")
    public ResponseEntity<Object> getAllResidencyInclude(@PathVariable(value = "tenantId", required = true) String tenantId) {
    	SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
		List<ResidencyInclude> residencyInclude = residencyIncludeService.findAll(tenantId);
		int size = residencyInclude.size();
		if (size > 0) {
			return new ResponseEntity<>((Collection<ResidencyInclude>) residencyInclude,HttpStatus.OK);
		} 
		else {
			responseMessage.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
    
    /**
     * get Residency Include by id
     * @param @PathVariable{tenantId}
     * @param @PathVariable{id}
     * @return Option
     */
 	@GetMapping(value = "/{tenantId}/{residencyIncludeId}")
 	public ResponseEntity<Object> getResidencyIncludeById(@PathVariable(value = "tenantId", required = true) String tenantId, 
 			                                              @PathVariable(value = "residencyIncludeId", required = true) Long residencyIncludeId) {
 		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
	 	Optional<ResidencyInclude> isPresentResidencyInclude = residencyIncludeService.findById(tenantId, residencyIncludeId);
	 	if (isPresentResidencyInclude.isPresent()) {
	 		return new ResponseEntity<>(isPresentResidencyInclude.get(), HttpStatus.OK);
	 	} 
	 	else {
	 		responseMessage.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
	 	}
 	}
 	
 	/**
     * get Residency Include by status
     * @param @PathVariable{tenantId}
     * @param @PathVariable{status}
     * @return Option
     */
 	@GetMapping(value = "/{tenantId}/residency-eligibility/{residencyEligibilityId}")
 	public ResponseEntity<Object> getResidencyIncludeByResidencyEligibilityId(@PathVariable(value = "tenantId", required = true) String tenantId, 
 			                                                                  @PathVariable(value = "residencyEligibilityId", required = true) Long residencyEligibilityId) {
 		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
 		Optional<ResidencyEligibility> isPresentResidencyEligibility = residencyEligibilityService.findById(tenantId, residencyEligibilityId);
	 	if (isPresentResidencyEligibility.isPresent()) {
	 		Optional<List<ResidencyInclude>> residencyInclude = residencyIncludeService.findByResidencyEligiId(tenantId, residencyEligibilityId);
	 		if(residencyInclude.isPresent()) {
	 			return new ResponseEntity<>(residencyInclude.get(), HttpStatus.OK);
	 		}
	 		else {
		 		responseMessage.setMessages(RECORD_NOT_FOUND);
				return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
	 		} 
 		}
 		else{
	 		responseMessage.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
	 	}
 	}
 	
 	/**
     * get Residency Include by status
     * @param @PathVariable{tenantId}
     * @param @PathVariable{status}
     * @return Option
     */
 	@GetMapping(value = "/{tenantId}/status/{status}")
 	public ResponseEntity<Object> getResidencyIncludeByStatus(@PathVariable(value = "tenantId", required = true) String tenantId, 
 			                                                  @PathVariable(value = "status", required = true) String status) {
 		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
 		
 		if(status.equals("ACTIVE") || status.equals("INACTIVE")){
		 	List<ResidencyInclude> isPresentResidencyInclude = residencyIncludeService.findByStatus(tenantId, status);
		 	int size = isPresentResidencyInclude.size();
		 	if (size>0) {
		 		return new ResponseEntity<>(isPresentResidencyInclude, HttpStatus.OK);
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
     * save Residency Include
     * @param @PathVariable{tenantId}
     * @param @RequestBody{ResidencyIncludeAddResource}
     * @return SuccessAndErrorDetailsDto
     */
 	@PostMapping(value = "/{tenantId}/{residencyEligibilityId}")
 	public ResponseEntity<Object> addResidencyInclude(@PathVariable(value = "tenantId", required = true) String tenantId,
 			                                          @PathVariable(value = "residencyEligibilityId", required = true) Long residencyEligibilityId,
 			                                          @Valid @RequestBody ResidencyIncludeResource residencyIncludeAddResource) {
 		if(LogginAuthentcation.getInstance().getUserName()==null || LogginAuthentcation.getInstance().getUserName().isEmpty()) { 
 			throw new UserNotFound(environment.getProperty(userNotFound));
 		}
		
 		SuccessAndErrorDetails successAndErrorDetails = new SuccessAndErrorDetails();
 		Optional<ResidencyEligibility> isPresentResidencyEligibility = residencyEligibilityService.findById(tenantId, residencyEligibilityId);
	 	if (isPresentResidencyEligibility.isPresent()) {
	 		ResidencyInclude residencyInclude = residencyIncludeService.addResidencyInclude(tenantId,residencyEligibilityId,residencyIncludeAddResource, LogginAuthentcation.getInstance().getUserName());
		 	successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty("rec.saved"),Long.toString(residencyInclude.getId()));
			return new ResponseEntity<>(successAndErrorDetails,HttpStatus.CREATED);
        }
		else {
			successAndErrorDetails.setMessages(environment.getProperty("residencyEligibilityId.invalid"));
			return new ResponseEntity<>(successAndErrorDetails, HttpStatus.UNPROCESSABLE_ENTITY);
		}
 	}
 	
	/**
     * update Residency Include
     * @param @PathVariable{tenantId}
     * @param @RequestBody{ResidencyIncludeUpdateResource}
     * @return SuccessAndErrorDetailsDto
     */
	@PutMapping(value = "/{tenantId}/{residencyIncludeId}")
	public ResponseEntity<Object> updateResidencyInclude(@PathVariable(value = "tenantId", required = true) String tenantId, 
			                                             @PathVariable(value = "residencyIncludeId", required = true) Long residencyIncludeId, 
			                                             @Valid @RequestBody ResidencyIncludeUpdateResource residencyIncludeUpdateResource) {
		if(LogginAuthentcation.getInstance().getUserName()==null || LogginAuthentcation.getInstance().getUserName().isEmpty()) { 
 			throw new UserNotFound(environment.getProperty(userNotFound));
 		}
		
		SuccessAndErrorDetails successAndErrorDetails=new SuccessAndErrorDetails();
		Optional<ResidencyInclude> isPresentResidencyInclude = residencyIncludeService.findById(tenantId, residencyIncludeId);
		if(isPresentResidencyInclude.isPresent()) {
			ResidencyInclude residencyInclude = residencyIncludeService.updateResidencyInclude(tenantId, residencyIncludeId, residencyIncludeUpdateResource, LogginAuthentcation.getInstance().getUserName());
			successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty("rec.updated"), residencyInclude.getId().toString());
        	return new ResponseEntity<>(successAndErrorDetails,HttpStatus.OK);
		}
		else {
			successAndErrorDetails.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(successAndErrorDetails, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
	

}
