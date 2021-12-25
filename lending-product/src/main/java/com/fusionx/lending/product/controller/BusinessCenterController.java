package com.fusionx.lending.product.controller;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.BusinessCenter;
import com.fusionx.lending.product.exception.UserNotFound;
import com.fusionx.lending.product.resources.BusinessCenterAddResource;
import com.fusionx.lending.product.resources.BusinessCenterUpdateResource;
import com.fusionx.lending.product.resources.SuccessAndErrorDetailsResource;
import com.fusionx.lending.product.service.BusinessCenterService;



/**
 * Business Center
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   10-08-2021      		     FX-	   Thsmokshi      Created
 *    
 ********************************************************************************************************
 */
@RestController
@RequestMapping(value = "/business-center")
@CrossOrigin(origins = "*")
public class BusinessCenterController {

	@Autowired
	private Environment environment;
	
	@Autowired
	private BusinessCenterService service;
	
	private String userNotFound = "common.user-not-found";
	private String commonSaved = "common.saved";
	private String commonUpdated = "common.updated";
	private String pageableLength = "common.pageable-length";
	private String recordNotFound = "common.record-not-found";
	
	/**
	 * Gets the all business types.
	 *
	 * @param tenantId - the tenant id
	 * @return the all business types
	 */
	@GetMapping(value = "/{tenantId}/all")
	public ResponseEntity<Object> getAllBusinessCenters(@PathVariable(value = "tenantId", required = true) String tenantId) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<BusinessCenter> BusinessCenter = service.findAll();
		if (!BusinessCenter.isEmpty()) {
			return new ResponseEntity<>((Collection<BusinessCenter>) BusinessCenter, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Gets the business type by id.
	 *
	 * @param tenantId - the tenant id
	 * @param id - the id
	 * @return the business type by id
	 */
	@GetMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> getBusinessCenterById(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<BusinessCenter> isPresentBusinessCenter = service.findById(id);
		if (isPresentBusinessCenter.isPresent()) {
			return new ResponseEntity<>(isPresentBusinessCenter, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Gets the business types by status.
	 *
	 * @param tenantId - the tenant id
	 * @param status - the status
	 * @return the business types by status
	 */
	@GetMapping(value = "/{tenantId}/status/{status}")
	public ResponseEntity<Object> getBusinessCentersByStatus(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "status", required = true) String status) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<BusinessCenter> BusinessCenter = service.findByStatus(status);
		if (!BusinessCenter.isEmpty()) {
			return new ResponseEntity<>((Collection<BusinessCenter>) BusinessCenter, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Gets the business types by name.
	 *
	 * @param tenantId - the tenant id
	 * @param name - the name
	 * @return the business types by name
	 */
	@GetMapping(value = "/{tenantId}/name/{name}")
	public ResponseEntity<Object> getBusinessCentersByName(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "name", required = true) String name) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<BusinessCenter> BusinessCenter = service.findByName(name);
		if (!BusinessCenter.isEmpty()) {
			return new ResponseEntity<>((Collection<BusinessCenter>) BusinessCenter, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Gets the business types by code.
	 *
	 * @param tenantId - the tenant id
	 * @param code - the code
	 * @return the business types by code
	 */
	@GetMapping(value = "/{tenantId}/code/{code}")
	public ResponseEntity<Object> getBusinessCentersByCode(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "code", required = true) String code) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<BusinessCenter> isPresentBusinessCenter = service.findByCode(code);
		if (isPresentBusinessCenter.isPresent()) {
			return new ResponseEntity<>(isPresentBusinessCenter, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Adds the business Center.
	 *
	 * @param tenantId - the tenant id
	 * @param BusinessCenterAddResource - the business type add resource
	 * @return the response entity
	 */
	@PostMapping(value = "/{tenantId}")
	public ResponseEntity<Object> add(@PathVariable(value = "tenantId", required = true) String tenantId,
			@Valid @RequestBody BusinessCenterAddResource BusinessCenterAddResource) {
		if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new UserNotFound(environment.getProperty(userNotFound));
		Long id = service.saveAndValidateBusinessCenter(tenantId, LogginAuthentcation.getInstance().getUserName(), BusinessCenterAddResource);
		SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty(commonSaved), id.toString());
		return new ResponseEntity<>(successDetailsDto,HttpStatus.CREATED);
	}
	
	/**
	 * Update business type.
	 *
	 * @param tenantId - the tenant id
	 * @param id - the id
	 * @param BusinessCenterUpdateResource - the business type update resource
	 * @return the response entity
	 */
	@PutMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> updateBusinessCenter(@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id,
			@Valid @RequestBody BusinessCenterUpdateResource BusinessCenterUpdateResource) {
		if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new UserNotFound(environment.getProperty(userNotFound));
		if (service.existsById(id)) {
			service.updateAndValidateBusinessCenter(tenantId, LogginAuthentcation.getInstance().getUserName(), id, BusinessCenterUpdateResource);
			SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty(commonUpdated));
			return new ResponseEntity<>(successDetailsDto, HttpStatus.OK);
		} else {
			SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(successDetailsDto, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
	


}
