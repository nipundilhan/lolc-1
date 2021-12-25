package com.fusionx.lending.transaction.controller;

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

import com.fusionx.lending.transaction.base.MessagePropertyBase;
import com.fusionx.lending.transaction.core.LogginAuthentcation;
import com.fusionx.lending.transaction.domain.WaiveOffApprovalTypes;
import com.fusionx.lending.transaction.enums.BankTransactionCodeStatus;
import com.fusionx.lending.transaction.exception.UserNotFound;
import com.fusionx.lending.transaction.resource.SuccessAndErrorDetails;
import com.fusionx.lending.transaction.resource.WaiveOffApprovalTypesAddResource;
import com.fusionx.lending.transaction.resource.WaiveOffApprovalTypesUpdateResource;
import com.fusionx.lending.transaction.service.WaiveOffApprovalTypesService;

/**
 * API Service related to Allocation Template.
 *
 * @author Ishan
 * @version 1.0.0
 * @since 1.0.0
 * <p>
 * <br/><br/>
 * <b>Change History : </b>
 * <br/><br/>
 * #        Date            Story Point     Task No     Author                  Description
 * <br/>
 * .....................................................................................................................<br/>
 * 1        06-12-2021      -               FXL-2112     Ishan                   Created
 * <p>
 *
 */

@RestController
@RequestMapping(value = "/waive-off-approval-type")
@CrossOrigin(origins = "*")
public class WaiveOffApprovalTypesController extends MessagePropertyBase {

	private WaiveOffApprovalTypesService waiveOffApprovalTypesService;
	@Autowired
	public void setWaiveOffTypeService(WaiveOffApprovalTypesService waiveOffApprovalTypesService) {
		this.waiveOffApprovalTypesService = waiveOffApprovalTypesService;
	}
	
	/**
	 * Gets all waive off approval types list
	 *
	 * @param tenantId the id of tenant
	 * @return the list of all waive off type list
	*/
	@GetMapping(value = "/{tenantId}/all")
	public ResponseEntity<Object> getAllWaiveOffApprovalTypea(@PathVariable(value = "tenantId") String tenantId) {
		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
        List<WaiveOffApprovalTypes> waiveOffApprovalTypes = waiveOffApprovalTypesService.findAll();
        int size = waiveOffApprovalTypes.size();
        if (size > 0) {
            return new ResponseEntity<>(waiveOffApprovalTypes, HttpStatus.OK);
        } else {
            responseMessage.setMessages(RECORD_NOT_FOUND);
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
	}
	
	/**
     * Get waive off approval types by id
     *
     * @param tenantId the id of tenant
     * @param id       the id of the record
     * @return waive off approval types if record exists, otherwise <code>204</code>
     */
	@GetMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> getWaiveOffApprovalTypesById(@PathVariable(value = "tenantId") String tenantId,
	                                                       @PathVariable(value = "id") Long id) {
	    Optional<WaiveOffApprovalTypes> isPresentwaiveOffApprovalTypes = waiveOffApprovalTypesService.findById(id);
	    if (isPresentwaiveOffApprovalTypes.isPresent()) {
	    	return new ResponseEntity<>(isPresentwaiveOffApprovalTypes.get(), HttpStatus.OK);
	    } else {
	    	SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
	        responseMessage.setMessages(RECORD_NOT_FOUND);
	        return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
	    }
	}
	
	/**
     * Get waive off approval types by status
     *
     * @param tenantId the id of tenant
     * @param name   the name
     * @return the list of all waive off approval types if record exists, otherwise <code>204 - No Content</code>
     */
	@GetMapping(value = "/{tenantId}/status/{status}")
	public ResponseEntity<Object> getAllWaiveOffApprovalTypesByStatus(@PathVariable(value = "tenantId") String tenantId,
														  @PathVariable(value = "status", required = true) String status) {
		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
		
		if(status.equals(BankTransactionCodeStatus.ACTIVE.toString()) || status.equals(BankTransactionCodeStatus.INACTIVE.toString())) {
			List<WaiveOffApprovalTypes> waiveOffApprovalTypes = waiveOffApprovalTypesService.findByStatus(status);
			if (!waiveOffApprovalTypes.isEmpty()) {
				return new ResponseEntity<>(waiveOffApprovalTypes, HttpStatus.OK);
			} else {
				responseMessage.setMessages(RECORD_NOT_FOUND);
				return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
			}
		} else {
			responseMessage.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}	
	}
	
	/**
     * Get all waive off approval types by waive off type id
     *
     * @param tenantId the id of tenant
     * @param waiveOffTypeId    the waiveOffTypeId of the record
     * @return all waive off approval types if record exists, otherwise <code>204</code>
     */
	@GetMapping(value = "/{tenantId}/waive-off-type-id/{waiveOffTypeId}")
	public ResponseEntity<Object> getAllWaiveOffApprovalTypesByWaiveOffTypeId(@PathVariable(value = "tenantId") String tenantId,
	                                                        	 @PathVariable(value = "waiveOffTypeId") Long waiveOffTypeId) {
		List<WaiveOffApprovalTypes> waiveOffApprovalTypes = waiveOffApprovalTypesService.findByWaiveOffTypeId(waiveOffTypeId);
	    if (!waiveOffApprovalTypes.isEmpty()) {
	    	return new ResponseEntity<>(waiveOffApprovalTypes, HttpStatus.OK);
	    } else {
	    	SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
	        responseMessage.setMessages(RECORD_NOT_FOUND);
	        return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
	    }
	}
	
	/**
     * Get all waive off approval types by waive off approval group id
     *
     * @param tenantId the id of tenant
     * @param waiveOffApprovalGroupId    the waiveOffApprovalGroupId of the record
     * @return all waive off approval types if record exists, otherwise <code>204</code>
     */
	@GetMapping(value = "/{tenantId}/waive-off-approval-group-id/{waiveOffApprovalGroupId}")
	public ResponseEntity<Object> getAllWaiveOffApprovalTypesByWaiveOffApprovalGroupId(@PathVariable(value = "tenantId") String tenantId,
	                                                        	 @PathVariable(value = "waiveOffApprovalGroupId") Long waiveOffApprovalGroupId) {
		List<WaiveOffApprovalTypes> waiveOffApprovalTypes = waiveOffApprovalTypesService.findByWaiveOffApprovalGroupId(waiveOffApprovalGroupId);
	    if (!waiveOffApprovalTypes.isEmpty()) {
	    	return new ResponseEntity<>(waiveOffApprovalTypes, HttpStatus.OK);
	    } else {
	    	SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
	        responseMessage.setMessages(RECORD_NOT_FOUND);
	        return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
	    }
	}
	
	/**
     * Saves the waive off approval types
     *
     * @param tenantId                  the id of the tenant
     * @param waiveOffApprovalTypesAddResource the object to save
     * @return message if record successfully saved.
     * @see WaiveOffApprovalTypesAddResource
     */
    @PostMapping(value = "/{tenantId}")
	public ResponseEntity<Object> addWaiveOffApprovalTypes(@PathVariable(value = "tenantId") String tenantId,
									@Valid @RequestBody WaiveOffApprovalTypesAddResource waiveOffApprovalTypesAddResource){
		if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty()) {
            throw new UserNotFound(environment.getProperty(COMMON_USER_NOT_FOUND));
        }
        SuccessAndErrorDetails successAndErrorDetails;
        WaiveOffApprovalTypes waiveOffApprovalTypes = waiveOffApprovalTypesService.addWaiveOffApprovalTypes(tenantId, waiveOffApprovalTypesAddResource);
        successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty(RECORD_CREATED), Long.toString(waiveOffApprovalTypes.getId()));
        return new ResponseEntity<>(successAndErrorDetails, HttpStatus.CREATED);
	}
    
    /**
     * Updates the waive off approval types
     *
     * @param tenantId                     the id of the tenant
     * @param id                           the id of the object
     * @param waiveOffApprovalTypesUpdateResource the object which contains data
     * @return the message
     * @see WaiveOffApprovalTypesUpdateResource
     */
    @PutMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> updateWaiveOffApprovalTypes(@PathVariable(value = "tenantId") String tenantId,
													 @PathVariable(value = "id") Long id,
													 @Valid @RequestBody WaiveOffApprovalTypesUpdateResource waiveOffApprovalTypesUpdateResource){
		if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty()) {
            throw new UserNotFound(environment.getProperty(COMMON_USER_NOT_FOUND));
        }
		
		SuccessAndErrorDetails successAndErrorDetails = new SuccessAndErrorDetails();
        Optional<WaiveOffApprovalTypes> optWaiveOffApprovalTypes = waiveOffApprovalTypesService.findById(id);
        if (optWaiveOffApprovalTypes.isPresent()) {
        	WaiveOffApprovalTypes waiveOffApprovalTypes = waiveOffApprovalTypesService.updateWaiveOffApprovalTypes(tenantId, id, waiveOffApprovalTypesUpdateResource);
            successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty(RECORD_UPDATED), waiveOffApprovalTypes.getId().toString());
            return new ResponseEntity<>(successAndErrorDetails, HttpStatus.OK);
        } else {
            successAndErrorDetails.setMessages(RECORD_NOT_FOUND);
            return new ResponseEntity<>(successAndErrorDetails, HttpStatus.UNPROCESSABLE_ENTITY);
        }
	}
	
}
