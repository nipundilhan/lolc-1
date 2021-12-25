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
import com.fusionx.lending.origination.domain.FieldSetup;
import com.fusionx.lending.origination.domain.RiskGrading;
import com.fusionx.lending.origination.domain.RiskTemplate;
import com.fusionx.lending.origination.exception.UserNotFound;
import com.fusionx.lending.origination.resource.FieldSetupAddResource;
import com.fusionx.lending.origination.resource.FieldSetupUpdateResource;
import com.fusionx.lending.origination.resource.RiskGradingAddResource;
import com.fusionx.lending.origination.resource.RiskGradingUpdateResource;
import com.fusionx.lending.origination.resource.RiskTemplateAddResource;
import com.fusionx.lending.origination.resource.RiskTemplateUpdateResource;
import com.fusionx.lending.origination.resource.SuccessAndErrorDetailsResource;
import com.fusionx.lending.origination.service.FieldSetupService;
import com.fusionx.lending.origination.service.RiskGradingService;
import com.fusionx.lending.origination.service.RiskTemplateService;

/**
 * Credit risk template.
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   16-08-2021      		     			SewwandiH      Created
 *    
 ********************************************************************************************************
 */

@RestController
@RequestMapping(value = "/risk-template")
@CrossOrigin(origins = "*")
public class RiskTemplateController {
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private RiskTemplateService riskTemplateService;
	
	private String userNotFound = "common.user-not-found";
	private String commonSaved = "common.saved";
	private String commonUpdated = "common.updated";
	private String pageableLength = "common.pageable-length";
	private String recordNotFound = "common.record-not-found";

	
	/**
	 * Get the all Risk Templates.
	 *
	 * @param tenantId - the tenant id
	 * @return the all Risk Template.
	 */
	@GetMapping(value = "/{tenantId}/all")
	public ResponseEntity<Object> getAllRiskTemplate(@PathVariable(value = "tenantId", required = true) String tenantId) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<RiskTemplate> riskTemplate = riskTemplateService.findAll();
		if (!riskTemplate.isEmpty()) {
			return new ResponseEntity<>((Collection<RiskTemplate>) riskTemplate, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Get the Risk Template by id.
	 *
	 * @param tenantId - the tenant id
	 * @param id - the id
	 * @return the Risk Template by id
	 */
	@GetMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> getRiskTemplateById(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<RiskTemplate> isPresentRiskTemplate = riskTemplateService.findById(id);
		if (isPresentRiskTemplate.isPresent()) {
			return new ResponseEntity<>(isPresentRiskTemplate, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Get the Risk Template by status.
	 *
	 * @param tenantId - the tenant id
	 * @param status - the status
	 * @return the Risk Template by status
	 */
	@GetMapping(value = "/{tenantId}/status/{status}")
	public ResponseEntity<Object> getRiskTemplateByStatus(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "status", required = true) String status) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<RiskTemplate> riskTemplate = riskTemplateService.findByStatus(status);
		if (!riskTemplate.isEmpty()) {
			return new ResponseEntity<>((Collection<RiskTemplate>) riskTemplate, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Get the Risk Template by  name.
	 *
	 * @param tenantId - the tenant id
	 * @param name - the name
	 * @return the Risk Template by name
	 */
	@GetMapping(value = "/{tenantId}/name/{name}")
	public ResponseEntity<Object> getRiskTemplateByName(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "name", required = true) String name) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<RiskTemplate> riskTemplate = riskTemplateService.findByName(name);
		if (!riskTemplate.isEmpty()) {
			return new ResponseEntity<>((Collection<RiskTemplate>) riskTemplate, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Get the Risk template by code.
	 *
	 * @param tenantId - the tenant id
	 * @param code - the code
	 * @return the Risk template by code
	 */
	@GetMapping(value = "/{tenantId}/code/{code}")
	public ResponseEntity<Object> getRiskTemplateByCode(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "code", required = true) String code) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<RiskTemplate> riskTemplate = riskTemplateService.findByCode(code);
		if (riskTemplate.isPresent()) {
			return new ResponseEntity<>(riskTemplate, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Add the Risk Template.
	 *
	 * @param tenantId - the tenant id
	 * @param riskTemplateAddResource - the Risk template request
	 * @return the response entity
	 */
	@PostMapping(value = "/{tenantId}")
	public ResponseEntity<Object> addRiskTemplate(@PathVariable(value = "tenantId", required = true) String tenantId,
			@Valid @RequestBody RiskTemplateAddResource riskTemplateAddResource) {
		if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new UserNotFound(environment.getProperty(userNotFound));
		Long id = riskTemplateService.saveAndValidateRiskTemplate(tenantId, LogginAuthentcation.getInstance().getUserName(), riskTemplateAddResource);
		SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty(commonSaved));
		return new ResponseEntity<>(successDetailsDto,HttpStatus.CREATED);
	}
	
	/**
	 * update the risk template.
	 *
	 * @param tenantId - the tenant id
	 * @param riskTemplateUpdateResource - the risk template update resource
	 * @return the response entity
	 */
	@PutMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> updateRiskTemplate(@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id,
			@Valid @RequestBody RiskTemplateUpdateResource riskTemplateUpdateResource) {
		if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new UserNotFound(environment.getProperty(userNotFound));
		if (riskTemplateService.existsById(id)) {
			riskTemplateService.updateAndValidateRiskTemplate(tenantId, LogginAuthentcation.getInstance().getUserName(), id, riskTemplateUpdateResource);
			SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty(commonUpdated));
			return new ResponseEntity<>(successDetailsDto, HttpStatus.OK);
		} else {
			SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(successDetailsDto, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
}
