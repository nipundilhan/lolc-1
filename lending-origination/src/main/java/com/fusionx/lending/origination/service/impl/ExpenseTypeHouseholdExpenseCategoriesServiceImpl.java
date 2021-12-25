package com.fusionx.lending.origination.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.origination.base.MessagePropertyBase;
import com.fusionx.lending.origination.core.LogginAuthentcation;
import com.fusionx.lending.origination.domain.ExpenseType;
import com.fusionx.lending.origination.domain.ExpenseTypeHouseholdExpenseCategories;
import com.fusionx.lending.origination.domain.ExpenseTypeHouseholdExpenseCategoriesDetails;
import com.fusionx.lending.origination.domain.HouseHoldExpenseCategory;
import com.fusionx.lending.origination.domain.IncomeSourceDetails;
import com.fusionx.lending.origination.domain.OtherIncomeExpenseType;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.enums.ServiceEntity;
import com.fusionx.lending.origination.enums.ServicePoint;
import com.fusionx.lending.origination.exception.DetailListValidateException;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.repository.ExpenseTypeHouseholdExpenseCategoriesDetailsRepository;
import com.fusionx.lending.origination.repository.ExpenseTypeHouseholdExpenseCategoriesRepository;
import com.fusionx.lending.origination.repository.ExpenseTypeRepository;
import com.fusionx.lending.origination.repository.HouseHoldExpenseCategoryRepository;
import com.fusionx.lending.origination.resource.ExpenseTypeHouseholdExpenseCategoriesAddResource;
import com.fusionx.lending.origination.resource.ExpenseTypeHouseholdExpenseCategoriesUpdateResource;
import com.fusionx.lending.origination.resource.ExpenseTypeListResource;
import com.fusionx.lending.origination.service.ExpenseTypeHouseholdExpenseCategoriesService;
import com.fusionx.lending.origination.service.ValidateService;

@Component
@Transactional(rollbackFor=Exception.class)
public class ExpenseTypeHouseholdExpenseCategoriesServiceImpl extends MessagePropertyBase implements ExpenseTypeHouseholdExpenseCategoriesService {

	@Autowired
	private ExpenseTypeHouseholdExpenseCategoriesRepository expenseTypeHouseholdExpenseCatRepository;
	
	@Autowired
	private ValidateService validateService;
	
	@Autowired
	private HouseHoldExpenseCategoryRepository houseHoldExpenseCategoryRepository;
	
	@Autowired
	private ExpenseTypeRepository expenseTypeRepository;

	@Override
	public List<ExpenseTypeHouseholdExpenseCategories> getAll() {
		List<ExpenseTypeHouseholdExpenseCategories> expenseTypeHouseholdExpenseCategories = expenseTypeHouseholdExpenseCatRepository.findAll();
		
		return expenseTypeHouseholdExpenseCategories;
	}

	@Override
	public Optional<ExpenseTypeHouseholdExpenseCategories> getById(Long id) {
		return expenseTypeHouseholdExpenseCatRepository.findById(id);
	}
	

	@Override
	public List<ExpenseTypeHouseholdExpenseCategories> getByStatus(String status) {
		List<ExpenseTypeHouseholdExpenseCategories> expenseTypeHouseholdExpenseCategoriess = expenseTypeHouseholdExpenseCatRepository.findByStatus(CommonStatus.valueOf(status));
		return expenseTypeHouseholdExpenseCategoriess;
	}

	@Override
	public void addExpenseTypeHouseholdExpenseCategories(String tenantId,
			ExpenseTypeHouseholdExpenseCategoriesAddResource expenseTypeHouseholdExpenseCategoriesAddResource) {
        
		if(LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
        	throw new ValidateRecordException(environment.getProperty(COMMON_NOT_NULL), USERNAME);
		
		HouseHoldExpenseCategory houseHoldExpenseCategory = validateHouseHoldExpenseCategory(validateService.stringToLong(expenseTypeHouseholdExpenseCategoriesAddResource.getHouseHoldExpenseCatId()), expenseTypeHouseholdExpenseCategoriesAddResource.getHouseHoldExpenseCatName());
		
		if(expenseTypeHouseholdExpenseCategoriesAddResource.getExpenseTypes() != null && !expenseTypeHouseholdExpenseCategoriesAddResource.getExpenseTypes().isEmpty()) {
			Integer index = 0;
			for(ExpenseTypeListResource expenseTypeListResource : expenseTypeHouseholdExpenseCategoriesAddResource.getExpenseTypes()) {
				ExpenseType expenseType = validateExpenseType(validateService.stringToLong(expenseTypeListResource.getExpenseTypeId()), expenseTypeListResource.getExpenseTypeName(), index);
				saveOrUpadateExpenseTypeHouseholdExpenseCategories(expenseTypeListResource.getId(), tenantId, expenseType, expenseTypeListResource.getStatus(),houseHoldExpenseCategory, index);
				index++;
			}
		}
		
		
	}

	@Override
	public void updateExpenseTypeHouseholdExpenseCategories(String tenantId,
			ExpenseTypeHouseholdExpenseCategoriesUpdateResource expenseTypeHouseholdExpenseCategoriesUpdateResource) {
		if(LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
        	throw new ValidateRecordException(environment.getProperty(COMMON_NOT_NULL), USERNAME);
		
		
		HouseHoldExpenseCategory houseHoldExpenseCategory = validateHouseHoldExpenseCategory(validateService.stringToLong(expenseTypeHouseholdExpenseCategoriesUpdateResource.getHouseHoldExpenseCatId()), expenseTypeHouseholdExpenseCategoriesUpdateResource.getHouseHoldExpenseCatName());
		
		
		if(expenseTypeHouseholdExpenseCategoriesUpdateResource.getExpenseTypes() != null && !expenseTypeHouseholdExpenseCategoriesUpdateResource.getExpenseTypes().isEmpty()) {
			Integer index = 0;
			for(ExpenseTypeListResource expenseTypeListResource : expenseTypeHouseholdExpenseCategoriesUpdateResource.getExpenseTypes()) {
				ExpenseType expenseType = validateExpenseType(validateService.stringToLong(expenseTypeListResource.getExpenseTypeId()), expenseTypeListResource.getExpenseTypeName(), index);
				saveOrUpadateExpenseTypeHouseholdExpenseCategories(expenseTypeListResource.getId(), tenantId, expenseType, expenseTypeListResource.getStatus(),houseHoldExpenseCategory, index);
				index++;
			}
		}
		
	}
	
	private HouseHoldExpenseCategory validateHouseHoldExpenseCategory(Long id, String name) {
		
		Optional<HouseHoldExpenseCategory> isPresentHouseHoldExpenseCategory = houseHoldExpenseCategoryRepository.findByIdAndNameAndStatus(id, name, CommonStatus.ACTIVE.toString());
		
		if(!isPresentHouseHoldExpenseCategory.isPresent())
			throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), "houseHoldExpenseCategoryId");
		
		return isPresentHouseHoldExpenseCategory.get();
	}
	
	private ExpenseType validateExpenseType(Long id, String name, Integer index) {
		
		Optional<ExpenseType> isPresentExpenseType = expenseTypeRepository.findByIdAndNameAndStatus(id, name, CommonStatus.ACTIVE.toString());
		
		if(!isPresentExpenseType.isPresent())
			throw new DetailListValidateException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.EXPENSE_TYPE_ID, ServicePoint.EXPENCE_TYPE_HOUSEHOLD_EXPENSE_CATEGORY, index);
		
		return isPresentExpenseType.get();
	}
	
	private void saveOrUpadateExpenseTypeHouseholdExpenseCategories(String id, String tenantId, ExpenseType expenseType, String status,HouseHoldExpenseCategory houseHoldExpenseCategory, Integer index) {
		
		ExpenseTypeHouseholdExpenseCategories expenseTypeHouseholdExpenseCategories = new ExpenseTypeHouseholdExpenseCategories();
		
		
		if(id != null && !id.isEmpty()) {
			
			Optional<ExpenseTypeHouseholdExpenseCategories> isPresentExpenseTypeHouseholdExpenseCategories = expenseTypeHouseholdExpenseCatRepository.findById(validateService.stringToLong(id));
			
			if(!isPresentExpenseTypeHouseholdExpenseCategories.isPresent()) {
				throw new ValidateRecordException(environment.getProperty("expenseType.house-hold-category-not-found"), MESSAGE);
			
			} else {
				
				Optional<ExpenseTypeHouseholdExpenseCategories> isPresentExpenseTypeHouseholdExpenseCategoriesOpt = expenseTypeHouseholdExpenseCatRepository.findByExpenseTypeAndHouseHoldExpenseCategoryAndIdNot(expenseType, houseHoldExpenseCategory,validateService.stringToLong(id));
				if(isPresentExpenseTypeHouseholdExpenseCategoriesOpt.isPresent()) {
					throw new ValidateRecordException(environment.getProperty("category.expense-duplicate"), MESSAGE);
				}
				
				expenseTypeHouseholdExpenseCategories = isPresentExpenseTypeHouseholdExpenseCategories.get();
				expenseTypeHouseholdExpenseCategories.setModifiedDate(validateService.getCreateOrModifyDate());
				expenseTypeHouseholdExpenseCategories.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
				
			}
		} else {
			
			Optional<ExpenseTypeHouseholdExpenseCategories> houseHoldExpenseCategoryIdAndExpenseTypeId = expenseTypeHouseholdExpenseCatRepository.findByExpenseTypeAndHouseHoldExpenseCategory(expenseType,houseHoldExpenseCategory);
			if(houseHoldExpenseCategoryIdAndExpenseTypeId.isPresent()) {
				throw new ValidateRecordException(environment.getProperty("category.expense-duplicate"), "catExpDuplicate");
			}
			
			expenseTypeHouseholdExpenseCategories.setTenantId(tenantId);
			expenseTypeHouseholdExpenseCategories.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
			expenseTypeHouseholdExpenseCategories.setCreatedDate(validateService.getCreateOrModifyDate());
		}
		
		expenseTypeHouseholdExpenseCategories.setHouseHoldExpenseCategory(houseHoldExpenseCategory);
		expenseTypeHouseholdExpenseCategories.setExpenseType(expenseType);
		expenseTypeHouseholdExpenseCategories.setStatus(CommonStatus.valueOf(status));
		expenseTypeHouseholdExpenseCategories.setExpenseType(expenseType);
		expenseTypeHouseholdExpenseCategories.setSyncTs(validateService.getCreateOrModifyDate());
		
		expenseTypeHouseholdExpenseCatRepository.saveAndFlush(expenseTypeHouseholdExpenseCategories);
		
	}
}
