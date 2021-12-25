package com.fusionx.lending.origination.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.origination.base.MessagePropertyBase;
import com.fusionx.lending.origination.core.LogginAuthentcation;
import com.fusionx.lending.origination.domain.CultivationCategory;
import com.fusionx.lending.origination.domain.ExpenceTypeCultivationCategory;
import com.fusionx.lending.origination.domain.ExpenseType;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.enums.ServiceEntity;
import com.fusionx.lending.origination.enums.ServicePoint;
import com.fusionx.lending.origination.exception.DetailListValidateException;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.repository.CultivationCategoryRepository;
import com.fusionx.lending.origination.repository.ExpenceTypeCultivationCategoryDetailsRepository;
import com.fusionx.lending.origination.repository.ExpenceTypeCultivationCategoryRepository;
import com.fusionx.lending.origination.repository.ExpenseTypeRepository;
import com.fusionx.lending.origination.resource.ExpenceTypeCultivationCategoryAddResource;
import com.fusionx.lending.origination.resource.ExpenceTypeCultivationCategoryUpdateResource;
import com.fusionx.lending.origination.resource.ExpenseTypeListResource;
import com.fusionx.lending.origination.service.ExpenceTypeCultivationCategoryService;
import com.fusionx.lending.origination.service.ValidateService;

@Component
@Transactional(rollbackFor=Exception.class)
public class ExpenceTypeCultivationCategoryServiceImpl extends MessagePropertyBase implements ExpenceTypeCultivationCategoryService {
	
	@Autowired
	private ExpenceTypeCultivationCategoryRepository expenceTypeCultiCatRepository;
	
	@Autowired
	private ValidateService validateService;
	
	@Autowired
	private CultivationCategoryRepository cultivationCategoryRepository;
	
	@Autowired
	private ExpenseTypeRepository expenseTypeRepository;

	@Override
	public List<ExpenceTypeCultivationCategory> getAll() {
		List<ExpenceTypeCultivationCategory> expenceTypeCultivationCategorys = expenceTypeCultiCatRepository.findAll();
		return expenceTypeCultivationCategorys;
	}
	
	@Override
	public Optional<ExpenceTypeCultivationCategory> getById(Long id) {
		Optional<ExpenceTypeCultivationCategory> isPresentExpenceTypeCultivationCategory = expenceTypeCultiCatRepository.findById(id);
		if (isPresentExpenceTypeCultivationCategory.isPresent()) {
			return isPresentExpenceTypeCultivationCategory;
		} else {
			return Optional.empty();
		}
	}

	@Override
	public List<ExpenceTypeCultivationCategory> getByStatus(String status) {
		List<ExpenceTypeCultivationCategory> expenceTypeCultivationCategorys = expenceTypeCultiCatRepository.findByStatus(CommonStatus.valueOf(status));
		
		return expenceTypeCultivationCategorys;
	}

	@Override
	public void addExpenceTypeCultivationCategory(String tenantId,
			ExpenceTypeCultivationCategoryAddResource expenceTypeCultivationCategoryAddResource) {
        
		if(LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
        	throw new ValidateRecordException(environment.getProperty(COMMON_NOT_NULL), USERNAME);
		
		CultivationCategory cultivationCategory = validateCultivationCategoty(validateService.stringToLong(expenceTypeCultivationCategoryAddResource.getCultivationCatId()), expenceTypeCultivationCategoryAddResource.getCultivationCatName());
		
		
		if(expenceTypeCultivationCategoryAddResource.getExpenseTypes() != null && !expenceTypeCultivationCategoryAddResource.getExpenseTypes().isEmpty()) {
			Integer index = 0;
			for(ExpenseTypeListResource expenseTypeListResource : expenceTypeCultivationCategoryAddResource.getExpenseTypes()) {
				ExpenseType expenseType = validateExpenseType(validateService.stringToLong(expenseTypeListResource.getExpenseTypeId()), expenseTypeListResource.getExpenseTypeName(), index);
				saveOrUpadateExpenceTypeCultivationCategoryDet(expenseTypeListResource.getId(), tenantId, expenseType, expenseTypeListResource.getStatus(),cultivationCategory, index);
				index++;
			}
		}
		
	}

	@Override
	public void updateExpenceTypeCultivationCategory(String tenantId,
			ExpenceTypeCultivationCategoryUpdateResource expenceTypeCultivationCategoryUpdateResource) {
		if(LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
        	throw new ValidateRecordException(environment.getProperty(COMMON_NOT_NULL), USERNAME);
		
		CultivationCategory cultivationCategory = validateCultivationCategoty(validateService.stringToLong(expenceTypeCultivationCategoryUpdateResource.getCultivationCatId()), expenceTypeCultivationCategoryUpdateResource.getCultivationCatName());
		
		
		if(expenceTypeCultivationCategoryUpdateResource.getExpenseTypes() != null && !expenceTypeCultivationCategoryUpdateResource.getExpenseTypes().isEmpty()) {
			Integer index = 0;
			for(ExpenseTypeListResource expenseTypeListResource : expenceTypeCultivationCategoryUpdateResource.getExpenseTypes()) {
				ExpenseType expenseType = validateExpenseType(validateService.stringToLong(expenseTypeListResource.getExpenseTypeId()), expenseTypeListResource.getExpenseTypeName(), index);
				saveOrUpadateExpenceTypeCultivationCategoryDet(expenseTypeListResource.getId(), tenantId, expenseType, expenseTypeListResource.getStatus(),cultivationCategory, index);
				index++;
			}
		}
		
	}
	
	private CultivationCategory validateCultivationCategoty(Long id, String name) {
		
		Optional<CultivationCategory> isPresentCultivationCategory = cultivationCategoryRepository.findByIdAndNameAndStatus(id, name, CommonStatus.ACTIVE);
		
		if(!isPresentCultivationCategory.isPresent())
			throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), "cultivationCategoryId");
		
		return isPresentCultivationCategory.get();
	}
	
	private ExpenseType validateExpenseType(Long id, String name, Integer index) {
		
		Optional<ExpenseType> isPresentExpenseType = expenseTypeRepository.findByIdAndNameAndStatus(id, name, CommonStatus.ACTIVE.toString());
		
		if(!isPresentExpenseType.isPresent())
			throw new DetailListValidateException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.EXPENSE_TYPE_ID, ServicePoint.EXPENCE_TYPE_CULTIVATION_CATEGORY, index);
		
		return isPresentExpenseType.get();
	}
	
	private void saveOrUpadateExpenceTypeCultivationCategoryDet(String id, String tenantId, ExpenseType expenseType, String status,CultivationCategory cultivationCategory, Integer index) {
		
		ExpenceTypeCultivationCategory expenceTypeCultivationCategory = new ExpenceTypeCultivationCategory();
		
		
		if(id != null && !id.isEmpty()) {
			
			Optional<ExpenceTypeCultivationCategory> isPresentExpenceTypeCultivationCategory = expenceTypeCultiCatRepository.findById(validateService.stringToLong(id));
			
			Optional<ExpenceTypeCultivationCategory> isPresentExpenceTypeCultivationCat = expenceTypeCultiCatRepository.findByCultivationCategoryAndExpenseTypeAndIdNotIn(cultivationCategory,expenseType, validateService.stringToLong(id));
			if(isPresentExpenceTypeCultivationCat.isPresent()) {
				throw new ValidateRecordException(environment.getProperty("category.expense-duplicate"), "catExpDuplicate");
			}
			
			
			if(!isPresentExpenceTypeCultivationCategory.isPresent()) {
				throw new DetailListValidateException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.EXPENCE_TYPE_CULTIVATION_CATEGORY_DET_ID, ServicePoint.EXPENCE_TYPE_CULTIVATION_CATEGORY, index);
			} else {
				expenceTypeCultivationCategory = isPresentExpenceTypeCultivationCategory.get();
				expenceTypeCultivationCategory.setModifiedDate(validateService.getCreateOrModifyDate());
				expenceTypeCultivationCategory.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
			}
		} else {
			
			Optional<ExpenceTypeCultivationCategory> isPresentExpenceTypeCultivationCategory = expenceTypeCultiCatRepository.findByCultivationCategoryAndExpenseType(cultivationCategory,expenseType);
			if(isPresentExpenceTypeCultivationCategory.isPresent()) {
				throw new ValidateRecordException(environment.getProperty("category.expense-duplicate"), "catExpDuplicate");
			}
			
			expenceTypeCultivationCategory.setTenantId(tenantId);
			expenceTypeCultivationCategory.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
			expenceTypeCultivationCategory.setCreatedDate(validateService.getCreateOrModifyDate());
		}
		
		
		
		expenceTypeCultivationCategory.setCultivationCategory(cultivationCategory);
		expenceTypeCultivationCategory.setStatus(CommonStatus.valueOf(status));
		expenceTypeCultivationCategory.setExpenseType(expenseType);
		expenceTypeCultivationCategory.setSyncTs(validateService.getCreateOrModifyDate());
		
		expenceTypeCultiCatRepository.saveAndFlush(expenceTypeCultivationCategory);
		
	}
	
	@Override
	public List<ExpenceTypeCultivationCategory> getByCaltiCatCode(String cultiCatCode) {
		List<ExpenceTypeCultivationCategory> expenceTypeCultivationCategorys = expenceTypeCultiCatRepository.findByCultivationCategoryCode(cultiCatCode);
		
		return expenceTypeCultivationCategorys;
	}

}
