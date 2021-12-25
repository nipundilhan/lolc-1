package com.fusionx.lending.origination.controller;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.fusionx.lending.origination.domain.ExpenseTypeHouseholdExpenseCategories;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.resource.ExpenseTypeHouseholdExpenseCategoriesAddResource;
import com.fusionx.lending.origination.resource.ExpenseTypeHouseholdExpenseCategoriesUpdateResource;
import com.fusionx.lending.origination.resource.SuccessAndErrorDetailsResource;
import com.fusionx.lending.origination.service.ExpenseTypeHouseholdExpenseCategoriesService;

@RestController
@RequestMapping(value = "/expense-type-household-expense-categories")
@CrossOrigin(origins = "*")
public class ExpenseTypeHouseholdExpenseCategoriesController extends MessagePropertyBase {
	
	@Autowired
	private ExpenseTypeHouseholdExpenseCategoriesService expenseTypeHouseholdExpenseCategoriesService;
	
	
	@GetMapping("/{tenantId}/all")
	public ResponseEntity<Object> getAllExpenseTypeHouseholdExpenseCategories(@PathVariable(value = "tenantId", required = true) String tenantId){
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<ExpenseTypeHouseholdExpenseCategories> isPresentExpenseTypeHouseholdExpenseCategories = expenseTypeHouseholdExpenseCategoriesService.getAll();
		if(!isPresentExpenseTypeHouseholdExpenseCategories.isEmpty()) {
			return new ResponseEntity<>((Collection<ExpenseTypeHouseholdExpenseCategories>)isPresentExpenseTypeHouseholdExpenseCategories,HttpStatus.OK);
		}
		else {
			responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	@GetMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> getExpenseTypeHouseholdExpenseCategoriesById(@PathVariable(value = "tenantId", required = true) String tenantId,
													         	  @PathVariable(value = "id", required = true) Long id){
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<ExpenseTypeHouseholdExpenseCategories> isPresentExpenseTypeHouseholdExpenseCategories = expenseTypeHouseholdExpenseCategoriesService.getById(id);
		if(isPresentExpenseTypeHouseholdExpenseCategories.isPresent()) {
			return new ResponseEntity<>(isPresentExpenseTypeHouseholdExpenseCategories.get(), HttpStatus.OK);
		}
		else {
			responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	

	
	
	@GetMapping(value = "/{tenantId}/status/{status}")
	public ResponseEntity<Object> getExpenseTypeHouseholdExpenseCategoriesByStatus(@PathVariable(value = "tenantId", required = true) String tenantId,
													   			      @PathVariable(value = "status", required = true) String status){
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		if(status.equals(CommonStatus.ACTIVE.toString()) || status.equals(CommonStatus.INACTIVE.toString())) {
			List<ExpenseTypeHouseholdExpenseCategories> isPresentExpenseTypeHouseholdExpenseCategories = expenseTypeHouseholdExpenseCategoriesService.getByStatus(status);
			if(!isPresentExpenseTypeHouseholdExpenseCategories.isEmpty()) {
				return new ResponseEntity<>(isPresentExpenseTypeHouseholdExpenseCategories, HttpStatus.OK);
			}
			else {
				responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
				return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
			}
		}
		else {
			responseMessage.setMessages(RECORD_NOT_FOUND);
	        throw new ValidateRecordException(environment.getProperty("common-status.pattern"), "message");
		}
	}
	
	
	@PostMapping("/{tenantId}")
	public ResponseEntity<Object> addExpenseTypeHouseholdExpenseCategories(@PathVariable(value = "tenantId", required = true) String tenantId,
			   									       		  @Valid @RequestBody ExpenseTypeHouseholdExpenseCategoriesAddResource expenseTypeHouseholdExpenseCategoriesAddResource){
		expenseTypeHouseholdExpenseCategoriesService.addExpenseTypeHouseholdExpenseCategories(tenantId, expenseTypeHouseholdExpenseCategoriesAddResource);
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource(getEnvironment().getProperty(RECORD_CREATED));
		return new ResponseEntity<>(responseMessage,HttpStatus.CREATED);
	}
	
	
	@PutMapping(value = "{tenantId}")
	public ResponseEntity<Object> updateExpenseTypeHouseholdExpenseCategories(@PathVariable(value = "tenantId", required = true) String tenantId, 
												                 @Valid @RequestBody ExpenseTypeHouseholdExpenseCategoriesUpdateResource expenseTypeHouseholdExpenseCategoriesUpdateResource){
		SuccessAndErrorDetailsResource successAndErrorDetailsResource=new SuccessAndErrorDetailsResource();
		//Optional<ExpenseTypeHouseholdExpenseCategories>isPresentExpenseTypeHouseholdExpenseCategories = expenseTypeHouseholdExpenseCategoriesService.getById(id);		
//		if(isPresentExpenseTypeHouseholdExpenseCategories.isPresent()) {
//			ExpenseTypeHouseholdExpenseCategories expenseTypeHouseholdExpenseCategories = expenseTypeHouseholdExpenseCategoriesService.updateExpenseTypeHouseholdExpenseCategories(tenantId, id, expenseTypeHouseholdExpenseCategoriesUpdateResource);
//			successAndErrorDetailsResource = new SuccessAndErrorDetailsResource(getEnvironment().getProperty(RECORD_UPDATED), expenseTypeHouseholdExpenseCategories.getId().toString());
//			return new ResponseEntity<>(successAndErrorDetailsResource,HttpStatus.OK);
//		}
//		else {
//			successAndErrorDetailsResource.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
//			return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.UNPROCESSABLE_ENTITY);
//		}
		
		expenseTypeHouseholdExpenseCategoriesService.updateExpenseTypeHouseholdExpenseCategories(tenantId, expenseTypeHouseholdExpenseCategoriesUpdateResource);
		successAndErrorDetailsResource = new SuccessAndErrorDetailsResource(getEnvironment().getProperty(RECORD_UPDATED));		
		return new ResponseEntity<>(successAndErrorDetailsResource,HttpStatus.OK);
	}

}
