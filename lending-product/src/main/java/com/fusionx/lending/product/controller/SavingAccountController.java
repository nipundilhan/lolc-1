package com.fusionx.lending.product.controller;


import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.exception.UserNotFound;
import com.fusionx.lending.product.resources.SavingAccountAddResource;
import com.fusionx.lending.product.resources.SuccessAndErrorDetails;
import com.fusionx.lending.product.service.SavingAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * API Service related to Lending Account Detail.
 *
 * @author Rohan
 * @version 1.0.0
 * @since 1.0.0
 * <p>
 * <br/><br/>
 * <b>Change History : </b>
 * <br/><br/>
 * #        Date            Story Point     Task No     Author                  Description
 * <br/>
 * .....................................................................................................................<br/>
 * 1        14-10-2021      -               -           Thushan                  Created
 * <p>
 *
 */
@RestController
@RequestMapping(value = "/saving-account-detail")
@CrossOrigin(origins = "*")
public class SavingAccountController extends MessagePropertyBase {

    private SavingAccountService  savingAccountService;

    @Autowired
    public void setSavingAccountService(SavingAccountService savingAccountService) {
        this.savingAccountService = savingAccountService;
    }

    @PostMapping(value = "/{tenantId}")
    public ResponseEntity<Object> addSavingAccount(@PathVariable(value = "tenantId") String tenantId,
                                                   @Valid @RequestBody SavingAccountAddResource savingAccountAddResource){
        if (LogginAuthentcation.getInstance().getUserName() == null
                || LogginAuthentcation.getInstance().getUserName().isEmpty()) {
            throw new UserNotFound(environment.getProperty(COMMON_USER_NOT_FOUND));
        }
        SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
        Object bankAccountRequestResponseResource = savingAccountService.addSavingAccount(tenantId, savingAccountAddResource);

        if (bankAccountRequestResponseResource != null) {
            return new ResponseEntity<>(bankAccountRequestResponseResource, HttpStatus.CREATED);
        } else {
            responseMessage.setMessages(RECORD_NOT_FOUND);
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

}
