package com.fusionx.lending.origination.controller;

import com.fusionx.lending.origination.Constants;
import com.fusionx.lending.origination.core.LoggerRequest;
import com.fusionx.lending.origination.core.LogginAuthentcation;
import com.fusionx.lending.origination.domain.RiskSubCriteria;
import com.fusionx.lending.origination.exception.PageableLengthException;
import com.fusionx.lending.origination.exception.UserNotFound;
import com.fusionx.lending.origination.resource.*;
import com.fusionx.lending.origination.service.RiskSubCriteriaService;
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
@RequestMapping(value = "/risk-sub-criteria")
@CrossOrigin(origins = "*")
public class RiskSubCriteriaController {

    private final Environment environment;

    private final RiskSubCriteriaService riskSubCriteriaService;

    private static final String recordNotFound = "common.record-not-found";
    private static final String userNotFound = "common.user-not-found";
    private static final String commonSaved = "common.saved";
    private static final String commonUpdated = "common.updated";
    private static final String pageableLength = "common.pageable-length";

    @Autowired
    public RiskSubCriteriaController(Environment environment, RiskSubCriteriaService riskSubCriteriaService) {
        this.environment = environment;
        this.riskSubCriteriaService = riskSubCriteriaService;
    }

    /**
     * Get all risk sub criteria
     * @param tenantId - the tenant id
     * @return all risk sub criteria
     */
    @GetMapping(value = "/{tenantId}/all")
    public ResponseEntity<Object> getAllRiskSubCriteria(@PathVariable(value = "tenantId") String tenantId) {
        LoggerRequest.getInstance().logInfo("Get all Risk sub criteria");

        List<RiskSubCriteria> riskSubCriteriaList = riskSubCriteriaService.findAll();
        if (!riskSubCriteriaList.isEmpty()) {
            return new ResponseEntity<>(riskSubCriteriaList, HttpStatus.OK);
        } else {
            SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
            responseMessage.setMessages(environment.getProperty(recordNotFound));
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Get risk sub criteria by id
     * @param tenantId - the tenant id
     * @param id - id of the risk sub criteria
     * @return risk sub criteria if matching id found, otherwise null
     */
    @GetMapping(value = "/{tenantId}/{id}")
    public ResponseEntity<Object> getRiskSubCriteriaById(@PathVariable(value = "tenantId") String tenantId,
                                                         @PathVariable(value = "id") Long id) {
        LoggerRequest.getInstance().logInfo("Get RiskSubCriteria by id");

        Optional<RiskSubCriteria> optionalRiskSubCriteria = riskSubCriteriaService.findById(id);
        if (optionalRiskSubCriteria.isPresent()) {
            return new ResponseEntity<>(optionalRiskSubCriteria, HttpStatus.OK);
        } else {
            SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
            responseMessage.setMessages(environment.getProperty(recordNotFound));
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Get risk sub criteria by code
     * @param tenantId - the tenant id
     * @param code - code of the risk sub criteria
     * @return risk sub criteria if matching code found, otherwise null
     */
    @GetMapping(value = "/{tenantId}/code/{code}")
    public ResponseEntity<Object> getRiskSubCriteriaByCode(@PathVariable(value = "tenantId") String tenantId,
                                                            @PathVariable(value = "code") String code) {
        LoggerRequest.getInstance().logInfo("Get RiskSubCriteria by code");

        Optional<RiskSubCriteria> optionalRiskSubCriteria = riskSubCriteriaService.findByCode(code);
        if (optionalRiskSubCriteria.isPresent()) {
            return new ResponseEntity<>(optionalRiskSubCriteria, HttpStatus.OK);
        } else {
            SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
            responseMessage.setMessages(environment.getProperty(recordNotFound));
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Get risk sub criteria list by name
     * @param tenantId - the tenant id
     * @param name - name of the risk sub criteria
     * @return risk sub criteria list if matching name found, otherwise null
     */
    @GetMapping(value = "/{tenantId}/name/{name}")
    public ResponseEntity<Object> getRiskSubCriteriaByName(@PathVariable(value = "tenantId") String tenantId,
                                                            @PathVariable(value = "name") String name) {
        LoggerRequest.getInstance().logInfo("Get RiskSubCriteria by name");

        List<RiskSubCriteria> riskSubCriteriaList = riskSubCriteriaService.findByName(name);
        if (!riskSubCriteriaList.isEmpty()) {
            return new ResponseEntity<>(riskSubCriteriaList, HttpStatus.OK);
        } else {
            SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
            responseMessage.setMessages(environment.getProperty(recordNotFound));
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Get risk sub criteria list by status
     * @param tenantId - the tenant id
     * @param status - status of the risk sub criteria
     * @return risk sub criteria list if matching status found, otherwise null
     */
    @GetMapping(value = "/{tenantId}/status/{status}")
    public ResponseEntity<Object> getRiskSubCriteriaByStatus(@PathVariable(value = "tenantId") String tenantId,
                                                              @PathVariable(value = "status") String status) {
        LoggerRequest.getInstance().logInfo("Get RiskSubCriteria by status");

        List<RiskSubCriteria> riskSubCriteriaList = riskSubCriteriaService.findByStatus(status);
        if (!riskSubCriteriaList.isEmpty()) {
            return new ResponseEntity<>(riskSubCriteriaList, HttpStatus.OK);
        } else {
            SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
            responseMessage.setMessages(environment.getProperty(recordNotFound));
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Adds risk sub criteria
     * @param tenantId - the tenant id
     * @param riskSubCriteriaAddResource - risk sub criteria resource
     * @return id of the newly added risk sub criteria
     */
    @PostMapping(value = "/{tenantId}")
    public ResponseEntity<Object> addRiskSubCriteria(@PathVariable(value = "tenantId") String tenantId,
                                                     @Valid @RequestBody RiskSubCriteriaAddResource riskSubCriteriaAddResource) {
        LoggerRequest.getInstance().logInfo("Add RiskSubCriteria");

        if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty()) {
            throw new UserNotFound(environment.getProperty(userNotFound));
        }

        Long id = riskSubCriteriaService.validateAndSaveRiskSubCriteria(tenantId, LogginAuthentcation.getInstance().getUserName(), riskSubCriteriaAddResource);
        SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty(commonSaved), id.toString());
        return new ResponseEntity<>(successDetailsDto,HttpStatus.CREATED);
    }

    /**
     * Updates risk sub criteria
     * @param tenantId - the tenant id
     * @param id - id of the risk sub criteria that is going to update
     * @param riskSubCriteriaUpdateResource - risk sub criteria resource with update details
     * @return
     */
    @PutMapping(value = "/{tenantId}/{id}")
    public ResponseEntity<Object> updateRiskSubCriteria(@PathVariable(value = "tenantId") String tenantId,
                                                        @PathVariable(value = "id") Long id,
                                                        @Valid @RequestBody RiskSubCriteriaUpdateResource riskSubCriteriaUpdateResource) {
        LoggerRequest.getInstance().logInfo("Update RiskSubCriteria");

        if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty()) {
            throw new UserNotFound(environment.getProperty(userNotFound));
        }

        if (riskSubCriteriaService.existsById(id)) {
            riskSubCriteriaService.validateAndUpdateRiskSubCriteria(tenantId, LogginAuthentcation.getInstance().getUserName(), id, riskSubCriteriaUpdateResource);
            SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty(commonUpdated));
            return new ResponseEntity<>(successDetailsDto, HttpStatus.OK);
        } else {
            SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty(recordNotFound));
            return new ResponseEntity<>(successDetailsDto, HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    /**
     * Search risk sub criteria
     * @param tenantId - the tenant id
     * @param code - risk sub criteria code
     * @param name - risk sub criteria name
     * @param status - risk sub criteria status
     * @param pageable - page details
     * @return list of risk sub criteria
     */
    @GetMapping(value = "/{tenantId}/search")
    public Page<RiskSubCriteria> searchRiskSubCriteria(@PathVariable(value = "tenantId") String tenantId,
                                                       @RequestParam(value = "code", required = false) String code,
                                                       @RequestParam(value = "name", required = false) String name,
                                                       @RequestParam(value = "status", required = false) String status,
                                                       @PageableDefault(size = 10) Pageable pageable) {
        LoggerRequest.getInstance().logInfo("Search RiskSubCriteria");

        if (pageable.getPageSize() > Constants.MAX_PAGEABLE_LENGTH) {
            throw new PageableLengthException(environment.getProperty(pageableLength));
        }
        return riskSubCriteriaService.searchRiskSubCriteria(code, name, status, pageable);
    }

}
