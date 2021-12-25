package com.fusionx.lending.product.controller;

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

import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.SubProduct;
import com.fusionx.lending.product.domain.SubProductPending;
import com.fusionx.lending.product.enums.CommonApproveStatus;
import com.fusionx.lending.product.enums.WorkflowStatus;
import com.fusionx.lending.product.exception.UserNotFound;
import com.fusionx.lending.product.resources.SubProductAddResource;
import com.fusionx.lending.product.resources.SubProductLoanApplicableUpdateResource;
import com.fusionx.lending.product.resources.SubProductUpdateResource;
import com.fusionx.lending.product.resources.SuccessAndErrorDetails;
import com.fusionx.lending.product.resources.SuccessAndErrorDetailsResource;
import com.fusionx.lending.product.service.SubProductService;

/**
 * Sub Product Service 
 * @author 	Sanatha
 * @Date    19-JUL-2021
 * 
 ********************************************************************************************************
 *  ###   	Date         	Story Point   	Task No    		Author      	 Description
 *-------------------------------------------------------------------------------------------------------
 *    1   	19-JUL-2021  	FXL-25      	FXL-25   		Sanatha     	 Initial Development.
 *    
 ********************************************************************************************************
 */
@RestController
@RequestMapping(value = "/sub-product")
@CrossOrigin(origins = "*")
public class SubProductController {
	
	@Autowired
	Environment environment;
	
	@Autowired
	public SubProductService subProductService;
	
	
	private static final String RECORD_NOT_FOUND = "Records not available";
	private String userNotFound="common.user-not-found";
	private String pageableLength = "common.pageable-length";
	
	/**
	 * get all SubProduct 
	 * @author Sanatha
	 * @since 19-JUL-2021 
	 * @param @PathVariable{tenantId}
	 * @param @PathVariable{all}
	 * @return List<Type>
	 */
    @GetMapping(value = "/{tenantId}/all")
    public ResponseEntity<Object> getAllSubProduct(@PathVariable(value = "tenantId", required = true) String tenantId) {
    	SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
		List<SubProduct> subProduct = subProductService.getAll();
		int size = subProduct.size();
		if (size > 0) {
			return new ResponseEntity<>((Collection<SubProduct>) subProduct,HttpStatus.OK);
		} 
		else {
			responseMessage.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
    
    /**
     * get SubProduct  by id
     * @author Sanatha
     * @since 19-JUL-2021 
     * @param @PathVariable{tenantId}
     * @param @PathVariable{id}
     * @return Option
     */
 	@GetMapping(value = "/{tenantId}/{id}")
 	public ResponseEntity<Object> getSubProductById(@PathVariable(value = "tenantId", required = true) String tenantId, 
 			                                              @PathVariable(value = "id", required = true) Long id) {
 		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
	 	Optional<SubProduct> isPresentSubProduct = subProductService.getById(id);
	 	if (isPresentSubProduct.isPresent()) {
	 		return new ResponseEntity<>(isPresentSubProduct.get(), HttpStatus.OK);
	 	} 
	 	else {
	 		responseMessage.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
	 	}
 	}
 	
 	/**
	 * get SubProduct by Status
	 * @author Sanatha
	 * @since 19-JUL-2021 
	 * @param @PathVariable{tenantId}
	 * @param @PathVariable{status}
	 * @return List<Type>
	 */
    @GetMapping(value = "/{tenantId}/status/{status}")
    public ResponseEntity<Object> getSubProductByStatus(@PathVariable(value = "tenantId", required = true) String tenantId,
    													@PathVariable(value = "status", required = true) String status) {
    	SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
		List<SubProduct> subProduct = subProductService.getByStatus(status);
		int size = subProduct.size();
		if (size > 0) {
			return new ResponseEntity<>((Collection<SubProduct>) subProduct,HttpStatus.OK);
		} 
		else {
			responseMessage.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
    
    /**
	 * get SubProduct by Name
	 * @author Sanatha
	 * @since 19-JUL-2021 
	 * @param @PathVariable{tenantId}
	 * @param @PathVariable{name}
	 * @return List<Type>
	 */
    @GetMapping(value = "/{tenantId}/name/{name}")
    public ResponseEntity<Object> getSubProductByName(@PathVariable(value = "tenantId", required = true) String tenantId,
    													@PathVariable(value = "name", required = true) String name) {
    	SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
		List<SubProduct> subProduct = subProductService.getByName(name);
		int size = subProduct.size();
		if (size > 0) {
			return new ResponseEntity<>((Collection<SubProduct>) subProduct,HttpStatus.OK);
		} 
		else {
			responseMessage.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
    
    /**
     * get SubProduct  by Code
     * @author Sanatha
     * @since 19-JUL-2021 
     * @param @PathVariable{tenantId}
     * @param @PathVariable{code}
     * @return Option
     */
 	@GetMapping(value = "/{tenantId}/code/{code}")
 	public ResponseEntity<Object> getSubProductByCode(@PathVariable(value = "tenantId", required = true) String tenantId, 
 			                                              @PathVariable(value = "code", required = true) String code) {
 		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
	 	Optional<SubProduct> isPresentSubProduct = subProductService.getByCode(code);
	 	if (isPresentSubProduct.isPresent()) {
	 		return new ResponseEntity<>(isPresentSubProduct.get(), HttpStatus.OK);
	 	} 
	 	else {
	 		responseMessage.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
	 	}
 	}
	
	/**
     * save SubProduct 
     * @author Sanatha
     * @since 19-JUL-2021 
     * @param @PathVariable{tenantId}
     * @param @RequestBody{subProductAddResource}
     * @return SuccessAndErrorDetailsDto
     */
 	@PostMapping(value = "/{tenantId}")
 	public ResponseEntity<Object> addSubProduct(@PathVariable(value = "tenantId", required = true) String tenantId,
 			                                          @Valid @RequestBody SubProductAddResource subProductAddResource) {
 		if(LogginAuthentcation.getInstance().getUserName()==null || LogginAuthentcation.getInstance().getUserName().isEmpty()) { 
 			throw new UserNotFound(environment.getProperty(userNotFound));
 		}
		
 		SuccessAndErrorDetails successAndErrorDetails = new SuccessAndErrorDetails();
 		SubProduct subProduct = subProductService.addSubProduct(tenantId, subProductAddResource);
	 	successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty("rec.saved"),Long.toString(subProduct.getId()));
		return new ResponseEntity<>(successAndErrorDetails,HttpStatus.CREATED);
 	}
 	
 	/**
     * update SubProduct  
     * @author Sanatha
     * @since 19-JUL-2021 
     * @param @PathVariable{tenantId}
     * @param @RequestBody{SubProductUpdateResource}
     * @return SuccessAndErrorDetailsDto
     */
	@PutMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> updateSubProduct(@PathVariable(value = "tenantId", required = true) String tenantId, 
			                                             @PathVariable(value = "id", required = true) Long id, 
			                                             @Valid @RequestBody SubProductUpdateResource subProductUpdateResource) {
		if(LogginAuthentcation.getInstance().getUserName()==null || LogginAuthentcation.getInstance().getUserName().isEmpty()) { 
 			throw new UserNotFound(environment.getProperty(userNotFound));
 		}
		
		SuccessAndErrorDetails successAndErrorDetails=new SuccessAndErrorDetails();
		Optional<SubProduct> isPresentSubProduct = subProductService.getById(id);
		if(isPresentSubProduct.isPresent()) {
			if(isPresentSubProduct.get().getApproveStatus() == null || !isPresentSubProduct.get().getApproveStatus().equals(CommonApproveStatus.PENDING))
			{
				SubProduct subProduct = subProductService.updateSubProduct(tenantId, id, subProductUpdateResource);
				successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty("rec.updated"), subProduct.getId().toString());
	        	return new ResponseEntity<>(successAndErrorDetails,HttpStatus.OK);
			} else {
				successAndErrorDetails.setMessages(environment.getProperty("common.can-not-update"));
				return new ResponseEntity<>(successAndErrorDetails, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			
		}
		else {
			successAndErrorDetails.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(successAndErrorDetails, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
	
	/**
	* approve SubProduct  Pending
	* @author Sanatha
    * @since 19-JUL-2021 
	* @param @PathVariable{tenantId}
	* @param @PathVariable{pendingId}
	* @return SuccessAndErrorDetailsDto
	*/
	@PutMapping(value = "{tenantId}/approve/{pendingId}")
	public ResponseEntity<Object> approve(@PathVariable(value = "tenantId", required = true) String tenantId, @PathVariable(value = "pendingId", required = true) Long pendingId){
		SuccessAndErrorDetailsResource successAndErrorDetailsResource=new SuccessAndErrorDetailsResource();
		Optional<SubProductPending> isPresentSubProductPending = subProductService.getByPendingId(pendingId);
		if(isPresentSubProductPending.isPresent()) {
	
			if(subProductService.approveReject(tenantId, pendingId, WorkflowStatus.COMPLETE)) {
			successAndErrorDetailsResource = new SuccessAndErrorDetailsResource(environment.getProperty("common.approved"), pendingId.toString());
			return new ResponseEntity<>(successAndErrorDetailsResource,HttpStatus.OK);
			} else {
				successAndErrorDetailsResource.setMessages(environment.getProperty("common.can-not-approved"));
				return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.UNPROCESSABLE_ENTITY);
			}

		} else {
			successAndErrorDetailsResource.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
	
	/**
	* Reject SubProduct  Pending
	* @author Sanatha
    * @since 19-JUL-2021 
	* @param @PathVariable{tenantId}
	* @param @PathVariable{pendingId}
	* @return SuccessAndErrorDetailsDto
	*/
	@PutMapping(value = "{tenantId}/reject/{pendingId}")
	public ResponseEntity<Object> reject(@PathVariable(value = "tenantId", required = true) String tenantId, @PathVariable(value = "pendingId", required = true) Long pendingId){
		SuccessAndErrorDetailsResource successAndErrorDetailsResource=new SuccessAndErrorDetailsResource();
		Optional<SubProductPending> isPresentSubProductPending = subProductService.getByPendingId(pendingId);
		if(isPresentSubProductPending.isPresent()) {
	
			if(subProductService.approveReject(tenantId, pendingId, WorkflowStatus.REJECT)) {
			successAndErrorDetailsResource = new SuccessAndErrorDetailsResource(environment.getProperty("common.rejected"), pendingId.toString());
			return new ResponseEntity<>(successAndErrorDetailsResource,HttpStatus.OK);
			} else {
				successAndErrorDetailsResource.setMessages(environment.getProperty("common.can-not-rejected"));
				return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.UNPROCESSABLE_ENTITY);
			}

		} else {
			successAndErrorDetailsResource.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
	
	/**
	 * Gets the SubProduct by pending id.
	 * @author Sanatha
	 * @since 19-JUL-2021 
	 * @param tenantId - the tenant id
	 * @param pendingId - the pending id
	 * @return  SubProductPending
	 */
	@GetMapping(value = "/{tenantId}/pending/{pendingId}")
	public ResponseEntity<Object> getSubProductByPendingId(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "pendingId", required = true) Long pendingId) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<SubProductPending> isPresentSubProductPending = subProductService.getByPendingId(pendingId);
		if (isPresentSubProductPending.isPresent()) {
			return new ResponseEntity<>(isPresentSubProductPending, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
 	/**
     * update SubProduct  
     * @author Sanatha
     * @since 19-JUL-2021 
     * @param @PathVariable{tenantId}
     * @param @RequestBody{SubProductUpdateResource}
     * @return SuccessAndErrorDetailsDto
     */
	@PutMapping(value = "/{tenantId}/update-loan-applicable-range/sub-product/{id}")
	public ResponseEntity<Object> updateSubProductWithLoanApplicableRange(@PathVariable(value = "tenantId", required = true) String tenantId, 
			                                             @PathVariable(value = "id", required = true) Long id, 
			                                             @Valid @RequestBody SubProductLoanApplicableUpdateResource subProductLoanApplicableUpdateResource) {
		if(LogginAuthentcation.getInstance().getUserName()==null || LogginAuthentcation.getInstance().getUserName().isEmpty()) { 
 			throw new UserNotFound(environment.getProperty(userNotFound));
 		}
		
		SuccessAndErrorDetails successAndErrorDetails=new SuccessAndErrorDetails();
		SubProduct subProduct = subProductService.updateSubProductLoanApplicableId(id, subProductLoanApplicableUpdateResource);
		if(subProduct != null) {
			successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty("rec.updated"), subProduct.getId().toString());
	        return new ResponseEntity<>(successAndErrorDetails,HttpStatus.OK);			
		}
		else {
			successAndErrorDetails.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(successAndErrorDetails, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

}
