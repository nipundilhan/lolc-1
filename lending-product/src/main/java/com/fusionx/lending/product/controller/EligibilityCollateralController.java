package com.fusionx.lending.product.controller;

import com.fusionx.lending.product.Constants;
import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.EligibilityCollateral;
import com.fusionx.lending.product.domain.EligibilityCollateralPending;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.enums.WorkflowStatus;
import com.fusionx.lending.product.exception.PageableLengthException;
import com.fusionx.lending.product.exception.UserNotFound;
import com.fusionx.lending.product.resources.EligibilityCollateralAddResource;
import com.fusionx.lending.product.resources.EligibilityCollateralUpdateResource;
import com.fusionx.lending.product.resources.SuccessAndErrorDetailsResource;
import com.fusionx.lending.product.service.EligibilityCollateralService;
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
 * API Service related to Eligibility Collateral.
 *
 * @author Miyuru Wijesinghe
 * @version 1.0.0
 * @since 1.0.0
 * <p>
 * <br/><br/>
 * <b>Change History : </b>
 * <br/><br/>
 * #        Date            Story Point     Task No             Author                  Description
 * <br/>
 * .....................................................................................................................<br/>
 * 1        01-07-2021    	-               FX-6889             Miyuru Wijesinghe          Created
 * <p>
 */
@RestController
@RequestMapping(value = "/eligibility-collateral")
@CrossOrigin(origins = "*")
public class EligibilityCollateralController extends MessagePropertyBase {

    private EligibilityCollateralService eligibilityCollateralService;

    @Autowired
    public void setEligibilityCollateralService(EligibilityCollateralService eligibilityCollateralService) {
        this.eligibilityCollateralService = eligibilityCollateralService;
    }

    /**
     * Gets the all eligibility collaterals.
     *
     * @param tenantId the tenant id
     * @return the all eligibility collaterals
     */
    @GetMapping(value = "/{tenantId}/all")
    public ResponseEntity<Object> getAllEligibilityCollaterals(@PathVariable(value = "tenantId") String tenantId) {
        List<EligibilityCollateral> eligibilityCollateral = eligibilityCollateralService.findAll(tenantId);

        if (!eligibilityCollateral.isEmpty()) {
            return new ResponseEntity<>(eligibilityCollateral, HttpStatus.OK);
        } else {
            SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
            responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }


    /**
     * Gets the eligibility collateral by id.
     *
     * @param tenantId the tenant id
     * @param id       the id
     * @return the eligibility collateral by id
     */
    @GetMapping(value = "/{tenantId}/{id}")
    public ResponseEntity<Object> getEligibilityCollateralById(
            @PathVariable(value = "tenantId") String tenantId,
            @PathVariable(value = "id") Long id) {

        Optional<EligibilityCollateral> isPresentEligibilityCollateral = eligibilityCollateralService.findById(tenantId, id);

        if (isPresentEligibilityCollateral.isPresent()) {
            return new ResponseEntity<>(isPresentEligibilityCollateral, HttpStatus.OK);
        } else {
            SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
            responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }


    /**
     * Gets the eligibility collaterals by status.
     *
     * @param tenantId the tenant id
     * @param status   the status
     * @return the eligibility collaterals by status
     */
    @GetMapping(value = "/{tenantId}/status/{status}")
    public ResponseEntity<Object> getEligibilityCollateralsByStatus(
            @PathVariable(value = "tenantId") String tenantId,
            @PathVariable(value = "status") String status) {

        SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();

        if (status.equals(CommonStatus.ACTIVE.toString()) || status.equals(CommonStatus.INACTIVE.toString())) {
            List<EligibilityCollateral> eligibilityCollateral = eligibilityCollateralService.findByStatus(tenantId, status);
            if (!eligibilityCollateral.isEmpty()) {
                return new ResponseEntity<>(eligibilityCollateral, HttpStatus.OK);
            } else {
                responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
                return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
            }
        } else {
            responseMessage.setMessages(environment.getProperty(INVALID_STATUS));
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }


    /**
     * Gets the eligibility collaterals by eligibility id.
     *
     * @param tenantId      the tenant id
     * @param eligibilityId the eligibility id
     * @return the eligibility collaterals by eligibility id
     */
    @GetMapping(value = "/{tenantId}/eligibility/{eligibilityId}")
    public ResponseEntity<Object> getEligibilityCollateralsByEligibilityId(
            @PathVariable(value = "tenantId") String tenantId,
            @PathVariable(value = "eligibilityId") Long eligibilityId) {

        List<EligibilityCollateral> eligibilityCollateral = eligibilityCollateralService.findByEligibilityId(tenantId, eligibilityId);

        if (!eligibilityCollateral.isEmpty()) {
            return new ResponseEntity<>(eligibilityCollateral, HttpStatus.OK);
        } else {
            SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
            responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }


    /**
     * Gets the eligibility collaterals by asset type id.
     *
     * @param tenantId    the tenant id
     * @param assetTypeId the asset type id
     * @return the eligibility collaterals by asset type id
     */
    @GetMapping(value = "/{tenantId}/asset-type/{assetTypeId}")
    public ResponseEntity<Object> getEligibilityCollateralsByAssetTypeId(
            @PathVariable(value = "tenantId") String tenantId,
            @PathVariable(value = "assetTypeId") Long assetTypeId) {

        List<EligibilityCollateral> eligibilityCollateral = eligibilityCollateralService.findByAssetTypeId(tenantId, assetTypeId);

        if (!eligibilityCollateral.isEmpty()) {
            return new ResponseEntity<>(eligibilityCollateral, HttpStatus.OK);
        } else {
            SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
            responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));

            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }


    /**
     * Adds the eligibility collateral.
     *
     * @param tenantId                         the tenant id
     * @param eligibilityCollateralAddResource the eligibility collateral add resource
     * @return the response entity
     */
    @PostMapping(value = "/{tenantId}")
    public ResponseEntity<Object> addEligibilityCollateral(@PathVariable(value = "tenantId") String tenantId,
                                                           @Valid @RequestBody EligibilityCollateralAddResource eligibilityCollateralAddResource) {
        if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
            throw new UserNotFound(environment.getProperty(COMMON_USER_NOT_FOUND));

        Long id = eligibilityCollateralService.saveAndValidateEligibilityCollateral(tenantId, LogginAuthentcation.getInstance().getUserName(), eligibilityCollateralAddResource);
        SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty(COMMON_SAVED), id);

        return new ResponseEntity<>(successDetailsDto, HttpStatus.CREATED);
    }


    /**
     * Update eligibility collateral.
     *
     * @param tenantId                            the tenant id
     * @param id                                  the id
     * @param eligibilityCollateralUpdateResource the eligibility collateral update resource
     * @return the response entity
     */
    @PutMapping(value = "/{tenantId}/{id}")
    public ResponseEntity<Object> updateEligibilityCollateral(@PathVariable(value = "tenantId") String tenantId,
                                                              @PathVariable(value = "id") Long id,
                                                              @Valid @RequestBody EligibilityCollateralUpdateResource eligibilityCollateralUpdateResource) {

        if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
            throw new UserNotFound(environment.getProperty(COMMON_USER_NOT_FOUND));

        eligibilityCollateralService.updateAndValidateEligibilityCollateral(tenantId, LogginAuthentcation.getInstance().getUserName(), id, eligibilityCollateralUpdateResource);
        SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty(COMMON_UPDATED));

        return new ResponseEntity<>(successDetailsDto, HttpStatus.OK);
    }


    /**
     * Approve eligibility collateral.
     *
     * @param tenantId  the tenant id
     * @param pendingId the pending id
     * @return the response entity
     */
    @PutMapping(value = "{tenantId}/pending/{pendingId}/approve")
    public ResponseEntity<Object> approveEligibilityCollateral(@PathVariable(value = "tenantId") String tenantId,
                                                               @PathVariable(value = "pendingId") Long pendingId) {
        SuccessAndErrorDetailsResource successAndErrorDetailsResource = new SuccessAndErrorDetailsResource();
        Optional<EligibilityCollateralPending> isPresentEligibilityCollateralPending = eligibilityCollateralService.getByPendingId(pendingId);

        if (isPresentEligibilityCollateralPending.isPresent()) {
            if (Boolean.TRUE.equals(eligibilityCollateralService.approveReject(tenantId, pendingId, WorkflowStatus.COMPLETE))) {
                successAndErrorDetailsResource = new SuccessAndErrorDetailsResource(environment.getProperty(COMMON_APPROVED), pendingId.toString());
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
     * Reject eligibility collateral.
     *
     * @param tenantId  the tenant id
     * @param pendingId the pending id
     * @return the response entity
     */
    @PutMapping(value = "{tenantId}/pending/{pendingId}/reject")
    public ResponseEntity<Object> rejectEligibilityCollateral(@PathVariable(value = "tenantId") String tenantId,
                                                              @PathVariable(value = "pendingId") Long pendingId) {
        SuccessAndErrorDetailsResource successAndErrorDetailsResource = new SuccessAndErrorDetailsResource();
        Optional<EligibilityCollateralPending> isPresentEligibilityCollateralPending = eligibilityCollateralService.getByPendingId(pendingId);

        if (isPresentEligibilityCollateralPending.isPresent()) {
            if (Boolean.TRUE.equals(eligibilityCollateralService.approveReject(tenantId, pendingId, WorkflowStatus.REJECT))) {
                successAndErrorDetailsResource = new SuccessAndErrorDetailsResource(environment.getProperty(COMMON_REJECTED), pendingId.toString());
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
     * Gets the eligibility collateral by pending id.
     *
     * @param tenantId  the tenant id
     * @param pendingId the pending id
     * @return the eligibility collateral by pending id
     */
    @GetMapping(value = "/{tenantId}/pending/{pendingId}")
    public ResponseEntity<Object> getEligibilityCollateralByPendingId(
            @PathVariable(value = "tenantId") String tenantId,
            @PathVariable(value = "pendingId") Long pendingId) {

        Optional<EligibilityCollateralPending> isPresentEligibilityCollateralPending = eligibilityCollateralService.getByPendingId(pendingId);

        if (isPresentEligibilityCollateralPending.isPresent()) {
            return new ResponseEntity<>(isPresentEligibilityCollateralPending, HttpStatus.OK);
        } else {
            SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
            responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }


    /**
     * Search eligibility collateral.
     *
     * @param tenantId      the tenant id
     * @param searchq       the searchq
     * @param status        the status
     * @param approveStatus the approve status
     * @param pageable      the pageable
     * @return the page
     */
    @GetMapping(value = "/{tenantId}/workflow-items/search")
    public Page<EligibilityCollateralPending> searchEligibilityCollateral(@PathVariable(value = "tenantId") String tenantId,
                                                                          @RequestParam(value = "searchq", required = false) String searchq,
                                                                          @RequestParam(value = "status", required = false) String status,
                                                                          @RequestParam(value = "approveStatus", required = false) String approveStatus,
                                                                          @PageableDefault(size = Constants.DEFAULT_PAGE_SIZE) Pageable pageable) {
        if (pageable.getPageSize() > Constants.MAX_PAGEABLE_LENGTH)
            throw new PageableLengthException(environment.getProperty(PAGEABLE_LENGTH));
        return eligibilityCollateralService.searchEligibilityCollateral(searchq, status, approveStatus, pageable);
    }

}
