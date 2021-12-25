package com.fusionx.lending.origination.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fusionx.lending.origination.domain.Customer;
import com.fusionx.lending.origination.domain.Guarantor;
import com.fusionx.lending.origination.domain.LeadInfo;
import com.fusionx.lending.origination.resource.AssertApproveRequestResource;
import com.fusionx.lending.origination.resource.PendingCutomerApproveRequestResource;
import com.fusionx.lending.origination.resource.PendingSuppliesApproveRequestResource;

/**
 * Final Approval Service
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   13-06-2021                           MenukaJ        Created
 *    
 ********************************************************************************************************
 */

@Service
public interface FinalApprovalService {
	
	/**
	 * 
	 * Bulk Pending Customer Approval
	 * @author MenukaJ
	 * @param - tenantId
	 * @param  - PendingCutomerApproveRequestResource
	 * @return - list Customer
	 * 
	 * */
	public List<Customer> bulkPendingCusApproval(String tenantId, PendingCutomerApproveRequestResource pendingCutomerApproveRequestResource);
	
	/**
	 * 
	 * Bulk Pending Suppliers Approval
	 * @author MenukaJ
	 * @param - tenantId
	 * @param  - PendingSuppliesApproveRequestResource
	 * @return - list Guarantor
	 * 
	 * */
	public List<Guarantor> bulkPendingSupApproval(String tenantId, PendingSuppliesApproveRequestResource pendingSuppliesApproveRequestResource);
	
	/**
	 * 
	 * Bulk Asserts Approval
	 * @author MenukaJ
	 * @param - tenantId
	 * @param  - AssertApproveRequestResource
	 * 
	 * */
	public void bulkAssertsApproval(String tenantId, AssertApproveRequestResource assertApproveRequestResource);

	


}
