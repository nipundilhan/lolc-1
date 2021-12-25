package com.fusionx.lending.origination.service.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.origination.base.MessagePropertyBase;
import com.fusionx.lending.origination.core.LoggerRequest;
import com.fusionx.lending.origination.core.LogginAuthentcation;
import com.fusionx.lending.origination.domain.BusinessIncomeExpense;
import com.fusionx.lending.origination.domain.BusinessType;
import com.fusionx.lending.origination.domain.CultivationCategory;
import com.fusionx.lending.origination.domain.Guarantor;
import com.fusionx.lending.origination.domain.GuarantorIncomeCultivation;
import com.fusionx.lending.origination.domain.OtherIncome;
import com.fusionx.lending.origination.domain.OtherIncomeType;
import com.fusionx.lending.origination.domain.SalaryIncome;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.enums.ServiceEntity;
import com.fusionx.lending.origination.enums.ServicePoint;
import com.fusionx.lending.origination.exception.DetailListValidateException;
import com.fusionx.lending.origination.exception.DetailValidateException;
import com.fusionx.lending.origination.repository.BusinessIncomeExpenseRepository;
import com.fusionx.lending.origination.repository.BusinessTypeRepository;
import com.fusionx.lending.origination.repository.CultivationCategoryRepository;
import com.fusionx.lending.origination.repository.GuarantorCultivationIncomeRepository;
import com.fusionx.lending.origination.repository.GuarantorRepository;
import com.fusionx.lending.origination.repository.OtherIncomeRepository;
import com.fusionx.lending.origination.repository.OtherIncomeTypeRepository;
import com.fusionx.lending.origination.repository.SalaryIncomeRepository;
import com.fusionx.lending.origination.resource.BusinessIncomeExpenseRequestResource;
import com.fusionx.lending.origination.resource.DesignationResponse;
import com.fusionx.lending.origination.resource.FrequencyResponse;
import com.fusionx.lending.origination.resource.GuarantorBusinessIncomeSummaryResponseResource;
import com.fusionx.lending.origination.resource.GuarantorCultivationIncomeAddResource;
import com.fusionx.lending.origination.resource.GuarantorCultivationIncomeSummaryResponseResource;
import com.fusionx.lending.origination.resource.GuarantorIncomeRequestResource;
import com.fusionx.lending.origination.resource.IncomeExpenseSummaryResponseResource;
import com.fusionx.lending.origination.resource.OtherIncomeAddRequestResource;
import com.fusionx.lending.origination.resource.OtherIncomeSummaryResponseResource;
import com.fusionx.lending.origination.resource.SalaryIncomeAddRequestResource;
import com.fusionx.lending.origination.resource.SalaryIncomeSummaryResponseResource;
import com.fusionx.lending.origination.service.GuarantorIncomeService;
import com.fusionx.lending.origination.service.ValidateService;

/**
 * Guarantor Income Detail Service.
 * 
 ********************************************************************************************************
 *  ###   Date         	Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   04-MAY-2021   		      FX-6301    Sanatha      Initial Development.
 *    
 ********************************************************************************************************
 */
@Component
@Transactional(rollbackFor=Exception.class)
public class GuarantorIncomeServiceImpl extends MessagePropertyBase implements GuarantorIncomeService{
	
	@Autowired
	private GuarantorRepository guarantorRepository;
	
	@Autowired
	private SalaryIncomeRepository salaryIncomeRepository;
	
	@Autowired
	private OtherIncomeTypeRepository otherIncomeTypeRepository;
	
	@Autowired
	private OtherIncomeRepository otherIncomeRepository;
	
	@Autowired
	private BusinessIncomeExpenseRepository businessIncomeExpenseRepository;
	
	@Autowired
	private BusinessTypeRepository businessTypeRepository;
	
	@Autowired
	private GuarantorCultivationIncomeRepository guarantorCultivationIncomeRepository;
	
	@Autowired
	private CultivationCategoryRepository cultivationCategoryRepository;
	
	@Autowired
	private ValidateService validateService;
	
	
	@Override
	public void saveAndUpdateGuarantorIncomeExpense(String tenantId, Long guarantorId, GuarantorIncomeRequestResource guarantorIncomeRequestResource) {
		LoggerRequest.getInstance().logInfo1("******<<<<<<#############>>>>>>******");
 		LoggerRequest.getInstance().logInfo1("******<<<<<<IN saveAndUpdateGuarantorIncomeExpense>>>>>>******");
 		
 		LoggerRequest.getInstance().logInfo1("******<<<<<<Validate Guarantor >>>>>>******");
 		Guarantor guarantor = validateGuarantor(guarantorId);
		
 		if(guarantorIncomeRequestResource.getSalaryIncomes() != null && (!guarantorIncomeRequestResource.getSalaryIncomes().isEmpty())) {
	 		LoggerRequest.getInstance().logInfo1("******<<<<<<INITIATE saveUpdateSalaryIncomeDetail>>>>>>******");
	 		saveUpdateSalaryIncomeDetail(tenantId, guarantorId, guarantorIncomeRequestResource, guarantorIncomeRequestResource.getSalaryIncomes());
 		}
 		
 		if(guarantorIncomeRequestResource.getOtherIncomes() != null) {
	 		LoggerRequest.getInstance().logInfo1("******<<<<<<INITIATE saveUpdateOtherIncomeDetail>>>>>>******");
	 		saveUpdateOtherIncomeDetail(tenantId, guarantorId, guarantorIncomeRequestResource, guarantorIncomeRequestResource.getOtherIncomes());
 		}
 		
 		if(guarantorIncomeRequestResource.getBusinessIncome() != null) {
 			LoggerRequest.getInstance().logInfo1("******<<<<<<INITIATE saveUpdateBusinessIncomeExpense>>>>>>******");
	 		saveUpdateBusinessIncomeExpense( tenantId, guarantorIncomeRequestResource.getBusinessIncome(),  guarantor);
 		}
 		
 		if(guarantorIncomeRequestResource.getCultivationIncome() != null) {
 			LoggerRequest.getInstance().logInfo1("******<<<<<<INITIATE saveUpdateCultivationIncomeDetail>>>>>>******");
 			saveUpdateCultivationIncomeDetail( tenantId,  guarantorId,  guarantorIncomeRequestResource,  guarantorIncomeRequestResource.getCultivationIncome()) ;
 		}
		
	}
	
	// validate Guarantor ID
	private Guarantor validateGuarantor(Long guarantorId) {
		Optional<Guarantor> guarantor = guarantorRepository.findById(guarantorId);
		if(guarantor.isPresent() && guarantor.get().getStatus().equalsIgnoreCase(CommonStatus.ACTIVE.toString()))
			return guarantor.get();
		else
			throw new DetailValidateException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.GUARANTOR_ID, ServicePoint.GUARANTOR_INCOME_EXPENSE);
	}
	
	// save Salary Income details
	private void saveUpdateSalaryIncomeDetail(String tenantId, Long guarantorId, GuarantorIncomeRequestResource guarantorIncomeRequestResource, List<SalaryIncomeAddRequestResource> salaryIncomeAddRequestResources) {
		LoggerRequest.getInstance().logInfo1("******<<<<<<#############>>>>>>******");
 		LoggerRequest.getInstance().logInfo1("******<<<<<<IN saveUpdateSalaryIncomeDetail>>>>>>******");
		
 		Optional<Guarantor> guarantor = guarantorRepository.findById(guarantorId);
 		
 		Integer index=0;
		for(SalaryIncomeAddRequestResource salaryIncomeAddRequestResource : salaryIncomeAddRequestResources) {	
	 		SalaryIncome salaryIncome;
	 		
	 		//checking Salary Income request is Update or Save
	 		if(salaryIncomeAddRequestResource.getId() != null && !salaryIncomeAddRequestResource.getId().isEmpty()) {
	 			// checking Version
	 			if(salaryIncomeAddRequestResource.getVersion() != null && !salaryIncomeAddRequestResource.getVersion().isEmpty()) {
	 				
		 			Optional<SalaryIncome> relevantSalaryIncome = salaryIncomeRepository.findById(stringToLong(salaryIncomeAddRequestResource.getId()));
		 			
		 			if(!relevantSalaryIncome.isPresent()) {
		 				
		 				throw new DetailListValidateException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.SALARY_INCOME_ID, ServicePoint.GUARANTOR_SALARY_INCOME,index);
		 			}
		 			
		 			//checking Salary Income related to Guarantor 
		 			if(relevantSalaryIncome.get().getGuarantor().getId()!=guarantorId) {
		 				
		 				throw new DetailListValidateException(environment.getProperty(COMMON_INVALID_GUARANTOR_INCOME), ServiceEntity.SALARY_INCOME_ID, ServicePoint.GUARANTOR_SALARY_INCOME,index);
		 			}
		 			
		 			//checking Version mismatch
		 			if (!relevantSalaryIncome.get().getVersion().equals(stringToLong(salaryIncomeAddRequestResource.getVersion()))) {
		 				
		 				throw new DetailListValidateException(environment.getProperty(INVALID_VERSION), ServiceEntity.VERSION, ServicePoint.GUARANTOR_SALARY_INCOME,index);
		 			}
		 			
		 			//when updating checking duplicate record for same employer
		 			List<SalaryIncome> duplicateRecord = salaryIncomeRepository.findByEmployerNameAndGuarantorIdAndIdNotIn(salaryIncomeAddRequestResource.getEmployerName(),guarantor.get().getId(),stringToLong(salaryIncomeAddRequestResource.getId()));
	 				if(!duplicateRecord.isEmpty()) {
	 					throw new DetailListValidateException(environment.getProperty(EMPLOYER_DUPLICATE), ServiceEntity.CUSTOMER_ID, ServicePoint.GUARANTOR_SALARY_INCOME,index);
	 				}
		 			
		 			salaryIncome = relevantSalaryIncome.get();
		 			salaryIncome.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
					salaryIncome.setModifiedDate(getCreateOrModifyDate());
	 			
	 			}else {
	 				throw new DetailListValidateException(environment.getProperty(BLANK_VERSION), ServiceEntity.VERSION, ServicePoint.GUARANTOR_SALARY_INCOME,index);
	 			}
	 			
	 		}else {
	 			
	 			//when inserting checking duplicate record for same employer
	 			List<SalaryIncome> duplicateRecord = salaryIncomeRepository.findByEmployerNameAndGuarantorId(salaryIncomeAddRequestResource.getEmployerName(),guarantor.get().getId());
 				if(!duplicateRecord.isEmpty()) {
 					throw new DetailListValidateException(environment.getProperty(EMPLOYER_DUPLICATE), ServiceEntity.CUSTOMER_ID, ServicePoint.GUARANTOR_SALARY_INCOME,index);
 				}
	 			
	 			
	 			salaryIncome = new SalaryIncome();
	 			salaryIncome.setTenantId(tenantId);
	 			salaryIncome.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
				salaryIncome.setCreatedDate(getCreateOrModifyDate());
	 		}
	 		
			salaryIncome.setGuarantor(guarantor.get());
			salaryIncome.setEmployerName(salaryIncomeAddRequestResource.getEmployerName());
			DesignationResponse validateDesignation = validateService.validateDesignation(tenantId, salaryIncomeAddRequestResource.getDesignationId());
			salaryIncome.setDesignationId(stringToLong(validateDesignation.getId()));
			salaryIncome.setDesignationCode(validateDesignation.getDesgCode());
			salaryIncome.setDesignationName(validateDesignation.getDesgName());
			
			FrequencyResponse validateFrequency = validateService.validateFrequency(tenantId, salaryIncomeAddRequestResource.getFrequencyId());
			salaryIncome.setFrequencyId(stringToLong(salaryIncomeAddRequestResource.getFrequencyId()));
			salaryIncome.setFrequencyCode(validateFrequency.getCode());
			salaryIncome.setFrequencyName(validateFrequency.getName());
			salaryIncome.setGrossSalary(stringToBigDecimal(salaryIncomeAddRequestResource.getGrossSalary()));
			salaryIncome.setDeductions(stringToBigDecimal(salaryIncomeAddRequestResource.getDeductions()));
			salaryIncome.setNetSalary(stringToBigDecimal(salaryIncomeAddRequestResource.getNetSalary()));
			salaryIncome.setComment(salaryIncomeAddRequestResource.getComment());
			salaryIncome.setStatus(salaryIncomeAddRequestResource.getStatus());
			salaryIncome.setSyncTs(getCreateOrModifyDate());
			salaryIncomeRepository.save(salaryIncome);
			
			index++;
		}
			
	}
	
	// save Other Income details
	private void saveUpdateOtherIncomeDetail(String tenantId, Long guarantorId, GuarantorIncomeRequestResource guarantorIncomeRequestResource, List<OtherIncomeAddRequestResource> otherIncomeAddRequestResources) {
		LoggerRequest.getInstance().logInfo1("******<<<<<<#############>>>>>>******");
 		LoggerRequest.getInstance().logInfo1("******<<<<<<IN saveUpdateOtherIncomeDetail>>>>>>******");
		
 		Optional<Guarantor> guarantor = guarantorRepository.findById(guarantorId);
 		
 		Integer index=0;
		for(OtherIncomeAddRequestResource otherIncomeAddRequestResource : otherIncomeAddRequestResources) {
	 		OtherIncome otherIncome;
	 		
	 		//checking Other Income request is Update or Save
	 		if(otherIncomeAddRequestResource.getId() != null && !otherIncomeAddRequestResource.getId().isEmpty()) {
	 			// checking Version
	 			if(otherIncomeAddRequestResource.getVersion() != null && !otherIncomeAddRequestResource.getVersion().isEmpty()) {
	 				
		 			Optional<OtherIncome> relevantOtherIncome = otherIncomeRepository.findById(stringToLong(otherIncomeAddRequestResource.getId()));
		 			if(!relevantOtherIncome.isPresent()) {
		 				throw new DetailListValidateException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.OTHER_INCOME_ID, ServicePoint.GUARANTOR_OTHER_INCOME,index);
		 			}
		 			//checking Other Income related to Guarantor 
		 			if(relevantOtherIncome.get().getGuarantor().getId()!=guarantorId) {
		 				throw new DetailListValidateException(environment.getProperty(COMMON_INVALID_GUARANTOR_INCOME), ServiceEntity.OTHER_INCOME_ID, ServicePoint.GUARANTOR_OTHER_INCOME,index);
		 			}
		 			//checking Version mismatch
		 			if (!relevantOtherIncome.get().getVersion().equals(stringToLong(otherIncomeAddRequestResource.getVersion()))) {
		 				throw new DetailListValidateException(environment.getProperty(INVALID_VERSION), ServiceEntity.VERSION, ServicePoint.GUARANTOR_OTHER_INCOME,index);
		 			}
		 			
		 			
		 			//when updating checking duplicate record for same other income type
		 			List<OtherIncome> duplicateRecord = otherIncomeRepository.findByGuarantorIdAndOtherIncomeTypeIdAndIdNotIn(guarantor.get().getId(),stringToLong(otherIncomeAddRequestResource.getOtherIncomeTypeId()), stringToLong(otherIncomeAddRequestResource.getId()));
	 				if(!duplicateRecord.isEmpty()) {
	 					throw new DetailListValidateException(environment.getProperty(OTHERINCOME_DUPLICATE), ServiceEntity.CUSTOMER_ID, ServicePoint.CUSTOMER_OTHER_INCOME,index);
	 				}
		 			
		 			otherIncome = relevantOtherIncome.get();
		 			otherIncome.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
		 			otherIncome.setModifiedDate(getCreateOrModifyDate());
	 			
	 			}else {
	 				throw new DetailListValidateException(environment.getProperty(BLANK_VERSION), ServiceEntity.VERSION, ServicePoint.GUARANTOR_OTHER_INCOME,index);
	 			}
	 			
	 		}else {
	 			
	 			//when inserting checking duplicate record for same other income type
	 			List<OtherIncome> duplicateRecord = otherIncomeRepository.findByGuarantorIdAndOtherIncomeTypeId(guarantor.get().getId(),stringToLong(otherIncomeAddRequestResource.getOtherIncomeTypeId()));
 				if(!duplicateRecord.isEmpty()) {
 					throw new DetailListValidateException(environment.getProperty(OTHERINCOME_DUPLICATE), ServiceEntity.CUSTOMER_ID, ServicePoint.GUARANTOR_OTHER_INCOME,index);
 				}
	 			
	 			otherIncome = new OtherIncome();
	 			otherIncome.setTenantId(tenantId);
	 			otherIncome.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
	 			otherIncome.setCreatedDate(getCreateOrModifyDate());
	 		}
	 		
	 		Optional<OtherIncomeType> otherIncomeType = otherIncomeTypeRepository.findByIdAndCodeAndStatus(stringToLong(otherIncomeAddRequestResource.getOtherIncomeTypeId()),otherIncomeAddRequestResource.getOtherIncomeTypeCode(),"ACTIVE");
	 		if (!otherIncomeType.isPresent()) {
	 			throw new DetailListValidateException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.OTHER_INCOME_TYPE_ID, ServicePoint.GUARANTOR_OTHER_INCOME,index);
	 		}
	 		
	 		otherIncome.setGuarantor(guarantor.get());
	 		otherIncome.setOtherIncomeType(otherIncomeType.get());
	 		otherIncome.setDescription(otherIncomeAddRequestResource.getDescription());
	 		FrequencyResponse validateFrequency = validateService.validateFrequency(tenantId, otherIncomeAddRequestResource.getFrequencyId());
			otherIncome.setFrequencyId(stringToLong(otherIncomeAddRequestResource.getFrequencyId()));//need to validate in future
			otherIncome.setFrequencyCode(validateFrequency.getCode());
			otherIncome.setFrequencyName(validateFrequency.getName());
			otherIncome.setGrossIncome(stringToBigDecimal(otherIncomeAddRequestResource.getGrossIncome()));
			otherIncome.setDeductions(stringToBigDecimal(otherIncomeAddRequestResource.getDeductions()));
			otherIncome.setNetIncome(stringToBigDecimal(otherIncomeAddRequestResource.getNetIncome()));
			otherIncome.setComment(otherIncomeAddRequestResource.getComment());
			otherIncome.setStatus(otherIncomeAddRequestResource.getStatus());
			otherIncome.setSyncTs(getCreateOrModifyDate());
			otherIncomeRepository.save(otherIncome);
			
			index++;
		}
			
	}
	
	// save Cultivation Income details
	private void saveUpdateCultivationIncomeDetail(String tenantId, Long guarantorId, GuarantorIncomeRequestResource guarantorIncomeRequestResource, List<GuarantorCultivationIncomeAddResource> guarantorCultivationIncomeAddResources) {
		LoggerRequest.getInstance().logInfo1("******<<<<<<#############>>>>>>******");
 		LoggerRequest.getInstance().logInfo1("******<<<<<<IN saveUpdateCultivationIncomeDetail>>>>>>******");
		
 		Optional<Guarantor> guarantor = guarantorRepository.findById(guarantorId);
 		
 		Integer index=0;
		for(GuarantorCultivationIncomeAddResource guarantorCultivationIncomeAddResource : guarantorCultivationIncomeAddResources) {
			GuarantorIncomeCultivation guarantorIncomeCultivation;
	 		
	 		//checking Other Income request is Update or Save
	 		if(guarantorCultivationIncomeAddResource.getId() != null && !guarantorCultivationIncomeAddResource.getId().isEmpty()) {
	 			// checking Version
	 			if(guarantorCultivationIncomeAddResource.getVersion() != null && !guarantorCultivationIncomeAddResource.getVersion().isEmpty()) {
	 				
		 			Optional<GuarantorIncomeCultivation> relevantCultivationIncome = guarantorCultivationIncomeRepository.findById(stringToLong(guarantorCultivationIncomeAddResource.getId()));
		 			if(!relevantCultivationIncome.isPresent()) {
		 				throw new DetailListValidateException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.CULTIVATION_INCOME_ID, ServicePoint.GUARANTOR_CULTIVATION_INCOME,index);
		 			}
		 			//checking Cultivation Income related to Guarantor 
		 			if(relevantCultivationIncome.get().getGuarantor().getId()!=guarantorId) {
		 				throw new DetailListValidateException(environment.getProperty(COMMON_INVALID_GUARANTOR_INCOME), ServiceEntity.CULTIVATION_INCOME_ID, ServicePoint.GUARANTOR_CULTIVATION_INCOME,index);
		 			}
		 			//checking Version mismatch
		 			if (!relevantCultivationIncome.get().getVersion().equals(stringToLong(guarantorCultivationIncomeAddResource.getVersion()))) {
		 				throw new DetailListValidateException(environment.getProperty(INVALID_VERSION), ServiceEntity.VERSION, ServicePoint.GUARANTOR_CULTIVATION_INCOME,index);
		 			}
		 			
		 			//when updating checking duplicate record for same cultivation category
		 			List<GuarantorIncomeCultivation> duplicateRecord = guarantorCultivationIncomeRepository.findByGuarantorIdAndCultivationCategoryIdAndIdNotIn(guarantor.get().getId(),stringToLong(guarantorCultivationIncomeAddResource.getCultivationCategoryId()), stringToLong(guarantorCultivationIncomeAddResource.getId()));
	 				if(!duplicateRecord.isEmpty()) {
	 					throw new DetailListValidateException(environment.getProperty(CULTIVATION_CATEGORY_DUPLICATE), ServiceEntity.CUSTOMER_ID, ServicePoint.GUARANTOR_CULTIVATION_INCOME,index);
	 				}
		 			
		 			
		 			guarantorIncomeCultivation = relevantCultivationIncome.get();
		 			guarantorIncomeCultivation.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
		 			guarantorIncomeCultivation.setModifiedDate(getCreateOrModifyDate());
	 			
	 			}else {
	 				throw new DetailListValidateException(environment.getProperty(BLANK_VERSION), ServiceEntity.VERSION, ServicePoint.GUARANTOR_CULTIVATION_INCOME,index);
	 			}
	 			
	 		}else {
	 			
	 			//when inserting checking duplicate record for same cultivation category
	 			List<GuarantorIncomeCultivation> duplicateRecord = guarantorCultivationIncomeRepository.findByGuarantorIdAndCultivationCategoryId(guarantor.get().getId(),stringToLong(guarantorCultivationIncomeAddResource.getCultivationCategoryId()));
 				if(!duplicateRecord.isEmpty()) {
 					throw new DetailListValidateException(environment.getProperty(CULTIVATION_CATEGORY_DUPLICATE), ServiceEntity.CUSTOMER_ID, ServicePoint.GUARANTOR_CULTIVATION_INCOME,index);
 				}
	 			
	 			guarantorIncomeCultivation = new GuarantorIncomeCultivation();
	 			guarantorIncomeCultivation.setTenantId(tenantId);
	 			guarantorIncomeCultivation.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
	 			guarantorIncomeCultivation.setCreatedDate(getCreateOrModifyDate());
	 		}
	 		
	 		Optional<CultivationCategory> cultivationCategory = cultivationCategoryRepository.findByIdAndCodeAndStatus(stringToLong(guarantorCultivationIncomeAddResource.getCultivationCategoryId()),guarantorCultivationIncomeAddResource.getCultivationCategoryCode(),CommonStatus.ACTIVE);
	 		if (!cultivationCategory.isPresent()) {
	 			throw new DetailListValidateException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.CULTIVATION_INCOME_TYPE_ID, ServicePoint.GUARANTOR_CULTIVATION_INCOME,index);
	 		}
	 		
	 		guarantorIncomeCultivation.setGuarantor(guarantor.get());
	 		guarantorIncomeCultivation.setCultivationCategory(cultivationCategory.get());
	 		guarantorIncomeCultivation.setDescription(guarantorCultivationIncomeAddResource.getDescription());
	 		FrequencyResponse validateFrequency = validateService.validateFrequency(tenantId, guarantorCultivationIncomeAddResource.getFrequencyId());
	 		guarantorIncomeCultivation.setFrequencyId(stringToLong(validateFrequency.getId()));
	 		guarantorIncomeCultivation.setFrequencyCode(validateFrequency.getCode());
	 		guarantorIncomeCultivation.setFrequencyName(validateFrequency.getName());
	 		guarantorIncomeCultivation.setGrossIncome(stringToBigDecimal(guarantorCultivationIncomeAddResource.getGrossIncome()));
	 		guarantorIncomeCultivation.setExpences(stringToBigDecimal(guarantorCultivationIncomeAddResource.getExpences()));
	 		guarantorIncomeCultivation.setNetIncome(stringToBigDecimal(guarantorCultivationIncomeAddResource.getNetIncome()));
	 		guarantorIncomeCultivation.setComment(guarantorCultivationIncomeAddResource.getComment());
	 		guarantorIncomeCultivation.setStatus(guarantorCultivationIncomeAddResource.getStatus());
	 		guarantorIncomeCultivation.setSyncTs(getCreateOrModifyDate());
	 		guarantorCultivationIncomeRepository.save(guarantorIncomeCultivation);
			
			index++;
		}
			
	}
	
	// save Business Income details
	private void saveUpdateBusinessIncomeExpense(String tenantId, List<BusinessIncomeExpenseRequestResource> businessIncomeExpenseRequestResources, Guarantor guarantor) {
		LoggerRequest.getInstance().logInfo1("******<<<<<<#############>>>>>>******");
 		LoggerRequest.getInstance().logInfo1("******<<<<<<IN saveUpdateBusinessIncomeExpense>>>>>>******");	
		
 		Integer index=0;
		for(BusinessIncomeExpenseRequestResource businessIncomeExpenseRequestResource : businessIncomeExpenseRequestResources) {
			BusinessIncomeExpense businessIncomeExpense;
				
			Optional<BusinessType> businessType = businessTypeRepository.findById(stringToLong(businessIncomeExpenseRequestResource.getBusinessTypesId()));
			
	 		if (!businessType.isPresent()) {
	 			throw new DetailListValidateException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.BUSINESS_INCOME_TYPE_ID, ServicePoint.BUSINESS_INCOME_EXPENSE, index);
	 			}
			
	 		if(!businessType.get().getName().equalsIgnoreCase(businessIncomeExpenseRequestResource.getBusinessTypeName())) {
	 			throw new DetailListValidateException(environment.getProperty(COMMON_NOT_MATCH), ServiceEntity.BUSINESS_INCOME_TYPE_ID, ServicePoint.BUSINESS_INCOME_EXPENSE, index);
	 		}
	 		
			if(businessIncomeExpenseRequestResource.getId() != null && !businessIncomeExpenseRequestResource.getId().isEmpty()) {
				
			 Optional<BusinessIncomeExpense> isPresentBusinessIncomeExpense = businessIncomeExpenseRepository.findById(stringToLong(businessIncomeExpenseRequestResource.getId())); 
				if(!isPresentBusinessIncomeExpense.isPresent())
					throw new DetailListValidateException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.BUSINESS_INCOME_EXPENSE_ID, ServicePoint.BUSINESS_INCOME_EXPENSE,index);
			
			//when updating checking duplicate record for same business type
 			List<BusinessIncomeExpense> duplicateRecord = businessIncomeExpenseRepository.findByGuarantorIdAndBusinessTypesIdAndIdNotIn(guarantor.getId(),stringToLong(businessIncomeExpenseRequestResource.getBusinessTypesId()),stringToLong(businessIncomeExpenseRequestResource.getId()));
			if(!duplicateRecord.isEmpty()) {
				throw new DetailListValidateException(environment.getProperty(BUSINESS_TYPE_DUPLICATE), ServiceEntity.BUSINESS_INCOME_TYPE_ID, ServicePoint.BUSINESS_INCOME_EXPENSE,index);
			}	
 				
				businessIncomeExpense = isPresentBusinessIncomeExpense.get();
				businessIncomeExpense.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
				businessIncomeExpense.setModifiedDate(getCreateOrModifyDate());
					
			} else {
				
				//when inserting checking duplicate record for same business type
	 			List<BusinessIncomeExpense> duplicateRecord = businessIncomeExpenseRepository.findByGuarantorIdAndBusinessTypesId(guarantor.getId(),stringToLong(businessIncomeExpenseRequestResource.getBusinessTypesId()));
 				if(!duplicateRecord.isEmpty()) {
 					throw new DetailListValidateException(environment.getProperty(BUSINESS_TYPE_DUPLICATE), ServiceEntity.BUSINESS_INCOME_TYPE_ID, ServicePoint.BUSINESS_INCOME_EXPENSE,index);
 				}				
				
				businessIncomeExpense = new BusinessIncomeExpense();
				businessIncomeExpense.setTenantId(tenantId);
				businessIncomeExpense.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
				businessIncomeExpense.setCreatedDate(getCreateOrModifyDate());
			}
			businessIncomeExpense.setGuarantor(guarantor);
			businessIncomeExpense.setBusinessType(businessType.get());
			businessIncomeExpense.setDescription(businessIncomeExpenseRequestResource.getDescription());
			businessIncomeExpense.setFrequencyId(stringToLong(businessIncomeExpenseRequestResource.getFrequencyId()));
			businessIncomeExpense.setFrequencyCode(businessIncomeExpenseRequestResource.getFrequencyCode());
			businessIncomeExpense.setFrequencyName(businessIncomeExpenseRequestResource.getFrequencyName());
			if(businessIncomeExpenseRequestResource.getTotalGrossIncome() != null && !businessIncomeExpenseRequestResource.getTotalGrossIncome().isEmpty())
				businessIncomeExpense.setTotalGrossIncome(new BigDecimal(businessIncomeExpenseRequestResource.getTotalGrossIncome()));
			if(businessIncomeExpenseRequestResource.getTotalExpences() != null && !businessIncomeExpenseRequestResource.getTotalExpences().isEmpty())
				businessIncomeExpense.setTotalExpences(new BigDecimal(businessIncomeExpenseRequestResource.getTotalExpences()));
			if(businessIncomeExpenseRequestResource.getProfitMargin() != null && !businessIncomeExpenseRequestResource.getProfitMargin().isEmpty())
				businessIncomeExpense.setProfitMargin(new BigDecimal(businessIncomeExpenseRequestResource.getProfitMargin()));
			if(businessIncomeExpenseRequestResource.getTotalNetIncome() != null && !businessIncomeExpenseRequestResource.getTotalNetIncome().isEmpty())
				businessIncomeExpense.setTotalNetIncome(new BigDecimal(businessIncomeExpenseRequestResource.getTotalNetIncome()));
			businessIncomeExpense.setComments(businessIncomeExpenseRequestResource.getComment());
			businessIncomeExpense.setStatus(CommonStatus.valueOf(businessIncomeExpenseRequestResource.getStatus()));
			businessIncomeExpense.setSyncTs(getCreateOrModifyDate());
			
			businessIncomeExpenseRepository.save(businessIncomeExpense);
			
			index++;
		}
	}
	
	// String to Long
	private Long stringToLong(String value){
		return Long.parseLong(value);
	}
	
	// Get a created and modified date
	private Timestamp getCreateOrModifyDate() {
		Calendar calendar = Calendar.getInstance();
    	java.util.Date now = calendar.getTime();
    	return new Timestamp(now.getTime());
	}
	
	// String to BigDecimal
	private BigDecimal stringToBigDecimal(String value) {
		if (value != null) {
			return new BigDecimal(value);
		} else {
			return BigDecimal.valueOf(0.0);
		}
	}
	
	@Override
	public IncomeExpenseSummaryResponseResource incomeExpenseSummary(Long guarantorId) {
		LoggerRequest.getInstance().logInfo1("******<<<<<<#############>>>>>>******");
 		LoggerRequest.getInstance().logInfo1("******<<<<<<IN incomeExpenseSummaryResponseResource>>>>>>******");
		
 		Double totalGrossIncome=0.00;
 		Double totalDeductions=0.00;
 		Double totalNetIncome=0.00;
 		IncomeExpenseSummaryResponseResource incomeExpenseSummaryResponseResource = new IncomeExpenseSummaryResponseResource();
		
 		SalaryIncomeSummaryResponseResource salarySummary = salaryIncomeSummaryResponseResource( guarantorId);
 		OtherIncomeSummaryResponseResource otherIncomeSummary =otherIncomeSummaryResponseResource( guarantorId);
 		GuarantorCultivationIncomeSummaryResponseResource cultivationIncomeSummary = guarantorCultivationIncomeSummaryResponseResource( guarantorId);
 		GuarantorBusinessIncomeSummaryResponseResource businessIncomeSummary =guarantorBusinessIncomeSummaryResponseResource( guarantorId);
 		
 		totalGrossIncome	=  	Double.parseDouble(salarySummary.getTotalIncome()) 
 							+  	Double.parseDouble(otherIncomeSummary.getTotalIncome()) 
 							+  	Double.parseDouble(cultivationIncomeSummary.getTotalCultivationIncome())
 							+	Double.parseDouble(businessIncomeSummary.getTotalBusinessIncome())
 							;
 		
 		totalDeductions		=  	Double.parseDouble(salarySummary.getDeductions()) 
							+  	Double.parseDouble(otherIncomeSummary.getDeductions()) 
							+  	Double.parseDouble(cultivationIncomeSummary.getTotalExpences())
							+	Double.parseDouble(businessIncomeSummary.getTotalExpences())
							;
 		
 		totalNetIncome 		= 	totalGrossIncome-totalDeductions;
 		
 		incomeExpenseSummaryResponseResource.setSalaryIncomeExpenseSummary(salarySummary);
 		incomeExpenseSummaryResponseResource.setOtherIncomeExpenseSummary(otherIncomeSummary);
 		incomeExpenseSummaryResponseResource.setGuarantorCultivationIncomeSummary(cultivationIncomeSummary);
 		incomeExpenseSummaryResponseResource.setGuarantorBusinessIncomeSummary(businessIncomeSummary);
 		
 		incomeExpenseSummaryResponseResource.setTotalGrossIncome(totalGrossIncome.toString());
 		incomeExpenseSummaryResponseResource.setTotalDeductions(totalDeductions.toString());
 		incomeExpenseSummaryResponseResource.setTotalNetIncome(totalNetIncome.toString());
		
		return incomeExpenseSummaryResponseResource;
	}
	
	
	
	private List<SalaryIncome> salaryIncomeSummary(Long guarantorId){
		LoggerRequest.getInstance().logInfo1("******<<<<<<#############>>>>>>******");
 		LoggerRequest.getInstance().logInfo1("******<<<<<<IN salaryIncomeSummary>>>>>>******");
		
		List<SalaryIncome> salaryIncome = salaryIncomeRepository.findByGuarantorIdAndStatus(guarantorId, CommonStatus.ACTIVE.toString());
		
		return salaryIncome;
	}
	
	


	private SalaryIncomeSummaryResponseResource salaryIncomeSummaryResponseResource(Long guarantorId) {
		LoggerRequest.getInstance().logInfo1("******<<<<<<#############>>>>>>******");
 		LoggerRequest.getInstance().logInfo1("******<<<<<<IN salaryIncomeSummaryResponseResource>>>>>>******");
		List<SalaryIncome> salaryIncomeSummaryList = null;
		SalaryIncomeSummaryResponseResource salaryIncomeSummaryResponseResource = new SalaryIncomeSummaryResponseResource();
		salaryIncomeSummaryList =salaryIncomeSummary(guarantorId);
		Double totSummary= 0.00;
		Double deductions=0.00;
		Double netSalaryIncome=0.00;
		
		int index=0;
		for(SalaryIncome salaryIncomeSummary : salaryIncomeSummaryList) {
		
			totSummary = totSummary + Double.parseDouble(salaryIncomeSummary.getGrossSalary().toString());
			deductions = deductions + Double.parseDouble(salaryIncomeSummary.getDeductions().toString());
			index++;
			
		}
		
		netSalaryIncome = totSummary-deductions;
		salaryIncomeSummaryResponseResource.setTotalIncome(totSummary.toString());
		salaryIncomeSummaryResponseResource.setDeductions(deductions.toString());
		salaryIncomeSummaryResponseResource.setNetSalaryIncome(netSalaryIncome.toString());
		salaryIncomeSummaryResponseResource.setSalaryIncomeDetail(salaryIncomeSummaryList);
		
		return salaryIncomeSummaryResponseResource;
	}
	
	private List<OtherIncome> otherIncomeSummary(Long guarantorId){
		LoggerRequest.getInstance().logInfo1("******<<<<<<#############>>>>>>******");
 		LoggerRequest.getInstance().logInfo1("******<<<<<<IN otherIncomeSummary>>>>>>******");
		
		List<OtherIncome> otherIncome = otherIncomeRepository.findByGuarantorIdAndStatus(guarantorId, CommonStatus.ACTIVE.toString());
		
		return otherIncome;
	}
	
	private OtherIncomeSummaryResponseResource otherIncomeSummaryResponseResource(Long guarantorId) {
		LoggerRequest.getInstance().logInfo1("******<<<<<<#############>>>>>>******");
 		LoggerRequest.getInstance().logInfo1("******<<<<<<IN otherIncomeSummaryResponseResource>>>>>>******");
		List<OtherIncome> otherIncomeSummaryList = null;
		OtherIncomeSummaryResponseResource otherIncomeSummaryResponseResource = new OtherIncomeSummaryResponseResource();
		otherIncomeSummaryList =otherIncomeSummary(guarantorId);
		Double totSummary= 0.00;
		Double deductions=0.00;
		Double netIncome=0.00;
		
		int index=0;
		for(OtherIncome otherIncomeSummary : otherIncomeSummaryList) {
			
			totSummary = totSummary + Double.parseDouble(otherIncomeSummary.getGrossIncome().toString());
			deductions = deductions + Double.parseDouble(otherIncomeSummary.getDeductions().toString());
			index++;
			
		}
		
		netIncome = totSummary-deductions;
		otherIncomeSummaryResponseResource.setTotalIncome(totSummary.toString());
		otherIncomeSummaryResponseResource.setDeductions(deductions.toString());
		otherIncomeSummaryResponseResource.setNetIncome(netIncome.toString());
		otherIncomeSummaryResponseResource.setOtherIncomeDetail(otherIncomeSummaryList);
		
		return otherIncomeSummaryResponseResource;
	}
	
	private List<GuarantorIncomeCultivation> guarantorCultivationIncomeSummary(Long guarantorId){
		LoggerRequest.getInstance().logInfo1("******<<<<<<#############>>>>>>******");
 		LoggerRequest.getInstance().logInfo1("******<<<<<<IN guarantorCultivationIncomeSummary>>>>>>******");
		
		List<GuarantorIncomeCultivation> guarantorIncomeCultivation = guarantorCultivationIncomeRepository.findByGuarantorIdAndStatus(guarantorId, CommonStatus.ACTIVE.toString());
		
		return guarantorIncomeCultivation;
	}
	
	private GuarantorCultivationIncomeSummaryResponseResource guarantorCultivationIncomeSummaryResponseResource(Long guarantorId) {
		LoggerRequest.getInstance().logInfo1("******<<<<<<#############>>>>>>******");
 		LoggerRequest.getInstance().logInfo1("******<<<<<<IN guarantorCultivationIncomeSummaryResponseResource>>>>>>******");
		List<GuarantorIncomeCultivation> guarantorIncomeCultivationSummaryList = null;
		GuarantorCultivationIncomeSummaryResponseResource guarantorCultivationIncomeSummaryResponseResource = new GuarantorCultivationIncomeSummaryResponseResource();
		guarantorIncomeCultivationSummaryList =guarantorCultivationIncomeSummary(guarantorId);
		Double totSummary= 0.00;
		Double deductions=0.00;
		Double netIncome=0.00;
		
		int index=0;
		for(GuarantorIncomeCultivation guarantorIncomeCultivationSummary : guarantorIncomeCultivationSummaryList) {
			
			totSummary = totSummary + Double.parseDouble(guarantorIncomeCultivationSummary.getGrossIncome().toString());
			deductions = deductions + Double.parseDouble(guarantorIncomeCultivationSummary.getExpences().toString());
			index++;
			
		}
		
		netIncome = totSummary-deductions;
		guarantorCultivationIncomeSummaryResponseResource.setTotalCultivationIncome(totSummary.toString());
		guarantorCultivationIncomeSummaryResponseResource.setTotalExpences(deductions.toString());
		guarantorCultivationIncomeSummaryResponseResource.setNetCultivatinIncome(netIncome.toString());
		guarantorCultivationIncomeSummaryResponseResource.setCultivationIncome(guarantorIncomeCultivationSummaryList);
		
		return guarantorCultivationIncomeSummaryResponseResource;
	}
	
	private List<BusinessIncomeExpense> guarantorBusinessIncomeSummary(Long guarantorId){
		LoggerRequest.getInstance().logInfo1("******<<<<<<#############>>>>>>******");
 		LoggerRequest.getInstance().logInfo1("******<<<<<<IN guarantorBusinessIncomeSummary>>>>>>******");
		
		List<BusinessIncomeExpense> guarantorBusinessIncome = businessIncomeExpenseRepository.findByGuarantorIdAndStatus(guarantorId, CommonStatus.ACTIVE);
		
		return guarantorBusinessIncome;
	}
	
	private GuarantorBusinessIncomeSummaryResponseResource guarantorBusinessIncomeSummaryResponseResource(Long guarantorId) {
		LoggerRequest.getInstance().logInfo1("******<<<<<<#############>>>>>>******");
 		LoggerRequest.getInstance().logInfo1("******<<<<<<IN guarantorBusinessIncomeSummaryResponseResource>>>>>>******");
		List<BusinessIncomeExpense> guarantorBusinessIncomeSummaryList = null;
		GuarantorBusinessIncomeSummaryResponseResource guarantorBusinessIncomeSummaryResponseResource = new GuarantorBusinessIncomeSummaryResponseResource();
		guarantorBusinessIncomeSummaryList =guarantorBusinessIncomeSummary(guarantorId);
		Double totSummary= 0.00;
		Double deductions=0.00;
		Double netIncome=0.00;
		
		int index=0;
		for(BusinessIncomeExpense guarantorBusinessIncomeSummary : guarantorBusinessIncomeSummaryList) {
			
			totSummary = totSummary + Double.parseDouble(guarantorBusinessIncomeSummary.getTotalGrossIncome().toString());
			deductions = deductions + Double.parseDouble(guarantorBusinessIncomeSummary.getTotalExpences().toString());
			index++;
			
		}
		
		netIncome = totSummary-deductions;
		guarantorBusinessIncomeSummaryResponseResource.setTotalBusinessIncome(totSummary.toString());
		guarantorBusinessIncomeSummaryResponseResource.setTotalExpences(deductions.toString());
		guarantorBusinessIncomeSummaryResponseResource.setNetBusinessIncome(netIncome.toString());
		guarantorBusinessIncomeSummaryResponseResource.setBusinessIncome(guarantorBusinessIncomeSummaryList);
		
		return guarantorBusinessIncomeSummaryResponseResource;
	}
	

}
