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
import com.fusionx.lending.product.resources.SubProductRiskMappingUpdateResource;
import com.fusionx.lending.product.resources.SuccessAndErrorDetails;
import com.fusionx.lending.product.service.SubProductRiskTemplateMappingService;
import com.fusionx.lending.product.service.SubProductService;

/**
 * Sub Product Risk Template Mapping Controller
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   18-10-2021    						Dilki        Created
 *    
 ********************************************************************************************************
 */

@RestController
@RequestMapping(value = "/sub-product-risk-template")
@CrossOrigin(origins = "*")
public class SubProductRiskTemplateMappingController extends MessagePropertyBase {

	@Autowired
	private SubProductRiskTemplateMappingService subProductRiskTemplateMappingService;

	@Autowired
	private SubProductService subProductService;

	/**
	 * get SubProduct by riskTemplateId
	 * 
	 * @param tenantId       the tenant id
	 * @param riskTemplateId the riskTemplateId
	 * @return the SubProductinition by riskTemplateId
	 */
	@GetMapping(value = "/{tenantId}/{riskTemplateId}")
	public ResponseEntity<Object> getByRiskTemplateId(@PathVariable(value = "tenantId") String tenantId,
			@PathVariable(value = "riskTemplateId") Long riskTemplateId) {

		List<SubProduct> isPresentSubProduct = subProductRiskTemplateMappingService.getByRiskTemplateId(riskTemplateId);
		if (!isPresentSubProduct.isEmpty()) {
			return new ResponseEntity<>(isPresentSubProduct, HttpStatus.OK);

		} else {

			SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * Update RiskTemplates details of SubProduct
	 *
	 * @param tenantId                            the tenant id
	 * @param subProductId                        the subProductId
	 * @param subProductRiskMappingUpdateResource the
	 *                                            subProductRiskMappingUpdateResource
	 * @return the response entity
	 */
	@PutMapping(value = "/{tenantId}/sub-product/{subProductId}")
	public ResponseEntity<Object> updateRiskTempMappingDetails(@PathVariable(value = "tenantId") String tenantId,
			@PathVariable(value = "subProductId") Long subProductId,
			@Valid @RequestBody SubProductRiskMappingUpdateResource subProductRiskMappingUpdateResource) {

		SuccessAndErrorDetails successAndErrorDetails = new SuccessAndErrorDetails();
		Optional<SubProduct> isPresentSubProductinition = subProductService.getById(subProductId);

		if (isPresentSubProductinition.isPresent()) {
			Long masterDefId = subProductRiskTemplateMappingService.updateRiskTempMappingDetails(tenantId, subProductId,
					subProductRiskMappingUpdateResource);
			successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty(RECORD_UPDATED),
					masterDefId.toString());
			return new ResponseEntity<>(successAndErrorDetails, HttpStatus.OK);

		} else {
			successAndErrorDetails.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(successAndErrorDetails, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

}
