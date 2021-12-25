package com.fusionx.lending.origination.service.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.origination.base.MessagePropertyBase;
import com.fusionx.lending.origination.core.LoggerRequest;
import com.fusionx.lending.origination.core.LogginAuthentcation;
import com.fusionx.lending.origination.domain.CommonList;
import com.fusionx.lending.origination.domain.Customer;
import com.fusionx.lending.origination.domain.ExternalLiability;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.enums.FinancialCommitmentCategory;
import com.fusionx.lending.origination.enums.ServiceEntity;
import com.fusionx.lending.origination.enums.ServicePoint;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.repository.CommonListRepository;
import com.fusionx.lending.origination.repository.CustomerRepository;
import com.fusionx.lending.origination.repository.ExternalLiabilityRepository;
import com.fusionx.lending.origination.resource.BankBranchResponseResource;
import com.fusionx.lending.origination.resource.BankResponse;
import com.fusionx.lending.origination.resource.ExternalLiabilityAddResource;
import com.fusionx.lending.origination.resource.ExternalLiabilityUpdateResource;
import com.fusionx.lending.origination.resource.RepaymentFrequencyResponse;
import com.fusionx.lending.origination.service.ExternalLiabilityService;
import com.fusionx.lending.origination.service.ValidateService;

@Component
@Transactional(rollbackFor = Exception.class)
public class ExternalLiabilityServiceImpl  extends MessagePropertyBase implements ExternalLiabilityService{
	
	@Autowired
	private ExternalLiabilityRepository externalLiabilityRepository;
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private ValidateService validateService;
	@Autowired
	private CommonListRepository commonListRepository;
	
	@Override
	public ExternalLiability addExternalLiability(String tenantId,Long customerId ,ExternalLiabilityAddResource externalLiabilitiesAddResource) {
		
		LoggerRequest.getInstance().logInfo1("-----------start of the validations-----------------------------");
		
		Optional<Customer> customerOptional = customerRepository.findById(customerId);
		if(!customerOptional.isPresent()) {
			throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND), "customerId");
		}
		
		Optional<CommonList> commonListCommitmentTypeOptoinal = commonListRepository.findById(validationServiceStringToLong(externalLiabilitiesAddResource.getCommitmentTypeId()));
		if(!commonListCommitmentTypeOptoinal.isPresent()) {
			throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND), "commitmentTypeId");
		}else {
			if(!("Typ_of_commitement").equals(commonListCommitmentTypeOptoinal.get().getReferenceCode())) {
				throw new ValidateRecordException(environment.getProperty("commanlist.invalid-reference-code"), "commitmentTypeId");
			}
			if((CommonStatus.INACTIVE.toString()).equals(commonListCommitmentTypeOptoinal.get().getStatus())) {
				throw new ValidateRecordException(environment.getProperty("commanlist.status-inactive"), "commitmentTypeId");				
			}
		}		
		
		Optional<CommonList> commonListFacilityTypeOptoinal = commonListRepository.findById(validationServiceStringToLong(externalLiabilitiesAddResource.getFacilityTypeId()));
		if(!commonListFacilityTypeOptoinal.isPresent()) {
			throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND), "facilityTypeId");
		}else {
			if(!("Typ_of_Facility").equals(commonListFacilityTypeOptoinal.get().getReferenceCode())) {
				throw new ValidateRecordException(environment.getProperty("commanlist.invalid-reference-code"), "facilityTypeId");
			}
			if((CommonStatus.INACTIVE.toString()).equals(commonListFacilityTypeOptoinal.get().getStatus())) {
				throw new ValidateRecordException(environment.getProperty("commanlist.status-inactive"), "facilityTypeId");				
			}
		}			
		
		BankResponse bankResp =null;
		BankBranchResponseResource branchResp = null;
		if((FinancialCommitmentCategory.EXTERNAL.toString()).equals(externalLiabilitiesAddResource.getCategory())) {
			if((externalLiabilitiesAddResource.getBankId() != null) && (externalLiabilitiesAddResource.getBankName() != null)) {				
				bankResp = validateService.validateCompany(tenantId,validationServiceStringToLong(externalLiabilitiesAddResource.getBankId()),externalLiabilitiesAddResource.getBankName());
			} else {
				throw new ValidateRecordException(environment.getProperty("custom.bank-and-branch-id-not-null"), "message");		
			}
			
			if((externalLiabilitiesAddResource.getBranchId() != null) && (externalLiabilitiesAddResource.getBankBranchCode() != null)) {				
				branchResp = validateService.validateBankBranch(tenantId,validationServiceStringToLong((externalLiabilitiesAddResource.getBankId())),validationServiceStringToLong((externalLiabilitiesAddResource.getBranchId())), externalLiabilitiesAddResource.getBankBranchCode(), ServiceEntity.BANK_BRANCH, ServicePoint.EXTERNAL_LIABILITY,0);
			}
		}
		LoggerRequest.getInstance().logInfo1("-----------end of the validations-----------------------------");

		
		RepaymentFrequencyResponse repaymentFrequencyResp = validateService.validateRepaymentFrequency(tenantId,externalLiabilitiesAddResource.getRepaymentFrequencyId());
		
		ExternalLiability externalLiability = new ExternalLiability();
		externalLiability.setTenantId(tenantId);
		externalLiability.setCustomer(customerOptional.get());
		externalLiability.setCategoryCode(FinancialCommitmentCategory.valueOf(externalLiabilitiesAddResource.getCategory()));
		externalLiability.setCommonListCommitmentType(commonListCommitmentTypeOptoinal.get());
		externalLiability.setCommonListFacilityType(commonListFacilityTypeOptoinal.get());
		if(bankResp != null) {
			externalLiability.setBankId(bankResp.getId());
			externalLiability.setBankCode(bankResp.getBankCode());
			externalLiability.setBankName(bankResp.getBankName());
		}
		if(branchResp != null) {
			externalLiability.setBranchId(validationServiceStringToLong(branchResp.getBbrhBankId()));
			externalLiability.setBranchCode(branchResp.getBbrhCode());
			externalLiability.setBranchName(branchResp.getBbrhName());
		}
		Date validationServiceStringToDate = validationServiceStringToDate(externalLiabilitiesAddResource.getDisbursementDate());
		
		externalLiability.setFacilityDisbursementDate(validationServiceStringToDate != null ? dateToTimeStamp(validationServiceStringToDate) : null);
		externalLiability.setRepaymentFrequencyId(repaymentFrequencyResp.getId());
		externalLiability.setRepaymentFrequencyCode(repaymentFrequencyResp.getCode());
		externalLiability.setRepaymentFrequencyName(repaymentFrequencyResp.getName());
		externalLiability.setOutstandingAmount(validationServiceStringToBigDecimal(externalLiabilitiesAddResource.getOutstandingAmount()));
		externalLiability.setNoOfRentalPaid(validationServiceStringToLong(externalLiabilitiesAddResource.getNoOfRentalPaid()));
		externalLiability.setRentalAmount(validationServiceStringToBigDecimal(externalLiabilitiesAddResource.getRentalAmount()));
		externalLiability.setFacilityAmount(validationServiceStringToBigDecimal(externalLiabilitiesAddResource.getFacilityAmount()));
		externalLiability.setInterestRate(validationServiceStringToBigDecimal(externalLiabilitiesAddResource.getInterestRate()));
		externalLiability.setOverdueAmount(validationServiceStringToBigDecimal(externalLiabilitiesAddResource.getOverdueAmount()));
		externalLiability.setStatus(CommonStatus.valueOf(externalLiabilitiesAddResource.getStatus()));
		externalLiability.setOverDueNote(externalLiabilitiesAddResource.getNote());
		externalLiability.setRemark(externalLiabilitiesAddResource.getRemark());
		externalLiability.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
		externalLiability.setCreatedDate(validateService.getCreateOrModifyDate());
		externalLiability.setSyncTs(validateService.getCreateOrModifyDate());
		externalLiability = externalLiabilityRepository.save(externalLiability);
		
		LoggerRequest.getInstance().logInfo1("----------- external eligibility saved -----------------------------");
		
		return externalLiability;
	}
	
	
	@Override
	public ExternalLiability updateExternalLiability(String tenantId,Long externalLiabilityId, ExternalLiabilityUpdateResource externalLiabilityUpdateResource) {
		
		LoggerRequest.getInstance().logInfo1("-----------start of the validations-----------------------------");
		
		Optional<ExternalLiability> externalLiabilityOptional = externalLiabilityRepository.findById(externalLiabilityId);
		if(!externalLiabilityOptional.isPresent()) {
			throw new ValidateRecordException(environment.getProperty("externalliabilty.not-found"), "message");	
		}
		
		if(validationServiceStringToLong(externalLiabilityUpdateResource.getVersion()) != externalLiabilityOptional.get().getVersion()) {
			throw new ValidateRecordException(environment.getProperty("common-invalid.version"), "message");	
		}
		
		Optional<CommonList> commonListCommitmentTypeOptoinal = commonListRepository.findById(validationServiceStringToLong(externalLiabilityUpdateResource.getCommitmentTypeId()));
		if(!commonListCommitmentTypeOptoinal.isPresent()) {
			throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND), "commitmentTypeId");
		}else {
			if(!("Typ_of_commitement").equals(commonListCommitmentTypeOptoinal.get().getReferenceCode())) {
				throw new ValidateRecordException(environment.getProperty("commanlist.invalid-reference-code"), "commitmentTypeId");
			}
			if((CommonStatus.INACTIVE.toString()).equals(commonListCommitmentTypeOptoinal.get().getStatus())) {
				throw new ValidateRecordException(environment.getProperty("commanlist.status-inactive"), "commitmentTypeId");				
			}
		}		
		
		Optional<CommonList> commonListFacilityTypeOptoinal = commonListRepository.findById(validationServiceStringToLong(externalLiabilityUpdateResource.getFacilityTypeId()));
		if(!commonListFacilityTypeOptoinal.isPresent()) {
			throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND), "facilityTypeId");
		}else {
			if(!("Typ_of_Facility").equals(commonListFacilityTypeOptoinal.get().getReferenceCode())) {
				throw new ValidateRecordException(environment.getProperty("commanlist.invalid-reference-code"), "facilityTypeId");
			}
			if((CommonStatus.INACTIVE.toString()).equals(commonListFacilityTypeOptoinal.get().getStatus())) {
				throw new ValidateRecordException(environment.getProperty("commanlist.status-inactive"), "facilityTypeId");				
			}
		}		
		
		BankResponse bankResp =null;
		BankBranchResponseResource branchResp = null;
		if((FinancialCommitmentCategory.EXTERNAL.toString()).equals(externalLiabilityUpdateResource.getCategory())) {
			if((externalLiabilityUpdateResource.getBankId() != null) && (externalLiabilityUpdateResource.getBankName() != null)) {				
				bankResp = validateService.validateCompany(tenantId,validationServiceStringToLong(externalLiabilityUpdateResource.getBankId()),externalLiabilityUpdateResource.getBankName());
			} else {
				throw new ValidateRecordException(environment.getProperty("custom.bank-and-branch-id-not-null"), "message");	
			}
			
			if((externalLiabilityUpdateResource.getBranchId() != null) && (externalLiabilityUpdateResource.getBankBranchCode() != null)) {				
				branchResp = validateService.validateBankBranch(tenantId,validationServiceStringToLong((externalLiabilityUpdateResource.getBankId())),validationServiceStringToLong((externalLiabilityUpdateResource.getBranchId())), externalLiabilityUpdateResource.getBankBranchCode(), ServiceEntity.BANK_BRANCH, ServicePoint.EXTERNAL_LIABILITY,0);
			}
		}

		RepaymentFrequencyResponse repaymentFrequencyResp = validateService.validateRepaymentFrequency(tenantId,externalLiabilityUpdateResource.getRepaymentFrequencyId());
		
		
		LoggerRequest.getInstance().logInfo1("-----------end of the validations-----------------------------");
		
		ExternalLiability externalLiability = externalLiabilityOptional.get();
		
		
		
		externalLiability.setCategoryCode(FinancialCommitmentCategory.valueOf(externalLiabilityUpdateResource.getCategory()));
		externalLiability.setCommonListCommitmentType(commonListCommitmentTypeOptoinal.get());
		externalLiability.setCommonListFacilityType(commonListFacilityTypeOptoinal.get());
		if(bankResp != null) {
			externalLiability.setBankId(bankResp.getId());
			externalLiability.setBankCode(bankResp.getBankCode());
			externalLiability.setBankName(bankResp.getBankName());
		}
		if(branchResp != null) {
			externalLiability.setBranchId(validationServiceStringToLong(branchResp.getBbrhBankId()));
			externalLiability.setBranchCode(branchResp.getBbrhCode());
			externalLiability.setBranchName(branchResp.getBbrhName());
		}
		Date validationServiceStringToDate = validationServiceStringToDate(externalLiabilityUpdateResource.getDisbursementDate());
		
		externalLiability.setFacilityDisbursementDate(validationServiceStringToDate != null ? dateToTimeStamp(validationServiceStringToDate) : null);
		externalLiability.setRepaymentFrequencyId(repaymentFrequencyResp.getId());
		externalLiability.setRepaymentFrequencyCode(repaymentFrequencyResp.getCode());
		externalLiability.setRepaymentFrequencyName(repaymentFrequencyResp.getName());
		externalLiability.setOutstandingAmount(validationServiceStringToBigDecimal(externalLiabilityUpdateResource.getOutstandingAmount()));
		externalLiability.setRentalAmount(validationServiceStringToBigDecimal(externalLiabilityUpdateResource.getRentalAmount()));
		externalLiability.setNoOfRentalPaid(validationServiceStringToLong(externalLiabilityUpdateResource.getNoOfRentalPaid()));
		externalLiability.setFacilityAmount(validationServiceStringToBigDecimal(externalLiabilityUpdateResource.getFacilityAmount()));
		externalLiability.setInterestRate(validationServiceStringToBigDecimal(externalLiabilityUpdateResource.getInterestRate()));
		externalLiability.setOverdueAmount(validationServiceStringToBigDecimal(externalLiabilityUpdateResource.getOverdueAmount()));
		externalLiability.setOverDueNote(externalLiabilityUpdateResource.getNote());
		externalLiability.setStatus(CommonStatus.valueOf(externalLiabilityUpdateResource.getStatus()));
		externalLiability.setRemark(externalLiabilityUpdateResource.getRemark());
		externalLiability.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
		externalLiability.setModifiedDate(validateService.getCreateOrModifyDate());
		externalLiability.setSyncTs(validateService.getCreateOrModifyDate());
		externalLiability = externalLiabilityRepository.save(externalLiability);
		
		LoggerRequest.getInstance().logInfo1("----------- external liabilities saved -----------------------------"); 
		
		return externalLiability;
	}

	
	public ExternalLiability commanAddUpdateExternalLiability() {
		return null;
	}
	
	@Override
	public List<ExternalLiability> findByCustomerId(Long customerId) {
		
		Optional<Customer> customerOptional = customerRepository.findById(customerId);
		if(!customerOptional.isPresent()) {
			 return new ArrayList<>();
		}
		
		List<ExternalLiability> externalLiabilityList = externalLiabilityRepository.findAllByCustomer(customerOptional.get());
		return externalLiabilityList;
	}	
	
	
	public BigDecimal validationServiceStringToBigDecimal(String value){
		if(value != null) {
			return validateService.stringToBigDecimal(value);
		}else {
			return null;
		}
	}
	
	
	public Long validationServiceStringToLong(String value){
		if(value != null) {
			return validateService.stringToLong(value);
		}else {
			return null;
		}
	}
	
	
	public Date validationServiceStringToDate(String date){
		if(date != null) {
			return validateService.formatDate(date);
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

}
