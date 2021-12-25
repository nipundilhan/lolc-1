package com.fusionx.lending.product.controller;

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

import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.domain.AccountStatus;
import com.fusionx.lending.product.domain.DocumentCheckList;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.exception.ValidateRecordException;
import com.fusionx.lending.product.resources.DocumentCheckListAddResource;
import com.fusionx.lending.product.resources.DocumentCheckListUpdateResource;
import com.fusionx.lending.product.resources.SuccessAndErrorDetailsResource;
import com.fusionx.lending.product.service.DocumentCheckListService;

/**
 * Document CheckList Controller
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   12-07-2021   							Dilhan        Created
 *    
 ********************************************************************************************************
 */

@RestController
@RequestMapping(value = "/document-checklist")
@CrossOrigin(origins = "*")
public class DocumentCheckListController extends MessagePropertyBase {

	@Autowired
	private DocumentCheckListService documentCheckListService;

	/**
	 * get all document check list
	 * 
	 * @param @PathVariable{tenantId}
	 * @param @PathVariable{all}
	 * @return List
	 **/
	@GetMapping("/{tenantId}/all")
	public ResponseEntity<Object> getAllDocumentCheckList(
			@PathVariable(value = "tenantId", required = true) String tenantId) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<DocumentCheckList> isPresentDocumentCheckList = documentCheckListService.getAll();
		if (!isPresentDocumentCheckList.isEmpty()) {
			return new ResponseEntity<>((Collection<DocumentCheckList>) isPresentDocumentCheckList, HttpStatus.OK);
		} else {
			responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * get document check list by id
	 * @param @pathVariable{tenantId}
	 * @param @pathVariable {id}
	 * @return Optional
	 **/
	@GetMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> getDocumentCheckListById(@PathVariable(value = "tenantId", required = true) String tenantId,
													         	  @PathVariable(value = "id", required = true) Long id){
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<DocumentCheckList> isPresentDocumentCheckList = documentCheckListService.getById(id);
		if(isPresentDocumentCheckList.isPresent()) {
			return new ResponseEntity<>(isPresentDocumentCheckList.get(), HttpStatus.OK);
		}
		else {
			responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * get document check list by status
	 * @param @pathVariable{tenantId}
	 * @param @pathVariable {status}
	 * @return List
	 **/
	@GetMapping(value = "/{tenantId}/status/{status}")
	public ResponseEntity<Object> getDocumentCheckListByStatus(@PathVariable(value = "tenantId", required = true) String tenantId,
													   			      @PathVariable(value = "status", required = true) String status){
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		if(status.equals(CommonStatus.ACTIVE.toString()) || status.equals(CommonStatus.INACTIVE.toString())) {
			List<DocumentCheckList> isPresentDocumentCheckList = documentCheckListService.getByStatus(status);
			if(!isPresentDocumentCheckList.isEmpty()) {
				return new ResponseEntity<>(isPresentDocumentCheckList, HttpStatus.OK);
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
	 * get document check list by code
	 * @param @pathVariable{tenantId}
	 * @param @pathVariable {code}
	 * @return Optional
	 **/
	@GetMapping(value = "/{tenantId}/code/{code}")
	public ResponseEntity<Object> getDocumentCheckListByCode(@PathVariable(value = "tenantId", required = true) String tenantId,
													   	            @PathVariable(value = "code", required = true) String code){
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<DocumentCheckList> isPresentDocumentCheckList = documentCheckListService.getByCode(code);
		if(isPresentDocumentCheckList.isPresent()) {
			return new ResponseEntity<>(isPresentDocumentCheckList.get(), HttpStatus.OK);
		}
		else {
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	  /**
     * Get document check list by name
     *
     * @param tenantId the id of tenant
     * @param name   the name
     * @return the list of all document check list if record exists, otherwise <code>204 - No Content</code>
     */
	@GetMapping(value = "/{tenantId}/name/{name}")
	public ResponseEntity<Object> getDocumentCheckListByName(@PathVariable(value = "tenantId", required = true) String tenantId,
															        @PathVariable(value = "name", required = true) String name){
		List<DocumentCheckList> isPresentDocumentCheckList = documentCheckListService.findByName(name);
		if(!isPresentDocumentCheckList.isEmpty()) {
			return new ResponseEntity<>(isPresentDocumentCheckList, HttpStatus.OK);
		}
		else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	  /**
     * Get document check list by product name
     *
     * @param tenantId the id of tenant
     * @param name   the name
     * @return the list of all document check list if record exists, otherwise <code>204 - No Content</code>
     */
	@GetMapping(value = "/{tenantId}/product-name/{name}")
	public ResponseEntity<Object> getDocumentCheckListByProductName(@PathVariable(value = "tenantId", required = true) String tenantId,
															        @PathVariable(value = "name", required = true) String name){
		List<DocumentCheckList> isPresentDocumentCheckList = documentCheckListService.findByProductName(name);
		if(!isPresentDocumentCheckList.isEmpty()) {
			return new ResponseEntity<>(isPresentDocumentCheckList, HttpStatus.OK);
		}
		else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	/**
	 * Get document check list by sub product name
	 *
	 * @param tenantId the id of tenant
	 * @param name   the name
	 * @return the list of all account status if record exists, otherwise <code>204 - No Content</code>
	 */
	@GetMapping(value = "/{tenantId}/sub-product-name/{name}")
	public ResponseEntity<Object> getDocumentCheckListBySubProductName(@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "name", required = true) String name){
		List<DocumentCheckList> isPresentDocumentCheckList = documentCheckListService.findBySubProductName(name);
		if(!isPresentDocumentCheckList.isEmpty()) {
			return new ResponseEntity<>(isPresentDocumentCheckList, HttpStatus.OK);
		}
		else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * save document check list
	 * 
	 * @param @PathVariable{tenantId}
	 * @param @RequestBody{DocumentCheckListAddResource}
	 * @return SuccessAndErrorDetailsDto
	 */
	@PostMapping("/{tenantId}")
	public ResponseEntity<Object> addDocumentCheckList(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@Valid @RequestBody DocumentCheckListAddResource documentCheckListAddResource) {
		DocumentCheckList documentCheckList = documentCheckListService.addDocumentCheckList(tenantId,
				documentCheckListAddResource);
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource(
				getEnvironment().getProperty(RECORD_CREATED), Long.toString(documentCheckList.getId()));
		return new ResponseEntity<>(responseMessage, HttpStatus.CREATED);
	}

	/**
	 * update document check list
	 * 
	 * @param @PathVariable{tenantId}
	 * @param @RequestBody{DocumentCheckListUpdateResource}
	 * @return SuccessAndErrorDetailsDto
	 */
	@PutMapping(value = "{tenantId}/{id}")
	public ResponseEntity<Object> updateDocumentCheckList(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id,
			@Valid @RequestBody DocumentCheckListUpdateResource documentCheckListUpdateResource) {
		SuccessAndErrorDetailsResource successAndErrorDetailsResource = new SuccessAndErrorDetailsResource();
		Optional<DocumentCheckList> isPresentDocumentCheckList = documentCheckListService.getById(id);
		if (isPresentDocumentCheckList.isPresent()) {
			DocumentCheckList updateDocumentCheckList = documentCheckListService.updateDocumentCheckList(tenantId, id,
					documentCheckListUpdateResource);
			successAndErrorDetailsResource = new SuccessAndErrorDetailsResource(
					getEnvironment().getProperty(RECORD_UPDATED), updateDocumentCheckList.getId().toString());
			return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.OK);
		} else {
			successAndErrorDetailsResource.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

}
