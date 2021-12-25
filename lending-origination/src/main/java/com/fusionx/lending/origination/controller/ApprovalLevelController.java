package com.fusionx.lending.origination.controller;

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
import com.fusionx.lending.origination.domain.ApprovalLevel;
import com.fusionx.lending.origination.resource.ApprovalLevelAddResource;
import com.fusionx.lending.origination.resource.ApprovalLevelUpdateResource;
import com.fusionx.lending.origination.resource.SuccessAndErrorDetailsResource;
import com.fusionx.lending.origination.service.ApprovalLevelService;

import io.micrometer.core.annotation.Timed;

@RestController
@RequestMapping(value = "/approval-level")
@CrossOrigin(origins = "*")
@Timed
public class ApprovalLevelController extends MessagePropertyBase{
	
	@Autowired
	private ApprovalLevelService approvalLevelService;
	
	
	
	@GetMapping("/{tenantId}/all")
	public ResponseEntity<Object> getAllApprovalLevel(@PathVariable(value = "tenantId", required = true) String tenantId){
		List<ApprovalLevel> list = approvalLevelService.findAll();
	      if(!list.isEmpty()) 
          return new ResponseEntity<>(list, HttpStatus.OK);
      else {
          return new ResponseEntity<>(list, HttpStatus.NO_CONTENT);
      }
	}
	
	
	
	@GetMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> getApprovalLevelById(@PathVariable(value = "tenantId", required = true) String tenantId,
													         	  @PathVariable(value = "id", required = true) Long id){
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		
		Optional<ApprovalLevel> isPresentApprovalLevel = approvalLevelService.findById(id);
		if(isPresentApprovalLevel.isPresent()) {
			return new ResponseEntity<>(isPresentApprovalLevel.get(), HttpStatus.OK);
		}
		else {
			responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	@GetMapping(value = "/{tenantId}/code/{code}")
	public ResponseEntity<Object> getApprovalLevelByCode(@PathVariable(value = "tenantId", required = true) String tenantId,
													   	            @PathVariable(value = "code", required = true) String code){
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		
		Optional<ApprovalLevel> isPresentApprovalLevel = approvalLevelService.findByCode(code);
		if(isPresentApprovalLevel.isPresent()) {
			return new ResponseEntity<>(isPresentApprovalLevel.get(), HttpStatus.OK);
		}
		else {
			responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	@GetMapping(value = "/{tenantId}/name/{name}")
	public ResponseEntity<Object> getApprovalLevelByName(@PathVariable(value = "tenantId", required = true) String tenantId,
															        @PathVariable(value = "name", required = true) String name){
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		
		List<ApprovalLevel> isPresentApprovalLevel = approvalLevelService.findByName(name);
		if(!isPresentApprovalLevel.isEmpty()) {
			return new ResponseEntity<>(isPresentApprovalLevel, HttpStatus.OK);
		}
		else {
			responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	@GetMapping(value = "/{tenantId}/status/{status}")
	public ResponseEntity<Object> getApprovalLevelByStatus(@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "status", required = true) String status) {

		List<ApprovalLevel> list = approvalLevelService.findByStatus(status);
		if (!list.isEmpty())
			return new ResponseEntity<>(list, HttpStatus.OK);
		else {
			return new ResponseEntity<>(list, HttpStatus.NO_CONTENT);
		}
	}
	
	
	@PostMapping("/{tenantId}")
	public ResponseEntity<Object> addApprovalLevels(@PathVariable(value = "tenantId", required = true) String tenantId,
			   									       		  @Valid @RequestBody ApprovalLevelAddResource approvalLevelAddResource){
		ApprovalLevel approvalLevel = approvalLevelService.save(tenantId, approvalLevelAddResource);
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource(getEnvironment().getProperty(RECORD_CREATED), Long.toString(approvalLevel.getId()));
		return new ResponseEntity<>(responseMessage,HttpStatus.CREATED);
	}
	
	
	@PutMapping(value = "{tenantId}/{id}")
	public ResponseEntity<Object> updateApprovalLevels(@PathVariable(value = "tenantId", required = true) String tenantId, 
												                 @PathVariable(value = "id", required = true) Long id, 
												                 @Valid @RequestBody ApprovalLevelUpdateResource approvalLevelUpdateResource){
		SuccessAndErrorDetailsResource successAndErrorDetailsResource=new SuccessAndErrorDetailsResource();
		Optional<ApprovalLevel>isPresentApprovalLevel = approvalLevelService.findById(id);		
		if(isPresentApprovalLevel.isPresent()) {
			approvalLevelUpdateResource.setId(id.toString());
			ApprovalLevel approvalLevel = approvalLevelService.update(tenantId, approvalLevelUpdateResource);
			successAndErrorDetailsResource = new SuccessAndErrorDetailsResource(getEnvironment().getProperty(RECORD_UPDATED), approvalLevel.getId().toString());
			return new ResponseEntity<>(successAndErrorDetailsResource,HttpStatus.OK);
		}
		else {
			successAndErrorDetailsResource.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

}
