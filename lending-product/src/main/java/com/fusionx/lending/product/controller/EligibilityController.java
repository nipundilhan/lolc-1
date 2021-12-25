package com.fusionx.lending.product.controller;

import com.fusionx.lending.product.Constants;
import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.Eligibility;
import com.fusionx.lending.product.domain.EligibilityPending;
import com.fusionx.lending.product.enums.CommonApproveStatus;
import com.fusionx.lending.product.enums.WorkflowStatus;
import com.fusionx.lending.product.exception.PageableLengthException;
import com.fusionx.lending.product.exception.UserNotFound;
import com.fusionx.lending.product.resources.EligibilityAddResource;
import com.fusionx.lending.product.resources.EligibilityUpdateResource;
import com.fusionx.lending.product.resources.SuccessAndErrorDetails;
import com.fusionx.lending.product.resources.SuccessAndErrorDetailsResource;
import com.fusionx.lending.product.service.EligibilityService;
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

/**
 * API Service related to Eligibility.
 *
 * @author Menuka Jayasinghe
 * @version 1.0.0
 * @since 1.0.0
 * <p>
 * <br/><br/>
 * <b>Change History : </b>
 * <br/><br/>
 * #        Date            Story Point     Task No             Author                  Description
 * <br/>
 * .....................................................................................................................<br/>
 * 1        10-06-2021    	-               		             Menuka Jayasinghe		Created
 * <p>
 */
@RestController
@RequestMapping(value = "/eligibility")
@CrossOrigin(origins = "*")
public class EligibilityController extends MessagePropertyBase {

    private EligibilityService eligibilityService;

    @Autowired
    public void setEligibilityService(EligibilityService eligibilityService) {
        this.eligibilityService = eligibilityService;
    }

    /**
     * Gets all eligibilities.
     *
     * @param tenantId the tenant id
     * @return the list of eligibilities.
     */
    @GetMapping(value = "/{tenantId}/all")
    public ResponseEntity<Object> getAllEligibility(@PathVariable(value = "tenantId") String tenantId) {

        List<Eligibility> eligibility = eligibilityService.getAll();

        int size = eligibility.size();

        if (size > 0) {
            return new ResponseEntity<>(eligibility, HttpStatus.OK);
        } else {
            SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
            responseMessage.setMessages(RECORD_NOT_FOUND);
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Get eligibility by id
     *
     * @param tenantId the tenant id
     * @param id       the id of record
     * @return the eligibility record
     */
    @GetMapping(value = "/{tenantId}/{id}")
    public ResponseEntity<Object> getEligibilityById(@PathVariable(value = "tenantId") String tenantId,
                                                     @PathVariable(value = "id") Long id) {

        Optional<Eligibility> isPresentEligibility = eligibilityService.getById(id);

        if (isPresentEligibility.isPresent()) {
            return new ResponseEntity<>(isPresentEligibility.get(), HttpStatus.OK);
        } else {
            SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
            responseMessage.setMessages(RECORD_NOT_FOUND);
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Gets eligibility by code
     *
     * @param tenantId the tenant id
     * @param code     the code
     * @return the eligibility record
     */
    @GetMapping(value = "/{tenantId}/code/{code}")
    public ResponseEntity<Object> getEligibilityByCode(@PathVariable(value = "tenantId") String tenantId,
                                                       @PathVariable(value = "code") String code) {

        Optional<Eligibility> isPresentEligibility = eligibilityService.getByCode(code);

        if (isPresentEligibility.isPresent()) {
            return new ResponseEntity<>(isPresentEligibility.get(), HttpStatus.OK);
        } else {
            SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
            responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Gets eligibility by name
     *
     * @param tenantId the tenant id
     * @param name     the name
     * @return the eligibility record
     */
    @GetMapping(value = "/{tenantId}/name/{name}")
    public ResponseEntity<Object> getEligibilityByName(@PathVariable(value = "tenantId") String tenantId,
                                                       @PathVariable(value = "name") String name) {

        List<Eligibility> isPresentEligibility = eligibilityService.getByName(name);

        if (!isPresentEligibility.isEmpty()) {
            return new ResponseEntity<>(isPresentEligibility, HttpStatus.OK);
        } else {
            SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
            responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Gets eligibility by status
     *
     * @param tenantId the tenant id
     * @param status   the status
     * @return the eligibility record
     */
    @GetMapping(value = "/{tenantId}/status/{status}")
    public ResponseEntity<Object> getEligibilityByStatus(@PathVariable(value = "tenantId") String tenantId,
                                                         @PathVariable(value = "status") String status) {
        SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();

        if (status.equals("ACTIVE") || status.equals("INACTIVE")) {

            List<Eligibility> isPresentEligibility = eligibilityService.getByStatus(status);
            int size = isPresentEligibility.size();

            if (size > 0) {
                return new ResponseEntity<>(isPresentEligibility, HttpStatus.OK);
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
     * Saves given eligibility record
     *
     * @param tenantId               the tenant id
     * @param eligibilityAddResource the object to save
     * @return the message
     */
    @PostMapping(value = "/{tenantId}")
    public ResponseEntity<Object> addEligibility(@PathVariable(value = "tenantId") String tenantId,
                                                 @Valid @RequestBody EligibilityAddResource eligibilityAddResource) {

        if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty()) {
            throw new UserNotFound(environment.getProperty(COMMON_USER_NOT_FOUND));
        }

        Eligibility eligibilityType = eligibilityService.addEligibility(tenantId, eligibilityAddResource);
        SuccessAndErrorDetails successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty("rec.saved"), Long.toString(eligibilityType.getId()));
        return new ResponseEntity<>(successAndErrorDetails, HttpStatus.CREATED);
    }

    /**
     * Updates the given eligibility
     *
     * @param tenantId                  the tenant id
     * @param id                        the id
     * @param eligibilityUpdateResource the object which contain update data
     * @return the message
     */
    @PutMapping(value = "/{tenantId}/{id}")
    public ResponseEntity<Object> updateEligibility(@PathVariable(value = "tenantId") String tenantId,
                                                    @PathVariable(value = "id") Long id,
                                                    @Valid @RequestBody EligibilityUpdateResource eligibilityUpdateResource) {

        if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty()) {
            throw new UserNotFound(environment.getProperty(COMMON_USER_NOT_FOUND));
        }

        SuccessAndErrorDetails successAndErrorDetails = new SuccessAndErrorDetails();
        Optional<Eligibility> isPresentEligibility = eligibilityService.getById(id);

        if (isPresentEligibility.isPresent()) {
            if (isPresentEligibility.get().getApproveStatus() == null || !isPresentEligibility.get().getApproveStatus().equals(CommonApproveStatus.PENDING)) {
                Eligibility eligibility = eligibilityService.updateEligibility(tenantId, id, eligibilityUpdateResource);
                successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty("rec.updated"), eligibility.getId().toString());
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
     * Approve the given eligibility record
     *
     * @param tenantId  the tenant id
     * @param pendingId the pending id
     * @return the message
     */
    @PutMapping(value = "{tenantId}/approve/{pendingId}")
    public ResponseEntity<Object> approve(@PathVariable(value = "tenantId") String tenantId, @PathVariable(value = "pendingId") Long pendingId) {

        SuccessAndErrorDetailsResource successAndErrorDetailsResource = new SuccessAndErrorDetailsResource();
        Optional<EligibilityPending> isPresentEligibilityPending = eligibilityService.getByPendingId(pendingId);

        if (isPresentEligibilityPending.isPresent()) {

            if (eligibilityService.approveReject(tenantId, pendingId, WorkflowStatus.COMPLETE)) {
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
     * Rejects the eligibility pending record
     *
     * @param tenantId  the tenant id
     * @param pendingId the pending record id
     * @return the message
     */
    @PutMapping(value = "{tenantId}/reject/{pendingId}")
    public ResponseEntity<Object> reject(@PathVariable(value = "tenantId") String tenantId, @PathVariable(value = "pendingId") Long pendingId) {
        SuccessAndErrorDetailsResource successAndErrorDetailsResource = new SuccessAndErrorDetailsResource();
        Optional<EligibilityPending> isPresentEligibilityPending = eligibilityService.getByPendingId(pendingId);

        if (isPresentEligibilityPending.isPresent()) {

            if (eligibilityService.approveReject(tenantId, pendingId, WorkflowStatus.REJECT)) {
                successAndErrorDetailsResource = new SuccessAndErrorDetailsResource(environment.getProperty("common.rejected"), pendingId.toString());
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
     * Gets the approval pending records by pending id
     *
     * @param tenantId  the tenant id
     * @param pendingId the pending record id
     * @return the pending record details.
     */
    @GetMapping(value = "/{tenantId}/pending/{pendingId}")
    public ResponseEntity<Object> getEligibilityByPendingId(
            @PathVariable(value = "tenantId") String tenantId,
            @PathVariable(value = "pendingId") Long pendingId) {
        SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
        Optional<EligibilityPending> isPresentEligibilityPending = eligibilityService.getByPendingId(pendingId);
        if (isPresentEligibilityPending.isPresent()) {
            return new ResponseEntity<>(isPresentEligibilityPending, HttpStatus.OK);
        } else {
            responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }


    /**
     * Search eligibility pending.
     *
     * @param tenantId      - the tenant id
     * @param searchq       - the searchq
     * @param status        - the status
     * @param approveStatus - the approve status
     * @param pageable      - the pageable
     * @return the page
     */
    @GetMapping(value = "/{tenantId}/workflow-items/search")
    public Page<EligibilityPending> searchEligibilityOfficerType(@PathVariable(value = "tenantId") String tenantId,
                                                                 @RequestParam(value = "searchq", required = false) String searchQ,
                                                                 @RequestParam(value = "status", required = false) String status,
                                                                 @RequestParam(value = "approveStatus", required = false) String approveStatus,
                                                                 @PageableDefault(size = Constants.DEFAULT_PAGE_SIZE) Pageable pageable) {
        if (pageable.getPageSize() > Constants.MAX_PAGEABLE_LENGTH)
            throw new PageableLengthException(environment.getProperty(PAGEABLE_LENGTH));
        return eligibilityService.searchEligibilityPending(searchQ, status, approveStatus, pageable);
    }

}
