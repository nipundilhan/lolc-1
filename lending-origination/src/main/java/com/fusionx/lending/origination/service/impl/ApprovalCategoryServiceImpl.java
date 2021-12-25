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
import com.fusionx.lending.origination.domain.ApprovalCategory;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.repository.ApprovalCategoryRepository;
import com.fusionx.lending.origination.resource.ApprovalCategoryAddResource;
import com.fusionx.lending.origination.resource.ApprovalCategoryUpdateResource;
import com.fusionx.lending.origination.service.ApprovalCategoryService;


/**
 * Approval Category Service Implementation
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   20-04-2021      		            	VenukiR      Created
 *    
 ********************************************************************************************************
 */

@Component
@Transactional(rollbackFor = Exception.class)
public class ApprovalCategoryServiceImpl implements ApprovalCategoryService {

	@Autowired
	private Environment environment;
	
	@Autowired
	private ApprovalCategoryRepository approvalCategoryRepository;
	
//	@Override
//	public Page<ApprovalCategory> findAll(Pageable pageable) {
//		return approvalCategoryRepository.findAll(pageable);
//	}

	@Override
	public Optional<ApprovalCategory> findById(Long id) {
		Optional<ApprovalCategory> ApprovalCategory = approvalCategoryRepository.findById(id);
		if (ApprovalCategory.isPresent()) {
			return Optional.ofNullable(ApprovalCategory.get());
		} else {
			return Optional.empty();
		}
	}

	@Override
	public List<ApprovalCategory> findByStatus(String status) {
		return approvalCategoryRepository.findByStatus(status);
	}

	@Override
	public List<ApprovalCategory> findByName(String name) {
		return approvalCategoryRepository.findByNameContaining(name);
	}

	@Override
	public Optional<ApprovalCategory> findByCode(String code) {
		Optional<ApprovalCategory> ApprovalCategory = approvalCategoryRepository.findByCode(code);
		if (ApprovalCategory.isPresent()) {
			return Optional.ofNullable(ApprovalCategory.get());
		} else {
			return Optional.empty();
		}
	}

	@Override
	public Boolean existsById(Long id) {
		return approvalCategoryRepository.existsById(id);
	}

	@Override
	public List<ApprovalCategory> findAll() {
		return approvalCategoryRepository.findAll();
	}

	@Override
	public Long saveAndValidateApprovalCategory(String tenantId, String createdUser, ApprovalCategoryAddResource ApprovalCategoryAddResource) {
		
		LoggerRequest.getInstance().logInfo("ApprovalCategory******************************** Validate Code *********************************************");
		if(approvalCategoryRepository.existsByCodeAndStatus(ApprovalCategoryAddResource.getCode(), CommonStatus.ACTIVE.toString()))
			throw new ValidateRecordException(environment.getProperty("common.unique"), "code");
		
		LoggerRequest.getInstance().logInfo("ApprovalCategory******************************** Save Expense Type *****************************************");
		ApprovalCategory ApprovalCategory=saveApprovalCategory(tenantId, createdUser, ApprovalCategoryAddResource);
		
		return ApprovalCategory.getId();
	}

	private ApprovalCategory saveApprovalCategory(String tenantId, String createdUser, ApprovalCategoryAddResource approvalCategoryAddResource) {
		ApprovalCategory approvalCategory = new ApprovalCategory();
		approvalCategory.setTenantId(tenantId);
		approvalCategory.setCode(approvalCategoryAddResource.getCode());
		approvalCategory.setName(approvalCategoryAddResource.getName());
		approvalCategory.setDescription(approvalCategoryAddResource.getDescription());
		approvalCategory.setStatus(approvalCategoryAddResource.getStatus());
		approvalCategory.setCreatedUser(createdUser);
		approvalCategory.setCreatedDate(getCreateOrModifyDate());
		approvalCategory.setSyncTs(getCreateOrModifyDate());
		approvalCategory = approvalCategoryRepository.saveAndFlush(approvalCategory);
		
		return approvalCategory;
	}
	
	@Override
	public Long updateAndValidateApprovalCategory(String tenantId, String createdUser, Long id,
			ApprovalCategoryUpdateResource approvalCategoryUpdateResource) {
		
//		LoggerRequest.getInstance().logInfo("ApprovalCategory********************************Validate Code*********************************************");
//		Optional<ApprovalCategory> isPresentApprovalCategoryByCode = approvalCategoryRepository.findByCodeAndIdNotIn(approvalCategoryUpdateResource.getCode(), id);
//		if (isPresentApprovalCategoryByCode.isPresent())
//			throw new ValidateRecordException(environment.getProperty("common.unique"), "code");
		
		LoggerRequest.getInstance().logInfo("ApprovalCategory********************************Update Expense Type*********************************************");
		ApprovalCategory approvalCategory=updateApprovalCategory(createdUser, approvalCategoryUpdateResource, id);
		
		return approvalCategory.getId();
	}
	
	private ApprovalCategory updateApprovalCategory(String createdUser, ApprovalCategoryUpdateResource approvalCategoryUpdateResource, Long id) {
		ApprovalCategory approvalCategory = approvalCategoryRepository.getOne(id);
		
		LoggerRequest.getInstance().logInfo("ApprovalCategory********************************Validate Version*********************************************");
		if(!approvalCategory.getVersion().equals(Long.parseLong(approvalCategoryUpdateResource.getVersion())))
			throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "version");
		
		LoggerRequest.getInstance().logInfo("************************** Code Can Not Update *****************************");
        if (!approvalCategory.getCode().equals(approvalCategoryUpdateResource.getCode())) {
            throw new ValidateRecordException(environment.getProperty("common.code-update"), "code");
        }
		
		approvalCategory.setCode(approvalCategoryUpdateResource.getCode());
		approvalCategory.setName(approvalCategoryUpdateResource.getName());
		approvalCategory.setDescription(approvalCategoryUpdateResource.getDescription());
		approvalCategory.setStatus(approvalCategoryUpdateResource.getStatus());
		approvalCategory.setModifiedUser(createdUser);
		approvalCategory.setModifiedDate(getCreateOrModifyDate());
		approvalCategory.setSyncTs(getCreateOrModifyDate());
		approvalCategory=approvalCategoryRepository.saveAndFlush(approvalCategory);
		
		return approvalCategory;
	}

	private Timestamp getCreateOrModifyDate() {
		Calendar calendar = Calendar.getInstance();
    	java.util.Date now = calendar.getTime();
    	return new Timestamp(now.getTime());
	}

	@Override
	public Page<ApprovalCategory> searchApprovalCategory(String searchq, String name, String code, Pageable pageable) {
		if(searchq==null || searchq.isEmpty())
			searchq=null;
		if(name==null || name.isEmpty())
			name=null;
		if(code==null || code.isEmpty())
			code=null;
		
		return approvalCategoryRepository.searchApprovalCategory(searchq, name, code, pageable);
	}

}