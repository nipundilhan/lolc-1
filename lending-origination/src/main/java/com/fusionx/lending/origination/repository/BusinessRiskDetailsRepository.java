package com.fusionx.lending.origination.repository;

import java.util.List;
import java.util.Optional;

/**
 * Business Risk Details Repository
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   06-09-2021   FXL-115  	 FXL-657       Piyumi       Created
 *    
 ********************************************************************************************************
*/

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.BusinessRiskDetails;
import com.fusionx.lending.origination.enums.CommonStatus;



@Repository
public interface BusinessRiskDetailsRepository extends JpaRepository<BusinessRiskDetails, Long>{

	Optional<BusinessRiskDetails> findByBusinessAdditionalDtlIdAndRiskTypeIdAndStatus(Long businessAdditionalDtlId, Long riskTypeId,
			CommonStatus status);

	List<BusinessRiskDetails> findByBusinessAdditionalDtlId(Long businessAdditionalDtlId);

	Optional<BusinessRiskDetails> findByBusinessAdditionalDtlIdAndId(Long businessAdditionalDtlId, Long id);

	Optional<BusinessRiskDetails> findByBusinessAdditionalDtlIdAndRiskTypeIdAndStatusAndIdNotIn(Long businessAdditionalDtlId, Long riskTypeId,
			CommonStatus status, Long id);


}
