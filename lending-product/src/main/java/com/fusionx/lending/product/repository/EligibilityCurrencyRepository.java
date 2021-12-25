package com.fusionx.lending.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.fusionx.lending.product.domain.EligibilityCurrency;
import com.fusionx.lending.product.enums.CommonStatus;

/**
 * Eligibility Currency Repository
 * 
 *******************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *------------------------------------------------------------------------------------------------------
 *    1   01-07-2021      		     FX-6891	MiyuruW      Created
 *    
 *******************************************************************************************************
 */

@Repository
public interface EligibilityCurrencyRepository extends JpaRepository<EligibilityCurrency, Long> {

	public List<EligibilityCurrency> findByStatus(CommonStatus status);
	
	public List<EligibilityCurrency> findByEligibilitysId(Long eligibilityId);

	public List<EligibilityCurrency> findByCurrencyId(Long currencyId);

	public Boolean existsByEligibilitysIdAndCurrencyId(Long eligibilityId, Long currencyId);

	public Boolean existsByEligibilitysIdAndCurrencyIdAndIdNotIn(Long eligibilityId, Long currencyId, Long id);

	
}
