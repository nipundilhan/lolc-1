package com.fusionx.lending.product.controller;

/**
 * Eligibility Template Legal Structure
 * <p>
 * *******************************************************************************************************
 * ###   Date         Story Point   Task No    Author       Description
 * -------------------------------------------------------------------------------------------------------
 * 1   21-07-2019   FXL-1         FXL-42     Dilki        Created
 * <p>
 * *******************************************************************************************************
 */

import com.fusionx.lending.product.Constants;
import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.EligibilityTemplateLegalStructure;
import com.fusionx.lending.product.domain.EligibilityTemplateLegalStructurePending;
import com.fusionx.lending.product.enums.CommonApproveStatus;
import com.fusionx.lending.product.enums.WorkflowStatus;
import com.fusionx.lending.product.exception.PageableLengthException;
import com.fusionx.lending.product.exception.UserNotFound;
import com.fusionx.lending.product.resources.EligibilityTemplateLegalStructureAddResource;
import com.fusionx.lending.product.resources.EligibilityTemplateLegalStructureUpdateResource;
import com.fusionx.lending.product.resources.SuccessAndErrorDetails;
import com.fusionx.lending.product.resources.SuccessAndErrorDetailsResource;
import com.fusionx.lending.product.service.EligibilityTemplateLegalStructureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/eligibility-legal-structure")
@CrossOrigin(origins = "*")
public class EligibilityTemplateLegalStructureController extends MessagePropertyBase {

    @Autowired
    public EligibilityTemplateLegalStructureService eligibilityTemplateLegalStructureService;

    /**
     * Returns the all records
     *
     * @param tenantId the tenant id
     * @return the all records
     */
    @GetMapping(value = "/{tenantId}/all")
    public ResponseEntity<Object> getAllEligibilityTemplateLegalStructure(@PathVariable(value = "tenantId") String tenantId) {

        SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
        List<EligibilityTemplateLegalStructure> eligibilityTemplateLegalStructure = eligibilityTemplateLegalStructureService.getAll();
        int size = eligibilityTemplateLegalStructure.size();

        if (size > 0) {
            return new ResponseEntity<>(eligibilityTemplateLegalStructure, HttpStatus.OK);
        } else {
            responseMessage.setMessages(RECORD_NOT_FOUND);
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Returns the record by id
     *
     * @param tenantId the tenant id
     * @param id       the id
     * @return the EligibilityTemplateLegalStructure Record.
     */
    @GetMapping(value = "/{tenantId}/{id}")
    public ResponseEntity<Object> getEligibilityTemplateLegalStructureById(
            @PathVariable(value = "tenantId") String tenantId,
            @PathVariable(value = "id") Long id) {
        SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
        Optional<EligibilityTemplateLegalStructure> isPresentEligibilityTemplateLegalStructure = eligibilityTemplateLegalStructureService
                .getById(id);
        if (isPresentEligibilityTemplateLegalStructure.isPresent()) {
            return new ResponseEntity<>(isPresentEligibilityTemplateLegalStructure.get(), HttpStatus.OK);
        } else {
            responseMessage.setMessages(RECORD_NOT_FOUND);
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Returns the EligibilityTemplateLegalStructure by status
     *
     * @param tenantId the tenant id
     * @param status   the status
     * @return the EligibilityTemplateLegalStructure
     */
    @GetMapping(value = "/{tenantId}/status/{status}")
    public ResponseEntity<Object> getEligibilityTemplateLegalStructureByStatus(
            @PathVariable(value = "tenantId") String tenantId,
            @PathVariable(value = "status") String status) {
        SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();

        if (status.equals("ACTIVE") || status.equals("INACTIVE")) {
            List<EligibilityTemplateLegalStructure> isPresentEligibilityTemplateLegalStructure = eligibilityTemplateLegalStructureService
                    .getByStatus(status);
            int size = isPresentEligibilityTemplateLegalStructure.size();
            if (size > 0) {
                return new ResponseEntity<>(isPresentEligibilityTemplateLegalStructure, HttpStatus.OK);
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
     * Saves the given EligibilityTemplateLegalStructure object
     *
     * @param tenantId                             the tenant id
     * @param EligibilityTemplateBranchAddResource the object to save
     * @return the message
     */
    @PostMapping(value = "/{tenantId}")
    public ResponseEntity<Object> addEligibilityTemplateLegalStructure(
            @PathVariable(value = "tenantId") String tenantId,
            @Valid @RequestBody EligibilityTemplateLegalStructureAddResource eligibilityTemplateAddResource) {
        if (LogginAuthentcation.getInstance().getUserName() == null
                || LogginAuthentcation.getInstance().getUserName().isEmpty()) {
            throw new UserNotFound(environment.getProperty(COMMON_USER_NOT_FOUND));
        }

        EligibilityTemplateLegalStructure eligibilityTemplateLegalStructureType = eligibilityTemplateLegalStructureService
                .addEligibilityTemplateLegalStructure(tenantId, eligibilityTemplateAddResource);
        SuccessAndErrorDetails successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty("rec.saved"),
                Long.toString(eligibilityTemplateLegalStructureType.getId()));
        return new ResponseEntity<>(successAndErrorDetails, HttpStatus.CREATED);
    }

    /**
     * Updates the given EligibilityTemplateLegalStructure record
     *
     * @param tenantId                                the tenant id
     * @param id                                      the id
     * @param EligibilityTemplateBranchUpdateResource object to update
     * @return the
     */
    @PutMapping(value = "/{tenantId}/{id}")
    public ResponseEntity<Object> updateEligibilityTemplateLegalStructure(
            @PathVariable(value = "tenantId") String tenantId, @PathVariable(value = "id") Long id,
            @Valid @RequestBody EligibilityTemplateLegalStructureUpdateResource eligibilityTemplateUpdateResource) {

        if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
            throw new UserNotFound(environment.getProperty(COMMON_USER_NOT_FOUND));

        SuccessAndErrorDetails successAndErrorDetails = new SuccessAndErrorDetails();
        Optional<EligibilityTemplateLegalStructure> isPresentEligibilityTemplateLegalStructure = eligibilityTemplateLegalStructureService
                .getById(id);
        if (isPresentEligibilityTemplateLegalStructure.isPresent()) {
            if (isPresentEligibilityTemplateLegalStructure.get().getApproveStatus() == null
                    || !isPresentEligibilityTemplateLegalStructure.get().getApproveStatus()
                    .equals(CommonApproveStatus.PENDING)) {
                EligibilityTemplateLegalStructure eligibilityTemplateLegalStructure = eligibilityTemplateLegalStructureService
                        .updateEligibilityTemplateLegalStructure(tenantId, id, eligibilityTemplateUpdateResource);
                successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty("rec.updated"),
                        eligibilityTemplateLegalStructure.getId().toString());
                return new ResponseEntity<>(successAndErrorDetails, HttpStatus.OK);
            } else {
                successAndErrorDetails.setMessages(environment.getProperty("common.can-not-update"));
                return new ResponseEntity<>(successAndErrorDetails, HttpStatus.UNPROCESSABLE_ENTITY);
            }

        } else {
            successAndErrorDetails.setMessages(RECORD_NOT_FOUND);
            return new ResponseEntity<>(successAndErrorDetails, HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    /**
     * approve EligibilityTemplateLegalStructure Pending
     *
     * @param @PathVariable{tenantId}
     * @param @PathVariable{pendingId}
     * @return SuccessAndErrorDetailsDto
     */
    @PutMapping(value = "{tenantId}/approve/{pendingId}")
    public ResponseEntity<Object> approve(@PathVariable(value = "tenantId") String tenantId,
                                          @PathVariable(value = "pendingId") Long pendingId) {

        if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
            throw new UserNotFound(environment.getProperty(COMMON_USER_NOT_FOUND));

        SuccessAndErrorDetailsResource successAndErrorDetailsResource = new SuccessAndErrorDetailsResource();
        Optional<EligibilityTemplateLegalStructurePending> isPresentEligiTempPending = eligibilityTemplateLegalStructureService.getByPendingId(pendingId);

        if (isPresentEligiTempPending.isPresent()) {

            if (eligibilityTemplateLegalStructureService.approveReject(tenantId, pendingId, WorkflowStatus.COMPLETE)) {
                successAndErrorDetailsResource = new SuccessAndErrorDetailsResource(environment.getProperty("common.approved"), pendingId.toString());
                return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.OK);
            } else {
                successAndErrorDetailsResource.setMessages(environment.getProperty("common.can-not-approved"));
                return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.UNPROCESSABLE_ENTITY);
            }

        } else {
            successAndErrorDetailsResource.setMessages(environment.getProperty(RECORD_NOT_FOUND));
            return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    /**
     * Reject EligibilityTemplateLegalStructure Pending
     *
     * @param @PathVariable{tenantId}
     * @param @PathVariable{pendingId}
     * @return SuccessAndErrorDetailsDto
     */
    @PutMapping(value = "{tenantId}/reject/{pendingId}")
    public ResponseEntity<Object> reject(@PathVariable(value = "tenantId") String tenantId,
                                         @PathVariable(value = "pendingId") Long pendingId) {

        if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
            throw new UserNotFound(environment.getProperty(COMMON_USER_NOT_FOUND));

        SuccessAndErrorDetailsResource successAndErrorDetailsResource = new SuccessAndErrorDetailsResource();
        Optional<EligibilityTemplateLegalStructurePending> isPresentEligiPending = eligibilityTemplateLegalStructureService
                .getByPendingId(pendingId);
        if (isPresentEligiPending.isPresent()) {

            if (eligibilityTemplateLegalStructureService.approveReject(tenantId, pendingId, WorkflowStatus.REJECT)) {
                successAndErrorDetailsResource = new SuccessAndErrorDetailsResource(
                        environment.getProperty("common.rejected"), pendingId.toString());
                return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.OK);
            } else {
                successAndErrorDetailsResource.setMessages(environment.getProperty("common.can-not-rejected"));
                return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.UNPROCESSABLE_ENTITY);
            }

        } else {
            successAndErrorDetailsResource.setMessages(environment.getProperty(RECORD_NOT_FOUND));
            return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    /**
     * Gets the EligibilityTemplateLegalStructure by pending id.
     *
     * @param tenantId  - the tenant id
     * @param pendingId - the pending id
     * @return EligibilityTemplateLegalStructurePending
     */
    @GetMapping(value = "/{tenantId}/pending/{pendingId}")
    public ResponseEntity<Object> getEligibilityTemplateLegalStructurePendingByPendingId(
            @PathVariable(value = "tenantId") String tenantId,
            @PathVariable(value = "pendingId") Long pendingId) {
        SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
        Optional<EligibilityTemplateLegalStructurePending> isPresentEligibilityTemplateLegalStructurePending = eligibilityTemplateLegalStructureService
                .getByPendingId(pendingId);
        if (isPresentEligibilityTemplateLegalStructurePending.isPresent()) {
            return new ResponseEntity<>(isPresentEligibilityTemplateLegalStructurePending, HttpStatus.OK);
        } else {
            responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Search EligibilityTemplateLegalStructure pending.
     *
     * @param tenantId      - the tenant id
     * @param searchq       - the searchq
     * @param status        - the status
     * @param approveStatus - the approve status
     * @param pageable      - the pageable
     * @return the page
     */
    @GetMapping(value = "/{tenantId}/workflow-items/search")
    public Page<EligibilityTemplateLegalStructurePending> searchEligibilityTemplateLegalStructurePending(
            @PathVariable(value = "tenantId") String tenantId,
            @RequestParam(value = "searchq", required = false) String searchq,
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "approveStatus", required = false) String approveStatus,
            @PageableDefault(size = 10) Pageable pageable) {
        if (pageable.getPageSize() > Constants.MAX_PAGEABLE_LENGTH)
            throw new PageableLengthException(environment.getProperty(PAGEABLE_LENGTH));
        return eligibilityTemplateLegalStructureService.searchEligibilityTemplateLegalStructurePending(searchq, status,
                approveStatus, pageable);
    }
}
