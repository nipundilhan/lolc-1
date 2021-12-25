package com.fusionx.lending.product.controller;


import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.MasterDefinition;
import com.fusionx.lending.product.domain.MasterDefinitionPending;
import com.fusionx.lending.product.enums.CommonApproveStatus;
import com.fusionx.lending.product.enums.WorkflowStatus;
import com.fusionx.lending.product.exception.UserNotFound;
import com.fusionx.lending.product.resources.*;
import com.fusionx.lending.product.service.MasterDefinitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * API Service related to Lending Module Definition - Master Definition
 *
 * @author Dilki Kalansooriya (Inova)
 * @version 1.0.0
 * @since 1.0.0
 * <p>
 * <br/><br/>
 * <b>Version History : </b>
 * <br/><br/>
 * #        Date            Story Point     Task No             Author                  Description     Verified By     Verified Date
 * <br/>
 * .....................................................................................................................................<br/>
 * 1        12-July-2021	FXL-1			FXL-5				DilkiK (Inova)			Created			ChinthakaMa     16-Sep-2021
 * <p>
 */
@RestController
@RequestMapping(value = "/master-definition")
@CrossOrigin(origins = "*")
public class MasterDefinitionController extends MessagePropertyBase {
    private MasterDefinitionService masterDefinitionService;

    @Autowired
    public void setMasterDefinitionService(MasterDefinitionService masterDefinitionService) {
        this.masterDefinitionService = masterDefinitionService;
    }

    /**
     * Gets list of all items
     *
     * @param tenantId the tenant id
     * @return the list of all items.
     */
    @GetMapping(value = "/{tenantId}/all")
    public ResponseEntity<Object> getAllMasterDefinition(
            @PathVariable(value = "tenantId") String tenantId) {

        List<MasterDefinition> masterDefinition = masterDefinitionService.getAll();
        int size = masterDefinition.size();

        if (size > 0) {
            return new ResponseEntity<>(masterDefinition, HttpStatus.OK);
        } else {
            SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
            responseMessage.setMessages(RECORD_NOT_FOUND);
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Get Master Definition by id
     *
     * @param tenantId the tenant id
     * @param id       the id of the record
     * @return the Master Definition record.
     */
    @GetMapping(value = "/{tenantId}/{id}")
    public ResponseEntity<Object> getMasterDefinitionById(
            @PathVariable(value = "tenantId") String tenantId,
            @PathVariable(value = "id") Long id) {

        Optional<MasterDefinition> isPresentMasterDefinition = masterDefinitionService.getById(id);

        if (isPresentMasterDefinition.isPresent()) {
            return new ResponseEntity<>(isPresentMasterDefinition.get(), HttpStatus.OK);
        } else {
            SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
            responseMessage.setMessages(RECORD_NOT_FOUND);
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Gets Master Definition by code
     *
     * @param tenantId the tenant id
     * @param code     the code
     * @return the master definition record.
     */
    @GetMapping(value = "/{tenantId}/code/{code}")
    public ResponseEntity<Object> getMasterDefinitionByCode(
            @PathVariable(value = "tenantId") String tenantId,
            @PathVariable(value = "code") String code) {

        Optional<MasterDefinition> isPresentMasterDefinition = masterDefinitionService.getByCode(code);

        if (isPresentMasterDefinition.isPresent()) {
            return new ResponseEntity<>(isPresentMasterDefinition.get(), HttpStatus.OK);
        } else {
            SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
            responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Gets master definitions by status
     *
     * @param tenantId the tenant id
     * @param status   the status should be either <code>ACTIVE</code> or <code>INACTIVE</code>
     * @return the list of master definitions.
     */
    @GetMapping(value = "/{tenantId}/status/{status}")
    public ResponseEntity<Object> getMasterDefinitionByStatus(
            @PathVariable(value = "tenantId") String tenantId,
            @PathVariable(value = "status") String status) {

        SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();

        if (status.equals("ACTIVE") || status.equals("INACTIVE")) {
            List<MasterDefinition> isPresentMasterDefinition = masterDefinitionService.getByStatus(status);
            int size = isPresentMasterDefinition.size();

            if (size > 0) {
                return new ResponseEntity<>(isPresentMasterDefinition, HttpStatus.OK);
            } else {
                responseMessage.setMessages(RECORD_NOT_FOUND);
                return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
            }
        } else {
            responseMessage.setMessages(environment.getProperty("invalid-status"));
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Saves the given master definition record
     *
     * @param tenantId                    the tenant id
     * @param masterDefinitionAddResource the resource file
     * @return the saved record id.
     */
    @PostMapping(value = "/{tenantId}")
    public ResponseEntity<Object> addMasterDefinition(
            @PathVariable(value = "tenantId") String tenantId,
            @Valid @RequestBody MasterDefinitionAddResource masterDefinitionAddResource) {

        if (LogginAuthentcation.getInstance().getUserName() == null
                || LogginAuthentcation.getInstance().getUserName().isEmpty()) {
            throw new UserNotFound(environment.getProperty(COMMON_USER_NOT_FOUND));
        }

        MasterDefinition masterDefinitionType = masterDefinitionService.addMasterDefinition(tenantId, masterDefinitionAddResource);
        SuccessAndErrorDetails successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty(COMMON_SAVED),
                Long.toString(masterDefinitionType.getId()));
        return new ResponseEntity<>(successAndErrorDetails, HttpStatus.CREATED);
    }

    /**
     * Updates the given master definition record
     *
     * @param tenantId                       the tenant id
     * @param id                             the id of record
     * @param masterDefinitionUpdateResource the resource file
     * @return the updated record id with message
     */
    @PutMapping(value = "/{tenantId}/{id}")
    public ResponseEntity<Object> updateMasterDefinition(
            @PathVariable(value = "tenantId") String tenantId,
            @PathVariable(value = "id") Long id,
            @Valid @RequestBody MasterDefinitionUpdateResource masterDefinitionUpdateResource) {

        if (LogginAuthentcation.getInstance().getUserName() == null
                || LogginAuthentcation.getInstance().getUserName().isEmpty()) {
            throw new UserNotFound(environment.getProperty(COMMON_USER_NOT_FOUND));
        }

        SuccessAndErrorDetails successAndErrorDetails = new SuccessAndErrorDetails();
        Optional<MasterDefinition> isPresentMasterDefinition = masterDefinitionService.getById(id);

        if (isPresentMasterDefinition.isPresent()) {
            if (isPresentMasterDefinition.get().getApproveStatus() == null
                    || !isPresentMasterDefinition.get().getApproveStatus().equals(CommonApproveStatus.PENDING)) {
                MasterDefinition masterDefinition = masterDefinitionService.updateMasterDefinition(tenantId, id,
                        masterDefinitionUpdateResource);
                successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty(COMMON_UPDATED),
                        masterDefinition.getId().toString());
                return new ResponseEntity<>(successAndErrorDetails, HttpStatus.OK);
            } else {
                successAndErrorDetails.setMessages(environment.getProperty(CANT_UPDATED));
                return new ResponseEntity<>(successAndErrorDetails, HttpStatus.UNPROCESSABLE_ENTITY);
            }
        } else {
            successAndErrorDetails.setMessages(RECORD_NOT_FOUND);
            return new ResponseEntity<>(successAndErrorDetails, HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    /**
     * Approves  pending master definition record
     *
     * @param tenantId  the tenant id
     * @param pendingId the pending record id
     * @return the success message with pending record id.
     */
    @PutMapping(value = "{tenantId}/approve/{pendingId}")
    public ResponseEntity<Object> approve(@PathVariable(value = "tenantId") String tenantId,
                                          @PathVariable(value = "pendingId") Long pendingId) {

        SuccessAndErrorDetailsResource successAndErrorDetailsResource = new SuccessAndErrorDetailsResource();
        Optional<MasterDefinitionPending> isPresentEligiTempPending = masterDefinitionService.getByPendingId(pendingId);

        if (isPresentEligiTempPending.isPresent()) {

            if (masterDefinitionService.approveReject(tenantId, pendingId, WorkflowStatus.COMPLETE)) {
                successAndErrorDetailsResource = new SuccessAndErrorDetailsResource(
                        environment.getProperty(COMMON_APPROVED), pendingId.toString());
                return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.OK);
            } else {
                successAndErrorDetailsResource.setMessages(environment.getProperty(CANT_APPROVED));
                return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.UNPROCESSABLE_ENTITY);
            }

        } else {
            successAndErrorDetailsResource.setMessages(environment.getProperty(RECORD_NOT_FOUND));
            return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    /**
     * Rejects the pending master definition record.
     *
     * @param tenantId  the tenant id
     * @param pendingId the pending record id
     * @return the success message with pending record id.
     */
    @PutMapping(value = "{tenantId}/reject/{pendingId}")
    public ResponseEntity<Object> reject(@PathVariable(value = "tenantId") String tenantId,
                                         @PathVariable(value = "pendingId") Long pendingId) {

        SuccessAndErrorDetailsResource successAndErrorDetailsResource = new SuccessAndErrorDetailsResource();
        Optional<MasterDefinitionPending> isPresentEligiPending = masterDefinitionService.getByPendingId(pendingId);

        if (isPresentEligiPending.isPresent()) {

            if (masterDefinitionService.approveReject(tenantId, pendingId, WorkflowStatus.REJECT)) {
                successAndErrorDetailsResource = new SuccessAndErrorDetailsResource(
                        environment.getProperty(COMMON_REJECTED), pendingId.toString());
                return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.OK);
            } else {
                successAndErrorDetailsResource.setMessages(environment.getProperty(CANT_REJECTED));
                return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.UNPROCESSABLE_ENTITY);
            }

        } else {
            successAndErrorDetailsResource.setMessages(environment.getProperty(RECORD_NOT_FOUND));
            return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    /**
     * retrieve contract number by lending account id
     *
     * @param tenantId  the tenant id
     * @param code the master definition code
     * @return
     */
    @GetMapping(value = "/{tenantId}/next-contract-number/{code}")
    public ResponseEntity<Object> getContractNumberByAccountId(@PathVariable(value = "tenantId") String tenantId,
                                                               @PathVariable(value = "code") String code) {
        SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
        ContractNumberResponseResource contractNumberResponseResource = masterDefinitionService.getNextContractNumberByAccountId(tenantId, code);
        if (contractNumberResponseResource != null) {
            return new ResponseEntity<>(contractNumberResponseResource, HttpStatus.OK);
        } else {
            responseMessage.setMessages(RECORD_NOT_FOUND);
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }
}
