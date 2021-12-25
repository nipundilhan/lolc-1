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
import com.fusionx.lending.transaction.domain.WaiveOffApprovalGroup;
import com.fusionx.lending.transaction.enums.BankTransactionCodeStatus;
import com.fusionx.lending.transaction.exception.UserNotFound;
import com.fusionx.lending.transaction.resource.SuccessAndErrorDetails;
import com.fusionx.lending.transaction.resource.WaiveOffApprovalGroupAddResource;
import com.fusionx.lending.transaction.resource.WaiveOffApprovalGroupUpdateResource;
import com.fusionx.lending.transaction.service.WaiveOffApprovalGroupService;

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
 * 1        03-12-2021      -               FXL-2111     Ishan                   Created
 * <p>
 *
 */
@RestController
@RequestMapping(value = "/waive-off-approval-group")
@CrossOrigin(origins = "*")
public class WaiveOffApprovalGroupController extends MessagePropertyBase {

	private WaiveOffApprovalGroupService waiveOffApprovalGroupService;
	@Autowired
	public void setWaiveOffTypeService(WaiveOffApprovalGroupService waiveOffApprovalGroupService) {
		this.waiveOffApprovalGroupService = waiveOffApprovalGroupService;
	}
	
	/**
	 * Gets all waive off approval group list
	 *
	 * @param tenantId the id of tenant
	 * @return the list of all waive off approval group list
	*/
	@GetMapping(value = "/{tenantId}/all")
	public ResponseEntity<Object> getAllWaiveOffApprovalGroup(@PathVariable(value = "tenantId") String tenantId) {
		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
        List<WaiveOffApprovalGroup> waiveOffApprovalGroup = waiveOffApprovalGroupService.findAll();
        int size = waiveOffApprovalGroup.size();
        if (size > 0) {
            return new ResponseEntity<>(waiveOffApprovalGroup, HttpStatus.OK);
        } else {
            responseMessage.setMessages(RECORD_NOT_FOUND);
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
	}
	
	/**
     * Get waive off approval group by id
     *
     * @param tenantId the id of tenant
     * @param id       the id of the record
     * @return waive off approval group if record exists, otherwise <code>204</code>
     */
	@GetMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> getWaiveOffApprovalGroupById(@PathVariable(value = "tenantId") String tenantId,
	                                                       @PathVariable(value = "id") Long id) {
	    Optional<WaiveOffApprovalGroup> isPresentWaiveOffApprovalGroup = waiveOffApprovalGroupService.findById(id);
	    if (isPresentWaiveOffApprovalGroup.isPresent()) {
	    	return new ResponseEntity<>(isPresentWaiveOffApprovalGroup.get(), HttpStatus.OK);
	    } else {
	    	SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
	        responseMessage.setMessages(RECORD_NOT_FOUND);
	        return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
	    }
	}
	
	/**
     * Get waive off approval group by code
     *
     * @param tenantId the id of tenant
     * @param code       the code of the record
     * @return waive off approval group if record exists, otherwise <code>204</code>
     */
	@GetMapping(value = "/{tenantId}/code/{code}")
	public ResponseEntity<Object> getWaiveOffApprovalGroupByCode(@PathVariable(value = "tenantId") String tenantId,
														@PathVariable(value = "code", required = true) String code) {
		List<WaiveOffApprovalGroup> isPresentWaiveOffApprovalGroup = waiveOffApprovalGroupService.findByCode(code);
		if (!isPresentWaiveOffApprovalGroup.isEmpty()) {
			return new ResponseEntity<>(isPresentWaiveOffApprovalGroup, HttpStatus.OK);
		} else {
			SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
			responseMessage.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
     * Get waive off approval group by status
     *
     * @param tenantId the id of tenant
     * @param name   the name
     * @return the list of all waive off approval group if record exists, otherwise <code>204 - No Content</code>
     */
	@GetMapping(value = "/{tenantId}/status/{status}")
	public ResponseEntity<Object> getWaiveOffApprovalGroupByStatus(@PathVariable(value = "tenantId") String tenantId,
														  @PathVariable(value = "status", required = true) String status) {
		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
		
		if(status.equals(BankTransactionCodeStatus.ACTIVE.toString()) || status.equals(BankTransactionCodeStatus.INACTIVE.toString())) {
			List<WaiveOffApprovalGroup> isPresentWaiveOffApprovalGroup = waiveOffApprovalGroupService.findByStatus(status);
			if (!isPresentWaiveOffApprovalGroup.isEmpty()) {
				return new ResponseEntity<>(isPresentWaiveOffApprovalGroup, HttpStatus.OK);
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
     * Get waive off approval group by name
     *
     * @param tenantId the id of tenant
     * @param name   the name
     * @return the list of all waive off approval group if record exists, otherwise <code>204 - No Content</code>
     */
	@GetMapping(value = "/{tenantId}/name/{name}")
	public ResponseEntity<Object> getWaiveOffApprovalGroupByName(@PathVariable(value = "tenantId") String tenantId,
														  @PathVariable(value = "name", required = true) String name) {
		List<WaiveOffApprovalGroup> isPresentWaiveOffApprovalGroup = waiveOffApprovalGroupService.findByName(name);
		if (!isPresentWaiveOffApprovalGroup.isEmpty()) {
			return new ResponseEntity<>(isPresentWaiveOffApprovalGroup, HttpStatus.OK);
		} else {
			SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
			responseMessage.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
     * Saves the waive off approval group
     *
     * @param tenantId                  the id of the tenant
     * @param addResource the object to save
     * @return message if record successfully saved.
     * @see WaiveOffApprovalGroupAddResource
     */
    @PostMapping(value = "/{tenantId}")
	public ResponseEntity<Object> addWaiveOffApprovalGroup(@PathVariable(value = "tenantId") String tenantId,
									@Valid @RequestBody WaiveOffApprovalGroupAddResource addResource){
		if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty()) {
            throw new UserNotFound(environment.getProperty(COMMON_USER_NOT_FOUND));
        }
        SuccessAndErrorDetails successAndErrorDetails;
        WaiveOffApprovalGroup waiveOffApprovalGroup = waiveOffApprovalGroupService.addWaiveOffType(tenantId, addResource);
        successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty(RECORD_CREATED), Long.toString(waiveOffApprovalGroup.getId()));
        return new ResponseEntity<>(successAndErrorDetails, HttpStatus.CREATED);
	}
    
    /**
     * Updates the waive off approval group
     *
     * @param tenantId                     the id of the tenant
     * @param id                           the id of the object
     * @param updateResource the object which contains data
     * @return the message
     * @see WaiveOffApprovalGroupUpdateResource
     */
    @PutMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> updateWaiveOffType(@PathVariable(value = "tenantId") String tenantId,
													 @PathVariable(value = "id") Long id,
													 @Valid @RequestBody WaiveOffApprovalGroupUpdateResource updateResource){
		if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty()) {
            throw new UserNotFound(environment.getProperty(COMMON_USER_NOT_FOUND));
        }
		
		SuccessAndErrorDetails successAndErrorDetails = new SuccessAndErrorDetails();
        Optional<WaiveOffApprovalGroup> isPresentisPresentWaiveOffApprovalGroup = waiveOffApprovalGroupService.findById(id);
        if (isPresentisPresentWaiveOffApprovalGroup.isPresent()) {
        	WaiveOffApprovalGroup waiveOffApprovalGroup = waiveOffApprovalGroupService.updateWaiveOffType(tenantId, id, updateResource);
            successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty(RECORD_UPDATED), waiveOffApprovalGroup.getId().toString());
            return new ResponseEntity<>(successAndErrorDetails, HttpStatus.OK);
        } else {
            successAndErrorDetails.setMessages(RECORD_NOT_FOUND);
            return new ResponseEntity<>(successAndErrorDetails, HttpStatus.UNPROCESSABLE_ENTITY);
        }
	}
	
}
