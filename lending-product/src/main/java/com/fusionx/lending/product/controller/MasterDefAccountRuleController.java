package com.fusionx.lending.product.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.EligibilityCurrencyPending;
import com.fusionx.lending.product.domain.InterestRateType;
import com.fusionx.lending.product.domain.MasterDefAccountRule;
import com.fusionx.lending.product.domain.MasterDefAccountRulePending;
import com.fusionx.lending.product.domain.MasterDefAccountRuleSetStandardPending;
import com.fusionx.lending.product.domain.MasterDefinition;
import com.fusionx.lending.product.domain.MasterDefinitionPending;
import com.fusionx.lending.product.enums.CommonApproveStatus;
import com.fusionx.lending.product.enums.MasterDefAccountRuleSetStandardEnum;
import com.fusionx.lending.product.enums.WorkflowStatus;
import com.fusionx.lending.product.exception.PageableLengthException;
import com.fusionx.lending.product.exception.UserNotFound;
import com.fusionx.lending.product.repository.MasterDefAccountRulePendingRepository;
import com.fusionx.lending.product.repository.MasterDefAccountRuleSetStandardPendingRepository;
import com.fusionx.lending.product.repository.MasterDefinitionPendingRepository;
import com.fusionx.lending.product.resources.AddBaseRequest;
import com.fusionx.lending.product.resources.MasterDefAccountRuleAddResource;
import com.fusionx.lending.product.resources.MasterDefAccountRuleDetailsResponse;
import com.fusionx.lending.product.resources.MasterDefAccountRulePendingDetailsResponse;
import com.fusionx.lending.product.resources.MasterDefinitionAccountRuleUpdateResource;
import com.fusionx.lending.product.resources.MasterDefinitionUpdateResource;
import com.fusionx.lending.product.resources.SuccessAndErrorDetails;
import com.fusionx.lending.product.resources.SuccessAndErrorDetailsResource;
import com.fusionx.lending.product.service.MasterDefAccountRuleService;
import com.fusionx.lending.product.service.MasterDefinitionService;

/**
 *  Master Def Account Eligibility Controller
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   17-07-2021              	         	Nipun      Created
 *    
 ********************************************************************************************************
 */

@RestController
@RequestMapping("/master-def-account-rule")
@CrossOrigin("*")
public class MasterDefAccountRuleController  extends MessagePropertyBase{
	

	
	@Autowired
	private MasterDefAccountRuleService masterDefAccountRuleService;
	@Autowired
	public MasterDefinitionService masterDefinitionService;
	

	@Autowired
	private MasterDefAccountRuleSetStandardPendingRepository masterDefAccountRuleSetStandardPendingRepository;
	
	@Autowired
	private MasterDefAccountRulePendingRepository masterDefAccountRulePendingRepository;
	
	@Autowired
	private MasterDefinitionPendingRepository masterDefinitionPendingRepository;
	
	
	private String pageableLength = "common.pageable-length";
 
	/**

     * save Master Def Account Eligibility
     * @param @PathVariable{tenantId}
     * @param @RequestBody{AddBaseRequest}
     * @return SuccessAndErrorDetailsDto
     */
	@PostMapping("/{tenantId}")
	public ResponseEntity<Object> addMasterDefAccountEligibility(@PathVariable(value = "tenantId", required = true) String tenantId,
   									                  @Valid @RequestBody MasterDefAccountRuleAddResource masterDefAccountRuleAddResource){
		
		if (LogginAuthentcation.getInstance().getUserName() == null
				|| LogginAuthentcation.getInstance().getUserName().isEmpty()) {
			throw new UserNotFound(environment.getProperty(COMMON_USER_NOT_FOUND));
		}

		
		MasterDefAccountRule masterDefAccountEligibility = masterDefAccountRuleService.create(tenantId,masterDefAccountRuleAddResource);//tenantId, null
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource(getEnvironment().getProperty(RECORD_CREATED), masterDefAccountEligibility.getId() );
		return new ResponseEntity<>(responseMessage,HttpStatus.CREATED);
	}
	
	
	@PutMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> updateMasterDefinition(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id,
			@Valid @RequestBody MasterDefinitionAccountRuleUpdateResource masterDefinitionAccountRuleUpdateResource) {
		if (LogginAuthentcation.getInstance().getUserName() == null
				|| LogginAuthentcation.getInstance().getUserName().isEmpty()) {
			throw new UserNotFound(environment.getProperty(COMMON_USER_NOT_FOUND));
		}

		SuccessAndErrorDetails successAndErrorDetails = new SuccessAndErrorDetails();

		MasterDefinition masterDefinition = masterDefAccountRuleService.updateMasterDefinitionAccountRule(tenantId, id, masterDefinitionAccountRuleUpdateResource);
		if (masterDefinition!= null) {

				
				successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty("rec.updated"),
						masterDefinition.getId().toString());
				return new ResponseEntity<>(successAndErrorDetails, HttpStatus.OK);


		} else {
			successAndErrorDetails.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(successAndErrorDetails, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
	
	
	
	@GetMapping(value = "{tenantId}/approve/{pendingId}")
	public ResponseEntity<Object> approve(@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "pendingId", required = true) Long pendingId) {
		SuccessAndErrorDetailsResource successAndErrorDetailsResource = new SuccessAndErrorDetailsResource();
		Optional<MasterDefinitionPending> isPresentEligiTempPending = masterDefinitionService.getByPendingId(pendingId);
		if (isPresentEligiTempPending.isPresent()) {
			masterDefAccountRuleService.temporyApprove(pendingId);
//			if (masterDefinitionService.approveReject(tenantId, pendingId, WorkflowStatus.COMPLETE)) {
				successAndErrorDetailsResource = new SuccessAndErrorDetailsResource(
						environment.getProperty("common.approved"), pendingId.toString());
				return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.OK);
//			} else {
//				successAndErrorDetailsResource.setMessages(environment.getProperty("common.can-not-approved"));
//				return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.UNPROCESSABLE_ENTITY);
//			}

		} else {
			successAndErrorDetailsResource.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
	
	
	
	@GetMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> getById1(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id) {
		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();

		List<String> setStandardsList = Stream.of(MasterDefAccountRuleSetStandardEnum.values())
                .map(MasterDefAccountRuleSetStandardEnum::name)
                .collect(Collectors.toList());
		
		Optional<MasterDefinitionPending> MasterDefinitionPendingOptional = masterDefinitionPendingRepository.findById(id);
		Optional<MasterDefAccountRulePending> masterDefAccountRulePendingOptional = masterDefAccountRulePendingRepository.findByMasterDefinitionPending(MasterDefinitionPendingOptional.get());
		List<MasterDefAccountRuleSetStandardPending> MasterDefAccountRuleSetStandardPendingList = masterDefAccountRuleSetStandardPendingRepository.findByMasterDefinitionPending(MasterDefinitionPendingOptional.get());
		if (MasterDefAccountRuleSetStandardPendingList != null) {
			return new ResponseEntity<>(MasterDefAccountRuleSetStandardPendingList, HttpStatus.OK);
		} else {
			responseMessage.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	/**
	 * Search Master Def Account Rule.
	 *
	 * @param tenantId - the tenant id
	 * @param searchq - the searchq
	 * @param status - the status
	 * @param approveStatus - the approve status
	 * @param pageable - the pageable
	 * @return the page
	 */
	@GetMapping(value = "/{tenantId}/workflow-items/search")
	public Page<MasterDefAccountRulePending> searchMasterDefAccountRule(@PathVariable(value = "tenantId", required = true) String tenantId,
			@RequestParam(value = "searchq", required = false) String searchq,
			@RequestParam(value = "status", required = false) String status,
			@RequestParam(value = "approveStatus", required = false) String approveStatus,
			@PageableDefault(size = 10) Pageable pageable) {
		if(pageable.getPageSize()>Constants.MAX_PAGEABLE_LENGTH) 
			throw new PageableLengthException(environment.getProperty(pageableLength));
		return masterDefAccountRuleService.searchMasterDefAccountRule(searchq, status, approveStatus, pageable);
	}
	
	
	
	/**
	 * get MasterDefinition by id
	 * 
	 * @param @PathVariable{tenantId}
	 * @param @PathVariable{id}
	 * @return Option
	 */
	@GetMapping(value = "/{tenantId}/master-def-pending/{id}")
	public ResponseEntity<Object> getAccountRulePendingByMasterDefPendingId(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id) {
		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
		MasterDefAccountRulePendingDetailsResponse response = masterDefAccountRuleService.getAccountRulePendingDetailsMasterDefinitionPendingId(id);
		if (response != null) {
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			responseMessage.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(responseMessage, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
	
	
	
	@GetMapping(value = "/{tenantId}/master-definition/{id}")
	public ResponseEntity<Object> getAccountRuleDetailsByMasterDefId(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id) {
		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
		 MasterDefAccountRuleDetailsResponse response = masterDefAccountRuleService.getAccountRuleDetailsMasterDefinitionId(id);
		if (response != null) {
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			responseMessage.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(responseMessage, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}



}
