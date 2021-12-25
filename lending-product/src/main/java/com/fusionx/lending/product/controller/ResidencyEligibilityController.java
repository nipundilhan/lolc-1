package com.fusionx.lending.product.controller;

/**
 * Residency eligibility service
 * @author 	Rangana
 * @Dat     07-06-2021
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   07-06-2019     FX-6        FX-6523    Rangana      Created
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
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.exception.UserNotFound;
import com.fusionx.lending.product.resources.ResidencyEligibilityResource;
import com.fusionx.lending.product.resources.ResidencyEligibilityUpdateResource;
import com.fusionx.lending.product.resources.SuccessAndErrorDetails;
import com.fusionx.lending.product.service.ResidencyEligibilityService;

@RestController
@RequestMapping(value = "/residency-eligibility")
@CrossOrigin(origins = "*")
public class ResidencyEligibilityController {

	@Autowired
	Environment environment;
	
	@Autowired
	public ResidencyEligibilityService residencyEligibilityService;
	
	private static final String RECORD_NOT_FOUND = "Records not available";
	private String userNotFound="common.user-not-found";
	
	/**
	 * get all Residency Eligibility details
	 * @param @PathVariable{tenantId}
	 * @param @PathVariable{all}
	 * @return List<Type>
	 */
    @GetMapping(value = "/{tenantId}/all")
    public ResponseEntity<Object> getAllResidencyEligibility(@PathVariable(value = "tenantId", required = true) String tenantId) {
    	SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
		List<ResidencyEligibility> residencyEligibility = residencyEligibilityService.findAll(tenantId);
		int size = residencyEligibility.size();
		if (size > 0) {
			return new ResponseEntity<>((Collection<ResidencyEligibility>) residencyEligibility,HttpStatus.OK);
		} 
		else {
			responseMessage.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
    
    /**
     * get Residency Eligibility by id
     * @param @PathVariable{tenantId}
     * @param @PathVariable{id}
     * @return Option
     */
 	@GetMapping(value = "/{tenantId}/{id}")
 	public ResponseEntity<Object> getResidencyEligibilityById(@PathVariable(value = "tenantId", required = true) String tenantId, 
 			                                                  @PathVariable(value = "id", required = true) Long id) {
 		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
	 	Optional<ResidencyEligibility> isPresentResidencyEligibility = residencyEligibilityService.findById(tenantId, id);
	 	if (isPresentResidencyEligibility.isPresent()) {
	 		return new ResponseEntity<>(isPresentResidencyEligibility.get(), HttpStatus.OK);
	 	} 
	 	else {
	 		responseMessage.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
	 	}
 	}
 	
    /**
     * get Residency Eligibility Detail by id
     * @param @PathVariable{tenantId}
     * @param @PathVariable{id}
     * @return Option
     */
 	@GetMapping(value = "/{tenantId}/detail/{residencyEligibilityId}")
 	public ResponseEntity<Object> getResidencyEligibilityDetailById(@PathVariable(value = "tenantId", required = true) String tenantId, 
 			                                                        @PathVariable(value = "residencyEligibilityId", required = true) Long residencyEligibilityId) {
 		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
	 	Optional<ResidencyEligibility> isPresentResidencyEligibility = residencyEligibilityService.findResidencyEligibilityDetailById(tenantId, residencyEligibilityId);
	 	if (isPresentResidencyEligibility.isPresent()) {
	 		return new ResponseEntity<>(isPresentResidencyEligibility.get(), HttpStatus.OK);
	 	} 
	 	else {
	 		responseMessage.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
	 	}
 	}
 	
 	/**
     * get Residency Eligibility by status
     * @param @PathVariable{tenantId}
     * @param @PathVariable{status}
     * @return Option
     */
 	@GetMapping(value = "/{tenantId}/status/{status}")
 	public ResponseEntity<Object> getResidencyEligibilityByStatus(@PathVariable(value = "tenantId", required = true) String tenantId, 
 			                                                      @PathVariable(value = "status", required = true) String status) {
 		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
 		
 		if(status.equals(CommonStatus.ACTIVE.toString()) || status.equals(CommonStatus.INACTIVE.toString())){
		 	List<ResidencyEligibility> isPresentResidencyEligibility = residencyEligibilityService.findByStatus(tenantId, status);
		 	int size = isPresentResidencyEligibility.size();
		 	if (size>0) {
		 		return new ResponseEntity<>(isPresentResidencyEligibility, HttpStatus.OK);
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
 	 * @author Senitha
     * get Eligibility by eligibilityCode
     * @param @PathVariable{tenantId}
     * @param @PathVariable{residencyEligibilityCode}
     * @return Option
     */
 	@GetMapping(value = "/{tenantId}/residency-eligibility-code/{residencyEligibilityCode}")
 	public ResponseEntity<Object> getResidencyEligibilityByCode(@PathVariable(value = "tenantId", required = true) String tenantId, 
 			                                           			@PathVariable(value = "residencyEligibilityCode", required = true) String residencyEligibilityCode) {
 		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
	 	Optional<ResidencyEligibility> isPresentEligibility = residencyEligibilityService.getResidencyEligibilityByCode(residencyEligibilityCode);
	 	if (isPresentEligibility.isPresent()) {
	 		return new ResponseEntity<>(isPresentEligibility.get(), HttpStatus.OK);
	 	} 
	 	else {
	 		responseMessage.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
	 	}
 	}
 	
 	/**
     * save Residency Eligibility
     * @param @PathVariable{tenantId}
     * @param @RequestBody{ResidencyEligibilityAddResource}
     * @return SuccessAndErrorDetailsDto
     */
 	@PostMapping(value = "/{tenantId}")
 	public ResponseEntity<Object> addResidencyEligibility(@PathVariable(value = "tenantId", required = true) String tenantId,
 			                                              @Valid @RequestBody ResidencyEligibilityResource residencyEligibilityAddResource) {
 		if(LogginAuthentcation.getInstance().getUserName()==null || LogginAuthentcation.getInstance().getUserName().isEmpty()) { 
 			throw new UserNotFound(environment.getProperty(userNotFound));
 		}
		
 		ResidencyEligibility residencyEligibility = residencyEligibilityService.addResidencyEligibility(tenantId,residencyEligibilityAddResource, LogginAuthentcation.getInstance().getUserName());
 		SuccessAndErrorDetails successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty("rec.saved"),Long.toString(residencyEligibility.getId()));
		return new ResponseEntity<>(successAndErrorDetails,HttpStatus.CREATED);
 	}
 	
	/**
     * update Residency Eligibility
     * @param @PathVariable{tenantId}
     * @param @RequestBody{ResidencyEligibilityUpdateResource}
     * @return SuccessAndErrorDetailsDto
     */
	@PutMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> updateResidencyEligibility(@PathVariable(value = "tenantId", required = true) String tenantId, 
			                                                 @PathVariable(value = "id", required = true) Long id, 
			                                                 @Valid @RequestBody ResidencyEligibilityUpdateResource residencyEligibilityUpdateResource) {
		if(LogginAuthentcation.getInstance().getUserName()==null || LogginAuthentcation.getInstance().getUserName().isEmpty()) { 
 			throw new UserNotFound(environment.getProperty(userNotFound));
 		}
		
		SuccessAndErrorDetails successAndErrorDetails=new SuccessAndErrorDetails();
		Optional<ResidencyEligibility> isPresentResidencyEligibility = residencyEligibilityService.findById(tenantId, id);
		if(isPresentResidencyEligibility.isPresent()) {
			ResidencyEligibility residencyEligibility = residencyEligibilityService.updateResidencyEligibility(tenantId, id, residencyEligibilityUpdateResource, LogginAuthentcation.getInstance().getUserName());
			successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty("rec.updated"), residencyEligibility.getId().toString());
        	return new ResponseEntity<>(successAndErrorDetails,HttpStatus.OK);
		}
		else {
			successAndErrorDetails.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(successAndErrorDetails, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
	
}
