package com.fusionx.lending.product.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fusionx.lending.product.domain.FeeCharge;
import com.fusionx.lending.product.domain.FeeChargePending;
import com.fusionx.lending.product.enums.WorkflowStatus;
import com.fusionx.lending.product.resources.FeeChargeAddResource;
import com.fusionx.lending.product.resources.FeeChargeUpdateResource;




/**
 * Fee Charge  Service
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   10-06-2021                           MenukaJ        Created
 *    
 ********************************************************************************************************
 */


@Service
public interface FeeChargeService {

	/**
	 * 
	 * Find all Fee Charge 
	 * @author MenukaJ
	 * @return -JSON array of all Fee Charge 
	 * 
	 * */
	public List<FeeCharge> getAll();
	
	/**
	 * 
	 * Find Fee Charge  by ID
	 * @author MenukaJ
	 * @return -JSON object of Fee Charge 
	 * 
	 * */
	public Optional<FeeCharge> getById(Long id);
	
	/**
	 * 
	 * Find Fee Charge  by code
	 * @author MenukaJ
	 * @return -JSON array of Fee Charge 
	 * 
	 * */
	public Optional<FeeCharge>getByCode(String code);
	
	/**
	 * 
	 * Find Fee Charge  by name
	 * @author MenukaJ
	 * @return -JSON array of Fee Charge 
	 * 
	 * */
	public List<FeeCharge> getByName(String name);
	
	/**
	 * 
	 * Find Fee Charge  by status
	 * @author MenukaJ
	 * @return -JSON array of Fee Charge 
	 * 
	 * */
	public List<FeeCharge> getByStatus(String status);
	
	
	/**
	 * 
	 * Insert Fee Charge 
	 * @author MenukaJ
	 * @param  - FeeChargeAddResource
	 * @return - Successfully saved
	 * 
	 * */
	public FeeCharge addFeeCharge(String tenantId , FeeChargeAddResource feeChargeAddResource);

	/**
	 * 
	 * Update Fee Charge 
	 * @author MenukaJ
	 * @param  - FeeChargeUpdateResource
	 * @return - Successfully saved
	 * 
	 * */
	public FeeCharge updateFeeCharge(String tenantId, Long id, FeeChargeUpdateResource feeChargeUpdateResource);

	/**
	 * 
	 * Approve Reject
	 * @author MenukaJ
	 * @param  - tenantId
	 * @param - workflowStatus
	 * @return - Successfully saved
	 * 
	 * */
	public boolean approveReject(String tenantId, Long id, WorkflowStatus workflowStatus);
	
	
	/**
	 * 
	 * Find Pending Fee Charge  by ID
	 * @author MenukaJ
	 * @return -JSON object of Fee Charge  Pending
	 * 
	 * */
	public Optional<FeeChargePending> getByPendingId(Long pendingId);

	/**
	 * 
	 * Search Fee Charge Pending
	 * @author MenukaJ
	 * @param searchq
	 * @param status
	 * @param approveStatus
	 * @param pageable
	 * @return -JSON Pageable FeeChargePending
	 * 
	 * */
	public Page<FeeChargePending> searchFeeChargePending(String searchq, String status, String approveStatus, Pageable pageable);
	
	public Optional<FeeCharge> findById(String tenantId, Long feeChargeId);
}
