package com.fusionx.lending.product.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.domain.MasterDefinition;
import com.fusionx.lending.product.resources.MasterDefDueDateMappingUpdateResource;
import com.fusionx.lending.product.resources.SuccessAndErrorDetails;
import com.fusionx.lending.product.service.MasterDefDueDateTemplateMappingService;
import com.fusionx.lending.product.service.MasterDefinitionService;

/**
 * MasterDef Due Date Template Mapping Controller
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   29-09-2021    FXL-680  	 FXL-924	Piyumi      Created
 *    
 ********************************************************************************************************
 */

@RestController
@RequestMapping(value = "/master-due-date-template")
@CrossOrigin(origins = "*")
public class MasterDefDueDateTemplateMappingController  extends MessagePropertyBase{
	
	@Autowired
	private MasterDefDueDateTemplateMappingService masterDefDueDateTemplateMappingService;
	
	@Autowired
	private MasterDefinitionService masterDefinitionService;
	
	/**
	 * get MasterDefinition by dueDateTemplateId
	 * 
	 * @param tenantId - the tenant id
	 * @param dueDateTemplateId - the dueDateTemplateId
	 * @return the MasterDefinition by dueDateTemplateId
	 */
	@GetMapping(value = "/{tenantId}/{dueDateTemplateId}")
	public ResponseEntity<Object> getByDueDateTemplateId(
			@PathVariable(value = "tenantId") String tenantId,
			@PathVariable(value = "dueDateTemplateId") Long dueDateTemplateId) {

		List<MasterDefinition> isPresentMasterDefinitions = masterDefDueDateTemplateMappingService.getByDueDateTemplateId(dueDateTemplateId);
		if (!isPresentMasterDefinitions.isEmpty()) {
			return new ResponseEntity<>(isPresentMasterDefinitions, HttpStatus.OK);
			
		} else {

			SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Update DueDateTemplates details of MasterDefinition
	 *
	 * @param tenantId - the tenant id
	 * @param masterDefinitionId - the masterDefinitionId
	 * @param masterDefDueDateMappingUpdateResource - the masterDefDueDateMappingUpdateResource
	 * @return the response entity
	 */
	@PutMapping(value = "/{tenantId}/master-definition/{masterDefinitionId}")
	public ResponseEntity<Object> updateDueDateTempMappingDetails(
			@PathVariable(value = "tenantId") String tenantId,
			@PathVariable(value = "masterDefinitionId") Long masterDefinitionId,
			@Valid @RequestBody MasterDefDueDateMappingUpdateResource masterDefDueDateMappingUpdateResource) {

		SuccessAndErrorDetails successAndErrorDetails = new SuccessAndErrorDetails();
		Optional<MasterDefinition> isPresentMasterDefinition = masterDefinitionService.getById(masterDefinitionId);		
		
		if (isPresentMasterDefinition.isPresent()) {
			Long masterDefId = masterDefDueDateTemplateMappingService.updateDueDateTempMappingDetails(tenantId, masterDefinitionId,masterDefDueDateMappingUpdateResource);
			successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty(RECORD_UPDATED),masterDefId.toString());
			return new ResponseEntity<>(successAndErrorDetails, HttpStatus.OK);
			
		} else {
			successAndErrorDetails.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(successAndErrorDetails, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

}
