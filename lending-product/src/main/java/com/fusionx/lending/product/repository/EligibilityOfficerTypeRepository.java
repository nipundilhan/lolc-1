package com.fusionx.lending.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.product.domain.EligibilityOfficerType;
import com.fusionx.lending.product.enums.CommonStatus;

/**
 * Eligibility Officer Type Repository
 * 
 *******************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *------------------------------------------------------------------------------------------------------
 *    1   01-07-2021      		     FX-6888	MiyuruW      Created
 *    
 *******************************************************************************************************
 */

@Repository
public interface EligibilityOfficerTypeRepository extends JpaRepository<EligibilityOfficerType, Long> {

	public List<EligibilityOfficerType> findByStatus(CommonStatus status);

	public List<EligibilityOfficerType> findByEligibilitysId(Long eligibilityId);

	public List<EligibilityOfficerType> findByOfficerEligibilitysId(Long officerTypeId);
	
	public Boolean existsByEligibilitysIdAndOfficerEligibilitysId(Long eligibilityId, Long officerTypeId);
	
	public Boolean existsByEligibilitysIdAndOfficerEligibilitysIdAndIdNotIn(Long eligibilityId, Long officerTypeId, Long id);

}
