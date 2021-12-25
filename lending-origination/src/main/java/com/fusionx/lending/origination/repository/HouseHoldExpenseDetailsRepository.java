package com.fusionx.lending.origination.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.HouseHoldExpenseDetails;
import com.fusionx.lending.origination.enums.CommonStatus;

/**
 * 	House Hold Expense Details Repository
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   02-09-2021                               Dilhan       Created
 *    
 ********************************************************************************************************
*/

@Repository
public interface HouseHoldExpenseDetailsRepository extends JpaRepository<HouseHoldExpenseDetails, Long>{

	List<HouseHoldExpenseDetails> findByIncomeSourceDetailsId(Long incomeSourceDetailId);

	List<HouseHoldExpenseDetails> findByIncomeSourceDetailsIdAndStatus(Long incomeSourceDetailId, CommonStatus status);
}
