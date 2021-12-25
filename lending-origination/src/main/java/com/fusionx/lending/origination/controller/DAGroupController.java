package com.fusionx.lending.origination.controller;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fusionx.lending.origination.Constants;
import com.fusionx.lending.origination.core.LogginAuthentcation;
import com.fusionx.lending.origination.domain.DelegationAuthorityGroup;
import com.fusionx.lending.origination.exception.PageableLengthException;
import com.fusionx.lending.origination.exception.UserNotFound;
import com.fusionx.lending.origination.resource.DelegationAuthorityGroupAddResource;
import com.fusionx.lending.origination.resource.DelegationAuthorityGroupUpdateResource;
import com.fusionx.lending.origination.resource.SuccessAndErrorDetailsResource;
import com.fusionx.lending.origination.service.DelegationAuthorityGroupService;


/**
 * Delegation Authority Group Controller
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   20-04-2021      		     			VenukiR      Created
 *    
 ********************************************************************************************************
 */

@RestController
@RequestMapping(value = "/delegation-authority-groups")
@CrossOrigin(origins = "*")
public class DAGroupController {

	@Autowired
	private Environment environment;
	
	@Autowired
	private DelegationAuthorityGroupService dAGroupService;
	
	private String userNotFound = "common.user-not-found";
	private String commonSaved = "common.saved";
	private String commonUpdated = "common.updated";
	private String pageableLength = "common.pageable-length";
	private String recordNotFound = "common.record-not-found";
	
	
	/**
	 * Gets the all DA Groups.
	 *
	 * @param tenantId - the tenant id
	 * @param pageable - the pageable
	 * @return the all DA Groups
	 */
//	@GetMapping(value = "/{tenantId}/all")
//	public Page<DelegationAuthorityGroup> getAllDAGroups(@PathVariable(value = "tenantId", required = true) String tenantId,
//			@PageableDefault(size = 10) Pageable pageable) {
//		if (pageable.getPageSize() > Constants.MAX_PAGEABLE_LENGTH)
//			throw new PageableLengthException(environment.getProperty(pageableLength));
//		return dAGroupService.findAll(pageable);
//	}
	@GetMapping(value = "/{tenantId}/all")
	public ResponseEntity<Object> getAllDAGroups(
			@PathVariable(value = "tenantId", required = true) String tenantId) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<DelegationAuthorityGroup> dagroup = dAGroupService.findAll();
		if (!dagroup.isEmpty()) {
			return new ResponseEntity<>((Collection<DelegationAuthorityGroup>) dagroup, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Gets the DA Groups by id.
	 *
	 * @param tenantId - the tenant id
	 * @param id - the id
	 * @return the DA Groups by id
	 */
	@GetMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> getDAGroupById(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<DelegationAuthorityGroup> isPresentDAGroup = dAGroupService.findById(id);
		if (isPresentDAGroup.isPresent()) {
			return new ResponseEntity<>(isPresentDAGroup, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Gets the DA Groups by status.
	 *
	 * @param tenantId - the tenant id
	 * @param status - the status
	 * @return the DA Groups by status
	 */
	@GetMapping(value = "/{tenantId}/status/{status}")
	public ResponseEntity<Object> getDAGroupsByStatus(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "status", required = true) String status) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<DelegationAuthorityGroup> daGroup = dAGroupService.findByStatus(status);
		if (!daGroup.isEmpty()) {
			return new ResponseEntity<>((Collection<DelegationAuthorityGroup>) daGroup, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	

	/**
	 * Gets the DA Groups by name.
	 *
	 * @param tenantId - the tenant id
	 * @param name - the name
	 * @return the DA Groups by name
	 */
	@GetMapping(value = "/{tenantId}/name/{name}")
	public ResponseEntity<Object> getDAGroupsByName(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "name", required = true) String name) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<DelegationAuthorityGroup> daGroup = dAGroupService.findByName(name);
		if (!daGroup.isEmpty()) {
			return new ResponseEntity<>((Collection<DelegationAuthorityGroup>) daGroup, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Gets the DA Groups by code.
	 *
	 * @param tenantId - the tenant id
	 * @param code - the code
	 * @return the DA Groups by code
	 */
	@GetMapping(value = "/{tenantId}/code/{code}")
	public ResponseEntity<Object> getDAGroupsByCode(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "code", required = true) String code) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<DelegationAuthorityGroup> isPresentDAGroup = dAGroupService.findByCode(code);
		if (isPresentDAGroup.isPresent()) {
			return new ResponseEntity<>(isPresentDAGroup, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Adds the DA Groups.
	 *
	 * @param tenantId - the tenant id
	 * @param daGroupAddResource - the DA Groups add resource
	 * @return the response entity
	 */
	@PostMapping(value = "/{tenantId}")
	public ResponseEntity<Object> addDAGroup(@PathVariable(value = "tenantId", required = true) String tenantId,
			@Valid @RequestBody DelegationAuthorityGroupAddResource daGroupAddResource) {
		if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new UserNotFound(environment.getProperty(userNotFound));
		Long id = dAGroupService.saveAndValidateDelegationAuthorityGroup(tenantId, LogginAuthentcation.getInstance().getUserName(), daGroupAddResource);
		SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty(commonSaved), id.toString());
		return new ResponseEntity<>(successDetailsDto,HttpStatus.CREATED);
	}
	
	/**
	 * Update DA Groups.
	 *
	 * @param tenantId - the tenant id
	 * @param id - the id
	 * @param delegationAuthorityGroupUpdateResource - the DA Groups update resource
	 * @return the response entity
	 */
	@PutMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> updateDAGroup(@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id,
			@Valid @RequestBody DelegationAuthorityGroupUpdateResource delegationAuthorityGroupUpdateResource) {
		if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new UserNotFound(environment.getProperty(userNotFound));
		if (dAGroupService.existsById(id)) {
			dAGroupService.updateAndValidateDelegationAuthorityGroup(tenantId, LogginAuthentcation.getInstance().getUserName(), id, delegationAuthorityGroupUpdateResource);
			SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty(commonUpdated));
			return new ResponseEntity<>(successDetailsDto, HttpStatus.OK);
		} else {
			SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(successDetailsDto, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
	
	/**
	 * Search DA Groups.
	 *
	 * @param tenantId - the tenant id
	 * @param searchq - the searchq
	 * @param name - the name
	 * @param code - the code
	 * @param pageable - the pageable
	 * @return the page
	 */
	@GetMapping(value = "/{tenantId}/search")
	public Page<DelegationAuthorityGroup> searchDAGroup(@PathVariable(value = "tenantId", required = true) String tenantId,
			@RequestParam(value = "searchq", required = false) String searchq,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "code", required = false) String code,
			@PageableDefault(size = 10) Pageable pageable) {
		if(pageable.getPageSize()>Constants.MAX_PAGEABLE_LENGTH) 
			throw new PageableLengthException(environment.getProperty(pageableLength));
		return dAGroupService.searchDelegationAuthorityGroup(searchq, name, code, pageable);
	}
}