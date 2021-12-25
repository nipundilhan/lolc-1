package com.fusionx.lending.origination.controller;

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

import com.fusionx.lending.origination.core.LogginAuthentcation;
import com.fusionx.lending.origination.domain.ApprovalLevelCategoryMapping;
import com.fusionx.lending.origination.exception.UserNotFound;
import com.fusionx.lending.origination.resource.ApprovalLevelCategoryMapResource;
import com.fusionx.lending.origination.resource.SuccessAndErrorDetailsResource;
import com.fusionx.lending.origination.resource.UpdateApprovalLevelCategoryMapResource;
import com.fusionx.lending.origination.service.ApprovalLevelCategoryMapService;

@RestController
@RequestMapping(value = "/approval-level-category-map")
@CrossOrigin(origins = "*")
public class ApprovalLevelCategoryMapController {

	@Autowired
	private Environment environment;
	
	@Autowired
	private ApprovalLevelCategoryMapService approvalLevelCategoryMapService;
	
	private String userNotFound = "common.user-not-found";
	private String commonSaved = "common.saved";
	private String commonUpdated = "common.updated";
	private String pageableLength = "common.pageable-length";
	private String recordNotFound = "common.record-not-found";
	
	@GetMapping(value = "/{tenantId}/all")
	public ResponseEntity<Object> getAll(@PathVariable(value = "tenantId", required = true) String tenantId) {
		 List<ApprovalLevelCategoryMapping> list=approvalLevelCategoryMapService.findAll();
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();

		 if(list.isEmpty()) {
			 responseMessage.setMessages(environment.getProperty(recordNotFound));
				return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		 }else {
				return new ResponseEntity<>(list, HttpStatus.OK);

		 }
	}
	

	@GetMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> getApprovalCatProductMapById(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<ApprovalLevelCategoryMapping> isPresent = approvalLevelCategoryMapService.findById(id);
		if (isPresent.isPresent()) {
			return new ResponseEntity<>(isPresent, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	@GetMapping(value = "/{tenantId}/status/{status}")
	public ResponseEntity<Object> getApprovalCatProductMapsByStatus(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "status", required = true) String status) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<ApprovalLevelCategoryMapping> approval = approvalLevelCategoryMapService.findByStatus(status);
		if (!approval.isEmpty()) {
			return new ResponseEntity<>((Collection<ApprovalLevelCategoryMapping>) approval, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
		
	
	@PostMapping(value = "/{tenantId}")
	public ResponseEntity<Object> addApprovalCatProductMap(@PathVariable(value = "tenantId", required = true) String tenantId,
			@Valid @RequestBody ApprovalLevelCategoryMapResource approvalLevelCategoryMapResource) {
		if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new UserNotFound(environment.getProperty(userNotFound));
		Long id = approvalLevelCategoryMapService.save(tenantId, LogginAuthentcation.getInstance().getUserName(), approvalLevelCategoryMapResource);
		SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty(commonSaved), id.toString());
		return new ResponseEntity<>(successDetailsDto,HttpStatus.CREATED);
	}
	@PutMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> updateApprovalCategory(@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id,
			@Valid @RequestBody UpdateApprovalLevelCategoryMapResource ApprovalCategoryUpdateResource) {
		if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new UserNotFound(environment.getProperty(userNotFound));
		if (approvalLevelCategoryMapService.existsById(id)) {
			approvalLevelCategoryMapService.updateAndValidateApprovalCategory(tenantId, LogginAuthentcation.getInstance().getUserName(), id, ApprovalCategoryUpdateResource);
			SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty(commonUpdated));
			return new ResponseEntity<>(successDetailsDto, HttpStatus.OK);
		} else {
			SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(successDetailsDto, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

	
	@GetMapping(value = "/{tenantId}/categoryId/{categoryId}")
	public ResponseEntity<Object> getAllbycatId(@PathVariable(value = "tenantId", required = true) String tenantId,@PathVariable(value = "categoryId", required = true) Long categoryId) {
		 List<ApprovalLevelCategoryMapping> list=approvalLevelCategoryMapService.findBycategoryId(categoryId);
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();

		 if(list.isEmpty()) {
			 responseMessage.setMessages(environment.getProperty(recordNotFound));
				return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		 }else {
				return new ResponseEntity<>(list, HttpStatus.OK);

		 }
		
	}
}
