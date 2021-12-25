package com.fusionx.lending.product.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.FeeCharge;
import com.fusionx.lending.product.domain.FeeChargeCap;
import com.fusionx.lending.product.domain.FeeChargeCapPending;
import com.fusionx.lending.product.domain.FeeChargePending;
import com.fusionx.lending.product.domain.LendingWorkflow;
import com.fusionx.lending.product.domain.OtherFeeType;
import com.fusionx.lending.product.enums.CommonApproveStatus;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.enums.EntityPoint;
import com.fusionx.lending.product.enums.MinMaxType;
import com.fusionx.lending.product.enums.ServiceEntity;
import com.fusionx.lending.product.enums.WorkflowStatus;
import com.fusionx.lending.product.enums.WorkflowType;
import com.fusionx.lending.product.exception.InvalidServiceIdException;
import com.fusionx.lending.product.exception.ValidateRecordException;
import com.fusionx.lending.product.repository.FeeChargeCapPendingRepository;
import com.fusionx.lending.product.repository.FeeChargeCapRepository;
import com.fusionx.lending.product.repository.FeeChargePendingRepository;
import com.fusionx.lending.product.repository.FeeChargeRepository;
import com.fusionx.lending.product.repository.LendingWorkflowRepository;
import com.fusionx.lending.product.repository.OtherFeeTypeRepository;
import com.fusionx.lending.product.resources.FeeChargeCapAddResource;
import com.fusionx.lending.product.resources.FeeChargeCapUpdateResource;
import com.fusionx.lending.product.resources.PeriodResponse;
import com.fusionx.lending.product.resources.WorkflowInitiationRequestResource;
import com.fusionx.lending.product.resources.WorkflowRulesResource;
import com.fusionx.lending.product.service.FeeChargeCapHistoryService;
import com.fusionx.lending.product.service.FeeChargeCapService;
import com.fusionx.lending.product.service.LendingWorkflowService;
import com.fusionx.lending.product.service.ValidationService;

/**
 * API Service related to  Fee Charge Cap
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
 * 1        17-07-2021      -                           Dilhan                   Created
 * <p>
 */
@Component
@Transactional(rollbackFor = Exception.class)
public class FeeChargeCapServiceImpl extends MessagePropertyBase implements FeeChargeCapService {

	@Autowired
	private FeeChargeCapRepository feeChargeCapRepository;

	@Autowired
	private FeeChargeRepository feeChargeRepository;
	
	@Autowired
	private OtherFeeTypeRepository otherFeeTypeRepository;

	@Autowired
	private ValidationService validationService;

	@Autowired
	private FeeChargeCapHistoryService feeChargeCapHistoryService;

	@Autowired
	private FeeChargeCapPendingRepository feeChargeCapPendingRepository;
	
	@Autowired
	private FeeChargePendingRepository feeChargePendingRepository;

	@Autowired
	private LendingWorkflowService lendingWorkflowService;

	@Autowired
	private LendingWorkflowRepository lendingWorkflowRepository;

	private static final String DEFAULT_APPROVAL_USER = "kie-server";

	private static final String DOMAIN_PATH = "com.fusionx.LendingWF";

	private static final String DOMAIN = "LendingWF";

	@Override
	public List<FeeChargeCap> getAll() {
		return feeChargeCapRepository.findAll();
	}

	@Override
	public Optional<FeeChargeCap> getById(Long id) {
		Optional<FeeChargeCap> isPresentFeeChargeCap = feeChargeCapRepository.findById(id);
		if (isPresentFeeChargeCap.isPresent()) {
			return Optional.ofNullable(isPresentFeeChargeCap.get());
		} else {
			return Optional.empty();
		}
	}

	@Override
	public Optional<FeeChargeCap> getByCode(String code) {
		Optional<FeeChargeCap> isPresentFeeChargeCap = feeChargeCapRepository.findByCode(code);
		if (isPresentFeeChargeCap.isPresent()) {
			return Optional.ofNullable(isPresentFeeChargeCap.get());
		} else {
			return Optional.empty();
		}
	}

	@Override
	public List<FeeChargeCap> getByStatus(String status) {
		return feeChargeCapRepository.findByStatus(CommonStatus.valueOf(status));
	}

	@Override
	public FeeChargeCap addFeeChargeCap(String tenantId, FeeChargeCapAddResource feeChargeCapAddResource) {

		Optional<FeeChargeCap> isPresentFeeCharge = feeChargeCapRepository
				.findByCode(feeChargeCapAddResource.getCode());

		if (isPresentFeeCharge.isPresent())
			throw new InvalidServiceIdException(environment.getProperty(COMMON_DUPLICATE), ServiceEntity.CODE,
					EntityPoint.FEE_CHARGE);

		Optional<FeeCharge> isFeeCharge = feeChargeRepository
				.findByIdAndStatus(validationService.stringToLong(feeChargeCapAddResource.getFeeChargeId()),CommonStatus.ACTIVE);
		if (isFeeCharge.isPresent()) {
			if (!isFeeCharge.get().getName().equalsIgnoreCase(feeChargeCapAddResource.getFeeChargeName()))
				throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), "feeChargeName");

		} else {
			throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), "feeChargeId");
		}
		
		Optional<OtherFeeType> otherFeeType = otherFeeTypeRepository.findByIdAndNameAndStatus(validationService.stringToLong(feeChargeCapAddResource.getOtherFeeTypeId()), feeChargeCapAddResource.getOtherFeeTypeName(), CommonStatus.ACTIVE);

		if(!otherFeeType.isPresent()) {
			throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), "otherFeeType");
		}
		//validate period id once it is vailable
		PeriodResponse periodResponse = validationService.validatePeriod(tenantId, feeChargeCapAddResource.getFeeCapPeriodId(), feeChargeCapAddResource.getFeeCapPeriodName(), EntityPoint.FEE_CHARGE_CAP);
		
		FeeChargeCap feeChargeCap = new FeeChargeCap();
		feeChargeCap.setTenantId(tenantId);
		feeChargeCap.setCode(feeChargeCapAddResource.getCode());
		feeChargeCap.setFeeCapPeriodId(validationService.stringToLong(feeChargeCapAddResource.getFeeCapPeriodId()));
		feeChargeCap.setPeriodCode(periodResponse.getCode());
		if(feeChargeCapAddResource.getFeeOccurence()!= null && !feeChargeCapAddResource.getFeeOccurence().isEmpty()) {
			feeChargeCap.setFeeOccurence(validationService.stringToLong(feeChargeCapAddResource.getFeeOccurence()));
		}
		feeChargeCap.setMinimumAmount(validationService.stringToBigDecimal(feeChargeCapAddResource.getMinimumAmount()));
		feeChargeCap.setMinMaxType(MinMaxType.valueOf(feeChargeCapAddResource.getMinMaxType()).toString());
		feeChargeCap.setFeeCharge(isFeeCharge.get());
		feeChargeCap.setOtherFeeType(otherFeeType.get());
		feeChargeCap.setStatus(CommonStatus.valueOf(feeChargeCapAddResource.getStatus()));
		feeChargeCap.setCreatedDate(validationService.getCreateOrModifyDate());
		feeChargeCap.setSyncTs(validationService.getCreateOrModifyDate());
		feeChargeCap.setCreatedUser(LogginAuthentcation.getInstance().getUserName());

		return feeChargeCapRepository.save(feeChargeCap);
	}

	@Override
	public FeeChargeCap updateFeeChargeCap(String tenantId, Long id,
			FeeChargeCapUpdateResource feeChargeCapUpdateResource) {

		Optional<FeeChargeCap> isPresentFeeChargeCap = feeChargeCapRepository.findById(id);

		if (!isPresentFeeChargeCap.get().getVersion().equals(Long.parseLong(feeChargeCapUpdateResource.getVersion())))
			throw new InvalidServiceIdException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.VERSION,
					EntityPoint.FEE_CHARGE_CAP);

		if (!isPresentFeeChargeCap.get().getCode().equalsIgnoreCase(feeChargeCapUpdateResource.getCode()))
			throw new InvalidServiceIdException(environment.getProperty("common.code-update"), ServiceEntity.CODE,
					EntityPoint.FEE_CHARGE_CAP);
		
		if (feeChargeCapUpdateResource.getFeeChargePendingId() != null && !feeChargeCapUpdateResource.getFeeChargePendingId().isEmpty()) {
			Optional<FeeChargePending> isPresentFeeChargePending = feeChargePendingRepository
					.findByIdAndStatus(
							validationService.stringToLong(feeChargeCapUpdateResource.getFeeChargePendingId()),
							CommonStatus.ACTIVE);

			if (!isPresentFeeChargePending.isPresent()) {
				throw new ValidateRecordException(environment.getProperty("record-not-found"), "message");
			} else {
				if (!CommonApproveStatus.PENDING.toString()
						.equals(isPresentFeeChargePending.get().getApproveStatus())) {
					throw new ValidateRecordException(environment.getProperty("feeChargeTemplate.can-not-update"),
							"message");
				}
			}

		}

		approveOrGenerateWorkFlow(tenantId, LogginAuthentcation.getInstance().getUserName(),
				isPresentFeeChargeCap.get(), feeChargeCapUpdateResource);

		return isPresentFeeChargeCap.get();
	}

	private void approveOrGenerateWorkFlow(String tenantId, String user, FeeChargeCap feeChargeCap,
			FeeChargeCapUpdateResource feeChargeCapUpdateResource) {

		WorkflowRulesResource workflowRulesResource = null;
		Long processId = null;
		WorkflowType workflowType = WorkflowType.FEE_CHARGE_TEMP_APPROVAL;
		LendingWorkflow lendingWorkflow = null;

		WorkflowInitiationRequestResource workflowInitiationRequestResource = new WorkflowInitiationRequestResource();
		workflowInitiationRequestResource.setApprovalUser(DEFAULT_APPROVAL_USER);
		workflowInitiationRequestResource.setApprovWorkflowType(workflowType);

		workflowRulesResource = validationService.invokedLendingProductRule(workflowType, DOMAIN_PATH, DOMAIN,
				tenantId);

		if (workflowRulesResource.getApprovalRequired().equalsIgnoreCase(CommonStatus.YES.toString())) {
			processId = validationService.initiateLendingProductWorkFlow(workflowInitiationRequestResource, tenantId);
			if (processId != null) {
				lendingWorkflow = lendingWorkflowService.save(tenantId, processId, workflowType, user);
				savePendingFeeChargeCap(tenantId, feeChargeCap, feeChargeCapUpdateResource, lendingWorkflow, user);
			}
		} else {
			FeeChargeCapPending feeChargeCapPending = savePendingFeeChargeCap(tenantId, feeChargeCap,
					feeChargeCapUpdateResource, lendingWorkflow, user);
			updateFeeChargeCap(feeChargeCapPending, feeChargeCap, CommonApproveStatus.APPROVED, user);
		}
	}

	private FeeChargeCapPending savePendingFeeChargeCap(String tenantId, FeeChargeCap feeChargeCap,
			FeeChargeCapUpdateResource feeChargeCapUpdateResource, LendingWorkflow lendingWorkflow, String user) {

		Optional<FeeCharge> isFeeCharge = feeChargeRepository
				.findByIdAndStatus(validationService.stringToLong(feeChargeCapUpdateResource.getFeeChargeId()),CommonStatus.ACTIVE);
		if (isFeeCharge.isPresent()) {
			if (!isFeeCharge.get().getName().equalsIgnoreCase(feeChargeCapUpdateResource.getFeeChargeName()))
				throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), "feeChargeName");

		} else {
			throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), "feeChargeId");
		}
		
		Optional<OtherFeeType> otherFeeType = otherFeeTypeRepository.findByIdAndNameAndStatus(validationService.stringToLong(feeChargeCapUpdateResource.getOtherFeeTypeId()), 
				feeChargeCapUpdateResource.getOtherFeeTypeName(), CommonStatus.ACTIVE);

		if(!otherFeeType.isPresent()) {
			throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), "otherFeeType");
		}
		
		PeriodResponse periodResponse = validationService.validatePeriod(tenantId, feeChargeCapUpdateResource.getFeeCapPeriodId(), feeChargeCapUpdateResource.getFeeCapPeriodName(), 
				EntityPoint.FEE_CHARGE_CAP);
		
		FeeChargePending feeChargePending = new FeeChargePending();
		feeChargePending.setTenantId(tenantId);
		if (lendingWorkflow != null)
			feeChargePending.setLendingWorkflow(lendingWorkflow);
		feeChargePending.setFeeCharge(feeChargeCap.getFeeCharge());
		feeChargePending.setCode(feeChargeCap.getFeeCharge().getCode());
		feeChargePending.setName(feeChargeCap.getFeeCharge().getName());
		feeChargePending.setFeeChargeType(feeChargeCap.getFeeCharge().getFeeChargeType());
		feeChargePending.setStatus(feeChargeCap.getFeeCharge().getStatus());
		feeChargePending.setPcreatedDate(validationService.getCreateOrModifyDate());
		feeChargePending.setPcreatedUser(user);
		feeChargePending.setSyncTs(validationService.getCreateOrModifyDate());
		feeChargePending = feeChargePendingRepository.save(feeChargePending);
		
		Optional<FeeCharge> feeCharge = feeChargeRepository.findById(feeChargePending.getFeeCharge().getId());
		//feeCharge.get().setApproveStatus(CommonApproveStatus.PENDING);
		feeChargeRepository.save(feeCharge.get());

		FeeChargeCapPending feeChargeCapPending = new FeeChargeCapPending();

		feeChargeCapPending.setTenantId(tenantId);
		if (lendingWorkflow != null)
			feeChargeCapPending.setLendingWorkflow(lendingWorkflow);
		feeChargeCapPending.setFeeChargeCap(feeChargeCap);
		feeChargeCapPending.setOtherFeeType(otherFeeType.get());
		feeChargeCapPending.setFeeCharge(isFeeCharge.get());
		feeChargeCapPending.setFeeChargePending(feeChargePending);
		feeChargeCapPending.setApproveStatus(CommonApproveStatus.PENDING);
		feeChargeCapPending.setStatus(CommonStatus.valueOf(feeChargeCapUpdateResource.getStatus()));
		feeChargeCapPending
				.setFeeCapPeriodId(validationService.stringToLong(feeChargeCapUpdateResource.getFeeCapPeriodId()));
		feeChargeCapPending.setPeriodCode(periodResponse.getCode());
		feeChargeCapPending
				.setFeeOccurence(feeChargeCap.getFeeOccurence());
		feeChargeCapPending
				.setMinimumAmount(validationService.stringToBigDecimal(feeChargeCapUpdateResource.getMinimumAmount()));
		feeChargeCapPending.setMinMaxType(MinMaxType.valueOf(feeChargeCapUpdateResource.getMinMaxType()).toString());
		feeChargeCapPending.setCreatedDate(validationService.getCreateOrModifyDate());
		feeChargeCapPending.setCreatedUser(user);
		feeChargeCapPending.setSyncTs(validationService.getCreateOrModifyDate());

		return feeChargeCapPendingRepository.save(feeChargeCapPending);
	}

	private void updateFeeChargeCap(FeeChargeCapPending feeChargeCapPending, FeeChargeCap feeCap,
			CommonApproveStatus approveStatus, String user) {

		// insert into fee charge cap history
		feeChargeCapHistoryService.saveHistory(feeCap.getTenantId(), feeCap, user);

		FeeChargeCap feeChargeCap = feeCap;

		FeeChargeCapPending pendingFeeChargeCap = feeChargeCapPending;
		if (approveStatus.toString().equalsIgnoreCase(CommonApproveStatus.APPROVED.toString())) {
			pendingFeeChargeCap.setPenApprovedUser(user);
			pendingFeeChargeCap.setPenApprovedDate(validationService.getCreateOrModifyDate());

			feeChargeCap.setStatus(feeChargeCapPending.getStatus());
			feeChargeCap.setFeeCapPeriodId(feeChargeCapPending.getFeeCapPeriodId());
			feeChargeCap.setFeeOccurence(feeChargeCapPending.getFeeOccurence());
			feeChargeCap.setMinimumAmount(feeChargeCapPending.getMinimumAmount());
			feeChargeCap.setMinMaxType(feeChargeCapPending.getMinMaxType());
			feeChargeCap.setFeeCharge(feeChargeCapPending.getFeeCharge());
			feeChargeCap.setOtherFeeType(feeChargeCapPending.getOtherFeeType());
			feeChargeCap.setModifiedDate(feeChargeCapPending.getCreatedDate());
			feeChargeCap.setModifiedUser(feeChargeCapPending.getCreatedUser());
			feeChargeCapRepository.saveAndFlush(feeChargeCap);

		} else {
			pendingFeeChargeCap.setPenRejectedUser(user);
			pendingFeeChargeCap.setPenRejectedDate(validationService.getCreateOrModifyDate());
		}
		pendingFeeChargeCap.setSyncTs(validationService.getCreateOrModifyDate());
		feeChargeCapPendingRepository.save(pendingFeeChargeCap);

	}

	@Override
	public boolean approveReject(String tenantId, Long id, WorkflowStatus workflowStatus) {
		WorkflowInitiationRequestResource workflowInitiationRequestResource = new WorkflowInitiationRequestResource();
		workflowInitiationRequestResource.setApprovalUser(DEFAULT_APPROVAL_USER);
		workflowInitiationRequestResource.setApprovWorkflowType(WorkflowType.FEE_CHARGE_TEMP_APPROVAL);
		WorkflowRulesResource workflowRulesResource = null;
		WorkflowType workflowType = WorkflowType.FEE_CHARGE_TEMP_APPROVAL;
		String user = LogginAuthentcation.getInstance().getUserName();
		CommonApproveStatus approveStatus = null;

		Optional<FeeChargeCapPending> isPresentFeeChargeCapPending = feeChargeCapPendingRepository.findById(id);

		Optional<FeeChargeCap> feeChargeCap = feeChargeCapRepository
				.findById(isPresentFeeChargeCapPending.get().getFeeChargeCap().getId());

//		if (!isPresentFeeChargeCapPending.get().getFeeCharge().getApproveStatus().equals(CommonApproveStatus.PENDING))
//			throw new ValidateRecordException(environment.getProperty("common.can-not-rej-app"), "message");

		Optional<LendingWorkflow> lendingWorkflow = lendingWorkflowRepository
				.findById(isPresentFeeChargeCapPending.get().getLendingWorkflow().getId());

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

		updateFeeChargeCap(isPresentFeeChargeCapPending.get(), feeChargeCap.get(), approveStatus, user);

		return true;
	}

	@Override
	public Optional<FeeChargeCapPending> getByPendingId(Long pendingId) {
		Optional<FeeChargeCapPending> isPresentFeeChargeCapPending = feeChargeCapPendingRepository.findById(pendingId);
		if (isPresentFeeChargeCapPending.isPresent()) {
			return Optional.ofNullable(isPresentFeeChargeCapPending.get());
		} else {
			return Optional.empty();
		}
	}

	@Override
	public Page<FeeChargeCapPending> searchFeeChargeCap(String searchq, String status, String approveStatus,
			Pageable pageable) {
		if(searchq==null || searchq.isEmpty())
			searchq=null;
		if(status==null || status.isEmpty())
			status=null;
		if(approveStatus==null || approveStatus.isEmpty())
			approveStatus=null;
		return feeChargeCapPendingRepository.searchFeeChargeCapPending(searchq, status, approveStatus, pageable);
	}

	@Override
	public List<FeeChargeCap> getByOtherFeeType(String name) {
		
		List<FeeChargeCap> allFeeChargeCaps = new ArrayList<>();
		List<OtherFeeType> otherFeeTypes = otherFeeTypeRepository.findByNameContaining(name);
		for(OtherFeeType otherFeeType : otherFeeTypes) {
			List<FeeChargeCap> feeChargeCaps = feeChargeCapRepository.findByOtherFeeType(otherFeeType);
			for(FeeChargeCap feeChargeCap : feeChargeCaps) {
				allFeeChargeCaps.add(feeChargeCap);
			}
		}
		return allFeeChargeCaps;
	}

	@Override
	public List<FeeChargeCap> getByFeeCharge(String name) {
		List<FeeChargeCap> allFeeChargeCaps = new ArrayList<>();
		List<FeeCharge> feeCharges = feeChargeRepository.findByNameContaining(name);
		for(FeeCharge feeCharge : feeCharges) {
			List<FeeChargeCap> feeChargeCaps = feeChargeCapRepository.findByFeeCharge(feeCharge);
			for(FeeChargeCap feeChargeCap : feeChargeCaps) {
				allFeeChargeCaps.add(feeChargeCap);
			}
		}
		return allFeeChargeCaps;
	}

	@Override
	public void approvalPendingFeeChargeCap(Long feeChargePendingId, CommonApproveStatus approveStatus) {

		Optional<FeeChargePending> feeChargePending = feeChargePendingRepository.findById(feeChargePendingId);

		if (!feeChargePending.isPresent()) {
			throw new ValidateRecordException(environment.getProperty("feeCharge-pending-not-found"), MESSAGE);
		}

		List<FeeChargeCapPending> feeChargeCapPendingList = feeChargeCapPendingRepository.findAllByFeeChargePending(feeChargePending.get());

		if ((feeChargeCapPendingList != null) && (!feeChargeCapPendingList.isEmpty())) {
			for (FeeChargeCapPending item : feeChargeCapPendingList) {
				updateFeeChargeCap(item, approveStatus);
			}
		}
	}
	
	
	private void updateFeeChargeCap(FeeChargeCapPending feeChargeCapPending, CommonApproveStatus approveStatus) {

		// insert into fee charge cap history
		feeChargeCapHistoryService.saveHistory(feeChargeCapPending.getTenantId(), feeChargeCapPending.getFeeChargeCap(), LogginAuthentcation.getInstance().getUserName());

		FeeChargeCap feeChargeCap = feeChargeCapPending.getFeeChargeCap();

		FeeChargeCapPending pendingFeeChargeCap = feeChargeCapPending;
		if (approveStatus.toString().equalsIgnoreCase(CommonApproveStatus.APPROVED.toString())) {
			pendingFeeChargeCap.setPenApprovedUser(LogginAuthentcation.getInstance().getUserName());
			pendingFeeChargeCap.setPenApprovedDate(validationService.getCreateOrModifyDate());

			feeChargeCap.setStatus(feeChargeCapPending.getStatus());
			feeChargeCap.setFeeCapPeriodId(feeChargeCapPending.getFeeCapPeriodId());
			feeChargeCap.setFeeOccurence(feeChargeCapPending.getFeeOccurence());
			feeChargeCap.setMinimumAmount(feeChargeCapPending.getMinimumAmount());
			feeChargeCap.setMinMaxType(feeChargeCapPending.getMinMaxType());
			feeChargeCap.setFeeCharge(feeChargeCapPending.getFeeCharge());
			feeChargeCap.setOtherFeeType(feeChargeCapPending.getOtherFeeType());
			feeChargeCap.setModifiedDate(feeChargeCapPending.getCreatedDate());
			feeChargeCap.setModifiedUser(feeChargeCapPending.getCreatedUser());
			feeChargeCapRepository.saveAndFlush(feeChargeCap);

		} else {
			pendingFeeChargeCap.setPenRejectedUser(LogginAuthentcation.getInstance().getUserName());
			pendingFeeChargeCap.setPenRejectedDate(validationService.getCreateOrModifyDate());
		}
		pendingFeeChargeCap.setSyncTs(validationService.getCreateOrModifyDate());
		feeChargeCapPendingRepository.save(pendingFeeChargeCap);

	}

}
