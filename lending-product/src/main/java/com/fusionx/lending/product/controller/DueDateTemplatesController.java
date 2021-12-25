package com.fusionx.lending.product.controller;

import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.domain.DueDateTemplates;
import com.fusionx.lending.product.resources.DueDateTemplatesAddResource;
import com.fusionx.lending.product.resources.DueDateTemplatesUpdateResource;
import com.fusionx.lending.product.resources.SuccessAndErrorDetails;
import com.fusionx.lending.product.service.DueDateTemplatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * Due Date Templates Controller
 * <p>
 * *******************************************************************************************************
 * ###   Date         Story Point   Task No    Author       Description
 * -------------------------------------------------------------------------------------------------------
 * 1   24-09-2021    FXL-139  	 FXL-926	Piyumi       Created
 * <p>
 * *******************************************************************************************************
 */

@RestController
@RequestMapping(value = "/due-date-template")
@CrossOrigin(origins = "*")
public class DueDateTemplatesController extends MessagePropertyBase {

    private DueDateTemplatesService dueDateTemplatesService;

    @Autowired
    public void setDueDateTemplatesService(DueDateTemplatesService dueDateTemplatesService) {
        this.dueDateTemplatesService = dueDateTemplatesService;
    }

    @GetMapping(value = "/{tenantId}/all")
    public ResponseEntity<Object> getAllDueDateTemplates(
            @PathVariable(value = "tenantId") String tenantId) {

        List<DueDateTemplates> dueDateTemplates = dueDateTemplatesService.findAll();

        int size = dueDateTemplates.size();
        if (size > 0) {
            return new ResponseEntity<>(dueDateTemplates, HttpStatus.OK);

        } else {
            SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
            responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * get Due Date Templates by id
     *
     * @param tenantId - the tenant id
     * @param id       - the id
     * @return the DueDateTemplates by id
     */
    @GetMapping(value = "/{tenantId}/{id}")
    public ResponseEntity<Object> getDueDateTemplatesById(
            @PathVariable(value = "tenantId") String tenantId,
            @PathVariable(value = "id") Long id) {

        Optional<DueDateTemplates> isPresentDueDateTemplates = dueDateTemplatesService.findById(id);
        if (isPresentDueDateTemplates.isPresent()) {
            return new ResponseEntity<>(isPresentDueDateTemplates.get(), HttpStatus.OK);

        } else {
            SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
            responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * get Due Date Templates by code
     *
     * @param tenantId - the tenant id
     * @param code     - the code
     * @return the DueDateTemplates by code
     */
    @GetMapping(value = "/{tenantId}/code/{code}")
    public ResponseEntity<Object> getDueDateTemplatesByCode(
            @PathVariable(value = "tenantId") String tenantId,
            @PathVariable(value = "code") String code) {

        Optional<DueDateTemplates> isPresentDueDateTemplates = dueDateTemplatesService.findByCode(code);
        if (isPresentDueDateTemplates.isPresent()) {
            return new ResponseEntity<>(isPresentDueDateTemplates.get(), HttpStatus.OK);

        } else {
            SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
            responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * get Due Date Templates by name
     *
     * @param tenantId - the tenant id
     * @param name     - the name
     * @return the DueDateTemplates by name
     */
    @GetMapping(value = "/{tenantId}/name/{name}")
    public ResponseEntity<Object> getDueDateTemplatesByName(
            @PathVariable(value = "tenantId") String tenantId,
            @PathVariable(value = "name") String name) {

        List<DueDateTemplates> isPresentDueDateTemplates = dueDateTemplatesService.findByNameContaining(name);
        if (!isPresentDueDateTemplates.isEmpty()) {
            return new ResponseEntity<>(isPresentDueDateTemplates, HttpStatus.OK);

        } else {

            SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
            responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * get Due Date Templates by status
     *
     * @param tenantId - the tenant id
     * @param status   - the status
     * @return the DueDateTemplates by status
     */
    @GetMapping(value = "/{tenantId}/status/{status}")
    public ResponseEntity<Object> getDueDateTemplatesByStatus(
            @PathVariable(value = "tenantId") String tenantId,
            @PathVariable(value = "status") String status) {
        SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();

        if (status.equals("ACTIVE") || status.equals("INACTIVE")) {
            List<DueDateTemplates> isPresentDueDateTemplates = dueDateTemplatesService.findByStatus(status);
            int size = isPresentDueDateTemplates.size();
            if (size > 0) {
                return new ResponseEntity<>(isPresentDueDateTemplates, HttpStatus.OK);
            } else {
                responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
                return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
            }
        } else {
            responseMessage.setMessages(environment.getProperty(COMMON_STATUS_PATTERN));
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }


    /**
     * get Due Date Templates by effectiveDate
     *
     * @param tenantId      - the tenant id
     * @param effectiveDate - the effectiveDate
     * @return the DueDateTemplates by effectiveDate
     */
    @GetMapping(value = "/{tenantId}/effective-date/{effectiveDate}")
    public ResponseEntity<Object> getDueDateTemplatesByEffectiveDate(
            @PathVariable(value = "tenantId") String tenantId,
            @PathVariable(value = "effectiveDate") String effectiveDate) {

        SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
        // yyyy-mm-dd
        String pattern = "^$|([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))";

        if (effectiveDate.matches(pattern)) {
            List<DueDateTemplates> isPresentDueDateTemplates = dueDateTemplatesService.findByEffectiveDate(effectiveDate);
            int size = isPresentDueDateTemplates.size();
            if (size > 0) {
                return new ResponseEntity<>(isPresentDueDateTemplates, HttpStatus.OK);
            } else {

                responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
                return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
            }
        } else {
            responseMessage.setMessages(environment.getProperty(COMMON_INVALID_DATE_PATTERN));
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Add the DueDateTemplates
     *
     * @param tenantId                    - the tenant id
     * @param dueDateTemplatesAddResource - the due date templates add resource
     * @return the response entity
     */
    @PostMapping(value = "/{tenantId}")
    public ResponseEntity<Object> addDueDateTemplates(
            @PathVariable(value = "tenantId") String tenantId,
            @Valid @RequestBody DueDateTemplatesAddResource dueDateTemplatesAddResource) {

        DueDateTemplates dueDateTemplates = dueDateTemplatesService.save(tenantId,
                dueDateTemplatesAddResource);
        SuccessAndErrorDetails successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty(RECORD_CREATED), Long.toString(dueDateTemplates.getId()));
        return new ResponseEntity<>(successAndErrorDetails, HttpStatus.CREATED);
    }

    /**
     * Update updateDueDateTemplates
     *
     * @param tenantId                       - the tenant id
     * @param id                             - the id
     * @param dueDateTemplatesUpdateResource - the due date templates update resource
     * @return the response entity
     */
    @PutMapping(value = "/{tenantId}/{id}")
    public ResponseEntity<Object> updateDueDateTemplates(
            @PathVariable(value = "tenantId") String tenantId,
            @PathVariable(value = "id") Long id,
            @Valid @RequestBody DueDateTemplatesUpdateResource dueDateTemplatesUpdateResource) {

        SuccessAndErrorDetails successAndErrorDetails = new SuccessAndErrorDetails();
        Optional<DueDateTemplates> isPresentDueDateTemplates = dueDateTemplatesService.findById(id);

        if (isPresentDueDateTemplates.isPresent()) {
            DueDateTemplates dueDateTemplates = dueDateTemplatesService.update(tenantId, id, dueDateTemplatesUpdateResource);
            successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty(RECORD_UPDATED), dueDateTemplates.getId().toString());
            return new ResponseEntity<>(successAndErrorDetails, HttpStatus.OK);

        } else {
            successAndErrorDetails.setMessages(environment.getProperty(RECORD_NOT_FOUND));
            return new ResponseEntity<>(successAndErrorDetails, HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

}
