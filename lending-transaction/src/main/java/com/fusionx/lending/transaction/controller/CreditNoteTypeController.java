package com.fusionx.lending.transaction.controller;

import com.fusionx.lending.transaction.base.MessagePropertyBase;
import com.fusionx.lending.transaction.core.LogginAuthentcation;
import com.fusionx.lending.transaction.domain.CreditNoteType;
import com.fusionx.lending.transaction.enums.BankTransactionCodeStatus;
import com.fusionx.lending.transaction.exception.UserNotFound;
import com.fusionx.lending.transaction.resource.CreditNoteAddResource;
import com.fusionx.lending.transaction.resource.CreditNoteUpdateResource;
import com.fusionx.lending.transaction.resource.SuccessAndErrorDetails;
import com.fusionx.lending.transaction.resource.SuccessAndErrorDetailsResource;
import com.fusionx.lending.transaction.service.CreditNoteTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

// TODO: Auto-generated Javadoc

/**
 * CreditNoteType Service
 * <p>
 * *******************************************************************************************************
 * ###   	Date         	Story Point   	Task No    		Author       	Description
 * -------------------------------------------------------------------------------------------------------
 * 1   	09-AUG-2021      		     					Sanatha      	Initial Development
 * <p>
 * *******************************************************************************************************.
 */
@RestController
@RequestMapping(value = "/credit-note-type")
@CrossOrigin(origins = "*")
public class CreditNoteTypeController extends MessagePropertyBase {
  
    @Autowired
    private CreditNoteTypeService service; 

    /**
     * get all CreditNoteType.
     *
     * @param tenantId the tenant id
     * @return List<Type>
     */
    @GetMapping(value = "/{tenantId}/all")
    public ResponseEntity<Object> getAllCreditNoteType(@PathVariable(value = "tenantId") String tenantId) {
        SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
        List<CreditNoteType> creditNoteType = service.findAll();
        int size = creditNoteType.size();
        if (size > 0) {
            return new ResponseEntity<>( creditNoteType, HttpStatus.OK);
        } else {
            responseMessage.setMessages(RECORD_NOT_FOUND);
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }


    /**
     * get CreditNoteType by id.
     *
     * @param tenantId the tenant id
     * @param id       the id
     * @return Option
     */
    @GetMapping(value = "/{tenantId}/{id}")
    public ResponseEntity<Object> getCreditNoteTypeById(@PathVariable(value = "tenantId") String tenantId,
                                                        @PathVariable(value = "id") Long id) {
        SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
        Optional<CreditNoteType> creditNoteType = service.findById(id);
        if (creditNoteType.isPresent()) {
            return new ResponseEntity<>(creditNoteType.get(), HttpStatus.OK);
        } else {
            responseMessage.setMessages(RECORD_NOT_FOUND);
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * get CreditNoteType by status.
     *
     * @param tenantId the tenant id
     * @param status   the status
     * @return Option
     */
    @GetMapping(value = "/{tenantId}/status/{status}")
    public ResponseEntity<Object> getCreditNoteTypeByStatus(@PathVariable(value = "tenantId") String tenantId,
                                                            @PathVariable(value = "status") String status) {
        SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();

        if (status.equals(BankTransactionCodeStatus.ACTIVE.toString()) || status.equals(BankTransactionCodeStatus.INACTIVE.toString())) {
            Optional<Collection<CreditNoteType>> creditNoteType = service.findByStatus(status);
            if (creditNoteType.isPresent()) {
                return new ResponseEntity<>(creditNoteType.get(), HttpStatus.OK);
            } else {
                responseMessage.setMessages(RECORD_NOT_FOUND);
                return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
            }
        } else {
            responseMessage.setMessages(RECORD_NOT_FOUND);
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }

    }

    /**
     * get CreditNoteType by code.
     *
     * @param tenantId the tenant id
     * @param code     the code
     * @return Option
     */
    @GetMapping(value = "/{tenantId}/code/{code}")
    public ResponseEntity<Object> getMerchantDetailByCode(@PathVariable(value = "tenantId") String tenantId,
                                                          @PathVariable(value = "code") String code) {
        SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();

        Optional<CreditNoteType> isPresentCreditNoteType = service.findByCode(code);
        if (isPresentCreditNoteType.isPresent()) {
            return new ResponseEntity<>(isPresentCreditNoteType.get(), HttpStatus.OK);
        } else {
            responseMessage.setMessages(RECORD_NOT_FOUND);
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * get CreditNoteType by name.
     *
     * @param tenantId the tenant id
     * @param name     the name
     * @return Option
     */
    @GetMapping(value = "/{tenantId}/name/{name}")
    public ResponseEntity<Object> getCreditNoteTypeByName(@PathVariable(value = "tenantId") String tenantId,
                                                          @PathVariable(value = "name") String name) {
        SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();


        Optional<Collection<CreditNoteType>> creditNoteType = service.findByName(name);
        if (creditNoteType.isPresent()) {
            return new ResponseEntity<>(creditNoteType.get(), HttpStatus.OK);
        } else {
            responseMessage.setMessages(RECORD_NOT_FOUND);
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }

    }

    /**
     * Adds the feature benefit group type.
     *
     * @param tenantId              the tenant id
     * @param creditNoteAddResource the credit note add resource
     * @return the response entity
     */
    @PostMapping(value = "/{tenantId}")
    public ResponseEntity<Object> addFeatureBenefitGroupType(@PathVariable(value = "tenantId") String tenantId,
                                                             @Valid @RequestBody CreditNoteAddResource creditNoteAddResource) {
        if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty()) {
            throw new UserNotFound(environment.getProperty(USER_NOT_FOUND));
        }

        CreditNoteType creditNoteType = service.addCreditNoteType(tenantId, creditNoteAddResource);
        SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails(environment.getProperty(RECORD_CREATED), Long.toString(creditNoteType.getId()));
        return new ResponseEntity<>(responseMessage, HttpStatus.CREATED);
    }

    /**
     * Update feature benefit group type.
     *
     * @param tenantId                 the tenant id
     * @param id                       the id
     * @param creditNoteUpdateResource the credit note update resource
     * @return the response entity
     */
    @PutMapping(value = "/{tenantId}/{id}")
    public ResponseEntity<Object> updateFeatureBenefitGroupType(@PathVariable(value = "tenantId") String tenantId,
                                                                @PathVariable(value = "id") Long id,
                                                                @Valid @RequestBody CreditNoteUpdateResource creditNoteUpdateResource) {
        if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty()) {
            throw new UserNotFound(environment.getProperty(USER_NOT_FOUND));
        }


        SuccessAndErrorDetailsResource successAndErrorDetailsResource = new SuccessAndErrorDetailsResource();
        Optional<CreditNoteType> isPresentCreditNoteType = service.findById(id);
        if (isPresentCreditNoteType.isPresent()) {

            CreditNoteType creditNoteType = service.updateCreditNoteType(tenantId, creditNoteUpdateResource, id);
            successAndErrorDetailsResource = new SuccessAndErrorDetailsResource(environment.getProperty(RECORD_UPDATED), creditNoteType.getCode());
            return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.OK);
        } else {
            successAndErrorDetailsResource.setMessages(RECORD_NOT_FOUND);
            return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

}
