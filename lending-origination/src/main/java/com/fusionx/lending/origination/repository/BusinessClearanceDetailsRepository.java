package com.fusionx.lending.origination.repository;

import java.util.List;
import java.util.Optional;

/**
 * Business Clearance Details Repository
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

import com.fusionx.lending.origination.domain.BusinessClearanceDetails;
import com.fusionx.lending.origination.enums.CommonStatus;



@Repository
public interface BusinessClearanceDetailsRepository extends JpaRepository<BusinessClearanceDetails, Long>{

	Optional<BusinessClearanceDetails> findByBusinessAdditionalDtlIdAndClearanceTypeIdAndStatus(Long businessAdditionalDtlId,
			Long clearanceTypeId, CommonStatus status);

	List<BusinessClearanceDetails> findByBusinessAdditionalDtlId(Long businessAdditionalDtlId);

	Optional<BusinessClearanceDetails> findByBusinessAdditionalDtlIdAndId(Long businessAdditionalDtlId, Long id);

	Optional<BusinessClearanceDetails> findByBusinessAdditionalDtlIdAndClearanceTypeIdAndStatusAndIdNotIn(Long businessAdditionalDtlId,
			Long clearanceTypeId, CommonStatus status, Long id);


}
