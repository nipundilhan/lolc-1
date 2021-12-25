package com.fusionx.lending.origination.controller;

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
import com.fusionx.lending.origination.domain.FacilityParameter;
import com.fusionx.lending.origination.exception.UserNotFound;
import com.fusionx.lending.origination.resource.FacilityCalculationRequestResource;
import com.fusionx.lending.origination.resource.SuccessAndErrorDetailsResource;
import com.fusionx.lending.origination.service.FacilityService;
import com.fusionx.lending.origination.service.LeadInfoService;

@RestController
@RequestMapping(value = "/facility")
@CrossOrigin(origins = "*")
public class FacilityController {

	@Autowired
	private Environment environment;
	
	@Autowired
	private FacilityService facilityService;
	
	@Autowired
	private LeadInfoService leadInfoService;
	
	private String userNotFound = "common.user-not-found";
	private String commonSaved = "common.saved";
	private String commonUpdated = "common.updated";
	private String pageableLength = "common.pageable-length";
	private String recordNotFound = "common.record-not-found";
	
	/**
	 * Gets the Facility Calculation Detail by leadId.
	 *
	 * @param tenantId - the tenant id
	 * @param leadId - the leadId
	 * @return the business type by id
	 */
	@GetMapping(value = "/{tenantId}/{leadId}")
	public ResponseEntity<Object> findFacilityCalculationDetailByLeadId(@PathVariable(value = "tenantId", required = true) String tenantId, @PathVariable(value = "leadId", required = true) Long leadId) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		FacilityParameter facilityParameter=facilityService.findFacilityCalculationDetailByLeadId(leadId);
		if (facilityParameter!=null) {
			return new ResponseEntity<>(facilityParameter, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Adds the Facility Calculation Detail.
	 *
	 * @param tenantId - the tenant id
	 * @param leadId - the lead id
	 * @param FacilityCalculationRequestResource
	 * @return the response entity
	 */
	@PostMapping(value = "/{tenantId}/{leadId}")
	public ResponseEntity<Object> saveFacility(@PathVariable(value = "tenantId", required = true) String tenantId, @PathVariable(value = "leadId", required = true) Long leadId,
			@Valid @RequestBody FacilityCalculationRequestResource facilityCalculationRequestResource) {
		if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new UserNotFound(environment.getProperty(userNotFound));
		if(leadInfoService.existsById(leadId)) {
			facilityService.saveFacility(tenantId, LogginAuthentcation.getInstance().getUserName(), leadId, facilityCalculationRequestResource);
			SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty(commonSaved));
			return new ResponseEntity<>(successDetailsDto,HttpStatus.CREATED);
		}else {
			SuccessAndErrorDetailsResource successDetailsDto=new SuccessAndErrorDetailsResource(environment.getProperty("invalid.leadId"));
	    	return new ResponseEntity<>(successDetailsDto,HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
	/**
	 * Update the Facility Calculation Detail.
	 *
	 * @param tenantId - the tenant id
	 * @param leadId - the lead id
	 * @param FacilityCalculationRequestResource
	 * @return the response entity
	 */
	@PutMapping(value = "/{tenantId}/{leadId}")
	public ResponseEntity<Object> updateFacility(@PathVariable(value = "tenantId", required = true) String tenantId, @PathVariable(value = "leadId", required = true) Long leadId,
			@Valid @RequestBody FacilityCalculationRequestResource facilityCalculationRequestResource) {
		if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new UserNotFound(environment.getProperty(userNotFound));
		if(leadInfoService.existsById(leadId)) {
			facilityService.updateFacility(tenantId, LogginAuthentcation.getInstance().getUserName(), leadId, facilityCalculationRequestResource);
			SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty(commonSaved));
			return new ResponseEntity<>(successDetailsDto,HttpStatus.OK);
		}else {
			SuccessAndErrorDetailsResource successDetailsDto=new SuccessAndErrorDetailsResource(environment.getProperty("invalid.leadId"));
	    	return new ResponseEntity<>(successDetailsDto,HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
}
