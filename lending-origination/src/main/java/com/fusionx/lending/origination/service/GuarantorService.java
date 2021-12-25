package com.fusionx.lending.origination.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fusionx.lending.origination.domain.Guarantor;
import com.fusionx.lending.origination.enums.WorkflowStatus;
import com.fusionx.lending.origination.resource.ApplicantBlackListApproveRejectResource;
import com.fusionx.lending.origination.resource.ApprovePendingGuarantorBlacklistResource;
import com.fusionx.lending.origination.resource.GuarantorAddResource;
import com.fusionx.lending.origination.resource.UpdateGuarantorResource;

/**
 * guarantor Service
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   26-04-2021   							 Thamokshi        Created
 *    
 ********************************************************************************************************
 */
@Service
public interface GuarantorService {

	/**
	 * 
	 * @param tenantId
	 * @param guarantorAddResource
	 * @return
	 */
	Guarantor addGuarantor(String tenantId, @Valid GuarantorAddResource guarantorAddResource);
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	Optional<Guarantor> getById(Long id);
	
	List<Guarantor> getByLeadId(Long id);

//	/**
//	 * 
//	 * @param tenantId
//	 * @param id
//	 * @param applicantBlackListApproveRejectResource
//	 * @param workflowStatus
//	 * @return
//	 */
//	public boolean approveBlacklist(String tenantId, Long id, ApplicantBlackListApproveRejectResource applicantBlackListApproveRejectResource,
//			WorkflowStatus workflowStatus);

//	/**
//	 * 
//	 * @param searchq
//	 * @param firstName
//	 * @param identificationNo
//	 * @param workflowStatus
//	 * @param pageable
//	 * @return
//	 */
//	Page<ApprovePendingGuarantorBlacklistResource> searchApprovePendingGuarantorBlacklist(String searchq,
//			String firstName, String identificationNo, String workflowStatus, Pageable pageable);
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	Guarantor findById(Long id);

	/**
	 * 
	 * @param id
	 * @return
	 */
	boolean existsById(Long id);

	/**
	 * 
	 * @param tenantId
	 * @param userName
	 * @param id
	 * @param updateGuarantorResource
	 * @return
	 */
	Guarantor update(String tenantId, String userName, Long id, @Valid UpdateGuarantorResource updateGuarantorResource);

}
