package com.fusionx.lending.product.controller;

/**
 * Residency Eligibility Notes service
 * @author 	Rangana
 * @Dat     30-06-2021
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   30-06-2021   FX-6       FX-6819     Rangana      Created
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
import com.fusionx.lending.product.domain.ResidencyEligibilityNotes;
import com.fusionx.lending.product.exception.UserNotFound;
import com.fusionx.lending.product.resources.AddNotesRequest;
import com.fusionx.lending.product.resources.SuccessAndErrorDetails;
import com.fusionx.lending.product.resources.UpdateNotesRequest;
import com.fusionx.lending.product.service.ResidencyEligibilityNotesService;
import com.fusionx.lending.product.service.ResidencyEligibilityService;

@RestController
@RequestMapping(value = "/residency-eligibility-notes")
@CrossOrigin(origins = "*")
public class ResidencyEligibilityNotesController {
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private ResidencyEligibilityNotesService residencyEligibilityNotesService;
	
	@Autowired
	private ResidencyEligibilityService residencyEligibilityService;
	
	private final static String RECORD_NOT_FOUND = "Record not found.";
	private String userNotFound="common.user-not-found";
	
	/**
	 * get all Residency Eligibility Notes
	 * @param @PathVariable{tenantId}
	 * @param @PathVariable{all}
	 * @return List<Type>
	 */
    @GetMapping(value = "/{tenantId}/all")
    public ResponseEntity<Object> getResidencyEligibilityNotes(@PathVariable(value = "tenantId", required = true) String tenantId) {
    	SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
		List<ResidencyEligibilityNotes> residencyEligibilityNotes = residencyEligibilityNotesService.findAll();
		int size = residencyEligibilityNotes.size();
		if (size > 0) {
			return new ResponseEntity<>((Collection<ResidencyEligibilityNotes>) residencyEligibilityNotes,HttpStatus.OK);
		} 
		else {
			responseMessage.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
    
    /**
     * get Residency Eligibility Notes by id
     * @param @PathVariable{tenantId}
     * @param @PathVariable{id}
     * @return Option
     */
 	@GetMapping(value = "/{tenantId}/{residencyEligibilityNotesId}")
 	public ResponseEntity<Object> getResidencyEligibilityNotesById(@PathVariable(value = "tenantId", required = true) String tenantId, 
 			                                                       @PathVariable(value = "residencyEligibilityNotesId", required = true) Long residencyEligibilityNotesId) {
 		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
	 	Optional<ResidencyEligibilityNotes> isPresentResidencyEligibilityNotes = residencyEligibilityNotesService.findById(residencyEligibilityNotesId);
	 	if (isPresentResidencyEligibilityNotes.isPresent()) {
	 		return new ResponseEntity<>(isPresentResidencyEligibilityNotes.get(), HttpStatus.OK);
	 	} 
	 	else {
	 		responseMessage.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
	 	}
 	}
    
    /**
     * get Residency Eligibility Notes by id
     * @param @PathVariable{tenantId}
     * @param @PathVariable{id}
     * @return Option
     */
 	@GetMapping(value = "/{tenantId}/residency-eligibility/{residencyEligibilityId}")
 	public ResponseEntity<Object> getResidencyEligibilityNotesByResidencyEligibilityId(@PathVariable(value = "tenantId", required = true) String tenantId, 
 			                                                                           @PathVariable(value = "residencyEligibilityId", required = true) Long residencyEligibilityId) {
 		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
	 	Optional<ResidencyEligibility> isPresentResidencyEligibility = residencyEligibilityService.findById(tenantId, residencyEligibilityId);
	 	if (isPresentResidencyEligibility.isPresent()) {
	 		List<ResidencyEligibilityNotes> residencyEligibilityNotes = residencyEligibilityNotesService.findByResidencyEligiId(residencyEligibilityId);
	 		int size = residencyEligibilityNotes.size();
	 		if(size>0) {
	 			return new ResponseEntity<>(residencyEligibilityNotes, HttpStatus.OK);
	 		}
	 		else {
		 		responseMessage.setMessages(RECORD_NOT_FOUND);
				return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
	 		} 
	 	}
	 	else {
	 		responseMessage.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
	 	}
 	}
 	
 	/**
     * get Residency Eligibility Notes by status
     * @param @PathVariable{tenantId}
     * @param @PathVariable{status}
     * @return Option
     */
 	@GetMapping(value = "/{tenantId}/status/{status}")
 	public ResponseEntity<Object> getResidencyEligibilityNotesByStatus(@PathVariable(value = "tenantId", required = true) String tenantId, 
 			                                                           @PathVariable(value = "status", required = true) String status) {
 		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
 		
 		if(status.equals("ACTIVE") || status.equals("INACTIVE")){
		 	List<ResidencyEligibilityNotes> isPresentResidencyEligibilityNotes = residencyEligibilityNotesService.findByStatus(status);
		 	int size = isPresentResidencyEligibilityNotes.size();
		 	if (size>0) {
		 		return new ResponseEntity<>(isPresentResidencyEligibilityNotes, HttpStatus.OK);
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
     * save Residency Eligibility Notes
     * @param @PathVariable{tenantId}
     * @param @RequestBody{AddNotesRequest}
     * @return SuccessAndErrorDetails
     */
 	@PostMapping(value = "/{tenantId}/residency-eligibility/{residencyEligibilityId}")
 	public ResponseEntity<Object> addResidencyEligibilityNotes(@PathVariable(value = "tenantId", required = true) String tenantId,
 			                                                   @PathVariable(value = "residencyEligibilityId", required = true) Long residencyEligibilityId,
 			                                                   @Valid @RequestBody AddNotesRequest addNotesRequest) {
 		if(LogginAuthentcation.getInstance().getUserName()==null || LogginAuthentcation.getInstance().getUserName().isEmpty()) { 
 			throw new UserNotFound(environment.getProperty(userNotFound));
 		}
		
 		SuccessAndErrorDetails successAndErrorDetails = new SuccessAndErrorDetails();
 		Optional<ResidencyEligibility> isPresentResidencyEligibility = residencyEligibilityService.findById(tenantId,residencyEligibilityId);
        if (isPresentResidencyEligibility.isPresent()) {
        	ResidencyEligibilityNotes residencyEligibilityNotes = residencyEligibilityNotesService.addResidencyEligibilityNotes(tenantId,residencyEligibilityId, addNotesRequest, LogginAuthentcation.getInstance().getUserName());
	 		successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty("rec.saved"),Long.toString(residencyEligibilityNotes.getId()));
		     	return new ResponseEntity<>(successAndErrorDetails,HttpStatus.CREATED);
        }
        else {
			successAndErrorDetails.setMessages(environment.getProperty("residencyEligibilityId.invalid"));
			return new ResponseEntity<>(successAndErrorDetails, HttpStatus.UNPROCESSABLE_ENTITY);
		}
 	}
 	
	/**
     * update Residency Eligibility Notes
     * @param @PathVariable{tenantId}
     * @param @RequestBody{UpdateNotesRequest}
     * @return SuccessAndErrorDetailsDto
     */
	@PutMapping(value = "/{tenantId}/{residencyEligibilityNotesId}")
	public ResponseEntity<Object> updateResidencyEligibilityNotes(@PathVariable(value = "tenantId", required = true) String tenantId, 
			                                                      @PathVariable(value = "residencyEligibilityNotesId", required = true) Long residencyEligibilityNotesId, 
			                                                      @Valid @RequestBody UpdateNotesRequest updateNotesRequest) {
		if(LogginAuthentcation.getInstance().getUserName()==null || LogginAuthentcation.getInstance().getUserName().isEmpty()) { 
 			throw new UserNotFound(environment.getProperty(userNotFound));
 		}
		
		SuccessAndErrorDetails successAndErrorDetails = new SuccessAndErrorDetails();
		Optional<ResidencyEligibilityNotes> isPresentResidencyEligibilityNotes = residencyEligibilityNotesService.findById(residencyEligibilityNotesId);
		if(isPresentResidencyEligibilityNotes.isPresent()) {
			ResidencyEligibilityNotes residencyEligibilityNotes = residencyEligibilityNotesService.updateResidencyEligibilityNotes(tenantId, residencyEligibilityNotesId, updateNotesRequest, LogginAuthentcation.getInstance().getUserName());
			successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty("rec.updated"), residencyEligibilityNotes.getId().toString());
        	return new ResponseEntity<>(successAndErrorDetails,HttpStatus.OK);
		}
		else {
			successAndErrorDetails.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(successAndErrorDetails, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

}
