package com.fusionx.lending.origination.repository;

import java.util.List;
import java.util.Optional;

/**
 * Business Asset Details Repository
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

import com.fusionx.lending.origination.domain.BusinessAssetDetails;



@Repository
public interface BusinessAssetDetailsRepository extends JpaRepository<BusinessAssetDetails, Long>{

	List<BusinessAssetDetails> findByBusinessAdditionalDtlId(Long businessAdditionalDtlId);

	Optional<BusinessAssetDetails> findByBusinessAdditionalDtlIdAndId(Long businessAdditionalDtlId, Long id);


}
