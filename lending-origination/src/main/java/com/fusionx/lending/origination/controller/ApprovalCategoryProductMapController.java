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
import com.fusionx.lending.origination.domain.ApprovalCategoryProductMapping;
import com.fusionx.lending.origination.exception.PageableLengthException;
import com.fusionx.lending.origination.exception.UserNotFound;
import com.fusionx.lending.origination.resource.ApprovalCatProductMapAddResource;
import com.fusionx.lending.origination.resource.ApprovalCatProductMapUpdateResource;
import com.fusionx.lending.origination.resource.SuccessAndErrorDetailsResource;
import com.fusionx.lending.origination.service.ApprovalCategoryProductMapService;


/**
 * Approval Category Map with Product Controller
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   20-04-2021      		     			VenukiR      Created
 *    
 ********************************************************************************************************
 */

@RestController
@RequestMapping(value = "/approval-category-product-map")
@CrossOrigin(origins = "*")
public class ApprovalCategoryProductMapController {

	@Autowired
	private Environment environment;
	
	@Autowired
	private ApprovalCategoryProductMapService approvalCatProdMapService;
	
	private String userNotFound = "common.user-not-found";
	private String commonSaved = "common.saved";
	private String commonUpdated = "common.updated";
	private String pageableLength = "common.pageable-length";
	private String recordNotFound = "common.record-not-found";
	
	
	/**
	 * Gets the all ApprovalCategory.
	 *
	 * @param tenantId - the tenant id
	 * @param pageable - the pageable
	 * @return the all ApprovalCategory
	 */
	@GetMapping(value = "/{tenantId}/all")
	public Page<ApprovalCategoryProductMapping> getAllApprovalCatProductMaps(@PathVariable(value = "tenantId", required = true) String tenantId,
			@PageableDefault(size = 10) Pageable pageable) {
		if (pageable.getPageSize() > Constants.MAX_PAGEABLE_LENGTH)
			throw new PageableLengthException(environment.getProperty(pageableLength));
		return approvalCatProdMapService.findAll(pageable);
	}
	
	/**
	 * Gets the ApprovalCategoryProductMapping by id.
	 *
	 * @param tenantId - the tenant id
	 * @param id - the id
	 * @return the ApprovalCategoryProductMapping by id
	 */
	@GetMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> getApprovalCatProductMapById(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<ApprovalCategoryProductMapping> isPresentApprovalCatProductMap = approvalCatProdMapService.findById(id);
		if (isPresentApprovalCatProductMap.isPresent()) {
			return new ResponseEntity<>(isPresentApprovalCatProductMap, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Gets the ApprovalCategoryProductMapping by status.
	 *
	 * @param tenantId - the tenant id
	 * @param status - the status
	 * @return the ApprovalCategoryProductMapping by status
	 */
	@GetMapping(value = "/{tenantId}/status/{status}")
	public ResponseEntity<Object> getApprovalCatProductMapsByStatus(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "status", required = true) String status) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<ApprovalCategoryProductMapping> approvalCategoryProductMap = approvalCatProdMapService.findByStatus(status);
		if (!approvalCategoryProductMap.isEmpty()) {
			return new ResponseEntity<>((Collection<ApprovalCategoryProductMapping>) approvalCategoryProductMap, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
		
	
	/**
	 * Adds the ApprovalCategoryProductMapping.
	 *
	 * @param tenantId - the tenant id
	 * @param ApprovalCatProductMapAddResource - the ApprovalCategoryProductMapping add resource
	 * @return the response entity
	 */
	@PostMapping(value = "/{tenantId}")
	public ResponseEntity<Object> addApprovalCatProductMap(@PathVariable(value = "tenantId", required = true) String tenantId,
			@Valid @RequestBody ApprovalCatProductMapAddResource approvalCatProdMapResource) {
		if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new UserNotFound(environment.getProperty(userNotFound));
		approvalCatProdMapService.saveAndValidateApprovalCatProductMap(tenantId, LogginAuthentcation.getInstance().getUserName(), approvalCatProdMapResource);
		SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty(commonSaved));
		return new ResponseEntity<>(successDetailsDto,HttpStatus.CREATED);
	}
	
	/**
	 * Update ApprovalCategoryProductMapping.
	 *
	 * @param tenantId - the tenant id
	 * @param id - the id
	 * @param ApprovalCatProductMapUpdateResource - the ApprovalCategoryProductMapping update resource
	 * @return the response entity
	 */
	@PutMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> updateApprovalCatProductMap(@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id,
			@Valid @RequestBody ApprovalCatProductMapUpdateResource approvalCatProductMapUpdateResource) {
		if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new UserNotFound(environment.getProperty(userNotFound));
		if (approvalCatProdMapService.existsById(id)) {
			approvalCatProdMapService.updateAndValidateApprovalCatProductMap(tenantId, LogginAuthentcation.getInstance().getUserName(), id, approvalCatProductMapUpdateResource);
			SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty(commonUpdated));
			return new ResponseEntity<>(successDetailsDto, HttpStatus.OK);
		} else {
			SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(successDetailsDto, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
	
	/**
	 * Search ApprovalCategoryProductMapping.
	 *
	 * @param tenantId - the tenant id
	 * @param searchq - the searchq
	 * @param name - the name
	 * @param code - the code
	 * @param pageable - the pageable
	 * @return the page
	 */
	@GetMapping(value = "/{tenantId}/search")
	public Page<ApprovalCategoryProductMapping> searchApprovalCatProductMap(@PathVariable(value = "tenantId", required = true) String tenantId,
			@RequestParam(value = "searchq", required = false) String searchq,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "code", required = false) String code,
			@PageableDefault(size = 10) Pageable pageable) {
		if(pageable.getPageSize()>Constants.MAX_PAGEABLE_LENGTH) 
			throw new PageableLengthException(environment.getProperty(pageableLength));
		return approvalCatProdMapService.searchByApprovalCategory(searchq, name, code, pageable);
	}
}