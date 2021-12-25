package com.fusionx.lending.origination.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.origination.base.MessagePropertyBase;
import com.fusionx.lending.origination.domain.ExpenseType;
import com.fusionx.lending.origination.domain.SalaryExpenseType;
import com.fusionx.lending.origination.domain.SalaryIncomeDetails;
import com.fusionx.lending.origination.domain.SalaryIncomeExpenses;
import com.fusionx.lending.origination.domain.SalaryIncomeType;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.repository.ExpenseTypeRepository;
import com.fusionx.lending.origination.repository.SalaryExpenseTypeRepository;
import com.fusionx.lending.origination.repository.SalaryIncomeDetailsRepository;
import com.fusionx.lending.origination.repository.SalaryIncomeExpensesRepository;
import com.fusionx.lending.origination.repository.SalaryIncomeTypeRepository;
import com.fusionx.lending.origination.resource.FrequencyResponse;
import com.fusionx.lending.origination.resource.SalaryIncomeExpenseDetailResponse;
import com.fusionx.lending.origination.resource.SalaryIncomeExpensesAddMainResource;
import com.fusionx.lending.origination.resource.SalaryIncomeExpensesAddResource;
import com.fusionx.lending.origination.resource.SalaryIncomeExpensesUpdateMainResource;
import com.fusionx.lending.origination.resource.SalaryIncomeExpensesUpdateResource;
import com.fusionx.lending.origination.service.SalaryIncomeExpensesService;
import com.fusionx.lending.origination.service.ValidateService;

/**
 * API Service related to salary income expense
 *
 * @author Nipun Dilhan
 * @version 1.0.0
 * @since 1.0.0
 * <p>
 * <br/><br/>
 * <b>Change History : </b>
 * <br/><br/>
 * #        Date            Story Point     Task No     Author                  Description
 * <br/>
 * .....................................................................................................................<br/>
 * 1        12-09-2021      -               FXL-784     Nipun Dilhan      Created
 * <p>
 *
 */

@Component
@Transactional(rollbackFor = Exception.class)
public class SalaryIncomeExpensesServiceImpl  extends MessagePropertyBase implements SalaryIncomeExpensesService{
	
	@Autowired
	private SalaryIncomeExpensesRepository  salaryIncomeExpensesRepository;
	
	@Autowired
	private SalaryIncomeTypeRepository  salaryIncomeTypeRepository;
	
	@Autowired
	private SalaryIncomeDetailsRepository  salaryIncomeDetailsRepository;

	@Autowired
	private ExpenseTypeRepository  expenseTypeRepository;
	
	private ValidateService validateService;
	
	@Autowired
	public void setValidateService(ValidateService validateService) {
		this.validateService = validateService;
	}
	
	
	public void saveIncomeExpenseList(String tenantId,String user,SalaryIncomeExpensesAddMainResource salaryIncomeExpensesAddMainResource) {
		
		List<SalaryIncomeExpensesAddResource> incomeExpenceList = salaryIncomeExpensesAddMainResource.getIncomeExpenceList();
		if((incomeExpenceList != null)&&(!incomeExpenceList.isEmpty())) {
			for(SalaryIncomeExpensesAddResource request : incomeExpenceList) {
				saveIncomeExpense(tenantId,user,validateService.stringToLong(salaryIncomeExpensesAddMainResource.getSalaryIncomeDetailId()),request);
			}
		}
		
		
	}
	
	
	public void updateIncomeExpenseList(String tenantId,String user,SalaryIncomeExpensesUpdateMainResource salaryIncomeExpensesUpdateMainResource) {
		
		List<SalaryIncomeExpensesUpdateResource> incomeExpenceList = salaryIncomeExpensesUpdateMainResource.getIncomeExpenceList();
		if((incomeExpenceList != null)&&(!incomeExpenceList.isEmpty())) {
			for(SalaryIncomeExpensesUpdateResource request : incomeExpenceList) {
				updateIncomeExpense(tenantId,user,validateService.stringToLong(salaryIncomeExpensesUpdateMainResource.getSalaryIncomeDetailId()),request);
			}
		}
		
		
	}
	
	@Override
	public SalaryIncomeExpenses findById(Long id) {
		Optional<SalaryIncomeExpenses> salaryIncomeExpensesOptional = salaryIncomeExpensesRepository.findById(id);
		if(!salaryIncomeExpensesOptional.isPresent()) {
			return null;
		}
		
		return salaryIncomeExpensesOptional.get();
		
	}
	
	@Override
	public SalaryIncomeExpenses saveIncomeExpense(String tenantId,String user,Long salaryIncomeDetailId, SalaryIncomeExpensesAddResource salaryIncomeExpensesAddResource) {
		
		Optional<SalaryIncomeDetails> salaryIncomeDetailsOptional = salaryIncomeDetailsRepository.findById(salaryIncomeDetailId);
		if(!salaryIncomeDetailsOptional.isPresent()) {
			throw new ValidateRecordException("invalid salary Income id", "message");
		}
		
		
		if((salaryIncomeExpensesAddResource.getSalaryExpenseTypeId()==null) && (salaryIncomeExpensesAddResource.getSalaryIncomeTypeId()==null)){
			throw new ValidateRecordException("both income and expence type id's cannot be null", "message");
		}
		if((salaryIncomeExpensesAddResource.getSalaryExpenseTypeId()!=null) && (salaryIncomeExpensesAddResource.getSalaryIncomeTypeId()!=null)){
			throw new ValidateRecordException("both income and expence type id's cannot have values", "message");
		}
		
		ExpenseType expenseType = null;
		SalaryIncomeType salaryIncomeType = null;
		if(salaryIncomeExpensesAddResource.getSalaryIncomeTypeId() != null) {
			Optional<SalaryIncomeType> salaryIncomeTypeOptional = salaryIncomeTypeRepository.findById(validateService.stringToLong(salaryIncomeExpensesAddResource.getSalaryIncomeTypeId()));
			salaryIncomeType = salaryIncomeTypeOptional.get();
		
		}else {
			Optional<ExpenseType> expenseTypeOptional = expenseTypeRepository.findById(validateService.stringToLong(salaryIncomeExpensesAddResource.getSalaryExpenseTypeId()));
			expenseType = expenseTypeOptional.get();
		}
		
		
		FrequencyResponse frequencyResponse = validateService.validateFrequency(tenantId,salaryIncomeExpensesAddResource.getFreequencyId());	
		
		SalaryIncomeExpenses salaryIncomeExpenses = new SalaryIncomeExpenses();
		salaryIncomeExpenses.setTenantId(tenantId);
		salaryIncomeExpenses.setSalaryIncomeDetails(salaryIncomeDetailsOptional.get());
		salaryIncomeExpenses.setExpenseType(expenseType);
		salaryIncomeExpenses.setSalaryIncomeType(salaryIncomeType);		
		salaryIncomeExpenses.setFrequencyId(validateService.stringToLong(frequencyResponse.getId()));
		salaryIncomeExpenses.setFrequencyCode(frequencyResponse.getCode());
		salaryIncomeExpenses.setFrequencyName(frequencyResponse.getName());
		salaryIncomeExpenses.setDescription(salaryIncomeExpensesAddResource.getDescription());
		salaryIncomeExpenses.setAmount(validateService.stringToBigDecimal(salaryIncomeExpensesAddResource.getAmount()));
		salaryIncomeExpenses.setStatus(CommonStatus.ACTIVE);
		salaryIncomeExpenses.setCurrencyId(null);
		salaryIncomeExpenses.setCurrencyCode(null);
		salaryIncomeExpenses.setCurrencyCodeNumeric(null);		
		salaryIncomeExpenses.setCreatedDate(validateService.getCreateOrModifyDate());
		salaryIncomeExpenses.setCreatedUser(user);
		salaryIncomeExpenses.setModifiedDate(validateService.getCreateOrModifyDate());
		salaryIncomeExpenses.setModifiedUser(user);
		salaryIncomeExpenses.setSyncTs(validateService.getCreateOrModifyDate());
		
		return salaryIncomeExpensesRepository.save(salaryIncomeExpenses);
	}
	
	
	@Override
	public SalaryIncomeExpenses updateIncomeExpense(String tenantId,String user,Long salaryIncomeDetailId, SalaryIncomeExpensesUpdateResource salaryIncomeExpensesUpdateResource) {
		
		Optional<SalaryIncomeExpenses> salaryIncomeExpensesOptional = salaryIncomeExpensesRepository.findById(validateService.stringToLong(salaryIncomeExpensesUpdateResource.getId()));
		if(!salaryIncomeExpensesOptional.isPresent()) {
			throw new ValidateRecordException("invalid salary Income expense id", "message");
		}
		
		SalaryIncomeExpenses salaryIncomeExpenses = salaryIncomeExpensesOptional.get();
		
		if(!(salaryIncomeDetailId).equals(salaryIncomeExpenses.getSalaryIncomeDetails().getId())) {
			throw new ValidateRecordException("incompatible salary Income detail id", "message");
		}
		
		
		if((salaryIncomeExpensesUpdateResource.getSalaryExpenseTypeId()==null) && (salaryIncomeExpensesUpdateResource.getSalaryIncomeTypeId()==null)){
			throw new ValidateRecordException("both income and expence type id's cannot be null", "message");
		}
		if((salaryIncomeExpensesUpdateResource.getSalaryExpenseTypeId()!=null) && (salaryIncomeExpensesUpdateResource.getSalaryIncomeTypeId()!=null)){
			throw new ValidateRecordException("both income and expence type id's cannot have values", "message");
		}
		if((salaryIncomeExpensesUpdateResource.getSalaryExpenseTypeId()!=null) && (salaryIncomeExpenses.getExpenseType() ==null)){
			throw new ValidateRecordException("cannot change income to expense", "message");
		}
		if((salaryIncomeExpensesUpdateResource.getSalaryIncomeTypeId()!=null) && (salaryIncomeExpenses.getSalaryIncomeType() ==null)){
			throw new ValidateRecordException("cannot change expense to income", "message");
		}
		
		ExpenseType expenseType = null;
		SalaryIncomeType salaryIncomeType = null;
		if(salaryIncomeExpensesUpdateResource.getSalaryIncomeTypeId() != null) {
			Optional<SalaryIncomeType> salaryIncomeTypeOptional = salaryIncomeTypeRepository.findById(validateService.stringToLong(salaryIncomeExpensesUpdateResource.getSalaryIncomeTypeId()));
			salaryIncomeType = salaryIncomeTypeOptional.get();
		
		}else {
			Optional<ExpenseType> expenseTypeOptional = expenseTypeRepository.findById(validateService.stringToLong(salaryIncomeExpensesUpdateResource.getSalaryExpenseTypeId()));
			expenseType = expenseTypeOptional.get();
		}
		
		
		FrequencyResponse frequencyResponse = validateService.validateFrequency(tenantId,salaryIncomeExpensesUpdateResource.getFreequencyId());	

		salaryIncomeExpenses.setExpenseType(expenseType);
		salaryIncomeExpenses.setSalaryIncomeType(salaryIncomeType);		
		salaryIncomeExpenses.setFrequencyId(validateService.stringToLong(frequencyResponse.getId()));
		salaryIncomeExpenses.setFrequencyCode(frequencyResponse.getCode());
		salaryIncomeExpenses.setFrequencyName(frequencyResponse.getName());
		salaryIncomeExpenses.setDescription(salaryIncomeExpensesUpdateResource.getDescription());
		salaryIncomeExpenses.setAmount(validateService.stringToBigDecimal(salaryIncomeExpensesUpdateResource.getAmount()));
		salaryIncomeExpenses.setStatus(CommonStatus.ACTIVE);
		salaryIncomeExpenses.setCurrencyId(null);
		salaryIncomeExpenses.setCurrencyCode(null);
		salaryIncomeExpenses.setCurrencyCodeNumeric(null);	
		salaryIncomeExpenses.setModifiedDate(validateService.getCreateOrModifyDate());
		salaryIncomeExpenses.setModifiedUser(user);
		salaryIncomeExpenses.setSyncTs(validateService.getCreateOrModifyDate());
		
		return salaryIncomeExpensesRepository.save(salaryIncomeExpenses);
	}
	
	
	@Override
	public SalaryIncomeExpenseDetailResponse getSalaryIncomeExpenseDetails(Long id) {
		
		Optional<SalaryIncomeDetails> salaryIncomeDetailsOptional = salaryIncomeDetailsRepository.findById(id);
		if(!salaryIncomeDetailsOptional.isPresent()) {
			return null;
		}
		
		List<SalaryIncomeExpenses> incomeList  = salaryIncomeExpensesRepository.findAllBySalaryIncomeDetailsAndExpenseType(salaryIncomeDetailsOptional.get(), null);
		List<SalaryIncomeExpenses> expenseList = salaryIncomeExpensesRepository.findAllBySalaryIncomeDetailsAndSalaryIncomeType(salaryIncomeDetailsOptional.get(), null);
		
		SalaryIncomeExpenseDetailResponse response = new SalaryIncomeExpenseDetailResponse();
		response.setExpenseList(expenseList);
		response.setIncomeList(incomeList);
		
		return response;
	}

}
