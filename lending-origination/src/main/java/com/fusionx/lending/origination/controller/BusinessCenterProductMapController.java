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
import com.fusionx.lending.origination.domain.BusinessCenterProductMap;
import com.fusionx.lending.origination.domain.BusinessType;
import com.fusionx.lending.origination.exception.PageableLengthException;
import com.fusionx.lending.origination.exception.UserNotFound;
import com.fusionx.lending.origination.resource.BusinessCenterProductMapAddResource;
import com.fusionx.lending.origination.resource.BusinessCenterProductMapUpdateResource;
import com.fusionx.lending.origination.resource.BusinessTypeAddResource;
import com.fusionx.lending.origination.resource.BusinessTypeUpdateResource;
import com.fusionx.lending.origination.resource.SuccessAndErrorDetailsResource;
import com.fusionx.lending.origination.service.BusinessCenterProductMapService;
import com.fusionx.lending.origination.service.BusinessTypeService;

/**
 * Business Center product map
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   10-08-2021      		     FX-	   Thsmokshi      Created
 *    
 ********************************************************************************************************
 */
@RestController
@RequestMapping(value = "/business-center-product")
@CrossOrigin(origins = "*")
public class BusinessCenterProductMapController {

	
	@Autowired
	private Environment environment;
	
	@Autowired
	private BusinessCenterProductMapService service;
	
	private String userNotFound = "common.user-not-found";
	private String commonSaved = "common.saved";
	private String commonUpdated = "common.updated";
	private String pageableLength = "common.pageable-length";
	private String recordNotFound = "common.record-not-found";
	
	/**
	 * Gets the all.
	 *
	 * @param tenantId - the tenant id
	 * @return the all business-center
	 */
	@GetMapping(value = "/{tenantId}/all")
	public ResponseEntity<Object> getAllBusinessTypes(@PathVariable(value = "tenantId", required = true) String tenantId) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<BusinessCenterProductMap> BusinessCenterProductMap = service.findAll();
		if (!BusinessCenterProductMap.isEmpty()) {
			return new ResponseEntity<>((Collection<BusinessCenterProductMap>) BusinessCenterProductMap, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Gets the business-center  by id.
	 *
	 * @param tenantId - the tenant id
	 * @param id - the id
	 * @return the business center by id
	 */
	@GetMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> getBusinessCenterProductMapById(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<BusinessCenterProductMap> isPresentBusinessCenterProductMap = service.findById(id);
		if (isPresentBusinessCenterProductMap.isPresent()) {
			return new ResponseEntity<>(isPresentBusinessCenterProductMap, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Gets the business-center id. by status.
	 *
	 * @param tenantId - the tenant id
	 * @param status - the status
	 * @return the business-center id. by status
	 */
	@GetMapping(value = "/{tenantId}/status/{status}")
	public ResponseEntity<Object> getBusinessCenterProductMapsByStatus(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "status", required = true) String status) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		if(status.equals("ACTIVE") || status.equals("INACTIVE")){
		List<BusinessCenterProductMap> BusinessCenterProductMap = service.findByStatus(status);
		if (!BusinessCenterProductMap.isEmpty()) {
			return new ResponseEntity<>((Collection<BusinessCenterProductMap>) BusinessCenterProductMap, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
		}else{
	 		responseMessage.setMessages(environment.getProperty("invalid-status"));
			return new ResponseEntity<>(responseMessage, HttpStatus.UNPROCESSABLE_ENTITY);
	 	}
	}
	
	/**
	 * Gets thebusiness-center by business-center id.
	 *
	 * @param tenantId - the tenant id
	 * @param id - the  business-center id.
	 * @return  business-center
	 */
	@GetMapping(value = "/{tenantId}/business-center/{id}")
	public ResponseEntity<Object> getBusinessCenterProductMapsByName(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<BusinessCenterProductMap> BusinessCenterProductMap = service.findByBusinessCenterId(id);
		if (!BusinessCenterProductMap.isEmpty()) {
			return new ResponseEntity<>((Collection<BusinessCenterProductMap>) BusinessCenterProductMap, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	

	
	/**
	 * Adds.
	 *
	 * @param tenantId - the tenant id
	 * @param BusinessCenterProductMapAddResource - the business type add resource
	 * @return the response entity
	 */
	@PostMapping(value = "/{tenantId}/{businessCenterId}")
	public ResponseEntity<Object> addBusinessCenterProductMap(@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "businessCenterId", required = true) Long businessCenterId,
			@Valid @RequestBody BusinessCenterProductMapAddResource BusinessCenterProductMapAddResource) {
		if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new UserNotFound(environment.getProperty(userNotFound));
		Long id = service.saveAndValidateBusinessCenterProductMap(tenantId, LogginAuthentcation.getInstance().getUserName(), BusinessCenterProductMapAddResource,businessCenterId);
		SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty(commonSaved), id.toString());
		return new ResponseEntity<>(successDetailsDto,HttpStatus.CREATED);
	}
	
	/**
	 * Update 
	 *
	 * @param tenantId - the tenant id
	 * @param id - the id
	 * @param BusinessCenterProductMapUpdateResource - the business type update resource
	 * @return the response entity
	 */
	@PutMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> updateBusinessCenterProductMap(@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id,
			@Valid @RequestBody BusinessCenterProductMapUpdateResource BusinessCenterProductMapUpdateResource) {
		if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new UserNotFound(environment.getProperty(userNotFound));
		if (service.existsById(id)) {
			service.updateAndValidateBusinessCenterProductMap(tenantId, LogginAuthentcation.getInstance().getUserName(), id, BusinessCenterProductMapUpdateResource);
			SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty(commonUpdated));
			return new ResponseEntity<>(successDetailsDto, HttpStatus.OK);
		} else {
			SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(successDetailsDto, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}


}
