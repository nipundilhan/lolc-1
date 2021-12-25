package com.fusionx.lending.transaction.controller;

import com.fusionx.lending.transaction.base.MessagePropertyBase;
import com.fusionx.lending.transaction.core.LogginAuthentcation;
import com.fusionx.lending.transaction.domain.CoreTransactionMethod;
import com.fusionx.lending.transaction.exception.UserNotFound;
import com.fusionx.lending.transaction.resource.CoreTransactionAddResource;
import com.fusionx.lending.transaction.resource.CoreTransactionUpdateResource;
import com.fusionx.lending.transaction.resource.SuccessAndErrorDetailsResource;
import com.fusionx.lending.transaction.service.CoreTransactionMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;


/**
 * Core Transaction Method Controller
 *
 * @author Pasindu
 * @Dat 01-10-2021
 * <p>
 * *******************************************************************************************************
 * ### Date Story Point Task No Author Description
 * -------------------------------------------------------------------------------------------------------
 * 1 01-10-2021 FXL-1052 FXL-1001 Pasindu Created
 * <p>
 * *******************************************************************************************************
 */

@RestController
@RequestMapping(value = "/core-transaction-method")
@CrossOrigin(origins = "*")
public class CoreTransactionMethodController extends MessagePropertyBase {

    @Autowired
    public CoreTransactionMethodService coreTransactionService; 

    /**
     * get all Core Transaction Method
     *
     * @param @PathVariable{tenantId}
     * @param @PathVariable{all}
     * @return List<Type>
     */
    @GetMapping(value = "/{tenantId}/all")
    public ResponseEntity<Object> getAllCoreTransaction(
            @PathVariable(value = "tenantId") String tenantId) {
        SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
        List<CoreTransactionMethod> coreTransactions = coreTransactionService.getAll();
        int size = coreTransactions.size();
        if (size > 0) {
            return new ResponseEntity<>( coreTransactions, HttpStatus.OK);
        } else {
            responseMessage.setMessages(RECORD_NOT_FOUND);
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * get Core Transaction Method by id
     *
     * @param @PathVariable{tenantId}
     * @param @PathVariable{id}
     * @return Option
     */
    @GetMapping(value = "/{tenantId}/{id}")
    public ResponseEntity<Object> getCoreTransactionById(
            @PathVariable(value = "tenantId") String tenantId,
            @PathVariable(value = "id") Long id) {

        SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
        Optional<CoreTransactionMethod> isPresentCoreTransaction = coreTransactionService.findById(id);
        if (isPresentCoreTransaction.isPresent()) {
            return new ResponseEntity<>(isPresentCoreTransaction.get(), HttpStatus.OK);
        } else {
            responseMessage.setMessages(RECORD_NOT_FOUND);
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * get Core Transaction Method by code
     *
     * @param @pathVariable{tenantId}
     * @param @pathVariable           {code}
     * @return Optional
     **/
    @GetMapping(value = "/{tenantId}/code/{code}")
    public ResponseEntity<Object> getCoreTransactionByCode(
            @PathVariable(value = "tenantId") String tenantId,
            @PathVariable(value = "code") String code) {

        SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
        Optional<CoreTransactionMethod> isPresentCoreTransaction = coreTransactionService.findByCode(code);
        if (isPresentCoreTransaction.isPresent()) {
            return new ResponseEntity<>(isPresentCoreTransaction.get(), HttpStatus.OK);
        } else {
            responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * get Core Transaction Method by name
     *
     * @param @pathVariable{tenantId}
     * @param @pathVariable{name}
     * @return List
     **/
    @GetMapping(value = "/{tenantId}/name/{name}")
    public ResponseEntity<Object> getCoreTransactionByName(
            @PathVariable(value = "tenantId") String tenantId,
            @PathVariable(value = "name") String name) {

        SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
        List<CoreTransactionMethod> isPresentSalesAccessChannel = coreTransactionService.findByName(name);
        if (!isPresentSalesAccessChannel.isEmpty()) {
            return new ResponseEntity<>(isPresentSalesAccessChannel, HttpStatus.OK);
        } else {
            responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * get Core Transaction Method by status
     *
     * @param @PathVariable{tenantId}
     * @param @PathVariable{status}
     * @return List
     */
    @GetMapping(value = "/{tenantId}/status/{status}")
    public ResponseEntity<Object> getCoreTransactionByStatus(
            @PathVariable(value = "tenantId") String tenantId,
            @PathVariable(value = "status") String status) {
        SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();

        if (status.equals("ACTIVE") || status.equals("INACTIVE")) {
            List<CoreTransactionMethod> coreTransactions = coreTransactionService.findByStatus(status);
            int size = coreTransactions.size();
            if (size > 0) {
                return new ResponseEntity<>(coreTransactions, HttpStatus.OK);
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
     * save CoreTransactionMethod
     *
     * @param @PathVariable{tenantId}
     * @param @RequestBody{CommonAddResource}
     * @return SuccessAndErrorDetailsDto
     */
    @PostMapping(value = "/{tenantId}")
    public ResponseEntity<Object> addCoreTransaction(@PathVariable(value = "tenantId") String tenantId,
                                                     @Valid @RequestBody CoreTransactionAddResource commonAddResource) {
        if (LogginAuthentcation.getInstance().getUserName() == null
                || LogginAuthentcation.getInstance().getUserName().isEmpty()) {
            throw new UserNotFound(environment.getProperty(USER_NOT_FOUND));
        }

        SuccessAndErrorDetailsResource successAndErrorDetails = new SuccessAndErrorDetailsResource();
        CoreTransactionMethod coreTransaction = coreTransactionService.addCoreTransaction(tenantId, commonAddResource);
        successAndErrorDetails = new SuccessAndErrorDetailsResource(environment.getProperty(RECORD_CREATED),
                Long.toString(coreTransaction.getId()));
        return new ResponseEntity<>(successAndErrorDetails, HttpStatus.CREATED);
    }

    /**
     * update CoreTransactionMethod
     *
     * @param @PathVariable{tenantId}
     * @param @RequestBody{CommonUpdateResource}
     * @return SuccessAndErrorDetailsDto
     */
    @PutMapping(value = "/{tenantId}/{id}")
    public ResponseEntity<Object> updateCoreTransaction(
            @PathVariable(value = "tenantId") String tenantId,
            @PathVariable(value = "id") Long id,
            @Valid @RequestBody CoreTransactionUpdateResource commonUpdateResource) {
        if (LogginAuthentcation.getInstance().getUserName() == null
                || LogginAuthentcation.getInstance().getUserName().isEmpty()) {
            throw new UserNotFound(environment.getProperty(USER_NOT_FOUND));
        }

        SuccessAndErrorDetailsResource successAndErrorDetails = new SuccessAndErrorDetailsResource();
        Optional<CoreTransactionMethod> isPresentCoreTransaction = coreTransactionService.findById(id);
        if (isPresentCoreTransaction.isPresent()) {
            CoreTransactionMethod coreTransaction = coreTransactionService.updateCoreTransaction(tenantId, id,
                    commonUpdateResource);
            successAndErrorDetails = new SuccessAndErrorDetailsResource(environment.getProperty(RECORD_UPDATED),
                    coreTransaction.getId().toString());
            return new ResponseEntity<>(successAndErrorDetails, HttpStatus.OK);
        } else {
            successAndErrorDetails.setMessages(RECORD_NOT_FOUND);
            return new ResponseEntity<>(successAndErrorDetails, HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

}
