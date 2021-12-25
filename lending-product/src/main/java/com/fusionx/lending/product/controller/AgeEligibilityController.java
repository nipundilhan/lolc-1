package com.fusionx.lending.product.controller;

import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.AgeEligibility;
import com.fusionx.lending.product.exception.UserNotFound;
import com.fusionx.lending.product.resources.AgeEligibilityAddResource;
import com.fusionx.lending.product.resources.AgeEligibilityUpdateResource;
import com.fusionx.lending.product.resources.SuccessAndErrorDetails;
import com.fusionx.lending.product.service.AgeEligibilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * API Service related to Age eligibility.
 *
 * @author Menuka Jayasinghe
 * @version 1.0.0
 * @since 1.0.0
 * <p>
 * <br/><br/>
 * <b>Change History : </b>
 * <br/><br/>
 * #        Date            Story Point     Task No     Author                  Description
 * <br/>
 * .....................................................................................................................<br/>
 * 1        17-07-2021      -               -           Menuka Jayasinghe      Created
 * <p>
 *
 */
@RestController
@RequestMapping(value = "/age-eligibility")
@CrossOrigin(origins = "*")
public class AgeEligibilityController extends MessagePropertyBase {

    private AgeEligibilityService ageEligibilityService;

        @Autowired
    public void setAgeEligibilityService(AgeEligibilityService ageEligibilityService) {
        this.ageEligibilityService = ageEligibilityService;
    }

    /**
     * Gets all age eligibility list
     *
     * @param tenantId the id of tenant
     * @return the list of all eligibility
     */
    @GetMapping(value = "/{tenantId}/all")
    public ResponseEntity<Object> getAllAgeEligibility(@PathVariable(value = "tenantId") String tenantId) {
        SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
        List<AgeEligibility> ageEligibility = ageEligibilityService.findAll();
        int size = ageEligibility.size();
        if (size > 0) {
            return new ResponseEntity<>(ageEligibility, HttpStatus.OK);
        } else {
            responseMessage.setMessages(RECORD_NOT_FOUND);
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

   
    /**
     * Gets the age eligibility by id.
     *
     * @param tenantId the tenant id
     * @param id the id
     * @return the age eligibility by id
     */
    @GetMapping(value = "/{tenantId}/{id}")
    public ResponseEntity<Object> getAgeEligibilityById(@PathVariable(value = "tenantId") String tenantId,
                                                        @PathVariable(value = "id") Long id) {
        SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
        Optional<AgeEligibility> isPresentAgeEligibility = ageEligibilityService.findById(id);
        if (isPresentAgeEligibility.isPresent()) {
            return new ResponseEntity<>(isPresentAgeEligibility.get(), HttpStatus.OK);
        } else {
            responseMessage.setMessages(RECORD_NOT_FOUND);
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Get eligibility by status
     *
     * @param tenantId the id of tenant
     * @param status   the status
     * @return the list of all eligibility if record exists, otherwise <code>204 - No Content</code>
     */
    @GetMapping(value = "/{tenantId}/status/{status}")
    public ResponseEntity<Object> getAgeEligibilityByStatus(@PathVariable(value = "tenantId") String tenantId,
                                                            @PathVariable(value = "status") String status) {
        SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();

        if (status.equals("ACTIVE") || status.equals("INACTIVE")) {
            List<AgeEligibility> isPresentAgeEligibility = ageEligibilityService.findByStatus(status);
            int size = isPresentAgeEligibility.size();
            if (size > 0) {
                return new ResponseEntity<>(isPresentAgeEligibility, HttpStatus.OK);
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
     * Adds the age eligibility.
     *
     * @param tenantId the tenant id
     * @param ageEligibilityAddResource the age eligibility add resource
     * @return the response entity
     */
    @PostMapping(value = "/{tenantId}")
    public ResponseEntity<Object> addAgeEligibility(@PathVariable(value = "tenantId") String tenantId,
                                                    @Valid @RequestBody AgeEligibilityAddResource ageEligibilityAddResource) {
        if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty()) {
            throw new UserNotFound(environment.getProperty(COMMON_USER_NOT_FOUND));
        }
        SuccessAndErrorDetails successAndErrorDetails;
        AgeEligibility ageEligibility = ageEligibilityService.addAgeEligibility(tenantId, ageEligibilityAddResource);
        successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty(RECORD_CREATED), Long.toString(ageEligibility.getId()));
        return new ResponseEntity<>(successAndErrorDetails, HttpStatus.CREATED);
    }

    /**
     * Updates the given age eligibility.10
     *
     * @param tenantId                     the id of the tenant
     * @param id                           the id of the object
     * @param ageEligibilityUpdateResource the object which contains data
     * @return the message
     * @see AgeEligibilityUpdateResource
     */
    @PutMapping(value = "/{tenantId}/{id}")
    public ResponseEntity<Object> updateAgeEligibility(@PathVariable(value = "tenantId") String tenantId,
                                                       @PathVariable(value = "id") Long id,
                                                       @Valid @RequestBody AgeEligibilityUpdateResource ageEligibilityUpdateResource) {
        if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty()) {
            throw new UserNotFound(environment.getProperty(COMMON_USER_NOT_FOUND));
        }

        SuccessAndErrorDetails successAndErrorDetails = new SuccessAndErrorDetails();
        Optional<AgeEligibility> isPresentAgeEligibility = ageEligibilityService.findById(id);
        if (isPresentAgeEligibility.isPresent()) {
            AgeEligibility ageEligibility = ageEligibilityService.updateAgeEligibility(tenantId, isPresentAgeEligibility.get(), ageEligibilityUpdateResource, LogginAuthentcation.getInstance().getUserName());
            successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty(RECORD_UPDATED), ageEligibility.getId().toString());
            return new ResponseEntity<>(successAndErrorDetails, HttpStatus.OK);
        } else {
            successAndErrorDetails.setMessages(RECORD_NOT_FOUND);
            return new ResponseEntity<>(successAndErrorDetails, HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
}
