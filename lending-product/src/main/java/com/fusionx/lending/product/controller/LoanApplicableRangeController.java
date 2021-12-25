package com.fusionx.lending.product.controller;

import java.util.Collection;
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

import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.LoanApplicableRange;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.exception.UserNotFound;
import com.fusionx.lending.product.resources.LoanApplicableRangeAddResource;
import com.fusionx.lending.product.resources.LoanApplicableRangeUpdateResource;
import com.fusionx.lending.product.resources.SuccessAndErrorDetails;
import com.fusionx.lending.product.resources.SuccessAndErrorDetailsResource;
import com.fusionx.lending.product.service.LoanApplicableRangeService;

/**
 * Application Frequency Controller
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   08-07-2021      			            Dilhan      Created
 *    
 ********************************************************************************************************
 */

@RestController
@RequestMapping("/loan-applicable-range")
@CrossOrigin("*")
public class LoanApplicableRangeController extends MessagePropertyBase{
	
	private String userNotFound="common.user-not-found";
	
	@Autowired
	private LoanApplicableRangeService loanApplicableRangeService;
	@Autowired
	Environment environment;
	
	/**
	 * get all Loan Applicable Ranges
	 * @param @PathVariable{tenantId}
	 * @param @PathVariable{all}
	 * @return List<Type>
	 */
	@GetMapping(value = "/{tenantId}/all")
    public ResponseEntity<Object> getAllLoanApplicableRanges(@PathVariable(value = "tenantId", required = true) String tenantId) {
    	SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
		List<LoanApplicableRange> loanApplicableRange = loanApplicableRangeService.getAll();
		int size = loanApplicableRange.size();
		if (size > 0) {
			return new ResponseEntity<>((Collection<LoanApplicableRange>) loanApplicableRange,HttpStatus.OK);
		} 
		else {
			responseMessage.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

	 /**
     * get Loan Applicable Range by id
     * @param @PathVariable{tenantId}
     * @param @PathVariable{id}
     * @return Option
     */
	@GetMapping(value = "/{tenantId}/{id}")
 	public ResponseEntity<Object> getLoanApplicableRangeById(@PathVariable(value = "tenantId", required = true) String tenantId, 
 			                                              @PathVariable(value = "id", required = true) Long id) {
 		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
	 	Optional<LoanApplicableRange> isPresentLoanApplicableRange = loanApplicableRangeService.getById(id);
	 	if (isPresentLoanApplicableRange.isPresent()) {
	 		return new ResponseEntity<>(isPresentLoanApplicableRange.get(), HttpStatus.OK);
	 	} 
	 	else {
	 		responseMessage.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
	 	}
 	}
	
	/**
	 * get Loan Applicable Range by code
	 * @param @pathVariable{tenantId}
	 * @param @pathVariable {code}
	 * @return Optional
	 **/
	@GetMapping(value = "/{tenantId}/code/{code}")
	public ResponseEntity<Object> getLoanApplicableRangeByCode(@PathVariable(value = "tenantId", required = true) String tenantId,
													   	            @PathVariable(value = "code", required = true) String code){
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<LoanApplicableRange> isPresentBrand = loanApplicableRangeService.getByCode(code);
		if(isPresentBrand.isPresent()) {
			return new ResponseEntity<>(isPresentBrand.get(), HttpStatus.OK);
		}
		else {
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * get Loan Applicable Range by name
	 * @param @pathVariable{tenantId}
	 * @param @pathVariable {name}
	 * @return List
	 **/
	@GetMapping(value = "/{tenantId}/name/{name}")
	public ResponseEntity<Object> getLoanApplicableRangeByName(@PathVariable(value = "tenantId", required = true) String tenantId,
															        @PathVariable(value = "name", required = true) String name){
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<LoanApplicableRange> isPresentLoanApplicableRange = loanApplicableRangeService.getByName(name);
		if(!isPresentLoanApplicableRange.isEmpty()) {
			return new ResponseEntity<>(isPresentLoanApplicableRange, HttpStatus.OK);
		}
		else {
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
     * get Loan Applicable Range by status
     * @param @PathVariable{tenantId}
     * @param @PathVariable{status}
     * @return Option
     */
 	@GetMapping(value = "/{tenantId}/status/{status}")
 	public ResponseEntity<Object> getLoanApplicableRangeByStatus(@PathVariable(value = "tenantId", required = true) String tenantId, 
 			                                                  @PathVariable(value = "status", required = true) String status) {
 		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
 		
 		if(status.equals(CommonStatus.ACTIVE.toString()) || status.equals(CommonStatus.INACTIVE.toString())){
		 	List<LoanApplicableRange> isPresentLoanApplicableRange = loanApplicableRangeService.getByStatus(status);
		 	int size = isPresentLoanApplicableRange.size();
		 	if (size>0) {
		 		return new ResponseEntity<>(isPresentLoanApplicableRange, HttpStatus.OK);
		 	} 
		 	else {
		 		responseMessage.setMessages(RECORD_NOT_FOUND);
				return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		 	}
 		}
 		else{
	 		responseMessage.setMessages(environment.getProperty("invalid-status"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
	 	}
 	}
 	
 	/**
     * save Loan Applicable Range
     * @param @PathVariable{tenantId}
     * @param @RequestBody{CommonAddResource}
     * @return SuccessAndErrorDetailsDto
     */
	@PostMapping(value = "/{tenantId}")
 	public ResponseEntity<Object> addLoanApplicableRange(@PathVariable(value = "tenantId", required = true) String tenantId,
 			                                          @Valid @RequestBody LoanApplicableRangeAddResource loanApplicableRangeResource) {
 		if(LogginAuthentcation.getInstance().getUserName()==null || LogginAuthentcation.getInstance().getUserName().isEmpty()) { 
 			throw new UserNotFound(environment.getProperty(userNotFound));
 		}
		
 		SuccessAndErrorDetails successAndErrorDetails = new SuccessAndErrorDetails();
 		LoanApplicableRange loanApplicableRange = loanApplicableRangeService.addLoanApplicableRange(tenantId, loanApplicableRangeResource);
	 	successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty("rec.saved"),Long.toString(loanApplicableRange.getId()));
		return new ResponseEntity<>(successAndErrorDetails,HttpStatus.CREATED);
 	}
	
	
	/**
     * update Loan Applicable Range
     * @param @PathVariable{tenantId}
     * @param @RequestBody{CommonUpdateResource}
     * @return SuccessAndErrorDetailsDto
     */
	@PutMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> updateLoanApplicableRange(@PathVariable(value = "tenantId", required = true) String tenantId, 
			                                             @PathVariable(value = "id", required = true) Long id, 
			                                             @Valid @RequestBody LoanApplicableRangeUpdateResource loanApplicableRangeUpdateResource) {
		if(LogginAuthentcation.getInstance().getUserName()==null || LogginAuthentcation.getInstance().getUserName().isEmpty()) { 
 			throw new UserNotFound(environment.getProperty(userNotFound));
 		}
		
		SuccessAndErrorDetails successAndErrorDetails=new SuccessAndErrorDetails();
		Optional<LoanApplicableRange> isPresentLoanApplicableRang = loanApplicableRangeService.getById(id);
		if(isPresentLoanApplicableRang.isPresent()) {
			LoanApplicableRange loanApplicableRange = loanApplicableRangeService.updateLoanApplicableRange(tenantId, id, loanApplicableRangeUpdateResource);
			successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty("rec.updated"), loanApplicableRange.getId().toString());
        	return new ResponseEntity<>(successAndErrorDetails,HttpStatus.OK);
		}
		else {
			successAndErrorDetails.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(successAndErrorDetails, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
	
	
}

