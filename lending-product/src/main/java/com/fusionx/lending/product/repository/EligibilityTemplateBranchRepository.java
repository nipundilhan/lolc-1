package com.fusionx.lending.product.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.product.domain.EligibilityTemplateBranch;
import com.fusionx.lending.product.enums.CommonStatus;

/**
Eligibility Template Branch
* 
********************************************************************************************************
*  ###   Date         Story Point   Task No    Author       Description
*-------------------------------------------------------------------------------------------------------
*    1   21-07-2019   FXL-1         FXL-42     Dilki        Created
*    
********************************************************************************************************
*/

@Repository
public interface EligibilityTemplateBranchRepository
		extends JpaRepository<EligibilityTemplateBranch, Long> {

	public Optional<EligibilityTemplateBranch> findById(Long id);
	
	public List<EligibilityTemplateBranch> findByStatus(CommonStatus status); 

	public List<EligibilityTemplateBranch> findByEligibilityId(Long eligibilityId);

	public Boolean existsByEligibilityIdAndBranchIdAndIdNotIn(Long eligibilityId, String branchId, Long id);

}
