package com.fusionx.lending.product.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fusionx.lending.product.domain.FeeChargeCap;
import com.fusionx.lending.product.domain.FeeChargeCapPending;
import com.fusionx.lending.product.domain.MasterDefAccountRulePending;
import com.fusionx.lending.product.enums.CommonApproveStatus;
import com.fusionx.lending.product.enums.WorkflowStatus;
import com.fusionx.lending.product.resources.FeeChargeAddResource;
import com.fusionx.lending.product.resources.FeeChargeCapAddResource;
import com.fusionx.lending.product.resources.FeeChargeCapUpdateResource;
import com.fusionx.lending.product.resources.FeeChargeUpdateResource;

/**
 * Fee Charge  Service
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   17-07-2021                            Dilhan       Created
 *    
 ********************************************************************************************************
 */

@Service
public interface FeeChargeCapService {

	/**
	 * 
	 * Find all Fee Charge Cap 
	 * @author Dilhan
	 * @return -JSON array of all Fee Charge 
	 * 
	 * */
	public List<FeeChargeCap> getAll();
	
	/**
	 * 
	 * Find Fee Charge Cap  by ID
	 * @author Dilhan
	 * @return -JSON array of Fee Charge 
	 * 
	 * */
	public Optional<FeeChargeCap> getById(Long id);
	
	/**
	 * 
	 * Find Fee Charge Cap  by code
	 * @author Dilhan
	 * @return -JSON array of Fee Charge 
	 * 
	 * */
	public Optional<FeeChargeCap>getByCode(String code);
	
	/**
	 * 
	 * Find Fee Charge Cap  by status
	 * @author Dilhan
	 * @return -JSON array of Fee Charge 
	 * 
	 * */
	public List<FeeChargeCap> getByStatus(String status);
	
	
	/**
	 * 
	 * Insert Fee Charge Cap 
	 * @author Dilhan
	 * @param  - FeeChargeAddResource
	 * @return - Successfully saved
	 * 
	 * */
	public FeeChargeCap addFeeChargeCap(String tenantId , FeeChargeCapAddResource feeChargeCapAddResource);

	/**
	 * 
	 * Update Fee Charge Cap 
	 * @author Dilhan
	 * @param  - FeeChargeUpdateResource
	 * @return - Successfully saved
	 * 
	 * */
	public FeeChargeCap updateFeeChargeCap(String tenantId, Long id, FeeChargeCapUpdateResource feeChargeCapUpdateResource);

	/**
	 * 
	 * Approve Reject
	 * @author Dilhan
	 * @param  - tenantId
	 * @param - workflowStatus
	 * @return - Successfully saved
	 * 
	 * */
	public boolean approveReject(String tenantId, Long id, WorkflowStatus workflowStatus);
	
	
	/**
	 * 
	 * Find Pending Fee Charge  by ID
	 * @author Dilhan
	 * @return -JSON array of Fee Charge  Pending
	 * 
	 * */
	public Optional<FeeChargeCapPending> getByPendingId(Long pendingId);
	
	/**
	 * Gets by other fee type.
	 *
	 * @param name the name
	 * @return the by other fee type
	 */
	public List<FeeChargeCap> getByOtherFeeType(String name);
	
	/**
	 * Gets by fee charge.
	 *
	 * @param name the name
	 * @return the by fee charge
	 */
	public List<FeeChargeCap> getByFeeCharge(String name);
	
	/**
	 * Search Fee Charge Cap Pending
	 *
	 * @param searchq - the searchq
	 * @param status - the status
	 * @param approveStatus - the approve status
	 * @param pageable - the pageable
	 * @return the page
	 */
	Page<FeeChargeCapPending> searchFeeChargeCap(String searchq, String status, String approveStatus, Pageable pageable);
	
	/**
	 * Approval pending fee charge cap.
	 *
	 * @param feeChargePendingId the fee charge pending id
	 * @param approveStatus the approve status
	 */
	void approvalPendingFeeChargeCap(Long feeChargePendingId, CommonApproveStatus approveStatus);
}