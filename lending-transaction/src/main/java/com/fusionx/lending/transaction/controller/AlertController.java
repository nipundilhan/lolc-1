package com.fusionx.lending.transaction.controller;

import com.fusionx.lending.transaction.base.MessagePropertyBase;
import com.fusionx.lending.transaction.core.LogginAuthentcation;
import com.fusionx.lending.transaction.resource.AlertRequest;
import com.fusionx.lending.transaction.resource.SuccessAndErrorDetailsResource;
import com.fusionx.lending.transaction.service.AlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping(value = "/alert-construct")
@CrossOrigin(origins = "*")
public class AlertController extends MessagePropertyBase {

    @Autowired
    private AlertService service;


    /**
     * Recovery submit process
     */
    @PostMapping(value = "/{tenantId}")
    public ResponseEntity<Object> recoverySubmitProcess(@PathVariable(value = "tenantId", required = true) String tenantId,
                                                        @Valid @RequestBody AlertRequest alertRequest) {
        String recoverySubmitExecution = service.alert(tenantId, LogginAuthentcation.getInstance().getUserName(), alertRequest);
        SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource(environment.getProperty(COMMON_PROCESS_COMPLETED), recoverySubmitExecution);
        return new ResponseEntity<>(responseMessage, HttpStatus.CREATED);
    }
}