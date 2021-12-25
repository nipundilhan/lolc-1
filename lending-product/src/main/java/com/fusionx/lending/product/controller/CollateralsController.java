package com.fusionx.lending.product.controller;

import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.Collaterals;
import com.fusionx.lending.product.exception.UserNotFound;
import com.fusionx.lending.product.resources.CollateralsAddResource;
import com.fusionx.lending.product.resources.CollateralsUpdateResource;
import com.fusionx.lending.product.resources.SuccessAndErrorDetails;
import com.fusionx.lending.product.service.CollateralsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * API Service related to Collaterals.
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
@RequestMapping(value = "/collaterals")
@CrossOrigin(origins = "*")
public class CollateralsController extends MessagePropertyBase {
    
    private CollateralsService collateralsService;

    @Autowired
    public void setCollateralsService(CollateralsService collateralsService) {
        this.collateralsService = collateralsService;
    }

    /**
     * Get collaterals by id
     *
     * @param tenantId the id of tenant
     * @param id       the id of the record
     * @return a collaterals if record exists, otherwise <code>204</code>
     */
    @GetMapping(value = "/{tenantId}/{id}")
    public ResponseEntity<Object> getCollateralsById(@PathVariable(value = "tenantId") String tenantId,
                                                   @PathVariable(value = "id") Long id) {
        Optional<Collaterals> isPresentCollaterals = collateralsService.findById(id);
        if (isPresentCollaterals.isPresent()) {
            return new ResponseEntity<>(isPresentCollaterals.get(), HttpStatus.OK);
        } else {
            SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
            responseMessage.setMessages(RECORD_NOT_FOUND);
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Get Collaterals by lending account id
     *
     * @param tenantId the id of tenant
     * @param lendingAccountId the id of the lending account
     * @return a Collaterals if record exists, otherwise <code>204</code>
     */
    @GetMapping(value = "/{tenantId}/lending-account/{lendingAccountId}")
    public ResponseEntity<Object> getCollateralsByLendingAccountId(@PathVariable(value = "tenantId") String tenantId,
                                                                 @PathVariable(value = "lendingAccountId") Long lendingAccountId) {
        Collaterals collaterals = collateralsService.findByLendingAccountId(lendingAccountId);
        if (collaterals != null ) {
            return new ResponseEntity<>(collaterals, HttpStatus.OK);
        } else {
            SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
            responseMessage.setMessages(RECORD_NOT_FOUND);
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Get Collaterals by status
     *
     * @param tenantId the id of tenant
     * @param status   the status
     * @return the list of all collaterals status if record exists, otherwise <code>204 - No Content</code>
     */
    @GetMapping(value = "/{tenantId}/status/{status}")
    public ResponseEntity<Object> getCollateralsByStatus(@PathVariable(value = "tenantId") String tenantId,
                                                       @PathVariable(value = "status") String status) {
        SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();

        if (status.equals("ACTIVE") || status.equals("INACTIVE")) {
            List<Collaterals> isPresentCollaterals = collateralsService.findByStatus(status);
            int size = isPresentCollaterals.size();
            if (size > 0) {
                return new ResponseEntity<>(isPresentCollaterals, HttpStatus.OK);
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
     * @param CollateralsAddResource the object to save
     * @return the message
     */
    @PostMapping(value = "/{tenantId}")
    public ResponseEntity<Object> addCollaterals(@PathVariable(value = "tenantId") String tenantId,
                                               @Valid @RequestBody CollateralsAddResource CollateralsAddResource) {
        if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty()) {
            throw new UserNotFound(environment.getProperty(COMMON_USER_NOT_FOUND));
        }
        Collaterals collaterals = collateralsService.addCollaterals(tenantId, CollateralsAddResource);
        SuccessAndErrorDetails successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty(RECORD_CREATED), Long.toString(collaterals.getId()));
        return new ResponseEntity<>(successAndErrorDetails, HttpStatus.CREATED);
    }

    /**
     * Updates the given brand record
     * @param tenantId the tenant id
     * @param id the id
     * @param CollateralsUpdateResource object to update
     * @return the message
     */
    @PutMapping(value = "/{tenantId}/{id}")
    public ResponseEntity<Object> updateCollaterals(@PathVariable(value = "tenantId") String tenantId,
                                                  @PathVariable(value = "id") Long id,
                                                  @Valid @RequestBody CollateralsUpdateResource CollateralsUpdateResource) {
        if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty()) {
            throw new UserNotFound(environment.getProperty(COMMON_USER_NOT_FOUND));
        }

        SuccessAndErrorDetails successAndErrorDetails = new SuccessAndErrorDetails();
        Optional<Collaterals> isPresentCollaterals = collateralsService.findById(id);
        if (isPresentCollaterals.isPresent()) {
            Collaterals collaterals = collateralsService.updateCollaterals(tenantId, id, CollateralsUpdateResource);
            successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty(RECORD_UPDATED),Long.toString(collaterals.getId()));
            return new ResponseEntity<>(successAndErrorDetails, HttpStatus.OK);
        } else {
            successAndErrorDetails.setMessages(RECORD_NOT_FOUND);
            return new ResponseEntity<>(successAndErrorDetails, HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
}
