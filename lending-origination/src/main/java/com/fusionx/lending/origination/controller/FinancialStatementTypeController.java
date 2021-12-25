package com.fusionx.lending.origination.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
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

import com.fusionx.lending.origination.base.MessagePropertyBase;
import com.fusionx.lending.origination.core.LoggerRequest;
import com.fusionx.lending.origination.core.LogginAuthentcation;
import com.fusionx.lending.origination.domain.FinancialStatementType;
import com.fusionx.lending.origination.exception.UserNotFound;
import com.fusionx.lending.origination.resource.StatementTypeAddResource;
import com.fusionx.lending.origination.resource.StatementTypeUpdateResource;
import com.fusionx.lending.origination.resource.SuccessAndErrorDetailsResource;
import com.fusionx.lending.origination.service.FinancialStatementTypeService;

/**
 * Statement Type Service.
 * 
 ********************************************************************************************************
 *  ###   Date         	Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   09-AUG-2021   FXL-357	      FXL-427    Sanatha      Initial Development.
 *    
 ********************************************************************************************************
 */
@RestController
@RequestMapping(value = "/financial-statement-type")
@CrossOrigin(origins = "*")
public class FinancialStatementTypeController extends MessagePropertyBase {
	
	private final Environment environment;

    private final FinancialStatementTypeService statementTypeService;

    //private static final String recordNotFound = "common.record-not-found";
    //private static final String userNotFound = "common.user-not-found";
    //private static final String commonSaved = "common.saved";
    //private static final String commonUpdated = "common.updated";
    //private static final String pageableLength = "common.pageable-length";

    @Autowired
    public FinancialStatementTypeController(Environment environment, FinancialStatementTypeService statementTypeService) {
        this.environment = environment;
        this.statementTypeService = statementTypeService;
    }
    
    /**
	 * @author Sanatha
	 * @since 09-AUG-2021
	 * @apiNote find all StatementType
	 * @return list of StatementType
	 */
    @GetMapping(value = "/{tenantId}/all")
    public ResponseEntity<Object> getAllStatementType(@PathVariable(value = "tenantId") String tenantId) {
        LoggerRequest.getInstance().logInfo("Get all StatementType");

        List<FinancialStatementType> statementType = statementTypeService.findAll();
        if (!statementType.isEmpty()) {
            return new ResponseEntity<>(statementType, HttpStatus.OK);
        } else {
            SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
            responseMessage.setMessages(environment.getProperty(MessagePropertyBase.RECORD_NOT_FOUND));
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
	 * @author Sanatha
	 * @since 09-AUG-2021
	 * @apiNote find by ID StatementType
	 * @return optional dataset of StatementType
	 */
    @GetMapping(value = "/{tenantId}/{id}")
    public ResponseEntity<Object> getStatementTypeById(@PathVariable(value = "tenantId") String tenantId,
                                                         @PathVariable(value = "id") Long id) {
        LoggerRequest.getInstance().logInfo("Get StatementType by id");

        Optional<FinancialStatementType> optionalStatementType = statementTypeService.findById(id);
        if (optionalStatementType.isPresent()) {
            return new ResponseEntity<>(optionalStatementType, HttpStatus.OK);
        } else {
            SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
            responseMessage.setMessages(environment.getProperty(MessagePropertyBase.RECORD_NOT_FOUND));
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
	 * @author Sanatha
	 * @since 09-AUG-2021
	 * @apiNote find by CODE StatementType
	 * @return optional dataset of StatementType
	 */
    @GetMapping(value = "/{tenantId}/code/{code}")
    public ResponseEntity<Object> getStatementTypeByCode(@PathVariable(value = "tenantId") String tenantId,
                                                            @PathVariable(value = "code") String code) {

        Optional<FinancialStatementType> optionalStatementType = statementTypeService.findByCode(code);
        if (optionalStatementType.isPresent()) {
            return new ResponseEntity<>(optionalStatementType, HttpStatus.OK);
        } else {
            SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
            responseMessage.setMessages(environment.getProperty(MessagePropertyBase.RECORD_NOT_FOUND));
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
	 * @author Sanatha
	 * @since 09-AUG-2021
	 * @apiNote find by NAME StatementType
	 * @return list of StatementType
	 */
    @GetMapping(value = "/{tenantId}/name/{name}")
    public ResponseEntity<Object> getStatementTypeByName(@PathVariable(value = "tenantId") String tenantId,
                                                            @PathVariable(value = "name") String name) {

        List<FinancialStatementType> statementType = statementTypeService.findByName(name);
        if (!statementType.isEmpty()) {
            return new ResponseEntity<>(statementType, HttpStatus.OK);
        } else {
            SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
            responseMessage.setMessages(environment.getProperty(MessagePropertyBase.RECORD_NOT_FOUND));
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
	 * @author Sanatha
	 * @since 09-AUG-2021
	 * @apiNote find by STATUS StatementType
	 * @return list of StatementType
	 */
    @GetMapping(value = "/{tenantId}/status/{status}")
    public ResponseEntity<Object> getStatementTypeByStatus(@PathVariable(value = "tenantId") String tenantId,
                                                              @PathVariable(value = "status") String status) {

        List<FinancialStatementType> statementType = statementTypeService.findByStatus(status);
        if (!statementType.isEmpty()) {
            return new ResponseEntity<>(statementType, HttpStatus.OK);
        } else {
            SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
            responseMessage.setMessages(environment.getProperty(MessagePropertyBase.RECORD_NOT_FOUND));
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
	 * @author Sanatha
	 * @since 09-AUG-2021
	 * @apiNote add StatementType
	 * @return ID of StatementType
	 */
    @PostMapping(value = "/{tenantId}")
    public ResponseEntity<Object> addStatementType(@PathVariable(value = "tenantId") String tenantId,
                                                     @Valid @RequestBody StatementTypeAddResource statementTypeAddResource) {

        if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty()) {
            throw new UserNotFound(environment.getProperty(MessagePropertyBase.USER_NOT_FOUND));
        }

        Long id = statementTypeService.validateAndSaveStatementType(tenantId, LogginAuthentcation.getInstance().getUserName(), statementTypeAddResource);
        SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty(MessagePropertyBase.RECORD_CREATED), id.toString());
        return new ResponseEntity<>(successDetailsDto,HttpStatus.CREATED);
    }

    /**
	 * @author Sanatha
	 * @since 09-AUG-2021
	 * @apiNote update StatementType
	 * @return ID of StatementType
	 */
    @PutMapping(value = "/{tenantId}/{id}")
    public ResponseEntity<Object> updateStatementType(@PathVariable(value = "tenantId") String tenantId,
                                                        @PathVariable(value = "id") Long id,
                                                        @Valid @RequestBody StatementTypeUpdateResource statementTypeUpdateResource) {

        if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty()) {
            throw new UserNotFound(environment.getProperty(MessagePropertyBase.USER_NOT_FOUND));
        }

        if (statementTypeService.existsById(id)) {
        	statementTypeService.validateAndUpdateStatementType(tenantId, LogginAuthentcation.getInstance().getUserName(), id, statementTypeUpdateResource);
            SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty(MessagePropertyBase.RECORD_UPDATED));
            return new ResponseEntity<>(successDetailsDto, HttpStatus.OK);
        } else {
            SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty(MessagePropertyBase.RECORD_NOT_FOUND));
            return new ResponseEntity<>(successDetailsDto, HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

}
