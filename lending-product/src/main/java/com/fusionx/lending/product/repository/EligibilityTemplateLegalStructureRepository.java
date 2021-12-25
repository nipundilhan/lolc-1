package com.fusionx.lending.product.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.product.domain.EligibilityTemplateLegalStructure;
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
public interface EligibilityTemplateLegalStructureRepository
		extends JpaRepository<EligibilityTemplateLegalStructure, Long> {

	public Optional<EligibilityTemplateLegalStructure> findById(Long id);
	
	public List<EligibilityTemplateLegalStructure> findByStatus(CommonStatus status);
	
	public List<EligibilityTemplateLegalStructure> findByEligibilityId(Long eligibilityId);
	Boolean existsByEligibilityIdAndLegalStructureIdAndIdNotIn(Long eligibilityId, String legalStructureId, Long id);

}
