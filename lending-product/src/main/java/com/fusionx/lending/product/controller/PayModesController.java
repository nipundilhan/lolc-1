package com.fusionx.lending.product.controller;

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

import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.PayModes;
import com.fusionx.lending.product.exception.UserNotFound;
import com.fusionx.lending.product.resources.CommonAddResource;
import com.fusionx.lending.product.resources.CommonUpdateResource;
import com.fusionx.lending.product.resources.SuccessAndErrorDetails;
import com.fusionx.lending.product.resources.SuccessAndErrorDetailsResource;
import com.fusionx.lending.product.service.PayModesService;

@RestController
@RequestMapping(value = "/pay-modes")
@CrossOrigin(origins = "*")
public class PayModesController extends MessagePropertyBase{

	private PayModesService payModesService;

	@Autowired
	public void setPayModesService(PayModesService payModesService) {
		this.payModesService = payModesService;
	}
	
	   /**
     * Gets all pay modes list
     *
     * @param tenantId the id of tenant
     * @return the list of pay modes list
     */
    @GetMapping(value = "/{tenantId}/all")
    public ResponseEntity<Object> getAllPayModess(@PathVariable(value = "tenantId") String tenantId) {
        SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
        List<PayModes> payModes = payModesService.getAll();
        int size = payModes.size();
        if (size > 0) {
            return new ResponseEntity<>(payModes, HttpStatus.OK);
        } else {
            responseMessage.setMessages(RECORD_NOT_FOUND);
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Get pay modes by id
     *
     * @param tenantId the id of tenant
     * @param id       the id of the record
     * @return a pay mode if record exists, otherwise <code>204</code>
     */
    @GetMapping(value = "/{tenantId}/{id}")
    public ResponseEntity<Object> getPayModesById(@PathVariable(value = "tenantId") String tenantId,
                                                        @PathVariable(value = "id") Long id) {
        Optional<PayModes> isPresentPayModes = payModesService.findById(id);
        if (isPresentPayModes.isPresent()) {
            return new ResponseEntity<>(isPresentPayModes.get(), HttpStatus.OK);
        } else {
        	SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
            responseMessage.setMessages(RECORD_NOT_FOUND);
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Get pay modes by status
     *
     * @param tenantId the id of tenant
     * @param status   the status
     * @return the list of all pay modess if record exists, otherwise <code>204 - No Content</code>
     */
    @GetMapping(value = "/{tenantId}/status/{status}")
    public ResponseEntity<Object> getPayModesByStatus(@PathVariable(value = "tenantId") String tenantId,
                                                            @PathVariable(value = "status") String status) {
        SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();

        if (status.equals("ACTIVE") || status.equals("INACTIVE")) {
            List<PayModes> isPresentPayModes = payModesService.getByStatus(status);
            int size = isPresentPayModes.size();
            if (size > 0) {
                return new ResponseEntity<>(isPresentPayModes, HttpStatus.OK);
            } else {
                responseMessage.setMessages(RECORD_NOT_FOUND);
                return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
            }
        } else {
        	responseMessage.setMessages(environment.getProperty(COMMON_STATUS_PATTERN));
			return new ResponseEntity<>(responseMessage, HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
    
    /**
     * Get pay modes by code
     *
     * @param tenantId the id of tenant
     * @param code   the code
     * @return  an pay modes if record exists, otherwise <code>204 - No Content</code>
     */
	@GetMapping(value = "/{tenantId}/code/{code}")
	public ResponseEntity<Object> getPayModesByCode(@PathVariable(value = "tenantId", required = true) String tenantId,
													   	            @PathVariable(value = "code", required = true) String code){
		Optional<PayModes> isPresentPayModes = payModesService.getByCode(code);
		if(isPresentPayModes.isPresent()) {
			return new ResponseEntity<>(isPresentPayModes.get(), HttpStatus.OK);
		}
		else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	  /**
     * Get pay modes by name
     *
     * @param tenantId the id of tenant
     * @param name   the name
     * @return the list of all pay modes if record exists, otherwise <code>204 - No Content</code>
     */
	@GetMapping(value = "/{tenantId}/name/{name}")
	public ResponseEntity<Object> getPayModesByName(@PathVariable(value = "tenantId", required = true) String tenantId,
															        @PathVariable(value = "name", required = true) String name){
		List<PayModes> isPresentPayModes = payModesService.getByName(name);
		if(!isPresentPayModes.isEmpty()) {
			return new ResponseEntity<>(isPresentPayModes, HttpStatus.OK);
		}
		else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
 	

    /**
     * Saves the pay modes
     *
     * @param tenantId                  the id of the tenant
     * @param commonAddResource the object to save
     * @return message if record successfully saved.
     * @see CommonAddResource
     */
    @PostMapping(value = "/{tenantId}")
    public ResponseEntity<Object> addPayModes(@PathVariable(value = "tenantId") String tenantId,
                                                    @Valid @RequestBody CommonAddResource commonAddResource) {
        if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty()) {
            throw new UserNotFound(environment.getProperty(COMMON_USER_NOT_FOUND));
        }
        SuccessAndErrorDetails successAndErrorDetails;
        PayModes payModes = payModesService.addPayModes(tenantId, commonAddResource);
        successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty(RECORD_CREATED), Long.toString(payModes.getId()));
        return new ResponseEntity<>(successAndErrorDetails, HttpStatus.CREATED);
    }

    /**
     * Updates the pay modes
     *
     * @param tenantId                     the id of the tenant
     * @param id                           the id of the object
     * @param commonUpdateResource the object which contains data
     * @return the message
     * @see CommonUpdateResource
     */
    @PutMapping(value = "/{tenantId}/{id}")
    public ResponseEntity<Object> updatePayModes(@PathVariable(value = "tenantId") String tenantId,
                                                       @PathVariable(value = "id") Long id,
                                                       @Valid @RequestBody CommonUpdateResource commonUpdateResource) {
        if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty()) {
            throw new UserNotFound(environment.getProperty(COMMON_USER_NOT_FOUND));
        }

        SuccessAndErrorDetails successAndErrorDetails = new SuccessAndErrorDetails();
        Optional<PayModes> isPresentPayModes = payModesService.findById(id);
        if (isPresentPayModes.isPresent()) {
        	PayModes payModes = payModesService.updatePayModes(tenantId, id, commonUpdateResource);
            successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty(RECORD_UPDATED), payModes.getId().toString());
            return new ResponseEntity<>(successAndErrorDetails, HttpStatus.OK);
        } else {
            successAndErrorDetails.setMessages(RECORD_NOT_FOUND);
            return new ResponseEntity<>(successAndErrorDetails, HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
}
