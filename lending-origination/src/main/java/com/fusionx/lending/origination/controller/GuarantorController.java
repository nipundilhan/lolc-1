package com.fusionx.lending.origination.controller;

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

import com.fusionx.lending.origination.Constants;
import com.fusionx.lending.origination.base.MessagePropertyBase;
import com.fusionx.lending.origination.core.LogginAuthentcation;
import com.fusionx.lending.origination.domain.Guarantor;
import com.fusionx.lending.origination.enums.WorkflowStatus;
import com.fusionx.lending.origination.exception.PageableLengthException;
import com.fusionx.lending.origination.exception.UserNotFound;
import com.fusionx.lending.origination.resource.ApplicantBlackListApproveRejectResource;
import com.fusionx.lending.origination.resource.ApprovalCategoryUpdateResource;
import com.fusionx.lending.origination.resource.ApprovePendingGuarantorBlacklistResource;
import com.fusionx.lending.origination.resource.GuarantorAddResource;
import com.fusionx.lending.origination.resource.SuccessAndErrorDetailsResource;
import com.fusionx.lending.origination.resource.UpdateGuarantorResource;
import com.fusionx.lending.origination.service.GuarantorService;

/**
 * guarantor Controller
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   26-04-2021   							 Thamokshi        Created
 *    
 ********************************************************************************************************
 */
@RestController
@RequestMapping(value = "/guarantor")
@CrossOrigin(origins = "*")
public class GuarantorController  extends MessagePropertyBase{
	
	@Autowired
	private GuarantorService guarantorService;

	private String userNotFound = "common.user-not-found";
	private String commonSaved = "common.saved";
	private String commonUpdated = "common.updated";
	private String pageableLength = "common.pageable-length";
	private String recordNotFound = "common.record-not-found";
	
	/**
	 * addGuarantor
	 * @param tenantId
	 * @param guarantorAddResource
	 * @return
	 */
	@PostMapping("/{tenantId}")
	public ResponseEntity<Object> addGuarantor(@PathVariable(value = "tenantId", required = true) String tenantId,
			   									     @Valid @RequestBody GuarantorAddResource guarantorAddResource){
		
		Guarantor guarantor = guarantorService.addGuarantor(tenantId, guarantorAddResource);
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource(getEnvironment().getProperty(RECORD_CREATED), Long.toString(guarantor.getId()));
		return new ResponseEntity<>(responseMessage,HttpStatus.CREATED);
		
	}
	
//	/**
//	 * Approve Blacklist
//	 * @param tenantId
//	 * @param id
//	 * @param applicantBlackListApproveRejectResource
//	 * @return SuccessAndErrorDetailsResource
//	 */
//	@PutMapping(value = "{tenantId}/guarantor-blacklist/approve/{id}")
//	public ResponseEntity<Object> approveBlacklist(@PathVariable(value = "tenantId", required = true) String tenantId, @PathVariable(value = "id", required = true) Long id,
//													@Valid @RequestBody ApplicantBlackListApproveRejectResource applicantBlackListApproveRejectResource){
//		SuccessAndErrorDetailsResource successAndErrorDetailsResource=new SuccessAndErrorDetailsResource();
//		Optional<Guarantor>isPresentGuarantor = guarantorService.getById(id);
//		if(isPresentGuarantor.isPresent()) {
//	
//			if(guarantorService.approveBlacklist(tenantId, id, applicantBlackListApproveRejectResource, WorkflowStatus.COMPLETE)) {
//			successAndErrorDetailsResource = new SuccessAndErrorDetailsResource(getEnvironment().getProperty("common.approved"), id.toString());
//			return new ResponseEntity<>(successAndErrorDetailsResource,HttpStatus.OK);
//			} else {
//				successAndErrorDetailsResource.setMessages(getEnvironment().getProperty("common.can-not-approved"));
//				return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.UNPROCESSABLE_ENTITY);
//			}
//
//		} else {
//			successAndErrorDetailsResource.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
//			return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.UNPROCESSABLE_ENTITY);
//		}
//	}
	
//	/**
//	* Reject Blacklist
//	* @param @PathVariable{tenantId}
//	* @param @RequestBody{ApplicantBlackListApproveRejectResource}
//	* @return SuccessAndErrorDetailsResource
//	*/
//	@PutMapping(value = "{tenantId}/guarantor-blacklist/reject/{id}")
//	public ResponseEntity<Object> rejectBlacklist(@PathVariable(value = "tenantId", required = true) String tenantId, @PathVariable(value = "id", required = true) Long id,
//													@Valid @RequestBody ApplicantBlackListApproveRejectResource applicantBlackListApproveRejectResource){
//		SuccessAndErrorDetailsResource successAndErrorDetailsResource=new SuccessAndErrorDetailsResource();
//		Optional<Guarantor> isPresentGuarantor = guarantorService.getById(id);
//		if(isPresentGuarantor.isPresent()) {
//	
//			if(guarantorService.approveBlacklist(tenantId, id, applicantBlackListApproveRejectResource, WorkflowStatus.REJECT)) {
//			successAndErrorDetailsResource = new SuccessAndErrorDetailsResource(getEnvironment().getProperty("common.rejected"), id.toString());
//			return new ResponseEntity<>(successAndErrorDetailsResource,HttpStatus.OK);
//			} else {
//				successAndErrorDetailsResource.setMessages(getEnvironment().getProperty("common.can-not-rejected"));
//				return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.UNPROCESSABLE_ENTITY);
//			}
//		} else {
//			successAndErrorDetailsResource.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
//			return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.UNPROCESSABLE_ENTITY);
//		}
//	}
//	
	
	@GetMapping(value = "/{tenantId}/lead-id/{id}")
	public ResponseEntity<Object> getByLeadId(@PathVariable(value = "tenantId", required = true) String tenantId, @PathVariable(value = "id", required = true) Long id) {
		
		List<Guarantor> guarantors = guarantorService.getByLeadId(id);
		if (!guarantors.isEmpty()) {
			return new ResponseEntity<>(guarantors, HttpStatus.OK);
		} else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(environment.getProperty(getEnvironment().getProperty(RECORD_NOT_FOUND)));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
//	/**
//	 * Search Approve Pending Guarantor Blacklist
//	 * @param tenantId
//	 * @param searchq
//	 * @param firstName
//	 * @param identificationNo
//	 * @param workflowStatus
//	 * @param pageable
//	 * @return Guarantor Blacklist list
//	 */
//	@GetMapping(value = "/{tenantId}/guarantor-blacklist/search")
//	public Page<ApprovePendingGuarantorBlacklistResource> searchApprovePendingGuarantorBlacklist(@PathVariable(value = "tenantId", required = true) String tenantId, 
//			@RequestParam(value = "searchq", required = false) String searchq,
//			@RequestParam(value = "firstName", required = false) String firstName,
//			@RequestParam(value = "identificationNo", required = false) String identificationNo,
//			@RequestParam(value = "workflowStatus", required = false) String workflowStatus,
//			@PageableDefault(size = 10) Pageable pageable) {
//		if(pageable.getPageSize()>Constants.MAX_PAGEABLE_LENGTH) 
//			throw new PageableLengthException(environment.getProperty(pageableLength));
//		return guarantorService.searchApprovePendingGuarantorBlacklist(searchq, firstName, identificationNo, workflowStatus, pageable);
//	}
	

	/**
	 * 
	 * @param tenantId
	 * @param id
	 * @param updateGuarantorResource
	 * @return
	 */
	@PutMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> update(@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id,
			@Valid @RequestBody UpdateGuarantorResource updateGuarantorResource) {
		if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new UserNotFound(environment.getProperty(userNotFound));
		if (guarantorService.existsById(id)) {
			guarantorService.update(tenantId, LogginAuthentcation.getInstance().getUserName(), id, updateGuarantorResource);
			SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty(commonUpdated));
			return new ResponseEntity<>(successDetailsDto, HttpStatus.OK);
		} else {
			SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(successDetailsDto, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
	
	
	@GetMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> getById(@PathVariable(value = "tenantId", required = true) String tenantId, @PathVariable(value = "id", required = true) Long id) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Guarantor leadInfo = guarantorService.findById(id);
		if (leadInfo!=null) {
			return new ResponseEntity<>(leadInfo, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(getEnvironment().getProperty(RECORD_NOT_FOUND)));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
}
