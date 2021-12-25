package com.fusionx.lending.transaction.controller;

/**
 * Bank Transaction Sub Code service
 *
 * @author Nisalak
 * @Dat 29-08-2019
 * <p>
 * *******************************************************************************************************
 * ###   Date         Story Point   Task No    Author       Description
 * -------------------------------------------------------------------------------------------------------
 * 1   29-08-2019   FX-1678       FX-825     Nisalak      Created
 * <p>
 * *******************************************************************************************************
 */

import com.fusionx.lending.transaction.base.MessagePropertyBase;
import com.fusionx.lending.transaction.core.LogginAuthentcation;
import com.fusionx.lending.transaction.domain.BankTransactionCode;
import com.fusionx.lending.transaction.domain.BankTransactionSubCode;
import com.fusionx.lending.transaction.enums.BankTransactionSubCodeStatus;
import com.fusionx.lending.transaction.exception.UserNotFound;
import com.fusionx.lending.transaction.resource.BankTransactionSubCodeResource;
import com.fusionx.lending.transaction.resource.BankTransactionSubCodeUpdateResource;
import com.fusionx.lending.transaction.resource.SuccessAndErrorDetailsResource;
import com.fusionx.lending.transaction.service.BankTransactionCodeService;
import com.fusionx.lending.transaction.service.BankTransactionSubCodeService;
import org.ietf.jgss.MessageProp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping(value = "/bank-transaction-sub-code")
@CrossOrigin(origins = "*")
public class BankTransactionSubCodeController extends MessagePropertyBase {
    
    @Autowired
    private BankTransactionCodeService bankTransactionCodeService;

    @Autowired
    private BankTransactionSubCodeService bankTransactionSubCodeService;


    /**
     * get all Bank Transaction Sub Code
     * @param @PathVariable{tenantId}
     * @param @PathVariable{all}
     * @return List<Type>
     */
    @GetMapping(value = "/{tenantId}/{transCodeId}/subcode/all")
    public ResponseEntity<Object> getAllBankTransactionSubCode(@PathVariable(value = "tenantId") String tenantId,
                                                               @PathVariable(value = "transCodeId") Long transCodeId) {
        SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
        List<BankTransactionSubCode> isPresentBankTransactionSubCode = bankTransactionSubCodeService.findByBankTransactionCodeId(transCodeId);
        if (!isPresentBankTransactionSubCode.isEmpty()) {
            return new ResponseEntity<>( isPresentBankTransactionSubCode, HttpStatus.OK);
        } else
            responseMessage.setMessages(RECORD_NOT_FOUND);
        return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
    }


    /**
     * get Bank Transaction Sub Code by sub code id
     * @param @PathVariable{tenantId}
     * @param @PathVariable{id}
     * @return Option
     */
    @GetMapping(value = "/{tenantId}/subcode/{transSubCodeId}")
    public ResponseEntity<Object> getBankTransactionSubCodeByCodeIdAndSubCodeId(@PathVariable(value = "tenantId") String tenantId,
                                                                                @PathVariable(value = "transSubCodeId") Long transSubCodeId) {
        SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
        Optional<BankTransactionSubCode> isPresentBankTransactionSubCode = bankTransactionSubCodeService.findById(transSubCodeId);
        if (isPresentBankTransactionSubCode.isPresent())
            return new ResponseEntity<>(isPresentBankTransactionSubCode.get(), HttpStatus.OK);
        else {
            responseMessage.setMessages(RECORD_NOT_FOUND);
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }


    /**
     * get Bank Transaction Sub Code by status
     * @param @PathVariable{tenantId}
     * @param @PathVariable{status}
     * @return Option
     */
    @GetMapping(value = "/{tenantId}/{transCodeId}/subcode/status/{status}")
    public ResponseEntity<Object> getBankTransactionCodeByCodeIdAndStatus(@PathVariable(value = "tenantId") String tenantId,
                                                                          @PathVariable(value = "transCodeId") Long transCodeId,
                                                                          @PathVariable(value = "status") String status) {
        SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
        Optional<BankTransactionCode> isPresentBankTransactionCode = bankTransactionCodeService.findById(transCodeId);
        if (isPresentBankTransactionCode.isPresent()) {
            if (status.equals(BankTransactionSubCodeStatus.ACTIVE.toString()) || status.equals(BankTransactionSubCodeStatus.INACTIVE.toString())) {
                List<BankTransactionSubCode> bankTransactionSubCode = bankTransactionSubCodeService.findByBankTransactionCodeIdAndStatus(transCodeId, status);
                int size = bankTransactionSubCode.size();
                if (size > 0) {
                    return new ResponseEntity<>(bankTransactionSubCode, HttpStatus.OK);
                } else {
                    responseMessage.setMessages(RECORD_NOT_FOUND);
                    return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
                }
            } else {
                responseMessage.setMessages(RECORD_NOT_FOUND);
                return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
            }
        } else
            responseMessage.setMessages(RECORD_NOT_FOUND);
        return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
    }
    
    /**
     * get Bank Transaction Sub Code by description
     * @param @PathVariable{tenantId}
     * @param @PathVariable{description}
     * @return Option
     */
    @GetMapping(value = "/{tenantId}/{transCodeId}/subcode/description/{description}")
    public ResponseEntity<Object> getBankTransactionCodeByCodeIdAndDescription(@PathVariable(value = "tenantId") String tenantId,
                                                                          @PathVariable(value = "transCodeId") Long transCodeId,
                                                                          @PathVariable(value = "description") String description) {
        SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
        Optional<BankTransactionCode> isPresentBankTransactionCode = bankTransactionCodeService.findById(transCodeId);
        if (isPresentBankTransactionCode.isPresent()) {
            
                List<BankTransactionSubCode> bankTransactionSubCode = bankTransactionSubCodeService.findByBankTransactionCodeIdAndDescription(transCodeId, description);
                int size = bankTransactionSubCode.size();
                if (size > 0) {
                    return new ResponseEntity<>(bankTransactionSubCode, HttpStatus.OK);
                } else {
                    responseMessage.setMessages(RECORD_NOT_FOUND);
                    return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
                }
            
        } else
            responseMessage.setMessages(RECORD_NOT_FOUND);
        return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
    }

    /**
     * get Bank Transaction Sub Code by sub code
     * @param @PathVariable{tenantId}
     * @param @PathVariable{status}
     * @return Option
     */
    @GetMapping(value = "/{tenantId}/{transCodeId}/subcode/{transSubCode}")
    public ResponseEntity<Object> getBankTransactionCodeByCodeIdAndSubCode(@PathVariable(value = "tenantId") String tenantId,
                                                                           @PathVariable(value = "transCodeId") Long transCodeId,
                                                                           @PathVariable(value = "transSubCode") String transSubCode) {
        SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
        Optional<BankTransactionCode> isPresentBankTransactionCode = bankTransactionCodeService.findById(transCodeId);
        if (isPresentBankTransactionCode.isPresent()) {
            Optional<BankTransactionSubCode> bankTransactionSubCode = bankTransactionSubCodeService.findByBankTransactionCodeIdAndSubCode(transCodeId, transSubCode);
            if (bankTransactionSubCode.isPresent()) {
                return new ResponseEntity<>(bankTransactionSubCode.get(), HttpStatus.OK);
            } else {
                responseMessage.setMessages(RECORD_NOT_FOUND);
                return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
            }
        } else
            responseMessage.setMessages(RECORD_NOT_FOUND);
        return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
    }

    /**
     * save Bank Transaction Sub Code
     * @param @PathVariable{tenantId}
     * @param @RequestBody{BankTransactionSubCodeAddResource}
     * @return SuccessAndErrorDetailsDto
     */
    @PostMapping(value = "/{tenantId}/{transCodeId}/subcode")
    public ResponseEntity<Object> addBankTransactionCode(@PathVariable(value = "tenantId") String tenantId,
                                                         @PathVariable(value = "transCodeId") Long transCodeId,
                                                         @Valid @RequestBody BankTransactionSubCodeResource bankTransactionSubCodeResource) {
        if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty()) {
            throw new UserNotFound(environment.getProperty(USER_NOT_FOUND));
        }

        Optional<BankTransactionCode> isPresentBankTransactionCode = bankTransactionCodeService.findById(transCodeId);
        SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
        if (isPresentBankTransactionCode.isPresent()) {
            bankTransactionSubCodeResource.setCodeId(transCodeId.toString());
            BankTransactionSubCode bankTransactionSubCode = bankTransactionSubCodeService.saveBankTransactionSubCode(tenantId, bankTransactionSubCodeResource);
            responseMessage = new SuccessAndErrorDetailsResource(environment.getProperty(RECORD_CREATED), bankTransactionSubCode.getSubCode());
            return new ResponseEntity<>(responseMessage, HttpStatus.CREATED);
        } else
            responseMessage.setMessages("Invalid transaction code.");
        return new ResponseEntity<>(responseMessage, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    /**
     * update Bank Transaction Sub Code
     * @param @PathVariable{tenantId}
     * @param @RequestBody{BankTransactionSubCodeUpdateResource}
     * @return SuccessAndErrorDetailsDto
     */
    @PutMapping(value = "/{tenantId}/{transCodeId}/subcode/{transSubCodeId}")
    public ResponseEntity<Object> updateBankTransactionCode(@PathVariable(value = "tenantId") String tenantId,
                                                            @PathVariable(value = "transCodeId") Long transCodeId,
                                                            @PathVariable(value = "transSubCodeId") Long transSubCodeId,
                                                            @Valid @RequestBody BankTransactionSubCodeUpdateResource bankTransactionSubCodeUpdateResource) {
        if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty()) {
            throw new UserNotFound(environment.getProperty(USER_NOT_FOUND));
        }
        SuccessAndErrorDetailsResource successAndErrorDetailsResource = new SuccessAndErrorDetailsResource();
        Optional<BankTransactionCode> isPresentBankTransactionCode = bankTransactionCodeService.findById(transCodeId);
        if (isPresentBankTransactionCode.isPresent()) {
            Optional<BankTransactionSubCode> isPresentBankTransactionSubCode = bankTransactionSubCodeService.findById(transSubCodeId);
            if (isPresentBankTransactionSubCode.isPresent() && isPresentBankTransactionSubCode.get().getCodeId().equals(transCodeId)) {
                bankTransactionSubCodeUpdateResource.setId(transSubCodeId.toString());
                bankTransactionSubCodeUpdateResource.setCodeId(transCodeId.toString());
                bankTransactionSubCodeUpdateResource.setTenantId(tenantId);
                BankTransactionSubCode bankTransactionSubCode = bankTransactionSubCodeService.updateBankTransactionSubCode(tenantId, bankTransactionSubCodeUpdateResource);
                successAndErrorDetailsResource = new SuccessAndErrorDetailsResource(environment.getProperty(RECORD_UPDATED), bankTransactionSubCode.getSubCode());
                return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.OK);
            } else {
                successAndErrorDetailsResource.setMessages("Invalid bank transaction sub code.");
                return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.UNPROCESSABLE_ENTITY);
            }
        } else {
            successAndErrorDetailsResource.setMessages("Invalid transaction code.");
            return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    /**
     * Get all Bank Transaction Sub Code
     * @param @PathVariable{tenantId}
     * @param @PathVariable{all}
     * @return List<BankTransactionSubCode>
     */
    @GetMapping(value = "/{tenantId}/subcode/all")
    public ResponseEntity<Object> getAllBankTransactionSubCodes(@PathVariable(value = "tenantId") String tenantId) {
        SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();

        List<BankTransactionSubCode> isPresentBankTransactionSubCode = bankTransactionSubCodeService.getAllBankTransactionSubCodes(tenantId);
        if (!isPresentBankTransactionSubCode.isEmpty())
            return new ResponseEntity<>(isPresentBankTransactionSubCode, HttpStatus.OK);
        else {
            responseMessage.setMessages(RECORD_NOT_FOUND);
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }
}
