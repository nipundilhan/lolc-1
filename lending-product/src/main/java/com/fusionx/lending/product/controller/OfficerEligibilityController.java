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

import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.OfficerEligibility;
import com.fusionx.lending.product.exception.UserNotFound;
import com.fusionx.lending.product.resources.OfficerEligibilityResource;
import com.fusionx.lending.product.resources.OfficerEligibilityUpdateResource;
import com.fusionx.lending.product.resources.SuccessAndErrorDetails;
import com.fusionx.lending.product.service.OfficerEligibilityService;

/**
* Officer Eligibility Controller
* 
********************************************************************************************************
*  ###   Date         Story Point   Task No    Author       Description
*-------------------------------------------------------------------------------------------------------
*    1   09-06-2020   			   	        	Thamokshi    Created
*    
********************************************************************************************************
*/
@RestController
@RequestMapping(value = "/officer-eligibility")
@CrossOrigin(origins = "*")
public class OfficerEligibilityController {

	@Autowired
	Environment environment;

	@Autowired
	private OfficerEligibilityService officerEligibilityService;
	
	private static final String RECORD_NOT_FOUND = "Records not available";
	private String userNotFound="common.user-not-found";
	
	/**
	 * get all Officer Eligibility details
	 * @param @PathVariable{tenantId}
	 * @param @PathVariable{all}
	 * @return List<Type>
	 */
    @GetMapping(value = "/{tenantId}/all")
    public ResponseEntity<Object> getAllOfficerEligibility(@PathVariable(value = "tenantId", required = true) String tenantId) {
    	SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
		List<OfficerEligibility> officerEligibility = officerEligibilityService.findAll();
		int size = officerEligibility.size();
		if (size > 0) {
			return new ResponseEntity<>((Collection<OfficerEligibility>) officerEligibility,HttpStatus.OK);
		} 
		else {
			responseMessage.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
    
    /**
     * get Officer Eligibility by id
     * @param @PathVariable{tenantId}
     * @param @PathVariable{id}
     * @return Option
     */
 	@GetMapping(value = "/{tenantId}/{id}")
 	public ResponseEntity<Object> getOfficerEligibilityById(@PathVariable(value = "tenantId", required = true) String tenantId, 
 			                                                @PathVariable(value = "id", required = true) Long id) {
 		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
	 	Optional<OfficerEligibility> isPresentOfficerEligibility = officerEligibilityService.findById(id);
	 	if (isPresentOfficerEligibility.isPresent()) {
	 		return new ResponseEntity<>(isPresentOfficerEligibility.get(), HttpStatus.OK);
	 	} 
	 	else {
	 		responseMessage.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
	 	}
 	}
 	
 	/**
     * get Officer Eligibility by status
     * @param @PathVariable{tenantId}
     * @param @PathVariable{status}
     * @return Option
     */
 	@GetMapping(value = "/{tenantId}/status/{status}")
 	public ResponseEntity<Object> getOfficerEligibilityByStatus(@PathVariable(value = "tenantId", required = true) String tenantId, 
 			                                                    @PathVariable(value = "status", required = true) String status) {
 		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
 		
 		if(status.equals("ACTIVE") || status.equals("INACTIVE")){
		 	List<OfficerEligibility> isPresentOfficerEligibility = officerEligibilityService.findByStatus(status);
		 	int size = isPresentOfficerEligibility.size();
		 	if (size>0) {
		 		return new ResponseEntity<>(isPresentOfficerEligibility, HttpStatus.OK);
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
     * get Eligibility by eligibilityCode
     * @param @PathVariable{tenantId}
     * @param @PathVariable{id}
     * @return Option
     */
 	@GetMapping(value = "/{tenantId}/code/{code}")
 	public ResponseEntity<Object> getEligibilityByCode(@PathVariable(value = "tenantId", required = true) String tenantId, 
 			                                           @PathVariable(value = "code", required = true) String code) {
 		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
	 	Optional<OfficerEligibility> isPresentEligibility = officerEligibilityService.findByCode(code);
	 	if (isPresentEligibility.isPresent()) {
	 		return new ResponseEntity<>(isPresentEligibility.get(), HttpStatus.OK);
	 	} 
	 	else {
	 		responseMessage.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
	 	}
 	}
 	
 	/**
     * save Officer Eligibility
     * @param @PathVariable{tenantId}
     * @param @RequestBody{OfficerEligibilityAddResource}
     * @return SuccessAndErrorDetailsDto
     */
 	@PostMapping(value = "/{tenantId}")
 	public ResponseEntity<Object> addOfficerEligibility(@PathVariable(value = "tenantId", required = true) String tenantId,
 			                                            @Valid @RequestBody OfficerEligibilityResource officerEligibilityAddResource) {
 		if(LogginAuthentcation.getInstance().getUserName()==null || LogginAuthentcation.getInstance().getUserName().isEmpty()) { 
 			throw new UserNotFound(environment.getProperty(userNotFound));
 		}
		
 		SuccessAndErrorDetails successAndErrorDetails = new SuccessAndErrorDetails();
 		OfficerEligibility officerEligibility = officerEligibilityService.addOfficerEligibility(tenantId,officerEligibilityAddResource);
	 	successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty("rec.saved"),Long.toString(officerEligibility.getId()));
		return new ResponseEntity<>(successAndErrorDetails,HttpStatus.CREATED);
 	}
 	
	/**
     * update Officer Eligibility
     * @param @PathVariable{tenantId}
     * @param @RequestBody{OfficerEligibilityUpdateResource}
     * @return SuccessAndErrorDetailsDto
     */
	@PutMapping(value = "/{tenantId}/{officerEligibilityId}")
	public ResponseEntity<Object> updateOfficerEligibility(@PathVariable(value = "tenantId", required = true) String tenantId, 
			                                               @PathVariable(value = "officerEligibilityId", required = true) Long officerEligibilityId, 
			                                               @Valid @RequestBody OfficerEligibilityUpdateResource officerEligibilityUpdateResource) {
		if(LogginAuthentcation.getInstance().getUserName()==null || LogginAuthentcation.getInstance().getUserName().isEmpty()) { 
 			throw new UserNotFound(environment.getProperty(userNotFound));
 		}
		
		SuccessAndErrorDetails successAndErrorDetails=new SuccessAndErrorDetails();
		Optional<OfficerEligibility> isPresentOfficerEligibility = officerEligibilityService.findById(officerEligibilityId);
		if(isPresentOfficerEligibility.isPresent()) {
			officerEligibilityUpdateResource.setId(officerEligibilityId.toString());
			OfficerEligibility officerEligibility = officerEligibilityService.updateOfficerEligibility(tenantId, officerEligibilityUpdateResource);
			successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty("rec.updated"), officerEligibility.getId().toString());
        	return new ResponseEntity<>(successAndErrorDetails,HttpStatus.OK);
		}
		else {
			successAndErrorDetails.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(successAndErrorDetails, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

}
