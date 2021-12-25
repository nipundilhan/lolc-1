package com.fusionx.lending.product.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.product.domain.InterestTemplate;
import com.fusionx.lending.product.domain.InterestTierBandSet;
import com.fusionx.lending.product.enums.CommonStatus;


/**
 * InterestTierBandSetRepository
 * 
 *******************************************************************************************************
 *  ###   Date         Story Point   		Task No    Author       Description
 *------------------------------------------------------------------------------------------------------
 *    1   19-07-2021    FXL_July_2021_2  	FXL-52		Piyumi      Created
 *    
 *******************************************************************************************************
 */

@Repository
public interface InterestTierBandSetRepository extends JpaRepository<InterestTierBandSet, Long>{
	
	List<InterestTierBandSet> findByInterestTemplate(InterestTemplate interestTemplate);

	Optional<InterestTierBandSet> findByCode(String code);

	Optional<InterestTierBandSet> findByInterestTemplateIdAndIdAndStatus(Long interestTempId, Long id, CommonStatus active);

	Optional<InterestTierBandSet> findByIdAndStatus(Long interestTierBandSetId, CommonStatus active);

	Optional<InterestTierBandSet> findByInterestTemplateIdAndId(Long interestTempId, Long id);
	
	Optional<InterestTierBandSet> findByCodeAndInterestTemplateId(String Code, Long interestTempId);

}
