package com.fusionx.lending.origination.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fusionx.lending.origination.core.LogginAuthentcation;
import com.fusionx.lending.origination.exception.UserNotFound;
import com.fusionx.lending.origination.resource.StatementTemplateAddResource;
import com.fusionx.lending.origination.resource.SuccessAndErrorDetailsResource;
import com.fusionx.lending.origination.service.StatementTemplateService;

/**
 * Statement Template Service.
 * 
 ********************************************************************************************************
 *  ###   Date         	Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   09-AUG-2021   FXL-473	      FXL-426    Sanatha      Initial Development.
 *    
 ********************************************************************************************************
 */
@RestController
@RequestMapping(value = "/statement-template")
@CrossOrigin(origins = "*")
public class StatementTemplateController {
	
	private final Environment environment;

    private final StatementTemplateService statementTemplateService;

    private static final String recordNotFound = "common.record-not-found";
    private static final String userNotFound = "common.user-not-found";
    private static final String commonSaved = "common.saved";
    private static final String commonUpdated = "common.updated";
    private static final String pageableLength = "common.pageable-length";

    @Autowired
    public StatementTemplateController(Environment environment, StatementTemplateService statementTemplateService) {
        this.environment = environment;
        this.statementTemplateService = statementTemplateService;
    }
    
    /**
 	 * @author Sanatha
 	 * @since 09-AUG-2021
 	 * @apiNote add StatementTemplate
 	 * @return ID of StatementTemplate
 	 */
     @PostMapping(value = "/{tenantId}")
     public ResponseEntity<Object> addStatementTemplate(@PathVariable(value = "tenantId") String tenantId,
                                                      @Valid @RequestBody StatementTemplateAddResource statementTemplateAddResource) {

         if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty()) {
             throw new UserNotFound(environment.getProperty(userNotFound));
         }

         Long id = statementTemplateService.validateAndSaveStatementTemplate(tenantId, LogginAuthentcation.getInstance().getUserName(), statementTemplateAddResource);
         SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty(commonSaved), id.toString());
         return new ResponseEntity<>(successDetailsDto,HttpStatus.CREATED);
     }

}
