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
import com.fusionx.lending.product.domain.SubProduct;
import com.fusionx.lending.product.resources.SubProductDueDateMappingUpdateResource;
import com.fusionx.lending.product.resources.SuccessAndErrorDetails;
import com.fusionx.lending.product.service.SubProductDueDateTemplateMappingService;
import com.fusionx.lending.product.service.SubProductService;

/**
 * Sub Product Due Date Template Mapping Controller
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   30-09-2021    FXL-155  	 FXL-933	Piyumi      Created
 *    
 ********************************************************************************************************
 */

@RestController
@RequestMapping(value = "/sub-product-due-date-template")
@CrossOrigin(origins = "*")
public class SubProductDueDateTemplateMappingController  extends MessagePropertyBase{
	
	@Autowired
	private SubProductDueDateTemplateMappingService subProductDueDateTemplateMappingService;
	
	@Autowired
	private SubProductService subProductService;
	
	/**
	 * get SubProduct by dueDateTemplateId
	 * 
	 * @param tenantId - the tenant id
	 * @param dueDateTemplateId - the dueDateTemplateId
	 * @return the SubProductinition by dueDateTemplateId
	 */
	@GetMapping(value = "/{tenantId}/{dueDateTemplateId}")
	public ResponseEntity<Object> getByDueDateTemplateId(
			@PathVariable(value = "tenantId") String tenantId,
			@PathVariable(value = "dueDateTemplateId") Long dueDateTemplateId) {

		List<SubProduct> isPresentSubProduct = subProductDueDateTemplateMappingService.getByDueDateTemplateId(dueDateTemplateId);
		if (!isPresentSubProduct.isEmpty()) {
			return new ResponseEntity<>(isPresentSubProduct, HttpStatus.OK);
			
		} else {

			SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Update DueDateTemplates details of SubProduct
	 *
	 * @param tenantId - the tenant id
	 * @param subProductId - the subProductId
	 * @param subProductDueDateMappingUpdateResource - the subProductDueDateMappingUpdateResource
	 * @return the response entity
	 */
	@PutMapping(value = "/{tenantId}/sub-product/{subProductId}")
	public ResponseEntity<Object> updateDueDateTempMappingDetails(
			@PathVariable(value = "tenantId") String tenantId,
			@PathVariable(value = "subProductId") Long subProductId,
			@Valid @RequestBody SubProductDueDateMappingUpdateResource subProductDueDateMappingUpdateResource) {

		SuccessAndErrorDetails successAndErrorDetails = new SuccessAndErrorDetails();
		Optional<SubProduct> isPresentSubProductinition = subProductService.getById(subProductId);		
		
		if (isPresentSubProductinition.isPresent()) {
			Long masterDefId = subProductDueDateTemplateMappingService.updateDueDateTempMappingDetails(tenantId, subProductId,subProductDueDateMappingUpdateResource);
			successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty(RECORD_UPDATED),masterDefId.toString());
			return new ResponseEntity<>(successAndErrorDetails, HttpStatus.OK);
			
		} else {
			successAndErrorDetails.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(successAndErrorDetails, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

}
