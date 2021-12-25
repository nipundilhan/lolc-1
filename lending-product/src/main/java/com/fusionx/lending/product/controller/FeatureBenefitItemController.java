package com.fusionx.lending.product.controller;



import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.domain.FeatureBenefitItem;
import com.fusionx.lending.product.resources.FeatureBenefitItemAddResource;
import com.fusionx.lending.product.resources.FeatureBenefitItemUpdateResource;
import com.fusionx.lending.product.resources.SuccessAndErrorDetails;
import com.fusionx.lending.product.resources.SuccessAndErrorDetailsResource;
import com.fusionx.lending.product.service.FeatureBenefitItemService;

/**
 * 	Feature Benefit Item Controller
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   08-06-2021     FX-6614     FX-6550    RavishikaS     Created
 *    
 ********************************************************************************************************
*/

@RestController
@RequestMapping(value = "/feature-benefit-item")
@CrossOrigin(origins = "*")
public class FeatureBenefitItemController extends MessagePropertyBase{
	
	private static final Logger logger = LoggerFactory.getLogger(FeatureBenefitItemController.class);
	
	@Autowired
	private FeatureBenefitItemService  featureBenefitItemService;
	
	@Autowired
	private Environment   environment;
	
	
		
	/**
	 * get all FeatureBenefitItems
	 * @param @PathVariable{tenantId}
	 * @param @PathVariable{all}
	 * @return List
	 **/
	
	@GetMapping("/{tenantId}/all")
	public ResponseEntity<Object> findAllFeatureBenefitItems(@PathVariable(value = "tenantId", required = true) String tenantId){
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<FeatureBenefitItem>isPresentFeatureBenefitItem = featureBenefitItemService.findAll();
		if(!isPresentFeatureBenefitItem.isEmpty()) {
			return new ResponseEntity<>((Collection<FeatureBenefitItem>)isPresentFeatureBenefitItem,HttpStatus.OK);
		}
		else {
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	

	/**
	 * get FeatureBenefitItem by ID
	 * @param @pathVariable{tenantId}
	 * @param @pathVariable {id}
	 * @return Optional
	 **/
	@GetMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> findAllFeatureBenefitItemById(@PathVariable(value = "tenantId", required = true) String tenantId,
													   @PathVariable(value = "id", required = true) Long id){
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<FeatureBenefitItem> isPresentFeatureBenefitItem = featureBenefitItemService.findById(id);
		if(isPresentFeatureBenefitItem.isPresent()) {
			return new ResponseEntity<>(isPresentFeatureBenefitItem.get(), HttpStatus.OK);
		}
		else {
			responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * get FeatureBenefitItem by name
	 * @param @pathVariable{tenantId}
	 * @param @pathVariable {testName}
	 * @return Optional
	 **/
	@GetMapping(value = "/{tenantId}/name/{name}")
	public ResponseEntity<Object> findFeatureBenefitItemByName(@PathVariable(value = "tenantId", required = true) String tenantId,
													   @PathVariable(value = "name", required = true) String name){
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<FeatureBenefitItem> isPresentFeatureBenefitItem = featureBenefitItemService.findByName(name);
		if(!isPresentFeatureBenefitItem.isEmpty()) {
			return new ResponseEntity<>(isPresentFeatureBenefitItem, HttpStatus.OK);
		}
		else {
			responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * get FeatureBenefitItem by status
	 * @param @pathVariable{tenantId}
	 * @param @pathVariable {testName}
	 * @return Optional
	 **/
	@GetMapping(value = "/{tenantId}/status/{status}")
	public ResponseEntity<Object> findFeatureBenefitItemByStatus(@PathVariable(value = "tenantId", required = true) String tenantId,
													   @PathVariable(value = "status", required = true) String status){
		List<FeatureBenefitItem> list = featureBenefitItemService.findByStatus(status);
		if (!list.isEmpty())
			return new ResponseEntity<>(list, HttpStatus.OK);
		else {
			return new ResponseEntity<>(list, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
     * save FeatureBenefitItem
     * @param @PathVariable{tenantId}
     * @param @RequestBody{AddTestResource}
     * @return SuccessAndErrorDetailsDto
     */
	@PostMapping("/{tenantId}")
	public ResponseEntity<Object> findFeatureBenefitItem(@PathVariable(value = "tenantId", required = true) String tenantId,
			   									           @Valid @RequestBody FeatureBenefitItemAddResource featureBenefitItemAddResource){
		FeatureBenefitItem featureBenefitItem = featureBenefitItemService.addFeatureBenefitItem(tenantId, featureBenefitItemAddResource);
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource(getEnvironment().getProperty(RECORD_CREATED));
		return new ResponseEntity<>(responseMessage,HttpStatus.CREATED);
	}
	
	/**
     * update FeatureBenefitItem
     * @param @PathVariable{tenantId}
     * @param @RequestBody{CommonUpdateResource}
     * @return SuccessAndErrorDetailsDto
     */
	@PutMapping(value = "{tenantId}/{id}")
	public ResponseEntity<Object> updateFeatureBenefitItem(@PathVariable(value = "tenantId", required = true) String tenantId, 
										              @PathVariable(value = "id", required = true) Long id, 
										              @Valid @RequestBody FeatureBenefitItemUpdateResource featureBenefitItemUpdateResource){
		SuccessAndErrorDetailsResource successAndErrorDetailsResource=new SuccessAndErrorDetailsResource();
		Optional<FeatureBenefitItem>isPresentClearingType = featureBenefitItemService.findById(id);
		
		if(isPresentClearingType.isPresent()) {
			featureBenefitItemUpdateResource.setId(id.toString());
			FeatureBenefitItem featureBenefitItem = featureBenefitItemService.updateFeatureBenefitItem(tenantId, featureBenefitItemUpdateResource);
			successAndErrorDetailsResource = new SuccessAndErrorDetailsResource(getEnvironment().getProperty(RECORD_UPDATED), featureBenefitItem.getId().toString());
			return new ResponseEntity<>(successAndErrorDetailsResource,HttpStatus.OK);
		}
		else {
			successAndErrorDetailsResource.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
 	
 	/**
     * get Feature Benefit Item Info by  Name
     * @param @PathVariable{tenantId}
     * @param @PathVariable{name}
     * @return Option
     */
 	@GetMapping(value = "/{tenantId}/item-type-code/{item-type-code}")
 	public ResponseEntity<Object> getFeatureBenefitItemByFeatureBenefitItemTypeCode(@PathVariable(value = "tenantId", required = true) String tenantId, 
 			                                    @PathVariable(value = "item-type-code", required = true) String code) {
 		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();

 			List<FeatureBenefitItem>  featureBenefitItems = featureBenefitItemService.findByFeatureBenefitItemTypeCode(code);
	 		
 			if(featureBenefitItems !=null && !featureBenefitItems.isEmpty()) {
	 			return new ResponseEntity<>(featureBenefitItems, HttpStatus.OK);
	 		} 
	 		else {
	 			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
				return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
	 		}
 		
 	}
 	
 	@GetMapping(value = "/{tenantId}/code/{code}")
	public ResponseEntity<Object> getFeatureBenefitItemByCode(@PathVariable(value = "tenantId", required = true) String tenantId,
													   	            @PathVariable(value = "code", required = true) String code){
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		
		Optional<FeatureBenefitItem> isPresentFeatureBenefitItem = featureBenefitItemService.findByCode(code);
		if(isPresentFeatureBenefitItem.isPresent()) {
			return new ResponseEntity<>(isPresentFeatureBenefitItem.get(), HttpStatus.OK);
		}
		else {
			responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
 	
 	@GetMapping(value = "/{tenantId}/code/{code}/item-type-id/{itemTypeId}")
	public ResponseEntity<Object> findByFeatureBenefitItemCodeAndItemTypeId(@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "code", required = true) String code,										   
			@PathVariable(value = "itemTypeId", required = true) Long itemTypeId){
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<FeatureBenefitItem> isPresentFeatureBenefitItem = featureBenefitItemService.findByCodeAndItemTypeId(code,itemTypeId);
		if(isPresentFeatureBenefitItem.isPresent()) {
			return new ResponseEntity<>(isPresentFeatureBenefitItem.get(), HttpStatus.OK);
		}
		else {
			responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
}
