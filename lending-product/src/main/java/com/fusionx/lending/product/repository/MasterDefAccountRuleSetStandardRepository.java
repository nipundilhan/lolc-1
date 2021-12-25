package com.fusionx.lending.product.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.product.domain.MasterDefAccountRule;
import com.fusionx.lending.product.domain.MasterDefAccountRulePending;
import com.fusionx.lending.product.domain.MasterDefAccountRuleSetStandard;
import com.fusionx.lending.product.domain.MasterDefinitionPending;

/**
 * Master Def Account Set Standard Repository
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   13-07-2021      		      			Nipun        Created
 *    
 ********************************************************************************************************
 */


@Repository
public interface MasterDefAccountRuleSetStandardRepository   extends JpaRepository<MasterDefAccountRuleSetStandard, Long> {
	
	List<MasterDefAccountRuleSetStandard> findByMasterDefAccountRule(MasterDefAccountRule masterDefAccountRule);

}
