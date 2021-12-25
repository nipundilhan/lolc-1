package com.fusionx.lending.product.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fusionx.lending.product.domain.EligibilityCurrencyPending;
import com.fusionx.lending.product.domain.MasterDefAccountRule;
import com.fusionx.lending.product.domain.MasterDefAccountRulePending;
import com.fusionx.lending.product.domain.MasterDefinition;
import com.fusionx.lending.product.resources.MasterDefAccountRuleAddResource;
import com.fusionx.lending.product.resources.MasterDefAccountRuleDetailsResponse;
import com.fusionx.lending.product.resources.MasterDefAccountRulePendingDetailsResponse;
import com.fusionx.lending.product.resources.MasterDefinitionAccountRuleUpdateResource;


/**
 * Master Definition Account Rule  Service
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   17-07-2021                           Nipun        Created
 *    
 ********************************************************************************************************
 */

@Service
public interface MasterDefAccountRuleService {
	
	
	/**
	 * 
	 * Insert Master Def Account Rule  
	 * @author NipunD
	 * @param  - MasterDefAccountRuleAddResource
	 * @return - Successfully saved
	 * 
	 * */
	public MasterDefAccountRule create(String tenantId,MasterDefAccountRuleAddResource masterDefAccountRuleAddResource);
	
	/**
	 * 
	 * Update Master Def Account Rule  
	 * @author NipunD
	 * @param  - MasterDefinitionAccountRuleUpdateResource
	 * @return - Successfully updated
	 * 
	 * */
	public MasterDefinition updateMasterDefinitionAccountRule(String tenantId, Long id,MasterDefinitionAccountRuleUpdateResource masterDefinitionAccountRuleUpdateResource);
	
	
	
	/**
	 * 
	 * Find All Master Def Account Pending Related  details using Master Definition pending Id
	 * @author Nipun_Dilhan
	 * @return -JSON response
	 * 
	 * */
	public MasterDefAccountRulePendingDetailsResponse getAccountRulePendingDetailsMasterDefinitionPendingId(Long masterDefinitionPendingId);

	/**
	 * 
	 * Find All Master Def Account  Related  details using Master Definition Id
	 * @author Nipun_Dilhan
	 * @return -JSON response
	 * 
	 * */
	public MasterDefAccountRuleDetailsResponse getAccountRuleDetailsMasterDefinitionId(Long masterDefinitionId);
	
	/**
	 * 
	 * pending search
	 * @author Nipun_Dilhan
	 * @return -JSON response
	 * 
	 * */
	Page<MasterDefAccountRulePending> searchMasterDefAccountRule(String searchq, String status, String approveStatus, Pageable pageable);

	
	Optional<MasterDefAccountRule> updateMasterDefAccoutRule( Long masterDefinitionPendingId) ;
	
	public void temporyApprove(Long masterDefinitionPendingId);
}
