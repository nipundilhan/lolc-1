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
import com.fusionx.lending.origination.domain.RiskParameterTemplate;
import com.fusionx.lending.origination.exception.UserNotFound;
import com.fusionx.lending.origination.resource.RiskGradingAddResource;
import com.fusionx.lending.origination.resource.RiskGradingUpdateResource;
import com.fusionx.lending.origination.resource.RiskParameterTemplateAddResource;
import com.fusionx.lending.origination.resource.RiskParameterTemplateUpdateResource;
import com.fusionx.lending.origination.resource.SuccessAndErrorDetailsResource;
import com.fusionx.lending.origination.service.RiskGradingService;
import com.fusionx.lending.origination.service.RiskParameterTemplateService;


/**
 * Risk Parameter Template Controller
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   16-08-2021      		     			SewwandiH      Created
 *    
 ********************************************************************************************************
 */

@RestController
@RequestMapping(value = "/risk-parameter-template")
@CrossOrigin(origins = "*")
public class RiskParameterTemplateController {

	@Autowired
	private Environment environment;
	
	@Autowired
	private RiskParameterTemplateService riskParameterTemplateService;
	
	private String userNotFound = "common.user-not-found";
	private String commonSaved = "common.saved";
	private String commonUpdated = "common.updated";
	private String pageableLength = "common.pageable-length";
	private String recordNotFound = "common.record-not-found";
	
	/**
	 * Get the all risk parameter templates.
	 *
	 * @param tenantId - the tenant id
	 * @return the all risk parameter templates.
	 */
	@GetMapping(value = "/{tenantId}/all")
	public ResponseEntity<Object> getAllRiskParameterTemplates(@PathVariable(value = "tenantId", required = true) String tenantId) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<RiskParameterTemplate> riskParaTemplate = riskParameterTemplateService.findAll();
		if (!riskParaTemplate.isEmpty()) {
			return new ResponseEntity<>((Collection<RiskParameterTemplate>) riskParaTemplate, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Get the parameter template by id.
	 *
	 * @param tenantId - the tenant id
	 * @param id - the id
	 * @return the risk parameter template by id
	 */
	@GetMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> getRiskParameterTemplateById(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<RiskParameterTemplate> isPresentRiskParaTemp = riskParameterTemplateService.findById(id);
		if (isPresentRiskParaTemp.isPresent()) {
			return new ResponseEntity<>(isPresentRiskParaTemp, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Get the risk parameter grading by status.
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
		List<RiskParameterTemplate> riskParameterTemplate = riskParameterTemplateService.findByStatus(status);
		if (!riskParameterTemplate.isEmpty()) {
			return new ResponseEntity<>((Collection<RiskParameterTemplate>) riskParameterTemplate, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Get the risk parameter template by name.
	 *
	 * @param tenantId - the tenant id
	 * @param name - the name
	 * @return the risk parameter template by name
	 */
	@GetMapping(value = "/{tenantId}/name/{name}")
	public ResponseEntity<Object> getRiskGradingByName(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "name", required = true) String name) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<RiskParameterTemplate>  riskParameterTemplate = riskParameterTemplateService.findByName(name);
		if (!riskParameterTemplate.isEmpty()) {
			return new ResponseEntity<>((Collection<RiskParameterTemplate>) riskParameterTemplate, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Get the risk parameter template by code.
	 *
	 * @param tenantId - the tenant id
	 * @param code - the code
	 * @return the risk parameter template by code
	 */
	@GetMapping(value = "/{tenantId}/code/{code}")
	public ResponseEntity<Object> getRiskGradingByCode(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "code", required = true) String code) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<RiskParameterTemplate> riskParameterTemplate = riskParameterTemplateService.findByCode(code);
		if (riskParameterTemplate.isPresent()) {
			return new ResponseEntity<>(riskParameterTemplate, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Add the risk parameter template.
	 *
	 * @param tenantId - the tenant id
	 * @param RiskParameterTemplateAddResource - the risk parameter template add resource
	 * @return the response entity
	 */
	@PostMapping(value = "/{tenantId}")
	public ResponseEntity<Object> addRiskParameterTemplate(@PathVariable(value = "tenantId", required = true) String tenantId,
			@Valid @RequestBody RiskParameterTemplateAddResource riskParameterTemplateAddResource) {
		if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new UserNotFound(environment.getProperty(userNotFound));
		Long id = riskParameterTemplateService.saveAndValidateRiskParameterTemplate(tenantId, LogginAuthentcation.getInstance().getUserName(), riskParameterTemplateAddResource);
		SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty(commonSaved));
		return new ResponseEntity<>(successDetailsDto,HttpStatus.CREATED);
	}
	
	/**
	 * update the risk parameter.
	 *
	 * @param tenantId - the tenant id
	 * @param RiskParameterTemplateUpdateResource - the risk parameter template update resource
	 * @return the response entity
	 */
	@PutMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> updateRiskParameterTemplate(@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id,
			@Valid @RequestBody RiskParameterTemplateUpdateResource riskParameterTemplateUpdateResource) {
		if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new UserNotFound(environment.getProperty(userNotFound));
		if (riskParameterTemplateService.existsById(id)) {
			riskParameterTemplateService.updateAndValidateRiskParameterTemplate(tenantId, LogginAuthentcation.getInstance().getUserName(), id, riskParameterTemplateUpdateResource);
			SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty(commonUpdated));
			return new ResponseEntity<>(successDetailsDto, HttpStatus.OK);
		} else {
			SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(successDetailsDto, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
}
