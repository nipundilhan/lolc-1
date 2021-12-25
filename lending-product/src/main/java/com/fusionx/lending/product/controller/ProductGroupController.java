package com.fusionx.lending.product.controller;

/**
 * Product Group Service
 * @author 	Venuki
 * @Dat     07-06-2021
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   07-06-2019   FX-2879        FX-6532    Venuki      Created
 *    
 ********************************************************************************************************
 */

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

import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.ProductGroup;
import com.fusionx.lending.product.exception.UserNotFound;
import com.fusionx.lending.product.resources.ProductGroupAddResource;
import com.fusionx.lending.product.resources.ProductGroupUpdateResource;
import com.fusionx.lending.product.resources.SuccessAndErrorDetails;
import com.fusionx.lending.product.service.ProductGroupService;

@RestController
@RequestMapping(value = "/product-group")
@CrossOrigin(origins = "*")
public class ProductGroupController {

	@Autowired
	Environment environment;
	
	@Autowired
	public ProductGroupService productGroupService;
	
	private static final String RECORD_NOT_FOUND = "Records not available";
	private String userNotFound="common.user-not-found";
	
	/**
	 * get all Product Group Details
	 * @param @PathVariable{tenantId}
	 * @param @PathVariable{all}
	 * @return List<Type>
	 */
    @GetMapping(value = "/{tenantId}/all")
    public ResponseEntity<Object> getAllProductGroups(@PathVariable(value = "tenantId", required = true) String tenantId) {
    	SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
		List<ProductGroup> productGroup = productGroupService.findAll();
		int size = productGroup.size();
		if (size > 0) {
			return new ResponseEntity<>((Collection<ProductGroup>) productGroup,HttpStatus.OK);
		} 	
		else {
			responseMessage.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
    
    /**
     * getProduct Group by id
     * @param @PathVariable{tenantId}
     * @param @PathVariable{id}
     * @return Option
     */
 	@GetMapping(value = "/{tenantId}/{productGroupId}")
 	public ResponseEntity<Object> getProductGroupsById(@PathVariable(value = "tenantId", required = true) String tenantId, 
 			                                                  @PathVariable(value = "productGroupId", required = true) Long productGroupId) {
 		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
	 	Optional<ProductGroup> isPresentProductGroup = productGroupService.findById(productGroupId);
	 	if (isPresentProductGroup.isPresent()) {
	 		return new ResponseEntity<>(isPresentProductGroup.get(), HttpStatus.OK);
	 	} 
	 	else {
	 		responseMessage.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
	 	}
 	}

	/**
	 * Gets the Product Groups by status.
	 *
	 * @param tenantId - the tenant id
	 * @param status - the status
	 * @return the ApprovalCategory by status
	 */
	@GetMapping(value = "/{tenantId}/status/{status}")
	public ResponseEntity<Object> getProductGroupsByStatus(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "status", required = true) String status) {
		SuccessAndErrorDetails responseMessage=new SuccessAndErrorDetails();
		List<ProductGroup> productGroups = productGroupService.findByStatus(status);
		if (!productGroups.isEmpty()) {
			return new ResponseEntity<>((Collection<ProductGroup>) productGroups, HttpStatus.OK);
		} else {
			responseMessage.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
 	
 	/**
     * save Product Group
     * @param @PathVariable{tenantId}
     * @param @RequestBody{ProductGroupAddResource}
     * @return SuccessAndErrorDetailsDto
     */
 	@PostMapping(value = "/{tenantId}")
 	public ResponseEntity<Object> addProductGroup(@PathVariable(value = "tenantId", required = true) String tenantId,
 			                                              @Valid @RequestBody ProductGroupAddResource productGroupAddResource) {
 		if(LogginAuthentcation.getInstance().getUserName()==null || LogginAuthentcation.getInstance().getUserName().isEmpty()) { 
 			throw new UserNotFound(environment.getProperty(userNotFound));
 		}
		
 		ProductGroup productGroup = productGroupService.addProductGroup(tenantId,productGroupAddResource, LogginAuthentcation.getInstance().getUserName());
 		SuccessAndErrorDetails successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty("rec.saved"),Long.toString(productGroup.getId()));
		return new ResponseEntity<>(successAndErrorDetails,HttpStatus.CREATED);
 	}
 	
	/**
     * updateProduct Group
     * @param @PathVariable{tenantId}
     * @param @RequestBody{ProductGroupUpdateResource}
     * @return SuccessAndErrorDetailsDto
     */
	@PutMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> updateProductGroup(@PathVariable(value = "tenantId", required = true) String tenantId, 
			                                                 @PathVariable(value = "id", required = true) Long id, 
			                                                 @Valid @RequestBody ProductGroupUpdateResource productGroupUpdateResource) {
		if(LogginAuthentcation.getInstance().getUserName()==null || LogginAuthentcation.getInstance().getUserName().isEmpty()) { 
 			throw new UserNotFound(environment.getProperty(userNotFound));
 		}
		
		SuccessAndErrorDetails successAndErrorDetails=new SuccessAndErrorDetails();
		Optional<ProductGroup> isPresentProductGroup = productGroupService.findById(id);
		if(isPresentProductGroup.isPresent()) {
			ProductGroup ProductGroup = productGroupService.updateProductGroup(tenantId, id, productGroupUpdateResource, LogginAuthentcation.getInstance().getUserName());
			successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty("rec.updated"), ProductGroup.getId().toString());
        	return new ResponseEntity<>(successAndErrorDetails,HttpStatus.OK);
		}
		else {
			successAndErrorDetails.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(successAndErrorDetails, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
	
	/**
	 * Search ProductGroup.
	 *
	 * @param tenantId - the tenant id
	 * @param searchq - the searchq
	 * @param name - the name
	 * @param code - the code
	 * @param status - the status
	 * @param pageable - the pageable
	 * @return the page
	 */
	@GetMapping(value = "/{tenantId}/search")
	public Page<ProductGroup> searchProductGroup(@PathVariable(value = "tenantId", required = true) String tenantId,
			@RequestParam(value = "searchq", required = false) String searchq,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "code", required = false) String code,
			@RequestParam(value = "status", required = false) String status,
			@PageableDefault(size = 10) Pageable pageable) {

		return productGroupService.searchProductGroupList(searchq, name, code, status, pageable);
	}
	
}
