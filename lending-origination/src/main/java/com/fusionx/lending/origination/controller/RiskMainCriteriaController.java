package com.fusionx.lending.origination.controller;

import com.fusionx.lending.origination.Constants;
import com.fusionx.lending.origination.core.LoggerRequest;
import com.fusionx.lending.origination.core.LogginAuthentcation;
import com.fusionx.lending.origination.domain.BusinessSubType;
import com.fusionx.lending.origination.domain.RiskMainCriteria;
import com.fusionx.lending.origination.exception.PageableLengthException;
import com.fusionx.lending.origination.exception.UserNotFound;
import com.fusionx.lending.origination.resource.RiskMainCriteriaAddResource;
import com.fusionx.lending.origination.resource.RiskMainCriteriaUpdateResource;
import com.fusionx.lending.origination.resource.SuccessAndErrorDetailsResource;
import com.fusionx.lending.origination.service.RiskMainCriteriaService;
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
@RequestMapping(value = "/risk-main-criteria")
@CrossOrigin(origins = "*")
public class RiskMainCriteriaController {

    private final Environment environment;

    private final RiskMainCriteriaService riskMainCriteriaService;

    private static final String recordNotFound = "common.record-not-found";
    private static final String userNotFound = "common.user-not-found";
    private static final String commonSaved = "common.saved";
    private static final String commonUpdated = "common.updated";
    private static final String pageableLength = "common.pageable-length";

    @Autowired
    public RiskMainCriteriaController(Environment environment, RiskMainCriteriaService riskMainCriteriaService) {
        this.environment = environment;
        this.riskMainCriteriaService = riskMainCriteriaService;
    }

    /**
     * Get all risk main criteria
     * @param tenantId - the tenant id
     * @return all risk main criteria
     */
    @GetMapping(value = "/{tenantId}/all")
    public ResponseEntity<Object> getAllRiskMainCriteria(@PathVariable(value = "tenantId") String tenantId) {
        LoggerRequest.getInstance().logInfo("Get all Risk main criteria");

        List<RiskMainCriteria> riskMainCriteriaList = riskMainCriteriaService.findAll();
        if (!riskMainCriteriaList.isEmpty()) {
            return new ResponseEntity<>(riskMainCriteriaList, HttpStatus.OK);
        } else {
            SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
            responseMessage.setMessages(environment.getProperty(recordNotFound));
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Get risk main criteria by id
     * @param tenantId - the tenant id
     * @param id - id of the risk main criteria
     * @return risk main criteria if matching id found, otherwise null
     */
    @GetMapping(value = "/{tenantId}/{id}")
    public ResponseEntity<Object> getRiskMainCriteriaById(@PathVariable(value = "tenantId") String tenantId,
                                                         @PathVariable(value = "id") Long id) {
        LoggerRequest.getInstance().logInfo("Get RiskMainCriteria by id");

        Optional<RiskMainCriteria> optionalRiskMainCriteria = riskMainCriteriaService.findById(id);
        if (optionalRiskMainCriteria.isPresent()) {
            return new ResponseEntity<>(optionalRiskMainCriteria, HttpStatus.OK);
        } else {
            SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
            responseMessage.setMessages(environment.getProperty(recordNotFound));
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Get risk main criteria by code
     * @param tenantId - the tenant id
     * @param code - code of the risk main criteria
     * @return risk main criteria if matching code found, otherwise null
     */
    @GetMapping(value = "/{tenantId}/code/{code}")
    public ResponseEntity<Object> getRiskMainCriteriaByCode(@PathVariable(value = "tenantId") String tenantId,
                                                            @PathVariable(value = "code") String code) {
        LoggerRequest.getInstance().logInfo("Get RiskMainCriteria by code");

        Optional<RiskMainCriteria> optionalRiskMainCriteria = riskMainCriteriaService.findByCode(code);
        if (optionalRiskMainCriteria.isPresent()) {
            return new ResponseEntity<>(optionalRiskMainCriteria, HttpStatus.OK);
        } else {
            SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
            responseMessage.setMessages(environment.getProperty(recordNotFound));
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Get risk main criteria list by name
     * @param tenantId - the tenant id
     * @param name - name of the risk main criteria
     * @return risk main criteria list if matching name found, otherwise null
     */
    @GetMapping(value = "/{tenantId}/name/{name}")
    public ResponseEntity<Object> getRiskMainCriteriaByName(@PathVariable(value = "tenantId") String tenantId,
                                                            @PathVariable(value = "name") String name) {
        LoggerRequest.getInstance().logInfo("Get RiskMainCriteria by name");

        List<RiskMainCriteria> riskMainCriteriaList = riskMainCriteriaService.findByName(name);
        if (!riskMainCriteriaList.isEmpty()) {
            return new ResponseEntity<>(riskMainCriteriaList, HttpStatus.OK);
        } else {
            SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
            responseMessage.setMessages(environment.getProperty(recordNotFound));
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Get risk main criteria list by status
     * @param tenantId - the tenant id
     * @param status - status of the risk main criteria
     * @return risk main criteria list if matching status found, otherwise null
     */
    @GetMapping(value = "/{tenantId}/status/{status}")
    public ResponseEntity<Object> getRiskMainCriteriaByStatus(@PathVariable(value = "tenantId") String tenantId,
                                                              @PathVariable(value = "status") String status) {
        LoggerRequest.getInstance().logInfo("Get RiskMainCriteria by status");

        List<RiskMainCriteria> riskMainCriteriaList = riskMainCriteriaService.findByStatus(status);
        if (!riskMainCriteriaList.isEmpty()) {
            return new ResponseEntity<>(riskMainCriteriaList, HttpStatus.OK);
        } else {
            SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
            responseMessage.setMessages(environment.getProperty(recordNotFound));
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Adds risk main criteria
     * @param tenantId - the tenant id
     * @param riskMainCriteriaAddResource - risk main criteria resource
     * @return id of the newly added risk main criteria
     */
    @PostMapping(value = "/{tenantId}")
    public ResponseEntity<Object> addRiskMainCriteria(@PathVariable(value = "tenantId") String tenantId,
                                                     @Valid @RequestBody RiskMainCriteriaAddResource riskMainCriteriaAddResource) {
        LoggerRequest.getInstance().logInfo("Add RiskMainCriteria");

        if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty()) {
            throw new UserNotFound(environment.getProperty(userNotFound));
        }

        Long id = riskMainCriteriaService.validateAndSaveRiskMainCriteria(tenantId, LogginAuthentcation.getInstance().getUserName(), riskMainCriteriaAddResource);
        SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty(commonSaved), id.toString());
        return new ResponseEntity<>(successDetailsDto,HttpStatus.CREATED);
    }

    @PutMapping(value = "/{tenantId}/{id}")
    public ResponseEntity<Object> updateRiskMainCriteria(@PathVariable(value = "tenantId") String tenantId,
                                                        @PathVariable(value = "id") Long id,
                                                        @Valid @RequestBody RiskMainCriteriaUpdateResource riskMainCriteriaUpdateResource) {
        LoggerRequest.getInstance().logInfo("Update RiskMainCriteria");

        if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty()) {
            throw new UserNotFound(environment.getProperty(userNotFound));
        }

        if (riskMainCriteriaService.existsById(id)) {
            riskMainCriteriaService.validateAndUpdateRiskMainCriteria(tenantId, LogginAuthentcation.getInstance().getUserName(), id, riskMainCriteriaUpdateResource);
            SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty(commonUpdated));
            return new ResponseEntity<>(successDetailsDto, HttpStatus.OK);
        } else {
            SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty(recordNotFound));
            return new ResponseEntity<>(successDetailsDto, HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @GetMapping(value = "/{tenantId}/search")
    public Page<RiskMainCriteria> searchRiskMainCriteria(@PathVariable(value = "tenantId") String tenantId,
                                                       @RequestParam(value = "code", required = false) String code,
                                                       @RequestParam(value = "name", required = false) String name,
                                                       @RequestParam(value = "status", required = false) String status,
                                                       @PageableDefault(size = 10) Pageable pageable) {
        LoggerRequest.getInstance().logInfo("Search RiskMainCriteria");

        if (pageable.getPageSize() > Constants.MAX_PAGEABLE_LENGTH) {
            throw new PageableLengthException(environment.getProperty(pageableLength));
        }
        return riskMainCriteriaService.searchRiskMainCriteria(code, name, status, pageable);
    }

}
