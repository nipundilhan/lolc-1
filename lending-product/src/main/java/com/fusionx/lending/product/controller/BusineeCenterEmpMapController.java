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
import com.fusionx.lending.product.domain.BusinessCenterEmpMap;
import com.fusionx.lending.product.exception.UserNotFound;
import com.fusionx.lending.product.resources.BusinessCenterEmpMapAddResource;
import com.fusionx.lending.product.resources.BusinessCenterEmpMapUpdateResource;
import com.fusionx.lending.product.resources.SuccessAndErrorDetailsResource;
import com.fusionx.lending.product.service.BusinessCenterEmpMapService;


/**
 * Business Center emp
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   10-08-2021      		     FX-	   Thsmokshi      Created
 *    
 ********************************************************************************************************
 */
@RestController
@RequestMapping(value = "/business-center-employee")
@CrossOrigin(origins = "*")
public class BusineeCenterEmpMapController {

	@Autowired
	private Environment environment;
	
	@Autowired
	private BusinessCenterEmpMapService service;
	
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
		List<BusinessCenterEmpMap> BusinessCenterEmpMap = service.findAll();
		if (!BusinessCenterEmpMap.isEmpty()) {
			return new ResponseEntity<>((Collection<BusinessCenterEmpMap>) BusinessCenterEmpMap, HttpStatus.OK);
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
	public ResponseEntity<Object> getBusinessCenterEmpMapById(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<BusinessCenterEmpMap> isPresentBusinessCenterEmpMap = service.findById(id);
		if (isPresentBusinessCenterEmpMap.isPresent()) {
			return new ResponseEntity<>(isPresentBusinessCenterEmpMap, HttpStatus.OK);
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
	public ResponseEntity<Object> getBusinessCenterEmpMapsByStatus(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "status", required = true) String status) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		if(status.equals("ACTIVE") || status.equals("INACTIVE")){
			List<BusinessCenterEmpMap> businessCenterEmpMap = service.findByStatus(status);
			if (!businessCenterEmpMap.isEmpty()) {
				return new ResponseEntity<>((Collection<BusinessCenterEmpMap>) businessCenterEmpMap, HttpStatus.OK);
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
	public ResponseEntity<Object> getBusinessCenterEmpMapsByName(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<BusinessCenterEmpMap> BusinessCenterEmpMap = service.findByBusinessCenterId(id);
		if (!BusinessCenterEmpMap.isEmpty()) {
			return new ResponseEntity<>((Collection<BusinessCenterEmpMap>) BusinessCenterEmpMap, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	

	
	/**
	 * Adds.
	 *
	 * @param tenantId - the tenant id
	 * @param BusinessCenterEmpMapAddResource - the business type add resource
	 * @return the response entity
	 */
	@PostMapping(value = "/{tenantId}/{businessCenterId}")
	public ResponseEntity<Object> addBusinessCenterEmpMap(@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "businessCenterId", required = true) Long businessCenterId,
			@Valid @RequestBody BusinessCenterEmpMapAddResource BusinessCenterEmpMapAddResource) {
		if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new UserNotFound(environment.getProperty(userNotFound));
		Long id = service.saveAndValidateBusinessCenterEmpMap(tenantId, LogginAuthentcation.getInstance().getUserName(), BusinessCenterEmpMapAddResource,businessCenterId);
		SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty(commonSaved), id.toString());
		return new ResponseEntity<>(successDetailsDto,HttpStatus.CREATED);
	}
	
	/**
	 * Update 
	 *
	 * @param tenantId - the tenant id
	 * @param id - the id
	 * @param BusinessCenterEmpMapUpdateResource - the business type update resource
	 * @return the response entity
	 */
	@PutMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> updateBusinessCenterEmpMap(@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id,
			@Valid @RequestBody BusinessCenterEmpMapUpdateResource BusinessCenterEmpMapUpdateResource) {
		if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new UserNotFound(environment.getProperty(userNotFound));

			service.updateAndValidateBusinessCenterEmpMap(tenantId, LogginAuthentcation.getInstance().getUserName(), id, BusinessCenterEmpMapUpdateResource);
			SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty(commonUpdated));
			return new ResponseEntity<>(successDetailsDto, HttpStatus.OK);		
	}

}