package com.fusionx.lending.transaction.controller;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
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

import com.fusionx.lending.transaction.core.LogginAuthentcation;
import com.fusionx.lending.transaction.domain.TransEventAccStatus;
import com.fusionx.lending.transaction.domain.TransEventSubCode;
import com.fusionx.lending.transaction.domain.TransactionEvent;
import com.fusionx.lending.transaction.exception.UserNotFound;
import com.fusionx.lending.transaction.resource.AddTransEventAccStatus;
import com.fusionx.lending.transaction.resource.AddTransEventSubCode;
import com.fusionx.lending.transaction.resource.SuccessAndErrorDetailsResource;
import com.fusionx.lending.transaction.resource.TransSubCodeResponse;
import com.fusionx.lending.transaction.resource.TransactionEventAddResource;
import com.fusionx.lending.transaction.resource.TransactionEventUpdateResource;
import com.fusionx.lending.transaction.resource.UpdateTransEventAccStatus;
import com.fusionx.lending.transaction.resource.UpdateTransEventSubCode;
import com.fusionx.lending.transaction.service.TransactionEventService;
import com.querydsl.core.types.Predicate;

@RestController
@RequestMapping(value = "/transaction-event")
@CrossOrigin(origins = "*")
public class TransactionEventController {
	

	@Autowired
	private Environment environment;
	
	private String commonRecordNotAvailable="common.record-not-available";
	private String userNotFound="common.user-not-found";
	
	@Autowired
	private TransactionEventService transactionService;
	
	String pageableLength="common.pageable-length";
	
	@GetMapping(value = "/{tenantId}/all")
    public List<?> getAllProduct(@PathVariable(value = "tenantId", required = true) String tenantId
    		//@PageableDefault(size = 10) Pageable pageable,
    		//@QuerydslPredicate(root = TransactionEvent.class) Predicate predicate
            ) {
    	
    	return  transactionService.findAll();
		
	}
	
	
	/*@GetMapping(value = "/{tenantId}/all/{start}/{length}")
	public ResponseEntity<Object> getAllTransactions(@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "start", required = true) Long start, @PathVariable(value = "length", required = true) Long length) {
		
		if(length>Constants.MAX_PAGEABLE_LENGTH) 
			throw new PageableLengthException(environment.getProperty(pageableLength));
		Pageable pageable = PageRequest.of(start.intValue(), length.intValue());
		SuccessAndErrorDetailsResource successAndErrorDetailsResource = new SuccessAndErrorDetailsResource();
		List<TransactionEvent> transactionResponse=transactionService.getAllTransactionEvents(pageable);
		if (transactionResponse!=null) {
			return new ResponseEntity<>(transactionResponse, HttpStatus.OK);
		} else {
			successAndErrorDetailsResource.setMessages(environment.getProperty(commonRecordNotAvailable));
			return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.NO_CONTENT);
		}
	}*/
	
 	@GetMapping(value = "/{tenantId}/{id}")
 	public ResponseEntity<Object> getTransactionEvents(@PathVariable(value = "tenantId", required = true) String tenantId, 
 			                                                 @PathVariable(value = "id", required = true) Long id) {
 		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
	 	Optional<TransactionEvent> transactionEvent = transactionService.getTransactionEvents(id) ;
	 	if (transactionEvent.isPresent()) {
	 		return new ResponseEntity<>(transactionEvent.get(), HttpStatus.OK);
	 	} 
	 	else {
	 		responseMessage.setMessages(commonRecordNotAvailable);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
	 	}
 	}
 	
	@PostMapping(value = "/{tenantId}/subCode")
 	public ResponseEntity<Object> add(@PathVariable(value = "tenantId", required = true) String tenantId,
 			                                             @Valid @RequestBody AddTransEventSubCode addTransEventSubCode) {
			if(LogginAuthentcation.getInstance().getUserName()==null || LogginAuthentcation.getInstance().getUserName().isEmpty()) { 
	 			throw new UserNotFound(environment.getProperty(userNotFound));
	 		}
			addTransEventSubCode.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
		
			transactionService.save(tenantId, addTransEventSubCode);
	 		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource(environment.getProperty("common.saved"));
	     	return new ResponseEntity<>(responseMessage,HttpStatus.CREATED);
 	}
	
	/*@GetMapping(value = "/{tenantId}/subCode/all/{start}/{length}")
	public ResponseEntity<Object> getAllTransactionSubCode(@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "start", required = true) Long start, @PathVariable(value = "length", required = true) Long length) {
		
		if(length>Constants.MAX_PAGEABLE_LENGTH) 
			throw new PageableLengthException(environment.getProperty(pageableLength));
		Pageable pageable = PageRequest.of(start.intValue(), length.intValue());
		SuccessAndErrorDetailsResource successAndErrorDetailsResource = new SuccessAndErrorDetailsResource();
		List<TransEventSubCode> transactionResponse=transactionService.getAllTransEventSubCode(pageable);
		if (transactionResponse!=null) {
			return new ResponseEntity<>(transactionResponse, HttpStatus.OK);
		} else {
			successAndErrorDetailsResource.setMessages(environment.getProperty(commonRecordNotAvailable));
			return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.NO_CONTENT);
		}
	}*/
	
	@GetMapping(value = "/{tenantId}/subCode/all")
    public List<?> getAllTransactionSubCode(@PathVariable(value = "tenantId", required = true) String tenantId 
    		//@PageableDefault(size = 10) Pageable pageable,
    		//@QuerydslPredicate(root = TransEventSubCode.class) Predicate predicate
            ) {
    	
    	return  transactionService.findAllSubCode();
		
	}
	@GetMapping(value = "/{tenantId}/status/{status}")
 	public ResponseEntity<Object> getTransEventByStatus(@PathVariable(value = "tenantId", required = true) String tenantId, 
 			                                                 @PathVariable(value = "status", required = true) String status) {
 		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
 		List<TransactionEvent> transactionEvent = transactionService.getTransEventByStatus(status) ;
	 	if (!transactionEvent.isEmpty()) {
	 		return new ResponseEntity<>(transactionEvent, HttpStatus.OK);
	 	} 
	 	else {
	 		responseMessage.setMessages(commonRecordNotAvailable);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
	 	}
 	}
	
	@GetMapping(value = "/{tenantId}/description/{description}")
 	public ResponseEntity<Object> getTransEventByDescription(@PathVariable(value = "tenantId", required = true) String tenantId, 
 			                                                 @PathVariable(value = "description", required = true) String description) {
 		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
 		List<TransactionEvent> transactionEvent = transactionService.getTransEventByDescription(description) ;
	 	if (!transactionEvent.isEmpty()) {
	 		return new ResponseEntity<>(transactionEvent, HttpStatus.OK);
	 	} 
	 	else {
	 		responseMessage.setMessages(commonRecordNotAvailable);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
	 	}
 	}
	
	@GetMapping(value = "/{tenantId}/code/{code}")
 	public ResponseEntity<Object> getTransactionEventByCode(@PathVariable(value = "tenantId", required = true) String tenantId, 
 			                                                 @PathVariable(value = "code", required = true) String code) {
 		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
	 	Optional<TransactionEvent> transactionEvent = transactionService.getTransEventByCode(code) ;
	 	if (transactionEvent.isPresent()) {
	 		return new ResponseEntity<>(transactionEvent.get(), HttpStatus.OK);
	 	} 
	 	else {
	 		responseMessage.setMessages(commonRecordNotAvailable);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
	 	}
 	}
	
	@GetMapping(value = "/{tenantId}/subCode/status/{status}")
 	public ResponseEntity<Object> getTransEventSubCodeByStatus(@PathVariable(value = "tenantId", required = true) String tenantId, 
 			                                                 @PathVariable(value = "status", required = true) String status) {
 		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
 		try {
 			
 		List<TransSubCodeResponse> transactionEvent = transactionService.getTransEventSubCodeByStatus(status);
	 	if (!transactionEvent.isEmpty()) {
	 		return new ResponseEntity<>(transactionEvent, HttpStatus.OK);
	 	} 
	 	else {
	 		responseMessage.setMessages(commonRecordNotAvailable);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
	 	}
 		}catch (NoSuchElementException e) {
 			responseMessage.setMessages(commonRecordNotAvailable);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
 	}
	
	@GetMapping(value = "/{tenantId}/subCode/eventCode/{eventCode}")
 	public ResponseEntity<Object> getTransEventSubCodeByeventCode(@PathVariable(value = "tenantId", required = true) String tenantId, 
 			                                                 @PathVariable(value = "eventCode", required = true) String eventCode) {
 		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
 		try {
 			
	 		TransSubCodeResponse transactionEvent = transactionService.getTransEventSubCodeByeventCode(eventCode);
		 	if (transactionEvent!=null) {
		 		return new ResponseEntity<>(transactionEvent, HttpStatus.OK);
		 	} else {
		 		responseMessage.setMessages(commonRecordNotAvailable);
				return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		 	}
 		} catch (NoSuchElementException e) {
 			responseMessage.setMessages(commonRecordNotAvailable);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
 	}
	
	@GetMapping(value = "/{tenantId}/accStatus/status/{status}")
 	public ResponseEntity<Object> getTransEventAccStatusByStatus(@PathVariable(value = "tenantId", required = true) String tenantId, 
 			                                                 @PathVariable(value = "status", required = true) String status) {
 		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
 		List<TransEventAccStatus> transactionEvent = transactionService.getTransEventAccStatusByStatus(status);
	 	if (!transactionEvent.isEmpty()) {
	 		return new ResponseEntity<>(transactionEvent, HttpStatus.OK);
	 	} 
	 	else {
	 		responseMessage.setMessages(commonRecordNotAvailable);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
	 	}
 	}
	
	@GetMapping(value = "/{tenantId}/accStatus/eventCode/{eventCode}")
 	public ResponseEntity<Object> getTransEventAccStatusByeventCode(@PathVariable(value = "tenantId", required = true) String tenantId, 
 			                                                 @PathVariable(value = "eventCode", required = true) String eventCode) {
 		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
 		List<TransEventAccStatus> transactionEvent = transactionService.getTransEventAccStatusByEventCode(eventCode);
	 	if (!transactionEvent.isEmpty()) {
	 		return new ResponseEntity<>(transactionEvent, HttpStatus.OK);
	 	} 
	 	else {
	 		responseMessage.setMessages(commonRecordNotAvailable);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
	 	}
 	}
 	@GetMapping(value = "/{tenantId}/subCode/{id}")
 	public ResponseEntity<Object> getTransactionEventsubCode(@PathVariable(value = "tenantId", required = true) String tenantId, 
 			                                                 @PathVariable(value = "id", required = true) Long id) {
 		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
 		Optional<TransEventSubCode> isPresentBankTransactionCode = transactionService.findTransEventSubCode(id);
		if(isPresentBankTransactionCode.isPresent()) {

	 		//TransEventSubCode transactionEvent = transactionService.getTransEventSubCode(id) ;
	 		return new ResponseEntity<>(isPresentBankTransactionCode.get(), HttpStatus.OK);
	 	} 
	 	else {
	 		responseMessage.setMessages(commonRecordNotAvailable);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
	 	}
 	}
 	
 	/**
 	 * @author Senitha
 	 * @date 26-NOV-2020
 	 * */
 	@GetMapping(value = "/{tenantId}/event-code/subCode/{transEventCode}")
 	public ResponseEntity<Object> transEventSubCodeByEventCode(@PathVariable(value = "tenantId", required = true) String tenantId, 
 			                                                   @PathVariable(value = "transEventCode", required = true) String transEventCode) {
 		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
 		Optional<TransEventSubCode> isPresentTransEventSubCode = transactionService.findTransEventSubCodeByEventCode(transEventCode);
		if(isPresentTransEventSubCode.isPresent()) {
			return new ResponseEntity<>(isPresentTransEventSubCode.get(), HttpStatus.OK);
		}
		else {
			responseMessage.setMessages(commonRecordNotAvailable);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
 	}
 	
 	@PutMapping(value = "/{tenantId}/subCode/{id}")
	public ResponseEntity<Object> updateBankTransactionCode(@PathVariable(value = "tenantId", required = true) String tenantId, 
			                                                @PathVariable(value = "id", required = true) Long id, 
			                                                @Valid @RequestBody UpdateTransEventSubCode updateTransEventSubCode) {
 		if(LogginAuthentcation.getInstance().getUserName()==null || LogginAuthentcation.getInstance().getUserName().isEmpty()) { 
 			throw new UserNotFound(environment.getProperty(userNotFound));
 		}
		updateTransEventSubCode.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
		
		SuccessAndErrorDetailsResource successAndErrorDetailsResource=new SuccessAndErrorDetailsResource();
		Optional<TransEventSubCode> isPresentBankTransactionCode = transactionService.findTransEventSubCode(id);
		if(isPresentBankTransactionCode.isPresent()) {
			//bankTransactionCodeUpdateResource.setId(transCodeId.toString());
			//bankTransactionCodeUpdateResource.setTenantId(tenantId);
			transactionService.update(tenantId, id,updateTransEventSubCode);
			successAndErrorDetailsResource = new SuccessAndErrorDetailsResource(environment.getProperty("common.updated"));
        	return new ResponseEntity<>(successAndErrorDetailsResource,HttpStatus.OK);
		}
		else {
			successAndErrorDetailsResource.setMessages(commonRecordNotAvailable);
			return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
 	@PostMapping(value = "/{tenantId}/accStatus")
 	public ResponseEntity<Object> addAccStatus(@PathVariable(value = "tenantId", required = true) String tenantId,
 			                                             @Valid @RequestBody AddTransEventAccStatus addTransEventAccStatus) {
	 		if(LogginAuthentcation.getInstance().getUserName()==null || LogginAuthentcation.getInstance().getUserName().isEmpty()) { 
	 			throw new UserNotFound(environment.getProperty(userNotFound));
	 		}
			addTransEventAccStatus.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
		
			transactionService.saveAccStatus(tenantId, addTransEventAccStatus);
	 		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource(environment.getProperty("common.saved"));
	     	return new ResponseEntity<>(responseMessage,HttpStatus.CREATED);
 	}
 	
	
	/*@GetMapping(value = "/{tenantId}/accStatus/all/{start}/{length}")
	public ResponseEntity<Object> getAllTransactionaccStatus(@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "start", required = true) Long start, @PathVariable(value = "length", required = true) Long length) {
		
		if(length>Constants.MAX_PAGEABLE_LENGTH) 
			throw new PageableLengthException(environment.getProperty(pageableLength));
		Pageable pageable = PageRequest.of(start.intValue(), length.intValue());
		SuccessAndErrorDetailsResource successAndErrorDetailsResource = new SuccessAndErrorDetailsResource();
		List<TransEventAccStatus> transactionResponse=transactionService.getAllTransEventAccStatus( pageable);
		if (transactionResponse!=null) {
			return new ResponseEntity<>(transactionResponse, HttpStatus.OK);
		} else {
			successAndErrorDetailsResource.setMessages(environment.getProperty(commonRecordNotAvailable));
			return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.NO_CONTENT);
		}
	}*/
 	
 	@GetMapping(value = "/{tenantId}/accStatus/all")
    public List<?> getAllTransactionaccStatus(@PathVariable(value = "tenantId", required = true) String tenantId 
    		//@PageableDefault(size = 10) Pageable pageable,
    		//@QuerydslPredicate(root = TransEventAccStatus.class) Predicate predicate
            ) {
    	
    	return  transactionService.findAllAccStatus();
		
	}
	
 	@GetMapping(value = "/{tenantId}/accStatus/{id}")
 	public ResponseEntity<Object> getTransactionEventaccStatus(@PathVariable(value = "tenantId", required = true) String tenantId, 
 			                                                 @PathVariable(value = "id", required = true) Long id) {
 		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
 		Optional<TransEventAccStatus> transactionEvent = transactionService.getTransEventAccStatus(id) ;
	 	if (transactionEvent.isPresent()) {
	 		return new ResponseEntity<>(transactionEvent, HttpStatus.OK);
	 	} 
	 	else {
	 		responseMessage.setMessages(commonRecordNotAvailable);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
	 	}
 	}
 	
 	@PutMapping(value = "/{tenantId}/accStatus/{id}")
	public ResponseEntity<Object> updateaccStatus(@PathVariable(value = "tenantId", required = true) String tenantId, 
			                                                @PathVariable(value = "id", required = true) Long id, 
			                                                @Valid @RequestBody UpdateTransEventAccStatus updateTransEventSubCode) {
 		if(LogginAuthentcation.getInstance().getUserName()==null || LogginAuthentcation.getInstance().getUserName().isEmpty()) { 
 			throw new UserNotFound(environment.getProperty(userNotFound));
 		}
		updateTransEventSubCode.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
		
		SuccessAndErrorDetailsResource successAndErrorDetailsResource=new SuccessAndErrorDetailsResource();
		Optional<TransEventAccStatus> isPresentBankTransactionCode = transactionService.getTransEventAccStatus(id);
		if(isPresentBankTransactionCode.isPresent()) {
			//bankTransactionCodeUpdateResource.setId(transCodeId.toString());
			//bankTransactionCodeUpdateResource.setTenantId(tenantId);
			transactionService.updateAccStatus(tenantId, id,updateTransEventSubCode);
			successAndErrorDetailsResource = new SuccessAndErrorDetailsResource(environment.getProperty("common.updated"));
        	return new ResponseEntity<>(successAndErrorDetailsResource,HttpStatus.OK);
		}
		else {
			successAndErrorDetailsResource.setMessages(commonRecordNotAvailable);
			return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
 	
 	@PostMapping(value = "/{tenantId}/transactionEvent")
 	public ResponseEntity<Object> addTransactionEvent(@PathVariable(value = "tenantId", required = true) String tenantId,
 			                                             @Valid @RequestBody TransactionEventAddResource transactionEventAddResource) {
			if(LogginAuthentcation.getInstance().getUserName()==null || LogginAuthentcation.getInstance().getUserName().isEmpty()) { 
	 			throw new UserNotFound(environment.getProperty(userNotFound));
	 		}
			
		
			transactionService.addTransactionEvent(tenantId, transactionEventAddResource);
	 		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource(environment.getProperty("common.saved"));
	     	return new ResponseEntity<>(responseMessage,HttpStatus.CREATED);
 	}
 	
 	@PutMapping(value = "/{tenantId}/transactionEvent/{id}")
 	public ResponseEntity<Object> updateTransactionEvent(@PathVariable(value = "tenantId", required = true) String tenantId,
 														 @PathVariable(value = "id", required = true) Long id,
 			                                             @Valid @RequestBody TransactionEventUpdateResource transactionEventUpdateResource) {
			if(LogginAuthentcation.getInstance().getUserName()==null || LogginAuthentcation.getInstance().getUserName().isEmpty()) { 
	 			throw new UserNotFound(environment.getProperty(userNotFound));
	 		}
			
		
			transactionService.updateTransactionEvent(tenantId, transactionEventUpdateResource, id);
	 		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource(environment.getProperty("common.saved"));
	     	return new ResponseEntity<>(responseMessage,HttpStatus.CREATED);
 	}
 	
 	
 	@GetMapping(value = "/{tenantId}/accStatus/{accStatus}/eventCode/{eventCode}")
 	public ResponseEntity<Object> getTransEventAccStatusByeventCodeandAccStatus(@PathVariable(value = "tenantId", required = true) String tenantId, 
 			@PathVariable(value = "accStatus", required = true) String accStatus,                                               
 			@PathVariable(value = "eventCode", required = true) String eventCode) {
 		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
 		Optional<TransEventAccStatus> transactionEvent = transactionService.getTransEventAccStatusByEventCodeAndAccStatus(accStatus,eventCode);
	 	if (transactionEvent.isPresent()) {
	 		return new ResponseEntity<>(transactionEvent, HttpStatus.OK);
	 	} 
	 	else {
	 		responseMessage.setMessages(commonRecordNotAvailable);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
	 	}
 	}

}
