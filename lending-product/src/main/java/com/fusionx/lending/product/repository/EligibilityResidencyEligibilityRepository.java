package com.fusionx.lending.product.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.product.domain.Eligibility;
import com.fusionx.lending.product.domain.EligibilityResidencyEligibility;
import com.fusionx.lending.product.enums.CommonStatus;

/**
 * EligibilityResidencyEligibility
 * 
 *******************************************************************************************************
 *  ###   Date         Story Point   		Task No    Author       Description
 *------------------------------------------------------------------------------------------------------
 *    1   28-07-2021    FXL_July_2021_3  	FXL-55		Piyumi      Created
 *    
 *******************************************************************************************************
 */

@Repository
public interface EligibilityResidencyEligibilityRepository extends JpaRepository<EligibilityResidencyEligibility, Long>{

	List<EligibilityResidencyEligibility> findByEligibilityId(Long id);

	Optional<EligibilityResidencyEligibility> findByEligibilityIdAndId(Long eligibilityId, Long id);

	Optional<EligibilityResidencyEligibility> findByResidencyEligibilityIdAndEligibilityIdAndStatusAndIdNotIn(Long residencyEligibilityId,Long eligibilityId ,CommonStatus active, Long id);

	Optional<EligibilityResidencyEligibility> findByResidencyEligibilityIdAndEligibilityId(Long residencyEligibilityId, Long id);

	List<EligibilityResidencyEligibility> findByResidencyEligibilityId(Long residencyEligibilityId);

	List<EligibilityResidencyEligibility> findByStatus(CommonStatus active);

	Optional<EligibilityResidencyEligibility> findByResidencyEligibilityIdAndEligibilityIdAndStatus(Long residencyEligibilityId, Long eligibilityId ,CommonStatus active);


}
