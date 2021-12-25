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
import com.fusionx.lending.origination.domain.BusinessSubCenterProductMap;
import com.fusionx.lending.origination.exception.UserNotFound;
import com.fusionx.lending.origination.resource.BusinessSubCenterProductMapAddResource;
import com.fusionx.lending.origination.resource.BusinessSubCenterProductMapUpdateResource;
import com.fusionx.lending.origination.resource.SuccessAndErrorDetailsResource;
import com.fusionx.lending.origination.service.BusinessSubCenterProductMapService;

/**
 * API Service related to Business Sub Center Product Mapping.
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
 * 1        31-08-2021      	             FXL-650          Nipun Dilhan      Created
 * <p>
 *
 */

@RestController
@RequestMapping(value = "/business-sub-center-product-map")
@CrossOrigin(origins = "*")
public class BusinessSubCenterProductMapController extends MessagePropertyBase{
	
    private BusinessSubCenterProductMapService businessSubCenterProductMapService;

    @Autowired
    public void setBusinessSubCenterProductMapService(BusinessSubCenterProductMapService businessSubCenterProductMapServic) {
        this.businessSubCenterProductMapService = businessSubCenterProductMapServic;
    }
    
    

	/**
	 * Gets the all.
	 *
	 * @param tenantId - the tenant id
	 * @return the all business-sub-center product
	 */
	@GetMapping(value = "/{tenantId}/all")
	public ResponseEntity<Object> getAllBusinessSubCenterProducts(@PathVariable(value = "tenantId", required = true) String tenantId) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<BusinessSubCenterProductMap> businessSubCenterProductMapList = businessSubCenterProductMapService.findAll();
		if (!businessSubCenterProductMapList.isEmpty()) {
			return new ResponseEntity<>(businessSubCenterProductMapList, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Gets the business-sub-center  product by   id.
	 *
	 * @param tenantId - the tenant id
	 * @param id - the id
	 * @return the business-sub-center product by  id
	 */
	@GetMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> getBusinessSubCenterProductMapById(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<BusinessSubCenterProductMap> businessSubCenterProductMapOptional = businessSubCenterProductMapService.findById(id);
		if (businessSubCenterProductMapOptional.isPresent()) {
			return new ResponseEntity<>(businessSubCenterProductMapOptional.get(), HttpStatus.OK);
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
	 * @return the business-sub-center-product id. by status
	 */
	@GetMapping(value = "/{tenantId}/status/{status}")
	public ResponseEntity<Object> getBusinessSubCenterProductMapsByStatus(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "status", required = true) String status) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
 		if(status.equals("ACTIVE") || status.equals("INACTIVE")){
 			List<BusinessSubCenterProductMap> businessSubCenterProductMapList = businessSubCenterProductMapService.findByStatus(status);
 			if (!businessSubCenterProductMapList.isEmpty()) {
 				return new ResponseEntity<>(businessSubCenterProductMapList, HttpStatus.OK);
 			} else {
 				responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
 				return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
 			}
 		}else{
	 		responseMessage.setMessages(environment.getProperty("invalid-status"));
			return new ResponseEntity<>(responseMessage, HttpStatus.UNPROCESSABLE_ENTITY);
	 	}
	}
	
	/**
	 * Gets BusinessCenterProduct by business-center id.
	 *
	 * @param tenantId - the tenant id
	 * @param id - the  business-center id.
	 * @return  business--sub-center-product
	 */
	@GetMapping(value = "/{tenantId}/business-sub-center/{id}")
	public ResponseEntity<Object> getBusinessCenterProductMapsByName(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<BusinessSubCenterProductMap> businessSubCenterProductMapList = businessSubCenterProductMapService.findByBusinessSubCenterId(id);
		if (!businessSubCenterProductMapList.isEmpty()) {
			return new ResponseEntity<>(businessSubCenterProductMapList, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	


    
	
	/**
	 * Adds.
	 *
	 * @param tenantId - the tenant id
	 * @param BusinessSubCenterProductMapAddResource - the business sub center product map add resource
	 * @return the response entity
	 */
	@PostMapping(value = "/{tenantId}/business-sub-center/{businessSubCenterId}")
	public ResponseEntity<Object> addBusinessCenterProductMap(@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "businessSubCenterId", required = true) Long businessSubCenterId,
			@Valid @RequestBody BusinessSubCenterProductMapAddResource businessSubCenterProductMapAddResource) {
		if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new UserNotFound(environment.getProperty(USER_NOT_FOUND));
		Long id = businessSubCenterProductMapService.create(tenantId, businessSubCenterProductMapAddResource, businessSubCenterId);
		SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty(RECORD_CREATED), id.toString());
		return new ResponseEntity<>(successDetailsDto,HttpStatus.CREATED);
	}
	
	
	/**
	 * Update 
	 *
	 * @param tenantId - the tenant id
	 * @param id - the id
	 * @param businessSubCenterProductMapUpdateResource - the business type update resource
	 * @return the response entity
	 */
	@PutMapping(value = "/{tenantId}/business-sub-center/{businessSubCenterId}")
	public ResponseEntity<Object> updateBusinessCenterProductMap(@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "businessSubCenterId", required = true) Long businessSubCenterId,
			@Valid @RequestBody  BusinessSubCenterProductMapUpdateResource businessSubCenterProductMapUpdateResource) {
		if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new UserNotFound(environment.getProperty(USER_NOT_FOUND));

		businessSubCenterProductMapService.updateBusinessSubCenterProductMap(tenantId, businessSubCenterId, businessSubCenterProductMapUpdateResource);
		SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty(RECORD_UPDATED));
		return new ResponseEntity<>(successDetailsDto, HttpStatus.OK);

	}

}
