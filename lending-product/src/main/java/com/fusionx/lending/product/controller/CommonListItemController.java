package com.fusionx.lending.product.controller;

import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.CommonListItem;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.exception.UserNotFound;
import com.fusionx.lending.product.resources.CommonListAddResource;
import com.fusionx.lending.product.resources.CommonListUpdateResource;
import com.fusionx.lending.product.resources.SuccessAndErrorDetails;
import com.fusionx.lending.product.resources.SuccessAndErrorDetailsResource;
import com.fusionx.lending.product.service.CommonListItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * API Service related to Common List Item.
 *
 * @author Senitha Perera
 * @version 1.0.0
 * @since 1.0.0
 * <p>
 * <br/><br/>
 * <b>Change History : </b>
 * <br/><br/>
 * #        Date            Story Point     Task No             Author                  Description     Verified By     Verified Date
 * <br/>
 * .....................................................................................................................................<br/>
 * 1        04-06-2020      -               FX-6514             Senitha Perera          Created         ChinthakaMa     16-Sep-2021
 * <p>
 */
@RestController
@RequestMapping(value = "/common-list-item")
@CrossOrigin(origins = "*")
public class CommonListItemController extends MessagePropertyBase {

    private CommonListItemService commonListService;

    @Autowired
    public void setCommonListService(CommonListItemService commonListItemService) {
        this.commonListService = commonListItemService;
    }

    /**
     * Get all common list items
     *
     * @param tenantId the tenant id
     * @return the list of common lists
     */
    @GetMapping(value = "/{tenantId}/all")
    public ResponseEntity<Object> getAllCommonList(@PathVariable(value = "tenantId") String tenantId) {
        SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
        List<CommonListItem> commonList = commonListService.findAll();

        if (!commonList.isEmpty()) {
            return new ResponseEntity<>(commonList, HttpStatus.OK);
        } else {
            responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Returns the common list by id
     *
     * @param tenantId the tenant id
     * @param id       the id
     * @return the common list.
     */
    @GetMapping(value = "/{tenantId}/{id}")
    public ResponseEntity<Object> getCommonListById(@PathVariable(value = "tenantId") String tenantId,
                                                    @PathVariable(value = "id") Long id) {

        SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
        Optional<CommonListItem> isPresentCommonList = commonListService.findById(id);

        if (isPresentCommonList.isPresent()) {
            return new ResponseEntity<>(isPresentCommonList, HttpStatus.OK);
        } else {
            responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }

    }

    /**
     * Returns the common list by status
     *
     * @param tenantId the tenant id
     * @param status   the status
     * @return the list of common list item
     */
    @GetMapping(value = "/{tenantId}/status/{status}")
    public ResponseEntity<Object> getCommonListByStatus(@PathVariable(value = "tenantId") String tenantId,
                                                        @PathVariable(value = "status") String status) {

        SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();

        if (status.equals(CommonStatus.ACTIVE.toString()) || status.equals(CommonStatus.INACTIVE.toString())) {
            List<CommonListItem> isPresentCommonList = commonListService.findByStatus(status);
            if (!isPresentCommonList.isEmpty()) {
                return new ResponseEntity<>(isPresentCommonList, HttpStatus.OK);
            } else {
                responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
                return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
            }
        } else {
            responseMessage.setMessages(RECORD_NOT_FOUND);
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }

    }

    /**
     * Returns the common list by name
     *
     * @param tenantId the tenant id
     * @param name     the name
     * @return the list of common list items
     */
    @GetMapping(value = "/{tenantId}/name/{name}")
    public ResponseEntity<Object> getCommonListsByName(@PathVariable(value = "tenantId") String tenantId,
                                                       @PathVariable(value = "name") String name) {

        SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
        List<CommonListItem> commonList = commonListService.findByName(name);
        if (!commonList.isEmpty()) {
            return new ResponseEntity<>(commonList, HttpStatus.OK);
        } else {
            responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }

    }

    /**
     * Returns the common list by its code
     *
     * @param tenantId the tenant id
     * @param code     the code
     * @return the common list item.
     */
    @GetMapping(value = "/{tenantId}/code/{code}")
    public ResponseEntity<Object> getCommonListsByCode(@PathVariable(value = "tenantId") String tenantId,
                                                       @PathVariable(value = "code") String code) {

        SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
        List<CommonListItem> commonList = commonListService.findByCode(code);

        if (!commonList.isEmpty()) {
            return new ResponseEntity<>(commonList, HttpStatus.OK);
        } else {
            responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }

    }

    /**
     * Returns the common list by reference code
     *
     * @param tenantId      the tenant id
     * @param referenceCode the reference code
     * @return the list of common list item.
     */
    @GetMapping(value = "/{tenantId}/reference-code/{referenceCode}")
    public ResponseEntity<Object> getCommonListsByReferenceCode(@PathVariable(value = "tenantId") String tenantId,
                                                                @PathVariable(value = "referenceCode") String referenceCode) {

        SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
        List<CommonListItem> commonList = commonListService.findByReferenceCode(referenceCode);

        if (!commonList.isEmpty()) {
            return new ResponseEntity<>(commonList, HttpStatus.OK);
        } else {
            responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }

    }

    /**
     * Saves the given common list
     *
     * @param tenantId              the tenant id
     * @param commonListAddResource the object to save
     * @return the message
     */
    @PostMapping(value = "/{tenantId}")
    public ResponseEntity<Object> addCommonList(@PathVariable(value = "tenantId") String tenantId,
                                                @Valid @RequestBody CommonListAddResource commonListAddResource) {

        if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
            throw new UserNotFound(environment.getProperty(COMMON_USER_NOT_FOUND));

        Long id = commonListService.saveAndValidateCommonList(tenantId, LogginAuthentcation.getInstance().getUserName(), commonListAddResource);
        SuccessAndErrorDetails successDetailsDto = new SuccessAndErrorDetails(environment.getProperty(COMMON_SAVED), id.toString());

        return new ResponseEntity<>(successDetailsDto, HttpStatus.CREATED);

    }

    /**
     * Updates the given common list
     *
     * @param tenantId                 the tenant id
     * @param id                       the id of the record to be update
     * @param commonListUpdateResource the object to update
     * @return the message
     */
    @PutMapping(value = "/{tenantId}/{id}")
    public ResponseEntity<Object> updateCommonList(@PathVariable(value = "tenantId") String tenantId,
                                                   @PathVariable(value = "id") Long id,
                                                   @Valid @RequestBody CommonListUpdateResource commonListUpdateResource) {

        if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
            throw new UserNotFound(environment.getProperty(COMMON_USER_NOT_FOUND));

        if (Boolean.TRUE.equals(commonListService.existsById(id))) {
            commonListService.updateAndValidateCommonList(tenantId, LogginAuthentcation.getInstance().getUserName(), id, commonListUpdateResource);
            SuccessAndErrorDetails successDetailsDto = new SuccessAndErrorDetails(environment.getProperty(COMMON_UPDATED));
            return new ResponseEntity<>(successDetailsDto, HttpStatus.OK);
        } else {
            SuccessAndErrorDetails successDetailsDto = new SuccessAndErrorDetails(environment.getProperty(RECORD_NOT_FOUND));
            return new ResponseEntity<>(successDetailsDto, HttpStatus.UNPROCESSABLE_ENTITY);
        }

    }

}
