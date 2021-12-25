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
import com.fusionx.lending.origination.domain.AppraisalUploadCheckList;
import com.fusionx.lending.origination.domain.FieldSetup;
import com.fusionx.lending.origination.domain.RiskGrading;
import com.fusionx.lending.origination.exception.UserNotFound;
import com.fusionx.lending.origination.resource.AppraisalUploadCheckListAddResource;
import com.fusionx.lending.origination.resource.AppraisalUploadCheckListUpdateResource;
import com.fusionx.lending.origination.resource.FieldSetupAddResource;
import com.fusionx.lending.origination.resource.FieldSetupUpdateResource;
import com.fusionx.lending.origination.resource.RiskGradingAddResource;
import com.fusionx.lending.origination.resource.RiskGradingUpdateResource;
import com.fusionx.lending.origination.resource.SuccessAndErrorDetailsResource;
import com.fusionx.lending.origination.service.AppraisalUploadCheckListService;
import com.fusionx.lending.origination.service.FieldSetupService;
import com.fusionx.lending.origination.service.RiskGradingService;

/**
 * Appraisal Verification Checklist.
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   30-09-2021      		     			SewwandiH      Created
 *    
 ********************************************************************************************************
 */

@RestController
@RequestMapping(value = "/appraisal-verification-checklist")
@CrossOrigin(origins = "*")
public class AppraisalUploadCheckListController {
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private AppraisalUploadCheckListService appraisalUploadCheckListService;
	
	private String userNotFound = "common.user-not-found";
	private String commonSaved = "common.saved";
	private String commonUpdated = "common.updated";
	private String pageableLength = "common.pageable-length";
	private String recordNotFound = "common.record-not-found";

	
	/**
	 * Get the all check lists.
	 *
	 * @param tenantId - the tenant id
	 * @return the all check lists.
	 */
	@GetMapping(value = "/{tenantId}/all")
	public ResponseEntity<Object> getAllAppraisalUploadCheckList(@PathVariable(value = "tenantId", required = true) String tenantId) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<AppraisalUploadCheckList> appraisalUploadCheckList = appraisalUploadCheckListService.findAll();
		if (!appraisalUploadCheckList.isEmpty()) {
			return new ResponseEntity<>((Collection<AppraisalUploadCheckList>) appraisalUploadCheckList, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Get the check list by id.
	 *
	 * @param tenantId - the tenant id
	 * @param id - the id
	 * @return the check list by id
	 */
	@GetMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> getAppraisalUploadCheckListById(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<AppraisalUploadCheckList> isPresentAppraisalCheckList = appraisalUploadCheckListService.findById(id);
		if (isPresentAppraisalCheckList.isPresent()) {
			return new ResponseEntity<>(isPresentAppraisalCheckList, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Get the check list by status.
	 *
	 * @param tenantId - the tenant id
	 * @param status - the status
	 * @return the check list by status
	 */
	@GetMapping(value = "/{tenantId}/status/{status}")
	public ResponseEntity<Object> getAppraisalUploadCheckListByStatus(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "status", required = true) String status) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<AppraisalUploadCheckList> appraisalUploadCheckList = appraisalUploadCheckListService.findByStatus(status);
		if (!appraisalUploadCheckList.isEmpty()) {
			return new ResponseEntity<>((Collection<AppraisalUploadCheckList>) appraisalUploadCheckList, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Gets the checklist template documents by checklist template id.
	 *
	 * @param tenantId - the tenant id
	 * @param checklistTemplateId - the checklist template id
	 * @return the checklist template documents by checklist template id
	 */
	@GetMapping(value = "/{tenantId}/checklistTemplate/{checklistTemplateId}")
	public ResponseEntity<Object> getByChecklistTemplateId(@PathVariable(value = "tenantId", required = true) String tenantId,
																					 @PathVariable(value = "checklistTemplateId", required = true) Long checklistTemplateId) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<AppraisalUploadCheckList> appraisalUploadCheckList = appraisalUploadCheckListService.findByChecklistTemplateId(checklistTemplateId);
		if (!appraisalUploadCheckList.isEmpty()) {
			return new ResponseEntity<>((Collection<AppraisalUploadCheckList>) appraisalUploadCheckList, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
		
	/**
	 * Add the check list.
	 *
	 * @param tenantId - the tenant id
	 * @param AppraisalUploadCheckListAddResource - the check list request
	 * @return the response entity
	 */
	@PostMapping(value = "/{tenantId}")
	public ResponseEntity<Object> addAppraisalUploadCheckList(@PathVariable(value = "tenantId", required = true) String tenantId,
			@Valid @RequestBody AppraisalUploadCheckListAddResource checkListAddResource) {
		if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new UserNotFound(environment.getProperty(userNotFound));
		Long id = appraisalUploadCheckListService.saveAndValidateappraisalUploadCheckList(tenantId, LogginAuthentcation.getInstance().getUserName(), checkListAddResource);
		SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty(commonSaved));
		return new ResponseEntity<>(successDetailsDto,HttpStatus.CREATED);
	}
	
	/**
	 * update the Field setup.
	 *
	 * @param tenantId - the tenant id
	 * @param riskGradingAddResource - the risk grading update resource
	 * @return the response entity
	 */
	@PutMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> updateAppraisalUploadCheckList(@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id,
			@Valid @RequestBody AppraisalUploadCheckListUpdateResource appraisalUploadCheckListUpdateResource) {
		if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new UserNotFound(environment.getProperty(userNotFound));
		if (appraisalUploadCheckListService.existsById(id)) {
			appraisalUploadCheckListService.updateAndValidateappraisalUploadCheckList(tenantId, LogginAuthentcation.getInstance().getUserName(), id, appraisalUploadCheckListUpdateResource);
			SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty(commonUpdated));
			return new ResponseEntity<>(successDetailsDto, HttpStatus.OK);
		} else {
			SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(successDetailsDto, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

}
