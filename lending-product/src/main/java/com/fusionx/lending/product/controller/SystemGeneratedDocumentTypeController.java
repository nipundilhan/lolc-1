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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.domain.SystemGeneratedDocumentType;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.exception.ValidateRecordException;
import com.fusionx.lending.product.resources.SystemGeneratedDocumentTypeAddResource;
import com.fusionx.lending.product.resources.SystemGeneratedDocumentTypeUpdateResource;
import com.fusionx.lending.product.resources.SuccessAndErrorDetailsResource;
import com.fusionx.lending.product.service.SystemGeneratedDocumentTypeService;

/**
 * System Generated Document Type
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   10-11-2021   FXL-358       FXL-1736   Dilki        Created
 *    
 ********************************************************************************************************
 */

@RestController
@RequestMapping(value = "/system-generated-document-type")
@CrossOrigin(origins = "*")
public class SystemGeneratedDocumentTypeController extends MessagePropertyBase {

	@Autowired
	private SystemGeneratedDocumentTypeService systemGeneratedDocumentTypeService;

	/**
	 * get all System Generated Document Type
	 * 
	 * @param @PathVariable{tenantId}
	 * @param @PathVariable{all}
	 * @return List
	 **/
	@GetMapping("/{tenantId}/all")
	public ResponseEntity<Object> getAllSystemGeneratedDocumentType(
			@PathVariable(value = "tenantId", required = true) String tenantId) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<SystemGeneratedDocumentType> isPresentSystemGeneratedDocumentType = systemGeneratedDocumentTypeService
				.getAll();
		if (!isPresentSystemGeneratedDocumentType.isEmpty()) {
			return new ResponseEntity<>(isPresentSystemGeneratedDocumentType, HttpStatus.OK);
		} else {
			responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * get System Generated Document Type by id
	 * 
	 * @param @pathVariable{tenantId}
	 * @param @pathVariable           {id}
	 * @return Optional
	 **/
	@GetMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> getSystemGeneratedDocumentTypeById(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<SystemGeneratedDocumentType> isPresentSystemGeneratedDocumentType = systemGeneratedDocumentTypeService
				.getById(id);
		if (isPresentSystemGeneratedDocumentType.isPresent()) {
			return new ResponseEntity<>(isPresentSystemGeneratedDocumentType.get(), HttpStatus.OK);
		} else {
			responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * get System Generated Document Type by status
	 * 
	 * @param @pathVariable{tenantId}
	 * @param @pathVariable           {status}
	 * @return List
	 **/
	@GetMapping(value = "/{tenantId}/status/{status}")
	public ResponseEntity<Object> getSystemGeneratedDocumentTypeByStatus(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "status", required = true) String status) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		if (status.equals(CommonStatus.ACTIVE.toString()) || status.equals(CommonStatus.INACTIVE.toString())) {
			List<SystemGeneratedDocumentType> isPresentSystemGeneratedDocumentType = systemGeneratedDocumentTypeService
					.getByStatus(status);
			if (!isPresentSystemGeneratedDocumentType.isEmpty()) {
				return new ResponseEntity<>(isPresentSystemGeneratedDocumentType, HttpStatus.OK);
			} else {
				responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
				return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
			}
		} else {
			responseMessage.setMessages(RECORD_NOT_FOUND);
			throw new ValidateRecordException(environment.getProperty("common-status.pattern"), "message");
		}
	}

	/**
	 * get System Generated Document Type by code
	 * 
	 * @param @pathVariable{tenantId}
	 * @param @pathVariable           {code}
	 * @return Optional
	 **/
	@GetMapping(value = "/{tenantId}/code/{code}")
	public ResponseEntity<Object> getSystemGeneratedDocumentTypeByCode(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "code", required = true) String code) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<SystemGeneratedDocumentType> isPresentSystemGeneratedDocumentType = systemGeneratedDocumentTypeService
				.getByCode(code);
		if (isPresentSystemGeneratedDocumentType.isPresent()) {
			return new ResponseEntity<>(isPresentSystemGeneratedDocumentType.get(), HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * Get System Generated Document Type by name
	 *
	 * @param tenantId the id of tenant
	 * @param name     the name
	 * @return the list of all System Generated Document Type if record exists,
	 *         otherwise <code>204 - No Content</code>
	 */
	@GetMapping(value = "/{tenantId}/name/{name}")
	public ResponseEntity<Object> getSystemGeneratedDocumentTypeByName(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "name", required = true) String name) {
		List<SystemGeneratedDocumentType> isPresentSystemGeneratedDocumentType = systemGeneratedDocumentTypeService
				.findByName(name);
		if (!isPresentSystemGeneratedDocumentType.isEmpty()) {
			return new ResponseEntity<>(isPresentSystemGeneratedDocumentType, HttpStatus.OK);
		} else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * Get System Generated Document Type by product name
	 *
	 * @param tenantId the id of tenant
	 * @param name     the name
	 * @return the list of all System Generated Document Type if record exists,
	 *         otherwise <code>204 - No Content</code>
	 */
	@GetMapping(value = "/{tenantId}/product-name/{name}")
	public ResponseEntity<Object> getSystemGeneratedDocumentTypeByProductName(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "name", required = true) String name) {
		List<SystemGeneratedDocumentType> isPresentSystemGeneratedDocumentType = systemGeneratedDocumentTypeService
				.findByProductName(name);
		if (!isPresentSystemGeneratedDocumentType.isEmpty()) {
			return new ResponseEntity<>(isPresentSystemGeneratedDocumentType, HttpStatus.OK);
		} else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * Get System Generated Document Type by sub product name
	 *
	 * @param tenantId the id of tenant
	 * @param name     the name
	 * @return the list of all account status if record exists, otherwise
	 *         <code>204 - No Content</code>
	 */
	@GetMapping(value = "/{tenantId}/sub-product-name/{name}")
	public ResponseEntity<Object> getSystemGeneratedDocumentTypeBySubProductName(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "name", required = true) String name) {
		List<SystemGeneratedDocumentType> isPresentSystemGeneratedDocumentType = systemGeneratedDocumentTypeService
				.findBySubProductName(name);
		if (!isPresentSystemGeneratedDocumentType.isEmpty()) {
			return new ResponseEntity<>(isPresentSystemGeneratedDocumentType, HttpStatus.OK);
		} else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * save System Generated Document Type
	 * 
	 * @param @PathVariable{tenantId}
	 * @param @RequestBody{SystemGeneratedDocumentTypeAddResource}
	 * @return SuccessAndErrorDetailsDto
	 */
	@PostMapping("/{tenantId}")
	public ResponseEntity<Object> addSystemGeneratedDocumentType(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@Valid @RequestBody SystemGeneratedDocumentTypeAddResource systemGeneratedDocumentTypeAddResource) {
		SystemGeneratedDocumentType systemGeneratedDocumentType = systemGeneratedDocumentTypeService
				.addSystemGeneratedDocumentType(tenantId, systemGeneratedDocumentTypeAddResource);
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource(
				getEnvironment().getProperty(RECORD_CREATED), Long.toString(systemGeneratedDocumentType.getId()));
		return new ResponseEntity<>(responseMessage, HttpStatus.CREATED);
	}

	/**
	 * update System Generated Document Type
	 * 
	 * @param @PathVariable{tenantId}
	 * @param @RequestBody{SystemGeneratedDocumentTypeUpdateResource}
	 * @return SuccessAndErrorDetailsDto
	 */
	@PutMapping(value = "{tenantId}/{id}")
	public ResponseEntity<Object> updateSystemGeneratedDocumentType(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id,
			@Valid @RequestBody SystemGeneratedDocumentTypeUpdateResource systemGeneratedDocumentTypeUpdateResource) {
		SuccessAndErrorDetailsResource successAndErrorDetailsResource = new SuccessAndErrorDetailsResource();
		Optional<SystemGeneratedDocumentType> isPresentSystemGeneratedDocumentType = systemGeneratedDocumentTypeService
				.getById(id);
		if (isPresentSystemGeneratedDocumentType.isPresent()) {
			SystemGeneratedDocumentType updateSystemGeneratedDocumentType = systemGeneratedDocumentTypeService
					.updateSystemGeneratedDocumentType(tenantId, id, systemGeneratedDocumentTypeUpdateResource);
			successAndErrorDetailsResource = new SuccessAndErrorDetailsResource(
					getEnvironment().getProperty(RECORD_UPDATED), updateSystemGeneratedDocumentType.getId().toString());
			return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.OK);
		} else {
			successAndErrorDetailsResource.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

}
