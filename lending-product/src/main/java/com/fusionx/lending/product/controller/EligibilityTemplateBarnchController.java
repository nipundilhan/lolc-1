package com.fusionx.lending.product.controller;

/**
 * Eligibility Template Branch
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
import com.fusionx.lending.product.domain.EligibilityTemplateBranch;
import com.fusionx.lending.product.domain.EligibilityTemplateBranchPending;
import com.fusionx.lending.product.enums.CommonApproveStatus;
import com.fusionx.lending.product.enums.WorkflowStatus;
import com.fusionx.lending.product.exception.PageableLengthException;
import com.fusionx.lending.product.exception.UserNotFound;
import com.fusionx.lending.product.resources.EligibilityTemplateBranchUpdateResource;
import com.fusionx.lending.product.resources.SuccessAndErrorDetails;
import com.fusionx.lending.product.resources.SuccessAndErrorDetailsResource;
import com.fusionx.lending.product.service.EligibilityTemplateBranchService;
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
@RequestMapping(value = "/eligibility-branch")
@CrossOrigin(origins = "*")
public class EligibilityTemplateBarnchController extends MessagePropertyBase {

    @Autowired
    public EligibilityTemplateBranchService eligibilityTemplateBranchService;

    /**
     * Returns the all records
     *
     * @param tenantId the tenant id
     * @return the all records
     */
    @GetMapping(value = "/{tenantId}/all")
    public ResponseEntity<Object> getAllEligibilityTemplateBranch(
            @PathVariable(value = "tenantId", required = true) String tenantId) {

        List<EligibilityTemplateBranch> eligibilityTemplateBranch = eligibilityTemplateBranchService.getAll();
        int size = eligibilityTemplateBranch.size();
        if (size > 0) {
            return new ResponseEntity<>(eligibilityTemplateBranch, HttpStatus.OK);
        } else {
            SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
            responseMessage.setMessages(RECORD_NOT_FOUND);
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Returns the record by id
     *
     * @param tenantId the tenant id
     * @param id       the id
     * @return the EligibilityTemplateBranch Record.
     */
    @GetMapping(value = "/{tenantId}/{id}")
    public ResponseEntity<Object> getEligibilityTemplateBranchById(
            @PathVariable(value = "tenantId", required = true) String tenantId,
            @PathVariable(value = "id", required = true) Long id) {

        Optional<EligibilityTemplateBranch> isPresentEligibilityTemplateBranch = eligibilityTemplateBranchService
                .getById(id);
        if (isPresentEligibilityTemplateBranch.isPresent()) {
            return new ResponseEntity<>(isPresentEligibilityTemplateBranch.get(), HttpStatus.OK);
        } else {
            SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
            responseMessage.setMessages(RECORD_NOT_FOUND);
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Returns the EligibilityTemplateLegalStructures by status
     *
     * @param tenantId the tenant id
     * @param status   the status
     * @return the EligibilityTemplateLegalStructures
     */
    @GetMapping(value = "/{tenantId}/status/{status}")
    public ResponseEntity<Object> getEligibilityTemplateBranchByStatus(
            @PathVariable(value = "tenantId", required = true) String tenantId,
            @PathVariable(value = "status", required = true) String status) {

        if (status.equals("ACTIVE") || status.equals("INACTIVE")) {
            List<EligibilityTemplateBranch> isPresentEligibilityTemplateBranch = eligibilityTemplateBranchService
                    .getByStatus(status);
            int size = isPresentEligibilityTemplateBranch.size();
            if (size > 0) {
                return new ResponseEntity<>(isPresentEligibilityTemplateBranch, HttpStatus.OK);
            } else {
                SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
                responseMessage.setMessages(RECORD_NOT_FOUND);
                return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
            }
        } else {
            SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
            responseMessage.setMessages(environment.getProperty("invalid-status"));
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Saves the given EligibilityTemplateLegalStructure object
     *
     * @param tenantId                             the tenant id
     * @param eligibilityTemplateBranchAddResource the object to save
     * @return the message
     */
    @PostMapping(value = "/{tenantId}")
    public ResponseEntity<Object> addEligibilityTemplateBranch(
            @PathVariable(value = "tenantId", required = true) String tenantId,
            @Valid @RequestBody EligibilityTemplateBranchUpdateResource eligibilityTemplateBranchAddResource) {
        if (LogginAuthentcation.getInstance().getUserName() == null
                || LogginAuthentcation.getInstance().getUserName().isEmpty()) {
            throw new UserNotFound(environment.getProperty(COMMON_USER_NOT_FOUND));
        }

        EligibilityTemplateBranch eligibilityTemplateBranchType = eligibilityTemplateBranchService
                .addEligibilityTemplateBranch(tenantId, eligibilityTemplateBranchAddResource);
        SuccessAndErrorDetails successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty("rec.saved"),
                Long.toString(eligibilityTemplateBranchType.getId()));
        return new ResponseEntity<>(successAndErrorDetails, HttpStatus.CREATED);
    }

    /**
     * Updates the given EligibilityTemplateLegalStructure record
     *
     * @param tenantId                                the tenant id
     * @param id                                      the id
     * @param eligibilityTemplateBranchUpdateResource object to update
     * @return the
     */
    @PutMapping(value = "/{tenantId}/{id}")
    public ResponseEntity<Object> updateEligibilityTemplateBranch(@PathVariable(value = "tenantId") String tenantId,
                                                                  @PathVariable(value = "id") Long id,
                                                                  @Valid @RequestBody EligibilityTemplateBranchUpdateResource eligibilityTemplateBranchUpdateResource) {

        if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty()) {
            throw new UserNotFound(environment.getProperty(COMMON_USER_NOT_FOUND));
        }

        SuccessAndErrorDetails successAndErrorDetails = new SuccessAndErrorDetails();
        Optional<EligibilityTemplateBranch> isPresentEligibilityTemplateBranch = eligibilityTemplateBranchService.getById(id);

        if (isPresentEligibilityTemplateBranch.isPresent()) {
            if (isPresentEligibilityTemplateBranch.get().getApproveStatus() == null || !isPresentEligibilityTemplateBranch.get().getApproveStatus().equals(CommonApproveStatus.PENDING)) {
                EligibilityTemplateBranch eligibilityTemplateBranch = eligibilityTemplateBranchService.updateEligibilityTemplateBranch(tenantId, id, eligibilityTemplateBranchUpdateResource);
                successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty("rec.updated"), eligibilityTemplateBranch.getId().toString());
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
     * approve EligibilityTemplateBranch Pending
     *
     * @param @PathVariable{tenantId}
     * @param @PathVariable{pendingId}
     * @return SuccessAndErrorDetailsDto
     */
    @PutMapping(value = "{tenantId}/approve/{pendingId}")
    public ResponseEntity<Object> approve(@PathVariable(value = "tenantId", required = true) String tenantId,
                                          @PathVariable(value = "pendingId", required = true) Long pendingId) {


        if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty()) {
            throw new UserNotFound(environment.getProperty(COMMON_USER_NOT_FOUND));
        }

        SuccessAndErrorDetailsResource successAndErrorDetailsResource = new SuccessAndErrorDetailsResource();
        Optional<EligibilityTemplateBranchPending> isPresentEligiTempPending = eligibilityTemplateBranchService
                .getByPendingId(pendingId);
        if (isPresentEligiTempPending.isPresent()) {

            if (eligibilityTemplateBranchService.approveReject(tenantId, pendingId, WorkflowStatus.COMPLETE)) {
                successAndErrorDetailsResource = new SuccessAndErrorDetailsResource(
                        environment.getProperty("common.approved"), pendingId.toString());
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
     * Reject EligibilityTemplateBranch Pending
     *
     * @param @PathVariable{tenantId}
     * @param @PathVariable{pendingId}
     * @return SuccessAndErrorDetailsDto
     */
    @PutMapping(value = "{tenantId}/reject/{pendingId}")
    public ResponseEntity<Object> reject(@PathVariable(value = "tenantId", required = true) String tenantId,
                                         @PathVariable(value = "pendingId", required = true) Long pendingId) {

        if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty()) {
            throw new UserNotFound(environment.getProperty(COMMON_USER_NOT_FOUND));
        }

        SuccessAndErrorDetailsResource successAndErrorDetailsResource = new SuccessAndErrorDetailsResource();
        Optional<EligibilityTemplateBranchPending> isPresentEligiPending = eligibilityTemplateBranchService
                .getByPendingId(pendingId);
        if (isPresentEligiPending.isPresent()) {

            if (eligibilityTemplateBranchService.approveReject(tenantId, pendingId, WorkflowStatus.REJECT)) {
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
     * Gets the EligibilityTemplateBranch by pending id.
     *
     * @param tenantId  - the tenant id
     * @param pendingId - the pending id
     * @return EligibilityTemplateBranchPending
     */
    @GetMapping(value = "/{tenantId}/pending/{pendingId}")
    public ResponseEntity<Object> getEligibilityTemplateBranchPendingByPendingId(
            @PathVariable(value = "tenantId", required = true) String tenantId,
            @PathVariable(value = "pendingId", required = true) Long pendingId) {

        Optional<EligibilityTemplateBranchPending> isPresentEligibilityTemplateBranchPending = eligibilityTemplateBranchService
                .getByPendingId(pendingId);
        if (isPresentEligibilityTemplateBranchPending.isPresent()) {
            return new ResponseEntity<>(isPresentEligibilityTemplateBranchPending, HttpStatus.OK);
        } else {
            SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
            responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Search EligibilityTemplateBranch pending.
     *
     * @param tenantId      - the tenant id
     * @param searchq       - the searchq
     * @param status        - the status
     * @param approveStatus - the approve status
     * @param pageable      - the pageable
     * @return the page
     */
    @GetMapping(value = "/{tenantId}/workflow-items/search")
    public Page<EligibilityTemplateBranchPending> searchEligibilityTemplateBranchPending(
            @PathVariable(value = "tenantId", required = true) String tenantId,
            @RequestParam(value = "searchq", required = false) String searchq,
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "approveStatus", required = false) String approveStatus,
            @PageableDefault(size = 10) Pageable pageable) {
        if (pageable.getPageSize() > Constants.MAX_PAGEABLE_LENGTH)
            throw new PageableLengthException(environment.getProperty(PAGEABLE_LENGTH));
        return eligibilityTemplateBranchService.searchEligibilityTemplateBranchPending(searchq, status, approveStatus,
                pageable);
    }
}
