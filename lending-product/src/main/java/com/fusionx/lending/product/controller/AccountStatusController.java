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
import com.fusionx.lending.product.domain.AccountStatus;
import com.fusionx.lending.product.exception.UserNotFound;
import com.fusionx.lending.product.resources.AccountStatusAddResource;
import com.fusionx.lending.product.resources.AccountStatusUpdateResource;
import com.fusionx.lending.product.resources.SuccessAndErrorDetails;
import com.fusionx.lending.product.resources.SuccessAndErrorDetailsResource;
import com.fusionx.lending.product.service.AccountStatusService;

/**
 * API Service related to Age eligibility.
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
 * 1        16-09-2021      -               -           Dilhan                  Created
 * <p>
 *
 */
@RestController
@RequestMapping(value = "/account-status")
@CrossOrigin(origins = "*")
public class AccountStatusController extends MessagePropertyBase{

	private AccountStatusService accountStatusService;

	@Autowired
	public void setAccountStatusService(AccountStatusService accountStatusService) {
		this.accountStatusService = accountStatusService;
	}

	   /**
     * Gets all account status list
     *
     * @param tenantId the id of tenant
     * @return the list of all account list
     */
    @GetMapping(value = "/{tenantId}/all")
    public ResponseEntity<Object> getAllAccountStatus(@PathVariable(value = "tenantId") String tenantId) {
        SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
        List<AccountStatus> accountStatus = accountStatusService.getAll();
        int size = accountStatus.size();
        if (size > 0) {
            return new ResponseEntity<>(accountStatus, HttpStatus.OK);
        } else {
            responseMessage.setMessages(RECORD_NOT_FOUND);
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Get Account status by id
     *
     * @param tenantId the id of tenant
     * @param id       the id of the record
     * @return an account status if record exists, otherwise <code>204</code>
     */
    @GetMapping(value = "/{tenantId}/{id}")
    public ResponseEntity<Object> getAccountStatusById(@PathVariable(value = "tenantId") String tenantId,
                                                        @PathVariable(value = "id") Long id) {
        Optional<AccountStatus> isPresentAccountStatus = accountStatusService.findById(id);
        if (isPresentAccountStatus.isPresent()) {
            return new ResponseEntity<>(isPresentAccountStatus.get(), HttpStatus.OK);
        } else {
        	SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
            responseMessage.setMessages(RECORD_NOT_FOUND);
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Get account status by status
     *
     * @param tenantId the id of tenant
     * @param status   the status
     * @return the list of all account status if record exists, otherwise <code>204 - No Content</code>
     */
    @GetMapping(value = "/{tenantId}/status/{status}")
    public ResponseEntity<Object> getAccountStatusByStatus(@PathVariable(value = "tenantId") String tenantId,
                                                            @PathVariable(value = "status") String status) {
        SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();

        if (status.equals("ACTIVE") || status.equals("INACTIVE")) {
            List<AccountStatus> isPresentAccountStatus = accountStatusService.findByStatus(status);
            int size = isPresentAccountStatus.size();
            if (size > 0) {
                return new ResponseEntity<>(isPresentAccountStatus, HttpStatus.OK);
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
     * Get account status by code
     *
     * @param tenantId the id of tenant
     * @param code   the code
     * @return  an account status if record exists, otherwise <code>204 - No Content</code>
     */
	@GetMapping(value = "/{tenantId}/code/{code}")
	public ResponseEntity<Object> getAccountStatusByCode(@PathVariable(value = "tenantId", required = true) String tenantId,
													   	            @PathVariable(value = "code", required = true) String code){
		Optional<AccountStatus> isPresentAccountStatus = accountStatusService.findByCode(code);
		if(isPresentAccountStatus.isPresent()) {
			return new ResponseEntity<>(isPresentAccountStatus.get(), HttpStatus.OK);
		}
		else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	  /**
     * Get account status by name
     *
     * @param tenantId the id of tenant
     * @param name   the name
     * @return the list of all account status if record exists, otherwise <code>204 - No Content</code>
     */
	@GetMapping(value = "/{tenantId}/name/{name}")
	public ResponseEntity<Object> getAccountStatusByName(@PathVariable(value = "tenantId", required = true) String tenantId,
															        @PathVariable(value = "name", required = true) String name){
		List<AccountStatus> isPresentAccountStatus = accountStatusService.findByName(name);
		if(!isPresentAccountStatus.isEmpty()) {
			return new ResponseEntity<>(isPresentAccountStatus, HttpStatus.OK);
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
    public ResponseEntity<Object> addAccountStatus(@PathVariable(value = "tenantId") String tenantId,
                                                    @Valid @RequestBody AccountStatusAddResource accountStatusAddResource) {
        if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty()) {
            throw new UserNotFound(environment.getProperty(COMMON_USER_NOT_FOUND));
        }
        SuccessAndErrorDetails successAndErrorDetails;
        AccountStatus accountStatus = accountStatusService.addAccountStatus(tenantId, accountStatusAddResource);
        successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty(RECORD_CREATED), Long.toString(accountStatus.getId()));
        return new ResponseEntity<>(successAndErrorDetails, HttpStatus.CREATED);
    }

    /**
     * Updates the account status
     *
     * @param tenantId                     the id of the tenant
     * @param id                           the id of the object
     * @param accountStatusUpdateResource the object which contains data
     * @return the message
     * @see AccountStatusUpdateResource
     */
    @PutMapping(value = "/{tenantId}/{id}")
    public ResponseEntity<Object> updateAccountStatus(@PathVariable(value = "tenantId") String tenantId,
                                                       @PathVariable(value = "id") Long id,
                                                       @Valid @RequestBody AccountStatusUpdateResource accountStatusUpdateResource) {
        if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty()) {
            throw new UserNotFound(environment.getProperty(COMMON_USER_NOT_FOUND));
        }

        SuccessAndErrorDetails successAndErrorDetails = new SuccessAndErrorDetails();
        Optional<AccountStatus> isPresentAccountStatus = accountStatusService.findById(id);
        if (isPresentAccountStatus.isPresent()) {
        	AccountStatus accountStatus = accountStatusService.updateAccountStatus(tenantId, id, accountStatusUpdateResource);
            successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty(RECORD_UPDATED), accountStatus.getId().toString());
            return new ResponseEntity<>(successAndErrorDetails, HttpStatus.OK);
        } else {
            successAndErrorDetails.setMessages(RECORD_NOT_FOUND);
            return new ResponseEntity<>(successAndErrorDetails, HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
}
