package com.fusionx.lending.origination.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.IncomeSourceDetails;
/**
 * 	Other Income Details
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   09-09-2021   FXL-78  	     FXL-777       Dilki        Created
 *    
 ********************************************************************************************************
*/
import com.fusionx.lending.origination.domain.OtherIncomeDetails;
import com.fusionx.lending.origination.enums.CommonStatus;

@Repository
public interface OtherIncomeDetailsRepository extends JpaRepository<OtherIncomeDetails, Long> {

	List<OtherIncomeDetails> findByStatus(CommonStatus status);

	List<OtherIncomeDetails> findByIncomeSourceDetailsIdAndStatus(IncomeSourceDetails incomeSourceDetail, CommonStatus status);

}
