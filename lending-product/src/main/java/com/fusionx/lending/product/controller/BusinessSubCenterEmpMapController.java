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
import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.BusinessSubCenterEmpMap;
import com.fusionx.lending.product.exception.UserNotFound;
import com.fusionx.lending.product.resources.BusinessSubCenterEmpMapAddResource;
import com.fusionx.lending.product.resources.BusinessSubCenterEmpMapUpdateResource;
import com.fusionx.lending.product.resources.SuccessAndErrorDetailsResource;
import com.fusionx.lending.product.service.BusinessSubCenterEmpMapService;



@RestController
@RequestMapping(value = "/business-sub-center-employee")
@CrossOrigin(origins = "*")
public class BusinessSubCenterEmpMapController  extends MessagePropertyBase{
	
    private BusinessSubCenterEmpMapService businessSubCenterEmpMapService;

    
    @Autowired
    public void setBusinessSubCenterProductMapService(BusinessSubCenterEmpMapService businessSubCenterEmpMapServic) {
        this.businessSubCenterEmpMapService = businessSubCenterEmpMapServic;
    }
    
    
	/**
	 * Gets the all.
	 *
	 * @param tenantId - the tenant id
	 * @return the all business-center
	 */
	@GetMapping(value = "/{tenantId}/all")
	public ResponseEntity<Object> getAll(@PathVariable(value = "tenantId", required = true) String tenantId) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<BusinessSubCenterEmpMap> businessCenterEmpMapList = businessSubCenterEmpMapService.findAll();
		if (!businessCenterEmpMapList.isEmpty()) {
			return new ResponseEntity<>( businessCenterEmpMapList, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
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
	public ResponseEntity<Object> getById(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<BusinessSubCenterEmpMap> isPresentBusinessCenterEmpMap = businessSubCenterEmpMapService.findById(id);
		if (isPresentBusinessCenterEmpMap.isPresent()) {
			return new ResponseEntity<>(isPresentBusinessCenterEmpMap, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
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
	public ResponseEntity<Object> getByStatus(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "status", required = true) String status) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		if(status.equals("ACTIVE") || status.equals("INACTIVE")){
		List<BusinessSubCenterEmpMap> businessCenterEmpMapList = businessSubCenterEmpMapService.findByStatus(status);
			if (!businessCenterEmpMapList.isEmpty()) {
				return new ResponseEntity<>(businessCenterEmpMapList, HttpStatus.OK);
			} else {
				responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
				return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
			}
 		}else{
	 		responseMessage.setMessages(environment.getProperty("invalid-status"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
	 	}
	}
	
	/**
	 * Gets thebusiness-center by business-center id.
	 *
	 * @param tenantId - the tenant id
	 * @param id - the  business-center id.
	 * @return  business-center
	 */
	@GetMapping(value = "/{tenantId}/business-sub-center/{id}")
	public ResponseEntity<Object> getByusinessSubCenterId(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<BusinessSubCenterEmpMap> businessCenterEmpMapList = businessSubCenterEmpMapService.findByBusinessSubCenterId(id);
		if (!businessCenterEmpMapList.isEmpty()) {
			return new ResponseEntity<>(businessCenterEmpMapList, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
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
	@PostMapping(value = "/{tenantId}/business-sub-center/{businessSubCenterId}")
	public ResponseEntity<Object> addBusinessCenterEmpMap(@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "businessSubCenterId", required = true) Long businessSubCenterId,
			@Valid @RequestBody BusinessSubCenterEmpMapAddResource businessSubCenterEmpMapAddResource) {
		if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new UserNotFound(environment.getProperty(COMMON_USER_NOT_FOUND));
		Long id = businessSubCenterEmpMapService.saveAndValidateBusinessSubCenterEmpMap(tenantId, LogginAuthentcation.getInstance().getUserName(), businessSubCenterEmpMapAddResource,businessSubCenterId);
		SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty(RECORD_CREATED));
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
	@PutMapping(value = "/{tenantId}/business-sub-center/{id}")
	public ResponseEntity<Object> updateBusinessCenterEmpMap(@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id,
			@Valid @RequestBody BusinessSubCenterEmpMapUpdateResource businessSubCenterEmpMapUpdateResource) {
		if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new UserNotFound(environment.getProperty(COMMON_USER_NOT_FOUND));
	
			businessSubCenterEmpMapService.updateAndValidateBusinessCenterEmpMap(tenantId, LogginAuthentcation.getInstance().getUserName(), id, businessSubCenterEmpMapUpdateResource);
			SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty(RECORD_UPDATED));
			return new ResponseEntity<>(successDetailsDto, HttpStatus.OK);

	}

}
