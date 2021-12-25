package com.fusionx.lending.origination.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.fusionx.lending.origination.Constants;
import com.fusionx.lending.origination.base.MessagePropertyBase;
import com.fusionx.lending.origination.core.LogginAuthentcation;
import com.fusionx.lending.origination.domain.OtherIncomeType;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.exception.PageableLengthException;
import com.fusionx.lending.origination.exception.UserNotFound;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.resource.OtherIncomeTypeAddResource;
import com.fusionx.lending.origination.resource.OtherIncomeTypeUpdateResource;
import com.fusionx.lending.origination.resource.SuccessAndErrorDetailsResource;
import com.fusionx.lending.origination.service.OtherIncomeTypeService;

/**
 * Other Income Type Controller
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   30-12-2020      		     FX-5272	MiyuruW      Created
 *    2   18-08-2021     FXL-639     FXL-537	Piyumi       Modified   
 ********************************************************************************************************
 */

@RestController
@RequestMapping(value = "/other-income-type")
@CrossOrigin(origins = "*")
public class OtherIncomeTypeController extends MessagePropertyBase{
	
	@Autowired
	private OtherIncomeTypeService otherIncomeTypeService;
	
	
	
	/**
	 * Get the all other income types.
	 *
	 * @param tenantId - the tenant id
	 * @return the all other income types
	 */
	@GetMapping(value = "/{tenantId}/all")
	public ResponseEntity<Object> getAllOtherIncomeTypes(@PathVariable(value = "tenantId", required = true) String tenantId) {
		
		List<OtherIncomeType> otherIncomeType = otherIncomeTypeService.findAll();
		if (!otherIncomeType.isEmpty()) {
			return new ResponseEntity<>(otherIncomeType, HttpStatus.OK);
		} else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(environment.getProperty(NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Get the other income type by id.
	 *
	 * @param tenantId - the tenant id
	 * @param id - the id
	 * @return the other income type by id
	 */
	@GetMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> getOtherIncomeTypeById(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id) {
		
		Optional<OtherIncomeType> isPresentOtherIncomeType = otherIncomeTypeService.findById(id);
		if (isPresentOtherIncomeType.isPresent()) {
			return new ResponseEntity<>(isPresentOtherIncomeType, HttpStatus.OK);
		} else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(environment.getProperty(NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Get the other income types by status.
	 *
	 * @param tenantId - the tenant id
	 * @param status - the status
	 * @return the other income types by status
	 */
	@GetMapping(value = "/{tenantId}/status/{status}")
	public ResponseEntity<Object> getOtherIncomeTypesByStatus(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "status", required = true) String status) {
		
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		if(status.equals(CommonStatus.ACTIVE.toString()) || status.equals(CommonStatus.INACTIVE.toString())) {
			
			List<OtherIncomeType> otherIncomeType = otherIncomeTypeService.findByStatus(status);
			if (!otherIncomeType.isEmpty()) {
				return new ResponseEntity<>(otherIncomeType, HttpStatus.OK);
			} else {
				responseMessage.setMessages(environment.getProperty(NOT_FOUND));
				return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
			}
		}else {
			responseMessage.setMessages(RECORD_NOT_FOUND);
	        throw new ValidateRecordException(environment.getProperty(COMMON_STATUS_PATTERN), "message");
		}
	}
	
	/**
	 * Get the other income types by name.
	 *
	 * @param tenantId - the tenant id
	 * @param name - the name
	 * @return the other income types by name
	 */
	@GetMapping(value = "/{tenantId}/name/{name}")
	public ResponseEntity<Object> getOtherIncomeTypesByName(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "name", required = true) String name) {
		
		List<OtherIncomeType> otherIncomeType = otherIncomeTypeService.findByName(name);
		if (!otherIncomeType.isEmpty()) {
			return new ResponseEntity<>(otherIncomeType, HttpStatus.OK);
		} else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(environment.getProperty(NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Get the other income types by code.
	 *
	 * @param tenantId - the tenant id
	 * @param code - the code
	 * @return the other income types by code
	 */
	@GetMapping(value = "/{tenantId}/code/{code}")
	public ResponseEntity<Object> getOtherIncomeTypesByCode(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "code", required = true) String code) {
	
		Optional<OtherIncomeType> isPresentOtherIncomeType = otherIncomeTypeService.findByCode(code);
		if (isPresentOtherIncomeType.isPresent()) {
			return new ResponseEntity<>(isPresentOtherIncomeType, HttpStatus.OK);
		} else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(environment.getProperty(NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Adds the other income type.
	 *
	 * @param tenantId - the tenant id
	 * @param otherIncomeTypeAddResource - the other income type add resource
	 * @return the response entity
	 */
	@PostMapping(value = "/{tenantId}")
	public ResponseEntity<Object> addOtherIncomeType(@PathVariable(value = "tenantId", required = true) String tenantId,
			@Valid @RequestBody OtherIncomeTypeAddResource otherIncomeTypeAddResource) {
	
		if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new UserNotFound(environment.getProperty(USER_NOT_FOUND));
		Long id = otherIncomeTypeService.saveAndValidateOtherIncomeType(tenantId, LogginAuthentcation.getInstance().getUserName(), otherIncomeTypeAddResource);
		SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty(RECORD_CREATED), id.toString());
		return new ResponseEntity<>(successDetailsDto,HttpStatus.CREATED);
	}
	
	/**
	 * Update other income type.
	 *
	 * @param tenantId - the tenant id
	 * @param id - the id
	 * @param otherIncomeTypeUpdateResource - the other income type update resource
	 * @return the response entity
	 */
	@PutMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> updateOtherIncomeType(@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id,
			@Valid @RequestBody OtherIncomeTypeUpdateResource otherIncomeTypeUpdateResource) {
	
		if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new UserNotFound(environment.getProperty(USER_NOT_FOUND));
		
		if (otherIncomeTypeService.existsById(id)) {
			otherIncomeTypeService.updateAndValidateOtherIncomeType(tenantId, LogginAuthentcation.getInstance().getUserName(), id, otherIncomeTypeUpdateResource);
			SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty(RECORD_UPDATED),id.toString());
			return new ResponseEntity<>(successDetailsDto, HttpStatus.OK);
		} else {
			SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty(NOT_FOUND));
			return new ResponseEntity<>(successDetailsDto, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
	
	/**
	 * Search other income type.
	 *
	 * @param tenantId - the tenant id
	 * @param searchq - the searchq
	 * @param name - the name
	 * @param code - the code
	 * @param pageable - the pageable
	 * @return the page
	 */
	@GetMapping(value = "/{tenantId}/search")
	public Page<OtherIncomeType> searchOtherIncomeType(@PathVariable(value = "tenantId", required = true) String tenantId,
			@RequestParam(value = "searchq", required = false) String searchq,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "code", required = false) String code,
			@PageableDefault(size = 10) Pageable pageable) {
		if(pageable.getPageSize()>Constants.MAX_PAGEABLE_LENGTH) 
			throw new PageableLengthException(environment.getProperty(PAGEABLE_LENGTH));
		return otherIncomeTypeService.searchOtherIncomeType(searchq, name, code, pageable);
	}
	
	
	/**
	 * Get the other income types by Other Income Category Id.
	 *
	 * @param tenantId - the tenant id
	 * @param otherIncomeCategoryId - the otherIncomeCategoryId
	 * @return the other income types by otherIncomeCategoryId
	 */
	@GetMapping(value = "/{tenantId}/other-income-category/{otherIncomeCategoryId}")
	public ResponseEntity<Object> getOtherIncomeTypesByOtherIncomeCategoryId(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "otherIncomeCategoryId", required = true) Long otherIncomeCategoryId) {
		
		List<OtherIncomeType> otherIncomeType = otherIncomeTypeService.findByOtherIncomeCategory(otherIncomeCategoryId);
		
		if (!otherIncomeType.isEmpty()) {
			return new ResponseEntity<>(otherIncomeType, HttpStatus.OK);
		} else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(environment.getProperty(NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
}