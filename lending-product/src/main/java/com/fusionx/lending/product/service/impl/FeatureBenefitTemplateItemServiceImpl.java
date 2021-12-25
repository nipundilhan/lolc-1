package com.fusionx.lending.product.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.core.LoggerRequest;
import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.FeatureBenefitItem;
import com.fusionx.lending.product.domain.FeatureBenefitTemplateItem;
import com.fusionx.lending.product.domain.FeatureBenefitTemplateItemEligibilityPending;
import com.fusionx.lending.product.domain.FeatureBenefitTemplateItemHistory;
import com.fusionx.lending.product.domain.FeatureBenefitTemplateItemPending;
import com.fusionx.lending.product.domain.FeatureBenifitTemplate;
import com.fusionx.lending.product.domain.FeatureBenifitTemplateHistory;
import com.fusionx.lending.product.domain.FeatureBenifitTemplatePending;
import com.fusionx.lending.product.domain.LendingWorkflow;
import com.fusionx.lending.product.enums.CommonApproveStatus;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.enums.WorkflowStatus;
import com.fusionx.lending.product.enums.WorkflowType;
import com.fusionx.lending.product.exception.ValidateRecordException;
import com.fusionx.lending.product.repository.FeatureBenefitItemRepository;
import com.fusionx.lending.product.repository.FeatureBenefitTemplateItemEligibilityPendingRepository;
import com.fusionx.lending.product.repository.FeatureBenefitTemplateItemHistoryRepository;
import com.fusionx.lending.product.repository.FeatureBenefitTemplateItemPendingRepository;
import com.fusionx.lending.product.repository.FeatureBenefitTemplateItemRepository;
import com.fusionx.lending.product.repository.FeatureBenifitTemplateHistoryRepository;
import com.fusionx.lending.product.repository.FeatureBenifitTemplatePendingRepository;
import com.fusionx.lending.product.repository.FeatureBenifitTemplateRepository;
import com.fusionx.lending.product.repository.LendingWorkflowRepository;
import com.fusionx.lending.product.resources.FeatureBenefitItemUsageResource;
import com.fusionx.lending.product.resources.FeatureBenefitTemplateItemAddResource;
import com.fusionx.lending.product.resources.FeatureBenefitTemplateItemPendingDetailsResponse;
import com.fusionx.lending.product.resources.FeatureBenefitTemplateItemUpdateResource;
import com.fusionx.lending.product.resources.FeatureBenifitTemplatePendingDetailsResponse;
import com.fusionx.lending.product.resources.WorkflowInitiationRequestResource;
import com.fusionx.lending.product.resources.WorkflowRulesResource;
import com.fusionx.lending.product.service.FeatureBenefitTemplateItemEligibilityService;
import com.fusionx.lending.product.service.FeatureBenefitTemplateItemService;
import com.fusionx.lending.product.service.LendingWorkflowService;
import com.fusionx.lending.product.service.ValidationService;

@Component
@Transactional(rollbackFor=Exception.class)
public class FeatureBenefitTemplateItemServiceImpl  extends MessagePropertyBase  implements FeatureBenefitTemplateItemService{
	
    /**
     * Common Properties
     */
    protected static final String DUPLICATE_FEATURE_BENIFIT_ITEM = "featureBenifitItem-duplicate";
    protected static final String INACTIVE_FEATURE_BENIFIT_ITEM = "featureBenifitItem-inactive-status";
	
    /**
     * JSON Properties
     */
    protected static final String FEATURE_BENIFIT_TEMPLATE_ID = "featureBenifitTemplateId";
    protected static final String FEATURE_BENIFIT_TEMPLATE_PENDING_ID = "featureBenifitTemplatePendingId";
    protected static final String FEATURE_BENIFIT_TEMPLATE_ITEM_ID = "featureBenifitTemplateItemId";
    protected static final String FEATURE_BENIFIT_ITEM_ID = "featureBenifitItemId";
	
	@Autowired
	private FeatureBenefitTemplateItemRepository featureBenefitTemplateItemRepository;	
	@Autowired
	private FeatureBenifitTemplateRepository featureBenefitTemplateRepository;	
	@Autowired
	private FeatureBenefitItemRepository featureBenefitItemRepository;	
	@Autowired
	private FeatureBenefitTemplateItemHistoryRepository featureBenefitTemplateItemHistoryRepository;	
	@Autowired
	private FeatureBenefitTemplateItemPendingRepository featureBenefitTemplateItemPendingRepository;	
	@Autowired
	private FeatureBenifitTemplatePendingRepository featureBenefitTemplatePendingRepository;	
	@Autowired
	private FeatureBenifitTemplatePendingRepository featureBenifitTemplatePendingRepository;	
	@Autowired
	private FeatureBenifitTemplateRepository featureBenifitTemplateRepository;
	@Autowired
	private FeatureBenefitTemplateItemEligibilityPendingRepository featureBenefitTemplateItemEligibilityPendingRepository;
	@Autowired
	private LendingWorkflowRepository lendingWorkflowRepository;
	
	@Autowired 
	private ValidationService validationService;
	@Autowired
	private LendingWorkflowService lendingWorkflowService;	
	@Autowired
	private FeatureBenefitTemplateItemEligibilityService featureBenefitTemplateItemEligibilityService;
	@Autowired
	private FeatureBenifitTemplateHistoryRepository featureBenifitTemplateHistoryRepository;

		

	
	private static final String DEFAULT_APPROVAL_USER = "kie-server";

	private static final String DOMAIN_PATH = "com.fusionx.LendingWF";

	private static final String DOMAIN = "LendingWF";

	
	
	@Override
	public FeatureBenefitTemplateItem createFeatureBenefitTemplateItem(String tenantId,FeatureBenefitTemplateItemAddResource featureBenefitTemplateItemAddResource) {	
		
		Optional<FeatureBenifitTemplate> featureBenifitTemplateOptional = featureBenefitTemplateRepository.findById(validationService.stringToLong(featureBenefitTemplateItemAddResource.getFeatureBenefitTemplateId()));		

		if(!featureBenifitTemplateOptional.isPresent()) {
			//throw new exception
			throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), FEATURE_BENIFIT_TEMPLATE_ID);
		}
		FeatureBenefitItemUsageResource featureBenefitItemUsageResource = featureBenefitTemplateItemAddResource.getFeatureBenefitItemUsageResource();
		
		LoggerRequest.getInstance().logInfo("Feature Benifit Template Item********************************Item validations*********************************************");
		
		Optional<FeatureBenefitItem> featureBenefitItemOptional = featureBenefitItemRepository.findById(validationService.stringToLong(featureBenefitItemUsageResource.getFeatureBenefitItemId()));

	
		if(!featureBenefitItemOptional.isPresent()) {
			throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), FEATURE_BENIFIT_TEMPLATE_ID);
		}
		
		
		if(!(CommonStatus.ACTIVE.toString()).equals(featureBenefitItemOptional.get().getStatus())) {
			throw new ValidateRecordException(environment.getProperty(INACTIVE_FEATURE_BENIFIT_ITEM), MESSAGE);
		}
		

		List<Long> addedFeatureBenifitItemIdList = getFeatureBenefitItemIdListRelatedToFeatureBenifitTemplate(featureBenifitTemplateOptional.get());
		
		
		if( (addedFeatureBenifitItemIdList != null) && (!addedFeatureBenifitItemIdList.isEmpty())
			&&(addedFeatureBenifitItemIdList.contains(featureBenefitItemOptional.get().getId())) ){

				throw new ValidateRecordException(environment.getProperty(DUPLICATE_FEATURE_BENIFIT_ITEM), MESSAGE);
							
		}
		
		FeatureBenefitTemplateItem featureBenefitTemplateItem = new FeatureBenefitTemplateItem();

		featureBenefitTemplateItem.setTenantId(tenantId);
		featureBenefitTemplateItem.setFeatureBenefitTemplate(featureBenifitTemplateOptional.get());
		featureBenefitTemplateItem.setFeatureBenefitItem(featureBenefitItemOptional.get());
		featureBenefitTemplateItem.setDescription(null);
		featureBenefitTemplateItem.setStatus(CommonStatus.valueOf(featureBenefitItemUsageResource.getStatus()));
		featureBenefitTemplateItem.setCreatedDate(validationService.getCreateOrModifyDate());
		featureBenefitTemplateItem.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
		featureBenefitTemplateItem.setModifiedDate(validationService.getCreateOrModifyDate());
		featureBenefitTemplateItem.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
		featureBenefitTemplateItem.setSyncTs(validationService.getCreateOrModifyDate());

		featureBenefitTemplateItem = featureBenefitTemplateItemRepository.save(featureBenefitTemplateItem);
		LoggerRequest.getInstance().logInfo("Feature Benifit Template Item******************************** record inserted *********************************************");

		saveFeatureBenefitTemplateItemHistory(null,featureBenefitTemplateItem);
		
		return featureBenefitTemplateItem;			
	}
	
	
	@Override
	public FeatureBenefitTemplateItem directUpdateFeatureBenefitTemplateItem(Long id ,  FeatureBenefitItemUsageResource featureBenefitItemUsageResource) {
		
		
		Optional<FeatureBenefitTemplateItem> featureBenefitTemplateItemOptional = featureBenefitTemplateItemRepository.findById(id);
		if(!featureBenefitTemplateItemOptional.isPresent()) {
			//throw new exception
			throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), MESSAGE);
		}
		
		FeatureBenefitTemplateItem featureBenefitTemplateItem = featureBenefitTemplateItemOptional.get();
		
		if(featureBenefitTemplateItem.getFeatureBenefitItem().getId() != validationService.stringToLong(featureBenefitItemUsageResource.getFeatureBenefitItemId())) {
			
			List<Long> addedFeatureBenifitItemIdList = getFeatureBenefitItemIdListRelatedToFeatureBenifitTemplate(featureBenefitTemplateItem.getFeatureBenefitTemplate());
			
			Optional<FeatureBenefitItem> featureBenefitItemOptional = featureBenefitItemRepository.findById(validationService.stringToLong(featureBenefitItemUsageResource.getFeatureBenefitItemId()));
					
			if(!featureBenefitItemOptional.isPresent()) {
				throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), FEATURE_BENIFIT_ITEM_ID);
			}
			
			if(!(CommonStatus.ACTIVE.toString()).equals(featureBenefitItemOptional.get().getStatus())) {
				//throw new exception
				throw new ValidateRecordException(environment.getProperty(INACTIVE_FEATURE_BENIFIT_ITEM), MESSAGE);
			}
			
			if( (addedFeatureBenifitItemIdList != null) && (!addedFeatureBenifitItemIdList.isEmpty())
				&&(addedFeatureBenifitItemIdList.contains(featureBenefitItemOptional.get().getId())) ){
					throw new ValidateRecordException(environment.getProperty(DUPLICATE_FEATURE_BENIFIT_ITEM), MESSAGE);
							
			}	
						
			featureBenefitTemplateItem.setFeatureBenefitItem(featureBenefitItemOptional.get());
		}
		
		featureBenefitTemplateItem.setStatus(CommonStatus.valueOf(featureBenefitItemUsageResource.getStatus()));
		featureBenefitTemplateItem.setDescription(null);
		featureBenefitTemplateItem.setModifiedDate(validationService.getCreateOrModifyDate());
		featureBenefitTemplateItem.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
		featureBenefitTemplateItem.setSyncTs(validationService.getCreateOrModifyDate());
		
		featureBenefitTemplateItem = featureBenefitTemplateItemRepository.save(featureBenefitTemplateItem);
		
		return featureBenefitTemplateItem;
		
	}

	@Override
	public List<FeatureBenefitTemplateItem> findByFeatureBenifitTemplateId(Long featureBenifitTemplateId) {
		
		Optional<FeatureBenifitTemplate> featureBenifitTemplateOptional = featureBenefitTemplateRepository.findById(featureBenifitTemplateId);
		if(!featureBenifitTemplateOptional.isPresent()) {
			//throw new exception
			throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), FEATURE_BENIFIT_TEMPLATE_ID);
		}
		

		List<FeatureBenefitTemplateItem> findAllByFeatureBenefitTemplateList = featureBenefitTemplateItemRepository.findAllByFeatureBenefitTemplate(featureBenifitTemplateOptional.get());
		return findAllByFeatureBenefitTemplateList;
	
	}
	
	
	public void saveFeatureBenefitTemplateItemHistory(Long featureBenifitTemplateItemPendingId,FeatureBenefitTemplateItem featureBenefitTemplateItem) {
		
		FeatureBenefitTemplateItemHistory featureBenefitTemplateItemHistory = new FeatureBenefitTemplateItemHistory();
		featureBenefitTemplateItemHistory.setTenantId(featureBenefitTemplateItem.getTenantId());
		featureBenefitTemplateItemHistory.setFeatureBenifitItemId(featureBenefitTemplateItem.getFeatureBenefitItem().getId());
		featureBenefitTemplateItemHistory.setFeatureBenifitTemplateItemId(featureBenefitTemplateItem.getId());
		featureBenefitTemplateItemHistory.setFeatureBenifitTemplateItemPendingId(featureBenifitTemplateItemPendingId);
		featureBenefitTemplateItemHistory.setDescription(featureBenefitTemplateItem.getDescription());
		featureBenefitTemplateItemHistory.setStatus(featureBenefitTemplateItem.getStatus());
		featureBenefitTemplateItemHistory.setCreatedDate(validationService.getCreateOrModifyDate());
		featureBenefitTemplateItemHistory.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
		featureBenefitTemplateItemHistory.setSyncTs(validationService.getCreateOrModifyDate());
		featureBenefitTemplateItemHistoryRepository.save(featureBenefitTemplateItemHistory);
		
		LoggerRequest.getInstance().logInfo("Feature Benifit Template Item******************************** History created *********************************************");
	}
	
	
	@Override
	public FeatureBenifitTemplatePending updateFeatureBenifitTemplate(String tenantId, Long id ,  FeatureBenefitTemplateItemUpdateResource featureBenefitTemplateItemUpdateResource) {
		
		Optional<FeatureBenifitTemplate> featureBenifitTemplateOptional = featureBenefitTemplateRepository.findById(id);		
		if(!featureBenifitTemplateOptional.isPresent()) {
			//throw new exception
			throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE),FEATURE_BENIFIT_TEMPLATE_ID);
		}
		
		
		if(featureBenefitTemplateItemUpdateResource.getFeatureBenefitTemplatePendingId() == null) {
			List<FeatureBenefitTemplateItemPending> featureBenefitTemplateItemPendingList = featureBenifitTemplatePendingRepository.findAllByFeatureBenifitTemplateAndApproveStatus(featureBenifitTemplateOptional.get(), CommonApproveStatus.PENDING);
			if(!featureBenefitTemplateItemPendingList.isEmpty()) {
				throw new ValidateRecordException(environment.getProperty("featureBenifitTemplate-item-error"), MESSAGE);
			}
		}
		
		boolean validRecord = validateFeatureBenifitTemplateItem(featureBenefitTemplateItemUpdateResource , featureBenifitTemplateOptional.get());
		
		FeatureBenifitTemplatePending featureBenifitTemplatePending = null;
		if(validRecord) {
			featureBenifitTemplatePending = approveOrGenerateWorkFlow(tenantId, LogginAuthentcation.getInstance().getUserName(),
				featureBenifitTemplateOptional.get(), featureBenefitTemplateItemUpdateResource);
		}else {
			throw new ValidateRecordException(environment.getProperty("featureBenifitTemplate-item-error"), MESSAGE);
		}

		return featureBenifitTemplatePending;
	}
	
	public boolean validateFeatureBenifitTemplateItem( FeatureBenefitTemplateItemUpdateResource featureBenefitTemplateItemUpdateResource , FeatureBenifitTemplate featureBenifitTemplate  ) {
		
		boolean validRecord = true;
		
		List<Long> addedFeatureBenifitItemIdList = getFeatureBenefitItemIdListRelatedToFeatureBenifitTemplate(featureBenifitTemplate);
		
		List<Long> addedFeatureBenifitItemIdPendingList = null;
		
		LoggerRequest.getInstance().logInfo("Feature Benifit Template Item********************************update senario validations*********************************************");
		
		if(featureBenefitTemplateItemUpdateResource.getFeatureBenefitTemplatePendingId() != null) {	
			Optional<FeatureBenifitTemplatePending> featureBenefitTemplatePendingOptional = featureBenefitTemplatePendingRepository.findById(validationService.stringToLong(featureBenefitTemplateItemUpdateResource.getFeatureBenefitTemplatePendingId()));
			if(!featureBenefitTemplatePendingOptional.isPresent()) {
				throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), FEATURE_BENIFIT_TEMPLATE_PENDING_ID);					
			}

			addedFeatureBenifitItemIdPendingList = getFeatureBenefitItemIdListRelatedToFeatureBenifitTemplatePending(featureBenefitTemplatePendingOptional.get());
		}
		
		LoggerRequest.getInstance().logInfo("Feature Benifit Template Item********************************check duplicates and data accuracy*********************************************");
		
		if(featureBenefitTemplateItemUpdateResource.getFeatureBenefitTemplateItemId() != null) {
			Optional<FeatureBenefitTemplateItem> featureBenefitTemplateItemOptional = featureBenefitTemplateItemRepository.findById(validationService.stringToLong(featureBenefitTemplateItemUpdateResource.getFeatureBenefitTemplateItemId()));
				if(!featureBenefitTemplateItemOptional.isPresent()) {
					throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), FEATURE_BENIFIT_TEMPLATE_ITEM_ID);
				}else {
					if(featureBenefitTemplateItemOptional.get().getFeatureBenefitTemplate().getId()!= featureBenifitTemplate.getId()) {
						throw new ValidateRecordException("feature benifit item not matched", MESSAGE);
					}
				}		
		} else {
			
			Optional<FeatureBenefitItem> featureBenefitItemOptional = featureBenefitItemRepository.findById(validationService.stringToLong(featureBenefitTemplateItemUpdateResource.getFeatureBenefitItemUsageResource().getFeatureBenefitItemId()));

			if(!featureBenefitItemOptional.isPresent()) {

				throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), FEATURE_BENIFIT_ITEM_ID);
			}
			
			if( (addedFeatureBenifitItemIdList != null) && (!addedFeatureBenifitItemIdList.isEmpty())
					&& (addedFeatureBenifitItemIdList.contains(validationService.stringToLong(featureBenefitTemplateItemUpdateResource.getFeatureBenefitItemUsageResource().getFeatureBenefitItemId())) ) ){
					throw new ValidateRecordException(environment.getProperty(DUPLICATE_FEATURE_BENIFIT_ITEM), MESSAGE);
							
			}
			
			if( (addedFeatureBenifitItemIdPendingList != null) && (!addedFeatureBenifitItemIdPendingList.isEmpty())
					&& (addedFeatureBenifitItemIdPendingList.contains(validationService.stringToLong(featureBenefitTemplateItemUpdateResource.getFeatureBenefitItemUsageResource().getFeatureBenefitItemId()))) ){
					
					throw new ValidateRecordException(environment.getProperty(DUPLICATE_FEATURE_BENIFIT_ITEM), MESSAGE);
								
			}
						
			if(!(CommonStatus.ACTIVE.toString()).equals(featureBenefitItemOptional.get().getStatus())) {
				//throw new exception
				throw new ValidateRecordException(environment.getProperty(INACTIVE_FEATURE_BENIFIT_ITEM), MESSAGE);
			}
		}
		

		
		return validRecord;
		
	}
	
	
	private FeatureBenifitTemplatePending approveOrGenerateWorkFlow(String tenantId, String user, FeatureBenifitTemplate featureBenifitTemplate,
			  FeatureBenefitTemplateItemUpdateResource featureBenefitTemplateItemUpdateResource) {
			
		
		FeatureBenifitTemplatePending featureBenifitTemplatePending = null;
		LoggerRequest.getInstance().logInfo("Feature Benifit Template Item******************************** update *********************************************");
		
		if(featureBenefitTemplateItemUpdateResource.getFeatureBenefitTemplatePendingId() != null) {	
			
			Optional<FeatureBenifitTemplatePending> featureBenefitTemplatePendingOptional = featureBenefitTemplatePendingRepository.findById(validationService.stringToLong(featureBenefitTemplateItemUpdateResource.getFeatureBenefitTemplatePendingId()));
			if(!featureBenefitTemplatePendingOptional.isPresent()) {
				throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), FEATURE_BENIFIT_TEMPLATE_PENDING_ID);		
			}
			
			featureBenifitTemplatePending = featureBenefitTemplatePendingOptional.get();
			saveFeatureBenefitTemplateItemPending(tenantId ,featureBenifitTemplatePending,featureBenefitTemplateItemUpdateResource);
			
			LoggerRequest.getInstance().logInfo("Feature Benifit Template Item******************************** attach to exisiting work flow*********************************************");
		
		} else {
			
			WorkflowRulesResource workflowRulesResource = null;
			Long processId = null;
			WorkflowType workflowType = WorkflowType.ELIGI_TEMP_APPROVAL;
			LendingWorkflow lendingWorkflow = null;

			LoggerRequest.getInstance().logInfo("Feature Benifit Template Item******************************** call workflow *********************************************");
			
			WorkflowInitiationRequestResource workflowInitiationRequestResource = new WorkflowInitiationRequestResource();
			workflowInitiationRequestResource.setApprovalUser(DEFAULT_APPROVAL_USER);
			workflowInitiationRequestResource.setApprovWorkflowType(workflowType);

			workflowRulesResource = validationService.invokedLendingProductRule(workflowType, DOMAIN_PATH, DOMAIN,tenantId);
			
//			featureBenifitTemplatePending = saveFeatureBenifitTemplatePending(featureBenifitTemplate , lendingWorkflow);
//			FeatureBenefitTemplateItemPending saveFeatureBenefitTemplateItemPending = saveFeatureBenefitTemplateItemPending(tenantId ,featureBenifitTemplatePending,featureBenefitTemplateItemUpdateResource);				
//			updatefeatureBenefitTemplateItem(saveFeatureBenefitTemplateItemPending);

			
			if (workflowRulesResource.getApprovalRequired().equalsIgnoreCase(CommonStatus.YES.toString())) {
				processId = validationService.initiateLendingProductWorkFlow(workflowInitiationRequestResource, tenantId);
				if (processId != null) {
					
					LoggerRequest.getInstance().logInfo("Feature Benifit Template Item******************************** add to pending table and create workflow *********************************************");
					
					lendingWorkflow = lendingWorkflowService.save(tenantId, processId, workflowType, user);
					featureBenifitTemplatePending = saveFeatureBenifitTemplatePending(featureBenifitTemplate , lendingWorkflow ,  CommonApproveStatus.PENDING);
					saveFeatureBenefitTemplateItemPending(tenantId ,featureBenifitTemplatePending,featureBenefitTemplateItemUpdateResource);	
				}
			} else {
				
				LoggerRequest.getInstance().logInfo("Feature Benifit Template Item******************************** update without workflow *********************************************");
				
				FeatureBenifitTemplatePending saveFeatureBenifitTemplatePending = saveFeatureBenifitTemplatePending(featureBenifitTemplate , lendingWorkflow ,  CommonApproveStatus.APPROVED);
				FeatureBenefitTemplateItemPending saveFeatureBenefitTemplateItemPending = saveFeatureBenefitTemplateItemPending(tenantId ,saveFeatureBenifitTemplatePending,featureBenefitTemplateItemUpdateResource);				
				addUpdatefeatureBenefitTemplateItem(saveFeatureBenefitTemplateItemPending , user);
		
			}		
		}
		
		return featureBenifitTemplatePending;
	}
	
	
	@Override
	public FeatureBenifitTemplatePending saveFeatureBenifitTemplatePending(FeatureBenifitTemplate featureBenifitTemplate , LendingWorkflow lendingWorkflow , CommonApproveStatus status ) {
		
		
        FeatureBenifitTemplatePending featureBenifitTemplatePending = new FeatureBenifitTemplatePending();
        featureBenifitTemplatePending.setTenantId(featureBenifitTemplate.getTenantId());
        featureBenifitTemplatePending.setFeatureBenifitTemplate(featureBenifitTemplate);
        featureBenifitTemplatePending.setLendingWorkflow(lendingWorkflow);
        featureBenifitTemplatePending.setCode(featureBenifitTemplate.getCode());
        featureBenifitTemplatePending.setName(featureBenifitTemplate.getName());
        featureBenifitTemplatePending.setStatus(featureBenifitTemplate.getStatus());
        featureBenifitTemplatePending.setApproveStatus(status);
        featureBenifitTemplatePending.setCreatedDate(validationService.getCreateOrModifyDate());
        featureBenifitTemplatePending.setSyncTs(validationService.getCreateOrModifyDate());
        featureBenifitTemplatePending.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
        
        featureBenifitTemplatePending = featureBenifitTemplatePendingRepository.save(featureBenifitTemplatePending);
		LoggerRequest.getInstance().logInfo("Feature Benifit Template Item******************************** save feature benifit template pending *********************************************");
        
        return featureBenifitTemplatePending;
		
	}
	
	public FeatureBenefitTemplateItemPending saveFeatureBenefitTemplateItemPending(String tenantId , FeatureBenifitTemplatePending featureBenifitTemplatePending
			,FeatureBenefitTemplateItemUpdateResource featureBenefitTemplateItemUpdateResource ) {
		
		
		Optional<FeatureBenefitItem> featureBenefitItemOptional = featureBenefitItemRepository.findById(validationService.stringToLong(featureBenefitTemplateItemUpdateResource.getFeatureBenefitItemUsageResource().getFeatureBenefitItemId()));		
		if(!featureBenefitItemOptional.isPresent()) {
			throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), FEATURE_BENIFIT_ITEM_ID);
		}
				
		FeatureBenefitTemplateItemPending featureBenefitTemplateItemPending = null;	
		FeatureBenefitTemplateItem featureBenefitTemplateItem = null;
		
		if(featureBenefitTemplateItemUpdateResource.getFeatureBenefitTemplateItemId() != null) {
			
			Optional<FeatureBenefitTemplateItem> featureBenefitTemplateItemOptional = featureBenefitTemplateItemRepository.findById(validationService.stringToLong(featureBenefitTemplateItemUpdateResource.getFeatureBenefitTemplateItemId()));
			if(!featureBenefitTemplateItemOptional.isPresent()) {
				throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), FEATURE_BENIFIT_TEMPLATE_ITEM_ID);		
			}
			featureBenefitTemplateItem = featureBenefitTemplateItemOptional.get();
			
			List<FeatureBenefitTemplateItemPending> featureBenefitTemplateItemPendingList = featureBenefitTemplateItemPendingRepository.findAllByFeatureBenifitTemplatePendingAndFeatureBenefitTemplateItem(featureBenifitTemplatePending, featureBenefitTemplateItemOptional.get());
			if(!featureBenefitTemplateItemPendingList.isEmpty()) {
				featureBenefitTemplateItemPending = featureBenefitTemplateItemPendingList.get(0);	
			}	
		}
		
		if(featureBenefitTemplateItemPending == null) {
			featureBenefitTemplateItemPending =  new FeatureBenefitTemplateItemPending();
			featureBenefitTemplateItemPending.setFeatureBenefitTemplateItem(featureBenefitTemplateItem);
			featureBenefitTemplateItemPending.setTenantId(tenantId);
			featureBenefitTemplateItemPending.setFeatureBenifitTemplatePending(featureBenifitTemplatePending);
			featureBenefitTemplateItemPending.setCreatedDate(validationService.getCreateOrModifyDate());
			featureBenefitTemplateItemPending.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
			
			
			featureBenefitTemplateItemPending.setFeatureBenefitItem(featureBenefitItemOptional.get());
		}
			
		featureBenefitTemplateItemPending.setDescription(null);
		featureBenefitTemplateItemPending.setStatus(CommonStatus.valueOf(featureBenefitTemplateItemUpdateResource.getFeatureBenefitItemUsageResource().getStatus()));
		featureBenefitTemplateItemPending.setSyncTs(validationService.getCreateOrModifyDate());
		featureBenefitTemplateItemPending = featureBenefitTemplateItemPendingRepository.save(featureBenefitTemplateItemPending);
		
		LoggerRequest.getInstance().logInfo("Feature Benifit Template Item******************************** create / update Feature Benifit Template Item pending *********************************************");
		
		return featureBenefitTemplateItemPending;
	}
	
	
	
	public void addUpdatefeatureBenefitTemplateItem(FeatureBenefitTemplateItemPending fbti ,  String user) {
		
		FeatureBenefitTemplateItemPending featureBenefitTemplateItemPending = fbti;
		
		FeatureBenefitTemplateItem featureBenefitTemplateItem = null;
		
		if(featureBenefitTemplateItemPending.getFeatureBenefitTemplateItem() != null) {
			featureBenefitTemplateItem = featureBenefitTemplateItemPending.getFeatureBenefitTemplateItem();
		}else {
			
			featureBenefitTemplateItem = new FeatureBenefitTemplateItem();

			featureBenefitTemplateItem.setTenantId(featureBenefitTemplateItemPending.getTenantId());
			featureBenefitTemplateItem.setFeatureBenefitTemplate(featureBenefitTemplateItemPending.getFeatureBenifitTemplatePending().getFeatureBenifitTemplate());
			featureBenefitTemplateItem.setCreatedDate(validationService.getCreateOrModifyDate());
			featureBenefitTemplateItem.setCreatedUser(featureBenefitTemplateItemPending.getCreatedUser());	
			
			featureBenefitTemplateItem.setFeatureBenefitItem(featureBenefitTemplateItemPending.getFeatureBenefitItem());
		}
		
		featureBenefitTemplateItem.setDescription(featureBenefitTemplateItemPending.getDescription());
		featureBenefitTemplateItem.setStatus(featureBenefitTemplateItemPending.getStatus());
		featureBenefitTemplateItem.setModifiedUser(user);
		featureBenefitTemplateItem.setModifiedDate(validationService.getCreateOrModifyDate());	
		featureBenefitTemplateItem.setSyncTs(validationService.getCreateOrModifyDate());
		featureBenefitTemplateItemRepository.save(featureBenefitTemplateItem);
		
		LoggerRequest.getInstance().logInfo("Feature Benifit Template Item******************************** create / update Feature Benifit Template Item using pending details *********************************************");		
	}
	
	@Override
	public boolean approveReject(String tenantId, Long id, WorkflowStatus workflowStatus , String usr) {

		WorkflowInitiationRequestResource workflowInitiationRequestResource = new WorkflowInitiationRequestResource();
		workflowInitiationRequestResource.setApprovalUser(DEFAULT_APPROVAL_USER);
		workflowInitiationRequestResource.setApprovWorkflowType(WorkflowType.FEATURE_BENEFIT_TEMP_APPROVAL);
		WorkflowRulesResource workflowRulesResource = null;
		WorkflowType workflowType = WorkflowType.FEATURE_BENEFIT_TEMP_APPROVAL;
		String user = usr;
		CommonApproveStatus approveStatus= null;
	
		Optional<FeatureBenifitTemplatePending> featureBenifitTemplatePendingOptional = featureBenifitTemplatePendingRepository.findById(id);
		
		if(!featureBenifitTemplatePendingOptional.isPresent()) {
			throw new ValidateRecordException("feature benifit template not exists", MESSAGE);
		}
		
		if(!featureBenifitTemplatePendingOptional.get().getApproveStatus().equals(CommonApproveStatus.PENDING))
			throw new ValidateRecordException(environment.getProperty("common.can-not-rej-app"), MESSAGE);
		
		FeatureBenifitTemplatePending featureBenifitTemplatePending = featureBenifitTemplatePendingOptional.get();
		
		Optional<LendingWorkflow> lendingWorkflow = lendingWorkflowRepository.findById(featureBenifitTemplatePendingOptional.get().getLendingWorkflow().getId());
	
		LoggerRequest.getInstance().logInfo(" Feature Benifit Template Item********************************Get Workflow Rules*********************************************");
		workflowRulesResource = validationService.invokedLendingProductRule(workflowType, DOMAIN_PATH, DOMAIN, tenantId);
	
		if(!lendingWorkflow.isPresent()) {
			throw new ValidateRecordException("lending work flow not exists", "message");
		}
		
		if(workflowRulesResource.getApprovalAllowed().equalsIgnoreCase(CommonStatus.NO.toString())
			&& (lendingWorkflow.get().getCreatedUser().equalsIgnoreCase(LogginAuthentcation.getInstance().getUserName()))) {
				throw new ValidateRecordException(environment.getProperty("cannot.process.approve"), MESSAGE);
		}
	
		if(workflowStatus.toString().equalsIgnoreCase(WorkflowStatus.COMPLETE.toString())) {
			LoggerRequest.getInstance().logInfo(" Feature Benifit Template Item********************************Approve Workflow*********************************************");
			validationService.approveWorkFlow(lendingWorkflow.get().getWorkflowProcessId(), workflowType, user, tenantId);
			approveStatus = CommonApproveStatus.APPROVED;
			updateFeatureBenifitTemplate(featureBenifitTemplatePending , user);
			featureBenifitTemplatePending.setApproveStatus(approveStatus);
			featureBenifitTemplatePending.setPenApprovedUser(user);
			featureBenifitTemplatePending.setPenApprovedDate(validationService.getCreateOrModifyDate());
			
			
		}
		else {
			LoggerRequest.getInstance().logInfo(" Feature Benifit Template Item********************************Abort Workflow*********************************************");
			validationService.abortedWorkFlow(lendingWorkflow.get().getWorkflowProcessId(), workflowType, user, tenantId);
			approveStatus = CommonApproveStatus.REJECTED;
			featureBenifitTemplatePending.setApproveStatus(approveStatus);
			featureBenifitTemplatePending.setPenRejectedUser(user);
			featureBenifitTemplatePending.setPenRejectedDate(validationService.getCreateOrModifyDate());
		}
		
		lendingWorkflowService.update(lendingWorkflow.get(), workflowStatus, user);
		
		featureBenifitTemplatePendingRepository.save(featureBenifitTemplatePending);
		LoggerRequest.getInstance().logInfo(" Feature Benifit Template Item********************************update*********************************************");
		
		return true;
	}
	
	
	public void updateFeatureBenifitTemplate(FeatureBenifitTemplatePending fbtp , String user) {
		
		LoggerRequest.getInstance().logInfo(" Feature Benifit Template Item********************************update using pending root service *********************************************");
		
		
		FeatureBenifitTemplatePending featureBenifitTemplatePending = fbtp;
		System.out.println("Approved History********************************");
		Optional<FeatureBenifitTemplate> featureBenifitTemplateOld = featureBenifitTemplateRepository.findById(fbtp.getFeatureBenifitTemplate().getId());
		if(featureBenifitTemplateOld.isPresent()) {
			saveHistory(featureBenifitTemplateOld.get());	
		}
		FeatureBenifitTemplate featureBenifitTemplate = featureBenifitTemplatePending.getFeatureBenifitTemplate();
		featureBenifitTemplate.setName(featureBenifitTemplatePending.getName());
		featureBenifitTemplate.setStatus(featureBenifitTemplatePending.getStatus());
		featureBenifitTemplate.setModifiedUser(featureBenifitTemplatePending.getCreatedUser());
		featureBenifitTemplate.setModifiedDate(validationService.getCreateOrModifyDate());
		featureBenifitTemplate.setSyncTs(validationService.getCreateOrModifyDate());		
		featureBenifitTemplateRepository.save(featureBenifitTemplate);
		
		
		List<FeatureBenefitTemplateItemPending> featureBenefitTemplateItemPendingList = featureBenefitTemplateItemPendingRepository.findAllByFeatureBenifitTemplatePending(featureBenifitTemplatePending);
		
		if( (featureBenefitTemplateItemPendingList != null) && (!featureBenefitTemplateItemPendingList.isEmpty())) {
			for(FeatureBenefitTemplateItemPending item :featureBenefitTemplateItemPendingList) {
				addUpdatefeatureBenefitTemplateItem(item , user);				
			}
		}
		
		List<FeatureBenefitTemplateItemEligibilityPending> featureBenefitTemplateItemEligibilityPendingList = featureBenefitTemplateItemEligibilityPendingRepository.findAllByFeatureBenefitTemplatePending(featureBenifitTemplatePending);
	
		if( (featureBenefitTemplateItemEligibilityPendingList != null) && (!featureBenefitTemplateItemEligibilityPendingList.isEmpty())) {
			for(FeatureBenefitTemplateItemEligibilityPending item :featureBenefitTemplateItemEligibilityPendingList) {
				featureBenefitTemplateItemEligibilityService.updateFeatureBenifitTemplateItemEligibility(item ,  user);				
			}
		}
		
		
	}
	
	public void saveHistory(FeatureBenifitTemplate featureBenifitTemplate) {
		
        FeatureBenifitTemplateHistory featureBenifitTemplateHistory = new FeatureBenifitTemplateHistory();
        featureBenifitTemplateHistory.setTenantId(featureBenifitTemplate.getTenantId());
        featureBenifitTemplateHistory.setFeatureBenifitTemplateId(featureBenifitTemplate.getId());
        featureBenifitTemplateHistory.setCode(featureBenifitTemplate.getCode());
        featureBenifitTemplateHistory.setName(featureBenifitTemplate.getName());
        featureBenifitTemplateHistory.setStatus(featureBenifitTemplate.getStatus());
        featureBenifitTemplateHistory.setHistoryCreatedDate(validationService.getCreateOrModifyDate());
        featureBenifitTemplateHistory.setHistoryCreatedUser(LogginAuthentcation.getInstance().getUserName());
        featureBenifitTemplateHistory.setCreatedUser(featureBenifitTemplate.getCreatedUser());
        featureBenifitTemplateHistory.setCreatedDate(featureBenifitTemplate.getCreatedDate());
        featureBenifitTemplateHistory.setSyncTs(validationService.getCreateOrModifyDate());      
        featureBenifitTemplateHistoryRepository.save(featureBenifitTemplateHistory);		
		
	}
	
	@Override
	public FeatureBenifitTemplatePendingDetailsResponse findDetailsByFeatureBenifitTemplatePendingId(Long id) {
		
		LoggerRequest.getInstance().logInfo(" Feature Benifit Template Item******************************** get pending details*********************************************");
				
		Optional<FeatureBenifitTemplatePending> featureBenefitTemplatePendingOptional = featureBenefitTemplatePendingRepository.findById(id);
		if(!featureBenefitTemplatePendingOptional.isPresent()) {
			throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), FEATURE_BENIFIT_TEMPLATE_PENDING_ID);		
		}
		
		FeatureBenifitTemplatePending featureBenifitTemplatePending = featureBenefitTemplatePendingOptional.get();
		

		
		List<FeatureBenefitTemplateItemPending> featureBenefitTemplateItemPendingList = featureBenefitTemplateItemPendingRepository.findAllByFeatureBenifitTemplatePending(featureBenifitTemplatePending);
		List<FeatureBenefitTemplateItemPendingDetailsResponse> itemPendingList = new ArrayList<>();
		
		
		List<Long> idItemPendingList = new ArrayList<>();
	    if((featureBenefitTemplateItemPendingList != null) && (!featureBenefitTemplateItemPendingList.isEmpty())) {
	    	for(FeatureBenefitTemplateItemPending item : featureBenefitTemplateItemPendingList) {
	    		FeatureBenefitTemplateItemPendingDetailsResponse subResponse = new FeatureBenefitTemplateItemPendingDetailsResponse();
	    		subResponse.setFeatureBenefitTemplateItemPending(item);
	    		subResponse.setFeatureBenefitTemplateEligibilityPendingList(null);
	    		
	    		if(item.getFeatureBenefitTemplateItem() != null) {
	    			idItemPendingList.add(item.getFeatureBenefitTemplateItem().getId());
	    			List<FeatureBenefitTemplateItemEligibilityPending> eligibilityPendingList = featureBenefitTemplateItemEligibilityPendingRepository.findAllByFeatureBenefitTemplatePendingAndFeatureBenefitTemplateItem(featureBenifitTemplatePending, item.getFeatureBenefitTemplateItem());	    			
	    			subResponse.setFeatureBenefitTemplateEligibilityPendingList(eligibilityPendingList);
	    		}
	    		    	
	    		itemPendingList.add(subResponse);
	    	}
	    }
	    
	    List<Long> idItemNotInPendingList = new ArrayList<>();	    
	    List<FeatureBenefitTemplateItemEligibilityPending> eligibiiltyPendingList = featureBenefitTemplateItemEligibilityPendingRepository.findAllByFeatureBenefitTemplatePending(featureBenifitTemplatePending);
	    
	    for(FeatureBenefitTemplateItemEligibilityPending eligibilityPending:eligibiiltyPendingList){			
	    	if(!idItemPendingList.contains(eligibilityPending.getFeatureBenefitTemplateItem().getId())
	    			&&(!idItemNotInPendingList.contains(eligibilityPending.getFeatureBenefitTemplateItem().getId())) ) {
		    		idItemNotInPendingList.add(eligibilityPending.getFeatureBenefitTemplateItem().getId());			
								
			}
	    }
	    
	    
	    if((!idItemNotInPendingList.isEmpty())) {
	    	for(Long itemId : idItemNotInPendingList) {
	    		FeatureBenefitTemplateItemPendingDetailsResponse subResponse = new FeatureBenefitTemplateItemPendingDetailsResponse();
	    		subResponse.setFeatureBenefitTemplateItemPending(null);
	    		
	    		
	    		
	    		Optional<FeatureBenefitTemplateItem> featureBenefitTemplateItemOptional = featureBenefitTemplateItemRepository.findById(itemId);
	    		if(!featureBenefitTemplateItemOptional.isPresent()) {
	    			throw new ValidateRecordException("feature benifit template item not found", MESSAGE);
	    		}
	    		List<FeatureBenefitTemplateItemEligibilityPending> eligibilityPendingList = featureBenefitTemplateItemEligibilityPendingRepository.findAllByFeatureBenefitTemplatePendingAndFeatureBenefitTemplateItem(featureBenifitTemplatePending,featureBenefitTemplateItemOptional.get());	
	    		subResponse.setFeatureBenefitTemplateEligibilityPendingList(eligibilityPendingList);
	    		
	    		itemPendingList.add(subResponse);
	    	}
	    }
	    
	    
		FeatureBenifitTemplatePendingDetailsResponse finalResponse = new FeatureBenifitTemplatePendingDetailsResponse();
		finalResponse.setFeatureBenifitTemplatePending(featureBenifitTemplatePending);
		finalResponse.setFeatureBenefitTemplateItemPendingDetailsList(itemPendingList);
		
		return finalResponse;
	
	}
	
	
	public List<Long> getFeatureBenefitItemIdListRelatedToFeatureBenifitTemplate(FeatureBenifitTemplate featureBenifitTemplate) {
		
		List<FeatureBenefitTemplateItem> findAllByFeatureBenefitTemplateList = featureBenefitTemplateItemRepository.findAllByFeatureBenefitTemplate(featureBenifitTemplate);				
		List<FeatureBenefitItem> addedFeatureBenifitItemList = findAllByFeatureBenefitTemplateList.stream().map(FeatureBenefitTemplateItem::getFeatureBenefitItem).collect(Collectors.toList());
		List<Long> addedFeatureBenifitItemIdList = addedFeatureBenifitItemList.stream().map(FeatureBenefitItem::getId).collect(Collectors.toList());
		return addedFeatureBenifitItemIdList;
		
	}
	
	public List<Long> getFeatureBenefitItemIdListRelatedToFeatureBenifitTemplatePending(FeatureBenifitTemplatePending featureBenifitTemplatePending) {
		
		List<FeatureBenefitTemplateItemPending> findAllByFeatureBenifitTemplatePendingList = featureBenefitTemplateItemPendingRepository.findAllByFeatureBenifitTemplatePending(featureBenifitTemplatePending);
		List<FeatureBenefitItem> featureBenefitItemPendingList = findAllByFeatureBenifitTemplatePendingList.stream().map(FeatureBenefitTemplateItemPending::getFeatureBenefitItem).collect(Collectors.toList());
		List<Long> addedFeatureBenifitItemIdPendingList = featureBenefitItemPendingList.stream().map(FeatureBenefitItem::getId).collect(Collectors.toList());
		return addedFeatureBenifitItemIdPendingList;
		
	}
	

}
