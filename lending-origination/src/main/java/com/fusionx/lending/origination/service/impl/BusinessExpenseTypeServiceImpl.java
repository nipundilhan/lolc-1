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
import com.fusionx.lending.origination.domain.BusinessExpenseType;
import com.fusionx.lending.origination.domain.BusinessType;
import com.fusionx.lending.origination.domain.ExpenseType;
import com.fusionx.lending.origination.enums.ActionType;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.enums.ServiceEntity;
import com.fusionx.lending.origination.enums.TransferType;
import com.fusionx.lending.origination.exception.InvalidDetailListServiceIdException;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.repository.BusinessExpenseTypeRepository;
import com.fusionx.lending.origination.repository.BusinessTypeRepository;
import com.fusionx.lending.origination.repository.ExpenseTypeRepository;
import com.fusionx.lending.origination.resource.BusinessExpenseTypeAddResource;
import com.fusionx.lending.origination.resource.BusinessExpenseTypeDetailResource;
import com.fusionx.lending.origination.resource.BusinessExpenseTypeUpdateResource;
import com.fusionx.lending.origination.service.BusinessExpenseTypeService;


/**
 * Business Expense Type Service Implementation
 * 
 *******************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *------------------------------------------------------------------------------------------------------
 *    1   26-12-2020      		     FX-5271	MiyuruW      Created
 *    
 *******************************************************************************************************
 */

@Component
@Transactional(rollbackFor = Exception.class)
public class BusinessExpenseTypeServiceImpl implements BusinessExpenseTypeService {

	@Autowired
	private Environment environment;
	
	@Autowired
	private BusinessExpenseTypeRepository businessExpenseTypeRepository;
	
	@Autowired
	private BusinessTypeRepository businessTypeRepository;
	
	@Autowired
	private ExpenseTypeRepository expenseTypeRepository;
	
	@Override
	public List<BusinessExpenseType> findAll() {
		return businessExpenseTypeRepository.findAll();
	}

	@Override
	public Optional<BusinessExpenseType> findById(Long id) {
		Optional<BusinessExpenseType> businessExpenseType = businessExpenseTypeRepository.findById(id);
		if (businessExpenseType.isPresent()) {
			return Optional.ofNullable(businessExpenseType.get());
		} else {
			return Optional.empty();
		}
	}

	@Override
	public List<BusinessExpenseType> findByStatus(String status) {
		return businessExpenseTypeRepository.findByStatus(status);
	}
	
	@Override
	public List<BusinessExpenseType> findByBusinessTypeId(Long businessTypeId) {
		return businessExpenseTypeRepository.findByBusinessTypeId(businessTypeId);
	}

	@Override
	public Boolean existsById(Long id) {
		return businessExpenseTypeRepository.existsById(id);
	}

	@Override
	public Boolean saveAndValidateBusinessExpenseType(String tenantId, String createdUser, BusinessExpenseTypeAddResource businessExpenseTypeAddResource) {
		
		LoggerRequest.getInstance().logInfo("BusinessExpenseType********************************Validate Business Type*********************************************");
		BusinessType businessType = setBusinessTypeAndValidate(businessExpenseTypeAddResource, null, ActionType.SAVE, 0);
		
		if(businessExpenseTypeAddResource.getExpenseTypesList()!=null && !businessExpenseTypeAddResource.getExpenseTypesList().isEmpty()) {
			Integer index=0;
			ExpenseType expenseType = null;
			
			for(BusinessExpenseTypeDetailResource businessExpenseTypeDetailResource : businessExpenseTypeAddResource.getExpenseTypesList()) {
				
				LoggerRequest.getInstance().logInfo("BusinessExpenseType********************************Validate Expense Type*********************************************");
				expenseType = setExpenseTypeAndValidate(businessExpenseTypeDetailResource, null, ActionType.SAVE, index);
				
				LoggerRequest.getInstance().logInfo("BusinessExpenseType********************************Validate Business Expense Type Duplicate*********************************************");
				if(businessExpenseTypeRepository.existsByBusinessTypeIdAndExpenseTypeIdAndStatus(Long.parseLong(businessExpenseTypeAddResource.getBusinessTypesId()), Long.parseLong(businessExpenseTypeDetailResource.getExpenseTypesId()), CommonStatus.ACTIVE.toString()))
					throw new InvalidDetailListServiceIdException(environment.getProperty("businessExpenseType.unique"), ServiceEntity.BUSINESS_EXPENSE_TYPE_ID, TransferType.BUSINESS_EXPENSE_TYPE_MAPPING, index);
				
				LoggerRequest.getInstance().logInfo("BusinessExpenseType********************************Save Business Expense Type*********************************************");
				saveBusinessExpenseType(tenantId, createdUser, businessType, expenseType, businessExpenseTypeAddResource, businessExpenseTypeDetailResource);
				
				index++;
			}			
		}
				
		return Boolean.TRUE;
	}
	
	private BusinessType setBusinessTypeAndValidate(BusinessExpenseTypeAddResource businessExpenseTypeAddResource, BusinessExpenseTypeUpdateResource businessExpenseTypeUpdateResource, ActionType actionType, Integer index) {
		BusinessType businessType = null;
		if(actionType.equals(ActionType.SAVE)) {
			if (businessExpenseTypeAddResource.getBusinessTypesId() != null && !businessExpenseTypeAddResource.getBusinessTypesId().isEmpty()) {
				Optional<BusinessType> businessTypeOptional = businessTypeRepository.findByIdAndStatus(Long.parseLong(businessExpenseTypeAddResource.getBusinessTypesId()), CommonStatus.ACTIVE.toString());
				if (!businessTypeOptional.isPresent()) {
					throw new InvalidDetailListServiceIdException(environment.getProperty("common.invalid-value"), ServiceEntity.BUSINESS_TYPE_ID, TransferType.BUSINESS_EXPENSE_TYPE_MAPPING, index);
				} else {
					businessType = businessTypeOptional.get();
				}	
			}
		} else if(actionType.equals(ActionType.UPDATE)) {
			if (businessExpenseTypeUpdateResource.getBusinessTypesId() != null && !businessExpenseTypeUpdateResource.getBusinessTypesId().isEmpty()) {
				Optional<BusinessType> businessTypeOptional = businessTypeRepository.findByIdAndStatus(Long.parseLong(businessExpenseTypeUpdateResource.getBusinessTypesId()), CommonStatus.ACTIVE.toString());
				if (!businessTypeOptional.isPresent()) {
					throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "businessTypesId");
				} else {
					businessType = businessTypeOptional.get();
				}	
			}
		}
		return businessType;
	}
	
	private ExpenseType setExpenseTypeAndValidate(BusinessExpenseTypeDetailResource businessExpenseTypeDetailResource, BusinessExpenseTypeUpdateResource businessExpenseTypeUpdateResource, ActionType actionType, Integer index) {
		ExpenseType expenseType = null;
		if(actionType.equals(ActionType.SAVE)) {
			if (businessExpenseTypeDetailResource.getExpenseTypesId() != null && !businessExpenseTypeDetailResource.getExpenseTypesId().isEmpty()) {
				Optional<ExpenseType> expenseTypeOptional = expenseTypeRepository.findByIdAndStatus(Long.parseLong(businessExpenseTypeDetailResource.getExpenseTypesId()), CommonStatus.ACTIVE.toString());
				if (!expenseTypeOptional.isPresent()) {
					throw new InvalidDetailListServiceIdException(environment.getProperty("common.invalid-value"), ServiceEntity.EXPENSE_TYPE_ID, TransferType.BUSINESS_EXPENSE_TYPE_MAPPING, index);
				} else {
					expenseType = expenseTypeOptional.get();
				}	
			}
		} else if(actionType.equals(ActionType.UPDATE)) {
			if (businessExpenseTypeUpdateResource.getExpenseTypesId() != null && !businessExpenseTypeUpdateResource.getExpenseTypesId().isEmpty()) {
				Optional<ExpenseType> expenseTypeOptional = expenseTypeRepository.findByIdAndStatus(Long.parseLong(businessExpenseTypeUpdateResource.getExpenseTypesId()), CommonStatus.ACTIVE.toString());
				if (!expenseTypeOptional.isPresent()) {
					throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "expenseTypesId");
				} else {
					expenseType = expenseTypeOptional.get();
				}	
			}
		}
		return expenseType;
	}
	
	private void saveBusinessExpenseType(String tenantId, String createdUser, BusinessType businessType, ExpenseType expenseType, BusinessExpenseTypeAddResource businessExpenseTypeAddResource, BusinessExpenseTypeDetailResource businessExpenseTypeDetailResource) {
		
		BusinessExpenseType businessExpenseType = new BusinessExpenseType();
		businessExpenseType.setTenantId(tenantId);
		businessExpenseType.setBusinessType(businessType);
		businessExpenseType.setExpenseType(expenseType);
		businessExpenseType.setStatus(businessExpenseTypeAddResource.getStatus());
		businessExpenseType.setCreatedUser(createdUser);
		businessExpenseType.setCreatedDate(getCreateOrModifyDate());
		businessExpenseType.setSyncTs(getCreateOrModifyDate());
		businessExpenseTypeRepository.saveAndFlush(businessExpenseType);
	}
	
	private Timestamp getCreateOrModifyDate() {
		Calendar calendar = Calendar.getInstance();
    	java.util.Date now = calendar.getTime();
    	return new Timestamp(now.getTime());
	}

	@Override
	public Long updateAndValidateBusinessExpenseType(String tenantId, String createdUser, Long id, BusinessExpenseTypeUpdateResource businessExpenseTypeUpdateResource) {
		
		LoggerRequest.getInstance().logInfo("BusinessExpenseType********************************Validate Version*********************************************");
		Optional<BusinessExpenseType> isPresentBusinessExpenseType = businessExpenseTypeRepository.findById(id);
		if(!isPresentBusinessExpenseType.get().getVersion().equals(Long.parseLong(businessExpenseTypeUpdateResource.getVersion())))
			throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "version");
		
		LoggerRequest.getInstance().logInfo("BusinessExpenseType********************************Validate Business Type*********************************************");
		BusinessType businessType = setBusinessTypeAndValidate(null, businessExpenseTypeUpdateResource, ActionType.UPDATE, 0);
		
		LoggerRequest.getInstance().logInfo("BusinessExpenseType********************************Validate Expense Type*********************************************");
		ExpenseType expenseType = setExpenseTypeAndValidate(null, businessExpenseTypeUpdateResource, ActionType.UPDATE, 0);
		
		LoggerRequest.getInstance().logInfo("BusinessExpenseType********************************Validate Business Expense Type Duplicate*********************************************");		
		Optional<BusinessExpenseType> businessExpenseTypeOptional = businessExpenseTypeRepository.findByBusinessTypeIdAndExpenseTypeIdAndStatusAndIdNotIn(Long.parseLong(businessExpenseTypeUpdateResource.getBusinessTypesId()), Long.parseLong(businessExpenseTypeUpdateResource.getExpenseTypesId()), CommonStatus.ACTIVE.toString(), id);
		if (businessExpenseTypeOptional.isPresent())
			throw new ValidateRecordException(environment.getProperty("businessExpenseType.unique"), "message");
		
		LoggerRequest.getInstance().logInfo("BusinessExpenseType********************************Update Business Expense Type*********************************************");
		BusinessExpenseType businessExpenseType=updateBusinessExpenseType(createdUser, businessType, expenseType, businessExpenseTypeUpdateResource, id);
		
		return businessExpenseType.getId();
	}

	private BusinessExpenseType updateBusinessExpenseType(String createdUser, BusinessType businessType, ExpenseType expenseType, BusinessExpenseTypeUpdateResource businessExpenseTypeUpdateResource, Long id) {
		BusinessExpenseType businessExpenseType = businessExpenseTypeRepository.getOne(id);
		businessExpenseType.setBusinessType(businessType);
		businessExpenseType.setExpenseType(expenseType);
		businessExpenseType.setStatus(businessExpenseTypeUpdateResource.getStatus());
		businessExpenseType.setModifiedUser(createdUser);
		businessExpenseType.setModifiedDate(getCreateOrModifyDate());
		businessExpenseType.setSyncTs(getCreateOrModifyDate());
		businessExpenseType=businessExpenseTypeRepository.saveAndFlush(businessExpenseType);
		
		return businessExpenseType;
	}

/*	@Override
	public Page<BusinessExpenseType> searchBusinessExpenseType(String businessTypeName, String businessTypeCode, Pageable pageable) {
//		if(searchq==null || searchq.isEmpty())
//			searchq=null;
		if(businessTypeName==null || businessTypeName.isEmpty())
			businessTypeName=null;
		if(businessTypeCode==null || businessTypeCode.isEmpty())
			businessTypeCode=null;
		//return businessExpenseTypeRepository.searchBusinessExpenseType(searchq, businessTypeName, businessTypeCode, pageable);
		return businessExpenseTypeRepository.searchBusinessExpenseType(businessTypeName, businessTypeCode, pageable);
	}*/
	
	@Override
    public Page<BusinessExpenseType> searchBusinessExpenseType(String businessTypeName, String businessTypeCode, Pageable pageable) {        
		businessTypeName = (businessTypeName == null || businessTypeName.isEmpty()) ? null : businessTypeName;
		businessTypeCode = (businessTypeCode == null || businessTypeCode.isEmpty()) ? null : businessTypeCode;

        return businessExpenseTypeRepository.searchBusinessExpenseType(businessTypeName, businessTypeCode, pageable);
    }

}
