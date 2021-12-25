package com.fusionx.lending.transaction.controller;

/**
 * Bank Transaction Code service
 *
 * @author Nisalak
 * @Dat 28-08-2019
 * <p>
 * *******************************************************************************************************
 * ###   Date         Story Point   Task No    Author       Description
 * -------------------------------------------------------------------------------------------------------
 * 1   28-08-2019   FX-1678       FX-825     Nisalak      Created
 * <p>
 * *******************************************************************************************************
 */

import com.fusionx.lending.transaction.base.MessagePropertyBase;
import com.fusionx.lending.transaction.core.LogginAuthentcation;
import com.fusionx.lending.transaction.domain.BankTransactionCode;
import com.fusionx.lending.transaction.enums.BankTransactionCodeStatus;
import com.fusionx.lending.transaction.exception.UserNotFound;
import com.fusionx.lending.transaction.resource.BankTransactionCodeResource;
import com.fusionx.lending.transaction.resource.BankTransactionCodeUpdateResource;
import com.fusionx.lending.transaction.resource.SuccessAndErrorDetailsResource;
import com.fusionx.lending.transaction.service.BankTransactionCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping(value = "/bank-transaction-code")
@CrossOrigin(origins = "*")
public class BankTransactionCodeController extends MessagePropertyBase {

	
    @Autowired
    private BankTransactionCodeService bankTransactionCodeService;
    /**
     * get all Bank Transaction Code
     * @param @PathVariable{tenantId}
     * @param @PathVariable{all}
     * @return List<Type>
     */
    @GetMapping(value = "/{tenantId}/all")
    public ResponseEntity<Object> getAllBankTransactionCode(@PathVariable(value = "tenantId") String tenantId) {
        SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
        List<BankTransactionCode> bankTransactionCode = bankTransactionCodeService.findAll();
        int size = bankTransactionCode.size();
        if (size > 0) {
            return new ResponseEntity<>(bankTransactionCode, HttpStatus.OK);
        } else {
            responseMessage.setMessages(RECORD_NOT_FOUND);
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * get Bank Transaction Code by id
     * @param @PathVariable{tenantId}
     * @param @PathVariable{id}
     * @return Option
     */
    @GetMapping(value = "/{tenantId}/{transactionCodeId}")
    public ResponseEntity<Object> getBankTransactionCodeById(@PathVariable(value = "tenantId") String tenantId,
                                                             @PathVariable(value = "transactionCodeId") Long transCodeId) {
        SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
        Optional<BankTransactionCode> bankTransactionCode = bankTransactionCodeService.findById(transCodeId);
        if (bankTransactionCode.isPresent()) {
            return new ResponseEntity<>(bankTransactionCode.get(), HttpStatus.OK);
        } else {
            responseMessage.setMessages(RECORD_NOT_FOUND);
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * get Bank Transaction Code by status
     * @param @PathVariable{tenantId}
     * @param @PathVariable{status}
     * @return Option
     */
    @GetMapping(value = "/{tenantId}/status/{status}")
    public ResponseEntity<Object> getBankTransactionCodeByStatus(@PathVariable(value = "tenantId") String tenantId,
                                                                 @PathVariable(value = "status") String status) {
        SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();

        if (status.equals(BankTransactionCodeStatus.ACTIVE.toString()) || status.equals(BankTransactionCodeStatus.INACTIVE.toString())) {
            Optional<Collection<BankTransactionCode>> bankTransactionCode = bankTransactionCodeService.findByStatus(status);
            if (bankTransactionCode.isPresent()) {
                return new ResponseEntity<>(bankTransactionCode.get(), HttpStatus.OK);
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
     * get Bank Transaction Code by description
     * @param @PathVariable{tenantId}
     * @param @PathVariable{description}
     * @return Option
     */
    @GetMapping(value = "/{tenantId}/description/{description}")
    public ResponseEntity<Object> getBankTransactionCodeByDescription(@PathVariable(value = "tenantId") String tenantId,
                                                                 @PathVariable(value = "description") String description) {
        SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();

       
            Optional<Collection<BankTransactionCode>> bankTransactionCode = bankTransactionCodeService.findByDescription(description);
            if (bankTransactionCode.isPresent()) {
                return new ResponseEntity<>(bankTransactionCode.get(), HttpStatus.OK);
            } else {
                responseMessage.setMessages(RECORD_NOT_FOUND);
                return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
            }
       
    }

    /**
     * get Bank Transaction Code by code
     * @param @PathVariable{tenantId}
     * @param @PathVariable{status}
     * @return Option
     */
    @GetMapping(value = "/{tenantId}/code/{transactionCode}")
    public ResponseEntity<Object> getMerchantDetailByCode(@PathVariable(value = "tenantId") String tenantId,
                                                          @PathVariable(value = "transactionCode") String bankTransactionCode) {
        SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();

        Optional<BankTransactionCode> isPresentBankTransactionCode = bankTransactionCodeService.findByCode(bankTransactionCode);
        if (isPresentBankTransactionCode.isPresent()) {
            return new ResponseEntity<>(isPresentBankTransactionCode.get(), HttpStatus.OK);
        } else {
            responseMessage.setMessages(RECORD_NOT_FOUND);
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * save Bank Transaction Code
     * @param @PathVariable{tenantId}
     * @param @RequestBody{BankTransactionCodeAddResource}
     * @return SuccessAndErrorDetailsDto
     */
    @PostMapping(value = "/{tenantId}")
    public ResponseEntity<Object> addBankTransactionCode(@PathVariable(value = "tenantId") String tenantId,
                                                         @Valid @RequestBody BankTransactionCodeResource bankTransactionCodeResource) {
        if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty()) {
            throw new UserNotFound(environment.getProperty(USER_NOT_FOUND));
        }

        BankTransactionCode bankTransactionCode = bankTransactionCodeService.saveBankTransactionCode(tenantId, bankTransactionCodeResource);
        SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource(environment.getProperty(RECORD_CREATED), bankTransactionCode.getCode());
        return new ResponseEntity<>(responseMessage, HttpStatus.CREATED);
    }

    /**
     * update Bank Transaction Code
     * @param @PathVariable{tenantId}
     * @param @RequestBody{BankTransactionCodeUpdateResource}
     * @return SuccessAndErrorDetailsDto
     */
    @PutMapping(value = "/{tenantId}/{transactionCodeId}")
    public ResponseEntity<Object> updateBankTransactionCode(@PathVariable(value = "tenantId") String tenantId,
                                                            @PathVariable(value = "transactionCodeId") Long transCodeId,
                                                            @Valid @RequestBody BankTransactionCodeUpdateResource bankTransactionCodeUpdateResource) {

        if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty()) {
            throw new UserNotFound(environment.getProperty(USER_NOT_FOUND));
        }

        SuccessAndErrorDetailsResource successAndErrorDetailsResource = new SuccessAndErrorDetailsResource();
        Optional<BankTransactionCode> isPresentBankTransactionCode = bankTransactionCodeService.findById(transCodeId);

        if (isPresentBankTransactionCode.isPresent()) {
            bankTransactionCodeUpdateResource.setId(transCodeId.toString());
            bankTransactionCodeUpdateResource.setTenantId(tenantId);
            BankTransactionCode bankTransactionCode = bankTransactionCodeService.updateBankTransactionCode(tenantId, bankTransactionCodeUpdateResource);
            successAndErrorDetailsResource = new SuccessAndErrorDetailsResource(environment.getProperty(RECORD_UPDATED), bankTransactionCode.getCode());
            return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.OK);
        } else {
            successAndErrorDetailsResource.setMessages(RECORD_NOT_FOUND);
            return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
}
