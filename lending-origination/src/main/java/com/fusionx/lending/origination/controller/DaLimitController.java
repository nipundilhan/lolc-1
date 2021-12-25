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
import com.fusionx.lending.origination.domain.DaLimit;
import com.fusionx.lending.origination.exception.UserNotFound;
import com.fusionx.lending.origination.resource.DaLimitAddResource;
import com.fusionx.lending.origination.resource.DaLimitUpdateResource;
import com.fusionx.lending.origination.resource.SuccessAndErrorDetailsResource;
import com.fusionx.lending.origination.service.DaLimitService;

/**
 * DA Limit Controller
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   06-05-2021      		     FX-6269	Amal       Created
 *    
 ********************************************************************************************************
 */

@RestController
@RequestMapping(value = "/da-limit")
@CrossOrigin(origins = "*")
public class DaLimitController {

	@Autowired
	private Environment environment;
	
	@Autowired
	private DaLimitService daLimitService;
	
	private String userNotFound = "common.user-not-found";
	private String commonSaved = "common.saved";
	private String commonUpdated = "common.updated";
	private String recordNotFound = "common.record-not-found";
	
	/**
	 * Gets the all DA Limits.
	 *
	 * @param tenantId - the tenant id
	 * @return the all DaLimit types
	 */
	/*@GetMapping(value = "/{tenantId}/all")
	public Page<?>  getAllDaLimits(@PathVariable(value = "tenantId", required = true) String tenantId,
			@PageableDefault(size = 10) Pageable pageable,
    		@QuerydslPredicate(root = DaLimit.class) Predicate predicate
            )  {
		return  daLimitService.findAll(pageable, predicate);
	}*/
	
	@GetMapping(value = "/{tenantId}/all")
	public ResponseEntity<Object> getAllDaLimits(
			@PathVariable(value = "tenantId", required = true) String tenantId) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<DaLimit> daLimits = daLimitService.findAll();
		if (!daLimits.isEmpty()) {
			return new ResponseEntity<>((Collection<DaLimit>) daLimits, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Gets the DA Limit by id.
	 *
	 * @param tenantId - the tenant id
	 * @param id - the id
	 * @return the dA Limit type by id
	 */
	@GetMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> getDaLimitById(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id) {
		
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<DaLimit> isPresentDaLimit = daLimitService.findById(id);
		if (isPresentDaLimit.isPresent()) {
			return new ResponseEntity<>(isPresentDaLimit, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Gets the DA Limit types by status.
	 *
	 * @param tenantId - the tenant id
	 * @param status - the status
	 * @return the DaLimit types by status
	 */
	@GetMapping(value = "/{tenantId}/status/{status}")
	public ResponseEntity<Object> getDaLimitsByStatus(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "status", required = true) String status) {
		
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<DaLimit> daLimits = daLimitService.findByStatus(status);
		if (!daLimits.isEmpty()) {
			return new ResponseEntity<>((Collection<DaLimit>) daLimits, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	
	/**
	 * Adds the DA Limit type.
	 *
	 * @param tenantId - the tenant id
	 * @param DaLimitAddResource - the DA Limit  add resource
	 * @return the response entity
	 */
	@PostMapping(value = "/{tenantId}")
	public ResponseEntity<Object> addDaLimit(@PathVariable(value = "tenantId", required = true) String tenantId,
			@Valid @RequestBody DaLimitAddResource daLimitAddResource) {
		if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new UserNotFound(environment.getProperty(userNotFound));
		Long id = daLimitService.saveAndValidateDaLimit(tenantId, LogginAuthentcation.getInstance().getUserName(), daLimitAddResource);
		SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty(commonSaved), id.toString());
		return new ResponseEntity<>(successDetailsDto,HttpStatus.CREATED);
	}
	
	/**
	 * Update DA Limit .
	 *
	 * @param tenantId - the tenant id
	 * @param id - the id
	 * @param DaLimitUpdateResource - the DA Limit type update resource
	 * @return the response entity
	 */
	@PutMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> updateDaLimit(@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id,
			@Valid @RequestBody DaLimitUpdateResource daLimitUpdateResource) {
		if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new UserNotFound(environment.getProperty(userNotFound));
		if (daLimitService.existsById(id)) {
			daLimitService.updateAndValidateDaLimit(tenantId, LogginAuthentcation.getInstance().getUserName(), id, daLimitUpdateResource);
			SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty(commonUpdated));
			return new ResponseEntity<>(successDetailsDto, HttpStatus.OK);
		} else {
			SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(successDetailsDto, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
	
	/**
	 * Find by Group Id ,level and category id.
	 *
	 * @param tenantId - the tenant id
	 * @param status - the status
	 * @return the DaLimit types by Group Id ,level and category id.
	 */
	@GetMapping(value = "/{tenantId}/groupid/{groupid}/level/{level}/categoryid/{categoryid}")
	public ResponseEntity<Object> getDaLimitsByDelegationAuthorityGroupIdAndDaLevelAndApprovalCategoryId(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "groupid", required = true) Long groupid,
			@PathVariable(value = "level", required = true) String level,
			@PathVariable(value = "categoryid", required = true) Long categoryid) {
		
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<DaLimit> daLimits = daLimitService.findByDelegationAuthorityGroupIdAndDaLevelAndApprovalCategoryId(groupid, level, categoryid);
		if (!daLimits.isEmpty()) {
			return new ResponseEntity<>((Collection<DaLimit>) daLimits, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
}
