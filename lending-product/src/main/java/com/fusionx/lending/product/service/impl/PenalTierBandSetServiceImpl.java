package com.fusionx.lending.product.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.core.LoggerRequest;
import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.LendingWorkflow;
import com.fusionx.lending.product.domain.PenalInterest;
import com.fusionx.lending.product.domain.PenalInterestPending;
import com.fusionx.lending.product.domain.PenalInterestType;
import com.fusionx.lending.product.domain.PenalTierBandSet;
import com.fusionx.lending.product.domain.PenalTierBandSetPending;
import com.fusionx.lending.product.enums.CommonApproveStatus;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.enums.EntityPoint;
import com.fusionx.lending.product.enums.InterestCalculationMethod;
import com.fusionx.lending.product.enums.PenalTierBandMethod;
import com.fusionx.lending.product.enums.RateCalculationMethod;
import com.fusionx.lending.product.enums.ServiceEntity;
import com.fusionx.lending.product.enums.WorkflowType;
import com.fusionx.lending.product.enums.YesNo;
import com.fusionx.lending.product.exception.InvalidServiceIdException;
import com.fusionx.lending.product.exception.ValidateRecordException;
import com.fusionx.lending.product.repository.PenalInterestPendingRepository;
import com.fusionx.lending.product.repository.PenalInterestRepository;
import com.fusionx.lending.product.repository.PenalInterestTypeRepository;
import com.fusionx.lending.product.repository.PenalTierBandSetPendingRepository;
import com.fusionx.lending.product.repository.PenalTierBandSetRepository;
import com.fusionx.lending.product.resources.PenalTierBandSetAddRequest;
import com.fusionx.lending.product.resources.PenalTierBandSetUpdateRequest;
import com.fusionx.lending.product.resources.WorkflowInitiationRequestResource;
import com.fusionx.lending.product.resources.WorkflowRulesResource;
import com.fusionx.lending.product.service.LendingWorkflowService;
import com.fusionx.lending.product.service.PenalTierBandSetService;
import com.fusionx.lending.product.service.ValidationService;

@Component
@Transactional(rollbackFor = Exception.class)
public class PenalTierBandSetServiceImpl extends MessagePropertyBase implements PenalTierBandSetService {

	@Autowired
	private PenalTierBandSetRepository penalTierBandSetRepository;

	@Autowired
	private PenalTierBandSetPendingRepository penalTierBandSetPendingRepository;

	@Autowired
	private PenalInterestRepository penalInterestTemplateRepository;

	@Autowired
	private PenalInterestTypeRepository penalInterestTypeRepository;

	@Autowired
	private PenalInterestPendingRepository penalInterestTemplatePendingRepository;

//	@Autowired
//	private PenalTierBandSetHistoryService  penalTierBandSetHistoryService;

	@Autowired
	private ValidationService validationService;

	@Autowired
	private LendingWorkflowService lendingWorkflowService;

	private static final String DEFAULT_APPROVAL_USER = "kie-server";

	private static final String DOMAIN_PATH = "com.fusionx.LendingWF";

	private static final String DOMAIN = "LendingWF";

	@Override
	public Optional<PenalTierBandSet> getById(Long id) {
		Optional<PenalTierBandSet> isPenalTierBandSet = penalTierBandSetRepository.findById(id);
		if (isPenalTierBandSet.isPresent()) {
			return Optional.ofNullable(isPenalTierBandSet.get());
		} else {
			return Optional.empty();
		}
	}

	@Override
	public Long addPenalTierBandSet(String tenantId, Long interestTempId,
			PenalTierBandSetAddRequest penalTierBandSetAddRequest) {

		Optional<PenalInterest> isPresentInterestTemplate = penalInterestTemplateRepository
				.findByIdAndStatus(interestTempId, CommonStatus.ACTIVE);

		if (!isPresentInterestTemplate.isPresent()) {
			LoggerRequest.getInstance().logInfo(
					"PenalInterestTemplate********************************Validate Penal Interest Template*********************************************");
			throw new ValidateRecordException(environment.getProperty("record-not-found"), "message");
		}

		Optional<PenalTierBandSet> isPresentInterestTierBandSet = penalTierBandSetRepository
				.findByCode(penalTierBandSetAddRequest.getCode());
		if (isPresentInterestTierBandSet.isPresent())
			throw new InvalidServiceIdException(environment.getProperty("common.duplicate"), ServiceEntity.CODE,
					EntityPoint.PENAL_TIER_BAND_SET);

		PenalInterestType penalInterestType = validatePenalInterestType(
				validationService.stringToLong(penalTierBandSetAddRequest.getPenalInterestTypeId()),
				penalTierBandSetAddRequest.getPenalInterestTypeName());

		PenalTierBandSet penalTierBandSet = new PenalTierBandSet();
		penalTierBandSet.setTenantId(tenantId);
		penalTierBandSet.setPenalInterest(isPresentInterestTemplate.get());
		penalTierBandSet.setRateCalculationMethod(RateCalculationMethod.valueOf(penalTierBandSetAddRequest.getRateCalculationMethod()));
		
		penalTierBandSet.setTierBandMethod(PenalTierBandMethod.valueOf(penalTierBandSetAddRequest.getTierBandMethod()));
		penalTierBandSet.setInterestCalculationMethod(
				InterestCalculationMethod.valueOf(penalTierBandSetAddRequest.getInterestCalculationMethod()));
		penalTierBandSet.setPenalInterestType(penalInterestType);
		penalTierBandSet.setNote(penalTierBandSetAddRequest.getNote());
		penalTierBandSet.setCode(penalTierBandSetAddRequest.getCode());
		penalTierBandSet.setGracePeriodLength(
				validationService.stringToLong(penalTierBandSetAddRequest.getGracePeriodLength()));
		penalTierBandSet.setStatus(CommonStatus.valueOf(penalTierBandSetAddRequest.getStatus()));
		penalTierBandSet.setCreatedDate(validationService.getCreateOrModifyDate());
		penalTierBandSet.setSyncTs(validationService.getCreateOrModifyDate());
		penalTierBandSet.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
		penalTierBandSetRepository.save(penalTierBandSet);
		return penalTierBandSet.getId();
	}

	private PenalInterestType validatePenalInterestType(Long id, String name) {
		Optional<PenalInterestType> penalInterestType = penalInterestTypeRepository.findByIdAndNameAndStatus(id, name,
				CommonStatus.ACTIVE);

		if (!penalInterestType.isPresent())
			throw new InvalidServiceIdException(environment.getProperty(COMMON_INVALID_VALUE),
					ServiceEntity.PENAL_INTEREST_TYPE_ID, EntityPoint.PENAL_TIER_BAND_SET);

		return penalInterestType.get();
	}

	@Override
	public Long updatePenalTierBandSet(String tenantId, Long interestTempId,
			PenalTierBandSetUpdateRequest penalTierBandSetUpdateRequest) {
		Optional<PenalInterestPending> penalInterestPendingOpt = null;
		PenalInterestPending penalInterestPending = null;
		Optional<PenalInterest> isPresentPenalInterest = penalInterestTemplateRepository
				.findByIdAndStatus(interestTempId, CommonStatus.ACTIVE);

		if (!isPresentPenalInterest.isPresent()) {
			LoggerRequest.getInstance().logInfo(
					"PenalInterestTemplate********************************Validate InterestTemplate*********************************************");
			throw new ValidateRecordException(environment.getProperty("record-not-found"), "message");
		}

		if (penalTierBandSetUpdateRequest.getInterestTempPendingId() != null) {
			Optional<PenalInterestPending> isPresentPenalInterestPending = penalInterestTemplatePendingRepository
					.findByIdAndStatus(
							validationService.stringToLong(penalTierBandSetUpdateRequest.getInterestTempPendingId()),
							CommonStatus.ACTIVE);

			if (!isPresentPenalInterestPending.isPresent()) {
				LoggerRequest.getInstance().logInfo(
						"PenalInterestTemplatePending********************************Validate PenalInterestTemplatePending*********************************************");
				throw new ValidateRecordException(environment.getProperty("record-not-found"), "message");
			} else {
				if (!CommonApproveStatus.PENDING.toString()
						.equals(isPresentPenalInterestPending.get().getApproveStatus())) {
					throw new ValidateRecordException(environment.getProperty("interestTemplate.can-not-update"),
							"message");
				}
			}

		}


		// Penal Interest Template Update - PenalTierBandSet Update existing
		if (penalTierBandSetUpdateRequest.getId() != null) {
			Optional<PenalTierBandSet> isPresentInterestTierBandSet = penalTierBandSetRepository
					.findByPenalInterestIdAndId(interestTempId,
							validationService.stringToLong(penalTierBandSetUpdateRequest.getId()));

			if (isPresentInterestTierBandSet.isPresent()) {
				if (!isPresentInterestTierBandSet.get().getVersion()
						.equals(Long.parseLong(penalTierBandSetUpdateRequest.getVersion()))) {
					throw new InvalidServiceIdException(environment.getProperty(COMMON_INVALID_VALUE),
							ServiceEntity.VERSION, EntityPoint.PENAL_TIER_BAND_SET);
				}

				if (!isPresentInterestTierBandSet.get().getCode()
						.equalsIgnoreCase(penalTierBandSetUpdateRequest.getCode())) {
					throw new InvalidServiceIdException(environment.getProperty(COMMON_CODE_UPDATE),
							ServiceEntity.CODE, EntityPoint.PENAL_TIER_BAND_SET);
				}

				if (penalTierBandSetUpdateRequest.getInterestTempPendingId() != null) {
					penalInterestPendingOpt = penalInterestTemplatePendingRepository.findById(
							validationService.stringToLong(penalTierBandSetUpdateRequest.getInterestTempPendingId()));
					penalInterestPending = createPenalTierBandSetPending(tenantId, isPresentPenalInterest.get(),
							penalInterestPendingOpt.get(), isPresentInterestTierBandSet.get(),
							penalInterestPendingOpt.get().getLendingWorkflow(), penalTierBandSetUpdateRequest);
				} else {
					penalInterestPending = approveOrGenerateWorkFlow(tenantId, isPresentPenalInterest.get(),
							isPresentInterestTierBandSet.get(), penalTierBandSetUpdateRequest);
				}

			} else {
				LoggerRequest.getInstance().logInfo(
						"PenalTierBandSet********************************Validate PenalTierBandSet*********************************************");
				throw new ValidateRecordException(environment.getProperty("record-not-found"), "message");
			}
		} else { // Penal Interest Template Update - add new PenalTierBandSet

			Optional<PenalTierBandSet> isPresentInterestTierBandSet = penalTierBandSetRepository
					.findByCode(penalTierBandSetUpdateRequest.getCode());
			if (isPresentInterestTierBandSet.isPresent())
				throw new InvalidServiceIdException(environment.getProperty("common.duplicate"), ServiceEntity.CODE,
						EntityPoint.PENAL_TIER_BAND_SET);

			if (penalTierBandSetUpdateRequest.getInterestTempPendingId() != null) {
				penalInterestPendingOpt = penalInterestTemplatePendingRepository.findById(
						validationService.stringToLong(penalTierBandSetUpdateRequest.getInterestTempPendingId()));
				penalInterestPending = createPenalTierBandSetPending(tenantId, isPresentPenalInterest.get(),
						penalInterestPendingOpt.get(), null, penalInterestPendingOpt.get().getLendingWorkflow(),
						penalTierBandSetUpdateRequest);
			} else {
				penalInterestPending = approveOrGenerateWorkFlow(tenantId, isPresentPenalInterest.get(), null,
						penalTierBandSetUpdateRequest);
			}
		}

		return penalInterestPending.getId();

	}

	private PenalInterestPending approveOrGenerateWorkFlow(String tenantId, PenalInterest interestTemp,
			PenalTierBandSet interestTierBandSet, PenalTierBandSetUpdateRequest interestTierBandSetUpdateResource) {
		WorkflowRulesResource workflowRulesResource = null;
		Long processId = null;
		WorkflowType workflowType = WorkflowType.INTEREST_TEMP_APPROVAL;
		LendingWorkflow lendingWorkflow = null;
		PenalInterestPending interestTempPending = null;

		WorkflowInitiationRequestResource workflowInitiationRequestResource = new WorkflowInitiationRequestResource();
		workflowInitiationRequestResource.setApprovalUser(DEFAULT_APPROVAL_USER);
		workflowInitiationRequestResource.setApprovWorkflowType(workflowType);

		workflowRulesResource = validationService.invokedLendingProductRule(workflowType, DOMAIN_PATH, DOMAIN,
				tenantId);

		if (workflowRulesResource.getApprovalRequired().equalsIgnoreCase(CommonStatus.YES.toString())) {
			processId = validationService.initiateLendingProductWorkFlow(workflowInitiationRequestResource, tenantId);
			if (processId != null) {
				lendingWorkflow = lendingWorkflowService.save(tenantId, processId, workflowType,
						LogginAuthentcation.getInstance().getUserName());
				interestTempPending = createPenalTierBandSetPending(tenantId, interestTemp, null,
						interestTierBandSet, lendingWorkflow, interestTierBandSetUpdateResource);
			}
		} else {
			directUpdate(tenantId, interestTemp, interestTierBandSetUpdateResource);
		}

		return interestTempPending;

	}

	private PenalInterestPending createPenalTierBandSetPending(String tenantId, PenalInterest interestTemp,
			PenalInterestPending interestTemplatePending, PenalTierBandSet interestTierBandSet,
			LendingWorkflow lendingWorkflow, PenalTierBandSetUpdateRequest penalTierBandSetUpdateRequest) {

		if (interestTemplatePending == null) {
			interestTemplatePending = createInterestTemplatePending(tenantId, interestTemp, lendingWorkflow);
		}

		PenalInterestType penalInterestType = validatePenalInterestType(
				validationService.stringToLong(penalTierBandSetUpdateRequest.getPenalInterestTypeId()),
				penalTierBandSetUpdateRequest.getPenalInterestTypeName());

		PenalTierBandSetPending penalTierBandSetPending = new PenalTierBandSetPending();
		penalTierBandSetPending.setTenantId(tenantId);
		penalTierBandSetPending.setPenalInterest(interestTemp);
		penalTierBandSetPending.setPenalInterestPending(interestTemplatePending);
		penalTierBandSetPending.setPenalTierBandSet(interestTierBandSet);
		penalTierBandSetPending.setPenalInterestType(penalInterestType);
		penalTierBandSetPending.setCode(penalTierBandSetUpdateRequest.getCode());
		penalTierBandSetPending.setGracePeriodLength(
				validationService.stringToLong(penalTierBandSetUpdateRequest.getGracePeriodLength()));
		penalTierBandSetPending.setRateCalculationMethod(
				RateCalculationMethod.valueOf(penalTierBandSetUpdateRequest.getRateCalculationMethod()));
		penalTierBandSetPending
				.setTierBandMethod(PenalTierBandMethod.valueOf(penalTierBandSetUpdateRequest.getTierBandMethod()));
		penalTierBandSetPending.setInterestCalculationMethod(
				InterestCalculationMethod.valueOf(penalTierBandSetUpdateRequest.getInterestCalculationMethod()));
		penalTierBandSetPending.setNote(penalTierBandSetUpdateRequest.getNote());
		penalTierBandSetPending.setLendingWorkflow(lendingWorkflow);
		penalTierBandSetPending.setUpdated(YesNo.YES);
		penalTierBandSetPending.setApproveStatus(CommonApproveStatus.PENDING);
		penalTierBandSetPending.setStatus(CommonStatus.valueOf(penalTierBandSetUpdateRequest.getStatus()));
		penalTierBandSetPending.setCreatedDate(validationService.getCreateOrModifyDate());
		penalTierBandSetPending.setSyncTs(validationService.getCreateOrModifyDate());
		penalTierBandSetPending.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
		penalTierBandSetPending = penalTierBandSetPendingRepository.saveAndFlush(penalTierBandSetPending);

		return interestTemplatePending;
	}

	private PenalInterestPending createInterestTemplatePending(String tenantId, PenalInterest interestTemplate,
			LendingWorkflow lendingWorkflow) {

		PenalInterestPending penalInterestTemplatePending = new PenalInterestPending();
		penalInterestTemplatePending.setTenantId(tenantId);
		penalInterestTemplatePending.setLendingWorkflow(lendingWorkflow);
		penalInterestTemplatePending.setPenalInterest(interestTemplate);
		penalInterestTemplatePending.setName(interestTemplate.getName());
		penalInterestTemplatePending.setStatus(CommonStatus.valueOf(interestTemplate.getStatus().toString()));
		penalInterestTemplatePending.setUpdated(YesNo.NO);
		penalInterestTemplatePending.setApproveStatus(CommonApproveStatus.PENDING);
		penalInterestTemplatePending.setCreatedDate(validationService.getCreateOrModifyDate());
		penalInterestTemplatePending.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
		penalInterestTemplatePending.setSyncTs(validationService.getCreateOrModifyDate());
		penalInterestTemplatePending = penalInterestTemplatePendingRepository
				.saveAndFlush(penalInterestTemplatePending);

		return penalInterestTemplatePending;

	}

	private void directUpdate(String tenantId, PenalInterest interestTemp,
			PenalTierBandSetUpdateRequest penalTierBandSetUpdateRequest) {

		Optional<PenalTierBandSet> intTierBandSet = null;
		PenalTierBandSet penalTierBandSet;

		if (penalTierBandSetUpdateRequest.getId() != null) {
			intTierBandSet = penalTierBandSetRepository
					.findById(validationService.stringToLong(penalTierBandSetUpdateRequest.getId()));
			// interestTierBandSetHistoryService.saveHistory(intTierBandSet.get().getTenantId(),
			// intTierBandSet.get(), LogginAuthentcation.getInstance().getUserName());

			penalTierBandSet = intTierBandSet.get();
			penalTierBandSet.setModifiedDate(validationService.getCreateOrModifyDate());
			penalTierBandSet.setModifiedUser(LogginAuthentcation.getInstance().getUserName());

		} else {
			penalTierBandSet = new PenalTierBandSet();
			penalTierBandSet.setCreatedDate(validationService.getCreateOrModifyDate());
			penalTierBandSet.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
		}
		penalTierBandSet.setTenantId(tenantId);
		penalTierBandSet.setPenalInterest(interestTemp);
		penalTierBandSet.setCode(penalTierBandSetUpdateRequest.getCode());
		penalTierBandSet.setRateCalculationMethod(
				RateCalculationMethod.valueOf(penalTierBandSetUpdateRequest.getRateCalculationMethod()));
		penalTierBandSet
				.setTierBandMethod(PenalTierBandMethod.valueOf(penalTierBandSetUpdateRequest.getTierBandMethod()));
		penalTierBandSet.setInterestCalculationMethod(
				InterestCalculationMethod.valueOf(penalTierBandSetUpdateRequest.getInterestCalculationMethod()));
		penalTierBandSet.setNote(penalTierBandSetUpdateRequest.getNote());
		penalTierBandSet.setStatus(CommonStatus.valueOf(penalTierBandSetUpdateRequest.getStatus()));
		penalTierBandSet.setSyncTs(validationService.getCreateOrModifyDate());
		penalTierBandSetRepository.save(penalTierBandSet);

	}

	@Override
	public Optional<PenalTierBandSetPending> getByPendingId(Long pendingId) {
		Optional<PenalTierBandSetPending> isPresentPenalTierBandSetPending = penalTierBandSetPendingRepository
				.findById(pendingId);

		if (isPresentPenalTierBandSetPending.isPresent()) {
			return Optional.ofNullable(isPresentPenalTierBandSetPending.get());
		} else {
			return Optional.empty();
		}
	}

	@Override
	public List<PenalTierBandSet> getByPenalInterestTemplateId(Long interestTempId) {
		List<PenalTierBandSet> interestTierBandSetList = new ArrayList<>();
		Optional<PenalInterest> isPresentPenalInterestTemplate = penalInterestTemplateRepository
				.findById(interestTempId);

		if (isPresentPenalInterestTemplate.isPresent())
			interestTierBandSetList = penalTierBandSetRepository
					.findByPenalInterest(isPresentPenalInterestTemplate.get());
		return interestTierBandSetList;
	}

	@Override
	public List<PenalTierBandSetPending> getPendingByPenalInterestTemplateId(Long interestTempId) {
		List<PenalTierBandSetPending> interestTierBandSetPendingList = new ArrayList<>();
		Optional<PenalInterest> isPresentInterestTemplate = penalInterestTemplateRepository.findById(interestTempId);

		if (isPresentInterestTemplate.isPresent())
			interestTierBandSetPendingList = penalTierBandSetPendingRepository
					.findByPenalInterest(isPresentInterestTemplate.get());
		return interestTierBandSetPendingList;
	}

//	@Override
//	public Page<InterestTierBandSetPending> searchInterestTierBandSetPending(String searchq, String status, String approveStatus,
//			Pageable pageable) {
//		if(searchq==null || searchq.isEmpty())
//			searchq=null;
//		if(status==null || status.isEmpty())
//			status=null;
//		if(approveStatus==null || approveStatus.isEmpty())
//			approveStatus=null;
//		return interestTierBandSetPendingRepository.searchInterestTierBandSetPending(searchq, status, approveStatus, pageable);
//	}

}
