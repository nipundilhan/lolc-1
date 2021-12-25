package com.fusionx.lending.origination.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.annotation.Validated;
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
import com.fusionx.lending.origination.domain.FinancialStatement;
import com.fusionx.lending.origination.domain.FinancialStatementDetail;
import com.fusionx.lending.origination.domain.FinancialStatementDetailNote;
import com.fusionx.lending.origination.repository.FinancialStatementDetailNoteRepository;
import com.fusionx.lending.origination.repository.FinancialStatementDetailRepository;
import com.fusionx.lending.origination.resource.FinancialStatementDetailMainResource;
import com.fusionx.lending.origination.resource.FinancialStatementDetailNoteRequest;
import com.fusionx.lending.origination.resource.FinancialStatementDetailUpdateMainResource;
import com.fusionx.lending.origination.resource.SuccessAndErrorDetailsResource;
import com.fusionx.lending.origination.service.FinancialStatementDetailService;

/**
 * API Service related to financial statement detail
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
 * 1        20-09-2021      -               FXL-818     Nipun Dilhan      Created
 * <p>
 *
 */

@RestController
@RequestMapping(value = "/financial-statement-detail")
@CrossOrigin(origins = "*")
@Validated
public class FinancialStatementDetailController   extends MessagePropertyBase {

	private FinancialStatementDetailService financeStatementDetailService;
	
    @Autowired
    public void setFinanceStatementService( FinancialStatementDetailService financeStatementDetailServic) {
        this.financeStatementDetailService = financeStatementDetailServic;
    }
    
	@Autowired
	private FinancialStatementDetailRepository financialStatementDetailRepository;
	@Autowired
	private FinancialStatementDetailNoteRepository financialStatementDetailNoteRepository;
    
    
	/**
	 * save financialStatementDetail
	 * 
	 * @param @PathVariable{tenantId}
	 * @param @RequestBody{financialStatementDetailMainResource}
	 * @return SuccessAndErrorDetailsDto
	 */
	@PostMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> addFinanceStatementDetail(
			@PathVariable(value = "tenantId") String tenantId,
			@PathVariable(value = "id") Long id,
			@Valid @RequestBody FinancialStatementDetailMainResource financialStatementDetailMainResource) {

		if (LogginAuthentcation.getInstance().getUserName() == null
				|| LogginAuthentcation.getInstance().getUserName().isEmpty()) {
			throw new UsernameNotFoundException(environment.getProperty(NOT_FOUND));
		}	

		FinancialStatement financelStatement = financeStatementDetailService.addFinancialStatementDetail(financialStatementDetailMainResource,id, LogginAuthentcation.getInstance().getUserName(),tenantId);
		SuccessAndErrorDetailsResource successAndErrorDetails = new SuccessAndErrorDetailsResource(environment.getProperty(RECORD_CREATED),Long.toString(financelStatement.getId()));
		return new ResponseEntity<>(successAndErrorDetails, HttpStatus.CREATED);
	}
	
	
	/**
	 * save financialStatementDetail
	 * 
	 * @param @PathVariable{tenantId}
	 * @param @RequestBody{financialStatementDetailAddResource}
	 * @return SuccessAndErrorDetailsDto
	 */
	@PutMapping(value = "/{tenantId}/financial-statement/{id}")
	public ResponseEntity<Object> updateFinanceStatementDetail(
			@PathVariable(value = "tenantId") String tenantId,
			@PathVariable(value = "id") Long id,
			@Valid @RequestBody FinancialStatementDetailUpdateMainResource financialStatementDetailUpdateMainResource) {

		if (LogginAuthentcation.getInstance().getUserName() == null
				|| LogginAuthentcation.getInstance().getUserName().isEmpty()) {
			throw new UsernameNotFoundException(environment.getProperty(NOT_FOUND));
		}	
		FinancialStatement financelStatement = financeStatementDetailService.updateFinancialStatementDetail(id,financialStatementDetailUpdateMainResource,tenantId, LogginAuthentcation.getInstance().getUserName());
		SuccessAndErrorDetailsResource successAndErrorDetails = new SuccessAndErrorDetailsResource(environment.getProperty(RECORD_UPDATED),Long.toString(financelStatement.getId()));
		return new ResponseEntity<>(successAndErrorDetails, HttpStatus.CREATED);
	}
	
	
	/**
	 * save financialStatementDetail
	 * 
	 * @param @PathVariable{tenantId}
	 * @param @RequestBody{financialStatementDetailAddResource}
	 * @return SuccessAndErrorDetailsDto
	 */
	@PutMapping(value = "/{tenantId}/update-note/financial-statement-detail/{id}")
	public ResponseEntity<Object> updateNotes(
			@PathVariable(value = "tenantId") String tenantId,
			@PathVariable(value = "id") Long id,
			@RequestBody List<@Valid FinancialStatementDetailNoteRequest> financialStatementDetailNoteList) {

		if (LogginAuthentcation.getInstance().getUserName() == null
				|| LogginAuthentcation.getInstance().getUserName().isEmpty()) {
			throw new UsernameNotFoundException(environment.getProperty(NOT_FOUND));
		}
		
		FinancialStatementDetail financelStatementDetail =financeStatementDetailService.updateNoteListStatementDetail(financialStatementDetailNoteList, id, LogginAuthentcation.getInstance().getUserName(), tenantId);
		SuccessAndErrorDetailsResource successAndErrorDetails = new SuccessAndErrorDetailsResource(environment.getProperty(RECORD_UPDATED),Long.toString(financelStatementDetail.getId()));
		return new ResponseEntity<>(successAndErrorDetails, HttpStatus.OK);
	}
	
	/**
	 * Gets the Facility Calculation Detail by leadId.
	 *
	 * @param tenantId - the tenant id
	 * @param leadId - the leadId
	 * @return the business type by id
	 */
	@GetMapping(value = "/{tenantId}/financial-statement/{id}")
	public ResponseEntity<Object> getDetailsByStatementId(@PathVariable(value = "tenantId") String tenantId,
			@PathVariable(value = "id", required = true) Long id) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<FinancialStatementDetail> allList = financeStatementDetailService.getDetailsByFinancialStatementId(id);
		if (!allList.isEmpty()) {
			return new ResponseEntity<>(allList, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	
	/**
	 * Gets the Facility Calculation Detail by leadId.
	 *
	 * @param tenantId - the tenant id
	 * @param leadId - the leadId
	 * @return the business type by id
	 */
	@GetMapping(value = "/{tenantId}/all")
	public ResponseEntity<Object> getAll(@PathVariable(value = "tenantId") String tenantId) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<FinancialStatementDetail> allList = financialStatementDetailRepository.findAll();
		if (!allList.isEmpty()) {
			return new ResponseEntity<>(allList, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	/**
	 * Gets the Facility Calculation Detail by leadId.
	 *
	 * @param tenantId - the tenant id
	 * @param leadId - the leadId
	 * @return the business type by id
	 */
	@GetMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> getById(@PathVariable(value = "tenantId") String tenantId,
			@PathVariable(value = "id", required = true) Long id) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<FinancialStatementDetail> financialStatementOptional = financialStatementDetailRepository.findById(id);
		if (financialStatementOptional.isPresent()) {
			return new ResponseEntity<>(financialStatementOptional.get(), HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	
	/**
	 * Gets the Facility Calculation Detail by leadId.
	 *
	 * @param tenantId - the tenant id
	 * @param leadId - the leadId
	 * @return the business type by id
	 */
	@GetMapping(value = "/{tenantId}/notes/statement-detail/{id}")
	public ResponseEntity<Object> getNotesByStatementDetailId(@PathVariable(value = "tenantId") String tenantId,
			@PathVariable(value = "id", required = true) Long id) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<FinancialStatementDetailNote> allList = financialStatementDetailNoteRepository.findAllByFinancialStatementDetailId(id);
		if (!allList.isEmpty()) {
			return new ResponseEntity<>(allList, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
}
