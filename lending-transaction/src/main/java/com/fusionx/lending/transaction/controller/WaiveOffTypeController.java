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
import com.fusionx.lending.transaction.domain.WaiveOffType;
import com.fusionx.lending.transaction.enums.BankTransactionCodeStatus;
import com.fusionx.lending.transaction.exception.UserNotFound;
import com.fusionx.lending.transaction.resource.SuccessAndErrorDetails;
import com.fusionx.lending.transaction.resource.WaiveOffTypeAddResource;
import com.fusionx.lending.transaction.resource.WaiveOffTypeUpdateResource;
import com.fusionx.lending.transaction.service.WaiveOffTypeService;

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
 * 1        01-12-2021      -               FXL-1390     Ishan                   Created
 * <p>
 *
 */

@RestController
@RequestMapping(value = "/waive-off-type")
@CrossOrigin(origins = "*")
public class WaiveOffTypeController extends MessagePropertyBase {

	private WaiveOffTypeService waiveOffTypeService;
	@Autowired
	public void setWaiveOffTypeService(WaiveOffTypeService waiveOffTypeService) {
		this.waiveOffTypeService = waiveOffTypeService;
	}
	
	/**
	 * Gets all waive off type list
	 *
	 * @param tenantId the id of tenant
	 * @return the list of all waive off type list
	*/
	@GetMapping(value = "/{tenantId}/all")
	public ResponseEntity<Object> getAllWaiveOffType(@PathVariable(value = "tenantId") String tenantId) {
		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
        List<WaiveOffType> waiveOffType = waiveOffTypeService.findAll();
        int size = waiveOffType.size();
        if (size > 0) {
            return new ResponseEntity<>(waiveOffType, HttpStatus.OK);
        } else {
            responseMessage.setMessages(RECORD_NOT_FOUND);
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
	}
	
    /**
     * Get waive off type by id
     *
     * @param tenantId the id of tenant
     * @param id       the id of the record
     * @return waive off type if record exists, otherwise <code>204</code>
     */
	@GetMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> getWaiveOffTypeById(@PathVariable(value = "tenantId") String tenantId,
	                                                       @PathVariable(value = "id") Long id) {
	    Optional<WaiveOffType> isPresentWaiveOffType = waiveOffTypeService.findById(id);
	    if (isPresentWaiveOffType.isPresent()) {
	    	return new ResponseEntity<>(isPresentWaiveOffType.get(), HttpStatus.OK);
	    } else {
	    	SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
	        responseMessage.setMessages(RECORD_NOT_FOUND);
	        return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
	    }
	}
	
	/**
     * Get waive off type by code
     *
     * @param tenantId the id of tenant
     * @param code       the code of the record
     * @return waive off type if record exists, otherwise <code>204</code>
     */
	@GetMapping(value = "/{tenantId}/code/{code}")
	public ResponseEntity<Object> getWaiveOffTypeByCode(@PathVariable(value = "tenantId") String tenantId,
														@PathVariable(value = "code", required = true) String code) {
		List<WaiveOffType> isPresentWaiveOffType = waiveOffTypeService.findByCode(code);
		if (!isPresentWaiveOffType.isEmpty()) {
			return new ResponseEntity<>(isPresentWaiveOffType, HttpStatus.OK);
		} else {
			SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
			responseMessage.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
     * Get waive off type by status
     *
     * @param tenantId the id of tenant
     * @param name   the name
     * @return the list of all waive off type if record exists, otherwise <code>204 - No Content</code>
     */
	@GetMapping(value = "/{tenantId}/status/{status}")
	public ResponseEntity<Object> getAllWaiveOffTypeByStatus(@PathVariable(value = "tenantId") String tenantId,
														  @PathVariable(value = "status", required = true) String status) {
		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
		
		if(status.equals(BankTransactionCodeStatus.ACTIVE.toString()) || status.equals(BankTransactionCodeStatus.INACTIVE.toString())) {
			List<WaiveOffType> isPresentWaiveOffType = waiveOffTypeService.findByStatus(status);
			if (!isPresentWaiveOffType.isEmpty()) {
				return new ResponseEntity<>(isPresentWaiveOffType, HttpStatus.OK);
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
     * Get waive off type by name
     *
     * @param tenantId the id of tenant
     * @param name   the name
     * @return the list of all waive off type if record exists, otherwise <code>204 - No Content</code>
     */
	@GetMapping(value = "/{tenantId}/name/{name}")
	public ResponseEntity<Object> getAllWaiveOffTypeByName(@PathVariable(value = "tenantId") String tenantId,
														  @PathVariable(value = "name", required = true) String name) {
		List<WaiveOffType> isPresentWaiveOffType = waiveOffTypeService.findByName(name);
		if (!isPresentWaiveOffType.isEmpty()) {
			return new ResponseEntity<>(isPresentWaiveOffType, HttpStatus.OK);
		} else {
			SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
			responseMessage.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
     * Saves the waive off type
     *
     * @param tenantId                  the id of the tenant
     * @param waiveOffTypeAddResource the object to save
     * @return message if record successfully saved.
     * @see WaiveOffTypeAddResource
     */
    @PostMapping(value = "/{tenantId}")
	public ResponseEntity<Object> addWaiveOffType(@PathVariable(value = "tenantId") String tenantId,
									@Valid @RequestBody WaiveOffTypeAddResource waiveOffTypeAddResource){
		if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty()) {
            throw new UserNotFound(environment.getProperty(COMMON_USER_NOT_FOUND));
        }
        SuccessAndErrorDetails successAndErrorDetails;
        WaiveOffType waiveOffType = waiveOffTypeService.addWaiveOffType(tenantId, waiveOffTypeAddResource);
        successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty(RECORD_CREATED), Long.toString(waiveOffType.getId()));
        return new ResponseEntity<>(successAndErrorDetails, HttpStatus.CREATED);
	}
    
    /**
     * Updates the waive off type
     *
     * @param tenantId                     the id of the tenant
     * @param id                           the id of the object
     * @param waiveOffTypeUpdateResource the object which contains data
     * @return the message
     * @see WaiveOffTypeUpdateResource
     */
    @PutMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> updateWaiveOffType(@PathVariable(value = "tenantId") String tenantId,
													 @PathVariable(value = "id") Long id,
													 @Valid @RequestBody WaiveOffTypeUpdateResource waiveOffTypeUpdateResource){
		if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty()) {
            throw new UserNotFound(environment.getProperty(COMMON_USER_NOT_FOUND));
        }
		
		SuccessAndErrorDetails successAndErrorDetails = new SuccessAndErrorDetails();
        Optional<WaiveOffType> isPresentWaiveOffType = waiveOffTypeService.findById(id);
        if (isPresentWaiveOffType.isPresent()) {
        	WaiveOffType waiveOffType = waiveOffTypeService.updateWaiveOffType(tenantId, id, waiveOffTypeUpdateResource);
            successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty(RECORD_UPDATED), waiveOffType.getId().toString());
            return new ResponseEntity<>(successAndErrorDetails, HttpStatus.OK);
        } else {
            successAndErrorDetails.setMessages(RECORD_NOT_FOUND);
            return new ResponseEntity<>(successAndErrorDetails, HttpStatus.UNPROCESSABLE_ENTITY);
        }
	}
	
}
