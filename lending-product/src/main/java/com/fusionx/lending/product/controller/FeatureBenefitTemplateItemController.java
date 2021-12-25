package com.fusionx.lending.product.controller;

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
import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.FeatureBenefitTemplateItem;
import com.fusionx.lending.product.domain.FeatureBenifitTemplatePending;
import com.fusionx.lending.product.exception.UserNotFound;
import com.fusionx.lending.product.repository.FeatureBenefitTemplateItemRepository;
import com.fusionx.lending.product.resources.FeatureBenefitItemUsageResource;
import com.fusionx.lending.product.resources.FeatureBenefitTemplateItemAddResource;
import com.fusionx.lending.product.resources.FeatureBenefitTemplateItemUpdateResource;
import com.fusionx.lending.product.resources.SuccessAndErrorDetails;
import com.fusionx.lending.product.service.FeatureBenefitTemplateItemService;

/**
 * Feature Benefit Template Item Controller
 * @author 	NipunDilhan
 * @Date    22-07-2021
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   22-07-2021 						    NipunDilhan      Created
 *    
 ********************************************************************************************************
 */


@RestController
@RequestMapping(value = "/feature-benefit-template-item")
@CrossOrigin(origins = "*")
public class FeatureBenefitTemplateItemController {
	
	@Autowired
	private FeatureBenefitTemplateItemService featureBenefitTemplateItemService;
		
	@Autowired
	private FeatureBenefitTemplateItemRepository featureBenefitTemplateItemRepository;
		
	@Autowired
	private Environment   environment;
	
	private String userNotFound="common.user-not-found";
	private static final String RECORD_NOT_FOUND = "Records not available";
 	
 	
	/**
     * save Feature Benifit Template Item 
     * @param @PathVariable{tenantId}
     * @param @RequestBody{FeatureBenefitTemplateItemAddResource}
     * @return SuccessAndErrorDetailsDto
     */
 	@PostMapping(value = "/{tenantId}")
 	public ResponseEntity<Object> addFeatureBenifitTemplateItem(@PathVariable(value = "tenantId", required = true) String tenantId,
 			                                @Valid @RequestBody FeatureBenefitTemplateItemAddResource featureBenefitTemplateItemAddResource) {
	 		if(LogginAuthentcation.getInstance().getUserName()==null || LogginAuthentcation.getInstance().getUserName().isEmpty()) { 
	 			throw new UserNotFound(environment.getProperty(userNotFound));
	 		}

	 		FeatureBenefitTemplateItem featureBenefitTemplateItem = featureBenefitTemplateItemService.createFeatureBenefitTemplateItem(tenantId, featureBenefitTemplateItemAddResource);  
	 		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails(environment.getProperty("rec.saved"),Long.toString(featureBenefitTemplateItem.getId()));
	     	return new ResponseEntity<>(responseMessage,HttpStatus.CREATED);
 	}
 	
 	
 	
	/**
     * update Feature Benifit Template Item 
     * @param @PathVariable{tenantId}
     * @param @RequestBody{FeatureBenefitTemplateItemUpdateResource}
     * @return SuccessAndErrorDetailsDto
     */
 	@PutMapping(value = "/{tenantId}/template/{id}")
 	public ResponseEntity<Object> updateFeatureBenifitTemplateItem(@PathVariable(value = "tenantId", required = true) String tenantId,
 											@PathVariable(value = "id", required = true) Long id,
 			                                @Valid @RequestBody FeatureBenefitTemplateItemUpdateResource featureBenefitTemplateItemUpdateResource) {
	 		if(LogginAuthentcation.getInstance().getUserName()==null || LogginAuthentcation.getInstance().getUserName().isEmpty()) { 
	 			throw new UserNotFound(environment.getProperty(userNotFound));
	 		}

	 		FeatureBenifitTemplatePending featureBenifitTemplatePending = featureBenefitTemplateItemService.updateFeatureBenifitTemplate(tenantId,id,featureBenefitTemplateItemUpdateResource); 
	 		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails(environment.getProperty("rec.saved"),Long.toString(featureBenifitTemplatePending.getId()));
	     	return new ResponseEntity<>(responseMessage,HttpStatus.CREATED);
 	}
 	
 	
	/**
     * direct update Feature Benifit Template Item
     * @param @PathVariable{tenantId}
     * @param @RequestBody{FeatureBenefitItemUsageResource}
     * @return SuccessAndErrorDetailsDto
     */
 	@PutMapping(value = "/{tenantId}/{id}")
 	public ResponseEntity<Object> directUpdateFeatureBenifitTemplateItem(@PathVariable(value = "tenantId", required = true) String tenantId,
 											@PathVariable(value = "id", required = true) Long id,
 			                                @Valid @RequestBody FeatureBenefitItemUsageResource featureBenefitItemUsageResource) {
	 		if(LogginAuthentcation.getInstance().getUserName()==null || LogginAuthentcation.getInstance().getUserName().isEmpty()) { 
	 			throw new UserNotFound(environment.getProperty(userNotFound));
	 		}

	 		FeatureBenefitTemplateItem featureBenefitTemplateItem = featureBenefitTemplateItemService.directUpdateFeatureBenefitTemplateItem(id, featureBenefitItemUsageResource);
	 		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails(environment.getProperty("rec.saved"),Long.toString(featureBenefitTemplateItem.getId()));
	     	return new ResponseEntity<>(responseMessage,HttpStatus.CREATED);
 	}
 	
	/**
	 * Gets the FeatureBenefitTemplateItem by pending feature benifit template item.
	 *
	 * @param tenantId - the tenant id
	 * @param id - feature benifit template item id
	 * @return  FeatureBenefitTemplateItem
	 */
 	@GetMapping(value = "/{tenantId}/{id}")
 	public ResponseEntity<Object>  findByFeatureBenifitTemplatItemId(@PathVariable(value = "tenantId", required = true) String tenantId, 
 			                                              @PathVariable(value = "id", required = true) Long id) {
 		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
 		Optional<FeatureBenefitTemplateItem> featureBenefitTemplateItemOptional = featureBenefitTemplateItemRepository.findById(id);
	 	if (featureBenefitTemplateItemOptional.isPresent()) {
	 		return new ResponseEntity<>(featureBenefitTemplateItemOptional.get(), HttpStatus.OK);
	 	} 
	 	else {
	 		responseMessage.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
	 	}
 	}

 	
	/**
	 * Gets the FeatureBenefitTemplate by pending feature benifit template .
	 *
	 * @param tenantId - the tenant id
	 * @param id - feature benifit template item
	 * @return  FeatureBenefitTemplate
	 */
	@GetMapping(value = "/{tenantId}/template/{id}")
	public ResponseEntity<Object> findByFeatureBenifitTemplateId(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id) {
		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();

		List<FeatureBenefitTemplateItem> featureBenifitTemplateItemList = featureBenefitTemplateItemService.findByFeatureBenifitTemplateId(id);
		
		if (!featureBenifitTemplateItemList.isEmpty()) {
			return new ResponseEntity<>(featureBenifitTemplateItemList, HttpStatus.OK);
		} else {
			responseMessage.setMessages("record not found");
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	

}
