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
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.repository.ApprovalCategoryRepository;
import com.fusionx.lending.origination.repository.ApprovalLevelCategoryMappingRepository;
import com.fusionx.lending.origination.repository.ApprovalLevelRepository;
import com.fusionx.lending.origination.resource.ApprovalLevelCategoryMapResource;
import com.fusionx.lending.origination.resource.UpdateApprovalLevelCategoryMapResource;
import com.fusionx.lending.origination.service.ApprovalLevelCategoryMapService;

@Component
@Transactional(rollbackFor = Exception.class)
public class ApprovalLevelCategoryMapServiceImpl  extends MessagePropertyBase implements ApprovalLevelCategoryMapService{

	@Autowired
	ApprovalLevelCategoryMappingRepository approvalLevelCategoryMappingRepository;
	
	@Autowired
	ApprovalLevelRepository approvalLevelRepository;
	
	@Autowired
	ApprovalCategoryRepository approvalCategoryRepository;
	
	@Override
	public List<ApprovalLevelCategoryMapping> findAll() {
		// TODO Auto-generated method stub
		return approvalLevelCategoryMappingRepository.findAll();
	}

	@Override
	public Optional<ApprovalLevelCategoryMapping> findById(Long id) {
		Optional<ApprovalLevelCategoryMapping> approval = approvalLevelCategoryMappingRepository.findById(id);
		if (approval.isPresent()) {
			Optional<ApprovalLevel> approvalLevel=approvalLevelRepository.findById(approval.get().getApprovalLevel().getId());
			Optional<ApprovalCategory> isPresentAppCat = approvalCategoryRepository.findById(approval.get().getApprovalCategory().getId());
			//approval.get().setLevel(approvalLevel.get());
			//approval.get().setCategory(isPresentAppCat.get());
			return Optional.ofNullable(approval.get());
		} else {
			return Optional.empty();
		}
	}

	@Override
	public List<ApprovalLevelCategoryMapping> findByStatus(String status) {
		// TODO Auto-generated method stub
		return approvalLevelCategoryMappingRepository.findByStatus(status);
	}

	@Override
	public Long save(String tenantId, String userName,
			@Valid ApprovalLevelCategoryMapResource approvalLevelCategoryMapResource) {
		ApprovalLevelCategoryMapping approvalLevelCategoryMapping=new ApprovalLevelCategoryMapping();
		Optional<ApprovalLevel> approvalLevel=approvalLevelRepository.findByIdAndNameAndStatus(Long.parseLong(approvalLevelCategoryMapResource.getApprovalLevelId()),approvalLevelCategoryMapResource.getApprovalLevel(),"ACTIVE");
		if(!approvalLevel.isPresent()) {
			throw new ValidateRecordException(environment.getProperty("invalid.approvalCategoryId"), "approvalLevel");
		}
		
		LoggerRequest.getInstance().logInfo("ApprovalCategoryMapProduct********************************Validate Approve Category*********************************************");
		Optional<ApprovalCategory> isPresentAppCat = approvalCategoryRepository.findByIdAndNameAndStatus(Long.parseLong(approvalLevelCategoryMapResource.getApprovalCategoryId()), 
				approvalLevelCategoryMapResource.getApprovalCategory(), CommonStatus.ACTIVE.toString());

		if (!isPresentAppCat.isPresent()) {
			
			throw new ValidateRecordException(environment.getProperty("invalid.approvalCategoryId"), "approvalCategoryId");
		}
		
		approvalLevelCategoryMapping.setApprovalCategory(isPresentAppCat.get());
		approvalLevelCategoryMapping.setApprovalLevel(approvalLevel.get());
		approvalLevelCategoryMapping.setTenantId(tenantId);
		approvalLevelCategoryMapping.setStatus(approvalLevelCategoryMapResource.getStatus());
		approvalLevelCategoryMapping.setCreatedUser(userName);
		approvalLevelCategoryMapping.setCreatedDate(getCreateOrModifyDate());
		approvalLevelCategoryMapping.setSyncTs(getCreateOrModifyDate());
		approvalLevelCategoryMapping=approvalLevelCategoryMappingRepository.saveAndFlush(approvalLevelCategoryMapping);
		return approvalLevelCategoryMapping.getId();
	}

	private Timestamp getCreateOrModifyDate() {
		Calendar calendar = Calendar.getInstance();
    	java.util.Date now = calendar.getTime();
    	return new Timestamp(now.getTime());
	}

	@Override
	public boolean existsById(Long id) {
		// TODO Auto-generated method stub
		return approvalLevelCategoryMappingRepository.existsById(id);
	}

	@Override
	public Long updateAndValidateApprovalCategory(String tenantId, String userName, Long id,
			@Valid UpdateApprovalLevelCategoryMapResource approvalLevelCategoryMapResource) {
		Optional<ApprovalLevelCategoryMapping> approvalLevelCategoryMappingop=approvalLevelCategoryMappingRepository.findById(id);
		ApprovalLevelCategoryMapping approvalLevelCategoryMapping=approvalLevelCategoryMappingop.get();
		Optional<ApprovalLevel> approvalLevel=approvalLevelRepository.findByIdAndNameAndStatus(Long.parseLong(approvalLevelCategoryMapResource.getApprovalLevelId()),approvalLevelCategoryMapResource.getApprovalLevel(),"ACTIVE");
		if(!approvalLevel.isPresent()) {
			throw new ValidateRecordException(environment.getProperty("invalid.approvalCategoryId"), "approvalLevel");
		}
		
		LoggerRequest.getInstance().logInfo("ApprovalCategoryMapProduct********************************Validate Approve Category*********************************************");
		Optional<ApprovalCategory> isPresentAppCat = approvalCategoryRepository.findByIdAndNameAndStatus(Long.parseLong(approvalLevelCategoryMapResource.getApprovalCategoryId()), 
				approvalLevelCategoryMapResource.getApprovalCategory(), CommonStatus.ACTIVE.toString());

		if (!isPresentAppCat.isPresent()) {
			
			throw new ValidateRecordException(environment.getProperty("invalid.approvalCategoryId"), "approvalCategoryId");
		}
		
		approvalLevelCategoryMapping.setApprovalCategory(isPresentAppCat.get());
		approvalLevelCategoryMapping.setApprovalLevel(approvalLevel.get());
		approvalLevelCategoryMapping.setTenantId(tenantId);
		approvalLevelCategoryMapping.setStatus(approvalLevelCategoryMapResource.getStatus());
		approvalLevelCategoryMapping.setCreatedUser(userName);
		approvalLevelCategoryMapping.setCreatedDate(getCreateOrModifyDate());
		approvalLevelCategoryMapping.setSyncTs(getCreateOrModifyDate());
		approvalLevelCategoryMapping=approvalLevelCategoryMappingRepository.saveAndFlush(approvalLevelCategoryMapping);
		return approvalLevelCategoryMapping.getId();
		
	}

	@Override
	public List<ApprovalLevelCategoryMapping> findBycategoryId(Long categoryId) {
		// TODO Auto-generated method stub
		return approvalLevelCategoryMappingRepository.findByApprovalCategoryId(categoryId);
	}

}
