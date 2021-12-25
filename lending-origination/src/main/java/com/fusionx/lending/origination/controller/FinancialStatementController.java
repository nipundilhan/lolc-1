package com.fusionx.lending.origination.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fusionx.lending.origination.base.MessagePropertyBase;
import com.fusionx.lending.origination.core.LogginAuthentcation;
import com.fusionx.lending.origination.repository.FinancialStatementDocumentRepository;
import com.fusionx.lending.origination.repository.FinancialStatementRepository;
import com.fusionx.lending.origination.domain.FinancialStatement;
import com.fusionx.lending.origination.domain.FinancialStatementDocument;
import com.fusionx.lending.origination.resource.DocumentRefAddResource;
import com.fusionx.lending.origination.resource.FinanceStatementAddRequest;
import com.fusionx.lending.origination.resource.FinanceStatementUpdateRequest;
import com.fusionx.lending.origination.resource.SuccessAndErrorDetailsResource;
import com.fusionx.lending.origination.service.FinanceStatementService;

/**
 * API Service related to financial statement
 *
 * @author Nipun Dilhan
 * @version 1.0.0
 * @since 1.0.0
 * <p>
 * <br/><br/>
 * <b>Change History : </b>
 * <br/><br/>
 * #        Date            Story Point     Task No     Author                  Description
 * <br/>
 * .....................................................................................................................<br/>
 * 1        16-09-2021      -               FXL-784     Nipun Dilhan      Created
 * <p>
 *
 */


@RestController
@RequestMapping(value = "/financial-statement")
@CrossOrigin(origins = "*")
public class FinancialStatementController  extends MessagePropertyBase {
	
	private FinanceStatementService financeStatementService;
	
    @Autowired
    public void setFinanceStatementService(FinanceStatementService financeStatementServic) {
        this.financeStatementService = financeStatementServic;
    }
    
	@Autowired
	private FinancialStatementRepository financialStatementRepository;
	@Autowired
	private FinancialStatementDocumentRepository financialStatementDocumentRepository;

    
	/**
	 * save financialStatement
	 * 
	 * @param @PathVariable{tenantId}
	 * @param @RequestBody{financeStatementAddRequest}
	 * @return SuccessAndErrorDetailsDto
	 */
	@PostMapping(value = "/{tenantId}")
	public ResponseEntity<Object> addFinanceStatement(
			@PathVariable(value = "tenantId") String tenantId,
			@Valid @RequestBody FinanceStatementAddRequest financeStatementAddRequest ) {

		if (LogginAuthentcation.getInstance().getUserName() == null
				|| LogginAuthentcation.getInstance().getUserName().isEmpty()) {
			throw new UsernameNotFoundException(environment.getProperty(NOT_FOUND));
		}

		SuccessAndErrorDetailsResource successAndErrorDetails = null;
		FinancialStatement financelStatement = financeStatementService.addFinancialStatement(tenantId,financeStatementAddRequest, LogginAuthentcation.getInstance().getUserName());
		successAndErrorDetails = new SuccessAndErrorDetailsResource(environment.getProperty(RECORD_CREATED),
				Long.toString(financelStatement.getId()));
		return new ResponseEntity<>(successAndErrorDetails, HttpStatus.CREATED);
	}
	
	/**
	 * save FinancialStatement
	 * 
	 * @param @PathVariable{tenantId}
	 * @param @RequestBody{financeStatementUpdateRequest}
	 * @return SuccessAndErrorDetailsDto
	 */
	@PutMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> updateFinanceStatement(
			@PathVariable(value = "tenantId") String tenantId,
			@Valid @RequestBody  FinanceStatementUpdateRequest financeStatementUpdateRequest,
			@PathVariable(value = "id") Long id) {

		if (LogginAuthentcation.getInstance().getUserName() == null
				|| LogginAuthentcation.getInstance().getUserName().isEmpty()) {
			throw new UsernameNotFoundException(environment.getProperty(NOT_FOUND));
		}

		SuccessAndErrorDetailsResource successAndErrorDetails = null;
		FinancialStatement financelStatement = financeStatementService.updateFinancialStatement(id,tenantId,financeStatementUpdateRequest, LogginAuthentcation.getInstance().getUserName());
		successAndErrorDetails = new SuccessAndErrorDetailsResource(environment.getProperty(RECORD_UPDATED),
				Long.toString(financelStatement.getId()));
		return new ResponseEntity<>(successAndErrorDetails, HttpStatus.CREATED);
	}
	
	
	/**
	 * save FinancialStatement documents
	 * 
	 * @param @PathVariable{tenantId}
	 * @param @RequestBody{docList}
	 * @return SuccessAndErrorDetailsDto
	 */
	@PutMapping(value = "/{tenantId}/documents/fincancial-statement/{id}")
	public ResponseEntity<Object> updateFinanceStatementDocuments(
			@PathVariable(value = "tenantId") String tenantId,
			@Valid @RequestBody  List<DocumentRefAddResource> docList,
			@PathVariable(value = "id") Long id) {

		if (LogginAuthentcation.getInstance().getUserName() == null
				|| LogginAuthentcation.getInstance().getUserName().isEmpty()) {
			throw new UsernameNotFoundException(environment.getProperty(NOT_FOUND));
		}

		SuccessAndErrorDetailsResource successAndErrorDetails = null;
		FinancialStatement financelStatement = financeStatementService.updateDocuments(docList,id,LogginAuthentcation.getInstance().getUserName(),tenantId);successAndErrorDetails = new SuccessAndErrorDetailsResource(environment.getProperty(RECORD_UPDATED),
				Long.toString(financelStatement.getId()));
		return new ResponseEntity<>(successAndErrorDetails, HttpStatus.CREATED);
	}
	
	
    /**
     * Gets all financial statement
     *
     * @param tenantId the id of tenant
     * @return the list of all financial statement
     */
	@GetMapping(value = "/{tenantId}/all")
	public ResponseEntity<Object> findByAll(@PathVariable(value = "tenantId") String tenantId) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<FinancialStatement> allList = financialStatementRepository.findAll();
		if (!allList.isEmpty()) {
			return new ResponseEntity<>(allList, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	/**
	 * Gets the Finacails statements by id.
	 *
	 * @param tenantId - the tenant id
	 * @param id - the financial statement id
	 * @return the financial statement by id
	 */
	@GetMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> findById(@PathVariable(value = "tenantId") String tenantId,
			@PathVariable(value = "id", required = true) Long id) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<FinancialStatement> financialStatementOptional = financialStatementRepository.findById(id);
		if (financialStatementOptional.isPresent()) {
			return new ResponseEntity<>(financialStatementOptional.get(), HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

	
	/**
	 * Gets the Finacail statements by customer id.
	 *
	 * @param tenantId - the tenant id
	 * @param id - the customer id
	 * @return the list of all financial statement
	 */
	@GetMapping(value = "/{tenantId}/customer/{id}")
	public ResponseEntity<Object> findByCustomerId(@PathVariable(value = "tenantId") String tenantId,
			@PathVariable(value = "id", required = true) Long id) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<FinancialStatement> allList  = financialStatementRepository.findAllByCustomerId(id);
		if (!allList.isEmpty()) {
			return new ResponseEntity<>(allList, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Gets the documents by financial statement id.
	 *
	 * @param tenantId - the tenant id
	 * @param id - the financial statement id
	 * @return the list of all financial statement documents
	 */
	@GetMapping(value = "/{tenantId}/documents/financial-statement/{id}")
	public ResponseEntity<Object> findDocumentsByFinancialId(@PathVariable(value = "tenantId") String tenantId,
			@PathVariable(value = "id", required = true) Long id) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<FinancialStatementDocument> allList = financialStatementDocumentRepository.findAllByFinancialStatementId(id);
		if (!allList.isEmpty()) {
			return new ResponseEntity<>(allList, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
}
