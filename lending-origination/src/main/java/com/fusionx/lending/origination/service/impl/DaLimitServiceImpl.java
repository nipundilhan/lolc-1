package com.fusionx.lending.origination.service.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.origination.core.LoggerRequest;
import com.fusionx.lending.origination.domain.ApprovalCategory;
import com.fusionx.lending.origination.domain.DaLimit;
import com.fusionx.lending.origination.domain.DelegationAuthorityGroup;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.repository.ApprovalCategoryRepository;
import com.fusionx.lending.origination.repository.DaLimitRepository;
import com.fusionx.lending.origination.repository.DelegationAuthorityGroupRepository;
import com.fusionx.lending.origination.resource.DaLimitAddResource;
import com.fusionx.lending.origination.resource.DaLimitUpdateResource;
import com.fusionx.lending.origination.service.DaLimitService;

/**
 * DA Limit Service Implementation
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   1   06-05-2021      		     FX-6269	Amal       Created
 *    
 ********************************************************************************************************
 */

@Component
@Transactional(rollbackFor = Exception.class)
public class DaLimitServiceImpl implements DaLimitService {

	@Autowired
	private Environment environment;
	
	@Autowired
	private DaLimitRepository daLimitRepository;
	
	@Autowired
	private ApprovalCategoryRepository approvalCategoryRepository;
	
	@Autowired
	private DelegationAuthorityGroupRepository dAGroupRepository;
	
	/*@Override
	public Page<?> findAll(Pageable pageable, Predicate predicate) {
		
		BooleanBuilder bb = new BooleanBuilder(predicate);

		return 	daLimitRepository.findAll(bb.getValue(), pageable);
	}*/
	
	public List<DaLimit> findAll() {
		   List<DaLimit> daLimitList= new ArrayList<>();
		   List<DaLimit> daLimits = daLimitRepository.findAll();
		   for (DaLimit daLimit :daLimits) {
			   daLimit.setApprovalCatId(daLimit.getApprovalCategory().getId().toString());
			   daLimit.setApprovalCatName(daLimit.getApprovalCategory().getName());
			   daLimit.setAuthorityGroupId(daLimit.getDelegationAuthorityGroup().getId().toString());
			   daLimit.setAuthorityGroupCode(daLimit.getDelegationAuthorityGroup().getCode());
			   daLimit.setAuthorityGroupName(daLimit.getDelegationAuthorityGroup().getName());
			   daLimitList.add(daLimit);
		   }
		return daLimitList;
	}

	@Override
	public Optional<DaLimit> findById(Long id) {
		Optional<DaLimit> daLimit = daLimitRepository.findById(id);
		if (daLimit.isPresent()) {
			daLimit.get().setApprovalCatId(daLimit.get().getApprovalCategory().getId().toString());
			daLimit.get().setApprovalCatName(daLimit.get().getApprovalCategory().getName());
			daLimit.get().setAuthorityGroupId(daLimit.get().getDelegationAuthorityGroup().getId().toString());
			daLimit.get().setAuthorityGroupCode(daLimit.get().getDelegationAuthorityGroup().getCode());
			daLimit.get().setAuthorityGroupName(daLimit.get().getDelegationAuthorityGroup().getName());
			return Optional.ofNullable(daLimit.get());
		} else {
			return Optional.empty();
		}
	}

	@Override
	public List<DaLimit> findByStatus(String status) {
		   List<DaLimit> daLimitList= new ArrayList<>();
		   List<DaLimit> daLimits = daLimitRepository.findByStatus(status);
		   for (DaLimit daLimit :daLimits) {
			   daLimit.setApprovalCatId(daLimit.getApprovalCategory().getId().toString());
			   daLimit.setApprovalCatName(daLimit.getApprovalCategory().getName());
			   daLimit.setAuthorityGroupId(daLimit.getDelegationAuthorityGroup().getId().toString());
			   daLimit.setAuthorityGroupCode(daLimit.getDelegationAuthorityGroup().getCode());
			   daLimit.setAuthorityGroupName(daLimit.getDelegationAuthorityGroup().getName());
			   daLimitList.add(daLimit);
		   }
		return daLimitList;
	}


	@Override
	public Boolean existsById(Long id) {
		return daLimitRepository.existsById(id);
	}

	@Override
	public Long saveAndValidateDaLimit(String tenantId, String createdUser, DaLimitAddResource daLimitAddResource) {
		
		LoggerRequest.getInstance().logInfo("DaLimit********************************Validate Unique record*********************************************");
	
		Boolean isExists = daLimitRepository.existsByDelegationAuthorityGroupIdAndDaLevelAndApprovalCategoryIdAndStatus(Long.parseLong(daLimitAddResource.getAuthorityGroupId()),
				daLimitAddResource.getDaLevel(),Long.parseLong(daLimitAddResource.getApprovalCategoryId()),"ACTIVE");
		
		if(isExists!=null && isExists) {
			throw new ValidateRecordException(environment.getProperty("common.unique"), "message");
		}
	
		LoggerRequest.getInstance().logInfo("DaLimit********************************Save DA Limit *********************************************");
		
		DaLimit daLimit=saveDaLimit(tenantId, createdUser, daLimitAddResource);
		
		return daLimit.getId();
	}
	
	private DaLimit saveDaLimit(String tenantId, String createdUser, DaLimitAddResource daLimitAddResource) {
	
		DaLimit daLimit = new DaLimit();
		
		Optional<ApprovalCategory> approvalCategory = approvalCategoryRepository.findById(Long.parseLong(daLimitAddResource.getApprovalCategoryId()));
		
		if (approvalCategory.isPresent()) {
			if(!approvalCategory.get().getName().equals(daLimitAddResource.getApprovalCategoryName())) {
				throw new ValidateRecordException(environment.getProperty("invalid.approvalCategory"), "approvalCategoryName");
			}else {
				daLimit.setApprovalCategory(approvalCategory.get());
			}
			
		}else {
			throw new ValidateRecordException(environment.getProperty("invalid.approvalCategoryId"), "approvalCategoryId");
		}
		
	   Optional<DelegationAuthorityGroup> dAGroup = dAGroupRepository.findById(Long.parseLong(daLimitAddResource.getAuthorityGroupId()));
		
		if (dAGroup.isPresent()) {
			if(!dAGroup.get().getName().equals(daLimitAddResource.getAuthorityGroupName())) {
				throw new ValidateRecordException(environment.getProperty("invalid.authorityGroup"), "authorityGroupName");
			}else {
				daLimit.setDelegationAuthorityGroup(dAGroup.get());
			}
			
		}else {
			throw new ValidateRecordException(environment.getProperty("invalid.authorityGroupId"), "authorityGroupId");
		}
		
		
		daLimit.setDaLimitValue(stringToBigDecimal(daLimitAddResource.getDaLimitValue()));
		daLimit.setDaLevel(daLimitAddResource.getDaLevel());
		daLimit.setTenantId(tenantId);
		daLimit.setStatus(daLimitAddResource.getStatus());
		daLimit.setCreatedUser(createdUser);
		daLimit.setCreatedDate(getCreateOrModifyDate());
		daLimit.setSyncTs(getCreateOrModifyDate());
		daLimit = daLimitRepository.saveAndFlush(daLimit);
		
		return daLimit;
	}
	
	@Override
	public Long updateAndValidateDaLimit(String tenantId, String createdUser, Long id, DaLimitUpdateResource daLimitUpdateResource) {
		
		LoggerRequest.getInstance().logInfo("DaLimit********************************Validate Version*********************************************");
		Optional<DaLimit> isDaLimite = daLimitRepository.findById(id);
		
		if(isDaLimite.isPresent() && !isDaLimite.get().getVersion().equals(Long.parseLong(daLimitUpdateResource.getVersion())))
			throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "version");
		
		LoggerRequest.getInstance().logInfo("DaLimit********************************Validate Unique*********************************************");

		Boolean isExists = daLimitRepository.existsByDelegationAuthorityGroupIdAndDaLevelAndApprovalCategoryIdAndStatusAndIdNotIn(Long.parseLong(daLimitUpdateResource.getAuthorityGroupId()),
				daLimitUpdateResource.getDaLevel(),Long.parseLong(daLimitUpdateResource.getApprovalCategoryId()),"ACTIVE",id);
		
		if(isExists!=null && isExists) {
			throw new ValidateRecordException(environment.getProperty("common.unique"), "message");
		}
		
		LoggerRequest.getInstance().logInfo("BusinessType********************************Update Business Type*********************************************");
		DaLimit daLimit = updateDaLimit(createdUser, daLimitUpdateResource, id);
		
		return daLimit.getId();
	}
	
	private DaLimit updateDaLimit(String createdUser,DaLimitUpdateResource daLimitUpdateResource, Long id) {
		
		DaLimit daLimit = daLimitRepository.getOne(id);

		Optional<ApprovalCategory> approvalCategory = approvalCategoryRepository.findById(Long.parseLong(daLimitUpdateResource.getApprovalCategoryId()));
		
		if (approvalCategory.isPresent()) {
			if(!approvalCategory.get().getName().equals(daLimitUpdateResource.getApprovalCategoryName())) {
				throw new ValidateRecordException(environment.getProperty("invalid.approvalCategory"), "approvalCategoryName");
			}else {
				daLimit.setApprovalCategory(approvalCategory.get());
			}
			
		}else {
			throw new ValidateRecordException(environment.getProperty("invalid.approvalCategoryId"), "approvalCategoryId");
		}
		
	   Optional<DelegationAuthorityGroup> dAGroup = dAGroupRepository.findById(Long.parseLong(daLimitUpdateResource.getAuthorityGroupId()));
		
		if (dAGroup.isPresent()) {
			if(!dAGroup.get().getName().equals(daLimitUpdateResource.getAuthorityGroupName())) {
				throw new ValidateRecordException(environment.getProperty("invalid.authorityGroup"), "authorityGroupName");
			}else {
				daLimit.setDelegationAuthorityGroup(dAGroup.get());
			}
			
		}else {
			throw new ValidateRecordException(environment.getProperty("invalid.authorityGroupId"), "authorityGroupId");
		}
		
		
		daLimit.setDaLimitValue(stringToBigDecimal(daLimitUpdateResource.getDaLimitValue()));
		daLimit.setDaLevel(daLimitUpdateResource.getDaLevel());
		daLimit.setStatus(daLimitUpdateResource.getStatus());
		daLimit.setSyncTs(getCreateOrModifyDate());
		daLimit.setModifiedUser(createdUser);
		daLimit.setModifiedDate(getCreateOrModifyDate());
		daLimit.setSyncTs(getCreateOrModifyDate());
		daLimit=daLimitRepository.saveAndFlush(daLimit);
		
		return daLimit;
	}

	private Timestamp getCreateOrModifyDate() {
		Calendar calendar = Calendar.getInstance();
    	java.util.Date now = calendar.getTime();
    	return new Timestamp(now.getTime());
	}

	private BigDecimal stringToBigDecimal(String value) {
		if (value != null) {
			return new BigDecimal(value);
		} else {
			return BigDecimal.valueOf(0.0);
		}
	}

	@Override
	public List<DaLimit> findByDelegationAuthorityGroupIdAndDaLevelAndApprovalCategoryId(Long groupId, String level,
			Long categoryId) {
		
		   List<DaLimit> daLimitList= new ArrayList<>();
		   List<DaLimit> daLimits = daLimitRepository.findByDelegationAuthorityGroupIdAndDaLevelAndApprovalCategoryId(groupId, level, categoryId);
		   for (DaLimit daLimit :daLimits) {
			   daLimit.setApprovalCatId(daLimit.getApprovalCategory().getId().toString());
			   daLimit.setApprovalCatName(daLimit.getApprovalCategory().getName());
			   daLimit.setAuthorityGroupId(daLimit.getDelegationAuthorityGroup().getId().toString());
			   daLimit.setAuthorityGroupCode(daLimit.getDelegationAuthorityGroup().getCode());
			   daLimit.setAuthorityGroupName(daLimit.getDelegationAuthorityGroup().getName());
			   daLimitList.add(daLimit);
		   }
		return daLimitList;
	}

}
