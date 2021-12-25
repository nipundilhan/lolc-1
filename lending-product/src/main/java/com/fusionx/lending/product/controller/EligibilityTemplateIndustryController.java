package com.fusionx.lending.product.controller;

/**
 * Eligibility Template Industry
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
import com.fusionx.lending.product.domain.EligibilityTemplateIndustry;
import com.fusionx.lending.product.domain.EligibilityTemplateIndustryPending;
import com.fusionx.lending.product.enums.CommonApproveStatus;
import com.fusionx.lending.product.enums.WorkflowStatus;
import com.fusionx.lending.product.exception.PageableLengthException;
import com.fusionx.lending.product.exception.UserNotFound;
import com.fusionx.lending.product.resources.EligibilityTemplateIndustryAddResource;
import com.fusionx.lending.product.resources.EligibilityTemplateIndustryUpdateResource;
import com.fusionx.lending.product.resources.SuccessAndErrorDetails;
import com.fusionx.lending.product.resources.SuccessAndErrorDetailsResource;
import com.fusionx.lending.product.service.EligibilityTemplateIndustryService;
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
@RequestMapping(value = "/eligibility-industry")
@CrossOrigin(origins = "*")
public class EligibilityTemplateIndustryController extends MessagePropertyBase {

    @Autowired
    public EligibilityTemplateIndustryService eligibilityTemplateIndustryService;

    /**
     * Returns the all records
     *
     * @param tenantId the tenant id
     * @return the all records
     */
    @GetMapping(value = "/{tenantId}/all")
    public ResponseEntity<Object> getAllEligibilityTemplateIndustry(
            @PathVariable(value = "tenantId", required = true) String tenantId) {
        SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
        List<EligibilityTemplateIndustry> eligibilityTemplateIndustry = eligibilityTemplateIndustryService.getAll();
        int size = eligibilityTemplateIndustry.size();
        if (size > 0) {
            return new ResponseEntity<>(eligibilityTemplateIndustry, HttpStatus.OK);
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
     * @return the EligibilityTemplateIndustry Record.
     */
    @GetMapping(value = "/{tenantId}/{id}")
    public ResponseEntity<Object> getEligibilityTemplateIndustryById(
            @PathVariable(value = "tenantId", required = true) String tenantId,
            @PathVariable(value = "id", required = true) Long id) {
        SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
        Optional<EligibilityTemplateIndustry> isPresentEligibilityTemplateIndustry = eligibilityTemplateIndustryService
                .getById(id);
        if (isPresentEligibilityTemplateIndustry.isPresent()) {
            return new ResponseEntity<>(isPresentEligibilityTemplateIndustry.get(), HttpStatus.OK);
        } else {
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
    public ResponseEntity<Object> getEligibilityTemplateIndustryByStatus(
            @PathVariable(value = "tenantId", required = true) String tenantId,
            @PathVariable(value = "status", required = true) String status) {
        SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();

        if (status.equals("ACTIVE") || status.equals("INACTIVE")) {
            List<EligibilityTemplateIndustry> isPresentEligibilityTemplateIndustry = eligibilityTemplateIndustryService
                    .getByStatus(status);
            int size = isPresentEligibilityTemplateIndustry.size();
            if (size > 0) {
                return new ResponseEntity<>(isPresentEligibilityTemplateIndustry, HttpStatus.OK);
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
    public ResponseEntity<Object> addEligibilityTemplateIndustry(
            @PathVariable(value = "tenantId", required = true) String tenantId,
            @Valid @RequestBody EligibilityTemplateIndustryAddResource eligibilityTemplateAddResource) {
        if (LogginAuthentcation.getInstance().getUserName() == null
                || LogginAuthentcation.getInstance().getUserName().isEmpty()) {
            throw new UserNotFound(environment.getProperty(COMMON_USER_NOT_FOUND));
        }
        EligibilityTemplateIndustry eligibilityTemplateIndustryType = eligibilityTemplateIndustryService
                .addEligibilityTemplateIndustry(tenantId, eligibilityTemplateAddResource);
        SuccessAndErrorDetails successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty("rec.saved"),
                Long.toString(eligibilityTemplateIndustryType.getId()));
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
    public ResponseEntity<Object> updateEligibilityTemplateIndustry(@PathVariable(value = "tenantId") String tenantId,
                                                                    @PathVariable(value = "id") Long id,
                                                                    @Valid @RequestBody EligibilityTemplateIndustryUpdateResource eligibilityTemplateUpdateResource) {
        if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty()) {
            throw new UserNotFound(environment.getProperty(COMMON_USER_NOT_FOUND));
        }

        SuccessAndErrorDetails successAndErrorDetails = new SuccessAndErrorDetails();
        Optional<EligibilityTemplateIndustry> isPresentEligibilityTemplateIndustry = eligibilityTemplateIndustryService.getById(id);

        if (isPresentEligibilityTemplateIndustry.isPresent()) {
            if (isPresentEligibilityTemplateIndustry.get().getApproveStatus() == null || !isPresentEligibilityTemplateIndustry.get().getApproveStatus().equals(CommonApproveStatus.PENDING)) {
                EligibilityTemplateIndustry eligibilityTemplateIndustry = eligibilityTemplateIndustryService.updateEligibilityTemplateIndustry(tenantId, id, eligibilityTemplateUpdateResource);
                successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty("rec.updated"), eligibilityTemplateIndustry.getId().toString());

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
     * approve EligibilityTemplateIndustry Pending
     *
     * @param @PathVariable{tenantId}
     * @param @PathVariable{pendingId}
     * @return SuccessAndErrorDetailsDto
     */
    @PutMapping(value = "{tenantId}/approve/{pendingId}")
    public ResponseEntity<Object> approve(@PathVariable(value = "tenantId", required = true) String tenantId,
                                          @PathVariable(value = "pendingId", required = true) Long pendingId) {
        SuccessAndErrorDetailsResource successAndErrorDetailsResource = new SuccessAndErrorDetailsResource();
        Optional<EligibilityTemplateIndustryPending> isPresentEligiTempPending = eligibilityTemplateIndustryService
                .getByPendingId(pendingId);
        if (isPresentEligiTempPending.isPresent()) {

            if (eligibilityTemplateIndustryService.approveReject(tenantId, pendingId, WorkflowStatus.COMPLETE)) {
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
     * Reject EligibilityTemplateIndustry Pending
     *
     * @param @PathVariable{tenantId}
     * @param @PathVariable{pendingId}
     * @return SuccessAndErrorDetailsDto
     */
    @PutMapping(value = "{tenantId}/reject/{pendingId}")
    public ResponseEntity<Object> reject(@PathVariable(value = "tenantId", required = true) String tenantId,
                                         @PathVariable(value = "pendingId", required = true) Long pendingId) {
        SuccessAndErrorDetailsResource successAndErrorDetailsResource = new SuccessAndErrorDetailsResource();
        Optional<EligibilityTemplateIndustryPending> isPresentEligiPending = eligibilityTemplateIndustryService
                .getByPendingId(pendingId);
        if (isPresentEligiPending.isPresent()) {

            if (eligibilityTemplateIndustryService.approveReject(tenantId, pendingId, WorkflowStatus.REJECT)) {
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
     * Gets the EligibilityTemplateIndustry by pending id.
     *
     * @param tenantId  - the tenant id
     * @param pendingId - the pending id
     * @return EligibilityTemplateIndustryPending
     */
    @GetMapping(value = "/{tenantId}/pending/{pendingId}")
    public ResponseEntity<Object> getEligibilityTemplateIndustryPendingByPendingId(
            @PathVariable(value = "tenantId", required = true) String tenantId,
            @PathVariable(value = "pendingId", required = true) Long pendingId) {
        SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
        Optional<EligibilityTemplateIndustryPending> isPresentEligibilityTemplateIndustryPending = eligibilityTemplateIndustryService
                .getByPendingId(pendingId);
        if (isPresentEligibilityTemplateIndustryPending.isPresent()) {
            return new ResponseEntity<>(isPresentEligibilityTemplateIndustryPending, HttpStatus.OK);
        } else {
            responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Search EligibilityTemplateIndustry pending.
     *
     * @param tenantId      - the tenant id
     * @param searchq       - the searchq
     * @param status        - the status
     * @param approveStatus - the approve status
     * @param pageable      - the pageable
     * @return the page
     */
    @GetMapping(value = "/{tenantId}/workflow-items/search")
    public Page<EligibilityTemplateIndustryPending> searchEligibilityTemplateIndustryPending(
            @PathVariable(value = "tenantId", required = true) String tenantId,
            @RequestParam(value = "searchq", required = false) String searchq,
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "approveStatus", required = false) String approveStatus,
            @PageableDefault(size = 10) Pageable pageable) {
        if (pageable.getPageSize() > Constants.MAX_PAGEABLE_LENGTH)
            throw new PageableLengthException(environment.getProperty(PAGEABLE_LENGTH));
        return eligibilityTemplateIndustryService.searchEligibilityTemplateIndustryPending(searchq, status,
                approveStatus, pageable);
    }
}
