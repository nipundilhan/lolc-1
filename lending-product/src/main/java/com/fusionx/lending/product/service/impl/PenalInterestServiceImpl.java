package com.fusionx.lending.product.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.DisbursementConditions;
import com.fusionx.lending.product.domain.LendingWorkflow;
import com.fusionx.lending.product.domain.PenalInterest;
import com.fusionx.lending.product.domain.PenalInterestPending;
import com.fusionx.lending.product.domain.PenalTierBand;
import com.fusionx.lending.product.domain.PenalTierBandPending;
import com.fusionx.lending.product.domain.PenalTierBandSet;
import com.fusionx.lending.product.domain.PenalTierBandSetPending;
import com.fusionx.lending.product.enums.CommonApproveStatus;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.enums.EntityPoint;
import com.fusionx.lending.product.enums.ServiceEntity;
import com.fusionx.lending.product.enums.WorkflowStatus;
import com.fusionx.lending.product.enums.WorkflowType;
import com.fusionx.lending.product.enums.YesNo;
import com.fusionx.lending.product.exception.InvalidServiceIdException;
import com.fusionx.lending.product.exception.ValidateRecordException;
import com.fusionx.lending.product.repository.LendingWorkflowRepository;
import com.fusionx.lending.product.repository.PenalInterestPendingRepository;
import com.fusionx.lending.product.repository.PenalInterestRepository;
import com.fusionx.lending.product.repository.PenalTierBandPendingRepository;
import com.fusionx.lending.product.repository.PenalTierBandRepository;
import com.fusionx.lending.product.repository.PenalTierBandSetPendingRepository;
import com.fusionx.lending.product.repository.PenalTierBandSetRepository;
import com.fusionx.lending.product.resources.PenalInterestAddResource;
import com.fusionx.lending.product.resources.PenalInterestUpdateResource;
import com.fusionx.lending.product.resources.WorkflowInitiationRequestResource;
import com.fusionx.lending.product.resources.WorkflowRulesResource;
import com.fusionx.lending.product.service.LendingWorkflowService;
import com.fusionx.lending.product.service.PenalInterestService;
import com.fusionx.lending.product.service.PenalTierBandSetService;
import com.fusionx.lending.product.service.ValidationService;

@Component
@Transactional(rollbackFor = Exception.class)
public class PenalInterestServiceImpl extends MessagePropertyBase implements PenalInterestService {

	@Autowired
	private PenalInterestRepository penalInterestRepository;
	@Autowired
	private PenalInterestPendingRepository penalInterestPendingRepository;
	@Autowired
	private PenalTierBandSetPendingRepository penalTierBandSetPendingRepository;
	@Autowired
	private PenalTierBandPendingRepository penalTierBandPendingRepository;

	@Autowired
	private LendingWorkflowRepository lendingWorkflowRepository;
//	@Autowired
//	private PenalInterestHistoryService penalInterestHistoryService;

	@Autowired
	PenalTierBandSetService penalTierBandSetService;

	@Autowired
	private PenalTierBandSetRepository penalTierBandSetRepository;

	@Autowired
	private PenalTierBandRepository penalTierBandRepository;

	@Autowired
	private ValidationService validationService;

	@Autowired
	private LendingWorkflowService lendingWorkflowService;

	private static final String DEFAULT_APPROVAL_USER = "kie-server";

	private static final String DOMAIN_PATH = "com.fusionx.LendingWF";

	private static final String DOMAIN = "LendingWF";

	@Override
	public List<PenalInterest> findAll() {
		return penalInterestRepository.findAll();
	}

	@Override
	public Optional<PenalInterest> findById(Long interestTemplateId) {
		Optional<PenalInterest> interestTemplate = penalInterestRepository.findById(interestTemplateId);
		if (interestTemplate.isPresent())
			return Optional.ofNullable(interestTemplate.get());
		else
			return Optional.empty();
	}

	@Override
	public List<PenalInterest> findByStatus(String status) {
		return penalInterestRepository.findByStatus(CommonStatus.valueOf(status));
	}

	@Override
	public Optional<PenalInterest> getPenalTemplateByCode(String interestTemplateCode) {
		Optional<PenalInterest> penalInterest = penalInterestRepository.findByCode(interestTemplateCode);
		if (penalInterest.isPresent())
			return Optional.ofNullable(penalInterest.get());
		else
			return Optional.empty();
	}

	@Override
	public PenalInterest addPenalInterestTemplate(String tenantId, PenalInterestAddResource penalInterestAddResource) {

		PenalInterest penalInterest = new PenalInterest();

		Optional<PenalInterest> isPresentPenalInterest = penalInterestRepository
				.findByCodeAndStatus(penalInterestAddResource.getCode(), CommonStatus.ACTIVE);
		if (isPresentPenalInterest.isPresent())
			throw new InvalidServiceIdException(environment.getProperty("common.duplicate"), ServiceEntity.CODE,
					EntityPoint.INTEREST_TEMPLATE);

		penalInterest.setTenantId(tenantId);
		penalInterest.setCode(penalInterestAddResource.getCode());
		penalInterest.setName(penalInterestAddResource.getName());
		penalInterest.setStatus(CommonStatus.valueOf(penalInterestAddResource.getStatus()));
		penalInterest.setCreatedDate(validationService.getCreateOrModifyDate());
		penalInterest.setSyncTs(validationService.getCreateOrModifyDate());
		penalInterest.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
		penalInterest = penalInterestRepository.saveAndFlush(penalInterest);
		return penalInterest;
	}

	@Override
	public PenalInterest updatePenalInterestTemplate(String tenantId, Long id,
			PenalInterestUpdateResource penalInterestUpdateResource) {

		PenalInterest penalInterest = null;

		Optional<PenalInterest> isPresentPenalInterest = penalInterestRepository.findById(id);

		if (isPresentPenalInterest.isPresent()) {

			if (!isPresentPenalInterest.get().getVersion()
					.equals(Long.parseLong(penalInterestUpdateResource.getVersion())))
				throw new InvalidServiceIdException(environment.getProperty("common.version.mismatch"),
						ServiceEntity.VERSION, EntityPoint.PENAL_INTEREST_TEMPLATE);

			penalInterest = isPresentPenalInterest.get();

			Optional<PenalInterestPending> isPresentPenalInterestPending = penalInterestPendingRepository
					.findByPenalInterest(penalInterest);

			if (isPresentPenalInterestPending.isPresent()) {
				if (CommonApproveStatus.PENDING.toString()
						.equals(isPresentPenalInterestPending.get().getApproveStatus()))
					throw new InvalidServiceIdException(environment.getProperty("invalid-pending.update"),
							ServiceEntity.PENAL_INTEREST_TEMPLATE, EntityPoint.PENAL_INTEREST_TEMPLATE);
			}

			Optional<PenalInterest> isPresentInterestTemplateCode = penalInterestRepository
					.findByCodeAndId(penalInterestUpdateResource.getCode(), id);
			if (!isPresentInterestTemplateCode.isPresent())
				throw new InvalidServiceIdException(environment.getProperty("code.update.mismatch"), ServiceEntity.CODE,
						EntityPoint.PENAL_INTEREST_TEMPLATE);

			executeWorkflowCall(tenantId, penalInterest, penalInterestUpdateResource,
					WorkflowType.INTEREST_TEMP_APPROVAL);
		} else
			throw new ValidateRecordException(environment.getProperty("record-not-found"), "message");

		return penalInterest;

	}

	private void executeWorkflowCall(String tenantId, PenalInterest penalInterest,
			PenalInterestUpdateResource penalInterestUpdateResource, WorkflowType workflowType) {
		Long processId = null;
		LendingWorkflow lendingWorkflow = null;
		String user = LogginAuthentcation.getInstance().getUserName();
		WorkflowInitiationRequestResource workflowInitiationRequestResource = new WorkflowInitiationRequestResource();
		workflowInitiationRequestResource.setApprovalUser(DEFAULT_APPROVAL_USER);
		workflowInitiationRequestResource.setApprovWorkflowType(workflowType);

		WorkflowRulesResource workflowRulesResource = validationService.invokedLendingProductRule(workflowType,
				DOMAIN_PATH, DOMAIN, tenantId);

		if (workflowRulesResource.getApprovalRequired().equalsIgnoreCase(CommonStatus.YES.toString())) {
			processId = validationService.initiateLendingProductWorkFlow(workflowInitiationRequestResource, tenantId);
			if (processId != null) {
				lendingWorkflow = lendingWorkflowService.save(tenantId, processId, workflowType, user);
				savePendingPenalInterestTemplate(tenantId, penalInterest, penalInterestUpdateResource, lendingWorkflow,
						user);
			}
		} else {
			PenalInterestPending penalInterestPending = savePendingPenalInterestTemplate(tenantId, penalInterest,
					penalInterestUpdateResource, lendingWorkflow, user);
			updateInterestTemplateDetails(penalInterestPending, penalInterest, CommonApproveStatus.APPROVED, user);
		}

	}

	private void updateInterestTemplateDetails(PenalInterestPending penalInterestPending, PenalInterest pnlInterest,
			CommonApproveStatus approvedStatus, String userName) {

		PenalInterest penalInterest = pnlInterest;

		if (approvedStatus.toString().equalsIgnoreCase(CommonApproveStatus.APPROVED.toString())) {

			penalInterest.setName(penalInterestPending.getName());
			penalInterest.setStatus(penalInterestPending.getStatus());
			penalInterest.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
			penalInterest.setModifiedDate(validationService.getCreateOrModifyDate());
			penalInterest.setSyncTs(validationService.getCreateOrModifyDate());
			penalInterestRepository.saveAndFlush(penalInterest);

			penalInterestPending.setApprovedUser(userName);
			penalInterestPending.setApproveStatus(CommonApproveStatus.APPROVED);
			penalInterestPending.setApprovedDate(validationService.getCreateOrModifyDate());
			penalInterestPending.setSyncTs(validationService.getCreateOrModifyDate());
			penalInterestPending = penalInterestPendingRepository.save(penalInterestPending);

			List<PenalTierBandSetPending> penalTierBandSetPending = penalTierBandSetPendingRepository
					.findByPenalInterestPendingAndStatus(penalInterestPending, CommonStatus.ACTIVE);

			for (PenalTierBandSetPending item : penalTierBandSetPending) {
				if (CommonApproveStatus.PENDING.toString().equals(item.getApproveStatus().toString())) {
					PenalTierBandSet penalTierBandSet = item.getPenalTierBandSet();
					penalTierBandSet.setRateCalculationMethod(item.getRateCalculationMethod());
					penalTierBandSet.setInterestCalculationMethod(item.getInterestCalculationMethod());
					penalTierBandSet.setTierBandMethod(item.getTierBandMethod());
					penalTierBandSet.setGracePeriodLength(item.getGracePeriodLength());
					penalTierBandSet.setNote(item.getNote());
					penalTierBandSet.setPenalInterestType(item.getPenalInterestType());
					penalTierBandSet.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
					penalTierBandSet.setModifiedDate(validationService.getCreateOrModifyDate());
					penalTierBandSet.setSyncTs(validationService.getCreateOrModifyDate());
					penalTierBandSetRepository.save(penalTierBandSet);

					item.setApprovedUser(userName);
					item.setApproveStatus(CommonApproveStatus.APPROVED);
					item.setApprovedDate(validationService.getCreateOrModifyDate());
					item.setSyncTs(validationService.getCreateOrModifyDate());
					penalTierBandSetPendingRepository.save(item);

					List<PenalTierBandPending> penalTierBandPending = penalTierBandPendingRepository
							.findByPenalTierBandSetPendingAndStatus(item, CommonStatus.ACTIVE);
					for (PenalTierBandPending itm : penalTierBandPending) {

						if (CommonApproveStatus.PENDING.toString().equals(itm.getApproveStatus().toString())) {
							PenalTierBand penalTierBand = itm.getPenalTierBand();
							penalTierBand.setFixedVariableTypeValue(itm.getFixedVariableTypeValue());
							penalTierBand.setLoanProviderIntRate(itm.getLoanProviderIntRate());
							penalTierBand.setTierValueMaximum(itm.getTierValueMaximum());
							penalTierBand.setNote(itm.getNote());
							penalTierBand.setAer(itm.getAer());

							penalTierBand.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
							penalTierBand.setModifiedDate(validationService.getCreateOrModifyDate());
							penalTierBand.setSyncTs(validationService.getCreateOrModifyDate());
							penalTierBandRepository.save(penalTierBand);

							itm.setApprovedUser(userName);
							itm.setApproveStatus(CommonApproveStatus.APPROVED);
							itm.setApprovedDate(validationService.getCreateOrModifyDate());
							itm.setSyncTs(validationService.getCreateOrModifyDate());
							penalTierBandPendingRepository.save(itm);
						}

					}
				}

			}

		} else {
			penalInterestPending.setApprovedUser(userName);
			penalInterestPending.setApproveStatus(CommonApproveStatus.REJECTED);
			penalInterestPending.setApprovedDate(validationService.getCreateOrModifyDate());
			penalInterestPending.setSyncTs(validationService.getCreateOrModifyDate());
			penalInterestPendingRepository.save(penalInterestPending);

			List<PenalTierBandSetPending> penalTierBandSetPending = penalTierBandSetPendingRepository
					.findByPenalInterestPendingAndStatus(penalInterestPending, CommonStatus.ACTIVE);

			for (PenalTierBandSetPending item : penalTierBandSetPending) {
				if (CommonApproveStatus.PENDING.toString().equals(item.getApproveStatus().toString())) {

					item.setApprovedUser(userName);
					item.setApproveStatus(CommonApproveStatus.REJECTED);
					item.setApprovedDate(validationService.getCreateOrModifyDate());
					item.setSyncTs(validationService.getCreateOrModifyDate());
					penalTierBandSetPendingRepository.save(item);

					List<PenalTierBandPending> penalTierBandPending = penalTierBandPendingRepository
							.findByPenalTierBandSetPendingAndStatus(item, CommonStatus.ACTIVE);
					for (PenalTierBandPending itm : penalTierBandPending) {

						if (CommonApproveStatus.PENDING.toString().equals(itm.getApproveStatus().toString())) {
							
							itm.setApprovedUser(userName);
							itm.setApproveStatus(CommonApproveStatus.REJECTED);
							itm.setApprovedDate(validationService.getCreateOrModifyDate());
							itm.setSyncTs(validationService.getCreateOrModifyDate());
							penalTierBandPendingRepository.save(itm);
						}

					}
				}

			}
		}

	}

	private PenalInterestPending savePendingPenalInterestTemplate(String tenantId, PenalInterest oldInterestTemplate,
			PenalInterestUpdateResource penalInterestUpdateResource, LendingWorkflow lendingWorkflow, String userName) {

		PenalInterestPending penalInterestPending = new PenalInterestPending();
		penalInterestPending.setTenantId(tenantId);
		if (lendingWorkflow != null)
			penalInterestPending.setLendingWorkflow(lendingWorkflow);
		penalInterestPending.setPenalInterest(oldInterestTemplate);
		penalInterestPending.setName(penalInterestUpdateResource.getName());
		penalInterestPending.setStatus(CommonStatus.valueOf(penalInterestUpdateResource.getStatus()));
		penalInterestPending.setApproveStatus(CommonApproveStatus.PENDING);
		penalInterestPending.setCreatedDate(validationService.getCreateOrModifyDate());
		penalInterestPending.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
		penalInterestPending.setSyncTs(validationService.getCreateOrModifyDate());
		penalInterestPending.setUpdated(YesNo.YES);
		return penalInterestPendingRepository.save(penalInterestPending);

	}

	@Override
	public Optional<PenalInterestPending> getByPendingId(Long pendingId) {
		Optional<PenalInterestPending> isPresentPenalInterestPending = penalInterestPendingRepository
				.findById(pendingId);
		if (isPresentPenalInterestPending.isPresent()) {
			return Optional.ofNullable(isPresentPenalInterestPending.get());
		} else {
			return Optional.empty();
		}
	}

	@Override
	public boolean approveReject(String tenantId, Long id, WorkflowStatus workflowStatus) {
		WorkflowInitiationRequestResource workflowInitiationRequestResource = new WorkflowInitiationRequestResource();
		workflowInitiationRequestResource.setApprovalUser(DEFAULT_APPROVAL_USER);
		workflowInitiationRequestResource.setApprovWorkflowType(WorkflowType.INTEREST_TEMP_APPROVAL);
		WorkflowRulesResource workflowRulesResource = null;
		WorkflowType workflowType = WorkflowType.INTEREST_TEMP_APPROVAL;
		String user = LogginAuthentcation.getInstance().getUserName();
		CommonApproveStatus approveStatus = null;

		Optional<PenalInterestPending> isPresentPenalInterestPending = penalInterestPendingRepository.findById(id);
		PenalInterestPending penalInterestPending = isPresentPenalInterestPending.get();
		Optional<LendingWorkflow> lendingWorkflow = lendingWorkflowRepository
				.findById(penalInterestPending.getLendingWorkflow().getId());

		Optional<PenalInterest> penalInterest = penalInterestRepository
				.findById(penalInterestPending.getPenalInterest().getId());

		if (!penalInterestPending.getApproveStatus().equals(CommonApproveStatus.PENDING))
			throw new ValidateRecordException(environment.getProperty("common.can-not-rej-app"), "message");

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
		} else {
			validationService.abortedWorkFlow(lendingWorkflow.get().getWorkflowProcessId(), workflowType, user,
					tenantId);
			approveStatus = CommonApproveStatus.REJECTED;
		}

		lendingWorkflowService.update(lendingWorkflow.get(), workflowStatus, user);

		updateInterestTemplateDetails(penalInterestPending, penalInterest.get(), approveStatus, user);

		return true;
	}

	@Override
	public List<PenalInterest> findByName(String name) {
		return penalInterestRepository.findByNameContaining(name);
	}

//		@Override
//		public Page<InterestTemplate> searchInterestTemplateList(String searchq, String name, String code, String status, Pageable pageable) {
//			if(searchq==null || searchq.isEmpty())
//				searchq=null;
//			if(name==null || name.isEmpty())
//				name=null;
//			if(code==null || code.isEmpty())
//				code=null;
//			if(status==null || status.isEmpty())
//				status=null;
//			
//			return interestTemplateRepository.searchInterestTemplateList(searchq, name, code,status, pageable);
//		}

}
