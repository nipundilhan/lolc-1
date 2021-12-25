package com.fusionx.lending.product.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.product.domain.EligibilityTemplateIndustry;
import com.fusionx.lending.product.enums.CommonStatus;

/**
Eligibility Template Legal Structure
* 
********************************************************************************************************
*  ###   Date         Story Point   Task No    Author       Description
*-------------------------------------------------------------------------------------------------------
*    1   21-07-2019   FXL-1         FXL-42     Dilki        Created
*    
********************************************************************************************************
*/

@Repository
public interface EligibilityTemplateIndustryRepository
		extends JpaRepository<EligibilityTemplateIndustry, Long> {

	public Optional<EligibilityTemplateIndustry> findById(Long id);
	
	public List<EligibilityTemplateIndustry> findByStatus(CommonStatus status); 

	public List<EligibilityTemplateIndustry> findByEligibilityId(Long eligibilityId);

	Boolean existsByEligibilityIdAndIndustryIdAndIdNotIn(Long eligibilityId, String industryId, Long id);



}
