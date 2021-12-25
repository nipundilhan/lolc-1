package com.fusionx.lending.product.repository;
import java.util.List;
import java.util.Optional;

/**
 * Interest Tier Band Repository 
 * 
 *******************************************************************************************************
 *  ###   Date         Story Point   		Task No    Author       Description
 *------------------------------------------------------------------------------------------------------
 *    1   22-07-2021    FXL_July_2021_2  	FXL-53		Piyumi      Created
 *    
 *******************************************************************************************************
 */
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.product.domain.InterestTierBand;
import com.fusionx.lending.product.domain.InterestTierBandSet;
import com.fusionx.lending.product.enums.CommonStatus;

@Repository
public interface InterestTierBandRepository extends JpaRepository<InterestTierBand, Long>{

	List<InterestTierBand> findByInterestTierBandSetAndStatus(InterestTierBandSet interestTierBandSet , CommonStatus status);

	Optional<InterestTierBand> findByCode(String code);

	Optional<InterestTierBand> findByInterestTierBandSetIdAndId(Long interestTierBandSetId, Long id);

	List<InterestTierBand> findByInterestTierBandSet(InterestTierBandSet interestTierBandSet);

}
