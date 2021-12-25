package com.fusionx.lending.origination.repository;

import java.util.List;
import java.util.Optional;

/**
 * Business Document Details Repository
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   08-09-2021   FXL-115  	 FXL-657       Piyumi       Created
 *    
 ********************************************************************************************************
*/

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.BusinessDocumentDetails;
import com.fusionx.lending.origination.enums.CommonStatus;



@Repository
public interface BusinessDocumentDetailsRepository extends JpaRepository<BusinessDocumentDetails, Long>{

	Optional<BusinessDocumentDetails> findByBusinessAdditionalDtlIdAndDocumentIdAndStatus(Long businessAdditionalDtlId, Long docId,
			CommonStatus status);

	List<BusinessDocumentDetails> findByBusinessAdditionalDtlId(Long businessAdditionalDtlId);

	Optional<BusinessDocumentDetails> findByBusinessAdditionalDtlIdAndDocumentIdAndStatusAndIdNotIn(Long businessAdditionalDtlId,
			Long docId, CommonStatus status, Long id);

	Optional<BusinessDocumentDetails> findByBusinessAdditionalDtlIdAndId(Long businessAdditionalDtlId, Long id);
}
