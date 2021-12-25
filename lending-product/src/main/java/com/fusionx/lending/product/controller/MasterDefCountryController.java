package com.fusionx.lending.product.controller;

import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.MasterDefinitionPending;
import com.fusionx.lending.product.exception.UserNotFound;
import com.fusionx.lending.product.resources.*;
import com.fusionx.lending.product.service.MasterDefCountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * API Service related to Lending Module Definition - Location Definition
 *
 * @author Nipun Dilhan (Inova)
 * @version 1.0.0
 * @since 1.0.0
 * <p>
 * <br/><br/>
 * <b>Version History : </b>
 * <br/><br/>
 * #        Date            Story Point     Task No             Author                  Description     Verified By     Verified Date
 * <br/>
 * .....................................................................................................................................<br/>
 * 1        10-Sep-2021						FXL-775				NipunD (Inova)			Created			ChinthakaMa     16-Sep-2021
 * <p>
 */
@RestController
@RequestMapping("/master-def-country")
@CrossOrigin("*")
public class MasterDefCountryController extends MessagePropertyBase {

    private MasterDefCountryService masterDefCountryService;

    @Autowired
    public void setMasterDefCountryService(MasterDefCountryService masterDefCountryService) {
        this.masterDefCountryService = masterDefCountryService;
    }


    /**
     * Saves the master definition country mapping
     *
     * @param tenantId                        the id of the tenant
     * @param masterDefCountryMainAddResource the object to save
     * @return message if record successfully saved.
     * @see MasterDefCountryMainAddResource
     */
    @PostMapping(value = "/{tenantId}/master-definition/{id}")
    public ResponseEntity<Object> addMasterDefCountry(@PathVariable(value = "tenantId") String tenantId,
                                                      @PathVariable(value = "id") Long id,
                                                      @Valid @RequestBody MasterDefCountryMainAddResource masterDefCountryMainAddResource) {
        if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty()) {
            throw new UserNotFound(environment.getProperty(COMMON_USER_NOT_FOUND));
        }

        masterDefCountryService.create(masterDefCountryMainAddResource, id, LogginAuthentcation.getInstance().getUserName(), tenantId);

        SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails(environment.getProperty(RECORD_CREATED));
        return new ResponseEntity<>(responseMessage, HttpStatus.CREATED);
    }

    /**
     * Get Country mapping details by master definition id
     *
     * @param tenantId the id of tenant
     * @param id       the id of the master definition
     * @return list of countries if record exists, otherwise <code>204</code>
     */
    @GetMapping(value = "/{tenantId}/master-definition/{id}")
    public ResponseEntity<Object> getCountryMappingDetailsByMasterDefId(
            @PathVariable(value = "tenantId") String tenantId,
            @PathVariable(value = "id") Long id) {
        SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
        MasterDefLocationDetailsReponse response = masterDefCountryService.findByMasterDefinitionId(id);

        if (response != null) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            responseMessage.setMessages(RECORD_NOT_FOUND);
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }


    /**
     * Get Country mapping pending details by master defintion pending id
     *
     * @param tenantId the id of tenant
     * @param id       the id of the master definition pending
     * @return list of countries if record exists, otherwise <code>204</code>
     */
    @GetMapping(value = "/{tenantId}/master-definition-pending/{id}")
    public ResponseEntity<Object> getCountryMappingDetailsByMasterDefPendingId(
            @PathVariable(value = "tenantId") String tenantId,
            @PathVariable(value = "id") Long id) {
        SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
        MasterDefLocationDetailsPendingReponse response = masterDefCountryService.findByMasterDefinitionPendingId(id);

        if (response != null) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            responseMessage.setMessages(RECORD_NOT_FOUND);
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Updates the given master definition country  mapping.
     *
     * @param tenantId                           the id of the tenant
     * @param id                                 the id of the master definition
     * @param masterDefCountryMainUpdateResource the object which contains data
     * @return the SuccessAndErrorDetails
     * @see MasterDefCountryMainUpdateResource
     */
    @PutMapping(value = "/{tenantId}/master-definition/{id}")
    public ResponseEntity<Object> updateMasterDefCountry(@PathVariable(value = "tenantId") String tenantId,
                                                         @PathVariable(value = "id") Long id,
                                                         @Valid @RequestBody MasterDefCountryMainUpdateResource masterDefCountryMainUpdateResource) {
        if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty()) {
            throw new UserNotFound(environment.getProperty(COMMON_USER_NOT_FOUND));
        }

        SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
        MasterDefinitionPending masterDefinitionPending = masterDefCountryService.updateMasterDefinitionCountry(tenantId, LogginAuthentcation.getInstance().getUserName(), id, masterDefCountryMainUpdateResource);

        if (masterDefinitionPending != null) {
            responseMessage = new SuccessAndErrorDetails(environment.getProperty(RECORD_UPDATED), masterDefinitionPending.getId().toString());
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } else {
            responseMessage.setMessages(COMMON_INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    //tempory written for testing
    @PutMapping(value = "/{tenantId}/temp-approve/master-definition-pending/{id}")
    public ResponseEntity<Object> temporaryApprove(@PathVariable(value = "tenantId") String tenantId,
                                                   @PathVariable(value = "id") Long id) {
        if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty()) {
            throw new UserNotFound(environment.getProperty(COMMON_USER_NOT_FOUND));
        }
        SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
        MasterDefinitionPending masterDefinitionPending = masterDefCountryService.updateMasterDefCountryUsingPending(id);

        if (masterDefinitionPending != null) {
            responseMessage = new SuccessAndErrorDetails(environment.getProperty(RECORD_UPDATED), masterDefinitionPending.getId().toString());
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } else {
            responseMessage.setMessages(COMMON_INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
