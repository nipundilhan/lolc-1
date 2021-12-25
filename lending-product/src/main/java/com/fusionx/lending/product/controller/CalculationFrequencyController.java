package com.fusionx.lending.product.controller;

import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.domain.CalculationFrequency;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.exception.ValidateRecordException;
import com.fusionx.lending.product.resources.ApplicationFrequencyAddResource;
import com.fusionx.lending.product.resources.ApplicationFrequencyUpdateResource;
import com.fusionx.lending.product.resources.SuccessAndErrorDetailsResource;
import com.fusionx.lending.product.service.CalculationFrequencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * API Service related to Calculation Frequency.
 *
 * @author Senitha Perera
 * @version 1.0.0
 * @since 1.0.0
 * <p>
 * <br/><br/>
 * <b>Change History : </b>
 * <br/><br/>
 * #        Date            Story Point     Task No             Author                  	Description
 * <br/>
 * .....................................................................................................................<br/>
 * 1        04-06-2020      -               FX-6511       		Senitha Perera         		Created
 * <p>
 */
@RestController
@RequestMapping("/calculation-frequency")
@CrossOrigin("*")
public class CalculationFrequencyController extends MessagePropertyBase {

    private CalculationFrequencyService calculationFrequencyService;

    @Autowired
    public void setCalculationFrequencyService(CalculationFrequencyService calculationFrequencyService) {
        this.calculationFrequencyService = calculationFrequencyService;
    }

    /**
     * Returns the all calculation frequency
     *
     * @param tenantId the tenant id
     * @return the calculation frequency list.
     */
    @GetMapping("/{tenantId}/all")
    public ResponseEntity<Object> getAllCalculationFrequency(@PathVariable(value = "tenantId") String tenantId) {
        SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
        List<CalculationFrequency> isPresentCalculationFrequency = calculationFrequencyService.getAll(tenantId);

        if (!isPresentCalculationFrequency.isEmpty()) {
            return new ResponseEntity<>(isPresentCalculationFrequency, HttpStatus.OK);
        } else {
            responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Returns the calculation frequency by id
     *
     * @param tenantId the tenant id
     * @param id       the id
     * @return the calculation frequency
     */
    @GetMapping(value = "/{tenantId}/{id}")
    public ResponseEntity<Object> getCalculationFrequencyById(@PathVariable(value = "tenantId") String tenantId,
                                                              @PathVariable(value = "id") Long id) {
        SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
        Optional<CalculationFrequency> isPresentCalculationFrequency = calculationFrequencyService.getById(tenantId, id);
        if (isPresentCalculationFrequency.isPresent()) {
            return new ResponseEntity<>(isPresentCalculationFrequency.get(), HttpStatus.OK);
        } else {
            responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Returns the calculation frequency by code
     *
     * @param tenantId the tenant id
     * @param code     the code
     * @return the calculation frequency
     */
    @GetMapping(value = "/{tenantId}/code/{code}")
    public ResponseEntity<Object> getCalculationFrequencyByCode(@PathVariable(value = "tenantId") String tenantId,
                                                                @PathVariable(value = "code") String code) {
        SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
        Optional<CalculationFrequency> isPresentCalculationFrequency = calculationFrequencyService.getByCode(tenantId, code);
        if (isPresentCalculationFrequency.isPresent()) {
            return new ResponseEntity<>(isPresentCalculationFrequency.get(), HttpStatus.OK);
        } else {
            responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Returns the calculation frequency by name
     *
     * @param tenantId the tenant id
     * @param name     the name
     * @return the calculation frequency
     */
    @GetMapping("/{tenantId}/name/{name}")
    public ResponseEntity<Object> getCalculationFrequencyByName(@PathVariable(value = "tenantId") String tenantId,
                                                                @PathVariable(value = "name") String name) {
        SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
        List<CalculationFrequency> isPresentCalculationFrequency = calculationFrequencyService.getByName(tenantId, name);

        if (!isPresentCalculationFrequency.isEmpty()) {
            return new ResponseEntity<>(isPresentCalculationFrequency, HttpStatus.OK);
        } else {
            responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Returns the calculation frequency  by status
     *
     * @param tenantId the tenant id
     * @param status   the status
     * @return the calculation frequency
     */
    @GetMapping(value = "/{tenantId}/status/{status}")
    public ResponseEntity<Object> getCalculationFrequencyByStatus(@PathVariable(value = "tenantId") String tenantId,
                                                                  @PathVariable(value = "status") String status) {

        SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
        if (status.equals(CommonStatus.ACTIVE.toString()) || status.equals(CommonStatus.INACTIVE.toString())) {

            List<CalculationFrequency> isPresentCalculationFrequency = calculationFrequencyService.getByStatus(tenantId, status);
            if (!isPresentCalculationFrequency.isEmpty()) {
                return new ResponseEntity<>(isPresentCalculationFrequency, HttpStatus.OK);
            } else {
                responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
                return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
            }

        } else {

            responseMessage.setMessages(RECORD_NOT_FOUND);
            throw new ValidateRecordException(environment.getProperty("common-status.pattern"), "status");

        }

    }

    /**
     * Saves the calculation frequency
     *
     * @param tenantId                        the tenant id
     * @param addApplicationFrequencyResource the object to save
     * @return the message.
     */
    @PostMapping("/{tenantId}")
    public ResponseEntity<Object> addCalculationFrequency(@PathVariable(value = "tenantId") String tenantId,
                                                          @Valid @RequestBody ApplicationFrequencyAddResource addApplicationFrequencyResource) {
        CalculationFrequency calculationFrequency = calculationFrequencyService.addCalculationFrequency(tenantId, addApplicationFrequencyResource);
        SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource(getEnvironment().getProperty(RECORD_CREATED), calculationFrequency.getCode());
        return new ResponseEntity<>(responseMessage, HttpStatus.CREATED);
    }

    /**
     * Updates the calculation frequency
     *
     * @param tenantId                           the tenant id
     * @param id                                 the id
     * @param updateApplicationFrequencyResource the object which contains update date
     * @return the message.
     */
    @PutMapping(value = "{tenantId}/{id}")
    public ResponseEntity<Object> updateCalculationFrequency(@PathVariable(value = "tenantId") String tenantId,
                                                             @PathVariable(value = "id") Long id,
                                                             @Valid @RequestBody ApplicationFrequencyUpdateResource updateApplicationFrequencyResource) {
        SuccessAndErrorDetailsResource successAndErrorDetailsResource = new SuccessAndErrorDetailsResource();
        Optional<CalculationFrequency> isPresentCalculationFrequency = calculationFrequencyService.getById(tenantId, id);

        if (isPresentCalculationFrequency.isPresent()) {
            updateApplicationFrequencyResource.setId(id.toString());
            CalculationFrequency calculationFrequency = calculationFrequencyService.updateCalculationFrequency(tenantId, updateApplicationFrequencyResource);
            successAndErrorDetailsResource = new SuccessAndErrorDetailsResource(getEnvironment().getProperty(RECORD_UPDATED), calculationFrequency.getId().toString());
            return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.OK);
        } else {
            successAndErrorDetailsResource.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
            return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
}
