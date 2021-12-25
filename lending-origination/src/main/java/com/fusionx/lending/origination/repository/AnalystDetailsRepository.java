package com.fusionx.lending.origination.repository;

/**
 * 	Analyst Details Repository
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   24-08-2021   FXL-117  	 FXL-543       Piyumi     Created
 *    
 ********************************************************************************************************
*/

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.fusionx.lending.origination.domain.AnalystDetails;
import com.fusionx.lending.origination.enums.CommonStatus;

@Repository
public interface AnalystDetailsRepository extends JpaRepository<AnalystDetails, Long> {

	List<AnalystDetails> findByStatus(CommonStatus status);

	List<AnalystDetails> findByLeadInfoId(Long leadInfoId);

}
