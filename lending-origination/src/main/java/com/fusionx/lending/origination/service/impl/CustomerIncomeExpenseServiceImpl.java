package com.fusionx.lending.origination.service.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.origination.base.MessagePropertyBase;
import com.fusionx.lending.origination.core.LoggerRequest;
import com.fusionx.lending.origination.core.LogginAuthentcation;
import com.fusionx.lending.origination.domain.BusinessExpenseType;
import com.fusionx.lending.origination.domain.BusinessIncomeExpense;
import com.fusionx.lending.origination.domain.BusinessType;
import com.fusionx.lending.origination.domain.CommonList;
import com.fusionx.lending.origination.domain.CultivationCategory;
import com.fusionx.lending.origination.domain.Customer;
import com.fusionx.lending.origination.domain.CustomerCultivationExpense;
import com.fusionx.lending.origination.domain.CustomerCultivationIncome;
import com.fusionx.lending.origination.domain.DisbursementDetails;
import com.fusionx.lending.origination.domain.ExpenceTypeCultivationCategory;
import com.fusionx.lending.origination.domain.ExpenceTypeCultivationCategoryDetails;
import com.fusionx.lending.origination.domain.ExpenseType;
import com.fusionx.lending.origination.domain.ExpenseTypeHouseholdExpenseCategoriesDetails;
import com.fusionx.lending.origination.domain.FinancialCommitment;
import com.fusionx.lending.origination.domain.HouseHoldExpense;
import com.fusionx.lending.origination.domain.IncomeExpenseDetails;
import com.fusionx.lending.origination.domain.IncomeExpenseTax;
import com.fusionx.lending.origination.domain.IncomeType;
import com.fusionx.lending.origination.domain.OtherIncome;
import com.fusionx.lending.origination.domain.OtherIncomeType;
import com.fusionx.lending.origination.domain.SalaryIncome;
import com.fusionx.lending.origination.enums.CommonListReferenceCodes;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.enums.ServiceEntity;
import com.fusionx.lending.origination.enums.ServicePoint;
import com.fusionx.lending.origination.enums.Type;
import com.fusionx.lending.origination.exception.CustomerIncomeExpenseValidateException;
import com.fusionx.lending.origination.exception.DetailListValidateException;
import com.fusionx.lending.origination.exception.DetailValidateException;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.repository.BusinessExpenseTypeRepository;
import com.fusionx.lending.origination.repository.BusinessIncomeExpenseRepository;
import com.fusionx.lending.origination.repository.BusinessTypeRepository;
import com.fusionx.lending.origination.repository.CommonListRepository;
import com.fusionx.lending.origination.repository.CultivationCategoryRepository;
import com.fusionx.lending.origination.repository.CustomerCultivationExpenseRepository;
import com.fusionx.lending.origination.repository.CustomerCultivationIncomeRepository;
import com.fusionx.lending.origination.repository.CustomerRepository;
import com.fusionx.lending.origination.repository.ExpenceTypeCultivationCategoryDetailsRepository;
import com.fusionx.lending.origination.repository.ExpenceTypeCultivationCategoryRepository;
import com.fusionx.lending.origination.repository.ExpenseTypeHouseholdExpenseCategoriesDetailsRepository;
import com.fusionx.lending.origination.repository.FinancialCommitmentRepository;
import com.fusionx.lending.origination.repository.HouseHoldExpenseRepository;
import com.fusionx.lending.origination.repository.IncomeExpenseDetailsRepository;
import com.fusionx.lending.origination.repository.IncomeExpenseTaxRepository;
import com.fusionx.lending.origination.repository.IncomeTypeRepository;
import com.fusionx.lending.origination.repository.OtherIncomeRepository;
import com.fusionx.lending.origination.repository.OtherIncomeTypeRepository;
import com.fusionx.lending.origination.repository.SalaryIncomeRepository;
import com.fusionx.lending.origination.resource.BusinessIncomeExpenseRequestResource;
import com.fusionx.lending.origination.resource.CustomerBusinessIncomeExpenseTaxResponseResource;
import com.fusionx.lending.origination.resource.CustomerBusinessIncomeSummaryResponseResource;
import com.fusionx.lending.origination.resource.CustomerCultivationExpenseResource;
import com.fusionx.lending.origination.resource.CustomerCultivationExpenseSummaryResponseResource;
import com.fusionx.lending.origination.resource.CustomerCultivationIncomeResource;
import com.fusionx.lending.origination.resource.CustomerCultivationIncomeSummaryResponseResource;
import com.fusionx.lending.origination.resource.CustomerIncomeExpenseRequestResource;
import com.fusionx.lending.origination.resource.FinancialCommitmentAddRequestResource;
import com.fusionx.lending.origination.resource.FinancialCommitmentSummaryResponseResource;
import com.fusionx.lending.origination.resource.HouseHoldExpenseAddRequestResource;
import com.fusionx.lending.origination.resource.HouseHoldExpenseSummaryResponseResource;
import com.fusionx.lending.origination.resource.IncomeExpenseDetailsResource;
import com.fusionx.lending.origination.resource.IncomeExpenseDetailsSummaryResponseResource;
import com.fusionx.lending.origination.resource.IncomeExpenseSummaryResponseResource;
import com.fusionx.lending.origination.resource.IncomeExpenseTaxResource;
import com.fusionx.lending.origination.resource.IncomeExpenseTaxSummaryResponseResource;
import com.fusionx.lending.origination.resource.OtherIncomeAddRequestResource;
import com.fusionx.lending.origination.resource.OtherIncomeSummaryResponseResource;
import com.fusionx.lending.origination.resource.SalaryIncomeAddRequestResource;
import com.fusionx.lending.origination.resource.SalaryIncomeSummaryResponseResource;
import com.fusionx.lending.origination.service.CustomerIncomeExpenseService;
import com.fusionx.lending.origination.service.ValidateService;
/**
 * Customer Income Expense Service Impl
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description  
 *-------------------------------------------------------------------------------------------------------
 *   1   13-05-2021      		     			MenukaJ       Created
 *   2	 13-MAY-2021							Sanatha		  Added Customer Other/Salary/HouseHold and Financial commitment income expense details. Added Customer income expense summary.(Parallel  dev with Menuka)
 *    
 ********************************************************************************************************
 */
@Component
@Transactional(rollbackFor=Exception.class)
public class CustomerIncomeExpenseServiceImpl extends MessagePropertyBase implements CustomerIncomeExpenseService {
	
	@Autowired
	private CustomerRepository salCustRepository;
	
	@Autowired
	private SalaryIncomeRepository salaryIncomeRepository;
	
	@Autowired
	private OtherIncomeRepository otherIncomeRepository;
	
	@Autowired
	private OtherIncomeTypeRepository otherIncomeTypeRepository;
	
	@Autowired
	private BusinessIncomeExpenseRepository businessIncomeExpenseRepository;
	
	@Autowired
	private ValidateService validateService;
	
	@Autowired
	private IncomeExpenseDetailsRepository incomeExpenseDetailsRepository;
	
	@Autowired
	private IncomeExpenseTaxRepository incomeExpenseTaxRepository;
	
	@Autowired
	private CustomerCultivationIncomeRepository customerCultivationIncomeRepository;
	
	@Autowired
	private CustomerCultivationExpenseRepository customerCultivationExpenseRepository;
	
	@Autowired
	private CommonListRepository commonListRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private BusinessTypeRepository businessTypeRepository;
	
	@Autowired
	private BusinessExpenseTypeRepository businessExpenseTypeRepository;
	
	@Autowired
	private CultivationCategoryRepository cultivationCategoryRepository;
	
	@Autowired
	private ExpenceTypeCultivationCategoryRepository expenceTypeCultivationCategoryRepository;
	
	@Autowired
	private ExpenceTypeCultivationCategoryDetailsRepository expenceTypeCultiCatDetRepository;
	
	@Autowired
	private FinancialCommitmentRepository financialCommitmentRepository;
	
	@Autowired
	private HouseHoldExpenseRepository houseHoldExpenseRepository;
	
	@Autowired
	private ExpenseTypeHouseholdExpenseCategoriesDetailsRepository expenseTypeHouseholdExpenseCategoriesDetailsRepository;
	
	@Autowired
	private IncomeTypeRepository incomeTypeRepository;
	
	Double customerBusinessGrossIncome=0.00;
	Double customerBusinessExpense=0.00;
	Double customerBusinessNetIncome=0.00;
	Double customerBusinessTaxAmount=0.00;
	Double customerBusinessProfitAfterTax=0.00;
	Double customerCultivationExpenseAmount=0.00;
	Double customerCultivationGrossIncome=0.00;
	Double customerCultivationExpense=0.00;
	Double customerCultivationNetIncome=0.00;

	@Override
	public void saveCustomerIncomeExpense(String tenantId, Long customerId,
			CustomerIncomeExpenseRequestResource customerIncomeExpenseRequestResource) {
		LoggerRequest.getInstance().logInfo1("******<<<<<<#############>>>>>>******");
 		LoggerRequest.getInstance().logInfo1("******<<<<<<IN saveCustomerIncomeExpense>>>>>>******");
		
		if(LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
        	throw new ValidateRecordException(environment.getProperty(COMMON_NOT_NULL), USERNAME);
		
		LoggerRequest.getInstance().logInfo("Customer Income Expense ********************* Validate Customer **********************************");
		Customer customer = validateCustomer(customerId);
		
		if(customerIncomeExpenseRequestResource.getBusinessIncomeExpenses() != null && !customerIncomeExpenseRequestResource.getBusinessIncomeExpenses().isEmpty()) {
			LoggerRequest.getInstance().logInfo("Customer Income Expense ********************* Save/Update and Validate Business Income Expense **********************************");
			saveUpdateBusinessIncomeExpense(tenantId, customerIncomeExpenseRequestResource.getBusinessIncomeExpenses(), customer);
		}
		
		if(customerIncomeExpenseRequestResource.getCustomerCultivationIncomes() != null && !customerIncomeExpenseRequestResource.getCustomerCultivationIncomes().isEmpty()) {
			LoggerRequest.getInstance().logInfo("Customer Income Expense ********************* Save/Update and Validate Customer Cultivation Income**********************************");
			saveCustomerCultivationIncome(tenantId, customerIncomeExpenseRequestResource.getCustomerCultivationIncomes(), customer);
		}

		if(customerIncomeExpenseRequestResource.getSalaryIncomes() != null) {
			LoggerRequest.getInstance().logInfo1("******<<<<<<INITIATE saveSalaryDetail>>>>>>******");
			saveSalaryIncomeDetail(tenantId, customerId, customerIncomeExpenseRequestResource, customerIncomeExpenseRequestResource.getSalaryIncomes());
		}
		
		if(customerIncomeExpenseRequestResource.getOtherIncomes() != null) {
			LoggerRequest.getInstance().logInfo1("******<<<<<<INITIATE saveOtherIncomeDetail>>>>>>******");
 			saveOtherIncomeDetail(tenantId, customerId, customerIncomeExpenseRequestResource, customerIncomeExpenseRequestResource.getOtherIncomes());
		}
		
		if(customerIncomeExpenseRequestResource.getFinancialCommitment() != null) {
			LoggerRequest.getInstance().logInfo1("******<<<<<<INITIATE saveFinancialCommitmentDetail>>>>>>******");
 			saveFinancialCommitmentDetail(tenantId, customerId, customerIncomeExpenseRequestResource, customerIncomeExpenseRequestResource.getFinancialCommitment());
		}
		
		if(customerIncomeExpenseRequestResource.getHouseHoldExpense() != null) {
			LoggerRequest.getInstance().logInfo1("******<<<<<<INITIATE saveHousEHoldExpenseDetail>>>>>>******");
			saveHousEHoldExpenseDetail( tenantId,  customerId,  customerIncomeExpenseRequestResource, customerIncomeExpenseRequestResource.getHouseHoldExpense()) ;
		}
	}
	
	// save Salary Income details
	private void saveSalaryIncomeDetail(String tenantId, Long customerId, CustomerIncomeExpenseRequestResource customerIncomeExpenseRequestResource, List<SalaryIncomeAddRequestResource> salaryIncomeAddRequestResources) {
		LoggerRequest.getInstance().logInfo1("******<<<<<<#############>>>>>>******");
 		LoggerRequest.getInstance().logInfo1("******<<<<<<IN saveSalaryDetail>>>>>>******");
		
 		Optional<Customer> customer = salCustRepository.findById(customerId);
 		
 		Integer index=0;
		for(SalaryIncomeAddRequestResource salaryIncomeAddRequestResource : salaryIncomeAddRequestResources) {
	 		SalaryIncome salaryIncome;
			
	 		if(salaryIncomeAddRequestResource.getSalaryType().equalsIgnoreCase("OTHER")) {
	 			if(salaryIncomeAddRequestResource.getKeyPersonId()==null ) {//&& salaryIncomeAddRequestResource.getKeyPersonId().isEmpty()
	 				throw new DetailListValidateException(environment.getProperty(COMMON_NOT_NULL), ServiceEntity.KEY_PERSON_ID, ServicePoint.CUSTOMER_SALARY_INCOME,index);
	 			}
	 		}
	 		
	 		//checking Salary Income request is Update or Save
	 		if(salaryIncomeAddRequestResource.getId() != null && !salaryIncomeAddRequestResource.getId().isEmpty()) {
	 			// checking Version
	 			if(salaryIncomeAddRequestResource.getVersion() != null && !salaryIncomeAddRequestResource.getVersion().isEmpty()) {
	 				
		 			Optional<SalaryIncome> relevantSalaryIncome = salaryIncomeRepository.findById(stringToLong(salaryIncomeAddRequestResource.getId()));
		 			if(!relevantSalaryIncome.isPresent()) {
		 				throw new DetailListValidateException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.SALARY_INCOME_ID, ServicePoint.CUSTOMER_SALARY_INCOME,index);
		 			}
		 			//checking Salary Income related to Customer 
		 			if(relevantSalaryIncome.get().getCustomer().getId()!=customerId) {
		 				throw new DetailListValidateException(environment.getProperty(COMMON_INVALID_CUSTOMER_INCOME), ServiceEntity.SALARY_INCOME_ID, ServicePoint.CUSTOMER_SALARY_INCOME,index);
		 			}
		 			//checking Version mismatch
		 			if (!relevantSalaryIncome.get().getVersion().equals(stringToLong(salaryIncomeAddRequestResource.getVersion()))) {
		 				throw new DetailListValidateException(environment.getProperty(INVALID_VERSION), ServiceEntity.VERSION, ServicePoint.CUSTOMER_SALARY_INCOME,index);
		 			}
		 			
		 			//when updating checking duplicate record for same employer
		 			if(salaryIncomeAddRequestResource.getSalaryType().equalsIgnoreCase("OWN")) {
		 				List<SalaryIncome> duplicateRecord = salaryIncomeRepository.findByEmployerNameAndCustomerIdAndIdNotIn(salaryIncomeAddRequestResource.getEmployerName(),customer.get().getId(),stringToLong(salaryIncomeAddRequestResource.getId()));
		 				if(!duplicateRecord.isEmpty()) {
		 					throw new DetailListValidateException(environment.getProperty(EMPLOYER_DUPLICATE), ServiceEntity.CUSTOMER_ID, ServicePoint.CUSTOMER_SALARY_INCOME,index);
		 				}
		 			}else if(salaryIncomeAddRequestResource.getSalaryType().equalsIgnoreCase("OTHER")) {
		 				List<SalaryIncome> duplicateRecord = salaryIncomeRepository.findByEmployerNameAndKeyPersonIdAndCustomerIdAndIdNotIn(salaryIncomeAddRequestResource.getEmployerName(),stringToLong(salaryIncomeAddRequestResource.getKeyPersonId()),customer.get().getId(),stringToLong(salaryIncomeAddRequestResource.getId()));
		 				if(!duplicateRecord.isEmpty()) {
		 					throw new DetailListValidateException(environment.getProperty(EMPLOYER_DUPLICATE), ServiceEntity.CUSTOMER_ID, ServicePoint.CUSTOMER_SALARY_INCOME,index);
		 				}
		 			}
		 			
		 			salaryIncome = relevantSalaryIncome.get();
		 			salaryIncome.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
					salaryIncome.setModifiedDate(getCreateOrModifyDate());
	 			
	 			}else {
	 				throw new DetailListValidateException(environment.getProperty(BLANK_VERSION), ServiceEntity.VERSION, ServicePoint.CUSTOMER_SALARY_INCOME,index);
	 			}
	 			
	 		}else {
	 			
	 			//when inserting checking duplicate record for same employer
	 			if(salaryIncomeAddRequestResource.getSalaryType().equalsIgnoreCase("OWN")) {
	 				List<SalaryIncome> duplicateRecord = salaryIncomeRepository.findByEmployerNameAndCustomerId(salaryIncomeAddRequestResource.getEmployerName(),customer.get().getId());
	 				if(!duplicateRecord.isEmpty()) {
	 					throw new DetailListValidateException(environment.getProperty(EMPLOYER_DUPLICATE), ServiceEntity.CUSTOMER_ID, ServicePoint.CUSTOMER_SALARY_INCOME,index);
	 				}
	 			}else if(salaryIncomeAddRequestResource.getSalaryType().equalsIgnoreCase("OTHER")) {
	 				List<SalaryIncome> duplicateRecord = salaryIncomeRepository.findByEmployerNameAndKeyPersonIdAndCustomerId(salaryIncomeAddRequestResource.getEmployerName(),stringToLong(salaryIncomeAddRequestResource.getKeyPersonId()),customer.get().getId());
	 				if(!duplicateRecord.isEmpty()) {
	 					throw new DetailListValidateException(environment.getProperty(EMPLOYER_DUPLICATE), ServiceEntity.CUSTOMER_ID, ServicePoint.CUSTOMER_SALARY_INCOME,index);
	 				}
	 			}
	 			
	 			salaryIncome = new SalaryIncome();
	 			salaryIncome.setTenantId(tenantId);
	 			salaryIncome.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
				salaryIncome.setCreatedDate(getCreateOrModifyDate());
	 		}
	 		
			
			salaryIncome.setCustomer(customer.get());
			salaryIncome.setSalaryType(salaryIncomeAddRequestResource.getSalaryType());
			if(salaryIncome.getKeyPersonId()!=null) {
				salaryIncome.setKeyPersonId(stringToLong(salaryIncomeAddRequestResource.getKeyPersonId()));//need to validate in future
			}else {
				salaryIncome.setKeyPersonId(null);
			}
			
			salaryIncome.setEmployerName(salaryIncomeAddRequestResource.getEmployerName());
			salaryIncome.setDesignationId(stringToLong(salaryIncomeAddRequestResource.getDesignationId()));//need to validate in future
			salaryIncome.setDesignationCode(salaryIncomeAddRequestResource.getDesignationCode());
			salaryIncome.setDesignationName(salaryIncomeAddRequestResource.getDesignationName());
			salaryIncome.setFrequencyId(stringToLong(salaryIncomeAddRequestResource.getFrequencyId()));//need to validate in future
			salaryIncome.setFrequencyCode(salaryIncomeAddRequestResource.getFrequencyCode());
			salaryIncome.setFrequencyName(salaryIncomeAddRequestResource.getFrequencyName());
			salaryIncome.setOccurance(stringToLong(salaryIncomeAddRequestResource.getOccurance()));
			salaryIncome.setSalaryIncome(stringToBigDecimal(salaryIncomeAddRequestResource.getSalaryIncome()));
			salaryIncome.setTotSalaryPerFreq(stringToBigDecimal(salaryIncomeAddRequestResource.getTotSalaryPerFreq()));
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
	private void saveOtherIncomeDetail(String tenantId, Long customerId, CustomerIncomeExpenseRequestResource customerIncomeExpenseRequestResource, List<OtherIncomeAddRequestResource> otherIncomeAddRequestResources) {
		LoggerRequest.getInstance().logInfo1("******<<<<<<#############>>>>>>******");
 		LoggerRequest.getInstance().logInfo1("******<<<<<<IN saveOtherIncomeDetail>>>>>>******");
		
 		Optional<Customer> customer = salCustRepository.findById(customerId);
 		
 		Integer index=0;
		for(OtherIncomeAddRequestResource otherIncomeAddRequestResource : otherIncomeAddRequestResources) {
	 		OtherIncome otherIncome;
	 		
	 		//checking Other Income request is Update or Save
	 		if(otherIncomeAddRequestResource.getId() != null && !otherIncomeAddRequestResource.getId().isEmpty()) {
	 			// checking Version
	 			if(otherIncomeAddRequestResource.getVersion() != null && !otherIncomeAddRequestResource.getVersion().isEmpty()) {
	 				
		 			Optional<OtherIncome> relevantOtherIncome = otherIncomeRepository.findById(stringToLong(otherIncomeAddRequestResource.getId()));
		 			if(!relevantOtherIncome.isPresent()) {
		 				throw new DetailListValidateException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.OTHER_INCOME_ID, ServicePoint.CUSTOMER_OTHER_INCOME,index);
		 			}
		 			//checking Other Income related to Customer 
		 			if(relevantOtherIncome.get().getCustomer().getId()!=customerId) {
		 				throw new DetailListValidateException(environment.getProperty(COMMON_INVALID_CUSTOMER_INCOME), ServiceEntity.OTHER_INCOME_ID, ServicePoint.CUSTOMER_OTHER_INCOME,index);
		 			}
		 			//checking Version mismatch
		 			if (!relevantOtherIncome.get().getVersion().equals(stringToLong(otherIncomeAddRequestResource.getVersion()))) {
		 				throw new DetailListValidateException(environment.getProperty(INVALID_VERSION), ServiceEntity.VERSION, ServicePoint.CUSTOMER_OTHER_INCOME,index);
		 			}
		 			
		 			//when updating checking duplicate record for same other income type
		 			List<OtherIncome> duplicateRecord = otherIncomeRepository.findByCustomerIdAndOtherIncomeTypeIdAndIdNotIn(customer.get().getId(),stringToLong(otherIncomeAddRequestResource.getOtherIncomeTypeId()), stringToLong(otherIncomeAddRequestResource.getId()));
	 				if(!duplicateRecord.isEmpty()) {
	 					throw new DetailListValidateException(environment.getProperty(OTHERINCOME_DUPLICATE), ServiceEntity.CUSTOMER_ID, ServicePoint.CUSTOMER_OTHER_INCOME,index);
	 				}
		 			
		 			otherIncome = relevantOtherIncome.get();
		 			otherIncome.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
		 			otherIncome.setModifiedDate(getCreateOrModifyDate());
	 			
	 			}else {
	 				throw new DetailListValidateException(environment.getProperty(BLANK_VERSION), ServiceEntity.VERSION, ServicePoint.CUSTOMER_OTHER_INCOME,index);
	 			}
	 			
	 		}else {
	 			
	 			//when inserting checking duplicate record for same other income type
	 			List<OtherIncome> duplicateRecord = otherIncomeRepository.findByCustomerIdAndOtherIncomeTypeId(customer.get().getId(),stringToLong(otherIncomeAddRequestResource.getOtherIncomeTypeId()));
 				if(!duplicateRecord.isEmpty()) {
 					throw new DetailListValidateException(environment.getProperty(OTHERINCOME_DUPLICATE), ServiceEntity.CUSTOMER_ID, ServicePoint.CUSTOMER_OTHER_INCOME,index);
 				}
	 			
	 			
	 			otherIncome = new OtherIncome();
	 			otherIncome.setTenantId(tenantId);
	 			otherIncome.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
	 			otherIncome.setCreatedDate(getCreateOrModifyDate());
	 		}
	 		
	 		Optional<OtherIncomeType> otherIncomeType = otherIncomeTypeRepository.findByIdAndCodeAndStatus(stringToLong(otherIncomeAddRequestResource.getOtherIncomeTypeId()),otherIncomeAddRequestResource.getOtherIncomeTypeCode(),"ACTIVE");
	 		if (!otherIncomeType.isPresent()) {
	 			throw new DetailListValidateException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.OTHER_INCOME_TYPE_ID, ServicePoint.CUSTOMER_OTHER_INCOME,index);
	 		}
			
	 		otherIncome.setCustomer(customer.get());
	 		otherIncome.setOtherIncomeType(otherIncomeType.get());
	 		otherIncome.setDescription(otherIncomeAddRequestResource.getDescription());
			otherIncome.setFrequencyId(stringToLong(otherIncomeAddRequestResource.getFrequencyId()));//need to validate in future
			otherIncome.setFrequencyCode(otherIncomeAddRequestResource.getFrequencyCode());
			otherIncome.setFrequencyName(otherIncomeAddRequestResource.getFrequencyName());
			otherIncome.setOccurance(stringToLong(otherIncomeAddRequestResource.getOccurance()));
			otherIncome.setIncomePerOccurance(stringToBigDecimal(otherIncomeAddRequestResource.getIncomePerOccurance()));
			otherIncome.setTotIncomePerFreq(stringToBigDecimal(otherIncomeAddRequestResource.getTotIncomePerFreq()));
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
	
	// save Financial Commitment details
	private void saveFinancialCommitmentDetail(String tenantId, Long customerId, CustomerIncomeExpenseRequestResource customerIncomeExpenseRequestResource, List<FinancialCommitmentAddRequestResource> financialCommitmentAddRequestResources) {
		LoggerRequest.getInstance().logInfo1("******<<<<<<#############>>>>>>******");
 		LoggerRequest.getInstance().logInfo1("******<<<<<<IN saveFinancialCommitmentDetail>>>>>>******");
		
 		Optional<Customer> customer = salCustRepository.findById(customerId);
 		
 		Integer index=0;
		for(FinancialCommitmentAddRequestResource financialCommitmentAddRequestResource : financialCommitmentAddRequestResources) {
			FinancialCommitment financialCommitment;
			
			//checking Other Income request is Update or Save
	 		if(financialCommitmentAddRequestResource.getId() != null && !financialCommitmentAddRequestResource.getId().isEmpty()) {
	 			// checking Version
	 			if(financialCommitmentAddRequestResource.getVersion() != null && !financialCommitmentAddRequestResource.getVersion().isEmpty()) {
	 				
		 			Optional<FinancialCommitment> relevantFinancialCommitment = financialCommitmentRepository.findById(stringToLong(financialCommitmentAddRequestResource.getId()));
		 			if(!relevantFinancialCommitment.isPresent()) {
		 				throw new DetailListValidateException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.FINANCIAL_COMMITMENT_ID, ServicePoint.CUSTOMER_FINANCIAL_COMMITMENT,index);
		 			}
		 			//checking Other Income related to Customer 
		 			if(relevantFinancialCommitment.get().getCustomer().getId()!=customerId) {
		 				throw new DetailListValidateException(environment.getProperty(COMMON_INVALID_CUSTOMER_INCOME), ServiceEntity.FINANCIAL_COMMITMENT_ID, ServicePoint.CUSTOMER_FINANCIAL_COMMITMENT,index);
		 			}
		 			//checking Version mismatch
		 			if (!relevantFinancialCommitment.get().getVersion().equals(stringToLong(financialCommitmentAddRequestResource.getVersion()))) {
		 				throw new DetailListValidateException(environment.getProperty(INVALID_VERSION), ServiceEntity.VERSION, ServicePoint.CUSTOMER_FINANCIAL_COMMITMENT,index);
		 			}
		 			
		 			financialCommitment = relevantFinancialCommitment.get();
		 			financialCommitment.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
		 			financialCommitment.setModifiedDate(getCreateOrModifyDate());
	 			
	 			}else {
	 				throw new DetailListValidateException(environment.getProperty(BLANK_VERSION), ServiceEntity.VERSION, ServicePoint.CUSTOMER_FINANCIAL_COMMITMENT,index);
	 			}
	 			
	 		}else {
	 			financialCommitment = new FinancialCommitment();
	 			financialCommitment.setTenantId(tenantId);
	 			financialCommitment.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
	 			financialCommitment.setCreatedDate(getCreateOrModifyDate());
	 		}
			
	 		LoggerRequest.getInstance().logInfo1("******<<<<<<  Validate Type Of Commitment >>>>>>******");
			CommonList typeOfCommitment = comnListValidation(financialCommitmentAddRequestResource.getTypeOfCommitmentId(), financialCommitmentAddRequestResource.getTypeOfCommitmentName(), CommonListReferenceCodes.TYP_OF_COMMITMENT, ServiceEntity.TYPE_OF_COMMITMENT, ServicePoint.CUSTOMER_FINANCIAL_COMMITMENT,index);
			
			LoggerRequest.getInstance().logInfo1("******<<<<<<  Validate Type Of Facility >>>>>>******");
			CommonList typeOfFacility = comnListValidation(financialCommitmentAddRequestResource.getTypeOfFacilityId(), financialCommitmentAddRequestResource.getTypeOfFacilityName(), CommonListReferenceCodes.TYP_OF_FACILITY, ServiceEntity.TYPE_OF_FACILITY, ServicePoint.CUSTOMER_FINANCIAL_COMMITMENT,index);
			
	 		
			financialCommitment.setCustomer(customer.get());
			financialCommitment.setCategory(financialCommitmentAddRequestResource.getCategory());
			financialCommitment.setTypeOfCommitment(typeOfCommitment);
			financialCommitment.setTypeOfCommitmentName(financialCommitmentAddRequestResource.getTypeOfCommitmentName());
			financialCommitment.setTypeOfFacility(typeOfFacility);
			financialCommitment.setTypeOfFacilityName(financialCommitmentAddRequestResource.getTypeOfFacilityName());
			if(financialCommitmentAddRequestResource.getExternalInstituteId() != null && !financialCommitmentAddRequestResource.getExternalInstituteId().isEmpty())
				financialCommitment.setExternalInstituteId(stringToLong(financialCommitmentAddRequestResource.getExternalInstituteId()));
			if(financialCommitmentAddRequestResource.getInternalInstituteId() != null && !financialCommitmentAddRequestResource.getInternalInstituteId().isEmpty())
				financialCommitment.setInternalInstituteId(stringToLong(financialCommitmentAddRequestResource.getInternalInstituteId()));
			financialCommitment.setRepaymentFrequencyId(stringToLong(financialCommitmentAddRequestResource.getRepaymentFrequencyId()));
			financialCommitment.setFacilityAmount(stringToBigDecimal(financialCommitmentAddRequestResource.getFacilityAmount()));
			financialCommitment.setRental(stringToBigDecimal(financialCommitmentAddRequestResource.getRental()));
			financialCommitment.setCalculationFrequencyId(stringToLong(financialCommitmentAddRequestResource.getCalculationFrequencyId())); //need to validate in future
			financialCommitment.setCalculationFrequencyCode(financialCommitmentAddRequestResource.getCalculationFrequencyCode());
			financialCommitment.setCalculationFrequencyName(financialCommitmentAddRequestResource.getCalculationFrequencyName());
			financialCommitment.setRentalCalcPerFreq(stringToBigDecimal(financialCommitmentAddRequestResource.getRentalCalcPerFreq()));
			financialCommitment.setNoOfRentalsLeft(stringToLong(financialCommitmentAddRequestResource.getNoOfRentalsLeft()));
			financialCommitment.setToalOutstanding(stringToBigDecimal(financialCommitmentAddRequestResource.getToalOutstanding()));			
			financialCommitment.setComment(financialCommitmentAddRequestResource.getComment());
			financialCommitment.setStatus(financialCommitmentAddRequestResource.getStatus());
			financialCommitment.setSyncTs(getCreateOrModifyDate());
			financialCommitmentRepository.save(financialCommitment);
			
			index++;
		}
			
	}
	
	// save House Hold Expense details
	private void saveHousEHoldExpenseDetail(String tenantId, Long customerId, CustomerIncomeExpenseRequestResource customerIncomeExpenseRequestResource, List<HouseHoldExpenseAddRequestResource> houseHoldExpenseAddRequestResources) {
		LoggerRequest.getInstance().logInfo1("******<<<<<<#############>>>>>>******");
 		LoggerRequest.getInstance().logInfo1("******<<<<<<IN saveHousEHoldExpenseDetail>>>>>>******");
		
 		Optional<Customer> customer = salCustRepository.findById(customerId);
 		
 		Integer index=0;
		for(HouseHoldExpenseAddRequestResource houseHoldExpenseAddRequestResource : houseHoldExpenseAddRequestResources) {
			HouseHoldExpense houseHoldExpense;
	 		
	 		//checking Other Income request is Update or Save
	 		if(houseHoldExpenseAddRequestResource.getId() != null && !houseHoldExpenseAddRequestResource.getId().isEmpty()) {
	 			// checking Version
	 			if(houseHoldExpenseAddRequestResource.getVersion() != null && !houseHoldExpenseAddRequestResource.getVersion().isEmpty()) {
	 				
		 			Optional<HouseHoldExpense> relevantHouseHoldExpense = houseHoldExpenseRepository.findById(stringToLong(houseHoldExpenseAddRequestResource.getId()));
		 			if(!relevantHouseHoldExpense.isPresent()) {
		 				throw new DetailListValidateException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.OTHER_INCOME_ID, ServicePoint.CUSTOMER_OTHER_INCOME,index);
		 			}
		 			//checking Other Income related to Customer 
		 			if(relevantHouseHoldExpense.get().getCustomer().getId()!=customerId) {
		 				throw new DetailListValidateException(environment.getProperty(COMMON_INVALID_CUSTOMER_INCOME), ServiceEntity.OTHER_INCOME_ID, ServicePoint.CUSTOMER_OTHER_INCOME,index);
		 			}
		 			//checking Version mismatch
		 			if (!relevantHouseHoldExpense.get().getVersion().equals(stringToLong(houseHoldExpenseAddRequestResource.getVersion()))) {
		 				throw new DetailListValidateException(environment.getProperty(INVALID_VERSION), ServiceEntity.VERSION, ServicePoint.CUSTOMER_OTHER_INCOME,index);
		 			}
		 			
		 			houseHoldExpense = relevantHouseHoldExpense.get();
		 			houseHoldExpense.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
		 			houseHoldExpense.setModifiedDate(getCreateOrModifyDate());
	 			
	 			}else {
	 				throw new DetailListValidateException(environment.getProperty(BLANK_VERSION), ServiceEntity.VERSION, ServicePoint.CUSTOMER_OTHER_INCOME,index);
	 			}
	 			
	 		}else {
	 			houseHoldExpense = new HouseHoldExpense();
	 			houseHoldExpense.setTenantId(tenantId);
	 			houseHoldExpense.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
	 			houseHoldExpense.setCreatedDate(getCreateOrModifyDate());
	 		}
	 		
	 		Optional<ExpenseTypeHouseholdExpenseCategoriesDetails> expenseTypeHouseholdExpenseCategoriesDetails = expenseTypeHouseholdExpenseCategoriesDetailsRepository.findByIdAndStatus(stringToLong(houseHoldExpenseAddRequestResource.getExpenseTypeExpCategoryDetId()),CommonStatus.ACTIVE);
	 		if (!expenseTypeHouseholdExpenseCategoriesDetails.isPresent()) {
	 			throw new DetailListValidateException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.OTHER_INCOME_TYPE_ID, ServicePoint.CUSTOMER_OTHER_INCOME,index);
	 		}
			
	 		houseHoldExpense.setCustomer(customer.get());
	 		houseHoldExpense.setExpenseTypeHouseholdExpenseCategoriesDetails(expenseTypeHouseholdExpenseCategoriesDetails.get());
	 		houseHoldExpense.setOccuranceFrequencyId(stringToLong(houseHoldExpenseAddRequestResource.getOccuranceFrequencyId()));//need to validate in future
	 		houseHoldExpense.setOccuranceFrequencyCode(houseHoldExpenseAddRequestResource.getOccuranceFrequencyCode());
	 		houseHoldExpense.setOccuranceFrequencyName(houseHoldExpenseAddRequestResource.getOccuranceFrequencyName());
	 		houseHoldExpense.setCost(stringToBigDecimal(houseHoldExpenseAddRequestResource.getCost()));
	 		houseHoldExpense.setCalculationFrequencyId(stringToLong(houseHoldExpenseAddRequestResource.getCalculationFrequencyId()));//need to validate in future
	 		houseHoldExpense.setCalculationFrequencyCode(houseHoldExpenseAddRequestResource.getCalculationFrequencyCode());
	 		houseHoldExpense.setCalculationFrequencyName(houseHoldExpenseAddRequestResource.getCalculationFrequencyName());
	 		houseHoldExpense.setFinalCost(stringToBigDecimal(houseHoldExpenseAddRequestResource.getFinalCost()));
	 		houseHoldExpense.setStatus(houseHoldExpenseAddRequestResource.getStatus());
	 		houseHoldExpense.setSyncTs(getCreateOrModifyDate());
	 		houseHoldExpenseRepository.save(houseHoldExpense);
			
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

	private void saveUpdateBusinessIncomeExpense(String tenantId, List<BusinessIncomeExpenseRequestResource> businessIncomeExpenses, Customer customer) {
		Integer index = 0;
		for(BusinessIncomeExpenseRequestResource businessIncomeExpenseRequestResource : businessIncomeExpenses) {
			BusinessIncomeExpense businessIncomeExpense;
			
			LoggerRequest.getInstance().logInfo("Customer Income Expense ********************* Validate Business Type **********************************");
			BusinessType businessType = validateBusinessType(validateService.stringToLong(businessIncomeExpenseRequestResource.getBusinessTypesId()), businessIncomeExpenseRequestResource.getBusinessTypeName(), index);
			
			if(businessIncomeExpenseRequestResource.getId() != null && !businessIncomeExpenseRequestResource.getId().isEmpty()) {
				
				 Optional<BusinessIncomeExpense> isPresentBusinessIncomeExpense = businessIncomeExpenseRepository.findById(validateService.stringToLong(businessIncomeExpenseRequestResource.getId())); 
					if(!isPresentBusinessIncomeExpense.isPresent())
						throw new DetailListValidateException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.BUSINESS_INCOME_EXPENSE_ID, ServicePoint.BUSINESS_INCOME_EXPENSES, index);
				
				Optional<BusinessIncomeExpense> existIncomeExpense = businessIncomeExpenseRepository.findByCustomerIdAndBusinessTypesIdAndStatusAndIdNotIn(customer.getId(), businessType.getId(), CommonStatus.ACTIVE, validateService.stringToLong(businessIncomeExpenseRequestResource.getId()));
				if(existIncomeExpense.isPresent())
					throw new DetailListValidateException(environment.getProperty(COMMON_DUPLICATE), ServiceEntity.BUSINESS_INCOME_EXPENSE_ID, ServicePoint.BUSINESS_INCOME_EXPENSES, index);
				
				if(businessIncomeExpenseRequestResource.getVersion() != null && !businessIncomeExpenseRequestResource.getVersion().isEmpty()) {
					if (!isPresentBusinessIncomeExpense.get().getVersion().equals(stringToLong(businessIncomeExpenseRequestResource.getVersion())))
						throw new DetailListValidateException(environment.getProperty(INVALID_VERSION), ServiceEntity.VERSION, ServicePoint.BUSINESS_INCOME_EXPENSES,index);
				}else {
					throw new DetailListValidateException(environment.getProperty(BLANK_VERSION), ServiceEntity.VERSION, ServicePoint.BUSINESS_INCOME_EXPENSES,index);
				}
				
					businessIncomeExpense = isPresentBusinessIncomeExpense.get();
					businessIncomeExpense.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
					businessIncomeExpense.setModifiedDate(validateService.getCreateOrModifyDate());
					
			} else {
				
				Optional<BusinessIncomeExpense> existIncomeExpense = businessIncomeExpenseRepository.findByCustomerIdAndBusinessTypesIdAndStatus(customer.getId(), businessType.getId(), CommonStatus.ACTIVE);
				if(existIncomeExpense.isPresent())
					throw new DetailListValidateException(environment.getProperty(COMMON_DUPLICATE), ServiceEntity.BUSINESS_INCOME_EXPENSE_ID, ServicePoint.BUSINESS_INCOME_EXPENSES, index);
				
				businessIncomeExpense = new BusinessIncomeExpense();
				businessIncomeExpense.setTenantId(tenantId);
				businessIncomeExpense.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
				businessIncomeExpense.setCreatedDate(validateService.getCreateOrModifyDate());
			}
			businessIncomeExpense.setCustomer(customer);
			businessIncomeExpense.setBusinessType(businessType);
			businessIncomeExpense.setDescription(businessIncomeExpenseRequestResource.getDescription());
			businessIncomeExpense.setFrequencyId(validateService.stringToLong(businessIncomeExpenseRequestResource.getFrequencyId()));
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
			businessIncomeExpense.setSyncTs(validateService.getCreateOrModifyDate());
			
			businessIncomeExpense = businessIncomeExpenseRepository.saveAndFlush(businessIncomeExpense);
			
			if(businessIncomeExpenseRequestResource.getIncomeExpenseDetails() != null && !businessIncomeExpenseRequestResource.getIncomeExpenseDetails().isEmpty()) {
				Integer index1 = 0;
				for(IncomeExpenseDetailsResource incomeExpenseDetailsResource : businessIncomeExpenseRequestResource.getIncomeExpenseDetails()) {
					IncomeType incomeType = null;
					ExpenseType expenseType = null;
					Boolean isIncome;
					
					if(incomeExpenseDetailsResource.getType().equalsIgnoreCase(Type.INCOME.toString())) {
						isIncome =true;
						if(incomeExpenseDetailsResource.getIncomeTypeId() != null && !incomeExpenseDetailsResource.getIncomeTypeId().isEmpty()) {
							LoggerRequest.getInstance().logInfo("Customer Income Expense ********************* Validate Income Type **********************************");
							incomeType = validateBusinessIncomeType(validateService.stringToLong(incomeExpenseDetailsResource.getIncomeTypeId()), incomeExpenseDetailsResource.getIncomeTypeName(),index, index1);
							LoggerRequest.getInstance().logInfo("Customer Income Expense ********************* Save or Update Expense Type **********************************");
							saveUpdateIncomeExpenseDetails(tenantId, businessIncomeExpense, expenseType, incomeType, incomeExpenseDetailsResource, index, index1, isIncome);
						} else {
							throw new CustomerIncomeExpenseValidateException(environment.getProperty(COMMON_NOT_NULL), ServiceEntity.INCOME_TYPE_ID, ServicePoint.INCOME_EXPENSE_DETAILS, index, index1);
						}
					} else  {
						isIncome =false;
						if(incomeExpenseDetailsResource.getExpenseTypeId() != null && !incomeExpenseDetailsResource.getExpenseTypeId().isEmpty()) {
							LoggerRequest.getInstance().logInfo("Customer Income Expense ********************* Validate Expense Type **********************************");
							expenseType = validateBusinessIncomeExpense(businessType.getId(), validateService.stringToLong(incomeExpenseDetailsResource.getExpenseTypeId()), incomeExpenseDetailsResource.getExpenseTypeName(),index, index1);
							LoggerRequest.getInstance().logInfo("Customer Income Expense ********************* Save or Update Expense Type **********************************");
							saveUpdateIncomeExpenseDetails(tenantId, businessIncomeExpense, expenseType, incomeType, incomeExpenseDetailsResource, index,index1, isIncome);
						} else {
							throw new CustomerIncomeExpenseValidateException(environment.getProperty(COMMON_NOT_NULL), ServiceEntity.EXPENSE_TYPE_ID, ServicePoint.INCOME_EXPENSE_DETAILS,index, index1);
						}
					}
					index++;
				}
			}
			
			if(businessIncomeExpenseRequestResource.getIncomeExpenseTax() != null && !businessIncomeExpenseRequestResource.getIncomeExpenseTax().isEmpty()) {
				Integer index1 = 0;
				for(IncomeExpenseTaxResource incomeExpenseTaxResource : businessIncomeExpenseRequestResource.getIncomeExpenseTax()) {
					LoggerRequest.getInstance().logInfo("Customer Income Expense ********************* Validate Tax ApplicableOn with Common List **********************************");
					CommonList applicableOn = comnListBulkValidation(incomeExpenseTaxResource.getApplicableOnId(), incomeExpenseTaxResource.getApplicableOnName(), CommonListReferenceCodes.TAX_APP_ON, ServiceEntity.TAX_APPLICABLE_ON_ID, ServicePoint.INCOME_EXPENSE_TAX, index, index1);
					LoggerRequest.getInstance().logInfo("Customer Income Expense ********************* Save or Update Income Expense Tax **********************************");
					saveUpdateIncomeExpenseTax(tenantId, businessIncomeExpense, incomeExpenseTaxResource, applicableOn, index, index1);
					index++;
				}
			}
			index++;
		}
	}
	
	private IncomeExpenseDetails saveUpdateIncomeExpenseDetails(String tenantId, BusinessIncomeExpense businessIncomeExpense, ExpenseType expenseType,IncomeType incomeType, IncomeExpenseDetailsResource incomeExpenseDetailsResource, Integer index, Integer index1,Boolean isIncome) {
		
		IncomeExpenseDetails incomeExpenseDetails;
		
		if(incomeExpenseDetailsResource.getId() != null && !incomeExpenseDetailsResource.getId().isEmpty()) {
			Optional<IncomeExpenseDetails> isPresentIncomeExpenseDetails =  incomeExpenseDetailsRepository.findById(validateService.stringToLong(incomeExpenseDetailsResource.getId()));
			
			if(!isPresentIncomeExpenseDetails.isPresent())
				throw new CustomerIncomeExpenseValidateException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.INCOME_EXPENSE_DETAILS_ID, ServicePoint.INCOME_EXPENSE_DETAILS, index, index1);
			
			incomeExpenseDetails = isPresentIncomeExpenseDetails.get();
			if(isIncome) {
				Optional<IncomeExpenseDetails> existIncomeExpenseDetails = incomeExpenseDetailsRepository.findByBusinessIncomeExpenseIdAndIncomeTypeIdAndStatusAndIdNotIn(businessIncomeExpense.getId(), incomeType.getId(), CommonStatus.ACTIVE, validateService.stringToLong(incomeExpenseDetailsResource.getId()));
				if(existIncomeExpenseDetails.isPresent())
					throw new CustomerIncomeExpenseValidateException(environment.getProperty(COMMON_DUPLICATE), ServiceEntity.INCOME_EXPENSE_DETAILS_ID, ServicePoint.INCOME_EXPENSE_DETAILS, index, index1);
				incomeExpenseDetails.setIncomeType(incomeType);
			} else {
				Optional<IncomeExpenseDetails> existIncomeExpenseDetails = incomeExpenseDetailsRepository.findByBusinessIncomeExpenseIdAndExpenseTypeIdAndStatusAndIdNotIn(businessIncomeExpense.getId(), expenseType.getId(), CommonStatus.ACTIVE, validateService.stringToLong(incomeExpenseDetailsResource.getId()));
				if(existIncomeExpenseDetails.isPresent())
					throw new CustomerIncomeExpenseValidateException(environment.getProperty(COMMON_DUPLICATE), ServiceEntity.INCOME_EXPENSE_DETAILS_ID, ServicePoint.INCOME_EXPENSE_DETAILS, index, index1);
				incomeExpenseDetails.setExpenseType(expenseType);
			}
			incomeExpenseDetails.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
			incomeExpenseDetails.setModifiedDate(validateService.getCreateOrModifyDate());
			
		} else {
			incomeExpenseDetails =  new IncomeExpenseDetails();
			incomeExpenseDetails.setTenantId(tenantId);
			incomeExpenseDetails.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
			incomeExpenseDetails.setCreatedDate(validateService.getCreateOrModifyDate());
			if(isIncome) {
				Optional<IncomeExpenseDetails> existIncomeExpenseDetails = incomeExpenseDetailsRepository.findByBusinessIncomeExpenseIdAndIncomeTypeIdAndStatus(businessIncomeExpense.getId(), incomeType.getId(), CommonStatus.ACTIVE);
				if(existIncomeExpenseDetails.isPresent())
					throw new CustomerIncomeExpenseValidateException(environment.getProperty(COMMON_DUPLICATE), ServiceEntity.INCOME_EXPENSE_DETAILS_ID, ServicePoint.INCOME_EXPENSE_DETAILS, index, index1);
				incomeExpenseDetails.setIncomeType(incomeType);
			} else {
				Optional<IncomeExpenseDetails> existIncomeExpenseDetails = incomeExpenseDetailsRepository.findByBusinessIncomeExpenseIdAndExpenseTypeIdAndStatus(businessIncomeExpense.getId(), expenseType.getId(), CommonStatus.ACTIVE);
				if(existIncomeExpenseDetails.isPresent())
					throw new CustomerIncomeExpenseValidateException(environment.getProperty(COMMON_DUPLICATE), ServiceEntity.INCOME_EXPENSE_DETAILS_ID, ServicePoint.INCOME_EXPENSE_DETAILS, index, index1);
				incomeExpenseDetails.setExpenseType(expenseType);
			}
		}
		
		incomeExpenseDetails.setBusinessIncomeExpense(businessIncomeExpense);
		incomeExpenseDetails.setType(Type.valueOf(incomeExpenseDetailsResource.getType()));
		incomeExpenseDetails.setDescription(incomeExpenseDetailsResource.getDescription());
		incomeExpenseDetails.setFrequencyId(validateService.stringToLong(incomeExpenseDetailsResource.getFrequencyId()));
		if(incomeExpenseDetailsResource.getGrossIncome() != null && !incomeExpenseDetailsResource.getGrossIncome().isEmpty())
			incomeExpenseDetails.setGrossIncome(new BigDecimal(incomeExpenseDetailsResource.getGrossIncome()));
		if(incomeExpenseDetailsResource.getAmount() != null && !incomeExpenseDetailsResource.getAmount().isEmpty())
			incomeExpenseDetails.setAmount(new BigDecimal(incomeExpenseDetailsResource.getGrossIncome()));
		incomeExpenseDetails.setStatus(CommonStatus.valueOf(incomeExpenseDetailsResource.getStatus()));
		incomeExpenseDetails.setSyncTs(validateService.getCreateOrModifyDate());
		
		return incomeExpenseDetailsRepository.saveAndFlush(incomeExpenseDetails);
	}
	
	private IncomeExpenseTax saveUpdateIncomeExpenseTax(String tenantId, BusinessIncomeExpense businessIncomeExpense, IncomeExpenseTaxResource incomeExpenseTaxResource, CommonList applicableOn, Integer index, Integer index1) {
		
		IncomeExpenseTax incomeExpenseTax;
		
		if(incomeExpenseTaxResource.getId() != null && !incomeExpenseTaxResource.getId().isEmpty()) {
			
			Optional<IncomeExpenseTax> isPresentIncomeExpenseTax = incomeExpenseTaxRepository.findById(validateService.stringToLong(incomeExpenseTaxResource.getId()));
			
			if(isPresentIncomeExpenseTax.isPresent())
				throw new CustomerIncomeExpenseValidateException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.INCOME_EXPENSE_TAX_ID, ServicePoint.INCOME_EXPENSE_TAX, index, index1);
			
			Optional<IncomeExpenseTax> existIncomeExpenseTax = incomeExpenseTaxRepository.findByBusinessIncomeExpenseIdAndTaxTypeCodeAndStatusAndIdNotIn(businessIncomeExpense.getId(), incomeExpenseTaxResource.getTaxTypeCode(), CommonStatus.ACTIVE, validateService.stringToLong(incomeExpenseTaxResource.getId()));
			
			if(!existIncomeExpenseTax.isPresent())
				throw new CustomerIncomeExpenseValidateException(environment.getProperty(COMMON_DUPLICATE), ServiceEntity.INCOME_EXPENSE_TAX_ID, ServicePoint.INCOME_EXPENSE_TAX, index, index1);
			
			incomeExpenseTax = isPresentIncomeExpenseTax.get();
			incomeExpenseTax.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
			incomeExpenseTax.setModifiedDate(validateService.getCreateOrModifyDate());
		} else {
			
			Optional<IncomeExpenseTax> existIncomeExpenseTax = incomeExpenseTaxRepository.findByBusinessIncomeExpenseIdAndTaxTypeCodeAndStatus(businessIncomeExpense.getId(), incomeExpenseTaxResource.getTaxTypeCode(), CommonStatus.ACTIVE);
			if(existIncomeExpenseTax.isPresent())
				throw new CustomerIncomeExpenseValidateException(environment.getProperty(COMMON_DUPLICATE), ServiceEntity.INCOME_EXPENSE_TAX_ID, ServicePoint.INCOME_EXPENSE_TAX, index, index1);
			
			incomeExpenseTax = new IncomeExpenseTax();
			incomeExpenseTax.setTenantId(tenantId);
			incomeExpenseTax.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
			incomeExpenseTax.setCreatedDate(validateService.getCreateOrModifyDate());
		}
		incomeExpenseTax.setBusinessIncomeExpense(businessIncomeExpense);
		incomeExpenseTax.setTaxTypeId(validateService.stringToLong(incomeExpenseTaxResource.getTaxTypeId()));
		incomeExpenseTax.setTaxTypeCode(incomeExpenseTaxResource.getTaxTypeCode());
		incomeExpenseTax.setTaxTypeName(incomeExpenseTaxResource.getTaxTypeName());
		incomeExpenseTax.setApplicableOnId(applicableOn);
		incomeExpenseTax.setFrequencyId(validateService.stringToLong(incomeExpenseTaxResource.getFrequencyId()));
		if(incomeExpenseTaxResource.getTaxRate() != null && !incomeExpenseTaxResource.getTaxRate().isEmpty())
			incomeExpenseTax.setTaxRate(new BigDecimal(incomeExpenseTaxResource.getTaxRate()));
		if(incomeExpenseTaxResource.getTaxAmount() != null && !incomeExpenseTaxResource.getTaxAmount().isEmpty())
			incomeExpenseTax.setTaxAmount(new BigDecimal(incomeExpenseTaxResource.getTaxAmount()));
		if(incomeExpenseTaxResource.getAmount() != null && !incomeExpenseTaxResource.getAmount().isEmpty())
			incomeExpenseTax.setAmount(new BigDecimal(incomeExpenseTaxResource.getAmount()));
		incomeExpenseTax.setStatus(CommonStatus.valueOf(incomeExpenseTaxResource.getStatus()));
		incomeExpenseTax.setSyncTs(validateService.getCreateOrModifyDate());
		return incomeExpenseTaxRepository.saveAndFlush(incomeExpenseTax);
	}
	
	private void saveCustomerCultivationIncome(String tenantId, List<CustomerCultivationIncomeResource> customerCultivationIncomes, Customer customer) {
		
		Integer index = 0;
		for(CustomerCultivationIncomeResource customerCultivationIncomeResource : customerCultivationIncomes) {
			
			LoggerRequest.getInstance().logInfo("Customer Income Expense ********************* Validate Cultivation Category**********************************");
			CultivationCategory cultivationCategory = validateCultivationCategory(validateService.stringToLong(customerCultivationIncomeResource.getCultivationCategoryId()), customerCultivationIncomeResource.getCultivationCategoryName(), index);
			
			LoggerRequest.getInstance().logInfo("Customer Income Expense ********************* Validate Ownership**********************************");
			CommonList ownership = comnListBulkValidation(customerCultivationIncomeResource.getOwnershipId(), customerCultivationIncomeResource.getOwnershipName(), CommonListReferenceCodes.OWNERSHIP_TYP, ServiceEntity.OWNERSHIP_ID, ServicePoint.CUSTOMER_CUTIVATION_EXPENSE, index);
			
			LoggerRequest.getInstance().logInfo("Customer Income Expense ********************* Validate Land Ownership**********************************");
			CommonList landOwnership = comnListBulkValidation(customerCultivationIncomeResource.getLandOwnershipId(), customerCultivationIncomeResource.getLandOwnershipName(), CommonListReferenceCodes.LAND_OWNERSHIP_TYP, ServiceEntity.LAND_OWNERSHIP_ID, ServicePoint.CUSTOMER_CUTIVATION_EXPENSE, index);
			
			LoggerRequest.getInstance().logInfo("Customer Income Expense ********************* Validate NoOf Unit**********************************");
			CommonList noOfUnit = comnListBulkValidation(customerCultivationIncomeResource.getNoOfUnitId(), customerCultivationIncomeResource.getNoOfUnitName(), CommonListReferenceCodes.UNIT_OF_MEASSURE, ServiceEntity.NO_OF_UNIT_ID, ServicePoint.CUSTOMER_CUTIVATION_EXPENSE, index);
			
			CustomerCultivationIncome customerCultivationIncome;
			
			if(customerCultivationIncomeResource.getId() != null && !customerCultivationIncomeResource.getId().isEmpty()) {
				
				Optional<CustomerCultivationIncome> isPresentCusCultiIncome = customerCultivationIncomeRepository.findById(validateService.stringToLong(customerCultivationIncomeResource.getId()));
				
				if(!isPresentCusCultiIncome.isPresent())
					throw new DetailListValidateException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.CUSTOMER_CULTIVATION_INCOME_ID, ServicePoint.CUSTOMER_CUTIVATION_EXPENSE, index);
				
				if(customerCultivationIncomeResource.getVersion() != null && !customerCultivationIncomeResource.getVersion().isEmpty()) {
					if (!isPresentCusCultiIncome.get().getVersion().equals(stringToLong(customerCultivationIncomeResource.getVersion())))
						throw new DetailListValidateException(environment.getProperty(INVALID_VERSION), ServiceEntity.VERSION, ServicePoint.CUSTOMER_CUTIVATION_EXPENSE,index);
				}else {
					throw new DetailListValidateException(environment.getProperty(BLANK_VERSION), ServiceEntity.VERSION, ServicePoint.CUSTOMER_CUTIVATION_EXPENSE,index);
				}
				
				Optional<CustomerCultivationIncome> existCusCultiIncome = customerCultivationIncomeRepository.findByCustomerIdAndCultivationCategoryIdAndStatusAndIdNotIn(customer.getId(), cultivationCategory.getId(), CommonStatus.ACTIVE, validateService.stringToLong(customerCultivationIncomeResource.getId()));
				if(existCusCultiIncome.isPresent())
					throw new DetailListValidateException(environment.getProperty(COMMON_DUPLICATE), ServiceEntity.CUSTOMER_CULTIVATION_INCOME_ID, ServicePoint.CUSTOMER_CUTIVATION_EXPENSE, index);
				
				customerCultivationIncome = isPresentCusCultiIncome.get();
				customerCultivationIncome.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
				customerCultivationIncome.setModifiedDate(validateService.getCreateOrModifyDate());
				
			} else {
				
				Optional<CustomerCultivationIncome> existCusCultiIncome = customerCultivationIncomeRepository.findByCustomerIdAndCultivationCategoryIdAndStatus(customer.getId(), cultivationCategory.getId(), CommonStatus.ACTIVE);
				if(existCusCultiIncome.isPresent())
					throw new DetailListValidateException(environment.getProperty(COMMON_DUPLICATE), ServiceEntity.CUSTOMER_CULTIVATION_INCOME_ID, ServicePoint.CUSTOMER_CUTIVATION_EXPENSE, index);
				
				customerCultivationIncome = new CustomerCultivationIncome();
				customerCultivationIncome.setTenantId(tenantId);
				customerCultivationIncome.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
				customerCultivationIncome.setCreatedDate(validateService.getCreateOrModifyDate());
			}
			customerCultivationIncome.setCustomer(customer);
			customerCultivationIncome.setCultivationCategory(cultivationCategory);
			customerCultivationIncome.setDescription(customerCultivationIncomeResource.getDescription());
			customerCultivationIncome.setOwnershipId(ownership);
			customerCultivationIncome.setLandOwnershipId(landOwnership);
			customerCultivationIncome.setExperienceId(validateService.stringToLong(customerCultivationIncomeResource.getExperienceId()));
			customerCultivationIncome.setOccurrenceFrequencyId(validateService.stringToLong(customerCultivationIncomeResource.getOccurrenceFrequencyId()));
			customerCultivationIncome.setOccurrenceId(validateService.stringToLong(customerCultivationIncomeResource.getOccurrenceId()));
			customerCultivationIncome.setCalculationFrequencyId(validateService.stringToLong(customerCultivationIncomeResource.getCalculationFrequencyId()));
			customerCultivationIncome.setOccurrenceId(validateService.stringToLong(customerCultivationIncomeResource.getOccurrenceId()));
			customerCultivationIncome.setCalculationFrequencyId(validateService.stringToLong(customerCultivationIncomeResource.getCalculationFrequencyId()));
			customerCultivationIncome.setNoOfUnitId(noOfUnit);
			customerCultivationIncome.setNoOfUnitValue(new BigDecimal(customerCultivationIncomeResource.getNoOfUnitValue()));
			customerCultivationIncome.setCurrencyId(validateService.stringToLong(customerCultivationIncomeResource.getCurrencyId()));
			customerCultivationIncome.setPricePerUnit(new BigDecimal(customerCultivationIncomeResource.getPricePerUnit()));
			customerCultivationIncome.setTotalGrossIncome(new BigDecimal(customerCultivationIncomeResource.getTotalGrossIncome()));
			customerCultivationIncome.setComments(customerCultivationIncomeResource.getComment());
			customerCultivationIncome.setStatus(CommonStatus.valueOf(customerCultivationIncomeResource.getStatus()));
			customerCultivationIncome.setSyncTs(validateService.getCreateOrModifyDate());
			customerCultivationIncome =  customerCultivationIncomeRepository.saveAndFlush(customerCultivationIncome);
			
			if(customerCultivationIncomeResource.getCustomerCultivationExpenses() != null && !customerCultivationIncomeResource.getCustomerCultivationExpenses().isEmpty()) {
				Integer index1 = 0;
				for(CustomerCultivationExpenseResource customerCultivationExpenseResource : customerCultivationIncomeResource.getCustomerCultivationExpenses()) {
					
					LoggerRequest.getInstance().logInfo("Customer Income Expense ********************* Validate Expense Type*********************************");
					ExpenseType expenseType = validateExpenseTypeWithCultivation(cultivationCategory.getId(), validateService.stringToLong(customerCultivationExpenseResource.getExpenseTypeId()), customerCultivationExpenseResource.getExpenseTypeName(), index, index1);
					
					LoggerRequest.getInstance().logInfo("Customer Income Expense ********************* Save or Update Customer Cultivation Expense*********************************");
					saveUpdateCustomerCultivationExpense(tenantId, customerCultivationExpenseResource, customerCultivationIncome, expenseType, index, index1);
					
					index1++;
				}
			}
			index++;
		}
	}
	
	private CustomerCultivationExpense saveUpdateCustomerCultivationExpense(String tenantId,CustomerCultivationExpenseResource customerCultivationExpenseResource, CustomerCultivationIncome customerCultivationIncome, ExpenseType expenseType, Integer index, Integer index1) {
		
		CustomerCultivationExpense customerCultivationExpense;
		
		if(customerCultivationExpenseResource.getId() != null && !customerCultivationExpenseResource.getId().isEmpty()) {
			
			Optional<CustomerCultivationExpense> isPresentCusCultiExpense = customerCultivationExpenseRepository.findById(validateService.stringToLong(customerCultivationExpenseResource.getId()));
			
			if(!isPresentCusCultiExpense.isPresent())
				throw new CustomerIncomeExpenseValidateException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.CUSTOMER_CULTIVATION_EXPENSE_ID, ServicePoint.CUSTOMER_CUTIVATION_EXPENSE, index, index1);
			
			Optional<CustomerCultivationExpense> existCusCultiExpense = customerCultivationExpenseRepository.findByCustomerCultivationIncomeIdAndExpenseTypeIdAndStatusAndIdNotIn(customerCultivationIncome.getId(), expenseType.getId(), CommonStatus.ACTIVE, validateService.stringToLong(customerCultivationExpenseResource.getId()));
			if(existCusCultiExpense.isPresent())
				throw new CustomerIncomeExpenseValidateException(environment.getProperty(COMMON_DUPLICATE), ServiceEntity.CUSTOMER_CULTIVATION_EXPENSE_ID, ServicePoint.CUSTOMER_CUTIVATION_EXPENSE, index, index1);
			
			customerCultivationExpense = isPresentCusCultiExpense.get();
			customerCultivationExpense.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
			customerCultivationExpense.setModifiedDate(validateService.getCreateOrModifyDate());
		} else {
			Optional<CustomerCultivationExpense> existCusCultiExpense = customerCultivationExpenseRepository.findByCustomerCultivationIncomeIdAndExpenseTypeIdAndStatus(customerCultivationIncome.getId(), expenseType.getId(), CommonStatus.ACTIVE);
			if(existCusCultiExpense.isPresent())
				throw new CustomerIncomeExpenseValidateException(environment.getProperty(COMMON_DUPLICATE), ServiceEntity.CUSTOMER_CULTIVATION_EXPENSE_ID, ServicePoint.CUSTOMER_CUTIVATION_EXPENSE, index, index1);
			
			customerCultivationExpense = new CustomerCultivationExpense();
			customerCultivationExpense.setTenantId(tenantId);
			customerCultivationExpense.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
			customerCultivationExpense.setCreatedDate(validateService.getCreateOrModifyDate());
		}
		customerCultivationExpense.setOccurrenceId(validateService.stringToLong(customerCultivationExpenseResource.getOccurrenceId()));
		customerCultivationExpense.setCustomerCultivationIncome(customerCultivationIncome);
		customerCultivationExpense.setExpenseType(expenseType);
		customerCultivationExpense.setDescription(customerCultivationExpenseResource.getDescription());
		customerCultivationExpense.setOccurrenceFrequencyId(validateService.stringToLong(customerCultivationExpenseResource.getOccurrenceFrequencyId()));
		customerCultivationExpense.setOccurrenceFrequencyId(validateService.stringToLong(customerCultivationExpenseResource.getOccurrenceId()));
		customerCultivationExpense.setCost(new BigDecimal(customerCultivationExpenseResource.getCost()));
		customerCultivationExpense.setCalculationFrequencyId(validateService.stringToLong(customerCultivationExpenseResource.getCalculationFrequencyId()));
		customerCultivationExpense.setFinalCost(new BigDecimal(customerCultivationExpenseResource.getFinalCost()));
		customerCultivationExpense.setStatus(CommonStatus.valueOf(customerCultivationExpenseResource.getStatus()));
		customerCultivationExpense.setSyncTs(validateService.getCreateOrModifyDate());
		return customerCultivationExpenseRepository.saveAndFlush(customerCultivationExpense);
		
	}
	
	private CommonList comnListBulkValidation(String id, String name, CommonListReferenceCodes referenceCode, ServiceEntity serviceEntity, ServicePoint servicePoint, Integer index, Integer index1) {
		if(id !=null && !id.isEmpty()) {
			Optional<CommonList> commonList=commonListRepository.findById(validateService.stringToLong(id));
			if(commonList.isPresent() && commonList.get().getName().equalsIgnoreCase(name) && commonList.get().getReferenceCode().equalsIgnoreCase(referenceCode.toString()) 
					&& commonList.get().getStatus().toString().equalsIgnoreCase(CommonStatus.ACTIVE.toString())) {
				return commonList.get();
			}else {
				throw new CustomerIncomeExpenseValidateException(environment.getProperty(COMMON_INVALID_VALUE), serviceEntity, servicePoint, index, index1);
			}
		}
		return null;
	}
	
	private CommonList comnListBulkValidation(String id, String name, CommonListReferenceCodes referenceCode, ServiceEntity serviceEntity, ServicePoint servicePoint, Integer index) {
		if(id !=null && !id.isEmpty()) {
			Optional<CommonList> commonList=commonListRepository.findById(validateService.stringToLong(id));
			if(commonList.isPresent() && commonList.get().getName().equalsIgnoreCase(name) && commonList.get().getReferenceCode().equalsIgnoreCase(referenceCode.toString()) 
					&& commonList.get().getStatus().toString().equalsIgnoreCase(CommonStatus.ACTIVE.toString())) {
				return commonList.get();
			}else {
				throw new DetailListValidateException(environment.getProperty(COMMON_INVALID_VALUE), serviceEntity, servicePoint, index);
			}
		}
		return null;
	}
	
	private CommonList comnListValidation(String id, String name, CommonListReferenceCodes referenceCode, ServiceEntity serviceEntity, ServicePoint servicePoint) {
		if(id !=null && !id.isEmpty()) {
			Optional<CommonList> commonList=commonListRepository.findById(Long.parseLong(id));
			if(commonList.isPresent() && commonList.get().getName().equalsIgnoreCase(name) && commonList.get().getReferenceCode().equalsIgnoreCase(referenceCode.toString()) 
					&& commonList.get().getStatus().toString().equalsIgnoreCase(CommonStatus.ACTIVE.toString())) {
				return commonList.get();
			}else {
				throw new DetailValidateException(environment.getProperty(COMMON_INVALID_VALUE), serviceEntity, servicePoint);
			}
		}
		return null;
	}
	
	/**
	 * @author Sanatha
	 * */
	private CommonList comnListValidation(String id, String name, CommonListReferenceCodes referenceCode, ServiceEntity serviceEntity, ServicePoint servicePoint, Integer index) {
		LoggerRequest.getInstance().logInfo1("******<<<<<<#############>>>>>>******");
 		LoggerRequest.getInstance().logInfo1("******<<<<<<IN comnListValidation>>>>>>******");
		if(id !=null && !id.isEmpty()) {
			LoggerRequest.getInstance().logInfo1("******<<<<<<id>>>>>>******"+id);
			LoggerRequest.getInstance().logInfo1("******<<<<<<name>>>>>>******"+name);
			LoggerRequest.getInstance().logInfo1("******<<<<<<referenceCode>>>>>>******"+referenceCode.toString());
			
			Optional<CommonList> commonList=commonListRepository.findById(Long.parseLong(id));
			if(!commonList.isPresent()) {
				throw new DetailListValidateException(environment.getProperty(COMMON_INVALID_VALUE), serviceEntity, servicePoint,index);
			}
			
			LoggerRequest.getInstance().logInfo1("******<<<<<<commonList.get().getId()>>>>>>******"+commonList.get().getId());
			LoggerRequest.getInstance().logInfo1("******<<<<<<commonList.get().getName()>>>>>>******"+commonList.get().getName());
			LoggerRequest.getInstance().logInfo1("******<<<<<<commonList.get().getReferenceCode()>>>>>>******"+commonList.get().getReferenceCode());
			if(commonList.isPresent() && commonList.get().getName().equalsIgnoreCase(name) && commonList.get().getReferenceCode().equalsIgnoreCase(referenceCode.toString()) 
					&& commonList.get().getStatus().toString().equalsIgnoreCase(CommonStatus.ACTIVE.toString())) {
				return commonList.get();
			}else {
				throw new DetailListValidateException(environment.getProperty(COMMON_INVALID_VALUE), serviceEntity, servicePoint,index);
			}
		}
		return null;
	}

	/**
	 * @author Sanatha
	 * */
	private CommonList getComnListName(String id) {
		LoggerRequest.getInstance().logInfo1("******<<<<<<#############>>>>>>******");
 		LoggerRequest.getInstance().logInfo1("******<<<<<<IN getComnListName>>>>>>******");
 		
 		Optional<CommonList> commonList=commonListRepository.findById(Long.parseLong(id));
 		if(commonList.isPresent()) {
 			return commonList.get();
 		}else {
 			return null;
 		}
	}
	
	private Customer validateCustomer(Long customerId) {
		Optional<Customer> customer = customerRepository.findById(customerId);
		if(customer.isPresent() && customer.get().getStatus().equalsIgnoreCase(CommonStatus.ACTIVE.toString()))
			return customer.get();
		else
			throw new ValidateRecordException(environment.getProperty(CUSTOMER_ID), MESSAGE);
	}
	
	private BusinessType validateBusinessType(Long businessTypeId, String businessTypeName, Integer index) {
		
		Optional<BusinessType> businessType = businessTypeRepository.findById(businessTypeId);
		
		if(businessType.isPresent() && businessType.get().getName().equalsIgnoreCase(businessTypeName))
			return businessType.get();
		else
			throw new DetailListValidateException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.BUSINESS_TYPE_ID, ServicePoint.BUSINESS_INCOME_EXPENSES, index);
	}
	
	private ExpenseType validateBusinessIncomeExpense(Long businessTypeId, Long expenseTypeId, String expenseTypeName, Integer index, Integer index1) {
		
		Optional<BusinessExpenseType> businessExpenseType  = businessExpenseTypeRepository.findByBusinessTypeIdAndExpenseTypeId(businessTypeId, expenseTypeId);
		
		if(businessExpenseType.isPresent() && businessExpenseType.get().getExpenseType().getName().equalsIgnoreCase(expenseTypeName) && businessExpenseType.get().getStatus().equalsIgnoreCase(CommonStatus.ACTIVE.toString()))
			return businessExpenseType.get().getExpenseType();
		else
			throw new CustomerIncomeExpenseValidateException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.EXPENSE_TYPE_ID, ServicePoint.INCOME_EXPENSE_DETAILS, index, index1);

	}
	
	private IncomeType validateBusinessIncomeType(Long incomeTypeId, String incomeTypeName, Integer index, Integer index1) {
		
		Optional<IncomeType> incomeType  = incomeTypeRepository.findById(incomeTypeId);
		
		if(incomeType.isPresent() && incomeType.get().getName().equalsIgnoreCase(incomeTypeName) && incomeType.get().getStatus().equalsIgnoreCase(CommonStatus.ACTIVE.toString()))
			return incomeType.get();
		else
			throw new CustomerIncomeExpenseValidateException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.INCOME_TYPE_ID, ServicePoint.INCOME_EXPENSE_DETAILS, index, index1);

	}
	
	private CultivationCategory validateCultivationCategory(Long cultivationCatId, String cultivationCatName, Integer index) {
		
		Optional<CultivationCategory> cultivationCategory = cultivationCategoryRepository.findById(cultivationCatId);
		
		if(cultivationCategory.isPresent() && cultivationCategory.get().getName().equalsIgnoreCase(cultivationCatName))
			return cultivationCategory.get();
		else
			throw new DetailListValidateException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.CULTIVATION_CATEGORY_ID, ServicePoint.CUSTOMER_CUTIVATION_EXPENSE, index);
	}
	
	private ExpenseType validateExpenseTypeWithCultivation(Long cultiCategoryId, Long expenseTypeId, String expenseTypeName, Integer index, Integer index1) {
		
		Optional<ExpenceTypeCultivationCategory> isPresentExpenceTypeCultiCat = expenceTypeCultivationCategoryRepository.findByCultivationCategoryId(cultiCategoryId);
			
		if(isPresentExpenceTypeCultiCat.isPresent() &&  isPresentExpenceTypeCultiCat.get().getStatus().equals(CommonStatus.ACTIVE)) {
			
			Optional<ExpenceTypeCultivationCategoryDetails> expenceTypeCultiCategoryDet = expenceTypeCultiCatDetRepository.findByExpenceTypeCultivationCategoryIdAndExpenseTypeIdAndExpenseTypeName(isPresentExpenceTypeCultiCat.get().getId(), expenseTypeId, expenseTypeName);
			if(!expenceTypeCultiCategoryDet.isPresent())
				throw new CustomerIncomeExpenseValidateException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.EXPENSE_TYPE_ID, ServicePoint.CUSTOMER_CUTIVATION_EXPENSE, index, index1);
			return expenceTypeCultiCategoryDet.get().getExpenseType();
		}
		else
			throw new DetailListValidateException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.CULTIVATION_CATEGORY_ID, ServicePoint.CUSTOMER_CUTIVATION_EXPENSE, index);
	}
	
	@Override
	public IncomeExpenseSummaryResponseResource incomeExpenseSummary(Long customerId) {
		LoggerRequest.getInstance().logInfo1("******<<<<<<#############>>>>>>******");
 		LoggerRequest.getInstance().logInfo1("******<<<<<<IN incomeExpenseSummaryResponseResource>>>>>>******");
		
 		Double totalGrossIncome=0.00;
 		Double totalNetIncome=0.00;
 		Double percentage = 40.00; // this will parameterized in future. Till that requirement is get 40% of Net Income.
 		Double p1 =0.00;
 		Double calculatedPercentageAmount=0.00;
 		IncomeExpenseSummaryResponseResource incomeExpenseSummaryResponseResource = new IncomeExpenseSummaryResponseResource();
		
 		SalaryIncomeSummaryResponseResource salarySummary = salaryIncomeSummaryResponseResource( customerId);
 		OtherIncomeSummaryResponseResource otherIncomeSummary =otherIncomeSummaryResponseResource( customerId);
 		FinancialCommitmentSummaryResponseResource financialCommitment = financialCommitmentSummaryResponseResource( customerId);
 		HouseHoldExpenseSummaryResponseResource houseHoldExpense = houseHoldExpenseSummaryResponseResource( customerId);
 		CustomerBusinessIncomeSummaryResponseResource  customerBusinessIncome = customerBusinessIncomeSummaryResponseResource( customerId);
 		CustomerCultivationIncomeSummaryResponseResource  customerCultivationIncome = customerCultivationIncomeSummaryResponseResource( customerId);
 		
 		totalGrossIncome=  Double.parseDouble(salarySummary.getNetSalaryIncome()) +  Double.parseDouble(otherIncomeSummary.getNetIncome()) + customerBusinessNetIncome + customerCultivationNetIncome;
 		
 		totalNetIncome = totalGrossIncome -  Double.parseDouble(financialCommitment.getTotalFinancialCommitment()) - Double.parseDouble(houseHoldExpense.getTotalHouseHoldExpense());
 		p1= totalNetIncome*percentage;
 		calculatedPercentageAmount = p1/100;
 		
 		incomeExpenseSummaryResponseResource.setSalaryIncomeExpenseSummary(salarySummary);
 		incomeExpenseSummaryResponseResource.setOtherIncomeExpenseSummary(otherIncomeSummary);
 		incomeExpenseSummaryResponseResource.setFinancialCommitmentSummary(financialCommitment);
 		incomeExpenseSummaryResponseResource.setCustomerBusinessIncomeSummary(customerBusinessIncome);
 		incomeExpenseSummaryResponseResource.setHouseHoldExpenseSummary(houseHoldExpense);
 		incomeExpenseSummaryResponseResource.setCustomerCultivationIncomeSummary(customerCultivationIncome);
 		incomeExpenseSummaryResponseResource.setTotalGrossIncome(totalGrossIncome.toString());
 		incomeExpenseSummaryResponseResource.setTotalNetIncome(totalNetIncome.toString());
 		incomeExpenseSummaryResponseResource.setPercentage(percentage.toString());
 		incomeExpenseSummaryResponseResource.setCalculatedPercentageAmount(calculatedPercentageAmount.toString());
		
		return incomeExpenseSummaryResponseResource;
	}
	
	
	
	private List<SalaryIncome> salaryIncomeSummary(Long customerId){
		LoggerRequest.getInstance().logInfo1("******<<<<<<#############>>>>>>******");
 		LoggerRequest.getInstance().logInfo1("******<<<<<<IN salaryIncomeSummary>>>>>>******");
		
		List<SalaryIncome> salaryIncome = salaryIncomeRepository.findByCustomerIdAndStatus(customerId, CommonStatus.ACTIVE.toString());
		
		return salaryIncome;
	}
	
	


	private SalaryIncomeSummaryResponseResource salaryIncomeSummaryResponseResource(Long customerId) {
		LoggerRequest.getInstance().logInfo1("******<<<<<<#############>>>>>>******");
 		LoggerRequest.getInstance().logInfo1("******<<<<<<IN salaryIncomeSummaryResponseResource>>>>>>******");
		List<SalaryIncome> salaryIncomeSummaryList = null;
		SalaryIncomeSummaryResponseResource salaryIncomeSummaryResponseResource = new SalaryIncomeSummaryResponseResource();
		salaryIncomeSummaryList =salaryIncomeSummary(customerId);
		Double totSummary= 0.00;
		Double deductions=0.00;
		Double netSalaryIncome=0.00;
		
		int index=0;
		for(SalaryIncome salaryIncomeSummary : salaryIncomeSummaryList) {
		
			totSummary = totSummary + Double.parseDouble(salaryIncomeSummary.getSalaryIncome().toString());
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
	
	private List<OtherIncome> otherIncomeSummary(Long customerId){
		LoggerRequest.getInstance().logInfo1("******<<<<<<#############>>>>>>******");
 		LoggerRequest.getInstance().logInfo1("******<<<<<<IN otherIncomeSummary>>>>>>******");
		
		List<OtherIncome> otherIncome = otherIncomeRepository.findByCustomerIdAndStatus(customerId, CommonStatus.ACTIVE.toString());
		
		return otherIncome;
	}
	
	private OtherIncomeSummaryResponseResource otherIncomeSummaryResponseResource(Long customerId) {
		LoggerRequest.getInstance().logInfo1("******<<<<<<#############>>>>>>******");
 		LoggerRequest.getInstance().logInfo1("******<<<<<<IN otherIncomeSummaryResponseResource>>>>>>******");
		List<OtherIncome> otherIncomeSummaryList = null;
		OtherIncomeSummaryResponseResource otherIncomeSummaryResponseResource = new OtherIncomeSummaryResponseResource();
		otherIncomeSummaryList =otherIncomeSummary(customerId);
		Double totSummary= 0.00;
		Double deductions=0.00;
		Double netIncome=0.00;
		
		int index=0;
		for(OtherIncome otherIncomeSummary : otherIncomeSummaryList) {
			
			totSummary = totSummary + Double.parseDouble(otherIncomeSummary.getTotIncomePerFreq().toString());
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
	
	private List<HouseHoldExpense> houseHoldExpenseSummary(Long customerId){
		LoggerRequest.getInstance().logInfo1("******<<<<<<#############>>>>>>******");
 		LoggerRequest.getInstance().logInfo1("******<<<<<<IN houseHoldExpenseSummary>>>>>>******");
		
		List<HouseHoldExpense> houseHoldExpense = houseHoldExpenseRepository.findByCustomerIdAndStatus(customerId, CommonStatus.ACTIVE.toString());
		
		return houseHoldExpense;
	}
	
	private HouseHoldExpenseSummaryResponseResource houseHoldExpenseSummaryResponseResource(Long customerId) {
		LoggerRequest.getInstance().logInfo1("******<<<<<<#############>>>>>>******");
 		LoggerRequest.getInstance().logInfo1("******<<<<<<IN houseHoldExpenseSummaryResponseResource>>>>>>******");
		List<HouseHoldExpense> houseHoldExpenseSummaryList = null;
		HouseHoldExpenseSummaryResponseResource houseHoldExpenseSummaryResponseResource = new HouseHoldExpenseSummaryResponseResource();
		houseHoldExpenseSummaryList =houseHoldExpenseSummary(customerId);
		Double totExpense= 0.00;
		
		int index=0;
		for(HouseHoldExpense houseHoldExpenseSummary : houseHoldExpenseSummaryList) {
			
			totExpense = totExpense + Double.parseDouble(houseHoldExpenseSummary.getFinalCost().toString());
			index++;
			
		}
		
		houseHoldExpenseSummaryResponseResource.setTotalHouseHoldExpense(totExpense.toString());
		houseHoldExpenseSummaryResponseResource.setHouseHoldExpenseDetails(houseHoldExpenseSummaryList);
		
		return houseHoldExpenseSummaryResponseResource;
	}
	
	private List<FinancialCommitment> financialCommitmentSummary(Long customerId){
		LoggerRequest.getInstance().logInfo1("******<<<<<<#############>>>>>>******");
 		LoggerRequest.getInstance().logInfo1("******<<<<<<IN financialCommitmentSummary>>>>>>******");
		
		List<FinancialCommitment> financialCommitment = financialCommitmentRepository.findByCustomerIdAndStatus(customerId, CommonStatus.ACTIVE.toString());
		for (FinancialCommitment financialCommitmentNames : financialCommitment) {
			
			CommonList commonListCommitment = getComnListName( financialCommitmentNames.getTypeOfCommitment().getId().toString());
			financialCommitmentNames.setTypeOfCommitmentName(commonListCommitment.getName());
			financialCommitmentNames.setTypeOfCommitmentCode(commonListCommitment.getCode());
			CommonList commonListFacility = getComnListName( financialCommitmentNames.getTypeOfFacility().getId().toString());
			financialCommitmentNames.setTypeOfFacilityName(commonListFacility.getName());
			financialCommitmentNames.setTypeOfFacilityCode(commonListFacility.getCode());
		}
		
		return financialCommitment;
	}
	
	private FinancialCommitmentSummaryResponseResource financialCommitmentSummaryResponseResource(Long customerId) {
		LoggerRequest.getInstance().logInfo1("******<<<<<<#############>>>>>>******");
 		LoggerRequest.getInstance().logInfo1("******<<<<<<IN financialCommitmentSummaryResponseResource>>>>>>******");
		List<FinancialCommitment> financialCommitmentSummaryList = null;
		FinancialCommitmentSummaryResponseResource financialCommitmentSummaryResponseResource = new FinancialCommitmentSummaryResponseResource();
		financialCommitmentSummaryList =financialCommitmentSummary(customerId);
		Double totalFinancialCommitment= 0.00;
		
		
		int index=0;
		for(FinancialCommitment financialCommitmentSummary : financialCommitmentSummaryList) {
			
			totalFinancialCommitment = totalFinancialCommitment + Double.parseDouble(financialCommitmentSummary.getToalOutstanding().toString());
			index++;
		
		}
		
		financialCommitmentSummaryResponseResource.setTotalFinancialCommitment(totalFinancialCommitment.toString());
		financialCommitmentSummaryResponseResource.setFinancialCommitmentDetails(financialCommitmentSummaryList);
		
		return financialCommitmentSummaryResponseResource;
	}
	
	//IncomeExpenseDetailsRepository
	private List<IncomeExpenseDetails> incomeExpenseDetailsSummary(Long businessIncomeExpenseId){
		LoggerRequest.getInstance().logInfo1("******<<<<<<#############>>>>>>******");
 		LoggerRequest.getInstance().logInfo1("******<<<<<<IN incomeExpenseDetailsSummary>>>>>>******"+businessIncomeExpenseId);
		
		List<IncomeExpenseDetails> incomeExpenseDetails = incomeExpenseDetailsRepository.findByBusinessIncomeExpenseIdAndStatus(businessIncomeExpenseId, CommonStatus.ACTIVE);
		
		return incomeExpenseDetails;
	}
	
	private IncomeExpenseDetailsSummaryResponseResource incomeExpenseDetailsSummaryResponseResource(Long businessIncomeExpenseId) {
		LoggerRequest.getInstance().logInfo1("******<<<<<<#############>>>>>>******");
 		LoggerRequest.getInstance().logInfo1("******<<<<<<IN incomeExpenseDetailsSummaryResponseResource>>>>>>******"+businessIncomeExpenseId);
		List<IncomeExpenseDetails> incomeExpenseDetailsSummaryList = null;
		IncomeExpenseDetailsSummaryResponseResource incomeExpenseDetailsSummaryResponseResource = new IncomeExpenseDetailsSummaryResponseResource();
		incomeExpenseDetailsSummaryList =incomeExpenseDetailsSummary(businessIncomeExpenseId);
		Double totalGrossIncome= 0.00;
		Double totalExpences=0.00;
		Double netIncome=0.00;
		
		
		int index=0;
		for(IncomeExpenseDetails incomeExpenseDetailsSummary : incomeExpenseDetailsSummaryList) {
			
			totalGrossIncome = totalGrossIncome + Double.parseDouble(incomeExpenseDetailsSummary.getGrossIncome().toString());
			totalExpences = totalExpences + Double.parseDouble(incomeExpenseDetailsSummary.getAmount().toString());
			index++;
		
		}
		
		netIncome = totalGrossIncome-totalGrossIncome;
		customerBusinessGrossIncome = customerBusinessGrossIncome + totalGrossIncome;
		customerBusinessExpense = customerBusinessExpense + totalExpences;
		
		
		incomeExpenseDetailsSummaryResponseResource.setGrossIncome(totalGrossIncome.toString());
		incomeExpenseDetailsSummaryResponseResource.setExpences(totalExpences.toString());
		
		incomeExpenseDetailsSummaryResponseResource.setIncomeExpenseDetails(incomeExpenseDetailsSummaryList);
		
		return incomeExpenseDetailsSummaryResponseResource;
	}
	
	
	
	//IncomeExpenseTaxRepository
	private List<IncomeExpenseTax> incomeExpenseTaxDetailsSummary(Long businessIncomeExpenseId){
		LoggerRequest.getInstance().logInfo1("******<<<<<<#############>>>>>>******");
 		LoggerRequest.getInstance().logInfo1("******<<<<<<IN incomeExpenseTaxDetailsSummary>>>>>>******"+businessIncomeExpenseId);
		
		List<IncomeExpenseTax> incomeExpenseTax = incomeExpenseTaxRepository.findByBusinessIncomeExpenseIdAndStatus(businessIncomeExpenseId, CommonStatus.ACTIVE);
		
		return incomeExpenseTax;
	}
	
	
	private IncomeExpenseTaxSummaryResponseResource incomeExpenseTaxSummaryResponseResource(Long businessIncomeExpenseId) {
		LoggerRequest.getInstance().logInfo1("******<<<<<<#############>>>>>>******");
 		LoggerRequest.getInstance().logInfo1("******<<<<<<IN incomeExpenseTaxSummaryResponseResource>>>>>>******"+businessIncomeExpenseId);
		List<IncomeExpenseTax> incomeExpenseTaxSummaryList = null;
		IncomeExpenseTaxSummaryResponseResource incomeExpenseTaxSummaryResponseResource = new IncomeExpenseTaxSummaryResponseResource();
		incomeExpenseTaxSummaryList =incomeExpenseTaxDetailsSummary(businessIncomeExpenseId);
		Double totalTaxAmount= 0.00;
	
		
		int index=0;
		for(IncomeExpenseTax incomeExpenseTaxSummary : incomeExpenseTaxSummaryList) {
			
			totalTaxAmount = totalTaxAmount + Double.parseDouble(incomeExpenseTaxSummary.getAmount().toString());
			index++;
		
		}
		
		customerBusinessTaxAmount = customerBusinessTaxAmount + totalTaxAmount;
		incomeExpenseTaxSummaryResponseResource.setTaxAmount(totalTaxAmount.toString());
		incomeExpenseTaxSummaryResponseResource.setIncomeExpenseTax(incomeExpenseTaxSummaryList);
		
		return incomeExpenseTaxSummaryResponseResource;
	}
	
	//BusinessIncomeExpenseRepository
	private List<BusinessIncomeExpense> businessIncomeExpenseDetailsSummary(Long customerId){
		LoggerRequest.getInstance().logInfo1("******<<<<<<#############>>>>>>******");
 		LoggerRequest.getInstance().logInfo1("******<<<<<<IN businessIncomeExpenseDetailsSummary>>>>>>******"+customerId);
		
		List<BusinessIncomeExpense> businessIncomeExpense = businessIncomeExpenseRepository.findByCustomerIdAndStatus(customerId, CommonStatus.ACTIVE);
		
		return businessIncomeExpense;
	}
	
	private CustomerBusinessIncomeExpenseTaxResponseResource customerBusinessIncomeExpenseTaxResponseResource(Long businessIncomeExpenseId) {
		LoggerRequest.getInstance().logInfo1("******<<<<<<#############>>>>>>******");
 		LoggerRequest.getInstance().logInfo1("******<<<<<<IN customerBusinessIncomeExpenseTaxResponseResource>>>>>>******"+businessIncomeExpenseId);
		
		
		CustomerBusinessIncomeExpenseTaxResponseResource customerBusinessIncomeExpenseTaxResponseResource = new CustomerBusinessIncomeExpenseTaxResponseResource();
		
		IncomeExpenseDetailsSummaryResponseResource incomeExpenseDetails = incomeExpenseDetailsSummaryResponseResource( businessIncomeExpenseId);
		IncomeExpenseTaxSummaryResponseResource incomeExpenseTax = incomeExpenseTaxSummaryResponseResource( businessIncomeExpenseId);
		
		customerBusinessIncomeExpenseTaxResponseResource.setIncomeExpenseDetails(incomeExpenseDetails);
		customerBusinessIncomeExpenseTaxResponseResource.setTaxDetails(incomeExpenseTax);
		
		return customerBusinessIncomeExpenseTaxResponseResource;
	}
	
	private CustomerBusinessIncomeSummaryResponseResource customerBusinessIncomeSummaryResponseResource(Long customerId) {
		LoggerRequest.getInstance().logInfo1("******<<<<<<#############>>>>>>******");
 		LoggerRequest.getInstance().logInfo1("******<<<<<<IN customerBusinessIncomeSummaryResponseResource>>>>>>******"+customerId);
		List<BusinessIncomeExpense> businessIncomeExpenseSummaryList = null;
		List<CustomerBusinessIncomeExpenseTaxResponseResource> customerBusinessIncomeExpenseTaxList = new ArrayList<>();
		CustomerBusinessIncomeSummaryResponseResource customerBusinessIncomeSummaryResponseResource = new CustomerBusinessIncomeSummaryResponseResource();
		
		 customerBusinessGrossIncome=0.00;
		 customerBusinessExpense=0.00;
		 customerBusinessNetIncome=0.00;
		 customerBusinessTaxAmount=0.00;
		 customerBusinessProfitAfterTax=0.00;
		
		businessIncomeExpenseSummaryList = businessIncomeExpenseDetailsSummary(customerId);
		Double totalTaxAmount= 0.00;
	
		
		int index=0;
		for(BusinessIncomeExpense businessIncomeExpenseSummary : businessIncomeExpenseSummaryList) {
			
			CustomerBusinessIncomeExpenseTaxResponseResource customerBusinessIncomeExpenseTax = new CustomerBusinessIncomeExpenseTaxResponseResource();
			
			customerBusinessIncomeExpenseTax = customerBusinessIncomeExpenseTaxResponseResource( businessIncomeExpenseSummary.getId());
			
			customerBusinessIncomeExpenseTaxList.add(customerBusinessIncomeExpenseTax);
			
			
			index++;
		
		}
		
		customerBusinessNetIncome= customerBusinessGrossIncome - customerBusinessExpense;
		customerBusinessProfitAfterTax = customerBusinessNetIncome - customerBusinessTaxAmount;
		
		customerBusinessIncomeSummaryResponseResource.setCustomerBusinessGrossIncome(customerBusinessGrossIncome.toString());
		customerBusinessIncomeSummaryResponseResource.setCustomerBusinessExpense(customerBusinessExpense.toString());
		customerBusinessIncomeSummaryResponseResource.setCustomerBusinessNetIncome(customerBusinessNetIncome.toString());
		customerBusinessIncomeSummaryResponseResource.setCustomerBusinessTaxAmount(customerBusinessTaxAmount.toString());
		customerBusinessIncomeSummaryResponseResource.setCustomerBusinessProfitAfterTax(customerBusinessProfitAfterTax.toString());
		customerBusinessIncomeSummaryResponseResource.setBusinessIncomeExpenseTaxDetails(customerBusinessIncomeExpenseTaxList);
		customerBusinessIncomeSummaryResponseResource.setBusinessIncomeExpenses(businessIncomeExpenseSummaryList);
		//customerBusinessIncomeSummaryResponseResource.setCustomerBusinessIncomeExpenseTax(customerBusinessIncomeExpenseTaxList);
		
		return customerBusinessIncomeSummaryResponseResource;
	}
	
	//CustomerCultivationIncomeExpenseRepository
	private List<CustomerCultivationIncome> cultivationncomeExpenseDetailsSummary(Long customerId){
		LoggerRequest.getInstance().logInfo1("******<<<<<<#############>>>>>>******");
 		LoggerRequest.getInstance().logInfo1("******<<<<<<IN cultivationncomeExpenseDetailsSummary>>>>>>******"+customerId);
		
		List<CustomerCultivationIncome> customerCultivationIncomeExpense = customerCultivationIncomeRepository.findByCustomerIdAndStatus(customerId, CommonStatus.ACTIVE);
		
		return customerCultivationIncomeExpense;
	}
	
	//IncomeExpenseTaxRepository
	private List<CustomerCultivationExpense> customerCultivationExpenseDetailsSummary(Long customerCultivationIncomeId){
		LoggerRequest.getInstance().logInfo1("******<<<<<<#############>>>>>>******");
 		LoggerRequest.getInstance().logInfo1("******<<<<<<IN customerCultivationExpenseDetailsSummary>>>>>>******"+customerCultivationIncomeId);
		
		List<CustomerCultivationExpense> customerCultivationExpense = customerCultivationExpenseRepository.findByCustomerCultivationIncomeIdAndStatus( customerCultivationIncomeId, CommonStatus.ACTIVE );
		
		return customerCultivationExpense;
	}
	
	private CustomerCultivationExpenseSummaryResponseResource customerCultivationExpenseSummaryResponseResource(Long customerCultivationIncomeId) {
		LoggerRequest.getInstance().logInfo1("******<<<<<<#############>>>>>>******");
 		LoggerRequest.getInstance().logInfo1("******<<<<<<IN customerCultivationExpenseSummaryResponseResource>>>>>>******"+customerCultivationIncomeId);
		List<CustomerCultivationExpense> customerCultivationExpenseSummaryList = null;
		CustomerCultivationExpenseSummaryResponseResource customerCultivationExpenseSummaryResponseResource = new CustomerCultivationExpenseSummaryResponseResource();
		customerCultivationExpenseSummaryList =customerCultivationExpenseDetailsSummary(customerCultivationIncomeId);
		Double totalFinalCost= 0.00;
	
		
		int index=0;
		for(CustomerCultivationExpense customerCultivationExpenseSummary : customerCultivationExpenseSummaryList) {
			
			totalFinalCost = totalFinalCost + Double.parseDouble(customerCultivationExpenseSummary.getFinalCost().toString());
			index++;
		
		}
		
		customerCultivationExpenseAmount = customerCultivationExpenseAmount + totalFinalCost;
		customerCultivationExpenseSummaryResponseResource.setFinalCost(totalFinalCost.toString());
		customerCultivationExpenseSummaryResponseResource.setCustomerCultivationExpense(customerCultivationExpenseSummaryList);
		
		return customerCultivationExpenseSummaryResponseResource;
	}
	
	private CustomerCultivationIncomeSummaryResponseResource customerCultivationIncomeSummaryResponseResource(Long customerId) {
		LoggerRequest.getInstance().logInfo1("******<<<<<<#############>>>>>>******");
 		LoggerRequest.getInstance().logInfo1("******<<<<<<IN customerCultivationIncomeSummaryResponseResource>>>>>>******"+customerId);
		List<CustomerCultivationIncome> customerCultivationIncomeSummaryList = null;
		List<CustomerCultivationExpenseSummaryResponseResource> customerCultivationExpenseList = new ArrayList<>();
		CustomerCultivationIncomeSummaryResponseResource customerCultivationIncomeSummaryResponseResource = new CustomerCultivationIncomeSummaryResponseResource();
		
		 customerCultivationGrossIncome=0.00;
		 customerCultivationExpense=0.00;
		 customerCultivationNetIncome=0.00;
		 customerCultivationExpenseAmount=0.00;
		
		
		 customerCultivationIncomeSummaryList = cultivationncomeExpenseDetailsSummary(customerId);
		 Double totalTaxAmount= 0.00;
	
		
		int index=0;
		for(CustomerCultivationIncome customerCultivationIncomeSummary : customerCultivationIncomeSummaryList) {
			
			customerCultivationGrossIncome = customerCultivationGrossIncome + Double.parseDouble(customerCultivationIncomeSummary.getTotalGrossIncome().toString());
			CustomerCultivationExpenseSummaryResponseResource customerCultivationExpense = new CustomerCultivationExpenseSummaryResponseResource();
			
			customerCultivationExpense = customerCultivationExpenseSummaryResponseResource( customerCultivationIncomeSummary.getId());
			
			customerCultivationExpenseList.add(customerCultivationExpense);
			
			
			index++;
		
		}
		
		customerCultivationExpense= customerCultivationExpenseAmount;
		customerCultivationNetIncome= customerCultivationGrossIncome - customerCultivationExpense;
		
		
		customerCultivationIncomeSummaryResponseResource.setCustomerCultivationGrossIncome(customerCultivationGrossIncome.toString());
		customerCultivationIncomeSummaryResponseResource.setCustomerCultivationExpense(customerCultivationExpense.toString());
		customerCultivationIncomeSummaryResponseResource.setCustomerCultivationNetIncome(customerCultivationNetIncome.toString());
		
		customerCultivationIncomeSummaryResponseResource.setCultivationIncome(customerCultivationIncomeSummaryList);
		customerCultivationIncomeSummaryResponseResource.setCultivationExpenseDetails(customerCultivationExpenseList);
		//customerBusinessIncomeSummaryResponseResource.setCustomerBusinessIncomeExpenseTax(customerBusinessIncomeExpenseTaxList);
		
		return customerCultivationIncomeSummaryResponseResource;
	}



}
