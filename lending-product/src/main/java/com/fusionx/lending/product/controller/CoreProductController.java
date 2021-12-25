package com.fusionx.lending.product.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.fusionx.lending.product.Constants;
import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.CoreProduct;
import com.fusionx.lending.product.domain.CoreProductPending;
import com.fusionx.lending.product.domain.MasterDefAccountRulePending;
import com.fusionx.lending.product.enums.WorkflowStatus;
import com.fusionx.lending.product.exception.PageableLengthException;
import com.fusionx.lending.product.exception.UserNotFound;
import com.fusionx.lending.product.resources.AccountStatusAddResource;
import com.fusionx.lending.product.resources.CoreProductAddResource;
import com.fusionx.lending.product.resources.CoreProductUpdateResource;
import com.fusionx.lending.product.resources.DisbursementConditionsUpdateResource;
import com.fusionx.lending.product.resources.SuccessAndErrorDetails;
import com.fusionx.lending.product.resources.SuccessAndErrorDetailsResource;
import com.fusionx.lending.product.service.CoreProductService;

/**
 * API Service related to Core Product.
 *
 * @author Dilhan
 * @version 1.0.0
 * @since 1.0.0
 * <p>
 * <br/><br/>
 * <b>Change History : </b>
 * <br/><br/>
 * #        Date            Story Point     Task No     Author                  Description
 * <br/>
 * .....................................................................................................................<br/>
 * 1        24-09-2021      -               FXL-795     Dilhan      Created
 * <p>
 *
 */
@RestController
@RequestMapping(value = "/core-product")
@CrossOrigin(origins = "*")
public class CoreProductController extends MessagePropertyBase{

	private CoreProductService coreProductService;

	@Autowired
	public void setCoreProductService(CoreProductService coreProductService) {
		this.coreProductService = coreProductService;
	}
	
	private String pageableLength = "common.pageable-length";
	
	 /**
     * Gets all core product list
     *
     * @param tenantId the id of tenant
     * @return the list of all core product list
     */
    @GetMapping(value = "/{tenantId}/all")
    public ResponseEntity<Object> getAllCoreProduct(@PathVariable(value = "tenantId") String tenantId) {
        SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
        List<CoreProduct> coreProduct = coreProductService.findAll();
        int size = coreProduct.size();
        if (size > 0) {
            return new ResponseEntity<>(coreProduct, HttpStatus.OK);
        } else {
            responseMessage.setMessages(RECORD_NOT_FOUND);
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Get core product by id
     *
     * @param tenantId the id of tenant
     * @param id       the id of the record
     * @return core product if record exists, otherwise <code>204</code>
     */
    @GetMapping(value = "/{tenantId}/{id}")
    public ResponseEntity<Object> getCoreProductById(@PathVariable(value = "tenantId") String tenantId,
                                                        @PathVariable(value = "id") Long id) {
        Optional<CoreProduct> isPresentCoreProduct = coreProductService.findById(id);
        if (isPresentCoreProduct.isPresent()) {
            return new ResponseEntity<>(isPresentCoreProduct.get(), HttpStatus.OK);
        } else {
        	SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
            responseMessage.setMessages(RECORD_NOT_FOUND);
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Get core product by status
     *
     * @param tenantId the id of tenant
     * @param status   the status
     * @return the list of all core product if record exists, otherwise <code>204 - No Content</code>
     */
    @GetMapping(value = "/{tenantId}/status/{status}")
    public ResponseEntity<Object> getCoreProductByStatus(@PathVariable(value = "tenantId") String tenantId,
                                                            @PathVariable(value = "status") String status) {
        SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();

        if (status.equals("ACTIVE") || status.equals("INACTIVE")) {
            List<CoreProduct> isPresentCoreProduct = coreProductService.findByStatus(status);
            int size = isPresentCoreProduct.size();
            if (size > 0) {
                return new ResponseEntity<>(isPresentCoreProduct, HttpStatus.OK);
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
     * Get core product by code
     *
     * @param tenantId the id of tenant
     * @param code   the code
     * @return  core product if record exists, otherwise <code>204 - No Content</code>
     */
	@GetMapping(value = "/{tenantId}/code/{code}")
	public ResponseEntity<Object> getCoreProductByCode(@PathVariable(value = "tenantId", required = true) String tenantId,
													   	            @PathVariable(value = "code", required = true) String code){
		Optional<CoreProduct> isPresentCoreProduct = coreProductService.getCoreProductByCode(code);
		if(isPresentCoreProduct.isPresent()) {
			return new ResponseEntity<>(isPresentCoreProduct.get(), HttpStatus.OK);
		}
		else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	  /**
     * Get core product by description
     *
     * @param tenantId the id of tenant
     * @param name   the name
     * @return the list of all core product if record exists, otherwise <code>204 - No Content</code>
     */
	@GetMapping(value = "/{tenantId}/description/{description}")
	public ResponseEntity<Object> getCoreProductByDescription(@PathVariable(value = "tenantId", required = true) String tenantId,
															        @PathVariable(value = "description", required = true) String description){
		List<CoreProduct> isPresentCoreProduct = coreProductService.getByDescription(description);
		if(!isPresentCoreProduct.isEmpty()) {
			return new ResponseEntity<>(isPresentCoreProduct, HttpStatus.OK);
		}
		else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	/**
     * Saves the account status
     *
     * @param tenantId                  the id of the tenant
     * @param accountStatusAddResource the object to save
     * @return message if record successfully saved.
     * @see AccountStatusAddResource
     */
    @PostMapping(value = "/{tenantId}")
    public ResponseEntity<Object> addDCoreProduct(@PathVariable(value = "tenantId") String tenantId,
                                                    @Valid @RequestBody CoreProductAddResource coreProductAddResource) {
        if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty()) {
            throw new UserNotFound(environment.getProperty(COMMON_USER_NOT_FOUND));
        }
        SuccessAndErrorDetails successAndErrorDetails;
        CoreProduct coreProduct = coreProductService.addCoreProduct(tenantId, coreProductAddResource);
        successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty(RECORD_CREATED), Long.toString(coreProduct.getId()));
        return new ResponseEntity<>(successAndErrorDetails, HttpStatus.CREATED);
    }
    
    /**
     * Updates the account status
     *
     * @param tenantId                     the id of the tenant
     * @param id                           the id of the object
     * @param disbursementConditionsUpdateResource the object which contains data
     * @return the message
     * @see DisbursementConditionsUpdateResource
     */
    @PutMapping(value = "/{tenantId}/{id}")
    public ResponseEntity<Object> updateCoreProduct(@PathVariable(value = "tenantId") String tenantId,
                                                       @PathVariable(value = "id") Long id,
                                                       @Valid @RequestBody CoreProductUpdateResource coreProductUpdateResource) {
        if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty()) {
            throw new UserNotFound(environment.getProperty(COMMON_USER_NOT_FOUND));
        }

        SuccessAndErrorDetails successAndErrorDetails = new SuccessAndErrorDetails();
        Optional<CoreProduct> isPresentCoreProduct = coreProductService.findById(id);
        if (isPresentCoreProduct.isPresent()) {
        	CoreProduct coreProduct = coreProductService.updateCoreProduct(tenantId, id, coreProductUpdateResource);
            successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty(RECORD_UPDATED), coreProduct.getId().toString());
            return new ResponseEntity<>(successAndErrorDetails, HttpStatus.OK);
        } else {
            successAndErrorDetails.setMessages(RECORD_NOT_FOUND);
            return new ResponseEntity<>(successAndErrorDetails, HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
    
	/**
	* approve Core Product Pending
	* @param @PathVariable{tenantId}
	* @param @PathVariable{pendingId}
	* @return SuccessAndErrorDetailsDto
	*/
	@PutMapping(value = "{tenantId}/approve/{pendingId}")
	public ResponseEntity<Object> approve(@PathVariable(value = "tenantId", required = true) String tenantId, @PathVariable(value = "pendingId", required = true) Long pendingId){
		SuccessAndErrorDetailsResource successAndErrorDetailsResource=new SuccessAndErrorDetailsResource();
		Optional<CoreProductPending> isPresentFeeCoreProductPending = coreProductService.getByPendingId(pendingId);
		if(isPresentFeeCoreProductPending.isPresent()) {
	
			if(coreProductService.approveReject(tenantId, pendingId, WorkflowStatus.COMPLETE)) {
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
	* Reject Core Product Pending
	* @param @PathVariable{tenantId}
	* @param @PathVariable{pendingId}
	* @return SuccessAndErrorDetailsDto
	*/
	@PutMapping(value = "{tenantId}/reject/{pendingId}")
	public ResponseEntity<Object> reject(@PathVariable(value = "tenantId", required = true) String tenantId, @PathVariable(value = "pendingId", required = true) Long pendingId){
		SuccessAndErrorDetailsResource successAndErrorDetailsResource=new SuccessAndErrorDetailsResource();
		Optional<CoreProductPending> isPresentCoreProductPending = coreProductService.getByPendingId(pendingId);
		if(isPresentCoreProductPending.isPresent()) {
	
			if(coreProductService.approveReject(tenantId, pendingId, WorkflowStatus.REJECT)) {
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
	 * Search Core Product.
	 *
	 * @param tenantId - the tenant id
	 * @param searchq - the searchq
	 * @param status - the status
	 * @param approveStatus - the approve status
	 * @param pageable - the pageable
	 * @return the page
	 */
	@GetMapping(value = "/{tenantId}/workflow-items/search")
	public Page<CoreProductPending> searchCoreProduct(@PathVariable(value = "tenantId", required = true) String tenantId,
			@RequestParam(value = "searchq", required = false) String searchq,
			@RequestParam(value = "status", required = false) String status,
			@RequestParam(value = "approveStatus", required = false) String approveStatus,
			@PageableDefault(size = 10) Pageable pageable) {
		if(pageable.getPageSize()>Constants.MAX_PAGEABLE_LENGTH) 
			throw new PageableLengthException(environment.getProperty(pageableLength));
		return coreProductService.searchCoreProduct(searchq, status, approveStatus, pageable);
	}
	

}
