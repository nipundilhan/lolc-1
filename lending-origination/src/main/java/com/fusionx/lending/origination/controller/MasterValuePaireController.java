package com.fusionx.lending.origination.controller;

/**
 * Master value paire Controller
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   16-08-2021      		     			SewwandiH    Created
 *    2   23-09-2021   FXL-641   	 FXL-792    Dilki        Edited
 *    
 ********************************************************************************************************
 */
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
import com.fusionx.lending.origination.domain.MasterValuePaire;
import com.fusionx.lending.origination.exception.UserNotFound;
import com.fusionx.lending.origination.resource.MasterValuePaireAddResource;
import com.fusionx.lending.origination.resource.MasterValuePaireUpdateResource;
import com.fusionx.lending.origination.resource.SuccessAndErrorDetailsResource;
import com.fusionx.lending.origination.service.MasterValuePaireService;

@RestController
@RequestMapping(value = "/master-value-paire")
@CrossOrigin(origins = "*")

public class MasterValuePaireController extends MessagePropertyBase {

	@Autowired
	private MasterValuePaireService masterValuePaireService;

	/**
	 * Get the all master value paires.
	 *
	 * @param tenantId
	 * @return the all master value paires.
	 */
	@GetMapping(value = "/{tenantId}/all")
	public ResponseEntity<Object> getAllMasterValuePaires(
			@PathVariable(value = "tenantId", required = true) String tenantId) {

		List<MasterValuePaire> masterValue = masterValuePaireService.findAll();
		if (!masterValue.isEmpty()) {
			return new ResponseEntity<>(masterValue, HttpStatus.OK);
		} else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * Get the value paire by id.
	 *
	 * @param tenantId
	 * @param id
	 * @return the value paire by id
	 */
	@GetMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> getValuePaireById(@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id) {

		Optional<MasterValuePaire> isPresentValuePaire = masterValuePaireService.findById(id);
		if (isPresentValuePaire.isPresent()) {
			return new ResponseEntity<>(isPresentValuePaire, HttpStatus.OK);
		} else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * Get the value paire by status.
	 *
	 * @param tenantId
	 * @param status
	 * @return the value paire by status
	 */
	@GetMapping(value = "/{tenantId}/status/{status}")
	public ResponseEntity<Object> getValuePaireByStatus(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "status", required = true) String status) {

		List<MasterValuePaire> valuePaire = masterValuePaireService.findByStatus(status);
		if (!valuePaire.isEmpty()) {
			return new ResponseEntity<>(valuePaire, HttpStatus.OK);
		} else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * Get the value paire by name.
	 *
	 * @param tenantId
	 * @param name
	 * @return the value paire by name
	 */
	@GetMapping(value = "/{tenantId}/name/{name}")
	public ResponseEntity<Object> getValuePaireByName(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "name", required = true) String name) {

		List<MasterValuePaire> valuePaire = masterValuePaireService.findByName(name);
		if (!valuePaire.isEmpty()) {
			return new ResponseEntity<>(valuePaire, HttpStatus.OK);
		} else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * Get the value Paire by code.
	 *
	 * @param tenantId
	 * @param code
	 * @return the value paire by code
	 */
	@GetMapping(value = "/{tenantId}/code/{code}")
	public ResponseEntity<Object> getValuePaireByCode(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "code", required = true) String code) {

		Optional<MasterValuePaire> valuePaire = masterValuePaireService.findByCode(code);
		if (valuePaire.isPresent()) {
			return new ResponseEntity<>(valuePaire, HttpStatus.OK);
		} else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * Add the value Paire.
	 *
	 * @param tenantId
	 * @param masterValuePaireAddResource
	 * @return the response entity
	 */
	@PostMapping(value = "/{tenantId}")
	public ResponseEntity<Object> addValuePaire(@PathVariable(value = "tenantId", required = true) String tenantId,
			@Valid @RequestBody MasterValuePaireAddResource masterValuePaireAddResource) {
		if (LogginAuthentcation.getInstance().getUserName() == null
				|| LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new UserNotFound(environment.getProperty(USER_NOT_FOUND));
		masterValuePaireService.saveAndValidateValuePaire(tenantId, LogginAuthentcation.getInstance().getUserName(),
				masterValuePaireAddResource);
		SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(
				environment.getProperty(RECORD_CREATED));
		return new ResponseEntity<>(successDetailsDto, HttpStatus.CREATED);
	}

	/**
	 * update the value Paire.
	 *
	 * @param tenantId
	 * @param masterValuePaireUpdateResource
	 * @return the response entity
	 */
	@PutMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> updateValuePaire(@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id,
			@Valid @RequestBody MasterValuePaireUpdateResource masterValuePaireUpdateResource) {
		if (LogginAuthentcation.getInstance().getUserName() == null
				|| LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new UserNotFound(environment.getProperty(USER_NOT_FOUND));
		Optional<MasterValuePaire> isPresentMasterValuePaire = this.masterValuePaireService.findById(id);
		if (isPresentMasterValuePaire.isPresent()) {
			masterValuePaireService.updateValuePaire(tenantId, LogginAuthentcation.getInstance().getUserName(),
					masterValuePaireUpdateResource, id);
			SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(
					environment.getProperty(RECORD_UPDATED));
			return new ResponseEntity<>(successDetailsDto, HttpStatus.OK);
		} else {
			SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(
					environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(successDetailsDto, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

}
