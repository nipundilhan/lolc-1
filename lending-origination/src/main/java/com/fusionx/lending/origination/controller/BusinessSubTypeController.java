package com.fusionx.lending.origination.controller;

import com.fusionx.lending.origination.Constants;
import com.fusionx.lending.origination.core.LoggerRequest;
import com.fusionx.lending.origination.core.LogginAuthentcation;
import com.fusionx.lending.origination.domain.BusinessSubType;
import com.fusionx.lending.origination.exception.PageableLengthException;
import com.fusionx.lending.origination.exception.UserNotFound;
import com.fusionx.lending.origination.resource.*;
import com.fusionx.lending.origination.service.BusinessSubTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
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
@RequestMapping(value = "/business-sub-type")
@CrossOrigin(origins = "*")
public class BusinessSubTypeController {

    private final Environment environment;

    private final BusinessSubTypeService businessSubTypeService;

    private static final String recordNotFound = "common.record-not-found";
    private static final String userNotFound = "common.user-not-found";
    private static final String commonSaved = "common.saved";
    private static final String commonUpdated = "common.updated";
    private static final String pageableLength = "common.pageable-length";

    @Autowired
    public BusinessSubTypeController(Environment environment, BusinessSubTypeService businessSubTypeService) {
        this.environment = environment;
        this.businessSubTypeService = businessSubTypeService;
    }

    /**
     * Get all business subtypes
     * @param tenantId - the tenant id
     * @return all business subtypes
     */
    @GetMapping(value = "/{tenantId}/all")
    public ResponseEntity<Object> getAllBusinessSubTypes(@PathVariable(value = "tenantId") String tenantId) {
        LoggerRequest.getInstance().logInfo("Get all BusinessSubTypes");

        List<BusinessSubType> businessSubTypes = businessSubTypeService.findAll();
        if (!businessSubTypes.isEmpty()) {
            return new ResponseEntity<>(businessSubTypes, HttpStatus.OK);
        } else {
            SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
            responseMessage.setMessages(environment.getProperty(recordNotFound));
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping(value = "/{tenantId}/{id}")
    public ResponseEntity<Object> getBusinessSubTypeById(@PathVariable(value = "tenantId") String tenantId,
                                                         @PathVariable(value = "id") Long id) {
        LoggerRequest.getInstance().logInfo("Get BusinessSubType by id");

        Optional<BusinessSubType> optionalBusinessSubType = businessSubTypeService.findById(id);
        if (optionalBusinessSubType.isPresent()) {
            return new ResponseEntity<>(optionalBusinessSubType, HttpStatus.OK);
        } else {
            SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
            responseMessage.setMessages(environment.getProperty(recordNotFound));
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping(value = "/{tenantId}/code/{code}")
    public ResponseEntity<Object> getBusinessSubTypesByCode(@PathVariable(value = "tenantId") String tenantId,
                                                            @PathVariable(value = "code") String code) {

        Optional<BusinessSubType> optionalBusinessSubType = businessSubTypeService.findByCode(code);
        if (optionalBusinessSubType.isPresent()) {
            return new ResponseEntity<>(optionalBusinessSubType, HttpStatus.OK);
        } else {
            SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
            responseMessage.setMessages(environment.getProperty(recordNotFound));
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping(value = "/{tenantId}/name/{name}")
    public ResponseEntity<Object> getBusinessSubTypesByName(@PathVariable(value = "tenantId") String tenantId,
                                                            @PathVariable(value = "name") String name) {

        List<BusinessSubType> businessSubTypes = businessSubTypeService.findByName(name);
        if (!businessSubTypes.isEmpty()) {
            return new ResponseEntity<>(businessSubTypes, HttpStatus.OK);
        } else {
            SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
            responseMessage.setMessages(environment.getProperty(recordNotFound));
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping(value = "/{tenantId}/status/{status}")
    public ResponseEntity<Object> getBusinessSubTypesByStatus(@PathVariable(value = "tenantId") String tenantId,
                                                              @PathVariable(value = "status") String status) {

        List<BusinessSubType> businessSubTypes = businessSubTypeService.findByStatus(status);
        if (!businessSubTypes.isEmpty()) {
            return new ResponseEntity<>(businessSubTypes, HttpStatus.OK);
        } else {
            SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
            responseMessage.setMessages(environment.getProperty(recordNotFound));
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping(value = "/{tenantId}")
    public ResponseEntity<Object> addBusinessSubType(@PathVariable(value = "tenantId") String tenantId,
                                                     @Valid @RequestBody BusinessSubTypeAddResource businessSubTypeAddResource) {

        if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty()) {
            throw new UserNotFound(environment.getProperty(userNotFound));
        }

        Long id = businessSubTypeService.validateAndSaveBusinessSubType(tenantId, LogginAuthentcation.getInstance().getUserName(), businessSubTypeAddResource);
        SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty(commonSaved), id.toString());
        return new ResponseEntity<>(successDetailsDto,HttpStatus.CREATED);
    }

    @PutMapping(value = "/{tenantId}/{id}")
    public ResponseEntity<Object> updateBusinessSubType(@PathVariable(value = "tenantId") String tenantId,
                                                        @PathVariable(value = "id") Long id,
                                                        @Valid @RequestBody BusinessSubTypeUpdateResource businessSubTypeUpdateResource) {

        if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty()) {
            throw new UserNotFound(environment.getProperty(userNotFound));
        }

        if (businessSubTypeService.existsById(id)) {
            businessSubTypeService.validateAndUpdateBusinessSubType(tenantId, LogginAuthentcation.getInstance().getUserName(), id, businessSubTypeUpdateResource);
            SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty(commonUpdated));
            return new ResponseEntity<>(successDetailsDto, HttpStatus.OK);
        } else {
            SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty(recordNotFound));
            return new ResponseEntity<>(successDetailsDto, HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @GetMapping(value = "/{tenantId}/search")
    public Page<BusinessSubType> searchBusinessSubType(@PathVariable(value = "tenantId") String tenantId,
                                                       @RequestParam(value = "businessTypeId", required = false) Long businessTypeId,
                                                       @RequestParam(value = "code", required = false) String code,
                                                       @RequestParam(value = "name", required = false) String name,
                                                       @PageableDefault(size = 10) Pageable pageable) {

        if (pageable.getPageSize() > Constants.MAX_PAGEABLE_LENGTH) {
            throw new PageableLengthException(environment.getProperty(pageableLength));
        }
        return businessSubTypeService.searchBusinessSubType(businessTypeId, code, name, pageable);
    }

}
