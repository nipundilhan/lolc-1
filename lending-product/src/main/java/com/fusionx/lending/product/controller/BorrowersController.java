package com.fusionx.lending.product.controller;

import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.Borrowers;
import com.fusionx.lending.product.exception.UserNotFound;
import com.fusionx.lending.product.resources.BorrowersAddResource;
import com.fusionx.lending.product.resources.BorrowersUpdateResource;
import com.fusionx.lending.product.resources.SuccessAndErrorDetails;
import com.fusionx.lending.product.service.BorrowersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * API Service related to Borrowers.
 *
 * @author Thushan
 * @version 1.0.0
 * @since 1.0.0
 * <p>
 * <br/><br/>
 * <b>Change History : </b>
 * <br/><br/>
 * #          Date            Story Point     Task No       Author                  Description
 * <br/>
 * .....................................................................................................................<br/>
 * 1        26-10-2021          -               -           Thushan                  Created
 * <p>
 */
@RestController
@RequestMapping(value = "/borrowers")
@CrossOrigin(origins = "*")
public class BorrowersController extends MessagePropertyBase {

    private BorrowersService borrowersService;

    @Autowired
    public void setBorrowersService(BorrowersService borrowersService) {
        this.borrowersService = borrowersService;
    }

    /**
     * Get borrowers by id
     *
     * @param tenantId the id of tenant
     * @param id       the id of the record
     * @return a borrowers if record exists, otherwise <code>204</code>
     */
    @GetMapping(value = "/{tenantId}/{id}")
    public ResponseEntity<Object> getBorrowersById(@PathVariable(value = "tenantId") String tenantId,
                                                       @PathVariable(value = "id") Long id) {
        Optional<Borrowers> isPresentBorrowers = borrowersService.findById(id);
        if (isPresentBorrowers.isPresent()) {
            return new ResponseEntity<>(isPresentBorrowers.get(), HttpStatus.OK);
        } else {
            SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
            responseMessage.setMessages(RECORD_NOT_FOUND);
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Get borrowers by lending account id
     *
     * @param tenantId the id of tenant
     * @param lendingAccountId the id of the lending account
     * @return a borrowers if record exists, otherwise <code>204</code>
     */
    @GetMapping(value = "/{tenantId}/lending-account/{lendingAccountId}")
    public ResponseEntity<Object> getBorrowersByLendingAccountId(@PathVariable(value = "tenantId") String tenantId,
                                                   @PathVariable(value = "lendingAccountId") Long lendingAccountId) {
        List<Borrowers> borrowers = borrowersService.findByLendingAccountId(lendingAccountId);
        if (!borrowers.isEmpty()) {
            return new ResponseEntity<>(borrowers, HttpStatus.OK);
        } else {
            SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
            responseMessage.setMessages(RECORD_NOT_FOUND);
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Get borrowers by status
     *
     * @param tenantId the id of tenant
     * @param status   the status
     * @return the list of all  borrowers status if record exists, otherwise <code>204 - No Content</code>
     */
    @GetMapping(value = "/{tenantId}/status/{status}")
    public ResponseEntity<Object> getBorrowersByStatus(@PathVariable(value = "tenantId") String tenantId,
                                                           @PathVariable(value = "status") String status) {
        SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();

        if (status.equals("ACTIVE") || status.equals("INACTIVE")) {
            List<Borrowers> isPresentBorrowers = borrowersService.findByStatus(status);
            int size = isPresentBorrowers.size();
            if (size > 0) {
                return new ResponseEntity<>(isPresentBorrowers, HttpStatus.OK);
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
     * Saves the given brand object
     *
     * @param tenantId          the tenant id
     * @param borrowersAddResource the object to save
     * @return the message
     */
    @PostMapping(value = "/{tenantId}")
    public ResponseEntity<Object> addBorrowers(@PathVariable(value = "tenantId") String tenantId,
                                               @Valid @RequestBody BorrowersAddResource borrowersAddResource) {
        if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty()) {
            throw new UserNotFound(environment.getProperty(COMMON_USER_NOT_FOUND));
        }
        Borrowers borrowers = borrowersService.addBorrowers(tenantId, borrowersAddResource);
        SuccessAndErrorDetails successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty(RECORD_CREATED), Long.toString(borrowers.getId()));
        return new ResponseEntity<>(successAndErrorDetails, HttpStatus.CREATED);
    }

    /**
     * Updates the given brand record
     * @param tenantId the tenant id
     * @param id the id
     * @param borrowersUpdateResource object to update
     * @return the message
     */
    @PutMapping(value = "/{tenantId}/{id}")
    public ResponseEntity<Object> updateBorrowers(@PathVariable(value = "tenantId") String tenantId,
                                              @PathVariable(value = "id") Long id,
                                              @Valid @RequestBody BorrowersUpdateResource borrowersUpdateResource) {
        if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty()) {
            throw new UserNotFound(environment.getProperty(COMMON_USER_NOT_FOUND));
        }

        SuccessAndErrorDetails successAndErrorDetails = new SuccessAndErrorDetails();
        Optional<Borrowers> isPresentBorrowers = borrowersService.findById(id);
        if (isPresentBorrowers.isPresent()) {
            Borrowers borrowers = borrowersService.updateBorrowers(tenantId, id, borrowersUpdateResource);
            successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty(RECORD_UPDATED),Long.toString(borrowers.getId()));
            return new ResponseEntity<>(successAndErrorDetails, HttpStatus.OK);
        } else {
            successAndErrorDetails.setMessages(RECORD_NOT_FOUND);
            return new ResponseEntity<>(successAndErrorDetails, HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

}
