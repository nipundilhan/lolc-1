package com.fusionx.lending.origination.service.impl;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.origination.base.MessagePropertyBase;
import com.fusionx.lending.origination.core.LoggerRequest;
import com.fusionx.lending.origination.domain.ApprovalCategory;
import com.fusionx.lending.origination.domain.ApprovalLevel;
import com.fusionx.lending.origination.domain.ApprovalLevelCategoryMapping;
import com.fusionx.lending.origination.domain.ApprovalLevelDaGroupMapping;
import com.fusionx.lending.origination.domain.DelegationAuthorityGroup;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.repository.ApprovalLevelCategoryMappingRepository;
import com.fusionx.lending.origination.repository.ApprovalLevelDaGroupMapRepository;
import com.fusionx.lending.origination.repository.ApprovalLevelRepository;
import com.fusionx.lending.origination.repository.DelegationAuthorityGroupRepository;
import com.fusionx.lending.origination.resource.ApprovalLevelCategoryMapResource;
import com.fusionx.lending.origination.resource.ApprovalLevelDaGroupMapResource;
import com.fusionx.lending.origination.resource.UpdateApprovalLevelDAgGroupMapResource;
import com.fusionx.lending.origination.service.ApprovalLevelDaGroupMapService;

@Component
@Transactional(rollbackFor = Exception.class)
public class ApprovalLevelDaGroupMapServiceImpl extends MessagePropertyBase implements ApprovalLevelDaGroupMapService{

	@Autowired
	ApprovalLevelDaGroupMapRepository approvalLevelDaGroupMapRepository;
	
	@Autowired
	ApprovalLevelRepository approvalLevelRepository;
	
	@Autowired
	DelegationAuthorityGroupRepository delegationAuthorityGroupRepository;
	@Override
	public List<ApprovalLevelDaGroupMapping> findAll() {
		// TODO Auto-generated method stub
		return approvalLevelDaGroupMapRepository.findAll();
	}

	@Override
	public Optional<ApprovalLevelDaGroupMapping> findById(Long id) {
		// TODO Auto-generated method stub
		Optional<ApprovalLevelDaGroupMapping> op=approvalLevelDaGroupMapRepository.findById(id);
		Optional<ApprovalLevel> approvalLevel=approvalLevelRepository.findById(op.get().getApprovalLevel().getId());
		Optional<DelegationAuthorityGroup> delegationAuthorityGroup = delegationAuthorityGroupRepository.findById(op.get().getDaGroup().getId());
	//	op.get().setLevel(approvalLevel.get());
	//	op.get().setDelegationAuthorityGroup(delegationAuthorityGroup.get());
		return op;
	}

	@Override
	public List<ApprovalLevelDaGroupMapping> findByStatus(String status) {
		// TODO Auto-generated method stub
		return approvalLevelDaGroupMapRepository.findByStatus(status);
	}

	@Override
	public Long save(String tenantId, String userName,
			@Valid ApprovalLevelDaGroupMapResource approvalLevelCategoryMapResource) {
		ApprovalLevelDaGroupMapping approvalLevelDaGroupMaping=new ApprovalLevelDaGroupMapping();
		Optional<ApprovalLevel> approvalLevel=approvalLevelRepository.findByIdAndNameAndStatus(Long.parseLong(approvalLevelCategoryMapResource.getApprovalLevelId()),approvalLevelCategoryMapResource.getApprovalLevel(),"ACTIVE");
		if(!approvalLevel.isPresent()) {
			throw new ValidateRecordException(environment.getProperty("invalid.approvalCategoryId"), "approvalLevel");
		}
		
		Optional<DelegationAuthorityGroup> isPresentAppCat = delegationAuthorityGroupRepository.findByIdAndNameAndStatus(Long.parseLong(approvalLevelCategoryMapResource.getDaGroupId()), 
				approvalLevelCategoryMapResource.getDaGroup(), CommonStatus.ACTIVE.toString());

		if (!isPresentAppCat.isPresent()) {
			
			throw new ValidateRecordException(environment.getProperty("invalid.approvalCategoryId"), "daGroupId");
		}
		
		approvalLevelDaGroupMaping.setDaGroup(isPresentAppCat.get());
		approvalLevelDaGroupMaping.setApprovalLevel(approvalLevel.get());
		approvalLevelDaGroupMaping.setStatus(approvalLevelCategoryMapResource.getStatus());
		approvalLevelDaGroupMaping.setCreatedUser(userName);
		approvalLevelDaGroupMaping.setTenantId(tenantId);
		approvalLevelDaGroupMaping.setCreatedDate(getCreateOrModifyDate());
		approvalLevelDaGroupMaping.setSyncTs(getCreateOrModifyDate());
		approvalLevelDaGroupMaping=approvalLevelDaGroupMapRepository.saveAndFlush(approvalLevelDaGroupMaping);
		return approvalLevelDaGroupMaping.getId();
	}

	private Timestamp getCreateOrModifyDate() {
		Calendar calendar = Calendar.getInstance();
    	java.util.Date now = calendar.getTime();
    	return new Timestamp(now.getTime());
	}

	@Override
	public boolean existsById(Long id) {
		// TODO Auto-generated method stub
		return approvalLevelDaGroupMapRepository.existsById(id);
	}

	@Override
	public Long updateAndValidateApprovalCategory(String tenantId, String userName, Long id,
			@Valid UpdateApprovalLevelDAgGroupMapResource approvalLevelCategoryMapResource) {
       Optional<ApprovalLevelDaGroupMapping> ap=approvalLevelDaGroupMapRepository.findById(id);
		ApprovalLevelDaGroupMapping approvalLevelDaGroupMaping=ap.get();
		Optional<ApprovalLevel> approvalLevel=approvalLevelRepository.findByIdAndNameAndStatus(Long.parseLong(approvalLevelCategoryMapResource.getApprovalLevelId()),approvalLevelCategoryMapResource.getApprovalLevel(),"ACTIVE");
		if(!approvalLevel.isPresent()) {
			throw new ValidateRecordException(environment.getProperty("invalid.approvalCategoryId"), "approvalLevel");
		}
		
		Optional<DelegationAuthorityGroup> isPresentAppCat = delegationAuthorityGroupRepository.findByIdAndNameAndStatus(Long.parseLong(approvalLevelCategoryMapResource.getDaGroupId()), 
				approvalLevelCategoryMapResource.getDaGroup(), CommonStatus.ACTIVE.toString());

		if (!isPresentAppCat.isPresent()) {
			
			throw new ValidateRecordException(environment.getProperty("invalid.approvalCategoryId"), "daGroupId");
		}
		
		approvalLevelDaGroupMaping.setDaGroup(isPresentAppCat.get());
		approvalLevelDaGroupMaping.setApprovalLevel(approvalLevel.get());
		approvalLevelDaGroupMaping.setStatus(approvalLevelCategoryMapResource.getStatus());
		approvalLevelDaGroupMaping.setCreatedUser(userName);
		approvalLevelDaGroupMaping.setTenantId(tenantId);
		approvalLevelDaGroupMaping.setCreatedDate(getCreateOrModifyDate());
		approvalLevelDaGroupMaping.setSyncTs(getCreateOrModifyDate());
		approvalLevelDaGroupMaping=approvalLevelDaGroupMapRepository.saveAndFlush(approvalLevelDaGroupMaping);
		return approvalLevelDaGroupMaping.getId();
		
	}

	@Override
	public List<ApprovalLevelDaGroupMapping> findByLevelId(Long levelId) {
		// TODO Auto-generated method stub
		List<ApprovalLevelDaGroupMapping> op=approvalLevelDaGroupMapRepository.findByApprovalLevelId(levelId);
		/*for(ApprovalLevelDaGroupMapping ap:op) {
		Optional<ApprovalLevel> approvalLevel=approvalLevelRepository.findById(ap.getApprovalLevel().getId());
		Optional<DelegationAuthorityGroup> delegationAuthorityGroup = delegationAuthorityGroupRepository.findById(ap.getDaGroup().getId());
		ap.setLevel(approvalLevel.get());
		ap.setDelegationAuthorityGroup(delegationAuthorityGroup.get());
		}*/
		return op;
	}

	
}
