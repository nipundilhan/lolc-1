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
import com.fusionx.lending.product.domain.DisbursementConditions;
import com.fusionx.lending.product.exception.UserNotFound;
import com.fusionx.lending.product.resources.AccountStatusAddResource;
import com.fusionx.lending.product.resources.DisbursementConditionsAddResource;
import com.fusionx.lending.product.resources.DisbursementConditionsUpdateResource;
import com.fusionx.lending.product.resources.SuccessAndErrorDetails;
import com.fusionx.lending.product.resources.SuccessAndErrorDetailsResource;
import com.fusionx.lending.product.service.DisbursementConditionsService;

/**
 * API Service related to Disbursement Conditions.
 *
 * @author Dilhan
 * @version 1.0.0
 * @since 1.0.0
 * <p>
 * <br/><br/>
 * <b>Change History : </b>
 * <br/><br/>
 * #        Date            Story Point     Task No     Author                  Description
 * <br/>
 * .....................................................................................................................<br/>
 * 1        16-09-2021      -               FXL-788     Dilhan      Created
 * <p>
 *
 */
@RestController
@RequestMapping(value = "/disbursement-conditions")
@CrossOrigin(origins = "*")
public class DisbursementConditionsController extends MessagePropertyBase{

	private DisbursementConditionsService disbursementConditionsService;
	
	@Autowired
	public void setDisbursementConditionsService(DisbursementConditionsService disbursementConditionsService) {
	    this.disbursementConditionsService = disbursementConditionsService;
	}

	   /**
     * Gets all disbursement conditions list
     *
     * @param tenantId the id of tenant
     * @return the list of all disbursement conditions list
     */
    @GetMapping(value = "/{tenantId}/all")
    public ResponseEntity<Object> getAllDisbursementConditions(@PathVariable(value = "tenantId") String tenantId) {
        SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
        List<DisbursementConditions> disbursementConditions = disbursementConditionsService.getAll();
        int size = disbursementConditions.size();
        if (size > 0) {
            return new ResponseEntity<>(disbursementConditions, HttpStatus.OK);
        } else {
            responseMessage.setMessages(RECORD_NOT_FOUND);
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Get disbursement conditions by id
     *
     * @param tenantId the id of tenant
     * @param id       the id of the record
     * @return disbursement conditions if record exists, otherwise <code>204</code>
     */
    @GetMapping(value = "/{tenantId}/{id}")
    public ResponseEntity<Object> getDisbursementConditionsById(@PathVariable(value = "tenantId") String tenantId,
                                                        @PathVariable(value = "id") Long id) {
        Optional<DisbursementConditions> isPresentDisbursementConditions = disbursementConditionsService.findById(id);
        if (isPresentDisbursementConditions.isPresent()) {
            return new ResponseEntity<>(isPresentDisbursementConditions.get(), HttpStatus.OK);
        } else {
        	SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
            responseMessage.setMessages(RECORD_NOT_FOUND);
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Get disbursement conditions by status
     *
     * @param tenantId the id of tenant
     * @param status   the status
     * @return the list of all disbursement conditions if record exists, otherwise <code>204 - No Content</code>
     */
    @GetMapping(value = "/{tenantId}/status/{status}")
    public ResponseEntity<Object> getDisbursementConditionsByStatus(@PathVariable(value = "tenantId") String tenantId,
                                                            @PathVariable(value = "status") String status) {
        SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();

        if (status.equals("ACTIVE") || status.equals("INACTIVE")) {
            List<DisbursementConditions> isPresentDisbursementConditions = disbursementConditionsService.findByStatus(status);
            int size = isPresentDisbursementConditions.size();
            if (size > 0) {
                return new ResponseEntity<>(isPresentDisbursementConditions, HttpStatus.OK);
            } else {
                responseMessage.setMessages(RECORD_NOT_FOUND);
                return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
            }
        } else {
            responseMessage.setMessages(environment.getProperty(INVALID_STATUS));
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }
    
    /**
     * Get disbursement conditions by code
     *
     * @param tenantId the id of tenant
     * @param code   the code
     * @return  disbursement conditions if record exists, otherwise <code>204 - No Content</code>
     */
	@GetMapping(value = "/{tenantId}/code/{code}")
	public ResponseEntity<Object> getDisbursementConditionsByCode(@PathVariable(value = "tenantId", required = true) String tenantId,
													   	            @PathVariable(value = "code", required = true) String code){
		Optional<DisbursementConditions> isPresentDisbursementConditions = disbursementConditionsService.findByCode(code);
		if(isPresentDisbursementConditions.isPresent()) {
			return new ResponseEntity<>(isPresentDisbursementConditions.get(), HttpStatus.OK);
		}
		else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	  /**
     * Get disbursement conditions by name
     *
     * @param tenantId the id of tenant
     * @param name   the name
     * @return the list of all disbursement conditions if record exists, otherwise <code>204 - No Content</code>
     */
	@GetMapping(value = "/{tenantId}/name/{name}")
	public ResponseEntity<Object> getDisbursementConditionsByName(@PathVariable(value = "tenantId", required = true) String tenantId,
															        @PathVariable(value = "name", required = true) String name){
		List<DisbursementConditions> isPresentDisbursementConditions = disbursementConditionsService.findByName(name);
		if(!isPresentDisbursementConditions.isEmpty()) {
			return new ResponseEntity<>(isPresentDisbursementConditions, HttpStatus.OK);
		}
		else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	/**
     * Saves the account status
     *
     * @param tenantId                  the id of the tenant
     * @param accountStatusAddResource the object to save
     * @return message if record successfully saved.
     * @see AccountStatusAddResource
     */
    @PostMapping(value = "/{tenantId}")
    public ResponseEntity<Object> addDisbursementConditions(@PathVariable(value = "tenantId") String tenantId,
                                                    @Valid @RequestBody DisbursementConditionsAddResource disbursementConditionsAddResource) {
        if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty()) {
            throw new UserNotFound(environment.getProperty(COMMON_USER_NOT_FOUND));
        }
        SuccessAndErrorDetails successAndErrorDetails;
        DisbursementConditions disbursementConditions = disbursementConditionsService.addDisbursementConditions(tenantId, disbursementConditionsAddResource);
        successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty(RECORD_CREATED), Long.toString(disbursementConditions.getId()));
        return new ResponseEntity<>(successAndErrorDetails, HttpStatus.CREATED);
    }
    
    /**
     * Updates the account status
     *
     * @param tenantId                     the id of the tenant
     * @param id                           the id of the object
     * @param disbursementConditionsUpdateResource the object which contains data
     * @return the message
     * @see DisbursementConditionsUpdateResource
     */
    @PutMapping(value = "/{tenantId}/{id}")
    public ResponseEntity<Object> updateDisbursementConditions(@PathVariable(value = "tenantId") String tenantId,
                                                       @PathVariable(value = "id") Long id,
                                                       @Valid @RequestBody DisbursementConditionsUpdateResource disbursementConditionsUpdateResource) {
        if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty()) {
            throw new UserNotFound(environment.getProperty(COMMON_USER_NOT_FOUND));
        }

        SuccessAndErrorDetails successAndErrorDetails = new SuccessAndErrorDetails();
        Optional<DisbursementConditions> isPresentDisbursementConditions = disbursementConditionsService.findById(id);
        if (isPresentDisbursementConditions.isPresent()) {
        	DisbursementConditions disbursementConditions = disbursementConditionsService.updateDisbursementConditions(tenantId, id, disbursementConditionsUpdateResource);
            successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty(RECORD_UPDATED), disbursementConditions.getId().toString());
            return new ResponseEntity<>(successAndErrorDetails, HttpStatus.OK);
        } else {
            successAndErrorDetails.setMessages(RECORD_NOT_FOUND);
            return new ResponseEntity<>(successAndErrorDetails, HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

}
