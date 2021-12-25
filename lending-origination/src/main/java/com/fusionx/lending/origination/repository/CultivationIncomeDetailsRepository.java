package com.fusionx.lending.origination.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.CultivationIncomeDetails;
import com.fusionx.lending.origination.domain.SalaryIncomeDetails;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.enums.SourceTypeEnum;

/**
 * 	Cultivation Income Details Repository
 * 
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   01-09-2021   FXL-115  	 FXL-661       Dilhan       Created
 *    
 ********************************************************************************************************
*/
@Repository
public interface CultivationIncomeDetailsRepository extends JpaRepository<CultivationIncomeDetails, Long>{

	Optional<CultivationIncomeDetails> findByIncomeSourceDetailIdAndSourceTypeAndStatusAndIdNotIn(Long incomeSourceDetailId,
			SourceTypeEnum sourceType, CommonStatus status, Long id);
	
	List<CultivationIncomeDetails> findByIncomeSourceDetailId(Long incomeSourceDetailsId);
	
	List<CultivationIncomeDetails> findByStatus(CommonStatus status);
	
	Optional<CultivationIncomeDetails> findByIncomeSourceDetailIdAndSourceTypeAndStatus(Long incomeSourceDetailId, SourceTypeEnum sourceType, CommonStatus status);

	List<CultivationIncomeDetails> findByIncomeSourceDetailIdAndStatus(Long incomeSourceDetailsId, CommonStatus status);
}
