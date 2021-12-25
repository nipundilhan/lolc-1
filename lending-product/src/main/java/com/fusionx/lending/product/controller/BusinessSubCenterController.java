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
import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.BusinessSubCenter;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.exception.UserNotFound;
import com.fusionx.lending.product.repository.BusinessSubCenterRepository;
import com.fusionx.lending.product.resources.BusinessSubCenterAddResource;
import com.fusionx.lending.product.resources.BusinessSubCenterUpdateResource;
import com.fusionx.lending.product.resources.SuccessAndErrorDetailsResource;
import com.fusionx.lending.product.service.BusinessSubCenterService;



/**
 * API Service related to Business Sub Center.
 *
 * @author Nipun Dilhan
 * @version 1.0.0
 * @since 1.0.0
 * <p>
 * <br/><br/>
 * <b>Change History : </b>
 * <br/><br/>
 * #        Date            Story Point     Task No     Author                  Description
 * <br/>
 * .....................................................................................................................<br/>
 * 1        28-08-2021      	             FXL-649          Nipun Dilhan      Created
 * <p>
 *
 */

@RestController
@RequestMapping(value = "/business-sub-center")
@CrossOrigin(origins = "*")
public class BusinessSubCenterController extends MessagePropertyBase{
	

    private static final String USER_NOT_FOUND = null;
	private BusinessSubCenterService businessSubCenterService;

    @Autowired
    public void setBusinessSubCenterService(BusinessSubCenterService businessSubCenterService) {
        this.businessSubCenterService = businessSubCenterService;
    }
    

	@Autowired
	private BusinessSubCenterRepository businessSubCenterRepository;
    
	/**
	 * Gets the all business sub centers.
	 *
	 * @param tenantId - the tenant id
	 * @return the all business sub centers
	 */
	@GetMapping(value = "/{tenantId}/all")
	public ResponseEntity<Object> getAllBusinessSubCenters(@PathVariable(value = "tenantId", required = true) String tenantId) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<BusinessSubCenter> businessSubCenterList = businessSubCenterRepository.findAll();
		if (!businessSubCenterList.isEmpty()) {
			return new ResponseEntity<>(businessSubCenterList, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Gets the business sub center by id.
	 *
	 * @param tenantId - the tenant id
	 * @param id - the id
	 * @return the business sub center by id
	 */
	@GetMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> getBusinessSubCenterById(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<BusinessSubCenter> isPresentBusinessCenter = businessSubCenterRepository.findById(id);
		if (isPresentBusinessCenter.isPresent()) {
			return new ResponseEntity<>(isPresentBusinessCenter, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Gets the business sub centers by status.
	 *
	 * @param tenantId - the tenant id
	 * @param status - the status
	 * @return the business sub centers by status
	 */
	@GetMapping(value = "/{tenantId}/status/{status}")
	public ResponseEntity<Object> getBusinessSubCentersByStatus(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "status", required = true) String status) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
 		if(status.equals("ACTIVE") || status.equals("INACTIVE")){
 			List<BusinessSubCenter> businessSubCenterList  = businessSubCenterRepository.findByStatus(CommonStatus.valueOf(status));
 			if (!businessSubCenterList.isEmpty()) {
 				return new ResponseEntity<>(businessSubCenterList, HttpStatus.OK);
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
	 * Gets the business sub centers by name.
	 *
	 * @param tenantId - the tenant id
	 * @param name - the name
	 * @return the business sub centers by name
	 */
	@GetMapping(value = "/{tenantId}/name/{name}")
	public ResponseEntity<Object> getBusinessSubCentersByName(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "name", required = true) String name) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<BusinessSubCenter> businessSubCenterList = businessSubCenterRepository.findByNameContaining(name);
		if (!businessSubCenterList.isEmpty()) {
			return new ResponseEntity<>(businessSubCenterList, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Gets the business sub center by code.
	 *
	 * @param tenantId - the tenant id
	 * @param code - the code
	 * @return the business sub center by code
	 */
	@GetMapping(value = "/{tenantId}/code/{code}")
	public ResponseEntity<Object> getBusinessCentersByCode(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "code", required = true) String code) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<BusinessSubCenter> isPresentBusinessCenter = businessSubCenterRepository.findByCode(code);
		if (isPresentBusinessCenter.isPresent()) {
			return new ResponseEntity<>(isPresentBusinessCenter, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	/**
	 * Gets the business sub centers by business center id.
	 *
	 * @param tenantId - the tenant id
	 * @param id - the business center id
	 * @return the business sub centers
	 */
	@GetMapping(value = "/{tenantId}/business-center/{id}")
	public ResponseEntity<Object> getByBusinessCentersId(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<BusinessSubCenter> businessSubCenterList = businessSubCenterRepository.findAllByBusinessCenterId(id);
		if (!businessSubCenterList.isEmpty()) {
			return new ResponseEntity<>(businessSubCenterList, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Gets the business sub centers by business center id.
	 *
	 * @param tenantId - the tenant id
	 * @param id - the business center id
	 * @return the business sub centers
	 */
	@GetMapping(value = "/{tenantId}/business-center/code/{code}")
	public ResponseEntity<Object> getByBusinessCentersId(
			@PathVariable(value = "tenantId") String tenantId,
			@PathVariable(value = "code") String code) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<BusinessSubCenter> businessSubCenterList = businessSubCenterService.findByBusinessCenterCode(code);
		if (!businessSubCenterList.isEmpty()) {
			return new ResponseEntity<>(businessSubCenterList, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Adds the business Sub Center.
	 *
	 * @param tenantId - the tenant id
	 * @param businessSubCenterAddResource - the business center add resource
	 * @return the response entity
	 */
	@PostMapping(value = "/{tenantId}")
	public ResponseEntity<Object> add(@PathVariable(value = "tenantId", required = true) String tenantId,
			@Valid @RequestBody BusinessSubCenterAddResource businessSubCenterAddResource) {
		if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new UserNotFound(environment.getProperty(USER_NOT_FOUND));
		BusinessSubCenter businessSubCenter = businessSubCenterService.create(tenantId, businessSubCenterAddResource);
		SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty(RECORD_CREATED), businessSubCenter.getId().toString());
		return new ResponseEntity<>(successDetailsDto,HttpStatus.CREATED);
	}
	
	
	/**
	 * Update business sub center.
	 *
	 * @param tenantId - the tenant id
	 * @param id - the id
	 * @param businessSubCenterUpdateResource - the business sub center update resource
	 * @return the response entity
	 */
	@PutMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> update(@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id,
			@Valid @RequestBody BusinessSubCenterUpdateResource businessSubCenterUpdateResource) {
		if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new UserNotFound(environment.getProperty(USER_NOT_FOUND));
		
		BusinessSubCenter businessSubCenter = businessSubCenterService.update(tenantId, id,businessSubCenterUpdateResource);

		SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty(RECORD_UPDATED),businessSubCenter.getId().toString());
		return new ResponseEntity<>(successDetailsDto, HttpStatus.OK);

	}

}
