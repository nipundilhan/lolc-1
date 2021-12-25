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
import com.fusionx.lending.origination.domain.RiskGrading;
import com.fusionx.lending.origination.exception.UserNotFound;
import com.fusionx.lending.origination.resource.RiskGradingAddResource;
import com.fusionx.lending.origination.resource.RiskGradingUpdateResource;
import com.fusionx.lending.origination.resource.SuccessAndErrorDetailsResource;
import com.fusionx.lending.origination.service.RiskGradingService;


/**
 * Risk Grading Controller
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   16-08-2021      		     			SewwandiH      Created
 *    
 ********************************************************************************************************
 */

@RestController
@RequestMapping(value = "/risk-grading")
@CrossOrigin(origins = "*")

public class RiskGradingController {
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private RiskGradingService riskGradingService;
	
	private String userNotFound = "common.user-not-found";
	private String commonSaved = "common.saved";
	private String commonUpdated = "common.updated";
	private String pageableLength = "common.pageable-length";
	private String recordNotFound = "common.record-not-found";
	
	
	/**
	 * Get the all risk gradings.
	 *
	 * @param tenantId - the tenant id
	 * @return the all risk gradings.
	 */
	@GetMapping(value = "/{tenantId}/all")
	public ResponseEntity<Object> getAllRiskGrading(@PathVariable(value = "tenantId", required = true) String tenantId) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<RiskGrading> riskGrading = riskGradingService.findAll();
		if (!riskGrading.isEmpty()) {
			return new ResponseEntity<>((Collection<RiskGrading>) riskGrading, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Get the risk grading by id.
	 *
	 * @param tenantId - the tenant id
	 * @param id - the id
	 * @return the risk grading by id
	 */
	@GetMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> getRiskGradingById(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<RiskGrading> isPresentRiskGrading = riskGradingService.findById(id);
		if (isPresentRiskGrading.isPresent()) {
			return new ResponseEntity<>(isPresentRiskGrading, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Get the risk grading by status.
	 *
	 * @param tenantId - the tenant id
	 * @param status - the status
	 * @return the risk grading by status
	 */
	@GetMapping(value = "/{tenantId}/status/{status}")
	public ResponseEntity<Object> getRiskGradingByStatus(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "status", required = true) String status) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<RiskGrading> riskGrading = riskGradingService.findByStatus(status);
		if (!riskGrading.isEmpty()) {
			return new ResponseEntity<>((Collection<RiskGrading>) riskGrading, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Get the risk grading by name.
	 *
	 * @param tenantId - the tenant id
	 * @param name - the name
	 * @return the risk grading by name
	 */
	@GetMapping(value = "/{tenantId}/name/{name}")
	public ResponseEntity<Object> getRiskGradingByName(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "name", required = true) String name) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<RiskGrading> riskGrading = riskGradingService.findByName(name);
		if (!riskGrading.isEmpty()) {
			return new ResponseEntity<>((Collection<RiskGrading>) riskGrading, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Get the risk grading by code.
	 *
	 * @param tenantId - the tenant id
	 * @param code - the code
	 * @return the risk grading by code
	 */
	@GetMapping(value = "/{tenantId}/code/{code}")
	public ResponseEntity<Object> getRiskGradingByCode(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "code", required = true) String code) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<RiskGrading> riskGrading = riskGradingService.findByCode(code);
		if (riskGrading.isPresent()) {
			return new ResponseEntity<>(riskGrading, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * Add the risk grading.
	 *
	 * @param tenantId - the tenant id
	 * @param riskGradingAddResource - the risk Grading add resource
	 * @return the response entity
	 */
	@PostMapping(value = "/{tenantId}")
	public ResponseEntity<Object> addRiskGrading(@PathVariable(value = "tenantId", required = true) String tenantId,
			@Valid @RequestBody RiskGradingAddResource riskGradingAddResource) {
		if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new UserNotFound(environment.getProperty(userNotFound));
		Long id = riskGradingService.saveAndValidateRiskGrading(tenantId, LogginAuthentcation.getInstance().getUserName(), riskGradingAddResource);
		SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty(commonSaved));
		return new ResponseEntity<>(successDetailsDto,HttpStatus.CREATED);
	}
	
	/**
	 * update the risk grading.
	 *
	 * @param tenantId - the tenant id
	 * @param riskGradingAddResource - the risk grading update resource
	 * @return the response entity
	 */
	@PutMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> updateRiskGrading(@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id,
			@Valid @RequestBody RiskGradingUpdateResource riskGradingUpdateResource) {
		if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new UserNotFound(environment.getProperty(userNotFound));
		if (riskGradingService.existsById(id)) {
			riskGradingService.updateAndValidateRiskGrading(tenantId, LogginAuthentcation.getInstance().getUserName(), id, riskGradingUpdateResource);
			SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty(commonUpdated));
			return new ResponseEntity<>(successDetailsDto, HttpStatus.OK);
		} else {
			SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(successDetailsDto, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
}
