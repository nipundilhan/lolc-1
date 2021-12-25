package com.fusionx.lending.transaction.controller;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fusionx.lending.transaction.base.MessagePropertyBase;
import com.fusionx.lending.transaction.core.LogginAuthentcation;
import com.fusionx.lending.transaction.domain.CreditNoteTransactionType;
import com.fusionx.lending.transaction.enums.BankTransactionCodeStatus;
import com.fusionx.lending.transaction.exception.UserNotFound;
import com.fusionx.lending.transaction.resource.CreditNoteTransactionTypeAddResource;
import com.fusionx.lending.transaction.resource.CreditNoteTransactionTypeUpdateResource;
import com.fusionx.lending.transaction.resource.SuccessAndErrorDetails;
import com.fusionx.lending.transaction.resource.SuccessAndErrorDetailsResource;
import com.fusionx.lending.transaction.service.CreditNoteTransactionTypeService;

@RestController
@RequestMapping(value = "/credit-note-transaction-type")
@CrossOrigin(origins = "*")
public class CreditNoteTransactionTypeController  extends MessagePropertyBase {	
	
	private CreditNoteTransactionTypeService creditNoteTransactionTypeService;
	@Autowired
	public void setCreditNoteTransactionTypeService(CreditNoteTransactionTypeService creditNoteTransactionTypeService) {
		this.creditNoteTransactionTypeService = creditNoteTransactionTypeService;
	}
	
    /**
     * Adds the feature benefit group type.
     *
     * @param tenantId              the tenant id
     * @param creditNoteAddResource the credit note add resource
     * @return the response entity
     */
    @GetMapping(value = "/{tenantId}/all")
    public ResponseEntity<Object> getAll(@PathVariable(value = "tenantId") String tenantId) {

        List<CreditNoteTransactionType> list = creditNoteTransactionTypeService.getAll();
        
		int size = list.size();

		if (size > 0) {
			return new ResponseEntity<>(list, HttpStatus.OK);
		} else {
			SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
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
    public ResponseEntity<Object> getCreditNoteTransactionTypeById(@PathVariable(value = "tenantId") String tenantId,
                                                        @PathVariable(value = "id") Long id) {
        SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
        Optional<CreditNoteTransactionType> creditNoteTransactionTypeOptional = creditNoteTransactionTypeService.getById(id);
        if (creditNoteTransactionTypeOptional.isPresent()) {
            return new ResponseEntity<>(creditNoteTransactionTypeOptional.get(), HttpStatus.OK);
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
    @GetMapping(value = "/{tenantId}/credit-note-type/{id}")
    public ResponseEntity<Object> getByCreditNoteTypeId(@PathVariable(value = "tenantId") String tenantId,
                                                        @PathVariable(value = "id") Long id) {
        SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
        List<CreditNoteTransactionType> list = creditNoteTransactionTypeService.getByCreditNoteType(id);
        if ((list != null) && (!list.isEmpty())) {
            return new ResponseEntity<>(list, HttpStatus.OK);
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
    public ResponseEntity<Object> getCreditNoteTransactionTypeByStatus(@PathVariable(value = "tenantId") String tenantId,
                                                            @PathVariable(value = "status") String status) {
        SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();

        if (status.equals(BankTransactionCodeStatus.ACTIVE.toString()) || status.equals(BankTransactionCodeStatus.INACTIVE.toString())) {
            List<CreditNoteTransactionType> list = creditNoteTransactionTypeService.getByStatus(status);
            if ((list != null) && (!list.isEmpty())) {
                return new ResponseEntity<>(list, HttpStatus.OK);
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
     * Adds the feature benefit group type.
     *
     * @param tenantId              the tenant id
     * @param creditNoteAddResource the credit note add resource
     * @return the response entity
     */
    @PostMapping(value = "/{tenantId}")
    public ResponseEntity<Object> addCreditNoteTransactionType(@PathVariable(value = "tenantId") String tenantId,
                                                             @Valid @RequestBody CreditNoteTransactionTypeAddResource creditNoteTransactionTypeAddResource) {
        if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty()) {
            throw new UserNotFound(environment.getProperty(USER_NOT_FOUND));
        }

        Long id = creditNoteTransactionTypeService.create(creditNoteTransactionTypeAddResource ,LogginAuthentcation.getInstance().getUserName() ,tenantId);
        SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails(environment.getProperty(RECORD_CREATED), id.toString());
        return new ResponseEntity<>(responseMessage, HttpStatus.CREATED);
    }
    
    /**
     * Adds the feature benefit group type.
     *
     * @param tenantId              the tenant id
     * @param creditNoteAddResource the credit note add resource
     * @return the response entity
     */
    @PutMapping(value = "/{tenantId}/{id}")
    public ResponseEntity<Object> updateCreditNoteTransactionType(@PathVariable(value = "tenantId") String tenantId,
    														 @PathVariable(value = "id") Long id,
                                                             @Valid @RequestBody CreditNoteTransactionTypeUpdateResource creditNoteTransactionTypeUpdateResource) {
        if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty()) {
            throw new UserNotFound(environment.getProperty(USER_NOT_FOUND));
        }

        Long updatedId = creditNoteTransactionTypeService.update(creditNoteTransactionTypeUpdateResource ,id ,LogginAuthentcation.getInstance().getUserName() ,tenantId);
        SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails(environment.getProperty(RECORD_UPDATED), updatedId.toString());
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }


}
