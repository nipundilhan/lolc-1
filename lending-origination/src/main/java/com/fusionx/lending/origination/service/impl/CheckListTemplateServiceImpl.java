package com.fusionx.lending.origination.service.impl;

/**
 * Check List Template
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   17-08-2021   FXL-75  		 FXL-551 	Dilki        Created
 *    
 ********************************************************************************************************
 */
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.fusionx.lending.origination.base.MessagePropertyBase;
import com.fusionx.lending.origination.core.LoggerRequest;
import com.fusionx.lending.origination.core.LogginAuthentcation;
import com.fusionx.lending.origination.domain.CheckListItem;
import com.fusionx.lending.origination.domain.CheckListTemplate;
import com.fusionx.lending.origination.domain.CheckListTemplateDetails;
import com.fusionx.lending.origination.domain.CheckListTemplateDetailsPending;
import com.fusionx.lending.origination.domain.CheckListTemplatePending;
import com.fusionx.lending.origination.domain.CommonList;
import com.fusionx.lending.origination.domain.MicroBprWorkflow;
import com.fusionx.lending.origination.enums.CommonApproveStatus;
import com.fusionx.lending.origination.enums.CommonListReferenceCode;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.enums.ServiceEntity;
import com.fusionx.lending.origination.enums.WorkflowStatus;
import com.fusionx.lending.origination.enums.WorkflowType;
import com.fusionx.lending.origination.exception.InvalidServiceIdException;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.repository.CheckListItemRepository;
import com.fusionx.lending.origination.repository.CheckListTemplateDetailsPendingRepository;
import com.fusionx.lending.origination.repository.CheckListTemplateDetailsRepository;
import com.fusionx.lending.origination.repository.CheckListTemplatePendingRepository;
import com.fusionx.lending.origination.repository.CheckListTemplateRepository;
import com.fusionx.lending.origination.repository.CommonListRepository;
import com.fusionx.lending.origination.repository.MicroBprWorkflowRepository;
import com.fusionx.lending.origination.resource.CheckListTemplateAddResource;
import com.fusionx.lending.origination.resource.CheckListTemplateDetailsAddResource;
import com.fusionx.lending.origination.resource.CheckListTemplateDetailsUpdateResource;
import com.fusionx.lending.origination.resource.CheckListTemplateUpdateResource;
import com.fusionx.lending.origination.resource.ProductResponse;
import com.fusionx.lending.origination.resource.SubProductResponse;
import com.fusionx.lending.origination.resource.WorkflowInitiationRequestResource;
import com.fusionx.lending.origination.resource.WorkflowRulesResource;
import com.fusionx.lending.origination.service.CheckListTemplateHistoryService;
import com.fusionx.lending.origination.service.CheckListTemplateService;
import com.fusionx.lending.origination.service.LendingWorkflowService;
import com.fusionx.lending.origination.service.ValidateService;

@Component
@Transactional(rollbackFor = Exception.class)
public class CheckListTemplateServiceImpl extends MessagePropertyBase implements CheckListTemplateService {

	private static final String DEFAULT_APPROVAL_USER = "kie-server";

	private static final String DOMAIN_PATH = "com.fusionx.comn.micro.bpr_rules.microBprWF";

	private static final String DOMAIN = "microBprWF";

	@Autowired
	private CheckListTemplateDetailsRepository checkListTemplateDetailsRepository;

	@Autowired
	private CheckListTemplateDetailsPendingRepository checkListTemplateDetailsPendingRepository;

	@Autowired
	private CheckListItemRepository checkListItemRepository;

	@Autowired
	private CheckListTemplateRepository checkListTemplateRepository;

	@Autowired
	private CheckListTemplatePendingRepository checkListTemplatePendingRepository;

	@Autowired
	private ValidateService validationService;

	@Autowired
	private CheckListTemplateHistoryService checkListTemplateHistoryService;

	@Autowired
	private MicroBprWorkflowRepository microBprWorkflowRepository;

	@Autowired
	private LendingWorkflowService lendingWorkflowService;

	@Autowired
	private CommonListRepository commonListRepository;

	@Override
	public List<CheckListTemplate> getAll() {
		List<CheckListTemplate> isPresentCheckListTemplate = checkListTemplateRepository.findAll();
		for (CheckListTemplate checkListTemplate : isPresentCheckListTemplate) {
			List<CheckListTemplateDetails> checkListTemplateTemplateDetail = checkListTemplateDetailsRepository
					.findByCheckListTemplateId(checkListTemplate.getId());
			checkListTemplate.setCheckListTemplateDetails(checkListTemplateTemplateDetail);
		}
		return isPresentCheckListTemplate;
	}

	@Override
	public Optional<CheckListTemplate> getById(Long id) {
		Optional<CheckListTemplate> isPresentCheckListTemplate = checkListTemplateRepository.findById(id);
		if (isPresentCheckListTemplate.isPresent()) {
			CheckListTemplate checkListTemplate = isPresentCheckListTemplate.get();
			List<CheckListTemplateDetails> checkListTemplateTemplateDetail = checkListTemplateDetailsRepository
					.findByCheckListTemplateId(checkListTemplate.getId());
			checkListTemplate.setCheckListTemplateDetails(checkListTemplateTemplateDetail);
			return isPresentCheckListTemplate;
		} else {
			return Optional.empty();
		}
	}

	@Override
	public Optional<CheckListTemplate> getByCode(String code) {
		Optional<CheckListTemplate> isPresentCheckListTemplate = checkListTemplateRepository.findByCode(code);
		if (isPresentCheckListTemplate.isPresent()) {
			CheckListTemplate checkListTemplate = isPresentCheckListTemplate.get();
			List<CheckListTemplateDetails> checkListTemplateTemplateDetail = checkListTemplateDetailsRepository
					.findByCheckListTemplateId(checkListTemplate.getId());
			checkListTemplate.setCheckListTemplateDetails(checkListTemplateTemplateDetail);
			return isPresentCheckListTemplate;
		} else {
			return Optional.empty();
		}

	}

	@Override
	public List<CheckListTemplate> getByName(String name) {
		List<CheckListTemplate> isPresentCheckListTemplate = checkListTemplateRepository.findByNameContaining(name);
		for (CheckListTemplate checkListTemplate : isPresentCheckListTemplate) {
			List<CheckListTemplateDetails> checkListTemplateTemplateDetail = checkListTemplateDetailsRepository
					.findByCheckListTemplateId(checkListTemplate.getId());
			checkListTemplate.setCheckListTemplateDetails(checkListTemplateTemplateDetail);
		}
		return isPresentCheckListTemplate;
	}

	@Override
	public List<CheckListTemplate> getByStatus(String status) {
		List<CheckListTemplate> isPresentCheckListTemplate = checkListTemplateRepository
				.findByStatus(CommonStatus.valueOf(status));
		for (CheckListTemplate checkListTemplate : isPresentCheckListTemplate) {
			List<CheckListTemplateDetails> checkListTemplateTemplateDetail = checkListTemplateDetailsRepository
					.findByCheckListTemplateId(checkListTemplate.getId());
			checkListTemplate.setCheckListTemplateDetails(checkListTemplateTemplateDetail);
		}
		return isPresentCheckListTemplate;
	}

	@Override
	public CheckListTemplate addCheckListTemplate(String tenantId,
			CheckListTemplateAddResource checkListTemplateAddResource) {

		Optional<CheckListTemplate> isPresentCheckListTemplate = checkListTemplateRepository
				.findByCode(checkListTemplateAddResource.getCode());

		ProductResponse productResponse = validationService.validateProduct(tenantId,
				checkListTemplateAddResource.getMainProductId());

		SubProductResponse subProductResponse = validationService.validateSubProduct(tenantId,
				checkListTemplateAddResource.getSubProductId());

		if (isPresentCheckListTemplate.isPresent()) {
			throw new ValidateRecordException(environment.getProperty(COMMON_DUPLICATE), "code");
		}

		CommonList applicableLevel = this.validateCategory(
				validationService.stringToLong(checkListTemplateAddResource.getApplicableLevelId()),
				checkListTemplateAddResource.getApplicableLevelName());

		CheckListTemplate checkListTemplate = new CheckListTemplate();
		checkListTemplate.setTenantId(tenantId);
		checkListTemplate.setCode(checkListTemplateAddResource.getCode());
		checkListTemplate.setName(checkListTemplateAddResource.getName());
		checkListTemplate.setMainProduct(productResponse.getId());
		checkListTemplate.setSubProduct(subProductResponse.getId());
		//checkListTemplate.setApplicableLevel(applicableLevel);
		checkListTemplate.setComment(checkListTemplateAddResource.getComment());
		checkListTemplate.setStatus(CommonStatus.valueOf(checkListTemplateAddResource.getStatus()));
		checkListTemplate.setSyncTs(validationService.getCreateOrModifyDate());
		checkListTemplate.setCreatedDate(validationService.getCreateOrModifyDate());
		checkListTemplate.setCreatedUser(LogginAuthentcation.getInstance().getUserName());

		checkListTemplate = checkListTemplateRepository.save(checkListTemplate);

		if (!CollectionUtils.isEmpty(checkListTemplateAddResource.getCheckListItem())) {

			for (CheckListTemplateDetailsAddResource checkListTemplateDetailsAddResource : checkListTemplateAddResource
					.getCheckListItem()) {

				if (StringUtils.isEmpty(checkListTemplateDetailsAddResource.getId())) {
					throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND), "checkListItemId");
				}

				Optional<CheckListItem> isPresentCheckListItem = checkListItemRepository.findByIdAndStatus(
						Long.parseLong(checkListTemplateDetailsAddResource.getId()), CommonStatus.ACTIVE);
				if (!isPresentCheckListItem.isPresent()) {
					throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND), "checkListItemId");
				}

				CheckListTemplateDetails checkListTemplateDetails = new CheckListTemplateDetails();
				checkListTemplateDetails.setTenantId(tenantId);
				checkListTemplateDetails.setCheckListItem(isPresentCheckListItem.get());
				checkListTemplateDetails.setCheckListTemplate(checkListTemplate);
				checkListTemplateDetails.setChecklistTemplateName(checkListTemplate.getName());
				checkListTemplateDetails.setIsMandatory(checkListTemplateDetailsAddResource.getIsMandatory());
				checkListTemplateDetails.setIsChecked(checkListTemplateDetailsAddResource.getIsChecked());
				checkListTemplateDetails
						.setStatus(CommonStatus.valueOf(checkListTemplateDetailsAddResource.getStatus()));
				checkListTemplateDetails.setCreatedDate(validationService.getCreateOrModifyDate());
				checkListTemplateDetails.setCreatedUser((LogginAuthentcation.getInstance().getUserName()));
				checkListTemplateDetails.setSyncTs(validationService.getSyncTs());
				checkListTemplateDetailsRepository.saveAndFlush(checkListTemplateDetails);
			}
		}

		return checkListTemplate;

	}

//	@Override
//	public CheckListTemplate updateCheckListTemplate(String tenantId, Long id,
//			CheckListTemplateUpdateResource checkListTemplateUpdateResource) {
//
//		Optional<CheckListTemplate> isPresentCheckListTemplate = checkListTemplateRepository.findById(id);
//
//		checkListTemplateHistoryService.saveHistory(tenantId, isPresentCheckListTemplate.get(),
//				LogginAuthentcation.getInstance().getUserName());
//
//		if (!isPresentCheckListTemplate.get().getVersion()
//				.equals(Long.parseLong(checkListTemplateUpdateResource.getVersion())))
//			throw new InvalidServiceIdException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.VERSION);
//
//		if (!isPresentCheckListTemplate.get().getCode().equalsIgnoreCase(checkListTemplateUpdateResource.getCode()))
//			throw new InvalidServiceIdException(environment.getProperty(RECORD_UPDATED), ServiceEntity.CODE);
//
//		CheckListTemplate checkListTemplate = isPresentCheckListTemplate.get();
//		checkListTemplate.setTenantId(tenantId);
//		checkListTemplate.setCode(checkListTemplate.getCode());
//		checkListTemplate.setName(checkListTemplate.getName());
//		checkListTemplate.setMainProduct(checkListTemplate.getMainProduct());
//		checkListTemplate.setSubProduct(checkListTemplate.getSubProduct());
//		checkListTemplate.setApplicableLevel(checkListTemplate.getApplicableLevel());
//		checkListTemplate.setComment(checkListTemplate.getComment());
//		checkListTemplate.setCheckListItem(checkListTemplate.getCheckListItem());
//		checkListTemplate.setStatus(checkListTemplate.getStatus());
//		checkListTemplate.setModifiedDate(validationService.getCreateOrModifyDate());
//		checkListTemplate.setSyncTs(validationService.getCreateOrModifyDate());
//		checkListTemplate.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
//
//		return checkListTemplateRepository.saveAndFlush(checkListTemplate);
//
//	}

	@Override
	public CheckListTemplate updateCheckListTemplate(String tenantId, Long id,
			CheckListTemplateUpdateResource checkListTemplateUpdateResource) {

		Optional<CheckListTemplate> isPresentCheckListTemplate = checkListTemplateRepository.findById(id);
		if (!isPresentCheckListTemplate.isPresent()) {
			throw new ValidateRecordException(environment.getProperty("common.record-not-found"), "message");
		} else {
			if (!isPresentCheckListTemplate.get().getVersion()
					.equals(Long.parseLong(checkListTemplateUpdateResource.getVersion())))
				throw new InvalidServiceIdException(environment.getProperty(COMMON_INVALID_VALUE),
						ServiceEntity.VERSION);

			if (!isPresentCheckListTemplate.get().getCode().equalsIgnoreCase(checkListTemplateUpdateResource.getCode()))
				throw new InvalidServiceIdException(environment.getProperty("common.code-update"), ServiceEntity.CODE);

			LoggerRequest.getInstance().logInfo(
					"CheckListTemplate********************************Workflow Call*********************************************");
			approveOrGenerateWorkFlow(tenantId, LogginAuthentcation.getInstance().getUserName(),
					isPresentCheckListTemplate.get(), checkListTemplateUpdateResource);

			return isPresentCheckListTemplate.get();
		}
	}

	private void approveOrGenerateWorkFlow(String tenantId, String user, CheckListTemplate checkListTemplate,
			CheckListTemplateUpdateResource checkListTemplateUpdateResource) {
		WorkflowRulesResource workflowRulesResource = null;
		Long processId = null;
		WorkflowType workflowType = WorkflowType.LOAN_CHECK_LIST_TEMPLATE;
		MicroBprWorkflow microBprWorkflow = null;

		WorkflowInitiationRequestResource workflowInitiationRequestResource = new WorkflowInitiationRequestResource();
		workflowInitiationRequestResource.setApprovalUser(DEFAULT_APPROVAL_USER);
		workflowInitiationRequestResource.setApprovWorkflowType(workflowType);

		LoggerRequest.getInstance().logInfo(
				"CheckListTemplate********************************Get Workflow Rules*********************************************");
		workflowRulesResource = validationService.invokedMicroBprRule(workflowType, DOMAIN_PATH, DOMAIN, tenantId);

		if (workflowRulesResource.getApprovalRequired().equalsIgnoreCase(CommonStatus.YES.toString())) {
			processId = validationService.initiateMicroBprWorkFlow(workflowInitiationRequestResource, tenantId);
			if (processId != null) {
				microBprWorkflow = lendingWorkflowService.save(tenantId, processId, workflowType, user);
				savePendingCheckListTemplate(tenantId, checkListTemplate, checkListTemplateUpdateResource,
						microBprWorkflow, user);
			}
		} else {
		savePendingCheckListTemplate(tenantId,
					checkListTemplate, checkListTemplateUpdateResource, microBprWorkflow, user);
//			updateCheckListTemplate(checkListTemplatePending, checkListTemplate, CommonApproveStatus.APPROVED, user);
		}
	}

	private CheckListTemplatePending savePendingCheckListTemplate(String tenantId, CheckListTemplate checkListTemplate,
			CheckListTemplateUpdateResource checkListTemplateUpdateResource, MicroBprWorkflow microBprWorkflow,
			String user) {

		ProductResponse productResponse = validationService.validateProduct(tenantId,
				checkListTemplateUpdateResource.getMainProductId());

		SubProductResponse subProductResponse = validationService.validateSubProduct(tenantId,
				checkListTemplateUpdateResource.getSubProductId());
		
		CommonList applicableLevel = this.validateCategory(
				validationService.stringToLong(checkListTemplateUpdateResource.getApplicableLevel()),
				checkListTemplateUpdateResource.getApplicableLevelName());

		LoggerRequest.getInstance().logInfo(
				"CheckListTemplate********************************Save History*********************************************");
		checkListTemplateHistoryService.saveHistory(tenantId, checkListTemplate, user);

		CheckListTemplate checkListTemplateNew = checkListTemplate;
		checkListTemplateNew.setApproveStatus(CommonApproveStatus.PENDING);
		checkListTemplateNew.setModifiedUser(user);
		checkListTemplateNew.setModifiedDate(validationService.getCreateOrModifyDate());
		checkListTemplateNew.setSyncTs(validationService.getCreateOrModifyDate());
		checkListTemplateRepository.saveAndFlush(checkListTemplateNew);

		CheckListTemplatePending checkListTemplatePending = new CheckListTemplatePending();

		checkListTemplatePending.setTenantId(tenantId);
		if (microBprWorkflow != null)
			checkListTemplatePending.setMicroBprWorkflow(microBprWorkflow);
		checkListTemplatePending.setCheckListTemplate(checkListTemplate);
		checkListTemplatePending.setCode(checkListTemplate.getCode());
		checkListTemplatePending.setName(checkListTemplateUpdateResource.getName());
		checkListTemplatePending.setMainProduct(productResponse.getId());
		checkListTemplatePending.setSubProduct(subProductResponse.getId());
		checkListTemplatePending.setApplicableLevel(applicableLevel);
		checkListTemplatePending.setComment(checkListTemplateUpdateResource.getComment());
		checkListTemplatePending.setStatus(CommonStatus.valueOf(checkListTemplateUpdateResource.getStatus()));
		checkListTemplatePending.setCreatedDate(checkListTemplate.getCreatedDate());
		checkListTemplatePending.setCreatedUser(checkListTemplate.getCreatedUser());
		checkListTemplatePending.setPenCreatedDate(validationService.getCreateOrModifyDate());
		checkListTemplatePending.setPenCreatedUser(LogginAuthentcation.getInstance().getUserName());
		checkListTemplatePending.setSyncTs(validationService.getCreateOrModifyDate());

		checkListTemplatePendingRepository.save(checkListTemplatePending);

		if (checkListTemplateUpdateResource.getCheckListItem() != null
				&& !checkListTemplateUpdateResource.getCheckListItem().isEmpty()) {

			for (CheckListTemplateDetailsUpdateResource checkListTemplateDetailsUpdateResource : checkListTemplateUpdateResource
					.getCheckListItem()) {

				if (StringUtils.isEmpty(checkListTemplateDetailsUpdateResource.getId())) {
					throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND), "checkListItemId");
				}
				Optional<CheckListItem> isPresentCheckListItem = checkListItemRepository
						.findById(validationService.stringToLong(checkListTemplateDetailsUpdateResource.getId()));
				if (!isPresentCheckListItem.isPresent()) {
					throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND), "checkListItemId");
				}

				CheckListTemplateDetailsPending checkListTemplateDetailsP = new CheckListTemplateDetailsPending();
				checkListTemplateDetailsP.setTenantId(tenantId);
				checkListTemplateDetailsP.setCheckListItem(isPresentCheckListItem.get());
				checkListTemplateDetailsP.setCheckListTemplate(checkListTemplate);
				checkListTemplateDetailsP.setChecklistTemplateName(checkListTemplate.getName());
				checkListTemplateDetailsP.setIsMandatory(checkListTemplateDetailsUpdateResource.getIsMandatory());
				checkListTemplateDetailsP.setIsChecked(checkListTemplateDetailsUpdateResource.getIsChecked());
				checkListTemplateDetailsP
						.setStatus(CommonStatus.valueOf(checkListTemplateDetailsUpdateResource.getStatus()));
				checkListTemplateDetailsP.setCreatedDate(checkListTemplate.getCreatedDate());
				checkListTemplateDetailsP.setCreatedUser(checkListTemplate.getCreatedUser());
				checkListTemplateDetailsP.setSyncTs(validationService.getSyncTs());
				checkListTemplateDetailsP.setPenCreatedDate(validationService.getCreateOrModifyDate());
				checkListTemplateDetailsP.setPenCreatedUser(LogginAuthentcation.getInstance().getUserName());
				checkListTemplateDetailsPendingRepository.saveAndFlush(checkListTemplateDetailsP);
			}
		}

		return checkListTemplatePending;

	}

	private void updateCheckListTemplate(CheckListTemplatePending checkListTemplatePending, CheckListTemplate cht,
			CommonApproveStatus approveStatus, String user) {

		LoggerRequest.getInstance().logInfo(
				"CheckListTemplate********************************Save History*********************************************");
		checkListTemplateHistoryService.saveHistory(cht.getTenantId(), cht, user);

		CheckListTemplate checkListTemplate = cht;

		if (approveStatus.toString().equalsIgnoreCase(CommonApproveStatus.APPROVED.toString())) {
			checkListTemplate.setName(checkListTemplatePending.getName());
			checkListTemplate.setCode(checkListTemplatePending.getCode());
			checkListTemplate.setName(checkListTemplatePending.getName());
			checkListTemplate.setMainProduct(checkListTemplatePending.getMainProduct());
			checkListTemplate.setSubProduct(checkListTemplatePending.getSubProduct());
			//checkListTemplate.setApplicableLevel(checkListTemplatePending.getApplicableLevel());
			checkListTemplate.setComment(checkListTemplatePending.getComment());
			checkListTemplate.setStatus(checkListTemplatePending.getStatus());
			checkListTemplate.setModifiedDate(checkListTemplatePending.getCreatedDate());
			checkListTemplate.setModifiedUser(checkListTemplatePending.getCreatedUser());
			checkListTemplate.setApproveStatus(approveStatus);
		}
		checkListTemplate.setSyncTs(validationService.getCreateOrModifyDate());

		if (checkListTemplatePending.getCheckListTemplateDetails() != null
				&& !checkListTemplatePending.getCheckListTemplateDetails().isEmpty()) {

			for (CheckListTemplateDetailsPending checkListTemplateDetailsPending : checkListTemplatePending
					.getCheckListTemplateDetails()) {

				CheckListTemplateDetails checkListTemplateDetails = new CheckListTemplateDetails();
				checkListTemplateDetails.setTenantId(checkListTemplateDetailsPending.getTenantId());
				checkListTemplateDetails.setCheckListItem(checkListTemplateDetailsPending.getCheckListItem());
				checkListTemplateDetails.setCheckListTemplate(checkListTemplateDetailsPending.getCheckListTemplate());
				checkListTemplateDetails
						.setChecklistTemplateName(checkListTemplateDetailsPending.getChecklistTemplateName());
				checkListTemplateDetails.setIsMandatory(checkListTemplateDetailsPending.getIsMandatory());
				checkListTemplateDetails.setIsChecked(checkListTemplateDetailsPending.getIsChecked());
				checkListTemplateDetails.setStatus(checkListTemplateDetailsPending.getStatus());
				checkListTemplateDetails.setCreatedDate(validationService.getCreateOrModifyDate());
				checkListTemplateDetails.setCreatedUser((LogginAuthentcation.getInstance().getUserName()));
				checkListTemplateDetails.setSyncTs(validationService.getSyncTs());
				checkListTemplateDetails.setModifiedDate(checkListTemplateDetailsPending.getCreatedDate());
				checkListTemplateDetails.setModifiedUser(checkListTemplateDetailsPending.getCreatedUser());
				checkListTemplateDetailsRepository.saveAndFlush(checkListTemplateDetails);
			}
		}

		checkListTemplateRepository.saveAndFlush(checkListTemplate);
	}

	@Override
	public boolean approveReject(String tenantId, Long id, WorkflowStatus workflowStatus) {

		WorkflowInitiationRequestResource workflowInitiationRequestResource = new WorkflowInitiationRequestResource();
		workflowInitiationRequestResource.setApprovalUser(DEFAULT_APPROVAL_USER);
		workflowInitiationRequestResource.setApprovWorkflowType(WorkflowType.LOAN_CHECK_LIST_TEMPLATE);
		WorkflowRulesResource workflowRulesResource = null;
		WorkflowType workflowType = WorkflowType.LOAN_CHECK_LIST_TEMPLATE;
		String user = LogginAuthentcation.getInstance().getUserName();
		CommonApproveStatus approveStatus = null;

		Optional<CheckListTemplatePending> isPresentCheckListTemplatePending = checkListTemplatePendingRepository
				.findById(id);
		if (!isPresentCheckListTemplatePending.isPresent()) {
			throw new ValidateRecordException(environment.getProperty("common.record-not-found"), "message");
		} else {
			Optional<CheckListTemplate> checkListTemplate = checkListTemplateRepository
					.findById(isPresentCheckListTemplatePending.get().getCheckListTemplate().getId());
			if (!checkListTemplate.isPresent()) {
				throw new ValidateRecordException(environment.getProperty("common.record-not-found"), "message");
			} else {
				if (!isPresentCheckListTemplatePending.get().getCheckListTemplate().getApproveStatus()
						.equals(CommonApproveStatus.PENDING))
					throw new ValidateRecordException(environment.getProperty("common.can-not-rej-app"), "message");

				Optional<MicroBprWorkflow> microBprWorkflow = microBprWorkflowRepository
						.findById(isPresentCheckListTemplatePending.get().getMicroBprWorkflow().getId());
				if (!microBprWorkflow.isPresent()) {
					throw new ValidateRecordException(environment.getProperty("common.record-not-found"), "message");
				} else {
					LoggerRequest.getInstance().logInfo(
							"CheckListTemplate********************************Get Workflow Rules*********************************************");
					workflowRulesResource = validationService.invokedMicroBprRule(workflowType, DOMAIN_PATH, DOMAIN,
							tenantId);

					if ((workflowRulesResource.getApprovalAllowed().equalsIgnoreCase(CommonStatus.NO.toString()))
							&& (microBprWorkflow.get().getCreatedUser()
									.equalsIgnoreCase(LogginAuthentcation.getInstance().getUserName()))) {
						throw new ValidateRecordException(environment.getProperty("cannot.process.approve"), "message");
					}

					if (workflowStatus.toString().equalsIgnoreCase(WorkflowStatus.COMPLETE.toString())) {
						LoggerRequest.getInstance().logInfo(
								"CheckListTemplate********************************Approve Workflow*********************************************");
						validationService.approveWorkFlow(microBprWorkflow.get().getWorkflowProcessId(), workflowType,
								user, tenantId);
						approveStatus = CommonApproveStatus.APPROVED;
					} else {
						LoggerRequest.getInstance().logInfo(
								"CheckListTemplate********************************Abort Workflow*********************************************");
						validationService.abortedWorkFlow(microBprWorkflow.get().getWorkflowProcessId(), workflowType,
								user, tenantId);
						approveStatus = CommonApproveStatus.REJECTED;
					}

					lendingWorkflowService.update(microBprWorkflow.get(), workflowStatus, user);

					LoggerRequest.getInstance().logInfo(
							"CheckListTemplate********************************update*********************************************");
					updateCheckListTemplate(isPresentCheckListTemplatePending.get(), checkListTemplate.get(),
							approveStatus, user);

					return true;
				}
			}
		}
	}

	@Override
	public Optional<CheckListTemplatePending> getByPendingId(Long pendingId) {
		return checkListTemplatePendingRepository.findById(pendingId);

	}

	public CommonList validateCategory(Long id, String name) {

		Optional<CommonList> commonListItem = commonListRepository.findById(id);
		if (!commonListItem.isPresent())
			throw new ValidateRecordException(environment.getProperty(NOT_FOUND), "applicableLevelId");

		else if (!CommonStatus.ACTIVE.toString().equalsIgnoreCase(commonListItem.get().getStatus()))
			throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), "applicableLevelId");

		else if (!CommonListReferenceCode.CHECKLIST_APP_LEVEL.toString()
				.equalsIgnoreCase(commonListItem.get().getReferenceCode())
				|| !commonListItem.get().getName().equalsIgnoreCase(name))
			throw new ValidateRecordException(environment.getProperty(COMMON_NOT_MATCH), "applicableLevelId");

		return commonListItem.get();
	}
}
