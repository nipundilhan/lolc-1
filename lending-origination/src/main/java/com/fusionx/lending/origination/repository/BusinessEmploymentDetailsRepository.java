package com.fusionx.lending.origination.repository;

import java.util.List;
import java.util.Optional;

/**
 * Business Employment Details Repository
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

import com.fusionx.lending.origination.domain.BusinessEmploymentDetails;
import com.fusionx.lending.origination.enums.CommonStatus;


@Repository
public interface BusinessEmploymentDetailsRepository extends JpaRepository<BusinessEmploymentDetails, Long>{

	Optional<BusinessEmploymentDetails> findByBusinessAdditionalDtlIdAndEmploymentTypeIdAndStatus(Long businessAdditionalDtlId,
			Long employmentTypeId, CommonStatus status);

	List<BusinessEmploymentDetails> findByBusinessAdditionalDtlId(Long businessAdditionalDtlId);

	Optional<BusinessEmploymentDetails> findByBusinessAdditionalDtlIdAndId(Long businessAdditionalDtlId, Long id);
	
	Optional<BusinessEmploymentDetails> findByBusinessAdditionalDtlIdAndEmploymentTypeIdAndStatusAndIdNotIn(Long businessAdditionalDtlId,
			Long employmentTypeId, CommonStatus status , Long id);


}
