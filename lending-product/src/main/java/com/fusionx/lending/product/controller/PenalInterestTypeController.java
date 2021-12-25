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
import com.fusionx.lending.product.domain.PenalInterestType;
import com.fusionx.lending.product.resources.PenalInterestTypeAddResource;
import com.fusionx.lending.product.resources.PenalInterestTypeUpdateResource;
import com.fusionx.lending.product.resources.SuccessAndErrorDetails;
import com.fusionx.lending.product.resources.SuccessAndErrorDetailsResource;
import com.fusionx.lending.product.service.PenalInterestTypeService;

/**
 * Penal Interest Type Controller
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   16-08-2020   	                        Dilhan      Created
 *    
 ********************************************************************************************************
 */

@RestController
@RequestMapping("/penal-interest-type")
@CrossOrigin("*")
public class PenalInterestTypeController extends MessagePropertyBase {

	@Autowired
	private PenalInterestTypeService penalInterestTypeService;

	/**
	 * @author Dilhan
	 * 
	 * get all Penal Interest Type
	 * @param @PathVariable{tenantId}
	 * @param @PathVariable{all}
	 * @return List
	 **/
	@GetMapping("/{tenantId}/all")
	public ResponseEntity<Object> getAllPenalInterestType(
			@PathVariable(value = "tenantId", required = true) String tenantId) {

		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<PenalInterestType> isPresentPenalInterestType = penalInterestTypeService.getAll();
		if (!isPresentPenalInterestType.isEmpty()) {
			return new ResponseEntity<>((Collection<PenalInterestType>) isPresentPenalInterestType, HttpStatus.OK);
		} else {
			responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}

	}

	/**
	 * get Penal Interest Type by id
	 * 
	 * @param @pathVariable{tenantId}
	 * @param @pathVariable{id}
	 * @return Optional
	 **/
	@GetMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> getPenalInterestTypeById(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<PenalInterestType> isPresentPenalInterestType = penalInterestTypeService.getById(id);
		if (isPresentPenalInterestType.isPresent()) {
			return new ResponseEntity<>(isPresentPenalInterestType.get(), HttpStatus.OK);
		} else {
			responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * get Penal Interest Type by code
	 * 
	 * @param @pathVariable{tenantId}
	 * @param @pathVariable{code}
	 * @return Optional
	 **/
	@GetMapping(value = "/{tenantId}/code/{code}")
	public ResponseEntity<Object> getPenalInterestTypeByCode(@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "code", required = true) String code) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<PenalInterestType> isPresentPenalInterestType = penalInterestTypeService.getByCode(code);
		if (isPresentPenalInterestType.isPresent()) {
			return new ResponseEntity<>(isPresentPenalInterestType.get(), HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * get Penal Interest Type by status
	 * 
	 * @param @PathVariable{tenantId}
	 * @param @PathVariable{status}
	 * @return Option
	 */
	@GetMapping(value = "/{tenantId}/status/{status}")
	public ResponseEntity<Object> getPenalInterestTypeByStatus(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "status", required = true) String status) {
		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();

		if (status.equals("ACTIVE") || status.equals("INACTIVE")) {
			List<PenalInterestType> isPresentPenalInterestType = penalInterestTypeService.getByStatus(status);
			int size = isPresentPenalInterestType.size();
			if (size > 0) {
				return new ResponseEntity<>(isPresentPenalInterestType, HttpStatus.OK);
			} else {
				responseMessage.setMessages(RECORD_NOT_FOUND);
				return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
			}
		} else {
			responseMessage.setMessages(environment.getProperty(COMMON_STATUS_PATTERN));
			return new ResponseEntity<>(responseMessage, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
	
	/**
	 * get Penal Interest Type by name
	 * @param @pathVariable{tenantId}
	 * @param @pathVariable {name}
	 * @return List
	 **/
	@GetMapping(value = "/{tenantId}/name/{name}")
	public ResponseEntity<Object> getPenalInterestTypeByName(@PathVariable(value = "tenantId", required = true) String tenantId,
															        @PathVariable(value = "name", required = true) String name){
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<PenalInterestType> isPresentLoanPenalInterestType = penalInterestTypeService.getByName(name);
		if(!isPresentLoanPenalInterestType.isEmpty()) {
			return new ResponseEntity<>(isPresentLoanPenalInterestType, HttpStatus.OK);
		}
		else {
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * save Penal Interest Type
	 * 
	 * @param @PathVariable{tenantId}
	 * @param @RequestBody{PenalInterestTypeAddResource}
	 * @return SuccessAndErrorDetailsDto
	 */
	@PostMapping("/{tenantId}")
	public ResponseEntity<Object> addPenalInterestType(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@Valid @RequestBody PenalInterestTypeAddResource penalInterestTypeAddResource) {
		PenalInterestType penalInterestType = penalInterestTypeService.addPenalInterestType(tenantId,
				penalInterestTypeAddResource);
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource(
				getEnvironment().getProperty(RECORD_CREATED), Long.toString(penalInterestType.getId()));
		return new ResponseEntity<>(responseMessage, HttpStatus.CREATED);
	}

	/**
	 * update Penal Interest Type
	 * 
	 * @param @PathVariable{tenantId}
	 * @param @RequestBody{PenalInterestTypeUpdateResource}
	 * @return SuccessAndErrorDetailsDto
	 */
	@PutMapping(value = "{tenantId}/{id}")
	public ResponseEntity<Object> updatePenalInterestType(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id,
			@Valid @RequestBody PenalInterestTypeUpdateResource penalInterestTypeUpdateResource) {
		SuccessAndErrorDetailsResource successAndErrorDetailsResource = new SuccessAndErrorDetailsResource();
		Optional<PenalInterestType> isPresentPenalInterestType = penalInterestTypeService.getById(id);
		if (isPresentPenalInterestType.isPresent()) {
			PenalInterestType penalInterestType = penalInterestTypeService.updatePenalInterestType(tenantId, id,
					penalInterestTypeUpdateResource);
			successAndErrorDetailsResource = new SuccessAndErrorDetailsResource(
					getEnvironment().getProperty(RECORD_UPDATED), penalInterestType.getId().toString());
			return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.OK);
		} else {
			successAndErrorDetailsResource.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

}
