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
import com.fusionx.lending.origination.domain.ExpenceTypeCultivationCategory;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.resource.ExpenceTypeCultivationCategoryAddResource;
import com.fusionx.lending.origination.resource.ExpenceTypeCultivationCategoryUpdateResource;
import com.fusionx.lending.origination.resource.SuccessAndErrorDetailsResource;
import com.fusionx.lending.origination.service.ExpenceTypeCultivationCategoryService;

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
@RequestMapping(value = "/expence-type-cultivation-category")
@CrossOrigin(origins = "*")
public class ExpenceTypeCultivationCategoryController extends MessagePropertyBase {
	
	@Autowired
	private ExpenceTypeCultivationCategoryService expenceTypeCultivationCategoryService;
	
	/**
	 * get all Expence Type Cultivation Category
	 * @param @PathVariable{tenantId}
	 * @param @PathVariable{all}
	 * @return List
	 **/
	@GetMapping("/{tenantId}/all")
	public ResponseEntity<Object> getAllExpenceTypeCultivationCategory(@PathVariable(value = "tenantId", required = true) String tenantId){
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<ExpenceTypeCultivationCategory> isPresentExpenceTypeCultivationCategory = expenceTypeCultivationCategoryService.getAll();
		if(!isPresentExpenceTypeCultivationCategory.isEmpty()) {
			return new ResponseEntity<>((Collection<ExpenceTypeCultivationCategory>)isPresentExpenceTypeCultivationCategory,HttpStatus.OK);
		}
		else {
			responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * get Expence Type Cultivation Category
	 * @param @pathVariable{tenantId}
	 * @param @pathVariable {id}
	 * @return Optional
	 **/
	@GetMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> getExpenceTypeCultivationCategoryById(@PathVariable(value = "tenantId", required = true) String tenantId,
													         	  @PathVariable(value = "id", required = true) Long id){
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<ExpenceTypeCultivationCategory> isPresentExpenceTypeCultivationCategory = expenceTypeCultivationCategoryService.getById(id);
		if(isPresentExpenceTypeCultivationCategory.isPresent()) {
			return new ResponseEntity<>(isPresentExpenceTypeCultivationCategory.get(), HttpStatus.OK);
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
	public ResponseEntity<Object> getExpenceTypeCultivationCategoryByStatus(@PathVariable(value = "tenantId", required = true) String tenantId,
													   			      @PathVariable(value = "status", required = true) String status){
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		if(status.equals(CommonStatus.ACTIVE.toString()) || status.equals(CommonStatus.INACTIVE.toString())) {
			List<ExpenceTypeCultivationCategory> isPresentExpenceTypeCultivationCategory = expenceTypeCultivationCategoryService.getByStatus(status);
			if(!isPresentExpenceTypeCultivationCategory.isEmpty()) {
				return new ResponseEntity<>(isPresentExpenceTypeCultivationCategory, HttpStatus.OK);
			}
			else {
				responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
				return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
			}
		}
		else {
			responseMessage.setMessages(RECORD_NOT_FOUND);
	        throw new ValidateRecordException(environment.getProperty("common-status.pattern"), "message");
		}
	}
	
	/**
     * save Expence Type Cultivation Category
     * @param @PathVariable{tenantId}
     * @param @RequestBody{ExpenceTypeCultivationCategoryAddResource}
     * @return SuccessAndErrorDetailsDto
     */
	@PostMapping("/{tenantId}")
	public ResponseEntity<Object> addCultivationCategory(@PathVariable(value = "tenantId", required = true) String tenantId,
			   									       		  @Valid @RequestBody ExpenceTypeCultivationCategoryAddResource expenceTypeCultivationCategoryAddResource){
		expenceTypeCultivationCategoryService.addExpenceTypeCultivationCategory(tenantId, expenceTypeCultivationCategoryAddResource);
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource(getEnvironment().getProperty(RECORD_CREATED));
		return new ResponseEntity<>(responseMessage,HttpStatus.CREATED);
	}
	
	/**
     * update Expence Type Cultivation Category
     * @param @PathVariable{tenantId}
     * @param @RequestBody{ExpenceTypeCultivationCategoryUpdateResource}
     * @return SuccessAndErrorDetailsDto
     */
	@PutMapping(value = "{tenantId}")
	public ResponseEntity<Object> updateExpenceTypeCultivationCategory(@PathVariable(value = "tenantId", required = true) String tenantId, 
												                 @Valid @RequestBody ExpenceTypeCultivationCategoryUpdateResource expenceTypeCultivationCategoryUpdateResource){
		SuccessAndErrorDetailsResource successAndErrorDetailsResource=new SuccessAndErrorDetailsResource();
		expenceTypeCultivationCategoryService.updateExpenceTypeCultivationCategory(tenantId, expenceTypeCultivationCategoryUpdateResource);
		successAndErrorDetailsResource = new SuccessAndErrorDetailsResource(getEnvironment().getProperty(RECORD_UPDATED));
		return new ResponseEntity<>(successAndErrorDetailsResource,HttpStatus.OK);
		
	}	
	
	/**
	 * Gets the by cultivation category code.
	 *
	 * @param tenantId the tenant id
	 * @param cultiCatCode the cultivation category code
	 * @return successAndErrorDetailsResource
	 */
	@GetMapping(value = "/{tenantId}/cauti-cat/code/{cultiCatCode}")
	public ResponseEntity<Object> getByCaltivationCatCode(@PathVariable(value = "tenantId", required = true) String tenantId,
													   			      @PathVariable(value = "cultiCatCode", required = true) String cultiCatCode){
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			List<ExpenceTypeCultivationCategory> isPresentExpenceTypeCultivationCategory = expenceTypeCultivationCategoryService.getByCaltiCatCode(cultiCatCode);
			if(!isPresentExpenceTypeCultivationCategory.isEmpty()) {
				return new ResponseEntity<>(isPresentExpenceTypeCultivationCategory, HttpStatus.OK);
			}
			else {
				responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
				return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
			}
	}
	
}
