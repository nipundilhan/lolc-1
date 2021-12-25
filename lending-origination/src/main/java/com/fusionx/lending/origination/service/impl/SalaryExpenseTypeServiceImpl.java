package com.fusionx.lending.origination.service.impl;
/**
 * Salary Expense Type Service Impl
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   30-08-2021   	FXL-521   	 FX-685		Piyumi      Created
 *    
 ********************************************************************************************************
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.origination.base.MessagePropertyBase;
import com.fusionx.lending.origination.core.LogginAuthentcation;
import com.fusionx.lending.origination.domain.ExpenseType;
import com.fusionx.lending.origination.domain.SalaryExpenseType;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.enums.ServiceEntity;
import com.fusionx.lending.origination.enums.ServicePoint;
import com.fusionx.lending.origination.exception.DetailListValidateException;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.repository.SalaryExpenseTypeRepository;
import com.fusionx.lending.origination.repository.ExpenseTypeRepository;
import com.fusionx.lending.origination.resource.SalaryExpenseTypeAddResource;
import com.fusionx.lending.origination.resource.SalaryExpenseTypeUpdateResource;
import com.fusionx.lending.origination.resource.ExpenseTypeListResource;
import com.fusionx.lending.origination.service.SalaryExpenseTypeService;
import com.fusionx.lending.origination.service.ValidateService;

@Component
@Transactional(rollbackFor=Exception.class)
public class SalaryExpenseTypeServiceImpl extends MessagePropertyBase implements SalaryExpenseTypeService {

	private SalaryExpenseTypeRepository salaryExpenseTypeRepository;
	
	@Autowired
	public void setSalaryExpenseTypeRepository(SalaryExpenseTypeRepository salaryExpenseTypeRepository) {
		this.salaryExpenseTypeRepository = salaryExpenseTypeRepository;
	}
	
	private ValidateService validateService;
	
	@Autowired
	public void setValidateService(ValidateService validateService) {
		this.validateService = validateService;
	}
	
	private ExpenseTypeRepository expenseTypeRepository;
	
	@Autowired
	public void setExpenseTypeRepository(ExpenseTypeRepository expenseTypeRepository) {
		this.expenseTypeRepository = expenseTypeRepository;
	}
	
	@Override
	public List<SalaryExpenseType> findAll() {
		List<SalaryExpenseType> salaryExpenseTypeList = salaryExpenseTypeRepository.findAll();
	
		for (SalaryExpenseType salaryExpenseType : salaryExpenseTypeList) {
			setSalaryExpenseTypes(salaryExpenseType);
		}
		return salaryExpenseTypeList;
	}

	@Override
	public Optional<SalaryExpenseType> findById(Long id) {
		Optional<SalaryExpenseType> isPresentSalaryExpenseType = salaryExpenseTypeRepository.findById(id);
		if (isPresentSalaryExpenseType.isPresent()) {			
			return Optional.ofNullable(setSalaryExpenseTypes(isPresentSalaryExpenseType.get()));
		} else {
			return Optional.empty();
		}
	}

	@Override
	public List<SalaryExpenseType> findByStatus(String status) {
		List<SalaryExpenseType> salaryExpenseTypeList = salaryExpenseTypeRepository.findByStatus(CommonStatus.valueOf(status));
		
		for (SalaryExpenseType salaryExpenseType : salaryExpenseTypeList) {
			setSalaryExpenseTypes(salaryExpenseType);
		}
		return salaryExpenseTypeList;
	}

	@Override
	public Boolean addSalaryExpenseType(String tenantId,
			SalaryExpenseTypeAddResource salaryExpenseTypeAddResource) {
        
		if(LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
        	throw new ValidateRecordException(environment.getProperty(COMMON_NOT_NULL), USERNAME);
		
		if(salaryExpenseTypeAddResource.getExpenseTypeList() != null && !salaryExpenseTypeAddResource.getExpenseTypeList().isEmpty()) {
			Integer index = 0;
			
			for(ExpenseTypeListResource expenseTypeResource : salaryExpenseTypeAddResource.getExpenseTypeList()) {
				
				ExpenseType expenseType = validateExpenseType(validateService.stringToLong(expenseTypeResource.getExpenseTypeId()), expenseTypeResource.getExpenseTypeName(), index);
				
				Optional<SalaryExpenseType> isDuplicateExpenseType = salaryExpenseTypeRepository.findByExpenseTypeIdAndStatus(validateService.stringToLong(expenseTypeResource.getExpenseTypeId()),CommonStatus.ACTIVE);
				
				if(isDuplicateExpenseType.isPresent()) {
					throw new ValidateRecordException(environment.getProperty(COMMON_UNIQUE), "expenseTypeId");
				}
				
				saveSalaryExpenseType(tenantId,expenseType,expenseTypeResource.getStatus());
				index++;
			}
		}else
			throw new ValidateRecordException(environment.getProperty(COMMON_NOT_NULL),"expenseTypeList");
		
		return true;
	}

	@Override
	public SalaryExpenseType updateSalaryExpenseType(String tenantId, Long id,
			SalaryExpenseTypeUpdateResource salaryExpenseTypeUpdateResource) {
		
		if(LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
        	throw new ValidateRecordException(environment.getProperty(COMMON_NOT_NULL), USERNAME);
		
		Optional<SalaryExpenseType> isPresentSalaryExpenseType = salaryExpenseTypeRepository.findById(id);
		
		if(!isPresentSalaryExpenseType.isPresent())
			throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND), MESSAGE);
			
			Optional<ExpenseType> isPresentExpenseType = expenseTypeRepository.findByIdAndNameAndStatus(validateService.stringToLong(salaryExpenseTypeUpdateResource.getExpenseTypeId()), salaryExpenseTypeUpdateResource.getExpenseTypeName(), CommonStatus.ACTIVE.toString());
			
			if(!isPresentExpenseType.isPresent())
				throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), "expenseTypeId");
			
			Optional<SalaryExpenseType> isDuplicateExpenseType = salaryExpenseTypeRepository.findByExpenseTypeIdAndStatusAndIdNotIn(isPresentExpenseType.get().getId(),CommonStatus.ACTIVE, isPresentSalaryExpenseType.get().getId());
						
			if(isDuplicateExpenseType.isPresent()) {
				throw new ValidateRecordException(environment.getProperty(COMMON_UNIQUE), "expenseTypeId");
			}
			
			if(!isPresentSalaryExpenseType.get().getVersion().equals(Long.parseLong(salaryExpenseTypeUpdateResource.getVersion())))
				throw new ValidateRecordException(environment.getProperty(INVALID_VERSION), "version");
						
				SalaryExpenseType salaryExpenseType = isPresentSalaryExpenseType.get();
				salaryExpenseType.setTenantId(tenantId);
				salaryExpenseType.setExpenseType(isPresentExpenseType.get());
				salaryExpenseType.setStatus(CommonStatus.valueOf(salaryExpenseTypeUpdateResource.getStatus()));
				salaryExpenseType.setModifiedDate(validateService.getCreateOrModifyDate());
				salaryExpenseType.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
				salaryExpenseType.setSyncTs(validateService.getSyncTs());		
				salaryExpenseType = salaryExpenseTypeRepository.save(salaryExpenseType);
				
		return salaryExpenseType;
	}
	
	private ExpenseType validateExpenseType(Long id, String name, Integer index) {
		
		Optional<ExpenseType> isPresentExpenseType = expenseTypeRepository.findByIdAndNameAndStatus(id, name, CommonStatus.ACTIVE.toString());
		
		if(!isPresentExpenseType.isPresent())
			throw new DetailListValidateException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.EXPENSE_TYPE_ID, ServicePoint.EXPENCE_TYPE_OTHER_INCOME_CATEGORY, index);
		
		return isPresentExpenseType.get();
	}
	
	private SalaryExpenseType setSalaryExpenseTypes(SalaryExpenseType salaryExpenseType) {		
		Optional<ExpenseType> expenseType = expenseTypeRepository.findById(salaryExpenseType.getExpenseType().getId());
				
			if(expenseType.isPresent()) {
				salaryExpenseType.setExpenseTypesId(expenseType.get().getId());
				salaryExpenseType.setExpenseTypesName(expenseType.get().getName());
				salaryExpenseType.setExpenseTypesCode(expenseType.get().getCode());
			}			
		return salaryExpenseType;
		
	}
	
	private void saveSalaryExpenseType(String tenantId, ExpenseType expenseType, String status) {
		SalaryExpenseType salaryExpenseType = new SalaryExpenseType();
		salaryExpenseType.setTenantId(tenantId);
		salaryExpenseType.setExpenseType(expenseType);
		salaryExpenseType.setStatus(CommonStatus.valueOf(status));
		salaryExpenseType.setCreatedDate(validateService.getCreateOrModifyDate());
		salaryExpenseType.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
		salaryExpenseType.setSyncTs(validateService.getSyncTs());		
		salaryExpenseTypeRepository.save(salaryExpenseType);
	}

	@Override
	public List<SalaryExpenseType> findByCode(String code) {
		List<SalaryExpenseType> salaryExpenseTypeList = new ArrayList<>();
		
		Optional<ExpenseType> expenseType = expenseTypeRepository.findByCode(code);		
		
		if(expenseType.isPresent()) {
			salaryExpenseTypeList = salaryExpenseTypeRepository.findByExpenseTypeId(expenseType.get().getId());
			for (SalaryExpenseType salaryExpenseType : salaryExpenseTypeList) {
				setSalaryExpenseTypes(salaryExpenseType);
			}
		}
		return salaryExpenseTypeList;
		
	}

	@Override
	public Page<SalaryExpenseType> search(String searchq, String name, String code, Pageable pageable) {
		if(searchq==null || searchq.isEmpty())
			searchq=null;
		if(name==null || name.isEmpty())
			name=null;
		if(code==null || code.isEmpty())
			code=null;
		
		List<SalaryExpenseType> salaryExpenseTypeList = new ArrayList<>();
		 Page<ExpenseType> expenseTypeList =  expenseTypeRepository.searchExpenseType(searchq, name, code, pageable);
		 
		 if(!expenseTypeList.isEmpty()) {
			 
			 for(ExpenseType expenseType : expenseTypeList){
				 List<SalaryExpenseType> salaryExpenseTypes = salaryExpenseTypeRepository.findByExpenseTypeId(expenseType.getId());
				 
				 if(!salaryExpenseTypes.isEmpty()) {
					for (SalaryExpenseType salaryExpenseType : salaryExpenseTypes) {
						setSalaryExpenseTypes(salaryExpenseType);
					}
				 }
				 
				salaryExpenseTypeList.addAll(salaryExpenseTypes);
			}
		 }
		return new PageImpl<>(salaryExpenseTypeList);
	
	}
}
