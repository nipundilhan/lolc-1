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
import com.fusionx.lending.product.domain.FeatureBenefitTemplateItemEligibility;
import com.fusionx.lending.product.domain.FeatureBenifitTemplatePending;
import com.fusionx.lending.product.exception.UserNotFound;
import com.fusionx.lending.product.repository.FeatureBenefitTemplateItemEligibilityRepository;
import com.fusionx.lending.product.resources.FeatureBenefitEligibilityUsageResource;
import com.fusionx.lending.product.resources.FeatureBenefitTemplateItemEligibilityAddResource;
import com.fusionx.lending.product.resources.FeatureBenefitTemplateItemEligibilityUpdateResource;
import com.fusionx.lending.product.resources.SuccessAndErrorDetails;
import com.fusionx.lending.product.service.FeatureBenefitTemplateItemEligibilityService;

/**
 * Feature Benefit Template Item Eligibility Controller
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
@RequestMapping(value = "/feature-benefit-template-item-eligibility")
@CrossOrigin(origins = "*")
public class FeatureBenefitTemplateItemEligibilityController {
	

	@Autowired
	private FeatureBenefitTemplateItemEligibilityService featureBenefitTemplateItemEligibilityService;
	@Autowired
	private FeatureBenefitTemplateItemEligibilityRepository featureBenefitTemplateItemEligibilityRepository;	

	
	@Autowired
	private Environment   environment;
	
	private String userNotFound="common.user-not-found";
 	
 	
	/**
     * save Feature Benifit Template Item Eligibility
     * @param @PathVariable{tenantId}
     * @param @RequestBody{FeatureBenefitTemplateItemEligibilityAddResource}
     * @return SuccessAndErrorDetailsDto
     */
 	@PostMapping(value = "/{tenantId}")
 	public ResponseEntity<Object> addFeatureBenifitTemplateItemEligibilityList(@PathVariable(value = "tenantId", required = true) String tenantId,
 			                                @Valid @RequestBody FeatureBenefitTemplateItemEligibilityAddResource featureBenefitTemplateItemEligibilityAddResource) {
	 		if(LogginAuthentcation.getInstance().getUserName()==null || LogginAuthentcation.getInstance().getUserName().isEmpty()) { 
	 			throw new UserNotFound(environment.getProperty(userNotFound));
	 		}

	 		FeatureBenefitTemplateItemEligibility featureBenefitTemplateItemEligibility = featureBenefitTemplateItemEligibilityService.createFeatureBenefitTemplateItemEligibility(tenantId, featureBenefitTemplateItemEligibilityAddResource);
	 		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails(environment.getProperty("rec.saved"),Long.toString(featureBenefitTemplateItemEligibility.getId()));
	     	return new ResponseEntity<>(responseMessage,HttpStatus.CREATED);
 	}
 	
 	
 	
	/**
     * update Feature Benifit Template Item Eligibility
     * @param @PathVariable{tenantId}
     * @param @RequestBody{FeatureBenefitTemplateItemEligibilityUpdateResource}
     * @return SuccessAndErrorDetailsDto
     */
 	@PutMapping(value = "/{tenantId}/template/{id}")
 	public ResponseEntity<Object> updateFeatureBenifitTemplateItem(@PathVariable(value = "tenantId", required = true) String tenantId,
 											@PathVariable(value = "id", required = true) Long id,
 			                                @Valid @RequestBody FeatureBenefitTemplateItemEligibilityUpdateResource featureBenefitTemplateItemEligibilityUpdateResource) {
	 		if(LogginAuthentcation.getInstance().getUserName()==null || LogginAuthentcation.getInstance().getUserName().isEmpty()) { 
	 			throw new UserNotFound(environment.getProperty(userNotFound));
	 		}

	 		FeatureBenifitTemplatePending featureBenifitTemplatePending = featureBenefitTemplateItemEligibilityService.updateupdateFeatureBenifitTemplate(tenantId, id, featureBenefitTemplateItemEligibilityUpdateResource);
	 		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails(environment.getProperty("rec.saved"),Long.toString(featureBenifitTemplatePending.getId()));
	     	return new ResponseEntity<>(responseMessage,HttpStatus.CREATED);
 	}
 	
	/**
     * direct update Feature Benifit Template Item Eligibility
     * @param @PathVariable{tenantId}
     * @param @RequestBody{FeatureBenefitEligibilityUsageResource}
     * @return SuccessAndErrorDetailsDto
     */
 	@PutMapping(value = "/{tenantId}/{id}")
 	public ResponseEntity<Object> directUpdateFeatureBenifitTemplateItemEligibility(@PathVariable(value = "tenantId", required = true) String tenantId,
 											@PathVariable(value = "id", required = true) Long id,
 			                                @Valid @RequestBody FeatureBenefitEligibilityUsageResource featureBenefitEligibilityUsageResource) {
	 		if(LogginAuthentcation.getInstance().getUserName()==null || LogginAuthentcation.getInstance().getUserName().isEmpty()) { 
	 			throw new UserNotFound(environment.getProperty(userNotFound));
	 		}

	 		FeatureBenefitTemplateItemEligibility featureBenefitTemplateItemEligibility = featureBenefitTemplateItemEligibilityService.directUpdateFeatureBenefitTemplateItemEligibility(id, featureBenefitEligibilityUsageResource);
	 		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails(environment.getProperty("rec.saved"),Long.toString(featureBenefitTemplateItemEligibility.getId()));
	     	return new ResponseEntity<>(responseMessage,HttpStatus.CREATED);
 	}
 	
	/**
	 * Gets the FeatureBenefitTemplateItem by feature benifit template item.
	 *
	 * @param tenantId - the tenant id
	 * @param id - feature benifit template item id
	 * @return  FeatureBenefitTemplateItem
	 */
	@GetMapping(value = "/{tenantId}/template-item/{id}")
	public ResponseEntity<Object> findByFeatureBenifitTemplateItemId(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id) {
		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();

		List<FeatureBenefitTemplateItemEligibility> findByFeatureBenifitTemplateItemList = featureBenefitTemplateItemEligibilityService.findByFeatureBenifitTemplateItem(id);
		if ((findByFeatureBenifitTemplateItemList != null) && (!findByFeatureBenifitTemplateItemList.isEmpty()) && (findByFeatureBenifitTemplateItemList.size() != 0)) {
			return new ResponseEntity<>(findByFeatureBenifitTemplateItemList, HttpStatus.OK);
		} else {
			responseMessage.setMessages("record not found");
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Gets the FeatureBenefitTemplate by feature benifit template.
	 *
	 * @param tenantId - the tenant id
	 * @param id - feature benifit template id
	 * @return  FeatureBenefitTemplate
	 */	
	@GetMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> findByFeatureBenifitTemplateItemEligibilityId(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id) {
		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();

		Optional<FeatureBenefitTemplateItemEligibility> featureBenefitTemplateItemEligibilityOptional = featureBenefitTemplateItemEligibilityRepository.findById(id);
		if (featureBenefitTemplateItemEligibilityOptional.isPresent()) {
			return new ResponseEntity<>(featureBenefitTemplateItemEligibilityOptional.get(), HttpStatus.OK);
		} else {
			responseMessage.setMessages("record not found");
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

}
