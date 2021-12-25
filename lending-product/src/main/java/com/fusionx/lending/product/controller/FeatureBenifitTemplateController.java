package com.fusionx.lending.product.controller;

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

import com.fusionx.lending.product.Constants;
import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.EligibilityPending;
import com.fusionx.lending.product.domain.FeatureBenifitTemplate;
import com.fusionx.lending.product.domain.FeatureBenifitTemplatePending;
import com.fusionx.lending.product.enums.WorkflowStatus;
import com.fusionx.lending.product.exception.PageableLengthException;
import com.fusionx.lending.product.exception.UserNotFound;
import com.fusionx.lending.product.resources.CommonAddResource;
import com.fusionx.lending.product.resources.CommonUpdateResource;
import com.fusionx.lending.product.resources.FeatureBenifitTemplatePendingDetailsResponse;
import com.fusionx.lending.product.resources.SuccessAndErrorDetails;
import com.fusionx.lending.product.resources.SuccessAndErrorDetailsResource;
import com.fusionx.lending.product.service.FeatureBenefitTemplateItemService;
import com.fusionx.lending.product.service.FeatureBenifitTemplateService;

/**
 *  Feature Benifit Template Controller
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   17-07-2021              	         	            Created
 *    
 ********************************************************************************************************
 */

@RestController
@RequestMapping("/feature-benefit-template")
@CrossOrigin("*")
public class FeatureBenifitTemplateController {
	
	
	@Autowired
	Environment environment;
	
	@Autowired
	private FeatureBenifitTemplateService featureBenifitTemplateService;
	
	@Autowired
	private FeatureBenefitTemplateItemService featureBenefitTemplateItemService;
	
	
	private static final String RECORD_NOT_FOUND = "Records not available";
	private String userNotFound="common.user-not-found";
	private String pageableLength = "common.pageable-length";
	
	/**
	 * get all FeatureBenifitTemplate 
	 * @param @PathVariable{tenantId}
	 * @param @PathVariable{all}
	 * @return List<Type>
	 */
    @GetMapping(value = "/{tenantId}/all")
    public ResponseEntity<Object> getAllFeatureBenifitTemplate(@PathVariable(value = "tenantId", required = true) String tenantId) {
    	SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
		 List<FeatureBenifitTemplate> featureBenifitTemplateList = featureBenifitTemplateService.getAll();
		int size = featureBenifitTemplateList.size();
		if (size > 0) {
			return new ResponseEntity<>((Collection<FeatureBenifitTemplate>) featureBenifitTemplateList,HttpStatus.OK);
		} 
		else {
			responseMessage.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
    
    /**
     * get FeatureBenifitTemplate  by id
     * @param @PathVariable{tenantId}
     * @param @PathVariable{id}
     * @return Option
     */
 	@GetMapping(value = "/{tenantId}/{id}")
 	public ResponseEntity<Object> getFeatureBenifitTemplateById(@PathVariable(value = "tenantId", required = true) String tenantId, 
 			                                              @PathVariable(value = "id", required = true) Long id) {
 		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
	 	Optional<FeatureBenifitTemplate> isPresentFeatureBenifitTemplate = featureBenifitTemplateService.getById(id);
	 	if (isPresentFeatureBenifitTemplate.isPresent()) {
	 		return new ResponseEntity<>(isPresentFeatureBenifitTemplate.get(), HttpStatus.OK);
	 	} 
	 	else {
	 		responseMessage.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
	 	}
 	}
 	
	/**
	 * get FeatureBenifitTemplate  by code
	 * @param @pathVariable{tenantId}
	 * @param @pathVariable {code}
	 * @return Optional
	 **/
	@GetMapping(value = "/{tenantId}/code/{code}")
	public ResponseEntity<Object> getFeatureBenifitTemplateByCode(@PathVariable(value = "tenantId", required = true) String tenantId,
													   	            @PathVariable(value = "code", required = true) String code){
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<FeatureBenifitTemplate> isPresentFeatureBenifitTemplate = featureBenifitTemplateService.getByCode(code);
		if(isPresentFeatureBenifitTemplate.isPresent()) {
			return new ResponseEntity<>(isPresentFeatureBenifitTemplate.get(), HttpStatus.OK);
		}
		else {
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * get FeatureBenifitTemplate  by name
	 * @param @pathVariable{tenantId}
	 * @param @pathVariable {name}
	 * @return List
	 **/
	@GetMapping(value = "/{tenantId}/name/{name}")
	public ResponseEntity<Object> getFeatureBenifitTemplateByName(@PathVariable(value = "tenantId", required = true) String tenantId,
															        @PathVariable(value = "name", required = true) String name){
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<FeatureBenifitTemplate> isPresentFeatureBenifitTemplate = featureBenifitTemplateService.getByName(name);
		if(!isPresentFeatureBenifitTemplate.isEmpty()) {
			return new ResponseEntity<>(isPresentFeatureBenifitTemplate, HttpStatus.OK);
		}
		else {
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
 	
 	/**
     * get FeatureBenifitTemplate  by status
     * @param @PathVariable{tenantId}
     * @param @PathVariable{status}
     * @return Option
     */
 	@GetMapping(value = "/{tenantId}/status/{status}")
 	public ResponseEntity<Object> getFeatureBenifitTemplateByStatus(@PathVariable(value = "tenantId", required = true) String tenantId, 
 			                                                  @PathVariable(value = "status", required = true) String status) {
 		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
 		
 		if(status.equals("ACTIVE") || status.equals("INACTIVE")){
		 	List<FeatureBenifitTemplate> isPresentFeatureBenifitTemplate = featureBenifitTemplateService.getByStatus(status);
		 	int size = isPresentFeatureBenifitTemplate.size();
		 	if (size>0) {
		 		return new ResponseEntity<>(isPresentFeatureBenifitTemplate, HttpStatus.OK);
		 	} 
		 	else {
		 		responseMessage.setMessages(RECORD_NOT_FOUND);
				return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		 	}
 		}
 		else{
	 		responseMessage.setMessages(environment.getProperty("invalid-status"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
	 	}
 	}
 	
 	/**
     * save FeatureBenifitTemplate 
     * @param @PathVariable{tenantId}
     * @param @RequestBody{CommoneAddResource}
     * @return SuccessAndErrorDetailsDto
     */
 	@PostMapping(value = "/{tenantId}")
 	public ResponseEntity<Object> addFeatureBenifitTemplate(@PathVariable(value = "tenantId", required = true) String tenantId,
 			                                          @Valid @RequestBody CommonAddResource commonAddResource) {
 		if(LogginAuthentcation.getInstance().getUserName()==null || LogginAuthentcation.getInstance().getUserName().isEmpty()) { 
 			throw new UserNotFound(environment.getProperty(userNotFound));
 		}
		
 		SuccessAndErrorDetails successAndErrorDetails = new SuccessAndErrorDetails();
 		FeatureBenifitTemplate featureBenifitTemplate = featureBenifitTemplateService.addFeatureBenifitTemplate(tenantId, commonAddResource);
	 	successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty("rec.saved"),Long.toString(featureBenifitTemplate.getId()));
		return new ResponseEntity<>(successAndErrorDetails,HttpStatus.CREATED);
 	}
	
	/**
     * update FeatureBenifitTemplate  
     * @param @PathVariable{tenantId}
     * @param @RequestBody{feeChargeUpdateResource}
     * @return SuccessAndErrorDetailsDto
     */
	@PutMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> updateFeatureBenifitTemplate(@PathVariable(value = "tenantId", required = true) String tenantId, 
			                                             @PathVariable(value = "id", required = true) Long id, 
			                                             @Valid @RequestBody CommonUpdateResource commonUpdateResource) {
		if(LogginAuthentcation.getInstance().getUserName()==null || LogginAuthentcation.getInstance().getUserName().isEmpty()) { 
 			throw new UserNotFound(environment.getProperty(userNotFound));
 		}
		
		SuccessAndErrorDetails successAndErrorDetails=new SuccessAndErrorDetails();
//		Optional<FeeCharge> isPresentFeeCharge = feeChargeService.getById(id);
//		if(isPresentFeeCharge.isPresent()) {
//			if(isPresentFeeCharge.get().getApproveStatus() == null || !isPresentFeeCharge.get().getApproveStatus().equals(CommonApproveStatus.PENDING))
//			{
//				FeeCharge feeCharge = feeChargeService.updateFeeCharge(tenantId, id, feeChargeUpdateResource);
				FeatureBenifitTemplatePending featureBenifitTemplatePending = featureBenifitTemplateService.updateFeatureBenifitTemplate(tenantId, id, commonUpdateResource);
				successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty("rec.updated"), featureBenifitTemplatePending.getId().toString());
	        	return new ResponseEntity<>(successAndErrorDetails,HttpStatus.OK);
//			} else {
//				successAndErrorDetails.setMessages(environment.getProperty("common.can-not-update"));
//				return new ResponseEntity<>(successAndErrorDetails, HttpStatus.UNPROCESSABLE_ENTITY);
//			}
			
//		}
//		else {
//			successAndErrorDetails.setMessages(RECORD_NOT_FOUND);
//			return new ResponseEntity<>(successAndErrorDetails, HttpStatus.UNPROCESSABLE_ENTITY);
//		}
	}
	
	/**
	* approve FeatureBenifitTemplate  Pending
	* @param @PathVariable{tenantId}
	* @param @PathVariable{pendingId}
	* @return SuccessAndErrorDetailsDto
	*/
	@PutMapping(value = "{tenantId}/approve/{pendingId}")
	public ResponseEntity<Object> approve(@PathVariable(value = "tenantId", required = true) String tenantId, @PathVariable(value = "pendingId", required = true) Long pendingId){
		SuccessAndErrorDetailsResource successAndErrorDetailsResource=new SuccessAndErrorDetailsResource();
		Optional<FeatureBenifitTemplatePending> isPresentEligiTempPending = featureBenifitTemplateService.getByPendingId(pendingId);
		
 		if(LogginAuthentcation.getInstance().getUserName()==null || LogginAuthentcation.getInstance().getUserName().isEmpty()) { 
 			throw new UserNotFound(environment.getProperty(userNotFound));
 		}
		
		
		if(isPresentEligiTempPending.isPresent()) {
	
			if(featureBenefitTemplateItemService.approveReject(tenantId, pendingId, WorkflowStatus.COMPLETE , LogginAuthentcation.getInstance().getUserName())) {
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
	* Reject FeatureBenifitTemplate  Pending
	* @param @PathVariable{tenantId}
	* @param @PathVariable{pendingId}
	* @return SuccessAndErrorDetailsDto
	*/
	@PutMapping(value = "{tenantId}/reject/{pendingId}")
	public ResponseEntity<Object> reject(@PathVariable(value = "tenantId", required = true) String tenantId, @PathVariable(value = "pendingId", required = true) Long pendingId){
		SuccessAndErrorDetailsResource successAndErrorDetailsResource=new SuccessAndErrorDetailsResource();
		Optional<FeatureBenifitTemplatePending> isPresentEligiPending = featureBenifitTemplateService.getByPendingId(pendingId);
		
 		if(LogginAuthentcation.getInstance().getUserName()==null || LogginAuthentcation.getInstance().getUserName().isEmpty()) { 
 			throw new UserNotFound(environment.getProperty(userNotFound));
 		}
 		
		if(isPresentEligiPending.isPresent()) {
	
			if(featureBenefitTemplateItemService.approveReject(tenantId, pendingId, WorkflowStatus.REJECT , LogginAuthentcation.getInstance().getUserName())) {
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
	 * Gets the FeatureBenifitTemplate by pending id.
	 *
	 * @param tenantId - the tenant id
	 * @param pendingId - the pending id
	 * @return  FeatureBenifitTemplate
	 */
	@GetMapping(value = "/{tenantId}/pending/{pendingId}")
	public ResponseEntity<Object> getEligibilityByPendingId(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "pendingId", required = true) Long pendingId) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<FeatureBenifitTemplatePending> isPresentEligibilityPending = featureBenifitTemplateService.getByPendingId(pendingId);
		if (isPresentEligibilityPending.isPresent()) {
			return new ResponseEntity<>(isPresentEligibilityPending, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	@GetMapping(value = "/{tenantId}/template-pending/{id}")
	public ResponseEntity<Object> findByFeatureBenifitTemplatePendingId(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id) {
		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();

		FeatureBenifitTemplatePendingDetailsResponse response = featureBenefitTemplateItemService.findDetailsByFeatureBenifitTemplatePendingId(id);
		
		if (response != null) {
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			responseMessage.setMessages("record not found");
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Search FeatureBenifitTemplate pending.
	 *
	 * @param tenantId - the tenant id
	 * @param searchq - the searchq
	 * @param status - the status
	 * @param approveStatus - the approve status
	 * @param pageable - the pageable
	 * @return the page
	 */
	@GetMapping(value = "/{tenantId}/workflow-items/search")
	public Page<FeatureBenifitTemplatePending> searchType(@PathVariable(value = "tenantId", required = true) String tenantId,
			@RequestParam(value = "searchq", required = false) String searchq,
			@RequestParam(value = "status", required = false) String status,
			@RequestParam(value = "approveStatus", required = false) String approveStatus,
			@PageableDefault(size = 10) Pageable pageable) {
		if(pageable.getPageSize()>Constants.MAX_PAGEABLE_LENGTH) 
			throw new PageableLengthException(environment.getProperty(pageableLength));
		return featureBenifitTemplateService.searchFeatureBenifitTemplatePending(searchq, status, approveStatus, pageable);
	}
	

}
