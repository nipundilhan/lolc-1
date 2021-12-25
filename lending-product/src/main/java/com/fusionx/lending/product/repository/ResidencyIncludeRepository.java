package com.fusionx.lending.product.repository;

/**
 * Residency eligibility service
 * @author 	Rangana
 * @Dat     08-06-2021
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   08-06-2021     FX-6        FX-6524    Rangana      Created
 *    
 ********************************************************************************************************
 */

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.product.domain.ResidencyInclude;
import com.fusionx.lending.product.enums.CommonStatus;

@Repository
public interface ResidencyIncludeRepository extends JpaRepository<ResidencyInclude, Long> {
	
	List<ResidencyInclude> findByStatus(CommonStatus status);
	
	List<ResidencyInclude> findByResidencyEligibilityId(Long residencyEligibilityId);
	
	Optional<ResidencyInclude> findByResidencyEligibilityIdAndCountryId(Long residencyEligibilityId, Long countryId);

}
