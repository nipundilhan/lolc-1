package com.fusionx.lending.product.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.LeadApprovalDetail;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.enums.LeadStatusEnum;
import com.fusionx.lending.product.repository.LeadApprovalDetailRepository;
import com.fusionx.lending.product.resources.LeadApprovalDetailAddResources;
import com.fusionx.lending.product.service.LeadApprovalDetailService;
import com.fusionx.lending.product.service.ValidationService;

@Service
public class LeadApprovalDetailServiceImpl implements LeadApprovalDetailService{

	@Autowired
	private LeadApprovalDetailRepository leadApprovalDetailRepository;
	@Autowired
	private ValidationService validationService;
	
	@Override
	public List<LeadApprovalDetail> getAll() {
		return leadApprovalDetailRepository.findAll();
	}

	@Override
	public List<LeadApprovalDetail> findAllByLeadId(Long leadId) {
		return leadApprovalDetailRepository.findByLeadId(leadId);
	}

	@Override
	public LeadApprovalDetail addLeadApprovalDetails(String tenantId,
			LeadApprovalDetailAddResources leadApprovalDetailAddResources) {
		
		LeadApprovalDetail leadApprovalDetail=new LeadApprovalDetail();
		leadApprovalDetail.setAction(LeadStatusEnum.valueOf(leadApprovalDetailAddResources.getAction()));
		leadApprovalDetail.setActionTakenDate(validationService.getCreateOrModifyDate());
		leadApprovalDetail.setActonTakenBy(LogginAuthentcation.getInstance().getUserName());
		leadApprovalDetail.setApprovalGroupId(leadApprovalDetailAddResources.getApprovalGroupId());
		leadApprovalDetail.setApprovalGroupUserId(leadApprovalDetailAddResources.getApprovalGroupUserId());
		leadApprovalDetail.setComments(leadApprovalDetailAddResources.getComments());
		leadApprovalDetail.setCreatedDate(validationService.getCreateOrModifyDate());
		leadApprovalDetail.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
		leadApprovalDetail.setCycle(leadApprovalDetailAddResources.getCycle());
		leadApprovalDetail.setLeadId(leadApprovalDetailAddResources.getLeadId());
		leadApprovalDetail.setModifiedDate(validationService.getCreateOrModifyDate());
		leadApprovalDetail.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
		leadApprovalDetail.setStatus(CommonStatus.valueOf(leadApprovalDetailAddResources.getStatus()));
		leadApprovalDetail.setTenantId(tenantId);
		
		return leadApprovalDetailRepository.save(leadApprovalDetail);
	}

}
