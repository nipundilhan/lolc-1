package com.fusionx.lending.origination.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.origination.domain.FacilityCharges;
import com.fusionx.lending.origination.domain.FacilityChargesDetail;
import com.fusionx.lending.origination.domain.FacilityDetail;
import com.fusionx.lending.origination.domain.FacilityParameter;
import com.fusionx.lending.origination.domain.FacilityStructure;
import com.fusionx.lending.origination.domain.FacilityTax;
import com.fusionx.lending.origination.domain.LeadInfo;
import com.fusionx.lending.origination.enums.ChargeCatogory;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.enums.RepaymentCriteria;
import com.fusionx.lending.origination.enums.Rewards;
import com.fusionx.lending.origination.exception.OtherCommonException;
import com.fusionx.lending.origination.repository.FacilityChargesDetailRepository;
import com.fusionx.lending.origination.repository.FacilityChargesRepository;
import com.fusionx.lending.origination.repository.FacilityDetailRepository;
import com.fusionx.lending.origination.repository.FacilityParameterRepository;
import com.fusionx.lending.origination.repository.FacilityStructureRepository;
import com.fusionx.lending.origination.repository.FacilityTaxRepository;
import com.fusionx.lending.origination.repository.LeadInfoRepository;
import com.fusionx.lending.origination.resource.FacilityCalculationFixedChargesRequestResource;
import com.fusionx.lending.origination.resource.FacilityCalculationOptionalChargesRequestResource;
import com.fusionx.lending.origination.resource.FacilityCalculationPeriodicChargesDetailRequestResource;
import com.fusionx.lending.origination.resource.FacilityCalculationPeriodicChargesRequestResource;
import com.fusionx.lending.origination.resource.FacilityCalculationRequestResource;
import com.fusionx.lending.origination.resource.FacilityCalculationTaxRequestResource;
import com.fusionx.lending.origination.resource.FacilityDetailRequestResource;
import com.fusionx.lending.origination.resource.FacilityStructureRequestResource;
import com.fusionx.lending.origination.resource.RentalCalculationRequestResource;
import com.fusionx.lending.origination.resource.RentalCalculationResponseResource;
import com.fusionx.lending.origination.resource.RentalCalculationpSnrvRequestResource;
import com.fusionx.lending.origination.resource.RentalCalculationpStruRequestResource;
import com.fusionx.lending.origination.resource.RentalCalculationpTrtxRequestResource;
import com.fusionx.lending.origination.service.FacilityService;
import com.fusionx.lending.origination.service.ValidateService;
 
@Component
@Transactional(rollbackFor = Exception.class)
public class FacilityServiceImpl implements FacilityService{

	@Autowired
	private Environment environment;
	
	@Autowired
	private FacilityParameterRepository facilityParameterRepository;
	
	@Autowired
	private FacilityDetailRepository facilityDetailRepository;
	
	@Autowired
	private FacilityStructureRepository facilityStructureRepository;
	
	@Autowired
	private FacilityChargesRepository facilityChargesRepository;
	
	@Autowired
	private FacilityTaxRepository facilityTaxRepository;
	
	@Autowired
	private FacilityChargesDetailRepository facilityChargesDetailRepository;
	
	@Autowired
	private LeadInfoRepository leadInfoRepository;
	
	@Autowired
	private ValidateService validateService;


	@Override
	public Long saveFacility(String tenantId, String createdUser, Long leadId, FacilityCalculationRequestResource facilityCalculationRequestResource) {
		Optional<FacilityParameter> optionalFacilityParameter=facilityParameterRepository.findByLeadInfoIdAndStatus(leadId, CommonStatus.ACTIVE);
		if(optionalFacilityParameter.isPresent())
			throw new OtherCommonException(environment.getProperty("facility.already-added"));
		
		//List<RentalCalculationResponseResource> rentalCalculationResponseResources = invokeFusionRentalCalculation(tenantId, createdUser, leadId, facilityCalculationRequestResource);
		//FacilityParameter facilityParameter=saveFacilityParameter(tenantId, createdUser, leadId, facilityCalculationRequestResource, rentalCalculationResponseResources.get(0));
		//saveFacilityDetail(tenantId, createdUser, facilityParameter, facilityCalculationRequestResource.getPaymentStructures());
		//savePaymentStructure(tenantId, createdUser, facilityParameter, facilityCalculationRequestResource.getFacilityStructures());
		return leadId;
	}
	
	private FacilityParameter saveFacilityParameter(String tenantId, String createdUser, Long leadId, FacilityCalculationRequestResource facilityCalculationRequestResource, RentalCalculationResponseResource rentalCalculationResponseResource) {
		FacilityParameter facilityParameter = new FacilityParameter();
		facilityParameter.setTenantId(tenantId);
		LeadInfo leadInfo=leadInfoRepository.getOne(leadId);
		facilityParameter.setLeadInfo(leadInfo);
		facilityParameter.setProductCode(facilityCalculationRequestResource.getProductCode());
		facilityParameter.setProductName(facilityCalculationRequestResource.getProductName());
		facilityParameter.setSubProductCode(facilityCalculationRequestResource.getSubProductCode());
		facilityParameter.setSubProductName(facilityCalculationRequestResource.getSubProductName());
		facilityParameter.setCalculationMethodCode(facilityCalculationRequestResource.getCalculationMethodCode());
		facilityParameter.setCalculationMethodName(facilityCalculationRequestResource.getCalculationMethodName());
		facilityParameter.setPaymentFrequencyCode(facilityCalculationRequestResource.getPaymentFrequencyCode());
		facilityParameter.setPaymentFrequencyName(facilityCalculationRequestResource.getPaymentFrequencyName());
		facilityParameter.setCurrencyCode(facilityCalculationRequestResource.getCurrencyCode());
		
		if(facilityCalculationRequestResource.getLoanAmount()!=null && !facilityCalculationRequestResource.getLoanAmount().isEmpty())
			facilityParameter.setLoanAmount(validateService.stringToBigDecimal(facilityCalculationRequestResource.getLoanAmount()));
		
		if(facilityCalculationRequestResource.getTerm()!=null && !facilityCalculationRequestResource.getTerm().isEmpty())
			facilityParameter.setTerm(validateService.stringToLong(facilityCalculationRequestResource.getTerm()));
		
		if(facilityCalculationRequestResource.getRate()!=null && !facilityCalculationRequestResource.getRate().isEmpty())
			facilityParameter.setRate(validateService.stringToFloat(facilityCalculationRequestResource.getRate()));
		
		if(facilityCalculationRequestResource.getApprovedLimit()!=null && !facilityCalculationRequestResource.getApprovedLimit().isEmpty())
			facilityParameter.setApprovedLimit(validateService.stringToBigDecimal(facilityCalculationRequestResource.getApprovedLimit()));
		
		if(facilityCalculationRequestResource.getInitialDisbursement()!=null && !facilityCalculationRequestResource.getInitialDisbursement().isEmpty())
			facilityParameter.setInitialDisbursement(validateService.stringToBigDecimal(facilityCalculationRequestResource.getInitialDisbursement()));
		
		if(facilityCalculationRequestResource.getDueDate()!=null && !facilityCalculationRequestResource.getDueDate().isEmpty())
			facilityParameter.setDueDate(validateService.formatDate(facilityCalculationRequestResource.getDueDate()));
		
		if(facilityCalculationRequestResource.getExpiryDate()!=null && !facilityCalculationRequestResource.getExpiryDate().isEmpty())
			facilityParameter.setExpiryDate(validateService.formatDate(facilityCalculationRequestResource.getExpiryDate()));
		
		if(facilityCalculationRequestResource.getPenalRate()!=null && !facilityCalculationRequestResource.getPenalRate().isEmpty())
			facilityParameter.setPenalRate(validateService.stringToFloat(facilityCalculationRequestResource.getPenalRate()));
		
		if(facilityCalculationRequestResource.getGracePeriod()!=null && !facilityCalculationRequestResource.getGracePeriod().isEmpty())
			facilityParameter.setGracePeriod(validateService.stringToLong(facilityCalculationRequestResource.getGracePeriod()));
		
		if(facilityCalculationRequestResource.getInterestRepaymentMethod()!=null && !facilityCalculationRequestResource.getInterestRepaymentMethod().isEmpty())
			facilityParameter.setInterestRepaymentMethod(facilityCalculationRequestResource.getInterestRepaymentMethod());
		
		if(facilityCalculationRequestResource.getRepaymentCriteria()!=null && !facilityCalculationRequestResource.getRepaymentCriteria().isEmpty())
			facilityParameter.setRepaymentCriteria(facilityCalculationRequestResource.getRepaymentCriteria());
		
		if(facilityCalculationRequestResource.getNoOfPrePayment()!=null && !facilityCalculationRequestResource.getNoOfPrePayment().isEmpty())
			facilityParameter.setNoOfPrePayment(validateService.stringToLong(facilityCalculationRequestResource.getNoOfPrePayment()));
		
		if(facilityCalculationRequestResource.getAmountPaidInAdvance()!=null && !facilityCalculationRequestResource.getAmountPaidInAdvance().isEmpty())
			facilityParameter.setAmountPaidInAdvance(validateService.stringToBigDecimal(facilityCalculationRequestResource.getAmountPaidInAdvance()));
		
		if(facilityCalculationRequestResource.getResidualValue()!=null && !facilityCalculationRequestResource.getResidualValue().isEmpty())
			facilityParameter.setResidualValue(validateService.stringToBigDecimal(facilityCalculationRequestResource.getResidualValue()));
		
		if(facilityCalculationRequestResource.getRewards()!=null && !facilityCalculationRequestResource.getRewards().isEmpty())
			facilityParameter.setRewards(facilityCalculationRequestResource.getRewards());
		facilityParameter.setRemaks(facilityCalculationRequestResource.getRemaks());
		
		if(facilityCalculationRequestResource.getLoanAmountWithTax()!=null && !facilityCalculationRequestResource.getLoanAmountWithTax().isEmpty())
			facilityParameter.setLoanAmountWithTax(validateService.stringToBigDecimal(facilityCalculationRequestResource.getLoanAmountWithTax()));
		
		if(facilityCalculationRequestResource.getAmountFinance()!=null && !facilityCalculationRequestResource.getAmountFinance().isEmpty())
			facilityParameter.setAmountFinance(validateService.stringToBigDecimal(facilityCalculationRequestResource.getAmountFinance()));
		
		if(facilityCalculationRequestResource.getTotalReceivable()!=null && !facilityCalculationRequestResource.getTotalReceivable().isEmpty())
			facilityParameter.setTotalReceivable(validateService.stringToBigDecimal(facilityCalculationRequestResource.getTotalReceivable()));
		
		if(facilityCalculationRequestResource.getPrePaidInstallment()!=null && !facilityCalculationRequestResource.getPrePaidInstallment().isEmpty())
			facilityParameter.setPrePaidInstallment(validateService.stringToBigDecimal(facilityCalculationRequestResource.getPrePaidInstallment()));
		
		if(facilityCalculationRequestResource.getDownPaymentAmount()!=null && !facilityCalculationRequestResource.getDownPaymentAmount().isEmpty())
			facilityParameter.setDownPaymentAmount(validateService.stringToBigDecimal(facilityCalculationRequestResource.getDownPaymentAmount()));
		
		if(facilityCalculationRequestResource.getTotalTaxes()!=null && !facilityCalculationRequestResource.getTotalTaxes().isEmpty())
			facilityParameter.setTotalTaxes(validateService.stringToBigDecimal(facilityCalculationRequestResource.getTotalTaxes()));
		
		if(facilityCalculationRequestResource.getTotalCharges()!=null && !facilityCalculationRequestResource.getTotalCharges().isEmpty())
			facilityParameter.setTotalCharges(validateService.stringToBigDecimal(facilityCalculationRequestResource.getTotalCharges()));
		
		if(facilityCalculationRequestResource.getLeaseFactor()!=null && !facilityCalculationRequestResource.getLeaseFactor().isEmpty())
			facilityParameter.setLeaseFactor(validateService.stringToFloat(facilityCalculationRequestResource.getLeaseFactor()));
		
		if(facilityCalculationRequestResource.getChargesFactor()!=null && !facilityCalculationRequestResource.getChargesFactor().isEmpty())
			facilityParameter.setChargesFactor(validateService.stringToFloat(facilityCalculationRequestResource.getChargesFactor()));
		
		if(facilityCalculationRequestResource.getTotalFactor()!=null && !facilityCalculationRequestResource.getTotalFactor().isEmpty())
			facilityParameter.setTotalFactor(validateService.stringToFloat(facilityCalculationRequestResource.getTotalFactor()));
		facilityParameter.setBranchCode(facilityCalculationRequestResource.getBranchCode());
		//facilityParameter.setTcNo(rentalCalculationResponseResource.);
		facilityParameter.setQuotationNo(rentalCalculationResponseResource.getQuotationNumber());
		
		facilityParameter.setStatus(CommonStatus.ACTIVE);
		facilityParameter.setCreatedUser(createdUser);
		facilityParameter.setCreatedDate(validateService.getCreateOrModifyDate());
		facilityParameter.setSyncTs(validateService.getCreateOrModifyDate());
		
		facilityParameter=facilityParameterRepository.saveAndFlush(facilityParameter);
		saveFacilityTax(tenantId, createdUser, facilityParameter, null, facilityCalculationRequestResource.getTaxes());
		saveFacilityCharges(tenantId, createdUser, facilityParameter, null, facilityCalculationRequestResource.getFixedCharges(), facilityCalculationRequestResource.getOptionalCharges(), facilityCalculationRequestResource.getPeriodicCharges());
		return facilityParameter;
	}
	
	private void saveFacilityDetail(String tenantId, String createdUser, FacilityParameter facilityParameter, List<FacilityDetailRequestResource> paymentStructures) {
		for (FacilityDetailRequestResource facilityDetailRequestResource : paymentStructures) {
			FacilityDetail facilityDetail = new FacilityDetail();
			facilityDetail.setTenantId(tenantId);
			if(facilityDetailRequestResource.getSequence()!=null && !facilityDetailRequestResource.getSequence().isEmpty())
				facilityDetail.setSequence(validateService.stringToLong(facilityDetailRequestResource.getSequence()));
			
			if(facilityDetailRequestResource.getPeriod()!=null && !facilityDetailRequestResource.getPeriod().isEmpty())
				facilityDetail.setPeriod(validateService.stringToLong(facilityDetailRequestResource.getPeriod()));
				
			if(facilityDetailRequestResource.getInstallment()!=null && !facilityDetailRequestResource.getInstallment().isEmpty())
				facilityDetail.setInstallment(validateService.stringToBigDecimal(facilityDetailRequestResource.getInstallment()));
			
			facilityDetail.setFacilityParameter(facilityParameter);
			facilityDetail.setStatus(CommonStatus.ACTIVE);
			facilityDetail.setCreatedUser(createdUser);
			facilityDetail.setCreatedDate(validateService.getCreateOrModifyDate());
			facilityDetail.setSyncTs(validateService.getCreateOrModifyDate());
			facilityDetailRepository.saveAndFlush(facilityDetail);
		}
	}
	
	private void savePaymentStructure(String tenantId, String createdUser, FacilityParameter facilityParameter, List<FacilityStructureRequestResource> facilityStructures) {
		if(facilityStructures!=null && !facilityStructures.isEmpty()) {
			for (FacilityStructureRequestResource facilityStructureRequestResource : facilityStructures) {
				FacilityStructure facilityStructure=new FacilityStructure();
				facilityStructure.setTenantId(tenantId);
				if(facilityStructureRequestResource.getSequence()!=null && !facilityStructureRequestResource.getSequence().isEmpty())
					facilityStructure.setSequence(validateService.stringToLong(facilityStructureRequestResource.getSequence()));
				
				if(facilityStructureRequestResource.getPeriod()!=null && !facilityStructureRequestResource.getPeriod().isEmpty())
					facilityStructure.setPeriod(validateService.stringToLong(facilityStructureRequestResource.getPeriod()));
				
				if(facilityStructureRequestResource.getInstallment()!=null && !facilityStructureRequestResource.getInstallment().isEmpty())
					facilityStructure.setRate(validateService.stringToBigDecimal(facilityStructureRequestResource.getInstallment()));
				
				if(facilityStructureRequestResource.getRate()!=null && !facilityStructureRequestResource.getRate().isEmpty())
					facilityStructure.setInstallment(validateService.stringToBigDecimal(facilityStructureRequestResource.getRate()));
				
				if(facilityStructureRequestResource.getTotalTaxes()!=null && !facilityStructureRequestResource.getTotalTaxes().isEmpty())
					facilityStructure.setTotalTaxes(validateService.stringToBigDecimal(facilityStructureRequestResource.getTotalTaxes()));
				
				if(facilityStructureRequestResource.getTotalCharges()!=null && !facilityStructureRequestResource.getTotalCharges().isEmpty())
					facilityStructure.setTotalCharges(validateService.stringToBigDecimal(facilityStructureRequestResource.getTotalCharges()));
				
				if(facilityStructureRequestResource.getTotalInstallment()!=null && !facilityStructureRequestResource.getTotalInstallment().isEmpty())
					facilityStructure.setTotalInstallment(validateService.stringToBigDecimal(facilityStructureRequestResource.getTotalInstallment()));
				
				facilityStructure.setFacilityParameter(facilityParameter);
				facilityStructure.setStatus(CommonStatus.ACTIVE);
				facilityStructure.setCreatedUser(createdUser);
				facilityStructure.setCreatedDate(validateService.getCreateOrModifyDate());
				facilityStructure.setSyncTs(validateService.getCreateOrModifyDate());
				
				facilityStructure=facilityStructureRepository.saveAndFlush(facilityStructure);
				saveFacilityTax(tenantId, createdUser, null, facilityStructure, facilityStructureRequestResource.getTaxes());
				saveFacilityCharges(tenantId, createdUser, null, facilityStructure, facilityStructureRequestResource.getFixedCharges(), facilityStructureRequestResource.getOptionalCharges(), facilityStructureRequestResource.getPeriodicCharges());
			}
		}
	}
	
	private void saveFacilityCharges(String tenantId, String createdUser, FacilityParameter facilityParameter, FacilityStructure facilityStructure, 
			List<FacilityCalculationFixedChargesRequestResource> fixedCharges, List<FacilityCalculationOptionalChargesRequestResource> optionalCharges, List<FacilityCalculationPeriodicChargesRequestResource> periodicCharges) {
		for (FacilityCalculationFixedChargesRequestResource charge : fixedCharges) {
			FacilityCharges facilityCharges=new FacilityCharges();
			facilityCharges.setTenantId(tenantId);
			facilityCharges.setChargeTypeCode(charge.getChargeTypeCode());
			facilityCharges.setChargeTypeName(charge.getChargeTypeName());
			facilityCharges.setCriteriaCode(charge.getCriteriaCode());
			facilityCharges.setCriteriaName(charge.getCriteriaName());
			facilityCharges.setCalculationMethodCode(charge.getCalculationMethodCode());
			facilityCharges.setCalculationMethodName(charge.getCalculationMethodName());
			if(charge.getChargeRate()!=null && !charge.getChargeRate().isEmpty())
				facilityCharges.setChargeRate(validateService.stringToFloat(charge.getChargeRate()));
			
			if(charge.getChargeAmount()!=null && !charge.getChargeAmount().isEmpty())
				facilityCharges.setChargeAmount(validateService.stringToBigDecimal(charge.getChargeAmount()));
			
			if(charge.getTotalChargeAmount()!=null && !charge.getTotalChargeAmount().isEmpty())
				facilityCharges.setTotalChargeAmount(validateService.stringToBigDecimal(charge.getTotalChargeAmount()));
			
			facilityCharges.setChargeCatogory(ChargeCatogory.FIXED);
			facilityCharges.setFacilityParameter(facilityParameter);
			facilityCharges.setFacilityStructure(facilityStructure);
			facilityCharges.setStatus(CommonStatus.ACTIVE);
			facilityCharges.setCreatedUser(createdUser);
			facilityCharges.setCreatedDate(validateService.getCreateOrModifyDate());
			facilityCharges.setSyncTs(validateService.getCreateOrModifyDate());
			
			facilityChargesRepository.saveAndFlush(facilityCharges);
		}
		for (FacilityCalculationOptionalChargesRequestResource charge : optionalCharges) {
			FacilityCharges facilityCharges=new FacilityCharges();
			facilityCharges.setTenantId(tenantId);
			facilityCharges.setChargeTypeCode(charge.getChargeTypeCode());
			facilityCharges.setChargeTypeName(charge.getChargeTypeName());
			facilityCharges.setCriteriaCode(charge.getCriteriaCode());
			facilityCharges.setCriteriaName(charge.getCriteriaName());
			facilityCharges.setCalculationMethodCode(charge.getCalculationMethodCode());
			facilityCharges.setCalculationMethodName(charge.getCalculationMethodName());
			facilityCharges.setOptions(charge.getOptions());
			if(charge.getChargeRate()!=null && !charge.getChargeRate().isEmpty())
				facilityCharges.setChargeRate(validateService.stringToFloat(charge.getChargeRate()));
			
			if(charge.getChargeAmount()!=null && !charge.getChargeAmount().isEmpty())
				facilityCharges.setChargeAmount(validateService.stringToBigDecimal(charge.getChargeAmount()));
			
			if(charge.getTotalChargeAmount()!=null && !charge.getTotalChargeAmount().isEmpty())
				facilityCharges.setTotalChargeAmount(validateService.stringToBigDecimal(charge.getTotalChargeAmount()));
			
			facilityCharges.setChargeCatogory(ChargeCatogory.OPTIONAL);
			facilityCharges.setFacilityParameter(facilityParameter);
			facilityCharges.setFacilityStructure(facilityStructure);
			facilityCharges.setStatus(CommonStatus.ACTIVE);
			facilityCharges.setCreatedUser(createdUser);
			facilityCharges.setCreatedDate(validateService.getCreateOrModifyDate());
			facilityCharges.setSyncTs(validateService.getCreateOrModifyDate());
			
			facilityChargesRepository.saveAndFlush(facilityCharges);
		}
		for (FacilityCalculationPeriodicChargesRequestResource charge : periodicCharges) {
			FacilityCharges facilityCharges=new FacilityCharges();
			facilityCharges.setTenantId(tenantId);
			facilityCharges.setChargeTypeCode(charge.getChargeTypeCode());
			facilityCharges.setChargeTypeName(charge.getChargeTypeName());
			facilityCharges.setCriteriaCode(charge.getCriteriaCode());
			facilityCharges.setCriteriaName(charge.getCriteriaName());
			facilityCharges.setFrequencyCode(charge.getFrequencyCode());
			facilityCharges.setFrequencyName(charge.getFrequencyName());
			facilityCharges.setChargeCatogory(ChargeCatogory.PERIODIC);
			facilityCharges.setFacilityParameter(facilityParameter);
			facilityCharges.setFacilityStructure(facilityStructure);
			facilityCharges.setStatus(CommonStatus.ACTIVE);
			facilityCharges.setCreatedUser(createdUser);
			facilityCharges.setCreatedDate(validateService.getCreateOrModifyDate());
			facilityCharges.setSyncTs(validateService.getCreateOrModifyDate());
			
			facilityCharges=facilityChargesRepository.saveAndFlush(facilityCharges);
			savePeriodicChargesDetail(tenantId, createdUser, facilityCharges, charge.getChargeDetails());
		}
		
	}
	private void savePeriodicChargesDetail(String tenantId, String createdUser, FacilityCharges facilityCharges, List<FacilityCalculationPeriodicChargesDetailRequestResource> chargeDetails) {
		for (FacilityCalculationPeriodicChargesDetailRequestResource facilityCalculationPeriodicChargesDetailRequestResource : chargeDetails) {
			FacilityChargesDetail facilityChargesDetail=new FacilityChargesDetail();
			facilityChargesDetail.setTenantId(tenantId);
			facilityChargesDetail.setPeriodCode(facilityCalculationPeriodicChargesDetailRequestResource.getPeriodCode());
			facilityChargesDetail.setPeriodName(facilityCalculationPeriodicChargesDetailRequestResource.getPeriodName());
			if(facilityCalculationPeriodicChargesDetailRequestResource.getChargeAmount()!=null && !facilityCalculationPeriodicChargesDetailRequestResource.getChargeAmount().isEmpty())
				facilityChargesDetail.setChargeAmount(validateService.stringToBigDecimal(facilityCalculationPeriodicChargesDetailRequestResource.getChargeAmount()));
			facilityChargesDetail.setFacilityCharges(facilityCharges);
			facilityChargesDetail.setStatus(CommonStatus.ACTIVE);
			facilityChargesDetail.setCreatedUser(createdUser);
			facilityChargesDetail.setCreatedDate(validateService.getCreateOrModifyDate());
			facilityChargesDetail.setSyncTs(validateService.getCreateOrModifyDate());
			facilityChargesDetailRepository.saveAndFlush(facilityChargesDetail);
		}
	}
	private void saveFacilityTax(String tenantId, String createdUser, FacilityParameter facilityParameter, FacilityStructure facilityStructure, List<FacilityCalculationTaxRequestResource> taxes) {
		for (FacilityCalculationTaxRequestResource facilityCalculationTaxRequestResource : taxes) {
			FacilityTax facilityTax=new FacilityTax();
			facilityTax.setTenantId(tenantId);
			facilityTax.setTaxTypeCode(facilityCalculationTaxRequestResource.getTaxTypeCode());
			facilityTax.setTaxTypeName(facilityCalculationTaxRequestResource.getTaxTypeName());
			facilityTax.setTaxApplicableOnCode(facilityCalculationTaxRequestResource.getTaxApplicableOnCode());
			facilityTax.setTaxApplicableOnName(facilityCalculationTaxRequestResource.getTaxApplicableOnName());
			if(facilityCalculationTaxRequestResource.getTaxRate()!=null && !facilityCalculationTaxRequestResource.getTaxRate().isEmpty())
				facilityTax.setTaxRate(validateService.stringToFloat(facilityCalculationTaxRequestResource.getTaxRate()));
			
			if(facilityCalculationTaxRequestResource.getTaxAmount()!=null && !facilityCalculationTaxRequestResource.getTaxAmount().isEmpty())
				facilityTax.setTaxAmount(validateService.stringToBigDecimal(facilityCalculationTaxRequestResource.getTaxAmount()));
			
			if(facilityCalculationTaxRequestResource.getTotalTaxAmount()!=null && !facilityCalculationTaxRequestResource.getTotalTaxAmount().isEmpty())
				facilityTax.setTotalTaxAmount(validateService.stringToBigDecimal(facilityCalculationTaxRequestResource.getTotalTaxAmount()));
			facilityTax.setFacilityParameter(facilityParameter);
			facilityTax.setFacilityStructure(facilityStructure);
			facilityTax.setStatus(CommonStatus.ACTIVE);
			facilityTax.setCreatedUser(createdUser);
			facilityTax.setCreatedDate(validateService.getCreateOrModifyDate());
			facilityTax.setSyncTs(validateService.getCreateOrModifyDate());
			
			facilityTaxRepository.saveAndFlush(facilityTax);
		}
	}

	@Override
	public Long updateFacility(String tenantId, String createdUser, Long leadId, FacilityCalculationRequestResource facilityCalculationRequestResource) {
		//List<RentalCalculationResponseResource> rentalCalculationResponseResources = invokeFusionRentalCalculation(tenantId, createdUser, leadId, facilityCalculationRequestResource);
		inactivePreviousFacilityInfomation(leadId, createdUser);
		//FacilityParameter facilityParameter=saveFacilityParameter(tenantId, createdUser, leadId, facilityCalculationRequestResource, rentalCalculationResponseResources.get(0));
		//saveFacilityDetail(tenantId, createdUser, facilityParameter, facilityCalculationRequestResource.getPaymentStructures());
		//savePaymentStructure(tenantId, createdUser, facilityParameter, facilityCalculationRequestResource.getFacilityStructures());
		return leadId;
	}
	
	private void inactivePreviousFacilityInfomation(Long leadId, String createdUser) {
		Optional<FacilityParameter> optionalFacilityParameter=facilityParameterRepository.findByLeadInfoIdAndStatus(leadId, CommonStatus.ACTIVE);
		if(optionalFacilityParameter.isPresent()) {
			FacilityParameter facilityParameter=optionalFacilityParameter.get();
			facilityParameter.setStatus(CommonStatus.INACTIVE);
			facilityParameter.setModifiedUser(createdUser);
			facilityParameter.setModifiedDate(validateService.getCreateOrModifyDate());
			facilityParameterRepository.saveAndFlush(facilityParameter);
			
			List<FacilityDetail> facilityDetails=facilityDetailRepository.findByFacilityParameterIdAndStatus(facilityParameter.getId(), CommonStatus.ACTIVE);
			if(facilityDetails!=null && !facilityDetails.isEmpty()) {
				for (FacilityDetail facilityDetail : facilityDetails) {
					facilityDetail.setStatus(CommonStatus.INACTIVE);
					facilityDetail.setModifiedUser(createdUser);
					facilityDetail.setModifiedDate(validateService.getCreateOrModifyDate());
					facilityDetailRepository.saveAndFlush(facilityDetail);
				}
			}
			List<FacilityTax> facilityTaxes=facilityTaxRepository.findByFacilityParameterIdAndStatus(facilityParameter.getId(), CommonStatus.ACTIVE);
			if(facilityTaxes!=null && !facilityTaxes.isEmpty()) {
				for (FacilityTax facilityTax : facilityTaxes) {
					facilityTax.setStatus(CommonStatus.INACTIVE);
					facilityTax.setModifiedUser(createdUser);
					facilityTax.setModifiedDate(validateService.getCreateOrModifyDate());
					facilityTaxRepository.saveAndFlush(facilityTax);
				}
			}
			List<FacilityCharges> facilityCharges=facilityChargesRepository.findByFacilityParameterIdAndStatus(facilityParameter.getId(), CommonStatus.ACTIVE);
			if(facilityCharges!=null && !facilityCharges.isEmpty()) {
				for (FacilityCharges facilityCharge : facilityCharges) {
					if(facilityCharge.getChargeCatogory().equals(ChargeCatogory.PERIODIC)) {
						List<FacilityChargesDetail> facilityChargesDetails=facilityChargesDetailRepository.findByfacilityChargesIdAndStatus(facilityCharge.getId(), CommonStatus.ACTIVE);
						if(facilityChargesDetails!=null && !facilityChargesDetails.isEmpty()) {
							for (FacilityChargesDetail facilityChargesDetail : facilityChargesDetails) {
								facilityChargesDetail.setStatus(CommonStatus.INACTIVE);
								facilityChargesDetail.setModifiedUser(createdUser);
								facilityChargesDetail.setModifiedDate(validateService.getCreateOrModifyDate());
								facilityChargesDetailRepository.saveAndFlush(facilityChargesDetail);
							}
						}
					}
					facilityCharge.setStatus(CommonStatus.INACTIVE);
					facilityCharge.setModifiedUser(createdUser);
					facilityCharge.setModifiedDate(validateService.getCreateOrModifyDate());
					facilityChargesRepository.saveAndFlush(facilityCharge);
				}
			}
			
			List<FacilityStructure> facilityStructures=facilityStructureRepository.findByFacilityParameterIdAndStatus(facilityParameter.getId(), CommonStatus.ACTIVE);
			if(facilityStructures!=null && !facilityStructures.isEmpty()) {
				for (FacilityStructure facilityStructure : facilityStructures) {
					List<FacilityTax> facilityStructureTaxes=facilityTaxRepository.findByFacilityStructureIdAndStatus(facilityStructure.getId(), CommonStatus.ACTIVE);
					if(facilityStructureTaxes!=null && !facilityStructureTaxes.isEmpty()) {
						for (FacilityTax facilityTax : facilityStructureTaxes) {
							facilityTax.setStatus(CommonStatus.INACTIVE);
							facilityTax.setModifiedUser(createdUser);
							facilityTax.setModifiedDate(validateService.getCreateOrModifyDate());
							facilityTaxRepository.saveAndFlush(facilityTax);
						}
					}
					List<FacilityCharges> facilityStructureCharges=facilityChargesRepository.findByFacilityStructureIdAndStatus(facilityStructure.getId(), CommonStatus.ACTIVE);
					if(facilityStructureCharges!=null && !facilityStructureCharges.isEmpty()) {
						for (FacilityCharges facilityCharge : facilityStructureCharges) {
							if(facilityCharge.getChargeCatogory().equals(ChargeCatogory.PERIODIC)) {
								List<FacilityChargesDetail> facilityChargesDetails=facilityChargesDetailRepository.findByfacilityChargesIdAndStatus(facilityCharge.getId(), CommonStatus.ACTIVE);
								if(facilityChargesDetails!=null && !facilityChargesDetails.isEmpty()) {
									for (FacilityChargesDetail facilityChargesDetail : facilityChargesDetails) {
										facilityChargesDetail.setStatus(CommonStatus.INACTIVE);
										facilityChargesDetail.setModifiedUser(createdUser);
										facilityChargesDetail.setModifiedDate(validateService.getCreateOrModifyDate());
										facilityChargesDetailRepository.saveAndFlush(facilityChargesDetail);
									}
								}
							}
							facilityCharge.setStatus(CommonStatus.INACTIVE);
							facilityCharge.setModifiedUser(createdUser);
							facilityCharge.setModifiedDate(validateService.getCreateOrModifyDate());
							facilityChargesRepository.saveAndFlush(facilityCharge);
						}
					}
					
					facilityStructure.setStatus(CommonStatus.INACTIVE);
					facilityStructure.setModifiedUser(createdUser);
					facilityStructure.setModifiedDate(validateService.getCreateOrModifyDate());
					facilityStructureRepository.saveAndFlush(facilityStructure);
				}
			}
		}
	}

	@Override
	public FacilityParameter findFacilityCalculationDetailByLeadId(Long leadId) {
		Optional<FacilityParameter> optionalFacilityParameter=facilityParameterRepository.findByLeadInfoIdAndStatus(leadId, CommonStatus.ACTIVE);
		if(optionalFacilityParameter.isPresent()) {
			FacilityParameter facilityParameter=optionalFacilityParameter.get();
			List<FacilityDetail> facilityDetails=facilityDetailRepository.findByFacilityParameterIdAndStatus(facilityParameter.getId(), CommonStatus.ACTIVE);
			facilityParameter.setFacilityDetails(facilityDetails);
			
			List<FacilityTax> facilityTaxes=facilityTaxRepository.findByFacilityParameterIdAndStatus(facilityParameter.getId(), CommonStatus.ACTIVE);
			facilityParameter.setFacilityTaxes(facilityTaxes);
			
			List<FacilityCharges> facilityCharges=facilityChargesRepository.findByFacilityParameterIdAndStatus(facilityParameter.getId(), CommonStatus.ACTIVE);
			if(facilityCharges!=null && !facilityCharges.isEmpty()) {
				for (FacilityCharges facilityCharge : facilityCharges) {
					if(facilityCharge.getChargeCatogory().equals(ChargeCatogory.PERIODIC)) {
						List<FacilityChargesDetail> facilityChargesDetails=facilityChargesDetailRepository.findByfacilityChargesIdAndStatus(facilityCharge.getId(), CommonStatus.ACTIVE);
						facilityCharge.setChargeDetails(facilityChargesDetails);
					}
				}
			}
			facilityParameter.setFacilityCharges(facilityCharges);
			
			List<FacilityStructure> facilityStructures=facilityStructureRepository.findByFacilityParameterIdAndStatus(facilityParameter.getId(), CommonStatus.ACTIVE);
			if(facilityStructures!=null && !facilityStructures.isEmpty()) {
				for (FacilityStructure facilityStructure : facilityStructures) {
					List<FacilityTax> facilityStructureTaxes=facilityTaxRepository.findByFacilityStructureIdAndStatus(facilityStructure.getId(), CommonStatus.ACTIVE);
					facilityStructure.setFacilityStructureTaxes(facilityStructureTaxes);
					List<FacilityCharges> facilityStructureCharges=facilityChargesRepository.findByFacilityStructureIdAndStatus(facilityStructure.getId(), CommonStatus.ACTIVE);
					if(facilityStructureCharges!=null && !facilityStructureCharges.isEmpty()) {
						for (FacilityCharges facilityCharge : facilityStructureCharges) {
							if(facilityCharge.getChargeCatogory().equals(ChargeCatogory.PERIODIC)) {
								List<FacilityChargesDetail> facilityChargesDetails=facilityChargesDetailRepository.findByfacilityChargesIdAndStatus(facilityCharge.getId(), CommonStatus.ACTIVE);
								facilityCharge.setChargeDetails(facilityChargesDetails);
							}
						}
					}
					facilityStructure.setFacilityStructureCharges(facilityStructureCharges);
				}
			}
			facilityParameter.setFacilityStructure(facilityStructures);
			
			return facilityParameter;
		}else {
			return null;
		}
	}
	
	
	
}
