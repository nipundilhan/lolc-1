package com.fusionx.lending.origination.controller;


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
import com.fusionx.lending.origination.core.LogginAuthentcation;
import com.fusionx.lending.origination.domain.BusinessRiskType;
import com.fusionx.lending.origination.exception.UserNotFound;
import com.fusionx.lending.origination.resource.CommonAddResource;
import com.fusionx.lending.origination.resource.CommonUpdateResource;
import com.fusionx.lending.origination.resource.SuccessAndErrorDetailsResource;
import com.fusionx.lending.origination.service.BusinessRiskTypeService;

@RestController
@RequestMapping(value = "/business-risk-type")
@CrossOrigin(origins = "*")
public class BusinessRiskTypeController  extends MessagePropertyBase{
	

	private BusinessRiskTypeService businessRiskTypeService;
	
    @Autowired
    public void setBusinessRiskTypeService(BusinessRiskTypeService businessRiskTypeService) {
        this.businessRiskTypeService = businessRiskTypeService;
    }
	

	
	/**
	 * Gets the all business types.
	 *
	 * @param tenantId - the tenant id
	 * @return the all business types
	 */
	@GetMapping(value = "/{tenantId}/all")
	public ResponseEntity<Object> getAllBusinessRiskTypes(@PathVariable(value = "tenantId", required = true) String tenantId) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<BusinessRiskType> businessRiskTypeList = businessRiskTypeService.findAll();
		if (!businessRiskTypeList.isEmpty()) {
			return new ResponseEntity<>(businessRiskTypeList, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
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
	public ResponseEntity<Object> getBusinessTypeById(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<BusinessRiskType> isPresentBusinessRiskType= businessRiskTypeService.findById(id);
		if (isPresentBusinessRiskType.isPresent()) {
			return new ResponseEntity<>(isPresentBusinessRiskType.get(), HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
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
	public ResponseEntity<Object> getBusinessTypesByStatus(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "status", required = true) String status) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<BusinessRiskType> businessRiskTypeList = businessRiskTypeService.findByStatus(status);
		if (!businessRiskTypeList.isEmpty()) {
			return new ResponseEntity<>(businessRiskTypeList, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
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
	public ResponseEntity<Object> getBusinessTypesByName(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "name", required = true) String name) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<BusinessRiskType> businessRiskTypeList = businessRiskTypeService.findByName(name);
		if (!businessRiskTypeList.isEmpty()) {
			return new ResponseEntity<>(businessRiskTypeList, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
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
	public ResponseEntity<Object> getBusinessTypesByCode(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "code", required = true) String code) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<BusinessRiskType> isPresentBusinessRiskType = businessRiskTypeService.findByCode(code);
		if (isPresentBusinessRiskType.isPresent()) {
			return new ResponseEntity<>(isPresentBusinessRiskType.get(), HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Adds the business type.
	 *
	 * @param tenantId - the tenant id
	 * @param businessTypeAddResource - the business type add resource
	 * @return the response entity
	 */
	@PostMapping(value = "/{tenantId}")
	public ResponseEntity<Object> addBusinessRiskType(@PathVariable(value = "tenantId", required = true) String tenantId,
			@Valid @RequestBody CommonAddResource commonAddResource) {
		if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new UserNotFound(environment.getProperty(USER_NOT_FOUND));
		Long id = businessRiskTypeService.saveAndValidateBusinessRiskType(tenantId, LogginAuthentcation.getInstance().getUserName(), commonAddResource);
		SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty(RECORD_CREATED), id.toString());
		return new ResponseEntity<>(successDetailsDto,HttpStatus.CREATED);
	}
	
	/**
	 * Update business type.
	 *
	 * @param tenantId - the tenant id
	 * @param id - the id
	 * @param businessTypeUpdateResource - the business type update resource
	 * @return the response entity
	 */
	@PutMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> updateBusinessType(@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id,
			@Valid @RequestBody CommonUpdateResource commonUpdateResource) {
		if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new UserNotFound(environment.getProperty(USER_NOT_FOUND));
		if (businessRiskTypeService.existsById(id)) {
			businessRiskTypeService.updateAndValidateBusinessRiskType(tenantId, LogginAuthentcation.getInstance().getUserName(), id, commonUpdateResource);
			SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty(RECORD_UPDATED));
			return new ResponseEntity<>(successDetailsDto, HttpStatus.OK);
		} else {
			SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(successDetailsDto, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
}
