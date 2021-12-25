package com.fusionx.lending.product.controller;

import com.fusionx.lending.product.Constants;
import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.EligibilityCustomerType;
import com.fusionx.lending.product.domain.EligibilityCustomerTypePending;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.exception.PageableLengthException;
import com.fusionx.lending.product.exception.UserNotFound;
import com.fusionx.lending.product.exception.ValidateRecordException;
import com.fusionx.lending.product.resources.EligibilityCustomerTypeAddResource;
import com.fusionx.lending.product.resources.EligibilityCustomerTypeUpdateResource;
import com.fusionx.lending.product.resources.SuccessAndErrorDetails;
import com.fusionx.lending.product.resources.SuccessAndErrorDetailsResource;
import com.fusionx.lending.product.service.EligibilityCustomerTypeService;
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
 * EligibilityCustomerTypeController
 * <p>
 * ******************************************************************************************************
 * ###   Date         Story Point   		Task No    Author       Description
 * ------------------------------------------------------------------------------------------------------
 * 1   29-07-2021    FXL_365			 	FXL-56		Piyumi      Created
 * <p>
 * ******************************************************************************************************
 */

@RestController
@RequestMapping(value = "/eligibility-customer-types")
@CrossOrigin(origins = "*")
public class EligibilityCustomerTypeController extends MessagePropertyBase {

    @Autowired
    private EligibilityCustomerTypeService eligibilityCustomerTypeService;


    /**
     * Get the EligibilityCustomerType by id.
     *
     * @param tenantId - the tenant id
     * @param id       - id
     * @return EligibilityCustomerType
     */
    @GetMapping(value = "/{tenantId}/{id}")
    public ResponseEntity<Object> getById(
            @PathVariable(value = "tenantId") String tenantId,
            @PathVariable(value = "id") Long id) {

        Optional<EligibilityCustomerType> isPresentEligibilityCustomerType = eligibilityCustomerTypeService.getById(tenantId, id);
        if (isPresentEligibilityCustomerType.isPresent()) {
            return new ResponseEntity<>(isPresentEligibilityCustomerType, HttpStatus.OK);
        } else {
            SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
            responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * get EligibilityCustomerType by eligibilityId
     *
     * @param tenantId      - the tenant id
     * @param eligibilityId - eligibilityId
     * @return List<Type>
     */
    @GetMapping(value = "/{tenantId}/eligibility/{eligibilityId}")
    public ResponseEntity<Object> getByEligibilityId(
            @PathVariable(value = "tenantId") String tenantId,
            @PathVariable(value = "eligibilityId") Long eligibilityId) {

        SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
        List<EligibilityCustomerType> eligibilityCustomerTypeList = eligibilityCustomerTypeService.getByEligibilityId(tenantId, eligibilityId);

        if (!eligibilityCustomerTypeList.isEmpty()) {
            return new ResponseEntity<>(eligibilityCustomerTypeList, HttpStatus.OK);
        } else {
            responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * get EligibilityCustomerType by customerTypeId
     *
     * @param tenantId       - the tenant id
     * @param customerTypeId - customerTypeId
     * @return List<Type>
     */
    @GetMapping(value = "/{tenantId}/customer-types/{customerTypeId}")
    public ResponseEntity<Object> getByCustomerTypeId(
            @PathVariable(value = "tenantId") String tenantId,
            @PathVariable(value = "customerTypeId") Long customerTypeId) {

        SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
        List<EligibilityCustomerType> eligibilityCustomerTypeList = eligibilityCustomerTypeService.getByCustomerTypeId(tenantId, customerTypeId);
        int size = eligibilityCustomerTypeList.size();
        if (size > 0) {
            return new ResponseEntity<>(eligibilityCustomerTypeList, HttpStatus.OK);
        } else {
            responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * get EligibilityCustomerType by status
     *
     * @param tenantId - the tenant id
     * @param status   - the status
     * @return List<Type>
     */
    @GetMapping(value = "/{tenantId}/status/{status}")
    public ResponseEntity<Object> getByStatus(
            @PathVariable(value = "tenantId") String tenantId,
            @PathVariable(value = "status") String status) {

        SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
        if (status.equals(CommonStatus.ACTIVE.toString()) || status.equals(CommonStatus.INACTIVE.toString())) {
            List<EligibilityCustomerType> eligibilityCustomerTypeList = eligibilityCustomerTypeService.getByStatus(tenantId, status);
            int size = eligibilityCustomerTypeList.size();
            if (size > 0) {
                return new ResponseEntity<>(eligibilityCustomerTypeList, HttpStatus.OK);
            } else {
                responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
                return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
            }
        } else {
            responseMessage.setMessages(RECORD_NOT_FOUND);
            throw new ValidateRecordException(environment.getProperty(COMMON_STATUS_PATTERN), "message");
        }
    }

    /**
     * Add EligibilityCustomerType
     *
     * @param tenantId                       - the tenant id
     * @param interestTierBandSetAddResource - the interest Tier Band Set Add Resource
     * @return SuccessAndErrorDetailsDto
     */
    @PostMapping(value = "/{tenantId}")
    public ResponseEntity<Object> addEligibilityCustomerType(@PathVariable(value = "tenantId") String tenantId,
                                                             @Valid @RequestBody EligibilityCustomerTypeAddResource interestTierBandSetAddResource) {

        if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
            throw new UserNotFound(environment.getProperty(COMMON_USER_NOT_FOUND));

        Long id = eligibilityCustomerTypeService.addEligibilityCustomerType(tenantId,
                interestTierBandSetAddResource);
        SuccessAndErrorDetails successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty(RECORD_CREATED),
                Long.toString(id));
        return new ResponseEntity<>(successAndErrorDetails, HttpStatus.CREATED);
    }

    /**
     * Update EligibilityCustomerType
     *
     * @param tenantId                          - the tenant id
     * @param interestTierBandSetUpdateResource - the interest Tier Band Set Update Resource
     * @return SuccessAndErrorDetailsDto
     */
    @PutMapping(value = "/{tenantId}/{id}")
    public ResponseEntity<Object> updateEligibilityCustomerType(@PathVariable(value = "tenantId") String tenantId, @PathVariable(value = "id") Long id,
                                                                @Valid @RequestBody EligibilityCustomerTypeUpdateResource interestTierBandSetUpdateResource) {

        if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
            throw new UserNotFound(environment.getProperty(COMMON_USER_NOT_FOUND));

        Long updatedRecordId = eligibilityCustomerTypeService.updateEligibilityCustomerType(tenantId,
                interestTierBandSetUpdateResource);
        SuccessAndErrorDetails successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty(RECORD_UPDATED),
                Long.toString(updatedRecordId));
        return new ResponseEntity<>(successAndErrorDetails, HttpStatus.CREATED);
    }

    /**
     * Gets EligibilityCustomerType by pending id.
     *
     * @param tenantId  - the tenant id
     * @param pendingId - the pending id
     * @return EligibilityCustomerType by pending id
     */
    @GetMapping(value = "/{tenantId}/pending/{pendingId}")
    public ResponseEntity<Object> getByPendingId(
            @PathVariable(value = "tenantId") String tenantId,
            @PathVariable(value = "pendingId") Long pendingId) {

        Optional<EligibilityCustomerTypePending> isPresentEligibilityCustomerTypePending = eligibilityCustomerTypeService.getByPendingId(tenantId, pendingId);
        if (isPresentEligibilityCustomerTypePending.isPresent()) {
            return new ResponseEntity<>(isPresentEligibilityCustomerTypePending, HttpStatus.OK);
        } else {
            SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
            responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Search EligibilityCustomerTypePending
     *
     * @param tenantId      - the tenant id
     * @param searchq       - the searchq
     * @param status        - the status
     * @param approveStatus - the approve status
     * @param pageable      - the pageable
     * @return the page
     */
    @GetMapping(value = "/{tenantId}/workflow-items/search")
    public Page<EligibilityCustomerTypePending> search(@PathVariable(value = "tenantId") String tenantId,
                                                       @RequestParam(value = "searchq", required = false) String searchq,
                                                       @RequestParam(value = "status", required = false) String status,
                                                       @RequestParam(value = "approveStatus", required = false) String approveStatus,
                                                       @PageableDefault Pageable pageable) {
        if (pageable.getPageSize() > Constants.MAX_PAGEABLE_LENGTH)
            throw new PageableLengthException(environment.getProperty("common.pageable-length"));
        return eligibilityCustomerTypeService.searchEligibilityCustomerTypePending(tenantId, searchq, status, approveStatus, pageable);
    }


}
