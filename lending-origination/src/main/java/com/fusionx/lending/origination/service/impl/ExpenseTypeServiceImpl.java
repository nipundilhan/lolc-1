package com.fusionx.lending.origination.service.impl;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.origination.core.LoggerRequest;
import com.fusionx.lending.origination.domain.ExpenseType;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.repository.ExpenseTypeRepository;
import com.fusionx.lending.origination.resource.ExpenseTypeAddResource;
import com.fusionx.lending.origination.resource.ExpenseTypeUpdateResource;
import com.fusionx.lending.origination.service.ExpenseTypeService;


/**
 * Expense Type Service Implementation
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   24-12-2020      		     FX-5270	MiyuruW      Created
 *    
 ********************************************************************************************************
 */

@Component
@Transactional(rollbackFor = Exception.class)
public class ExpenseTypeServiceImpl implements ExpenseTypeService {

	@Autowired
	private Environment environment;
	
	@Autowired
	private ExpenseTypeRepository expenseTypeRepository;
	
	@Override
	public List<ExpenseType> findAll() {
		return expenseTypeRepository.findAll();
	}

	@Override
	public Optional<ExpenseType> findById(Long id) {
		Optional<ExpenseType> expenseType = expenseTypeRepository.findById(id);
		if (expenseType.isPresent()) {
			return Optional.ofNullable(expenseType.get());
		} else {
			return Optional.empty();
		}
	}

	@Override
	public List<ExpenseType> findByStatus(String status) {
		return expenseTypeRepository.findByStatus(status);
	}

	@Override
	public List<ExpenseType> findByName(String name) {
		return expenseTypeRepository.findByNameContaining(name);
	}

	@Override
	public Optional<ExpenseType> findByCode(String code) {
		Optional<ExpenseType> expenseType = expenseTypeRepository.findByCode(code);
		if (expenseType.isPresent()) {
			return Optional.ofNullable(expenseType.get());
		} else {
			return Optional.empty();
		}
	}

	@Override
	public Boolean existsById(Long id) {
		return expenseTypeRepository.existsById(id);
	}

	@Override
	public Long saveAndValidateExpenseType(String tenantId, String createdUser, ExpenseTypeAddResource expenseTypeAddResource) {
		
		LoggerRequest.getInstance().logInfo("ExpenseType********************************Validate Code*********************************************");
		if(expenseTypeRepository.existsByCodeAndStatus(expenseTypeAddResource.getCode(), CommonStatus.ACTIVE.toString()))
			throw new ValidateRecordException(environment.getProperty("common.unique"), "code");
		
		LoggerRequest.getInstance().logInfo("ExpenseType********************************Save Expense Type*********************************************");
		ExpenseType expenseType=saveExpenseType(tenantId, createdUser, expenseTypeAddResource);
		
		return expenseType.getId();
	}

	private ExpenseType saveExpenseType(String tenantId, String createdUser, ExpenseTypeAddResource expenseTypeAddResource) {
		ExpenseType expenseType = new ExpenseType();
		expenseType.setTenantId(tenantId);
		expenseType.setCode(expenseTypeAddResource.getCode());
		expenseType.setName(expenseTypeAddResource.getName());
		expenseType.setDescription(expenseTypeAddResource.getDescription());
		expenseType.setStatus(expenseTypeAddResource.getStatus());
		expenseType.setCreatedUser(createdUser);
		expenseType.setCreatedDate(getCreateOrModifyDate());
		expenseType.setSyncTs(getCreateOrModifyDate());
		expenseType = expenseTypeRepository.saveAndFlush(expenseType);
		
		return expenseType;
	}
	
	@Override
	public Long updateAndValidateExpenseType(String tenantId, String createdUser, Long id, ExpenseTypeUpdateResource expenseTypeUpdateResource) {
		
		LoggerRequest.getInstance().logInfo("ExpenseType********************************Validate Version*********************************************");
		Optional<ExpenseType> isPresentExpenseType = expenseTypeRepository.findById(id);
		if(!isPresentExpenseType.get().getVersion().equals(Long.parseLong(expenseTypeUpdateResource.getVersion())))
			throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "version");
		
		LoggerRequest.getInstance().logInfo("ExpenseType********************************Validate Code*********************************************");
		Optional<ExpenseType> isPresentExpenseTypeByCode = expenseTypeRepository.findByCodeAndIdNotIn(expenseTypeUpdateResource.getCode(), id);
		if (isPresentExpenseTypeByCode.isPresent())
			throw new ValidateRecordException(environment.getProperty("common.unique"), "code");
		
		LoggerRequest.getInstance().logInfo("ExpenseType********************************Update Expense Type*********************************************");
		ExpenseType expenseType=updateExpenseType(createdUser, expenseTypeUpdateResource, id);
		
		return expenseType.getId();
	}
	
	private ExpenseType updateExpenseType(String createdUser, ExpenseTypeUpdateResource expenseTypeUpdateResource, Long id) {
		ExpenseType expenseType = expenseTypeRepository.getOne(id);
		expenseType.setCode(expenseTypeUpdateResource.getCode());
		expenseType.setName(expenseTypeUpdateResource.getName());
		expenseType.setDescription(expenseTypeUpdateResource.getDescription());
		expenseType.setStatus(expenseTypeUpdateResource.getStatus());
		expenseType.setModifiedUser(createdUser);
		expenseType.setModifiedDate(getCreateOrModifyDate());
		expenseType.setSyncTs(getCreateOrModifyDate());
		expenseType=expenseTypeRepository.saveAndFlush(expenseType);
		
		return expenseType;
	}

	private Timestamp getCreateOrModifyDate() {
		Calendar calendar = Calendar.getInstance();
    	java.util.Date now = calendar.getTime();
    	return new Timestamp(now.getTime());
	}

	@Override
	public Page<ExpenseType> searchExpenseType(String searchq, String name, String code, Pageable pageable) {
		if(searchq==null || searchq.isEmpty())
			searchq=null;
		if(name==null || name.isEmpty())
			name=null;
		if(code==null || code.isEmpty())
			code=null;
		return expenseTypeRepository.searchExpenseType(searchq, name, code, pageable);
	}
}