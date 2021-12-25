package com.fusionx.lending.product.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.MasterDefAccountRule;
import com.fusionx.lending.product.domain.MasterDefAccountRuleHistory;
import com.fusionx.lending.product.domain.MasterDefAccountRulePending;
import com.fusionx.lending.product.domain.MasterDefAccountRuleSetStandard;
import com.fusionx.lending.product.domain.MasterDefAccountRuleSetStandardHistory;
import com.fusionx.lending.product.domain.MasterDefAccountRuleSetStandardPending;
import com.fusionx.lending.product.domain.MasterDefinition;
import com.fusionx.lending.product.domain.MasterDefinitionPending;
import com.fusionx.lending.product.domain.CommonListItem;
import com.fusionx.lending.product.domain.LendingWorkflow;
import com.fusionx.lending.product.enums.CommonApproveStatus;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.enums.EntityPoint;
import com.fusionx.lending.product.enums.MasterDefAccountRuleSetStandardEnum;
import com.fusionx.lending.product.enums.MasterDefinitionCategory;
import com.fusionx.lending.product.enums.ServiceEntity;
import com.fusionx.lending.product.enums.WorkflowStatus;
import com.fusionx.lending.product.enums.WorkflowType;
import com.fusionx.lending.product.enums.YesNo;
import com.fusionx.lending.product.exception.InvalidServiceIdException;
import com.fusionx.lending.product.exception.ValidateRecordException;
import com.fusionx.lending.product.repository.CommonListItemRepository;
import com.fusionx.lending.product.repository.LendingWorkflowRepository;
import com.fusionx.lending.product.repository.MasterDefAccountRuleRepository;
import com.fusionx.lending.product.repository.MasterDefAccountRuleHistoryRepository;
import com.fusionx.lending.product.repository.MasterDefAccountRulePendingRepository;
import com.fusionx.lending.product.repository.MasterDefAccountRuleSetStandardHistoryRepository;
import com.fusionx.lending.product.repository.MasterDefAccountRuleSetStandardPendingRepository;
import com.fusionx.lending.product.repository.MasterDefAccountRuleSetStandardRepository;
import com.fusionx.lending.product.repository.MasterDefinitionPendingRepository;
import com.fusionx.lending.product.repository.MasterDefinitionRepository;
import com.fusionx.lending.product.resources.CommonListUsageResource;
import com.fusionx.lending.product.resources.MasterDefAccRuleCommonListUpdateResource;
import com.fusionx.lending.product.resources.MasterDefAccountRuleAddResource;
import com.fusionx.lending.product.resources.MasterDefAccountRuleDetailsResponse;
import com.fusionx.lending.product.resources.MasterDefAccountRulePendingDetailsResponse;
import com.fusionx.lending.product.resources.MasterDefAccountRuleSetStandardDetResponse;
import com.fusionx.lending.product.resources.MasterDefAccountRuleSetStandardPendingDetResponse;
import com.fusionx.lending.product.resources.MasterDefinitionAccountRuleUpdateResource;
import com.fusionx.lending.product.resources.WorkflowInitiationRequestResource;
import com.fusionx.lending.product.resources.WorkflowRulesResource;
import com.fusionx.lending.product.service.LendingWorkflowService;
import com.fusionx.lending.product.service.MasterDefAccountRuleService;
import com.fusionx.lending.product.service.MasterDefinitionHistoryService;
import com.fusionx.lending.product.service.ValidationService;
/**
Master Def Account Rule Service Impl
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   13-07-2019                            NipunDilhan        Created
 *    
 ********************************************************************************************************
 */

@Component
@Transactional(rollbackFor = Exception.class)
public class MasterDefAccountRuleServiceImpl  extends MessagePropertyBase  implements MasterDefAccountRuleService {
	
    protected static final String MASTER_DEFINITION = "masterDefinitionId";
    protected static final String MASTER_DEFINITION_PENDING = "masterDefinitionPendingId";
    
	@Autowired 
	private ValidationService validationService;
	@Autowired
	private LendingWorkflowService lendingWorkflowService;	
	@Autowired
	private MasterDefinitionHistoryService masterDefinitionHistoryService;
	
	@Autowired 
	private MasterDefAccountRuleRepository  masterDefAccountRuleRepository;	
	@Autowired 
	private MasterDefinitionRepository masterDefinitionRepository;	
	@Autowired 
	private MasterDefAccountRuleSetStandardRepository masterDefAccountRuleSetStandardRepository;	
	@Autowired 
	private CommonListItemRepository commonListItemRepository;	
	@Autowired 
	private MasterDefAccountRuleHistoryRepository masterDefAccountRuleHistoryRepository;	
	@Autowired 
	private MasterDefAccountRuleSetStandardHistoryRepository masterDefAccountRuleSetStandardHistoryRepository;	
	@Autowired 
	private MasterDefAccountRulePendingRepository masterDefAccountRulePendingRepository;	
	@Autowired 
	private MasterDefAccountRuleSetStandardPendingRepository masterDefAccountRuleSetStandardPendingRepository;	
	@Autowired 
	private MasterDefinitionPendingRepository masterDefinitionPendingRepository;	
	@Autowired
	private LendingWorkflowRepository lendingWorkflowRepository;


	private static final String DEFAULT_APPROVAL_USER = "kie-server";
	private static final String DOMAIN_PATH = "com.fusionx.LendingWF";
	private static final String DOMAIN = "LendingWF";
	
	public MasterDefAccountRule create(String tenantId,MasterDefAccountRuleAddResource masterDefAccountRuleAddResource) {
		
		
		Optional<MasterDefinition> MasterDefinitionOptional = masterDefinitionRepository.findById(validationService.stringToLong(masterDefAccountRuleAddResource.getMasterDefinitionId()));
		if(!MasterDefinitionOptional.isPresent())		
			throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND),MASTER_DEFINITION);
		
		MasterDefAccountRule masterDefAccountRule = new MasterDefAccountRule();
		masterDefAccountRule.setTenantId(tenantId);
		masterDefAccountRule.setMasterDefinitionId(MasterDefinitionOptional.get());
		masterDefAccountRule.setAccountWise(masterDefAccountRuleAddResource.getGlEntryPosting().getAccountWise()!=null
				?YesNo.valueOf(masterDefAccountRuleAddResource.getGlEntryPosting().getAccountWise()):YesNo.NO);
		masterDefAccountRule.setCustomerWise(masterDefAccountRuleAddResource.getGlEntryPosting().getCustomerWise()!=null
				?YesNo.valueOf(masterDefAccountRuleAddResource.getGlEntryPosting().getCustomerWise()):YesNo.NO);
		masterDefAccountRule.setRealTime(masterDefAccountRuleAddResource.getReconciliations().getRealTime()!=null
				?YesNo.valueOf(masterDefAccountRuleAddResource.getReconciliations().getRealTime()):YesNo.NO);
		masterDefAccountRule.setOnDemand(masterDefAccountRuleAddResource.getReconciliations().getOnDemand()!=null
				?YesNo.valueOf(masterDefAccountRuleAddResource.getReconciliations().getOnDemand()):YesNo.NO);
		masterDefAccountRule.setEndOfDay(masterDefAccountRuleAddResource.getReconciliations().getEndOfDay()!=null
				?YesNo.valueOf(masterDefAccountRuleAddResource.getReconciliations().getEndOfDay()):YesNo.NO);
		masterDefAccountRule.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
		masterDefAccountRule.setCreatedDate(validationService.getCreateOrModifyDate());
		masterDefAccountRule.setModifiedUser(LogginAuthentcation.getInstance().getUserName());		
		masterDefAccountRule.setModifiedDate(validationService.getCreateOrModifyDate());
		masterDefAccountRule.setSyncTs(validationService.getCreateOrModifyDate());
		
		masterDefAccountRule = masterDefAccountRuleRepository.save(masterDefAccountRule);
		
		List<MasterDefAccountRuleSetStandard> listMasterDefAccountsRuleList = new ArrayList<>();

		
		List<String> setStandardsList = Stream.of(MasterDefAccountRuleSetStandardEnum.values())
                .map(MasterDefAccountRuleSetStandardEnum::name)
                .collect(Collectors.toList());
		
		List<Long> commonListItemIdList =  new ArrayList<>();
		
		if((masterDefAccountRuleAddResource.getCommonListUsageList()!= null) && (!masterDefAccountRuleAddResource.getCommonListUsageList().isEmpty())) {
			for (CommonListUsageResource item : masterDefAccountRuleAddResource.getCommonListUsageList()) {

				Optional<CommonListItem> CommonListItemOptional = commonListItemRepository.findById(validationService.stringToLong(item.getId()));

				if (!CommonListItemOptional.isPresent())
					throw new InvalidServiceIdException(environment.getProperty("common.record-not-found"),
							ServiceEntity.CODE, EntityPoint.MASTER_DEFINITION);//change 
					
				if( (commonListItemIdList != null) && (!commonListItemIdList.isEmpty())  &&
					(commonListItemIdList.contains(CommonListItemOptional.get().getId()))) {
						//item newly going to add is already exists in the list
						throw new InvalidServiceIdException(environment.getProperty("common.duplicate"),
								ServiceEntity.CODE, EntityPoint.MASTER_DEFINITION);//change 
					
				}
				
				if(setStandardsList.contains(CommonListItemOptional.get().getReferenceCode())){
					
					MasterDefAccountRuleSetStandard masterDefAccountRuleSetStandard = new MasterDefAccountRuleSetStandard();
					masterDefAccountRuleSetStandard.setTenantId(tenantId);
					masterDefAccountRuleSetStandard.setMasterDefinitionId(MasterDefinitionOptional.get());
					masterDefAccountRuleSetStandard.setMasterDefAccountRule(masterDefAccountRule);
					masterDefAccountRuleSetStandard
							.setTypeValue(MasterDefAccountRuleSetStandardEnum.valueOf(CommonListItemOptional.get().getReferenceCode()));
					masterDefAccountRuleSetStandard.setCommonListItem(CommonListItemOptional.get());
					masterDefAccountRuleSetStandard.setCommonListItemName(CommonListItemOptional.get().getName());
					masterDefAccountRuleSetStandard
							.setIsSelected(item.getIsSelected() != null ? YesNo.valueOf(item.getIsSelected()) : YesNo.NO);
					masterDefAccountRuleSetStandard.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
					masterDefAccountRuleSetStandard.setCreatedDate(validationService.getCreateOrModifyDate());
					masterDefAccountRuleSetStandard.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
					masterDefAccountRuleSetStandard.setModifiedDate(validationService.getCreateOrModifyDate());
					masterDefAccountRuleSetStandard.setSyncTs(validationService.getCreateOrModifyDate());

					commonListItemIdList.add(CommonListItemOptional.get().getId());
					
					listMasterDefAccountsRuleList.add(masterDefAccountRuleSetStandard);

				}else {
					throw new ValidateRecordException(environment.getProperty("masterDefAccountRule-common-list-item.invalid"), "commonListItem");
				}
			}
			masterDefAccountRuleSetStandardRepository.saveAll(listMasterDefAccountsRuleList);
		}
		
		return masterDefAccountRule;
	}
	
	
	
	@Override
	public MasterDefinition updateMasterDefinitionAccountRule(String tenantId, Long id,MasterDefinitionAccountRuleUpdateResource masterDefinitionAccountRuleUpdateResource) {
	
		Optional<MasterDefinition> isPresentMasterDefinition = masterDefinitionRepository.findById(id);
		if(!isPresentMasterDefinition.isPresent())		
			throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND),MASTER_DEFINITION);
		
		MasterDefinition masterDefinition =isPresentMasterDefinition.get();
		
		Optional<MasterDefAccountRule> masterDefAccountRuleOptional = masterDefAccountRuleRepository.findById(validationService.stringToLong(masterDefinitionAccountRuleUpdateResource.getMasterDefAccountRuleId()));
		
		if(!masterDefAccountRuleOptional.isPresent()) {
			throw new InvalidServiceIdException(environment.getProperty("common.record-not-found"),
					ServiceEntity.CODE, EntityPoint.MASTER_DEFINITION);//change 
		}else {
			if(masterDefAccountRuleOptional.get().getMasterDefinition().getId()!=isPresentMasterDefinition.get().getId()) {
				//throw new exception master definition incompatible issue
			}
		}

		approveOrGenerateWorkFlow(tenantId, LogginAuthentcation.getInstance().getUserName(),
				masterDefinition, masterDefinitionAccountRuleUpdateResource);

		return masterDefinition;
	}

	private void approveOrGenerateWorkFlow(String tenantId, String user, MasterDefinition masterDefinition,
			MasterDefinitionAccountRuleUpdateResource masterDefinitionAccountRuleUpdateResource) {
		
		if(masterDefinitionAccountRuleUpdateResource.getMasterDefinitionPendingId() != null) {
			
			Optional<MasterDefinitionPending> masterDefinitionPendingOptional = masterDefinitionPendingRepository.findById(validationService.stringToLong(masterDefinitionAccountRuleUpdateResource.getMasterDefinitionPendingId()));
			MasterDefAccountRulePending masterDefAccountRulePending = this.savePendingMasterDefAccountRulePending(tenantId , masterDefinitionAccountRuleUpdateResource , masterDefinitionPendingOptional.get());
			
		} else {
			
			WorkflowRulesResource workflowRulesResource = null;
			Long processId = null;
			WorkflowType workflowType = WorkflowType.MASTER_DEFINITION_TEMP_APPROVAL;
			LendingWorkflow lendingWorkflow = null;

			WorkflowInitiationRequestResource workflowInitiationRequestResource = new WorkflowInitiationRequestResource();
			workflowInitiationRequestResource.setApprovalUser(DEFAULT_APPROVAL_USER);
			workflowInitiationRequestResource.setApprovWorkflowType(workflowType);

			workflowRulesResource = validationService.invokedLendingProductRule(workflowType, DOMAIN_PATH, DOMAIN,tenantId);
			
			if (workflowRulesResource.getApprovalRequired().equalsIgnoreCase(CommonStatus.YES.toString())) {
				processId = validationService.initiateLendingProductWorkFlow(workflowInitiationRequestResource, tenantId);
				if (processId != null) {
					lendingWorkflow = lendingWorkflowService.save(tenantId, processId, workflowType, user);
					MasterDefinitionPending masterDefinitionPending =this.saveMasterDefinitionPending(tenantId, masterDefinition, masterDefinitionAccountRuleUpdateResource, lendingWorkflow,user);
					MasterDefAccountRulePending masterDefAccountRulePending =this.savePendingMasterDefAccountRulePending(tenantId , masterDefinitionAccountRuleUpdateResource , masterDefinitionPending);
				}
			} else {
				MasterDefinitionPending masterDefinitionPending = this.saveMasterDefinitionPending(tenantId, masterDefinition,masterDefinitionAccountRuleUpdateResource, lendingWorkflow, user);
				MasterDefAccountRulePending masterDefAccountRulePending = this.savePendingMasterDefAccountRulePending(tenantId , masterDefinitionAccountRuleUpdateResource , masterDefinitionPending);
				
				updateMasterDefinition(masterDefinitionPending, CommonApproveStatus.APPROVED, user);
				Optional<MasterDefAccountRule> updatedMasterDefAccoutRule = updateMasterDefAccoutRule( masterDefinitionPending.getId());
				
				if(updatedMasterDefAccoutRule.isPresent()) {
					List<MasterDefAccountRuleSetStandard> masterDefAccountRuleSetStandardList = masterDefAccountRuleSetStandardRepository.findByMasterDefAccountRule(updatedMasterDefAccoutRule.get());
					this.saveHistory(tenantId, updatedMasterDefAccoutRule.get() , masterDefAccountRuleSetStandardList );
				}
			}
			
		}
	}
	
	
	private MasterDefinitionPending saveMasterDefinitionPending(String tenantId, MasterDefinition masterDefinition,
			MasterDefinitionAccountRuleUpdateResource masterDefinitionAccountRuleUpdateResource, LendingWorkflow lendingWorkflow,
			String user) {

		MasterDefinition md = masterDefinition;
		md.setApproveStatus(CommonApproveStatus.PENDING);
		md.setModifiedUser(user);
		md.setModifiedDate(validationService.getCreateOrModifyDate());
		md.setSyncTs(validationService.getCreateOrModifyDate());
		masterDefinitionRepository.saveAndFlush(md);

		MasterDefinitionPending masterDefinitionPending = new MasterDefinitionPending();

		masterDefinitionPending.setTenantId(tenantId);
		if (lendingWorkflow != null)
		masterDefinitionPending.setLendingWorkflow(lendingWorkflow);
		
		masterDefinitionPending.setMasterDefinition(masterDefinition);
		masterDefinitionPending.setMasterDefinitionCategory(MasterDefinitionCategory.ACCOUNTING);
		masterDefinitionPending.setBusinessPrinciple(md.getBusinessPrinciple());
		masterDefinitionPending.setModule(md.getModule());
		masterDefinitionPending.setSubModuleCode(md.getSubModuleCode());
		masterDefinitionPending.setSubModuleName(md.getSubModuleName());
		masterDefinitionPending.setCode(md.getCode());
		masterDefinitionPending.setName(md.getName());
		masterDefinitionPending.setStatus(md.getStatus());
		masterDefinitionPending.setVersion(md.getVersion());
		masterDefinitionPending.setPenCreatedDate(validationService.getCreateOrModifyDate());
		masterDefinitionPending.setPenCreatedUser(user);
		masterDefinitionPending.setSyncTs(validationService.getCreateOrModifyDate());

		masterDefinitionPending = masterDefinitionPendingRepository.save(masterDefinitionPending);
			
		return masterDefinitionPending;

	}
	
	
	public MasterDefAccountRulePending savePendingMasterDefAccountRulePending(String tenantId,MasterDefinitionAccountRuleUpdateResource masterDefinitionAccountRuleUpdateResource, MasterDefinitionPending masterDefinitionPending) {
		
		Optional<MasterDefAccountRule> masterDefAccountRuleOptional = masterDefAccountRuleRepository.findById(validationService.stringToLong(masterDefinitionAccountRuleUpdateResource.getMasterDefAccountRuleId()));
		
		if(!masterDefAccountRuleOptional.isPresent()) 
			throw new InvalidServiceIdException(environment.getProperty("common.record-not-found"),
				ServiceEntity.CODE, EntityPoint.MASTER_DEFINITION);//change 
		
		MasterDefAccountRulePending masterDefAccountRulePending = new MasterDefAccountRulePending();
		masterDefAccountRulePending.setTenantId(tenantId);
		masterDefAccountRulePending.setMasterDefAccountRuleId(masterDefAccountRuleOptional.get());
		masterDefAccountRulePending.setMasterDefinitionPending(masterDefinitionPending);
		masterDefAccountRulePending.setAccountWise(masterDefinitionAccountRuleUpdateResource.getGlEntryPosting().getAccountWise()!=null
				?YesNo.valueOf(masterDefinitionAccountRuleUpdateResource.getGlEntryPosting().getAccountWise()):YesNo.NO);
		masterDefAccountRulePending.setCustomerWise(masterDefinitionAccountRuleUpdateResource.getGlEntryPosting().getCustomerWise()!=null
				?YesNo.valueOf(masterDefinitionAccountRuleUpdateResource.getGlEntryPosting().getCustomerWise()):YesNo.NO);
		masterDefAccountRulePending.setRealTime(masterDefinitionAccountRuleUpdateResource.getReconciliations().getRealTime()!=null
				?YesNo.valueOf(masterDefinitionAccountRuleUpdateResource.getReconciliations().getRealTime()):YesNo.NO);
		masterDefAccountRulePending.setOnDemand(masterDefinitionAccountRuleUpdateResource.getReconciliations().getOnDemand()!=null
				?YesNo.valueOf(masterDefinitionAccountRuleUpdateResource.getReconciliations().getOnDemand()):YesNo.NO);
		masterDefAccountRulePending.setEndOfDay(masterDefinitionAccountRuleUpdateResource.getReconciliations().getEndOfDay()!=null
				?YesNo.valueOf(masterDefinitionAccountRuleUpdateResource.getReconciliations().getEndOfDay()):YesNo.NO);
		masterDefAccountRulePending.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
		masterDefAccountRulePending.setCreatedDate(validationService.getCreateOrModifyDate());
		masterDefAccountRulePending.setSyncTs(validationService.getCreateOrModifyDate());
		masterDefAccountRulePending = masterDefAccountRulePendingRepository.save(masterDefAccountRulePending);
		
		List<MasterDefAccountRuleSetStandardPending> listMasterDefAccountRuleSetStandardPendingList = new ArrayList<>();

		List<String> setStandardsList = Stream.of(MasterDefAccountRuleSetStandardEnum.values())
                .map(MasterDefAccountRuleSetStandardEnum::name)
                .collect(Collectors.toList());
		
		List<MasterDefAccountRuleSetStandard> masterDefAccountRuleSetStandardExistingList = masterDefAccountRuleSetStandardRepository.findByMasterDefAccountRule(masterDefAccountRuleOptional.get());
		List<CommonListItem> commonListItemList = masterDefAccountRuleSetStandardExistingList.stream().map(MasterDefAccountRuleSetStandard::getCommonListItem).collect(Collectors.toList());
		List<Long> commonListItemIdList = commonListItemList.stream().map(CommonListItem::getId).collect(Collectors.toList());
		
		
		if((masterDefinitionAccountRuleUpdateResource.getCommonListUsageList()!= null) && (!masterDefinitionAccountRuleUpdateResource.getCommonListUsageList().isEmpty())) {
			for(MasterDefAccRuleCommonListUpdateResource item :masterDefinitionAccountRuleUpdateResource.getCommonListUsageList()) {
				
					Optional<CommonListItem> CommonListItemOptional = commonListItemRepository.findById(validationService.stringToLong(item.getId()));
					
					if (!CommonListItemOptional.isPresent())
						throw new InvalidServiceIdException(environment.getProperty("common.record-not-found"),
								ServiceEntity.CODE, EntityPoint.MASTER_DEFINITION);//change 
								
									
					MasterDefAccountRuleSetStandard masterDefAccountRuleSetStandard = null;
					
					if(item.getMasterDefAccountRuleSetStandardId()!= null){
						Optional<MasterDefAccountRuleSetStandard> masterDefAccountRuleSetStandardOptional = masterDefAccountRuleSetStandardRepository.findById(validationService.stringToLong(item.getMasterDefAccountRuleSetStandardId()));
						if(masterDefAccountRuleSetStandardOptional.isPresent()) {
							masterDefAccountRuleSetStandard = masterDefAccountRuleSetStandardOptional.get();
						}else {
							//throw new exception 
						}
					}else {
						if(commonListItemIdList.contains(CommonListItemOptional.get().getId())) {
							//item newly going to add is already saved in the db
							throw new InvalidServiceIdException(environment.getProperty("common.duplicate"),
									ServiceEntity.CODE, EntityPoint.MASTER_DEFINITION);//change 
						}
					}
					

				if (setStandardsList.contains(CommonListItemOptional.get().getReferenceCode())) {
					
					MasterDefAccountRuleSetStandardPending masterDefAccountRuleSetStandardPending = new MasterDefAccountRuleSetStandardPending();
					masterDefAccountRuleSetStandardPending.setTenantId(tenantId);
					masterDefAccountRuleSetStandardPending.setMasterDefinitionPending(masterDefinitionPending);
					masterDefAccountRuleSetStandardPending.setMasterDefAccountRuleSetStandardId(masterDefAccountRuleSetStandard);
					masterDefAccountRuleSetStandardPending.setMasterDefAccountRulePendingId(masterDefAccountRulePending);
					masterDefAccountRuleSetStandardPending.setTypeValue(MasterDefAccountRuleSetStandardEnum.valueOf(item.getReferenceCode()));
					masterDefAccountRuleSetStandardPending.setCommonListItemId(CommonListItemOptional.get());
					masterDefAccountRuleSetStandardPending.setCommonListItemName(CommonListItemOptional.get().getName());
					masterDefAccountRuleSetStandardPending.setIsSelected(item.getIsSelected() != null ? YesNo.valueOf(item.getIsSelected()) : YesNo.NO);
					masterDefAccountRuleSetStandardPending.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
					masterDefAccountRuleSetStandardPending.setCreatedDate(validationService.getCreateOrModifyDate());
					masterDefAccountRuleSetStandardPending.setSyncTs(validationService.getCreateOrModifyDate());

					listMasterDefAccountRuleSetStandardPendingList.add(masterDefAccountRuleSetStandardPending);
				}else {
					throw new ValidateRecordException(environment.getProperty("masterDefAccountRule-common-list-item.invalid"), "commonListItem");
				}
			}
			
			masterDefAccountRuleSetStandardPendingRepository.saveAll(listMasterDefAccountRuleSetStandardPendingList);
		}
		
		return masterDefAccountRulePending;
	}
	

	
	
	private void updateMasterDefinition(MasterDefinitionPending masterDefinitionPending,
			CommonApproveStatus approveStatus, String user) {

		//masterDefinitionHistoryService.saveHistory(masterDefinitionPending.getMasterDefinition().getTenantId(), masterDefinitionPending.getMasterDefinition(), user);

		MasterDefinition masterDefinition = masterDefinitionPending.getMasterDefinition();
		masterDefinition.setModifiedDate(masterDefinitionPending.getPenCreatedDate());
		masterDefinition.setModifiedUser(masterDefinitionPending.getPenCreatedUser());
//		masterDefinition.setApproveStatus(approveStatus);

		if (approveStatus.toString().equalsIgnoreCase(CommonApproveStatus.APPROVED.toString())) {
			masterDefinition.setPenApprovedUser(user);
			masterDefinition.setPenApprovedDate(validationService.getCreateOrModifyDate());
		} else {
			masterDefinition.setPenRejectedUser(user);
			masterDefinition.setPenRejectedDate(validationService.getCreateOrModifyDate());
		}
		masterDefinition.setSyncTs(validationService.getCreateOrModifyDate());

		masterDefinitionRepository.saveAndFlush(masterDefinition);
	}
	
	@Override
	public Optional<MasterDefAccountRule> updateMasterDefAccoutRule( Long masterDefinitionPendingId) {
		
		Optional<MasterDefinitionPending> masterDefinitionPendingOptional = masterDefinitionPendingRepository.findById(masterDefinitionPendingId);
		List<MasterDefAccountRulePending> masterDefinitionAccountRulePendingList = null;
		
		if(masterDefinitionPendingOptional.isPresent()) {			
			masterDefinitionAccountRulePendingList = masterDefAccountRulePendingRepository.findAllByMasterDefinitionPending(masterDefinitionPendingOptional.get());
			
		}else {
			 throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND), MASTER_DEFINITION_PENDING);
		}
		
		MasterDefAccountRule masterDefAccountRule = null;	
		
		if ((masterDefinitionAccountRulePendingList != null) && (masterDefinitionAccountRulePendingList.size() == 1)) {
			
			MasterDefAccountRulePending mdarp =   masterDefinitionAccountRulePendingList.get(0);

			masterDefAccountRule = masterDefinitionAccountRulePendingList.get(0).getMasterDefAccountRuleId();
			masterDefAccountRule.setAccountWise(mdarp.getAccountWise());
			masterDefAccountRule.setCustomerWise(mdarp.getCustomerWise());
			masterDefAccountRule.setRealTime(mdarp.getRealTime());
			masterDefAccountRule.setOnDemand(mdarp.getOnDemand());
			masterDefAccountRule.setEndOfDay(mdarp.getEndOfDay());
			masterDefAccountRule.setModifiedUser(mdarp.getCreatedUser());
			masterDefAccountRule.setModifiedDate(validationService.getCreateOrModifyDate());
			masterDefAccountRule.setSyncTs(validationService.getCreateOrModifyDate());

			masterDefAccountRule = masterDefAccountRuleRepository.save(masterDefAccountRule);

			List<MasterDefAccountRuleSetStandardPending> masterDefAccountRuleSetStandardPendingList = masterDefAccountRuleSetStandardPendingRepository
					.findByMasterDefinitionPending(mdarp.getMasterDefinitionPending());
			List<MasterDefAccountRuleSetStandard> listMasterDefAccountsRuleList = new ArrayList<>();

			if ((masterDefAccountRuleSetStandardPendingList != null)
					&& (!masterDefAccountRuleSetStandardPendingList.isEmpty())) {
				for (MasterDefAccountRuleSetStandardPending item : masterDefAccountRuleSetStandardPendingList) {
					MasterDefAccountRuleSetStandard masterDefAccountRuleSetStandard = null;
					if (item.getMasterDefAccountRuleSetStandardId() != null) {
						masterDefAccountRuleSetStandard = item.getMasterDefAccountRuleSetStandardId();
					} else {
						masterDefAccountRuleSetStandard = new MasterDefAccountRuleSetStandard();
						masterDefAccountRuleSetStandard.setCommonListItem(item.getCommonListItemId());
						masterDefAccountRuleSetStandard.setTenantId(item.getTenantId());
						masterDefAccountRuleSetStandard
								.setMasterDefinitionId(item.getMasterDefinitionPending().getMasterDefinition());
						masterDefAccountRuleSetStandard.setMasterDefAccountRule(masterDefAccountRule);
						masterDefAccountRuleSetStandard.setCommonListItem(item.getCommonListItemId());
						masterDefAccountRuleSetStandard.setCommonListItemName(item.getCommonListItemId().getName());
						masterDefAccountRuleSetStandard.setTypeValue(item.getTypeValue());
						masterDefAccountRuleSetStandard.setCreatedUser(item.getCreatedUser());
						masterDefAccountRuleSetStandard.setCreatedDate(validationService.getCreateOrModifyDate());
					}

					masterDefAccountRuleSetStandard.setIsSelected(item.getIsSelected());
					masterDefAccountRuleSetStandard.setModifiedUser(item.getCreatedUser());
					masterDefAccountRuleSetStandard.setModifiedDate(validationService.getCreateOrModifyDate());
					masterDefAccountRuleSetStandard.setSyncTs(validationService.getCreateOrModifyDate());

					listMasterDefAccountsRuleList.add(masterDefAccountRuleSetStandard);
				}
				masterDefAccountRuleSetStandardRepository.saveAll(listMasterDefAccountsRuleList);

			}
		}
		
		return Optional.ofNullable(masterDefAccountRule);
	
	}

	

	
	
	
	
	
	//@Override
	public boolean approveReject(String tenantId, Long id, WorkflowStatus workflowStatus) {

		WorkflowInitiationRequestResource workflowInitiationRequestResource = new WorkflowInitiationRequestResource();
		workflowInitiationRequestResource.setApprovalUser(DEFAULT_APPROVAL_USER);
		workflowInitiationRequestResource.setApprovWorkflowType(WorkflowType.MASTER_DEFINITION_TEMP_APPROVAL);
		WorkflowRulesResource workflowRulesResource = null;
		WorkflowType workflowType = WorkflowType.MASTER_DEFINITION_TEMP_APPROVAL;
		String user = LogginAuthentcation.getInstance().getUserName();
		CommonApproveStatus approveStatus = null;

		Optional<MasterDefinitionPending> isPresentMasterDefinitionPending = masterDefinitionPendingRepository
				.findById(id);

		Optional<MasterDefinition> masterDefinition = masterDefinitionRepository
				.findById(isPresentMasterDefinitionPending.get().getMasterDefinition().getId());

		if (!isPresentMasterDefinitionPending.get().getMasterDefinition().getApproveStatus()
				.equals(CommonApproveStatus.PENDING))
			throw new ValidateRecordException(environment.getProperty("common.can-not-rej-app"), "message");

		Optional<LendingWorkflow> lendingWorkflow = lendingWorkflowRepository
				.findById(isPresentMasterDefinitionPending.get().getLendingWorkflow().getId());

		workflowRulesResource = validationService.invokedLendingProductRule(workflowType, DOMAIN_PATH, DOMAIN,
				tenantId);

		if (workflowRulesResource.getApprovalAllowed().equalsIgnoreCase(CommonStatus.NO.toString())) {
			if (lendingWorkflow.get().getCreatedUser()
					.equalsIgnoreCase(LogginAuthentcation.getInstance().getUserName()))
				throw new ValidateRecordException(environment.getProperty("cannot.process.approve"), "message");
		}

		if (workflowStatus.toString().equalsIgnoreCase(WorkflowStatus.COMPLETE.toString())) {
			validationService.approveWorkFlow(lendingWorkflow.get().getWorkflowProcessId(), workflowType, user,
					tenantId);
			approveStatus = CommonApproveStatus.APPROVED;
		
			
			updateMasterDefinition(isPresentMasterDefinitionPending.get(),CommonApproveStatus.APPROVED, user);
			
			Optional<MasterDefAccountRulePending> masterDefAccountRulePendingOptional = masterDefAccountRulePendingRepository.findByMasterDefinitionPending(isPresentMasterDefinitionPending.get());	
			updateMasterDefAccoutRule( isPresentMasterDefinitionPending.get().getId());
			
		} else {
			validationService.abortedWorkFlow(lendingWorkflow.get().getWorkflowProcessId(), workflowType, user,
					tenantId);
			approveStatus = CommonApproveStatus.REJECTED;
		}

		lendingWorkflowService.update(lendingWorkflow.get(), workflowStatus, user);

		//updateMasterDefinition(isPresentMasterDefinitionPending.get(), approveStatus, user);

		return true;
	}
	
	public void saveHistory(String tenantId,MasterDefAccountRule masterDefAccountRule ,List<MasterDefAccountRuleSetStandard> listMasterDefAccountsRuleList ) {
	
		MasterDefAccountRuleHistory masterDefAccountRuleHistory = new MasterDefAccountRuleHistory();
		masterDefAccountRuleHistory.setTenantId(tenantId);
		masterDefAccountRuleHistory.setMasterDefinitionId(masterDefAccountRule.getMasterDefinition().getId());
		masterDefAccountRuleHistory.setMasterDefAccountRuleId(masterDefAccountRule.getId());
		masterDefAccountRuleHistory.setAccountWise(masterDefAccountRule.getAccountWise()!=null?YesNo.valueOf(masterDefAccountRule.getAccountWise().toString()):YesNo.NO);
		masterDefAccountRuleHistory.setCustomerWise(masterDefAccountRule.getAccountWise()!=null?YesNo.valueOf(masterDefAccountRule.getCustomerWise().toString()):YesNo.NO);
		masterDefAccountRuleHistory.setRealTime(masterDefAccountRule.getRealTime()!=null?YesNo.valueOf(masterDefAccountRule.getRealTime().toString()):YesNo.NO);
		masterDefAccountRuleHistory.setOnDemand(masterDefAccountRule.getOnDemand()!=null?YesNo.valueOf(masterDefAccountRule.getOnDemand().toString()):YesNo.NO);
		masterDefAccountRuleHistory.setEndOfDay(masterDefAccountRule.getEndOfDay()!=null?YesNo.valueOf(masterDefAccountRule.getEndOfDay().toString()):YesNo.NO);
		masterDefAccountRuleHistory.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
		masterDefAccountRuleHistory.setCreatedDate(validationService.getCreateOrModifyDate());
		masterDefAccountRuleHistory.setSyncTs(validationService.getCreateOrModifyDate());
		
		masterDefAccountRuleHistoryRepository.save(masterDefAccountRuleHistory);
		
		List<MasterDefAccountRuleSetStandardHistory> listMasterDefAccountsRuleHistoryList = new ArrayList<>();

		if((listMasterDefAccountsRuleList!= null) && (!listMasterDefAccountsRuleList.isEmpty())) {
			for(MasterDefAccountRuleSetStandard item :listMasterDefAccountsRuleList) {
				
					
					MasterDefAccountRuleSetStandardHistory masterDefAccountRuleSetStandardHistory= new MasterDefAccountRuleSetStandardHistory();
					masterDefAccountRuleSetStandardHistory.setTenantId(tenantId);
					masterDefAccountRuleSetStandardHistory.setMasterDefAccountRuleSetStandardId(item.getId());
					masterDefAccountRuleSetStandardHistory.setMasterDefinitionId(masterDefAccountRule.getMasterDefinition().getId());
					masterDefAccountRuleSetStandardHistory.setMasterDefAccountRuleId(masterDefAccountRule.getId());	
					masterDefAccountRuleSetStandardHistory.setMasterDefAccountRuleHistoryId(item.getId());
					masterDefAccountRuleSetStandardHistory.setTypeValue(item.getTypeValue());
					masterDefAccountRuleSetStandardHistory.setCommonListItemId(item.getCommonListItem().getId());
					masterDefAccountRuleSetStandardHistory.setCommonListItemName(item.getCommonListItem().getName());
					masterDefAccountRuleSetStandardHistory.setIsSelected(item.getIsSelected());
					masterDefAccountRuleSetStandardHistory.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
					masterDefAccountRuleSetStandardHistory.setCreatedDate(validationService.getCreateOrModifyDate());
					masterDefAccountRuleSetStandardHistory.setSyncTs(validationService.getCreateOrModifyDate());
					
					listMasterDefAccountsRuleHistoryList.add(masterDefAccountRuleSetStandardHistory);
				}
			
				masterDefAccountRuleSetStandardHistoryRepository.saveAll(listMasterDefAccountsRuleHistoryList);				
			}		
	}
	
	
	@Override
	public Page<MasterDefAccountRulePending> searchMasterDefAccountRule(String searchq, String status, String approveStatus, Pageable pageable) {
		if(searchq==null || searchq.isEmpty())
			searchq=null;
		if(status==null || status.isEmpty())
			status=null;
		if(approveStatus==null || approveStatus.isEmpty())
			approveStatus=null;
		return masterDefAccountRulePendingRepository.searchMasterDefAccountRulePending(searchq, status, approveStatus, pageable);
	}
	
	@Override
	public MasterDefAccountRulePendingDetailsResponse getAccountRulePendingDetailsMasterDefinitionPendingId(Long masterDefinitionPendingId) {
		
		Optional<MasterDefinitionPending> isPresentMasterDefinitionPending = masterDefinitionPendingRepository.findById(masterDefinitionPendingId);
		
		if(!isPresentMasterDefinitionPending.isPresent())		
			throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND),MASTER_DEFINITION_PENDING);
		
		Optional<MasterDefAccountRulePending> MasterDefAccountRulePendingOptional = masterDefAccountRulePendingRepository.findByMasterDefinitionPending(isPresentMasterDefinitionPending.get());
		
		if(!MasterDefAccountRulePendingOptional.isPresent())		
			throw new InvalidServiceIdException(environment.getProperty("common.record-not-found"), ServiceEntity.CODE,
				EntityPoint.MASTER_DEFINITION);
		
		MasterDefAccountRulePending masterDefAccountRulePending = MasterDefAccountRulePendingOptional.get();
		List<MasterDefAccountRuleSetStandardPendingDetResponse> masterDefAccountRuleSetStandardPendingDetResponseList =  new ArrayList<>();
		
		List<MasterDefAccountRuleSetStandardPending> masterDefAccountRuleSetStandardPendingList = masterDefAccountRuleSetStandardPendingRepository.findByMasterDefinitionPending(isPresentMasterDefinitionPending.get());
		if((masterDefAccountRuleSetStandardPendingList!= null) && (!masterDefAccountRuleSetStandardPendingList.isEmpty())) {
			for(MasterDefAccountRuleSetStandardPending item : masterDefAccountRuleSetStandardPendingList) {
				
				MasterDefAccountRuleSetStandardPendingDetResponse subResponse1= new MasterDefAccountRuleSetStandardPendingDetResponse();
				subResponse1.setCommonListItemId(item.getCommonListItemId().getId());
				subResponse1.setMasterDefAccountRuleSetStandardPending(item);
				masterDefAccountRuleSetStandardPendingDetResponseList.add(subResponse1);
				
			}
		}
		
		MasterDefAccountRulePendingDetailsResponse response = new MasterDefAccountRulePendingDetailsResponse();
		response.setMasterDefinitionId(isPresentMasterDefinitionPending.get().getMasterDefinition().getId());
		response.setMasterDefinitionPendingId(isPresentMasterDefinitionPending.get().getId());
		response.setMasterDefAccountRuleId(masterDefAccountRulePending.getMasterDefAccountRuleId().getId());
		response.setMasterDefAccountRulePending(masterDefAccountRulePending);
		response.setMasterDefAccountRuleSetStandardPendingList(masterDefAccountRuleSetStandardPendingDetResponseList);	
	
		return response;
	}
	
	
	@Override
	public MasterDefAccountRuleDetailsResponse getAccountRuleDetailsMasterDefinitionId(Long masterDefinitionId) {
		
		Optional<MasterDefinition> isPresentMasterDefinition = masterDefinitionRepository.findById(masterDefinitionId);
		
		if(!isPresentMasterDefinition.isPresent())		
			throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND),MASTER_DEFINITION);
		
		 Optional<MasterDefAccountRule> MasterDefAccountRuleOptional = masterDefAccountRuleRepository.findByMasterDefinition(isPresentMasterDefinition.get());
		 
		 List<MasterDefAccountRuleSetStandardDetResponse> masterDefAccountRuleSetStandardPendingDetResponseList =  new ArrayList<>();
			
		List<MasterDefAccountRuleSetStandard> masterDefAccountRuleSetStandardList = masterDefAccountRuleSetStandardRepository
				.findByMasterDefAccountRule(MasterDefAccountRuleOptional.get());

		if ((masterDefAccountRuleSetStandardList != null) && (!masterDefAccountRuleSetStandardList.isEmpty())) {
			for (MasterDefAccountRuleSetStandard item : masterDefAccountRuleSetStandardList) {

				MasterDefAccountRuleSetStandardDetResponse subResponse1 = new MasterDefAccountRuleSetStandardDetResponse();
				subResponse1.setCommonListItemId(item.getCommonListItem().getId());
				subResponse1.setMasterDefAccountRuleSetStandard(item);
				masterDefAccountRuleSetStandardPendingDetResponseList.add(subResponse1);
			}
		}
		
		
		MasterDefAccountRuleDetailsResponse response = new MasterDefAccountRuleDetailsResponse();
		response.setMasterDefinitionId(isPresentMasterDefinition.get().getId());
		response.setMasterDefAccountRule(MasterDefAccountRuleOptional.get());
		response.setMasterDefAccountRuleSetStandardDetResponseList(masterDefAccountRuleSetStandardPendingDetResponseList);
		return response;
			
			
	}	
	
	
	
	
	
	public MasterDefAccountRule directUpdate(String tenantId,MasterDefinitionAccountRuleUpdateResource masterDefinitionAccountRuleUpdateResource , MasterDefinition masterDefinition) {
		Optional<MasterDefAccountRule> masterDefAccountRuleOptional = masterDefAccountRuleRepository.findById(validationService.stringToLong(masterDefinitionAccountRuleUpdateResource.getMasterDefAccountRuleId()));
		MasterDefAccountRule masterDefAccountRule = null;
		if(masterDefAccountRuleOptional.isPresent()){
			masterDefAccountRule = masterDefAccountRuleOptional.get();
		}else {
			//throw exception
		}
		
		masterDefAccountRule.setTenantId(tenantId);
		masterDefAccountRule.setAccountWise(masterDefinitionAccountRuleUpdateResource.getGlEntryPosting().getAccountWise()!=null
				?YesNo.valueOf(masterDefinitionAccountRuleUpdateResource.getGlEntryPosting().getAccountWise()):YesNo.NO);
		masterDefAccountRule.setCustomerWise(masterDefinitionAccountRuleUpdateResource.getGlEntryPosting().getCustomerWise()!=null
				?YesNo.valueOf(masterDefinitionAccountRuleUpdateResource.getGlEntryPosting().getCustomerWise()):YesNo.NO);
		masterDefAccountRule.setRealTime(masterDefinitionAccountRuleUpdateResource.getReconciliations().getRealTime()!=null
				?YesNo.valueOf(masterDefinitionAccountRuleUpdateResource.getReconciliations().getRealTime()):YesNo.NO);
		masterDefAccountRule.setOnDemand(masterDefinitionAccountRuleUpdateResource.getReconciliations().getOnDemand()!=null
				?YesNo.valueOf(masterDefinitionAccountRuleUpdateResource.getReconciliations().getOnDemand()):YesNo.NO);
		masterDefAccountRule.setEndOfDay(masterDefinitionAccountRuleUpdateResource.getReconciliations().getEndOfDay()!=null
				?YesNo.valueOf(masterDefinitionAccountRuleUpdateResource.getReconciliations().getEndOfDay()):YesNo.NO);
		masterDefAccountRule.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
		masterDefAccountRule.setModifiedDate(validationService.getCreateOrModifyDate());
		masterDefAccountRule.setSyncTs(validationService.getCreateOrModifyDate());
		masterDefAccountRule = masterDefAccountRuleRepository.save(masterDefAccountRule);
		
		List<MasterDefAccountRuleSetStandard> listMasterDefAccountRuleSetStandardList = new ArrayList<>();

		if((masterDefinitionAccountRuleUpdateResource.getCommonListUsageList()!= null) && (!masterDefinitionAccountRuleUpdateResource.getCommonListUsageList().isEmpty())) {
			for(MasterDefAccRuleCommonListUpdateResource item :masterDefinitionAccountRuleUpdateResource.getCommonListUsageList()) {
				
					Optional<CommonListItem> CommonListItemOptional = commonListItemRepository.findById(validationService.stringToLong(item.getId()));
					if(!CommonListItemOptional.isPresent()) {
						//throw new exception
					}
					
					
					MasterDefAccountRuleSetStandard masterDefAccountRuleSetStandard = null;
					
					if(item.getMasterDefAccountRuleSetStandardId()!= null){
						Optional<MasterDefAccountRuleSetStandard> masterDefAccountRuleSetStandardOptional = masterDefAccountRuleSetStandardRepository.findById(validationService.stringToLong(item.getMasterDefAccountRuleSetStandardId()));
						if(masterDefAccountRuleSetStandardOptional.isPresent()) {
							masterDefAccountRuleSetStandard = masterDefAccountRuleSetStandardOptional.get();
						}else {
							
						}
					}else {
						masterDefAccountRuleSetStandard =  new MasterDefAccountRuleSetStandard();
						masterDefAccountRuleSetStandard.setTenantId(tenantId);
						masterDefAccountRuleSetStandard.setMasterDefAccountRule(masterDefAccountRule);
						masterDefAccountRuleSetStandard.setMasterDefinitionId(masterDefinition);
						masterDefAccountRuleSetStandard.setCommonListItem(CommonListItemOptional.get());
						masterDefAccountRuleSetStandard.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
						masterDefAccountRuleSetStandard.setCreatedDate(validationService.getCreateOrModifyDate());
					}
					

					
					

					masterDefAccountRuleSetStandard.setTypeValue(MasterDefAccountRuleSetStandardEnum.valueOf(item.getReferenceCode()));
					masterDefAccountRuleSetStandard.setCommonListItemName(CommonListItemOptional.get().getName());
					masterDefAccountRuleSetStandard.setIsSelected(item.getIsSelected()!=null?YesNo.valueOf(item.getIsSelected()):YesNo.NO);
					masterDefAccountRuleSetStandard.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
					masterDefAccountRuleSetStandard.setModifiedDate(validationService.getCreateOrModifyDate());
					masterDefAccountRuleSetStandard.setSyncTs(validationService.getCreateOrModifyDate());
					
					listMasterDefAccountRuleSetStandardList.add(masterDefAccountRuleSetStandard);
				}
			
					masterDefAccountRuleSetStandardRepository.saveAll(listMasterDefAccountRuleSetStandardList);
				

		}
		return masterDefAccountRule;
	}

	
	
	@Override
	public void temporyApprove(Long masterDefinitionPendingId) {
		
		Optional<MasterDefinitionPending> masterDefinitionPendingOptional = masterDefinitionPendingRepository.findById(masterDefinitionPendingId);
		MasterDefinitionPending masterDefinitionPending = masterDefinitionPendingOptional.get();
		
		updateMasterDefinition(masterDefinitionPending,CommonApproveStatus.APPROVED, "sanjeewa");
		
		Optional<MasterDefAccountRulePending> masterDefAccountRulePendingOptional = masterDefAccountRulePendingRepository.findByMasterDefinitionPending(masterDefinitionPending);
	
	
		updateMasterDefAccoutRule( masterDefinitionPending.getId());
	}

}
