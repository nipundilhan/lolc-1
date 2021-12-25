package com.fusionx.lending.product.service;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
/**
 * Master Def Currency Eligibility Service
 * 
 *******************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *------------------------------------------------------------------------------------------------------
 *    1   13-07-2021      		     FX-	Piyumi      Created
 *    
 *******************************************************************************************************
 */
import org.springframework.stereotype.Service;

import com.fusionx.lending.product.domain.MasterCurrency;
import com.fusionx.lending.product.domain.MasterCurrencyPending;
import com.fusionx.lending.product.domain.MasterDefinitionPending;
import com.fusionx.lending.product.enums.CommonApproveStatus;
import com.fusionx.lending.product.enums.WorkflowStatus;
import com.fusionx.lending.product.resources.MasterCurrencyMainAddResource;
import com.fusionx.lending.product.resources.MasterCurrencyMainUpdateResource;

@Service
public interface MasterCurrencyService {
	
	/**
	 * 
	 * Find MasterCurrency by ID
	 * 
	 * @author Piyumi
	 * @return -JSON Object of MasterCurrency
	 * 
	 */
	public Optional<MasterCurrency> getById(Long id);

	/**
	 * 
	 * Insert MasterCurrency
	 * 
	 * @author Piyumi
	 * @param - MasterCurrencyAddResource
	 * @return - Successfully saved
	 * 
	 */
	public  List<Long> addMasterCurrency(String tenantId, Long masterDefId,
			MasterCurrencyMainAddResource masterCurrencyMainAddResource);

	/**
	 * 
	 * Update MasterCurrency
	 * 
	 * @author Piyumi
	 * @param - MasterCurrencyMainUpdateResource
	 * @return - Successfully saved
	 * 
	 */
	public Long updateMasterCurrency(String tenantId, Long masterDefId,
			MasterCurrencyMainUpdateResource masterCurrencyMainUpdateResource);

	/**
	 * 
	 * Approve Reject
	 * 
	 * @author Piyumi
	 * @param - tenantId
	 * @param - workflowStatus
	 * @return - Successfully saved
	 * 
	 */
	public boolean approveReject(String tenantId, Long id, WorkflowStatus workflowStatus);

	/**
	 * 
	 * Find Pending MasterCurrency by ID
	 * 
	 * @author Piyumi
	 * @return -JSON object of MasterCurrency Pending
	 * 
	 */
	public Optional<MasterCurrencyPending> getByPendingId(Long pendingId);
	
	/**
	 * 
	 * Find MasterCurrency by masterDefinitionId
	 * 
	 * @author Piyumi
	 * @return -JSON array of all MasterCurrency
	 * 
	 */
	public List<MasterCurrency> getByMasterDefinitionId(Long masterDefId);
	
	/**
	 * 
	 * Find Pending MasterCurrency by MasterDefinitionId
	 * 
	 * @author Piyumi
	 * @return -JSON array of MasterCurrency Pending
	 * 
	 */
	public List<MasterCurrencyPending> getPendingByMasterDefinitionId(Long masterDefId);
	
	/**
	 * Search Master Currency
	 *
	 * @param searchq - the searchq
	 * @param status - the status
	 * @param approveStatus - the approve status
	 * @param pageable - the pageable
	 * @return the page
	 */
	public Page<MasterCurrencyPending> searchMasterCurrency(String searchq, String status, String approveStatus, Pageable pageable);

	/**
	 * 
	 * Approve Reject
	 * 
	 * @param - approveStatus
	 * @param - masterDefinitionPending
	 * 
	 */
	public void updateApprovalDetails(Long masterDefinitionPendingId);
}
