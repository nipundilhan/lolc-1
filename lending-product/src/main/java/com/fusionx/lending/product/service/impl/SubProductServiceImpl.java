package com.fusionx.lending.product.service.impl;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import com.fusionx.lending.product.domain.CommonListItem;
import com.fusionx.lending.product.domain.CoreProduct;
import com.fusionx.lending.product.domain.DueDateTemplates;
import com.fusionx.lending.product.domain.Eligibility;
import com.fusionx.lending.product.domain.FeatureBenifitTemplate;
import com.fusionx.lending.product.domain.FeeCharge;
import com.fusionx.lending.product.domain.InterestTemplate;
import com.fusionx.lending.product.domain.LendingWorkflow;
import com.fusionx.lending.product.domain.LoanApplicableRange;
import com.fusionx.lending.product.domain.Product;
import com.fusionx.lending.product.domain.Repayment;
import com.fusionx.lending.product.domain.SubProduct;
import com.fusionx.lending.product.domain.SubProductPending;
import com.fusionx.lending.product.enums.CommonApproveStatus;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.enums.EntityPoint;
import com.fusionx.lending.product.enums.ServiceEntity;
import com.fusionx.lending.product.enums.WorkflowStatus;
import com.fusionx.lending.product.enums.WorkflowType;
import com.fusionx.lending.product.exception.InvalidServiceIdException;
import com.fusionx.lending.product.exception.ValidateRecordException;
import com.fusionx.lending.product.repository.CommonListItemRepository;
import com.fusionx.lending.product.repository.CoreProductRepository;
import com.fusionx.lending.product.repository.DueDateTemplatesRepository;
import com.fusionx.lending.product.repository.EligibilityRepository;
import com.fusionx.lending.product.repository.FeatureBenifitTemplateRepository;
import com.fusionx.lending.product.repository.FeeChargeRepository;
import com.fusionx.lending.product.repository.InterestTemplateRepository;
import com.fusionx.lending.product.repository.LendingWorkflowRepository;
import com.fusionx.lending.product.repository.LoanApplicableRangeRepository;
import com.fusionx.lending.product.repository.ProductRepository;
import com.fusionx.lending.product.repository.RepaymentRepository;
import com.fusionx.lending.product.repository.SubProductPendingRepository;
import com.fusionx.lending.product.repository.SubProductRepository;
import com.fusionx.lending.product.resources.PeriodResponse;
import com.fusionx.lending.product.resources.SubProductAddResource;
import com.fusionx.lending.product.resources.SubProductLoanApplicableUpdateResource;
import com.fusionx.lending.product.resources.SubProductUpdateResource;
import com.fusionx.lending.product.resources.WorkflowInitiationRequestResource;
import com.fusionx.lending.product.resources.WorkflowRulesResource;
import com.fusionx.lending.product.service.LendingWorkflowService;
import com.fusionx.lending.product.service.SubProductHistoryService;
import com.fusionx.lending.product.service.SubProductService;
import com.fusionx.lending.product.service.ValidationService;

/**
 * Sub Product Service 
 * @author 	Sanatha
 * @Date    19-JUL-2021
 * 
 ********************************************************************************************************
 *  ###   	Date         	Story Point   	Task No    		Author      	 Description
 *-------------------------------------------------------------------------------------------------------
 *    1   	19-JUL-2021  	FXL-25      	FXL-25   		Sanatha     	 Initial Development.
 *    2		14/9/2021						FXL-817			Nipun Dilhan	 loan applicable range mapping
 ********************************************************************************************************
 */
@Component
@Transactional(rollbackFor=Exception.class)
public class SubProductServiceImpl extends MessagePropertyBase implements SubProductService{
	
	@Autowired
	private SubProductRepository subProductRepository;
	
	@Autowired 
	private ValidationService validationService;

	
	@Autowired
	private LendingWorkflowService lendingWorkflowService;
	
	@Autowired
	private LendingWorkflowRepository lendingWorkflowRepository;
	
	@Autowired
	private FeatureBenifitTemplateRepository featureBenifitTemplateRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CoreProductRepository coreproductRepository;
	
	@Autowired
	private EligibilityRepository eligibilityRepository;
	
	@Autowired
	private InterestTemplateRepository interestTemplateRepository;
	
	@Autowired
	private RepaymentRepository repaymentRepository;
	
	@Autowired
	private CommonListItemRepository commonListItemRepository;
	
	@Autowired
	private SubProductPendingRepository subProductPendingRepository;
	
	@Autowired
	private FeeChargeRepository feeChargeRepository;
	
	@Autowired
	private SubProductHistoryService subProductHistoryService;
	
	@Autowired
	private LoanApplicableRangeRepository loanApplicableRangeRepository;
	
	private static final String DEFAULT_APPROVAL_USER = "kie-server";

	private static final String DOMAIN_PATH = "com.fusionx.LendingWF";

	private static final String DOMAIN = "LendingWF";
	
	
	@Override
	public List<SubProduct> getAll() {
		
		List<SubProduct> subProdList = subProductRepository.findAll();
		for (SubProduct subProduct : subProdList){
			
			if(subProduct.getProductId()!=null) {
				Product product = validateProduct(subProduct.getProductId().toString());
				subProduct.setProductCode(product.getProductCode());
				subProduct.setProductName(product.getProductName());
			}
			if(subProduct.getFeatureBenifitTemplateId()!=null) {
				FeatureBenifitTemplate featureBenifitTemplate = validateFeatureBenifitTemplate(subProduct.getFeatureBenifitTemplateId().toString());
				subProduct.setFeatureBenifitTemplateCode(featureBenifitTemplate.getCode());
				subProduct.setFeatureBenifitTemplateName(featureBenifitTemplate.getName());
			}
			if(subProduct.getEligibilityId()!=null) {
				Eligibility eligibility = validateEligibility(subProduct.getEligibilityId().toString());
				subProduct.setEligibilityCode(eligibility.getCode());
				subProduct.setEligibilityName(eligibility.getName());
			}
			if(subProduct.getInterestTemplateId()!=null) {
				InterestTemplate interestTemplate = validateInterestTemplate(subProduct.getInterestTemplateId().toString());
				subProduct.setInterestTemplateCode(interestTemplate.getCode());
				subProduct.setInterestTemplateName(interestTemplate.getName());
			}
			if(subProduct.getRepaymentId()!=null) {
				Repayment repayment = validateRepayment(subProduct.getRepaymentId().toString());
				subProduct.setRepaymentCode(repayment.getCode());
				subProduct.setRepaymentName(repayment.getName());
			}
			if(subProduct.getPredecessorId()!=null) {
				SubProduct predecessorId = validatePredecessorId(subProduct.getPredecessorId());
				subProduct.setPredecessorCode(predecessorId.getCode());
				subProduct.setPredecessorName(predecessorId.getName());
			}
			if(subProduct.getMarketingStateId()!=null) {
				CommonListItem marketingStateId = getMarketingStateDetails(subProduct.getMarketingStateId().toString());
				subProduct.setMarketingStateCode(marketingStateId.getCode());
				subProduct.setMarketingStateName(marketingStateId.getName());
				subProduct.setMarketingStateReferenceCode(marketingStateId.getReferenceCode());
			}
			if(subProduct.getStateTenurePeriodId()!=null) {
				PeriodResponse period = validationService.getPeriod(subProduct.getTenantId(), subProduct.getStateTenurePeriodId().toString(), EntityPoint.SUB_PRODUCT);
				subProduct.setStateTenurePeriodCode(period.getCode());
				subProduct.setStateTenurePeriodName(period.getName());
				subProduct.setStateTenurePeriodDescription(period.getDescription());
			}
			
		}
		
		return subProdList;
	}

	@Override
	public Optional<SubProduct> getById(Long id) {
		Optional<SubProduct> isPresentSubProduct= subProductRepository.findById(id);
		if (isPresentSubProduct.isPresent()) {
			
			if(isPresentSubProduct.get().getProductId()!=null) {
				Product product = validateProduct(isPresentSubProduct.get().getProductId().toString());
				isPresentSubProduct.get().setProductCode(product.getProductCode());
				isPresentSubProduct.get().setProductName(product.getProductName());
			}
			if(isPresentSubProduct.get().getFeatureBenifitTemplateId()!=null) {
				FeatureBenifitTemplate featureBenifitTemplate = validateFeatureBenifitTemplate(isPresentSubProduct.get().getFeatureBenifitTemplateId().toString());
				isPresentSubProduct.get().setFeatureBenifitTemplateCode(featureBenifitTemplate.getCode());
				isPresentSubProduct.get().setFeatureBenifitTemplateName(featureBenifitTemplate.getName());
			}
			if(isPresentSubProduct.get().getEligibilityId()!=null) {
				Eligibility eligibility = validateEligibility(isPresentSubProduct.get().getEligibilityId().toString());
				isPresentSubProduct.get().setEligibilityCode(eligibility.getCode());
				isPresentSubProduct.get().setEligibilityName(eligibility.getName());
			}
			if(isPresentSubProduct.get().getInterestTemplateId()!=null) {
				InterestTemplate interestTemplate = validateInterestTemplate(isPresentSubProduct.get().getInterestTemplateId().toString());
				isPresentSubProduct.get().setInterestTemplateCode(interestTemplate.getCode());
				isPresentSubProduct.get().setInterestTemplateName(interestTemplate.getName());
			}
			if(isPresentSubProduct.get().getRepaymentId()!=null) {
				Repayment repayment = validateRepayment(isPresentSubProduct.get().getRepaymentId().toString());
				isPresentSubProduct.get().setRepaymentCode(repayment.getCode());
				isPresentSubProduct.get().setRepaymentName(repayment.getName());
				
			}
			if(isPresentSubProduct.get().getPredecessorId()!=null) {
				SubProduct predecessorId = validatePredecessorId(isPresentSubProduct.get().getPredecessorId());
				isPresentSubProduct.get().setPredecessorCode(predecessorId.getCode());
				isPresentSubProduct.get().setPredecessorName(predecessorId.getName());
			}
			if(isPresentSubProduct.get().getMarketingStateId()!=null) {
				CommonListItem marketingStateId = getMarketingStateDetails(isPresentSubProduct.get().getMarketingStateId().toString());
				isPresentSubProduct.get().setMarketingStateCode(marketingStateId.getCode());
				isPresentSubProduct.get().setMarketingStateName(marketingStateId.getName());
				isPresentSubProduct.get().setMarketingStateReferenceCode(marketingStateId.getReferenceCode());
			}
			if(isPresentSubProduct.get().getStateTenurePeriodId()!=null) {
				PeriodResponse period = validationService.getPeriod(isPresentSubProduct.get().getTenantId(), isPresentSubProduct.get().getStateTenurePeriodId().toString(), EntityPoint.SUB_PRODUCT);
				isPresentSubProduct.get().setStateTenurePeriodCode(period.getCode());
				isPresentSubProduct.get().setStateTenurePeriodName(period.getName());
				isPresentSubProduct.get().setStateTenurePeriodDescription(period.getDescription());
			}
			
			
			return Optional.ofNullable(isPresentSubProduct.get());
		}
		else {
			return Optional.empty();
		}
	}
	
	@Override
	public List<SubProduct> getByStatus(String status) {
		
		List<SubProduct> subProdList = subProductRepository.findByStatus(status);
		for (SubProduct subProduct : subProdList){
			
			if(subProduct.getProductId()!=null) {
				Product product = validateProduct(subProduct.getProductId().toString());
				subProduct.setProductCode(product.getProductCode());
				subProduct.setProductName(product.getProductName());
			}
			if(subProduct.getFeatureBenifitTemplateId()!=null) {
				FeatureBenifitTemplate featureBenifitTemplate = validateFeatureBenifitTemplate(subProduct.getFeatureBenifitTemplateId().toString());
				subProduct.setFeatureBenifitTemplateCode(featureBenifitTemplate.getCode());
				subProduct.setFeatureBenifitTemplateName(featureBenifitTemplate.getName());
			}
			if(subProduct.getEligibilityId()!=null) {
				Eligibility eligibility = validateEligibility(subProduct.getEligibilityId().toString());
				subProduct.setEligibilityCode(eligibility.getCode());
				subProduct.setEligibilityName(eligibility.getName());
			}
			if(subProduct.getInterestTemplateId()!=null) {
				InterestTemplate interestTemplate = validateInterestTemplate(subProduct.getInterestTemplateId().toString());
				subProduct.setInterestTemplateCode(interestTemplate.getCode());
				subProduct.setInterestTemplateName(interestTemplate.getName());
			}
			if(subProduct.getRepaymentId()!=null) {
				Repayment repayment = validateRepayment(subProduct.getRepaymentId().toString());
				subProduct.setRepaymentCode(repayment.getCode());
				subProduct.setRepaymentName(repayment.getName());
			}
			if(subProduct.getPredecessorId()!=null) {
				SubProduct predecessorId = validatePredecessorId(subProduct.getPredecessorId());
				subProduct.setPredecessorCode(predecessorId.getCode());
				subProduct.setPredecessorName(predecessorId.getName());
			}
			if(subProduct.getMarketingStateId()!=null) {
				CommonListItem marketingStateId = getMarketingStateDetails(subProduct.getMarketingStateId().toString());
				subProduct.setMarketingStateCode(marketingStateId.getCode());
				subProduct.setMarketingStateName(marketingStateId.getName());
				subProduct.setMarketingStateReferenceCode(marketingStateId.getReferenceCode());
			}
			if(subProduct.getStateTenurePeriodId()!=null) {
				PeriodResponse period = validationService.getPeriod(subProduct.getTenantId(), subProduct.getStateTenurePeriodId().toString(), EntityPoint.SUB_PRODUCT);
				subProduct.setStateTenurePeriodCode(period.getCode());
				subProduct.setStateTenurePeriodName(period.getName());
				subProduct.setStateTenurePeriodDescription(period.getDescription());
			}
			
		}
		
		return subProdList;
	}
	
	@Override
	public List<SubProduct> getByName(String name) {
		
		List<SubProduct> subProdList = subProductRepository.findByNameContaining(name);
		for (SubProduct subProduct : subProdList){
			
			if(subProduct.getProductId()!=null) {
				Product product = validateProduct(subProduct.getProductId().toString());
				subProduct.setProductCode(product.getProductCode());
				subProduct.setProductName(product.getProductName());
			}
			if(subProduct.getFeatureBenifitTemplateId()!=null) {
				FeatureBenifitTemplate featureBenifitTemplate = validateFeatureBenifitTemplate(subProduct.getFeatureBenifitTemplateId().toString());
				subProduct.setFeatureBenifitTemplateCode(featureBenifitTemplate.getCode());
				subProduct.setFeatureBenifitTemplateName(featureBenifitTemplate.getName());
			}
			if(subProduct.getEligibilityId()!=null) {
				Eligibility eligibility = validateEligibility(subProduct.getEligibilityId().toString());
				subProduct.setEligibilityCode(eligibility.getCode());
				subProduct.setEligibilityName(eligibility.getName());
			}
			if(subProduct.getInterestTemplateId()!=null) {
				InterestTemplate interestTemplate = validateInterestTemplate(subProduct.getInterestTemplateId().toString());
				subProduct.setInterestTemplateCode(interestTemplate.getCode());
				subProduct.setInterestTemplateName(interestTemplate.getName());
			}
			if(subProduct.getRepaymentId()!=null) {
				Repayment repayment = validateRepayment(subProduct.getRepaymentId().toString());
				subProduct.setRepaymentCode(repayment.getCode());
				subProduct.setRepaymentName(repayment.getName());
			}
			if(subProduct.getPredecessorId()!=null) {
				SubProduct predecessorId = validatePredecessorId(subProduct.getPredecessorId());
				subProduct.setPredecessorCode(predecessorId.getCode());
				subProduct.setPredecessorName(predecessorId.getName());
			}
			if(subProduct.getMarketingStateId()!=null) {
				CommonListItem marketingStateId = getMarketingStateDetails(subProduct.getMarketingStateId().toString());
				subProduct.setMarketingStateCode(marketingStateId.getCode());
				subProduct.setMarketingStateName(marketingStateId.getName());
				subProduct.setMarketingStateReferenceCode(marketingStateId.getReferenceCode());
			}
			if(subProduct.getStateTenurePeriodId()!=null) {
				PeriodResponse period = validationService.getPeriod(subProduct.getTenantId(), subProduct.getStateTenurePeriodId().toString(), EntityPoint.SUB_PRODUCT);
				subProduct.setStateTenurePeriodCode(period.getCode());
				subProduct.setStateTenurePeriodName(period.getName());
				subProduct.setStateTenurePeriodDescription(period.getDescription());
			}
			
		}
		
		return subProdList;
	}
	
	@Override
	public Optional<SubProduct> getByCode(String code) {
		Optional<SubProduct> isPresentSubProduct= subProductRepository.findByCode(code);
		if (isPresentSubProduct.isPresent()) {
			
			if(isPresentSubProduct.get().getProductId()!=null) {
				Product product = validateProduct(isPresentSubProduct.get().getProductId().toString());
				isPresentSubProduct.get().setProductCode(product.getProductCode());
				isPresentSubProduct.get().setProductName(product.getProductName());
			}
			if(isPresentSubProduct.get().getFeatureBenifitTemplateId()!=null) {
				FeatureBenifitTemplate featureBenifitTemplate = validateFeatureBenifitTemplate(isPresentSubProduct.get().getFeatureBenifitTemplateId().toString());
				isPresentSubProduct.get().setFeatureBenifitTemplateCode(featureBenifitTemplate.getCode());
				isPresentSubProduct.get().setFeatureBenifitTemplateName(featureBenifitTemplate.getName());
			}
			if(isPresentSubProduct.get().getEligibilityId()!=null) {
				Eligibility eligibility = validateEligibility(isPresentSubProduct.get().getEligibilityId().toString());
				isPresentSubProduct.get().setEligibilityCode(eligibility.getCode());
				isPresentSubProduct.get().setEligibilityName(eligibility.getName());
			}
			if(isPresentSubProduct.get().getInterestTemplateId()!=null) {
				InterestTemplate interestTemplate = validateInterestTemplate(isPresentSubProduct.get().getInterestTemplateId().toString());
				isPresentSubProduct.get().setInterestTemplateCode(interestTemplate.getCode());
				isPresentSubProduct.get().setInterestTemplateName(interestTemplate.getName());
			}
			if(isPresentSubProduct.get().getRepaymentId()!=null) {
				Repayment repayment = validateRepayment(isPresentSubProduct.get().getRepaymentId().toString());
				isPresentSubProduct.get().setRepaymentCode(repayment.getCode());
				isPresentSubProduct.get().setRepaymentName(repayment.getName());
			}
			if(isPresentSubProduct.get().getPredecessorId()!=null) {
				SubProduct predecessorId = validatePredecessorId(isPresentSubProduct.get().getPredecessorId());
				isPresentSubProduct.get().setPredecessorCode(predecessorId.getCode());
				isPresentSubProduct.get().setPredecessorName(predecessorId.getName());
			}
			if(isPresentSubProduct.get().getMarketingStateId()!=null) {
				CommonListItem marketingStateId = getMarketingStateDetails(isPresentSubProduct.get().getMarketingStateId().toString());
				isPresentSubProduct.get().setMarketingStateCode(marketingStateId.getCode());
				isPresentSubProduct.get().setMarketingStateName(marketingStateId.getName());
				isPresentSubProduct.get().setMarketingStateReferenceCode(marketingStateId.getReferenceCode());
			}
			if(isPresentSubProduct.get().getStateTenurePeriodId()!=null) {
				PeriodResponse period = validationService.getPeriod(isPresentSubProduct.get().getTenantId(), isPresentSubProduct.get().getStateTenurePeriodId().toString(), EntityPoint.SUB_PRODUCT);
				isPresentSubProduct.get().setStateTenurePeriodCode(period.getCode());
				isPresentSubProduct.get().setStateTenurePeriodName(period.getName());
				isPresentSubProduct.get().setStateTenurePeriodDescription(period.getDescription());
			}
			
			return Optional.ofNullable(isPresentSubProduct.get());
		}
		else {
			return Optional.empty();
		}
	}
	
	@Override
	public SubProduct addSubProduct(String tenantId, SubProductAddResource subProductAddResource) {
		LoggerRequest.getInstance().logInfo("SubProduct ### addSubProduct");
		PeriodResponse period;
		
		LoggerRequest.getInstance().logInfo("SubProduct ### validating CODE");
		Optional<SubProduct> isPresentSubProduct = subProductRepository.findByCode(subProductAddResource.getCode());
        if (isPresentSubProduct.isPresent()) 
        	throw new InvalidServiceIdException(environment.getProperty("common.duplicate"), ServiceEntity.CODE, EntityPoint.SUB_PRODUCT);
        
        SubProduct subProduct = new SubProduct();
        subProduct.setTenantId(tenantId);
        subProduct.setCode(subProductAddResource.getCode());
        subProduct.setName(subProductAddResource.getName());
        
        
        if(subProductAddResource.getProductId()!=null) {
        	LoggerRequest.getInstance().logInfo("SubProduct ### INITIATE validateProduct");
            Product product = validateProduct(subProductAddResource.getProductId());
            subProduct.setProduct(product);
        }
        
        if( subProductAddResource.getPredecessorId()!=null && !subProductAddResource.getPredecessorId().isEmpty() ) {
        	LoggerRequest.getInstance().logInfo("SubProduct ### INITIATE validatePredecessorId");
            SubProduct predecessorId = validatePredecessorId(subProductAddResource.getPredecessorId());
            subProduct.setPredecessorId(predecessorId.getId().toString());
        }
        
        if(subProductAddResource.getFeatureBenifitTemplateId()!=null) {
        	LoggerRequest.getInstance().logInfo("SubProduct ### INITIATE validateFeatureBenifitTemplate");
            FeatureBenifitTemplate featureBenifitTemplate = validateFeatureBenifitTemplate(subProductAddResource.getFeatureBenifitTemplateId());
            subProduct.setFeatureBenifitTemplate(featureBenifitTemplate);
        }
        
        if(subProductAddResource.getEligibilityId()!=null) {
        	LoggerRequest.getInstance().logInfo("SubProduct ### INITIATE validateEligibility");
            Eligibility eligibility = validateEligibility(subProductAddResource.getEligibilityId());
            subProduct.setEligibility(eligibility);
        }
        
        if(subProductAddResource.getInterestTemplateId()!=null) {
        	LoggerRequest.getInstance().logInfo("SubProduct ### INITIATE validateInterestTemplate");
            InterestTemplate interestTemplate = validateInterestTemplate(subProductAddResource.getInterestTemplateId());
            subProduct.setInterestTemplate(interestTemplate);
        }
        
        if(subProductAddResource.getRepaymentId()!=null) {
        	LoggerRequest.getInstance().logInfo("SubProduct ### INITIATE validateRepayment");
            Repayment repayment = validateRepayment(subProductAddResource.getRepaymentId());
            subProduct.setRepayment(repayment);
        }
        
        if(subProductAddResource.getRepaymentId()!=null) {
        	LoggerRequest.getInstance().logInfo("SubProduct ### INITIATE validateRepayment");
            Repayment repayment = validateRepayment(subProductAddResource.getRepaymentId());
            subProduct.setRepayment(repayment);
        }
        
        if(subProductAddResource.getFeeChargeId()!=null) {
        	FeeCharge feeCharge = validateFeeCharge(subProductAddResource.getFeeChargeId());
            subProduct.setFeeCharge(feeCharge);
        }
        
        
        if(subProductAddResource.getLoanApplicableRangeId()!=null) {
        	LoanApplicableRange loanApplicableRange = validateLoanApplicableRange(subProductAddResource.getLoanApplicableRangeId());
            subProduct.setLoanApplicableRange(loanApplicableRange);
        }
       
        
		Optional<CoreProduct> iscoreProduct = coreproductRepository
				.findById(new Integer(subProductAddResource.getCoreProductId()).longValue());
		if (!iscoreProduct.isPresent()) {

			throw new InvalidServiceIdException(environment.getProperty(COMMON_INVALID_VALUE),
					ServiceEntity.COREPRODUCT_ID, EntityPoint.SUB_PRODUCT);

		} else {
			LoggerRequest.getInstance().logInfo("SubProduct ### INITIATE setCoreProductId");
			subProduct.setCoreProductId(validationService.stringToLong(subProductAddResource.getCoreProductId()));
		}
        
        if(subProductAddResource.getStateTenurePeriodId()!=null&&!subProductAddResource.getStateTenurePeriodId().isEmpty()) {
	        period = validationService.validatePeriod(tenantId, subProductAddResource.getStateTenurePeriodId(), subProductAddResource.getStateTenurePeriodName(), EntityPoint.SUB_PRODUCT);
	        subProduct.setStateTenurePeriodId(validationService.stringToLong(subProductAddResource.getStateTenurePeriodId()));
        }
        
        LoggerRequest.getInstance().logInfo("SubProduct ### INITIATE validateMarketingStateId");
        CommonListItem marketingStateId = validateMarketingStateId(subProductAddResource.getMarketingStateId(), subProductAddResource.getMarketingStateName(), "MKTSTATE");
        subProduct.setMarketingStateId(marketingStateId.getId());
        
        subProduct.setFirstMarketedDate(dateToTimeStamp(validationServiceStringToDate(subProductAddResource.getFirstMarketedDate())));
        if(subProductAddResource.getLastMarketedDate()!=null) {
        	subProduct.setLastMarketedDate(dateToTimeStamp(validationServiceStringToDate(subProductAddResource.getLastMarketedDate())));
        }
        
        if(subProductAddResource.getStateTenureLength()!=null) {
        	subProduct.setStateTenureLength(validationService.stringToLong(subProductAddResource.getStateTenureLength()));
        }
        subProduct.setStatus(subProductAddResource.getStatus());
        subProduct.setCreatedDate(validationService.getCreateOrModifyDate());
        subProduct.setSyncTs(validationService.getCreateOrModifyDate());
        subProduct.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
        
        return subProductRepository.save(subProduct);
	}
	
	private FeatureBenifitTemplate validateFeatureBenifitTemplate(String id) {
		LoggerRequest.getInstance().logInfo("SubProduct ### IN validateFeatureBenifitTemplate");
			Optional<FeatureBenifitTemplate> featureBenifitTemplate = featureBenifitTemplateRepository.findByIdAndStatus(validationService.stringToLong(id), CommonStatus.ACTIVE);
			
			if(!featureBenifitTemplate.isPresent())
				throw new InvalidServiceIdException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.FEATURE_BENEFIT_ELIGIBILITY_ID, EntityPoint.SUB_PRODUCT);
			else
				return featureBenifitTemplate.get();
		}
	
	private Product validateProduct(String id) {
		LoggerRequest.getInstance().logInfo("SubProduct ### IN validateProduct ");
			Optional<Product> product = productRepository.findByIdAndStatus(validationService.stringToLong(id), CommonStatus.ACTIVE);
			
			if(!product.isPresent()) {
				throw new InvalidServiceIdException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.PRODUCT_ID, EntityPoint.SUB_PRODUCT);
			}
			else {
				return product.get();
			}
		}
	
	private Eligibility validateEligibility(String id) {
		LoggerRequest.getInstance().logInfo("SubProduct ### IN validateEligibility");
			Optional<Eligibility> eligibility = eligibilityRepository.findByIdAndStatus(validationService.stringToLong(id), CommonStatus.ACTIVE);
			
			if(!eligibility.isPresent())
				throw new InvalidServiceIdException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.ELIGIBILITY_ID, EntityPoint.SUB_PRODUCT);
			else
				return eligibility.get();
		}
	
	private InterestTemplate validateInterestTemplate(String id) {
		LoggerRequest.getInstance().logInfo("SubProduct ### IN validateInterestTemplate");
			Optional<InterestTemplate> interestTemplate = interestTemplateRepository.findByIdAndStatus(validationService.stringToLong(id), CommonStatus.ACTIVE);
			
			if(!interestTemplate.isPresent())
				throw new InvalidServiceIdException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.INTEREST_TEMPLATE_ID, EntityPoint.SUB_PRODUCT);
			else
				return interestTemplate.get();
		}
	
	private Repayment validateRepayment(String id) {
		LoggerRequest.getInstance().logInfo("SubProduct ### IN validateRepayment");
			Optional<Repayment> repayment = repaymentRepository.findByIdAndStatus(validationService.stringToLong(id), CommonStatus.ACTIVE);
			
			if(!repayment.isPresent())
				throw new InvalidServiceIdException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.REPAYMENT_ID, EntityPoint.SUB_PRODUCT);
			else
				return repayment.get();
		}
	
	private SubProduct validatePredecessorId(String id) {
		LoggerRequest.getInstance().logInfo("SubProduct ### IN validatePredecessorId");
			Optional<SubProduct> predecessorId = subProductRepository.findByIdAndStatus(validationService.stringToLong(id), "ACTIVE");
			
			if(!predecessorId.isPresent())
				throw new InvalidServiceIdException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.PREDECESSOR_ID, EntityPoint.SUB_PRODUCT);
			else
				return predecessorId.get();
		}
	
	private CommonListItem validateMarketingStateId(String id, String name, String referenceCode) {
		LoggerRequest.getInstance().logInfo("SubProduct ### IN validateMarketingStateId");
			Optional<CommonListItem> marketingStateId = commonListItemRepository.findByIdAndNameAndReferenceCodeAndStatus(validationService.stringToLong(id), name, referenceCode, CommonStatus.ACTIVE);
			
			if(!marketingStateId.isPresent())
				throw new InvalidServiceIdException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.MARKETING_STATE_ID, EntityPoint.SUB_PRODUCT);
			else
				return marketingStateId.get();
		}
	
	private CommonListItem getMarketingStateDetails(String id) {
		LoggerRequest.getInstance().logInfo("SubProduct ### IN getMarketingStateDetails");
			Optional<CommonListItem> marketingStateId = commonListItemRepository.findById(validationService.stringToLong(id));
			
			if(!marketingStateId.isPresent())
				throw new InvalidServiceIdException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.MARKETING_STATE_ID, EntityPoint.SUB_PRODUCT);
			else
				return marketingStateId.get();
		}
	
	public Date validationServiceStringToDate(String date){
		if(date != null) {
	        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	        try {
	            return format.parse(date);
	        } catch (ParseException e) {
	            return null;
	        }
		}else {
			return null;
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
	
	@Override
	public SubProduct updateSubProduct(String tenantId, Long id, SubProductUpdateResource subProductUpdateResource) {
		LoggerRequest.getInstance().logInfo("SubProduct ### updateSubProduct");
		Optional<SubProduct> isPresentSubProduct= subProductRepository.findById(id);

		if (isPresentSubProduct.isPresent()) {
			if (!isPresentSubProduct.get().getVersion().equals(Long.parseLong(subProductUpdateResource.getVersion())))
				throw new InvalidServiceIdException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.VERSION, EntityPoint.SUB_PRODUCT);

			if (!isPresentSubProduct.get().getCode().equalsIgnoreCase(subProductUpdateResource.getCode()))
				throw new InvalidServiceIdException(environment.getProperty("common.code-update"), ServiceEntity.CODE, EntityPoint.SUB_PRODUCT);
			
			Optional<CoreProduct> iscoreProduct = coreproductRepository
					.findById(new Integer(subProductUpdateResource.getCoreProductId()).longValue());
			if (!iscoreProduct.isPresent()) {

				throw new InvalidServiceIdException(environment.getProperty(COMMON_INVALID_VALUE),
						ServiceEntity.COREPRODUCT_ID, EntityPoint.SUB_PRODUCT);

			} 

			LoggerRequest.getInstance().logInfo("SubProduct ### INITIATE Workflow ");
			approveOrGenerateWorkFlow(tenantId, LogginAuthentcation.getInstance().getUserName(), isPresentSubProduct.get(), subProductUpdateResource);

			return isPresentSubProduct.get();
		}else
			throw new ValidateRecordException(environment.getProperty("common.record-not-found"), "message");
	}
	
	private void approveOrGenerateWorkFlow(String tenantId, String user, SubProduct subProduct, SubProductUpdateResource subProductUpdateResource) {
		LoggerRequest.getInstance().logInfo("SubProduct ### approveOrGenerateWorkFlow");
		WorkflowRulesResource workflowRulesResource = null;
		Long processId = null;
		WorkflowType workflowType = WorkflowType.ELIGI_TEMP_APPROVAL;
		LendingWorkflow lendingWorkflow =  null;

		WorkflowInitiationRequestResource workflowInitiationRequestResource = new WorkflowInitiationRequestResource();
		workflowInitiationRequestResource.setApprovalUser(DEFAULT_APPROVAL_USER);
		workflowInitiationRequestResource.setApprovWorkflowType(workflowType);

		LoggerRequest.getInstance().logInfo("SubProduct ### Get Workflow Rules");
		workflowRulesResource = validationService.invokedLendingProductRule(workflowType, DOMAIN_PATH, DOMAIN, tenantId);

		if(workflowRulesResource.getApprovalRequired().equalsIgnoreCase(CommonStatus.YES.toString())) {
			processId = validationService.initiateLendingProductWorkFlow(workflowInitiationRequestResource, tenantId);
				if(processId != null) {
					lendingWorkflow = lendingWorkflowService.save(tenantId, processId, workflowType, user);
					LoggerRequest.getInstance().logInfo("SubProduct ### INITIATE savePendingSubProduct WITH Work Flow");
					savePendingSubProduct(tenantId, subProduct, subProductUpdateResource, lendingWorkflow, user);
				}
		} else {
			LoggerRequest.getInstance().logInfo("SubProduct ### INITIATE savePendingSubProduct WITHOUT Work Flow");
			SubProductPending subProductPending = savePendingSubProduct(tenantId, subProduct, subProductUpdateResource, lendingWorkflow, user);
			LoggerRequest.getInstance().logInfo("SubProduct ### INITIATE updateSubProduct");
			updateSubProduct(subProductPending, subProduct, CommonApproveStatus.APPROVED, user);
		}
	}
	
	private SubProductPending savePendingSubProduct(String tenantId, SubProduct subProduct, SubProductUpdateResource subProductUpdateResource, LendingWorkflow lendingWorkflow, String user) {
		LoggerRequest.getInstance().logInfo("SubProduct ### IN savePendingSubProduct");
			
		LoggerRequest.getInstance().logInfo("SubProduct ### INITIATE Save History");
		subProductHistoryService.saveHistory(tenantId, subProduct, user);
		PeriodResponse period;	
		SubProduct subProd = subProduct;
		subProd.setApproveStatus("PENDING");
		subProd.setModifiedUser(user);
		subProd.setModifiedDate(validationService.getCreateOrModifyDate());
		subProd.setSyncTs(validationService.getCreateOrModifyDate());
		subProductRepository.saveAndFlush(subProd);
			
		SubProductPending subProductPending = new SubProductPending();
			
		subProductPending.setTenantId(tenantId);
			if(lendingWorkflow != null)
				subProductPending.setLendingWorkflow(lendingWorkflow);
			subProductPending.setCode(subProductUpdateResource.getCode());
			subProductPending.setName(subProductUpdateResource.getName());
			subProductPending.setSubProduct(subProduct);
			
			if(subProductUpdateResource.getProductId()!=null) {
	        	LoggerRequest.getInstance().logInfo("SubProduct ### INITIATE validateProduct");
	            Product product = validateProduct(subProductUpdateResource.getProductId());
	            subProductPending.setProduct(product);
	        }
	        
	        if(subProductUpdateResource.getPredecessorId()!=null) {
	        	LoggerRequest.getInstance().logInfo("SubProduct ### INITIATE validatePredecessorId");
	            SubProduct predecessorId = validatePredecessorId(subProductUpdateResource.getPredecessorId());
	            subProductPending.setPredecessorId(predecessorId.getId().toString());
	        }
	        
	        if(subProductUpdateResource.getFeatureBenifitTemplateId()!=null) {
	        	LoggerRequest.getInstance().logInfo("SubProduct ### INITIATE validateFeatureBenifitTemplate");
	            FeatureBenifitTemplate featureBenifitTemplate = validateFeatureBenifitTemplate(subProductUpdateResource.getFeatureBenifitTemplateId());
	            subProductPending.setFeatureBenifitTemplate(featureBenifitTemplate);
	        }
	        
	        if(subProductUpdateResource.getEligibilityId()!=null) {
	        	LoggerRequest.getInstance().logInfo("SubProduct ### INITIATE validateEligibility");
	            Eligibility eligibility = validateEligibility(subProductUpdateResource.getEligibilityId());
	            subProductPending.setEligibility(eligibility);
	        }
	        
	        if(subProductUpdateResource.getInterestTemplateId()!=null) {
	        	LoggerRequest.getInstance().logInfo("SubProduct ### INITIATE validateInterestTemplate");
	            InterestTemplate interestTemplate = validateInterestTemplate(subProductUpdateResource.getInterestTemplateId());
	            subProductPending.setInterestTemplate(interestTemplate);
	        }
	        
	        if(subProductUpdateResource.getRepaymentId()!=null) {
	        	LoggerRequest.getInstance().logInfo("SubProduct ### INITIATE validateRepayment");
	            Repayment repayment = validateRepayment(subProductUpdateResource.getRepaymentId());
	            subProductPending.setRepayment(repayment);
	        }
	        
	        if(subProductUpdateResource.getFeeChargeId()!=null) {
	        	FeeCharge feeCharge = validateFeeCharge(subProductUpdateResource.getFeeChargeId());
	            subProduct.setFeeCharge(feeCharge);
	        }
	        
	        
	        if(subProductUpdateResource.getLoanApplicableRangeId()!=null) {
	        	LoanApplicableRange loanApplicableRange = validateLoanApplicableRange(subProductUpdateResource.getLoanApplicableRangeId());
	            subProduct.setLoanApplicableRange(loanApplicableRange);
	        }
	        
	        if(subProductUpdateResource.getCoreProductId()!=null) {
	        	LoggerRequest.getInstance().logInfo("SubProduct ### INITIATE setCoreProductId");
	        	subProductPending.setCoreProductId(validationService.stringToLong(subProductUpdateResource.getCoreProductId()));// Need to validate in future
	        }
	        
	        if(subProductUpdateResource.getStateTenurePeriodId()!=null&&!subProductUpdateResource.getStateTenurePeriodId().isEmpty()) {
		        period = validationService.validatePeriod(tenantId, subProductUpdateResource.getStateTenurePeriodId(), subProductUpdateResource.getStateTenurePeriodName(), EntityPoint.SUB_PRODUCT);
		        subProductPending.setStateTenurePeriodId(validationService.stringToLong(subProductUpdateResource.getStateTenurePeriodId()));
	        }
			
	        LoggerRequest.getInstance().logInfo("SubProduct ### INITIATE validateMarketingStateId "+subProductUpdateResource.getMarketingStateId());
	        CommonListItem marketingStateId = validateMarketingStateId(subProductUpdateResource.getMarketingStateId(), subProductUpdateResource.getMarketingStateName(), "MKTSTATE");
	        subProductPending.setMarketingStateId(validationService.stringToLong(subProductUpdateResource.getMarketingStateId()));
	        
	        subProductPending.setFirstMarketedDate(dateToTimeStamp(validationServiceStringToDate(subProductUpdateResource.getFirstMarketedDate())));
	        
	        if(subProductUpdateResource.getLastMarketedDate()!=null) {
	        	subProductPending.setLastMarketedDate(dateToTimeStamp(validationServiceStringToDate(subProductUpdateResource.getLastMarketedDate())));
	        }
	        
	        if(subProductUpdateResource.getStateTenureLength()!=null) {
	        	subProductPending.setStateTenureLength(validationService.stringToLong(subProductUpdateResource.getStateTenureLength()));
	        }
	        
			subProductPending.setStatus(subProductUpdateResource.getStatus());
			subProductPending.setCreatedDate(validationService.getCreateOrModifyDate());
			subProductPending.setCreatedUser(user);
			subProductPending.setSyncTs(validationService.getCreateOrModifyDate());
			
			return subProductPendingRepository.save(subProductPending);
			
		}
	
	private void updateSubProduct(SubProductPending subProductPending, SubProduct subProd, CommonApproveStatus approveStatus, String user) {
		LoggerRequest.getInstance().logInfo("SubProduct ### IN updateSubProduct");
			
		LoggerRequest.getInstance().logInfo("SubProduct ### INITIATE Save History");
		subProductHistoryService.saveHistory(subProd.getTenantId(), subProd, user);
			
		SubProduct subProduct = subProd;
			
			
			if(approveStatus.toString().equalsIgnoreCase(CommonApproveStatus.APPROVED.toString())) {
				
				
				subProduct.setCode(subProductPending.getCode());
				subProduct.setName(subProductPending.getName());
				
				subProduct.setProduct(subProductPending.getProduct());
				subProduct.setPredecessorId(subProductPending.getPredecessorId());
		        subProduct.setFeatureBenifitTemplate(subProductPending.getFeatureBenifitTemplate());
		        subProduct.setEligibility(subProductPending.getEligibility());
		       	subProduct.setInterestTemplate(subProductPending.getInterestTemplate());
		       	subProduct.setRepayment(subProductPending.getRepayment());
		       	subProduct.setCoreProductId(subProductPending.getCoreProductId());// Need to validate in future
		       	subProduct.setStateTenurePeriodId(subProductPending.getStateTenurePeriodId());
		        subProduct.setMarketingStateId(subProductPending.getMarketingStateId());
		        subProduct.setLoanApplicableRange(subProductPending.getLoanApplicableRange());
		        subProduct.setFeeCharge(subProductPending.getFeeCharge());
		        
		        subProduct.setFirstMarketedDate(subProductPending.getFirstMarketedDate());
		        subProduct.setLastMarketedDate(subProductPending.getLastMarketedDate());
				
				
				subProduct.setStatus(subProductPending.getStatus());
				subProduct.setModifiedDate(subProductPending.getPendingApprovedDate());
				subProduct.setModifiedUser(subProductPending.getPendingApprovedUser());
				subProduct.setApproveStatus("APPROVED");
				subProduct.setPendingApprovedUser(user);
				subProduct.setPendingApprovedDate(validationService.getCreateOrModifyDate());
			} else {
				subProduct.setPendingRejectedUser(user);
				subProduct.setPendingRejectedDate(validationService.getCreateOrModifyDate());
			} 
			subProduct.setSyncTs(validationService.getCreateOrModifyDate());
			
			subProductRepository.saveAndFlush(subProduct);
		}
	
	@Override
	public boolean approveReject(String tenantId, Long id, WorkflowStatus workflowStatus) {
		LoggerRequest.getInstance().logInfo("SubProduct ### IN approveReject");
		WorkflowInitiationRequestResource workflowInitiationRequestResource = new WorkflowInitiationRequestResource();
		workflowInitiationRequestResource.setApprovalUser(DEFAULT_APPROVAL_USER);
		workflowInitiationRequestResource.setApprovWorkflowType(WorkflowType.ELIGI_TEMP_APPROVAL);
		WorkflowRulesResource workflowRulesResource = null;
		WorkflowType workflowType = WorkflowType.ELIGI_TEMP_APPROVAL;
		String user = LogginAuthentcation.getInstance().getUserName();
		CommonApproveStatus approveStatus= null;
	
		Optional<SubProductPending> isPresentSubProductPending= subProductPendingRepository.findById(id);
		
		Optional<SubProduct> subProd = subProductRepository.findById(isPresentSubProductPending.get().getSubProduct().getId());
		
		if(!isPresentSubProductPending.get().getSubProduct().getApproveStatus().equals("PENDING"))
			throw new ValidateRecordException(environment.getProperty("common.can-not-rej-app"), "message");
		
		Optional<LendingWorkflow> lendingWorkflow = lendingWorkflowRepository.findById(isPresentSubProductPending.get().getLendingWorkflow().getId());
	
		LoggerRequest.getInstance().logInfo("SubProduct ### INITIATE Get Workflow Rules");
		workflowRulesResource = validationService.invokedLendingProductRule(workflowType, DOMAIN_PATH, DOMAIN, tenantId);
	
		if(workflowRulesResource.getApprovalAllowed().equalsIgnoreCase(CommonStatus.NO.toString())) {
			if(lendingWorkflow.get().getCreatedUser().equalsIgnoreCase(LogginAuthentcation.getInstance().getUserName()))
				throw new ValidateRecordException(environment.getProperty("cannot.process.approve"), "message");
		}
	
		if(workflowStatus.toString().equalsIgnoreCase(WorkflowStatus.COMPLETE.toString())) {
			LoggerRequest.getInstance().logInfo("SubProduct ### INITIATE Approve Workflow");
			validationService.approveWorkFlow(lendingWorkflow.get().getWorkflowProcessId(), workflowType, user, tenantId);
			approveStatus = CommonApproveStatus.APPROVED;
		}
		else {
			LoggerRequest.getInstance().logInfo("SubProduct ### INITIATE Abort Workflow");
			validationService.abortedWorkFlow(lendingWorkflow.get().getWorkflowProcessId(), workflowType, user, tenantId);
			approveStatus = CommonApproveStatus.REJECTED;
		}
	
		lendingWorkflowService.update(lendingWorkflow.get(), workflowStatus, user);
		
		LoggerRequest.getInstance().logInfo("SubProduct ### INITIATE updateEligibility");
		updateSubProduct(isPresentSubProductPending.get(), subProd.get(), approveStatus, user);
		
		return true;
	}
	
	@Override
	public Optional<SubProductPending> getByPendingId(Long pendingId) {
		Optional<SubProductPending> isPresentSubProductPending= subProductPendingRepository.findById(pendingId);
		if (isPresentSubProductPending.isPresent()) {
			
			if(isPresentSubProductPending.get().getProductId()!=null) {
				Product product = validateProduct(isPresentSubProductPending.get().getProductId().toString());
				isPresentSubProductPending.get().setProductCode(product.getProductCode());
				isPresentSubProductPending.get().setProductName(product.getProductName());
			}
			if(isPresentSubProductPending.get().getFeatureBenifitTemplateId()!=null) {
				FeatureBenifitTemplate featureBenifitTemplate = validateFeatureBenifitTemplate(isPresentSubProductPending.get().getFeatureBenifitTemplateId().toString());
				isPresentSubProductPending.get().setFeatureBenifitTemplateCode(featureBenifitTemplate.getCode());
				isPresentSubProductPending.get().setFeatureBenifitTemplateName(featureBenifitTemplate.getName());
			}
			if(isPresentSubProductPending.get().getEligibilityId()!=null) {
				Eligibility eligibility = validateEligibility(isPresentSubProductPending.get().getEligibilityId().toString());
				isPresentSubProductPending.get().setEligibilityCode(eligibility.getCode());
				isPresentSubProductPending.get().setEligibilityName(eligibility.getName());
			}
			if(isPresentSubProductPending.get().getInterestTemplateId()!=null) {
				InterestTemplate interestTemplate = validateInterestTemplate(isPresentSubProductPending.get().getInterestTemplateId().toString());
				isPresentSubProductPending.get().setInterestTemplateCode(interestTemplate.getCode());
				isPresentSubProductPending.get().setInterestTemplateName(interestTemplate.getName());
			}
			if(isPresentSubProductPending.get().getRepaymentId()!=null) {
				Repayment repayment = validateRepayment(isPresentSubProductPending.get().getRepaymentId().toString());
				isPresentSubProductPending.get().setRepaymentCode(repayment.getCode());
				isPresentSubProductPending.get().setRepaymentName(repayment.getName());
			}
			if(isPresentSubProductPending.get().getPredecessorId()!=null) {
				SubProduct predecessorId = validatePredecessorId(isPresentSubProductPending.get().getPredecessorId());
				isPresentSubProductPending.get().setPredecessorCode(predecessorId.getCode());
				isPresentSubProductPending.get().setPredecessorName(predecessorId.getName());
			}
			if(isPresentSubProductPending.get().getMarketingStateId()!=null) {
				CommonListItem marketingStateId = getMarketingStateDetails(isPresentSubProductPending.get().getMarketingStateId().toString());
				isPresentSubProductPending.get().setMarketingStateCode(marketingStateId.getCode());
				isPresentSubProductPending.get().setMarketingStateName(marketingStateId.getName());
				isPresentSubProductPending.get().setMarketingStateReferenceCode(marketingStateId.getReferenceCode());
			}
			if(isPresentSubProductPending.get().getStateTenurePeriodId()!=null) {
				PeriodResponse period = validationService.getPeriod(isPresentSubProductPending.get().getTenantId(), isPresentSubProductPending.get().getStateTenurePeriodId().toString(), EntityPoint.SUB_PRODUCT);
				isPresentSubProductPending.get().setStateTenurePeriodCode(period.getCode());
				isPresentSubProductPending.get().setStateTenurePeriodName(period.getName());
				isPresentSubProductPending.get().setStateTenurePeriodDescription(period.getDescription());
			}
			
			return Optional.ofNullable(isPresentSubProductPending.get());
		}
		else {
			return Optional.empty();
		}
	}
	
	
	@Override
	public SubProduct updateSubProductLoanApplicableId(Long id ,SubProductLoanApplicableUpdateResource subProductLoanApplicableUpdateResource) {
		
		Optional<SubProduct> subProductOptional = subProductRepository.findById(id);
		if(!subProductOptional.isPresent()) {
			throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), "id");
		}
		
		SubProduct subProduct = subProductOptional.get();
		
		Optional<LoanApplicableRange> loanApplicableRangeOptional = loanApplicableRangeRepository.findById(validationService.stringToLong(subProductLoanApplicableUpdateResource.getLoanApplicableRangeId()));
		if(!loanApplicableRangeOptional.isPresent()) {
			throw new ValidateRecordException(environment.getProperty("loanApplicableRange.id-invalid"), MESSAGE);
		}		
		if((CommonStatus.INACTIVE).equals(loanApplicableRangeOptional.get().getStatus())) {
			throw new ValidateRecordException(environment.getProperty("loanApplicableRange.status-inactive"), MESSAGE);
		}		
		if(!(loanApplicableRangeOptional.get().getCode()).equals(subProductLoanApplicableUpdateResource.getLoanApplicableRangeCode())) {
			throw new ValidateRecordException(environment.getProperty("loanApplicableRange.code-incompatible"), MESSAGE);			
		}
		
		
		subProduct.setLoanApplicableRange(loanApplicableRangeOptional.get());
		return subProductRepository.save(subProduct);
	}
	
	
	private FeeCharge validateFeeCharge(String id) {
			Optional<FeeCharge> feeCharge = feeChargeRepository.findById(validationService.stringToLong(id));
			
			if(!feeCharge.isPresent()) {
				throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), "feeChargeId");
			}
			if((CommonStatus.INACTIVE).equals(feeCharge.get().getStatus())) {
				throw new ValidateRecordException(environment.getProperty("loanApplicableRange.status-inactive"), "feeChargeId");
			}	
			
			return feeCharge.get();

	}
	
	private LoanApplicableRange validateLoanApplicableRange(String id) {
		Optional<LoanApplicableRange> loanApplicableRangeOptional = loanApplicableRangeRepository.findById(validationService.stringToLong(id));
		if(!loanApplicableRangeOptional.isPresent()) {
			throw new ValidateRecordException(environment.getProperty("loanApplicableRange.id-invalid"), MESSAGE);
		}		
		if((CommonStatus.INACTIVE).equals(loanApplicableRangeOptional.get().getStatus())) {
			throw new ValidateRecordException(environment.getProperty("loanApplicableRange.status-inactive"), MESSAGE);
		}
		
		return loanApplicableRangeOptional.get();
	}

}
