package com.fusionx.lending.product.repository;

/**
 * Residency eligibility service
 * @author 	Rangana
 * @Dat     07-06-2021
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   07-06-2019     FX-6        FX-6523    Rangana      Created
 *    
 ********************************************************************************************************
 */

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.product.domain.ResidencyEligibility;
import com.fusionx.lending.product.enums.CommonStatus;

@Repository
public interface ResidencyEligibilityRepository extends JpaRepository<ResidencyEligibility, Long>{
	
	List<ResidencyEligibility> findByStatus(CommonStatus status);
	
	Optional<ResidencyEligibility> findByResidencyTypeId(Long residencyTypeId);

	Optional<ResidencyEligibility> findByIdAndStatus(Long residencyEligibilityId, CommonStatus status);

	Optional<ResidencyEligibility> findByResidencyTypeIdAndIdNotIn(Long residencyTypeId, Long id);
	
	Optional<ResidencyEligibility> findByCode(String code);
	
	Optional<ResidencyEligibility> findByCodeAndId(String code, Long id);
}
