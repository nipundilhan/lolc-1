package com.fusionx.lending.product.controller;

import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.config.CommonModuleProperties;
import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.Borrowers;
import com.fusionx.lending.product.domain.LendingAccountDetail;
import com.fusionx.lending.product.exception.UserNotFound;
import com.fusionx.lending.product.resources.*;
import com.fusionx.lending.product.service.LendingAccountDetailService;
import com.fusionx.lending.product.service.RemoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * API Service related to Lending Account Detail.
 *
 * @author Rohan
 * @version 1.0.0
 * @since 1.0.0
 * <p>
 * <br/><br/>
 * <b>Change History : </b>
 * <br/><br/>
 * #        Date            Story Point     Task No     Author                  Description
 * <br/>
 * .....................................................................................................................<br/>
 * 1        07-10-2021      -               -           Rohan                  Created
 * <p>
 *
 */
@RestController
@RequestMapping(value = "/lending-account-detail")
@CrossOrigin(origins = "*")
public class LendingAccountDetailController extends MessagePropertyBase {

    private LendingAccountDetailService lendingAccountDetailService;
    private RemoteService remoteService;
    private CommonModuleProperties commonModuleProperties;

    @Autowired
    public void setCommonModuleProperties(CommonModuleProperties commonModuleProperties) {
        this.commonModuleProperties = commonModuleProperties;
    }

    @Autowired
    public void setRemoteService(RemoteService remoteService) {
        this.remoteService = remoteService;
    }

    @Autowired
    public void setLendingAccountDetailService(LendingAccountDetailService lendingAccountDetailService) {
        this.lendingAccountDetailService = lendingAccountDetailService;
    }

    /**
     * Search lending account detail by account number
     *
     * @param tenantId   the id of the tenant
     * @param searchText the search key
     * @return
     */
    @GetMapping(value = "/{tenantId}/search/{searchText}")
    public ResponseEntity<Object> getAllLendingAccountsByAccountNumber(@PathVariable(value = "tenantId") String tenantId,
                                                                       @PathVariable(value = "searchText") String searchText) {
        SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
        List<LendingAccountAdvanceSearchResponse> lendingAccountAdvanceSearchResponses = lendingAccountDetailService.searchByLendingAccountNumber(tenantId, searchText);
        if (lendingAccountAdvanceSearchResponses.size() != 0) {
            return new ResponseEntity<>(lendingAccountAdvanceSearchResponses, HttpStatus.OK);
        } else {
            responseMessage.setMessages(RECORD_NOT_FOUND);
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Saves the lending account detail
     *
     * @param tenantId                        the id of the tenant
     * @param lendingAccountDetailAddResource the object to save
     * @return
     */
    @PostMapping(value = "/{tenantId}")
    public ResponseEntity<Object> addLendingAccountDetail(@PathVariable(value = "tenantId") String tenantId,
                                                          @Valid @RequestBody LendingAccountDetailAddResource lendingAccountDetailAddResource) {
        if (LogginAuthentcation.getInstance().getUserName() == null
                || LogginAuthentcation.getInstance().getUserName().isEmpty()) {
            throw new UserNotFound(environment.getProperty(COMMON_USER_NOT_FOUND));
        }
        SuccessAndErrorDetails successAndErrorDetails;
        LendingAccountDetail lendingAccountDetail = lendingAccountDetailService.addLendingAccountDetail(tenantId, lendingAccountDetailAddResource);
        successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty(RECORD_CREATED), Long.toString(lendingAccountDetail.getId()));
        return new ResponseEntity<>(successAndErrorDetails, HttpStatus.CREATED);

    }

    /**
     * Updates the given brand record
     *
     * @param tenantId the tenant id
     * @param id the id
     * @param lendingAccountDetailUpdateResource object to update
     * @return the message
     */
    @PutMapping(value = "/{tenantId}/{id}")
    public ResponseEntity<Object> updateLendingAccountDetail(@PathVariable(value = "tenantId") String tenantId,
                                                  @PathVariable(value = "id") Long id,
                                                  @Valid @RequestBody LendingAccountDetailUpdateResource lendingAccountDetailUpdateResource) {
        if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty()) {
            throw new UserNotFound(environment.getProperty(COMMON_USER_NOT_FOUND));
        }

        SuccessAndErrorDetails successAndErrorDetails = new SuccessAndErrorDetails();
        Optional<LendingAccountDetail> isPresentLendingAccountDetail = lendingAccountDetailService.getByLendingAccountDetailById(id);
        if (isPresentLendingAccountDetail.isPresent()) {
            LendingAccountDetail lendingAccountDetail = lendingAccountDetailService.updateLendingAccountDetail(tenantId, id, lendingAccountDetailUpdateResource);
            successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty(RECORD_UPDATED),Long.toString(lendingAccountDetail.getId()));
            return new ResponseEntity<>(successAndErrorDetails, HttpStatus.OK);
        } else {
            successAndErrorDetails.setMessages(RECORD_NOT_FOUND);
            return new ResponseEntity<>(successAndErrorDetails, HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    /**
     *retrieve lending account detail by lending account id
     *
     * @param tenantId the tenant id
     * @param id the account id
     * @return
     */
    @GetMapping(value = "/{tenantId}/{id}")
    public ResponseEntity<Object> getAccountById(@PathVariable(value = "tenantId") String tenantId,
                                                 @PathVariable(value = "id") String id){
        SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
        LendingAccountDetailRequestResponseResource lendingAccountDetail = lendingAccountDetailService.getAccountById(tenantId,id);
        if (lendingAccountDetail != null) {
            return new ResponseEntity<>(lendingAccountDetail, HttpStatus.OK);
        } else {
            responseMessage.setMessages(RECORD_NOT_FOUND);
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
     *Update collection method using account id
     *
     * @param tenantId the tenant id
     * @param collectionMethodAddResource  the collection method add recourse
     * @return
     */
    @PutMapping(value = "/{tenantId}")
    public ResponseEntity<Object> setCollectionMethod(@PathVariable(value = "tenantId") String tenantId,
                                                     @Valid @RequestBody CollectionMethodAddResource collectionMethodAddResource){
        SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
        LendingAccountDetail lendingAccountDetail = lendingAccountDetailService.setCollectionMethod(tenantId, collectionMethodAddResource);

        if (lendingAccountDetail != null) {
            return new ResponseEntity<>(lendingAccountDetail, HttpStatus.OK);
        } else {
            responseMessage.setMessages(RECORD_NOT_FOUND);
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Get LendingAccountDetail by status
     *
     * @param tenantId the id of tenant
     * @param status   the status
     * @return the list of all  lendingAccountDetail status if record exists, otherwise <code>204 - No Content</code>
     */
    @GetMapping(value = "/{tenantId}/status/{status}")
    public ResponseEntity<Object> getLendingAccountDetailByStatus(@PathVariable(value = "tenantId") String tenantId,
                                                       @PathVariable(value = "status") String status) {
        SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();

        if (status.equals("ACTIVE") || status.equals("INACTIVE")) {
            List<LendingAccountDetail> isPresentBorrowers = lendingAccountDetailService.findByStatus(status);
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
}

