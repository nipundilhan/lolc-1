package com.fusionx.lending.product.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fusionx.lending.product.domain.FeeChargeCap;
import com.fusionx.lending.product.domain.FeeChargeDetails;
import com.fusionx.lending.product.domain.FeeChargeDetailsPending;
import com.fusionx.lending.product.enums.CommonApproveStatus;
import com.fusionx.lending.product.enums.WorkflowStatus;
import com.fusionx.lending.product.resources.FeeChargeDetailsAddResource;
import com.fusionx.lending.product.resources.FeeChargeDetailsUpdateResource;

/**
 * Fee Charge Details Service
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   28-07-2021                            Dilhan       Created
 *    
 ********************************************************************************************************
 */

@Service
public interface FeeChargeDetailsService {

	/**
	 * 
	 * Find all Fee Charge Details
	 * @author Dilhan
	 * @return -JSON array of all Fee Charge 
	 * 
	 * */
	public List<FeeChargeDetails> getAll();
	
	/**
	 * 
	 * Find Fee Charge Details  by ID
	 * @author Dilhan
	 * @return -JSON array of Fee Charge 
	 * 
	 * */
	public Optional<FeeChargeDetails> getById(Long id);
	
	/**
	 * 
	 * Find Fee Charge Details by code
	 * @author Dilhan
	 * @return -JSON array of Fee Charge 
	 * 
	 * */
	public Optional<FeeChargeDetails> getByCode(String code);
	
	/**
	 * 
	 * FindFee Charge Details by status
	 * @author Dilhan
	 * @return -JSON array of Fee Charge 
	 * 
	 * */
	public List<FeeChargeDetails> getByStatus(String status);
	
	
	/**
	 * 
	 * Insert Fee Charge Details
	 * @author Dilhan
	 * @param  - FeeChargeDetailsAddResource
	 * @return - Successfully saved
	 * 
	 * */
	public FeeChargeDetails addFeeChargeDetails(String tenantId , FeeChargeDetailsAddResource feeChargeDetailsAddResource);

	/**
	 * 
	 * Update Fee Charge Details
	 * @author Dilhan
	 * @param  - FeeChargeDetailsUpdateResource
	 * @return - Successfully saved
	 * 
	 * */
	public FeeChargeDetails updateFeeChargeDetails(String tenantId, Long id, FeeChargeDetailsUpdateResource feeChargeDetailsUpdateResource);

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
	Optional<FeeChargeDetailsPending> getByPendingId(Long pendingId);
	
	/**
	 * Gets the by fee charge.
	 *
	 * @param name the name
	 * @return the by fee charge
	 */
	List<FeeChargeDetails> getByFeeCharge(String name);
	
	/**
	 * Gets the by fee type.
	 *
	 * @param name the name
	 * @return the by fee type
	 */
	List<FeeChargeDetails> getByFeeType(String name);
	
	/**
	 * Gets the by fee category.
	 *
	 * @param name the name
	 * @return the by fee category
	 */
	List<FeeChargeDetails> getByFeeCategory(String name);
	
	/**
	 * Approval pending fee charge details.
	 *
	 * @param feeChargePendingId the fee charge pending id
	 */
	void approvalPendingFeeChargeDetails(Long feeChargePendingId, CommonApproveStatus approveStatus);
	
}
