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
import com.fusionx.lending.product.domain.FeeCharge;
import com.fusionx.lending.product.domain.FeeChargeTempNotes;
import com.fusionx.lending.product.exception.UserNotFound;
import com.fusionx.lending.product.resources.AddNotesRequest;
import com.fusionx.lending.product.resources.SuccessAndErrorDetails;
import com.fusionx.lending.product.resources.UpdateNotesRequest;
import com.fusionx.lending.product.service.FeeChargeService;
import com.fusionx.lending.product.service.FeeChargeTempNotesService;

@RestController
@RequestMapping(value = "/fee-charge-temp-notes")
@CrossOrigin(origins = "*")
public class FeeChargeTempNotesController {
	

	@Autowired
	private Environment environment;
	
	@Autowired
	private FeeChargeTempNotesService feeChargeCapTempNotesService;
	
	@Autowired
	private FeeChargeService feeChargeService;
	
	private final static String RECORD_NOT_FOUND = "Record not found.";
	private String userNotFound="common.user-not-found";

	/**
	 * get all Fee Charge Template Notes
	 * @param @PathVariable{tenantId}
	 * @param @PathVariable{all}
	 * @return List<Type>
	 */
    @GetMapping(value = "/{tenantId}/all")
    public ResponseEntity<Object> getFeeChargeTempNotes(@PathVariable(value = "tenantId", required = true) String tenantId) {
    	SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
		List<FeeChargeTempNotes> feeChargeTempNotes = feeChargeCapTempNotesService.findAll();
		int size = feeChargeTempNotes.size();
		if (size > 0) {
			return new ResponseEntity<>((Collection<FeeChargeTempNotes>) feeChargeTempNotes,HttpStatus.OK);
		} 
		else {
			responseMessage.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
    
    /**
     * get Fee Charge Template Notes by id
     * @param @PathVariable{tenantId}
     * @param @PathVariable{id}
     * @return Option
     */
 	@GetMapping(value = "/{tenantId}/{id}")
 	public ResponseEntity<Object> getFeeChargeTempNotesById(@PathVariable(value = "tenantId", required = true) String tenantId, 
 			                                                       @PathVariable(value = "id", required = true) Long id) {
 		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
	 	Optional<FeeChargeTempNotes> isPresentFeeChargeTempNotes = feeChargeCapTempNotesService.findById(id);
	 	if (isPresentFeeChargeTempNotes.isPresent()) {
	 		return new ResponseEntity<>(isPresentFeeChargeTempNotes.get(), HttpStatus.OK);
	 	} 
	 	else {
	 		responseMessage.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
	 	}
 	}
    
    /**
     * get Fee Charge Template Notes by fee charge id
     * @param @PathVariable{tenantId}
     * @param @PathVariable{id}
     * @return Option
     */
 	@GetMapping(value = "/{tenantId}/fee-charge/{feeChargeId}")
 	public ResponseEntity<Object> getFeeChargeTempNotesByFeeChargeId(@PathVariable(value = "tenantId", required = true) String tenantId, 
 			                                                                           @PathVariable(value = "feeChargeId", required = true) Long feeChargeId) {
 		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
	 	Optional<FeeCharge> isPresentFeeChargey = feeChargeService.findById(tenantId, feeChargeId);
	 	if (isPresentFeeChargey.isPresent()) {
	 		List<FeeChargeTempNotes> feeChargeTempNotes = feeChargeCapTempNotesService.findByFeeChargeId(feeChargeId);
	 		int size = feeChargeTempNotes.size();
	 		if(size>0) {
	 			return new ResponseEntity<>(feeChargeTempNotes, HttpStatus.OK);
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
     * get Fee Charge Template Notes by status
     * @param @PathVariable{tenantId}
     * @param @PathVariable{status}
     * @return Option
     */
 	@GetMapping(value = "/{tenantId}/status/{status}")
 	public ResponseEntity<Object> getFeeChargeTempNotesByStatus(@PathVariable(value = "tenantId", required = true) String tenantId, 
 			                                                           @PathVariable(value = "status", required = true) String status) {
 		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
 		
 		if(status.equals("ACTIVE") || status.equals("INACTIVE")){
		 	List<FeeChargeTempNotes> isPresentFeeChargeTempNotes = feeChargeCapTempNotesService.findByStatus(status);
		 	int size = isPresentFeeChargeTempNotes.size();
		 	if (size>0) {
		 		return new ResponseEntity<>(isPresentFeeChargeTempNotes, HttpStatus.OK);
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
     * save Fee Charge Template Notes
     * @param @PathVariable{tenantId}
     * @param @RequestBody{AddNotesRequest}
     * @return SuccessAndErrorDetails
     */
 	@PostMapping(value = "/{tenantId}/fee-charge/{feeChargeId}")
 	public ResponseEntity<Object> addFeeChargeTempNotes(@PathVariable(value = "tenantId", required = true) String tenantId,
 			                                                   @PathVariable(value = "feeChargeId", required = true) Long feeChargeId,
 			                                                   @Valid @RequestBody AddNotesRequest addNotesRequest) {
 		if(LogginAuthentcation.getInstance().getUserName()==null || LogginAuthentcation.getInstance().getUserName().isEmpty()) { 
 			throw new UserNotFound(environment.getProperty(userNotFound));
 		}
		
 		SuccessAndErrorDetails successAndErrorDetails = new SuccessAndErrorDetails();
 		Optional<FeeCharge> isPresentFeeCharge = feeChargeService.findById(tenantId,feeChargeId);
        if (isPresentFeeCharge.isPresent()) {
        	FeeChargeTempNotes feeChargeTempNotes = feeChargeCapTempNotesService.addFeeChargeCapTempNotes(tenantId,feeChargeId, addNotesRequest, LogginAuthentcation.getInstance().getUserName());
	 		successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty("rec.saved"),Long.toString(feeChargeTempNotes.getId()));
		     	return new ResponseEntity<>(successAndErrorDetails,HttpStatus.CREATED);
        }
        else {
			successAndErrorDetails.setMessages(environment.getProperty("feeChargeId.invalid"));
			return new ResponseEntity<>(successAndErrorDetails, HttpStatus.UNPROCESSABLE_ENTITY);
		}
 	}
 	
	/**
     * update Fee Charge Template Notes
     * @param @PathVariable{tenantId}
     * @param @RequestBody{UpdateNotesRequest}
     * @return SuccessAndErrorDetailsDto
     */
	@PutMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> updateFeeChargeTempNotes(@PathVariable(value = "tenantId", required = true) String tenantId, 
			                                                      @PathVariable(value = "id", required = true) Long id, 
			                                                      @Valid @RequestBody UpdateNotesRequest updateNotesRequest) {
		if(LogginAuthentcation.getInstance().getUserName()==null || LogginAuthentcation.getInstance().getUserName().isEmpty()) { 
 			throw new UserNotFound(environment.getProperty(userNotFound));
 		}
		
		SuccessAndErrorDetails successAndErrorDetails = new SuccessAndErrorDetails();
		Optional<FeeChargeTempNotes> isPresentFeeChargeTempNotes = feeChargeCapTempNotesService.findById(id);
		if(isPresentFeeChargeTempNotes.isPresent()) {
			FeeChargeTempNotes feeChargeTempNotes = feeChargeCapTempNotesService.updateFeeChargeCapTempNotes(tenantId, id, updateNotesRequest, LogginAuthentcation.getInstance().getUserName());
			successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty("rec.updated"), feeChargeTempNotes.getId().toString());
        	return new ResponseEntity<>(successAndErrorDetails,HttpStatus.OK);
		}
		else {
			successAndErrorDetails.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(successAndErrorDetails, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

}
