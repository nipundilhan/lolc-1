package com.fusionx.lending.product.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fusionx.lending.product.domain.Repayment;
import com.fusionx.lending.product.domain.RepaymentPending;
import com.fusionx.lending.product.enums.WorkflowStatus;
import com.fusionx.lending.product.resources.RepaymentAddResource;
import com.fusionx.lending.product.resources.RepaymentUpdateResource;

/**
 * Repayment Service
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   	Task No    	Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   05-07-2021     FX-6620 		FX-6803     RavishikaS      Created
 *    
 ********************************************************************************************************
 */
@Service
public interface RepaymentService {
	
	/**
	 * 
	 * Find all Repayment
	 * @author RavishikaS
	 * @return -JSON array of all Repayment
	 * 
	 * */
	public List<Repayment> getAll();
	
	/**
	 * 
	 * Find Repayment by ID
	 * @author RavishikaS
	 * @return -JSON array of Repayment
	 * 
	 * */
	public Optional<Repayment> getById(Long id);
	
	/**
	 * 
	 * Find Repayment by code
	 * @author RavishikaS
	 * @return -JSON array of Repayment
	 * 
	 * */
	public Optional<Repayment>getByCode(String code);
	
	/**
	 * 
	 * Find Repayment by status
	 * @author RavishikaS
	 * @return -JSON array of Repayment
	 * 
	 * */
	public List<Repayment> getByStatus(String status);
	
	
	/**
	 * 
	 * Insert Repayment
	 * @author RavishikaS
	 * @param  - RepaymentAddResource
	 * @return - Successfully saved
	 * 
	 * */
	public Repayment addRepayment(String tenantId , RepaymentAddResource repaymentAddResource);

	/**
	 * 
	 * Update Repayment
	 * @author RavishikaS
	 * @param  - RepaymentUpdateResource
	 * @return - Successfully updated
	 * 
	 * */
	public Repayment updateRepayment(String tenantId, Long id, RepaymentUpdateResource repaymentUpdateResource);

	/**
	 * 
	 * Approve Reject
	 * @author RavishikaS
	 * @param  - tenantId
	 * @param - workflowStatus
	 * @return - Successfully saved
	 * 
	 *  */
	public boolean approveReject(String tenantId, Long id, WorkflowStatus workflowStatus);

	/**
	 * 
	 * Find Pending Eligibility  by ID
	 * @author RavishikaS
	 * @param pendingId
	 * @return -JSON object of Repayment Pending
	 * 
	 * */
	public Optional<RepaymentPending> getByPendingId(Long pendingId);

	/**
	 * 
	 * Search Eligibility Pending
	 * @author RavishikaS
	 * @param searchq
	 * @param status
	 * @param approveStatus
	 * @param pageable
	 * @return -JSON Pageable RepaymentPending
	 * 
	 * */
	public Page<RepaymentPending> searchRepaymentPending(String searchq, String status, String approveStatus, Pageable pageable);

	/**
	 * Find repayment by name.
	 * @author RavishikaS
	 * @param the name
	 * @return -JSON array of Repayment
	 */
	public List<Repayment> getByName(String name);

}
