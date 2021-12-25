package com.fusionx.lending.origination.controller;

import java.util.Collection;
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

import com.fusionx.lending.origination.core.LogginAuthentcation;
import com.fusionx.lending.origination.domain.BusinessType;
import com.fusionx.lending.origination.domain.HouseHoldExpenseCategory;
import com.fusionx.lending.origination.exception.UserNotFound;
import com.fusionx.lending.origination.resource.BusinessTypeAddResource;
import com.fusionx.lending.origination.resource.BusinessTypeUpdateResource;
import com.fusionx.lending.origination.resource.HouseHoldExpenseCategoryAddResource;
import com.fusionx.lending.origination.resource.HouseHoldExpenseCategoryUpdateResource;
import com.fusionx.lending.origination.resource.SuccessAndErrorDetailsResource;
import com.fusionx.lending.origination.service.BusinessTypeService;
import com.fusionx.lending.origination.service.HouseHoldExpenseCategoryService;

@RestController
@RequestMapping(value = "/House-hold-expense-category")
@CrossOrigin(origins = "*")
public class HouseHoldExpenseCategoryController {
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private HouseHoldExpenseCategoryService businessTypeService;
	
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
	public ResponseEntity<Object> getAllBusinessTypes(@PathVariable(value = "tenantId", required = true) String tenantId) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<HouseHoldExpenseCategory> businessType = businessTypeService.findAll();
		if (!businessType.isEmpty()) {
			return new ResponseEntity<>((Collection<HouseHoldExpenseCategory>) businessType, HttpStatus.OK);
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
	public ResponseEntity<Object> getBusinessTypeById(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<HouseHoldExpenseCategory> isPresentBusinessType = businessTypeService.findById(id);
		if (isPresentBusinessType.isPresent()) {
			return new ResponseEntity<>(isPresentBusinessType, HttpStatus.OK);
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
	public ResponseEntity<Object> getBusinessTypesByStatus(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "status", required = true) String status) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<HouseHoldExpenseCategory> businessType = businessTypeService.findByStatus(status);
		if (!businessType.isEmpty()) {
			return new ResponseEntity<>((Collection<HouseHoldExpenseCategory>) businessType, HttpStatus.OK);
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
	public ResponseEntity<Object> getBusinessTypesByName(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "name", required = true) String name) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<HouseHoldExpenseCategory> businessType = businessTypeService.findByName(name);
		if (!businessType.isEmpty()) {
			return new ResponseEntity<>((Collection<HouseHoldExpenseCategory>) businessType, HttpStatus.OK);
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
	public ResponseEntity<Object> getBusinessTypesByCode(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "code", required = true) String code) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<HouseHoldExpenseCategory> isPresentBusinessType = businessTypeService.findByCode(code);
		if (isPresentBusinessType.isPresent()) {
			return new ResponseEntity<>(isPresentBusinessType, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(recordNotFound));
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
	public ResponseEntity<Object> addBusinessType(@PathVariable(value = "tenantId", required = true) String tenantId,
			@Valid @RequestBody HouseHoldExpenseCategoryAddResource houseHoldExpenseCategoryAddResource) {
		if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new UserNotFound(environment.getProperty(userNotFound));
		Long id = businessTypeService.saveAndValidateBusinessType(tenantId, LogginAuthentcation.getInstance().getUserName(), houseHoldExpenseCategoryAddResource);
		SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty(commonSaved), id.toString());
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
			@Valid @RequestBody HouseHoldExpenseCategoryUpdateResource houseHoldExpenseCategoryUpdateResource) {
		if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new UserNotFound(environment.getProperty(userNotFound));
		if (businessTypeService.existsById(id)) {
			businessTypeService.updateAndValidateBusinessType(tenantId, LogginAuthentcation.getInstance().getUserName(), id, houseHoldExpenseCategoryUpdateResource);
			SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty(commonUpdated));
			return new ResponseEntity<>(successDetailsDto, HttpStatus.OK);
		} else {
			SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(successDetailsDto, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

}
