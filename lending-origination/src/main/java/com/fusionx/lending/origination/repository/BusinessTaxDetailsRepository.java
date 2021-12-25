package com.fusionx.lending.origination.repository;
/**
 * 	Business Tax Details Repository
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   15-09-2021   FXL-115  	 FXL-785       Piyumi       Created
 *    
 ********************************************************************************************************
*/

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.BusinessTaxDetails;
import com.fusionx.lending.origination.enums.CommonStatus;

@Repository
public interface BusinessTaxDetailsRepository extends JpaRepository<BusinessTaxDetails, Long> {

	List<BusinessTaxDetails> findByStatus(CommonStatus status);

	List<BusinessTaxDetails> findByBusinessIncomeDetailId(Long businessIncomeDetailId);

	List<BusinessTaxDetails> findByBusinessIncomeDetailIdAndStatus(Long businessIncomeDetailId, CommonStatus status);

	
}
