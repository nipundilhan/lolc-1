package com.fusionx.lending.product.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.ApplicationFrequency;
import com.fusionx.lending.product.domain.CoreProduct;
import com.fusionx.lending.product.domain.DocumentCheckList;
import com.fusionx.lending.product.domain.DocumentCheckListDetails;
import com.fusionx.lending.product.domain.FeeCharge;
import com.fusionx.lending.product.domain.Product;
import com.fusionx.lending.product.domain.RepaymentFrequency;
import com.fusionx.lending.product.domain.SubProduct;
import com.fusionx.lending.product.domain.TcAdhocCharges;
import com.fusionx.lending.product.domain.TcFixedCharges;
import com.fusionx.lending.product.domain.TcHeader;
import com.fusionx.lending.product.domain.TcOptionsCharges;
import com.fusionx.lending.product.domain.TcRevolvingDetail;
import com.fusionx.lending.product.domain.TcRevolvingPaymentSchedule;
import com.fusionx.lending.product.enums.CalculateMethodEnum;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.enums.EntityPoint;
import com.fusionx.lending.product.enums.InterestRepaymentMethod;
import com.fusionx.lending.product.enums.RateOrAmount;
import com.fusionx.lending.product.enums.RepaymentCriteriaEnum;
import com.fusionx.lending.product.enums.ServiceEntity;
import com.fusionx.lending.product.exception.InvalidServiceIdException;
import com.fusionx.lending.product.repository.ApplicationFrequencyRepository;
import com.fusionx.lending.product.repository.FeeChargeRepository;
import com.fusionx.lending.product.repository.ProductRepository;
import com.fusionx.lending.product.repository.RepaymentFrequencyRepository;
import com.fusionx.lending.product.repository.SubProductRepository;
import com.fusionx.lending.product.repository.TcAdhocChargesRepository;
import com.fusionx.lending.product.repository.TcFixedChargesRepository;
import com.fusionx.lending.product.repository.TcHeaderRepository;
import com.fusionx.lending.product.repository.TcOptionsChargesRepository;
import com.fusionx.lending.product.repository.TcRevolvingDetailRepository;
import com.fusionx.lending.product.repository.TcRevolvingPaymentScheduleRepository;
import com.fusionx.lending.product.resources.AdhocChargesListResource;
import com.fusionx.lending.product.resources.AdhocChargesResource;
import com.fusionx.lending.product.resources.Currency;
import com.fusionx.lending.product.resources.FixedChargesResource;
import com.fusionx.lending.product.resources.FrequencyResponse;
import com.fusionx.lending.product.resources.OptionalChargesListResource;
import com.fusionx.lending.product.resources.OptionalChargesResource;
import com.fusionx.lending.product.resources.RevolvingDetailsResource;
import com.fusionx.lending.product.resources.RevolvingLoanCalculatorAddResource;
import com.fusionx.lending.product.resources.RevolvingPaymentScheduleResource;
import com.fusionx.lending.product.service.TcRevolvingCalculationService;
import com.fusionx.lending.product.service.ValidationService;

/**
 * API Service related to Tc Revolving gCalculation
 *
 * @author Dilhan Jayasinghe
 * @version 1.0.0
 * @since 1.0.0
 * <p>
 * <br/><br/>
 * <b>Change History : </b>
 * <br/><br/>
 * #        Date            Story Point     Task No     Author                  Description
 * <br/>
 * .....................................................................................................................<br/>
 * 1        05-10-2021      -               FXL-994     Dilhan Jayasinghe        Created
 * <p>
 */
@Component
@Transactional(rollbackFor = Exception.class)
public class TcRevolvingCalculationServiceImpl extends MessagePropertyBase implements TcRevolvingCalculationService{

	@Autowired
	ValidationService validationService;
	
	@Autowired
	TcHeaderRepository tcHeaderRepository;
	
	@Autowired
	FeeChargeRepository feeChargeRepository;
	
	@Autowired
	ApplicationFrequencyRepository applicationFrequencyRepository;
	
	@Autowired
	TcRevolvingDetailRepository tcRevolvingDetailRepository;
	
	@Autowired
	TcAdhocChargesRepository tcAdhocChargesRepository;
	
	@Autowired
	TcFixedChargesRepository tcFixedChargesRepository;
	
	@Autowired
	TcOptionsChargesRepository tcOptionsChargesRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private SubProductRepository subProductRepository;
	
	@Autowired
	private RepaymentFrequencyRepository repaymentFrequencyRepository;
	
	@Autowired
	TcRevolvingPaymentScheduleRepository tcRevolvingPaymentScheduleRepository;

	@Override
	public TcHeader saveRevolvingDetails(String tenantId, RevolvingLoanCalculatorAddResource revolvingLoanCalculatorAddResource) {
		
		TcRevolvingDetail tcRevolvingDetail = createTcRevolvingDetail(tenantId,revolvingLoanCalculatorAddResource.getRevolvingDetails());
		
		createTcRevolvingPaymentSchedule(tenantId,tcRevolvingDetail, revolvingLoanCalculatorAddResource.getRevolvingDetails().getRevolvingPaymentSchedule());
		
		//create tc header
		Currency currency = validationService.validateCurrency(tenantId, revolvingLoanCalculatorAddResource.getCurrencyId(), revolvingLoanCalculatorAddResource.getCurrencyName(), EntityPoint.TC_REVOLVING_CALCULATION);
		
        Product product = validateProduct(validationService.stringToLong(revolvingLoanCalculatorAddResource.getProductId()), revolvingLoanCalculatorAddResource.getProductName());
		
		SubProduct subProduct = validateSubProduct(validationService.stringToLong(revolvingLoanCalculatorAddResource.getSubProductId()), revolvingLoanCalculatorAddResource.getSubProductName());
		
		RepaymentFrequency repaymentFrequency = validateRepaymentFrequency(validationService.stringToLong(revolvingLoanCalculatorAddResource.getRepaymentFrequencyId()), revolvingLoanCalculatorAddResource.getRepaymentFrequencyName());
		
		TcHeader tcHeader = new TcHeader();
		tcHeader.setTenantId(tenantId);
		tcHeader.setProduct(product);
		tcHeader.setSubProduct(subProduct);
		tcHeader.setRepaymentFrequency(repaymentFrequency);
		tcHeader.setCurrencyId(validationService.stringToLong(revolvingLoanCalculatorAddResource.getCurrencyId()));
		tcHeader.setTcRevolvingDetail(tcRevolvingDetail);
		tcHeader.setGracePeriod((validationService.stringToLong(revolvingLoanCalculatorAddResource.getGracePeriod())));
		tcHeader.setLeadId(validationService.stringToLong(revolvingLoanCalculatorAddResource.getLeadId()));
		tcHeader.setPenalInterestRate(validationService.stringToBigDecimal(revolvingLoanCalculatorAddResource.getPenalRate()));
		tcHeader.setCurrencyCode(currency.getCurrencyCode());
		tcHeader.setCurrencyCodeNumeric(currency.getCodeNumeric());
		tcHeader.setDueDate(2021L);
		tcHeader.setRepaymentCriteria(RepaymentCriteriaEnum.IN_ARRERS);
		tcHeader.setTcExpiryDate(validationService.getCreateOrModifyDate());
		tcHeader.setTerm(validationService.stringToLong(revolvingLoanCalculatorAddResource.getTerm()));
		tcHeader.setCalculationMethod(CalculateMethodEnum.valueOf(revolvingLoanCalculatorAddResource.getCalculationMethodName()));
		tcHeader.setInterestRate(validationService.stringToBigDecimal(revolvingLoanCalculatorAddResource.getInterestRate()));
		tcHeader.setLoanAmount(validationService.stringToBigDecimal(revolvingLoanCalculatorAddResource.getLoanAmount()));
		tcHeader.setInterestRate(validationService.stringToBigDecimal(revolvingLoanCalculatorAddResource.getInterestRate()));
		tcHeader.setCreatedDate(validationService.getCreateOrModifyDate());
		tcHeader.setSyncTs(validationService.getCreateOrModifyDate());
		tcHeader.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
		tcHeader = tcHeaderRepository.save(tcHeader);
		
		
		//create fixed charges
		if(revolvingLoanCalculatorAddResource.getFixedChargesDetails() != null && !revolvingLoanCalculatorAddResource.getFixedChargesDetails().isEmpty()) {
			
			for(FixedChargesResource item : revolvingLoanCalculatorAddResource.getFixedChargesDetails()) {
				
				FeeCharge feeCharge = validateFeeCharge(validationService.stringToLong(item.getChargeTypeId()), item.getChargeTypeName());
				
				ApplicationFrequency applicationFrequency = validateApplicationFrequency(validationService.stringToLong(item.getCriteriaId()), item.getCriteriaName());
				
				TcFixedCharges tcFixedCharges = new TcFixedCharges();
				tcFixedCharges.setTenantId(tenantId);
//				tcFixedCharges.setFeeCharge(feeCharge);
				tcFixedCharges.setCriteria(applicationFrequency);
				tcFixedCharges.setTcHeader(tcHeader);
				tcFixedCharges.setRateOrAmount(RateOrAmount.valueOf(item.getCalculationMethod()));
				tcFixedCharges.setRateAmount(validationService.stringToBigDecimal((item.getRateOrAmount())));
				tcFixedCharges.setTotalChargeAmount(validationService.stringToBigDecimal((item.getTotalChargeAmount())));
				tcFixedCharges.setStatus(CommonStatus.valueOf(item.getStatus()));
				tcFixedCharges.setCreatedDate(validationService.getCreateOrModifyDate());
				tcFixedCharges.setSyncTs(validationService.getCreateOrModifyDate());
				tcFixedCharges.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
				tcFixedChargesRepository.save(tcFixedCharges);
			}
		}
		
		//create optional charges
		if(revolvingLoanCalculatorAddResource.getOptionalChargesDetails() != null && !revolvingLoanCalculatorAddResource.getOptionalChargesDetails().isEmpty()) {
			
			for(OptionalChargesResource item : revolvingLoanCalculatorAddResource.getOptionalChargesDetails()) {
				
                FeeCharge feeCharge = validateFeeCharge(validationService.stringToLong(item.getChargeTypeId()), item.getChargeTypeName());
				
				ApplicationFrequency applicationFrequency = validateApplicationFrequency(validationService.stringToLong(item.getCriteriaId()), item.getCriteriaName());
				
				if(item.getOptionalChargesList() != null && !item.getOptionalChargesList().isEmpty()) {
					
					for(OptionalChargesListResource itm : item.getOptionalChargesList()) {
						
						TcOptionsCharges tcOptionsCharges = new TcOptionsCharges();
						tcOptionsCharges.setTenantId(tenantId);
//						tcOptionsCharges.setFeeCharge(feeCharge);
						tcOptionsCharges.setCriteria(applicationFrequency);
						tcOptionsCharges.setRateAmount(validationService.stringToBigDecimal((itm.getRateAmount())));
						tcOptionsCharges.setRateOrAmount(RateOrAmount.valueOf(itm.getCalculationMethod()));
						tcOptionsCharges.setTotalChargeAmount(validationService.stringToBigDecimal((itm.getTotalChargeAmount())));
						tcOptionsCharges.setOption(itm.getOption());
						tcOptionsCharges.setTcHeader(tcHeader);
						tcOptionsCharges.setStatus(CommonStatus.valueOf(item.getStatus()));
						tcOptionsCharges.setCreatedDate(validationService.getCreateOrModifyDate());
						tcOptionsCharges.setSyncTs(validationService.getCreateOrModifyDate());
						tcOptionsCharges.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
						tcOptionsChargesRepository.save(tcOptionsCharges);
					}
				}
				
				
			}
			
		}
		
		//create adhoc charges
		if(revolvingLoanCalculatorAddResource.getAdhocChargesDetails() != null && !revolvingLoanCalculatorAddResource.getAdhocChargesDetails().isEmpty()) {
			
           for(AdhocChargesResource item : revolvingLoanCalculatorAddResource.getAdhocChargesDetails()) {
				
                FeeCharge feeCharge = validateFeeCharge(validationService.stringToLong(item.getChargeTypeId()), item.getChargeTypeName());
				
				ApplicationFrequency applicationFrequency = validateApplicationFrequency(validationService.stringToLong(item.getCriteriaId()), item.getCriteriaName());
				
				FrequencyResponse frequencyResponse = validationService.validateFrequency(tenantId, validationService.stringToLong(item.getFrequencyId()), item.getFrequencyName(), validationService.stringToLong(item.getUnit()), EntityPoint.TC_REVOLVING_CALCULATION);
				
				if(item.getAdhocChargesList() != null && !item.getAdhocChargesList().isEmpty()) {
					
					for(AdhocChargesListResource itm : item.getAdhocChargesList()) {
						
						TcAdhocCharges tcAdhocCharges = new TcAdhocCharges();
						tcAdhocCharges.setTenantId(tenantId);
//						tcAdhocCharges.setFeeCharge(feeCharge);
						tcAdhocCharges.setCriteria(applicationFrequency);
						tcAdhocCharges.setPeriod(itm.getPeriod());
						tcAdhocCharges.setTcHeader(tcHeader);
						tcAdhocCharges.setFrequencyId(validationService.stringToLong(item.getFrequencyId()));
						tcAdhocCharges.setFrequencyCode(frequencyResponse.getCode());
						tcAdhocCharges.setRateAmount(validationService.stringToBigDecimal((itm.getRateAmount())));
						tcAdhocCharges.setRateOrAmount(RateOrAmount.valueOf(itm.getCalculationMethod()));
						tcAdhocCharges.setTotalChargeAmount(validationService.stringToBigDecimal((itm.getTotalChargeAmount())));
						tcAdhocCharges.setStatus(CommonStatus.valueOf(item.getStatus()));
						tcAdhocCharges.setCreatedDate(validationService.getCreateOrModifyDate());
						tcAdhocCharges.setSyncTs(validationService.getCreateOrModifyDate());
						tcAdhocCharges.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
						tcAdhocChargesRepository.save(tcAdhocCharges);
					}
				}
				
				
			}
		}
		
		return tcHeader;
	}


	private void createTcRevolvingPaymentSchedule(String tenantId, TcRevolvingDetail tcRevolvingDetail,List<RevolvingPaymentScheduleResource> revolvingPaymentSchedule) {
		
		for(RevolvingPaymentScheduleResource item : revolvingPaymentSchedule) {
			
			TcRevolvingPaymentSchedule tcRevolvingPaymentSchedule = new TcRevolvingPaymentSchedule();
			tcRevolvingPaymentSchedule.setAmount(validationService.stringToBigDecimal(item.getAmount()));
			tcRevolvingPaymentSchedule.setDueDate(null);
			tcRevolvingPaymentSchedule.setTrasactionTypeCode(item.getTransactionTypeCode());
			tcRevolvingPaymentSchedule.setSequence(10L);
			tcRevolvingPaymentSchedule.setTenantId(tenantId);
			tcRevolvingPaymentSchedule.setDescription(item.getDescription());
			tcRevolvingPaymentSchedule.setTcRevolvingDetail(tcRevolvingDetail);
			tcRevolvingPaymentSchedule.setStatus(CommonStatus.valueOf(item.getStatus()));
			tcRevolvingPaymentSchedule.setCreatedDate(validationService.getCreateOrModifyDate());
			tcRevolvingPaymentSchedule.setSyncTs(validationService.getCreateOrModifyDate());
			tcRevolvingPaymentSchedule.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
			tcRevolvingPaymentScheduleRepository.save(tcRevolvingPaymentSchedule);
		}
		
	}

	private TcRevolvingDetail createTcRevolvingDetail(String tenantId, RevolvingDetailsResource revolvingDetails) {
		
		TcRevolvingDetail tcRevolvingDetail = new TcRevolvingDetail();
		tcRevolvingDetail.setTenantId(tenantId);
		tcRevolvingDetail.setInitialDisbursementAmount(validationService.stringToBigDecimal(revolvingDetails.getInitialDisbursement()));
		tcRevolvingDetail.setRepaymentMethod(InterestRepaymentMethod.valueOf(revolvingDetails.getInterestPaymentMethod()));
		tcRevolvingDetail.setStatus(CommonStatus.valueOf(revolvingDetails.getStatus()));
		tcRevolvingDetail.setCreatedDate(validationService.getCreateOrModifyDate());
		tcRevolvingDetail.setSyncTs(validationService.getCreateOrModifyDate());
		tcRevolvingDetail.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
		return tcRevolvingDetailRepository.save(tcRevolvingDetail);
	}
	
	private Date formatDate(String date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return format.parse(date);
		} catch (ParseException e) {
			return null;
		}
		
	}
	
    public Product validateProduct(Long id, String name) {
		
		Optional<Product> product = productRepository.findByIdAndProductNameAndStatus(id, name, CommonStatus.ACTIVE);
		if (!product.isPresent()) {
			throw new InvalidServiceIdException(environment.getProperty(RECORD_NOT_FOUND), ServiceEntity.PRODUCT_ID, EntityPoint.TC_REVOLVING_CALCULATION);
		}
		
		return product.get();
	}
	
	public SubProduct validateSubProduct(Long id, String name) {
		
		Optional<SubProduct> subProduct = subProductRepository.findByIdAndNameAndStatus(id, name, CommonStatus.ACTIVE.toString());
		if (!subProduct.isPresent()) {
			throw new InvalidServiceIdException(environment.getProperty(RECORD_NOT_FOUND), ServiceEntity.SUB_PRODUCT_ID, EntityPoint.TC_REVOLVING_CALCULATION);
		}
		
		return subProduct.get();
	}
	
	private RepaymentFrequency validateRepaymentFrequency(Long id, String name) {
		
		Optional<RepaymentFrequency> repaymentFrequency = repaymentFrequencyRepository.findByIdAndNameAndStatus(id, name, CommonStatus.ACTIVE);
		if (!repaymentFrequency.isPresent()) {
			throw new InvalidServiceIdException(environment.getProperty(RECORD_NOT_FOUND), ServiceEntity.REPAYMENT_FREQUENCY_ID, EntityPoint.TC_REVOLVING_CALCULATION);
		}
		
		return repaymentFrequency.get();
	}
	
	private FeeCharge validateFeeCharge(Long id, String name) {
		
		Optional<FeeCharge> feeCharge = feeChargeRepository.findByIdAndNameAndStatus(id, name, CommonStatus.ACTIVE);
		if (!feeCharge.isPresent()) {
			throw new InvalidServiceIdException(environment.getProperty(RECORD_NOT_FOUND), ServiceEntity.FEE_CHARGE_ID, EntityPoint.TC_REVOLVING_CALCULATION);
		}
		
		return feeCharge.get();
	}
	
    private ApplicationFrequency validateApplicationFrequency(Long id, String name) {
		
		Optional<ApplicationFrequency> applicationFrequency = applicationFrequencyRepository.findByIdAndNameAndStatus(id, name, CommonStatus.ACTIVE);
		if (!applicationFrequency.isPresent()) {
			throw new InvalidServiceIdException(environment.getProperty(RECORD_NOT_FOUND), ServiceEntity.FEE_CHARGE_ID, EntityPoint.TC_REVOLVING_CALCULATION);
		}
		
		return applicationFrequency.get();
	}


	@Override
	public Optional<TcHeader> findByTcHeaderId(Long tcHeaderId) {
		
		Optional<TcHeader> isPresentTcHeader = tcHeaderRepository.findById(tcHeaderId);
		
		if(isPresentTcHeader.isPresent()) {
			TcHeader tcHeader = isPresentTcHeader.get();
			
			Optional<TcRevolvingDetail> tcRevolvingDetail = tcRevolvingDetailRepository.findById(tcHeader.getTcRevolvingDetail().getId());
			
			List<TcRevolvingPaymentSchedule> tcRevolvingPaymentScheduleDetails = tcRevolvingPaymentScheduleRepository.findByTcRevolvingDetailId(tcRevolvingDetail.get().getId());
			
			tcRevolvingDetail.get().setRevolvingPaymentScheduleDetails(tcRevolvingPaymentScheduleDetails);
			
			tcHeader.setTcRevolvingDetails(tcRevolvingDetail);
			
			List<TcFixedCharges> tcFixedCharges = tcFixedChargesRepository.findByTcHeaderId(tcHeader.getId());
			List<TcOptionsCharges> tcOptionsCharges = tcOptionsChargesRepository.findByTcHeaderId(tcHeader.getId());
			List<TcAdhocCharges> tcAdhocCharges = tcAdhocChargesRepository.findByTcHeaderId(tcHeader.getId());
			
			tcHeader.setFixedChargesDetails(tcFixedCharges);
			tcHeader.setOptionsChargesDetails(tcOptionsCharges);
			tcHeader.setAdhocChargesDetails(tcAdhocCharges);
			
			return isPresentTcHeader;
		}else {
			return Optional.empty();
		}
		
	}


	@Override
	public Optional<TcHeader> findByLeadId(Long leadId) {
		//List<TcHeader> isPresentTcHeader = tcHeaderRepository.findByLeadId(leadId);
		//Optional<TcHeader> isPresentTcHeader = tcHeaderRepository.findByLeadIdAndStatus(leadId,CommonStatus.ACTIVE);
		Optional<TcHeader> isPresentTcHeader = Optional.empty();
		if(isPresentTcHeader.isPresent()) {
			TcHeader tcHeader = isPresentTcHeader.get();
			
			Optional<TcRevolvingDetail> tcRevolvingDetail = tcRevolvingDetailRepository.findById(tcHeader.getTcRevolvingDetail().getId());
			
			List<TcRevolvingPaymentSchedule> tcRevolvingPaymentScheduleDetails = tcRevolvingPaymentScheduleRepository.findByTcRevolvingDetailId(tcRevolvingDetail.get().getId());
			
			tcRevolvingDetail.get().setRevolvingPaymentScheduleDetails(tcRevolvingPaymentScheduleDetails);
			
			tcHeader.setTcRevolvingDetails(tcRevolvingDetail);
			
			List<TcFixedCharges> tcFixedCharges = tcFixedChargesRepository.findByTcHeaderId(tcHeader.getId());
			List<TcOptionsCharges> tcOptionsCharges = tcOptionsChargesRepository.findByTcHeaderId(tcHeader.getId());
			List<TcAdhocCharges> tcAdhocCharges = tcAdhocChargesRepository.findByTcHeaderId(tcHeader.getId());
			
			tcHeader.setFixedChargesDetails(tcFixedCharges);
			tcHeader.setOptionsChargesDetails(tcOptionsCharges);
			tcHeader.setAdhocChargesDetails(tcAdhocCharges);
			
			return isPresentTcHeader;
		}else {
			return Optional.empty();
		}
	}
}
