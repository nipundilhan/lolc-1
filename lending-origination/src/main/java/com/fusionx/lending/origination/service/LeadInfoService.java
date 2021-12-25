package com.fusionx.lending.origination.service;
/**
 * LeadInfoService
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    
 *    1	  09-08-2021	FXL-380		 FXL-421	Piyumi		 Modified
 ********************************************************************************************************
 */
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fusionx.lending.origination.domain.ApprovalDetail;
import com.fusionx.lending.origination.domain.LeadInfo;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.resource.ApprovalDataResource;
import com.fusionx.lending.origination.resource.LeadInforAssignToCurrentUserUpdateResource;
import com.fusionx.lending.origination.resource.LeadInforBranchAssignUpdateResource;
import com.fusionx.lending.origination.resource.LeadInforMarketingOfficerAssignUpdateResource;

@Service
public interface LeadInfoService {
	
	public Boolean existsById(Long id);
	
	public LeadInfo findDetailById(Long id);
	
	public Page<LeadInfo> findAll(Pageable pageable);

	public Optional<LeadInfo> findById(Long leadId);

	public LeadInfo finalApprovalUpdate(String tenantId, LeadInfo leadInfo, CommonStatus approved);

	public ApprovalDetail save(String tenantId, @Valid ApprovalDataResource approvalDataResource);
	
	/** @author Piyumi
	 * 
	 * get Lead infor  by lead status
	 * input @param{status}
	 * 
	 * @return  Json array of LeadInfo
	 */
	public List<LeadInfo> findByLeadStatus(String status);
	
	/** @author Piyumi
	 * 
	 * update Lead infor  assigned branch
	 * input @param{LeadInforBranchAssignUpdateResource}
	 * 
	 * @return  Long
	 */
	public Long updateAssignedBranch(String tenantId, LeadInforBranchAssignUpdateResource leadInforBranchAssignResource);
	
	/** @authorPiyumi
	 * 
	 * update Lead infor  assigned MarketingOfficer
	 * input @param{LeadInforMarketingOfficerAssignUpdateResource}
	 * 
	 * @return  Long
	 */
	public Long updateAssignedMarketingOfficer(String tenantId,LeadInforMarketingOfficerAssignUpdateResource leadInforMarketingOfficerAssignResource);
	
	/** @author Piyumi
	 * 
	 * update Lead infor  assigned CurrentUser For MarketingOffice
	 * input @param{LeadInforAssignToCurrentUserUpdateResource}
	 * 
	 * @return  Long
	 */
	public Long updateCurrentUserForMarketingOfficer(String tenantId,LeadInforAssignToCurrentUserUpdateResource leadInforAssignToCurrentUserResource);

	/** @author Piyumi
	 * 
	 * get Lead infor  by created date
	 * input @param{date}
	 * 
	 * @return  Json array of LeadInfo
	 */
	public List<LeadInfo> getByDate(String date);
	
	/** @author Piyumi
	 * 
	 * get Lead infor  by CustomerName
	 * input @param{customerName}
	 * 
	 * @return  Json array of LeadInfo
	 */
	
	public List<LeadInfo> getByCustomerNameContaining(String customerName);

	
	
	/** @author Piyumi
	 * 
	 * get Lead infor  by branchId
	 * input @param{branchId}
	 * 
	 * @return  Json array of LeadInfo
	 */
	
	public List<LeadInfo> getByBranch(Long branchId);
	
	/** @author Piyumi
	 * 
	 * get Lead infor  by status
	 * input @param{status}
	 * 
	 * @return  Json array of LeadInfo
	 */
	
	public List<LeadInfo> getByStatus(String status);
	
	public List<LeadInfo> findByStatus(String status);
	
	public String upadetFinalApprovalFlowStatus(Long id ,Boolean approve);

}
