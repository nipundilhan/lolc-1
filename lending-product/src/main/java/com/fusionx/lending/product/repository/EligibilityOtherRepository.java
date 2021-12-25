package com.fusionx.lending.product.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.product.domain.Eligibility;
import com.fusionx.lending.product.domain.EligibilityOther;
import com.fusionx.lending.product.enums.CommonStatus;

/**
 * EligibilityOtherRepository
 * 
 *******************************************************************************************************
 *  ###   Date         Story Point   		Task No    Author       Description
 *------------------------------------------------------------------------------------------------------
 *    1   27-07-2021    FXL_July_2021_2  	FXL-54		Piyumi      Created
 *    
 *******************************************************************************************************
 */
@Repository
public interface EligibilityOtherRepository extends JpaRepository<EligibilityOther, Long>{

	List<EligibilityOther> findByEligibility(Eligibility eligibility);

	Optional<EligibilityOther> findByEligibilityIdAndId(Long eligibilityId, Long id);

	Optional<EligibilityOther> findByOtherEligibilityTypeIdAndEligibilityIdAndStatusAndIdNotIn(Long otherEligibilityTypeId , Long eligibilityId, CommonStatus status ,Long id);

	Optional<EligibilityOther> findByOtherEligibilityTypeIdAndEligibilityIdAndStatus(Long otherEligibilityTypeId , Long eligibilityId, CommonStatus status);

}
