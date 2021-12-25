package com.fusionx.lending.product.controller;

import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.CheckList;
import com.fusionx.lending.product.exception.UserNotFound;
import com.fusionx.lending.product.resources.AccountStatusUpdateResource;
import com.fusionx.lending.product.resources.CheckListAddResource;
import com.fusionx.lending.product.resources.CheckListUpdateResource;
import com.fusionx.lending.product.resources.SuccessAndErrorDetails;
import com.fusionx.lending.product.service.CheckListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * API Service related to Check List.
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
 * 1        26-10-2021      -               -           Rohan                  Created
 * <p>
 */
@RestController
@RequestMapping(value = "/check-list")
@CrossOrigin(origins = "*")
public class CheckListController extends MessagePropertyBase {

    private CheckListService checkListService;

    @Autowired
    public void setCheckListService(CheckListService checkListService) {
        this.checkListService = checkListService;
    }

    /**
     * Saves the Check List
     *
     * @param tenantId             the tenant id
     * @param checkListAddResource the add resource of Check List
     * @return message if record successfully saved.
     * @see CheckListAddResource
     */
    @PostMapping(value = "/{tenantId}")
    public ResponseEntity<Object> addCheckList(@PathVariable(value = "tenantId") String tenantId,
                                               @Valid @RequestBody CheckListAddResource checkListAddResource) {
        if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty()) {
            throw new UserNotFound(environment.getProperty(COMMON_USER_NOT_FOUND));
        }
        SuccessAndErrorDetails successAndErrorDetails;
        CheckList checkList = checkListService.addCheckList(tenantId, checkListAddResource);
        successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty(RECORD_CREATED), Long.toString(checkList.getId()));
        return new ResponseEntity<>(successAndErrorDetails, HttpStatus.CREATED);
    }

    /**
     * Updates the check list
     *
     * @param tenantId                the id of the tenant
     * @param id                      the id of the object
     * @param checkListUpdateResource the object which contains data
     * @return the message
     * @see AccountStatusUpdateResource
     */
    @PutMapping(value = "/{tenantId}/{id}")
    public ResponseEntity<Object> updateCheckList(@PathVariable(value = "tenantId") String tenantId,
                                                  @PathVariable(value = "id") Long id,
                                                  @Valid @RequestBody CheckListUpdateResource checkListUpdateResource) {
        if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty()) {
            throw new UserNotFound(environment.getProperty(COMMON_USER_NOT_FOUND));
        }

        SuccessAndErrorDetails successAndErrorDetails = new SuccessAndErrorDetails();
        Optional<CheckList> isPresentCheckList = checkListService.getById(id);
        if (isPresentCheckList.isPresent()) {
            CheckList checkList = checkListService.updateCheckList(tenantId, id, checkListUpdateResource);
            successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty(RECORD_UPDATED), checkList.getId().toString());
            return new ResponseEntity<>(successAndErrorDetails, HttpStatus.OK);
        } else {
            successAndErrorDetails.setMessages(RECORD_NOT_FOUND);
            return new ResponseEntity<>(successAndErrorDetails, HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    /**
     * Gets all check list
     *
     * @param tenantId the id of tenant
     * @return the list of all check list
     */
    @GetMapping(value = "/{tenantId}/all")
    public ResponseEntity<Object> getAllCheckList(@PathVariable(value = "tenantId") String tenantId) {
        SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
        List<CheckList> checkLists = checkListService.getAll();
        if (checkLists.size() > 0) {
            return new ResponseEntity<>(checkLists, HttpStatus.OK);
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
     * @return the Checklist Record.
     */
    @GetMapping(value = "/{tenantId}/{id}")
    public ResponseEntity<Object> getCheckListById(@PathVariable(value = "tenantId") String tenantId,
                                                   @PathVariable(value = "id") Long id) {
        SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
        Optional<CheckList> isPresentCheckList = checkListService.getById(id);
        if (isPresentCheckList.isPresent()) {
            return new ResponseEntity<>(isPresentCheckList.get(), HttpStatus.OK);
        } else {
            responseMessage.setMessages(RECORD_NOT_FOUND);
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Get check list by status
     *
     * @param tenantId the id of tenant
     * @param status   the status
     * @return the list of all check list if record exists
     */
    @GetMapping(value = "/{tenantId}/status/{status}")
    public ResponseEntity<Object> getCheckListByStatus(@PathVariable(value = "tenantId") String tenantId,
                                                       @PathVariable(value = "status") String status) {
        SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();

        if (status.equals("ACTIVE") || status.equals("INACTIVE")) {
            List<CheckList> isPresentCheckList = checkListService.getByStatus(status);
            if (isPresentCheckList.size() > 0) {
                return new ResponseEntity<>(isPresentCheckList, HttpStatus.OK);
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
     * Returns the record by lending account id
     *
     * @param tenantId         the tenant id
     * @param lendingAccountId the lendingAccountId
     * @return the Checklist Record.
     */
    @GetMapping(value = "/{tenantId}/lending-account/{lendingAccountId}")
    public ResponseEntity<Object> getCheckListByLendingAccount(@PathVariable(value = "tenantId") String tenantId,
                                                               @PathVariable(value = "lendingAccountId") Long lendingAccountId) {
        SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
        Optional<CheckList> isPresentCheckList = checkListService.getByLendingAccount(lendingAccountId);
        if (isPresentCheckList.isPresent()) {
            return new ResponseEntity<>(isPresentCheckList, HttpStatus.OK);
        } else {
            responseMessage.setMessages(RECORD_NOT_FOUND);
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }
}
