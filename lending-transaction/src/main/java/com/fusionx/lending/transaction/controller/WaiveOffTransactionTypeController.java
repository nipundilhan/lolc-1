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
import com.fusionx.lending.transaction.domain.WaiveOffTransactionType;
import com.fusionx.lending.transaction.enums.BankTransactionCodeStatus;
import com.fusionx.lending.transaction.exception.UserNotFound;
import com.fusionx.lending.transaction.resource.SuccessAndErrorDetails;
import com.fusionx.lending.transaction.resource.WaiveOffTransactionTypeAddResource;
import com.fusionx.lending.transaction.resource.WaiveOffTransactionTypeUpdateResource;
import com.fusionx.lending.transaction.service.WaiveOffTransactionTypeService;

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
 * 1        02-12-2021      -               FXL-2110     Ishan                   Created
 * <p>
 *
 */

@RestController
@RequestMapping(value = "/waive-off-transaction-type")
@CrossOrigin(origins = "*")
public class WaiveOffTransactionTypeController extends MessagePropertyBase {

	private WaiveOffTransactionTypeService waiveOffTransactionTypeService;
	@Autowired
	public void setWaiveOffTypeService(WaiveOffTransactionTypeService waiveOffTransactionTypeService) {
		this.waiveOffTransactionTypeService = waiveOffTransactionTypeService;
	}
	
	/**
	 * Gets all waive off transaction type list
	 *
	 * @param tenantId the id of tenant
	 * @return the list of all waive off transaction type list
	*/
	@GetMapping(value = "/{tenantId}/all")
	public ResponseEntity<Object> getAllWaiveOffTransactionType(@PathVariable(value = "tenantId") String tenantId) {
		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
        List<WaiveOffTransactionType> waiveOffTransactionType = waiveOffTransactionTypeService.findAll();
        int size = waiveOffTransactionType.size();
        if (size > 0) {
            return new ResponseEntity<>(waiveOffTransactionType, HttpStatus.OK);
        } else {
            responseMessage.setMessages(RECORD_NOT_FOUND);
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
	}
	
	/**
     * Get waive off transaction type by id
     *
     * @param tenantId the id of tenant
     * @param id       the id of the record
     * @return waive off transaction type if record exists, otherwise <code>204</code>
     */
	@GetMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> getWaiveOffTransactionTypeById(@PathVariable(value = "tenantId") String tenantId,
	                                                        	 @PathVariable(value = "id") Long id) {
	    Optional<WaiveOffTransactionType> optwaiveOffTransactionTypeService = waiveOffTransactionTypeService.findById(id);
	    if (optwaiveOffTransactionTypeService.isPresent()) {
	    	return new ResponseEntity<>(optwaiveOffTransactionTypeService.get(), HttpStatus.OK);
	    } else {
	    	SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
	        responseMessage.setMessages(RECORD_NOT_FOUND);
	        return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
	    }
	}
	
	/**
     * Get waive off transaction type by status
     *
     * @param tenantId the id of tenant
     * @param name   the name
     * @return the list of all waive off transaction type if record exists, otherwise <code>204 - No Content</code>
     */
	@GetMapping(value = "/{tenantId}/status/{status}")
	public ResponseEntity<Object> getAllWaiveOffTransactionTypeByStatus(@PathVariable(value = "tenantId") String tenantId,
														  @PathVariable(value = "status", required = true) String status) {
		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
		
		if(status.equals(BankTransactionCodeStatus.ACTIVE.toString()) || status.equals(BankTransactionCodeStatus.INACTIVE.toString())) {
			List<WaiveOffTransactionType> optwaiveOffTransactionTypeService = waiveOffTransactionTypeService.findByStatus(status);
			if (!optwaiveOffTransactionTypeService.isEmpty()) {
				return new ResponseEntity<>(optwaiveOffTransactionTypeService, HttpStatus.OK);
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
     * Get all waive off transaction type by waive off type id
     *
     * @param tenantId the id of tenant
     * @param waiveOffTypeId    the waiveOffTypeId of the record
     * @return all waive off transaction type if record exists, otherwise <code>204</code>
     */
	@GetMapping(value = "/{tenantId}/waive-off-type/{waiveOffTypeId}")
	public ResponseEntity<Object> getAllWaiveOffTransactionTypeByWaiveOffTypeId(@PathVariable(value = "tenantId") String tenantId,
	                                                        	 @PathVariable(value = "waiveOffTypeId") Long waiveOffTypeId) {
	    List<WaiveOffTransactionType> allWaiveOffTransactionTypeService = waiveOffTransactionTypeService.findByWaiveOffTypeId(waiveOffTypeId);
	    if (!allWaiveOffTransactionTypeService.isEmpty()) {
	    	return new ResponseEntity<>(allWaiveOffTransactionTypeService, HttpStatus.OK);
	    } else {
	    	SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
	        responseMessage.setMessages(RECORD_NOT_FOUND);
	        return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
	    }
	}
	
	/**
     * Saves the waive off transaction type
     *
     * @param tenantId                  the id of the tenant
     * @param waiveOffTransactionTypeAddResource the object to save
     * @return message if record successfully saved.
     * @see WaiveOffTransactionTypeAddResource
     */
    @PostMapping(value = "/{tenantId}")
	public ResponseEntity<Object> addWaiveOffTransactionType(@PathVariable(value = "tenantId") String tenantId,
									@Valid @RequestBody WaiveOffTransactionTypeAddResource waiveOffTransactionTypeAddResource){
		if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty()) {
            throw new UserNotFound(environment.getProperty(COMMON_USER_NOT_FOUND));
        }
        SuccessAndErrorDetails successAndErrorDetails;
        WaiveOffTransactionType coreProduct = waiveOffTransactionTypeService.addWaiveOffTransactionType(tenantId, waiveOffTransactionTypeAddResource);
        successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty(RECORD_CREATED), Long.toString(coreProduct.getId()));
        return new ResponseEntity<>(successAndErrorDetails, HttpStatus.CREATED);
	}
    
    /**
     * Updates the waive off transaction type
     *
     * @param tenantId                     the id of the tenant
     * @param id                           the id of the object
     * @param waiveOffTransactionTypeUpdateResource the object which contains data
     * @return the message
     * @see WaiveOffTransactionTypeUpdateResource
     */
    @PutMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> updateWaiveOffTransactionType(@PathVariable(value = "tenantId") String tenantId,
													 @PathVariable(value = "id") Long id,
													 @Valid @RequestBody WaiveOffTransactionTypeUpdateResource waiveOffTransactionTypeUpdateResource){
		if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty()) {
            throw new UserNotFound(environment.getProperty(COMMON_USER_NOT_FOUND));
        }
		
		SuccessAndErrorDetails successAndErrorDetails = new SuccessAndErrorDetails();
        Optional<WaiveOffTransactionType> optWaiveOffTransactionType = waiveOffTransactionTypeService.findById(id);
        if (optWaiveOffTransactionType.isPresent()) {
        	WaiveOffTransactionType waiveOffTransactionType = waiveOffTransactionTypeService.updateWaiveOffTransactionType(tenantId, id, waiveOffTransactionTypeUpdateResource);
            successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty(RECORD_UPDATED), waiveOffTransactionType.getId().toString());
            return new ResponseEntity<>(successAndErrorDetails, HttpStatus.OK);
        } else {
            successAndErrorDetails.setMessages(RECORD_NOT_FOUND);
            return new ResponseEntity<>(successAndErrorDetails, HttpStatus.UNPROCESSABLE_ENTITY);
        }
	}
	
}
