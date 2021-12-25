package com.fusionx.lending.origination.controller;

import com.fusionx.lending.origination.base.MessagePropertyBase;
import com.fusionx.lending.origination.core.LogginAuthentcation;
import com.fusionx.lending.origination.domain.FinancialStatementTemplate;
import com.fusionx.lending.origination.resource.FinancialStatementAddResource;
import com.fusionx.lending.origination.resource.FinancialStatementUpdateResource;
import com.fusionx.lending.origination.resource.SuccessAndErrorDetailsResource;
import com.fusionx.lending.origination.service.FinancialStatementTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * Financial Statement Level Domain
 * <p>
 * *******************************************************************************************************
 * ###   Date         Story Point   Task No    Author       Description
 * -------------------------------------------------------------------------------------------------------
 * 1   27-08-2021      	FXL-338	  FXL-655	Dilki        Created
 * <p>
 * *******************************************************************************************************
 */

@RestController
@RequestMapping(value = "/financial-statement-template")
@CrossOrigin(origins = "*")
public class FinancialStatementTemplateController extends MessagePropertyBase {

    @Autowired
    public FinancialStatementTemplateService financialStatementService;

    /**
     * get all FinancialStatement
     *
     * @param @PathVariable{tenantId}
     * @param @PathVariable{all}
     * @return List<Type>
     */
    @GetMapping(value = "/{tenantId}/all")
    public ResponseEntity<Object> getAllFinancialStatement(
            @PathVariable(value = "tenantId", required = true) String tenantId) {

        List<FinancialStatementTemplate> financialStatement = financialStatementService.getAll();
        int size = financialStatement.size();
        if (size > 0) {
            return new ResponseEntity<>(financialStatement, HttpStatus.OK);
        } else {
            SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
            responseMessage.setMessages(RECORD_NOT_FOUND);
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * get FinancialStatement by id
     *
     * @param @PathVariable{tenantId}
     * @param @PathVariable{id}
     * @return Option
     */
    @GetMapping(value = "/{tenantId}/{id}")
    public ResponseEntity<Object> getFinancialStatementById(
            @PathVariable(value = "tenantId", required = true) String tenantId,
            @PathVariable(value = "id", required = true) Long id) {

        Optional<FinancialStatementTemplate> isPresentFinancialStatement = financialStatementService.getById(id);
        if (isPresentFinancialStatement.isPresent()) {
            return new ResponseEntity<>(isPresentFinancialStatement.get(), HttpStatus.OK);
        } else {
            SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
            responseMessage.setMessages(RECORD_NOT_FOUND);
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * get FinancialStatement by code
     *
     * @param @pathVariable{tenantId}
     * @param @pathVariable           {code}
     * @return Optional
     **/
    @GetMapping(value = "/{tenantId}/code/{code}")
    public ResponseEntity<Object> getFinancialStatementByCode(
            @PathVariable(value = "tenantId", required = true) String tenantId,
            @PathVariable(value = "code", required = true) String code) {

        Optional<FinancialStatementTemplate> isPresentFinancialStatement = financialStatementService.getByCode(code);
        if (isPresentFinancialStatement.isPresent()) {
            return new ResponseEntity<>(isPresentFinancialStatement.get(), HttpStatus.OK);
        } else {
            SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
            responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * get FinancialStatement by name
     *
     * @param @pathVariable{tenantId}
     * @param @pathVariable           {name}
     * @return List
     **/
    @GetMapping(value = "/{tenantId}/name/{name}")
    public ResponseEntity<Object> getFinancialStatementByName(
            @PathVariable(value = "tenantId", required = true) String tenantId,
            @PathVariable(value = "name", required = true) String name) {

        Optional<FinancialStatementTemplate> isPresentFinancialStatement = financialStatementService.getByName(name);
        if (isPresentFinancialStatement.isPresent()) {
            return new ResponseEntity<>(isPresentFinancialStatement, HttpStatus.OK);
        } else {
            SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
            responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * get FinancialStatement by status
     *
     * @param @PathVariable{tenantId}
     * @param @PathVariable{status}
     * @return Option
     */
    @GetMapping(value = "/{tenantId}/status/{status}")
    public ResponseEntity<Object> getFinancialStatementByStatus(
            @PathVariable(value = "tenantId", required = true) String tenantId,
            @PathVariable(value = "status", required = true) String status) {

        SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();

        if (status.equals("ACTIVE") || status.equals("INACTIVE")) {
            List<FinancialStatementTemplate> isPresentFinancialStatement = financialStatementService
                    .getByStatus(status);
            int size = isPresentFinancialStatement.size();
            if (size > 0) {
                return new ResponseEntity<>(isPresentFinancialStatement, HttpStatus.OK);
            } else {
                responseMessage.setMessages(RECORD_NOT_FOUND);
                return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
            }
        } else {
            responseMessage.setMessages(environment.getProperty(COMMON_INVALID_VALUE));
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * save FinancialStatement
     *
     * @param @PathVariable{tenantId}
     * @param financialStatementAddResource
     * @return SuccessAndErrorDetailsDto
     */
    @PostMapping(value = "/{tenantId}")
    public ResponseEntity<Object> addFinancialStatement(
            @PathVariable(value = "tenantId", required = true) String tenantId,
            @Valid @RequestBody FinancialStatementAddResource financialStatementAddResource) {

        /*if (LogginAuthentcation.getInstance().getUserName() == null
                || LogginAuthentcation.getInstance().getUserName().isEmpty()) {
            throw new UsernameNotFoundException(environment.getProperty(USER_NOT_FOUND));
        }*/
        FinancialStatementTemplate financialStatement = financialStatementService.addFinancialStatement(tenantId,
                financialStatementAddResource);
        SuccessAndErrorDetailsResource successAndErrorDetails = new SuccessAndErrorDetailsResource(
                environment.getProperty(RECORD_CREATED), Long.toString(financialStatement.getId()));
        return new ResponseEntity<>(successAndErrorDetails, HttpStatus.CREATED);
    }

    /**
     * update FinancialStatement
     *
     * @param @PathVariable{tenantId}
     * @param @RequestBody{FinancialStatementUpdateResource}
     * @return SuccessAndErrorDetailsDto
     */
    @PutMapping(value = "/{tenantId}/{id}")
    public ResponseEntity<Object> updateFinancialStatement(
            @PathVariable(value = "tenantId", required = true) String tenantId,
            @PathVariable(value = "id", required = true) Long id,
            @Valid @RequestBody FinancialStatementUpdateResource commonUpdateResource) {

        /*if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty()) {
            throw new UsernameNotFoundException(environment.getProperty(USER_NOT_FOUND));
        }*/

        SuccessAndErrorDetailsResource successAndErrorDetails = new SuccessAndErrorDetailsResource();
        Optional<FinancialStatementTemplate> isPresentFinancialStatement = financialStatementService.getById(id);

        if (isPresentFinancialStatement.isPresent()) {
            FinancialStatementTemplate financialStatement = financialStatementService.updateFinancialStatement(tenantId, id, commonUpdateResource);
            successAndErrorDetails = new SuccessAndErrorDetailsResource(environment.getProperty(RECORD_UPDATED), financialStatement.getId().toString());
            return new ResponseEntity<>(successAndErrorDetails, HttpStatus.OK);
        } else {
            successAndErrorDetails.setMessages(RECORD_NOT_FOUND);
            return new ResponseEntity<>(successAndErrorDetails, HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
}
