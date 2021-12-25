package com.fusionx.lending.transaction.controller;

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

import com.fusionx.lending.transaction.Constants;
import com.fusionx.lending.transaction.base.MessagePropertyBase;
import com.fusionx.lending.transaction.core.LogginAuthentcation;
import com.fusionx.lending.transaction.domain.AllocationTemplate;
import com.fusionx.lending.transaction.domain.AllocationTemplatePending;
import com.fusionx.lending.transaction.enums.WorkflowStatus;
import com.fusionx.lending.transaction.exception.PageableLengthException;
import com.fusionx.lending.transaction.exception.UserNotFound;
import com.fusionx.lending.transaction.resource.AllocationTemplateAddResource;
import com.fusionx.lending.transaction.resource.AllocationTemplateUpdateResource;
import com.fusionx.lending.transaction.resource.SuccessAndErrorDetails;
import com.fusionx.lending.transaction.resource.SuccessAndErrorDetailsResource;
import com.fusionx.lending.transaction.service.AllocationTemplateService;


/**
 * API Service related to Allocation Template.
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
 * 1        21-10-2021      -               FXL-1151     Dilhan                   Created
 * <p>
 *
 */
@RestController
@RequestMapping(value = "/allocation-template")
@CrossOrigin(origins = "*")
public class AllocationTemplateController extends MessagePropertyBase{

	private AllocationTemplateService allocationTemplateService;
	@Autowired
	public void setAllocationTemplateService(AllocationTemplateService allocationTemplateService) {
		this.allocationTemplateService = allocationTemplateService;
	}
	
	private String pageableLength = "common.pageable-length";
	 /**
     * Gets all allocation template list
     *
     * @param tenantId the id of tenant
     * @return the list of all allocation template list
     */
	 @GetMapping(value = "/{tenantId}/all")
	    public ResponseEntity<Object> getAllAllocationTemplate(@PathVariable(value = "tenantId") String tenantId) {
	        SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
	        List<AllocationTemplate> allocationTemplate = allocationTemplateService.findAll();
	        int size = allocationTemplate.size();
	        if (size > 0) {
	            return new ResponseEntity<>(allocationTemplate, HttpStatus.OK);
	        } else {
	            responseMessage.setMessages(RECORD_NOT_FOUND);
	            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
	        }
	    }

	    /**
	     * Get allocation template by id
	     *
	     * @param tenantId the id of tenant
	     * @param id       the id of the record
	     * @return allocation template if record exists, otherwise <code>204</code>
	     */
	    @GetMapping(value = "/{tenantId}/{id}")
	    public ResponseEntity<Object> getAllocationTemplateById(@PathVariable(value = "tenantId") String tenantId,
	                                                        @PathVariable(value = "id") Long id) {
	        Optional<AllocationTemplate> isPresentAllocationTemplate = allocationTemplateService.findById(id);
	        if (isPresentAllocationTemplate.isPresent()) {
	            return new ResponseEntity<>(isPresentAllocationTemplate.get(), HttpStatus.OK);
	        } else {
	        	SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
	            responseMessage.setMessages(RECORD_NOT_FOUND);
	            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
	        }
	    }

	    /**
	     * Get allocation template by status
	     *
	     * @param tenantId the id of tenant
	     * @param status   the status
	     * @return the list of all allocation template if record exists, otherwise <code>204 - No Content</code>
	     */
	    @GetMapping(value = "/{tenantId}/status/{status}")
	    public ResponseEntity<Object> getAllocationTemplateByStatus(@PathVariable(value = "tenantId") String tenantId,
	                                                            @PathVariable(value = "status") String status) {
	        SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();

	        if (status.equals("ACTIVE") || status.equals("INACTIVE")) {
	            List<AllocationTemplate> isPresentAllocationTemplate = allocationTemplateService.findByStatus(status);
	            int size = isPresentAllocationTemplate.size();
	            if (size > 0) {
	                return new ResponseEntity<>(isPresentAllocationTemplate, HttpStatus.OK);
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
	     * Get allocation template by code
	     *
	     * @param tenantId the id of tenant
	     * @param code   the code
	     * @return  allocation template if record exists, otherwise <code>204 - No Content</code>
	     */
		@GetMapping(value = "/{tenantId}/code/{code}")
		public ResponseEntity<Object> getAllocationTemplateByCode(@PathVariable(value = "tenantId", required = true) String tenantId,
														   	            @PathVariable(value = "code", required = true) String code){
			Optional<AllocationTemplate> isPresentAllocationTemplate = allocationTemplateService.findByCode(code);
			if(isPresentAllocationTemplate.isPresent()) {
				return new ResponseEntity<>(isPresentAllocationTemplate.get(), HttpStatus.OK);
			}
			else {
				SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
				responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
				return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
			}
		}
		
		  /**
	     * Get allocation template by name
	     *
	     * @param tenantId the id of tenant
	     * @param name   the name
	     * @return the list of all allocation template if record exists, otherwise <code>204 - No Content</code>
	     */
		@GetMapping(value = "/{tenantId}/name/{name}")
		public ResponseEntity<Object> getCoreProductByDescription(@PathVariable(value = "tenantId", required = true) String tenantId,
																        @PathVariable(value = "name", required = true) String name){
			List<AllocationTemplate> isPresentAllocationTemplate = allocationTemplateService.getByName(name);
			if(!isPresentAllocationTemplate.isEmpty()) {
				return new ResponseEntity<>(isPresentAllocationTemplate, HttpStatus.OK);
			}
			else {
				SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
				responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
				return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
			}
		}
		/**
	     * Saves the allocation template
	     *
	     * @param tenantId                  the id of the tenant
	     * @param allocationTemplateAddResource the object to save
	     * @return message if record successfully saved.
	     * @see AllocationTemplateAddResource
	     */
	    @PostMapping(value = "/{tenantId}")
	    public ResponseEntity<Object> addAllocationTemplate(@PathVariable(value = "tenantId") String tenantId,
	                                                    @Valid @RequestBody AllocationTemplateAddResource allocationTemplateAddResource) {
	        if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty()) {
	            throw new UserNotFound(environment.getProperty(COMMON_USER_NOT_FOUND));
	        }
	        SuccessAndErrorDetails successAndErrorDetails;
	        AllocationTemplate coreProduct = allocationTemplateService.addAllocationTemplate(tenantId, allocationTemplateAddResource);
	        successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty(RECORD_CREATED), Long.toString(coreProduct.getId()));
	        return new ResponseEntity<>(successAndErrorDetails, HttpStatus.CREATED);
	    }
	    
	    /**
	     * Updates the allocation template
	     *
	     * @param tenantId                     the id of the tenant
	     * @param id                           the id of the object
	     * @param allocationTemplateUpdateResource the object which contains data
	     * @return the message
	     * @see AllocationTemplateUpdateResource
	     */
	    @PutMapping(value = "/{tenantId}/{id}")
	    public ResponseEntity<Object> updateAllocationTemplate(@PathVariable(value = "tenantId") String tenantId,
	                                                       @PathVariable(value = "id") Long id,
	                                                       @Valid @RequestBody AllocationTemplateUpdateResource allocationTemplateUpdateResource) {
	        if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty()) {
	            throw new UserNotFound(environment.getProperty(COMMON_USER_NOT_FOUND));
	        }

	        SuccessAndErrorDetails successAndErrorDetails = new SuccessAndErrorDetails();
	        Optional<AllocationTemplate> isPresentAllocationTemplate = allocationTemplateService.findById(id);
	        if (isPresentAllocationTemplate.isPresent()) {
	        	AllocationTemplate coreProduct = allocationTemplateService.updateAllocationTemplate(tenantId, id, allocationTemplateUpdateResource);
	            successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty(RECORD_UPDATED), coreProduct.getId().toString());
	            return new ResponseEntity<>(successAndErrorDetails, HttpStatus.OK);
	        } else {
	            successAndErrorDetails.setMessages(RECORD_NOT_FOUND);
	            return new ResponseEntity<>(successAndErrorDetails, HttpStatus.UNPROCESSABLE_ENTITY);
	        }
	    }
	    
	    /**
		* approve allocation template pending
		* @param @PathVariable{tenantId}
		* @param @PathVariable{pendingId}
		* @return SuccessAndErrorDetailsDto
		*/
		@PutMapping(value = "{tenantId}/approve/{pendingId}")
		public ResponseEntity<Object> approve(@PathVariable(value = "tenantId", required = true) String tenantId, @PathVariable(value = "pendingId", required = true) Long pendingId){
			SuccessAndErrorDetailsResource successAndErrorDetailsResource=new SuccessAndErrorDetailsResource();
			Optional<AllocationTemplatePending> isPresentAllocationTemplatePending = allocationTemplateService.getByPendingId(pendingId);
			if(isPresentAllocationTemplatePending.isPresent()) {
		
				if(allocationTemplateService.approveReject(tenantId, pendingId, WorkflowStatus.COMPLETE)) {
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
		* Reject allocation template pending
		* @param @PathVariable{tenantId}
		* @param @PathVariable{pendingId}
		* @return SuccessAndErrorDetailsDto
		*/
		@PutMapping(value = "{tenantId}/reject/{pendingId}")
		public ResponseEntity<Object> reject(@PathVariable(value = "tenantId", required = true) String tenantId, @PathVariable(value = "pendingId", required = true) Long pendingId){
			SuccessAndErrorDetailsResource successAndErrorDetailsResource=new SuccessAndErrorDetailsResource();
			Optional<AllocationTemplatePending> isPresentAllocationTemplatePending = allocationTemplateService.getByPendingId(pendingId);
			if(isPresentAllocationTemplatePending.isPresent()) {
		
				if(allocationTemplateService.approveReject(tenantId, pendingId, WorkflowStatus.REJECT)) {
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
		 * Search allocation template
		 *
		 * @param tenantId - the tenant id
		 * @param searchq - the searchq
		 * @param status - the status
		 * @param approveStatus - the approve status
		 * @param pageable - the pageable
		 * @return the page
		 */
		@GetMapping(value = "/{tenantId}/workflow-items/search")
		public Page<AllocationTemplatePending> searchAllocationTemplate(@PathVariable(value = "tenantId", required = true) String tenantId,
				@RequestParam(value = "searchq", required = false) String searchq,
				@RequestParam(value = "status", required = false) String status,
				@RequestParam(value = "approveStatus", required = false) String approveStatus,
				@PageableDefault(size = 10) Pageable pageable) {
			if(pageable.getPageSize()>Constants.MAX_PAGEABLE_LENGTH) 
				throw new PageableLengthException(environment.getProperty(pageableLength));
			return allocationTemplateService.searchAllocationTemplate(searchq, status, approveStatus, pageable);
		}
		
		/**
		  * Gets the allocation template by pending id.
		  *
		  * @param tenantId the tenant id
		  * @param id the id
		  * @return the allocation template by pending id
		  */
		@GetMapping(value = "/{tenantId}/pending-id/{id}")
	 	public ResponseEntity<Object> getFeeChargeByPendingId(@PathVariable(value = "tenantId", required = true) String tenantId, 
	 			                                              @PathVariable(value = "id", required = true) Long id) {
	 		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
		 	Optional<AllocationTemplatePending> allocationTemplatePending = allocationTemplateService.getByPendingId(id);
		 	if (allocationTemplatePending.isPresent()) {
		 		return new ResponseEntity<>(allocationTemplatePending.get(), HttpStatus.OK);
		 	} 
		 	else {
		 		responseMessage.setMessages(RECORD_NOT_FOUND);
				return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		 	}
	 	}		
		
		
}
