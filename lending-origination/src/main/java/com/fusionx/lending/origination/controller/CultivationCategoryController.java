package com.fusionx.lending.origination.controller;

import java.util.Collection;
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
import com.fusionx.lending.origination.domain.CultivationCategory;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.resource.CommonAddResource;
import com.fusionx.lending.origination.resource.CommonUpdateResource;
import com.fusionx.lending.origination.resource.SuccessAndErrorDetailsResource;
import com.fusionx.lending.origination.service.CultivationCategoryService;

/**
 * Cultivation Category Controller
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   26-12-2020   							 MenukaJ        Created
 *    
 ********************************************************************************************************
 */

@RestController
@RequestMapping(value = "/cultivation-category")
@CrossOrigin(origins = "*")
public class CultivationCategoryController extends MessagePropertyBase {
	
	@Autowired
	private CultivationCategoryService cultivationCategoryService;
	
	/**
	 * get all cultivation category
	 * @param @PathVariable{tenantId}
	 * @param @PathVariable{all}
	 * @return List
	 **/
	@GetMapping("/{tenantId}/all")
	public ResponseEntity<Object> getAllCultivationCategory(@PathVariable(value = "tenantId", required = true) String tenantId){
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<CultivationCategory>isPresentCultivationCategory = cultivationCategoryService.getAll();
		if(!isPresentCultivationCategory.isEmpty()) {
			return new ResponseEntity<>((Collection<CultivationCategory>)isPresentCultivationCategory,HttpStatus.OK);
		}
		else {
			responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * get cultivation category by ID
	 * @param @pathVariable{tenantId}
	 * @param @pathVariable {id}
	 * @return Optional
	 **/
	@GetMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> getCultivationCategoryById(@PathVariable(value = "tenantId", required = true) String tenantId,
													         	  @PathVariable(value = "id", required = true) Long id){
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<CultivationCategory> isPresentCultivationCategory = cultivationCategoryService.getById(id);
		if(isPresentCultivationCategory.isPresent()) {
			return new ResponseEntity<>(isPresentCultivationCategory.get(), HttpStatus.OK);
		}
		else {
			responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * get cultivation category by code
	 * @param @pathVariable{tenantId}
	 * @param @pathVariable {code}
	 * @return Optional
	 **/
	@GetMapping(value = "/{tenantId}/code/{code}")
	public ResponseEntity<Object> getCultivationCategoryByCode(@PathVariable(value = "tenantId", required = true) String tenantId,
													   	            @PathVariable(value = "code", required = true) String code){
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<CultivationCategory> isPresentCultivationCategory = cultivationCategoryService.getByCode(code);
		if(isPresentCultivationCategory.isPresent()) {
			return new ResponseEntity<>(isPresentCultivationCategory.get(), HttpStatus.OK);
		}
		else {
			responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * get cultivation category by name
	 * @param @pathVariable{tenantId}
	 * @param @pathVariable {name}
	 * @return List
	 **/
	@GetMapping(value = "/{tenantId}/name/{name}")
	public ResponseEntity<Object> getCultivationCategoryByName(@PathVariable(value = "tenantId", required = true) String tenantId,
															        @PathVariable(value = "name", required = true) String name){
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<CultivationCategory> isPresentCultivationCategory = cultivationCategoryService.getByName(name);
		if(!isPresentCultivationCategory.isEmpty()) {
			return new ResponseEntity<>(isPresentCultivationCategory, HttpStatus.OK);
		}
		else {
			responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * get cultivation category by status
	 * @param @pathVariable{tenantId}
	 * @param @pathVariable {status}
	 * @return List
	 **/
	@GetMapping(value = "/{tenantId}/status/{status}")
	public ResponseEntity<Object> getCultivationCategoryByStatus(@PathVariable(value = "tenantId", required = true) String tenantId,
													   			      @PathVariable(value = "status", required = true) String status){
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		if(status.equals(CommonStatus.ACTIVE.toString()) || status.equals(CommonStatus.INACTIVE.toString())) {
			List<CultivationCategory> isPresentCultivationCategory = cultivationCategoryService.getByStatus(status);
			if(!isPresentCultivationCategory.isEmpty()) {
				return new ResponseEntity<>(isPresentCultivationCategory, HttpStatus.OK);
			}
			else {
				responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
				return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
			}
		}
		else {
			responseMessage.setMessages(getEnvironment().getProperty("common-status.pattern"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);

		}
	}
	
	/**
     * save cultivation category
     * @param @PathVariable{tenantId}
     * @param @RequestBody{CommonAddResource}
     * @return SuccessAndErrorDetailsDto
     */
	@PostMapping("/{tenantId}")
	public ResponseEntity<Object> addCultivationCategory(@PathVariable(value = "tenantId", required = true) String tenantId,
			   									       		  @Valid @RequestBody CommonAddResource commonAddResource){
		CultivationCategory CultivationCategory = cultivationCategoryService.addCultivationCategory(tenantId, commonAddResource);
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource(getEnvironment().getProperty(RECORD_CREATED), Long.toString(CultivationCategory.getId()));
		return new ResponseEntity<>(responseMessage,HttpStatus.CREATED);
	}
	
	/**
     * update cultivation category
     * @param @PathVariable{tenantId}
     * @param @RequestBody{CommonUpdateResource}
     * @return SuccessAndErrorDetailsDto
     */
	@PutMapping(value = "{tenantId}/{id}")
	public ResponseEntity<Object> updateCultivationCategory(@PathVariable(value = "tenantId", required = true) String tenantId, 
												                 @PathVariable(value = "id", required = true) Long id, 
												                 @Valid @RequestBody CommonUpdateResource commonUpdateResource){
		SuccessAndErrorDetailsResource successAndErrorDetailsResource=new SuccessAndErrorDetailsResource();
		Optional<CultivationCategory>isPresentCultivationCategory = cultivationCategoryService.getById(id);		
		if(isPresentCultivationCategory.isPresent()) {
			commonUpdateResource.setId(id.toString());
			CultivationCategory CultivationCategory = cultivationCategoryService.updateCultivationCategory(tenantId, commonUpdateResource);
			successAndErrorDetailsResource = new SuccessAndErrorDetailsResource(getEnvironment().getProperty(RECORD_UPDATED), CultivationCategory.getId().toString());
			return new ResponseEntity<>(successAndErrorDetailsResource,HttpStatus.OK);
		}
		else {
			successAndErrorDetailsResource.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}	
	
}
