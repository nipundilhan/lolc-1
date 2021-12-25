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
import com.fusionx.lending.origination.domain.CommonList;
import com.fusionx.lending.origination.exception.UserNotFound;
import com.fusionx.lending.origination.resource.CommonListAddResource;
import com.fusionx.lending.origination.resource.CommonListUpdateResource;
import com.fusionx.lending.origination.resource.SuccessAndErrorDetailsResource;
import com.fusionx.lending.origination.service.CommonListService;

/**
 * Common list Controller
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   18-12-2020      		     FX-5273	MiyuruW      Created
 *    
 ********************************************************************************************************
 */

@RestController
@RequestMapping(value = "/common-list")
@CrossOrigin(origins = "*")
public class CommonListController {

	@Autowired
	private Environment environment;
	
	@Autowired
	private CommonListService commonListService;
	
	private String userNotFound = "common.user-not-found";
	private String commonSaved = "common.saved";
	private String commonUpdated = "common.updated";
	private String recordNotFound = "common.record-not-found";
	
	/**
	 * Gets the all common lists.
	 *
	 * @param tenantId the tenant id
	 * @return the all common lists
	 */
	@GetMapping(value = "/{tenantId}/all")
	public ResponseEntity<Object> getAllCommonLists(@PathVariable(value = "tenantId", required = true) String tenantId) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<CommonList> commonList = commonListService.findAll();
		if (!commonList.isEmpty()) {
			return new ResponseEntity<>((Collection<CommonList>) commonList, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Gets the common list by id.
	 *
	 * @param tenantId - the tenant id
	 * @param id - the id
	 * @return the common list by id
	 */
	@GetMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> getCommonListById(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<CommonList> isPresentCommonList = commonListService.findById(id);
		if (isPresentCommonList.isPresent()) {
			return new ResponseEntity<>(isPresentCommonList, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Gets the common lists by status.
	 *
	 * @param tenantId - the tenant id
	 * @param status - the status
	 * @return the common lists by status
	 */
	@GetMapping(value = "/{tenantId}/status/{status}")
	public ResponseEntity<Object> getCommonListsByStatus(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "status", required = true) String status) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<CommonList> commonList = commonListService.findByStatus(status);
		if (!commonList.isEmpty()) {
			return new ResponseEntity<>((Collection<CommonList>) commonList, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Gets the common lists by name.
	 *
	 * @param tenantId - the tenant id
	 * @param name - the name
	 * @return the common lists by name
	 */
	@GetMapping(value = "/{tenantId}/name/{name}")
	public ResponseEntity<Object> getCommonListsByName(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "name", required = true) String name) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<CommonList> commonList = commonListService.findByName(name);
		if (!commonList.isEmpty()) {
			return new ResponseEntity<>((Collection<CommonList>) commonList, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Gets the common lists by code.
	 *
	 * @param tenantId - the tenant id
	 * @param code - the code
	 * @return the common lists by code
	 */
	@GetMapping(value = "/{tenantId}/code/{code}")
	public ResponseEntity<Object> getCommonListsByCode(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "code", required = true) String code) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<CommonList> commonList = commonListService.findByCode(code);
		if (!commonList.isEmpty()) {
			return new ResponseEntity<>((Collection<CommonList>) commonList, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Gets the common lists by reference code.
	 *
	 * @param tenantId - the tenant id
	 * @param referenceCode - the reference code
	 * @return the common lists by reference code
	 */
	@GetMapping(value = "/{tenantId}/reference-code/{referenceCode}")
	public ResponseEntity<Object> getCommonListsByReferenceCode(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "referenceCode", required = true) String referenceCode) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<CommonList> commonList = commonListService.findByReferenceCode(referenceCode);
		if (!commonList.isEmpty()) {
			return new ResponseEntity<>((Collection<CommonList>) commonList, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Adds the common list.
	 *
	 * @param tenantId - the tenant id
	 * @param commonListAddResource - the common list add resource
	 * @return the response entity
	 */
	@PostMapping(value = "/{tenantId}")
	public ResponseEntity<Object> addCommonList(@PathVariable(value = "tenantId", required = true) String tenantId,
			@Valid @RequestBody CommonListAddResource commonListAddResource) {
		if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new UserNotFound(environment.getProperty(userNotFound));
		Long id = commonListService.saveAndValidateCommonList(tenantId, LogginAuthentcation.getInstance().getUserName(), commonListAddResource);
		SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty(commonSaved), id.toString());
		return new ResponseEntity<>(successDetailsDto,HttpStatus.CREATED);
	}
	
	/**
	 * Update common list.
	 *
	 * @param tenantId - the tenant id
	 * @param id - the id
	 * @param commonListUpdateResource - the common list update resource
	 * @return the response entity
	 */
	@PutMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> updateCommonList(@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id,
			@Valid @RequestBody CommonListUpdateResource commonListUpdateResource) {
		if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new UserNotFound(environment.getProperty(userNotFound));
		if (commonListService.existsById(id)) {
			commonListService.updateAndValidateCommonList(tenantId, LogginAuthentcation.getInstance().getUserName(), id, commonListUpdateResource);
			SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty(commonUpdated));
			return new ResponseEntity<>(successDetailsDto, HttpStatus.OK);
		} else {
			SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(successDetailsDto, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
}
