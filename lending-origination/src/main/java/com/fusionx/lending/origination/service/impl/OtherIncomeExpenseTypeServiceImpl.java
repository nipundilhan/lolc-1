package com.fusionx.lending.origination.service.impl;
/**
 * Other Income Expense Type Service Impl
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   19-08-2021   	FXL-524   	 FX-542		Piyumi      Created
 *    
 ********************************************************************************************************
 */

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.origination.base.MessagePropertyBase;
import com.fusionx.lending.origination.core.LogginAuthentcation;
import com.fusionx.lending.origination.domain.ExpenceTypeCultivationCategory;
import com.fusionx.lending.origination.domain.ExpenseType;
import com.fusionx.lending.origination.domain.OtherIncomeExpenseType;
import com.fusionx.lending.origination.domain.OtherIncomeExpenseTypeDetail;
import com.fusionx.lending.origination.domain.OtherIncomeCategory;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.enums.ServiceEntity;
import com.fusionx.lending.origination.enums.ServicePoint;
import com.fusionx.lending.origination.exception.DetailListValidateException;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.repository.OtherIncomeExpenseTypeRepository;
import com.fusionx.lending.origination.repository.ExpenseTypeRepository;
import com.fusionx.lending.origination.repository.OtherIncomeCategoryRepository;
import com.fusionx.lending.origination.repository.OtherIncomeExpenseTypeDetailRepository;
import com.fusionx.lending.origination.resource.OtherIncomeExpenseTypeAddResource;
import com.fusionx.lending.origination.resource.OtherIncomeExpenseTypeUpdateResource;
import com.fusionx.lending.origination.resource.ExpenseTypeListResource;
import com.fusionx.lending.origination.service.OtherIncomeExpenseTypeService;
import com.fusionx.lending.origination.service.ValidateService;

@Component
@Transactional(rollbackFor=Exception.class)
public class OtherIncomeExpenseTypeServiceImpl extends MessagePropertyBase implements OtherIncomeExpenseTypeService {

	private OtherIncomeExpenseTypeRepository otherIncomeExpenseTypeRepository;
	
	@Autowired
	public void setOtherIncomeExpenseTypeRepository(OtherIncomeExpenseTypeRepository otherIncomeExpenseTypeRepository) {
		this.otherIncomeExpenseTypeRepository = otherIncomeExpenseTypeRepository;
	}
	
	private ValidateService validateService;
	
	@Autowired
	public void setValidateService(ValidateService validateService) {
		this.validateService = validateService;
	}
	
	private OtherIncomeCategoryRepository otherIncomeCategoryRepository;
	
	@Autowired
	public void setOtherIncomeCategoryRepository(OtherIncomeCategoryRepository otherIncomeCategoryRepository) {
		this.otherIncomeCategoryRepository = otherIncomeCategoryRepository;
	}
	
	private ExpenseTypeRepository expenseTypeRepository;
	
	@Autowired
	public void setExpenseTypeRepository(ExpenseTypeRepository expenseTypeRepository) {
		this.expenseTypeRepository = expenseTypeRepository;
	}
	
	private OtherIncomeExpenseTypeDetailRepository otherIncomeExpenseTypeDetailRepository;
	
	@Autowired
	public void setOtherIncomeExpenseTypeDetailRepository(OtherIncomeExpenseTypeDetailRepository otherIncomeExpenseTypeDetailRepository) {
		this.otherIncomeExpenseTypeDetailRepository = otherIncomeExpenseTypeDetailRepository;
	}

	@Override
	public List<OtherIncomeExpenseType> findAll() {
		List<OtherIncomeExpenseType> otherIncomeExpenseTypeList = otherIncomeExpenseTypeRepository.findAll();
	
//		for (OtherIncomeExpenseType otherIncomeExpenseType : otherIncomeExpenseTypeList) {
//			setOtherIncomeExpenseTypeDetails(otherIncomeExpenseType);
//		}
		return otherIncomeExpenseTypeList;
	}

	@Override
	public Optional<OtherIncomeExpenseType> findById(Long id) {
		Optional<OtherIncomeExpenseType> isPresentOtherIncomeExpenseType = otherIncomeExpenseTypeRepository.findById(id);
		
		if (isPresentOtherIncomeExpenseType.isPresent()) {
			return isPresentOtherIncomeExpenseType;
		} else {
			return Optional.empty();
		}
	}

	@Override
	public List<OtherIncomeExpenseType> findByStatus(String status) {
		List<OtherIncomeExpenseType> otherIncomeExpenseTypeList = otherIncomeExpenseTypeRepository.findByStatus(CommonStatus.valueOf(status));
		
//		for (OtherIncomeExpenseType otherIncomeExpenseType : otherIncomeExpenseTypeList) {
//			setOtherIncomeExpenseTypeDetails(otherIncomeExpenseType);
//		}
		return otherIncomeExpenseTypeList;
	}

	@Override
	public void addOtherIncomeExpenseType(String tenantId,
			OtherIncomeExpenseTypeAddResource otherIncomeExpenseTypeAddResource) {
        
		if(LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
        	throw new ValidateRecordException(environment.getProperty(COMMON_NOT_NULL), USERNAME);
		
		OtherIncomeCategory otherIncomeCategory = validateOtherIncomeCategory(validateService.stringToLong(otherIncomeExpenseTypeAddResource.getOtherIncomeCategoryId()), otherIncomeExpenseTypeAddResource.getOtherIncomeCategoryName());
		
		
		if(otherIncomeExpenseTypeAddResource.getExpenseTypeList() != null && !otherIncomeExpenseTypeAddResource.getExpenseTypeList().isEmpty()) {
			Integer index = 0;
			
			for(ExpenseTypeListResource expenseTypeResource : otherIncomeExpenseTypeAddResource.getExpenseTypeList()) {
				
				ExpenseType expenseType = validateExpenseType(validateService.stringToLong(expenseTypeResource.getExpenseTypeId()), expenseTypeResource.getExpenseTypeName(), index);
				saveOtherIncomeExpenseTypeDetail(expenseTypeResource.getId(),tenantId,expenseType,expenseTypeResource.getStatus(),otherIncomeCategory,index);
				index++;
			}
		}else
			throw new ValidateRecordException(environment.getProperty(COMMON_NOT_NULL),"expenseTypeList");
		
	}

	@Override
	public void updateOtherIncomeExpenseType(String tenantId,
			OtherIncomeExpenseTypeUpdateResource otherIncomeExpenseTypeUpdateResource) {
		
		if(LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
        	throw new ValidateRecordException(environment.getProperty(COMMON_NOT_NULL), USERNAME);
		
		OtherIncomeCategory otherIncomeCategory = validateOtherIncomeCategory(validateService.stringToLong(otherIncomeExpenseTypeUpdateResource.getOtherIncomeCategoryId()), otherIncomeExpenseTypeUpdateResource.getOtherIncomeCategoryName());
		
		
		if(otherIncomeExpenseTypeUpdateResource.getExpenseTypeList() != null && !otherIncomeExpenseTypeUpdateResource.getExpenseTypeList().isEmpty()) {
			Integer index = 0;
			
			for(ExpenseTypeListResource expenseTypeResource : otherIncomeExpenseTypeUpdateResource.getExpenseTypeList()) {
				
				ExpenseType expenseType = validateExpenseType(validateService.stringToLong(expenseTypeResource.getExpenseTypeId()), expenseTypeResource.getExpenseTypeName(), index);
				
				saveOtherIncomeExpenseTypeDetail(expenseTypeResource.getId(),tenantId,expenseType,expenseTypeResource.getStatus(),otherIncomeCategory,index);
				index++;
				

			}
		}else
			throw new ValidateRecordException(environment.getProperty(COMMON_NOT_NULL),"expenseTypeList");
		
	}
	
	private OtherIncomeCategory validateOtherIncomeCategory(Long id, String name) {
		
		Optional<OtherIncomeCategory> isPresentOtherIncomeCategory = otherIncomeCategoryRepository.findByIdAndNameAndStatus(id, name, CommonStatus.ACTIVE);
		
		if(!isPresentOtherIncomeCategory.isPresent())
			throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), "otherIncomeCategoryId");
		
		return isPresentOtherIncomeCategory.get();
	}
	
	private ExpenseType validateExpenseType(Long id, String name, Integer index) {
		
		Optional<ExpenseType> isPresentExpenseType = expenseTypeRepository.findByIdAndNameAndStatus(id, name, CommonStatus.ACTIVE.toString());
		
		if(!isPresentExpenseType.isPresent())
			throw new DetailListValidateException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.EXPENSE_TYPE_ID, ServicePoint.EXPENCE_TYPE_OTHER_INCOME_CATEGORY, index);
		
		return isPresentExpenseType.get();
	}
	
	private OtherIncomeExpenseType setOtherIncomeExpenseTypeDetails(OtherIncomeExpenseType otherIncomeExpenseType) {		
		Optional<OtherIncomeCategory> otherIncomeCategory = otherIncomeCategoryRepository.findById(otherIncomeExpenseType.getOtherIncomeCategory().getId());
		List<OtherIncomeExpenseTypeDetail> otherIncomeExpenseTypeDetailList = otherIncomeExpenseTypeDetailRepository.findByOtherIncomeExpenseTypeId(otherIncomeExpenseType.getId());
		
		if(otherIncomeCategory.isPresent()) {
			
//			otherIncomeExpenseType.setOtherIncomeCatId(otherIncomeCategory.get().getId());
//			otherIncomeExpenseType.setOtherIncomeCatName(otherIncomeCategory.get().getName());
		}
		
		if(!otherIncomeExpenseTypeDetailList.isEmpty()) {		
			
			for(OtherIncomeExpenseTypeDetail otherIncomeExpenseTypeDetail : otherIncomeExpenseTypeDetailList) {
				Optional<ExpenseType> expenseType = expenseTypeRepository.findById(otherIncomeExpenseTypeDetail.getExpenseType().getId());
				
				if(expenseType.isPresent()) {
					otherIncomeExpenseTypeDetail.setExpenseTypesId(expenseType.get().getId());
					otherIncomeExpenseTypeDetail.setExpenseTypesName(expenseType.get().getName());
				}
			}
			//otherIncomeExpenseType.setOtherIncomeExpenseTypeDetailList(otherIncomeExpenseTypeDetailList);
		}
		
		return otherIncomeExpenseType;
		
	}
	
	private void saveOtherIncomeExpenseTypeDetail(String id,String tenantId, ExpenseType expenseType, String status, OtherIncomeCategory otherIncomeCategory, Integer index) {
		
		OtherIncomeExpenseType otherIncomeExpenseType = new OtherIncomeExpenseType();
		
		if(id != null && !id.isEmpty()) {
			
			Optional<OtherIncomeExpenseType> isPresentOtherIncomeExpenseType = otherIncomeExpenseTypeRepository.findById(validateService.stringToLong(id));
			
			if(!isPresentOtherIncomeExpenseType.isPresent()) {
				throw new DetailListValidateException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.EXPENCE_TYPE_OTHER_INCOME_CATEGORY_DET_ID, ServicePoint.EXPENCE_TYPE_OTHER_INCOME_CATEGORY, index);
			} else {
				
				Optional<OtherIncomeExpenseType> isPresentOtherIncomeExpenseTypeOpt = otherIncomeExpenseTypeRepository.findByOtherIncomeCategoryAndExpenseTypeAndIdNot(otherIncomeCategory, expenseType, validateService.stringToLong(id));
				if(isPresentOtherIncomeExpenseTypeOpt.isPresent()) {
					throw new ValidateRecordException(environment.getProperty("category.expense-duplicate"), "catExpDuplicate");
				}
				
				otherIncomeExpenseType = isPresentOtherIncomeExpenseType.get();
				otherIncomeExpenseType.setModifiedDate(validateService.getCreateOrModifyDate());
				otherIncomeExpenseType.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
			}
		}else {
			
			Optional<OtherIncomeExpenseType> isPresentOtherIncomeExpenseType = otherIncomeExpenseTypeRepository.findByOtherIncomeCategoryAndExpenseType(otherIncomeCategory,expenseType);
			if(isPresentOtherIncomeExpenseType.isPresent()) {
				throw new ValidateRecordException(environment.getProperty("category.expense-duplicate"), "catExpDuplicate");
			}
			
			otherIncomeExpenseType.setTenantId(tenantId);
			otherIncomeExpenseType.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
			otherIncomeExpenseType.setCreatedDate(validateService.getCreateOrModifyDate());
		}
		
		
		
		otherIncomeExpenseType.setOtherIncomeCategory(otherIncomeCategory);
		otherIncomeExpenseType.setStatus(CommonStatus.valueOf(status));
		otherIncomeExpenseType.setExpenseType(expenseType);
		otherIncomeExpenseType.setSyncTs(validateService.getCreateOrModifyDate());
		
		otherIncomeExpenseTypeRepository.saveAndFlush(otherIncomeExpenseType);
		
	}
}
