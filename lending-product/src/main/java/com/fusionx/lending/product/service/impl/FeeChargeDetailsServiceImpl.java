package com.fusionx.lending.product.service.impl;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.core.LoggerRequest;
import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.ApplicationFrequency;
import com.fusionx.lending.product.domain.CalculationFrequency;
import com.fusionx.lending.product.domain.CommonListItem;
import com.fusionx.lending.product.domain.FeeCharge;
import com.fusionx.lending.product.domain.FeeChargeCap;
import com.fusionx.lending.product.domain.FeeChargeCapPending;
import com.fusionx.lending.product.domain.FeeChargeDetailOptionalPending;
import com.fusionx.lending.product.domain.FeeChargeDetails;
import com.fusionx.lending.product.domain.FeeChargeDetailsPending;
import com.fusionx.lending.product.domain.FeeChargePending;
import com.fusionx.lending.product.domain.LendingWorkflow;
import com.fusionx.lending.product.domain.OtherFeeRateType;
import com.fusionx.lending.product.domain.OtherFeeType;
import com.fusionx.lending.product.domain.PenalInterestPending;
import com.fusionx.lending.product.enums.CommonApproveStatus;
import com.fusionx.lending.product.enums.CommonListCode;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.enums.EntityPoint;
import com.fusionx.lending.product.enums.FeeChargeDetailsType;
import com.fusionx.lending.product.enums.MinMaxType;
import com.fusionx.lending.product.enums.ServiceEntity;
import com.fusionx.lending.product.enums.WorkflowStatus;
import com.fusionx.lending.product.enums.WorkflowType;
import com.fusionx.lending.product.exception.InvalidServiceIdException;
import com.fusionx.lending.product.exception.ValidateRecordException;
import com.fusionx.lending.product.repository.ApplicationFrequencyRepository;
import com.fusionx.lending.product.repository.CalculationFrequencyRepository;
import com.fusionx.lending.product.repository.CommonListItemRepository;
import com.fusionx.lending.product.repository.FeeChargeCapPendingRepository;
import com.fusionx.lending.product.repository.FeeChargeDetailsPendingRepository;
import com.fusionx.lending.product.repository.FeeChargeDetailsRepository;
import com.fusionx.lending.product.repository.FeeChargePendingRepository;
import com.fusionx.lending.product.repository.FeeChargeRepository;
import com.fusionx.lending.product.repository.LendingWorkflowRepository;
import com.fusionx.lending.product.repository.OtherFeeRateTypeRepository;
import com.fusionx.lending.product.repository.OtherFeeTypeRepository;
import com.fusionx.lending.product.resources.FeeChargeDetailsAddResource;
import com.fusionx.lending.product.resources.FeeChargeDetailsUpdateResource;
import com.fusionx.lending.product.resources.WorkflowInitiationRequestResource;
import com.fusionx.lending.product.resources.WorkflowRulesResource;
import com.fusionx.lending.product.service.FeeChargeCapHistoryService;
import com.fusionx.lending.product.service.FeeChargeDetailsHistoryService;
import com.fusionx.lending.product.service.FeeChargeDetailsService;
import com.fusionx.lending.product.service.LendingWorkflowService;
import com.fusionx.lending.product.service.ValidationService;

/**
 * Fee Charge Details Service Impl
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   27-07-2021             				Dilhan        Created
 *    
 ********************************************************************************************************
 */

@Component
@Transactional(rollbackFor = Exception.class)
public class FeeChargeDetailsServiceImpl extends MessagePropertyBase implements FeeChargeDetailsService {

	@Autowired
	private FeeChargeDetailsRepository feeChargeDetailsRepository;

	@Autowired
	private FeeChargeRepository feeChargeRepository;

	@Autowired
	private OtherFeeTypeRepository otherFeeTypeRepository;

	@Autowired
	private OtherFeeRateTypeRepository otherFeeRateTypeRepository;

	@Autowired
	private FeeChargePendingRepository feeChargePendingRepository;

	@Autowired
	private FeeChargeDetailsPendingRepository feeChargeDetailsPendingRepository;

	@Autowired
	private CalculationFrequencyRepository calculationFrequencyRepository;

	@Autowired
	private ApplicationFrequencyRepository applicationFrequencyRepository;

	@Autowired
	private CommonListItemRepository commonListItemRepository;

	@Autowired
	private ValidationService validationService;

	@Autowired
	private LendingWorkflowService lendingWorkflowService;

	@Autowired
	private FeeChargeDetailsHistoryService feeChargeDetailsHistoryService;

	@Autowired
	private LendingWorkflowRepository lendingWorkflowRepository;

	private static final String DEFAULT_APPROVAL_USER = "kie-server";

	private static final String DOMAIN_PATH = "com.fusionx.LendingWF";

	private static final String DOMAIN = "LendingWF";

	@Override
	public List<FeeChargeDetails> getAll() {
		return feeChargeDetailsRepository.findAll();
	}

	@Override
	public Optional<FeeChargeDetails> getById(Long id) {
		Optional<FeeChargeDetails> isPresentFeeChargeDetails = feeChargeDetailsRepository.findById(id);
		if (isPresentFeeChargeDetails.isPresent()) {
			return Optional.ofNullable(isPresentFeeChargeDetails.get());
		} else {
			return Optional.empty();
		}
	}

	@Override
	public Optional<FeeChargeDetails> getByCode(String code) {
		Optional<FeeChargeDetails> isPresentFeeChargeDetails = feeChargeDetailsRepository.findByCode(code);
		if (isPresentFeeChargeDetails.isPresent()) {
			return Optional.ofNullable(isPresentFeeChargeDetails.get());
		} else {
			return Optional.empty();
		}
	}

	@Override
	public List<FeeChargeDetails> getByStatus(String status) {
		return feeChargeDetailsRepository.findByStatus(CommonStatus.valueOf(status));
	}

	@Override
	public FeeChargeDetails addFeeChargeDetails(String tenantId,
			FeeChargeDetailsAddResource feeChargeDetailsAddResource) {
		
		FeeChargeDetails feeChargeDetails = new FeeChargeDetails();
		Optional<FeeChargeDetails> isPresentFeeChargeDetails = feeChargeDetailsRepository
				.findByCode(feeChargeDetailsAddResource.getCode());

		if (isPresentFeeChargeDetails.isPresent())
			throw new InvalidServiceIdException(environment.getProperty("common.duplicate"), ServiceEntity.CODE,
					EntityPoint.FEE_CHARGE_DETAILS);

		Optional<ApplicationFrequency> isApplicationFrequency = applicationFrequencyRepository
				.findByIdAndStatus(validationService.stringToLong(feeChargeDetailsAddResource.getApplicationFrequencyId()),CommonStatus.ACTIVE);
		if (isApplicationFrequency.isPresent()) {
			if (!isApplicationFrequency.get().getName()
					.equalsIgnoreCase(feeChargeDetailsAddResource.getApplicationFrequencyName()))
				throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE),
						"applicationFrequencyName");

		} else {
			throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), "applicationFrequencyId");
		}

		Optional<FeeCharge> isFeeCharge = feeChargeRepository
				.findByIdAndStatus(validationService.stringToLong(feeChargeDetailsAddResource.getFeeChargeId()),CommonStatus.ACTIVE);
		if (isFeeCharge.isPresent()) {
			if (!isFeeCharge.get().getName().equalsIgnoreCase(feeChargeDetailsAddResource.getFeeChargeName()))
				throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), "feeChargeName");

		} else {
			throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), "feeChargeId");
		}

		Optional<CalculationFrequency> isCalculationFrequency = calculationFrequencyRepository
				.findByIdAndStatus(validationService.stringToLong(feeChargeDetailsAddResource.getCalculationFrequencyId()),CommonStatus.ACTIVE);
		if (isCalculationFrequency.isPresent()) {
			if (!isCalculationFrequency.get().getName()
					.equalsIgnoreCase(feeChargeDetailsAddResource.getCalculationFrequencyName()))
				throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE),
						"calculationFrequencyName");

		} else {
			throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), "calculationFrequencyId");
		}

		Optional<OtherFeeType> isFeeType = otherFeeTypeRepository
				.findByIdAndStatus(validationService.stringToLong(feeChargeDetailsAddResource.getFeeTypeId()), CommonStatus.ACTIVE);
		if (isFeeType.isPresent()) {
			if (!isFeeType.get().getName().equalsIgnoreCase(feeChargeDetailsAddResource.getFeeTypeName()))
				throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), "feeTypeName");

		} else {
			throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), "feeTypeId");
		}

		CommonListItem feeCategory = validateFeeCategory(
				validationService.stringToLong(feeChargeDetailsAddResource.getFeeCategoryId()),
				feeChargeDetailsAddResource.getFeeCategoryName());
		
		if(validationService.stringToBigDecimal(feeChargeDetailsAddResource.getMinAmount()).compareTo(validationService.stringToBigDecimal(feeChargeDetailsAddResource.getMaxAmount())) >= 0) {
    		throw new ValidateRecordException(environment.getProperty("feeChargeDetails.minimum-maximum"), "message");
    	}
		
		if(FeeChargeDetailsType.Amount.equals(FeeChargeDetailsType.valueOf(feeChargeDetailsAddResource.getType()))) {
			if(feeChargeDetailsAddResource.getAmount() == null || feeChargeDetailsAddResource.getAmount().isEmpty()) {
				throw new ValidateRecordException(environment.getProperty(COMMON_NOT_NULL), "amount");
			}else {
				feeChargeDetails.setAmount(validationService.stringToBigDecimal(feeChargeDetailsAddResource.getAmount()));
			}
		}
		
		if(FeeChargeDetailsType.Rate.equals(FeeChargeDetailsType.valueOf(feeChargeDetailsAddResource.getType()))) {
			if(feeChargeDetailsAddResource.getRateTypeId() == null || feeChargeDetailsAddResource.getRateTypeId().isEmpty()) {
				throw new ValidateRecordException(environment.getProperty(COMMON_NOT_NULL), "rateTypeId");
			}
			else if(feeChargeDetailsAddResource.getRateTypeName() == null|| feeChargeDetailsAddResource.getRateTypeName().isEmpty()) {
				throw new ValidateRecordException(environment.getProperty(COMMON_NOT_NULL), "rateTypeName");
			}
			else if(feeChargeDetailsAddResource.getRate() == null|| feeChargeDetailsAddResource.getRate().isEmpty()) {
				throw new ValidateRecordException(environment.getProperty(COMMON_NOT_NULL), "rate");
			}
			else {
				
				Optional<OtherFeeRateType> isOtherFeeRateType = otherFeeRateTypeRepository
						.findByIdAndStatus(validationService.stringToLong(feeChargeDetailsAddResource.getRateTypeId()), CommonStatus.ACTIVE);
				
				if (isOtherFeeRateType.isPresent()) {
					if (!isOtherFeeRateType.get().getName().equalsIgnoreCase(feeChargeDetailsAddResource.getRateTypeName())) {
						throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), "rateTypeName");
					}else {
						feeChargeDetails.setRateType(isOtherFeeRateType.get());
						feeChargeDetails.setRate(validationService.stringToBigDecimal(feeChargeDetailsAddResource.getRate()));
					}
						
				} else {
					throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), "rateTypeId");
				}
			}
		}
		Date effectiveDate = validationServiceStringToDate(feeChargeDetailsAddResource.getEffectiveDate(),environment.getProperty("common-effective-date.invalid"));
		
		List<FeeChargeDetails> feeChargeDetailsAvailability = feeChargeDetailsRepository.findByFeeChargeAndOtherFeeTypeAndApplicationFrequencyAndCalculationFrequencyAndTypeAndStatusAndEffectiveDate(isFeeCharge.get(),isFeeType.get(),isApplicationFrequency.get(),
				isCalculationFrequency.get(),FeeChargeDetailsType.valueOf(feeChargeDetailsAddResource.getType()).toString(), CommonStatus.ACTIVE,dateToTimeStamp(effectiveDate));
		for(FeeChargeDetails item : feeChargeDetailsAvailability) {
			
			if(validationService.stringToBigDecimal(feeChargeDetailsAddResource.getMinAmount()).compareTo(item.getMinAmount()) == 0 
	    			&& validationService.stringToBigDecimal(feeChargeDetailsAddResource.getMaxAmount()).compareTo(item.getMaxAmount()) ==0 ) {
				throw new ValidateRecordException(environment.getProperty("minMaxFeeType.can-not-duplicate"), "message");
	    	}
			
			if(validationService.stringToBigDecimal(feeChargeDetailsAddResource.getMinAmount()).compareTo(item.getMinAmount()) >= 0 
        			&& validationService.stringToBigDecimal(feeChargeDetailsAddResource.getMinAmount()).compareTo(item.getMaxAmount()) <=0) {
        		throw new ValidateRecordException(environment.getProperty("minimum.amount-can-not-update"), "message");
        	}
        	if(validationService.stringToBigDecimal(feeChargeDetailsAddResource.getMaxAmount()).compareTo(item.getMinAmount()) >= 0 
        			&& validationService.stringToBigDecimal(feeChargeDetailsAddResource.getMaxAmount()).compareTo(item.getMaxAmount()) <=0) {
        		throw new ValidateRecordException(environment.getProperty("maximum.amount-can-not-update"), "message");
        	}
		}
		
		
		feeChargeDetails.setTenantId(tenantId);
		feeChargeDetails.setCode(feeChargeDetailsAddResource.getCode());
		
		feeChargeDetails.setType(FeeChargeDetailsType.valueOf(feeChargeDetailsAddResource.getType()).toString());
		feeChargeDetails.setFeeCharge(isFeeCharge.get());
		feeChargeDetails.setEffectiveDate(dateToTimeStamp(effectiveDate));
		feeChargeDetails.setCalculationFrequency(isCalculationFrequency.get());
		feeChargeDetails.setApplicationFrequency(isApplicationFrequency.get());
		feeChargeDetails.setOtherFeeType(isFeeType.get());
		feeChargeDetails.setFeeCategory(feeCategory);
		feeChargeDetails.setIsNegotiable(feeChargeDetailsAddResource.getIsNegotiable());

		feeChargeDetails.setMaxAmount(validationService.stringToBigDecimal(feeChargeDetailsAddResource.getMaxAmount()));
		feeChargeDetails.setMinAmount(validationService.stringToBigDecimal(feeChargeDetailsAddResource.getMinAmount()));

		feeChargeDetails.setStatus(CommonStatus.valueOf(feeChargeDetailsAddResource.getStatus()));
		feeChargeDetails.setCreatedDate(validationService.getCreateOrModifyDate());
		feeChargeDetails.setSyncTs(validationService.getCreateOrModifyDate());
		feeChargeDetails.setCreatedUser(LogginAuthentcation.getInstance().getUserName());

		return feeChargeDetailsRepository.save(feeChargeDetails);
	}

	private CommonListItem validateFeeCategory(Long id, String name) {

		Optional<CommonListItem> commonListItem = commonListItemRepository.findByIdAndNameAndStatus(id, name,
				CommonStatus.ACTIVE);

		if (!commonListItem.isPresent())
			throw new InvalidServiceIdException(environment.getProperty(COMMON_INVALID_VALUE),
					ServiceEntity.FEE_CATEGORY_ID, EntityPoint.FEE_CHARGE_DETAILS);
		if (!commonListItem.get().getReferenceCode().toString()
				.equalsIgnoreCase(CommonListCode.FEE_CATEGORY.toString()))
			throw new InvalidServiceIdException(environment.getProperty(COMMON_INVALID_VALUE),
					ServiceEntity.FEE_CATEGORY_ID, EntityPoint.FEE_CHARGE_DETAILS);
		return commonListItem.get();
	}

	@Override
	public FeeChargeDetails updateFeeChargeDetails(String tenantId, Long id,
			FeeChargeDetailsUpdateResource feeChargeDetailsUpdateResource) {

		Optional<FeeChargeDetails> isPresentFeeChargeDetails = feeChargeDetailsRepository.findById(id);

		if(isPresentFeeChargeDetails.isPresent()) {
			if (!isPresentFeeChargeDetails.get().getVersion()
					.equals(Long.parseLong(feeChargeDetailsUpdateResource.getVersion())))
				throw new InvalidServiceIdException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.VERSION,
						EntityPoint.FEE_CHARGE_DETAILS);

		}
		
		if (!isPresentFeeChargeDetails.get().getCode().equalsIgnoreCase(feeChargeDetailsUpdateResource.getCode()))
			throw new InvalidServiceIdException(environment.getProperty(COMMON_CODE_UPDATE), ServiceEntity.CODE,
					EntityPoint.FEE_CHARGE_DETAILS);
		
		if (feeChargeDetailsUpdateResource.getFeeChargePendingId() != null && !feeChargeDetailsUpdateResource.getFeeChargePendingId().isEmpty()) {
			Optional<FeeChargePending> isPresentFeeChargePending = feeChargePendingRepository
					.findByIdAndStatus(
							validationService.stringToLong(feeChargeDetailsUpdateResource.getFeeChargePendingId()),
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
				isPresentFeeChargeDetails.get(), feeChargeDetailsUpdateResource);

		return isPresentFeeChargeDetails.get();
	}

	private void approveOrGenerateWorkFlow(String tenantId, String user, FeeChargeDetails feeChargeDetails,
			FeeChargeDetailsUpdateResource feeChargeDetailsUpdateResource) {

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
				savePendingFeeChargeDetails(tenantId, feeChargeDetails, feeChargeDetailsUpdateResource, lendingWorkflow,
						user);
			}
		} else {
			FeeChargeDetailsPending feeChargeDetailsPending = savePendingFeeChargeDetails(tenantId, feeChargeDetails,
					feeChargeDetailsUpdateResource, lendingWorkflow, user);
			updateFeeChargeDetails(feeChargeDetailsPending, feeChargeDetails, CommonApproveStatus.APPROVED, user);
		}
	}

	private FeeChargeDetailsPending savePendingFeeChargeDetails(String tenantId, FeeChargeDetails feeChargeDetails,
			FeeChargeDetailsUpdateResource feeChargeDetailsUpdateResource, LendingWorkflow lendingWorkflow,
			String user) {

		FeeChargeDetailsPending feeChargeDetailsPending = new FeeChargeDetailsPending();
		Optional<ApplicationFrequency> isApplicationFrequency = applicationFrequencyRepository
				.findByIdAndStatus(validationService.stringToLong(feeChargeDetailsUpdateResource.getApplicationFrequencyId()),CommonStatus.ACTIVE);
		if (isApplicationFrequency.isPresent()) {
			if (!isApplicationFrequency.get().getName()
					.equalsIgnoreCase(feeChargeDetailsUpdateResource.getApplicationFrequencyName()))
				throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE),
						"applicationFrequencyName");

		} else {
			throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), "applicationFrequencyId");
		}

		Optional<CalculationFrequency> isCalculationFrequency = calculationFrequencyRepository
				.findByIdAndStatus(validationService.stringToLong(feeChargeDetailsUpdateResource.getCalculationFrequencyId()), CommonStatus.ACTIVE);
		if (isCalculationFrequency.isPresent()) {
			if (!isCalculationFrequency.get().getName()
					.equalsIgnoreCase(feeChargeDetailsUpdateResource.getCalculationFrequencyName()))
				throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE),
						"calculationFrequencyName");

		} else {
			throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), "calculationFrequencyId");
		}
		
		if(validationService.stringToBigDecimal(feeChargeDetailsUpdateResource.getMinAmount()).compareTo(validationService.stringToBigDecimal(feeChargeDetailsUpdateResource.getMaxAmount())) >= 0) {
    		throw new ValidateRecordException(environment.getProperty("feeChargeDetails.minimum-maximum"), "message");
    	}

		Optional<OtherFeeType> isFeeType = otherFeeTypeRepository
				.findByIdAndStatus(validationService.stringToLong(feeChargeDetailsUpdateResource.getFeeTypeId()), CommonStatus.ACTIVE);
		if (isFeeType.isPresent()) {
			if (!isFeeType.get().getName().equalsIgnoreCase(feeChargeDetailsUpdateResource.getFeeTypeName()))
				throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), "feeTypeName");

		} else {
			throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), "feeTypeId");
		}

		CommonListItem feeCategory = validateFeeCategory(
				validationService.stringToLong(feeChargeDetailsUpdateResource.getFeeCategoryId()),
				feeChargeDetailsUpdateResource.getFeeCategoryName());

		//insert into fee charge pending
		if(!feeChargeDetails.getFeeCharge().getId().equals(validationService.stringToLong(feeChargeDetailsUpdateResource.getFeeChargeId())) || !feeChargeDetails.getFeeCharge().getName().equalsIgnoreCase(feeChargeDetailsUpdateResource.getFeeChargeName())) {
			throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), "feeChargeId");
		}
		
		
		if(FeeChargeDetailsType.Amount.equals(FeeChargeDetailsType.valueOf(feeChargeDetailsUpdateResource.getType()))) {
			if(feeChargeDetailsUpdateResource.getAmount() == null || feeChargeDetailsUpdateResource.getAmount().isEmpty()) {
				throw new ValidateRecordException(environment.getProperty(COMMON_NOT_NULL), "amount");
			}else {
				feeChargeDetailsPending.setAmount(validationService.stringToBigDecimal(feeChargeDetailsUpdateResource.getAmount()));
			}
		}
		
		if(FeeChargeDetailsType.Rate.equals(FeeChargeDetailsType.valueOf(feeChargeDetailsUpdateResource.getType()))) {
			if(feeChargeDetailsUpdateResource.getRateTypeId() == null || feeChargeDetailsUpdateResource.getRateTypeId().isEmpty()) {
				throw new ValidateRecordException(environment.getProperty(COMMON_NOT_NULL), "rateType");
			}
			else if(feeChargeDetailsUpdateResource.getRateTypeName() == null|| feeChargeDetailsUpdateResource.getRateTypeName().isEmpty()) {
				throw new ValidateRecordException(environment.getProperty(COMMON_NOT_NULL), "rateTypeName");
			}
			else if(feeChargeDetailsUpdateResource.getRate() == null|| feeChargeDetailsUpdateResource.getRate().isEmpty()) {
				throw new ValidateRecordException(environment.getProperty(COMMON_NOT_NULL), "rate");
			}
			else {
				
				Optional<OtherFeeRateType> isOtherFeeRateType = otherFeeRateTypeRepository
						.findByIdAndStatus(validationService.stringToLong(feeChargeDetailsUpdateResource.getRateTypeId()), CommonStatus.ACTIVE);
				
				if (isOtherFeeRateType.isPresent()) {
					if (!isOtherFeeRateType.get().getName().equalsIgnoreCase(feeChargeDetailsUpdateResource.getRateTypeName())) {
						throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), "rateTypeName");
					}else {
						feeChargeDetailsPending.setRateType(isOtherFeeRateType.get());
						feeChargeDetailsPending.setRate(validationService.stringToBigDecimal(feeChargeDetailsUpdateResource.getRate()));
					}
						
				} else {
					throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), "rateTypeId");
				}
			}
		}

		Date effectiveDate = validationServiceStringToDate(feeChargeDetailsUpdateResource.getEffectiveDate(),environment.getProperty("common-effective-date.invalid"));
		
		List<FeeChargeDetails> feeChargeDetailsAvailability = feeChargeDetailsRepository.findByFeeChargeAndOtherFeeTypeAndApplicationFrequencyAndCalculationFrequencyAndTypeAndStatusAndEffectiveDateAndIdNot(feeChargeDetails.getFeeCharge(),isFeeType.get(),isApplicationFrequency.get(),
				isCalculationFrequency.get(),FeeChargeDetailsType.valueOf(feeChargeDetailsUpdateResource.getType()).toString(), CommonStatus.ACTIVE,dateToTimeStamp(effectiveDate), feeChargeDetails.getId());
		for(FeeChargeDetails item : feeChargeDetailsAvailability) {
			
			if(validationService.stringToBigDecimal(feeChargeDetailsUpdateResource.getMinAmount()).compareTo(item.getMinAmount()) == 0 
	    			&& validationService.stringToBigDecimal(feeChargeDetailsUpdateResource.getMaxAmount()).compareTo(item.getMaxAmount()) ==0 ) {
				throw new ValidateRecordException(environment.getProperty("minMaxFeeType.can-not-duplicate"), "message");
	    	}
			
			if(validationService.stringToBigDecimal(feeChargeDetailsUpdateResource.getMinAmount()).compareTo(item.getMinAmount()) >= 0 
        			&& validationService.stringToBigDecimal(feeChargeDetailsUpdateResource.getMinAmount()).compareTo(item.getMaxAmount()) <=0) {
        		throw new ValidateRecordException(environment.getProperty("minimum.amount-can-not-update"), "message");
        	}
        	if(validationService.stringToBigDecimal(feeChargeDetailsUpdateResource.getMaxAmount()).compareTo(item.getMinAmount()) >= 0 
        			&& validationService.stringToBigDecimal(feeChargeDetailsUpdateResource.getMaxAmount()).compareTo(item.getMaxAmount()) <=0) {
        		throw new ValidateRecordException(environment.getProperty("maximum.amount-can-not-update"), "message");
        	}
			
			
		}

		FeeChargePending feeChargePending = new FeeChargePending();
		feeChargePending.setTenantId(tenantId);
		if (lendingWorkflow != null)
			feeChargePending.setLendingWorkflow(lendingWorkflow);
		feeChargePending.setFeeCharge(feeChargeDetails.getFeeCharge());
		feeChargePending.setCode(feeChargeDetails.getFeeCharge().getCode());
		feeChargePending.setName(feeChargeDetails.getFeeCharge().getName());
		feeChargePending.setFeeChargeType(feeChargeDetails.getFeeCharge().getFeeChargeType());
		feeChargePending.setStatus(feeChargeDetails.getFeeCharge().getStatus());
		feeChargePending.setPcreatedDate(validationService.getCreateOrModifyDate());
		feeChargePending.setPcreatedUser(user);
		feeChargePending.setSyncTs(validationService.getCreateOrModifyDate());
		feeChargePending = feeChargePendingRepository.save(feeChargePending);
		
		Optional<FeeCharge> feeCharge = feeChargeRepository.findById(feeChargePending.getFeeCharge().getId());
//		feeCharge.get().setApproveStatus(CommonApproveStatus.PENDING);
		feeChargeRepository.save(feeCharge.get());

		// insert into fee charge details pending
		feeChargeDetailsPending.setTenantId(tenantId);
		feeChargeDetailsPending.setFeeChargePending(feeChargePending);
		feeChargeDetailsPending.setFeeChargeDetails(feeChargeDetails);
		feeChargeDetailsPending.setType(FeeChargeDetailsType.valueOf(feeChargeDetailsUpdateResource.getType()).toString());
		feeChargeDetailsPending.setCalculationFrequency(isCalculationFrequency.get());
		feeChargeDetailsPending.setApplicationFrequency(isApplicationFrequency.get());
		feeChargeDetailsPending.setOtherFeeType(isFeeType.get());
		feeChargeDetailsPending.setFeeCategory(feeCategory);
		feeChargeDetailsPending.setIsNegotiable(feeChargeDetailsUpdateResource.getIsNegotiable());
		feeChargeDetailsPending.setEffectiveDate(dateToTimeStamp(effectiveDate));

		feeChargeDetailsPending
				.setMaxAmount(validationService.stringToBigDecimal(feeChargeDetailsUpdateResource.getMaxAmount()));
		feeChargeDetailsPending
				.setMinAmount(validationService.stringToBigDecimal(feeChargeDetailsUpdateResource.getMinAmount()));
		// remove code from the table
		feeChargeDetailsPending.setStatus(CommonStatus.valueOf(feeChargeDetailsUpdateResource.getStatus()));
		feeChargeDetailsPending.setCreatedDate(validationService.getCreateOrModifyDate());
		feeChargeDetailsPending.setSyncTs(validationService.getCreateOrModifyDate());
		feeChargeDetailsPending.setCreatedUser(LogginAuthentcation.getInstance().getUserName());

		return feeChargeDetailsPendingRepository.save(feeChargeDetailsPending);

	}

	private void updateFeeChargeDetails(FeeChargeDetailsPending feeChargeDetailsPending,
			FeeChargeDetails feeChrgeDetail, CommonApproveStatus approveStatus, String user) {

		// insert into fee charge details history
		feeChargeDetailsHistoryService.saveHistory(feeChrgeDetail.getTenantId(), feeChrgeDetail, user);

		FeeChargeDetails feeChargeDetails = feeChrgeDetail;

		FeeChargeDetailsPending pendingFeeChargeDetails = feeChargeDetailsPending;
		if (approveStatus.toString().equalsIgnoreCase(CommonApproveStatus.APPROVED.toString())) {
//			pendingFeeChargeCap.setPenApprovedUser(user);
//			pendingFeeChargeCap.setPenApprovedDate(validationService.getCreateOrModifyDate());

			feeChargeDetails.setAmount(pendingFeeChargeDetails.getAmount());
			feeChargeDetails.setRateType(pendingFeeChargeDetails.getRateType());
			feeChargeDetails.setType(pendingFeeChargeDetails.getType());
			feeChargeDetails.setCalculationFrequency(pendingFeeChargeDetails.getCalculationFrequency());
			feeChargeDetails.setApplicationFrequency(pendingFeeChargeDetails.getApplicationFrequency());
			feeChargeDetails.setOtherFeeType(pendingFeeChargeDetails.getOtherFeeType());
			feeChargeDetails.setFeeCategory(pendingFeeChargeDetails.getFeeCategory());
			feeChargeDetails.setIsNegotiable(pendingFeeChargeDetails.getIsNegotiable());
			feeChargeDetails.setEffectiveDate(pendingFeeChargeDetails.getEffectiveDate());

			feeChargeDetails.setMaxAmount(pendingFeeChargeDetails.getMaxAmount());
			feeChargeDetails.setMinAmount(pendingFeeChargeDetails.getMinAmount());
			feeChargeDetails.setStatus(pendingFeeChargeDetails.getStatus());
			feeChargeDetails.setRate(pendingFeeChargeDetails.getRate());
			feeChargeDetails.setRateType(pendingFeeChargeDetails.getRateType());
			feeChargeDetails.setModifiedDate(pendingFeeChargeDetails.getCreatedDate());
			feeChargeDetails.setModifiedUser(pendingFeeChargeDetails.getCreatedUser());

			feeChargeDetailsRepository.saveAndFlush(feeChargeDetails);

		} else {
//			pendingFeeChargeCap.setPenRejectedUser(user);
//			pendingFeeChargeCap.setPenRejectedDate(validationService.getCreateOrModifyDate());
		}
		pendingFeeChargeDetails.setSyncTs(validationService.getCreateOrModifyDate());
		feeChargeDetailsPendingRepository.save(pendingFeeChargeDetails);

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

		Optional<FeeChargeDetailsPending> isPresentFeeChargeDetailsPending = feeChargeDetailsPendingRepository
				.findById(id);
		Optional<FeeChargePending> isPresentFeeChargePending = feeChargePendingRepository
				.findById(isPresentFeeChargeDetailsPending.get().getFeeChargePending().getId());

		Optional<LendingWorkflow> lendingWorkflow = lendingWorkflowRepository
				.findById(isPresentFeeChargePending.get().getLendingWorkflow().getId());

		Optional<FeeChargeDetails> feeChargeDetails = feeChargeDetailsRepository
				.findById(isPresentFeeChargeDetailsPending.get().getFeeChargeDetails().getId());

		if (lendingWorkflow.get().getWorkflowStatus().equals(CommonApproveStatus.ACTIVE))
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

		updateFeeChargeDetails(isPresentFeeChargeDetailsPending.get(), feeChargeDetails.get(), approveStatus, user);

		return true;
	}

	@Override
	public Optional<FeeChargeDetailsPending> getByPendingId(Long pendingId) {
		Optional<FeeChargeDetailsPending> isPresentFeeChargeDetailsPending = feeChargeDetailsPendingRepository
				.findById(pendingId);
		if (isPresentFeeChargeDetailsPending.isPresent()) {
			return Optional.ofNullable(isPresentFeeChargeDetailsPending.get());
		} else {
			return Optional.empty();
		}
	}

	@Override
	public List<FeeChargeDetails> getByFeeCharge(String name) {
		
		List<FeeChargeDetails> allFeeChargeDetails = new ArrayList<>();
		List<FeeCharge> feeCharges = feeChargeRepository.findByNameContaining(name);
		for(FeeCharge feeCharge : feeCharges) {
			List<FeeChargeDetails> feeChargeDetails = feeChargeDetailsRepository.findByFeeCharge(feeCharge);
			for(FeeChargeDetails feeChargeDetail : feeChargeDetails) {
				allFeeChargeDetails.add(feeChargeDetail);
			}
		}
		return allFeeChargeDetails;
	}

	@Override
	public List<FeeChargeDetails> getByFeeType(String name) {
		
		List<FeeChargeDetails> allFeeChargeDetails = new ArrayList<>();
		List<OtherFeeType> otherFeeTypes = otherFeeTypeRepository.findByNameContaining(name);
		for(OtherFeeType otherFeeType : otherFeeTypes) {
			List<FeeChargeDetails> feeChargeDetails = feeChargeDetailsRepository.findByOtherFeeType(otherFeeType);
			for(FeeChargeDetails feeChargeDetail : feeChargeDetails) {
				allFeeChargeDetails.add(feeChargeDetail);
			}
		}
		return allFeeChargeDetails;
	}

	@Override
	public List<FeeChargeDetails> getByFeeCategory(String name) {
		
		List<FeeChargeDetails> allFeeChargeDetails = new ArrayList<>();
		 List<CommonListItem> commonListItems = commonListItemRepository.findByNameContaining(name);
		for(CommonListItem commonListItem : commonListItems) {
			List<FeeChargeDetails> feeChargeDetails = feeChargeDetailsRepository.findByFeeCategory(commonListItem);
			for(FeeChargeDetails feeChargeDetail : feeChargeDetails) {
				allFeeChargeDetails.add(feeChargeDetail);
			}
		}
		return allFeeChargeDetails;
	}
	
	public Timestamp dateToTimeStamp(Date dt) {
		if(dt != null) {
			return new Timestamp(dt.getTime());
		}else {
	        Calendar calendar = Calendar.getInstance();
	        java.util.Date now = calendar.getTime();
	        return new Timestamp(now.getTime());
		}
	}

	public Date validationServiceStringToDate(String date,String message){
		if( (!("").equals(date))&&(date != null)) {
	        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	        try {
	            return format.parse(date);
	        } catch (ParseException e) {
	        	throw new ValidateRecordException(message, MESSAGE);
	        }
		}else {
			throw new ValidateRecordException(message, MESSAGE);
		}
	}

	@Override
	public void approvalPendingFeeChargeDetails(Long feeChargePendingId, CommonApproveStatus approveStatus) {

		
        Optional<FeeChargePending> feeChargePending = feeChargePendingRepository.findById(feeChargePendingId);
		
		if(!feeChargePending.isPresent()) {
			throw new ValidateRecordException(environment.getProperty("feeCharge-pending-not-found"), MESSAGE);
		}
		
		List<FeeChargeDetailsPending> feeChargeDetailsPendingList = feeChargeDetailsPendingRepository.findAllByFeeChargePending(feeChargePending.get());
		
		if((feeChargeDetailsPendingList  != null) && (!feeChargeDetailsPendingList.isEmpty())) {
			for(FeeChargeDetailsPending item :feeChargeDetailsPendingList) {
				updateFeeChargeDetails(item, approveStatus);
			}
		}
		
	}
	
	private void updateFeeChargeDetails(FeeChargeDetailsPending feeChargeDetailsPending,CommonApproveStatus approveStatus) {

		// insert into fee charge details history
		feeChargeDetailsHistoryService.saveHistory(feeChargeDetailsPending.getTenantId(), feeChargeDetailsPending.getFeeChargeDetails(), LogginAuthentcation.getInstance().getUserName());

		FeeChargeDetails feeChargeDetails = feeChargeDetailsPending.getFeeChargeDetails();

		FeeChargeDetailsPending pendingFeeChargeDetails = feeChargeDetailsPending;
		if (approveStatus.toString().equalsIgnoreCase(CommonApproveStatus.APPROVED.toString())) {
//			pendingFeeChargeCap.setPenApprovedUser(user);
//			pendingFeeChargeCap.setPenApprovedDate(validationService.getCreateOrModifyDate());

			feeChargeDetails.setAmount(pendingFeeChargeDetails.getAmount());
			feeChargeDetails.setRateType(pendingFeeChargeDetails.getRateType());
			feeChargeDetails.setType(pendingFeeChargeDetails.getType());
			feeChargeDetails.setCalculationFrequency(pendingFeeChargeDetails.getCalculationFrequency());
			feeChargeDetails.setApplicationFrequency(pendingFeeChargeDetails.getApplicationFrequency());
			feeChargeDetails.setOtherFeeType(pendingFeeChargeDetails.getOtherFeeType());
			feeChargeDetails.setFeeCategory(pendingFeeChargeDetails.getFeeCategory());
			feeChargeDetails.setIsNegotiable(pendingFeeChargeDetails.getIsNegotiable());
			feeChargeDetails.setEffectiveDate(pendingFeeChargeDetails.getEffectiveDate());

			feeChargeDetails.setMaxAmount(pendingFeeChargeDetails.getMaxAmount());
			feeChargeDetails.setMinAmount(pendingFeeChargeDetails.getMinAmount());
			feeChargeDetails.setStatus(pendingFeeChargeDetails.getStatus());
			feeChargeDetails.setRate(pendingFeeChargeDetails.getRate());
			feeChargeDetails.setRateType(pendingFeeChargeDetails.getRateType());
			feeChargeDetails.setModifiedDate(pendingFeeChargeDetails.getCreatedDate());
			feeChargeDetails.setModifiedUser(pendingFeeChargeDetails.getCreatedUser());

			feeChargeDetailsRepository.saveAndFlush(feeChargeDetails);

		} else {
//			pendingFeeChargeCap.setPenRejectedUser(user);
//			pendingFeeChargeCap.setPenRejectedDate(validationService.getCreateOrModifyDate());
		}
		pendingFeeChargeDetails.setSyncTs(validationService.getCreateOrModifyDate());
		feeChargeDetailsPendingRepository.save(pendingFeeChargeDetails);

	}

}
