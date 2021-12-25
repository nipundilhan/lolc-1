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
import com.fusionx.lending.origination.domain.DelegationAuthorityGroup;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.repository.DelegationAuthorityGroupRepository;
import com.fusionx.lending.origination.resource.DelegationAuthorityGroupAddResource;
import com.fusionx.lending.origination.resource.DelegationAuthorityGroupUpdateResource;
import com.fusionx.lending.origination.service.DelegationAuthorityGroupService;


/**
 * Delegation Authority Group Service Implementation
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
public class DelegationAuthorityGroupServiceImpl implements DelegationAuthorityGroupService {

	@Autowired
	private Environment environment;
	
	@Autowired
	private DelegationAuthorityGroupRepository dAGroupRepository;
	
//	@Override
//	public Page<DelegationAuthorityGroup> findAll(Pageable pageable) {
//		return dAGroupRepository.findAll(pageable);
//	}

	@Override
	public Optional<DelegationAuthorityGroup> findById(Long id) {
		Optional<DelegationAuthorityGroup> DelegationAuthorityGroup = dAGroupRepository.findById(id);
		if (DelegationAuthorityGroup.isPresent()) {
			return Optional.ofNullable(DelegationAuthorityGroup.get());
		} else {
			return Optional.empty();
		}
	}

	@Override
	public List<DelegationAuthorityGroup> findByStatus(String status) {
		return dAGroupRepository.findByStatus(status);
	}

	@Override
	public List<DelegationAuthorityGroup> findByName(String name) {
		return dAGroupRepository.findByNameContaining(name);
	}

	@Override
	public Optional<DelegationAuthorityGroup> findByCode(String code) {
		Optional<DelegationAuthorityGroup> DelegationAuthorityGroup = dAGroupRepository.findByCode(code);
		if (DelegationAuthorityGroup.isPresent()) {
			return Optional.ofNullable(DelegationAuthorityGroup.get());
		} else {
			return Optional.empty();
		}
	}

	@Override
	public Boolean existsById(Long id) {
		return dAGroupRepository.existsById(id);
	}

	@Override
	public List<DelegationAuthorityGroup> findAll() {
		return dAGroupRepository.findAll();
	}

	@Override
	public Long saveAndValidateDelegationAuthorityGroup(String tenantId, String createdUser, DelegationAuthorityGroupAddResource delegationAuthorityGroupAddResource) {
		
		LoggerRequest.getInstance().logInfo("DelegationAuthorityGroup********************************Validate Code*********************************************");
		if(dAGroupRepository.existsByCodeAndStatus(delegationAuthorityGroupAddResource.getCode(), CommonStatus.ACTIVE.toString()))
			throw new ValidateRecordException(environment.getProperty("common.unique"), "code");
		
		LoggerRequest.getInstance().logInfo("DelegationAuthorityGroup********************************Validate Order*********************************************");
		if(dAGroupRepository.existsByDaOrderAndStatus(Long.parseLong(delegationAuthorityGroupAddResource.getOrder()), CommonStatus.ACTIVE.toString()))
			throw new ValidateRecordException(environment.getProperty("common.unique"), "order");
		
		LoggerRequest.getInstance().logInfo("DelegationAuthorityGroup********************************Save Expense Type*********************************************");
		DelegationAuthorityGroup delegationAuthorityGroup=saveDelegationAuthorityGroup(tenantId, createdUser, delegationAuthorityGroupAddResource);
		
		return delegationAuthorityGroup.getId();
	}

	private DelegationAuthorityGroup saveDelegationAuthorityGroup(String tenantId, String createdUser, DelegationAuthorityGroupAddResource delegationAuthorityGroupAddResource) {
		DelegationAuthorityGroup dAGroup = new DelegationAuthorityGroup();
		dAGroup.setTenantId(tenantId);
		dAGroup.setCode(delegationAuthorityGroupAddResource.getCode());
		dAGroup.setName(delegationAuthorityGroupAddResource.getName());
		dAGroup.setDescription(delegationAuthorityGroupAddResource.getDescription());
		dAGroup.setStatus(delegationAuthorityGroupAddResource.getStatus());
		if(delegationAuthorityGroupAddResource.getNoOfMinUsersToBeApproved()!=null) {
			dAGroup.setNoOfMinUsersToBeApproved(Long.parseLong(delegationAuthorityGroupAddResource.getNoOfMinUsersToBeApproved()));
		}
		dAGroup.setDaOrder(Long.parseLong(delegationAuthorityGroupAddResource.getOrder()));
		dAGroup.setCreatedUser(createdUser);
		dAGroup.setCreatedDate(getCreateOrModifyDate());
		dAGroup.setSyncTs(getCreateOrModifyDate());
		dAGroup = dAGroupRepository.saveAndFlush(dAGroup);
		
		return dAGroup;
	}
	
	@Override
	public Long updateAndValidateDelegationAuthorityGroup(String tenantId, String createdUser, Long id,
			DelegationAuthorityGroupUpdateResource delegationAuthorityGroupUpdateResource) {
		
		LoggerRequest.getInstance().logInfo("ApprovalCategory********************************Validate Code*********************************************");
		Optional<DelegationAuthorityGroup> isPresentApprovalCategoryByCode = dAGroupRepository.findByCodeAndIdNotIn(delegationAuthorityGroupUpdateResource.getCode(), id);
		if (isPresentApprovalCategoryByCode.isPresent())
			throw new ValidateRecordException(environment.getProperty("common.unique"), "code");
		
		LoggerRequest.getInstance().logInfo("ApprovalCategory********************************Validate Order*********************************************");
		Optional<DelegationAuthorityGroup> isPresentApprovalCategoryByOrder = dAGroupRepository.findByDaOrderAndIdNotIn(Long.parseLong(delegationAuthorityGroupUpdateResource.getOrder()), id);
		if (isPresentApprovalCategoryByOrder.isPresent())
			throw new ValidateRecordException(environment.getProperty("common.unique"), "order");
		
		LoggerRequest.getInstance().logInfo("ApprovalCategory********************************Update Expense Type*********************************************");
		DelegationAuthorityGroup delegationAuthorityGroup=updatedelegationAuthorityGroup(createdUser, delegationAuthorityGroupUpdateResource, id);
		
		return delegationAuthorityGroup.getId();
	}
	
	private DelegationAuthorityGroup updatedelegationAuthorityGroup(String createdUser, DelegationAuthorityGroupUpdateResource delegationAuthorityGroupUpdateResource, Long id) {
		DelegationAuthorityGroup dAuthorityGroup = dAGroupRepository.getOne(id);

		LoggerRequest.getInstance().logInfo("ApprovalCategory********************************Validate Version*********************************************");
		if(!dAuthorityGroup.getVersion().equals(Long.parseLong(delegationAuthorityGroupUpdateResource.getVersion())))
			throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "version");
			
		dAuthorityGroup.setCode(delegationAuthorityGroupUpdateResource.getCode());
		dAuthorityGroup.setName(delegationAuthorityGroupUpdateResource.getName());
		dAuthorityGroup.setDescription(delegationAuthorityGroupUpdateResource.getDescription());
		dAuthorityGroup.setStatus(delegationAuthorityGroupUpdateResource.getStatus());
		if(delegationAuthorityGroupUpdateResource.getNoOfMinUsersToBeApproved()!=null && !delegationAuthorityGroupUpdateResource.getNoOfMinUsersToBeApproved().isEmpty()) {
			dAuthorityGroup.setNoOfMinUsersToBeApproved(Long.parseLong(delegationAuthorityGroupUpdateResource.getNoOfMinUsersToBeApproved()));
		}
		dAuthorityGroup.setDaOrder(Long.parseLong(delegationAuthorityGroupUpdateResource.getOrder()));
		dAuthorityGroup.setModifiedUser(createdUser);
		dAuthorityGroup.setModifiedDate(getCreateOrModifyDate());
		dAuthorityGroup.setSyncTs(getCreateOrModifyDate());
		dAuthorityGroup=dAGroupRepository.saveAndFlush(dAuthorityGroup);
		
		return dAuthorityGroup;
	}

	private Timestamp getCreateOrModifyDate() {
		Calendar calendar = Calendar.getInstance();
    	java.util.Date now = calendar.getTime();
    	return new Timestamp(now.getTime());
	}

	@Override
	public Page<DelegationAuthorityGroup> searchDelegationAuthorityGroup(String searchq, String name, String code, Pageable pageable) {
		if(searchq==null || searchq.isEmpty())
			searchq=null;
		if(name==null || name.isEmpty())
			name=null;
		if(code==null || code.isEmpty())
			code=null;
		
		return dAGroupRepository.searchDelegationAuthorityGroup(searchq, name, code, pageable);
	}

}