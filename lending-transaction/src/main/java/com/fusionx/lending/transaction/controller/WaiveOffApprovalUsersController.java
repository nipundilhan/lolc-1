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
import com.fusionx.lending.transaction.domain.WaiveOffApprovalUsers;
import com.fusionx.lending.transaction.enums.BankTransactionCodeStatus;
import com.fusionx.lending.transaction.exception.UserNotFound;
import com.fusionx.lending.transaction.resource.SuccessAndErrorDetails;
import com.fusionx.lending.transaction.resource.WaiveOffApprovalUsersAddResource;
import com.fusionx.lending.transaction.resource.WaiveOffApprovalUsersUpdateResource;
import com.fusionx.lending.transaction.service.WaiveOffApprovalUsersService;

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
 * 1        07-12-2021      -               FXL-2113     Ishan                   Created
 * <p>
 *
 */

@RestController
@RequestMapping(value = "/waive-off-approval-user")
@CrossOrigin(origins = "*")
public class WaiveOffApprovalUsersController extends MessagePropertyBase {

	private WaiveOffApprovalUsersService waiveOffApprovalUsersService;
	@Autowired
	public void setWaiveOffTypeService(WaiveOffApprovalUsersService waiveOffApprovalUsersService) {
		this.waiveOffApprovalUsersService = waiveOffApprovalUsersService;
	}
	
	/**
	 * Gets all waive off approval users list
	 *
	 * @param tenantId the id of tenant
	 * @return the list of all waive off approval users list
	*/
	@GetMapping(value = "/{tenantId}/all")
	public ResponseEntity<Object> getAllWaiveOffApprovalUsers(@PathVariable(value = "tenantId") String tenantId) {
		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
        List<WaiveOffApprovalUsers> waiveOffApprovalUsers = waiveOffApprovalUsersService.findAll();
        int size = waiveOffApprovalUsers.size();
        if (size > 0) {
            return new ResponseEntity<>(waiveOffApprovalUsers, HttpStatus.OK);
        } else {
            responseMessage.setMessages(RECORD_NOT_FOUND);
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
	}
	
	/**
     * Get waive off approval user by id
     *
     * @param tenantId the id of tenant
     * @param id       the id of the record
     * @return waive off approval users if record exists, otherwise <code>204</code>
     */
	@GetMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> getWaiveOffApprovalUsersById(@PathVariable(value = "tenantId") String tenantId,
	                                                        	 @PathVariable(value = "id") Long id) {
	    Optional<WaiveOffApprovalUsers> optWaiveOffApprovalUsers = waiveOffApprovalUsersService.findById(id);
	    if (optWaiveOffApprovalUsers.isPresent()) {
	    	return new ResponseEntity<>(optWaiveOffApprovalUsers.get(), HttpStatus.OK);
	    } else {
	    	SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
	        responseMessage.setMessages(RECORD_NOT_FOUND);
	        return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
	    }
	}
	
	/**
     * Get waive off approval user by status
     *
     * @param tenantId the id of tenant
     * @param status   the status
     * @return the list of all waive off approval user if record exists, otherwise <code>204 - No Content</code>
     */
	@GetMapping(value = "/{tenantId}/status/{status}")
	public ResponseEntity<Object> getAllWaiveOffApprovalUsersByStatus(@PathVariable(value = "tenantId") String tenantId,
														  @PathVariable(value = "status", required = true) String status) {
		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
		
		if(status.equals(BankTransactionCodeStatus.ACTIVE.toString()) || status.equals(BankTransactionCodeStatus.INACTIVE.toString())) {
			List<WaiveOffApprovalUsers> optWaiveOffApprovalUsers = waiveOffApprovalUsersService.findByStatus(status);
			if (!optWaiveOffApprovalUsers.isEmpty()) {
				return new ResponseEntity<>(optWaiveOffApprovalUsers, HttpStatus.OK);
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
     * Get all waive off approval user by waive off approval group id
     *
     * @param tenantId the id of tenant
     * @param waiveOffApprovalGroupId    the waiveOffApprovalGroupId of the record
     * @return all waive off approval users if record exists, otherwise <code>204</code>
     */
	@GetMapping(value = "/{tenantId}/waive-off-approval-group-id/{waiveOffApprovalGroupId}")
	public ResponseEntity<Object> getAllWaiveOffApprovalUserByWaiveOffApprovalGroupId(@PathVariable(value = "tenantId") String tenantId,
	                                                        	 @PathVariable(value = "waiveOffApprovalGroupId") Long waiveOffApprovalGroupId) {
	    List<WaiveOffApprovalUsers> allWaiveOffApprovalUsers = waiveOffApprovalUsersService.findByWaiveOffApprovalGroupId(waiveOffApprovalGroupId);
	    if (!allWaiveOffApprovalUsers.isEmpty()) {
	    	return new ResponseEntity<>(allWaiveOffApprovalUsers, HttpStatus.OK);
	    } else {
	    	SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
	        responseMessage.setMessages(RECORD_NOT_FOUND);
	        return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
	    }
	}
	
	/**
     * Get all waive off approval user by user id
     *
     * @param tenantId the id of tenant
     * @param userId    the userId of the record
     * @return all waive off approval users if record exists, otherwise <code>204</code>
     */
	@GetMapping(value = "/{tenantId}/user-id/{userId}")
	public ResponseEntity<Object> getAllWaiveOffApprovalUserByuserId(@PathVariable(value = "tenantId") String tenantId,
	                                                        	 @PathVariable(value = "userId") String userId) {
	    List<WaiveOffApprovalUsers> allWaiveOffApprovalUsers = waiveOffApprovalUsersService.findByUserId(userId);
	    if (!allWaiveOffApprovalUsers.isEmpty()) {
	    	return new ResponseEntity<>(allWaiveOffApprovalUsers, HttpStatus.OK);
	    } else {
	    	SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
	        responseMessage.setMessages(RECORD_NOT_FOUND);
	        return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
	    }
	}
	
	/**
     * Saves the waive off approval users
     *
     * @param tenantId                  the id of the tenant
     * @param waiveOffApprovalUsersAddResource the object to save
     * @return message if record successfully saved.
     * @see WaiveOffApprovalUsersAddResource
     */
    @PostMapping(value = "/{tenantId}")
	public ResponseEntity<Object> addWaiveOffApprovalUsers(@PathVariable(value = "tenantId") String tenantId,
									@Valid @RequestBody WaiveOffApprovalUsersAddResource waiveOffApprovalUsersAddResource){
		if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty()) {
            throw new UserNotFound(environment.getProperty(COMMON_USER_NOT_FOUND));
        }
        SuccessAndErrorDetails successAndErrorDetails;
        WaiveOffApprovalUsers waiveOffApprovalUsers = waiveOffApprovalUsersService.addWaiveOffApprovalUsers(tenantId, waiveOffApprovalUsersAddResource);
        successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty(RECORD_CREATED), Long.toString(waiveOffApprovalUsers.getId()));
        return new ResponseEntity<>(successAndErrorDetails, HttpStatus.CREATED);
	}
    
    /**
     * Updates the waive off approval user
     *
     * @param tenantId                     the id of the tenant
     * @param id                           the id of the object
     * @param waiveOffTransactionTypeUpdateResource the object which contains data
     * @return the message
     * @see WaiveOffApprovalUsersUpdateResource
     */
    @PutMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> updateWaiveOffApprovalUser(@PathVariable(value = "tenantId") String tenantId,
													 @PathVariable(value = "id") Long id,
													 @Valid @RequestBody WaiveOffApprovalUsersUpdateResource waiveOffApprovalUsersUpdateResource){
		if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty()) {
            throw new UserNotFound(environment.getProperty(COMMON_USER_NOT_FOUND));
        }
		
		SuccessAndErrorDetails successAndErrorDetails = new SuccessAndErrorDetails();
        Optional<WaiveOffApprovalUsers> optWaiveOffApprovalUsers = waiveOffApprovalUsersService.findById(id);
        if (optWaiveOffApprovalUsers.isPresent()) {
        	WaiveOffApprovalUsers waiveOffApprovalUsers = waiveOffApprovalUsersService.updateWaiveOffApprovalUsers(tenantId, id, waiveOffApprovalUsersUpdateResource);
            successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty(RECORD_UPDATED), waiveOffApprovalUsers.getId().toString());
            return new ResponseEntity<>(successAndErrorDetails, HttpStatus.OK);
        } else {
            successAndErrorDetails.setMessages(RECORD_NOT_FOUND);
            return new ResponseEntity<>(successAndErrorDetails, HttpStatus.UNPROCESSABLE_ENTITY);
        }
	}
	
}
