package com.fusionx.lending.product.service.impl;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.ApplicationFrequency;
import com.fusionx.lending.product.domain.CalculationFrequency;
import com.fusionx.lending.product.domain.CommonListItem;
import com.fusionx.lending.product.domain.FeeCharge;
import com.fusionx.lending.product.domain.FeeChargeDetailOptional;
import com.fusionx.lending.product.domain.FeeChargeDetailOptionalHistory;
import com.fusionx.lending.product.domain.FeeChargeDetailOptionalOption;
import com.fusionx.lending.product.domain.FeeChargeDetailOptionalOptionHistory;
import com.fusionx.lending.product.domain.FeeChargeDetailOptionalOptionPending;
import com.fusionx.lending.product.domain.FeeChargeDetailOptionalPending;
import com.fusionx.lending.product.domain.FeeChargePending;
import com.fusionx.lending.product.domain.LendingWorkflow;
import com.fusionx.lending.product.domain.OtherFeeType;
import com.fusionx.lending.product.enums.CommonApproveStatus;
import com.fusionx.lending.product.enums.CommonListCode;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.enums.FeeChargeDetailsType;
import com.fusionx.lending.product.enums.WorkflowStatus;
import com.fusionx.lending.product.enums.WorkflowType;
import com.fusionx.lending.product.enums.YesNo;
import com.fusionx.lending.product.exception.ValidateRecordException;
import com.fusionx.lending.product.repository.ApplicationFrequencyRepository;
import com.fusionx.lending.product.repository.CalculationFrequencyRepository;
import com.fusionx.lending.product.repository.CommonListItemRepository;
import com.fusionx.lending.product.repository.FeeChargeDetailOptionalHistoryRepository;
import com.fusionx.lending.product.repository.FeeChargeDetailOptionalOptionHistoryRepository;
import com.fusionx.lending.product.repository.FeeChargeDetailOptionalOptionPendingRepository;
import com.fusionx.lending.product.repository.FeeChargeDetailOptionalOptionRepository;
import com.fusionx.lending.product.repository.FeeChargeDetailOptionalPendingRepository;
import com.fusionx.lending.product.repository.FeeChargeDetailOptionalRepository;
import com.fusionx.lending.product.repository.FeeChargePendingRepository;
import com.fusionx.lending.product.repository.FeeChargeRepository;
import com.fusionx.lending.product.repository.OtherFeeTypeRepository;
import com.fusionx.lending.product.resources.FeeChargeDetailsCommonResource;
import com.fusionx.lending.product.resources.FeeChargeDetailOptionalAddResource;
import com.fusionx.lending.product.resources.FeeChargeDetailOptionalOptionResource;
import com.fusionx.lending.product.resources.FeeChargeDetailOptionalUpdateResource;
import com.fusionx.lending.product.resources.WorkflowInitiationRequestResource;
import com.fusionx.lending.product.resources.WorkflowRulesResource;
import com.fusionx.lending.product.service.FeeChargeDetailOptionalService;
import com.fusionx.lending.product.service.LendingWorkflowService;
import com.fusionx.lending.product.service.ValidationService;

@Component
@Transactional(rollbackFor = Exception.class)
public class FeeChargeDetailOptionalServiceImpl  extends MessagePropertyBase implements FeeChargeDetailOptionalService{
	
	@Autowired
	private ValidationService validationService;
	@Autowired
	private LendingWorkflowService lendingWorkflowService;
	@Autowired
	private FeeChargePendingRepository feeChargePendingRepository;
	@Autowired
	private FeeChargeRepository feeChargeRepository;
	@Autowired
	private OtherFeeTypeRepository otherFeeTypeRepository;
	@Autowired
	private CalculationFrequencyRepository calculationFrequencyRepository;
	@Autowired
	private ApplicationFrequencyRepository applicationFrequencyRepository;
	@Autowired
	private CommonListItemRepository commonListItemRepository;	
	@Autowired
	private FeeChargeDetailOptionalRepository feeChargeDetailOptionalRepository;	
	@Autowired
	private FeeChargeDetailOptionalPendingRepository feeChargeDetailOptionalPendingRepository;	
	@Autowired
	private FeeChargeDetailOptionalHistoryRepository feeChargeDetailOptionalHistoryRepository;
	@Autowired
	private FeeChargeDetailOptionalOptionRepository feeChargeDetailOptionalOptionRepository;	
	@Autowired
	private FeeChargeDetailOptionalOptionHistoryRepository feeChargeDetailOptionalOptionHistoryRepository;
	@Autowired
	private FeeChargeDetailOptionalOptionPendingRepository feeChargeDetailOptionalOptionPendingRepository;
	

	private static final String DEFAULT_APPROVAL_USER = "kie-server";
	private static final String DOMAIN_PATH = "com.fusionx.LendingWF";
	private static final String DOMAIN = "LendingWF";
	
	@Override
	public FeeChargeDetailOptional addFeeChargeDetailOptional(String tenentId,FeeChargeDetailOptionalAddResource feeChargeDetailOptionalAddResource) {
		
		Optional<FeeCharge> isFeeCharge = feeChargeRepository
				.findById(validationService.stringToLong(feeChargeDetailOptionalAddResource.getFeeChargeId()));
		if (isFeeCharge.isPresent()) {
			if (!isFeeCharge.get().getName().equalsIgnoreCase(feeChargeDetailOptionalAddResource.getFeeChargeName()))
				throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), "feeChargeName");

		} else {
			throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), "feeChargeId");
		}
		
		
		if(!(CommonStatus.ACTIVE).equals(isFeeCharge.get().getStatus())) {
			throw new ValidateRecordException(environment.getProperty("common.status-invaild"), "feeChargeId");
		}
		

		FeeChargeDetailOptionalPending feeChargeDetailOptionalPending = createIntermediateFeeChargeDetailOptionalPending(tenentId ,new FeeChargeDetailOptionalPending() ,feeChargeDetailOptionalAddResource.getCommonResource(),LogginAuthentcation.getInstance().getUserName());
		FeeChargeDetailOptional feeChargeDetailOptional = mapPendingTofeeChargeDetailOptional(feeChargeDetailOptionalPending ,new FeeChargeDetailOptional());
				
		Date effectiveDate = validationServiceStringToDate(feeChargeDetailOptionalAddResource.getEffectiveDate(),environment.getProperty("common-effective-date.invalid"));
		List<FeeChargeDetailOptional> feeChargeWithSameFeeTypeAndEffecDateList = feeChargeDetailOptionalRepository.searchByFeeTypeEffDateAndId(effectiveDate, validationService.stringToLong(feeChargeDetailOptionalAddResource.getCommonResource().getFeeTypeId()), null);
		
		if((feeChargeWithSameFeeTypeAndEffecDateList !=null)&&(feeChargeWithSameFeeTypeAndEffecDateList.size() != 0)) {
			throw new ValidateRecordException(environment.getProperty("feeCharge-detail-option-same.feetype.effectivedate"), MESSAGE);
		}
		
		feeChargeDetailOptional.setFeeCharge(isFeeCharge.get());
		feeChargeDetailOptional.setEffectiveDate(dateToTimeStamp(effectiveDate));
		feeChargeDetailOptional.setCreatedDate(validationService.getCreateOrModifyDate());
		feeChargeDetailOptional.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
		feeChargeDetailOptional.setModifiedDate(null);
		feeChargeDetailOptional.setModifiedUser(null);
		
		feeChargeDetailOptional = feeChargeDetailOptionalRepository.save(feeChargeDetailOptional);
						
		saveHistoryFeeChargeDetailOptional(tenentId,null,feeChargeDetailOptional , LogginAuthentcation.getInstance().getUserName());
		
		if((feeChargeDetailOptionalAddResource.getOptionsList() != null) && (!feeChargeDetailOptionalAddResource.getOptionsList().isEmpty())) {		
			saveOptionsList(tenentId,feeChargeDetailOptionalAddResource.getOptionsList(),feeChargeDetailOptional,feeChargeDetailOptionalAddResource.getCommonResource().getType());
		}		
		
		return feeChargeDetailOptional;		
	}
	
	@Override
	public FeeChargePending updateFeeChargeDetailsOptional(String tenantId, Long id,
			FeeChargeDetailOptionalUpdateResource feeChargeDetailOptionalUpdateResource) {
			
		Optional<FeeCharge> feeChargeOptional = feeChargeRepository.findById(id);
		if(!feeChargeOptional.isPresent()) {
			throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), "feeChargeId");
		}
		
		if(feeChargeDetailOptionalUpdateResource.getFeeChargePendingId() == null) {
			
    		List<FeeChargePending> pendingList = feeChargePendingRepository.findAllByFeeChargeAndApproveStatus(feeChargeOptional.get(),CommonApproveStatus.PENDING);
    		if(!pendingList.isEmpty()) {
    			throw new ValidateRecordException(environment.getProperty("feeCharge-pending-active-exists"), MESSAGE);
    		}
//    		
//			List<FeeChargePending> findAllByFeeChargeList = feeChargePendingRepository.findAllByFeeCharge(feeChargeOptional.get());
//		
//			findAllByFeeChargeList =findAllByFeeChargeList.stream()
//					.filter(e -> (e.getLendingWorkflow() != null && e.getLendingWorkflow().getWorkflowStatus().equals(WorkflowStatus.ACTIVE)))
//					.collect(Collectors.toList());
//		
//			if(!findAllByFeeChargeList.isEmpty()) {
//				throw new ValidateRecordException(environment.getProperty("feeCharge-pending-active-exists"), MESSAGE);
//			}
		}
		
		FeeChargePending feeChargePending = approveOrGenerateWorkFlow(tenantId, LogginAuthentcation.getInstance().getUserName(), feeChargeDetailOptionalUpdateResource ,feeChargeOptional.get());

		return feeChargePending;
	}

	private FeeChargePending approveOrGenerateWorkFlow(String tenantId, String user,
			FeeChargeDetailOptionalUpdateResource feeChargeDetailOptionalUpdateResource , FeeCharge feeCharge) {
		
		FeeChargePending feeChargePending = null;
		
		if (feeChargeDetailOptionalUpdateResource.getFeeChargePendingId() != null) {
			Optional<FeeChargePending> feeChargePendingOptional = feeChargePendingRepository.findById(
					validationService.stringToLong(feeChargeDetailOptionalUpdateResource.getFeeChargePendingId()));
			if (feeChargePendingOptional.isPresent()) {
				feeChargePending = feeChargePendingOptional.get();
			} else {
				throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), "feeChargePendingId");
			}
		}
		
		FeeChargeDetailOptional feeChargeDetailOptional =  null;
		
		if(feeChargeDetailOptionalUpdateResource.getFeeChargeDetailsOptionalId()!= null) {
			Optional<FeeChargeDetailOptional> isPresentFeeChargeDetailOptional = feeChargeDetailOptionalRepository.findById(validationService.stringToLong(feeChargeDetailOptionalUpdateResource.getFeeChargeDetailsOptionalId()));
			if(isPresentFeeChargeDetailOptional.isPresent()) {
				feeChargeDetailOptional = isPresentFeeChargeDetailOptional.get();
				

                if (!(feeChargeDetailOptional.getVersion()).equals(Long.parseLong(feeChargeDetailOptionalUpdateResource.getVersion()))) {
                    throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), "version");
                }
                
			}else {
				throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), "feeChargeDetailOptionalId");
			}
		}
		
		if(feeChargePending != null) {	
			List<FeeChargeDetailOptionalPending> existingList = null;
			if(feeChargeDetailOptional != null) {
				existingList = feeChargeDetailOptionalPendingRepository.findAllByFeeChargePendingAndFeeChargeDetailOptional(feeChargePending, feeChargeDetailOptional);
			}
			if((existingList==null) || (existingList.isEmpty())) {
				FeeChargeDetailOptionalPending savePendingFeeChargeDetailOptional = savePendingFeeChargeDetailOptional(tenantId, feeChargePending,feeChargeDetailOptional, feeChargeDetailOptionalUpdateResource,user);
				
				if((feeChargeDetailOptionalUpdateResource.getOptionsList() != null) && (!feeChargeDetailOptionalUpdateResource.getOptionsList().isEmpty())) {
					saveOptionsPendingList(tenantId,savePendingFeeChargeDetailOptional,feeChargeDetailOptionalUpdateResource.getOptionsList(),feeChargeDetailOptionalUpdateResource.getCommonResource().getType());
				} 	
			}else {
				throw new ValidateRecordException(environment.getProperty("feeCharge-detail-optional-pending-exists"), MESSAGE);
			}
		} else {

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
				processId = validationService.initiateLendingProductWorkFlow(workflowInitiationRequestResource,
						tenantId);
				if (processId != null) {
					lendingWorkflow = lendingWorkflowService.save(tenantId, processId, workflowType, user);
					feeChargePending = saveFeeChargePending(feeCharge ,lendingWorkflow , user);
					
					FeeChargeDetailOptionalPending savePendingFeeChargeDetailOptional = savePendingFeeChargeDetailOptional(
							tenantId, feeChargePending, feeChargeDetailOptional, feeChargeDetailOptionalUpdateResource,
							user);
					if ((feeChargeDetailOptionalUpdateResource.getOptionsList() != null) && (!feeChargeDetailOptionalUpdateResource.getOptionsList().isEmpty())) {
						saveOptionsPendingList(tenantId, savePendingFeeChargeDetailOptional,feeChargeDetailOptionalUpdateResource.getOptionsList(),feeChargeDetailOptionalUpdateResource.getCommonResource().getType());
					}
				}
			} else {
				//4
				//directUpdate(tenantId,user,feeChargeDetailOptionalUpdateResource,feeCharge);				
				feeChargePending = saveFeeChargePending(feeCharge ,lendingWorkflow , user);
				
				FeeChargeDetailOptionalPending savePendingFeeChargeDetailOptional = savePendingFeeChargeDetailOptional(
						tenantId, feeChargePending, feeChargeDetailOptional, feeChargeDetailOptionalUpdateResource,
						user);
				//updateFeeChargeDetailOptional(savePendingFeeChargeDetailOptional);//1

				if ((feeChargeDetailOptionalUpdateResource.getOptionsList() != null)&& (!feeChargeDetailOptionalUpdateResource.getOptionsList().isEmpty())) {
					saveOptionsPendingList(tenantId, savePendingFeeChargeDetailOptional,feeChargeDetailOptionalUpdateResource.getOptionsList(),feeChargeDetailOptionalUpdateResource.getCommonResource().getType());
					//updateFeeChargeDetailOptionalOption(savePendingFeeChargeDetailOptional);//2
				}
	
				//3
				approvePendingFeeChargeDetailsOptional(feeChargePending.getId());//comment 3 and uncomment 1 ,2 
				//or comment all 1,2,3 and uncomment 4
			}
		
		}
		return feeChargePending;
	}
	
	
	private FeeChargeDetailOptionalPending savePendingFeeChargeDetailOptional(String tenentId ,FeeChargePending feeChargePending, FeeChargeDetailOptional feeChargeDetailOptional,FeeChargeDetailOptionalUpdateResource feeChargeDetailOptionalUpdateResource, String user) {
		

		FeeChargeDetailOptionalPending feeChargeDetailOptionalPending = createIntermediateFeeChargeDetailOptionalPending(tenentId ,new FeeChargeDetailOptionalPending() ,feeChargeDetailOptionalUpdateResource.getCommonResource(),LogginAuthentcation.getInstance().getUserName());

		feeChargeDetailOptionalPending.setFeeChargePending(feeChargePending);
		feeChargeDetailOptionalPending.setFeeChargeDetailOptional(feeChargeDetailOptional);
		feeChargeDetailOptionalPending.setEffectiveDate(dateToTimeStamp(validationServiceStringToDate(feeChargeDetailOptionalUpdateResource.getEffectiveDate(),environment.getProperty("common-effective-date.invalid"))));
		
		return feeChargeDetailOptionalPendingRepository.saveAndFlush(feeChargeDetailOptionalPending);
	}
	
	@Override
	public List<FeeChargeDetailOptional> getAllFeeChargeOptionalByFeeChargeId(Long feeChargeId) {
		
		Optional<FeeCharge> isFeeCharge = feeChargeRepository.findById(feeChargeId);
		if (!isFeeCharge.isPresent()) {
			throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), "feeChargeId");
		}
		
		return feeChargeDetailOptionalRepository.findAllByFeeCharge(isFeeCharge.get());
	}
	
	@Override
	public FeeChargeDetailOptional getDetailsById(Long id) {
		
		Optional<FeeChargeDetailOptional> isPresentFeeChargeDetailOptional = feeChargeDetailOptionalRepository.findById(id);		
		if (!isPresentFeeChargeDetailOptional.isPresent()) {
			throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), "feeChargeDetailOptionalId");
		}
		
		FeeChargeDetailOptional feeChargeDetailOptional = isPresentFeeChargeDetailOptional.get();
		List<FeeChargeDetailOptionalOption> feeChargeDetailOptionalOptionList = feeChargeDetailOptionalOptionRepository.findAllByFeeChargeDetailOptional(feeChargeDetailOptional);
		feeChargeDetailOptional.setOptionsList(feeChargeDetailOptionalOptionList);
		
		return feeChargeDetailOptional;
	}
	
	@Override
	public List<FeeChargeDetailOptionalPending> getAllDetailsByFeeChargePendingId(Long feeChargePendingId){
		
		Optional<FeeChargePending> feeChargePendingOptional = feeChargePendingRepository.findById(feeChargePendingId);
		if(!feeChargePendingOptional.isPresent()) {
			throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), "feeChargePendingId");
		}
		
		List<FeeChargeDetailOptionalPending> findAllByFeeChargeDetailOptionalPendingList = feeChargeDetailOptionalPendingRepository.findAllByFeeChargePending(feeChargePendingOptional.get());
		findAllByFeeChargeDetailOptionalPendingList.
		forEach(
				i -> {
						List<FeeChargeDetailOptionalOptionPending> feeChargeDetailOptionalOptionPendingList = feeChargeDetailOptionalOptionPendingRepository.findAllByFeeChargeDetailOptionalPending(i);
						i.setOptionsPendingList(feeChargeDetailOptionalOptionPendingList);								
					}
		);	
		
		return findAllByFeeChargeDetailOptionalPendingList;
	}

	
	@Override
	public List<FeeChargeDetailOptional> findByStatus(String status) {
		
		return feeChargeDetailOptionalRepository.findAllByStatus(CommonStatus.valueOf(status));
	}
	
	@Override
	public List<FeeChargeDetailOptional> findAll() {
		
		return feeChargeDetailOptionalRepository.findAll();
	}
	
	@Override
	public List<FeeChargeDetailOptional> findByOtherFeeTypeId(Long otherFeeTypeId) {
		
		return feeChargeDetailOptionalRepository.findAllByOtherFeeTypeId(otherFeeTypeId);
	}
	
	@Override
	public List<FeeChargeDetailOptional> findByCategory(Long categoryId) {
		
		Optional<CommonListItem> commonListItemOptional = commonListItemRepository.findById(categoryId);

		if (!commonListItemOptional.isPresent()) {
			return new ArrayList<>();
		}
		
		return feeChargeDetailOptionalRepository.findAllByFeeCategory(commonListItemOptional.get());		
	}
	
	
	


	
	@Override
	public void approvePendingFeeChargeDetailsOptional(Long feeChargePendingId) {
		Optional<FeeChargePending> feeChargePendingOptional = feeChargePendingRepository.findById(feeChargePendingId);
		
		if(!feeChargePendingOptional.isPresent()) {
			throw new ValidateRecordException(environment.getProperty("feeCharge-pending-not-found"), MESSAGE);
		}
		
		List<FeeChargeDetailOptionalPending> feeChargeDetailOptionalPendingList = feeChargeDetailOptionalPendingRepository.findAllByFeeChargePending(feeChargePendingOptional.get());
		
		if((feeChargeDetailOptionalPendingList  != null) && (!feeChargeDetailOptionalPendingList.isEmpty())) {
			for(FeeChargeDetailOptionalPending item :feeChargeDetailOptionalPendingList) {
				updateFeeChargeDetailOptional(item);
				updateFeeChargeDetailOptionalOption(item);
			}
		}
		
	}
	
	

	
	

	private CommonListItem validateFeeCategory(Long id, String name) {

		Optional<CommonListItem> commonListItem = commonListItemRepository.findById(id);

		if (!commonListItem.isPresent()) {
			throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), "feeCategoryId");
		}
		if (!commonListItem.get().getReferenceCode().toString()
				.equalsIgnoreCase(CommonListCode.FEE_CATEGORY.toString())) {
			throw new ValidateRecordException(environment.getProperty("commonListItem.referenceCode.invalid"), "feeCategoryId");
		}
		
		if (!commonListItem.get().getName().equalsIgnoreCase(name))
			throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), "feeCategoryName");
	
		if(!(CommonStatus.ACTIVE).equals(commonListItem.get().getStatus())) {
			throw new ValidateRecordException(environment.getProperty("common.status-invaild"), "feeCategoryId");
		}
		
		return commonListItem.get();
	}
	
	private FeeChargePending saveFeeChargePending(FeeCharge feeCharge , LendingWorkflow lendingWorkflow , String user) {
		FeeChargePending feeChargePending = new FeeChargePending();
		feeChargePending.setTenantId(feeCharge.getTenantId());
		if( lendingWorkflow!= null)
			feeChargePending.setLendingWorkflow(lendingWorkflow);
		feeChargePending.setFeeCharge(feeCharge);
		feeChargePending.setCode(feeCharge.getCode());
		feeChargePending.setName(feeCharge.getName());
		feeChargePending.setFeeChargeType(feeCharge.getFeeChargeType());
		feeChargePending.setStatus(feeCharge.getStatus());
		feeChargePending.setApproveStatus(CommonApproveStatus.PENDING);
		feeChargePending.setPcreatedDate(validationService.getCreateOrModifyDate());
		feeChargePending.setPcreatedUser(user);
		feeChargePending.setSyncTs(validationService.getCreateOrModifyDate());
		feeChargePending = feeChargePendingRepository.save(feeChargePending);
		return feeChargePending;
	}
	
	
	
	public void updateFeeChargeDetailOptional(FeeChargeDetailOptionalPending feeChargeDetailOptionalPending){
		
		FeeChargeDetailOptional feeChargeDetailOptional =  null;
		if(feeChargeDetailOptionalPending.getFeeChargeDetailOptional() != null) {
			feeChargeDetailOptional = feeChargeDetailOptionalPending.getFeeChargeDetailOptional();
			
			feeChargeDetailOptional.setModifiedDate(validationService.getCreateOrModifyDate());
			feeChargeDetailOptional.setModifiedUser(feeChargeDetailOptionalPending.getCreatedUser());
		}else {
			feeChargeDetailOptional = new FeeChargeDetailOptional();
			
			feeChargeDetailOptional.setFeeCharge(feeChargeDetailOptionalPending.getFeeChargePending().getFeeCharge());
			feeChargeDetailOptional.setCreatedDate(validationService.getCreateOrModifyDate());
			feeChargeDetailOptional.setCreatedUser(feeChargeDetailOptionalPending.getCreatedUser());
		}		
		
		
		feeChargeDetailOptional = mapPendingTofeeChargeDetailOptional(feeChargeDetailOptionalPending ,feeChargeDetailOptional);
		
		feeChargeDetailOptional.setEffectiveDate(feeChargeDetailOptionalPending.getEffectiveDate());
		feeChargeDetailOptional = feeChargeDetailOptionalRepository.save(feeChargeDetailOptional);
		
		saveHistoryFeeChargeDetailOptional(feeChargeDetailOptionalPending.getTenantId(),feeChargeDetailOptionalPending,feeChargeDetailOptional,feeChargeDetailOptionalPending.getCreatedUser());
	}

	
	@Override
	public FeeChargeDetailOptional directUpdate(String tenantId, String user,FeeChargeDetailOptionalUpdateResource feeChargeDetailOptionalUpdateResource , FeeCharge feeCharge) {
		FeeChargeDetailOptional feeChargeDetailOptional =  null;
		
		if(feeChargeDetailOptionalUpdateResource.getFeeChargeDetailsOptionalId()!= null) {
			Optional<FeeChargeDetailOptional> isPresentFeeChargeDetailOptional = feeChargeDetailOptionalRepository.findById(validationService.stringToLong(feeChargeDetailOptionalUpdateResource.getFeeChargeDetailsOptionalId()));
			if(isPresentFeeChargeDetailOptional.isPresent()) {
				feeChargeDetailOptional = isPresentFeeChargeDetailOptional.get();
							
				FeeChargeDetailOptionalPending feeChargeDetailOptionalPending = createIntermediateFeeChargeDetailOptionalPending(tenantId ,new FeeChargeDetailOptionalPending() ,feeChargeDetailOptionalUpdateResource.getCommonResource(),user);
				feeChargeDetailOptional = mapPendingTofeeChargeDetailOptional(feeChargeDetailOptionalPending ,feeChargeDetailOptional);
				
				feeChargeDetailOptional.setModifiedDate(validationService.getCreateOrModifyDate());
				feeChargeDetailOptional.setModifiedUser(user);
			}else {
				throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), "feeChargeDetailOptionalId");
			}
		}else {

			FeeChargeDetailOptionalPending feeChargeDetailOptionalPending = createIntermediateFeeChargeDetailOptionalPending(tenantId ,new FeeChargeDetailOptionalPending() ,feeChargeDetailOptionalUpdateResource.getCommonResource(),user);
			
			feeChargeDetailOptional = mapPendingTofeeChargeDetailOptional(feeChargeDetailOptionalPending ,new FeeChargeDetailOptional());
		
			feeChargeDetailOptional.setFeeCharge(feeCharge);
			feeChargeDetailOptional.setCreatedDate(validationService.getCreateOrModifyDate());
			feeChargeDetailOptional.setCreatedUser(user);
		
		
		}
		
		
		feeChargeDetailOptional.setEffectiveDate(dateToTimeStamp(validationServiceStringToDate(feeChargeDetailOptionalUpdateResource.getEffectiveDate(),environment.getProperty("common-effective-date.invalid"))));
		
		feeChargeDetailOptional = feeChargeDetailOptionalRepository.save(feeChargeDetailOptional);
		
		saveHistoryFeeChargeDetailOptional(tenantId,null,feeChargeDetailOptional , user);
				
		if((feeChargeDetailOptionalUpdateResource.getOptionsList() != null) && (!feeChargeDetailOptionalUpdateResource.getOptionsList().isEmpty())) {
			
			List<FeeChargeDetailOptionalOptionHistory> feeChargeDetailOptionalOptionHistoryList = new ArrayList<>();
			
			for(FeeChargeDetailOptionalOptionResource resource:feeChargeDetailOptionalUpdateResource.getOptionsList()) {
				
				
				FeeChargeDetailOptionalOption feeChargeDetailOptionalOption = null;
				
				if(resource.getFeeChargeDetailOptionalOptionId()!= null){
					Optional<FeeChargeDetailOptionalOption> FeeChargeDetailOptionalOptionOptional = feeChargeDetailOptionalOptionRepository.findById(validationService.stringToLong(resource.getFeeChargeDetailOptionalOptionId()));
					if(FeeChargeDetailOptionalOptionOptional.isPresent()) {
						feeChargeDetailOptionalOption = FeeChargeDetailOptionalOptionOptional.get();
						feeChargeDetailOptionalOption.setModifiedDate(validationService.getCreateOrModifyDate());
						feeChargeDetailOptionalOption.setModifiedUser(user);	
						feeChargeDetailOptionalOption.setSyncTs(validationService.getCreateOrModifyDate());	
					}else {
						throw new ValidateRecordException("invalid details optional option id", MESSAGE);
					}		
				}else {
					feeChargeDetailOptionalOption = new FeeChargeDetailOptionalOption();
					feeChargeDetailOptionalOption.setTenantId(tenantId);
					feeChargeDetailOptionalOption.setCreatedDate(validationService.getCreateOrModifyDate());
					feeChargeDetailOptionalOption.setCreatedUser(user);	
					feeChargeDetailOptionalOption.setSyncTs(validationService.getCreateOrModifyDate());	
					feeChargeDetailOptionalOption.setFeeChargeDetailOptional(feeChargeDetailOptional);
				}
								
				feeChargeDetailOptionalOption.setOptionValue(validationService.stringToLong(resource.getOption()));
							
				if(!(FeeChargeDetailsType.Amount.toString()).equals(feeChargeDetailOptional.getType())) {
					if(resource.getAmount() != null) {
						feeChargeDetailOptionalOption.setAmount(validationService.stringToBigDecimal(resource.getAmount()));
						feeChargeDetailOptionalOption.setRate(null);	
					}else {
						throw new ValidateRecordException(environment.getProperty("feeCharge-detail-option-amount-not-null"), MESSAGE);
					}				
				}else if(!(FeeChargeDetailsType.Rate.toString()).equals(feeChargeDetailOptional.getType())) {
					if(resource.getRate() != null) {
						feeChargeDetailOptionalOption.setAmount(null);
						feeChargeDetailOptionalOption.setRate(validationService.stringToBigDecimal(resource.getRate()));	
					}else {
						throw new ValidateRecordException(environment.getProperty("feeCharge-detail-option-rate-not-null"), MESSAGE);
					}	
				}			
				feeChargeDetailOptionalOption.setStatus(CommonStatus.ACTIVE);
					
				feeChargeDetailOptionalOption = feeChargeDetailOptionalOptionRepository.save(feeChargeDetailOptionalOption);
				feeChargeDetailOptionalOptionHistoryList.add(createIntermediateOptionsHistory(feeChargeDetailOptionalOption , null));				
			}
			
			feeChargeDetailOptionalOptionHistoryRepository.saveAll(feeChargeDetailOptionalOptionHistoryList);
		}
		
		return feeChargeDetailOptional;	
	}
	
	
	private void saveOptionsList(String tenentId, List<FeeChargeDetailOptionalOptionResource> optionsList , FeeChargeDetailOptional feeChargeDetailOptional , String type) {
		
		List<FeeChargeDetailOptionalOptionHistory> feeChargeDetailOptionalOptionHistoryList = new ArrayList<>();
		for(FeeChargeDetailOptionalOptionResource resource :optionsList) {
			
			FeeChargeDetailOptionalOption feeChargeDetailOptionalOption = new FeeChargeDetailOptionalOption();
			feeChargeDetailOptionalOption.setTenantId(tenentId);
			feeChargeDetailOptionalOption.setFeeChargeDetailOptional(feeChargeDetailOptional);
			feeChargeDetailOptionalOption.setOptionValue(validationService.stringToLong(resource.getOption()));
						
			if((FeeChargeDetailsType.Amount.toString()).equals(type)) {
				if((!resource.getAmount().isEmpty()) && (resource.getAmount() != null)) {
					feeChargeDetailOptionalOption.setAmount(validationService.stringToBigDecimal(resource.getAmount()));
					feeChargeDetailOptionalOption.setRate(null);	
				}else {
					throw new ValidateRecordException(environment.getProperty("feeCharge-detail-option-amount-not-null"), MESSAGE);
				}				
			}else if((FeeChargeDetailsType.Rate.toString()).equals(type)) {
				if((!resource.getRate().isEmpty()) && (resource.getRate() != null)) {
					feeChargeDetailOptionalOption.setAmount(null);
					feeChargeDetailOptionalOption.setRate(validationService.stringToBigDecimal(resource.getRate()));	
				}else {
					throw new ValidateRecordException(environment.getProperty("feeCharge-detail-option-rate-not-null"), MESSAGE);
				}	
			}
		
			feeChargeDetailOptionalOption.setCreatedDate(validationService.getCreateOrModifyDate());
			feeChargeDetailOptionalOption.setCreatedUser(LogginAuthentcation.getInstance().getUserName());	
			feeChargeDetailOptionalOption.setStatus(CommonStatus.ACTIVE);
			feeChargeDetailOptionalOption.setSyncTs(validationService.getCreateOrModifyDate());		
			feeChargeDetailOptionalOption = feeChargeDetailOptionalOptionRepository.save(feeChargeDetailOptionalOption);
			
			feeChargeDetailOptionalOptionHistoryList.add(createIntermediateOptionsHistory(feeChargeDetailOptionalOption , null));
		}
		feeChargeDetailOptionalOptionHistoryRepository.saveAll(feeChargeDetailOptionalOptionHistoryList);
	}
	
	
	private void saveOptionsPendingList(String tenentId,FeeChargeDetailOptionalPending feeChargeDetailOptionalPending, List<FeeChargeDetailOptionalOptionResource> optionsList, String type) {
		
		List<FeeChargeDetailOptionalOptionPending> feeChargeDetailOptionalOptionPendingList = new ArrayList<>();

		for(FeeChargeDetailOptionalOptionResource resource :optionsList) {
			
			FeeChargeDetailOptionalOptionPending feeChargeDetailOptionalOptionPending = new FeeChargeDetailOptionalOptionPending();
			feeChargeDetailOptionalOptionPending.setTenantId(tenentId);
			feeChargeDetailOptionalOptionPending.setFeeChargeDetailOptionalPending(feeChargeDetailOptionalPending);
			
			FeeChargeDetailOptionalOption feeChargeDetailOptionalOption = null;
			if(resource.getFeeChargeDetailOptionalOptionId()!= null){
				Optional<FeeChargeDetailOptionalOption> FeeChargeDetailOptionalOptionOptional = feeChargeDetailOptionalOptionRepository.findById(validationService.stringToLong(resource.getFeeChargeDetailOptionalOptionId()));
				if(FeeChargeDetailOptionalOptionOptional.isPresent()) {
					feeChargeDetailOptionalOption = FeeChargeDetailOptionalOptionOptional.get();
				}		
			}
			
			if(!(FeeChargeDetailsType.Amount.toString()).equals(type)) {
				if(resource.getAmount() != null) {
					feeChargeDetailOptionalOptionPending.setAmount(validationService.stringToBigDecimal(resource.getAmount()));
					feeChargeDetailOptionalOptionPending.setRate(null);	
				}else {
					throw new ValidateRecordException(environment.getProperty("feeCharge-detail-option-amount-not-null"), MESSAGE);
				}				
			}else if(!(FeeChargeDetailsType.Rate.toString()).equals(type)) {
				if(resource.getRate() != null && !resource.getRate().isEmpty()) {
					feeChargeDetailOptionalOptionPending.setAmount(null);
					feeChargeDetailOptionalOptionPending.setRate(validationService.stringToBigDecimal(resource.getRate()));	
				}else {
					throw new ValidateRecordException(environment.getProperty("feeCharge-detail-option-rate-not-null"), MESSAGE);
				}	
			}
			
			feeChargeDetailOptionalOptionPending.setFeeChargeDetailOptionalOption(feeChargeDetailOptionalOption);
			feeChargeDetailOptionalOptionPending.setOptionValue(validationService.stringToLong(resource.getOption()));
			feeChargeDetailOptionalOptionPending.setCreatedDate(validationService.getCreateOrModifyDate());
			feeChargeDetailOptionalOptionPending.setCreatedUser(LogginAuthentcation.getInstance().getUserName());	
			feeChargeDetailOptionalOptionPending.setStatus(CommonStatus.ACTIVE);
			feeChargeDetailOptionalOptionPending.setSyncTs(validationService.getCreateOrModifyDate());
			feeChargeDetailOptionalOptionPendingList.add(feeChargeDetailOptionalOptionPending);
		
		}				
		feeChargeDetailOptionalOptionPendingRepository.saveAll(feeChargeDetailOptionalOptionPendingList);		
	}
	

	
	
	private void updateFeeChargeDetailOptionalOption(FeeChargeDetailOptionalPending feeChargeDetailOptionalPending) {
		List<FeeChargeDetailOptionalOptionPending> findAllByFeeChargeDetailOptionalPendingList = feeChargeDetailOptionalOptionPendingRepository.findAllByFeeChargeDetailOptionalPending(feeChargeDetailOptionalPending);
		
		List<FeeChargeDetailOptionalOptionHistory> feeChargeDetailOptionalOptionHistoryList = new ArrayList<>();

		if((findAllByFeeChargeDetailOptionalPendingList != null) && (!findAllByFeeChargeDetailOptionalPendingList.isEmpty())) {
			for(FeeChargeDetailOptionalOptionPending item : findAllByFeeChargeDetailOptionalPendingList) {
				FeeChargeDetailOptionalOption feeChargeDetailOptionalOption = null;
				if(item.getFeeChargeDetailOptionalOption() != null) {				
					feeChargeDetailOptionalOption = item.getFeeChargeDetailOptionalOption();
					feeChargeDetailOptionalOption.setModifiedUser(item.getCreatedUser());
					feeChargeDetailOptionalOption.setModifiedDate(validationService.getCreateOrModifyDate());
				}else {
					feeChargeDetailOptionalOption =  new FeeChargeDetailOptionalOption();
					feeChargeDetailOptionalOption.setTenantId(item.getTenantId());
					feeChargeDetailOptionalOption.setFeeChargeDetailOptional(item.getFeeChargeDetailOptionalPending().getFeeChargeDetailOptional());			
					feeChargeDetailOptionalOption.setCreatedDate(validationService.getCreateOrModifyDate());
					feeChargeDetailOptionalOption.setCreatedUser(item.getCreatedUser());	
					feeChargeDetailOptionalOption.setStatus(CommonStatus.ACTIVE);
					feeChargeDetailOptionalOption.setSyncTs(validationService.getCreateOrModifyDate());					
				}
				
				feeChargeDetailOptionalOption.setOptionValue(item.getOptionValue());
				feeChargeDetailOptionalOption.setAmount(item.getAmount());
				feeChargeDetailOptionalOption.setRate(item.getRate());
				
				feeChargeDetailOptionalOption = feeChargeDetailOptionalOptionRepository.save(feeChargeDetailOptionalOption);
				
				feeChargeDetailOptionalOptionHistoryList.add(createIntermediateOptionsHistory(feeChargeDetailOptionalOption , item.getId()));
				
			}
			
			feeChargeDetailOptionalOptionHistoryRepository.saveAll(feeChargeDetailOptionalOptionHistoryList);	
		}
	}
	


	
	
	public FeeChargeDetailOptionalPending createIntermediateFeeChargeDetailOptionalPending(String tenantId, FeeChargeDetailOptionalPending fcdop, FeeChargeDetailsCommonResource commonResource , String user) {
		
		FeeChargeDetailOptionalPending feeChargeDetailOptionalPending = fcdop; 
		
		Optional<ApplicationFrequency> isApplicationFrequency = applicationFrequencyRepository
				.findById(validationService.stringToLong(commonResource.getApplicationFrequencyId()));
		if (isApplicationFrequency.isPresent()) {
			if (!isApplicationFrequency.get().getName()
					.equalsIgnoreCase(commonResource.getApplicationFrequencyName()))
				throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE),"applicationFrequencyName");

		} else {
			throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), "applicationFrequencyId");
		}


		Optional<CalculationFrequency> isCalculationFrequency = calculationFrequencyRepository
				.findById(validationService.stringToLong(commonResource.getCalculationFrequencyId()));
		if (isCalculationFrequency.isPresent()) {
			if (!isCalculationFrequency.get().getName()
					.equalsIgnoreCase(commonResource.getCalculationFrequencyName()))
				throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE),
						"calculationFrequencyName");

		} else {
			throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), "calculationFrequencyId");
		}

		Optional<OtherFeeType> isFeeType = otherFeeTypeRepository
				.findById(validationService.stringToLong(commonResource.getFeeTypeId()));
		if (isFeeType.isPresent()) {
			if (!isFeeType.get().getName().equalsIgnoreCase(commonResource.getFeeTypeName()))
				throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), "feeTypeName");

		} else {
			throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), "feeTypeId");
		}
		
		
		if(!(CommonStatus.ACTIVE).equals(isFeeType.get().getStatus())) {
			throw new ValidateRecordException(environment.getProperty("common.status-invaild"), "feeTypeId");
		}
		if(!(CommonStatus.ACTIVE).equals(isCalculationFrequency.get().getStatus())) {
			throw new ValidateRecordException(environment.getProperty("common.status-invaild"), "calculationFrequencyId");
		}
		if(!(CommonStatus.ACTIVE).equals(isApplicationFrequency.get().getStatus())) {
			throw new ValidateRecordException(environment.getProperty("common.status-invaild"), "applicationFrequencyId");
		}

		CommonListItem feeCategory = validateFeeCategory(
				validationService.stringToLong(commonResource.getFeeCategoryId()),
				commonResource.getFeeCategoryName());


		feeChargeDetailOptionalPending.setType(FeeChargeDetailsType.valueOf(commonResource.getType()).toString());
		feeChargeDetailOptionalPending.setCalculationFrequency(isCalculationFrequency.get());
		feeChargeDetailOptionalPending.setApplicationFrequency(isApplicationFrequency.get());
		feeChargeDetailOptionalPending.setOtherFeeType(isFeeType.get());
		feeChargeDetailOptionalPending.setFeeCategory(feeCategory);
		feeChargeDetailOptionalPending.setMandatory(YesNo.valueOf(commonResource.getMandatory()).toString());
		feeChargeDetailOptionalPending.setNote(commonResource.getNote());
		feeChargeDetailOptionalPending.setStatus(CommonStatus.valueOf(commonResource.getStatus()));
		
		feeChargeDetailOptionalPending.setTenantId(tenantId);
		feeChargeDetailOptionalPending.setSyncTs(validationService.getCreateOrModifyDate());
		feeChargeDetailOptionalPending.setCreatedDate(validationService.getCreateOrModifyDate());
		feeChargeDetailOptionalPending.setCreatedUser(user);
		
		return feeChargeDetailOptionalPending;
	}
	
	
	public FeeChargeDetailOptional mapPendingTofeeChargeDetailOptional(FeeChargeDetailOptionalPending fcdop , FeeChargeDetailOptional fcdo) {
		
		FeeChargeDetailOptionalPending feeChargeDetailOptionalPending = fcdop;
		FeeChargeDetailOptional feeChargeDetailOptional = fcdo;
		
		feeChargeDetailOptional.setType(feeChargeDetailOptionalPending.getType());
		feeChargeDetailOptional.setCalculationFrequency(feeChargeDetailOptionalPending.getCalculationFrequency());
		feeChargeDetailOptional.setApplicationFrequency(feeChargeDetailOptionalPending.getApplicationFrequency());
		feeChargeDetailOptional.setOtherFeeType(feeChargeDetailOptionalPending.getOtherFeeType());
		feeChargeDetailOptional.setFeeCategory(feeChargeDetailOptionalPending.getFeeCategory());
		feeChargeDetailOptional.setMandatory(feeChargeDetailOptionalPending.getMandatory());
		feeChargeDetailOptional.setNote(feeChargeDetailOptionalPending.getNote());
		feeChargeDetailOptional.setStatus(feeChargeDetailOptionalPending.getStatus());
		
		feeChargeDetailOptional.setTenantId(feeChargeDetailOptionalPending.getTenantId());
		feeChargeDetailOptional.setSyncTs(validationService.getCreateOrModifyDate());
		feeChargeDetailOptional.setModifiedDate(validationService.getCreateOrModifyDate());
		feeChargeDetailOptional.setModifiedUser(feeChargeDetailOptionalPending.getCreatedUser());
		
		return feeChargeDetailOptional;
		
	}
	
	private void saveHistoryFeeChargeDetailOptional(String tenantId,FeeChargeDetailOptionalPending feeChargeDetailOptionalPending,  FeeChargeDetailOptional  feeChargeDetailOptional, String historyCreatedUser) {
		
		FeeChargeDetailOptionalHistory  feeChargeDetailOptionalHistory = new  FeeChargeDetailOptionalHistory();
		feeChargeDetailOptionalHistory.setTenantId(tenantId);
		feeChargeDetailOptionalHistory.setFeeChargeDetailOptional(feeChargeDetailOptional);
		feeChargeDetailOptionalHistory.setFeeChargeDetailOptionalPending(feeChargeDetailOptionalPending);
		feeChargeDetailOptionalHistory.setType(feeChargeDetailOptional.getType());
		feeChargeDetailOptionalHistory.setEffectiveDate(feeChargeDetailOptional.getEffectiveDate());
		feeChargeDetailOptionalHistory.setFeeChargeId(feeChargeDetailOptional.getFeeCharge().getId());
		feeChargeDetailOptionalHistory.setCalculationFrequency(feeChargeDetailOptional.getCalculationFrequency());
		feeChargeDetailOptionalHistory.setApplicationFrequency(feeChargeDetailOptional.getApplicationFrequency());
		feeChargeDetailOptionalHistory.setOtherFeeType(feeChargeDetailOptional.getOtherFeeType());
		feeChargeDetailOptionalHistory.setFeeCategory(feeChargeDetailOptional.getFeeCategory());
		feeChargeDetailOptionalHistory.setMandatory(feeChargeDetailOptional.getMandatory());
		feeChargeDetailOptionalHistory.setNote(feeChargeDetailOptional.getNote());
		feeChargeDetailOptionalHistory.setStatus(feeChargeDetailOptional.getStatus());
		feeChargeDetailOptionalHistory.setCreatedDate(feeChargeDetailOptional.getCreatedDate());
		feeChargeDetailOptionalHistory.setCreatedUser(feeChargeDetailOptional.getCreatedUser());
		feeChargeDetailOptionalHistory.setModifiedDate(feeChargeDetailOptional.getModifiedDate());
		feeChargeDetailOptionalHistory.setModifiedUser(feeChargeDetailOptional.getModifiedUser());
		feeChargeDetailOptionalHistory.setHistoryCreatedDate(validationService.getCreateOrModifyDate());
		feeChargeDetailOptionalHistory.setHistoryCreatedUser(historyCreatedUser);
		feeChargeDetailOptionalHistory.setVersion(feeChargeDetailOptional.getVersion());
		feeChargeDetailOptionalHistory.setSyncTs(validationService.getCreateOrModifyDate());
			
		feeChargeDetailOptionalHistoryRepository.save(feeChargeDetailOptionalHistory);
	}
	
	
	private FeeChargeDetailOptionalOptionHistory createIntermediateOptionsHistory(FeeChargeDetailOptionalOption feeChargeDetailOptionalOption ,Long feeChargeDetailOptionalOptionPendingId) {
		
		FeeChargeDetailOptionalOptionHistory feeChargeDetailOptionalOptionHisotry = new FeeChargeDetailOptionalOptionHistory();
		feeChargeDetailOptionalOptionHisotry.setTenantId(feeChargeDetailOptionalOption.getTenantId());
		feeChargeDetailOptionalOptionHisotry.setFeeChargeDetailOptionalOptionId(feeChargeDetailOptionalOption.getId());
		feeChargeDetailOptionalOptionHisotry.setFeeChargeDetailOptionalOptionPendingId(feeChargeDetailOptionalOptionPendingId);
		feeChargeDetailOptionalOptionHisotry.setOptionValue(feeChargeDetailOptionalOption.getOptionValue());
		feeChargeDetailOptionalOptionHisotry.setAmount(feeChargeDetailOptionalOption.getAmount());
		feeChargeDetailOptionalOptionHisotry.setRate(feeChargeDetailOptionalOption.getRate());			
		feeChargeDetailOptionalOptionHisotry.setCreatedDate(validationService.getCreateOrModifyDate());
		feeChargeDetailOptionalOptionHisotry.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
		feeChargeDetailOptionalOptionHisotry.setStatus(feeChargeDetailOptionalOption.getStatus());
		feeChargeDetailOptionalOptionHisotry.setSyncTs(feeChargeDetailOptionalOption.getSyncTs());
		
		return feeChargeDetailOptionalOptionHisotry;	
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
	
	public Timestamp dateToTimeStamp(Date dt) {
		if(dt != null) {
			return new Timestamp(dt.getTime());
		}else {
	        Calendar calendar = Calendar.getInstance();
	        java.util.Date now = calendar.getTime();
	        return new Timestamp(now.getTime());
		}
	}
	
	

}
