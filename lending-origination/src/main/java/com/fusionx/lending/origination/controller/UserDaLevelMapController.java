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
import com.fusionx.lending.origination.domain.UserDaLevelMap;
import com.fusionx.lending.origination.exception.UserNotFound;
import com.fusionx.lending.origination.resource.SuccessAndErrorDetailsResource;
import com.fusionx.lending.origination.resource.UserDaLevelMapAddResource;
import com.fusionx.lending.origination.resource.UserDaLevelMapUpdateResource;
import com.fusionx.lending.origination.service.UserDaLevelMapService;

/**
 * DA Limit User Map Controller
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   06-05-2021      		     FX-6269	Amal       Created
 *    
 ********************************************************************************************************
 */

@RestController
@RequestMapping(value = "/user-da-level-map")
@CrossOrigin(origins = "*")
public class UserDaLevelMapController {

	@Autowired
	private Environment environment;
	
	@Autowired
	private UserDaLevelMapService userDaLevelMapService;
	
	private String userNotFound = "common.user-not-found";
	private String commonSaved = "common.saved";
	private String commonUpdated = "common.updated";
	private String recordNotFound = "common.record-not-found";
	
	/**
	 * Gets the all DA Limits User Map Info.
	 *
	 * @param tenantId - the tenant id
	 * @return the all DaLimit types
	 */
/*	@GetMapping(value = "/{tenantId}/all")
	public Page<?>  getAllUserDaLevelMaps(@PathVariable(value = "tenantId", required = true) String tenantId,
			@PageableDefault(size = 10) Pageable pageable,
    		@QuerydslPredicate(root = UserDaLevelMap.class) Predicate predicate
            )  {
		return  userDaLevelMapService.findAll(pageable, predicate);
	}*/
	
	@GetMapping(value = "/{tenantId}/all")
	public ResponseEntity<Object> getAllUserDaLevelMaps(
			@PathVariable(value = "tenantId", required = true) String tenantId) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<UserDaLevelMap> userDaLevelMaps = userDaLevelMapService.findAll();
		if (userDaLevelMaps!=null && !userDaLevelMaps.isEmpty()) {
			return new ResponseEntity<>(userDaLevelMaps, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Gets the DA Limit User Mappings by id.
	 *
	 * @param tenantId - the tenant id
	 * @param id - the id
	 * @return the UserDaLevelMap type by id
	 */
	@GetMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> getUserDaLevelMapById(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id) {
		
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<UserDaLevelMap> isPresentUserDaLevelMap = userDaLevelMapService.findById(id);
		if (isPresentUserDaLevelMap.isPresent()) {
			return new ResponseEntity<>(isPresentUserDaLevelMap, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Gets the DA Limit User Mappings by status.
	 *
	 * @param tenantId - the tenant id
	 * @param status - the status
	 * @return the UserDaLevelMap types by status
	 */
	@GetMapping(value = "/{tenantId}/status/{status}")
	public ResponseEntity<Object> getUserDaLevelMapByStatus(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "status", required = true) String status) {
		
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<UserDaLevelMap> userDaLevelMap = userDaLevelMapService.findByStatus(status);
		if (!userDaLevelMap.isEmpty()) {
			return new ResponseEntity<>((Collection<UserDaLevelMap>) userDaLevelMap, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Gets the DA Limit User Mappings by group id and level.
	 *
	 * @param tenantId - the tenant id
	 * @param status - the status
	 * @return the UserDaLevelMap types by group id and level
	 */
	@GetMapping(value = "/{tenantId}/groupid/{groupid}/level/{level}")
	public ResponseEntity<Object> getUserDaLevelMapByGroupIdAndLevel(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "groupid", required = true) Long groupid,
			@PathVariable(value = "level", required = true) String level) {
		
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<UserDaLevelMap> userDaLevelMap = userDaLevelMapService.findByDelegationAuthorityGroupIdAndDaLevel(groupid, level);
		if (!userDaLevelMap.isEmpty()) {
			return new ResponseEntity<>((Collection<UserDaLevelMap>) userDaLevelMap, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Adds the Use rDaLevel Map.
	 *
	 * @param tenantId - the tenant id
	 * @param UserDaLevelMapAddResource - the DA Limit User Map add resource
	 * @return the response entity
	 */
	@PostMapping(value = "/{tenantId}")
	public ResponseEntity<Object> addUserDaLevelMap(@PathVariable(value = "tenantId", required = true) String tenantId,
			@Valid @RequestBody UserDaLevelMapAddResource userDaLevelMapAddResource) {
		if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new UserNotFound(environment.getProperty(userNotFound));
		Long id = userDaLevelMapService.saveAndValidateUserDaLevelMap(tenantId, LogginAuthentcation.getInstance().getUserName(), userDaLevelMapAddResource);
		SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty(commonSaved), id.toString());
		return new ResponseEntity<>(successDetailsDto,HttpStatus.CREATED);
	}
	
	/**
	 * Update DA Limit User Mappings .
	 *
	 * @param tenantId - the tenant id
	 * @param id - the id
	 * @param UserDaLevelMapUpdateResource - the DA Limit User Map update resource
	 * @return the response entity
	 */
	@PutMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> updateUserDaLevelMap(@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id,
			@Valid @RequestBody UserDaLevelMapUpdateResource userDaLevelMapUpdateResource) {
		if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new UserNotFound(environment.getProperty(userNotFound));
		if (userDaLevelMapService.existsById(id)) {
			userDaLevelMapService.updateAndValidateUserDaLevelMap(tenantId, LogginAuthentcation.getInstance().getUserName(), id, userDaLevelMapUpdateResource);
			SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty(commonUpdated));
			return new ResponseEntity<>(successDetailsDto, HttpStatus.OK);
		} else {
			SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(successDetailsDto, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
}
