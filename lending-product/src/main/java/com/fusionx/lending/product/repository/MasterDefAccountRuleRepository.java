package com.fusionx.lending.product.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.product.domain.MasterDefAccountRule;
import com.fusionx.lending.product.domain.MasterDefAccountRulePending;
import com.fusionx.lending.product.domain.MasterDefinition;
import com.fusionx.lending.product.domain.MasterDefinitionPending;

/**
 * Master Def Account Rule Repository
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   13-07-2021      		      			Nipun        Created
 *    
 ********************************************************************************************************
 */


@Repository
public interface MasterDefAccountRuleRepository  extends JpaRepository<MasterDefAccountRule, Long> {
	
	Optional<MasterDefAccountRule> findByMasterDefinition(MasterDefinition masterDefinition);

}
