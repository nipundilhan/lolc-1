package com.fusionx.lending.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fusionx.lending.product.domain.MasterDefAccountRuleSetStandardHistory;
import com.fusionx.lending.product.domain.MasterDefAccountRuleSetStandardPending;
import com.fusionx.lending.product.domain.MasterDefinitionPending;


/**
 * Master Def Account Rule Set Standard Pending Repository
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   13-07-2021      		      			Nipun        Created
 *    
 ********************************************************************************************************
 */

public interface MasterDefAccountRuleSetStandardPendingRepository  extends JpaRepository<MasterDefAccountRuleSetStandardPending, Long> {


	
	List<MasterDefAccountRuleSetStandardPending> findByMasterDefinitionPending(MasterDefinitionPending masterDefinitionPending);
	
}
