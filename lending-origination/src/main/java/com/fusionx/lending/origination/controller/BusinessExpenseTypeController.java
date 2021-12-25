package com.fusionx.lending.origination.controller;

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

import com.fusionx.lending.origination.Constants;
import com.fusionx.lending.origination.core.LogginAuthentcation;
import com.fusionx.lending.origination.domain.BusinessExpenseType;
import com.fusionx.lending.origination.exception.PageableLengthException;
import com.fusionx.lending.origination.exception.UserNotFound;
import com.fusionx.lending.origination.resource.BusinessExpenseTypeAddResource;
import com.fusionx.lending.origination.resource.BusinessExpenseTypeUpdateResource;
import com.fusionx.lending.origination.resource.SuccessAndErrorDetailsResource;
import com.fusionx.lending.origination.service.BusinessExpenseTypeService;
import com.fusionx.lending.origination.base.MessagePropertyBase;

/**
 * Business Expense Type Service Controller
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   26-12-2020      		     FX-5271	MiyuruW      Created
 *    
 ********************************************************************************************************
 */

@RestController
@RequestMapping(value = "/business-expense-type")
@CrossOrigin(origins = "*")
public class BusinessExpenseTypeController {

	@Autowired
	private Environment environment;
	
	@Autowired
	private BusinessExpenseTypeService businessExpenseTypeService;
	
	private String userNotFound = "common.user-not-found";
	private String commonSaved = "common.saved";
	private String commonUpdated = "common.updated";
	private String pageableLength = "common.pageable-length";
	private String recordNotFound = "common.record-not-found";
	
	
	/**
	 * Gets the all business expense types.
	 *
	 * @param tenantId - the tenant id
	 * @return the all business expense types
	 */
	@GetMapping(value = "/{tenantId}/all")
	public ResponseEntity<Object> getAllBusinessExpenseTypes(@PathVariable(value = "tenantId", required = true) String tenantId) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<BusinessExpenseType> businessExpenseType = businessExpenseTypeService.findAll();
		if (!businessExpenseType.isEmpty()) {
			return new ResponseEntity<>((Collection<BusinessExpenseType>) businessExpenseType, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	/**
	 * Gets the business expense type by id.
	 *
	 * @param tenantId - the tenant id
	 * @param id - the id
	 * @return the business expense type by id
	 */
	@GetMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> getBusinessExpenseTypeById(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<BusinessExpenseType> isPresentBusinessExpenseType = businessExpenseTypeService.findById(id);
		if (isPresentBusinessExpenseType.isPresent()) {
			return new ResponseEntity<>(isPresentBusinessExpenseType, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	/**
	 * Gets the business expense types by status.
	 *
	 * @param tenantId - the tenant id
	 * @param status - the status
	 * @return the business expense types by status
	 */
	@GetMapping(value = "/{tenantId}/status/{status}")
	public ResponseEntity<Object> getBusinessExpenseTypesByStatus(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "status", required = true) String status) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<BusinessExpenseType> businessExpenseType = businessExpenseTypeService.findByStatus(status);
		if (!businessExpenseType.isEmpty()) {
			return new ResponseEntity<>((Collection<BusinessExpenseType>) businessExpenseType, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	/**
	 * Gets the business expense types by business type id.
	 *
	 * @param tenantId - the tenant id
	 * @param businessTypeId - the business type id
	 * @return the business expense types by business type id
	 */
	@GetMapping(value = "/{tenantId}/businessType/{businessTypeId}")
	public ResponseEntity<Object> getBusinessExpenseTypesByBusinessTypeId(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "businessTypeId", required = true) Long businessTypeId) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<BusinessExpenseType> businessExpenseType = businessExpenseTypeService.findByBusinessTypeId(businessTypeId);
		if (!businessExpenseType.isEmpty()) {
			return new ResponseEntity<>((Collection<BusinessExpenseType>) businessExpenseType, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	/**
	 * Adds the business expense type.
	 *
	 * @param tenantId - the tenant id
	 * @param businessExpenseTypeAddResource - the business expense type add resource
	 * @return the response entity
	 */
	@PostMapping(value = "/{tenantId}")
	public ResponseEntity<Object> addBusinessExpenseType(@PathVariable(value = "tenantId", required = true) String tenantId,
			@Valid @RequestBody BusinessExpenseTypeAddResource businessExpenseTypeAddResource) {
		if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new UserNotFound(environment.getProperty(userNotFound));
		businessExpenseTypeService.saveAndValidateBusinessExpenseType(tenantId, LogginAuthentcation.getInstance().getUserName(), businessExpenseTypeAddResource);
		SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty(commonSaved));
		return new ResponseEntity<>(successDetailsDto,HttpStatus.CREATED);
	}
	
	
	/**
	 * Update business expense type.
	 *
	 * @param tenantId - the tenant id
	 * @param id - the id
	 * @param businessExpenseTypeUpdateResource - the business expense type update resource
	 * @return the response entity
	 */
	@PutMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> updateBusinessExpenseType(@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id,
			@Valid @RequestBody BusinessExpenseTypeUpdateResource businessExpenseTypeUpdateResource) {
		if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new UserNotFound(environment.getProperty(userNotFound));
		if (businessExpenseTypeService.existsById(id)) {
			businessExpenseTypeService.updateAndValidateBusinessExpenseType(tenantId, LogginAuthentcation.getInstance().getUserName(), id, businessExpenseTypeUpdateResource);
			SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty(commonUpdated));
			return new ResponseEntity<>(successDetailsDto, HttpStatus.OK);
		} else {
			SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(successDetailsDto, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
	
	
	/**
	 * Search business expense type.
	 *
	 * @param tenantId - the tenant id
	 * @param searchq - the searchq
	 * @param businessTypeName - the business type name
	 * @param businessTypeCode - the business type code
	 * @param pageable - the pageable
	 * @return the page
	 */
	/*@GetMapping(value = "/{tenantId}/search")
	public Page<BusinessExpenseType> searchBusinessExpenseType(@PathVariable(value = "tenantId", required = true) String tenantId,
			//@RequestParam(value = "searchq", required = false) String searchq,
			@RequestParam(value = "businessTypeName", required = false) String businessTypeName,
			@RequestParam(value = "businessTypeCode", required = false) String businessTypeCode,
			@PageableDefault(size = 10) Pageable pageable) {
		if(pageable.getPageSize()>Constants.MAX_PAGEABLE_LENGTH) 
			throw new PageableLengthException(environment.getProperty(pageableLength));
		//return businessExpenseTypeService.searchBusinessExpenseType(searchq, businessTypeName, businessTypeCode, pageable);
		return businessExpenseTypeService.searchBusinessExpenseType(businessTypeName, businessTypeCode, pageable);
	}*/
	
	 @GetMapping(value = "/{tenantId}/workflow-items/search")
	    public Page<BusinessExpenseType> searchBusinessExpenseType(@PathVariable(value = "tenantId") String tenantId,	                                                                 
														    		@RequestParam(value = "businessTypeName", required = false) String businessTypeName,
																	@RequestParam(value = "businessTypeCode", required = false) String businessTypeCode,	                                                                 
	                                                                @PageableDefault(size = Constants.DEFAULT_PAGE_SIZE) Pageable pageable) {
	        if (pageable.getPageSize() > Constants.MAX_PAGEABLE_LENGTH)
	            throw new PageableLengthException(environment.getProperty(pageableLength));
	        return businessExpenseTypeService.searchBusinessExpenseType(businessTypeName, businessTypeCode, pageable);
	    }
}
