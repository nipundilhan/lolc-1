package com.fusionx.lending.product.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.LendingWorkflow;
import com.fusionx.lending.product.domain.RiskTemplateResponse;
import com.fusionx.lending.product.domain.SubProduct;
import com.fusionx.lending.product.domain.SubProductPending;
import com.fusionx.lending.product.enums.CommonApproveStatus;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.enums.WorkflowType;
import com.fusionx.lending.product.exception.ValidateRecordException;
import com.fusionx.lending.product.repository.SubProductPendingRepository;
import com.fusionx.lending.product.repository.SubProductRepository;
import com.fusionx.lending.product.resources.SubProductRiskMappingUpdateResource;
import com.fusionx.lending.product.resources.WorkflowInitiationRequestResource;
import com.fusionx.lending.product.resources.WorkflowRulesResource;
import com.fusionx.lending.product.service.LendingWorkflowService;
import com.fusionx.lending.product.service.SubProductRiskTemplateMappingService;
import com.fusionx.lending.product.service.SubProductHistoryService;
import com.fusionx.lending.product.service.ValidationService;

/**
 * Sub Product Risk Mapping Update Resource
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1  18-10-2021 							Dilki        Created
 *    
 ********************************************************************************************************
 */

@Component
@Transactional(rollbackFor = Exception.class)
public class SubProductRiskTemplateMappingServiceImpl extends MessagePropertyBase
		implements SubProductRiskTemplateMappingService {

	@Autowired
	private ValidationService validationService;

	@Autowired
	private SubProductRepository subProductRepository;

	@Autowired
	private SubProductPendingRepository subProductPendingRepository;

	@Autowired
	private LendingWorkflowService lendingWorkflowService;

	@Autowired
	private SubProductHistoryService subProductHistoryService;

	private static final String DEFAULT_APPROVAL_USER = "kie-server";

	private static final String DOMAIN_PATH = "com.fusionx.LendingWF";

	private static final String DOMAIN = "LendingWF";

	@Override
	public List<SubProduct> getByRiskTemplateId(Long riskTemplateId) {
		return subProductRepository.findByRiskTemplateId(riskTemplateId);

	}

	@Override
	public Long updateRiskTempMappingDetails(String tenantId, Long id,
			SubProductRiskMappingUpdateResource subProductRiskMappingUpdateResource) {

		if (LogginAuthentcation.getInstance().getUserName() == null
				|| LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new ValidateRecordException(environment.getProperty(COMMON_NOT_NULL), "username");

		Optional<SubProduct> isPresentSubProduct = subProductRepository.findById(id);
		if (!isPresentSubProduct.isPresent()) {
			throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), "riskTemplateId");
		}

		Optional<SubProductPending> subProductPending = subProductPendingRepository
				.findBySubProductIdAndStatusAndApproveStatus(id, CommonStatus.ACTIVE.toString(),
						CommonApproveStatus.PENDING.toString());

		if (CommonApproveStatus.PENDING.toString().equals(isPresentSubProduct.get().getApproveStatus())
				|| subProductPending.isPresent()) {
			throw new ValidateRecordException(environment.getProperty("subProduct.can-not-update"), "message");
		}

		RiskTemplateResponse isPresentRiskTemplates = validationService.validateRiskTemplate(tenantId,
				subProductRiskMappingUpdateResource.getRiskTemplateId());

		approveOrGenerateWorkFlow(tenantId, LogginAuthentcation.getInstance().getUserName(), isPresentSubProduct.get(),
				subProductRiskMappingUpdateResource, isPresentRiskTemplates.getId().toString());

		return isPresentSubProduct.get().getId();
	}

	private SubProductPending approveOrGenerateWorkFlow(String tenantId, String userName, SubProduct subProduct,
			SubProductRiskMappingUpdateResource subProductRiskMappingUpdateResource, String riskTemplates) {
		WorkflowRulesResource workflowRulesResource = null;
		Long processId = null;
		WorkflowType workflowType = WorkflowType.MASTER_DEFINITION_TEMP_APPROVAL;
		LendingWorkflow lendingWorkflow = null;
		SubProductPending subProductPending = null;

		WorkflowInitiationRequestResource workflowInitiationRequestResource = new WorkflowInitiationRequestResource();
		workflowInitiationRequestResource.setApprovalUser(DEFAULT_APPROVAL_USER);
		workflowInitiationRequestResource.setApprovWorkflowType(workflowType);

		workflowRulesResource = validationService.invokedLendingProductRule(workflowType, DOMAIN_PATH, DOMAIN,
				tenantId);

		if (workflowRulesResource.getApprovalRequired().equalsIgnoreCase(CommonStatus.YES.toString())) {
			processId = validationService.initiateLendingProductWorkFlow(workflowInitiationRequestResource, tenantId);
			if (processId != null) {
				lendingWorkflow = lendingWorkflowService.save(tenantId, processId, workflowType, userName);
				subProductPending = savePendingSubProduct(tenantId, subProduct, subProductRiskMappingUpdateResource,
						lendingWorkflow, riskTemplates, userName);

			}
		} else {

			directUpdateSubProduct(tenantId, subProduct, riskTemplates, subProductRiskMappingUpdateResource);

		}
		return subProductPending;

	}

	private void directUpdateSubProduct(String tenantId, SubProduct md, String riskTemplates,
			SubProductRiskMappingUpdateResource subProductRiskMappingUpdateResource) {

		subProductHistoryService.saveHistory(tenantId, md, LogginAuthentcation.getInstance().getUserName());

		SubProduct subProduct = md;
		subProduct.setTenantId(tenantId);
		subProduct.setRiskTemplateId(riskTemplates);
		subProduct.setRiskTemplateRemark(subProductRiskMappingUpdateResource.getRemark());
		subProduct.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
		subProduct.setModifiedDate(validationService.getCreateOrModifyDate());
		subProduct.setSyncTs(validationService.getSyncTs());
	}

	private SubProductPending savePendingSubProduct(String tenantId, SubProduct subProduct,
			SubProductRiskMappingUpdateResource subProductRiskMappingUpdateResource, LendingWorkflow lendingWorkflow,
			String riskTemplates, String userName) {

		SubProduct sp = subProduct;
		sp.setApproveStatus(CommonApproveStatus.PENDING.toString());
		sp.setModifiedUser(userName);
		sp.setModifiedDate(validationService.getCreateOrModifyDate());
		sp.setSyncTs(validationService.getCreateOrModifyDate());
		subProductRepository.saveAndFlush(sp);

		SubProductPending subProductPending = new SubProductPending();

		subProductPending.setTenantId(tenantId);
		if (lendingWorkflow != null)
			subProductPending.setLendingWorkflow(lendingWorkflow);

		subProductPending.setSubProduct(sp);
		subProductPending.setApproveStatus(CommonApproveStatus.PENDING.toString());
		subProductPending.setMaxPenalInterestRate(sp.getMaxPenalInterestRate());
		subProductPending.setMiniPenalInterestRate(sp.getMiniPenalInterestRate());
		subProductPending.setPenalInterest(sp.getPenalInterest());
		subProductPending.setCode(sp.getCode());
		subProductPending.setName(sp.getName());
		subProductPending.setProduct(sp.getProduct());
		subProductPending.setPredecessorId(sp.getPredecessorId());
		subProductPending.setFeatureBenifitTemplate(sp.getFeatureBenifitTemplate());
		subProductPending.setEligibility(sp.getEligibility());
		subProductPending.setInterestTemplate(sp.getInterestTemplate());
		subProductPending.setRepayment(sp.getRepayment());
		subProductPending.setCoreProductId(sp.getCoreProductId());
		subProductPending.setStateTenurePeriodId(sp.getStateTenurePeriodId());
		subProductPending.setMarketingStateId(sp.getMarketingStateId());
		subProductPending.setFirstMarketedDate(sp.getFirstMarketedDate());
		subProductPending.setLastMarketedDate(sp.getLastMarketedDate());
		subProductPending.setStatus(sp.getStatus());
		subProductPending.setModifiedDate(sp.getModifiedDate());
		subProductPending.setModifiedUser(sp.getModifiedUser());
		subProductPending.setVersion(sp.getVersion());
		subProductPending.setCreatedDate(validationService.getCreateOrModifyDate());
		subProductPending.setCreatedUser(userName);
		subProductPending.setDueDateTemplate(sp.getDueDateTemplate());
		subProductPending.setDueDateTemplateRemark(sp.getDueDateTemplateRemark());
		subProductPending.setRiskTemplateId(riskTemplates);
		subProductPending.setRiskTemplateRemark(subProductRiskMappingUpdateResource.getRemark());
		subProductPending.setSyncTs(validationService.getCreateOrModifyDate());

		subProductPending = subProductPendingRepository.save(subProductPending);

		return subProductPending;
	}

}
