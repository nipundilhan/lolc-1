package com.fusionx.lending.product.controller;

import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.domain.ApplicationFrequency;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.exception.ValidateRecordException;
import com.fusionx.lending.product.resources.ApplicationFrequencyAddResource;
import com.fusionx.lending.product.resources.ApplicationFrequencyUpdateResource;
import com.fusionx.lending.product.resources.SuccessAndErrorDetailsResource;
import com.fusionx.lending.product.service.ApplicationFrequencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * API Service related to Application Frequency.
 *
 * @author Senitha Perera
 * @version 1.0.0
 * @since 1.0.0
 * <p>
 * <br/><br/>
 * <b>Change History : </b>
 * <br/><br/>
 * #        Date            Story Point     Task No             Author                  Description
 * <br/>
 * .....................................................................................................................<br/>
 * 1        04-06-2020      -               FX-6511             Senitha Perera          Created
 * <p>
 */
@RestController
@RequestMapping("/application-frequency")
@CrossOrigin("*")
public class ApplicationFrequencyController extends MessagePropertyBase {

    private ApplicationFrequencyService applicationFrequencyService;

    @Autowired
    public void setApplicationFrequencyService(ApplicationFrequencyService applicationFrequencyService) {
        this.applicationFrequencyService = applicationFrequencyService;
    }


    /**
     * Returns the list of Application frequencies
     *
     * @param tenantId the tenant id
     * @return the list of application frequencies
     */
    @GetMapping("/{tenantId}/all")
    public ResponseEntity<Object> getAllApplicationFrequency(@PathVariable(value = "tenantId") String tenantId) {
        SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
        List<ApplicationFrequency> isPresentApplicationFrequency = applicationFrequencyService.getAll(tenantId);

        if (!isPresentApplicationFrequency.isEmpty()) {
            return new ResponseEntity<>(isPresentApplicationFrequency, HttpStatus.OK);
        } else {
            responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Returns the application frequency by id
     *
     * @param tenantId the tenant id
     * @param id       the application frequency id
     * @return the application frequency json
     */
    @GetMapping(value = "/{tenantId}/{id}")
    public ResponseEntity<Object> getApplicationFrequencyById(@PathVariable(value = "tenantId") String tenantId,
                                                              @PathVariable(value = "id") Long id) {
        SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
        Optional<ApplicationFrequency> isPresentApplicationFrequency = applicationFrequencyService.getById(tenantId, id);

        if (isPresentApplicationFrequency.isPresent()) {
            return new ResponseEntity<>(isPresentApplicationFrequency.get(), HttpStatus.OK);
        } else {
            responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Returns the application frequency by code
     *
     * @param tenantId the tenant id
     * @param code     the code
     * @return the application frequency
     */
    @GetMapping(value = "/{tenantId}/code/{code}")
    public ResponseEntity<Object> getApplicationFrequencyByCode(@PathVariable(value = "tenantId") String tenantId,
                                                                @PathVariable(value = "code") String code) {
        SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
        Optional<ApplicationFrequency> isPresentApplicationFrequency = applicationFrequencyService.getByCode(tenantId, code);
        if (isPresentApplicationFrequency.isPresent()) {
            return new ResponseEntity<>(isPresentApplicationFrequency.get(), HttpStatus.OK);
        } else {
            responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Returns the application frequency by its name
     *
     * @param tenantId the tenant id
     * @param name     the name
     * @return the application frequency
     */
    @GetMapping("/{tenantId}/name/{name}")
    public ResponseEntity<Object> getApplicationFrequencyByName(@PathVariable(value = "tenantId") String tenantId,
                                                                @PathVariable(value = "name") String name) {
        SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
        List<ApplicationFrequency> isPresentApplicationFrequency = applicationFrequencyService.getByName(tenantId, name);

        if (!isPresentApplicationFrequency.isEmpty()) {
            return new ResponseEntity<>(isPresentApplicationFrequency, HttpStatus.OK);
        } else {
            responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Returns the application frequency by status
     *
     * @param tenantId the tenant id
     * @param status   the status
     * @return the application frequency
     */
    @GetMapping(value = "/{tenantId}/status/{status}")
    public ResponseEntity<Object> getApplicationFrequencyByStatus(@PathVariable(value = "tenantId") String tenantId,
                                                                  @PathVariable(value = "status") String status) {

        SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
        if (status.equals(CommonStatus.ACTIVE.toString()) || status.equals(CommonStatus.INACTIVE.toString())) {

            List<ApplicationFrequency> isPresentApplicationFrequency = applicationFrequencyService.getByStatus(tenantId, status);
            if (!isPresentApplicationFrequency.isEmpty()) {
                return new ResponseEntity<>(isPresentApplicationFrequency, HttpStatus.OK);
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
     * Saves the given application frequency
     *
     * @param tenantId                        the tenant id
     * @param addApplicationFrequencyResource the object to save
     * @return the status message
     */
    @PostMapping("/{tenantId}")
    public ResponseEntity<Object> addApplicationFrequency(@PathVariable(value = "tenantId") String tenantId,
                                                          @Valid @RequestBody ApplicationFrequencyAddResource addApplicationFrequencyResource) {
        ApplicationFrequency applicationFrequency = applicationFrequencyService.addApplicationFrequency(tenantId, addApplicationFrequencyResource);
        SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource(getEnvironment().getProperty(RECORD_CREATED), applicationFrequency.getCode());
        return new ResponseEntity<>(responseMessage, HttpStatus.CREATED);
    }

    /**
     * Updates the given application frequency
     *
     * @param tenantId                           the tenant id
     * @param id                                 the id
     * @param updateApplicationFrequencyResource the object which contains update data
     * @return the message
     */
    @PutMapping(value = "{tenantId}/{id}")
    public ResponseEntity<Object> updateApplicationFrequency(@PathVariable(value = "tenantId") String tenantId,
                                                             @PathVariable(value = "id") Long id,
                                                             @Valid @RequestBody ApplicationFrequencyUpdateResource updateApplicationFrequencyResource) {
        SuccessAndErrorDetailsResource successAndErrorDetailsResource = new SuccessAndErrorDetailsResource();
        Optional<ApplicationFrequency> isPresentApplicationFrequency = applicationFrequencyService.getById(tenantId, id);

        if (isPresentApplicationFrequency.isPresent()) {
            updateApplicationFrequencyResource.setId(id.toString());
            ApplicationFrequency applicationFrequency = applicationFrequencyService.updateApplicationFrequency(tenantId, updateApplicationFrequencyResource);
            successAndErrorDetailsResource = new SuccessAndErrorDetailsResource(getEnvironment().getProperty(RECORD_UPDATED), applicationFrequency.getId().toString());
            return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.OK);
        } else {
            successAndErrorDetailsResource.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
            return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

}
