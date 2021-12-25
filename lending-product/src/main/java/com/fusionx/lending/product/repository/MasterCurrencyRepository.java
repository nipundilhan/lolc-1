package com.fusionx.lending.product.repository;

import java.util.List;

/**
 * Master Def Currency Eligibility Repository
 * 
 *******************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *------------------------------------------------------------------------------------------------------
 *    1   13-07-2021      		     FX-	Piyumi      Created
 *    
 *******************************************************************************************************
 */
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.product.domain.MasterCurrency;
import com.fusionx.lending.product.domain.MasterDefinition;

@Repository
public interface MasterCurrencyRepository  extends JpaRepository<MasterCurrency, Long>{
	
	List<MasterCurrency> findByMasterDefinition(MasterDefinition masterDefinition);

	public Boolean existsByMasterDefinitionIdAndCurrencyId(Long masterDefId, Long currencyId);
	
	public Boolean existsByMasterDefinitionIdAndCurrencyIdAndIdNotIn(Long masterDefId, Long currencyId,Long Id);
	
	

}
