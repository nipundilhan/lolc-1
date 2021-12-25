package com.fusionx.lending.origination.repository;
/**
 * 	Salary Income Details Repository
 * 
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   01-09-2021   FXL-115  	 FXL-658       Piyumi       Created
 *    
 ********************************************************************************************************
*/


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.SalaryIncomeDetails;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.enums.SourceTypeEnum;

@Repository
public interface SalaryIncomeDetailsRepository extends JpaRepository<SalaryIncomeDetails, Long>{

	List<SalaryIncomeDetails> findByStatus(CommonStatus status);

	Optional<SalaryIncomeDetails> findByIncomeSourceDetailIdAndSourceTypeAndStatus(Long incomeSourceDetailId, SourceTypeEnum sourceType, CommonStatus status);

	Optional<SalaryIncomeDetails> findByIncomeSourceDetailIdAndSourceTypeAndStatusAndIdNotIn(Long incomeSourceDetailId,
			SourceTypeEnum sourceType, CommonStatus status, Long id);

	List<SalaryIncomeDetails> findByIncomeSourceDetailId(Long incomeSourceDetailsId);

	List<SalaryIncomeDetails> findByIncomeSourceDetailIdAndStatus(Long incomeSourceDetailsId,
			CommonStatus status);


}
