package com.fusionx.lending.origination.repository;

import java.util.List;

/**
 * Business Additional Details Repository
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

import com.fusionx.lending.origination.domain.BusinessAdditionalDetails;
import com.fusionx.lending.origination.enums.CommonStatus;



@Repository
public interface BusinessAdditionalDetailsRepository extends JpaRepository<BusinessAdditionalDetails, Long>{

	List<BusinessAdditionalDetails> findByStatus(CommonStatus status);


}
